package android.view;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.app.slice.Slice;
import android.content.AutofillOptions;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.credentials.CredentialOption;
import android.credentials.GetCredentialException;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Interpolator;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.TtmlUtils;
import android.media.audio.Enums;
import android.media.audio.common.AudioChannelLayout;
import android.os.BatteryStats;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.OutcomeReceiver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.security.keystore.KeyProperties;
import android.service.credentials.CredentialProviderService;
import android.sysprop.DisplayProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatProperty;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.LongSparseLongArray;
import android.util.Pair;
import android.util.Pools;
import android.util.Property;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.StateSet;
import android.util.SuperNotCalledException;
import android.util.TypedValue;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityEventSource;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeIdManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.ContentCaptureSession;
import android.view.displayhash.DisplayHash;
import android.view.displayhash.DisplayHashResultCallback;
import android.view.flags.Flags;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.IntFlagMapping;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.view.translation.TranslationCapability;
import android.view.translation.ViewTranslationCallback;
import android.view.translation.ViewTranslationRequest;
import android.view.translation.ViewTranslationResponse;
import android.webkit.WebView;
import android.widget.Checkable;
import android.widget.ScrollBarDrawable;
import android.window.OnBackInvokedDispatcher;

import com.android.internal.R;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.policy.DecorView;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.view.ScrollCaptureInternal;
import com.android.internal.view.TooltipPopup;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.widget.ScrollBarUtils;

import com.google.android.collect.Lists;
import com.google.android.collect.Maps;
import com.samsung.android.content.smartclip.SemSmartClipCroppedArea;
import com.samsung.android.content.smartclip.SemSmartClipDataElement;
import com.samsung.android.content.smartclip.SemSmartClipDataExtractionListener;
import com.samsung.android.content.smartclip.SemSmartClipDataRepository;
import com.samsung.android.content.smartclip.SemSmartClipMetaTagArray;
import com.samsung.android.content.smartclip.SmartClipDataCropperImpl;
import com.samsung.android.content.smartclip.SmartClipDataElementImpl;
import com.samsung.android.core.CompatTranslator;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.media.SemMediaPostProcessor;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.wallpaperbackup.GenerateXML;
import com.samsung.android.widget.SemHoverPopupWindow;
import com.samsung.android.widget.SemPressGestureDetector;

import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class View implements Drawable.Callback, KeyEvent.Callback, AccessibilityEventSource {
    public static final int ACCESSIBILITY_CURSOR_POSITION_UNDEFINED = -1;
    public static final int ACCESSIBILITY_DATA_SENSITIVE_AUTO = 0;
    public static final int ACCESSIBILITY_DATA_SENSITIVE_NO = 2;
    public static final int ACCESSIBILITY_DATA_SENSITIVE_YES = 1;
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    static final int ACCESSIBILITY_LIVE_REGION_DEFAULT = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    static final int ALL_RTL_PROPERTIES_RESOLVED = 1610678816;
    public static final int AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 1;
    public static final String AUTOFILL_HINT_CREDENTIAL_MANAGER = "credential";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE =
            "creditCardExpirationDate";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DAY = "creditCardExpirationDay";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_MONTH =
            "creditCardExpirationMonth";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_YEAR =
            "creditCardExpirationYear";
    public static final String AUTOFILL_HINT_CREDIT_CARD_NUMBER = "creditCardNumber";
    public static final String AUTOFILL_HINT_CREDIT_CARD_SECURITY_CODE = "creditCardSecurityCode";
    public static final String AUTOFILL_HINT_EMAIL_ADDRESS = "emailAddress";
    public static final String AUTOFILL_HINT_NAME = "name";
    public static final String AUTOFILL_HINT_PASSWORD = "password";
    public static final String AUTOFILL_HINT_PASSWORD_AUTO = "passwordAuto";
    public static final String AUTOFILL_HINT_PHONE = "phone";
    public static final String AUTOFILL_HINT_POSTAL_ADDRESS = "postalAddress";
    public static final String AUTOFILL_HINT_POSTAL_CODE = "postalCode";
    public static final String AUTOFILL_HINT_USERNAME = "username";
    private static final String AUTOFILL_LOG_TAG = "View.Autofill";
    public static final int AUTOFILL_TYPE_DATE = 4;
    public static final int AUTOFILL_TYPE_LIST = 3;
    public static final int AUTOFILL_TYPE_NONE = 0;
    public static final int AUTOFILL_TYPE_TEXT = 1;
    public static final int AUTOFILL_TYPE_TOGGLE = 2;
    private static final int BLUR_MODE_NONE = -1;
    static final int CLICKABLE = 16384;
    private static final String CONTENT_CAPTURE_LOG_TAG = "View.ContentCapture";
    public static final int CONTENT_SENSITIVITY_AUTO = 0;
    public static final int CONTENT_SENSITIVITY_NOT_SENSITIVE = 2;
    public static final int CONTENT_SENSITIVITY_SENSITIVE = 1;
    static final int CONTEXT_CLICKABLE = 8388608;
    private static final boolean DBG = false;
    private static final String DEBUG_BLUR_TARGET_NAME = "NotificationShadeWindowView";
    private static final boolean DEBUG_CONTENT_CAPTURE = false;
    static final int DEBUG_CORNERS_SIZE_DIP = 8;
    static final int DISABLED = 32;
    public static final int DRAG_FLAG_ACCESSIBILITY_ACTION = 1024;
    public static final int DRAG_FLAG_FROM_RECENT = 2097152;
    public static final int DRAG_FLAG_GLOBAL = 256;
    public static final int DRAG_FLAG_GLOBAL_PERSISTABLE_URI_PERMISSION = 64;
    public static final int DRAG_FLAG_GLOBAL_PREFIX_URI_PERMISSION = 128;
    public static final int DRAG_FLAG_GLOBAL_SAME_APPLICATION = 4096;
    public static final int DRAG_FLAG_GLOBAL_URI_READ = 1;
    public static final int DRAG_FLAG_GLOBAL_URI_WRITE = 2;
    public static final int DRAG_FLAG_HIDE_CALLING_TASK_ON_DRAG_START = 16384;
    public static final int DRAG_FLAG_OBJECT_CAPTURE = 4194304;
    public static final int DRAG_FLAG_OPAQUE = 512;
    public static final int DRAG_FLAG_REQUEST_SURFACE_FOR_RETURN_ANIMATION = 2048;
    public static final int DRAG_FLAG_START_INTENT_SENDER_ON_UNHANDLED_DRAG = 8192;
    static final int DRAG_MASK = 3;
    static final int DRAWING_CACHE_ENABLED = 32768;

    @Deprecated public static final int DRAWING_CACHE_QUALITY_AUTO = 0;

    @Deprecated public static final int DRAWING_CACHE_QUALITY_HIGH = 1048576;

    @Deprecated public static final int DRAWING_CACHE_QUALITY_LOW = 524288;
    static final int DRAWING_CACHE_QUALITY_MASK = 1572864;
    static final int DRAW_MASK = 128;
    static final int DUPLICATE_PARENT_STATE = 4194304;
    static final int ENABLED = 0;
    static final int ENABLED_MASK = 32;
    static final int FADING_EDGE_HORIZONTAL = 4096;
    static final int FADING_EDGE_MASK = 12288;
    static final int FADING_EDGE_NONE = 0;
    static final int FADING_EDGE_VERTICAL = 8192;
    static final int FILTER_TOUCHES_WHEN_OBSCURED = 1024;
    public static final int FIND_VIEWS_WITH_ACCESSIBILITY_NODE_PROVIDERS = 4;
    public static final int FIND_VIEWS_WITH_CONTENT_DESCRIPTION = 2;
    public static final int FIND_VIEWS_WITH_TEXT = 1;
    private static final int FITS_SYSTEM_WINDOWS = 2;
    public static final int FOCUSABLE = 1;
    public static final int FOCUSABLES_ALL = 0;
    public static final int FOCUSABLES_TOUCH_MODE = 1;
    public static final int FOCUSABLE_AUTO = 16;
    static final int FOCUSABLE_IN_TOUCH_MODE = 262144;
    private static final int FOCUSABLE_MASK = 17;
    public static final int FOCUS_BACKWARD = 1;
    public static final int FOCUS_DOWN = 130;
    public static final int FOCUS_FORWARD = 2;
    public static final int FOCUS_LEFT = 17;
    public static final int FOCUS_RIGHT = 66;
    public static final int FOCUS_UP = 33;
    public static final int FRAME_RATE_CATEGORY_REASON_BOOST = 134217728;
    public static final int FRAME_RATE_CATEGORY_REASON_BOOST_TIMEOUT = 184549376;
    public static final int FRAME_RATE_CATEGORY_REASON_CONFLICTED = 167772160;
    public static final int FRAME_RATE_CATEGORY_REASON_IDLE_TIMEOUT = 201326592;
    public static final int FRAME_RATE_CATEGORY_REASON_INTERMITTENT = 33554432;
    public static final int FRAME_RATE_CATEGORY_REASON_INVALID = 83886080;
    public static final int FRAME_RATE_CATEGORY_REASON_LARGE = 50331648;
    public static final int FRAME_RATE_CATEGORY_REASON_LARGE_HINT = 218103808;
    private static final int FRAME_RATE_CATEGORY_REASON_MASK = -65536;
    public static final int FRAME_RATE_CATEGORY_REASON_REQUESTED = 67108864;
    public static final int FRAME_RATE_CATEGORY_REASON_SMALL = 16777216;
    public static final int FRAME_RATE_CATEGORY_REASON_TOUCH = 150994944;
    public static final int FRAME_RATE_CATEGORY_REASON_UNKNOWN = 0;
    public static final int FRAME_RATE_CATEGORY_REASON_VELOCITY = 100663296;
    private static final float FRAME_RATE_LAGRE_SIZE_PERCENTAGE_THRESHOLD = 0.9f;
    private static final float FRAME_RATE_NARROW_SIZE_DP = 10.0f;
    private static final float FRAME_RATE_SIZE_PERCENTAGE_THRESHOLD = 0.07f;
    private static final float FRAME_RATE_SQUARE_SMALL_SIZE_DP = 40.0f;
    public static final int GONE = 8;
    public static final int HAPTIC_FEEDBACK_ENABLED = 268435456;
    private static final int HOVERING_UI_DISABLED = 2;
    private static final int HOVERING_UI_ENABLED = 1;
    private static final int HOVERING_UI_MASK = 15;
    private static final int HOVERING_UI_NOT_DECIDED = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    static final int IMPORTANT_FOR_ACCESSIBILITY_DEFAULT = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int IMPORTANT_FOR_AUTOFILL_AUTO = 0;
    public static final int IMPORTANT_FOR_AUTOFILL_NO = 2;
    public static final int IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS = 8;
    public static final int IMPORTANT_FOR_AUTOFILL_YES = 1;
    public static final int IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_AUTO = 0;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_NO = 2;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_NO_EXCLUDE_DESCENDANTS = 8;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_YES = 1;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_YES_EXCLUDE_DESCENDANTS = 4;
    public static final int INVISIBLE = 4;
    public static final int KEEP_SCREEN_ON = 67108864;
    public static final int LAST_APP_AUTOFILL_ID = 1073741823;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    private static final int LAYOUT_DIRECTION_DEFAULT = 2;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    static final int LAYOUT_DIRECTION_RESOLVED_DEFAULT = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int LAYOUT_DIRECTION_UNDEFINED = -1;
    static final int LONG_CLICKABLE = 2097152;
    static final float MAX_FRAME_RATE = 120.0f;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    public static final int NOT_FOCUSABLE = 0;
    public static final int NO_ID = -1;
    static final int OPTIONAL_FITS_SYSTEM_WINDOWS = 2048;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    static final int PARENT_SAVE_DISABLED = 536870912;
    static final int PARENT_SAVE_DISABLED_MASK = 536870912;
    static final int PFLAG2_ACCESSIBILITY_FOCUSED = 67108864;
    static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_MASK = 25165824;
    static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_SHIFT = 23;
    static final int PFLAG2_DRAG_CAN_ACCEPT = 1;
    static final int PFLAG2_DRAG_HOVERED = 2;
    static final int PFLAG2_DRAWABLE_RESOLVED = 1073741824;
    static final int PFLAG2_HAS_TRANSIENT_STATE = Integer.MIN_VALUE;
    static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK = 7340032;
    static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_SHIFT = 20;
    static final int PFLAG2_LAYOUT_DIRECTION_MASK = 12;
    static final int PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT = 2;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED = 32;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_MASK = 48;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL = 16;
    static final int PFLAG2_PADDING_RESOLVED = 536870912;
    static final int PFLAG2_SUBTREE_ACCESSIBILITY_STATE_CHANGED = 134217728;
    static final int PFLAG2_TEXT_ALIGNMENT_MASK = 57344;
    static final int PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT = 13;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED = 65536;
    private static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT = 131072;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK = 917504;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT = 17;
    static final int PFLAG2_TEXT_DIRECTION_MASK = 448;
    static final int PFLAG2_TEXT_DIRECTION_MASK_SHIFT = 6;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED = 512;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT = 1024;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK = 7168;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT = 10;
    static final int PFLAG2_VIEW_QUICK_REJECTED = 268435456;
    private static final int PFLAG3_ACCESSIBILITY_HEADING = Integer.MIN_VALUE;
    private static final int PFLAG3_AGGREGATED_VISIBLE = 536870912;
    static final int PFLAG3_APPLYING_INSETS = 32;
    static final int PFLAG3_ASSIST_BLOCKED = 16384;
    private static final int PFLAG3_AUTOFILLID_EXPLICITLY_SET = 1073741824;
    static final int PFLAG3_CALLED_SUPER = 16;
    private static final int PFLAG3_CLUSTER = 32768;
    private static final int PFLAG3_FINGER_DOWN = 131072;
    static final int PFLAG3_FITTING_SYSTEM_WINDOWS = 64;
    private static final int PFLAG3_FOCUSED_BY_DEFAULT = 262144;
    private static final int PFLAG3_HAS_OVERLAPPING_RENDERING_FORCED = 16777216;
    static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK = 7864320;
    static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_SHIFT = 19;
    private static final int PFLAG3_IS_AUTOFILLED = 65536;
    static final int PFLAG3_IS_LAID_OUT = 4;
    static final int PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT = 8;
    static final int PFLAG3_NESTED_SCROLLING_ENABLED = 128;
    static final int PFLAG3_NOTIFY_AUTOFILL_ENTER_ON_LAYOUT = 134217728;
    private static final int PFLAG3_NO_REVEAL_ON_FOCUS = 67108864;
    private static final int PFLAG3_OVERLAPPING_RENDERING_FORCED_VALUE = 8388608;
    private static final int PFLAG3_SCREEN_READER_FOCUSABLE = 268435456;
    static final int PFLAG3_SCROLL_INDICATOR_BOTTOM = 512;
    static final int PFLAG3_SCROLL_INDICATOR_END = 8192;
    static final int PFLAG3_SCROLL_INDICATOR_LEFT = 1024;
    static final int PFLAG3_SCROLL_INDICATOR_RIGHT = 2048;
    static final int PFLAG3_SCROLL_INDICATOR_START = 4096;
    static final int PFLAG3_SCROLL_INDICATOR_TOP = 256;
    static final int PFLAG3_TEMPORARY_DETACH = 33554432;
    static final int PFLAG3_VIEW_IS_ANIMATING_ALPHA = 2;
    static final int PFLAG3_VIEW_IS_ANIMATING_TRANSFORM = 1;
    private static final int PFLAG4_ALLOW_CLICK_WHEN_DISABLED = 4096;
    private static final int PFLAG4_AUTOFILL_HIDE_HIGHLIGHT = 512;
    private static final int PFLAG4_AUTO_HANDWRITING_ENABLED = 65536;
    private static final int PFLAG4_CONTENT_CAPTURE_IMPORTANCE_CACHED_VALUE = 128;
    private static final int PFLAG4_CONTENT_CAPTURE_IMPORTANCE_IS_CACHED = 64;
    private static final int PFLAG4_CONTENT_CAPTURE_IMPORTANCE_MASK = 192;
    private static final int PFLAG4_CONTENT_SENSITIVITY_MASK = 50331648;
    private static final int PFLAG4_CONTENT_SENSITIVITY_SHIFT = 24;
    private static final int PFLAG4_DETACHED = 8192;
    private static final int PFLAG4_DRAG_A11Y_STARTED = 32768;
    static final int PFLAG4_FRAMEWORK_OPTIONAL_FITS_SYSTEM_WINDOWS = 256;
    private static final int PFLAG4_HAS_DRAWN = 134217728;
    private static final int PFLAG4_HAS_MOVED = 268435456;
    private static final int PFLAG4_HAS_TRANSLATION_TRANSIENT_STATE = 16384;
    private static final int PFLAG4_HAS_VIEW_PROPERTY_INVALIDATION = 536870912;
    private static final int PFLAG4_IMPORTANT_FOR_CONTENT_CAPTURE_MASK = 15;
    private static final int PFLAG4_IMPORTANT_FOR_CREDENTIAL_MANAGER = 131072;
    private static final int PFLAG4_IS_COUNTED_AS_SENSITIVE = 67108864;
    private static final int PFLAG4_NOTIFIED_CONTENT_CAPTURE_APPEARED = 16;
    private static final int PFLAG4_NOTIFIED_CONTENT_CAPTURE_DISAPPEARED = 32;
    private static final int PFLAG4_RELAYOUT_TRACING_ENABLED = 524288;
    private static final int PFLAG4_ROTARY_HAPTICS_DETERMINED = 1048576;
    private static final int PFLAG4_ROTARY_HAPTICS_ENABLED = 2097152;
    private static final int PFLAG4_ROTARY_HAPTICS_SCROLL_SINCE_LAST_ROTARY_INPUT = 4194304;
    private static final int PFLAG4_ROTARY_HAPTICS_WAITING_FOR_SCROLL_EVENT = 8388608;
    static final int PFLAG4_SCROLL_CAPTURE_HINT_MASK = 7168;
    static final int PFLAG4_SCROLL_CAPTURE_HINT_SHIFT = 10;
    private static final int PFLAG4_TRAVERSAL_TRACING_ENABLED = 262144;
    static final int PFLAG_ACTIVATED = 1073741824;
    static final int PFLAG_ALPHA_SET = 262144;
    static final int PFLAG_ANIMATION_STARTED = 65536;
    private static final int PFLAG_AWAKEN_SCROLL_BARS_ON_ATTACH = 134217728;
    static final int PFLAG_CANCEL_NEXT_UP_EVENT = 67108864;
    static final int PFLAG_DIRTY = 2097152;
    static final int PFLAG_DIRTY_MASK = 2097152;
    static final int PFLAG_DRAWABLE_STATE_DIRTY = 1024;
    static final int PFLAG_DRAWING_CACHE_VALID = 32768;
    static final int PFLAG_DRAWN = 32;
    static final int PFLAG_DRAW_ANIMATION = 64;
    static final int PFLAG_FOCUSED = 2;
    static final int PFLAG_FORCE_LAYOUT = 4096;
    static final int PFLAG_HAS_BOUNDS = 16;
    private static final int PFLAG_HOVERED = 268435456;
    static final int PFLAG_INVALIDATED = Integer.MIN_VALUE;
    static final int PFLAG_IS_ROOT_NAMESPACE = 8;
    static final int PFLAG_LAYOUT_REQUIRED = 8192;
    static final int PFLAG_MEASURED_DIMENSION_SET = 2048;
    private static final int PFLAG_NOTIFY_AUTOFILL_MANAGER_ON_CLICK = 536870912;
    static final int PFLAG_OPAQUE_BACKGROUND = 8388608;
    static final int PFLAG_OPAQUE_MASK = 25165824;
    static final int PFLAG_OPAQUE_SCROLLBARS = 16777216;
    private static final int PFLAG_PREPRESSED = 33554432;
    private static final int PFLAG_PRESSED = 16384;
    static final int PFLAG_REQUEST_TRANSPARENT_REGIONS = 512;
    private static final int PFLAG_SAVE_STATE_CALLED = 131072;
    static final int PFLAG_SCROLL_CONTAINER = 524288;
    static final int PFLAG_SCROLL_CONTAINER_ADDED = 1048576;
    static final int PFLAG_SELECTED = 4;
    static final int PFLAG_SKIP_DRAW = 128;
    static final int PFLAG_WANTS_FOCUS = 1;
    private static final int POPULATING_ACCESSIBILITY_EVENT_TYPES = 172479;
    private static final int PROVIDER_BACKGROUND = 0;
    private static final int PROVIDER_BOUNDS = 2;
    private static final int PROVIDER_NONE = 1;
    private static final int PROVIDER_PADDED_BOUNDS = 3;
    public static final int PUBLIC_STATUS_BAR_VISIBILITY_MASK = 16375;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_DEFAULT = Float.NaN;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_HIGH = -4.0f;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_LOW = -2.0f;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_NORMAL = -3.0f;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_NO_PREFERENCE = -1.0f;
    protected static final String SAMSUNG_BASIC_INTERACTION_METADATA_NAME =
            "SamsungBasicInteraction";
    protected static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP10 = "SEP10";
    protected static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP11 = "SEP11";
    protected static final String SAMSUNG_DISPLAY_CUTOUT_BG_METADATA_NAME =
            "DisplayCutoutBackground";
    static final int SAVE_DISABLED = 65536;
    static final int SAVE_DISABLED_MASK = 65536;
    public static final int SCREEN_STATE_OFF = 0;
    public static final int SCREEN_STATE_ON = 1;
    static final int SCROLLBARS_HORIZONTAL = 256;
    static final int SCROLLBARS_INSET_MASK = 16777216;
    public static final int SCROLLBARS_INSIDE_INSET = 16777216;
    public static final int SCROLLBARS_INSIDE_OVERLAY = 0;
    static final int SCROLLBARS_MASK = 768;
    static final int SCROLLBARS_NONE = 0;
    public static final int SCROLLBARS_OUTSIDE_INSET = 50331648;
    static final int SCROLLBARS_OUTSIDE_MASK = 33554432;
    public static final int SCROLLBARS_OUTSIDE_OVERLAY = 33554432;
    static final int SCROLLBARS_STYLE_MASK = 50331648;
    static final int SCROLLBARS_VERTICAL = 512;
    public static final int SCROLLBAR_POSITION_DEFAULT = 0;
    public static final int SCROLLBAR_POSITION_LEFT = 1;
    public static final int SCROLLBAR_POSITION_RIGHT = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_CAPTURE_HINT_AUTO = 0;
    public static final int SCROLL_CAPTURE_HINT_EXCLUDE = 1;
    public static final int SCROLL_CAPTURE_HINT_EXCLUDE_DESCENDANTS = 4;
    public static final int SCROLL_CAPTURE_HINT_INCLUDE = 2;
    static final int SCROLL_INDICATORS_NONE = 0;
    static final int SCROLL_INDICATORS_PFLAG3_MASK = 16128;
    static final int SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT = 8;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    public static final int SEM_DRAG_FLAG_NO_ANIMATION = 1048576;
    public static final int SEM_ROUNDED_CORNER_ALL = 15;
    public static final int SEM_ROUNDED_CORNER_BOTTOM_LEFT = 4;
    public static final int SEM_ROUNDED_CORNER_BOTTOM_RIGHT = 8;
    public static final int SEM_ROUNDED_CORNER_NONE = 0;
    public static final int SEM_ROUNDED_CORNER_TOP_LEFT = 1;
    public static final int SEM_ROUNDED_CORNER_TOP_RIGHT = 2;
    static final int SEM_SPEN_HOVERED = 1;
    static final int SEM_TOOLTIP = 2;
    public static final int SOUND_EFFECTS_ENABLED = 134217728;
    public static final int STATUS_BAR_DISABLE_BACK = 4194304;
    public static final int STATUS_BAR_DISABLE_CLOCK = 8388608;
    public static final int STATUS_BAR_DISABLE_EXPAND = 65536;
    public static final int STATUS_BAR_DISABLE_HOME = 2097152;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_ICONS = 131072;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_TICKER = 524288;
    public static final int STATUS_BAR_DISABLE_ONGOING_CALL_CHIP = 67108864;
    public static final int STATUS_BAR_DISABLE_RECENT = 16777216;
    public static final int STATUS_BAR_DISABLE_SEARCH = 33554432;
    public static final int STATUS_BAR_DISABLE_SYSTEM_INFO = 1048576;

    @Deprecated public static final int STATUS_BAR_HIDDEN = 1;

    @Deprecated public static final int STATUS_BAR_VISIBLE = 0;
    public static final int SYSTEM_UI_CLEARABLE_FLAGS = 7;

    @Deprecated public static final int SYSTEM_UI_FLAG_FULLSCREEN = 4;

    @Deprecated public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = 2;

    @Deprecated public static final int SYSTEM_UI_FLAG_IMMERSIVE = 2048;

    @Deprecated public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 4096;

    @Deprecated public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 1024;
    public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = 512;

    @Deprecated public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE = 256;

    @Deprecated public static final int SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR = 16;

    @Deprecated public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;

    @Deprecated public static final int SYSTEM_UI_FLAG_LOW_PROFILE = 1;

    @Deprecated public static final int SYSTEM_UI_FLAG_VISIBLE = 0;

    @Deprecated public static final int SYSTEM_UI_LAYOUT_FLAGS = 1536;
    private static final int SYSTEM_UI_RESERVED_LEGACY1 = 16384;
    private static final int SYSTEM_UI_RESERVED_LEGACY2 = 65536;
    public static final int TEXT_ALIGNMENT_CENTER = 4;
    private static final int TEXT_ALIGNMENT_DEFAULT = 1;
    public static final int TEXT_ALIGNMENT_GRAVITY = 1;
    public static final int TEXT_ALIGNMENT_INHERIT = 0;
    static final int TEXT_ALIGNMENT_RESOLVED_DEFAULT = 1;
    public static final int TEXT_ALIGNMENT_TEXT_END = 3;
    public static final int TEXT_ALIGNMENT_TEXT_START = 2;
    public static final int TEXT_ALIGNMENT_VIEW_END = 6;
    public static final int TEXT_ALIGNMENT_VIEW_START = 5;
    public static final int TEXT_DIRECTION_ANY_RTL = 2;
    private static final int TEXT_DIRECTION_DEFAULT = 0;
    public static final int TEXT_DIRECTION_FIRST_STRONG = 1;
    public static final int TEXT_DIRECTION_FIRST_STRONG_LTR = 6;
    public static final int TEXT_DIRECTION_FIRST_STRONG_RTL = 7;
    public static final int TEXT_DIRECTION_INHERIT = 0;
    public static final int TEXT_DIRECTION_LOCALE = 5;
    public static final int TEXT_DIRECTION_LTR = 3;
    static final int TEXT_DIRECTION_RESOLVED_DEFAULT = 1;
    public static final int TEXT_DIRECTION_RTL = 4;
    static final int TOOLTIP = 1073741824;
    private static final int UNDEFINED_PADDING = Integer.MIN_VALUE;
    protected static final String VIEW_LOG_TAG = "View";
    protected static final int VIEW_STRUCTURE_FOR_ASSIST = 0;
    protected static final int VIEW_STRUCTURE_FOR_AUTOFILL = 1;
    protected static final int VIEW_STRUCTURE_FOR_CONTENT_CAPTURE = 2;
    static final int VISIBILITY_MASK = 12;
    public static final int VISIBLE = 0;
    static final int WILL_NOT_CACHE_DRAWING = 131072;
    static final int WILL_NOT_DRAW = 128;
    private static SparseArray<String> mAttributeMap;
    private static boolean sAcceptZeroSizeDragShadow;
    private static boolean sAlwaysAssignFocus;
    private static boolean sAutoFocusableOffUIThreadWontNotifyParents;
    static boolean sBrokenInsetsDispatch;
    protected static boolean sBrokenWindowBackground;
    private static boolean sCanFocusZeroSized;
    static boolean sCascadedDragDrop;
    private static Paint sDebugPaint;
    public static String sDebugViewAttributesApplicationPackage;
    static boolean sForceLayoutWhenInsetsChanged;
    static boolean sHasFocusableExcludeAutoFocusable;
    private static int sNextAccessibilityViewId;
    protected static boolean sPreserveMarginParamsInLayoutParamConversion;
    private static boolean sThrowOnInvalidFloatProperties;
    private static boolean sTraceLayoutSteps;
    private static String sTraceRequestLayoutClass;
    private static boolean sUseDefaultFocusHighlight;
    private float VELOCITY_FRAMERATE1;
    private float VELOCITY_FRAMERATE2;
    private float VELOCITY_FRAMERATE3;
    private float VELOCITY_FRAMERATE4;
    private float VELOCITY_THRESHOLD_T1;
    private float VELOCITY_THRESHOLD_T2;
    private float VELOCITY_THRESHOLD_T3;
    boolean isPenSideButton;
    private int mAccessibilityCursorPosition;
    AccessibilityDelegate mAccessibilityDelegate;
    private CharSequence mAccessibilityPaneTitle;
    private int mAccessibilityTraversalAfterId;
    private int mAccessibilityTraversalBeforeId;
    private int mAccessibilityViewId;
    private String mAllowedHandwritingDelegatePackageName;
    private String mAllowedHandwritingDelegatorPackageName;
    private float mAmbiguousGestureMultiplier;
    private ViewPropertyAnimator mAnimator;
    protected int mAppWidgetScrollBarBottomPadding;
    protected int mAppWidgetScrollBarTopPadding;
    AttachInfo mAttachInfo;
    private SparseArray<int[]> mAttributeResolutionStacks;
    private SparseIntArray mAttributeSourceResId;

    @ViewDebug.ExportedProperty(category = "attributes", hasAdjacentMapping = true)
    public String[] mAttributes;

    private String[] mAutofillHints;
    private AutofillId mAutofillId;
    private int mAutofillViewId;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "bg_")
    private Drawable mBackground;

    private int mBackgroundBlurColor;
    private float mBackgroundBlurCornerRadiusBL;
    private float mBackgroundBlurCornerRadiusBR;
    private float mBackgroundBlurCornerRadiusTL;
    private float mBackgroundBlurCornerRadiusTR;
    RenderNode mBackgroundRenderNode;
    private int mBackgroundResource;
    private boolean mBackgroundSizeChanged;
    private TintInfo mBackgroundTint;
    String mBixbyTouchFoundText;
    private Rect mBlurBitmapBounds;
    private SemBlurInfo.ColorCurve mBlurColorCurve;
    private SemGfxImageFilter mBlurFilter;
    private SemBlurInfo mBlurInfo;

    @ViewDebug.ExportedProperty private int mBlurMode;
    private int mBlurRadius;
    private Rect mBlurViewBounds;
    private Bitmap mBlurredBitmap;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    protected int mBottom;

    private Drawable mBottomLeftRound;
    private int mBottomLeftRoundColor;
    private Drawable mBottomRightRound;
    private int mBottomRightRoundColor;
    public boolean mCachingFailed;
    private Bitmap mCanvasBlurBitmap;
    private int mCanvasDownScale;
    private Bitmap mCapturedBitmap;
    private boolean mCapturingCanvas;

    @ViewDebug.ExportedProperty(category = "drawing")
    Rect mClipBounds;

    private ContentCaptureSession mContentCaptureSession;
    private boolean mContentCaptureSessionCached;
    private CharSequence mContentDescription;

    @ViewDebug.ExportedProperty(deepExport = true)
    protected Context mContext;

    private int mCornerOffset;
    protected Animation mCurrentAnimation;
    private Drawable mDefaultFocusHighlight;
    private Drawable mDefaultFocusHighlightCache;
    boolean mDefaultFocusHighlightEnabled;
    private boolean mDefaultFocusHighlightSizeChanged;
    private int mDefaultRoundedCornerColor;
    private boolean mDisablePenGestureforfactorytest;
    private int[] mDrawableState;
    private Bitmap mDrawingCache;
    private int mDrawingCacheBackgroundColor;
    private int mExplicitAccessibilityDataSensitive;
    private int mExplicitStyle;
    private int mExtendedTouchSlop;
    public int mExtraPaddingBottomForPreference;
    private ViewTreeObserver mFloatingTreeObserver;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "fg_")
    private ForegroundInfo mForegroundInfo;

    private float mFrameContentVelocity;
    private ArrayList<FrameMetricsObserver> mFrameMetricsObservers;
    int mFrameRateCompatibility;
    private SemGfxImageFilter mGfxImageFilter;
    GhostView mGhostView;
    private float mHandwritingBoundsOffsetBottom;
    private float mHandwritingBoundsOffsetLeft;
    private float mHandwritingBoundsOffsetRight;
    private float mHandwritingBoundsOffsetTop;
    private int mHandwritingDelegateFlags;
    private Runnable mHandwritingDelegatorCallback;
    private boolean mHasPerformedLongPress;
    private int mHorizontalScrollbarPosition;
    protected SemHoverPopupWindow mHoverPopup;
    protected int mHoverPopupToolTypeByApp;
    protected int mHoverPopupType;
    private boolean mHoveringTouchDelegate;

    @ViewDebug.ExportedProperty(resolveId = true)
    int mID;

    private boolean mIgnoreNextUpEvent;
    private boolean mInContextButtonPress;
    private int mInferredAccessibilityDataSensitive;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private boolean mIsDeviceDefault;
    private boolean mIsFlingState;
    private boolean mIsHandwritingDelegate;
    private boolean mIsSetFingerHoveredInAppWidget;
    private SparseArray<Object> mKeyedTags;
    private int mLabelForId;
    private int mLastBlurRadius;
    private int mLastFrameLeft;
    private int mLastFrameRateCategory;
    private int mLastFrameTop;
    private boolean mLastIsOpaque;
    Paint mLayerPaint;
    int mLayerType;
    private Insets mLayoutInsets;
    protected ViewGroup.LayoutParams mLayoutParams;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    protected int mLeft;

    private boolean mLeftPaddingDefined;
    ListenerInfo mListenerInfo;
    private float mLongClickX;
    private float mLongClickY;
    private MatchIdPredicate mMatchIdPredicate;
    private MatchLabelForPredicate mMatchLabelForPredicate;
    private LongSparseLongArray mMeasureCache;

    @ViewDebug.ExportedProperty(category = "measurement")
    int mMeasuredHeight;

    @ViewDebug.ExportedProperty(category = "measurement")
    int mMeasuredWidth;

    @ViewDebug.ExportedProperty(category = "measurement")
    private int mMinHeight;

    @ViewDebug.ExportedProperty(category = "measurement")
    private int mMinWidth;

    private PointerIcon mMousePointerIcon;
    private boolean mNeedToSendSavedStickyDragEvent;
    private boolean mNeededToChangedScrollBarPosition;
    private ViewParent mNestedScrollingParent;
    int mNextClusterForwardId;
    private int mNextFocusDownId;
    int mNextFocusForwardId;
    private int mNextFocusLeftId;
    private int mNextFocusRightId;
    private int mNextFocusUpId;
    int mOldHeightMeasureSpec;
    int mOldWidthMeasureSpec;
    public OnAddRemoveListener mOnAddRemoveListener;
    ViewOutlineProvider mOutlineProvider;
    private int mOverScrollMode;
    ViewOverlay mOverlay;

    @ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingBottom;

    @ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingLeft;

    @ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingRight;

    @ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingTop;

    protected ViewParent mParent;
    private CheckForLongPress mPendingCheckForLongPress;
    private CheckForTap mPendingCheckForTap;
    private PerformClick mPerformClick;
    private PointerIcon mPointerIconForMouse;
    private PointerIcon mPointerIconForStylus;
    private float mPreferredFrameRate;

    @ViewDebug.ExportedProperty(
            flagMapping = {
                @ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "FORCE_LAYOUT"),
                @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LAYOUT_REQUIRED"),
                @ViewDebug.FlagToString(
                        equals = 32768,
                        mask = 32768,
                        name = "DRAWING_CACHE_INVALID",
                        outputIf = false),
                @ViewDebug.FlagToString(equals = 32, mask = 32, name = "DRAWN", outputIf = true),
                @ViewDebug.FlagToString(
                        equals = 32,
                        mask = 32,
                        name = "NOT_DRAWN",
                        outputIf = false),
                @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "DIRTY")
            },
            formatToHexString = true)
    public int mPrivateFlags;

    int mPrivateFlags2;
    int mPrivateFlags3;
    private int mPrivateFlags4;
    private String[] mReceiveContentMimeTypes;
    boolean mRecreateDisplayList;
    final RenderNode mRenderNode;
    private final Resources mResources;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    protected int mRight;

    private boolean mRightPaddingDefined;
    private int mRoundRadius;
    private RoundScrollbarRenderer mRoundScrollbarRenderer;
    private Rect mRoundedCornerBounds;
    private int mRoundedCornerMode;
    private Pair<Integer, Integer> mRoundedCornerOffset;
    private HandlerActionQueue mRunQueue;
    private int mScrollBarPositionPadding;
    private ScrollabilityCache mScrollCache;
    public HapticScrollFeedbackProvider mScrollFeedbackProvider;
    private Drawable mScrollIndicatorDrawable;

    @ViewDebug.ExportedProperty(category = "scrolling")
    protected int mScrollX;

    @ViewDebug.ExportedProperty(category = "scrolling")
    protected int mScrollY;

    public final Rect mSemHorizontalScrollbarRect;
    public boolean mSemScrollingByScrollbar;
    public boolean mSemScrollingVertical;
    public final Rect mSemVerticalScrollbarRect;
    private int mSemViewFlags;
    private SendAccessibilityEventThrottle mSendStateChangedAccessibilityEvent;
    private SendViewScrolledAccessibilityEvent mSendViewScrolledAccessibilityEvent;
    private boolean mSendingHoverAccessibilityEvents;
    private boolean mShouldFakeFocus;
    private int mSizeBasedFrameRateCategoryAndReason;
    protected SemSmartClipDataExtractionListener mSmartClipDataExtractionListener;
    protected SemSmartClipMetaTagArray mSmartClipDataTag;
    private int mSourceLayoutId;
    String mStartActivityRequestWho;
    private CharSequence mStateDescription;
    private StateListAnimator mStateListAnimator;

    @ViewDebug.ExportedProperty(
            flagMapping = {
                @ViewDebug.FlagToString(equals = 1, mask = 1, name = "LOW_PROFILE"),
                @ViewDebug.FlagToString(equals = 2, mask = 2, name = "HIDE_NAVIGATION"),
                @ViewDebug.FlagToString(equals = 4, mask = 4, name = "FULLSCREEN"),
                @ViewDebug.FlagToString(equals = 256, mask = 256, name = "LAYOUT_STABLE"),
                @ViewDebug.FlagToString(equals = 512, mask = 512, name = "LAYOUT_HIDE_NAVIGATION"),
                @ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "LAYOUT_FULLSCREEN"),
                @ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "IMMERSIVE"),
                @ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "IMMERSIVE_STICKY"),
                @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LIGHT_STATUS_BAR"),
                @ViewDebug.FlagToString(equals = 16, mask = 16, name = "LIGHT_NAVIGATION_BAR"),
                @ViewDebug.FlagToString(
                        equals = 65536,
                        mask = 65536,
                        name = "STATUS_BAR_DISABLE_EXPAND"),
                @ViewDebug.FlagToString(
                        equals = 131072,
                        mask = 131072,
                        name = "STATUS_BAR_DISABLE_NOTIFICATION_ICONS"),
                @ViewDebug.FlagToString(
                        equals = 262144,
                        mask = 262144,
                        name = "STATUS_BAR_DISABLE_NOTIFICATION_ALERTS"),
                @ViewDebug.FlagToString(
                        equals = 524288,
                        mask = 524288,
                        name = "STATUS_BAR_DISABLE_NOTIFICATION_TICKER"),
                @ViewDebug.FlagToString(
                        equals = 1048576,
                        mask = 1048576,
                        name = "STATUS_BAR_DISABLE_SYSTEM_INFO"),
                @ViewDebug.FlagToString(
                        equals = 2097152,
                        mask = 2097152,
                        name = "STATUS_BAR_DISABLE_HOME"),
                @ViewDebug.FlagToString(
                        equals = 4194304,
                        mask = 4194304,
                        name = "STATUS_BAR_DISABLE_BACK"),
                @ViewDebug.FlagToString(
                        equals = 8388608,
                        mask = 8388608,
                        name = "STATUS_BAR_DISABLE_CLOCK"),
                @ViewDebug.FlagToString(
                        equals = 16777216,
                        mask = 16777216,
                        name = "STATUS_BAR_DISABLE_RECENT"),
                @ViewDebug.FlagToString(
                        equals = 33554432,
                        mask = 33554432,
                        name = "STATUS_BAR_DISABLE_SEARCH"),
                @ViewDebug.FlagToString(
                        equals = 67108864,
                        mask = 67108864,
                        name = "STATUS_BAR_DISABLE_ONGOING_CALL_CHIP")
            },
            formatToHexString = true)
    int mSystemUiVisibility;

    protected Object mTag;
    private int[] mTempNestedScrollConsumed;
    TooltipInfo mTooltipInfo;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    protected int mTop;

    private Drawable mTopLeftRound;
    private int mTopLeftRoundColor;
    private Drawable mTopRightRound;
    private int mTopRightRoundColor;
    private TouchDelegate mTouchDelegate;
    private int mTouchSlop;
    private ViewTraversalTracingStrings mTracingStrings;
    public TransformationInfo mTransformationInfo;
    int mTransientStateCount;
    private String mTransitionName;
    int mUnbufferedInputSource;
    private Bitmap mUnscaledDrawingCache;
    private UnsetPressedState mUnsetPressedState;

    @ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingBottom;

    @ViewDebug.ExportedProperty(category = "padding")
    int mUserPaddingEnd;

    @ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingLeft;

    int mUserPaddingLeftInitial;

    @ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingRight;

    int mUserPaddingRightInitial;

    @ViewDebug.ExportedProperty(category = "padding")
    int mUserPaddingStart;

    private float mVerticalScrollFactor;
    private int mVerticalScrollbarPosition;
    private Vibrator mVibrator;
    private ViewCredentialHandler mViewCredentialHandler;

    @ViewDebug.ExportedProperty(formatToHexString = true)
    int mViewFlags;

    private ViewTranslationCallback mViewTranslationCallback;
    private ViewTranslationResponse mViewTranslationResponse;
    private Handler mVisibilityChangeForAutofillHandler;
    int mWindowAttachCount;
    public static boolean DEBUG_DRAW = false;
    public static boolean sDebugViewAttributes = false;
    private static final int[] AUTOFILL_HIGHLIGHT_ATTR = {16844136};
    private static boolean sCompatibilityDone = false;
    private static boolean sAlwaysRemeasureExactly = false;
    static boolean sTextureViewIgnoresDrawableSetters = false;
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static final int[] DRAWING_CACHE_QUALITY_FLAGS = {0, 524288, 1048576};
    private static final boolean sToolkitFrameRateDefaultNormalReadOnlyFlagValue =
            Flags.toolkitFrameRateDefaultNormalReadOnly();
    private static final boolean sToolkitFrameRateBySizeReadOnlyFlagValue =
            Flags.toolkitFrameRateBySizeReadOnly();
    private static final boolean sToolkitFrameRateSmallUsesPercentReadOnlyFlagValue =
            Flags.toolkitFrameRateSmallUsesPercentReadOnly();
    private static final boolean sToolkitFrameRateViewEnablingReadOnlyFlagValue =
            Flags.toolkitFrameRateViewEnablingReadOnly();
    private static boolean sToolkitFrameRateVelocityMappingReadOnlyFlagValue =
            Flags.toolkitFrameRateVelocityMappingReadOnly();
    protected static final int[] EMPTY_STATE_SET = StateSet.get(0);
    protected static final int[] WINDOW_FOCUSED_STATE_SET = StateSet.get(1);
    protected static final int[] SELECTED_STATE_SET = StateSet.get(2);
    protected static final int[] SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(3);
    protected static final int[] FOCUSED_STATE_SET = StateSet.get(4);
    protected static final int[] FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(5);
    protected static final int[] FOCUSED_SELECTED_STATE_SET = StateSet.get(6);
    protected static final int[] FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(7);
    protected static final int[] ENABLED_STATE_SET = StateSet.get(8);
    protected static final int[] ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(9);
    protected static final int[] ENABLED_SELECTED_STATE_SET = StateSet.get(10);
    protected static final int[] ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(11);
    protected static final int[] ENABLED_FOCUSED_STATE_SET = StateSet.get(12);
    protected static final int[] ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(13);
    protected static final int[] ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(14);
    protected static final int[] ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET =
            StateSet.get(15);
    protected static final int[] PRESSED_STATE_SET = StateSet.get(16);
    protected static final int[] PRESSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(17);
    protected static final int[] PRESSED_SELECTED_STATE_SET = StateSet.get(18);
    protected static final int[] PRESSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(19);
    protected static final int[] PRESSED_FOCUSED_STATE_SET = StateSet.get(20);
    protected static final int[] PRESSED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(21);
    protected static final int[] PRESSED_FOCUSED_SELECTED_STATE_SET = StateSet.get(22);
    protected static final int[] PRESSED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET =
            StateSet.get(23);
    protected static final int[] PRESSED_ENABLED_STATE_SET = StateSet.get(24);
    protected static final int[] PRESSED_ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(25);
    protected static final int[] PRESSED_ENABLED_SELECTED_STATE_SET = StateSet.get(26);
    protected static final int[] PRESSED_ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET =
            StateSet.get(27);
    protected static final int[] PRESSED_ENABLED_FOCUSED_STATE_SET = StateSet.get(28);
    protected static final int[] PRESSED_ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET =
            StateSet.get(29);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(30);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET =
            StateSet.get(31);
    protected static boolean sToolkitSetFrameRateReadOnlyFlagValue =
            Flags.toolkitSetFrameRateReadOnly();
    private static boolean sToolkitMetricsForFrameRateDecisionFlagValue =
            Flags.toolkitMetricsForFrameRateDecision();
    private static boolean sUseMeasureCacheDuringForceLayoutFlagValue =
            Flags.enableUseMeasureCacheDuringForceLayout();
    static final int DEBUG_CORNERS_COLOR = Color.rgb(63, 127, 255);
    static final ThreadLocal<Rect> sThreadLocal =
            ThreadLocal.withInitial(
                    new Supplier() { // from class: android.view.View$$ExternalSyntheticLambda3
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return new Rect();
                        }
                    });
    private static final int[] LAYOUT_DIRECTION_FLAGS = {0, 1, 2, 3};
    private static final int[] PFLAG2_TEXT_DIRECTION_FLAGS = {0, 64, 128, 192, 256, 320, 384, 448};
    private static final int[] PFLAG2_TEXT_ALIGNMENT_FLAGS = {
        0,
        8192,
        16384,
        24576,
        32768,
        UsbManager.USB_DATA_TRANSFER_RATE_40G,
        AudioChannelLayout.VOICE_CALL_MONO
    };
    private static final boolean DEBUG_ROUNDED_CORNER =
            SystemProperties.getBoolean("view.debug.rounded_corner", false);
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static boolean sIsSamsungBasicInteraction = false;
    public static int sSEP_Version = 0;
    public static boolean sIsDisplayCutoutBackground = false;
    private static boolean sMetaDataNeedCheck = true;
    public static final Property<View, Float> ALPHA =
            new FloatProperty<View>("alpha") { // from class: android.view.View.3
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    if (object != null) {
                        object.setAlpha(value);
                    }
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getAlpha());
                }
            };
    public static final Property<View, Float> TRANSLATION_X =
            new FloatProperty<View>("translationX") { // from class: android.view.View.4
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setTranslationX(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getTranslationX());
                }
            };
    public static final Property<View, Float> TRANSLATION_Y =
            new FloatProperty<View>("translationY") { // from class: android.view.View.5
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setTranslationY(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getTranslationY());
                }
            };
    public static final Property<View, Float> TRANSLATION_Z =
            new FloatProperty<View>("translationZ") { // from class: android.view.View.6
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setTranslationZ(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getTranslationZ());
                }
            };
    public static final Property<View, Float> X =
            new FloatProperty<View>("x") { // from class: android.view.View.7
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setX(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getX());
                }
            };
    public static final Property<View, Float> Y =
            new FloatProperty<View>("y") { // from class: android.view.View.8
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setY(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getY());
                }
            };
    public static final Property<View, Float> Z =
            new FloatProperty<View>("z") { // from class: android.view.View.9
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setZ(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getZ());
                }
            };
    public static final Property<View, Float> ROTATION =
            new FloatProperty<View>(GenerateXML.ROTATION) { // from class: android.view.View.10
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setRotation(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getRotation());
                }
            };
    public static final Property<View, Float> ROTATION_X =
            new FloatProperty<View>("rotationX") { // from class: android.view.View.11
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setRotationX(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getRotationX());
                }
            };
    public static final Property<View, Float> ROTATION_Y =
            new FloatProperty<View>("rotationY") { // from class: android.view.View.12
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setRotationY(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getRotationY());
                }
            };
    public static final Property<View, Float> SCALE_X =
            new FloatProperty<View>("scaleX") { // from class: android.view.View.13
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setScaleX(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getScaleX());
                }
            };
    public static final Property<View, Float> SCALE_Y =
            new FloatProperty<View>("scaleY") { // from class: android.view.View.14
                @Override // android.util.FloatProperty
                public void setValue(View object, float value) {
                    object.setScaleY(value);
                }

                @Override // android.util.Property
                public Float get(View object) {
                    return Float.valueOf(object.getScaleY());
                }
            };
    private static int sHoverUIEnableFlag = 0;
    protected static int sHoverUIFeatureLevel = -1;
    protected static boolean sIsCheckedHoverUIFeatureLevel = false;
    static final int TEST_BLUR_DISABLED = SystemProperties.getInt("test.debug.blur_disabled", 0);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessibilityDataSensitive {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillFlags {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillImportance {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentCaptureImportance {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentSensitivity {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawingCacheQuality {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FindViewFlags {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusDirection {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusRealDirection {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Focusable {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusableMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayerType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutDir {}

    public interface OnAddRemoveListener {
        void onViewAdded(View view, View view2);

        void onViewRemoved(View view, View view2);
    }

    public interface OnApplyWindowInsetsListener {
        WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets);
    }

    public interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    public interface OnCapturedPointerListener {
        boolean onCapturedPointer(View view, MotionEvent motionEvent);
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    public interface OnContextClickListener {
        boolean onContextClick(View view);
    }

    public interface OnCreateContextMenuListener {
        void onCreateContextMenu(
                ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo);
    }

    public interface OnDragListener {
        boolean onDrag(View view, DragEvent dragEvent);
    }

    public interface OnFocusChangeListener {
        void onFocusChange(View view, boolean z);
    }

    public interface OnGenericMotionListener {
        boolean onGenericMotion(View view, MotionEvent motionEvent);
    }

    public interface OnHoverListener {
        boolean onHover(View view, MotionEvent motionEvent);
    }

    public interface OnKeyListener {
        boolean onKey(View view, int i, KeyEvent keyEvent);
    }

    public interface OnLayoutChangeListener {
        void onLayoutChange(
                View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);
    }

    public interface OnScrollChangeListener {
        void onScrollChange(View view, int i, int i2, int i3, int i4);
    }

    @Deprecated
    public interface OnSystemUiVisibilityChangeListener {
        void onSystemUiVisibilityChange(int i);
    }

    public interface OnTouchListener {
        boolean onTouch(View view, MotionEvent motionEvent);
    }

    public interface OnUnhandledKeyEventListener {
        boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResolvedLayoutDir {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollBarStyle {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollCaptureHint {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollIndicators {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemUiVisibility {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextAlignment {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewStructureType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {}

    public final class InspectionCompanion
            implements android.view.inspector.InspectionCompanion<View> {
        private int mAccessibilityFocusedId;
        private int mAccessibilityHeadingId;
        private int mAccessibilityLiveRegionId;
        private int mAccessibilityPaneTitleId;
        private int mAccessibilityTraversalAfterId;
        private int mAccessibilityTraversalBeforeId;
        private int mActivatedId;
        private int mAlphaId;
        private int mAutofillHintsId;
        private int mBackgroundId;
        private int mBackgroundTintId;
        private int mBackgroundTintModeId;
        private int mBaselineId;
        private int mClickableId;
        private int mContentDescriptionId;
        private int mContextClickableId;
        private int mDefaultFocusHighlightEnabledId;
        private int mDrawingCacheQualityId;
        private int mDuplicateParentStateId;
        private int mElevationId;
        private int mEnabledId;
        private int mFadingEdgeLengthId;
        private int mFilterTouchesWhenObscuredId;
        private int mFitsSystemWindowsId;
        private int mFocusableId;
        private int mFocusableInTouchModeId;
        private int mFocusedByDefaultId;
        private int mFocusedId;
        private int mForceDarkAllowedId;
        private int mForegroundGravityId;
        private int mForegroundId;
        private int mForegroundTintId;
        private int mForegroundTintModeId;
        private int mHapticFeedbackEnabledId;
        private int mIdId;
        private int mImportantForAccessibilityId;
        private int mImportantForAutofillId;
        private int mImportantForContentCaptureId;
        private int mIsScrollContainerId;
        private int mKeepScreenOnId;
        private int mKeyboardNavigationClusterId;
        private int mLabelForId;
        private int mLayerTypeId;
        private int mLayoutDirectionId;
        private int mLongClickableId;
        private int mMinHeightId;
        private int mMinWidthId;
        private int mNestedScrollingEnabledId;
        private int mNextClusterForwardId;
        private int mNextFocusDownId;
        private int mNextFocusForwardId;
        private int mNextFocusLeftId;
        private int mNextFocusRightId;
        private int mNextFocusUpId;
        private int mOutlineAmbientShadowColorId;
        private int mOutlineProviderId;
        private int mOutlineSpotShadowColorId;
        private int mOverScrollModeId;
        private int mPaddingBottomId;
        private int mPaddingLeftId;
        private int mPaddingRightId;
        private int mPaddingTopId;
        private int mPointerIconId;
        private int mPressedId;
        private boolean mPropertiesMapped = false;
        private int mRawLayoutDirectionId;
        private int mRawTextAlignmentId;
        private int mRawTextDirectionId;
        private int mRequiresFadingEdgeId;
        private int mRotationId;
        private int mRotationXId;
        private int mRotationYId;
        private int mSaveEnabledId;
        private int mScaleXId;
        private int mScaleYId;
        private int mScreenReaderFocusableId;
        private int mScrollIndicatorsId;
        private int mScrollXId;
        private int mScrollYId;
        private int mScrollbarDefaultDelayBeforeFadeId;
        private int mScrollbarFadeDurationId;
        private int mScrollbarSizeId;
        private int mScrollbarStyleId;
        private int mSelectedId;
        private int mSolidColorId;
        private int mSoundEffectsEnabledId;
        private int mStateListAnimatorId;
        private int mTagId;
        private int mTextAlignmentId;
        private int mTextDirectionId;
        private int mTooltipTextId;
        private int mTransformPivotXId;
        private int mTransformPivotYId;
        private int mTransitionNameId;
        private int mTranslationXId;
        private int mTranslationYId;
        private int mTranslationZId;
        private int mVisibilityId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mAccessibilityFocusedId = propertyMapper.mapBoolean("accessibilityFocused", 0);
            this.mAccessibilityHeadingId =
                    propertyMapper.mapBoolean("accessibilityHeading", 16844160);
            SparseArray<String> accessibilityLiveRegionEnumMapping = new SparseArray<>();
            accessibilityLiveRegionEnumMapping.put(0, "none");
            accessibilityLiveRegionEnumMapping.put(1, "polite");
            accessibilityLiveRegionEnumMapping.put(2, "assertive");
            Objects.requireNonNull(accessibilityLiveRegionEnumMapping);
            this.mAccessibilityLiveRegionId =
                    propertyMapper.mapIntEnum(
                            "accessibilityLiveRegion",
                            16843758,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    accessibilityLiveRegionEnumMapping));
            this.mAccessibilityPaneTitleId =
                    propertyMapper.mapObject("accessibilityPaneTitle", 16844156);
            this.mAccessibilityTraversalAfterId =
                    propertyMapper.mapResourceId("accessibilityTraversalAfter", 16843986);
            this.mAccessibilityTraversalBeforeId =
                    propertyMapper.mapResourceId("accessibilityTraversalBefore", 16843985);
            this.mActivatedId = propertyMapper.mapBoolean("activated", 0);
            this.mAlphaId = propertyMapper.mapFloat("alpha", 16843551);
            this.mAutofillHintsId = propertyMapper.mapObject("autofillHints", 16844118);
            this.mBackgroundId = propertyMapper.mapObject("background", 16842964);
            this.mBackgroundTintId = propertyMapper.mapObject("backgroundTint", 16843883);
            this.mBackgroundTintModeId = propertyMapper.mapObject("backgroundTintMode", 16843884);
            this.mBaselineId = propertyMapper.mapInt("baseline", 16843548);
            this.mClickableId = propertyMapper.mapBoolean("clickable", 16842981);
            this.mContentDescriptionId = propertyMapper.mapObject("contentDescription", 16843379);
            this.mContextClickableId = propertyMapper.mapBoolean("contextClickable", 16844007);
            this.mDefaultFocusHighlightEnabledId =
                    propertyMapper.mapBoolean("defaultFocusHighlightEnabled", 16844130);
            SparseArray<String> drawingCacheQualityEnumMapping = new SparseArray<>();
            drawingCacheQualityEnumMapping.put(0, "auto");
            drawingCacheQualityEnumMapping.put(524288, "low");
            drawingCacheQualityEnumMapping.put(1048576, "high");
            Objects.requireNonNull(drawingCacheQualityEnumMapping);
            this.mDrawingCacheQualityId =
                    propertyMapper.mapIntEnum(
                            "drawingCacheQuality",
                            16842984,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    drawingCacheQualityEnumMapping));
            this.mDuplicateParentStateId =
                    propertyMapper.mapBoolean("duplicateParentState", 16842985);
            this.mElevationId =
                    propertyMapper.mapFloat(
                            SemMediaPostProcessor.ProcessingFormat.Key.ELEVATION, 16843840);
            this.mEnabledId = propertyMapper.mapBoolean("enabled", 16842766);
            this.mFadingEdgeLengthId = propertyMapper.mapInt("fadingEdgeLength", 16842976);
            this.mFilterTouchesWhenObscuredId =
                    propertyMapper.mapBoolean("filterTouchesWhenObscured", 16843460);
            this.mFitsSystemWindowsId = propertyMapper.mapBoolean("fitsSystemWindows", 16842973);
            SparseArray<String> focusableEnumMapping = new SparseArray<>();
            focusableEnumMapping.put(0, "false");
            focusableEnumMapping.put(1, "true");
            focusableEnumMapping.put(16, "auto");
            Objects.requireNonNull(focusableEnumMapping);
            this.mFocusableId =
                    propertyMapper.mapIntEnum(
                            "focusable",
                            16842970,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    focusableEnumMapping));
            this.mFocusableInTouchModeId =
                    propertyMapper.mapBoolean("focusableInTouchMode", 16842971);
            this.mFocusedId = propertyMapper.mapBoolean("focused", 0);
            this.mFocusedByDefaultId = propertyMapper.mapBoolean("focusedByDefault", 16844100);
            this.mForceDarkAllowedId = propertyMapper.mapBoolean("forceDarkAllowed", 16844172);
            this.mForegroundId = propertyMapper.mapObject("foreground", 16843017);
            this.mForegroundGravityId = propertyMapper.mapGravity("foregroundGravity", 16843264);
            this.mForegroundTintId = propertyMapper.mapObject("foregroundTint", 16843885);
            this.mForegroundTintModeId = propertyMapper.mapObject("foregroundTintMode", 16843886);
            this.mHapticFeedbackEnabledId =
                    propertyMapper.mapBoolean("hapticFeedbackEnabled", 16843358);
            this.mIdId = propertyMapper.mapResourceId("id", 16842960);
            SparseArray<String> importantForAccessibilityEnumMapping = new SparseArray<>();
            importantForAccessibilityEnumMapping.put(0, "auto");
            importantForAccessibilityEnumMapping.put(1, "yes");
            importantForAccessibilityEnumMapping.put(2, "no");
            importantForAccessibilityEnumMapping.put(4, "noHideDescendants");
            Objects.requireNonNull(importantForAccessibilityEnumMapping);
            this.mImportantForAccessibilityId =
                    propertyMapper.mapIntEnum(
                            "importantForAccessibility",
                            16843690,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    importantForAccessibilityEnumMapping));
            SparseArray<String> importantForAutofillEnumMapping = new SparseArray<>();
            importantForAutofillEnumMapping.put(0, "auto");
            importantForAutofillEnumMapping.put(1, "yes");
            importantForAutofillEnumMapping.put(2, "no");
            importantForAutofillEnumMapping.put(4, "yesExcludeDescendants");
            importantForAutofillEnumMapping.put(8, "noExcludeDescendants");
            Objects.requireNonNull(importantForAutofillEnumMapping);
            this.mImportantForAutofillId =
                    propertyMapper.mapIntEnum(
                            "importantForAutofill",
                            16844120,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    importantForAutofillEnumMapping));
            SparseArray<String> importantForContentCaptureEnumMapping = new SparseArray<>();
            importantForContentCaptureEnumMapping.put(0, "auto");
            importantForContentCaptureEnumMapping.put(1, "yes");
            importantForContentCaptureEnumMapping.put(2, "no");
            importantForContentCaptureEnumMapping.put(4, "yesExcludeDescendants");
            importantForContentCaptureEnumMapping.put(8, "noExcludeDescendants");
            Objects.requireNonNull(importantForContentCaptureEnumMapping);
            this.mImportantForContentCaptureId =
                    propertyMapper.mapIntEnum(
                            "importantForContentCapture",
                            16844295,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    importantForContentCaptureEnumMapping));
            this.mIsScrollContainerId = propertyMapper.mapBoolean("isScrollContainer", 16843342);
            this.mKeepScreenOnId = propertyMapper.mapBoolean("keepScreenOn", 16843286);
            this.mKeyboardNavigationClusterId =
                    propertyMapper.mapBoolean("keyboardNavigationCluster", 16844096);
            this.mLabelForId = propertyMapper.mapResourceId("labelFor", 16843718);
            SparseArray<String> layerTypeEnumMapping = new SparseArray<>();
            layerTypeEnumMapping.put(0, "none");
            layerTypeEnumMapping.put(1, "software");
            layerTypeEnumMapping.put(2, "hardware");
            Objects.requireNonNull(layerTypeEnumMapping);
            this.mLayerTypeId =
                    propertyMapper.mapIntEnum(
                            "layerType",
                            16843604,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    layerTypeEnumMapping));
            SparseArray<String> layoutDirectionEnumMapping = new SparseArray<>();
            layoutDirectionEnumMapping.put(0, "ltr");
            layoutDirectionEnumMapping.put(1, "rtl");
            Objects.requireNonNull(layoutDirectionEnumMapping);
            this.mLayoutDirectionId =
                    propertyMapper.mapIntEnum(
                            "layoutDirection",
                            16843698,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    layoutDirectionEnumMapping));
            this.mLongClickableId = propertyMapper.mapBoolean("longClickable", 16842982);
            this.mMinHeightId = propertyMapper.mapInt("minHeight", 16843072);
            this.mMinWidthId = propertyMapper.mapInt("minWidth", 16843071);
            this.mNestedScrollingEnabledId =
                    propertyMapper.mapBoolean("nestedScrollingEnabled", 16843830);
            this.mNextClusterForwardId =
                    propertyMapper.mapResourceId("nextClusterForward", 16844098);
            this.mNextFocusDownId = propertyMapper.mapResourceId("nextFocusDown", 16842980);
            this.mNextFocusForwardId = propertyMapper.mapResourceId("nextFocusForward", 16843580);
            this.mNextFocusLeftId = propertyMapper.mapResourceId("nextFocusLeft", 16842977);
            this.mNextFocusRightId = propertyMapper.mapResourceId("nextFocusRight", 16842978);
            this.mNextFocusUpId = propertyMapper.mapResourceId("nextFocusUp", 16842979);
            this.mOutlineAmbientShadowColorId =
                    propertyMapper.mapColor("outlineAmbientShadowColor", 16844162);
            this.mOutlineProviderId = propertyMapper.mapObject("outlineProvider", 16843960);
            this.mOutlineSpotShadowColorId =
                    propertyMapper.mapColor("outlineSpotShadowColor", 16844161);
            SparseArray<String> overScrollModeEnumMapping = new SparseArray<>();
            overScrollModeEnumMapping.put(0, "always");
            overScrollModeEnumMapping.put(1, "ifContentScrolls");
            overScrollModeEnumMapping.put(2, "never");
            Objects.requireNonNull(overScrollModeEnumMapping);
            this.mOverScrollModeId =
                    propertyMapper.mapIntEnum(
                            "overScrollMode",
                            16843457,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    overScrollModeEnumMapping));
            this.mPaddingBottomId = propertyMapper.mapInt("paddingBottom", 16842969);
            this.mPaddingLeftId = propertyMapper.mapInt("paddingLeft", 16842966);
            this.mPaddingRightId = propertyMapper.mapInt("paddingRight", 16842968);
            this.mPaddingTopId = propertyMapper.mapInt("paddingTop", 16842967);
            this.mPointerIconId = propertyMapper.mapObject("pointerIcon", 16844041);
            this.mPressedId = propertyMapper.mapBoolean("pressed", 0);
            SparseArray<String> rawLayoutDirectionEnumMapping = new SparseArray<>();
            rawLayoutDirectionEnumMapping.put(0, "ltr");
            rawLayoutDirectionEnumMapping.put(1, "rtl");
            rawLayoutDirectionEnumMapping.put(2, "inherit");
            rawLayoutDirectionEnumMapping.put(3, "locale");
            Objects.requireNonNull(rawLayoutDirectionEnumMapping);
            this.mRawLayoutDirectionId =
                    propertyMapper.mapIntEnum(
                            "rawLayoutDirection",
                            0,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    rawLayoutDirectionEnumMapping));
            SparseArray<String> rawTextAlignmentEnumMapping = new SparseArray<>();
            rawTextAlignmentEnumMapping.put(0, "inherit");
            rawTextAlignmentEnumMapping.put(1, "gravity");
            rawTextAlignmentEnumMapping.put(2, "textStart");
            rawTextAlignmentEnumMapping.put(3, "textEnd");
            rawTextAlignmentEnumMapping.put(4, "center");
            rawTextAlignmentEnumMapping.put(5, "viewStart");
            rawTextAlignmentEnumMapping.put(6, "viewEnd");
            Objects.requireNonNull(rawTextAlignmentEnumMapping);
            this.mRawTextAlignmentId =
                    propertyMapper.mapIntEnum(
                            "rawTextAlignment",
                            0,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    rawTextAlignmentEnumMapping));
            SparseArray<String> rawTextDirectionEnumMapping = new SparseArray<>();
            rawTextDirectionEnumMapping.put(0, "inherit");
            rawTextDirectionEnumMapping.put(1, "firstStrong");
            rawTextDirectionEnumMapping.put(2, "anyRtl");
            rawTextDirectionEnumMapping.put(3, "ltr");
            rawTextDirectionEnumMapping.put(4, "rtl");
            rawTextDirectionEnumMapping.put(5, "locale");
            rawTextDirectionEnumMapping.put(6, "firstStrongLtr");
            rawTextDirectionEnumMapping.put(7, "firstStrongRtl");
            Objects.requireNonNull(rawTextDirectionEnumMapping);
            this.mRawTextDirectionId =
                    propertyMapper.mapIntEnum(
                            "rawTextDirection",
                            0,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    rawTextDirectionEnumMapping));
            IntFlagMapping requiresFadingEdgeFlagMapping = new IntFlagMapping();
            requiresFadingEdgeFlagMapping.add(4096, 4096, Slice.HINT_HORIZONTAL);
            requiresFadingEdgeFlagMapping.add(12288, 0, "none");
            requiresFadingEdgeFlagMapping.add(8192, 8192, "vertical");
            Objects.requireNonNull(requiresFadingEdgeFlagMapping);
            this.mRequiresFadingEdgeId =
                    propertyMapper.mapIntFlag(
                            "requiresFadingEdge",
                            16843685,
                            new View$InspectionCompanion$$ExternalSyntheticLambda1(
                                    requiresFadingEdgeFlagMapping));
            this.mRotationId = propertyMapper.mapFloat(GenerateXML.ROTATION, 16843558);
            this.mRotationXId = propertyMapper.mapFloat("rotationX", 16843559);
            this.mRotationYId = propertyMapper.mapFloat("rotationY", 16843560);
            this.mSaveEnabledId = propertyMapper.mapBoolean("saveEnabled", 16842983);
            this.mScaleXId = propertyMapper.mapFloat("scaleX", 16843556);
            this.mScaleYId = propertyMapper.mapFloat("scaleY", 16843557);
            this.mScreenReaderFocusableId =
                    propertyMapper.mapBoolean("screenReaderFocusable", 16844148);
            IntFlagMapping scrollIndicatorsFlagMapping = new IntFlagMapping();
            scrollIndicatorsFlagMapping.add(2, 2, GenerateXML.BOTTOM);
            scrollIndicatorsFlagMapping.add(32, 32, "end");
            scrollIndicatorsFlagMapping.add(4, 4, "left");
            scrollIndicatorsFlagMapping.add(-1, 0, "none");
            scrollIndicatorsFlagMapping.add(8, 8, "right");
            scrollIndicatorsFlagMapping.add(16, 16, "start");
            scrollIndicatorsFlagMapping.add(1, 1, GenerateXML.TOP);
            Objects.requireNonNull(scrollIndicatorsFlagMapping);
            this.mScrollIndicatorsId =
                    propertyMapper.mapIntFlag(
                            "scrollIndicators",
                            16844006,
                            new View$InspectionCompanion$$ExternalSyntheticLambda1(
                                    scrollIndicatorsFlagMapping));
            this.mScrollXId = propertyMapper.mapInt("scrollX", 16842962);
            this.mScrollYId = propertyMapper.mapInt("scrollY", 16842963);
            this.mScrollbarDefaultDelayBeforeFadeId =
                    propertyMapper.mapInt("scrollbarDefaultDelayBeforeFade", 16843433);
            this.mScrollbarFadeDurationId =
                    propertyMapper.mapInt("scrollbarFadeDuration", 16843432);
            this.mScrollbarSizeId = propertyMapper.mapInt("scrollbarSize", 16842851);
            SparseArray<String> scrollbarStyleEnumMapping = new SparseArray<>();
            scrollbarStyleEnumMapping.put(0, "insideOverlay");
            scrollbarStyleEnumMapping.put(16777216, "insideInset");
            scrollbarStyleEnumMapping.put(33554432, "outsideOverlay");
            scrollbarStyleEnumMapping.put(50331648, "outsideInset");
            Objects.requireNonNull(scrollbarStyleEnumMapping);
            this.mScrollbarStyleId =
                    propertyMapper.mapIntEnum(
                            "scrollbarStyle",
                            16842879,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    scrollbarStyleEnumMapping));
            this.mSelectedId = propertyMapper.mapBoolean(Slice.HINT_SELECTED, 0);
            this.mSolidColorId = propertyMapper.mapColor("solidColor", 16843594);
            this.mSoundEffectsEnabledId =
                    propertyMapper.mapBoolean("soundEffectsEnabled", 16843285);
            this.mStateListAnimatorId = propertyMapper.mapObject("stateListAnimator", 16843848);
            this.mTagId = propertyMapper.mapObject("tag", 16842961);
            SparseArray<String> textAlignmentEnumMapping = new SparseArray<>();
            textAlignmentEnumMapping.put(1, "gravity");
            textAlignmentEnumMapping.put(2, "textStart");
            textAlignmentEnumMapping.put(3, "textEnd");
            textAlignmentEnumMapping.put(4, "center");
            textAlignmentEnumMapping.put(5, "viewStart");
            textAlignmentEnumMapping.put(6, "viewEnd");
            Objects.requireNonNull(textAlignmentEnumMapping);
            this.mTextAlignmentId =
                    propertyMapper.mapIntEnum(
                            "textAlignment",
                            16843697,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    textAlignmentEnumMapping));
            SparseArray<String> textDirectionEnumMapping = new SparseArray<>();
            textDirectionEnumMapping.put(1, "firstStrong");
            textDirectionEnumMapping.put(2, "anyRtl");
            textDirectionEnumMapping.put(3, "ltr");
            textDirectionEnumMapping.put(4, "rtl");
            textDirectionEnumMapping.put(5, "locale");
            textDirectionEnumMapping.put(6, "firstStrongLtr");
            textDirectionEnumMapping.put(7, "firstStrongRtl");
            Objects.requireNonNull(textDirectionEnumMapping);
            this.mTextDirectionId =
                    propertyMapper.mapIntEnum(
                            "textDirection",
                            0,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    textDirectionEnumMapping));
            this.mTooltipTextId = propertyMapper.mapObject("tooltipText", 16844084);
            this.mTransformPivotXId = propertyMapper.mapFloat("transformPivotX", 16843552);
            this.mTransformPivotYId = propertyMapper.mapFloat("transformPivotY", 16843553);
            this.mTransitionNameId = propertyMapper.mapObject("transitionName", 16843776);
            this.mTranslationXId = propertyMapper.mapFloat("translationX", 16843554);
            this.mTranslationYId = propertyMapper.mapFloat("translationY", 16843555);
            this.mTranslationZId = propertyMapper.mapFloat("translationZ", 16843770);
            SparseArray<String> visibilityEnumMapping = new SparseArray<>();
            visibilityEnumMapping.put(0, CalendarContract.CalendarColumns.VISIBLE);
            visibilityEnumMapping.put(4, "invisible");
            visibilityEnumMapping.put(8, "gone");
            Objects.requireNonNull(visibilityEnumMapping);
            this.mVisibilityId =
                    propertyMapper.mapIntEnum(
                            "visibility",
                            16842972,
                            new View$InspectionCompanion$$ExternalSyntheticLambda0(
                                    visibilityEnumMapping));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(View node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAccessibilityFocusedId, node.isAccessibilityFocused());
            propertyReader.readBoolean(this.mAccessibilityHeadingId, node.isAccessibilityHeading());
            propertyReader.readIntEnum(
                    this.mAccessibilityLiveRegionId, node.getAccessibilityLiveRegion());
            propertyReader.readObject(
                    this.mAccessibilityPaneTitleId, node.getAccessibilityPaneTitle());
            propertyReader.readResourceId(
                    this.mAccessibilityTraversalAfterId, node.getAccessibilityTraversalAfter());
            propertyReader.readResourceId(
                    this.mAccessibilityTraversalBeforeId, node.getAccessibilityTraversalBefore());
            propertyReader.readBoolean(this.mActivatedId, node.isActivated());
            propertyReader.readFloat(this.mAlphaId, node.getAlpha());
            propertyReader.readObject(this.mAutofillHintsId, node.getAutofillHints());
            propertyReader.readObject(this.mBackgroundId, node.getBackground());
            propertyReader.readObject(this.mBackgroundTintId, node.getBackgroundTintList());
            propertyReader.readObject(this.mBackgroundTintModeId, node.getBackgroundTintMode());
            propertyReader.readInt(this.mBaselineId, node.getBaseline());
            propertyReader.readBoolean(this.mClickableId, node.isClickable());
            propertyReader.readObject(this.mContentDescriptionId, node.getContentDescription());
            propertyReader.readBoolean(this.mContextClickableId, node.isContextClickable());
            propertyReader.readBoolean(
                    this.mDefaultFocusHighlightEnabledId, node.getDefaultFocusHighlightEnabled());
            propertyReader.readIntEnum(this.mDrawingCacheQualityId, node.getDrawingCacheQuality());
            propertyReader.readBoolean(
                    this.mDuplicateParentStateId, node.isDuplicateParentStateEnabled());
            propertyReader.readFloat(this.mElevationId, node.getElevation());
            propertyReader.readBoolean(this.mEnabledId, node.isEnabled());
            propertyReader.readInt(this.mFadingEdgeLengthId, node.getFadingEdgeLength());
            propertyReader.readBoolean(
                    this.mFilterTouchesWhenObscuredId, node.getFilterTouchesWhenObscured());
            propertyReader.readBoolean(this.mFitsSystemWindowsId, node.getFitsSystemWindows());
            propertyReader.readIntEnum(this.mFocusableId, node.getFocusable());
            propertyReader.readBoolean(this.mFocusableInTouchModeId, node.isFocusableInTouchMode());
            propertyReader.readBoolean(this.mFocusedId, node.isFocused());
            propertyReader.readBoolean(this.mFocusedByDefaultId, node.isFocusedByDefault());
            propertyReader.readBoolean(this.mForceDarkAllowedId, node.isForceDarkAllowed());
            propertyReader.readObject(this.mForegroundId, node.getForeground());
            propertyReader.readGravity(this.mForegroundGravityId, node.getForegroundGravity());
            propertyReader.readObject(this.mForegroundTintId, node.getForegroundTintList());
            propertyReader.readObject(this.mForegroundTintModeId, node.getForegroundTintMode());
            propertyReader.readBoolean(
                    this.mHapticFeedbackEnabledId, node.isHapticFeedbackEnabled());
            propertyReader.readResourceId(this.mIdId, node.getId());
            propertyReader.readIntEnum(
                    this.mImportantForAccessibilityId, node.getImportantForAccessibility());
            propertyReader.readIntEnum(
                    this.mImportantForAutofillId, node.getImportantForAutofill());
            propertyReader.readIntEnum(
                    this.mImportantForContentCaptureId, node.getImportantForContentCapture());
            propertyReader.readBoolean(this.mIsScrollContainerId, node.isScrollContainer());
            propertyReader.readBoolean(this.mKeepScreenOnId, node.getKeepScreenOn());
            propertyReader.readBoolean(
                    this.mKeyboardNavigationClusterId, node.isKeyboardNavigationCluster());
            propertyReader.readResourceId(this.mLabelForId, node.getLabelFor());
            propertyReader.readIntEnum(this.mLayerTypeId, node.getLayerType());
            propertyReader.readIntEnum(this.mLayoutDirectionId, node.getLayoutDirection());
            propertyReader.readBoolean(this.mLongClickableId, node.isLongClickable());
            propertyReader.readInt(this.mMinHeightId, node.getMinimumHeight());
            propertyReader.readInt(this.mMinWidthId, node.getMinimumWidth());
            propertyReader.readBoolean(
                    this.mNestedScrollingEnabledId, node.isNestedScrollingEnabled());
            propertyReader.readResourceId(
                    this.mNextClusterForwardId, node.getNextClusterForwardId());
            propertyReader.readResourceId(this.mNextFocusDownId, node.getNextFocusDownId());
            propertyReader.readResourceId(this.mNextFocusForwardId, node.getNextFocusForwardId());
            propertyReader.readResourceId(this.mNextFocusLeftId, node.getNextFocusLeftId());
            propertyReader.readResourceId(this.mNextFocusRightId, node.getNextFocusRightId());
            propertyReader.readResourceId(this.mNextFocusUpId, node.getNextFocusUpId());
            propertyReader.readColor(
                    this.mOutlineAmbientShadowColorId, node.getOutlineAmbientShadowColor());
            propertyReader.readObject(this.mOutlineProviderId, node.getOutlineProvider());
            propertyReader.readColor(
                    this.mOutlineSpotShadowColorId, node.getOutlineSpotShadowColor());
            propertyReader.readIntEnum(this.mOverScrollModeId, node.getOverScrollMode());
            propertyReader.readInt(this.mPaddingBottomId, node.getPaddingBottom());
            propertyReader.readInt(this.mPaddingLeftId, node.getPaddingLeft());
            propertyReader.readInt(this.mPaddingRightId, node.getPaddingRight());
            propertyReader.readInt(this.mPaddingTopId, node.getPaddingTop());
            propertyReader.readObject(this.mPointerIconId, node.getPointerIcon());
            propertyReader.readBoolean(this.mPressedId, node.isPressed());
            propertyReader.readIntEnum(this.mRawLayoutDirectionId, node.getRawLayoutDirection());
            propertyReader.readIntEnum(this.mRawTextAlignmentId, node.getRawTextAlignment());
            propertyReader.readIntEnum(this.mRawTextDirectionId, node.getRawTextDirection());
            propertyReader.readIntFlag(this.mRequiresFadingEdgeId, node.getFadingEdge());
            propertyReader.readFloat(this.mRotationId, node.getRotation());
            propertyReader.readFloat(this.mRotationXId, node.getRotationX());
            propertyReader.readFloat(this.mRotationYId, node.getRotationY());
            propertyReader.readBoolean(this.mSaveEnabledId, node.isSaveEnabled());
            propertyReader.readFloat(this.mScaleXId, node.getScaleX());
            propertyReader.readFloat(this.mScaleYId, node.getScaleY());
            propertyReader.readBoolean(
                    this.mScreenReaderFocusableId, node.isScreenReaderFocusable());
            propertyReader.readIntFlag(this.mScrollIndicatorsId, node.getScrollIndicators());
            propertyReader.readInt(this.mScrollXId, node.getScrollX());
            propertyReader.readInt(this.mScrollYId, node.getScrollY());
            propertyReader.readInt(
                    this.mScrollbarDefaultDelayBeforeFadeId,
                    node.getScrollBarDefaultDelayBeforeFade());
            propertyReader.readInt(this.mScrollbarFadeDurationId, node.getScrollBarFadeDuration());
            propertyReader.readInt(this.mScrollbarSizeId, node.getScrollBarSize());
            propertyReader.readIntEnum(this.mScrollbarStyleId, node.getScrollBarStyle());
            propertyReader.readBoolean(this.mSelectedId, node.isSelected());
            propertyReader.readColor(this.mSolidColorId, node.getSolidColor());
            propertyReader.readBoolean(this.mSoundEffectsEnabledId, node.isSoundEffectsEnabled());
            propertyReader.readObject(this.mStateListAnimatorId, node.getStateListAnimator());
            propertyReader.readObject(this.mTagId, node.getTag());
            propertyReader.readIntEnum(this.mTextAlignmentId, node.getTextAlignment());
            propertyReader.readIntEnum(this.mTextDirectionId, node.getTextDirection());
            propertyReader.readObject(this.mTooltipTextId, node.getTooltipText());
            propertyReader.readFloat(this.mTransformPivotXId, node.getPivotX());
            propertyReader.readFloat(this.mTransformPivotYId, node.getPivotY());
            propertyReader.readObject(this.mTransitionNameId, node.getTransitionName());
            propertyReader.readFloat(this.mTranslationXId, node.getTranslationX());
            propertyReader.readFloat(this.mTranslationYId, node.getTranslationY());
            propertyReader.readFloat(this.mTranslationZId, node.getTranslationZ());
            propertyReader.readIntEnum(this.mVisibilityId, node.getVisibility());
        }
    }

    public boolean isHighContrastTextEnabled() {
        return ThreadedRenderer.isHighContrastTextEnabled();
    }

    public boolean semIsHighContrastTextEnabled() {
        return ThreadedRenderer.isHighContrastTextEnabled();
    }

    static class TransformationInfo {
        private Matrix mInverseMatrix;
        private final Matrix mMatrix = new Matrix();

        @ViewDebug.ExportedProperty private float mAlpha = 1.0f;
        float mTransitionAlpha = 1.0f;

        TransformationInfo() {}
    }

    static class TintInfo {
        BlendMode mBlendMode;
        boolean mHasTintList;
        boolean mHasTintMode;
        ColorStateList mTintList;

        TintInfo() {}
    }

    private static class ForegroundInfo {
        private boolean mBoundsChanged;
        private Drawable mDrawable;
        private int mGravity;
        private boolean mInsidePadding;
        private final Rect mOverlayBounds;
        private final Rect mSelfBounds;
        private TintInfo mTintInfo;

        private ForegroundInfo() {
            this.mGravity = 119;
            this.mInsidePadding = true;
            this.mBoundsChanged = true;
            this.mSelfBounds = new Rect();
            this.mOverlayBounds = new Rect();
        }
    }

    static class ListenerInfo {
        OnApplyWindowInsetsListener mOnApplyWindowInsetsListener;
        private CopyOnWriteArrayList<OnAttachStateChangeListener> mOnAttachStateChangeListeners;
        OnCapturedPointerListener mOnCapturedPointerListener;
        public OnClickListener mOnClickListener;
        protected OnContextClickListener mOnContextClickListener;
        protected OnCreateContextMenuListener mOnCreateContextMenuListener;
        private OnDragListener mOnDragListener;
        protected OnFocusChangeListener mOnFocusChangeListener;
        private OnGenericMotionListener mOnGenericMotionListener;
        private OnHoverListener mOnHoverListener;
        private OnKeyListener mOnKeyListener;
        private ArrayList<OnLayoutChangeListener> mOnLayoutChangeListeners;
        protected OnLongClickListener mOnLongClickListener;
        private OnReceiveContentListener mOnReceiveContentListener;
        protected OnScrollChangeListener mOnScrollChangeListener;
        private OnSystemUiVisibilityChangeListener mOnSystemUiVisibilityChangeListener;
        private OnTouchListener mOnTouchListener;
        private Runnable mPositionChangedUpdate;
        public RenderNode.PositionUpdateListener mPositionUpdateListener;
        ScrollCaptureCallback mScrollCaptureCallback;
        private ArrayList<OnUnhandledKeyEventListener> mUnhandledKeyListeners;
        WindowInsetsAnimation.Callback mWindowInsetsAnimationCallback;
        private List<Rect> mSystemGestureExclusionRects = null;
        private List<Rect> mKeepClearRects = null;
        private List<Rect> mUnrestrictedKeepClearRects = null;
        private boolean mPreferKeepClear = false;
        private Rect mHandwritingArea = null;

        ListenerInfo() {}
    }

    private static class TooltipInfo {
        int mAnchorX;
        int mAnchorY;
        Runnable mHideTooltipRunnable;
        int mHoverSlop;
        boolean mSemIsTooltipNull;
        boolean mSemSetTooltipPosition;
        int mSemX;
        int mSemY;
        Runnable mShowTooltipRunnable;
        boolean mTooltipFromLongClick;
        TooltipPopup mTooltipPopup;
        CharSequence mTooltipText;

        private TooltipInfo() {
            this.mSemIsTooltipNull = false;
        }

        public boolean updateAnchorPos(MotionEvent event) {
            int newAnchorX = (int) event.getX();
            int newAnchorY = (int) event.getY();
            if (Math.abs(newAnchorX - this.mAnchorX) <= this.mHoverSlop
                    && Math.abs(newAnchorY - this.mAnchorY) <= this.mHoverSlop) {
                return false;
            }
            this.mAnchorX = newAnchorX;
            this.mAnchorY = newAnchorY;
            return true;
        }

        public void clearAnchorPos() {
            this.mAnchorX = Integer.MAX_VALUE;
            this.mAnchorY = Integer.MAX_VALUE;
        }
    }

    public View(Context context) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        boolean z14;
        this.mScrollFeedbackProvider = null;
        this.mFrameRateCompatibility = 1;
        this.mCurrentAnimation = null;
        this.mRecreateDisplayList = false;
        this.mID = -1;
        this.mAutofillViewId = -1;
        this.mAccessibilityViewId = -1;
        this.mAccessibilityCursorPosition = -1;
        this.mTag = null;
        this.mTransientStateCount = 0;
        this.mClipBounds = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mExplicitAccessibilityDataSensitive = 0;
        this.mInferredAccessibilityDataSensitive = 0;
        this.mLabelForId = -1;
        this.mAccessibilityTraversalBeforeId = -1;
        this.mAccessibilityTraversalAfterId = -1;
        this.mLeftPaddingDefined = false;
        this.mRightPaddingDefined = false;
        this.mOldWidthMeasureSpec = Integer.MIN_VALUE;
        this.mOldHeightMeasureSpec = Integer.MIN_VALUE;
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        this.mDrawableState = null;
        this.mOutlineProvider = ViewOutlineProvider.BACKGROUND;
        this.mNextFocusLeftId = -1;
        this.mNextFocusRightId = -1;
        this.mNextFocusUpId = -1;
        this.mNextFocusDownId = -1;
        this.mNextFocusForwardId = -1;
        this.mNextClusterForwardId = -1;
        this.mDefaultFocusHighlightEnabled = true;
        this.mPendingCheckForTap = null;
        this.mTouchDelegate = null;
        this.mHoveringTouchDelegate = false;
        this.mDrawingCacheBackgroundColor = 0;
        this.mAnimator = null;
        this.mRoundRadius = -1;
        this.mRoundedCornerBounds = new Rect();
        this.mRoundedCornerOffset = new Pair<>(0, 0);
        this.mCornerOffset = 0;
        this.mExtraPaddingBottomForPreference = 0;
        this.mLayerType = 0;
        this.mDisablePenGestureforfactorytest = false;
        this.isPenSideButton = false;
        if (!InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            inputEventConsistencyVerifier = null;
        } else {
            inputEventConsistencyVerifier = new InputEventConsistencyVerifier(this, 0);
        }
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mIsDeviceDefault = false;
        this.mSourceLayoutId = 0;
        this.mUnbufferedInputSource = 0;
        this.mFrameContentVelocity = -1.0f;
        this.mGfxImageFilter = null;
        this.mPreferredFrameRate = Float.NaN;
        this.mLastFrameRateCategory = 1;
        this.mNeedToSendSavedStickyDragEvent = false;
        this.mNeededToChangedScrollBarPosition = false;
        this.mScrollBarPositionPadding = 0;
        this.mSemScrollingByScrollbar = false;
        this.mSemScrollingVertical = true;
        this.mSemVerticalScrollbarRect = new Rect();
        this.mSemHorizontalScrollbarRect = new Rect();
        this.mIsSetFingerHoveredInAppWidget = true;
        this.mShouldFakeFocus = false;
        this.mHoverPopup = null;
        this.mHoverPopupType = 0;
        this.mHoverPopupToolTypeByApp = 0;
        this.VELOCITY_THRESHOLD_T1 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_t1", 4000);
        this.VELOCITY_THRESHOLD_T2 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_t2", 500);
        this.VELOCITY_THRESHOLD_T3 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_t3", 125);
        this.VELOCITY_FRAMERATE1 =
                SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate1", 120);
        this.VELOCITY_FRAMERATE2 =
                SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate2", 120);
        this.VELOCITY_FRAMERATE3 =
                SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate3", 120);
        this.VELOCITY_FRAMERATE4 =
                SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate4", 60);
        this.mIsFlingState = false;
        this.mBlurInfo = null;
        this.mBlurMode = -1;
        this.mBlurRadius = 128;
        this.mLastBlurRadius = 0;
        this.mBlurColorCurve = null;
        this.mBackgroundBlurCornerRadiusTL = 0.0f;
        this.mBackgroundBlurCornerRadiusTR = 0.0f;
        this.mBackgroundBlurCornerRadiusBL = 0.0f;
        this.mBackgroundBlurCornerRadiusBR = 0.0f;
        this.mBackgroundBlurColor = 0;
        this.mCanvasDownScale = 8;
        this.mCapturingCanvas = false;
        this.mAppWidgetScrollBarBottomPadding = 0;
        this.mAppWidgetScrollBarTopPadding = 0;
        this.mSmartClipDataTag = null;
        this.mSmartClipDataExtractionListener = null;
        this.mContext = context;
        this.mResources = context != null ? context.getResources() : null;
        this.mViewFlags = 402653200;
        this.mPrivateFlags2 = 140296;
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mExtendedTouchSlop = this.mTouchSlop * 4;
        this.mAmbiguousGestureMultiplier = configuration.getScaledAmbiguousGestureMultiplier();
        setOverScrollMode(1);
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mRenderNode =
                RenderNode.create(getClass().getName(), new ViewAnimationHostBridge(this));
        if (!sCompatibilityDone && context != null) {
            int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
            if (targetSdkVersion <= 23) {
                z = true;
            } else {
                z = false;
            }
            sAlwaysRemeasureExactly = z;
            if (targetSdkVersion <= 23) {
                z2 = true;
            } else {
                z2 = false;
            }
            sTextureViewIgnoresDrawableSetters = z2;
            if (targetSdkVersion >= 24) {
                z3 = true;
            } else {
                z3 = false;
            }
            sPreserveMarginParamsInLayoutParamConversion = z3;
            if (targetSdkVersion < 24) {
                z4 = true;
            } else {
                z4 = false;
            }
            sCascadedDragDrop = z4;
            if (targetSdkVersion < 26) {
                z5 = true;
            } else {
                z5 = false;
            }
            sHasFocusableExcludeAutoFocusable = z5;
            if (targetSdkVersion < 26) {
                z6 = true;
            } else {
                z6 = false;
            }
            sAutoFocusableOffUIThreadWontNotifyParents = z6;
            sUseDefaultFocusHighlight =
                    context.getResources().getBoolean(R.bool.config_useDefaultFocusHighlight);
            if (targetSdkVersion >= 28) {
                z7 = true;
            } else {
                z7 = false;
            }
            sThrowOnInvalidFloatProperties = z7;
            if (targetSdkVersion < 28) {
                z8 = true;
            } else {
                z8 = false;
            }
            sCanFocusZeroSized = z8;
            if (targetSdkVersion < 28) {
                z9 = true;
            } else {
                z9 = false;
            }
            sAlwaysAssignFocus = z9;
            if (targetSdkVersion < 28) {
                z10 = true;
            } else {
                z10 = false;
            }
            sAcceptZeroSizeDragShadow = z10;
            if (targetSdkVersion < 30) {
                z11 = true;
            } else {
                z11 = false;
            }
            sBrokenInsetsDispatch = z11;
            if (targetSdkVersion < 29) {
                z12 = true;
            } else {
                z12 = false;
            }
            sBrokenWindowBackground = z12;
            if (targetSdkVersion >= 29) {
                z13 = true;
            } else {
                z13 = false;
            }
            GradientDrawable.sWrapNegativeAngleMeasurements = z13;
            if (targetSdkVersion < 30) {
                z14 = true;
            } else {
                z14 = false;
            }
            sForceLayoutWhenInsetsChanged = z14;
            sCompatibilityDone = true;
        }
        try {
            if (!ViewRune.WIDGET_PEN_SUPPORTED
                    || Settings.System.getInt(
                                    context.getContentResolver(), "disable_pen_gesture", 0)
                            != 0) {
                this.mDisablePenGestureforfactorytest = true;
            }
        } catch (Exception e) {
            Log.i(VIEW_LOG_TAG, "Setting disable_pen_gesture is not accessible.", e);
        }
        try {
            if (sMetaDataNeedCheck) {
                try {
                    ApplicationInfo info =
                            this.mContext
                                    .getPackageManager()
                                    .getApplicationInfo(this.mContext.getPackageName(), 128);
                    if (info.metaData != null) {
                        String data =
                                info.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME);
                        if (data != null && data.startsWith("SEP") && data.length() == 5) {
                            sIsSamsungBasicInteraction = true;
                            sSEP_Version = Integer.parseInt(data.substring(3, 5));
                        }
                        sIsDisplayCutoutBackground =
                                info.metaData.getBoolean(SAMSUNG_DISPLAY_CUTOUT_BG_METADATA_NAME);
                    }
                } catch (Exception e2) {
                    Log.d(VIEW_LOG_TAG, "Unable to get SamsungBasicInteraction metadata");
                }
            }
        } finally {
            sMetaDataNeedCheck = false;
        }
    }

    public View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public View(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context);
        int padding;
        int rightPadding;
        int bottomPadding;
        int topPadding;
        int x;
        int paddingHorizontal;
        boolean rightPaddingDefined;
        int padding2;
        String rawString;
        CharSequence[] rawHints;
        this.mSourceLayoutId = Resources.getAttributeSetSourceResId(attrs);
        TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.View, defStyleAttr, defStyleRes);
        retrieveExplicitStyle(context.getTheme(), attrs);
        saveAttributeDataForStyleable(
                context, R.styleable.View, attrs, a, defStyleAttr, defStyleRes);
        if (sDebugViewAttributes) {
            saveAttributeData(attrs, a);
        }
        float tx = 0.0f;
        boolean transformSet = false;
        int scrollbarStyle = 0;
        int overScrollMode = this.mOverScrollMode;
        boolean initializeScrollbars = false;
        boolean initializeScrollIndicators = false;
        boolean startPaddingDefined = false;
        boolean endPaddingDefined = false;
        int viewFlagValues = 0 | 16;
        int viewFlagMasks = 0 | 16;
        int leftPadding = -1;
        int N = a.getIndexCount();
        float ty = 0.0f;
        float tz = 0.0f;
        float elevation = 0.0f;
        float rotation = 0.0f;
        float rotationX = 0.0f;
        float rotationY = 0.0f;
        float sx = 1.0f;
        float sy = 1.0f;
        boolean leftPaddingDefined = false;
        boolean rightPaddingDefined2 = false;
        int i = 0;
        int x2 = 0;
        int y = 0;
        int x3 = -1;
        int paddingHorizontal2 = -1;
        int y2 = 0;
        int rightPadding2 = -1;
        int paddingHorizontal3 = Integer.MIN_VALUE;
        int endPadding = viewFlagMasks;
        Drawable background = null;
        int viewFlagMasks2 = -1;
        int padding3 = -1;
        int padding4 = Integer.MIN_VALUE;
        int startPadding = viewFlagValues;
        int viewFlagValues2 = -1;
        int topPadding2 = overScrollMode;
        while (i < N) {
            int N2 = N;
            int attr = a.getIndex(i);
            switch (attr) {
                case 8:
                    int paddingHorizontal4 = paddingHorizontal2;
                    boolean rightPaddingDefined3 = rightPaddingDefined2;
                    int padding5 = padding3;
                    int scrollbarStyle2 = a.getInt(attr, 0);
                    if (scrollbarStyle2 == 0) {
                        scrollbarStyle = scrollbarStyle2;
                        padding3 = padding5;
                        paddingHorizontal2 = paddingHorizontal4;
                        rightPaddingDefined2 = rightPaddingDefined3;
                    } else {
                        startPadding |= scrollbarStyle2 & 50331648;
                        scrollbarStyle = scrollbarStyle2;
                        endPadding = 50331648 | endPadding;
                        padding3 = padding5;
                        paddingHorizontal2 = paddingHorizontal4;
                        rightPaddingDefined2 = rightPaddingDefined3;
                        continue;
                    }
                    i++;
                    N = N2;
                case 9:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mID = a.getResourceId(attr, -1);
                    break;
                case 10:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mTag = a.getText(attr);
                    break;
                case 11:
                    int x4 = a.getDimensionPixelOffset(attr, 0);
                    x2 = x4;
                    continue;
                    i++;
                    N = N2;
                case 12:
                    int y3 = a.getDimensionPixelOffset(attr, 0);
                    y = y3;
                    continue;
                    i++;
                    N = N2;
                case 13:
                    Drawable background2 = a.getDrawable(attr);
                    background = background2;
                    continue;
                    i++;
                    N = N2;
                case 14:
                    int padding6 = a.getDimensionPixelSize(attr, -1);
                    this.mUserPaddingLeftInitial = padding6;
                    this.mUserPaddingRightInitial = padding6;
                    rightPaddingDefined2 = true;
                    padding3 = padding6;
                    leftPaddingDefined = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 15:
                    int leftPadding2 = a.getDimensionPixelSize(attr, -1);
                    this.mUserPaddingLeftInitial = leftPadding2;
                    leftPadding = leftPadding2;
                    leftPaddingDefined = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 16:
                    int topPadding3 = a.getDimensionPixelSize(attr, -1);
                    viewFlagValues2 = topPadding3;
                    continue;
                    i++;
                    N = N2;
                case 17:
                    int rightPadding3 = a.getDimensionPixelSize(attr, -1);
                    this.mUserPaddingRightInitial = rightPadding3;
                    rightPadding2 = rightPadding3;
                    rightPaddingDefined2 = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 18:
                    int bottomPadding2 = a.getDimensionPixelSize(attr, -1);
                    x3 = bottomPadding2;
                    continue;
                    i++;
                    N = N2;
                case 19:
                    int paddingHorizontal5 = paddingHorizontal2;
                    boolean rightPaddingDefined4 = rightPaddingDefined2;
                    int padding7 = padding3;
                    int viewFlagValues3 = (startPadding & (-18)) | getFocusableAttribute(a);
                    if ((viewFlagValues3 & 16) != 0) {
                        startPadding = viewFlagValues3;
                        padding3 = padding7;
                        paddingHorizontal2 = paddingHorizontal5;
                        rightPaddingDefined2 = rightPaddingDefined4;
                    } else {
                        startPadding = viewFlagValues3;
                        endPadding |= 17;
                        padding3 = padding7;
                        paddingHorizontal2 = paddingHorizontal5;
                        rightPaddingDefined2 = rightPaddingDefined4;
                        continue;
                    }
                    i++;
                    N = N2;
                case 20:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.getBoolean(attr, false)) {
                        int viewFlagValues4 = startPadding & (-17);
                        startPadding = viewFlagValues4 | 262145;
                        endPadding = 262161 | endPadding;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 21:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    int visibility = a.getInt(attr, 0);
                    if (visibility != 0) {
                        int viewFlagValues5 = VISIBILITY_FLAGS[visibility] | startPadding;
                        int viewFlagValues6 = endPadding | 12;
                        endPadding = viewFlagValues6;
                        padding3 = padding2;
                        rightPaddingDefined2 = rightPaddingDefined;
                        startPadding = viewFlagValues5;
                        paddingHorizontal2 = paddingHorizontal;
                        i++;
                        N = N2;
                    }
                    break;
                case 22:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.getBoolean(attr, false)) {
                        int viewFlagValues7 = startPadding | 2;
                        startPadding = viewFlagValues7;
                        endPadding |= 2;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 23:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    int scrollbars = a.getInt(attr, 0);
                    if (scrollbars != 0) {
                        int viewFlagValues8 = startPadding | scrollbars;
                        int viewFlagValues9 = endPadding | 768;
                        initializeScrollbars = true;
                        padding3 = padding2;
                        rightPaddingDefined2 = rightPaddingDefined;
                        endPadding = viewFlagValues9;
                        startPadding = viewFlagValues8;
                        paddingHorizontal2 = paddingHorizontal;
                        i++;
                        N = N2;
                    }
                    break;
                case 24:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 25:
                case 45:
                case 46:
                case 47:
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                default:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 26:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mNextFocusLeftId = a.getResourceId(attr, -1);
                    break;
                case 27:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mNextFocusRightId = a.getResourceId(attr, -1);
                    break;
                case 28:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mNextFocusUpId = a.getResourceId(attr, -1);
                    break;
                case 29:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mNextFocusDownId = a.getResourceId(attr, -1);
                    break;
                case 30:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.getBoolean(attr, false)) {
                        int viewFlagValues10 = startPadding | 16384;
                        startPadding = viewFlagValues10;
                        endPadding |= 16384;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 31:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.getBoolean(attr, false)) {
                        int viewFlagValues11 = startPadding | 2097152;
                        endPadding = 2097152 | endPadding;
                        startPadding = viewFlagValues11;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 32:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (!a.getBoolean(attr, true)) {
                        int viewFlagValues12 = startPadding | 65536;
                        endPadding = 65536 | endPadding;
                        startPadding = viewFlagValues12;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 33:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    int cacheQuality = a.getInt(attr, 0);
                    if (cacheQuality != 0) {
                        int viewFlagValues13 =
                                DRAWING_CACHE_QUALITY_FLAGS[cacheQuality] | startPadding;
                        endPadding = 1572864 | endPadding;
                        padding3 = padding2;
                        rightPaddingDefined2 = rightPaddingDefined;
                        startPadding = viewFlagValues13;
                        paddingHorizontal2 = paddingHorizontal;
                        i++;
                        N = N2;
                    }
                    break;
                case 34:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.getBoolean(attr, false)) {
                        int viewFlagValues14 = startPadding | 4194304;
                        endPadding = 4194304 | endPadding;
                        startPadding = viewFlagValues14;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 35:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setForeground(a.getDrawable(attr));
                    break;
                case 36:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mMinWidth = a.getDimensionPixelSize(attr, 0);
                    break;
                case 37:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mMinHeight = a.getDimensionPixelSize(attr, 0);
                    break;
                case 38:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setForegroundGravity(a.getInt(attr, 0));
                    break;
                case 39:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (!a.getBoolean(attr, true)) {
                        int viewFlagValues15 = (-134217729) & startPadding;
                        startPadding = viewFlagValues15;
                        endPadding = 134217728 | endPadding;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 40:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.getBoolean(attr, false)) {
                        int viewFlagValues16 = startPadding | 67108864;
                        endPadding = 67108864 | endPadding;
                        startPadding = viewFlagValues16;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 41:
                    int paddingHorizontal6 = paddingHorizontal2;
                    boolean rightPaddingDefined5 = rightPaddingDefined2;
                    int padding8 = padding3;
                    if (a.getBoolean(attr, false)) {
                        setScrollContainer(true);
                    }
                    y2 = 1;
                    padding3 = padding8;
                    paddingHorizontal2 = paddingHorizontal6;
                    rightPaddingDefined2 = rightPaddingDefined5;
                    continue;
                    i++;
                    N = N2;
                case 42:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (!a.getBoolean(attr, true)) {
                        int viewFlagValues17 = (-268435457) & startPadding;
                        startPadding = viewFlagValues17;
                        endPadding = 268435456 | endPadding;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 43:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (context.isRestricted()) {
                        throw new IllegalStateException(
                                "The android:onClick attribute cannot be used within a restricted"
                                    + " context");
                    }
                    String handlerName = a.getString(attr);
                    if (handlerName != null) {
                        setOnClickListener(new DeclaredOnClickListener(this, handlerName));
                        break;
                    }
                    break;
                case 44:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setContentDescription(a.getString(attr));
                    break;
                case 48:
                    int overScrollMode2 = a.getInt(attr, 1);
                    topPadding2 = overScrollMode2;
                    continue;
                    i++;
                    N = N2;
                case 49:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.getBoolean(attr, false)) {
                        int viewFlagValues18 = startPadding | 1024;
                        startPadding = viewFlagValues18;
                        endPadding |= 1024;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 50:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setAlpha(a.getFloat(attr, 1.0f));
                    break;
                case 51:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setPivotX(a.getDimension(attr, 0.0f));
                    break;
                case 52:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setPivotY(a.getDimension(attr, 0.0f));
                    break;
                case 53:
                    float tx2 = a.getDimension(attr, 0.0f);
                    tx = tx2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 54:
                    float ty2 = a.getDimension(attr, 0.0f);
                    ty = ty2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 55:
                    float sx2 = a.getFloat(attr, 1.0f);
                    sx = sx2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 56:
                    float sy2 = a.getFloat(attr, 1.0f);
                    sy = sy2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 57:
                    float rotation2 = a.getFloat(attr, 0.0f);
                    rotation = rotation2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 58:
                    float rotationX2 = a.getFloat(attr, 0.0f);
                    rotationX = rotationX2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 59:
                    float rotationY2 = a.getFloat(attr, 0.0f);
                    rotationY = rotationY2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 60:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mVerticalScrollbarPosition = a.getInt(attr, 0);
                    break;
                case 61:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mNextFocusForwardId = a.getResourceId(attr, -1);
                    break;
                case 62:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setLayerType(a.getInt(attr, 0), null);
                    break;
                case 63:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    int fadingEdge = a.getInt(attr, 0);
                    if (fadingEdge != 0) {
                        int viewFlagValues19 = startPadding | fadingEdge;
                        int viewFlagValues20 = endPadding | 12288;
                        initializeFadingEdgeInternal(a);
                        endPadding = viewFlagValues20;
                        padding3 = padding2;
                        rightPaddingDefined2 = rightPaddingDefined;
                        startPadding = viewFlagValues19;
                        paddingHorizontal2 = paddingHorizontal;
                        i++;
                        N = N2;
                    }
                    break;
                case 64:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setImportantForAccessibility(a.getInt(attr, 0));
                    break;
                case 65:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    int paddingHorizontal7 = this.mPrivateFlags2;
                    this.mPrivateFlags2 = paddingHorizontal7 & (-449);
                    int textDirection = a.getInt(attr, -1);
                    if (textDirection != -1) {
                        this.mPrivateFlags2 |= PFLAG2_TEXT_DIRECTION_FLAGS[textDirection];
                        break;
                    }
                    break;
                case 66:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mPrivateFlags2 &= -57345;
                    int textAlignment = a.getInt(attr, 1);
                    this.mPrivateFlags2 |= PFLAG2_TEXT_ALIGNMENT_FLAGS[textAlignment];
                    break;
                case 67:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    int paddingHorizontal8 = this.mPrivateFlags2;
                    this.mPrivateFlags2 = paddingHorizontal8 & (-61);
                    int layoutDirection = a.getInt(attr, -1);
                    int value = layoutDirection != -1 ? LAYOUT_DIRECTION_FLAGS[layoutDirection] : 2;
                    this.mPrivateFlags2 |= value << 2;
                    break;
                case 68:
                    int paddingHorizontal9 = paddingHorizontal2;
                    boolean rightPaddingDefined6 = rightPaddingDefined2;
                    int padding9 = padding3;
                    int startPadding2 = a.getDimensionPixelSize(attr, Integer.MIN_VALUE);
                    boolean startPaddingDefined2 = startPadding2 != Integer.MIN_VALUE;
                    startPaddingDefined = startPaddingDefined2;
                    padding4 = startPadding2;
                    padding3 = padding9;
                    paddingHorizontal2 = paddingHorizontal9;
                    rightPaddingDefined2 = rightPaddingDefined6;
                    continue;
                    i++;
                    N = N2;
                case 69:
                    int paddingHorizontal10 = paddingHorizontal2;
                    boolean rightPaddingDefined7 = rightPaddingDefined2;
                    int padding10 = padding3;
                    int endPadding2 = a.getDimensionPixelSize(attr, Integer.MIN_VALUE);
                    boolean endPaddingDefined2 = endPadding2 != Integer.MIN_VALUE;
                    endPaddingDefined = endPaddingDefined2;
                    paddingHorizontal3 = endPadding2;
                    padding3 = padding10;
                    paddingHorizontal2 = paddingHorizontal10;
                    rightPaddingDefined2 = rightPaddingDefined7;
                    continue;
                    i++;
                    N = N2;
                case 70:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setLabelFor(a.getResourceId(attr, -1));
                    break;
                case 71:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setAccessibilityLiveRegion(a.getInt(attr, 0));
                    break;
                case 72:
                    float tz2 = a.getDimension(attr, 0.0f);
                    tz = tz2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 73:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setTransitionName(a.getString(attr));
                    break;
                case 74:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setNestedScrollingEnabled(a.getBoolean(attr, false));
                    break;
                case 75:
                    float elevation2 = a.getDimension(attr, 0.0f);
                    elevation = elevation2;
                    transformSet = true;
                    paddingHorizontal2 = paddingHorizontal2;
                    continue;
                    i++;
                    N = N2;
                case 76:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setStateListAnimator(
                            AnimatorInflater.loadStateListAnimator(
                                    context, a.getResourceId(attr, 0)));
                    break;
                case 77:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (this.mBackgroundTint == null) {
                        this.mBackgroundTint = new TintInfo();
                    }
                    this.mBackgroundTint.mTintList = a.getColorStateList(77);
                    this.mBackgroundTint.mHasTintList = true;
                    break;
                case 78:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (this.mBackgroundTint == null) {
                        this.mBackgroundTint = new TintInfo();
                    }
                    this.mBackgroundTint.mBlendMode =
                            Drawable.parseBlendMode(a.getInt(78, -1), null);
                    this.mBackgroundTint.mHasTintMode = true;
                    break;
                case 79:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setForegroundTintList(a.getColorStateList(attr));
                    break;
                case 80:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setForegroundTintBlendMode(Drawable.parseBlendMode(a.getInt(attr, -1), null));
                    break;
                case 81:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setOutlineProviderFromAttribute(a.getInt(81, 0));
                    break;
                case 82:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setAccessibilityTraversalBefore(a.getResourceId(attr, -1));
                    break;
                case 83:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setAccessibilityTraversalAfter(a.getResourceId(attr, -1));
                    break;
                case 84:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    int scrollIndicators = (a.getInt(attr, 0) << 8) & SCROLL_INDICATORS_PFLAG3_MASK;
                    if (scrollIndicators != 0) {
                        this.mPrivateFlags3 |= scrollIndicators;
                        initializeScrollIndicators = true;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 85:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.getBoolean(attr, false)) {
                        int viewFlagValues21 = startPadding | 8388608;
                        endPadding = 8388608 | endPadding;
                        startPadding = viewFlagValues21;
                        padding3 = padding2;
                        paddingHorizontal2 = paddingHorizontal;
                        rightPaddingDefined2 = rightPaddingDefined;
                        i++;
                        N = N2;
                    }
                    break;
                case 86:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    int resourceId = a.getResourceId(attr, 0);
                    if (resourceId != 0) {
                        setPointerIcon(PointerIcon.load(context.getResources(), resourceId));
                        break;
                    } else {
                        int pointerType = a.getInt(attr, 1);
                        if (pointerType != 1) {
                            setPointerIcon(PointerIcon.getSystemIcon(context, pointerType));
                            break;
                        }
                    }
                    break;
                case 87:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.peekValue(attr) != null) {
                        forceHasOverlappingRendering(a.getBoolean(attr, true));
                        break;
                    }
                    break;
                case 88:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    setTooltipText(a.getText(attr));
                    break;
                case 89:
                    int paddingHorizontal11 = a.getDimensionPixelSize(attr, -1);
                    this.mUserPaddingLeftInitial = paddingHorizontal11;
                    this.mUserPaddingRightInitial = paddingHorizontal11;
                    rightPaddingDefined2 = true;
                    leftPaddingDefined = true;
                    paddingHorizontal2 = paddingHorizontal11;
                    continue;
                    i++;
                    N = N2;
                case 90:
                    int paddingVertical = a.getDimensionPixelSize(attr, -1);
                    viewFlagMasks2 = paddingVertical;
                    continue;
                    i++;
                    N = N2;
                case 91:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.peekValue(attr) != null) {
                        setKeyboardNavigationCluster(a.getBoolean(attr, true));
                        break;
                    }
                    break;
                case 92:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    this.mNextClusterForwardId = a.getResourceId(attr, -1);
                    break;
                case 93:
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    if (a.peekValue(attr) != null) {
                        setFocusedByDefault(a.getBoolean(attr, true));
                        break;
                    }
                    break;
                case 94:
                    paddingHorizontal = paddingHorizontal2;
                    if (a.peekValue(attr) == null) {
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    } else {
                        String rawString2 = null;
                        if (a.getType(attr) == 1) {
                            int resId = a.getResourceId(attr, 0);
                            try {
                                CharSequence[] rawHints2 = a.getTextArray(attr);
                                rawHints = rawHints2;
                            } catch (Resources.NotFoundException e) {
                                rawString2 = getResources().getString(resId);
                                rawHints = null;
                            }
                            rawString = rawString2;
                        } else {
                            String rawString3 = a.getString(attr);
                            rawString = rawString3;
                            rawHints = null;
                        }
                        if (rawHints == null) {
                            if (rawString == null) {
                                throw new IllegalArgumentException(
                                        "Could not resolve autofillHints");
                            }
                            rawHints = rawString.split(",");
                        }
                        String[] hints = new String[rawHints.length];
                        padding2 = padding3;
                        int padding11 = rawHints.length;
                        rightPaddingDefined = rightPaddingDefined2;
                        for (int rawHintNum = 0; rawHintNum < padding11; rawHintNum++) {
                            hints[rawHintNum] = rawHints[rawHintNum].toString().trim();
                        }
                        setAutofillHints(hints);
                        break;
                    }
                case 95:
                    paddingHorizontal = paddingHorizontal2;
                    if (a.peekValue(attr) != null) {
                        setImportantForAutofill(a.getInt(attr, 0));
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    } else {
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    }
                case 96:
                    paddingHorizontal = paddingHorizontal2;
                    if (a.peekValue(attr) != null) {
                        setDefaultFocusHighlightEnabled(a.getBoolean(attr, true));
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    } else {
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    }
                case 97:
                    paddingHorizontal = paddingHorizontal2;
                    if (a.peekValue(attr) != null) {
                        setScreenReaderFocusable(a.getBoolean(attr, false));
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    } else {
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    }
                case 98:
                    paddingHorizontal = paddingHorizontal2;
                    if (a.peekValue(attr) != null) {
                        setAccessibilityPaneTitle(a.getString(attr));
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    } else {
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    }
                case 99:
                    paddingHorizontal = paddingHorizontal2;
                    setAccessibilityHeading(a.getBoolean(attr, false));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 100:
                    paddingHorizontal = paddingHorizontal2;
                    setOutlineSpotShadowColor(a.getColor(attr, -16777216));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 101:
                    paddingHorizontal = paddingHorizontal2;
                    setOutlineAmbientShadowColor(a.getColor(attr, -16777216));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 102:
                    paddingHorizontal = paddingHorizontal2;
                    this.mRenderNode.setForceDarkAllowed(a.getBoolean(attr, true));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 103:
                    paddingHorizontal = paddingHorizontal2;
                    if (a.peekValue(attr) != null) {
                        setImportantForContentCapture(a.getInt(attr, 0));
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    } else {
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    }
                case 104:
                    paddingHorizontal = paddingHorizontal2;
                    setAllowClickWhenDisabled(a.getBoolean(attr, false));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 105:
                    paddingHorizontal = paddingHorizontal2;
                    setClipToOutline(a.getBoolean(attr, false));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 106:
                    paddingHorizontal = paddingHorizontal2;
                    setPreferKeepClear(a.getBoolean(attr, false));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 107:
                    paddingHorizontal = paddingHorizontal2;
                    setAutoHandwritingEnabled(a.getBoolean(attr, false));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 108:
                    paddingHorizontal = paddingHorizontal2;
                    this.mHandwritingBoundsOffsetLeft = a.getDimension(attr, 0.0f);
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 109:
                    paddingHorizontal = paddingHorizontal2;
                    this.mHandwritingBoundsOffsetTop = a.getDimension(attr, 0.0f);
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 110:
                    paddingHorizontal = paddingHorizontal2;
                    this.mHandwritingBoundsOffsetRight = a.getDimension(attr, 0.0f);
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 111:
                    paddingHorizontal = paddingHorizontal2;
                    this.mHandwritingBoundsOffsetBottom = a.getDimension(attr, 0.0f);
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 112:
                    paddingHorizontal = paddingHorizontal2;
                    setAccessibilityDataSensitive(a.getInt(attr, 0));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 113:
                    paddingHorizontal = paddingHorizontal2;
                    if (a.peekValue(attr) != null) {
                        setIsCredential(a.getBoolean(attr, false));
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    } else {
                        rightPaddingDefined = rightPaddingDefined2;
                        padding2 = padding3;
                        break;
                    }
                case 114:
                    paddingHorizontal = paddingHorizontal2;
                    setContentSensitivity(a.getInt(attr, 0));
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 120:
                    if (this.mForegroundInfo == null) {
                        paddingHorizontal = paddingHorizontal2;
                        this.mForegroundInfo = new ForegroundInfo();
                    } else {
                        paddingHorizontal = paddingHorizontal2;
                    }
                    this.mForegroundInfo.mInsidePadding =
                            a.getBoolean(attr, this.mForegroundInfo.mInsidePadding);
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 121:
                    semSetHoverPopupType(a.getInt(attr, 0));
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
                case 122:
                    setScrollCaptureHint(a.getInt(attr, 0));
                    paddingHorizontal = paddingHorizontal2;
                    rightPaddingDefined = rightPaddingDefined2;
                    padding2 = padding3;
                    break;
            }
            padding3 = padding2;
            paddingHorizontal2 = paddingHorizontal;
            rightPaddingDefined2 = rightPaddingDefined;
            i++;
            N = N2;
        }
        int paddingHorizontal12 = paddingHorizontal2;
        boolean rightPaddingDefined8 = rightPaddingDefined2;
        int padding12 = padding3;
        setOverScrollMode(topPadding2);
        this.mUserPaddingStart = padding4;
        this.mUserPaddingEnd = paddingHorizontal3;
        if (background != null) {
            setBackground(background);
        }
        this.mLeftPaddingDefined = leftPaddingDefined;
        this.mRightPaddingDefined = rightPaddingDefined8;
        if (padding12 >= 0) {
            padding = padding12;
            bottomPadding = padding12;
            this.mUserPaddingLeftInitial = padding;
            this.mUserPaddingRightInitial = padding;
            viewFlagValues2 = padding12;
            rightPadding = padding12;
            topPadding = padding;
        } else {
            padding = padding12;
            if (paddingHorizontal12 >= 0) {
                leftPadding = paddingHorizontal12;
                this.mUserPaddingLeftInitial = paddingHorizontal12;
                this.mUserPaddingRightInitial = paddingHorizontal12;
                rightPadding2 = paddingHorizontal12;
            }
            if (viewFlagMasks2 < 0) {
                rightPadding = rightPadding2;
                bottomPadding = x3;
                topPadding = leftPadding;
            } else {
                int topPadding4 = viewFlagMasks2;
                bottomPadding = viewFlagMasks2;
                viewFlagValues2 = topPadding4;
                rightPadding = rightPadding2;
                topPadding = leftPadding;
            }
        }
        if (isRtlCompatibilityMode()) {
            if (!this.mLeftPaddingDefined && startPaddingDefined) {
                topPadding = padding4;
            }
            this.mUserPaddingLeftInitial =
                    topPadding >= 0 ? topPadding : this.mUserPaddingLeftInitial;
            if (!this.mRightPaddingDefined && endPaddingDefined) {
                rightPadding = paddingHorizontal3;
            }
            this.mUserPaddingRightInitial =
                    rightPadding >= 0 ? rightPadding : this.mUserPaddingRightInitial;
        } else {
            boolean hasRelativePadding = startPaddingDefined || endPaddingDefined;
            if (this.mLeftPaddingDefined && !hasRelativePadding) {
                this.mUserPaddingLeftInitial = topPadding;
            }
            if (this.mRightPaddingDefined && !hasRelativePadding) {
                this.mUserPaddingRightInitial = rightPadding;
            }
        }
        int i2 = this.mUserPaddingLeftInitial;
        int i3 = viewFlagValues2 >= 0 ? viewFlagValues2 : this.mPaddingTop;
        int rightPadding4 = this.mUserPaddingRightInitial;
        internalSetPadding(
                i2, i3, rightPadding4, bottomPadding >= 0 ? bottomPadding : this.mPaddingBottom);
        if (endPadding != 0) {
            setFlags(startPadding, endPadding);
        }
        if (initializeScrollbars) {
            initializeScrollbarsInternal(a);
        }
        if (initializeScrollIndicators) {
            initializeScrollIndicatorsInternal();
        }
        a.recycle();
        if (scrollbarStyle != 0) {
            recomputePadding();
        }
        if (x2 == 0 && y == 0) {
            x = x2;
        } else {
            x = x2;
            scrollTo(x, y);
        }
        if (transformSet) {
            setTranslationX(tx);
            setTranslationY(ty);
            float ty3 = tz;
            setTranslationZ(ty3);
            float tz3 = elevation;
            setElevation(tz3);
            float elevation3 = rotation;
            setRotation(elevation3);
            float rotation3 = rotationX;
            setRotationX(rotation3);
            float rotationX3 = rotationY;
            setRotationY(rotationX3);
            float rotationY3 = sx;
            setScaleX(rotationY3);
            float sx3 = sy;
            setScaleY(sx3);
        }
        if (y2 == 0 && (startPadding & 512) != 0) {
            setScrollContainer(true);
        }
        computeOpaqueFlags();
    }

    public int[] getAttributeResolutionStack(int attribute) {
        if (!sDebugViewAttributes
                || this.mAttributeResolutionStacks == null
                || this.mAttributeResolutionStacks.get(attribute) == null) {
            return new int[0];
        }
        int[] attributeResolutionStack = this.mAttributeResolutionStacks.get(attribute);
        int stackSize = attributeResolutionStack.length;
        if (this.mSourceLayoutId != 0) {
            stackSize++;
        }
        int currentIndex = 0;
        int[] stack = new int[stackSize];
        if (this.mSourceLayoutId != 0) {
            stack[0] = this.mSourceLayoutId;
            currentIndex = 0 + 1;
        }
        for (int i : attributeResolutionStack) {
            stack[currentIndex] = i;
            currentIndex++;
        }
        return stack;
    }

    public Map<Integer, Integer> getAttributeSourceResourceMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        if (!sDebugViewAttributes || this.mAttributeSourceResId == null) {
            return map;
        }
        for (int i = 0; i < this.mAttributeSourceResId.size(); i++) {
            map.put(
                    Integer.valueOf(this.mAttributeSourceResId.keyAt(i)),
                    Integer.valueOf(this.mAttributeSourceResId.valueAt(i)));
        }
        return map;
    }

    public int getExplicitStyle() {
        if (!sDebugViewAttributes) {
            return 0;
        }
        return this.mExplicitStyle;
    }

    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View hostView, String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, v);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        private void resolveMethod(Context context, String name) {
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted()
                            && (method = context.getClass().getMethod(this.mMethodName, View.class))
                                    != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException e) {
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            String idText =
                    id == -1
                            ? ""
                            : " with id '"
                                    + this.mHostView
                                            .getContext()
                                            .getResources()
                                            .getResourceEntryName(id)
                                    + "'";
            throw new IllegalStateException(
                    "Could not find method "
                            + this.mMethodName
                            + "(View) in a parent or ancestor Context for android:onClick attribute"
                            + " defined on view "
                            + this.mHostView.getClass()
                            + idText);
        }
    }

    View() {
        InputEventConsistencyVerifier inputEventConsistencyVerifier;
        this.mScrollFeedbackProvider = null;
        this.mFrameRateCompatibility = 1;
        this.mCurrentAnimation = null;
        this.mRecreateDisplayList = false;
        this.mID = -1;
        this.mAutofillViewId = -1;
        this.mAccessibilityViewId = -1;
        this.mAccessibilityCursorPosition = -1;
        this.mTag = null;
        this.mTransientStateCount = 0;
        this.mClipBounds = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mExplicitAccessibilityDataSensitive = 0;
        this.mInferredAccessibilityDataSensitive = 0;
        this.mLabelForId = -1;
        this.mAccessibilityTraversalBeforeId = -1;
        this.mAccessibilityTraversalAfterId = -1;
        this.mLeftPaddingDefined = false;
        this.mRightPaddingDefined = false;
        this.mOldWidthMeasureSpec = Integer.MIN_VALUE;
        this.mOldHeightMeasureSpec = Integer.MIN_VALUE;
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        this.mDrawableState = null;
        this.mOutlineProvider = ViewOutlineProvider.BACKGROUND;
        this.mNextFocusLeftId = -1;
        this.mNextFocusRightId = -1;
        this.mNextFocusUpId = -1;
        this.mNextFocusDownId = -1;
        this.mNextFocusForwardId = -1;
        this.mNextClusterForwardId = -1;
        this.mDefaultFocusHighlightEnabled = true;
        this.mPendingCheckForTap = null;
        this.mTouchDelegate = null;
        this.mHoveringTouchDelegate = false;
        this.mDrawingCacheBackgroundColor = 0;
        this.mAnimator = null;
        this.mRoundRadius = -1;
        this.mRoundedCornerBounds = new Rect();
        this.mRoundedCornerOffset = new Pair<>(0, 0);
        this.mCornerOffset = 0;
        this.mExtraPaddingBottomForPreference = 0;
        this.mLayerType = 0;
        this.mDisablePenGestureforfactorytest = false;
        this.isPenSideButton = false;
        if (!InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            inputEventConsistencyVerifier = null;
        } else {
            inputEventConsistencyVerifier = new InputEventConsistencyVerifier(this, 0);
        }
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mIsDeviceDefault = false;
        this.mSourceLayoutId = 0;
        this.mUnbufferedInputSource = 0;
        this.mFrameContentVelocity = -1.0f;
        this.mGfxImageFilter = null;
        this.mPreferredFrameRate = Float.NaN;
        this.mLastFrameRateCategory = 1;
        this.mNeedToSendSavedStickyDragEvent = false;
        this.mNeededToChangedScrollBarPosition = false;
        this.mScrollBarPositionPadding = 0;
        this.mSemScrollingByScrollbar = false;
        this.mSemScrollingVertical = true;
        this.mSemVerticalScrollbarRect = new Rect();
        this.mSemHorizontalScrollbarRect = new Rect();
        this.mIsSetFingerHoveredInAppWidget = true;
        this.mShouldFakeFocus = false;
        this.mHoverPopup = null;
        this.mHoverPopupType = 0;
        this.mHoverPopupToolTypeByApp = 0;
        this.VELOCITY_THRESHOLD_T1 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_t1", 4000);
        this.VELOCITY_THRESHOLD_T2 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_t2", 500);
        this.VELOCITY_THRESHOLD_T3 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_t3", 125);
        this.VELOCITY_FRAMERATE1 =
                SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate1", 120);
        this.VELOCITY_FRAMERATE2 =
                SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate2", 120);
        this.VELOCITY_FRAMERATE3 =
                SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate3", 120);
        this.VELOCITY_FRAMERATE4 =
                SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate4", 60);
        this.mIsFlingState = false;
        this.mBlurInfo = null;
        this.mBlurMode = -1;
        this.mBlurRadius = 128;
        this.mLastBlurRadius = 0;
        this.mBlurColorCurve = null;
        this.mBackgroundBlurCornerRadiusTL = 0.0f;
        this.mBackgroundBlurCornerRadiusTR = 0.0f;
        this.mBackgroundBlurCornerRadiusBL = 0.0f;
        this.mBackgroundBlurCornerRadiusBR = 0.0f;
        this.mBackgroundBlurColor = 0;
        this.mCanvasDownScale = 8;
        this.mCapturingCanvas = false;
        this.mAppWidgetScrollBarBottomPadding = 0;
        this.mAppWidgetScrollBarTopPadding = 0;
        this.mSmartClipDataTag = null;
        this.mSmartClipDataExtractionListener = null;
        this.mResources = null;
        this.mRenderNode =
                RenderNode.create(getClass().getName(), new ViewAnimationHostBridge(this));
    }

    public final boolean isShowingLayoutBounds() {
        return DEBUG_DRAW || (this.mAttachInfo != null && this.mAttachInfo.mDebugLayout);
    }

    public final void setShowingLayoutBounds(boolean debugLayout) {
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mDebugLayout = debugLayout;
        }
    }

    private static SparseArray<String> getAttributeMap() {
        if (mAttributeMap == null) {
            mAttributeMap = new SparseArray<>();
        }
        return mAttributeMap;
    }

    private void retrieveExplicitStyle(Resources.Theme theme, AttributeSet attrs) {
        if (!sDebugViewAttributes) {
            return;
        }
        this.mExplicitStyle = theme.getExplicitStyle(attrs);
    }

    public final void saveAttributeDataForStyleable(
            Context context,
            int[] styleable,
            AttributeSet attrs,
            TypedArray t,
            int defStyleAttr,
            int defStyleRes) {
        if (!sDebugViewAttributes) {
            return;
        }
        int[] attributeResolutionStack =
                context.getTheme()
                        .getAttributeResolutionStack(
                                defStyleAttr, defStyleRes, this.mExplicitStyle);
        if (this.mAttributeResolutionStacks == null) {
            this.mAttributeResolutionStacks = new SparseArray<>();
        }
        if (this.mAttributeSourceResId == null) {
            this.mAttributeSourceResId = new SparseIntArray();
        }
        int indexCount = t.getIndexCount();
        for (int j = 0; j < indexCount; j++) {
            int index = t.getIndex(j);
            this.mAttributeSourceResId.append(styleable[index], t.getSourceResourceId(index, 0));
            this.mAttributeResolutionStacks.append(styleable[index], attributeResolutionStack);
        }
    }

    private void saveAttributeData(AttributeSet attrs, TypedArray t) {
        int resourceId;
        int attrsCount = attrs == null ? 0 : attrs.getAttributeCount();
        int indexCount = t.getIndexCount();
        String[] attributes = new String[(attrsCount + indexCount) * 2];
        int i = 0;
        for (int j = 0; j < attrsCount; j++) {
            attributes[i] = attrs.getAttributeName(j);
            attributes[i + 1] = attrs.getAttributeValue(j);
            i += 2;
        }
        Resources res = t.getResources();
        SparseArray<String> attributeMap = getAttributeMap();
        int i2 = i;
        for (int j2 = 0; j2 < indexCount; j2++) {
            int index = t.getIndex(j2);
            if (t.hasValueOrEmpty(index) && (resourceId = t.getResourceId(index, 0)) != 0) {
                String resourceName = attributeMap.get(resourceId);
                if (resourceName == null) {
                    try {
                        resourceName = res.getResourceName(resourceId);
                    } catch (Resources.NotFoundException e) {
                        resourceName = "0x" + Integer.toHexString(resourceId);
                    }
                    attributeMap.put(resourceId, resourceName);
                }
                attributes[i2] = resourceName;
                attributes[i2 + 1] = t.getString(index);
                i2 += 2;
            }
        }
        String[] trimmed = new String[i2];
        System.arraycopy(attributes, 0, trimmed, 0, i2);
        this.mAttributes = trimmed;
    }

    protected void semEnableHorizontalScrollbar() {
        this.mViewFlags = (this.mViewFlags & (-769)) | 256;
    }

    public String toString() {
        StringBuilder out = new StringBuilder(256);
        out.append(getClass().getName());
        out.append('{');
        out.append(Integer.toHexString(System.identityHashCode(this)));
        out.append(' ');
        switch (this.mViewFlags & 12) {
            case 0:
                out.append('V');
                break;
            case 4:
                out.append('I');
                break;
            case 8:
                out.append('G');
                break;
            default:
                out.append('.');
                break;
        }
        out.append((this.mViewFlags & 1) == 1 ? 'F' : '.');
        out.append((this.mViewFlags & 32) == 0 ? DateFormat.DAY : '.');
        out.append((this.mViewFlags & 128) == 128 ? '.' : 'D');
        out.append((256 & this.mViewFlags) != 0 ? 'H' : '.');
        out.append((this.mViewFlags & 512) == 0 ? '.' : 'V');
        out.append((this.mViewFlags & 16384) != 0 ? 'C' : '.');
        out.append((this.mViewFlags & 2097152) != 0 ? DateFormat.STANDALONE_MONTH : '.');
        out.append((this.mViewFlags & 8388608) != 0 ? 'X' : '.');
        out.append(' ');
        out.append((this.mPrivateFlags & 8) != 0 ? 'R' : '.');
        out.append((this.mPrivateFlags & 2) == 0 ? '.' : 'F');
        out.append((this.mPrivateFlags & 4) != 0 ? 'S' : '.');
        if ((this.mPrivateFlags & 33554432) != 0) {
            out.append('p');
        } else {
            out.append((this.mPrivateFlags & 16384) != 0 ? 'P' : '.');
        }
        out.append((this.mPrivateFlags & 268435456) == 0 ? '.' : 'H');
        out.append((this.mPrivateFlags & 1073741824) != 0 ? DateFormat.CAPITAL_AM_PM : '.');
        out.append((this.mPrivateFlags & Integer.MIN_VALUE) == 0 ? '.' : 'I');
        out.append((this.mPrivateFlags & 2097152) != 0 ? 'D' : '.');
        out.append(' ');
        out.append(this.mLeft);
        out.append(',');
        out.append(this.mTop);
        out.append('-');
        out.append(this.mRight);
        out.append(',');
        out.append(this.mBottom);
        appendId(out);
        if (this.mAutofillId != null) {
            out.append(" aid=");
            out.append(this.mAutofillId);
        }
        out.append("}");
        return out.toString();
    }

    void appendId(StringBuilder out) {
        String pkgname;
        int id = getId();
        if (id != -1) {
            out.append(" #");
            out.append(Integer.toHexString(id));
            Resources r = this.mResources;
            if (id > 0 && Resources.resourceHasPackage(id) && r != null) {
                switch ((-16777216) & id) {
                    case 16777216:
                        pkgname = "android";
                        String typename = r.getResourceTypeName(id);
                        String entryname = r.getResourceEntryName(id);
                        out.append(" ");
                        out.append(pkgname);
                        out.append(":");
                        out.append(typename);
                        out.append("/");
                        out.append(entryname);
                        return;
                    case 2130706432:
                        pkgname = "app";
                        String typename2 = r.getResourceTypeName(id);
                        String entryname2 = r.getResourceEntryName(id);
                        out.append(" ");
                        out.append(pkgname);
                        out.append(":");
                        out.append(typename2);
                        out.append("/");
                        out.append(entryname2);
                        return;
                    default:
                        try {
                            pkgname = r.getResourcePackageName(id);
                            String typename22 = r.getResourceTypeName(id);
                            String entryname22 = r.getResourceEntryName(id);
                            out.append(" ");
                            out.append(pkgname);
                            out.append(":");
                            out.append(typename22);
                            out.append("/");
                            out.append(entryname22);
                            return;
                        } catch (Resources.NotFoundException e) {
                        }
                }
            }
        }
    }

    protected void initializeFadingEdge(TypedArray a) {
        TypedArray arr = this.mContext.obtainStyledAttributes(R.styleable.View);
        initializeFadingEdgeInternal(arr);
        arr.recycle();
    }

    protected void initializeFadingEdgeInternal(TypedArray a) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength =
                a.getDimensionPixelSize(
                        25, ViewConfiguration.get(this.mContext).getScaledFadingEdgeLength());
    }

    public int getVerticalFadingEdgeLength() {
        ScrollabilityCache cache;
        if (isVerticalFadingEdgeEnabled() && (cache = this.mScrollCache) != null) {
            return cache.fadingEdgeLength;
        }
        return 0;
    }

    public void setFadingEdgeLength(int length) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = length;
    }

    public void clearPendingCredentialRequest() {
        if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            Log.v(AUTOFILL_LOG_TAG, "clearPendingCredentialRequest called");
        }
        this.mViewCredentialHandler = null;
    }

    public void setPendingCredentialRequest(
            GetCredentialRequest request,
            OutcomeReceiver<GetCredentialResponse, GetCredentialException> callback) {
        Preconditions.checkNotNull(request, "request must not be null");
        Preconditions.checkNotNull(callback, "callback must not be null");
        for (CredentialOption option : request.getCredentialOptions()) {
            ArrayList<AutofillId> ids =
                    option.getCandidateQueryData()
                            .getParcelableArrayList(
                                    CredentialProviderService.EXTRA_AUTOFILL_ID, AutofillId.class);
            ArrayList<AutofillId> ids2 = ids != null ? ids : new ArrayList<>();
            if (!ids2.contains(getAutofillId())) {
                ids2.add(getAutofillId());
            }
            option.getCandidateQueryData()
                    .putParcelableArrayList(CredentialProviderService.EXTRA_AUTOFILL_ID, ids2);
        }
        this.mViewCredentialHandler = new ViewCredentialHandler(request, callback);
    }

    public ViewCredentialHandler getViewCredentialHandler() {
        return this.mViewCredentialHandler;
    }

    public int getHorizontalFadingEdgeLength() {
        ScrollabilityCache cache;
        if (isHorizontalFadingEdgeEnabled() && (cache = this.mScrollCache) != null) {
            return cache.fadingEdgeLength;
        }
        return 0;
    }

    public int getVerticalScrollbarWidth() {
        ScrollBarDrawable scrollBar;
        ScrollabilityCache cache = this.mScrollCache;
        if (cache == null || (scrollBar = cache.scrollBar) == null) {
            return 0;
        }
        int size = scrollBar.getSize(true);
        if (size <= 0) {
            return cache.scrollBarSize;
        }
        return size;
    }

    public int getHorizontalScrollbarHeight() {
        ScrollBarDrawable scrollBar;
        ScrollabilityCache cache = this.mScrollCache;
        if (cache == null || (scrollBar = cache.scrollBar) == null) {
            return 0;
        }
        int size = scrollBar.getSize(false);
        if (size <= 0) {
            return cache.scrollBarSize;
        }
        return size;
    }

    protected void initializeScrollbars(TypedArray a) {
        TypedArray arr = this.mContext.obtainStyledAttributes(R.styleable.View);
        initializeScrollbarsInternal(arr);
        arr.recycle();
    }

    private void initializeScrollBarDrawable() {
        initScrollCache();
        if (this.mScrollCache.scrollBar == null) {
            this.mScrollCache.scrollBar = new ScrollBarDrawable();
            this.mScrollCache.scrollBar.setState(getDrawableState());
            this.mScrollCache.scrollBar.setCallback(this);
        }
    }

    protected void initializeScrollbarsInternal(TypedArray a) {
        initScrollCache();
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache.scrollBar == null) {
            scrollabilityCache.scrollBar = new ScrollBarDrawable(this);
            scrollabilityCache.scrollBar.setState(getDrawableState());
            scrollabilityCache.scrollBar.setCallback(this);
        }
        boolean fadeScrollbars = a.getBoolean(47, true);
        if (!fadeScrollbars) {
            scrollabilityCache.state = 1;
        }
        scrollabilityCache.fadeScrollBars = fadeScrollbars;
        scrollabilityCache.scrollBarFadeDuration =
                a.getInt(45, ViewConfiguration.getScrollBarFadeDuration());
        scrollabilityCache.scrollBarDefaultDelayBeforeFade =
                a.getInt(46, ViewConfiguration.getScrollDefaultDelay());
        scrollabilityCache.scrollBarSize =
                a.getDimensionPixelSize(
                        1, ViewConfiguration.get(this.mContext).getScaledScrollBarSize());
        scrollabilityCache.scrollBar.setHorizontalTrackDrawable(a.getDrawable(4));
        Drawable thumb = a.getDrawable(2);
        if (thumb != null) {
            scrollabilityCache.scrollBar.setHorizontalThumbDrawable(thumb);
        }
        boolean alwaysDraw = a.getBoolean(6, false);
        if (alwaysDraw) {
            scrollabilityCache.scrollBar.setAlwaysDrawHorizontalTrack(true);
        }
        Drawable track = a.getDrawable(5);
        scrollabilityCache.scrollBar.setVerticalTrackDrawable(track);
        Drawable thumb2 = a.getDrawable(3);
        if (thumb2 != null) {
            scrollabilityCache.scrollBar.setVerticalThumbDrawable(thumb2);
            if (this.mContext != null) {
                int padding =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sem_scroller_default_vertical_padding);
                this.mAppWidgetScrollBarBottomPadding = padding;
                this.mAppWidgetScrollBarTopPadding = padding;
            }
        }
        boolean alwaysDraw2 = a.getBoolean(7, false);
        if (alwaysDraw2) {
            scrollabilityCache.scrollBar.setAlwaysDrawVerticalTrack(true);
        }
        int layoutDirection = getLayoutDirection();
        if (track != null) {
            track.setLayoutDirection(layoutDirection);
        }
        if (thumb2 != null) {
            thumb2.setLayoutDirection(layoutDirection);
        }
        resolvePadding();
    }

    public void setVerticalScrollbarThumbDrawable(Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setVerticalThumbDrawable(drawable);
    }

    public void setVerticalScrollbarTrackDrawable(Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setVerticalTrackDrawable(drawable);
    }

    public void setHorizontalScrollbarThumbDrawable(Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setHorizontalThumbDrawable(drawable);
    }

    public void setHorizontalScrollbarTrackDrawable(Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setHorizontalTrackDrawable(drawable);
    }

    public Drawable getVerticalScrollbarThumbDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getVerticalThumbDrawable();
        }
        return null;
    }

    public Drawable getVerticalScrollbarTrackDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getVerticalTrackDrawable();
        }
        return null;
    }

    public Drawable getHorizontalScrollbarThumbDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getHorizontalThumbDrawable();
        }
        return null;
    }

    public Drawable getHorizontalScrollbarTrackDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getHorizontalTrackDrawable();
        }
        return null;
    }

    private void initializeScrollIndicatorsInternal() {
        if (this.mScrollIndicatorDrawable == null) {
            this.mScrollIndicatorDrawable =
                    this.mContext.getDrawable(R.drawable.scroll_indicator_material);
        }
    }

    private void initScrollCache() {
        if (this.mScrollCache == null) {
            this.mScrollCache = new ScrollabilityCache(ViewConfiguration.get(this.mContext), this);
        }
    }

    private ScrollabilityCache getScrollCache() {
        initScrollCache();
        return this.mScrollCache;
    }

    public void setVerticalScrollbarPosition(int position) {
        if (this.mVerticalScrollbarPosition != position) {
            this.mVerticalScrollbarPosition = position;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public int getVerticalScrollbarPosition() {
        return this.mVerticalScrollbarPosition;
    }

    public void semSetVerticalScrollBarPadding(boolean flag) {
        this.mNeededToChangedScrollBarPosition = flag;
    }

    public void semSetVerticalScrollBarPaddingPosition(int paddingValue) {
        this.mScrollBarPositionPadding = paddingValue;
    }

    boolean isOnScrollbar(float x, float y) {
        if (this.mScrollCache == null) {
            return false;
        }
        float x2 = x + getScrollX();
        float y2 = y + getScrollY();
        boolean canScrollVertically = computeVerticalScrollRange() > computeVerticalScrollExtent();
        if (isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && canScrollVertically) {
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getVerticalScrollBarBounds(null, touchBounds);
            if (touchBounds.contains((int) x2, (int) y2)) {
                return true;
            }
        }
        boolean canScrollHorizontally =
                computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        if (isHorizontalScrollBarEnabled() && canScrollHorizontally) {
            Rect touchBounds2 = this.mScrollCache.mScrollBarTouchBounds;
            getHorizontalScrollBarBounds(null, touchBounds2);
            if (touchBounds2.contains((int) x2, (int) y2)) {
                return true;
            }
        }
        return false;
    }

    boolean isOnScrollbarThumb(float x, float y) {
        return isOnVerticalScrollbarThumb(x, y) || isOnHorizontalScrollbarThumb(x, y);
    }

    private boolean isOnVerticalScrollbarThumb(float x, float y) {
        int range;
        int extent;
        if (this.mScrollCache != null
                && isVerticalScrollBarEnabled()
                && !isVerticalScrollBarHidden()
                && (range = computeVerticalScrollRange())
                        > (extent = computeVerticalScrollExtent())) {
            float x2 = x + getScrollX();
            float y2 = y + getScrollY();
            Rect bounds = this.mScrollCache.mScrollBarBounds;
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getVerticalScrollBarBounds(bounds, touchBounds);
            int offset = computeVerticalScrollOffset();
            int thumbLength =
                    ScrollBarUtils.getThumbLength(bounds.height(), bounds.width(), extent, range);
            int thumbOffset =
                    ScrollBarUtils.getThumbOffset(
                            bounds.height(), thumbLength, extent, range, offset);
            int thumbTop = bounds.top + thumbOffset;
            int adjust = Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (x2 >= touchBounds.left
                    && x2 <= touchBounds.right
                    && y2 >= thumbTop - adjust
                    && y2 <= thumbTop + thumbLength + adjust) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnHorizontalScrollbarThumb(float x, float y) {
        int range;
        int extent;
        if (this.mScrollCache != null
                && isHorizontalScrollBarEnabled()
                && (range = computeHorizontalScrollRange())
                        > (extent = computeHorizontalScrollExtent())) {
            float x2 = x + getScrollX();
            float y2 = y + getScrollY();
            Rect bounds = this.mScrollCache.mScrollBarBounds;
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getHorizontalScrollBarBounds(bounds, touchBounds);
            int offset = computeHorizontalScrollOffset();
            int thumbLength =
                    ScrollBarUtils.getThumbLength(bounds.width(), bounds.height(), extent, range);
            int thumbOffset =
                    ScrollBarUtils.getThumbOffset(
                            bounds.width(), thumbLength, extent, range, offset);
            int thumbLeft = bounds.left + thumbOffset;
            int adjust = Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (x2 >= thumbLeft - adjust
                    && x2 <= thumbLeft + thumbLength + adjust
                    && y2 >= touchBounds.top
                    && y2 <= touchBounds.bottom) {
                return true;
            }
        }
        return false;
    }

    boolean isDraggingScrollBar() {
        return (this.mScrollCache == null || this.mScrollCache.mScrollBarDraggingState == 0)
                ? false
                : true;
    }

    @RemotableViewMethod
    public void setScrollIndicators(int indicators) {
        setScrollIndicators(indicators, 63);
    }

    public void setScrollIndicators(int indicators, int mask) {
        int mask2 = (mask << 8) & SCROLL_INDICATORS_PFLAG3_MASK;
        int indicators2 = (indicators << 8) & mask2;
        int updatedFlags = (this.mPrivateFlags3 & (~mask2)) | indicators2;
        if (this.mPrivateFlags3 != updatedFlags) {
            this.mPrivateFlags3 = updatedFlags;
            if (indicators2 != 0) {
                initializeScrollIndicatorsInternal();
            }
            invalidate();
        }
    }

    public int getScrollIndicators() {
        return (this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) >>> 8;
    }

    public void setSemHorizontalScrollbarPosition(int position) {
        if (this.mHorizontalScrollbarPosition != position) {
            this.mHorizontalScrollbarPosition = position;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public int semGetHorizontalScrollbarPosition() {
        return this.mHorizontalScrollbarPosition;
    }

    protected int semGetScaledMinScrollbarTouchTarget(ViewConfiguration configuration) {
        if (sIsSamsungBasicInteraction) {
            return 0;
        }
        return configuration.getScaledMinScrollbarTouchTarget();
    }

    ListenerInfo getListenerInfo() {
        if (this.mListenerInfo != null) {
            return this.mListenerInfo;
        }
        this.mListenerInfo = new ListenerInfo();
        return this.mListenerInfo;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        getListenerInfo().mOnScrollChangeListener = l;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        getListenerInfo().mOnFocusChangeListener = l;
    }

    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        ListenerInfo li = getListenerInfo();
        if (li.mOnLayoutChangeListeners == null) {
            li.mOnLayoutChangeListeners = new ArrayList();
        }
        if (!li.mOnLayoutChangeListeners.contains(listener)) {
            li.mOnLayoutChangeListeners.add(listener);
        }
    }

    public void removeOnLayoutChangeListener(OnLayoutChangeListener listener) {
        ListenerInfo li = this.mListenerInfo;
        if (li == null || li.mOnLayoutChangeListeners == null) {
            return;
        }
        li.mOnLayoutChangeListeners.remove(listener);
    }

    public void addOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        ListenerInfo li = getListenerInfo();
        if (li.mOnAttachStateChangeListeners == null) {
            li.mOnAttachStateChangeListeners = new CopyOnWriteArrayList();
        }
        li.mOnAttachStateChangeListeners.add(listener);
    }

    public void removeOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        ListenerInfo li = this.mListenerInfo;
        if (li == null || li.mOnAttachStateChangeListeners == null) {
            return;
        }
        li.mOnAttachStateChangeListeners.remove(listener);
    }

    public OnFocusChangeListener getOnFocusChangeListener() {
        ListenerInfo li = this.mListenerInfo;
        if (li != null) {
            return li.mOnFocusChangeListener;
        }
        return null;
    }

    public void setOnClickListener(OnClickListener l) {
        if (l != null) {
            TypedValue outValue = new TypedValue();
            Context con = getContext();
            if (con != null && con.getTheme() != null) {
                this.mIsDeviceDefault =
                        con.getTheme()
                                        .resolveAttribute(
                                                R.attr.parentIsDeviceDefault, outValue, true)
                                && outValue.data != 0;
            }
        }
        if (!isClickable()) {
            setClickable(true);
        }
        getListenerInfo().mOnClickListener = l;
    }

    public boolean hasOnClickListeners() {
        ListenerInfo li = this.mListenerInfo;
        return (li == null || li.mOnClickListener == null) ? false : true;
    }

    public void setOnLongClickListener(OnLongClickListener l) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        getListenerInfo().mOnLongClickListener = l;
    }

    public boolean hasOnLongClickListeners() {
        ListenerInfo li = this.mListenerInfo;
        return (li == null || li.mOnLongClickListener == null) ? false : true;
    }

    public OnLongClickListener getOnLongClickListener() {
        ListenerInfo li = this.mListenerInfo;
        if (li != null) {
            return li.mOnLongClickListener;
        }
        return null;
    }

    public void setOnContextClickListener(OnContextClickListener l) {
        if (!isContextClickable()) {
            setContextClickable(true);
        }
        getListenerInfo().mOnContextClickListener = l;
    }

    public void setOnCreateContextMenuListener(OnCreateContextMenuListener l) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        getListenerInfo().mOnCreateContextMenuListener = l;
    }

    public void addFrameMetricsListener(
            Window window, Window.OnFrameMetricsAvailableListener listener, Handler handler) {
        if (this.mAttachInfo != null) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                if (this.mFrameMetricsObservers == null) {
                    this.mFrameMetricsObservers = new ArrayList<>();
                }
                FrameMetricsObserver fmo = new FrameMetricsObserver(window, handler, listener);
                this.mFrameMetricsObservers.add(fmo);
                this.mAttachInfo.mThreadedRenderer.addObserver(fmo.getRendererObserver());
                return;
            }
            Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
            return;
        }
        if (this.mFrameMetricsObservers == null) {
            this.mFrameMetricsObservers = new ArrayList<>();
        }
        this.mFrameMetricsObservers.add(new FrameMetricsObserver(window, handler, listener));
    }

    public boolean isFrameMetricsObservers() {
        return this.mFrameMetricsObservers != null;
    }

    public void removeFrameMetricsListener(Window.OnFrameMetricsAvailableListener listener) {
        ThreadedRenderer renderer = getThreadedRenderer();
        FrameMetricsObserver fmo = findFrameMetricsObserver(listener);
        if (fmo == null) {
            throw new IllegalArgumentException(
                    "attempt to remove OnFrameMetricsAvailableListener that was never added");
        }
        if (this.mFrameMetricsObservers != null) {
            this.mFrameMetricsObservers.remove(fmo);
            if (renderer != null) {
                renderer.removeObserver(fmo.getRendererObserver());
            }
        }
    }

    private void registerPendingFrameMetricsObservers() {
        if (this.mFrameMetricsObservers != null) {
            ThreadedRenderer renderer = getThreadedRenderer();
            if (renderer != null) {
                Iterator<FrameMetricsObserver> it = this.mFrameMetricsObservers.iterator();
                while (it.hasNext()) {
                    FrameMetricsObserver fmo = it.next();
                    renderer.addObserver(fmo.getRendererObserver());
                }
                return;
            }
            Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
        }
    }

    private FrameMetricsObserver findFrameMetricsObserver(
            Window.OnFrameMetricsAvailableListener listener) {
        if (this.mFrameMetricsObservers != null) {
            for (int i = 0; i < this.mFrameMetricsObservers.size(); i++) {
                FrameMetricsObserver observer = this.mFrameMetricsObservers.get(i);
                if (observer.mListener == listener) {
                    return observer;
                }
            }
            return null;
        }
        return null;
    }

    public void setNotifyAutofillManagerOnClick(boolean notify) {
        if (notify) {
            this.mPrivateFlags |= 536870912;
        } else {
            this.mPrivateFlags &= -536870913;
        }
    }

    private void notifyAutofillManagerOnClick() {
        if ((this.mPrivateFlags & 536870912) != 0) {
            try {
                getAutofillManager().notifyViewClicked(this);
            } finally {
                this.mPrivateFlags = (-536870913) & this.mPrivateFlags;
            }
        }
    }

    public boolean performClickInternal() {
        notifyAutofillManagerOnClick();
        return performClick();
    }

    public boolean performClick() {
        boolean result;
        notifyAutofillManagerOnClick();
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnClickListener != null) {
            playSoundEffect(0);
            li.mOnClickListener.onClick(this);
            result = true;
        } else {
            result = false;
        }
        sendAccessibilityEvent(1);
        notifyEnterOrExitForAutoFillIfNeeded(true);
        return result;
    }

    public boolean callOnClick() {
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnClickListener != null) {
            li.mOnClickListener.onClick(this);
            return true;
        }
        return false;
    }

    public boolean performLongClick() {
        return performLongClickInternal(this.mLongClickX, this.mLongClickY);
    }

    public boolean performLongClick(float x, float y) {
        this.mLongClickX = x;
        this.mLongClickY = y;
        boolean handled = performLongClick();
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        return handled;
    }

    private boolean performLongClickInternal(float x, float y) {
        sendAccessibilityEvent(2);
        boolean handled = false;
        OnLongClickListener listener =
                this.mListenerInfo == null ? null : this.mListenerInfo.mOnLongClickListener;
        boolean shouldPerformHapticFeedback = true;
        if (listener != null && (handled = listener.onLongClick(this))) {
            shouldPerformHapticFeedback = listener.onLongClickUseDefaultHapticFeedback(this);
        }
        if (!handled) {
            boolean isAnchored = (Float.isNaN(x) || Float.isNaN(y)) ? false : true;
            handled = isAnchored ? showContextMenu(x, y) : showContextMenu();
        }
        if ((this.mViewFlags & 1073741824) == 1073741824 && !handled) {
            handled = showLongClickTooltip((int) x, (int) y);
        }
        if (handled && shouldPerformHapticFeedback) {
            performHapticFeedback(0);
        }
        return handled;
    }

    public boolean performContextClick(float x, float y) {
        return performContextClick();
    }

    public boolean performContextClick() {
        sendAccessibilityEvent(8388608);
        boolean handled = false;
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnContextClickListener != null) {
            handled = li.mOnContextClickListener.onContextClick(this);
        }
        if (handled) {
            performHapticFeedback(6);
        }
        return handled;
    }

    protected boolean performButtonActionOnTouchDown(MotionEvent event) {
        if (event.isFromSource(8194) && (event.getButtonState() & 2) != 0) {
            showContextMenu(event.getX(), event.getY());
            this.mPrivateFlags |= 67108864;
            return true;
        }
        return false;
    }

    public boolean showContextMenu() {
        return getParent().showContextMenuForChild(this);
    }

    public boolean showContextMenu(float x, float y) {
        return getParent().showContextMenuForChild(this, x, y);
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        ViewParent parent = getParent();
        if (parent == null) {
            return null;
        }
        try {
            return parent.startActionModeForChild(this, callback, type);
        } catch (AbstractMethodError e) {
            return parent.startActionModeForChild(this, callback);
        }
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        this.mStartActivityRequestWho = "@android:view:" + System.identityHashCode(this);
        getContext()
                .startActivityForResult(this.mStartActivityRequestWho, intent, requestCode, null);
    }

    public boolean dispatchActivityResult(
            String who, int requestCode, int resultCode, Intent data) {
        if (this.mStartActivityRequestWho != null && this.mStartActivityRequestWho.equals(who)) {
            onActivityResult(requestCode, resultCode, data);
            this.mStartActivityRequestWho = null;
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    public void setOnKeyListener(OnKeyListener l) {
        getListenerInfo().mOnKeyListener = l;
    }

    public void setOnTouchListener(OnTouchListener l) {
        getListenerInfo().mOnTouchListener = l;
    }

    public void setOnGenericMotionListener(OnGenericMotionListener l) {
        getListenerInfo().mOnGenericMotionListener = l;
    }

    public void setOnHoverListener(OnHoverListener l) {
        getListenerInfo().mOnHoverListener = l;
    }

    public void setOnDragListener(OnDragListener l) {
        if (l != null && getListenerInfo().mOnDragListener == null) {
            if (isAttachedToWindow()) {
                postRequestSendStickyDragStartedEvent();
            } else {
                this.mNeedToSendSavedStickyDragEvent = true;
            }
        }
        getListenerInfo().mOnDragListener = l;
    }

    void handleFocusGainInternal(int direction, Rect previouslyFocusedRect) {
        if ((this.mPrivateFlags & 2) == 0) {
            this.mPrivateFlags |= 2;
            View oldFocus = this.mAttachInfo != null ? getRootView().findFocus() : null;
            if (this.mParent != null) {
                this.mParent.requestChildFocus(this, this);
                updateFocusedInCluster(oldFocus, direction);
            }
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mTreeObserver.dispatchOnGlobalFocusChange(oldFocus, this);
            }
            onFocusChanged(true, direction, previouslyFocusedRect);
            refreshDrawableState();
        }
    }

    public final void setRevealOnFocusHint(boolean revealOnFocus) {
        if (revealOnFocus) {
            this.mPrivateFlags3 &= -67108865;
        } else {
            this.mPrivateFlags3 |= 67108864;
        }
    }

    public final boolean getRevealOnFocusHint() {
        return (this.mPrivateFlags3 & 67108864) == 0;
    }

    public void getHotspotBounds(Rect outRect) {
        Drawable background = getBackground();
        if (background != null) {
            background.getHotspotBounds(outRect);
        } else {
            getBoundsOnScreen(outRect);
        }
    }

    public boolean requestRectangleOnScreen(Rect rectangle) {
        return requestRectangleOnScreen(rectangle, false);
    }

    public boolean requestRectangleOnScreen(Rect rectangle, boolean immediate) {
        if (this.mParent == null) {
            return false;
        }
        View child = this;
        RectF position =
                this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformRect : new RectF();
        position.set(rectangle);
        ViewParent parent = this.mParent;
        boolean scrolled = false;
        while (parent != null) {
            rectangle.set(
                    (int) position.left,
                    (int) position.top,
                    (int) position.right,
                    (int) position.bottom);
            scrolled |= parent.requestChildRectangleOnScreen(child, rectangle, immediate);
            if (!(parent instanceof View)) {
                break;
            }
            position.offset(child.mLeft - child.getScrollX(), child.mTop - child.getScrollY());
            View child2 = parent;
            child = child2;
            parent = child.getParent();
        }
        return scrolled;
    }

    public void clearFocus() {
        boolean refocus = sAlwaysAssignFocus || !isInTouchMode();
        clearFocusInternal(null, true, refocus);
    }

    public void clearFocusInternal(View focused, boolean propagate, boolean refocus) {
        if ((this.mPrivateFlags & 2) != 0) {
            this.mPrivateFlags &= -3;
            clearParentsWantFocus();
            if (propagate && this.mParent != null) {
                this.mParent.clearChildFocus(this);
            }
            onFocusChanged(false, 0, null);
            refreshDrawableState();
            if (propagate) {
                if (!refocus || !rootViewRequestFocus()) {
                    notifyGlobalFocusCleared(this);
                }
            }
        }
    }

    void notifyGlobalFocusCleared(View oldFocus) {
        if (oldFocus != null && this.mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnGlobalFocusChange(oldFocus, null);
        }
    }

    boolean rootViewRequestFocus() {
        View root = getRootView();
        return root != null && root.requestFocus();
    }

    void unFocus(View focused) {
        clearFocusInternal(focused, false, false);
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public boolean hasFocus() {
        return (this.mPrivateFlags & 2) != 0;
    }

    public boolean hasFocusable() {
        return hasFocusable(!sHasFocusableExcludeAutoFocusable, false);
    }

    public boolean hasExplicitFocusable() {
        return hasFocusable(false, true);
    }

    boolean hasFocusable(boolean allowAutoFocus, boolean dispatchExplicit) {
        if (!isFocusableInTouchMode()) {
            for (ViewParent p = this.mParent; p instanceof ViewGroup; p = p.getParent()) {
                ViewGroup g = (ViewGroup) p;
                if (g.shouldBlockFocusForTouchscreen()) {
                    return false;
                }
            }
        }
        if ((this.mViewFlags & 12) == 0 && (this.mViewFlags & 32) == 0) {
            return (allowAutoFocus || getFocusable() != 16) && isFocusable();
        }
        return false;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (gainFocus) {
            sendAccessibilityEvent(8);
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
        switchDefaultFocusHighlight();
        if (!gainFocus) {
            if (isPressed()) {
                setPressed(false);
            }
            if (hasWindowFocus()) {
                notifyFocusChangeToImeFocusController(false);
            }
            onFocusLost();
        } else if (hasWindowFocus()) {
            notifyFocusChangeToImeFocusController(true);
            ViewRootImpl viewRoot = getViewRootImpl();
            if (viewRoot != null) {
                if (this.mIsHandwritingDelegate) {
                    viewRoot.getHandwritingInitiator().onDelegateViewFocused(this);
                } else if (android.view.inputmethod.Flags.initiationWithoutInputConnection()
                        && onCheckIsTextEditor()) {
                    viewRoot.getHandwritingInitiator().onEditorFocused(this);
                }
            }
        }
        invalidate(true);
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnFocusChangeListener != null) {
            li.mOnFocusChangeListener.onFocusChange(this, gainFocus);
        }
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mKeyDispatchState.reset(this);
        }
        if (this.mParent != null) {
            this.mParent.onDescendantUnbufferedRequested();
        }
        notifyEnterOrExitForAutoFillIfNeeded(gainFocus);
        updatePreferKeepClearForFocus();
    }

    private void notifyFocusChangeToImeFocusController(boolean hasFocus) {
        if (this.mAttachInfo == null) {
            return;
        }
        this.mAttachInfo.mViewRootImpl.getImeFocusController().onViewFocusChanged(this, hasFocus);
    }

    public void notifyEnterOrExitForAutoFillIfNeeded(boolean enter) {
        AutofillManager afm;
        if (canNotifyAutofillEnterExitEvent() && (afm = getAutofillManager()) != null) {
            if (enter) {
                if (!isLaidOut() || !isVisibleToUser()) {
                    this.mPrivateFlags3 |= 134217728;
                    return;
                } else {
                    if (isVisibleToUser()) {
                        if (isFocused()) {
                            afm.notifyViewEntered(this);
                            return;
                        } else {
                            afm.notifyViewEnteredForFillDialog(this);
                            return;
                        }
                    }
                    return;
                }
            }
            if (!isFocused()) {
                afm.notifyViewExited(this);
            }
        }
    }

    public void setAccessibilityPaneTitle(CharSequence accessibilityPaneTitle) {
        if (!TextUtils.equals(accessibilityPaneTitle, this.mAccessibilityPaneTitle)) {
            boolean currentPaneTitleEmpty = this.mAccessibilityPaneTitle == null;
            boolean newPaneTitleEmpty = accessibilityPaneTitle == null;
            this.mAccessibilityPaneTitle = accessibilityPaneTitle;
            if (this.mAccessibilityPaneTitle != null && getImportantForAccessibility() == 0) {
                setImportantForAccessibility(1);
            }
            if (currentPaneTitleEmpty) {
                notifyViewAccessibilityStateChangedIfNeeded(16);
            } else if (newPaneTitleEmpty) {
                notifyViewAccessibilityStateChangedIfNeeded(32);
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(8);
            }
        }
    }

    public CharSequence getAccessibilityPaneTitle() {
        return this.mAccessibilityPaneTitle;
    }

    private boolean isAccessibilityPane() {
        return this.mAccessibilityPaneTitle != null;
    }

    @Override // android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEvent(int eventType) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.sendAccessibilityEvent(this, eventType);
        } else {
            sendAccessibilityEventInternal(eventType);
        }
    }

    public void announceForAccessibility(CharSequence text) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mParent != null) {
            AccessibilityEvent event = AccessibilityEvent.obtain(16384);
            onInitializeAccessibilityEvent(event);
            event.getText().add(text);
            event.setContentDescription(null);
            this.mParent.requestSendAccessibilityEvent(this, event);
        }
    }

    public void sendAccessibilityEventInternal(int eventType) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            sendAccessibilityEventUnchecked(AccessibilityEvent.obtain(eventType));
        }
    }

    @Override // android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.sendAccessibilityEventUnchecked(this, event);
        } else {
            sendAccessibilityEventUncheckedInternal(event);
        }
    }

    public void sendAccessibilityEventUncheckedInternal(final AccessibilityEvent event) {
        boolean isWindowStateChanged = event.getEventType() == 32;
        boolean isWindowDisappearedEvent =
                isWindowStateChanged && (32 & event.getContentChangeTypes()) != 0;
        boolean detached = detached();
        if (!isShown() && !isWindowDisappearedEvent && !detached) {
            return;
        }
        onInitializeAccessibilityEvent(event);
        if ((event.getEventType() & POPULATING_ACCESSIBILITY_EVENT_TYPES) != 0) {
            dispatchPopulateAccessibilityEvent(event);
        }
        SendAccessibilityEventThrottle throttle = getThrottleForAccessibilityEvent(event);
        if (throttle != null) {
            throttle.post(event);
        } else if (!isWindowDisappearedEvent && detached) {
            postDelayed(
                    new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            View.this.lambda$sendAccessibilityEventUncheckedInternal$0(event);
                        }
                    },
                    ViewConfiguration.getSendRecurringAccessibilityEventsInterval());
        } else {
            requestParentSendAccessibilityEvent(event);
        }
    }

    public /* synthetic */ void lambda$sendAccessibilityEventUncheckedInternal$0(
            AccessibilityEvent event) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && isShown()) {
            requestParentSendAccessibilityEvent(event);
        }
    }

    public void requestParentSendAccessibilityEvent(AccessibilityEvent event) {
        ViewParent parent = getParent();
        if (parent != null) {
            getParent().requestSendAccessibilityEvent(this, event);
        }
    }

    private SendAccessibilityEventThrottle getThrottleForAccessibilityEvent(
            AccessibilityEvent accessibilityEvent) {
        byte b = 0;
        if (accessibilityEvent.getEventType() == 4096) {
            if (this.mSendViewScrolledAccessibilityEvent == null) {
                this.mSendViewScrolledAccessibilityEvent = new SendViewScrolledAccessibilityEvent();
            }
            return this.mSendViewScrolledAccessibilityEvent;
        }
        boolean z = (accessibilityEvent.getContentChangeTypes() & 64) != 0;
        if (accessibilityEvent.getEventType() != 2048 || !z) {
            return null;
        }
        if (this.mSendStateChangedAccessibilityEvent == null) {
            this.mSendStateChangedAccessibilityEvent = new SendAccessibilityEventThrottle();
        }
        return this.mSendStateChangedAccessibilityEvent;
    }

    private void clearAccessibilityThrottles() {
        cancel(this.mSendViewScrolledAccessibilityEvent);
        cancel(this.mSendStateChangedAccessibilityEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.dispatchPopulateAccessibilityEvent(this, event);
        }
        return dispatchPopulateAccessibilityEventInternal(event);
    }

    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        onPopulateAccessibilityEvent(event);
        return false;
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onPopulateAccessibilityEvent(this, event);
        } else {
            onPopulateAccessibilityEventInternal(event);
        }
    }

    public void onPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        if (event.getEventType() == 32 && isAccessibilityPane()) {
            event.getText().add(getAccessibilityPaneTitle());
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onInitializeAccessibilityEvent(this, event);
        } else {
            onInitializeAccessibilityEventInternal(event);
        }
    }

    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        event.setSource(this);
        event.setClassName(getAccessibilityClassName());
        event.setPackageName(getContext().getPackageName());
        event.setEnabled(isEnabled());
        event.setContentDescription(this.mContentDescription);
        event.setScrollX(getScrollX());
        event.setScrollY(getScrollY());
        switch (event.getEventType()) {
            case 8:
                ArrayList<View> focusablesTempList =
                        this.mAttachInfo != null
                                ? this.mAttachInfo.mTempArrayList
                                : new ArrayList<>();
                getRootView().addFocusables(focusablesTempList, 2, 0);
                event.setItemCount(focusablesTempList.size());
                event.setCurrentItemIndex(focusablesTempList.indexOf(this));
                if (this.mAttachInfo != null) {
                    focusablesTempList.clear();
                    break;
                }
                break;
            case 8192:
                CharSequence text = getIterableTextForAccessibility();
                if (text != null && text.length() > 0) {
                    event.setFromIndex(getAccessibilitySelectionStart());
                    event.setToIndex(getAccessibilitySelectionEnd());
                    event.setItemCount(text.length());
                    break;
                }
                break;
        }
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo() {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.createAccessibilityNodeInfo(this);
        }
        return createAccessibilityNodeInfoInternal();
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfoInternal() {
        AccessibilityNodeProvider provider = getAccessibilityNodeProvider();
        if (provider != null) {
            return provider.createAccessibilityNodeInfo(-1);
        }
        AccessibilityNodeInfo info = AccessibilityNodeInfo.obtain(this);
        onInitializeAccessibilityNodeInfo(info);
        return info;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onInitializeAccessibilityNodeInfo(this, info);
        } else {
            onInitializeAccessibilityNodeInfoInternal(info);
        }
    }

    public void getBoundsOnScreen(Rect outRect) {
        getBoundsOnScreen(outRect, false);
    }

    public void getBoundsOnScreen(Rect outRect, boolean clipToParent) {
        if (this.mAttachInfo == null) {
            return;
        }
        RectF position = this.mAttachInfo.mTmpTransformRect;
        getBoundsToScreenInternal(position, clipToParent);
        outRect.set(
                Math.round(position.left),
                Math.round(position.top),
                Math.round(position.right),
                Math.round(position.bottom));
        this.mAttachInfo.mViewRootImpl.applyViewBoundsSandboxingIfNeeded(outRect);
    }

    public void getBoundsOnScreen(RectF outRect, boolean clipToParent) {
        if (this.mAttachInfo == null) {
            return;
        }
        RectF position = this.mAttachInfo.mTmpTransformRect;
        getBoundsToScreenInternal(position, clipToParent);
        outRect.set(position.left, position.top, position.right, position.bottom);
    }

    public void getBoundsInWindow(Rect outRect, boolean clipToParent) {
        if (this.mAttachInfo == null) {
            return;
        }
        RectF position = this.mAttachInfo.mTmpTransformRect;
        getBoundsToWindowInternal(position, clipToParent);
        outRect.set(
                Math.round(position.left),
                Math.round(position.top),
                Math.round(position.right),
                Math.round(position.bottom));
    }

    private void getBoundsToScreenInternal(RectF position, boolean clipToParent) {
        position.set(0.0f, 0.0f, this.mRight - this.mLeft, this.mBottom - this.mTop);
        mapRectFromViewToScreenCoords(position, clipToParent);
    }

    private void getBoundsToWindowInternal(RectF position, boolean clipToParent) {
        position.set(0.0f, 0.0f, this.mRight - this.mLeft, this.mBottom - this.mTop);
        mapRectFromViewToWindowCoords(position, clipToParent);
    }

    public void mapRectFromViewToScreenCoords(RectF rect, boolean clipToParent) {
        mapRectFromViewToWindowCoords(rect, clipToParent);
        rect.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    public void mapRectFromViewToWindowCoords(RectF rect, boolean clipToParent) {
        if (!hasIdentityMatrix()) {
            getMatrix().mapRect(rect);
        }
        rect.offset(this.mLeft, this.mTop);
        ViewParent parent = this.mParent;
        while (parent instanceof View) {
            View parentView = (View) parent;
            rect.offset(-parentView.mScrollX, -parentView.mScrollY);
            if (clipToParent) {
                rect.left = Math.max(rect.left, 0.0f);
                rect.top = Math.max(rect.top, 0.0f);
                rect.right = Math.min(rect.right, parentView.getWidth());
                rect.bottom = Math.min(rect.bottom, parentView.getHeight());
            }
            if (!parentView.hasIdentityMatrix()) {
                parentView.getMatrix().mapRect(rect);
            }
            rect.offset(parentView.mLeft, parentView.mTop);
            parent = parentView.mParent;
        }
        if (parent instanceof ViewRootImpl) {
            ViewRootImpl viewRootImpl = (ViewRootImpl) parent;
            rect.offset(0.0f, -viewRootImpl.mCurScrollY);
        }
    }

    public CharSequence getAccessibilityClassName() {
        return View.class.getName();
    }

    public void onProvideStructure(ViewStructure structure) {
        onProvideStructure(structure, 0, 0);
    }

    public void onProvideAutofillStructure(ViewStructure structure, int flags) {
        onProvideStructure(structure, 1, flags);
    }

    public void onProvideContentCaptureStructure(ViewStructure structure, int flags) {
        onProvideStructure(structure, 2, flags);
    }

    protected void onProvideStructure(ViewStructure structure, int viewFor, int flags) {
        String pkg;
        String type;
        String pkg2;
        int id = this.mID;
        if (id != -1 && !isViewIdGenerated(id)) {
            try {
                Resources res = getResources();
                pkg = res.getResourceEntryName(id);
                type = res.getResourceTypeName(id);
                pkg2 = res.getResourcePackageName(id);
            } catch (Resources.NotFoundException e) {
                pkg = null;
                type = null;
                pkg2 = null;
            }
            structure.setId(id, pkg2, type, pkg);
        } else {
            structure.setId(id, null, null, null);
        }
        if (viewFor == 1 || viewFor == 2) {
            int autofillType = getAutofillType();
            if (autofillType != 0) {
                structure.setAutofillType(autofillType);
                structure.setAutofillHints(getAutofillHints());
                structure.setAutofillValue(getAutofillValue());
                structure.setIsCredential(isCredential());
            }
            if (getViewCredentialHandler() != null) {
                structure.setPendingCredentialRequest(
                        getViewCredentialHandler().getRequest(),
                        getViewCredentialHandler().getCallback());
            }
            structure.setImportantForAutofill(getImportantForAutofill());
            structure.setReceiveContentMimeTypes(getReceiveContentMimeTypes());
        }
        int ignoredParentLeft = 0;
        int ignoredParentTop = 0;
        if (viewFor == 1 && (flags & 1) == 0) {
            View parentGroup = null;
            Object parent = getParent();
            if (parent instanceof View) {
                parentGroup = (View) parent;
            }
            while (parentGroup != null && !parentGroup.isImportantForAutofill()) {
                ignoredParentLeft += parentGroup.mLeft - parentGroup.mScrollX;
                ignoredParentTop += parentGroup.mTop - parentGroup.mScrollY;
                Object parent2 = parentGroup.getParent();
                if (!(parent2 instanceof View)) {
                    break;
                } else {
                    parentGroup = (View) parent2;
                }
            }
        }
        structure.setDimens(
                ignoredParentLeft + this.mLeft,
                ignoredParentTop + this.mTop,
                this.mScrollX,
                this.mScrollY,
                this.mRight - this.mLeft,
                this.mBottom - this.mTop);
        if (viewFor == 0) {
            if (!hasIdentityMatrix()) {
                structure.setTransformation(getMatrix());
            }
            structure.setElevation(getZ());
        }
        structure.setVisibility(getVisibility());
        structure.setEnabled(isEnabled());
        if (isClickable()) {
            structure.setClickable(true);
        }
        if (isFocusable()) {
            structure.setFocusable(true);
        }
        if (isFocused()) {
            structure.setFocused(true);
        }
        if (isAccessibilityFocused()) {
            structure.setAccessibilityFocused(true);
        }
        if (isSelected()) {
            structure.setSelected(true);
        }
        if (isActivated()) {
            structure.setActivated(true);
        }
        if (isLongClickable()) {
            structure.setLongClickable(true);
        }
        if (this instanceof Checkable) {
            structure.setCheckable(true);
            if (((Checkable) this).isChecked()) {
                structure.setChecked(true);
            }
        }
        if (isOpaque()) {
            structure.setOpaque(true);
        }
        if (isContextClickable()) {
            structure.setContextClickable(true);
        }
        structure.setClassName(getAccessibilityClassName().toString());
        structure.setContentDescription(getContentDescription());
    }

    public void onProvideVirtualStructure(ViewStructure structure) {
        onProvideVirtualStructureCompat(structure, false);
    }

    private void onProvideVirtualStructureCompat(ViewStructure structure, boolean forAutofill) {
        AccessibilityNodeProvider provider = getAccessibilityNodeProvider();
        if (provider != null) {
            if (forAutofill && Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                Log.v(AUTOFILL_LOG_TAG, "onProvideVirtualStructureCompat() for " + this);
            }
            AccessibilityNodeInfo info = createAccessibilityNodeInfo();
            structure.setChildCount(1);
            ViewStructure root = structure.newChild(0);
            if (info == null) {
                Log.w(AUTOFILL_LOG_TAG, "AccessibilityNodeInfo is null.");
            } else {
                populateVirtualStructure(root, provider, info, forAutofill);
                info.recycle();
            }
        }
    }

    public void onProvideAutofillVirtualStructure(ViewStructure structure, int flags) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            onProvideVirtualStructureCompat(structure, true);
        }
    }

    public void setOnReceiveContentListener(String[] mimeTypes, OnReceiveContentListener listener) {
        if (listener != null) {
            Preconditions.checkArgument(
                    mimeTypes != null && mimeTypes.length > 0,
                    "When the listener is set, MIME types must also be set");
        }
        if (mimeTypes != null) {
            Preconditions.checkArgument(
                    Arrays.stream(mimeTypes)
                            .noneMatch(
                                    new Predicate() { // from class:
                                                      // android.view.View$$ExternalSyntheticLambda9
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean startsWith;
                                            startsWith = ((String) obj).startsWith("*");
                                            return startsWith;
                                        }
                                    }),
                    "A MIME type set here must not start with *: " + Arrays.toString(mimeTypes));
        }
        this.mReceiveContentMimeTypes = ArrayUtils.isEmpty(mimeTypes) ? null : mimeTypes;
        getListenerInfo().mOnReceiveContentListener = listener;
    }

    public ContentInfo performReceiveContent(ContentInfo payload) {
        OnReceiveContentListener listener =
                this.mListenerInfo == null ? null : getListenerInfo().mOnReceiveContentListener;
        if (listener != null) {
            ContentInfo remaining = listener.onReceiveContent(this, payload);
            if (remaining == null) {
                return null;
            }
            return onReceiveContent(remaining);
        }
        return onReceiveContent(payload);
    }

    public ContentInfo onReceiveContent(ContentInfo payload) {
        return payload;
    }

    public String[] getReceiveContentMimeTypes() {
        return this.mReceiveContentMimeTypes;
    }

    public void autofill(AutofillValue value) {}

    public void autofill(SparseArray<AutofillValue> values) {
        AccessibilityNodeProvider provider;
        if (!this.mContext.isAutofillCompatibilityEnabled()
                || (provider = getAccessibilityNodeProvider()) == null) {
            return;
        }
        int valueCount = values.size();
        for (int i = 0; i < valueCount; i++) {
            AutofillValue value = values.valueAt(i);
            if (value.isText()) {
                int virtualId = values.keyAt(i);
                CharSequence text = value.getTextValue();
                Bundle arguments = new Bundle();
                arguments.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
                provider.performAction(virtualId, 2097152, arguments);
            }
        }
    }

    public void onGetCredentialResponse(GetCredentialResponse response) {
        if (getPendingCredentialCallback() == null) {
            Log.w(AUTOFILL_LOG_TAG, "onGetCredentialResponse called but no callback found");
        } else {
            getPendingCredentialCallback().onResult(response);
        }
    }

    public void onGetCredentialException(String errorType, String errorMsg) {
        if (getPendingCredentialCallback() == null) {
            Log.w(AUTOFILL_LOG_TAG, "onGetCredentialException called but no callback found");
        } else {
            getPendingCredentialCallback().onError(new GetCredentialException(errorType, errorMsg));
        }
    }

    public final AutofillId getAutofillId() {
        if (this.mAutofillId == null) {
            this.mAutofillId = new AutofillId(getAutofillViewId());
        }
        return this.mAutofillId;
    }

    public final GetCredentialRequest getPendingCredentialRequest() {
        if (this.mViewCredentialHandler == null) {
            return null;
        }
        return this.mViewCredentialHandler.getRequest();
    }

    public final OutcomeReceiver<GetCredentialResponse, GetCredentialException>
            getPendingCredentialCallback() {
        if (this.mViewCredentialHandler == null) {
            return null;
        }
        return this.mViewCredentialHandler.getCallback();
    }

    public void setAutofillId(AutofillId id) {
        if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            Log.v(AUTOFILL_LOG_TAG, "setAutofill(): from " + this.mAutofillId + " to " + id);
        }
        if (isAttachedToWindow()) {
            throw new IllegalStateException("Cannot set autofill id when view is attached");
        }
        if (id != null && !id.isNonVirtual()) {
            throw new IllegalStateException("Cannot set autofill id assigned to virtual views");
        }
        if (id == null && (this.mPrivateFlags3 & 1073741824) == 0) {
            return;
        }
        this.mAutofillId = id;
        if (id != null) {
            this.mAutofillViewId = id.getViewId();
            this.mPrivateFlags3 = 1073741824 | this.mPrivateFlags3;
        } else {
            this.mAutofillViewId = -1;
            this.mPrivateFlags3 &= -1073741825;
        }
    }

    public void resetSubtreeAutofillIds() {
        if (this.mAutofillViewId == -1) {
            return;
        }
        if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 2)) {
            Log.v(CONTENT_CAPTURE_LOG_TAG, "resetAutofillId() for " + this.mAutofillViewId);
        } else if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            Log.v(AUTOFILL_LOG_TAG, "resetAutofillId() for " + this.mAutofillViewId);
        }
        this.mAutofillId = null;
        this.mAutofillViewId = -1;
        this.mPrivateFlags3 &= -1073741825;
    }

    public int getAutofillType() {
        return 0;
    }

    @ViewDebug.ExportedProperty
    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public boolean isAutofilled() {
        return (this.mPrivateFlags3 & 65536) != 0;
    }

    public boolean hideAutofillHighlight() {
        return (this.mPrivateFlags4 & 512) != 0;
    }

    public AutofillValue getAutofillValue() {
        return null;
    }

    @ViewDebug.ExportedProperty(
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "auto"),
                @ViewDebug.IntToString(from = 1, to = "yes"),
                @ViewDebug.IntToString(from = 2, to = "no"),
                @ViewDebug.IntToString(from = 4, to = "yesExcludeDescendants"),
                @ViewDebug.IntToString(from = 8, to = "noExcludeDescendants")
            })
    public int getImportantForAutofill() {
        return (this.mPrivateFlags3 & PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK) >> 19;
    }

    public void setImportantForAutofill(int mode) {
        this.mPrivateFlags3 &= -7864321;
        this.mPrivateFlags3 |= (mode << 19) & PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK;
    }

    public final boolean isImportantForAutofill() {
        for (ViewParent parent = this.mParent;
                parent instanceof View;
                parent = parent.getParent()) {
            int parentImportance = ((View) parent).getImportantForAutofill();
            if (parentImportance == 8 || parentImportance == 4) {
                if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                    Log.v(
                            AUTOFILL_LOG_TAG,
                            "View ("
                                    + this
                                    + ") is not important for autofill because parent "
                                    + parent
                                    + "'s importance is "
                                    + parentImportance);
                }
                return false;
            }
        }
        int importance = getImportantForAutofill();
        if (importance == 4 || importance == 1) {
            return true;
        }
        if (importance == 8 || importance == 2) {
            if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                Log.v(
                        AUTOFILL_LOG_TAG,
                        "View ("
                                + this
                                + ") is not important for autofill because its importance is "
                                + importance);
            }
            return false;
        }
        if (importance != 0) {
            Log.w(
                    AUTOFILL_LOG_TAG,
                    "invalid autofill importance (" + importance + " on view " + this);
            return false;
        }
        int id = this.mID;
        if (id != -1 && !isViewIdGenerated(id)) {
            Resources res = getResources();
            String entry = null;
            String pkg = null;
            try {
                entry = res.getResourceEntryName(id);
                pkg = res.getResourcePackageName(id);
            } catch (Resources.NotFoundException e) {
            }
            if (entry != null && pkg != null && pkg.equals(this.mContext.getPackageName())) {
                return true;
            }
        }
        return getAutofillHints() != null;
    }

    public final void setContentSensitivity(int mode) {
        this.mPrivateFlags4 &= -50331649;
        this.mPrivateFlags4 |= (mode << 24) & 50331648;
        if (Flags.sensitiveContentAppProtection()) {
            updateSensitiveViewsCountIfNeeded(isAggregatedVisible());
        }
    }

    public final int getContentSensitivity() {
        return (this.mPrivateFlags4 & 50331648) >> 24;
    }

    public final boolean isContentSensitive() {
        int contentSensitivity = getContentSensitivity();
        if (contentSensitivity == 1) {
            return true;
        }
        if (contentSensitivity != 2 && Flags.sensitiveContentAppProtection()) {
            return SensitiveAutofillHintsHelper.containsSensitiveAutofillHint(getAutofillHints());
        }
        return false;
    }

    private void updateSensitiveViewsCountIfNeeded(boolean appeared) {
        if (!Flags.sensitiveContentAppProtection() || this.mAttachInfo == null) {
            return;
        }
        if (!appeared || !isContentSensitive()) {
            if ((67108864 & this.mPrivateFlags4) != 0) {
                this.mPrivateFlags4 &= -67108865;
                this.mAttachInfo.decreaseSensitiveViewsCount();
                return;
            }
            return;
        }
        if ((this.mPrivateFlags4 & 67108864) == 0) {
            this.mPrivateFlags4 = 67108864 | this.mPrivateFlags4;
            this.mAttachInfo.increaseSensitiveViewsCount();
        }
    }

    @ViewDebug.ExportedProperty(
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "auto"),
                @ViewDebug.IntToString(from = 1, to = "yes"),
                @ViewDebug.IntToString(from = 2, to = "no"),
                @ViewDebug.IntToString(from = 4, to = "yesExcludeDescendants"),
                @ViewDebug.IntToString(from = 8, to = "noExcludeDescendants")
            })
    public int getImportantForContentCapture() {
        return this.mPrivateFlags4 & 15;
    }

    public void setImportantForContentCapture(int mode) {
        this.mPrivateFlags4 &= -16;
        this.mPrivateFlags4 |= mode & 15;
    }

    public final boolean isImportantForContentCapture() {
        if ((this.mPrivateFlags4 & 64) != 0) {
            return (this.mPrivateFlags4 & 128) != 0;
        }
        boolean isImportant = calculateIsImportantForContentCapture();
        this.mPrivateFlags4 &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        if (isImportant) {
            this.mPrivateFlags4 |= 128;
        }
        this.mPrivateFlags4 |= 64;
        return isImportant;
    }

    private boolean calculateIsImportantForContentCapture() {
        for (ViewParent parent = this.mParent;
                parent instanceof View;
                parent = parent.getParent()) {
            int parentImportance = ((View) parent).getImportantForContentCapture();
            if (parentImportance == 8 || parentImportance == 4) {
                if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 2)) {
                    Log.v(
                            CONTENT_CAPTURE_LOG_TAG,
                            "View ("
                                    + this
                                    + ") is not important for content capture because parent "
                                    + parent
                                    + "'s importance is "
                                    + parentImportance);
                }
                return false;
            }
        }
        int importance = getImportantForContentCapture();
        if (importance == 4 || importance == 1) {
            return true;
        }
        if (importance == 8 || importance == 2) {
            if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 2)) {
                Log.v(
                        CONTENT_CAPTURE_LOG_TAG,
                        "View ("
                                + this
                                + ") is not important for content capture because its importance is"
                                + " "
                                + importance);
            }
            return false;
        }
        if (importance != 0) {
            Log.w(
                    CONTENT_CAPTURE_LOG_TAG,
                    "invalid content capture importance (" + importance + " on view " + this);
            return false;
        }
        if (this instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) this;
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                if (child.isImportantForContentCapture()) {
                    return true;
                }
            }
        }
        return getAutofillHints() != null;
    }

    private void notifyAppearedOrDisappearedForContentCaptureIfNeeded(boolean appeared) {
        AttachInfo ai = this.mAttachInfo;
        if ((ai == null || ai.mReadyForContentCaptureUpdates)
                && this.mContext.getContentCaptureOptions() != null) {
            if (appeared) {
                boolean isRecycledWithoutRelayout =
                        getNotifiedContentCaptureDisappeared()
                                && getVisibility() == 0
                                && !isLayoutRequested();
                if (getVisibility() == 0 && !getNotifiedContentCaptureAppeared()) {
                    if (!isLaidOut() && !isRecycledWithoutRelayout) {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                boolean isRecycledWithoutRelayout2 = getNotifiedContentCaptureAppeared();
                if (!isRecycledWithoutRelayout2 || getNotifiedContentCaptureDisappeared()) {
                    return;
                }
            }
            ContentCaptureSession session = getContentCaptureSession();
            if (session != null && isImportantForContentCapture()) {
                if (appeared) {
                    setNotifiedContentCaptureAppeared();
                    if (ai != null) {
                        makeParentImportantAndNotifyAppearedEventIfNeed();
                        ai.delayNotifyContentCaptureEvent(session, this, appeared);
                        return;
                    }
                    return;
                }
                this.mPrivateFlags4 |= 32;
                this.mPrivateFlags4 &= -17;
                if (ai != null) {
                    ai.delayNotifyContentCaptureEvent(session, this, appeared);
                }
                if (!isTemporarilyDetached()) {
                    clearTranslationState();
                }
            }
        }
    }

    private void makeParentImportantAndNotifyAppearedEventIfNeed() {
        Object parent = getParent();
        if (parent instanceof View) {
            View p = (View) parent;
            if (p.getNotifiedContentCaptureAppeared()) {
                return;
            }
            p.mPrivateFlags4 |= 192;
            p.notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
        }
    }

    private void setNotifiedContentCaptureAppeared() {
        this.mPrivateFlags4 |= 16;
        this.mPrivateFlags4 &= -33;
    }

    protected boolean getNotifiedContentCaptureAppeared() {
        return (this.mPrivateFlags4 & 16) != 0;
    }

    private boolean getNotifiedContentCaptureDisappeared() {
        return (this.mPrivateFlags4 & 32) != 0;
    }

    public void setContentCaptureSession(ContentCaptureSession contentCaptureSession) {
        this.mContentCaptureSession = contentCaptureSession;
    }

    public final ContentCaptureSession getContentCaptureSession() {
        if (this.mContentCaptureSessionCached) {
            return this.mContentCaptureSession;
        }
        this.mContentCaptureSession = getAndCacheContentCaptureSession();
        this.mContentCaptureSessionCached = true;
        return this.mContentCaptureSession;
    }

    private ContentCaptureSession getAndCacheContentCaptureSession() {
        if (this.mContentCaptureSession != null) {
            return this.mContentCaptureSession;
        }
        ContentCaptureSession session = null;
        if (this.mParent instanceof View) {
            session = ((View) this.mParent).getContentCaptureSession();
        }
        if (session == null) {
            ContentCaptureManager ccm =
                    (ContentCaptureManager)
                            this.mContext.getSystemService(ContentCaptureManager.class);
            if (ccm == null) {
                return null;
            }
            return ccm.getMainContentCaptureSession();
        }
        return session;
    }

    private AutofillManager getAutofillManager() {
        return (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
    }

    final boolean isActivityDeniedForAutofillForUnimportantView() {
        AutofillManager afm = getAutofillManager();
        if (afm == null) {
            return false;
        }
        return afm.isActivityDeniedForAutofill();
    }

    final boolean isMatchingAutofillableHeuristics() {
        AutofillManager afm = getAutofillManager();
        if (afm == null || !afm.isTriggerFillRequestOnUnimportantViewEnabled()) {
            return false;
        }
        return afm.isAutofillable(this);
    }

    private boolean isAutofillable() {
        AutofillManager afm;
        if (getAutofillType() == 0
                || (afm = getAutofillManager()) == null
                || getAutofillViewId() <= 1073741823) {
            return false;
        }
        if ((isImportantForAutofill() && afm.isTriggerFillRequestOnFilteredImportantViewsEnabled())
                || (!isImportantForAutofill()
                        && afm.isTriggerFillRequestOnUnimportantViewEnabled())) {
            if (afm.isAutofillable(this)) {
                return true;
            }
            return notifyAugmentedAutofillIfNeeded(afm);
        }
        if (isImportantForAutofill()) {
            return true;
        }
        return notifyAugmentedAutofillIfNeeded(afm);
    }

    private boolean notifyAugmentedAutofillIfNeeded(AutofillManager afm) {
        AutofillOptions options = this.mContext.getAutofillOptions();
        if (options == null || !options.isAugmentedAutofillEnabled(this.mContext)) {
            return false;
        }
        afm.notifyViewEnteredForAugmentedAutofill(this);
        return true;
    }

    public boolean canNotifyAutofillEnterExitEvent() {
        return isAutofillable() && isAttachedToWindow();
    }

    private void populateVirtualStructure(
            ViewStructure structure,
            AccessibilityNodeProvider provider,
            AccessibilityNodeInfo info,
            boolean forAutofill) {
        structure.setId(
                AccessibilityNodeInfo.getVirtualDescendantId(info.getSourceNodeId()),
                null,
                null,
                info.getViewIdResourceName());
        Rect rect = structure.getTempRect();
        info.getBoundsInParent(rect);
        structure.setDimens(rect.left, rect.top, 0, 0, rect.width(), rect.height());
        structure.setVisibility(0);
        structure.setEnabled(info.isEnabled());
        if (info.isClickable()) {
            structure.setClickable(true);
        }
        if (info.isFocusable()) {
            structure.setFocusable(true);
        }
        if (info.isFocused()) {
            structure.setFocused(true);
        }
        if (info.isAccessibilityFocused()) {
            structure.setAccessibilityFocused(true);
        }
        if (info.isSelected()) {
            structure.setSelected(true);
        }
        if (info.isLongClickable()) {
            structure.setLongClickable(true);
        }
        if (info.isCheckable()) {
            structure.setCheckable(true);
            if (info.isChecked()) {
                structure.setChecked(true);
            }
        }
        if (info.isContextClickable()) {
            structure.setContextClickable(true);
        }
        if (forAutofill) {
            structure.setAutofillId(
                    new AutofillId(
                            getAutofillId(),
                            AccessibilityNodeInfo.getVirtualDescendantId(info.getSourceNodeId())));
        }
        if (getViewCredentialHandler() != null) {
            structure.setPendingCredentialRequest(
                    getViewCredentialHandler().getRequest(),
                    getViewCredentialHandler().getCallback());
        }
        CharSequence cname = info.getClassName();
        structure.setClassName(cname != null ? cname.toString() : null);
        structure.setContentDescription(info.getContentDescription());
        if (forAutofill) {
            int maxTextLength = info.getMaxTextLength();
            if (maxTextLength != -1) {
                structure.setMaxTextLength(maxTextLength);
            }
            structure.setHint(info.getHintText());
        }
        CharSequence text = info.getText();
        boolean hasText = (text == null && info.getError() == null) ? false : true;
        if (hasText) {
            structure.setText(text, info.getTextSelectionStart(), info.getTextSelectionEnd());
        }
        if (forAutofill) {
            if (info.isEditable()) {
                structure.setDataIsSensitive(true);
                if (hasText) {
                    structure.setAutofillType(1);
                    structure.setAutofillValue(AutofillValue.forText(text));
                }
                int inputType = info.getInputType();
                if (inputType == 0 && info.isPassword()) {
                    inputType = 129;
                }
                structure.setInputType(inputType);
            } else {
                structure.setDataIsSensitive(false);
            }
        }
        int NCHILDREN = info.getChildCount();
        if (NCHILDREN > 0) {
            structure.setChildCount(NCHILDREN);
            for (int i = 0; i < NCHILDREN; i++) {
                if (AccessibilityNodeInfo.getVirtualDescendantId(info.getChildNodeIds().get(i))
                        == -1) {
                    Log.e(VIEW_LOG_TAG, "Virtual view pointing to its host. Ignoring");
                } else {
                    AccessibilityNodeInfo cinfo =
                            provider.createAccessibilityNodeInfo(
                                    AccessibilityNodeInfo.getVirtualDescendantId(
                                            info.getChildId(i)));
                    if (cinfo != null) {
                        ViewStructure child = structure.newChild(i);
                        populateVirtualStructure(child, provider, cinfo, forAutofill);
                        cinfo.recycle();
                    }
                }
            }
        }
    }

    public void dispatchProvideStructure(ViewStructure structure) {
        dispatchProvideStructure(structure, 0, 0);
    }

    public void dispatchProvideAutofillStructure(ViewStructure structure, int flags) {
        dispatchProvideStructure(structure, 1, flags);
    }

    private void dispatchProvideStructure(ViewStructure structure, int viewFor, int flags) {
        if (viewFor == 1) {
            structure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(structure, flags);
            onProvideAutofillVirtualStructure(structure, flags);
        } else if (!isAssistBlocked()) {
            onProvideStructure(structure);
            onProvideVirtualStructure(structure);
        } else {
            structure.setClassName(getAccessibilityClassName().toString());
            structure.setAssistBlocked(true);
        }
    }

    public void dispatchInitialProvideContentCaptureStructure() {
        AttachInfo ai = this.mAttachInfo;
        if (ai == null) {
            Log.w(
                    CONTENT_CAPTURE_LOG_TAG,
                    "dispatchProvideContentCaptureStructure(): no AttachInfo for " + this);
            return;
        }
        ContentCaptureManager ccm = ai.mContentCaptureManager;
        if (ccm == null) {
            Log.w(
                    CONTENT_CAPTURE_LOG_TAG,
                    "dispatchProvideContentCaptureStructure(): no ContentCaptureManager for "
                            + this);
            return;
        }
        ai.mReadyForContentCaptureUpdates = true;
        if (!isImportantForContentCapture()) {
            if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 3)) {
                Log.d(
                        CONTENT_CAPTURE_LOG_TAG,
                        "dispatchProvideContentCaptureStructure(): decorView is not important");
                return;
            }
            return;
        }
        ai.mContentCaptureManager = ccm;
        ContentCaptureSession session = getContentCaptureSession();
        if (session == null) {
            if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 3)) {
                Log.d(
                        CONTENT_CAPTURE_LOG_TAG,
                        "dispatchProvideContentCaptureStructure(): no session for " + this);
            }
        } else {
            session.notifyViewTreeEvent(true);
            try {
                dispatchProvideContentCaptureStructure();
            } finally {
                session.notifyViewTreeEvent(false);
            }
        }
    }

    void dispatchProvideContentCaptureStructure() {
        ContentCaptureSession session = getContentCaptureSession();
        if (session != null) {
            ViewStructure structure = session.newViewStructure(this);
            onProvideContentCaptureStructure(structure, 0);
            setNotifiedContentCaptureAppeared();
            session.notifyViewAppeared(structure);
        }
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction;
        if (this.mAttachInfo == null) {
            return;
        }
        Rect bounds = this.mAttachInfo.mTmpInvalRect;
        getDrawingRect(bounds);
        info.setBoundsInParent(bounds);
        getBoundsOnScreen(bounds, true);
        info.setBoundsInScreen(bounds);
        getBoundsInWindow(bounds, true);
        info.setBoundsInWindow(bounds);
        Object parentForAccessibility = getParentForAccessibility();
        if (parentForAccessibility instanceof View) {
            info.setParent((View) parentForAccessibility);
        }
        if (this.mID != -1) {
            View rootView = getRootView();
            if (rootView == null) {
                rootView = this;
            }
            View label = rootView.findLabelForView(this, this.mID);
            if (label != null) {
                info.setLabeledBy(label);
            }
            if ((this.mAttachInfo.mAccessibilityFetchFlags & 256) != 0
                    && Resources.resourceHasPackage(this.mID)) {
                try {
                    String viewId = getResources().getResourceName(this.mID);
                    info.setViewIdResourceName(viewId);
                } catch (Resources.NotFoundException e) {
                }
            }
        }
        if (this.mLabelForId != -1) {
            View rootView2 = getRootView();
            if (rootView2 == null) {
                rootView2 = this;
            }
            View labeled = rootView2.findViewInsideOutShouldExist(this, this.mLabelForId);
            if (labeled != null) {
                info.setLabelFor(labeled);
            }
        }
        if (this.mAccessibilityTraversalBeforeId != -1) {
            View rootView3 = getRootView();
            if (rootView3 == null) {
                rootView3 = this;
            }
            View next =
                    rootView3.findViewInsideOutShouldExist(
                            this, this.mAccessibilityTraversalBeforeId);
            if (next != null && next.includeForAccessibility()) {
                info.setTraversalBefore(next);
            }
        }
        if (this.mAccessibilityTraversalAfterId != -1) {
            View rootView4 = getRootView();
            if (rootView4 == null) {
                rootView4 = this;
            }
            View next2 =
                    rootView4.findViewInsideOutShouldExist(
                            this, this.mAccessibilityTraversalAfterId);
            if (next2 != null && next2.includeForAccessibility()) {
                info.setTraversalAfter(next2);
            }
        }
        info.setVisibleToUser(isVisibleToUser());
        info.setImportantForAccessibility(isImportantForAccessibility());
        info.setAccessibilityDataSensitive(isAccessibilityDataSensitive());
        info.setPackageName(this.mContext.getPackageName());
        info.setClassName(getAccessibilityClassName());
        info.setStateDescription(getStateDescription());
        info.setContentDescription(getContentDescription());
        info.setEnabled(isEnabled());
        info.setClickable(isClickable());
        info.setFocusable(isFocusable());
        info.setScreenReaderFocusable(isScreenReaderFocusable());
        info.setFocused(isFocused());
        info.setAccessibilityFocused(isAccessibilityFocused());
        info.setSelected(isSelected());
        info.setLongClickable(isLongClickable());
        info.setContextClickable(isContextClickable());
        info.setLiveRegion(getAccessibilityLiveRegion());
        if (this.mTooltipInfo != null && this.mTooltipInfo.mTooltipText != null) {
            info.setTooltipText(this.mTooltipInfo.mTooltipText);
            if (this.mTooltipInfo.mTooltipPopup == null) {
                accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP;
            } else {
                accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
            }
            info.addAction(accessibilityAction);
        }
        info.addAction(4);
        info.addAction(8);
        if (isFocusable()) {
            if (isFocused()) {
                info.addAction(2);
            } else {
                info.addAction(1);
            }
        }
        if (!isAccessibilityFocused()) {
            info.addAction(64);
        } else {
            info.addAction(128);
        }
        if (isClickable() && isEnabled()) {
            info.addAction(16);
        }
        if (isLongClickable() && isEnabled()) {
            info.addAction(32);
        }
        if (isContextClickable() && isEnabled()) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK);
        }
        CharSequence text = getIterableTextForAccessibility();
        if (text != null && text.length() > 0) {
            info.setTextSelection(getAccessibilitySelectionStart(), getAccessibilitySelectionEnd());
            info.addAction(131072);
            info.addAction(256);
            info.addAction(512);
            info.setMovementGranularities(11);
        }
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN);
        populateAccessibilityNodeInfoDrawingOrderInParent(info);
        info.setPaneTitle(this.mAccessibilityPaneTitle);
        info.setHeading(isAccessibilityHeading());
        if (this.mTouchDelegate != null) {
            info.setTouchDelegateInfo(this.mTouchDelegate.getTouchDelegateInfo());
        }
        if (startedSystemDragForAccessibility()) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_CANCEL);
        }
        if (canAcceptAccessibilityDrop()) {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_DROP);
        }
    }

    public void addExtraDataToAccessibilityNodeInfo(
            AccessibilityNodeInfo info, String extraDataKey, Bundle arguments) {}

    private void populateAccessibilityNodeInfoDrawingOrderInParent(AccessibilityNodeInfo info) {
        if ((this.mPrivateFlags & 16) == 0) {
            info.setDrawingOrder(0);
            return;
        }
        int drawingOrderInParent = 1;
        View viewAtDrawingLevel = this;
        ViewParent parent = getParentForAccessibility();
        while (true) {
            if (viewAtDrawingLevel == parent) {
                break;
            }
            Object parent2 = viewAtDrawingLevel.getParent();
            if (!(parent2 instanceof ViewGroup)) {
                drawingOrderInParent = 0;
                break;
            }
            ViewGroup parentGroup = (ViewGroup) parent2;
            int childCount = parentGroup.getChildCount();
            if (childCount > 1) {
                List<View> preorderedList = parentGroup.buildOrderedChildList();
                if (preorderedList != null) {
                    int childDrawIndex = preorderedList.indexOf(viewAtDrawingLevel);
                    for (int i = 0; i < childDrawIndex; i++) {
                        drawingOrderInParent += numViewsForAccessibility(preorderedList.get(i));
                    }
                    preorderedList.clear();
                } else {
                    int childIndex = parentGroup.indexOfChild(viewAtDrawingLevel);
                    boolean customOrder = parentGroup.isChildrenDrawingOrderEnabled();
                    int childDrawIndex2 =
                            (childIndex < 0 || !customOrder)
                                    ? childIndex
                                    : parentGroup.getChildDrawingOrder(childCount, childIndex);
                    int numChildrenToIterate = customOrder ? childCount : childDrawIndex2;
                    if (childDrawIndex2 != 0) {
                        for (int i2 = 0; i2 < numChildrenToIterate; i2++) {
                            int otherDrawIndex =
                                    customOrder
                                            ? parentGroup.getChildDrawingOrder(childCount, i2)
                                            : i2;
                            if (otherDrawIndex < childDrawIndex2) {
                                drawingOrderInParent +=
                                        numViewsForAccessibility(parentGroup.getChildAt(i2));
                            }
                        }
                    }
                }
            }
            viewAtDrawingLevel = (View) parent2;
        }
        info.setDrawingOrder(drawingOrderInParent);
    }

    private static int numViewsForAccessibility(View view) {
        if (view != null) {
            if (view.includeForAccessibility()) {
                return 1;
            }
            if (view instanceof ViewGroup) {
                return ((ViewGroup) view).getNumChildrenForAccessibility();
            }
            return 0;
        }
        return 0;
    }

    private View findLabelForView(View view, int labeledId) {
        if (this.mMatchLabelForPredicate == null) {
            this.mMatchLabelForPredicate = new MatchLabelForPredicate();
        }
        this.mMatchLabelForPredicate.mLabeledId = labeledId;
        return findViewByPredicateInsideOut(view, this.mMatchLabelForPredicate);
    }

    public boolean isVisibleToUserForAutofill(int virtualId) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            AccessibilityNodeProvider provider = getAccessibilityNodeProvider();
            if (provider != null) {
                AccessibilityNodeInfo node = provider.createAccessibilityNodeInfo(virtualId);
                if (node != null) {
                    return node.isVisibleToUser();
                }
                return false;
            }
            Log.w(VIEW_LOG_TAG, "isVisibleToUserForAutofill(" + virtualId + "): no provider");
            return false;
        }
        return true;
    }

    public boolean isVisibleToUser() {
        return isVisibleToUser(null);
    }

    protected boolean isVisibleToUser(Rect boundInView) {
        if (this.mAttachInfo == null || this.mAttachInfo.mWindowVisibility != 0) {
            return false;
        }
        Object current = this;
        while (current instanceof View) {
            View view = (View) current;
            if (view.getAlpha() <= 0.0f
                    || view.getTransitionAlpha() <= 0.0f
                    || view.getVisibility() != 0) {
                return false;
            }
            current = view.mParent;
        }
        Rect visibleRect = this.mAttachInfo.mTmpInvalRect;
        Point offset = this.mAttachInfo.mPoint;
        if (!getGlobalVisibleRect(visibleRect, offset)) {
            return false;
        }
        if (boundInView != null) {
            visibleRect.offset(-offset.x, -offset.y);
            return boundInView.intersect(visibleRect);
        }
        return true;
    }

    public AccessibilityDelegate getAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegate(AccessibilityDelegate delegate) {
        this.mAccessibilityDelegate = delegate;
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.getAccessibilityNodeProvider(this);
        }
        return null;
    }

    public int getAccessibilityViewId() {
        if (this.mAccessibilityViewId == -1) {
            int i = sNextAccessibilityViewId;
            sNextAccessibilityViewId = i + 1;
            this.mAccessibilityViewId = i;
        }
        return this.mAccessibilityViewId;
    }

    public int getAutofillViewId() {
        if (this.mAutofillViewId == -1) {
            this.mAutofillViewId = this.mContext.getNextAutofillId();
        }
        return this.mAutofillViewId;
    }

    public int getAccessibilityWindowId() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mAccessibilityWindowId;
        }
        return -1;
    }

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE)
    public final CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE)
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @RemotableViewMethod
    public void setStateDescription(CharSequence stateDescription) {
        if (this.mStateDescription == null) {
            if (stateDescription == null) {
                return;
            }
        } else if (this.mStateDescription.equals(stateDescription)) {
            return;
        }
        this.mStateDescription = stateDescription;
        if (!TextUtils.isEmpty(stateDescription) && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            AccessibilityEvent event = AccessibilityEvent.obtain();
            event.setEventType(2048);
            event.setContentChangeTypes(64);
            sendAccessibilityEventUnchecked(event);
        }
    }

    @RemotableViewMethod
    public void setContentDescription(CharSequence contentDescription) {
        if (this.mContentDescription == null) {
            if (contentDescription == null) {
                return;
            }
        } else if (this.mContentDescription.equals(contentDescription)) {
            return;
        }
        if (isHoveringUIEnabled() && this.mHoverPopupType == 1) {
            semSetTooltipText(contentDescription);
        }
        this.mContentDescription = contentDescription;
        boolean nonEmptyDesc = contentDescription != null && contentDescription.length() > 0;
        if (nonEmptyDesc && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
            notifySubtreeAccessibilityStateChangedIfNeeded();
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(4);
        }
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalBefore(int beforeId) {
        if (this.mAccessibilityTraversalBeforeId == beforeId) {
            return;
        }
        this.mAccessibilityTraversalBeforeId = beforeId;
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public int getAccessibilityTraversalBefore() {
        return this.mAccessibilityTraversalBeforeId;
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalAfter(int afterId) {
        if (this.mAccessibilityTraversalAfterId == afterId) {
            return;
        }
        this.mAccessibilityTraversalAfterId = afterId;
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public int getAccessibilityTraversalAfter() {
        return this.mAccessibilityTraversalAfterId;
    }

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE)
    public int getLabelFor() {
        return this.mLabelForId;
    }

    @RemotableViewMethod
    public void setLabelFor(int id) {
        if (this.mLabelForId == id) {
            return;
        }
        this.mLabelForId = id;
        if (this.mLabelForId != -1 && this.mID == -1) {
            this.mID = generateViewId();
        }
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    protected void onFocusLost() {
        resetPressedState();
    }

    private void resetPressedState() {
        if ((this.mViewFlags & 32) != 32 && isPressed()) {
            setPressed(false);
            if (!this.mHasPerformedLongPress) {
                removeLongPressCallback();
            }
        }
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return (this.mPrivateFlags & 2) != 0;
    }

    public View findFocus() {
        if ((this.mPrivateFlags & 2) != 0) {
            return this;
        }
        return null;
    }

    public boolean isScrollContainer() {
        return (this.mPrivateFlags & 1048576) != 0;
    }

    public void setScrollContainer(boolean isScrollContainer) {
        if (!isScrollContainer) {
            if ((1048576 & this.mPrivateFlags) != 0) {
                this.mAttachInfo.mScrollContainers.remove(this);
            }
            this.mPrivateFlags &= -1572865;
        } else {
            if (this.mAttachInfo != null && (this.mPrivateFlags & 1048576) == 0) {
                this.mAttachInfo.mScrollContainers.add(this);
                this.mPrivateFlags = 1048576 | this.mPrivateFlags;
            }
            this.mPrivateFlags |= 524288;
        }
    }

    @Deprecated
    public int getDrawingCacheQuality() {
        return this.mViewFlags & 1572864;
    }

    @Deprecated
    public void setDrawingCacheQuality(int quality) {
        setFlags(quality, 1572864);
    }

    public boolean getKeepScreenOn() {
        return (this.mViewFlags & 67108864) != 0;
    }

    public void setKeepScreenOn(boolean keepScreenOn) {
        setFlags(keepScreenOn ? 67108864 : 0, 67108864);
    }

    public int getNextFocusLeftId() {
        return this.mNextFocusLeftId;
    }

    public void setNextFocusLeftId(int nextFocusLeftId) {
        this.mNextFocusLeftId = nextFocusLeftId;
    }

    public int getNextFocusRightId() {
        return this.mNextFocusRightId;
    }

    public void setNextFocusRightId(int nextFocusRightId) {
        this.mNextFocusRightId = nextFocusRightId;
    }

    public int getNextFocusUpId() {
        return this.mNextFocusUpId;
    }

    public void setNextFocusUpId(int nextFocusUpId) {
        this.mNextFocusUpId = nextFocusUpId;
    }

    public int getNextFocusDownId() {
        return this.mNextFocusDownId;
    }

    public void setNextFocusDownId(int nextFocusDownId) {
        this.mNextFocusDownId = nextFocusDownId;
    }

    public int getNextFocusForwardId() {
        return this.mNextFocusForwardId;
    }

    public void setNextFocusForwardId(int nextFocusForwardId) {
        this.mNextFocusForwardId = nextFocusForwardId;
    }

    public int getNextClusterForwardId() {
        return this.mNextClusterForwardId;
    }

    public void setNextClusterForwardId(int nextClusterForwardId) {
        this.mNextClusterForwardId = nextClusterForwardId;
    }

    public boolean isShown() {
        Object obj;
        View current = this;
        while ((current.mViewFlags & 12) == 0 && (obj = current.mParent) != null) {
            if (!(obj instanceof View)) {
                return true;
            }
            current = (View) obj;
            if (current == null) {
                return false;
            }
        }
        return false;
    }

    private boolean detached() {
        View current = this;
        while ((current.mPrivateFlags4 & 8192) == 0) {
            Object obj = current.mParent;
            if (obj == null || !(obj instanceof View) || (current = (View) obj) == null) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    protected boolean fitSystemWindows(Rect insets) {
        if ((this.mPrivateFlags3 & 32) == 0) {
            if (insets == null) {
                return false;
            }
            try {
                this.mPrivateFlags3 |= 64;
                return dispatchApplyWindowInsets(new WindowInsets(insets)).isConsumed();
            } finally {
                this.mPrivateFlags3 &= -65;
            }
        }
        return fitSystemWindowsInt(insets);
    }

    private boolean fitSystemWindowsInt(Rect insets) {
        if ((this.mViewFlags & 2) == 2) {
            Rect localInsets = sThreadLocal.get();
            boolean res = computeFitSystemWindows(insets, localInsets);
            applyInsets(localInsets);
            return res;
        }
        return false;
    }

    private void applyInsets(Rect insets) {
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mUserPaddingLeftInitial = insets.left;
        this.mUserPaddingRightInitial = insets.right;
        internalSetPadding(insets.left, insets.top, insets.right, insets.bottom);
    }

    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if ((this.mPrivateFlags4 & 256) != 0 && (this.mViewFlags & 2) != 0) {
            return onApplyFrameworkOptionalFitSystemWindows(insets);
        }
        if ((this.mPrivateFlags3 & 64) == 0) {
            if (fitSystemWindows(insets.getSystemWindowInsetsAsRect())) {
                return insets.consumeSystemWindowInsets();
            }
        } else if (fitSystemWindowsInt(insets.getSystemWindowInsetsAsRect())) {
            return insets.consumeSystemWindowInsets();
        }
        return insets;
    }

    private WindowInsets onApplyFrameworkOptionalFitSystemWindows(WindowInsets insets) {
        Rect localInsets = sThreadLocal.get();
        WindowInsets result = computeSystemWindowInsets(insets, localInsets);
        applyInsets(localInsets);
        return result;
    }

    public void setOnApplyWindowInsetsListener(OnApplyWindowInsetsListener listener) {
        getListenerInfo().mOnApplyWindowInsetsListener = listener;
    }

    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        try {
            this.mPrivateFlags3 |= 32;
            if (this.mListenerInfo != null
                    && this.mListenerInfo.mOnApplyWindowInsetsListener != null) {
                return this.mListenerInfo.mOnApplyWindowInsetsListener.onApplyWindowInsets(
                        this, insets);
            }
            return onApplyWindowInsets(insets);
        } finally {
            this.mPrivateFlags3 &= -33;
        }
    }

    public void setWindowInsetsAnimationCallback(WindowInsetsAnimation.Callback callback) {
        getListenerInfo().mWindowInsetsAnimationCallback = callback;
    }

    public boolean hasWindowInsetsAnimationCallback() {
        return getListenerInfo().mWindowInsetsAnimationCallback != null;
    }

    public void dispatchWindowInsetsAnimationPrepare(WindowInsetsAnimation animation) {
        if (this.mListenerInfo != null
                && this.mListenerInfo.mWindowInsetsAnimationCallback != null) {
            this.mListenerInfo.mWindowInsetsAnimationCallback.onPrepare(animation);
        }
    }

    public WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(
            WindowInsetsAnimation animation, WindowInsetsAnimation.Bounds bounds) {
        if (this.mListenerInfo != null
                && this.mListenerInfo.mWindowInsetsAnimationCallback != null) {
            return this.mListenerInfo.mWindowInsetsAnimationCallback.onStart(animation, bounds);
        }
        return bounds;
    }

    public WindowInsets dispatchWindowInsetsAnimationProgress(
            WindowInsets insets, List<WindowInsetsAnimation> runningAnimations) {
        if (this.mListenerInfo != null
                && this.mListenerInfo.mWindowInsetsAnimationCallback != null) {
            return this.mListenerInfo.mWindowInsetsAnimationCallback.onProgress(
                    insets, runningAnimations);
        }
        return insets;
    }

    public void dispatchWindowInsetsAnimationEnd(WindowInsetsAnimation animation) {
        if (this.mListenerInfo != null
                && this.mListenerInfo.mWindowInsetsAnimationCallback != null) {
            this.mListenerInfo.mWindowInsetsAnimationCallback.onEnd(animation);
        }
    }

    public void setSystemGestureExclusionRects(List<Rect> rects) {
        if (rects.isEmpty() && this.mListenerInfo == null) {
            return;
        }
        ListenerInfo info = getListenerInfo();
        if (info.mSystemGestureExclusionRects != null) {
            info.mSystemGestureExclusionRects.clear();
            info.mSystemGestureExclusionRects.addAll(rects);
        } else {
            info.mSystemGestureExclusionRects = new ArrayList(rects);
        }
        updatePositionUpdateListener();
        postUpdate(new View$$ExternalSyntheticLambda5(this));
    }

    private void updatePositionUpdateListener() {
        final ListenerInfo info = getListenerInfo();
        if (getSystemGestureExclusionRects().isEmpty()
                && collectPreferKeepClearRects().isEmpty()
                && collectUnrestrictedPreferKeepClearRects().isEmpty()
                && (info.mHandwritingArea == null || !shouldTrackHandwritingArea())) {
            if (info.mPositionUpdateListener != null) {
                this.mRenderNode.removePositionUpdateListener(info.mPositionUpdateListener);
                info.mPositionUpdateListener = null;
                info.mPositionChangedUpdate = null;
                return;
            }
            return;
        }
        if (info.mPositionUpdateListener == null) {
            info.mPositionChangedUpdate =
                    new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            View.this.lambda$updatePositionUpdateListener$2();
                        }
                    };
            info.mPositionUpdateListener =
                    new RenderNode.PositionUpdateListener() { // from class: android.view.View.1
                        @Override // android.graphics.RenderNode.PositionUpdateListener
                        public void positionChanged(long n, int l, int t, int r, int b) {
                            View.this.postUpdate(info.mPositionChangedUpdate);
                        }

                        @Override // android.graphics.RenderNode.PositionUpdateListener
                        public void positionLost(long frameNumber) {
                            View.this.postUpdate(info.mPositionChangedUpdate);
                        }
                    };
            this.mRenderNode.addPositionUpdateListener(info.mPositionUpdateListener);
        }
    }

    public /* synthetic */ void lambda$updatePositionUpdateListener$2() {
        updateSystemGestureExclusionRects();
        updateKeepClearRects();
        updateHandwritingArea();
    }

    public void postUpdate(Runnable r) {
        Handler h = getHandler();
        if (h != null) {
            h.postAtFrontOfQueue(r);
        }
    }

    void updateSystemGestureExclusionRects() {
        AttachInfo ai = this.mAttachInfo;
        if (ai != null) {
            ai.mViewRootImpl.updateSystemGestureExclusionRectsForView(this);
        }
    }

    public List<Rect> getSystemGestureExclusionRects() {
        List<Rect> list;
        ListenerInfo info = this.mListenerInfo;
        if (info != null && (list = info.mSystemGestureExclusionRects) != null) {
            return list;
        }
        return Collections.emptyList();
    }

    public final void setPreferKeepClear(boolean preferKeepClear) {
        getListenerInfo().mPreferKeepClear = preferKeepClear;
        updatePositionUpdateListener();
        postUpdate(new View$$ExternalSyntheticLambda6(this));
    }

    public final boolean isPreferKeepClear() {
        return this.mListenerInfo != null && this.mListenerInfo.mPreferKeepClear;
    }

    public final void setPreferKeepClearRects(List<Rect> rects) {
        ListenerInfo info = getListenerInfo();
        if (info.mKeepClearRects != null) {
            info.mKeepClearRects.clear();
            info.mKeepClearRects.addAll(rects);
        } else {
            info.mKeepClearRects = new ArrayList(rects);
        }
        updatePositionUpdateListener();
        postUpdate(new View$$ExternalSyntheticLambda6(this));
    }

    public final List<Rect> getPreferKeepClearRects() {
        ListenerInfo info = this.mListenerInfo;
        if (info != null && info.mKeepClearRects != null) {
            return new ArrayList(info.mKeepClearRects);
        }
        return Collections.emptyList();
    }

    @SystemApi
    public final void setUnrestrictedPreferKeepClearRects(List<Rect> rects) {
        ListenerInfo info = getListenerInfo();
        if (info.mUnrestrictedKeepClearRects != null) {
            info.mUnrestrictedKeepClearRects.clear();
            info.mUnrestrictedKeepClearRects.addAll(rects);
        } else {
            info.mUnrestrictedKeepClearRects = new ArrayList(rects);
        }
        updatePositionUpdateListener();
        postUpdate(new View$$ExternalSyntheticLambda6(this));
    }

    @SystemApi
    public final List<Rect> getUnrestrictedPreferKeepClearRects() {
        ListenerInfo info = this.mListenerInfo;
        if (info != null && info.mUnrestrictedKeepClearRects != null) {
            return new ArrayList(info.mUnrestrictedKeepClearRects);
        }
        return Collections.emptyList();
    }

    void updateKeepClearRects() {
        AttachInfo ai = this.mAttachInfo;
        if (ai != null) {
            ai.mViewRootImpl.updateKeepClearRectsForView(this);
        }
    }

    public List<Rect> collectPreferKeepClearRects() {
        ListenerInfo info = this.mListenerInfo;
        boolean keepClearForFocus =
                isFocused()
                        && ViewConfiguration.get(this.mContext).isPreferKeepClearForFocusEnabled();
        boolean keepBoundsClear = (info != null && info.mPreferKeepClear) || keepClearForFocus;
        boolean hasCustomKeepClearRects =
                (info == null || info.mKeepClearRects == null) ? false : true;
        if (!keepBoundsClear && !hasCustomKeepClearRects) {
            return Collections.emptyList();
        }
        if (keepBoundsClear && !hasCustomKeepClearRects) {
            return Collections.singletonList(new Rect(0, 0, getWidth(), getHeight()));
        }
        List<Rect> list = new ArrayList<>();
        if (keepBoundsClear) {
            list.add(new Rect(0, 0, getWidth(), getHeight()));
        }
        if (hasCustomKeepClearRects) {
            list.addAll(info.mKeepClearRects);
        }
        return list;
    }

    private void updatePreferKeepClearForFocus() {
        if (ViewConfiguration.get(this.mContext).isPreferKeepClearForFocusEnabled()) {
            updatePositionUpdateListener();
            post(new View$$ExternalSyntheticLambda6(this));
        }
    }

    public List<Rect> collectUnrestrictedPreferKeepClearRects() {
        ListenerInfo info = this.mListenerInfo;
        if (info != null && info.mUnrestrictedKeepClearRects != null) {
            return info.mUnrestrictedKeepClearRects;
        }
        return Collections.emptyList();
    }

    public void setHandwritingBoundsOffsets(
            float offsetLeft, float offsetTop, float offsetRight, float offsetBottom) {
        this.mHandwritingBoundsOffsetLeft = offsetLeft;
        this.mHandwritingBoundsOffsetTop = offsetTop;
        this.mHandwritingBoundsOffsetRight = offsetRight;
        this.mHandwritingBoundsOffsetBottom = offsetBottom;
    }

    public float getHandwritingBoundsOffsetLeft() {
        return this.mHandwritingBoundsOffsetLeft;
    }

    public float getHandwritingBoundsOffsetTop() {
        return this.mHandwritingBoundsOffsetTop;
    }

    public float getHandwritingBoundsOffsetRight() {
        return this.mHandwritingBoundsOffsetRight;
    }

    public float getHandwritingBoundsOffsetBottom() {
        return this.mHandwritingBoundsOffsetBottom;
    }

    public void setHandwritingArea(Rect rect) {
        ListenerInfo info = getListenerInfo();
        info.mHandwritingArea = rect;
        updatePositionUpdateListener();
        postUpdate(new View$$ExternalSyntheticLambda0(this));
    }

    public Rect getHandwritingArea() {
        ListenerInfo info = this.mListenerInfo;
        if (info != null && info.mHandwritingArea != null) {
            return new Rect(info.mHandwritingArea);
        }
        return null;
    }

    void updateHandwritingArea() {
        AttachInfo ai;
        if (shouldTrackHandwritingArea() && (ai = this.mAttachInfo) != null) {
            ai.mViewRootImpl.getHandwritingInitiator().updateHandwritingAreasForView(this);
        }
    }

    boolean shouldInitiateHandwriting() {
        return isAutoHandwritingEnabled() || getHandwritingDelegatorCallback() != null;
    }

    public boolean shouldTrackHandwritingArea() {
        return shouldInitiateHandwriting();
    }

    public void setHandwritingDelegatorCallback(Runnable callback) {
        this.mHandwritingDelegatorCallback = callback;
        if (callback != null) {
            setHandwritingArea(new Rect(0, 0, getWidth(), getHeight()));
        }
    }

    public Runnable getHandwritingDelegatorCallback() {
        return this.mHandwritingDelegatorCallback;
    }

    public void setAllowedHandwritingDelegatePackage(String allowedPackageName) {
        this.mAllowedHandwritingDelegatePackageName = allowedPackageName;
    }

    public String getAllowedHandwritingDelegatePackageName() {
        return this.mAllowedHandwritingDelegatePackageName;
    }

    public void setIsHandwritingDelegate(boolean isHandwritingDelegate) {
        this.mIsHandwritingDelegate = isHandwritingDelegate;
    }

    public boolean isHandwritingDelegate() {
        return this.mIsHandwritingDelegate;
    }

    public void setAllowedHandwritingDelegatorPackage(String allowedPackageName) {
        this.mAllowedHandwritingDelegatorPackageName = allowedPackageName;
    }

    public String getAllowedHandwritingDelegatorPackageName() {
        return this.mAllowedHandwritingDelegatorPackageName;
    }

    public void setHandwritingDelegateFlags(int flags) {
        this.mHandwritingDelegateFlags = flags;
    }

    public int getHandwritingDelegateFlags() {
        return this.mHandwritingDelegateFlags;
    }

    public void getLocationInSurface(int[] location) {
        getLocationInWindow(location);
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRootImpl != null) {
            location[0] =
                    location[0]
                            + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.left;
            location[1] =
                    location[1]
                            + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.top;
        }
    }

    public WindowInsets getRootWindowInsets() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl.getWindowInsets(false);
        }
        return null;
    }

    public WindowInsetsController getWindowInsetsController() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl.getInsetsController();
        }
        Object parent = getParent();
        if (parent instanceof View) {
            return ((View) parent).getWindowInsetsController();
        }
        if (parent instanceof ViewRootImpl) {
            return ((ViewRootImpl) parent).getInsetsController();
        }
        return null;
    }

    public final OnBackInvokedDispatcher findOnBackInvokedDispatcher() {
        ViewParent parent = getParent();
        if (parent != null) {
            return parent.findOnBackInvokedDispatcherForChild(this, this);
        }
        return null;
    }

    @Deprecated
    protected boolean computeFitSystemWindows(Rect inoutInsets, Rect outLocalInsets) {
        WindowInsets innerInsets =
                computeSystemWindowInsets(new WindowInsets(inoutInsets), outLocalInsets);
        inoutInsets.set(innerInsets.getSystemWindowInsetsAsRect());
        return innerInsets.isSystemWindowInsetsConsumed();
    }

    public WindowInsets computeSystemWindowInsets(WindowInsets in, Rect outLocalInsets) {
        boolean isOptionalFitSystemWindows =
                ((this.mViewFlags & 2048) == 0 && (this.mPrivateFlags4 & 256) == 0) ? false : true;
        if (isOptionalFitSystemWindows && this.mAttachInfo != null) {
            Window.OnContentApplyWindowInsetsListener listener =
                    this.mAttachInfo.mContentOnApplyWindowInsetsListener;
            if (listener == null) {
                outLocalInsets.setEmpty();
                return in;
            }
            Pair<Insets, WindowInsets> result = listener.onContentApplyWindowInsets(this, in);
            outLocalInsets.set(result.first.toRect());
            return result.second;
        }
        outLocalInsets.set(in.getSystemWindowInsetsAsRect());
        return in.consumeSystemWindowInsets().inset(outLocalInsets);
    }

    protected boolean hasContentOnApplyWindowInsetsListener() {
        return (this.mAttachInfo == null
                        || this.mAttachInfo.mContentOnApplyWindowInsetsListener == null)
                ? false
                : true;
    }

    public void setFitsSystemWindows(boolean fitSystemWindows) {
        setFlags(fitSystemWindows ? 2 : 0, 2);
    }

    @ViewDebug.ExportedProperty
    public boolean getFitsSystemWindows() {
        return (this.mViewFlags & 2) == 2;
    }

    public boolean fitsSystemWindows() {
        return getFitsSystemWindows();
    }

    @Deprecated
    public void requestFitSystemWindows() {
        if (this.mParent != null) {
            this.mParent.requestFitSystemWindows();
        }
    }

    public void requestApplyInsets() {
        requestFitSystemWindows();
    }

    public void makeOptionalFitsSystemWindows() {
        setFlags(2048, 2048);
    }

    public void makeFrameworkOptionalFitsSystemWindows() {
        this.mPrivateFlags4 |= 256;
    }

    public boolean isFrameworkOptionalFitsSystemWindows() {
        return (this.mPrivateFlags4 & 256) != 0;
    }

    @ViewDebug.ExportedProperty(
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "VISIBLE"),
                @ViewDebug.IntToString(from = 4, to = "INVISIBLE"),
                @ViewDebug.IntToString(from = 8, to = "GONE")
            })
    public int getVisibility() {
        return this.mViewFlags & 12;
    }

    @RemotableViewMethod
    public void setVisibility(int visibility) {
        setFlags(visibility, 12);
    }

    @ViewDebug.ExportedProperty
    public boolean isEnabled() {
        return (this.mViewFlags & 32) == 0;
    }

    @RemotableViewMethod
    public void setEnabled(boolean enabled) {
        if (enabled == isEnabled()) {
            return;
        }
        setFlags(enabled ? 0 : 32, 32);
        refreshDrawableState();
        invalidate(true);
        if (!enabled) {
            cancelPendingInputEvents();
        }
        notifyViewAccessibilityStateChangedIfNeeded(4096);
    }

    @RemotableViewMethod
    public void setFocusable(boolean z) {
        setFocusable(z ? 1 : 0);
    }

    @RemotableViewMethod
    public void setFocusable(int focusable) {
        if ((focusable & 17) == 0) {
            setFlags(0, 262144);
        }
        setFlags(focusable, 17);
    }

    @RemotableViewMethod
    public void setFocusableInTouchMode(boolean focusableInTouchMode) {
        setFlags(focusableInTouchMode ? 262144 : 0, 262144);
        if (focusableInTouchMode) {
            setFlags(1, 17);
        }
    }

    public void setAutofillHints(String... autofillHints) {
        if (autofillHints == null || autofillHints.length == 0) {
            this.mAutofillHints = null;
        } else {
            this.mAutofillHints = autofillHints;
        }
        if (Flags.sensitiveContentAppProtection() && getContentSensitivity() == 0) {
            updateSensitiveViewsCountIfNeeded(isAggregatedVisible());
        }
    }

    public void setAutofilled(boolean isAutofilled, boolean hideHighlight) {
        boolean wasChanged = isAutofilled != isAutofilled();
        if (wasChanged) {
            if (isAutofilled) {
                this.mPrivateFlags3 |= 65536;
            } else {
                this.mPrivateFlags3 &= -65537;
            }
            if (hideHighlight) {
                this.mPrivateFlags4 |= 512;
            } else {
                this.mPrivateFlags4 &= -513;
            }
            invalidate();
        }
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        setFlags(soundEffectsEnabled ? 134217728 : 0, 134217728);
    }

    @ViewDebug.ExportedProperty
    public boolean isSoundEffectsEnabled() {
        return 134217728 == (this.mViewFlags & 134217728);
    }

    public void setHapticFeedbackEnabled(boolean hapticFeedbackEnabled) {
        setFlags(hapticFeedbackEnabled ? 268435456 : 0, 268435456);
    }

    @ViewDebug.ExportedProperty
    public boolean isHapticFeedbackEnabled() {
        return 268435456 == (this.mViewFlags & 268435456);
    }

    @ViewDebug.ExportedProperty(
            category = TtmlUtils.TAG_LAYOUT,
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "LTR"),
                @ViewDebug.IntToString(from = 1, to = "RTL"),
                @ViewDebug.IntToString(from = 2, to = "INHERIT"),
                @ViewDebug.IntToString(from = 3, to = "LOCALE")
            })
    public int getRawLayoutDirection() {
        return (this.mPrivateFlags2 & 12) >> 2;
    }

    @RemotableViewMethod
    public void setLayoutDirection(int layoutDirection) {
        if (getRawLayoutDirection() != layoutDirection) {
            this.mPrivateFlags2 &= -13;
            resetRtlProperties();
            this.mPrivateFlags2 |= (layoutDirection << 2) & 12;
            resolveRtlPropertiesIfNeeded();
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(
            category = TtmlUtils.TAG_LAYOUT,
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "RESOLVED_DIRECTION_LTR"),
                @ViewDebug.IntToString(from = 1, to = "RESOLVED_DIRECTION_RTL")
            })
    public int getLayoutDirection() {
        return (this.mPrivateFlags2 & 16) == 16 ? 1 : 0;
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public boolean isLayoutRtl() {
        return getLayoutDirection() == 1;
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public boolean hasTransientState() {
        return (this.mPrivateFlags2 & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    public void setHasTransientState(boolean hasTransientState) {
        boolean oldHasTransientState = hasTransientState();
        this.mTransientStateCount =
                hasTransientState ? this.mTransientStateCount + 1 : this.mTransientStateCount - 1;
        if (this.mTransientStateCount < 0) {
            this.mTransientStateCount = 0;
            Log.e(
                    VIEW_LOG_TAG,
                    "hasTransientState decremented below 0: unmatched pair of setHasTransientState"
                        + " calls");
            return;
        }
        if ((hasTransientState && this.mTransientStateCount == 1)
                || (!hasTransientState && this.mTransientStateCount == 0)) {
            this.mPrivateFlags2 =
                    (this.mPrivateFlags2 & Integer.MAX_VALUE)
                            | (hasTransientState ? Integer.MIN_VALUE : 0);
            boolean newHasTransientState = hasTransientState();
            if (this.mParent != null && newHasTransientState != oldHasTransientState) {
                try {
                    this.mParent.childHasTransientStateChanged(this, newHasTransientState);
                } catch (AbstractMethodError e) {
                    Log.e(
                            VIEW_LOG_TAG,
                            this.mParent.getClass().getSimpleName()
                                    + " does not fully implement ViewParent",
                            e);
                }
            }
        }
    }

    public void setHasTranslationTransientState(boolean hasTranslationTransientState) {
        if (hasTranslationTransientState) {
            this.mPrivateFlags4 |= 16384;
        } else {
            this.mPrivateFlags4 &= -16385;
        }
    }

    public boolean hasTranslationTransientState() {
        return (this.mPrivateFlags4 & 16384) == 16384;
    }

    public void clearTranslationState() {
        if (this.mViewTranslationCallback != null) {
            this.mViewTranslationCallback.onClearTranslation(this);
        }
        clearViewTranslationResponse();
        if (hasTranslationTransientState()) {
            setHasTransientState(false);
            setHasTranslationTransientState(false);
        }
    }

    public boolean isAttachedToWindow() {
        return this.mAttachInfo != null;
    }

    public boolean isLaidOut() {
        return (this.mPrivateFlags3 & 4) == 4;
    }

    boolean isLayoutValid() {
        return isLaidOut() && (this.mPrivateFlags & 4096) == 0;
    }

    public void setWillNotDraw(boolean willNotDraw) {
        setFlags(willNotDraw ? 128 : 0, 128);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean willNotDraw() {
        return (this.mViewFlags & 128) == 128;
    }

    @Deprecated
    public void setWillNotCacheDrawing(boolean willNotCacheDrawing) {
        setFlags(willNotCacheDrawing ? 131072 : 0, 131072);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    @Deprecated
    public boolean willNotCacheDrawing() {
        return (this.mViewFlags & 131072) == 131072;
    }

    @ViewDebug.ExportedProperty
    public boolean isClickable() {
        return (this.mViewFlags & 16384) == 16384;
    }

    public void setClickable(boolean clickable) {
        setFlags(clickable ? 16384 : 0, 16384);
    }

    public void setAllowClickWhenDisabled(boolean clickableWhenDisabled) {
        if (clickableWhenDisabled) {
            this.mPrivateFlags4 |= 4096;
        } else {
            this.mPrivateFlags4 &= -4097;
        }
    }

    public boolean isLongClickable() {
        return (this.mViewFlags & 2097152) == 2097152;
    }

    public void setLongClickable(boolean longClickable) {
        setFlags(longClickable ? 2097152 : 0, 2097152);
    }

    public boolean isContextClickable() {
        return (this.mViewFlags & 8388608) == 8388608;
    }

    public void setContextClickable(boolean contextClickable) {
        setFlags(contextClickable ? 8388608 : 0, 8388608);
    }

    public void setPressed(boolean pressed, float x, float y) {
        if (pressed) {
            drawableHotspotChanged(x, y);
        }
        setPressed(pressed);
    }

    public void setPressed(boolean pressed) {
        boolean needsRefresh = pressed != ((this.mPrivateFlags & 16384) == 16384);
        if (pressed) {
            this.mPrivateFlags = 16384 | this.mPrivateFlags;
        } else {
            this.mPrivateFlags &= -16385;
        }
        if (needsRefresh) {
            refreshDrawableState();
        }
        dispatchSetPressed(pressed);
    }

    protected void dispatchSetPressed(boolean pressed) {}

    @ViewDebug.ExportedProperty
    public boolean isPressed() {
        return (this.mPrivateFlags & 16384) == 16384;
    }

    public boolean isAssistBlocked() {
        return (this.mPrivateFlags3 & 16384) != 0;
    }

    public void setAssistBlocked(boolean enabled) {
        if (enabled) {
            this.mPrivateFlags3 |= 16384;
        } else {
            this.mPrivateFlags3 &= -16385;
        }
    }

    public boolean isSaveEnabled() {
        return (this.mViewFlags & 65536) != 65536;
    }

    public void setSaveEnabled(boolean enabled) {
        setFlags(enabled ? 0 : 65536, 65536);
    }

    @ViewDebug.ExportedProperty
    public boolean getFilterTouchesWhenObscured() {
        return (this.mViewFlags & 1024) != 0;
    }

    public void setFilterTouchesWhenObscured(boolean enabled) {
        setFlags(enabled ? 1024 : 0, 1024);
        calculateAccessibilityDataSensitive();
    }

    public boolean isSaveFromParentEnabled() {
        return (this.mViewFlags & 536870912) != 536870912;
    }

    public void setSaveFromParentEnabled(boolean enabled) {
        setFlags(enabled ? 0 : 536870912, 536870912);
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusable() {
        return 1 == (this.mViewFlags & 1);
    }

    @ViewDebug.ExportedProperty(
            category = "focus",
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "NOT_FOCUSABLE"),
                @ViewDebug.IntToString(from = 1, to = "FOCUSABLE"),
                @ViewDebug.IntToString(from = 16, to = "FOCUSABLE_AUTO")
            })
    public int getFocusable() {
        if ((this.mViewFlags & 16) > 0) {
            return 16;
        }
        return this.mViewFlags & 1;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusableInTouchMode() {
        return 262144 == (this.mViewFlags & 262144);
    }

    public boolean isScreenReaderFocusable() {
        return (this.mPrivateFlags3 & 268435456) != 0;
    }

    public void setScreenReaderFocusable(boolean screenReaderFocusable) {
        updatePflags3AndNotifyA11yIfChanged(268435456, screenReaderFocusable);
    }

    public boolean isAccessibilityHeading() {
        return (this.mPrivateFlags3 & Integer.MIN_VALUE) != 0;
    }

    public void setAccessibilityHeading(boolean isHeading) {
        updatePflags3AndNotifyA11yIfChanged(Integer.MIN_VALUE, isHeading);
    }

    private void updatePflags3AndNotifyA11yIfChanged(int mask, boolean newValue) {
        int pflags3;
        int pflags32 = this.mPrivateFlags3;
        if (newValue) {
            pflags3 = pflags32 | mask;
        } else {
            pflags3 = pflags32 & (~mask);
        }
        if (pflags3 != this.mPrivateFlags3) {
            this.mPrivateFlags3 = pflags3;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public View focusSearch(int direction) {
        if (this.mParent != null) {
            return this.mParent.focusSearch(this, direction);
        }
        return null;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isKeyboardNavigationCluster() {
        return (this.mPrivateFlags3 & 32768) != 0;
    }

    View findKeyboardNavigationCluster() {
        if (this.mParent instanceof View) {
            View cluster = ((View) this.mParent).findKeyboardNavigationCluster();
            if (cluster != null) {
                return cluster;
            }
            if (isKeyboardNavigationCluster()) {
                return this;
            }
            return null;
        }
        return null;
    }

    public void setKeyboardNavigationCluster(boolean isCluster) {
        if (isCluster) {
            this.mPrivateFlags3 |= 32768;
        } else {
            this.mPrivateFlags3 &= -32769;
        }
    }

    public final void setFocusedInCluster() {
        setFocusedInCluster(findKeyboardNavigationCluster());
    }

    private void setFocusedInCluster(View view) {
        if (this instanceof ViewGroup) {
            ((ViewGroup) this).mFocusedInCluster = null;
        }
        if (view == this) {
            return;
        }
        View view2 = this;
        for (ViewGroup viewGroup = this.mParent;
                viewGroup instanceof ViewGroup;
                viewGroup = viewGroup.getParent()) {
            viewGroup.mFocusedInCluster = view2;
            if (viewGroup != view) {
                view2 = viewGroup;
            } else {
                return;
            }
        }
    }

    private void updateFocusedInCluster(View oldFocus, int direction) {
        if (oldFocus != null) {
            View oldCluster = oldFocus.findKeyboardNavigationCluster();
            View cluster = findKeyboardNavigationCluster();
            if (oldCluster != cluster) {
                oldFocus.setFocusedInCluster(oldCluster);
                if (!(oldFocus.mParent instanceof ViewGroup)) {
                    return;
                }
                if (direction == 2 || direction == 1) {
                    ((ViewGroup) oldFocus.mParent).clearFocusedInCluster(oldFocus);
                } else if ((oldFocus instanceof ViewGroup)
                        && ((ViewGroup) oldFocus).getDescendantFocusability() == 262144
                        && ViewRootImpl.isViewDescendantOf(this, oldFocus)) {
                    ((ViewGroup) oldFocus.mParent).clearFocusedInCluster(oldFocus);
                }
            }
        }
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusedByDefault() {
        return (this.mPrivateFlags3 & 262144) != 0;
    }

    @RemotableViewMethod
    public void setFocusedByDefault(boolean isFocusedByDefault) {
        if (isFocusedByDefault == ((this.mPrivateFlags3 & 262144) != 0)) {
            return;
        }
        if (isFocusedByDefault) {
            this.mPrivateFlags3 |= 262144;
        } else {
            this.mPrivateFlags3 &= -262145;
        }
        if (this.mParent instanceof ViewGroup) {
            if (isFocusedByDefault) {
                ((ViewGroup) this.mParent).setDefaultFocus(this);
            } else {
                ((ViewGroup) this.mParent).clearDefaultFocus(this);
            }
        }
    }

    boolean hasDefaultFocus() {
        return isFocusedByDefault();
    }

    public View keyboardNavigationClusterSearch(View currentCluster, int direction) {
        if (isKeyboardNavigationCluster()) {
            currentCluster = this;
        }
        if (isRootNamespace()) {
            return FocusFinder.getInstance()
                    .findNextKeyboardNavigationCluster(this, currentCluster, direction);
        }
        if (this.mParent != null) {
            return this.mParent.keyboardNavigationClusterSearch(currentCluster, direction);
        }
        return null;
    }

    public boolean dispatchUnhandledMove(View focused, int direction) {
        return false;
    }

    public void setDefaultFocusHighlightEnabled(boolean defaultFocusHighlightEnabled) {
        this.mDefaultFocusHighlightEnabled = defaultFocusHighlightEnabled;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean getDefaultFocusHighlightEnabled() {
        return this.mDefaultFocusHighlightEnabled;
    }

    View findUserSetNextFocus(final View root, int direction) {
        switch (direction) {
            case 1:
                if (this.mID != -1) {
                    break;
                }
                break;
            case 2:
                if (this.mNextFocusForwardId != -1) {
                    break;
                }
                break;
            case 17:
                if (this.mNextFocusLeftId != -1) {
                    break;
                }
                break;
            case 33:
                if (this.mNextFocusUpId != -1) {
                    break;
                }
                break;
            case 66:
                if (this.mNextFocusRightId != -1) {
                    break;
                }
                break;
            case 130:
                if (this.mNextFocusDownId != -1) {
                    break;
                }
                break;
        }
        return null;
    }

    public /* synthetic */ boolean lambda$findUserSetNextFocus$3(
            View rootView, View startView, View t) {
        return findViewInsideOutShouldExist(rootView, t, t.mNextFocusForwardId) == startView;
    }

    View findUserSetNextKeyboardNavigationCluster(View root, int direction) {
        switch (direction) {
            case 1:
                if (this.mID != -1) {
                    final int id = this.mID;
                    break;
                }
                break;
            case 2:
                if (this.mNextClusterForwardId != -1) {
                    break;
                }
                break;
        }
        return null;
    }

    static /* synthetic */ boolean lambda$findUserSetNextKeyboardNavigationCluster$4(
            int id, View t) {
        return t.mNextClusterForwardId == id;
    }

    private View findViewInsideOutShouldExist(View root, int id) {
        return findViewInsideOutShouldExist(root, this, id);
    }

    private View findViewInsideOutShouldExist(View root, View start, int id) {
        if (this.mMatchIdPredicate == null) {
            this.mMatchIdPredicate = new MatchIdPredicate();
        }
        this.mMatchIdPredicate.mId = id;
        View result = root.findViewByPredicateInsideOut(start, this.mMatchIdPredicate);
        if (result == null) {
            Log.w(VIEW_LOG_TAG, "couldn't find view with id " + id);
        }
        return result;
    }

    public ArrayList<View> getFocusables(int direction) {
        ArrayList<View> result = new ArrayList<>(24);
        addFocusables(result, direction);
        return result;
    }

    public void addFocusables(ArrayList<View> arrayList, int i) {
        addFocusables(arrayList, i, isInTouchMode() ? 1 : 0);
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        if (views == null || !canTakeFocus()) {
            return;
        }
        if ((focusableMode & 1) == 1 && !isFocusableInTouchMode()) {
            return;
        }
        views.add(this);
    }

    public void addKeyboardNavigationClusters(Collection<View> views, int direction) {
        if (!isKeyboardNavigationCluster() || !hasFocusable()) {
            return;
        }
        views.add(this);
    }

    public void findViewsWithText(ArrayList<View> outViews, CharSequence searched, int flags) {
        if (getAccessibilityNodeProvider() != null) {
            if ((flags & 4) != 0) {
                outViews.add(this);
            }
        } else if ((flags & 2) != 0
                && searched != null
                && searched.length() > 0
                && this.mContentDescription != null
                && this.mContentDescription.length() > 0) {
            String searchedLowerCase = searched.toString().toLowerCase();
            String contentDescriptionLowerCase = this.mContentDescription.toString().toLowerCase();
            if (contentDescriptionLowerCase.contains(searchedLowerCase)) {
                outViews.add(this);
            }
        }
    }

    public ArrayList<View> getTouchables() {
        ArrayList<View> result = new ArrayList<>();
        addTouchables(result);
        return result;
    }

    public void addTouchables(ArrayList<View> views) {
        int viewFlags = this.mViewFlags;
        if (((viewFlags & 16384) == 16384
                        || (viewFlags & 2097152) == 2097152
                        || (viewFlags & 8388608) == 8388608)
                && (viewFlags & 32) == 0) {
            views.add(this);
        }
    }

    public boolean isAccessibilityFocused() {
        return (this.mPrivateFlags2 & 67108864) != 0;
    }

    public boolean requestAccessibilityFocus() {
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mContext);
        if (!manager.isEnabled()
                || !manager.isTouchExplorationEnabled()
                || (this.mViewFlags & 12) != 0
                || (this.mPrivateFlags2 & 67108864) != 0) {
            return false;
        }
        this.mPrivateFlags2 |= 67108864;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.setAccessibilityFocus(this, null);
        }
        invalidate();
        sendAccessibilityEvent(32768);
        return true;
    }

    public boolean semRequestAccessibilityFocus() {
        return requestAccessibilityFocus();
    }

    public void clearAccessibilityFocus() {
        View focusHost;
        clearAccessibilityFocusNoCallbacks(0);
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null
                && (focusHost = viewRootImpl.getAccessibilityFocusedHost()) != null
                && ViewRootImpl.isViewDescendantOf(focusHost, this)) {
            viewRootImpl.setAccessibilityFocus(null, null);
        }
    }

    public void semClearAccessibilityFocus() {
        clearAccessibilityFocus();
    }

    private void sendAccessibilityHoverEvent(int eventType) {
        View source = this;
        while (!source.includeForAccessibility(false)) {
            Object parent = source.getParent();
            if (parent instanceof View) {
                source = (View) parent;
            } else {
                return;
            }
        }
        source.sendAccessibilityEvent(eventType);
    }

    void clearAccessibilityFocusNoCallbacks(int action) {
        if ((this.mPrivateFlags2 & 67108864) != 0) {
            this.mPrivateFlags2 &= -67108865;
            invalidate();
            if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
                AccessibilityEvent event = AccessibilityEvent.obtain(65536);
                event.setAction(action);
                if (this.mAccessibilityDelegate != null) {
                    this.mAccessibilityDelegate.sendAccessibilityEventUnchecked(this, event);
                } else {
                    sendAccessibilityEventUnchecked(event);
                }
            }
            updatePreferKeepClearForFocus();
        }
    }

    public final boolean requestFocus() {
        return requestFocus(130);
    }

    public boolean restoreFocusInCluster(int direction) {
        if (restoreDefaultFocus()) {
            return true;
        }
        return requestFocus(direction);
    }

    public boolean restoreFocusNotInCluster() {
        return requestFocus(130);
    }

    public boolean restoreDefaultFocus() {
        return requestFocus(130);
    }

    public final boolean requestFocus(int direction) {
        return requestFocus(direction, null);
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return requestFocusNoSearch(direction, previouslyFocusedRect);
    }

    private boolean requestFocusNoSearch(int direction, Rect previouslyFocusedRect) {
        if (!canTakeFocus()) {
            return false;
        }
        if ((isInTouchMode() && 262144 != (this.mViewFlags & 262144))
                || hasAncestorThatBlocksDescendantFocus()) {
            return false;
        }
        if (!isLayoutValid()) {
            this.mPrivateFlags |= 1;
        } else {
            clearParentsWantFocus();
        }
        handleFocusGainInternal(direction, previouslyFocusedRect);
        return true;
    }

    void clearParentsWantFocus() {
        if (this.mParent instanceof View) {
            ((View) this.mParent).mPrivateFlags &= -2;
            ((View) this.mParent).clearParentsWantFocus();
        }
    }

    public final boolean requestFocusFromTouch() {
        ViewRootImpl viewRoot;
        if (isInTouchMode() && (viewRoot = getViewRootImpl()) != null) {
            viewRoot.ensureTouchMode(false);
        }
        return requestFocus(130);
    }

    private boolean hasAncestorThatBlocksDescendantFocus() {
        boolean focusableInTouchMode = isFocusableInTouchMode();
        ViewParent ancestor = this.mParent;
        while (ancestor instanceof ViewGroup) {
            ViewGroup vgAncestor = (ViewGroup) ancestor;
            if (vgAncestor.getDescendantFocusability() != 393216) {
                if (!focusableInTouchMode && vgAncestor.shouldBlockFocusForTouchscreen()) {
                    return true;
                }
                ancestor = vgAncestor.getParent();
            } else {
                return true;
            }
        }
        return false;
    }

    @ViewDebug.ExportedProperty(
            category = Context.ACCESSIBILITY_SERVICE,
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "auto"),
                @ViewDebug.IntToString(from = 1, to = "yes"),
                @ViewDebug.IntToString(from = 2, to = "no"),
                @ViewDebug.IntToString(from = 4, to = "noHideDescendants")
            })
    public int getImportantForAccessibility() {
        return (this.mPrivateFlags2 & PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK) >> 20;
    }

    public void setAccessibilityLiveRegion(int mode) {
        if (mode != getAccessibilityLiveRegion()) {
            this.mPrivateFlags2 &= -25165825;
            this.mPrivateFlags2 |= (mode << 23) & 25165824;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public int getAccessibilityLiveRegion() {
        return (this.mPrivateFlags2 & 25165824) >> 23;
    }

    public void setImportantForAccessibility(int mode) {
        View focusHost;
        int oldMode = getImportantForAccessibility();
        if (mode != oldMode) {
            boolean hideDescendants = mode == 4;
            if ((mode == 2 || hideDescendants)
                    && (focusHost = findAccessibilityFocusHost(hideDescendants)) != null) {
                focusHost.clearAccessibilityFocus();
            }
            boolean maySkipNotify = oldMode == 0 || mode == 0;
            boolean oldIncludeForAccessibility = maySkipNotify && includeForAccessibility(false);
            this.mPrivateFlags2 &= -7340033;
            this.mPrivateFlags2 |= (mode << 20) & PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK;
            if (!maySkipNotify || oldIncludeForAccessibility != includeForAccessibility(false)) {
                notifySubtreeAccessibilityStateChangedIfNeeded();
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    private View findAccessibilityFocusHost(boolean searchDescendants) {
        ViewRootImpl viewRoot;
        View focusHost;
        if (isAccessibilityFocusedViewOrHost()) {
            return this;
        }
        if (searchDescendants
                && (viewRoot = getViewRootImpl()) != null
                && (focusHost = viewRoot.getAccessibilityFocusedHost()) != null
                && ViewRootImpl.isViewDescendantOf(focusHost, this)) {
            return focusHost;
        }
        return null;
    }

    public boolean isImportantForAccessibility() {
        int mode = getImportantForAccessibility();
        if (mode == 2 || mode == 4) {
            return false;
        }
        for (ViewParent parent = this.mParent;
                parent instanceof View;
                parent = parent.getParent()) {
            if (((View) parent).getImportantForAccessibility() == 4) {
                return false;
            }
        }
        return mode == 1
                || isActionableForAccessibility()
                || hasListenersForAccessibility()
                || getAccessibilityNodeProvider() != null
                || getAccessibilityDelegate() != null
                || getAccessibilityLiveRegion() != 0
                || isAccessibilityPane()
                || isAccessibilityHeading();
    }

    public ViewParent getParentForAccessibility() {
        if (this.mParent instanceof View) {
            View parentView = (View) this.mParent;
            if (parentView.includeForAccessibility()) {
                return this.mParent;
            }
            return this.mParent.getParentForAccessibility();
        }
        return null;
    }

    View getSelfOrParentImportantForA11y() {
        if (isImportantForAccessibility()) {
            return this;
        }
        Object parentForAccessibility = getParentForAccessibility();
        if (parentForAccessibility instanceof View) {
            return (View) parentForAccessibility;
        }
        return null;
    }

    public void addChildrenForAccessibility(ArrayList<View> outChildren) {}

    public boolean includeForAccessibility() {
        return includeForAccessibility(true);
    }

    public boolean includeForAccessibility(boolean forNodeTree) {
        if (this.mAttachInfo == null) {
            return false;
        }
        if (forNodeTree
                && !AccessibilityManager.getInstance(this.mContext).isRequestFromAccessibilityTool()
                && isAccessibilityDataSensitive()) {
            return false;
        }
        return (this.mAttachInfo.mAccessibilityFetchFlags & 128) != 0
                || isImportantForAccessibility();
    }

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE)
    public boolean isAccessibilityDataSensitive() {
        if (this.mInferredAccessibilityDataSensitive == 0) {
            calculateAccessibilityDataSensitive();
        }
        return this.mInferredAccessibilityDataSensitive == 1;
    }

    void calculateAccessibilityDataSensitive() {
        if (this.mExplicitAccessibilityDataSensitive != 0) {
            this.mInferredAccessibilityDataSensitive = this.mExplicitAccessibilityDataSensitive;
            return;
        }
        if (getFilterTouchesWhenObscured()) {
            this.mInferredAccessibilityDataSensitive = 1;
        } else if ((this.mParent instanceof View)
                && ((View) this.mParent).isAccessibilityDataSensitive()) {
            this.mInferredAccessibilityDataSensitive = 1;
        } else {
            this.mInferredAccessibilityDataSensitive = 2;
        }
    }

    public void setAccessibilityDataSensitive(int accessibilityDataSensitive) {
        this.mExplicitAccessibilityDataSensitive = accessibilityDataSensitive;
        calculateAccessibilityDataSensitive();
    }

    public boolean isActionableForAccessibility() {
        return isClickable()
                || isLongClickable()
                || isFocusable()
                || isContextClickable()
                || isScreenReaderFocusable();
    }

    private boolean hasListenersForAccessibility() {
        ListenerInfo info = getListenerInfo();
        return (this.mTouchDelegate == null
                        && info.mOnKeyListener == null
                        && info.mOnTouchListener == null
                        && info.mOnGenericMotionListener == null
                        && info.mOnHoverListener == null
                        && info.mOnDragListener == null)
                ? false
                : true;
    }

    public void notifyViewAccessibilityStateChangedIfNeeded(int changeType) {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled()
                || this.mAttachInfo == null) {
            return;
        }
        if (this.mAttachInfo != null
                && this.mAttachInfo.mViewRootImpl.mThread != Thread.currentThread()) {
            return;
        }
        if (changeType != 1
                && ((isAccessibilityPane() || (changeType == 32 && isAggregatedVisible()))
                        && (isAggregatedVisible() || changeType == 32))) {
            AccessibilityEvent event = AccessibilityEvent.obtain();
            onInitializeAccessibilityEvent(event);
            event.setEventType(32);
            event.setContentChangeTypes(changeType);
            event.setSource(this);
            onPopulateAccessibilityEvent(event);
            if (this.mParent != null) {
                try {
                    this.mParent.requestSendAccessibilityEvent(this, event);
                    return;
                } catch (AbstractMethodError e) {
                    Log.e(
                            VIEW_LOG_TAG,
                            this.mParent.getClass().getSimpleName()
                                    + " does not fully implement ViewParent",
                            e);
                    return;
                }
            }
            return;
        }
        if (getAccessibilityLiveRegion() != 0) {
            AccessibilityEvent event2 = AccessibilityEvent.obtain();
            event2.setEventType(2048);
            event2.setContentChangeTypes(changeType);
            sendAccessibilityEventUnchecked(event2);
            return;
        }
        if (this.mParent != null) {
            try {
                this.mParent.notifySubtreeAccessibilityStateChanged(this, this, changeType);
            } catch (AbstractMethodError e2) {
                Log.e(
                        VIEW_LOG_TAG,
                        this.mParent.getClass().getSimpleName()
                                + " does not fully implement ViewParent",
                        e2);
            }
        }
    }

    private void hidden_notifyViewAccessibilityStateChangedIfNeeded(int changeType) {
        notifyViewAccessibilityStateChangedIfNeeded(changeType);
    }

    public void notifySubtreeAccessibilityStateChangedIfNeeded() {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()
                && this.mAttachInfo != null
                && (this.mPrivateFlags2 & 134217728) == 0) {
            this.mPrivateFlags2 |= 134217728;
            if (this.mParent != null) {
                try {
                    this.mParent.notifySubtreeAccessibilityStateChanged(this, this, 1);
                } catch (AbstractMethodError e) {
                    Log.e(
                            VIEW_LOG_TAG,
                            this.mParent.getClass().getSimpleName()
                                    + " does not fully implement ViewParent",
                            e);
                }
            }
        }
    }

    private void notifySubtreeAccessibilityStateChangedByParentIfNeeded() {
        View sendA11yEventView;
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()
                && (sendA11yEventView = (View) getParentForAccessibility()) != null
                && sendA11yEventView.isShown()) {
            sendA11yEventView.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTransitionVisibility(int visibility) {
        this.mViewFlags = (this.mViewFlags & (-13)) | visibility;
    }

    void resetSubtreeAccessibilityStateChanged() {
        this.mPrivateFlags2 &= -134217729;
    }

    public boolean dispatchNestedPrePerformAccessibilityAction(int action, Bundle arguments) {
        for (ViewParent p = getParent(); p != null; p = p.getParent()) {
            if (p.onNestedPrePerformAccessibilityAction(this, action, arguments)) {
                return true;
            }
        }
        return false;
    }

    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.performAccessibilityAction(this, action, arguments);
        }
        return performAccessibilityActionInternal(action, arguments);
    }

    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        int start;
        if (isNestedScrollingEnabled()
                && ((action == 8192
                                || action == 4096
                                || action == 16908344
                                || action == 16908345
                                || action == 16908346
                                || action == 16908347)
                        && dispatchNestedPrePerformAccessibilityAction(action, arguments))) {
            return true;
        }
        switch (action) {
            case 1:
                if (!hasFocus()) {
                    if (this.mAttachInfo != null) {
                        getViewRootImpl().ensureTouchMode(false);
                    }
                    return requestFocus();
                }
                return false;
            case 2:
                if (hasFocus()) {
                    clearFocus();
                    return !isFocused();
                }
                return false;
            case 4:
                if (!isSelected()) {
                    setSelected(true);
                    return isSelected();
                }
                return false;
            case 8:
                if (isSelected()) {
                    setSelected(false);
                    return !isSelected();
                }
                return false;
            case 16:
                if (isClickable()) {
                    performClickInternal();
                    return true;
                }
                return false;
            case 32:
                if (isLongClickable()) {
                    performLongClick();
                    return true;
                }
                return false;
            case 64:
                if (!isAccessibilityFocused()) {
                    return requestAccessibilityFocus();
                }
                return false;
            case 128:
                boolean extendSelection = isAccessibilityFocused();
                if (extendSelection) {
                    clearAccessibilityFocus();
                    return true;
                }
                return false;
            case 256:
                if (arguments != null) {
                    int granularity =
                            arguments.getInt(
                                    AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT);
                    boolean extendSelection2 =
                            arguments.getBoolean(
                                    AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN);
                    return traverseAtGranularity(granularity, true, extendSelection2);
                }
                return false;
            case 512:
                if (arguments != null) {
                    int granularity2 =
                            arguments.getInt(
                                    AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT);
                    boolean extendSelection3 =
                            arguments.getBoolean(
                                    AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN);
                    return traverseAtGranularity(granularity2, false, extendSelection3);
                }
                return false;
            case 131072:
                CharSequence text = getIterableTextForAccessibility();
                if (text == null) {
                    return false;
                }
                if (arguments != null) {
                    start =
                            arguments.getInt(
                                    AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, -1);
                } else {
                    start = -1;
                }
                int end =
                        arguments != null
                                ? arguments.getInt(
                                        AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, -1)
                                : -1;
                if ((getAccessibilitySelectionStart() != start
                                || getAccessibilitySelectionEnd() != end)
                        && start == end) {
                    setAccessibilitySelection(start, end);
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                    return true;
                }
                return false;
            case 16908342:
                if (this.mAttachInfo != null) {
                    Rect r = this.mAttachInfo.mTmpInvalRect;
                    getDrawingRect(r);
                    return requestRectangleOnScreen(r, true);
                }
                return false;
            case 16908348:
                if (isContextClickable()) {
                    performContextClick();
                    return true;
                }
                return false;
            case 16908356:
                if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
                    return showLongClickTooltip(0, 0);
                }
                return false;
            case 16908357:
                if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
                    return false;
                }
                hideTooltip();
                return true;
            case 16908374:
                if (!canAcceptAccessibilityDrop()) {
                    return false;
                }
                try {
                    if (this.mAttachInfo != null && this.mAttachInfo.mSession != null) {
                        int[] location = new int[2];
                        getLocationInWindow(location);
                        int centerX = location[0] + (getWidth() / 2);
                        int centerY = location[1] + (getHeight() / 2);
                        return this.mAttachInfo.mSession.dropForAccessibility(
                                this.mAttachInfo.mWindow, centerX, centerY);
                    }
                } catch (RemoteException e) {
                    Log.e(VIEW_LOG_TAG, "Unable to drop for accessibility", e);
                }
                return false;
            case 16908375:
                if (!startedSystemDragForAccessibility()
                        || this.mAttachInfo == null
                        || this.mAttachInfo.mDragToken == null) {
                    return false;
                }
                cancelDragAndDrop();
                return true;
            default:
                return false;
        }
    }

    private boolean canAcceptAccessibilityDrop() {
        ListenerInfo li;
        if (canAcceptDrag() && (li = this.mListenerInfo) != null) {
            return (li.mOnDragListener == null && li.mOnReceiveContentListener == null)
                    ? false
                    : true;
        }
        return false;
    }

    private boolean traverseAtGranularity(
            int granularity, boolean forward, boolean extendSelection) {
        AccessibilityIterators.TextSegmentIterator iterator;
        int selectionStart;
        int selectionEnd;
        CharSequence text = getIterableTextForAccessibility();
        if (text == null
                || text.length() == 0
                || (iterator = getIteratorForGranularity(granularity)) == null) {
            return false;
        }
        int current = getAccessibilitySelectionEnd();
        if (current == -1) {
            current = forward ? 0 : text.length();
        }
        int[] range = forward ? iterator.following(current) : iterator.preceding(current);
        if (range == null) {
            return false;
        }
        int segmentStart = range[0];
        int segmentEnd = range[1];
        if (extendSelection && isAccessibilitySelectionExtendable()) {
            prepareForExtendedAccessibilitySelection();
            selectionStart = getAccessibilitySelectionStart();
            if (selectionStart == -1) {
                selectionStart = forward ? segmentStart : segmentEnd;
            }
            selectionEnd = forward ? segmentEnd : segmentStart;
        } else {
            selectionStart = forward ? segmentEnd : segmentStart;
            selectionEnd = selectionStart;
        }
        setAccessibilitySelection(selectionStart, selectionEnd);
        int action = forward ? 256 : 512;
        sendViewTextTraversedAtGranularityEvent(action, granularity, segmentStart, segmentEnd);
        return true;
    }

    public CharSequence getIterableTextForAccessibility() {
        return getContentDescription();
    }

    public boolean isAccessibilitySelectionExtendable() {
        return false;
    }

    public void prepareForExtendedAccessibilitySelection() {}

    public int getAccessibilitySelectionStart() {
        return this.mAccessibilityCursorPosition;
    }

    public int getAccessibilitySelectionEnd() {
        return getAccessibilitySelectionStart();
    }

    public void setAccessibilitySelection(int start, int end) {
        if (start == end && end == this.mAccessibilityCursorPosition) {
            return;
        }
        if (start >= 0 && start == end && end <= getIterableTextForAccessibility().length()) {
            this.mAccessibilityCursorPosition = start;
        } else {
            this.mAccessibilityCursorPosition = -1;
        }
        sendAccessibilityEvent(8192);
    }

    private void sendViewTextTraversedAtGranularityEvent(
            int action, int granularity, int fromIndex, int toIndex) {
        if (this.mParent == null) {
            return;
        }
        AccessibilityEvent event = AccessibilityEvent.obtain(131072);
        onInitializeAccessibilityEvent(event);
        onPopulateAccessibilityEvent(event);
        event.setFromIndex(fromIndex);
        event.setToIndex(toIndex);
        event.setAction(action);
        event.setMovementGranularity(granularity);
        this.mParent.requestSendAccessibilityEvent(this, event);
    }

    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int granularity) {
        switch (granularity) {
            case 1:
                CharSequence text = getIterableTextForAccessibility();
                if (text != null && text.length() > 0) {
                    AccessibilityIterators.CharacterTextSegmentIterator iterator =
                            AccessibilityIterators.CharacterTextSegmentIterator.getInstance(
                                    this.mContext.getResources().getConfiguration().locale);
                    iterator.initialize(text.toString());
                    return iterator;
                }
                return null;
            case 2:
                CharSequence text2 = getIterableTextForAccessibility();
                if (text2 != null && text2.length() > 0) {
                    AccessibilityIterators.WordTextSegmentIterator iterator2 =
                            AccessibilityIterators.WordTextSegmentIterator.getInstance(
                                    this.mContext.getResources().getConfiguration().locale);
                    iterator2.initialize(text2.toString());
                    return iterator2;
                }
                return null;
            case 8:
                CharSequence text3 = getIterableTextForAccessibility();
                if (text3 != null && text3.length() > 0) {
                    AccessibilityIterators.ParagraphTextSegmentIterator iterator3 =
                            AccessibilityIterators.ParagraphTextSegmentIterator.getInstance();
                    iterator3.initialize(text3.toString());
                    return iterator3;
                }
                return null;
            default:
                return null;
        }
    }

    public final boolean isTemporarilyDetached() {
        return (this.mPrivateFlags3 & 33554432) != 0;
    }

    public void dispatchStartTemporaryDetach() {
        this.mPrivateFlags3 |= 33554432;
        notifyEnterOrExitForAutoFillIfNeeded(false);
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(false);
        onStartTemporaryDetach();
    }

    public void onStartTemporaryDetach() {
        removeUnsetPressCallback();
        this.mPrivateFlags |= 67108864;
    }

    public void dispatchFinishTemporaryDetach() {
        this.mPrivateFlags3 &= -33554433;
        onFinishTemporaryDetach();
        if (hasWindowFocus() && hasFocus()) {
            notifyFocusChangeToImeFocusController(true);
        }
        notifyEnterOrExitForAutoFillIfNeeded(true);
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
    }

    public void onFinishTemporaryDetach() {}

    public KeyEvent.DispatcherState getKeyDispatcherState() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mKeyDispatchState;
        }
        return null;
    }

    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        return onKeyPreIme(event.getKeyCode(), event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onKeyEvent(event, 0);
        }
        ListenerInfo li = this.mListenerInfo;
        if (li != null
                && li.mOnKeyListener != null
                && (this.mViewFlags & 32) == 0
                && li.mOnKeyListener.onKey(this, event.getKeyCode(), event)) {
            return true;
        }
        if (event.dispatch(
                this, this.mAttachInfo != null ? this.mAttachInfo.mKeyDispatchState : null, this)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
        }
        return false;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return onKeyShortcut(event.getKeyCode(), event);
    }

    protected boolean semIsShowingScrollbar() {
        return (this.mScrollCache == null || this.mScrollCache.state == 0) ? false : true;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.isTargetAccessibilityFocus()) {
            if (!isAccessibilityFocusedViewOrHost()) {
                return false;
            }
            event.setTargetAccessibilityFocus(false);
        }
        boolean result = false;
        if (ViewRune.WIDGET_PEN_SUPPORTED && !this.mDisablePenGestureforfactorytest) {
            boolean isPenEvent = false;
            int action = event.getAction();
            if (event.getToolType(0) == 2 && (event.getButtonState() & 32) != 0) {
                switch (action) {
                    case 0:
                        this.isPenSideButton = true;
                        isPenEvent = true;
                        break;
                    case 1:
                    case 3:
                        if (this.isPenSideButton) {
                            this.isPenSideButton = false;
                            isPenEvent = true;
                            break;
                        }
                        break;
                    case 2:
                        if (this.isPenSideButton) {
                            isPenEvent = true;
                            break;
                        }
                        break;
                    case 211:
                    case 212:
                    case 213:
                    case 214:
                        isPenEvent = true;
                        break;
                }
            } else {
                switch (action) {
                    case 0:
                    case 1:
                    case 3:
                        this.isPenSideButton = false;
                        break;
                    case 2:
                        if (this.isPenSideButton) {
                            isPenEvent = true;
                            break;
                        }
                        break;
                    case 212:
                    case 213:
                        isPenEvent = true;
                        break;
                }
            }
            if (isPenEvent && this.mAttachInfo != null) {
                this.mAttachInfo.mTreeObserver.dispatchOnPenButtonEventListener(event);
            }
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(event, 0);
        }
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0) {
            stopNestedScroll();
        }
        if (onFilterTouchEventForSecurity(event)) {
            result = performOnTouchCallback(event);
        }
        if (!result && this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
        }
        if (actionMasked == 1 || actionMasked == 3 || (actionMasked == 0 && !result)) {
            stopNestedScroll();
        }
        return result;
    }

    private boolean performOnTouchCallback(MotionEvent event) {
        boolean handled = false;
        if ((this.mViewFlags & 32) == 0 && handleScrollBarDragging(event)) {
            handled = true;
        }
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnTouchListener != null && (this.mViewFlags & 32) == 0) {
            try {
                Trace.traceBegin(8L, "View.onTouchListener#onTouch");
                handled = li.mOnTouchListener.onTouch(this, event);
            } finally {
            }
        }
        if (!handled) {
            try {
                Trace.traceBegin(8L, "View#onTouchEvent");
                return onTouchEvent(event);
            } finally {
            }
        }
        return true;
    }

    boolean isAccessibilityFocusedViewOrHost() {
        return isAccessibilityFocused()
                || (getViewRootImpl() != null
                        && getViewRootImpl().getAccessibilityFocusedHost() == this);
    }

    protected boolean canReceivePointerEvents() {
        return (this.mViewFlags & 12) == 0 || getAnimation() != null;
    }

    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        return (this.mViewFlags & 1024) == 0 || (event.getFlags() & 1) == 0;
    }

    public boolean dispatchTrackballEvent(MotionEvent event) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTrackballEvent(event, 0);
        }
        return onTrackballEvent(event);
    }

    public boolean dispatchCapturedPointerEvent(MotionEvent event) {
        if (!hasPointerCapture()) {
            return false;
        }
        ListenerInfo li = this.mListenerInfo;
        if (li != null
                && li.mOnCapturedPointerListener != null
                && li.mOnCapturedPointerListener.onCapturedPointer(this, event)) {
            return true;
        }
        return onCapturedPointerEvent(event);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onGenericMotionEvent(event, 0);
        }
        int source = event.getSource();
        if ((source & 2) != 0) {
            int action = event.getAction();
            if (action == 9 || action == 7 || action == 10) {
                if (dispatchHoverEvent(event)) {
                    return true;
                }
            } else if (dispatchGenericPointerEvent(event)) {
                return true;
            }
        } else if (dispatchGenericFocusedEvent(event)) {
            return true;
        }
        if (dispatchGenericMotionEventInternal(event)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
        }
        return false;
    }

    private boolean dispatchGenericMotionEventInternal(MotionEvent event) {
        boolean isRotaryEncoderEvent = event.isFromSource(4194304);
        if (isRotaryEncoderEvent && (this.mPrivateFlags4 & 1048576) == 0) {
            if (ViewConfiguration.get(this.mContext)
                    .isViewBasedRotaryEncoderHapticScrollFeedbackEnabled()) {
                this.mPrivateFlags4 |= 2097152;
            }
            this.mPrivateFlags4 |= 1048576;
        }
        boolean processForRotaryScrollHaptics =
                isRotaryEncoderEvent && (2097152 & this.mPrivateFlags4) != 0;
        if (processForRotaryScrollHaptics) {
            this.mPrivateFlags4 &= -4194305;
            this.mPrivateFlags4 |= 8388608;
        }
        ListenerInfo li = this.mListenerInfo;
        if (li != null
                && li.mOnGenericMotionListener != null
                && (this.mViewFlags & 32) == 0
                && li.mOnGenericMotionListener.onGenericMotion(this, event)) {
            return true;
        }
        boolean onGenericMotionEventResult = onGenericMotionEvent(event);
        if (processForRotaryScrollHaptics) {
            if ((4194304 & this.mPrivateFlags4) != 0) {
                doRotaryProgressForScrollHaptics(event);
            } else {
                doRotaryLimitForScrollHaptics(event);
            }
        }
        if (onGenericMotionEventResult) {
            return true;
        }
        int actionButton = event.getActionButton();
        switch (event.getActionMasked()) {
            case 11:
                if (isContextClickable()
                        && !this.mInContextButtonPress
                        && !this.mHasPerformedLongPress
                        && ((actionButton == 32 || actionButton == 2)
                                && performContextClick(event.getX(), event.getY()))) {
                    this.mInContextButtonPress = true;
                    setPressed(true, event.getX(), event.getY());
                    removeTapCallback();
                    removeLongPressCallback();
                    return true;
                }
                break;
            case 12:
                if (this.mInContextButtonPress && (actionButton == 32 || actionButton == 2)) {
                    this.mInContextButtonPress = false;
                    this.mIgnoreNextUpEvent = true;
                    break;
                }
                break;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
        }
        return false;
    }

    protected boolean dispatchHoverEvent(MotionEvent event) {
        ListenerInfo li = this.mListenerInfo;
        if (li != null
                && li.mOnHoverListener != null
                && (this.mViewFlags & 32) == 0
                && li.mOnHoverListener.onHover(this, event)) {
            return true;
        }
        return onHoverEvent(event);
    }

    protected boolean hasHoveredChild() {
        return false;
    }

    protected boolean pointInHoveredChild(MotionEvent event) {
        return false;
    }

    protected boolean dispatchGenericPointerEvent(MotionEvent event) {
        return false;
    }

    protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
        return false;
    }

    public final boolean dispatchPointerEvent(MotionEvent event) {
        if (event.isTouchEvent()) {
            return dispatchTouchEvent(event);
        }
        return dispatchGenericMotionEvent(event);
    }

    public void dispatchWindowFocusChanged(boolean hasFocus) {
        onWindowFocusChanged(hasFocus);
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            if (isPressed()) {
                setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            if ((this.mPrivateFlags & 2) != 0) {
                notifyFocusChangeToImeFocusController(false);
            }
            removeLongPressCallback();
            removeTapCallback();
            onFocusLost();
            if (CoreRune.FW_SPEN_HOVER) {
                if ((this.mPrivateFlags & 268435456) != 0) {
                    this.mPrivateFlags &= -268435457;
                }
                if ((1 & this.mSemViewFlags) != 0) {
                    this.mSemViewFlags &= -2;
                }
            }
            if (this.mHoverPopup != null) {
                this.mHoverPopup.dismiss();
            }
        } else if ((this.mPrivateFlags & 2) != 0) {
            notifyFocusChangeToImeFocusController(true);
            ViewRootImpl viewRoot = getViewRootImpl();
            if (viewRoot != null
                    && android.view.inputmethod.Flags.initiationWithoutInputConnection()
                    && onCheckIsTextEditor()) {
                viewRoot.getHandwritingInitiator().onEditorFocused(this);
            }
        }
        refreshDrawableState();
    }

    public boolean hasWindowFocus() {
        return this.mAttachInfo != null && this.mAttachInfo.mHasWindowFocus;
    }

    public boolean hasImeFocus() {
        return getViewRootImpl() != null && getViewRootImpl().getImeFocusController().hasImeFocus();
    }

    protected void dispatchVisibilityChanged(View changedView, int visibility) {
        onVisibilityChanged(changedView, visibility);
    }

    protected void onVisibilityChanged(View changedView, int visibility) {}

    public void dispatchDisplayHint(int hint) {
        onDisplayHint(hint);
    }

    protected void onDisplayHint(int hint) {}

    public void dispatchWindowVisibilityChanged(int visibility) {
        onWindowVisibilityChanged(visibility);
    }

    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == 0) {
            initialAwakenScrollBars();
        }
    }

    public boolean isAggregatedVisible() {
        return (this.mPrivateFlags3 & 536870912) != 0;
    }

    boolean dispatchVisibilityAggregated(boolean isVisible) {
        boolean thisVisible = getVisibility() == 0;
        if (thisVisible || !isVisible) {
            onVisibilityAggregated(isVisible);
        }
        return thisVisible && isVisible;
    }

    public void onVisibilityAggregated(boolean isVisible) {
        int i;
        boolean oldVisible = isAggregatedVisible();
        this.mPrivateFlags3 =
                isVisible ? this.mPrivateFlags3 | 536870912 : this.mPrivateFlags3 & (-536870913);
        if (isVisible && this.mAttachInfo != null) {
            initialAwakenScrollBars();
        }
        Drawable dr = this.mBackground;
        if (dr != null && isVisible != dr.isVisible()) {
            dr.setVisible(isVisible, false);
        }
        Drawable hl = this.mDefaultFocusHighlight;
        if (hl != null && isVisible != hl.isVisible()) {
            hl.setVisible(isVisible, false);
        }
        Drawable fg = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (fg != null && isVisible != fg.isVisible()) {
            fg.setVisible(isVisible, false);
        }
        notifyAutofillManagerViewVisibilityChanged(isVisible);
        if (isVisible != oldVisible) {
            if (isAccessibilityPane()) {
                if (isVisible) {
                    i = 16;
                } else {
                    i = 32;
                }
                notifyViewAccessibilityStateChangedIfNeeded(i);
            }
            notifyAppearedOrDisappearedForContentCaptureIfNeeded(isVisible);
            updateSensitiveViewsCountIfNeeded(isVisible);
            if (!getSystemGestureExclusionRects().isEmpty()) {
                postUpdate(new View$$ExternalSyntheticLambda5(this));
            }
            if (!collectPreferKeepClearRects().isEmpty()) {
                postUpdate(new View$$ExternalSyntheticLambda6(this));
            }
        }
    }

    private void notifyAutofillManagerViewVisibilityChanged(boolean isVisible) {
        AutofillManager afm;
        if (isAutofillable()
                && (afm = getAutofillManager()) != null
                && getAutofillViewId() > 1073741823) {
            if (this.mVisibilityChangeForAutofillHandler != null) {
                this.mVisibilityChangeForAutofillHandler.removeMessages(0);
            }
            if (isVisible) {
                afm.notifyViewVisibilityChanged(this, true);
                return;
            }
            if (this.mVisibilityChangeForAutofillHandler == null) {
                this.mVisibilityChangeForAutofillHandler =
                        new VisibilityChangeForAutofillHandler(afm, this);
            }
            this.mVisibilityChangeForAutofillHandler.obtainMessage(0, this).sendToTarget();
        }
    }

    public int getWindowVisibility() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindowVisibility;
        }
        return 8;
    }

    public void getWindowVisibleDisplayFrame(Rect outRect) {
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mViewRootImpl.getWindowVisibleDisplayFrame(outRect);
            translateToWindowIfNeeded(outRect);
            return;
        }
        WindowManager windowManager =
                (WindowManager) this.mContext.getSystemService(WindowManager.class);
        WindowMetrics metrics = windowManager.getMaximumWindowMetrics();
        Insets insets =
                metrics.getWindowInsets()
                        .getInsets(
                                WindowInsets.Type.navigationBars()
                                        | WindowInsets.Type.displayCutout());
        outRect.set(metrics.getBounds());
        outRect.inset(insets);
        outRect.offsetTo(0, 0);
    }

    public void getWindowDisplayFrame(Rect outRect) {
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mViewRootImpl.getDisplayFrame(outRect);
            translateToWindowIfNeeded(outRect);
        } else {
            Display d = DisplayManagerGlobal.getInstance().getRealDisplay(0);
            d.getRectSize(outRect);
        }
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        onConfigurationChanged(newConfig);
    }

    protected void onConfigurationChanged(Configuration newConfig) {}

    void dispatchCollectViewAttributes(AttachInfo attachInfo, int visibility) {
        performCollectViewAttributes(attachInfo, visibility);
    }

    void performCollectViewAttributes(AttachInfo attachInfo, int visibility) {
        if ((visibility & 12) == 0) {
            if ((this.mViewFlags & 67108864) == 67108864) {
                attachInfo.mKeepScreenOn = true;
            }
            attachInfo.mSystemUiVisibility |= this.mSystemUiVisibility;
            ListenerInfo li = this.mListenerInfo;
            if (li != null && li.mOnSystemUiVisibilityChangeListener != null) {
                attachInfo.mHasSystemUiListeners = true;
            }
        }
    }

    void needGlobalAttributesUpdate(boolean force) {
        AttachInfo ai = this.mAttachInfo;
        if (ai != null && !ai.mRecomputeGlobalAttributes) {
            if (force
                    || ai.mKeepScreenOn
                    || ai.mSystemUiVisibility != 0
                    || ai.mHasSystemUiListeners) {
                ai.mRecomputeGlobalAttributes = true;
            }
        }
    }

    @ViewDebug.ExportedProperty
    public boolean isInTouchMode() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mInTouchMode;
        }
        return this.mResources.getBoolean(R.bool.config_defaultInTouchMode);
    }

    @ViewDebug.CapturedViewProperty
    public final Context getContext() {
        return this.mContext;
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.isConfirmKey(keyCode) && event.hasNoModifiers()) {
            if ((this.mViewFlags & 32) == 32) {
                return true;
            }
            if (event.getRepeatCount() == 0) {
                boolean clickable =
                        (this.mViewFlags & 16384) == 16384
                                || (this.mViewFlags & 2097152) == 2097152;
                boolean isTooltipAvailable =
                        (this.mViewFlags & 1073741824) == 1073741824
                                && (this.mSemViewFlags & 2) != 2;
                if (clickable || isTooltipAvailable) {
                    float x = getWidth() / 2.0f;
                    float y = getHeight() / 2.0f;
                    if (clickable) {
                        setPressed(true, x, y);
                    }
                    checkForLongClick(ViewConfiguration.getLongPressTimeout(), x, y, 0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.isConfirmKey(keyCode) && event.hasNoModifiers()) {
            if ((this.mViewFlags & 32) == 32) {
                return true;
            }
            if ((this.mViewFlags & 16384) == 16384 && isPressed()) {
                setPressed(false);
                if (!this.mHasPerformedLongPress) {
                    removeLongPressCallback();
                    if (!event.isCanceled()) {
                        return performClickInternal();
                    }
                }
            }
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return false;
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onCheckIsTextEditor() {
        return false;
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return null;
    }

    public void onInputConnectionOpenedInternal(
            InputConnection inputConnection, EditorInfo editorInfo, Handler handler) {}

    public void onInputConnectionClosedInternal() {}

    public boolean checkInputConnectionProxy(View view) {
        return false;
    }

    public void semSetDisplayCutoutBackgroundColor(final int color) {
        post(
                new Runnable() { // from class: android.view.View.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ViewRootImpl viewRootImpl = View.this.getViewRootImpl();
                        if (viewRootImpl != null) {
                            View rootView = viewRootImpl.getView();
                            if (rootView instanceof DecorView) {
                                DecorView decorView = (DecorView) rootView;
                                decorView.setDisplayCutoutBackgroundColor(color);
                                Log.d(
                                        viewRootImpl.getTag(),
                                        "DecorView.setDisplayCutoutBackgroundColor() #"
                                                + Integer.toHexString(color));
                            }
                        }
                    }
                });
    }

    public void semSetRoundedCorners(int corners) {
        if ((corners & (-16)) != 0) {
            throw new IllegalArgumentException(
                    "Use wrong rounded corners to the param, corners = " + corners);
        }
        if (this.mRoundRadius == -1) {
            initRoundedCorner();
        }
        if (this.mRoundedCornerMode == corners) {
            return;
        }
        this.mRoundedCornerMode = corners;
        removeRoundedCorner((~corners) & 15);
        if (corners == 0) {
            return;
        }
        if (this.mTopLeftRound == null && (corners & 1) != 0) {
            this.mTopLeftRound = getTopLeftRound();
            this.mTopLeftRound.setTint(this.mTopLeftRoundColor);
            if (DEBUG_ROUNDED_CORNER) {
                Log.i(
                        VIEW_LOG_TAG,
                        "set TL bounds[ "
                                + this.mTopLeftRound.getBounds()
                                + " ] color[ "
                                + this.mTopLeftRoundColor
                                + " ] this - "
                                + this);
            }
        }
        if (this.mTopRightRound == null && (corners & 2) != 0) {
            this.mTopRightRound = getTopRightRound();
            this.mTopRightRound.setTint(this.mTopRightRoundColor);
            if (DEBUG_ROUNDED_CORNER) {
                Log.i(
                        VIEW_LOG_TAG,
                        "set TR bounds[ "
                                + this.mTopRightRound.getBounds()
                                + " ] color[ "
                                + this.mTopRightRoundColor
                                + " ] this - "
                                + this);
            }
        }
        if (this.mBottomLeftRound == null && (corners & 4) != 0) {
            this.mBottomLeftRound = getBottomLeftRound();
            this.mBottomLeftRound.setTint(this.mBottomLeftRoundColor);
            if (DEBUG_ROUNDED_CORNER) {
                Log.i(
                        VIEW_LOG_TAG,
                        "set BL bounds[ "
                                + this.mBottomLeftRound.getBounds()
                                + " ] color[ "
                                + this.mBottomLeftRoundColor
                                + " ] this - "
                                + this);
            }
        }
        if (this.mBottomRightRound == null && (corners & 8) != 0) {
            this.mBottomRightRound = getBottomRightRound();
            this.mBottomRightRound.setTint(this.mBottomRightRoundColor);
            if (DEBUG_ROUNDED_CORNER) {
                Log.i(
                        VIEW_LOG_TAG,
                        "set BR bounds[ "
                                + this.mBottomRightRound.getBounds()
                                + " ] color[ "
                                + this.mBottomRightRoundColor
                                + " ] this - "
                                + this);
            }
        }
    }

    public void semSetRoundedCorners(int corners, Pair<Integer, Integer> offset) {
        semSetRoundedCorners(corners);
        this.mRoundedCornerOffset = offset;
        Log.i(VIEW_LOG_TAG, "RoundedCornerOffset " + this.mRoundedCornerOffset);
    }

    public void semSetRoundedCorners(int corners, int roundRadius) {
        if (this.mRoundRadius == -1) {
            initRoundedCorner();
        }
        if (this.mRoundRadius != roundRadius) {
            if (roundRadius < 0) {
                throw new IllegalArgumentException("Invalid radius value " + roundRadius);
            }
            this.mRoundRadius = roundRadius;
        }
        semSetRoundedCorners(corners);
    }

    public int semGetRoundedCorners() {
        return this.mRoundedCornerMode;
    }

    public void semDrawRoundedCorner(Canvas canvas) {
        if (!setOverrideRoundedCornerBounds(this.mRoundedCornerBounds)) {
            canvas.getClipBounds(this.mRoundedCornerBounds);
        }
        try {
            Log.i(VIEW_LOG_TAG, "RoundedCornerOffset " + this.mRoundedCornerOffset);
            if (this.mTopLeftRound != null) {
                this.mTopLeftRound.setBounds(
                        this.mRoundedCornerBounds.left + this.mRoundedCornerOffset.first.intValue(),
                        this.mRoundedCornerBounds.top,
                        this.mRoundedCornerBounds.left
                                + this.mRoundRadius
                                + this.mRoundedCornerOffset.first.intValue(),
                        this.mRoundedCornerBounds.top + this.mRoundRadius);
                this.mTopLeftRound.draw(canvas);
            }
            if (this.mTopRightRound != null) {
                this.mTopRightRound.setBounds(
                        (this.mRoundedCornerBounds.right - this.mRoundRadius)
                                - this.mRoundedCornerOffset.second.intValue(),
                        this.mRoundedCornerBounds.top,
                        this.mRoundedCornerBounds.right
                                - this.mRoundedCornerOffset.second.intValue(),
                        this.mRoundedCornerBounds.top + this.mRoundRadius);
                this.mTopRightRound.draw(canvas);
            }
            if (this.mBottomLeftRound != null) {
                this.mBottomLeftRound.setBounds(
                        this.mRoundedCornerBounds.left + this.mRoundedCornerOffset.first.intValue(),
                        this.mRoundedCornerBounds.bottom - this.mRoundRadius,
                        this.mRoundedCornerBounds.left
                                + this.mRoundRadius
                                + this.mRoundedCornerOffset.first.intValue(),
                        this.mRoundedCornerBounds.bottom);
                this.mBottomLeftRound.draw(canvas);
            }
            if (this.mBottomRightRound != null) {
                this.mBottomRightRound.setBounds(
                        (this.mRoundedCornerBounds.right - this.mRoundRadius)
                                - this.mRoundedCornerOffset.second.intValue(),
                        this.mRoundedCornerBounds.bottom - this.mRoundRadius,
                        this.mRoundedCornerBounds.right
                                - this.mRoundedCornerOffset.second.intValue(),
                        this.mRoundedCornerBounds.bottom);
                this.mBottomRightRound.draw(canvas);
            }
        } catch (NullPointerException ex) {
            Log.e("SemRoundedCorner", "semDrawRoundedCorner: view=" + this + " ex=" + ex);
            throw new NullPointerException();
        }
    }

    public void semSetRoundedCornerColor(int corners, int color) {
        if (corners == 0) {
            throw new IllegalArgumentException("There is no rounded corner on = " + this);
        }
        if ((corners & (-16)) != 0) {
            throw new IllegalArgumentException(
                    "Use wrong rounded corners to the param, corners = " + corners);
        }
        if (DEBUG_ROUNDED_CORNER) {
            return;
        }
        if (this.mRoundRadius == -1) {
            initRoundedCorner();
        }
        if ((corners & 1) != 0) {
            this.mTopLeftRoundColor = color;
            if (this.mTopLeftRound != null) {
                this.mTopLeftRound.setTint(color);
            }
        }
        if ((corners & 2) != 0) {
            this.mTopRightRoundColor = color;
            if (this.mTopRightRound != null) {
                this.mTopRightRound.setTint(color);
            }
        }
        if ((corners & 4) != 0) {
            this.mBottomLeftRoundColor = color;
            if (this.mBottomLeftRound != null) {
                this.mBottomLeftRound.setTint(color);
            }
        }
        if ((corners & 8) != 0) {
            this.mBottomRightRoundColor = color;
            if (this.mBottomRightRound != null) {
                this.mBottomRightRound.setTint(color);
            }
        }
    }

    @Deprecated
    public int semGetRoundedCornerColor(int corner) {
        if (corner == 0) {
            throw new IllegalArgumentException("There is no rounded corner on = " + this);
        }
        if (corner != 1 && corner != 2 && corner != 4 && corner != 8) {
            throw new IllegalArgumentException("Use multiple rounded corner as param on = " + this);
        }
        if ((corner & 1) != 0) {
            return this.mTopLeftRoundColor;
        }
        if ((corner & 2) != 0) {
            return this.mTopRightRoundColor;
        }
        if ((corner & 4) != 0) {
            return this.mBottomLeftRoundColor;
        }
        return this.mBottomRightRoundColor;
    }

    public void semSetRoundedCornerOffset(int offset) {
        this.mCornerOffset = offset;
    }

    private void initRoundedCorner() {
        this.mRoundRadius =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sem_rounded_corner_radius);
        int color =
                DEBUG_ROUNDED_CORNER
                        ? -16776961
                        : this.mResources.getColor(R.color.sem_round_and_bgcolor_dark, null);
        this.mBottomRightRoundColor = color;
        this.mBottomLeftRoundColor = color;
        this.mTopRightRoundColor = color;
        this.mTopLeftRoundColor = color;
    }

    private void removeRoundedCorner(int corner) {
        if ((corner & 1) != 0) {
            this.mTopLeftRound = null;
        }
        if ((corner & 2) != 0) {
            this.mTopRightRound = null;
        }
        if ((corner & 4) != 0) {
            this.mBottomLeftRound = null;
        }
        if ((corner & 8) != 0) {
            this.mBottomRightRound = null;
        }
    }

    protected boolean setOverrideRoundedCornerBounds(Rect outRoundedCornerBounds) {
        return false;
    }

    protected Drawable getTopLeftRound() {
        return getResources().getDrawable(R.drawable.sem_top_left_round);
    }

    protected Drawable getTopRightRound() {
        return getResources().getDrawable(R.drawable.sem_top_right_round);
    }

    protected Drawable getBottomLeftRound() {
        return getResources().getDrawable(R.drawable.sem_bottom_left_round);
    }

    protected Drawable getBottomRightRound() {
        return getResources().getDrawable(R.drawable.sem_bottom_right_round);
    }

    public void getRoundedCornerRegion(Region region) {
        region.setEmpty();
        if (this.mTopLeftRound != null && !this.mTopLeftRound.getBounds().isEmpty()) {
            region.op(this.mTopLeftRound.getBounds(), Region.Op.UNION);
        }
        if (this.mTopRightRound != null && !this.mTopRightRound.getBounds().isEmpty()) {
            region.op(this.mTopRightRound.getBounds(), Region.Op.UNION);
        }
        if (this.mBottomLeftRound != null && !this.mBottomLeftRound.getBounds().isEmpty()) {
            region.op(this.mBottomLeftRound.getBounds(), Region.Op.UNION);
        }
        if (this.mBottomRightRound != null && !this.mBottomRightRound.getBounds().isEmpty()) {
            region.op(this.mBottomRightRound.getBounds(), Region.Op.UNION);
        }
    }

    public void createContextMenu(ContextMenu contextMenu) {
        ContextMenu.ContextMenuInfo menuInfo = getContextMenuInfo();
        ((MenuBuilder) contextMenu).setCurrentMenuInfo(menuInfo);
        onCreateContextMenu(contextMenu);
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnCreateContextMenuListener != null) {
            li.mOnCreateContextMenuListener.onCreateContextMenu(contextMenu, this, menuInfo);
        }
        ((MenuBuilder) contextMenu).setCurrentMenuInfo(null);
        if (this.mParent != null) {
            this.mParent.createContextMenu(contextMenu);
        }
    }

    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return null;
    }

    protected void onCreateContextMenu(ContextMenu menu) {}

    public boolean onTrackballEvent(MotionEvent event) {
        return false;
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        return false;
    }

    private boolean dispatchTouchExplorationHoverEvent(MotionEvent event) {
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mContext);
        if (!manager.isEnabled() || !manager.isTouchExplorationEnabled()) {
            return false;
        }
        boolean oldHoveringTouchDelegate = this.mHoveringTouchDelegate;
        int action = event.getActionMasked();
        boolean pointInDelegateRegion = false;
        AccessibilityNodeInfo.TouchDelegateInfo info = this.mTouchDelegate.getTouchDelegateInfo();
        for (int i = 0; i < info.getRegionCount(); i++) {
            Region r = info.getRegionAt(i);
            if (r.contains((int) event.getX(), (int) event.getY())) {
                pointInDelegateRegion = true;
            }
        }
        if (!oldHoveringTouchDelegate) {
            if ((action == 9 || action == 7)
                    && !pointInHoveredChild(event)
                    && pointInDelegateRegion) {
                this.mHoveringTouchDelegate = true;
            }
        } else if (action == 10
                || (action == 7 && (pointInHoveredChild(event) || !pointInDelegateRegion))) {
            this.mHoveringTouchDelegate = false;
        }
        switch (action) {
            case 7:
                if (oldHoveringTouchDelegate && this.mHoveringTouchDelegate) {
                    boolean handled = this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                    break;
                } else if (!oldHoveringTouchDelegate && this.mHoveringTouchDelegate) {
                    MotionEvent eventNoHistory =
                            event.getHistorySize() == 0
                                    ? event
                                    : MotionEvent.obtainNoHistory(event);
                    eventNoHistory.setAction(9);
                    boolean handled2 =
                            this.mTouchDelegate.onTouchExplorationHoverEvent(eventNoHistory);
                    eventNoHistory.setAction(action);
                    break;
                } else if (oldHoveringTouchDelegate && !this.mHoveringTouchDelegate) {
                    boolean hoverExitPending = event.isHoverExitPending();
                    event.setHoverExitPending(true);
                    this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                    MotionEvent eventNoHistory2 =
                            event.getHistorySize() == 0
                                    ? event
                                    : MotionEvent.obtainNoHistory(event);
                    eventNoHistory2.setHoverExitPending(hoverExitPending);
                    eventNoHistory2.setAction(10);
                    this.mTouchDelegate.onTouchExplorationHoverEvent(eventNoHistory2);
                    break;
                }
                break;
            case 9:
                if (!oldHoveringTouchDelegate && this.mHoveringTouchDelegate) {
                    boolean handled3 = this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                    break;
                }
                break;
            case 10:
                if (oldHoveringTouchDelegate) {
                    this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                    break;
                }
                break;
        }
        return false;
    }

    public boolean onHoverEvent(MotionEvent event) {
        if (this.mTouchDelegate != null && dispatchTouchExplorationHoverEvent(event)) {
            return true;
        }
        int action = event.getActionMasked();
        if (!this.mSendingHoverAccessibilityEvents) {
            if ((action == 9 || action == 7)
                    && !hasHoveredChild()
                    && pointInView(event.getX(), event.getY())) {
                sendAccessibilityHoverEvent(128);
                this.mSendingHoverAccessibilityEvents = true;
            }
        } else if (action == 10 || (action == 7 && !pointInView(event.getX(), event.getY()))) {
            this.mSendingHoverAccessibilityEvents = false;
            sendAccessibilityHoverEvent(256);
        }
        if ((action == 9 || action == 7)
                && event.isFromSource(8194)
                && isOnScrollbar(event.getX(), event.getY())) {
            awakenScrollBars();
        }
        getViewRootImpl();
        boolean z = ViewRune.WIDGET_PEN_SUPPORTED;
        int tooltype = event.getToolType(0);
        boolean compareTooltype = tooltype == 2 || tooltype == 1 || tooltype == 3;
        boolean compareTooltypeByApp =
                this.mHoverPopupToolTypeByApp == 0 || this.mHoverPopupToolTypeByApp == tooltype;
        if (compareTooltypeByApp && compareTooltype && this.mHoverPopupType != 0) {
            if (tooltype == 1 && semIsDesktopMode() && event.isFromSource(8194)) {
                tooltype = 3;
            }
            SemHoverPopupWindow hoverPopup = semGetHoverPopup(tooltype, false);
            if (this.mTooltipInfo == null
                    && hoverPopup != null
                    && !hoverPopup.onHoverEvent(event)
                    && isFingerHoveredInAppWidget()) {
                if (action == 9 && (event.getButtonState() & 32) == 0) {
                    hoverPopup.setHoveringPoint((int) event.getRawX(), (int) event.getRawY());
                    semIsDesktopMode();
                    hoverPopup.show();
                } else if (action == 10) {
                    hoverPopup.dismiss();
                }
            }
        }
        boolean isToolTypeStylus = event.getToolType(0) == 2;
        boolean isDexTouchpad = (event.getFlags() & 67108864) != 0;
        if (!isHoverable() && !isHovered()) {
            return false;
        }
        switch (action) {
            case 9:
                if (CoreRune.FW_SPEN_HOVER && isToolTypeStylus && !isDexTouchpad) {
                    setSpenHovered(true);
                }
                setHovered(true);
                break;
            case 10:
                if (CoreRune.FW_SPEN_HOVER && isToolTypeStylus && !isDexTouchpad) {
                    setSpenHovered(false);
                }
                setHovered(false);
                break;
        }
        dispatchGenericMotionEventInternal(event);
        return true;
    }

    private boolean isHoverable() {
        int viewFlags = this.mViewFlags;
        if ((viewFlags & 32) != 32 || isHovered()) {
            return (viewFlags & 16384) == 16384
                    || (viewFlags & 2097152) == 2097152
                    || (viewFlags & 8388608) == 8388608;
        }
        return false;
    }

    @ViewDebug.ExportedProperty
    public boolean isHovered() {
        return (this.mPrivateFlags & 268435456) != 0;
    }

    public void setHovered(boolean hovered) {
        if (hovered) {
            if ((this.mPrivateFlags & 268435456) == 0) {
                this.mPrivateFlags = 268435456 | this.mPrivateFlags;
                refreshDrawableState();
                onHoverChanged(true);
                return;
            }
            return;
        }
        if ((268435456 & this.mPrivateFlags) != 0) {
            this.mPrivateFlags &= -268435457;
            refreshDrawableState();
            onHoverChanged(false);
        }
    }

    public void onHoverChanged(boolean hovered) {}

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean handleScrollBarDragging(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.View.handleScrollBarDragging(android.view.MotionEvent):boolean");
    }

    @ViewDebug.ExportedProperty
    public boolean isSpenHovered() {
        return (this.mSemViewFlags & 1) != 0;
    }

    public void setSpenHovered(boolean spenHovered) {
        if (spenHovered) {
            if ((this.mSemViewFlags & 1) == 0) {
                this.mSemViewFlags |= 1;
            }
        } else if ((this.mSemViewFlags & 1) != 0) {
            this.mSemViewFlags &= -2;
        }
    }

    protected boolean findSetFingerHovedInAppWidget(View view) {
        return true;
    }

    public boolean isFingerHoveredInAppWidget() {
        return this.mIsSetFingerHoveredInAppWidget;
    }

    @RemotableViewMethod
    public void setFingerHoveredInAppWidget(boolean fingerHovered) {
        this.mIsSetFingerHoveredInAppWidget = fingerHovered;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        String str;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int i7 = this.mViewFlags;
        int action = motionEvent.getAction();
        byte b = (i7 & 1073741824) == 1073741824 && (this.mSemViewFlags & 2) != 2;
        boolean z = (i7 & 16384) == 16384 || (i7 & 2097152) == 2097152 || (i7 & 8388608) == 8388608;
        if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
            Log.i(VIEW_LOG_TAG, "onTouchEvent " + motionEvent);
        }
        if (motionEvent.getToolType(0) == 2 && (motionEvent.getButtonState() & 32) != 0) {
            return z;
        }
        if ((i7 & 32) == 32 && (this.mPrivateFlags4 & 4096) == 0) {
            if (action == 1 && (this.mPrivateFlags & 16384) != 0) {
                setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.i(VIEW_LOG_TAG, "onTouchEvent return_1");
            }
            return z;
        }
        if (this.mTouchDelegate != null && this.mTouchDelegate.onTouchEvent(motionEvent)) {
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.i(VIEW_LOG_TAG, "onTouchEvent return_2");
            }
            return true;
        }
        if (z || b != false) {
            byte b2 = 0;
            byte b3 = 0;
            switch (action) {
                case 0:
                    str = VIEW_LOG_TAG;
                    if (motionEvent.getSource() == 4098) {
                        this.mPrivateFlags3 |= 131072;
                    }
                    this.mHasPerformedLongPress = false;
                    if (!z) {
                        checkForLongClick(ViewConfiguration.getLongPressTimeout(), x, y, 3);
                        break;
                    } else if (!performButtonActionOnTouchDown(motionEvent)) {
                        if (isInScrollingContainer()) {
                            this.mPrivateFlags = 33554432 | this.mPrivateFlags;
                            if (this.mPendingCheckForTap == null) {
                                this.mPendingCheckForTap = new CheckForTap();
                            }
                            this.mPendingCheckForTap.x = motionEvent.getX();
                            this.mPendingCheckForTap.y = motionEvent.getY();
                            postDelayed(
                                    this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                            break;
                        } else {
                            setPressed(true, x, y);
                            checkForLongClick(ViewConfiguration.getLongPressTimeout(), x, y, 3);
                            break;
                        }
                    }
                    break;
                case 1:
                    str = VIEW_LOG_TAG;
                    this.mPrivateFlags3 &= -131073;
                    if (b != false) {
                        handleTooltipUp();
                    }
                    if (z) {
                        boolean z2 = (33554432 & this.mPrivateFlags) != 0;
                        if ((this.mPrivateFlags & 16384) != 0 || z2) {
                            boolean z3 = false;
                            if (isFocusable() && isFocusableInTouchMode() && !isFocused()) {
                                z3 = requestFocus();
                            }
                            if (z2) {
                                setPressed(true, x, y);
                            }
                            if (!this.mHasPerformedLongPress && !this.mIgnoreNextUpEvent) {
                                removeLongPressCallback();
                                if (!z3) {
                                    if (this.mPerformClick == null) {
                                        this.mPerformClick = new PerformClick();
                                    }
                                    if (!post(this.mPerformClick)) {
                                        performClickInternal();
                                    }
                                }
                            }
                            if (this.mUnsetPressedState == null) {
                                this.mUnsetPressedState = new UnsetPressedState();
                            }
                            if (!z2) {
                                if (!post(this.mUnsetPressedState)) {
                                    this.mUnsetPressedState.run();
                                }
                            } else {
                                postDelayed(
                                        this.mUnsetPressedState,
                                        ViewConfiguration.getPressedStateDuration());
                            }
                            removeTapCallback();
                        }
                        this.mIgnoreNextUpEvent = false;
                        break;
                    } else {
                        removeTapCallback();
                        removeLongPressCallback();
                        this.mInContextButtonPress = false;
                        this.mHasPerformedLongPress = false;
                        this.mIgnoreNextUpEvent = false;
                        break;
                    }
                    break;
                case 2:
                    if (z) {
                        drawableHotspotChanged(x, y);
                    }
                    int classification = motionEvent.getClassification();
                    byte b4 = classification == 1;
                    if (this.mIsDeviceDefault) {
                        i = this.mExtendedTouchSlop;
                    } else {
                        i = this.mTouchSlop;
                    }
                    if (CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL
                            && (motionEvent.getFlags() & 1048576) != 0) {
                        setPressed(false);
                        removeLongPressCallback();
                        removeTapCallback();
                        i2 = 0;
                    } else {
                        i2 = i;
                    }
                    if (b4 != false && hasPendingLongPressCallback()) {
                        if (pointInView(x, y, i2)) {
                            i6 = i2;
                            i4 = classification;
                            str = VIEW_LOG_TAG;
                            i5 = 16384;
                        } else {
                            removeLongPressCallback();
                            long longPressTimeout =
                                    ((long)
                                                    (ViewConfiguration.getLongPressTimeout()
                                                            * this.mAmbiguousGestureMultiplier))
                                            - (motionEvent.getEventTime()
                                                    - motionEvent.getDownTime());
                            i6 = i2;
                            i4 = classification;
                            str = VIEW_LOG_TAG;
                            i5 = 16384;
                            checkForLongClick(longPressTimeout, x, y, 3);
                        }
                        i3 = (int) (i6 * this.mAmbiguousGestureMultiplier);
                    } else {
                        i3 = i2;
                        i4 = classification;
                        str = VIEW_LOG_TAG;
                        i5 = 16384;
                    }
                    if (!pointInView(x, y, i3)) {
                        removeTapCallback();
                        removeLongPressCallback();
                        if ((this.mPrivateFlags & i5) != 0) {
                            setPressed(false);
                        }
                        this.mPrivateFlags3 &= -131073;
                    }
                    if ((i4 == 2) && hasPendingLongPressCallback()) {
                        removeLongPressCallback();
                        checkForLongClick(0L, x, y, 4);
                        break;
                    }
                    break;
                case 3:
                    if (z) {
                        setPressed(false);
                    }
                    if (b != false) {
                        handleTooltipUp();
                    }
                    removeTapCallback();
                    removeLongPressCallback();
                    this.mInContextButtonPress = false;
                    this.mHasPerformedLongPress = false;
                    this.mIgnoreNextUpEvent = false;
                    this.mPrivateFlags3 &= -131073;
                    str = VIEW_LOG_TAG;
                    break;
                default:
                    str = VIEW_LOG_TAG;
                    break;
            }
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.i(str, "onTouchEvent return_3");
                return true;
            }
            return true;
        }
        if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
            Log.i(VIEW_LOG_TAG, "onTouchEvent return_4");
        }
        return false;
    }

    private boolean hasExpensiveMeasuresDuringInputEvent() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null
                || attachInfo.mRootView == null
                || !attachInfo.mHandlingPointerEvent) {
            return false;
        }
        ViewFrameInfo info = attachInfo.mViewRootImpl.mViewFrameInfo;
        long durationFromVsyncTimeMs =
                (System.nanoTime() - Choreographer.getInstance().getLastFrameTimeNanos()) / 1000000;
        return durationFromVsyncTimeMs > 3 || info.getAndIncreaseViewMeasuredCount() > 10;
    }

    public boolean isInScrollingContainer() {
        for (ViewParent p = getParent(); p != null && (p instanceof ViewGroup); p = p.getParent()) {
            if (((ViewGroup) p).shouldDelayChildPressedState()) {
                return true;
            }
        }
        return false;
    }

    private boolean hidden_isInScrollingContainer() {
        return isInScrollingContainer();
    }

    private void removeLongPressCallback() {
        if (this.mPendingCheckForLongPress != null) {
            removeCallbacks(this.mPendingCheckForLongPress);
        }
    }

    private boolean hasPendingLongPressCallback() {
        AttachInfo attachInfo;
        if (this.mPendingCheckForLongPress == null || (attachInfo = this.mAttachInfo) == null) {
            return false;
        }
        return attachInfo.mHandler.hasCallbacks(this.mPendingCheckForLongPress);
    }

    private void removePerformClickCallback() {
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
    }

    private void removeUnsetPressCallback() {
        if ((this.mPrivateFlags & 16384) != 0 && this.mUnsetPressedState != null) {
            setPressed(false);
            removeCallbacks(this.mUnsetPressedState);
        }
    }

    private void removeTapCallback() {
        if (this.mPendingCheckForTap != null) {
            this.mPrivateFlags &= -33554433;
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    public void cancelLongPress() {
        removeLongPressCallback();
        removeTapCallback();
    }

    public void setTouchDelegate(TouchDelegate delegate) {
        this.mTouchDelegate = delegate;
    }

    public TouchDelegate getTouchDelegate() {
        return this.mTouchDelegate;
    }

    public final void requestUnbufferedDispatch(MotionEvent event) {
        int action = event.getAction();
        if (this.mAttachInfo != null) {
            if ((action != 0 && action != 2) || !event.isTouchEvent()) {
                return;
            }
            this.mAttachInfo.mUnbufferedDispatchRequested = true;
        }
    }

    public final void requestUnbufferedDispatch(int source) {
        if (this.mUnbufferedInputSource == source) {
            return;
        }
        this.mUnbufferedInputSource = source;
        if (this.mParent != null) {
            this.mParent.onDescendantUnbufferedRequested();
        }
    }

    private boolean hasSize() {
        return this.mBottom > this.mTop && this.mRight > this.mLeft;
    }

    private boolean canTakeFocus() {
        return (this.mViewFlags & 12) == 0
                && (this.mViewFlags & 1) == 1
                && (this.mViewFlags & 32) == 0
                && (sCanFocusZeroSized || !isLayoutValid() || hasSize());
    }

    void setFlags(int flags, int mask) {
        int newFocus;
        boolean accessibilityEnabled = AccessibilityManager.getInstance(this.mContext).isEnabled();
        boolean oldIncludeForAccessibility = accessibilityEnabled && includeForAccessibility(false);
        int old = this.mViewFlags;
        this.mViewFlags = (this.mViewFlags & (~mask)) | (flags & mask);
        int changed = this.mViewFlags ^ old;
        if (changed == 0) {
            return;
        }
        int privateFlags = this.mPrivateFlags;
        boolean shouldNotifyFocusableAvailable = false;
        int focusableChangedByAuto = 0;
        if ((this.mViewFlags & 16) != 0
                && (changed & BatteryStats.HistoryItem.EVENT_TEMP_WHITELIST_FINISH) != 0) {
            if ((this.mViewFlags & 16384) != 0) {
                newFocus = 1;
            } else {
                newFocus = 0;
            }
            this.mViewFlags = (this.mViewFlags & (-2)) | newFocus;
            focusableChangedByAuto = (old & 1) ^ (newFocus & 1);
            changed = (changed & (-2)) | focusableChangedByAuto;
        }
        int newFocus2 = changed & 1;
        if (newFocus2 != 0 && (privateFlags & 16) != 0) {
            if ((old & 1) == 1 && (privateFlags & 2) != 0) {
                clearFocus();
                if (this.mParent instanceof ViewGroup) {
                    ((ViewGroup) this.mParent).clearFocusedInCluster();
                }
            } else if ((old & 1) == 0 && (privateFlags & 2) == 0 && this.mParent != null) {
                ViewRootImpl viewRootImpl = getViewRootImpl();
                if (!sAutoFocusableOffUIThreadWontNotifyParents
                        || focusableChangedByAuto == 0
                        || viewRootImpl == null
                        || viewRootImpl.mThread == Thread.currentThread()) {
                    shouldNotifyFocusableAvailable = canTakeFocus();
                }
            }
        }
        int newVisibility = flags & 12;
        if (newVisibility == 0 && (changed & 12) != 0) {
            this.mPrivateFlags |= 32;
            invalidate(true);
            needGlobalAttributesUpdate(true);
            shouldNotifyFocusableAvailable = hasSize();
        }
        if ((changed & 32) != 0) {
            if ((this.mViewFlags & 32) == 0) {
                shouldNotifyFocusableAvailable = canTakeFocus();
            } else if (isFocused()) {
                clearFocus();
            }
        }
        if (shouldNotifyFocusableAvailable && this.mParent != null) {
            this.mParent.focusableViewAvailable(this);
        }
        if ((changed & 8) != 0) {
            needGlobalAttributesUpdate(false);
            requestLayout();
            if ((this.mViewFlags & 12) == 8) {
                if (hasFocus()) {
                    clearFocus();
                    if (this.mParent instanceof ViewGroup) {
                        ((ViewGroup) this.mParent).clearFocusedInCluster();
                    }
                }
                clearAccessibilityFocus();
                destroyDrawingCache();
                if (this.mParent instanceof View) {
                    ((View) this.mParent).invalidate(true);
                }
                this.mPrivateFlags |= 32;
            }
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewVisibilityChanged = true;
            }
        }
        if ((changed & 4) != 0) {
            needGlobalAttributesUpdate(false);
            this.mPrivateFlags |= 32;
            if ((this.mViewFlags & 12) == 4 && getRootView() != this) {
                if (hasFocus()) {
                    clearFocus();
                    if (this.mParent instanceof ViewGroup) {
                        ((ViewGroup) this.mParent).clearFocusedInCluster();
                    }
                }
                clearAccessibilityFocus();
            }
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewVisibilityChanged = true;
            }
        }
        if ((changed & 12) != 0) {
            if (newVisibility != 0 && this.mAttachInfo != null) {
                cleanupDraw();
            }
            if (this.mParent instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) this.mParent;
                parent.onChildVisibilityChanged(this, changed & 12, newVisibility);
                parent.invalidate(true);
            } else if (this.mParent != null) {
                this.mParent.invalidateChild(this, null);
            }
            if (this.mAttachInfo != null) {
                dispatchVisibilityChanged(this, newVisibility);
                if (this.mParent != null
                        && getWindowVisibility() == 0
                        && (!(this.mParent instanceof ViewGroup)
                                || ((ViewGroup) this.mParent).isShown())) {
                    dispatchVisibilityAggregated(newVisibility == 0);
                }
                if ((old & 12) == 0) {
                    notifySubtreeAccessibilityStateChangedByParentIfNeeded();
                } else {
                    notifySubtreeAccessibilityStateChangedIfNeeded();
                }
            }
        }
        if ((131072 & changed) != 0) {
            destroyDrawingCache();
        }
        if ((32768 & changed) != 0) {
            destroyDrawingCache();
            this.mPrivateFlags &= -32769;
            invalidateParentCaches();
        }
        if ((1572864 & changed) != 0) {
            destroyDrawingCache();
            this.mPrivateFlags &= -32769;
        }
        if ((changed & 128) != 0) {
            if ((this.mViewFlags & 128) != 0) {
                if (this.mBackground != null
                        || this.mDefaultFocusHighlight != null
                        || (this.mForegroundInfo != null
                                && this.mForegroundInfo.mDrawable != null)) {
                    this.mPrivateFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
                } else {
                    this.mPrivateFlags |= 128;
                }
            } else {
                this.mPrivateFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            requestLayout();
            invalidate(true);
        }
        if ((67108864 & changed) != 0
                && this.mParent != null
                && this.mAttachInfo != null
                && !this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mParent.recomputeViewAttributes(this);
        }
        if (accessibilityEnabled) {
            if (isAccessibilityPane()) {
                changed &= -13;
            }
            if ((changed & 1) != 0
                    || (changed & 12) != 0
                    || (changed & 16384) != 0
                    || (2097152 & changed) != 0
                    || (8388608 & changed) != 0) {
                if (oldIncludeForAccessibility != includeForAccessibility(false)) {
                    notifySubtreeAccessibilityStateChangedIfNeeded();
                    return;
                } else {
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                    return;
                }
            }
            if ((changed & 32) != 0) {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    public void bringToFront() {
        if (this.mParent != null) {
            this.mParent.bringChildToFront(this);
        }
    }

    private HapticScrollFeedbackProvider getScrollFeedbackProvider() {
        if (this.mScrollFeedbackProvider == null) {
            this.mScrollFeedbackProvider =
                    new HapticScrollFeedbackProvider(
                            this, ViewConfiguration.get(this.mContext), false);
        }
        return this.mScrollFeedbackProvider;
    }

    private void doRotaryProgressForScrollHaptics(MotionEvent rotaryEvent) {
        float axisScrollValue = rotaryEvent.getAxisValue(26);
        float verticalScrollFactor =
                ViewConfiguration.get(this.mContext).getScaledVerticalScrollFactor();
        int scrollAmount = -Math.round(axisScrollValue * verticalScrollFactor);
        getScrollFeedbackProvider()
                .onScrollProgress(rotaryEvent.getDeviceId(), 4194304, 26, scrollAmount);
    }

    private void doRotaryLimitForScrollHaptics(MotionEvent rotaryEvent) {
        boolean isStart = rotaryEvent.getAxisValue(26) > 0.0f;
        getScrollFeedbackProvider().onScrollLimit(rotaryEvent.getDeviceId(), 4194304, 26, isStart);
    }

    private void processScrollEventForRotaryEncoderHaptics() {
        int i = this.mPrivateFlags4 | 8388608;
        this.mPrivateFlags4 = i;
        if (i != 0) {
            this.mPrivateFlags4 |= 4194304;
            this.mPrivateFlags4 &= -8388609;
        }
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        notifySubtreeAccessibilityStateChangedIfNeeded();
        postSendViewScrolledAccessibilityEventCallback(l - oldl, t - oldt);
        processScrollEventForRotaryEncoderHaptics();
        this.mBackgroundSizeChanged = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (this.mForegroundInfo != null) {
            this.mForegroundInfo.mBoundsChanged = true;
        }
        AttachInfo ai = this.mAttachInfo;
        if (ai != null) {
            ai.mViewScrollChanged = true;
        }
        if (this.mListenerInfo != null && this.mListenerInfo.mOnScrollChangeListener != null) {
            this.mListenerInfo.mOnScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {}

    protected void dispatchDraw(Canvas canvas) {}

    public final ViewParent getParent() {
        return this.mParent;
    }

    public void setScrollX(int value) {
        scrollTo(value, this.mScrollY);
    }

    public void setScrollY(int value) {
        scrollTo(this.mScrollX, value);
    }

    public final int getScrollX() {
        return this.mScrollX;
    }

    public final int getScrollY() {
        return this.mScrollY;
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public final int getWidth() {
        return this.mRight - this.mLeft;
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public final int getHeight() {
        return this.mBottom - this.mTop;
    }

    public void getDrawingRect(Rect outRect) {
        outRect.left = this.mScrollX;
        outRect.top = this.mScrollY;
        outRect.right = this.mScrollX + (this.mRight - this.mLeft);
        outRect.bottom = this.mScrollY + (this.mBottom - this.mTop);
    }

    public final int getMeasuredWidth() {
        return this.mMeasuredWidth & 16777215;
    }

    @ViewDebug.ExportedProperty(
            category = "measurement",
            flagMapping = {
                @ViewDebug.FlagToString(
                        equals = 16777216,
                        mask = -16777216,
                        name = "MEASURED_STATE_TOO_SMALL")
            })
    public final int getMeasuredWidthAndState() {
        return this.mMeasuredWidth;
    }

    public final int getMeasuredHeight() {
        return this.mMeasuredHeight & 16777215;
    }

    @ViewDebug.ExportedProperty(
            category = "measurement",
            flagMapping = {
                @ViewDebug.FlagToString(
                        equals = 16777216,
                        mask = -16777216,
                        name = "MEASURED_STATE_TOO_SMALL")
            })
    public final int getMeasuredHeightAndState() {
        return this.mMeasuredHeight;
    }

    public final int getMeasuredState() {
        return (this.mMeasuredWidth & (-16777216)) | ((this.mMeasuredHeight >> 16) & (-256));
    }

    public Matrix getMatrix() {
        ensureTransformationInfo();
        Matrix matrix = this.mTransformationInfo.mMatrix;
        this.mRenderNode.getMatrix(matrix);
        return matrix;
    }

    public final boolean hasIdentityMatrix() {
        return this.mRenderNode.hasIdentityMatrix();
    }

    void ensureTransformationInfo() {
        if (this.mTransformationInfo == null) {
            this.mTransformationInfo = new TransformationInfo();
        }
    }

    public final Matrix getInverseMatrix() {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mInverseMatrix == null) {
            this.mTransformationInfo.mInverseMatrix = new Matrix();
        }
        Matrix matrix = this.mTransformationInfo.mInverseMatrix;
        this.mRenderNode.getInverseMatrix(matrix);
        return matrix;
    }

    public float getCameraDistance() {
        float dpi = this.mResources.getDisplayMetrics().densityDpi;
        return this.mRenderNode.getCameraDistance() * dpi;
    }

    public void setCameraDistance(float distance) {
        float dpi = this.mResources.getDisplayMetrics().densityDpi;
        invalidateViewProperty(true, false);
        this.mRenderNode.setCameraDistance(Math.abs(distance) / dpi);
        invalidateViewProperty(false, false);
        invalidateParentIfNeededAndWasQuickRejected();
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getRotation() {
        return this.mRenderNode.getRotationZ();
    }

    @RemotableViewMethod
    public void setRotation(float rotation) {
        if (rotation != getRotation()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationZ(rotation);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getRotationY() {
        return this.mRenderNode.getRotationY();
    }

    @RemotableViewMethod
    public void setRotationY(float rotationY) {
        if (rotationY != getRotationY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationY(rotationY);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getRotationX() {
        return this.mRenderNode.getRotationX();
    }

    @RemotableViewMethod
    public void setRotationX(float rotationX) {
        if (rotationX != getRotationX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationX(rotationX);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getScaleX() {
        return this.mRenderNode.getScaleX();
    }

    @RemotableViewMethod
    public void setScaleX(float scaleX) {
        if (scaleX != getScaleX()) {
            float scaleX2 = sanitizeFloatPropertyValue(scaleX, "scaleX");
            invalidateViewProperty(true, false);
            this.mRenderNode.setScaleX(scaleX2);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getScaleY() {
        return this.mRenderNode.getScaleY();
    }

    @RemotableViewMethod
    public void setScaleY(float scaleY) {
        if (scaleY != getScaleY()) {
            float scaleY2 = sanitizeFloatPropertyValue(scaleY, "scaleY");
            invalidateViewProperty(true, false);
            this.mRenderNode.setScaleY(scaleY2);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getPivotX() {
        return this.mRenderNode.getPivotX();
    }

    @RemotableViewMethod
    public void setPivotX(float pivotX) {
        if (!this.mRenderNode.isPivotExplicitlySet() || pivotX != getPivotX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setPivotX(pivotX);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getPivotY() {
        return this.mRenderNode.getPivotY();
    }

    @RemotableViewMethod
    public void setPivotY(float pivotY) {
        if (!this.mRenderNode.isPivotExplicitlySet() || pivotY != getPivotY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setPivotY(pivotY);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public boolean isPivotSet() {
        return this.mRenderNode.isPivotExplicitlySet();
    }

    public void resetPivot() {
        if (this.mRenderNode.resetPivot()) {
            invalidateViewProperty(false, false);
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mAlpha;
        }
        return 1.0f;
    }

    public void forceHasOverlappingRendering(boolean hasOverlappingRendering) {
        this.mPrivateFlags3 |= 16777216;
        if (hasOverlappingRendering) {
            this.mPrivateFlags3 |= 8388608;
        } else {
            this.mPrivateFlags3 &= -8388609;
        }
    }

    public final boolean getHasOverlappingRendering() {
        if ((this.mPrivateFlags3 & 16777216) != 0) {
            return (this.mPrivateFlags3 & 8388608) != 0;
        }
        return hasOverlappingRendering();
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean hasOverlappingRendering() {
        return true;
    }

    @RemotableViewMethod
    public void setAlpha(float alpha) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != alpha) {
            setAlphaInternal(alpha);
            if (onSetAlpha((int) (255.0f * alpha))) {
                this.mPrivateFlags |= 262144;
                invalidateParentCaches();
                invalidate(true);
            } else {
                this.mPrivateFlags &= -262145;
                invalidateViewProperty(true, false);
                this.mRenderNode.setAlpha(getFinalAlpha());
            }
        }
    }

    boolean setAlphaNoInvalidation(float alpha) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != alpha) {
            setAlphaInternal(alpha);
            boolean subclassHandlesAlpha = onSetAlpha((int) (255.0f * alpha));
            if (subclassHandlesAlpha) {
                this.mPrivateFlags |= 262144;
                return true;
            }
            this.mPrivateFlags &= -262145;
            this.mRenderNode.setAlpha(getFinalAlpha());
            return false;
        }
        return false;
    }

    void setAlphaInternal(float alpha) {
        float oldAlpha = this.mTransformationInfo.mAlpha;
        this.mTransformationInfo.mAlpha = alpha;
        if ((alpha == 0.0f) ^ (oldAlpha == 0.0f)) {
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTransitionAlpha(float alpha) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mTransitionAlpha != alpha) {
            this.mTransformationInfo.mTransitionAlpha = alpha;
            this.mPrivateFlags &= -262145;
            invalidateViewProperty(true, false);
            this.mRenderNode.setAlpha(getFinalAlpha());
        }
    }

    private float getFinalAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mAlpha * this.mTransformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTransitionAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    public void setForceDarkAllowed(boolean allow) {
        if (this.mRenderNode.setForceDarkAllowed(allow)) {
            invalidate();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean isForceDarkAllowed() {
        return this.mRenderNode.isForceDarkAllowed();
    }

    @ViewDebug.CapturedViewProperty
    public final int getTop() {
        return this.mTop;
    }

    public final void setTop(int top) {
        int minTop;
        int yLoc;
        if (top != this.mTop) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (this.mAttachInfo != null) {
                    if (top < this.mTop) {
                        minTop = top;
                        yLoc = top - this.mTop;
                    } else {
                        minTop = this.mTop;
                        yLoc = 0;
                    }
                    invalidate(0, yLoc, this.mRight - this.mLeft, this.mBottom - minTop);
                }
            } else {
                invalidate(true);
            }
            int width = this.mRight - this.mLeft;
            int oldHeight = this.mBottom - this.mTop;
            this.mTop = top;
            this.mRenderNode.setTop(this.mTop);
            sizeChange(width, this.mBottom - this.mTop, width, oldHeight);
            if (!matrixIsIdentity) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    @ViewDebug.CapturedViewProperty
    public final int getBottom() {
        return this.mBottom;
    }

    public boolean isDirty() {
        return (this.mPrivateFlags & 2097152) != 0;
    }

    public final void setBottom(int bottom) {
        int maxBottom;
        if (bottom != this.mBottom) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (this.mAttachInfo != null) {
                    if (bottom < this.mBottom) {
                        maxBottom = this.mBottom;
                    } else {
                        maxBottom = bottom;
                    }
                    invalidate(0, 0, this.mRight - this.mLeft, maxBottom - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int width = this.mRight - this.mLeft;
            int oldHeight = this.mBottom - this.mTop;
            this.mBottom = bottom;
            this.mRenderNode.setBottom(this.mBottom);
            sizeChange(width, this.mBottom - this.mTop, width, oldHeight);
            if (!matrixIsIdentity) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    @ViewDebug.CapturedViewProperty
    public final int getLeft() {
        return this.mLeft;
    }

    public final void setLeft(int left) {
        int minLeft;
        int xLoc;
        if (left != this.mLeft) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (this.mAttachInfo != null) {
                    if (left < this.mLeft) {
                        minLeft = left;
                        xLoc = left - this.mLeft;
                    } else {
                        minLeft = this.mLeft;
                        xLoc = 0;
                    }
                    invalidate(xLoc, 0, this.mRight - minLeft, this.mBottom - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int oldWidth = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            this.mLeft = left;
            this.mRenderNode.setLeft(left);
            sizeChange(this.mRight - this.mLeft, height, oldWidth, height);
            if (!matrixIsIdentity) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    @ViewDebug.CapturedViewProperty
    public final int getRight() {
        return this.mRight;
    }

    public final void setRight(int right) {
        int maxRight;
        if (right != this.mRight) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (this.mAttachInfo != null) {
                    if (right < this.mRight) {
                        maxRight = this.mRight;
                    } else {
                        maxRight = right;
                    }
                    invalidate(0, 0, maxRight - this.mLeft, this.mBottom - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int oldWidth = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            this.mRight = right;
            this.mRenderNode.setRight(this.mRight);
            sizeChange(this.mRight - this.mLeft, height, oldWidth, height);
            if (!matrixIsIdentity) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    private static float sanitizeFloatPropertyValue(float value, String propertyName) {
        return sanitizeFloatPropertyValue(value, propertyName, -3.4028235E38f, Float.MAX_VALUE);
    }

    private static float sanitizeFloatPropertyValue(
            float value, String propertyName, float min, float max) {
        if (value >= min && value <= max) {
            return value;
        }
        if (value < min || value == Float.NEGATIVE_INFINITY) {
            if (sThrowOnInvalidFloatProperties) {
                throw new IllegalArgumentException(
                        "Cannot set '"
                                + propertyName
                                + "' to "
                                + value
                                + ", the value must be >= "
                                + min);
            }
            return min;
        }
        if (value > max || value == Float.POSITIVE_INFINITY) {
            if (sThrowOnInvalidFloatProperties) {
                throw new IllegalArgumentException(
                        "Cannot set '"
                                + propertyName
                                + "' to "
                                + value
                                + ", the value must be <= "
                                + max);
            }
            return max;
        }
        if (Float.isNaN(value)) {
            if (sThrowOnInvalidFloatProperties) {
                throw new IllegalArgumentException(
                        "Cannot set '" + propertyName + "' to Float.NaN");
            }
            return 0.0f;
        }
        throw new IllegalStateException("How do you get here?? " + value);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getX() {
        return this.mLeft + getTranslationX();
    }

    public void setX(float x) {
        setTranslationX(x - this.mLeft);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getY() {
        return this.mTop + getTranslationY();
    }

    public void setY(float y) {
        setTranslationY(y - this.mTop);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getZ() {
        return getElevation() + getTranslationZ();
    }

    public void setZ(float z) {
        setTranslationZ(z - getElevation());
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getElevation() {
        return this.mRenderNode.getElevation();
    }

    @RemotableViewMethod
    public void setElevation(float elevation) {
        if (elevation != getElevation()) {
            float elevation2 =
                    sanitizeFloatPropertyValue(
                            elevation, SemMediaPostProcessor.ProcessingFormat.Key.ELEVATION);
            invalidateViewProperty(true, false);
            this.mRenderNode.setElevation(elevation2);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationX() {
        return this.mRenderNode.getTranslationX();
    }

    @RemotableViewMethod
    public void setTranslationX(float translationX) {
        if (translationX != getTranslationX()) {
            this.mPrivateFlags4 |= 268435456;
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationX(translationX);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationY() {
        return this.mRenderNode.getTranslationY();
    }

    @RemotableViewMethod
    public void setTranslationY(float translationY) {
        if (translationY != getTranslationY()) {
            this.mPrivateFlags4 |= 268435456;
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationY(translationY);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationZ() {
        return this.mRenderNode.getTranslationZ();
    }

    @RemotableViewMethod
    public void setTranslationZ(float translationZ) {
        if (translationZ != getTranslationZ()) {
            float translationZ2 = sanitizeFloatPropertyValue(translationZ, "translationZ");
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationZ(translationZ2);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public void setAnimationMatrix(Matrix matrix) {
        invalidateViewProperty(true, false);
        this.mRenderNode.setAnimationMatrix(matrix);
        invalidateViewProperty(false, true);
        invalidateParentIfNeededAndWasQuickRejected();
    }

    public Matrix getAnimationMatrix() {
        return this.mRenderNode.getAnimationMatrix();
    }

    public StateListAnimator getStateListAnimator() {
        return this.mStateListAnimator;
    }

    public void setStateListAnimator(StateListAnimator stateListAnimator) {
        if (this.mStateListAnimator == stateListAnimator) {
            return;
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.setTarget(null);
        }
        this.mStateListAnimator = stateListAnimator;
        if (stateListAnimator != null) {
            stateListAnimator.setTarget(this);
            if (isAttachedToWindow()) {
                stateListAnimator.setState(getDrawableState());
            }
        }
    }

    public final boolean getClipToOutline() {
        return this.mRenderNode.getClipToOutline();
    }

    @RemotableViewMethod
    public void setClipToOutline(boolean clipToOutline) {
        damageInParent();
        if (getClipToOutline() != clipToOutline) {
            this.mRenderNode.setClipToOutline(clipToOutline);
        }
    }

    private void setOutlineProviderFromAttribute(int providerInt) {
        switch (providerInt) {
            case 0:
                setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                break;
            case 1:
                setOutlineProvider(null);
                break;
            case 2:
                setOutlineProvider(ViewOutlineProvider.BOUNDS);
                break;
            case 3:
                setOutlineProvider(ViewOutlineProvider.PADDED_BOUNDS);
                break;
        }
    }

    public void setOutlineProvider(ViewOutlineProvider provider) {
        if (this.mOutlineProvider != provider) {
            this.mOutlineProvider = provider;
            invalidateOutline();
        }
    }

    public ViewOutlineProvider getOutlineProvider() {
        return this.mOutlineProvider;
    }

    public void invalidateOutline() {
        rebuildOutline();
        notifySubtreeAccessibilityStateChangedIfNeeded();
        invalidateViewProperty(false, false);
    }

    private void rebuildOutline() {
        if (this.mAttachInfo == null) {
            return;
        }
        if (this.mOutlineProvider == null) {
            this.mRenderNode.setOutline(null);
            return;
        }
        Outline outline = this.mAttachInfo.mTmpOutline;
        outline.setEmpty();
        outline.setAlpha(1.0f);
        this.mOutlineProvider.getOutline(this, outline);
        this.mRenderNode.setOutline(outline);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean hasShadow() {
        return this.mRenderNode.hasShadow();
    }

    public void setOutlineSpotShadowColor(int color) {
        if (this.mRenderNode.setSpotShadowColor(color)) {
            invalidateViewProperty(true, true);
        }
    }

    public int getOutlineSpotShadowColor() {
        return this.mRenderNode.getSpotShadowColor();
    }

    public void setOutlineAmbientShadowColor(int color) {
        if (this.mRenderNode.setAmbientShadowColor(color)) {
            invalidateViewProperty(true, true);
        }
    }

    public int getOutlineAmbientShadowColor() {
        return this.mRenderNode.getAmbientShadowColor();
    }

    public void setRevealClip(boolean shouldClip, float x, float y, float radius) {
        this.mRenderNode.setRevealClip(shouldClip, x, y, radius);
        invalidateViewProperty(false, false);
    }

    public void getHitRect(Rect outRect) {
        if (hasIdentityMatrix() || this.mAttachInfo == null) {
            outRect.set(this.mLeft, this.mTop, this.mRight, this.mBottom);
            return;
        }
        RectF tmpRect = this.mAttachInfo.mTmpTransformRect;
        tmpRect.set(0.0f, 0.0f, getWidth(), getHeight());
        getMatrix().mapRect(tmpRect);
        outRect.set(
                ((int) tmpRect.left) + this.mLeft,
                ((int) tmpRect.top) + this.mTop,
                ((int) tmpRect.right) + this.mLeft,
                ((int) tmpRect.bottom) + this.mTop);
    }

    final boolean pointInView(float localX, float localY) {
        return pointInView(localX, localY, 0.0f);
    }

    public boolean pointInView(float localX, float localY, float slop) {
        return localX >= (-slop)
                && localY >= (-slop)
                && localX < ((float) (this.mRight - this.mLeft)) + slop
                && localY < ((float) (this.mBottom - this.mTop)) + slop;
    }

    public void getFocusedRect(Rect r) {
        getDrawingRect(r);
    }

    public boolean getGlobalVisibleRect(Rect r, Point globalOffset) {
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        if (width <= 0 || height <= 0) {
            return false;
        }
        r.set(0, 0, width, height);
        if (globalOffset != null) {
            globalOffset.set(-this.mScrollX, -this.mScrollY);
        }
        return this.mParent == null || this.mParent.getChildVisibleRect(this, r, globalOffset);
    }

    public final boolean getGlobalVisibleRect(Rect r) {
        return getGlobalVisibleRect(r, null);
    }

    public final boolean getLocalVisibleRect(Rect r) {
        Point offset = this.mAttachInfo != null ? this.mAttachInfo.mPoint : new Point();
        if (getGlobalVisibleRect(r, offset)) {
            r.offset(-offset.x, -offset.y);
            return true;
        }
        return false;
    }

    public void offsetTopAndBottom(int offset) {
        int minTop;
        int maxBottom;
        int yLoc;
        if (offset != 0) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (isHardwareAccelerated()) {
                    invalidateViewProperty(false, false);
                } else {
                    ViewParent p = this.mParent;
                    if (p != null && this.mAttachInfo != null) {
                        Rect r = this.mAttachInfo.mTmpInvalRect;
                        if (offset < 0) {
                            minTop = this.mTop + offset;
                            maxBottom = this.mBottom;
                            yLoc = offset;
                        } else {
                            minTop = this.mTop;
                            maxBottom = this.mBottom + offset;
                            yLoc = 0;
                        }
                        r.set(0, yLoc, this.mRight - this.mLeft, maxBottom - minTop);
                        p.invalidateChild(this, r);
                    }
                }
            } else {
                invalidateViewProperty(false, false);
            }
            this.mTop += offset;
            this.mBottom += offset;
            this.mRenderNode.offsetTopAndBottom(offset);
            if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
                invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!matrixIsIdentity) {
                    invalidateViewProperty(false, true);
                }
                invalidateParentIfNeeded();
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void offsetLeftAndRight(int offset) {
        int minLeft;
        int maxRight;
        if (offset != 0) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (isHardwareAccelerated()) {
                    invalidateViewProperty(false, false);
                } else {
                    ViewParent p = this.mParent;
                    if (p != null && this.mAttachInfo != null) {
                        Rect r = this.mAttachInfo.mTmpInvalRect;
                        if (offset < 0) {
                            minLeft = this.mLeft + offset;
                            maxRight = this.mRight;
                        } else {
                            minLeft = this.mLeft;
                            maxRight = this.mRight + offset;
                        }
                        r.set(0, 0, maxRight - minLeft, this.mBottom - this.mTop);
                        p.invalidateChild(this, r);
                    }
                }
            } else {
                invalidateViewProperty(false, false);
            }
            this.mLeft += offset;
            this.mRight += offset;
            this.mRenderNode.offsetLeftAndRight(offset);
            if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
                invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!matrixIsIdentity) {
                    invalidateViewProperty(false, true);
                }
                invalidateParentIfNeeded();
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "layout_")
    public ViewGroup.LayoutParams getLayoutParams() {
        return this.mLayoutParams;
    }

    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (params == null) {
            throw new NullPointerException("Layout parameters cannot be null");
        }
        this.mLayoutParams = params;
        resolveLayoutParams();
        if (this.mParent instanceof ViewGroup) {
            ((ViewGroup) this.mParent).onSetLayoutParams(this, params);
        }
        requestLayout();
    }

    public void resolveLayoutParams() {
        if (this.mLayoutParams != null) {
            this.mLayoutParams.resolveLayoutDirection(getLayoutDirection());
        }
    }

    public void scrollTo(int x, int y) {
        if (this.mScrollX != x || this.mScrollY != y) {
            int oldX = this.mScrollX;
            int oldY = this.mScrollY;
            this.mScrollX = x;
            this.mScrollY = y;
            invalidateParentCaches();
            onScrollChanged(this.mScrollX, this.mScrollY, oldX, oldY);
            if (!awakenScrollBars()) {
                postInvalidateOnAnimation();
            }
        }
    }

    public void scrollBy(int x, int y) {
        scrollTo(this.mScrollX + x, this.mScrollY + y);
    }

    public boolean awakenScrollBars() {
        return this.mScrollCache != null
                && awakenScrollBars(this.mScrollCache.scrollBarDefaultDelayBeforeFade, true);
    }

    private boolean initialAwakenScrollBars() {
        return false;
    }

    public boolean awakenScrollBars(int startDelay) {
        return awakenScrollBars(startDelay, true);
    }

    public boolean awakenScrollBars(int startDelay, boolean invalidate) {
        ScrollabilityCache scrollCache = this.mScrollCache;
        if (scrollCache == null || !scrollCache.fadeScrollBars) {
            return false;
        }
        if (scrollCache.scrollBar == null) {
            scrollCache.scrollBar = new ScrollBarDrawable();
            scrollCache.scrollBar.setState(getDrawableState());
            scrollCache.scrollBar.setCallback(this);
        }
        if (!isHorizontalScrollBarEnabled() && !isVerticalScrollBarEnabled()) {
            return false;
        }
        if (invalidate) {
            postInvalidateOnAnimation();
        }
        if (scrollCache.state == 0) {
            startDelay = Math.max(750, startDelay);
        }
        long fadeStartTime = AnimationUtils.currentAnimationTimeMillis() + startDelay;
        scrollCache.fadeStartTime = fadeStartTime;
        scrollCache.state = 1;
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mHandler.removeCallbacks(scrollCache);
            this.mAttachInfo.mHandler.postAtTime(scrollCache, fadeStartTime);
        }
        return true;
    }

    private boolean skipInvalidate() {
        return ((this.mViewFlags & 12) == 0
                        || this.mCurrentAnimation != null
                        || ((this.mParent instanceof ViewGroup)
                                && ((ViewGroup) this.mParent).isViewTransitioning(this)))
                ? false
                : true;
    }

    @Deprecated
    public void invalidate(Rect dirty) {
        int scrollX = this.mScrollX;
        int scrollY = this.mScrollY;
        invalidateInternal(
                dirty.left - scrollX,
                dirty.top - scrollY,
                dirty.right - scrollX,
                dirty.bottom - scrollY,
                true,
                false);
    }

    @Deprecated
    public void invalidate(int l, int t, int r, int b) {
        int scrollX = this.mScrollX;
        int scrollY = this.mScrollY;
        invalidateInternal(l - scrollX, t - scrollY, r - scrollX, b - scrollY, true, false);
    }

    public void invalidate() {
        invalidate(true);
    }

    public void invalidate(boolean invalidateCache) {
        invalidateInternal(
                0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, invalidateCache, true);
    }

    void invalidateInternal(
            int l, int t, int r, int b, boolean invalidateCache, boolean fullInvalidate) {
        View receiver;
        if (this.mGhostView != null) {
            this.mGhostView.invalidate(true);
            return;
        }
        if (skipInvalidate()) {
            return;
        }
        this.mPrivateFlags4 &= -193;
        this.mContentCaptureSessionCached = false;
        if ((this.mPrivateFlags & 48) == 48
                || ((invalidateCache && (this.mPrivateFlags & 32768) == 32768)
                        || (this.mPrivateFlags & Integer.MIN_VALUE) != Integer.MIN_VALUE
                        || (fullInvalidate && isOpaque() != this.mLastIsOpaque))) {
            if (fullInvalidate) {
                this.mLastIsOpaque = isOpaque();
                this.mPrivateFlags &= -33;
            }
            this.mPrivateFlags |= 2097152;
            if (invalidateCache) {
                this.mPrivateFlags |= Integer.MIN_VALUE;
                this.mPrivateFlags &= -32769;
            }
            AttachInfo ai = this.mAttachInfo;
            ViewParent p = this.mParent;
            if (p != null && ai != null && l < r && t < b) {
                Rect damage = ai.mTmpInvalRect;
                damage.set(l, t, r, b);
                p.invalidateChild(this, damage);
            }
            if (this.mBackground != null
                    && this.mBackground.isProjected()
                    && (receiver = getProjectionReceiver()) != null) {
                receiver.damageInParent();
            }
        }
    }

    private View getProjectionReceiver() {
        for (ViewParent p = getParent(); p != null && (p instanceof View); p = p.getParent()) {
            View v = (View) p;
            if (v.isProjectionReceiver()) {
                return v;
            }
        }
        return null;
    }

    private boolean isProjectionReceiver() {
        return this.mBackground != null;
    }

    void invalidateViewProperty(boolean invalidateParent, boolean forceRedraw) {
        if (!isHardwareAccelerated()
                || !this.mRenderNode.hasDisplayList()
                || (this.mPrivateFlags & 64) != 0) {
            if (invalidateParent) {
                invalidateParentCaches();
            }
            if (forceRedraw) {
                this.mPrivateFlags |= 32;
            }
            invalidate(false);
        } else {
            damageInParent();
        }
        this.mPrivateFlags4 |= 536870912;
    }

    protected void damageInParent() {
        if (this.mParent != null && this.mAttachInfo != null) {
            this.mParent.onDescendantInvalidated(this, this);
        }
    }

    protected void invalidateParentCaches() {
        if (this.mParent instanceof View) {
            ((View) this.mParent).mPrivateFlags |= Integer.MIN_VALUE;
        }
    }

    protected void invalidateParentIfNeeded() {
        if (isHardwareAccelerated() && (this.mParent instanceof View)) {
            ((View) this.mParent).invalidate(true);
        }
    }

    protected void invalidateParentIfNeededAndWasQuickRejected() {
        if ((this.mPrivateFlags2 & 268435456) != 0) {
            invalidateParentIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean isOpaque() {
        return (this.mPrivateFlags & 25165824) == 25165824 && getFinalAlpha() >= 1.0f;
    }

    protected void computeOpaqueFlags() {
        if (this.mBackground != null && this.mBackground.getOpacity() == -1) {
            this.mPrivateFlags |= 8388608;
        } else {
            this.mPrivateFlags &= -8388609;
        }
        int flags = this.mViewFlags;
        if (((flags & 512) == 0 && (flags & 256) == 0)
                || (flags & 50331648) == 0
                || (50331648 & flags) == 33554432) {
            this.mPrivateFlags |= 16777216;
        } else {
            this.mPrivateFlags &= -16777217;
        }
    }

    protected boolean hasOpaqueScrollbars() {
        return (this.mPrivateFlags & 16777216) == 16777216;
    }

    public Handler getHandler() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler;
        }
        return null;
    }

    private HandlerActionQueue getRunQueue() {
        if (this.mRunQueue == null) {
            this.mRunQueue = new HandlerActionQueue();
        }
        return this.mRunQueue;
    }

    public ViewRootImpl getViewRootImpl() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl;
        }
        return null;
    }

    public ThreadedRenderer getThreadedRenderer() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mThreadedRenderer;
        }
        return null;
    }

    public boolean post(Runnable action) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.post(action);
        }
        getRunQueue().post(action);
        return true;
    }

    public boolean postDelayed(Runnable action, long delayMillis) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.postDelayed(action, delayMillis);
        }
        getRunQueue().postDelayed(action, delayMillis);
        return true;
    }

    public void postOnAnimation(Runnable action) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallback(1, action, null);
        } else {
            getRunQueue().post(action);
        }
    }

    public void postOnAnimationDelayed(Runnable action, long delayMillis) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(
                    1, action, null, delayMillis);
        } else {
            getRunQueue().postDelayed(action, delayMillis);
        }
    }

    public boolean removeCallbacks(Runnable action) {
        if (action != null) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mHandler.removeCallbacks(action);
                attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, action, null);
            }
            getRunQueue().removeCallbacks(action);
        }
        return true;
    }

    public void postInvalidate() {
        postInvalidateDelayed(0L);
    }

    public void postInvalidate(int left, int top, int right, int bottom) {
        postInvalidateDelayed(0L, left, top, right, bottom);
    }

    public void postInvalidateDelayed(long delayMilliseconds) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateDelayed(this, delayMilliseconds);
        }
    }

    public void postInvalidateDelayed(
            long delayMilliseconds, int left, int top, int right, int bottom) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            AttachInfo.InvalidateInfo info = AttachInfo.InvalidateInfo.obtain();
            info.target = this;
            info.left = left;
            info.top = top;
            info.right = right;
            info.bottom = bottom;
            attachInfo.mViewRootImpl.dispatchInvalidateRectDelayed(info, delayMilliseconds);
        }
    }

    public void postInvalidateOnAnimation() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateOnAnimation(this);
        }
    }

    public void postInvalidateOnAnimation(int left, int top, int right, int bottom) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            AttachInfo.InvalidateInfo info = AttachInfo.InvalidateInfo.obtain();
            info.target = this;
            info.left = left;
            info.top = top;
            info.right = right;
            info.bottom = bottom;
            attachInfo.mViewRootImpl.dispatchInvalidateRectOnAnimation(info);
        }
    }

    private void postSendViewScrolledAccessibilityEventCallback(int dx, int dy) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            AccessibilityEvent event = AccessibilityEvent.obtain(4096);
            event.setScrollDeltaX(dx);
            event.setScrollDeltaY(dy);
            sendAccessibilityEventUnchecked(event);
        }
    }

    public void computeScroll() {}

    public boolean isHorizontalFadingEdgeEnabled() {
        return (this.mViewFlags & 4096) == 4096;
    }

    public void setHorizontalFadingEdgeEnabled(boolean horizontalFadingEdgeEnabled) {
        if (isHorizontalFadingEdgeEnabled() != horizontalFadingEdgeEnabled) {
            if (horizontalFadingEdgeEnabled) {
                initScrollCache();
            }
            this.mViewFlags ^= 4096;
        }
    }

    public boolean isVerticalFadingEdgeEnabled() {
        return (this.mViewFlags & 8192) == 8192;
    }

    public void setVerticalFadingEdgeEnabled(boolean verticalFadingEdgeEnabled) {
        if (isVerticalFadingEdgeEnabled() != verticalFadingEdgeEnabled) {
            if (verticalFadingEdgeEnabled) {
                initScrollCache();
            }
            this.mViewFlags ^= 8192;
        }
    }

    public int getFadingEdge() {
        return this.mViewFlags & 12288;
    }

    public int getFadingEdgeLength() {
        if (this.mScrollCache != null && (this.mViewFlags & 12288) != 0) {
            return this.mScrollCache.fadingEdgeLength;
        }
        return 0;
    }

    protected float getTopFadingEdgeStrength() {
        return computeVerticalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    protected float getBottomFadingEdgeStrength() {
        return computeVerticalScrollOffset() + computeVerticalScrollExtent()
                        < computeVerticalScrollRange()
                ? 1.0f
                : 0.0f;
    }

    protected float getLeftFadingEdgeStrength() {
        return computeHorizontalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    protected float getRightFadingEdgeStrength() {
        return computeHorizontalScrollOffset() + computeHorizontalScrollExtent()
                        < computeHorizontalScrollRange()
                ? 1.0f
                : 0.0f;
    }

    public boolean isHorizontalScrollBarEnabled() {
        return (this.mViewFlags & 256) == 256;
    }

    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        if (isHorizontalScrollBarEnabled() != horizontalScrollBarEnabled) {
            this.mViewFlags ^= 256;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public boolean isVerticalScrollBarEnabled() {
        return (this.mViewFlags & 512) == 512;
    }

    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        if (isVerticalScrollBarEnabled() != verticalScrollBarEnabled) {
            this.mViewFlags ^= 512;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    protected void recomputePadding() {
        internalSetPadding(
                this.mUserPaddingLeft,
                this.mPaddingTop,
                this.mUserPaddingRight,
                this.mUserPaddingBottom);
    }

    public void setScrollbarFadingEnabled(boolean fadeScrollbars) {
        initScrollCache();
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        scrollabilityCache.fadeScrollBars = fadeScrollbars;
        if (fadeScrollbars) {
            scrollabilityCache.state = 0;
        } else {
            scrollabilityCache.state = 1;
        }
    }

    public boolean isScrollbarFadingEnabled() {
        return this.mScrollCache != null && this.mScrollCache.fadeScrollBars;
    }

    public int getScrollBarDefaultDelayBeforeFade() {
        return this.mScrollCache == null
                ? ViewConfiguration.getScrollDefaultDelay()
                : this.mScrollCache.scrollBarDefaultDelayBeforeFade;
    }

    public void setScrollBarDefaultDelayBeforeFade(int scrollBarDefaultDelayBeforeFade) {
        getScrollCache().scrollBarDefaultDelayBeforeFade = scrollBarDefaultDelayBeforeFade;
    }

    public int getScrollBarFadeDuration() {
        return this.mScrollCache == null
                ? ViewConfiguration.getScrollBarFadeDuration()
                : this.mScrollCache.scrollBarFadeDuration;
    }

    public void setScrollBarFadeDuration(int scrollBarFadeDuration) {
        getScrollCache().scrollBarFadeDuration = scrollBarFadeDuration;
    }

    public int getScrollBarSize() {
        return this.mScrollCache == null
                ? ViewConfiguration.get(this.mContext).getScaledScrollBarSize()
                : this.mScrollCache.scrollBarSize;
    }

    public void setScrollBarSize(int scrollBarSize) {
        getScrollCache().scrollBarSize = scrollBarSize;
    }

    public void setScrollBarStyle(int style) {
        if (style != (this.mViewFlags & 50331648)) {
            this.mViewFlags = (this.mViewFlags & (-50331649)) | (50331648 & style);
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    @ViewDebug.ExportedProperty(
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "INSIDE_OVERLAY"),
                @ViewDebug.IntToString(from = 16777216, to = "INSIDE_INSET"),
                @ViewDebug.IntToString(from = 33554432, to = "OUTSIDE_OVERLAY"),
                @ViewDebug.IntToString(from = 50331648, to = "OUTSIDE_INSET")
            })
    public int getScrollBarStyle() {
        return this.mViewFlags & 50331648;
    }

    protected int computeHorizontalScrollRange() {
        return getWidth();
    }

    protected int computeHorizontalScrollOffset() {
        return this.mScrollX;
    }

    protected int computeHorizontalScrollExtent() {
        return getWidth();
    }

    protected int computeVerticalScrollRange() {
        return getHeight();
    }

    protected int computeVerticalScrollOffset() {
        return this.mScrollY;
    }

    protected int computeVerticalScrollExtent() {
        return getHeight();
    }

    public boolean canScrollHorizontally(int direction) {
        int offset = computeHorizontalScrollOffset();
        int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        if (range == 0) {
            return false;
        }
        if (direction < 0) {
            if (offset <= 0) {
                return false;
            }
            return true;
        }
        if (offset >= range - 1) {
            return false;
        }
        return true;
    }

    public boolean canScrollVertically(int direction) {
        int offset = computeVerticalScrollOffset();
        int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) {
            return false;
        }
        if (direction < 0) {
            if (offset <= 0) {
                return false;
            }
            return true;
        }
        if (offset >= range - 1) {
            return false;
        }
        return true;
    }

    void getScrollIndicatorBounds(Rect out) {
        out.left = this.mScrollX;
        out.right = (this.mScrollX + this.mRight) - this.mLeft;
        out.top = this.mScrollY;
        out.bottom = (this.mScrollY + this.mBottom) - this.mTop;
    }

    private void onDrawScrollIndicators(Canvas c) {
        Drawable dr;
        int leftRtl;
        int rightRtl;
        if ((this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) == 0
                || (dr = this.mScrollIndicatorDrawable) == null
                || this.mAttachInfo == null) {
            return;
        }
        int h = dr.getIntrinsicHeight();
        int w = dr.getIntrinsicWidth();
        Rect rect = this.mAttachInfo.mTmpInvalRect;
        getScrollIndicatorBounds(rect);
        if ((this.mPrivateFlags3 & 256) != 0) {
            boolean canScrollUp = canScrollVertically(-1);
            if (canScrollUp) {
                dr.setBounds(rect.left, rect.top, rect.right, rect.top + h);
                dr.draw(c);
            }
        }
        if ((this.mPrivateFlags3 & 512) != 0) {
            boolean canScrollDown = canScrollVertically(1);
            if (canScrollDown) {
                dr.setBounds(rect.left, rect.bottom - h, rect.right, rect.bottom);
                dr.draw(c);
            }
        }
        if (getLayoutDirection() == 1) {
            leftRtl = 8192;
            rightRtl = 4096;
        } else {
            leftRtl = 4096;
            rightRtl = 8192;
        }
        int leftMask = leftRtl | 1024;
        if ((this.mPrivateFlags3 & leftMask) != 0) {
            boolean canScrollLeft = canScrollHorizontally(-1);
            if (canScrollLeft) {
                dr.setBounds(rect.left, rect.top, rect.left + w, rect.bottom);
                dr.draw(c);
            }
        }
        int rightMask = rightRtl | 2048;
        if ((this.mPrivateFlags3 & rightMask) != 0) {
            boolean canScrollRight = canScrollHorizontally(1);
            if (canScrollRight) {
                dr.setBounds(rect.right - w, rect.top, rect.right, rect.bottom);
                dr.draw(c);
            }
        }
    }

    private void getHorizontalScrollBarBounds(Rect drawBounds, Rect touchBounds) {
        Rect bounds = drawBounds != null ? drawBounds : touchBounds;
        if (bounds == null) {
            return;
        }
        int inside = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
        boolean drawVerticalScrollBar =
                isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
        int size = getHorizontalScrollbarHeight();
        int verticalScrollBarGap = drawVerticalScrollBar ? getVerticalScrollbarWidth() : 0;
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        bounds.top = ((this.mScrollY + height) - size) - (this.mUserPaddingBottom & inside);
        bounds.left = this.mScrollX + (this.mPaddingLeft & inside);
        bounds.right =
                ((this.mScrollX + width) - (this.mUserPaddingRight & inside))
                        - verticalScrollBarGap;
        bounds.bottom = bounds.top + size;
        if (touchBounds == null) {
            return;
        }
        if (touchBounds != bounds) {
            touchBounds.set(bounds);
        }
        int minTouchTarget = this.mScrollCache.scrollBarMinTouchTarget;
        if (touchBounds.height() < minTouchTarget) {
            int adjust = (minTouchTarget - touchBounds.height()) / 2;
            touchBounds.bottom = Math.min(touchBounds.bottom + adjust, this.mScrollY + height);
            touchBounds.top = touchBounds.bottom - minTouchTarget;
        }
        int adjust2 = touchBounds.width();
        if (adjust2 < minTouchTarget) {
            int adjust3 = (minTouchTarget - touchBounds.width()) / 2;
            touchBounds.left -= adjust3;
            touchBounds.right = touchBounds.left + minTouchTarget;
        }
    }

    private void getVerticalScrollBarBounds(Rect bounds, Rect touchBounds) {
        if (this.mRoundScrollbarRenderer == null) {
            getStraightVerticalScrollBarBounds(bounds, touchBounds);
        } else {
            this.mRoundScrollbarRenderer.getRoundVerticalScrollBarBounds(
                    bounds != null ? bounds : touchBounds);
        }
    }

    private void getStraightVerticalScrollBarBounds(Rect drawBounds, Rect touchBounds) {
        int i;
        Rect bounds = drawBounds != null ? drawBounds : touchBounds;
        if (bounds == null) {
            return;
        }
        int inside = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
        int size = getVerticalScrollbarWidth();
        int verticalScrollbarPosition = this.mVerticalScrollbarPosition;
        if (verticalScrollbarPosition == 0) {
            if (!isLayoutRtl()) {
                i = 2;
            } else {
                i = 1;
            }
            verticalScrollbarPosition = i;
        }
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        switch (verticalScrollbarPosition) {
            case 1:
                bounds.left = this.mScrollX + (this.mUserPaddingLeft & inside);
                break;
            default:
                bounds.left = ((this.mScrollX + width) - size) - (this.mUserPaddingRight & inside);
                break;
        }
        bounds.top =
                this.mScrollY + (this.mPaddingTop & inside) + this.mAppWidgetScrollBarTopPadding;
        bounds.right = bounds.left + size;
        bounds.bottom =
                ((this.mScrollY + height) - (this.mUserPaddingBottom & inside))
                        - this.mAppWidgetScrollBarBottomPadding;
        if (touchBounds == null) {
            return;
        }
        if (touchBounds != bounds) {
            touchBounds.set(bounds);
        }
        int minTouchTarget = this.mScrollCache.scrollBarMinTouchTarget;
        if (touchBounds.width() < minTouchTarget) {
            int adjust = (minTouchTarget - touchBounds.width()) / 2;
            if (verticalScrollbarPosition == 2) {
                touchBounds.right = Math.min(touchBounds.right + adjust, this.mScrollX + width);
                touchBounds.left = touchBounds.right - minTouchTarget;
            } else {
                touchBounds.left = Math.max(touchBounds.left + adjust, this.mScrollX);
                touchBounds.right = touchBounds.left + minTouchTarget;
            }
        }
        int adjust2 = touchBounds.height();
        if (adjust2 < minTouchTarget) {
            int adjust3 = (minTouchTarget - touchBounds.height()) / 2;
            touchBounds.top -= adjust3;
            touchBounds.bottom = touchBounds.top + minTouchTarget;
        }
    }

    protected final void onDrawScrollBars(Canvas canvas) {
        int state;
        boolean invalidate;
        ScrollBarDrawable scrollBar;
        ScrollabilityCache cache = this.mScrollCache;
        if (cache == null || (state = cache.state) == 0) {
            return;
        }
        boolean z = false;
        if (state == 2) {
            if (cache.interpolatorValues == null) {
                cache.interpolatorValues = new float[1];
            }
            float[] values = cache.interpolatorValues;
            if (cache.scrollBarInterpolator.timeToValues(values)
                    == Interpolator.Result.FREEZE_END) {
                cache.state = 0;
            } else {
                cache.scrollBar.mutate().setAlpha(Math.round(values[0]));
            }
            invalidate = true;
        } else {
            cache.scrollBar.mutate().setAlpha(255);
            invalidate = false;
        }
        boolean drawHorizontalScrollBar = isHorizontalScrollBarEnabled();
        boolean drawVerticalScrollBar =
                isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
        if (this.mRoundScrollbarRenderer != null) {
            if (drawVerticalScrollBar) {
                Rect bounds = cache.mScrollBarBounds;
                getVerticalScrollBarBounds(bounds, null);
                if (this.mVerticalScrollbarPosition == 1
                        || (this.mVerticalScrollbarPosition == 0 && isLayoutRtl())) {
                    z = true;
                }
                boolean shouldDrawScrollbarAtLeft = z;
                this.mRoundScrollbarRenderer.drawRoundScrollbars(
                        canvas,
                        cache.scrollBar.getAlpha() / 255.0f,
                        bounds,
                        shouldDrawScrollbarAtLeft);
                if (invalidate) {
                    invalidate();
                    return;
                }
                return;
            }
            return;
        }
        if (drawVerticalScrollBar || drawHorizontalScrollBar) {
            ScrollBarDrawable scrollBar2 = cache.scrollBar;
            if (drawHorizontalScrollBar) {
                scrollBar2.setParameters(
                        computeHorizontalScrollRange(),
                        computeHorizontalScrollOffset(),
                        computeHorizontalScrollExtent(),
                        false);
                Rect bounds2 = cache.mScrollBarBounds;
                getHorizontalScrollBarBounds(bounds2, null);
                scrollBar = scrollBar2;
                onDrawHorizontalScrollBar(
                        canvas,
                        scrollBar2,
                        bounds2.left,
                        bounds2.top,
                        bounds2.right,
                        bounds2.bottom);
                if (invalidate) {
                    invalidate(bounds2);
                }
            } else {
                scrollBar = scrollBar2;
            }
            if (drawVerticalScrollBar) {
                scrollBar.setParameters(
                        computeVerticalScrollRange(),
                        computeVerticalScrollOffset(),
                        computeVerticalScrollExtent(),
                        true);
                Rect bounds3 = cache.mScrollBarBounds;
                getVerticalScrollBarBounds(bounds3, null);
                if (this.mNeededToChangedScrollBarPosition) {
                    bounds3.left -= this.mScrollBarPositionPadding;
                    bounds3.right -= this.mScrollBarPositionPadding;
                }
                onDrawVerticalScrollBar(
                        canvas,
                        scrollBar,
                        bounds3.left,
                        bounds3.top,
                        bounds3.right,
                        bounds3.bottom);
                if (invalidate) {
                    invalidate(bounds3);
                }
            }
        }
    }

    protected boolean isVerticalScrollBarHidden() {
        return false;
    }

    protected boolean semIsHorizontalScrollBarHidden() {
        return false;
    }

    protected void onDrawHorizontalScrollBar(
            Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        scrollBar.setBounds(l, t, r, b);
        scrollBar.draw(canvas);
    }

    public void onDrawVerticalScrollBar(
            Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        scrollBar.setBounds(l, t, r, b);
        scrollBar.draw(canvas);
    }

    protected void onDraw(Canvas canvas) {}

    void assignParent(ViewParent parent) {
        if (this.mParent == null) {
            this.mParent = parent;
        } else {
            if (parent == null) {
                this.mParent = null;
                return;
            }
            throw new RuntimeException(
                    "view " + this + " being added, but it already has a parent");
        }
    }

    protected void onAttachedToWindow() {
        if (this.mParent != null && (this.mPrivateFlags & 512) != 0) {
            this.mParent.requestTransparentRegion(this);
        }
        this.mPrivateFlags3 &= -5;
        jumpDrawablesToCurrentState();
        AccessibilityNodeIdManager.getInstance().registerViewWithId(this, getAccessibilityViewId());
        resetSubtreeAccessibilityStateChanged();
        rebuildOutline();
        if (isFocused()) {
            notifyFocusChangeToImeFocusController(true);
        }
        if (sTraceLayoutSteps) {
            setTraversalTracingEnabled(true);
        }
        if (sTraceRequestLayoutClass != null
                && sTraceRequestLayoutClass.equals(getClass().getSimpleName())) {
            setRelayoutTracingEnabled(true);
        }
    }

    public boolean resolveRtlPropertiesIfNeeded() {
        if (!needRtlPropertiesResolution()) {
            return false;
        }
        if (!isLayoutDirectionResolved()) {
            resolveLayoutDirection();
            resolveLayoutParams();
        }
        if (!isTextDirectionResolved()) {
            resolveTextDirection();
        }
        if (!isTextAlignmentResolved()) {
            resolveTextAlignment();
        }
        if (!areDrawablesResolved()) {
            resolveDrawables();
        }
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        onRtlPropertiesChanged(getLayoutDirection());
        return true;
    }

    public void resetRtlProperties() {
        resetResolvedLayoutDirection();
        resetResolvedTextDirection();
        resetResolvedTextAlignment();
        resetResolvedPadding();
        resetResolvedDrawables();
    }

    void dispatchScreenStateChanged(int screenState) {
        onScreenStateChanged(screenState);
    }

    public void onScreenStateChanged(int screenState) {}

    void dispatchMovedToDisplay(Display display, Configuration config) {
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mDisplay = display;
            this.mAttachInfo.mDisplayState = display.getState();
        }
        onMovedToDisplay(display.getDisplayId(), config);
    }

    public void onMovedToDisplay(int displayId, Configuration config) {}

    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    private boolean isRtlCompatibilityMode() {
        return !hasRtlSupport();
    }

    private boolean needRtlPropertiesResolution() {
        return (this.mPrivateFlags2 & ALL_RTL_PROPERTIES_RESOLVED) != ALL_RTL_PROPERTIES_RESOLVED;
    }

    public void onRtlPropertiesChanged(int layoutDirection) {}

    public boolean resolveLayoutDirection() {
        this.mPrivateFlags2 &= -49;
        if (hasRtlSupport()) {
            switch ((this.mPrivateFlags2 & 12) >> 2) {
                case 1:
                    this.mPrivateFlags2 |= 16;
                    break;
                case 2:
                    if (!canResolveLayoutDirection()) {
                        return false;
                    }
                    try {
                        if (this.mParent == null || !this.mParent.isLayoutDirectionResolved()) {
                            return false;
                        }
                        if (this.mParent.getLayoutDirection() == 1) {
                            this.mPrivateFlags2 |= 16;
                            break;
                        }
                    } catch (AbstractMethodError e) {
                        Log.e(
                                VIEW_LOG_TAG,
                                this.mParent.getClass().getSimpleName()
                                        + " does not fully implement ViewParent",
                                e);
                        break;
                    }
                    break;
                case 3:
                    if (1 == TextUtils.getLayoutDirectionFromLocale(Locale.getDefault())) {
                        this.mPrivateFlags2 |= 16;
                        break;
                    }
                    break;
            }
        }
        this.mPrivateFlags2 |= 32;
        return true;
    }

    public boolean canResolveLayoutDirection() {
        switch (getRawLayoutDirection()) {
            case 2:
                if (this.mParent != null) {
                    try {
                        return this.mParent.canResolveLayoutDirection();
                    } catch (AbstractMethodError e) {
                        Log.e(
                                VIEW_LOG_TAG,
                                this.mParent.getClass().getSimpleName()
                                        + " does not fully implement ViewParent",
                                e);
                        return false;
                    }
                }
                return false;
            default:
                return true;
        }
    }

    public void resetResolvedLayoutDirection() {
        this.mPrivateFlags2 &= -49;
    }

    public boolean isLayoutDirectionInherited() {
        return getRawLayoutDirection() == 2;
    }

    public boolean isLayoutDirectionResolved() {
        return (this.mPrivateFlags2 & 32) == 32;
    }

    boolean isPaddingResolved() {
        return (this.mPrivateFlags2 & 536870912) == 536870912;
    }

    public void resolvePadding() {
        int resolvedLayoutDirection = getLayoutDirection();
        if (!isRtlCompatibilityMode()) {
            if (this.mBackground != null
                    && (!this.mLeftPaddingDefined || !this.mRightPaddingDefined)) {
                Rect padding = sThreadLocal.get();
                if (padding == null) {
                    padding = new Rect();
                    sThreadLocal.set(padding);
                }
                this.mBackground.getPadding(padding);
                if (!this.mLeftPaddingDefined) {
                    this.mUserPaddingLeftInitial = padding.left;
                }
                if (!this.mRightPaddingDefined) {
                    this.mUserPaddingRightInitial = padding.right;
                }
            }
            switch (resolvedLayoutDirection) {
                case 1:
                    if (this.mUserPaddingStart != Integer.MIN_VALUE) {
                        this.mUserPaddingRight = this.mUserPaddingStart;
                    } else {
                        this.mUserPaddingRight = this.mUserPaddingRightInitial;
                    }
                    if (this.mUserPaddingEnd != Integer.MIN_VALUE) {
                        this.mUserPaddingLeft = this.mUserPaddingEnd;
                        break;
                    } else {
                        this.mUserPaddingLeft = this.mUserPaddingLeftInitial;
                        break;
                    }
                default:
                    if (this.mUserPaddingStart != Integer.MIN_VALUE) {
                        this.mUserPaddingLeft = this.mUserPaddingStart;
                    } else {
                        this.mUserPaddingLeft = this.mUserPaddingLeftInitial;
                    }
                    if (this.mUserPaddingEnd != Integer.MIN_VALUE) {
                        this.mUserPaddingRight = this.mUserPaddingEnd;
                        break;
                    } else {
                        this.mUserPaddingRight = this.mUserPaddingRightInitial;
                        break;
                    }
            }
            this.mUserPaddingBottom =
                    this.mUserPaddingBottom >= 0 ? this.mUserPaddingBottom : this.mPaddingBottom;
        }
        internalSetPadding(
                this.mUserPaddingLeft,
                this.mPaddingTop,
                this.mUserPaddingRight,
                this.mUserPaddingBottom);
        onRtlPropertiesChanged(resolvedLayoutDirection);
        this.mPrivateFlags2 |= 536870912;
    }

    private void hidden_resolvePadding() {
        resolvePadding();
    }

    public void resetResolvedPadding() {
        resetResolvedPaddingInternal();
    }

    void resetResolvedPaddingInternal() {
        this.mPrivateFlags2 &= -536870913;
    }

    protected void onDetachedFromWindow() {}

    protected void onDetachedFromWindowInternal() {
        if (this.mHoverPopupType != 0 && this.mHoverPopup != null) {
            this.mHoverPopup.dismiss();
            this.mHoverPopup = null;
        }
        this.mPrivateFlags &= -67108865;
        this.mPrivateFlags3 &= -5;
        this.mPrivateFlags3 &= -33554433;
        removeUnsetPressCallback();
        removeLongPressCallback();
        removePerformClickCallback();
        clearAccessibilityThrottles();
        stopNestedScroll();
        jumpDrawablesToCurrentState();
        destroyDrawingCache();
        cleanupDraw();
        this.mCurrentAnimation = null;
        if ((this.mViewFlags & 1073741824) == 1073741824) {
            removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
            removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
            hideTooltip();
        }
        AccessibilityNodeIdManager.getInstance().unregisterViewWithId(getAccessibilityViewId());
        if (this.mBackgroundRenderNode != null) {
            this.mBackgroundRenderNode.forceEndAnimators();
        }
        this.mRenderNode.forceEndAnimators();
    }

    private void cleanupDraw() {
        resetDisplayList();
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mViewRootImpl.cancelInvalidate(this);
        }
    }

    void invalidateInheritedLayoutMode(int layoutModeOfRoot) {}

    public int getWindowAttachCount() {
        return this.mWindowAttachCount;
    }

    public IBinder getWindowToken() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindowToken;
        }
        return null;
    }

    public WindowId getWindowId() {
        AttachInfo ai = this.mAttachInfo;
        if (ai == null) {
            return null;
        }
        if (ai.mWindowId == null) {
            try {
                ai.mIWindowId = ai.mSession.getWindowId(ai.mWindowToken);
                if (ai.mIWindowId != null) {
                    ai.mWindowId = new WindowId(ai.mIWindowId);
                }
            } catch (RemoteException e) {
            }
        }
        return ai.mWindowId;
    }

    public IBinder getApplicationWindowToken() {
        AttachInfo ai = this.mAttachInfo;
        if (ai != null) {
            IBinder appWindowToken = ai.mPanelParentWindowToken;
            if (appWindowToken == null) {
                return ai.mWindowToken;
            }
            return appWindowToken;
        }
        return null;
    }

    public Display getDisplay() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mDisplay;
        }
        return null;
    }

    IWindowSession getWindowSession() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mSession;
        }
        return null;
    }

    protected IWindow getWindow() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindow;
        }
        return null;
    }

    int combineVisibility(int vis1, int vis2) {
        return Math.max(vis1, vis2);
    }

    public void fakeFocusAfterAttachingToWindow() {
        this.mShouldFakeFocus = true;
    }

    void dispatchAttachedToWindow(AttachInfo info, int visibility) {
        this.mAttachInfo = info;
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().dispatchAttachedToWindow(info, visibility);
        }
        this.mWindowAttachCount++;
        this.mPrivateFlags |= 1024;
        if (this.mFloatingTreeObserver != null) {
            info.mTreeObserver.merge(this.mFloatingTreeObserver);
            this.mFloatingTreeObserver = null;
        }
        registerPendingFrameMetricsObservers();
        if ((this.mPrivateFlags & 524288) != 0) {
            this.mAttachInfo.mScrollContainers.add(this);
            this.mPrivateFlags |= 1048576;
        }
        if (this.mRunQueue != null) {
            this.mRunQueue.executeActions(info.mHandler);
            this.mRunQueue = null;
        }
        performCollectViewAttributes(this.mAttachInfo, visibility);
        onAttachedToWindow();
        ListenerInfo li = this.mListenerInfo;
        CopyOnWriteArrayList<OnAttachStateChangeListener> listeners =
                li != null ? li.mOnAttachStateChangeListeners : null;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnAttachStateChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnAttachStateChangeListener listener = it.next();
                listener.onViewAttachedToWindow(this);
            }
        }
        int vis = info.mWindowVisibility;
        if (vis != 8) {
            onWindowVisibilityChanged(vis);
            if (isShown()) {
                onVisibilityAggregated(vis == 0);
            }
        }
        onVisibilityChanged(this, visibility);
        if ((this.mPrivateFlags & 1024) != 0) {
            refreshDrawableState();
        }
        needGlobalAttributesUpdate(false);
        notifyEnterOrExitForAutoFillIfNeeded(true);
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
        if (this.mShouldFakeFocus) {
            getViewRootImpl().dispatchCompatFakeFocus();
            this.mShouldFakeFocus = false;
        }
        invalidateBlur();
        if (this.mNeedToSendSavedStickyDragEvent && this.mParent != null) {
            postRequestSendStickyDragStartedEvent();
        }
    }

    void dispatchDetachedFromWindow() {
        AttachInfo info = this.mAttachInfo;
        if (info != null) {
            int vis = info.mWindowVisibility;
            if (vis != 8) {
                onWindowVisibilityChanged(8);
                if (isShown()) {
                    onVisibilityAggregated(false);
                } else {
                    notifyAutofillManagerViewVisibilityChanged(false);
                }
            }
        }
        onDetachedFromWindow();
        onDetachedFromWindowInternal();
        if (info != null) {
            info.mViewRootImpl.getImeFocusController().onViewDetachedFromWindow(this);
        }
        ListenerInfo li = this.mListenerInfo;
        CopyOnWriteArrayList<OnAttachStateChangeListener> listeners =
                li != null ? li.mOnAttachStateChangeListeners : null;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnAttachStateChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnAttachStateChangeListener listener = it.next();
                listener.onViewDetachedFromWindow(this);
            }
        }
        if ((this.mPrivateFlags & 1048576) != 0) {
            this.mAttachInfo.mScrollContainers.remove(this);
            this.mPrivateFlags &= -1048577;
        }
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(false);
        updateSensitiveViewsCountIfNeeded(false);
        this.mAttachInfo = null;
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().dispatchDetachedFromWindow();
        }
        notifyEnterOrExitForAutoFillIfNeeded(false);
        if (info != null && !collectPreferKeepClearRects().isEmpty()) {
            info.mViewRootImpl.updateKeepClearRectsForView(this);
        }
        clearBlurMode();
    }

    public final void cancelPendingInputEvents() {
        dispatchCancelPendingInputEvents();
    }

    void dispatchCancelPendingInputEvents() {
        this.mPrivateFlags3 &= -17;
        onCancelPendingInputEvents();
        if ((this.mPrivateFlags3 & 16) != 16) {
            throw new SuperNotCalledException(
                    "View "
                            + getClass().getSimpleName()
                            + " did not call through to super.onCancelPendingInputEvents()");
        }
    }

    public void onCancelPendingInputEvents() {
        removePerformClickCallback();
        cancelLongPress();
        this.mPrivateFlags3 |= 16;
    }

    public void saveHierarchyState(SparseArray<Parcelable> container) {
        dispatchSaveInstanceState(container);
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        if (this.mID != -1 && (this.mViewFlags & 65536) == 0) {
            this.mPrivateFlags &= -131073;
            Parcelable state = onSaveInstanceState();
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new IllegalStateException(
                        "Derived class did not call super.onSaveInstanceState()");
            }
            if (state != null) {
                container.put(this.mID, state);
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        this.mPrivateFlags |= 131072;
        if (this.mStartActivityRequestWho != null
                || isAutofilled()
                || this.mAutofillViewId > 1073741823) {
            BaseSavedState state = new BaseSavedState(AbsSavedState.EMPTY_STATE);
            if (this.mStartActivityRequestWho != null) {
                state.mSavedData |= 1;
            }
            if (isAutofilled()) {
                state.mSavedData |= 2;
            }
            if (this.mAutofillViewId > 1073741823) {
                state.mSavedData |= 4;
            }
            state.mStartActivityRequestWhoSaved = this.mStartActivityRequestWho;
            state.mIsAutofilled = isAutofilled();
            state.mHideHighlight = hideAutofillHighlight();
            state.mAutofillViewId = this.mAutofillViewId;
            return state;
        }
        return BaseSavedState.EMPTY_STATE;
    }

    public void restoreHierarchyState(SparseArray<Parcelable> container) {
        dispatchRestoreInstanceState(container);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        Parcelable state;
        if (this.mID != -1 && (state = container.get(this.mID)) != null) {
            this.mPrivateFlags &= -131073;
            onRestoreInstanceState(state);
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new IllegalStateException(
                        "Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    public void onRestoreInstanceState(Parcelable state) {
        this.mPrivateFlags |= 131072;
        if (state != null && !(state instanceof AbsSavedState)) {
            throw new IllegalArgumentException(
                    "Wrong state class, expecting View State but received "
                            + state.getClass().toString()
                            + " instead. This usually happens when two views of different type have"
                            + " the same id in the same hierarchy. This view's id is "
                            + ViewDebug.resolveId(this.mContext, getId())
                            + ". Make sure other views do not use the same id.");
        }
        if (state != null && (state instanceof BaseSavedState)) {
            BaseSavedState baseState = (BaseSavedState) state;
            if ((baseState.mSavedData & 1) != 0) {
                this.mStartActivityRequestWho = baseState.mStartActivityRequestWhoSaved;
            }
            if ((baseState.mSavedData & 2) != 0) {
                setAutofilled(baseState.mIsAutofilled, baseState.mHideHighlight);
            }
            if ((baseState.mSavedData & 4) != 0) {
                ((BaseSavedState) state).mSavedData &= -5;
                if ((this.mPrivateFlags3 & 1073741824) != 0) {
                    if (Log.isLoggable(AUTOFILL_LOG_TAG, 3)) {
                        Log.d(
                                AUTOFILL_LOG_TAG,
                                "onRestoreInstanceState(): not setting autofillId to "
                                        + baseState.mAutofillViewId
                                        + " because view explicitly set it to "
                                        + this.mAutofillId);
                    }
                } else {
                    this.mAutofillViewId = baseState.mAutofillViewId;
                    this.mAutofillId = null;
                }
            }
        }
    }

    public long getDrawingTime() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mDrawingTime;
        }
        return 0L;
    }

    public void setDuplicateParentStateEnabled(boolean enabled) {
        setFlags(enabled ? 4194304 : 0, 4194304);
    }

    public boolean isDuplicateParentStateEnabled() {
        return (this.mViewFlags & 4194304) == 4194304;
    }

    public void setLayerType(int layerType, Paint paint) {
        if (layerType < 0 || layerType > 2) {
            throw new IllegalArgumentException(
                    "Layer type can only be one of: LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or"
                        + " LAYER_TYPE_HARDWARE");
        }
        boolean typeChanged = this.mRenderNode.setLayerType(layerType);
        if (!typeChanged) {
            setLayerPaint(paint);
            return;
        }
        if (layerType != 1) {
            destroyDrawingCache();
        }
        this.mLayerType = layerType;
        this.mLayerPaint = this.mLayerType == 0 ? null : paint;
        this.mRenderNode.setLayerPaint(this.mLayerPaint);
        invalidateParentCaches();
        invalidate(true);
    }

    public void setRenderEffect(RenderEffect renderEffect) {
        if (this.mRenderNode.setRenderEffect(renderEffect)) {
            invalidateViewProperty(true, true);
        }
    }

    public void setBackdropRenderEffect(RenderEffect renderEffect) {
        if (this.mRenderNode.setBackdropRenderEffect(renderEffect)) {
            invalidateViewProperty(true, true);
        }
    }

    public void setLayerPaint(Paint paint) {
        int layerType = getLayerType();
        if (layerType != 0) {
            this.mLayerPaint = paint;
            if (layerType == 2) {
                if (this.mRenderNode.setLayerPaint(paint)) {
                    invalidateViewProperty(false, false);
                    return;
                }
                return;
            }
            invalidate();
        }
    }

    @ViewDebug.ExportedProperty(
            category = "drawing",
            mapping = {
                @ViewDebug.IntToString(from = 0, to = KeyProperties.DIGEST_NONE),
                @ViewDebug.IntToString(from = 1, to = "SOFTWARE"),
                @ViewDebug.IntToString(from = 2, to = "HARDWARE")
            })
    public int getLayerType() {
        return this.mLayerType;
    }

    public void buildLayer() {
        if (this.mLayerType == 0) {
            return;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            throw new IllegalStateException("This view must be attached to a window first");
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        switch (this.mLayerType) {
            case 1:
                buildDrawingCache(true);
                return;
            case 2:
                updateDisplayListIfDirty();
                if (attachInfo.mThreadedRenderer != null && this.mRenderNode.hasDisplayList()) {
                    attachInfo.mThreadedRenderer.buildLayer(this.mRenderNode);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean probablyHasInput() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return false;
        }
        return viewRootImpl.probablyHasInput();
    }

    protected void destroyHardwareResources() {
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().destroyHardwareResources();
        }
        if (this.mGhostView != null) {
            this.mGhostView.destroyHardwareResources();
        }
    }

    @Deprecated
    public void setDrawingCacheEnabled(boolean enabled) {
        int i = 0;
        this.mCachingFailed = false;
        if (enabled) {
            i = 32768;
        }
        setFlags(i, 32768);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    @Deprecated
    public boolean isDrawingCacheEnabled() {
        return (this.mViewFlags & 32768) == 32768;
    }

    public void outputDirtyFlags(String indent, boolean clear, int clearMask) {
        Log.d(
                VIEW_LOG_TAG,
                indent
                        + this
                        + "             DIRTY("
                        + (this.mPrivateFlags & 2097152)
                        + ") DRAWN("
                        + (this.mPrivateFlags & 32)
                        + ") CACHE_VALID("
                        + (this.mPrivateFlags & 32768)
                        + ") INVALIDATED("
                        + (this.mPrivateFlags & Integer.MIN_VALUE)
                        + NavigationBarInflaterView.KEY_CODE_END);
        if (clear) {
            this.mPrivateFlags &= clearMask;
        }
        if (this instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) this;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt(i);
                child.outputDirtyFlags(indent + "  ", clear, clearMask);
            }
        }
    }

    protected void dispatchGetDisplayList() {}

    public boolean canHaveDisplayList() {
        return (this.mAttachInfo == null || this.mAttachInfo.mThreadedRenderer == null)
                ? false
                : true;
    }

    public RenderNode updateDisplayListIfDirty() {
        RenderNode renderNode = this.mRenderNode;
        if (!canHaveDisplayList()) {
            return renderNode;
        }
        if ((this.mPrivateFlags & 32768) == 0
                || !renderNode.hasDisplayList()
                || this.mRecreateDisplayList) {
            if (renderNode.hasDisplayList() && !this.mRecreateDisplayList) {
                this.mPrivateFlags |= 32800;
                this.mPrivateFlags &= -2097153;
                dispatchGetDisplayList();
                return renderNode;
            }
            this.mRecreateDisplayList = true;
            int width = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            int layerType = getLayerType();
            renderNode.clearStretch();
            RecordingCanvas canvas = renderNode.beginRecording(width, height);
            try {
                if (layerType == 1) {
                    buildDrawingCache(true);
                    Bitmap cache = getDrawingCache(true);
                    if (cache != null) {
                        canvas.drawBitmap(cache, 0.0f, 0.0f, this.mLayerPaint);
                    }
                } else {
                    computeScroll();
                    canvas.translate(-this.mScrollX, -this.mScrollY);
                    this.mPrivateFlags |= 32800;
                    this.mPrivateFlags &= -2097153;
                    this.mPrivateFlags4 |= 134217728;
                    drawBlurBitmap(canvas);
                    if ((this.mPrivateFlags & 128) == 128 && this.mGfxImageFilter == null) {
                        dispatchDraw(canvas);
                        if (this.mRoundedCornerMode != 0) {
                            semDrawRoundedCorner(canvas);
                        }
                        drawAutofilledHighlight(canvas);
                        if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                            this.mOverlay.getOverlayView().draw(canvas);
                        }
                        if (isShowingLayoutBounds()) {
                            debugDrawFocus(canvas);
                        }
                    } else {
                        draw(canvas);
                    }
                }
                if (CoreRune.FW_VRR_DISCRETE
                        && sToolkitSetFrameRateReadOnlyFlagValue
                        && sToolkitFrameRateViewEnablingReadOnlyFlagValue) {
                    votePreferredFrameRate();
                }
            } finally {
                renderNode.endRecording();
                setDisplayListProperties(renderNode);
            }
        } else {
            if ((this.mPrivateFlags4 & 536870912) == 536870912) {
                if (CoreRune.FW_VRR_DISCRETE
                        && sToolkitSetFrameRateReadOnlyFlagValue
                        && sToolkitFrameRateViewEnablingReadOnlyFlagValue) {
                    votePreferredFrameRate();
                }
                this.mPrivateFlags4 &= -536870913;
            }
            this.mPrivateFlags |= 32800;
            this.mPrivateFlags &= -2097153;
        }
        this.mPrivateFlags4 &= -268435457;
        this.mFrameContentVelocity = -1.0f;
        return renderNode;
    }

    private void resetDisplayList() {
        this.mRenderNode.discardDisplayList();
        if (this.mBackgroundRenderNode != null) {
            this.mBackgroundRenderNode.discardDisplayList();
        }
    }

    @Deprecated
    public Bitmap getDrawingCache() {
        return getDrawingCache(false);
    }

    @Deprecated
    public Bitmap getDrawingCache(boolean autoScale) {
        if ((this.mViewFlags & 131072) == 131072) {
            return null;
        }
        if ((this.mViewFlags & 32768) == 32768) {
            buildDrawingCache(autoScale);
        }
        return autoScale ? this.mDrawingCache : this.mUnscaledDrawingCache;
    }

    @Deprecated
    public void destroyDrawingCache() {
        if (this.mDrawingCache != null) {
            this.mDrawingCache.recycle();
            this.mDrawingCache = null;
        }
        if (this.mUnscaledDrawingCache != null) {
            this.mUnscaledDrawingCache.recycle();
            this.mUnscaledDrawingCache = null;
        }
    }

    @Deprecated
    public void setDrawingCacheBackgroundColor(int color) {
        if (color != this.mDrawingCacheBackgroundColor) {
            this.mDrawingCacheBackgroundColor = color;
            this.mPrivateFlags &= -32769;
        }
    }

    @Deprecated
    public int getDrawingCacheBackgroundColor() {
        return this.mDrawingCacheBackgroundColor;
    }

    @Deprecated
    public void buildDrawingCache() {
        buildDrawingCache(false);
    }

    @Deprecated
    public void buildDrawingCache(boolean autoScale) {
        if ((this.mPrivateFlags & 32768) != 0) {
            if (autoScale) {
                if (this.mDrawingCache != null) {
                    return;
                }
            } else if (this.mUnscaledDrawingCache != null) {
                return;
            }
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.traceBegin(8L, "buildDrawingCache/SW Layer for " + getClass().getSimpleName());
        }
        try {
            buildDrawingCacheImpl(autoScale);
        } finally {
            Trace.traceEnd(8L);
        }
    }

    private void buildDrawingCacheImpl(boolean autoScale) {
        Bitmap.Config quality;
        boolean z;
        Canvas canvas;
        this.mCachingFailed = false;
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        AttachInfo attachInfo = this.mAttachInfo;
        boolean scalingRequired = attachInfo != null && attachInfo.mScalingRequired;
        if (autoScale && scalingRequired) {
            width = (int) ((width * attachInfo.mApplicationScale) + 0.5f);
            height = (int) ((height * attachInfo.mApplicationScale) + 0.5f);
        }
        int drawingCacheBackgroundColor = this.mDrawingCacheBackgroundColor;
        boolean opaque = drawingCacheBackgroundColor != 0 || isOpaque();
        boolean use32BitCache = attachInfo != null && attachInfo.mUse32BitDrawingCache;
        long projectedBitmapSize = width * height * ((!opaque || use32BitCache) ? 4 : 2);
        long drawingCacheSize =
                ViewConfiguration.get(this.mContext).getScaledMaximumDrawingCacheSize();
        if (width > 0 && height > 0) {
            if (projectedBitmapSize <= drawingCacheSize) {
                boolean clear = true;
                Bitmap bitmap = autoScale ? this.mDrawingCache : this.mUnscaledDrawingCache;
                if (bitmap == null || bitmap.getWidth() != width || bitmap.getHeight() != height) {
                    if (!opaque) {
                        quality = Bitmap.Config.ARGB_8888;
                    } else {
                        quality = use32BitCache ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                    }
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    try {
                        bitmap =
                                Bitmap.createBitmap(
                                        this.mResources.getDisplayMetrics(),
                                        width,
                                        height,
                                        quality);
                        bitmap.setDensity(getResources().getDisplayMetrics().densityDpi);
                        if (autoScale) {
                            try {
                                this.mDrawingCache = bitmap;
                            } catch (OutOfMemoryError e) {
                                if (autoScale) {
                                    this.mDrawingCache = null;
                                } else {
                                    this.mUnscaledDrawingCache = null;
                                }
                                this.mCachingFailed = true;
                                return;
                            }
                        } else {
                            this.mUnscaledDrawingCache = bitmap;
                        }
                        if (opaque && use32BitCache) {
                            z = false;
                            bitmap.setHasAlpha(false);
                        } else {
                            z = false;
                        }
                        if (drawingCacheBackgroundColor != 0) {
                            z = true;
                        }
                        clear = z;
                    } catch (OutOfMemoryError e2) {
                    }
                }
                if (attachInfo != null) {
                    canvas = attachInfo.mCanvas;
                    if (canvas == null) {
                        canvas = new Canvas();
                    }
                    canvas.setBitmap(bitmap);
                    attachInfo.mCanvas = null;
                } else {
                    canvas = new Canvas(bitmap);
                }
                if (clear) {
                    bitmap.eraseColor(drawingCacheBackgroundColor);
                }
                computeScroll();
                int restoreCount = canvas.save();
                if (autoScale && scalingRequired) {
                    float scale = attachInfo.mApplicationScale;
                    canvas.scale(scale, scale);
                }
                canvas.translate(-this.mScrollX, -this.mScrollY);
                this.mPrivateFlags |= 32;
                if (this.mAttachInfo == null
                        || !this.mAttachInfo.mHardwareAccelerated
                        || this.mLayerType != 0) {
                    this.mPrivateFlags |= 32768;
                }
                if ((this.mPrivateFlags & 128) == 128) {
                    this.mPrivateFlags &= -2097153;
                    dispatchDraw(canvas);
                    drawAutofilledHighlight(canvas);
                    if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                        this.mOverlay.getOverlayView().draw(canvas);
                    }
                } else {
                    draw(canvas);
                }
                canvas.restoreToCount(restoreCount);
                canvas.setBitmap(null);
                if (attachInfo != null) {
                    attachInfo.mCanvas = canvas;
                    return;
                }
                return;
            }
        }
        if (width > 0 && height > 0) {
            Log.w(
                    VIEW_LOG_TAG,
                    getClass().getSimpleName()
                            + " not displayed because it is too large to fit into a software layer"
                            + " (or drawing cache), needs "
                            + projectedBitmapSize
                            + " bytes, only "
                            + drawingCacheSize
                            + " available");
        }
        destroyDrawingCache();
        this.mCachingFailed = true;
    }

    public Bitmap createSnapshot(ViewDebug.CanvasProvider canvasProvider, boolean skipChildren) {
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        AttachInfo attachInfo = this.mAttachInfo;
        float scale = attachInfo != null ? attachInfo.mApplicationScale : 1.0f;
        int width2 = (int) ((width * scale) + 0.5f);
        int height2 = (int) ((height * scale) + 0.5f);
        Canvas oldCanvas = null;
        try {
            Canvas canvas =
                    canvasProvider.getCanvas(
                            this, width2 > 0 ? width2 : 1, height2 > 0 ? height2 : 1);
            if (attachInfo != null) {
                oldCanvas = attachInfo.mCanvas;
                attachInfo.mCanvas = null;
            }
            computeScroll();
            int restoreCount = canvas.save();
            canvas.scale(scale, scale);
            canvas.translate(-this.mScrollX, -this.mScrollY);
            int flags = this.mPrivateFlags;
            this.mPrivateFlags &= -2097153;
            if ((this.mPrivateFlags & 128) == 128) {
                dispatchDraw(canvas);
                drawAutofilledHighlight(canvas);
                if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                    this.mOverlay.getOverlayView().draw(canvas);
                }
            } else {
                draw(canvas);
            }
            this.mPrivateFlags = flags;
            canvas.restoreToCount(restoreCount);
            return canvasProvider.createBitmap();
        } finally {
            if (oldCanvas != null) {
                attachInfo.mCanvas = oldCanvas;
            }
        }
    }

    public boolean isInEditMode() {
        return false;
    }

    protected boolean isPaddingOffsetRequired() {
        return false;
    }

    protected int getLeftPaddingOffset() {
        return 0;
    }

    protected int getRightPaddingOffset() {
        return 0;
    }

    protected int getTopPaddingOffset() {
        return 0;
    }

    protected int getBottomPaddingOffset() {
        return 0;
    }

    protected int getFadeTop(boolean offsetRequired) {
        int top = this.mPaddingTop;
        return offsetRequired ? top + getTopPaddingOffset() : top;
    }

    protected int getFadeHeight(boolean offsetRequired) {
        int padding = this.mPaddingTop;
        if (offsetRequired) {
            padding += getTopPaddingOffset();
        }
        return ((this.mBottom - this.mTop) - this.mPaddingBottom) - padding;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean isHardwareAccelerated() {
        return this.mAttachInfo != null && this.mAttachInfo.mHardwareAccelerated;
    }

    public void setClipBounds(Rect clipBounds) {
        if (clipBounds != this.mClipBounds) {
            if (clipBounds != null && clipBounds.equals(this.mClipBounds)) {
                return;
            }
            if (clipBounds != null) {
                if (this.mClipBounds == null) {
                    this.mClipBounds = new Rect(clipBounds);
                } else {
                    this.mClipBounds.set(clipBounds);
                }
            } else {
                this.mClipBounds = null;
            }
            this.mRenderNode.setClipRect(this.mClipBounds);
            invalidateViewProperty(false, false);
        }
    }

    public Rect getClipBounds() {
        if (this.mClipBounds != null) {
            return new Rect(this.mClipBounds);
        }
        return null;
    }

    public boolean getClipBounds(Rect outRect) {
        if (this.mClipBounds != null) {
            outRect.set(this.mClipBounds);
            return true;
        }
        return false;
    }

    private boolean applyLegacyAnimation(
            ViewGroup parent, long drawingTime, Animation a, boolean scalingRequired) {
        Transformation invalidationTransform;
        int flags = parent.mGroupFlags;
        boolean initialized = a.isInitialized();
        if (!initialized) {
            a.initialize(
                    this.mRight - this.mLeft,
                    this.mBottom - this.mTop,
                    parent.getWidth(),
                    parent.getHeight());
            a.initializeInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            if (this.mAttachInfo != null) {
                a.setListenerHandler(this.mAttachInfo.mHandler);
            }
            onAnimationStart();
        }
        Transformation t = parent.getChildTransformation();
        boolean more = a.getTransformation(drawingTime, t, 1.0f);
        if (scalingRequired && this.mAttachInfo.mApplicationScale != 1.0f) {
            if (parent.mInvalidationTransformation == null) {
                parent.mInvalidationTransformation = new Transformation();
            }
            Transformation invalidationTransform2 = parent.mInvalidationTransformation;
            a.getTransformation(drawingTime, invalidationTransform2, 1.0f);
            invalidationTransform = invalidationTransform2;
        } else {
            invalidationTransform = t;
        }
        if (more) {
            if (!a.willChangeBounds()) {
                if ((flags & 144) == 128) {
                    parent.mGroupFlags |= 4;
                } else if ((flags & 4) == 0) {
                    parent.mPrivateFlags |= 64;
                    parent.invalidate(this.mLeft, this.mTop, this.mRight, this.mBottom);
                }
            } else {
                if (parent.mInvalidateRegion == null) {
                    parent.mInvalidateRegion = new RectF();
                }
                RectF region = parent.mInvalidateRegion;
                a.getInvalidateRegion(
                        0,
                        0,
                        this.mRight - this.mLeft,
                        this.mBottom - this.mTop,
                        region,
                        invalidationTransform);
                parent.mPrivateFlags |= 64;
                int left = this.mLeft + ((int) region.left);
                int top = this.mTop + ((int) region.top);
                parent.invalidate(
                        left,
                        top,
                        ((int) (region.width() + 0.5f)) + left,
                        ((int) (region.height() + 0.5f)) + top);
            }
        }
        return more;
    }

    void setDisplayListProperties(RenderNode renderNode) {
        int transformType;
        if (renderNode != null) {
            renderNode.setHasOverlappingRendering(getHasOverlappingRendering());
            renderNode.setClipToBounds(
                    (this.mParent instanceof ViewGroup)
                            && ((ViewGroup) this.mParent).getClipChildren());
            float alpha = 1.0f;
            if ((this.mParent instanceof ViewGroup)
                    && (((ViewGroup) this.mParent).mGroupFlags & 2048) != 0) {
                ViewGroup parentVG = (ViewGroup) this.mParent;
                Transformation t = parentVG.getChildTransformation();
                if (parentVG.getChildStaticTransformation(this, t)
                        && (transformType = t.getTransformationType()) != 0) {
                    if ((transformType & 1) != 0) {
                        alpha = t.getAlpha();
                    }
                    if ((transformType & 2) != 0) {
                        renderNode.setStaticMatrix(t.getMatrix());
                    }
                }
            }
            if (this.mTransformationInfo == null) {
                if (alpha < 1.0f) {
                    renderNode.setAlpha(alpha);
                }
            } else {
                float alpha2 = alpha * getFinalAlpha();
                if (alpha2 < 1.0f) {
                    int multipliedAlpha = (int) (255.0f * alpha2);
                    if (onSetAlpha(multipliedAlpha)) {
                        alpha2 = 1.0f;
                    }
                }
                renderNode.setAlpha(alpha2);
            }
        }
    }

    protected final boolean drawsWithRenderNode(Canvas canvas) {
        return this.mAttachInfo != null
                && this.mAttachInfo.mHardwareAccelerated
                && canvas.isHardwareAccelerated();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean draw(android.graphics.Canvas r31, android.view.ViewGroup r32, long r33) {
        /*
            Method dump skipped, instructions count: 988
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.view.View.draw(android.graphics.Canvas,"
                    + " android.view.ViewGroup, long):boolean");
    }

    static Paint getDebugPaint() {
        if (sDebugPaint == null) {
            sDebugPaint = new Paint();
            sDebugPaint.setAntiAlias(false);
        }
        return sDebugPaint;
    }

    final int dipsToPixels(int dips) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) ((dips * scale) + 0.5f);
    }

    private void debugDrawFocus(Canvas canvas) {
        if (isFocused()) {
            int cornerSquareSize = dipsToPixels(8);
            int l = this.mScrollX;
            int r = (this.mRight + l) - this.mLeft;
            int t = this.mScrollY;
            int b = (this.mBottom + t) - this.mTop;
            Paint paint = getDebugPaint();
            paint.setColor(DEBUG_CORNERS_COLOR);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(l, t, l + cornerSquareSize, t + cornerSquareSize, paint);
            canvas.drawRect(r - cornerSquareSize, t, r, t + cornerSquareSize, paint);
            canvas.drawRect(l, b - cornerSquareSize, l + cornerSquareSize, b, paint);
            canvas.drawRect(r - cornerSquareSize, b - cornerSquareSize, r, b, paint);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(l, t, r, b, paint);
            canvas.drawLine(l, b, r, t, paint);
        }
    }

    public void draw(Canvas canvas) {
        int paddingLeft;
        int bottom;
        boolean drawRight;
        float topFadeStrength;
        int length;
        int leftSaveCount;
        int leftSaveCount2;
        int rightSaveCount;
        int right;
        float fadeHeight;
        int topSaveCount;
        int saveCount;
        float rightFadeStrength;
        int top;
        int rightSaveCount2;
        int right2;
        int topSaveCount2;
        int privateFlags = this.mPrivateFlags;
        this.mPrivateFlags = ((-2097153) & privateFlags) | 32;
        drawBackground(canvas);
        int viewFlags = this.mViewFlags;
        boolean horizontalEdges = (viewFlags & 4096) != 0;
        boolean verticalEdges = (viewFlags & 8192) != 0;
        if (!verticalEdges && !horizontalEdges) {
            onDraw(canvas);
            dispatchDraw(canvas);
            if (this.mRoundedCornerMode != 0) {
                semDrawRoundedCorner(canvas);
            }
            drawAutofilledHighlight(canvas);
            if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                this.mOverlay.getOverlayView().dispatchDraw(canvas);
            }
            onDrawForeground(canvas);
            if (this.mGfxImageFilter != null) {
                this.mGfxImageFilter.draw(canvas);
            }
            drawDefaultFocusHighlight(canvas);
            if (isShowingLayoutBounds()) {
                debugDrawFocus(canvas);
                return;
            }
            return;
        }
        float bottomFadeStrength = 0.0f;
        float leftFadeStrength = 0.0f;
        float rightFadeStrength2 = 0.0f;
        int paddingLeft2 = this.mPaddingLeft;
        boolean offsetRequired = isPaddingOffsetRequired();
        if (!offsetRequired) {
            paddingLeft = paddingLeft2;
        } else {
            paddingLeft = paddingLeft2 + getLeftPaddingOffset();
        }
        int left = this.mScrollX + paddingLeft;
        boolean drawTop = false;
        int right3 = (((this.mRight + left) - this.mLeft) - this.mPaddingRight) - paddingLeft;
        int top2 = this.mScrollY + getFadeTop(offsetRequired);
        int bottom2 = top2 + getFadeHeight(offsetRequired);
        if (!offsetRequired) {
            bottom = bottom2;
        } else {
            right3 += getRightPaddingOffset();
            bottom = bottom2 + getBottomPaddingOffset();
        }
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        boolean drawBottom = false;
        float fadeHeight2 = scrollabilityCache == null ? 0.0f : scrollabilityCache.fadingEdgeLength;
        boolean drawLeft = false;
        int length2 = (int) fadeHeight2;
        if (verticalEdges) {
            drawRight = false;
            topFadeStrength = 0.0f;
            if (top2 + length2 > bottom - length2) {
                length2 = (bottom - top2) / 2;
            }
        } else {
            drawRight = false;
            topFadeStrength = 0.0f;
        }
        if (horizontalEdges && left + length2 > right3 - length2) {
            length = (right3 - left) / 2;
        } else {
            length = length2;
        }
        if (verticalEdges) {
            float topFadeStrength2 = Math.max(0.0f, Math.min(1.0f, getTopFadingEdgeStrength()));
            topFadeStrength = topFadeStrength2;
            boolean drawTop2 = topFadeStrength2 * fadeHeight2 > 1.0f;
            drawTop = drawTop2;
            bottomFadeStrength = Math.max(0.0f, Math.min(1.0f, getBottomFadingEdgeStrength()));
            drawBottom = bottomFadeStrength * fadeHeight2 > 1.0f;
        }
        if (horizontalEdges) {
            leftFadeStrength = Math.max(0.0f, Math.min(1.0f, getLeftFadingEdgeStrength()));
            boolean drawLeft2 = leftFadeStrength * fadeHeight2 > 1.0f;
            drawLeft = drawLeft2;
            rightFadeStrength2 = Math.max(0.0f, Math.min(1.0f, getRightFadingEdgeStrength()));
            drawRight = rightFadeStrength2 * fadeHeight2 > 1.0f;
        }
        int saveCount2 = canvas.getSaveCount();
        int topSaveCount3 = -1;
        int bottomSaveCount = -1;
        int leftSaveCount3 = -1;
        int solidColor = getSolidColor();
        if (solidColor != 0) {
            scrollabilityCache.setFadeColor(solidColor);
            leftSaveCount = -1;
            leftSaveCount2 = -1;
            rightSaveCount = -1;
        } else {
            if (drawTop) {
                topSaveCount3 = canvas.saveUnclippedLayer(left, top2, right3, top2 + length);
            }
            if (!drawBottom) {
                topSaveCount2 = topSaveCount3;
            } else {
                topSaveCount2 = topSaveCount3;
                bottomSaveCount = canvas.saveUnclippedLayer(left, bottom - length, right3, bottom);
            }
            if (drawLeft) {
                leftSaveCount3 = canvas.saveUnclippedLayer(left, top2, left + length, bottom);
            }
            if (!drawRight) {
                leftSaveCount = leftSaveCount3;
                topSaveCount3 = topSaveCount2;
                leftSaveCount2 = bottomSaveCount;
                rightSaveCount = -1;
            } else {
                int rightSaveCount3 =
                        canvas.saveUnclippedLayer(right3 - length, top2, right3, bottom);
                leftSaveCount = leftSaveCount3;
                topSaveCount3 = topSaveCount2;
                leftSaveCount2 = bottomSaveCount;
                rightSaveCount = rightSaveCount3;
            }
        }
        onDraw(canvas);
        dispatchDraw(canvas);
        float bottomFadeStrength2 = bottomFadeStrength;
        Paint p = scrollabilityCache.paint;
        int leftSaveCount4 = leftSaveCount;
        Matrix matrix = scrollabilityCache.matrix;
        float leftFadeStrength2 = leftFadeStrength;
        Shader fade = scrollabilityCache.shader;
        if (!drawRight) {
            right = right3;
            fadeHeight = fadeHeight2;
            topSaveCount = topSaveCount3;
            saveCount = saveCount2;
            rightFadeStrength = 1.0f;
            top = top2;
            rightSaveCount2 = left;
        } else {
            matrix.setScale(1.0f, fadeHeight2 * rightFadeStrength2);
            matrix.postRotate(90.0f);
            matrix.postTranslate(right3, top2);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor != 0) {
                fadeHeight = fadeHeight2;
                top = top2;
                rightSaveCount2 = left;
                topSaveCount = topSaveCount3;
                right = right3;
                saveCount = saveCount2;
                rightFadeStrength = 1.0f;
                canvas.drawRect(right3 - length, top2, right3, bottom, p);
            } else {
                canvas.restoreUnclippedLayer(rightSaveCount, p);
                right = right3;
                fadeHeight = fadeHeight2;
                topSaveCount = topSaveCount3;
                rightSaveCount2 = left;
                saveCount = saveCount2;
                top = top2;
                rightFadeStrength = 1.0f;
            }
        }
        if (drawLeft) {
            matrix.setScale(rightFadeStrength, fadeHeight * leftFadeStrength2);
            matrix.postRotate(-90.0f);
            matrix.postTranslate(rightSaveCount2, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(leftSaveCount4, p);
            } else {
                canvas.drawRect(rightSaveCount2, top, rightSaveCount2 + length, bottom, p);
            }
        }
        if (!drawBottom) {
            right2 = right;
        } else {
            matrix.setScale(rightFadeStrength, fadeHeight * bottomFadeStrength2);
            matrix.postRotate(180.0f);
            matrix.postTranslate(rightSaveCount2, bottom);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                int bottomSaveCount2 = leftSaveCount2;
                canvas.restoreUnclippedLayer(bottomSaveCount2, p);
                right2 = right;
            } else {
                int right4 = right;
                right2 = right4;
                canvas.drawRect(rightSaveCount2, bottom - length, right4, bottom, p);
            }
        }
        if (drawTop) {
            matrix.setScale(rightFadeStrength, fadeHeight * topFadeStrength);
            matrix.postTranslate(rightSaveCount2, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(topSaveCount, p);
            } else {
                canvas.drawRect(rightSaveCount2, top, right2, top + length, p);
            }
        }
        canvas.restoreToCount(saveCount);
        drawAutofilledHighlight(canvas);
        if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
            this.mOverlay.getOverlayView().dispatchDraw(canvas);
        }
        onDrawForeground(canvas);
        if (this.mGfxImageFilter != null) {
            this.mGfxImageFilter.draw(canvas);
        }
        drawDefaultFocusHighlight(canvas);
        if (isShowingLayoutBounds()) {
            debugDrawFocus(canvas);
        }
    }

    private void drawBackground(Canvas canvas) {
        int squareSize;
        Drawable background = this.mBackground;
        if (background == null) {
            if (isBlurDebug()) {
                Log.i(VIEW_LOG_TAG, "return mBackground is null, this=" + this);
                return;
            }
            return;
        }
        int moveX = 0;
        int moveY = 0;
        if (getLastBackgroundResource() == 17304791
                && this.mContext.getResources().getAssets().getSamsungThemeOverlays().size() > 0) {
            int width = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            if (width > height) {
                squareSize = width;
                moveY = (width - height) / 2;
            } else {
                squareSize = height;
                moveX = (height - width) / 2;
            }
            setBackgroundBounds(squareSize);
        } else {
            setBackgroundBounds();
        }
        if (canvas.isHardwareAccelerated()
                && this.mAttachInfo != null
                && this.mAttachInfo.mThreadedRenderer != null) {
            this.mBackgroundRenderNode =
                    getDrawableRenderNode(background, this.mBackgroundRenderNode);
            RenderNode renderNode = this.mBackgroundRenderNode;
            if (renderNode != null && renderNode.hasDisplayList()) {
                setBackgroundRenderNodeProperties(renderNode);
                if ((moveX | moveY) != 0) {
                    canvas.translate(-moveX, -moveY);
                    ((RecordingCanvas) canvas).drawRenderNode(renderNode);
                    canvas.translate(moveX, moveY);
                    return;
                }
                ((RecordingCanvas) canvas).drawRenderNode(renderNode);
                return;
            }
        }
        int scrollX = this.mScrollX;
        int scrollY = this.mScrollY;
        if ((scrollX | scrollY | moveX | moveY) == 0) {
            background.draw(canvas);
            return;
        }
        canvas.translate((-moveX) + scrollX, (-moveY) + scrollY);
        background.draw(canvas);
        canvas.translate((-scrollX) + moveX, (-scrollY) + moveY);
    }

    void setBackgroundBounds() {
        if (this.mBackgroundSizeChanged && this.mBackground != null) {
            this.mBackground.setBounds(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            this.mBackgroundSizeChanged = false;
            rebuildOutline();
        }
    }

    private void setBackgroundRenderNodeProperties(RenderNode renderNode) {
        renderNode.setTranslationX(this.mScrollX);
        renderNode.setTranslationY(this.mScrollY);
    }

    private RenderNode getDrawableRenderNode(Drawable drawable, RenderNode renderNode) {
        if (renderNode == null) {
            renderNode =
                    RenderNode.create(
                            drawable.getClass().getName(), new ViewAnimationHostBridge(this));
            renderNode.setUsageHint(1);
        }
        Rect bounds = drawable.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        renderNode.clearStretch();
        RecordingCanvas canvas = renderNode.beginRecording(width, height);
        canvas.translate(-bounds.left, -bounds.top);
        try {
            drawable.draw(canvas);
            renderNode.endRecording();
            renderNode.setLeftTopRightBottom(bounds.left, bounds.top, bounds.right, bounds.bottom);
            renderNode.setProjectBackwards(drawable.isProjected());
            renderNode.setProjectionReceiver(true);
            renderNode.setClipToBounds(false);
            return renderNode;
        } catch (Throwable th) {
            renderNode.endRecording();
            throw th;
        }
    }

    public ViewOverlay getOverlay() {
        if (this.mOverlay == null) {
            this.mOverlay = new ViewOverlay(this.mContext, this);
        }
        return this.mOverlay;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public int getSolidColor() {
        return 0;
    }

    private static String printFlags(int flags) {
        String output = "";
        int numFlags = 0;
        if ((flags & 1) == 1) {
            output = "TAKES_FOCUS";
            numFlags = 0 + 1;
        }
        switch (flags & 12) {
            case 4:
                if (numFlags > 0) {
                    output = output + " ";
                }
                return output + "INVISIBLE";
            case 8:
                if (numFlags > 0) {
                    output = output + " ";
                }
                return output + "GONE";
            default:
                return output;
        }
    }

    private static String printPrivateFlags(int privateFlags) {
        String output = "";
        int numFlags = 0;
        if ((privateFlags & 1) == 1) {
            output = "WANTS_FOCUS";
            numFlags = 0 + 1;
        }
        if ((privateFlags & 2) == 2) {
            if (numFlags > 0) {
                output = output + " ";
            }
            output = output + "FOCUSED";
            numFlags++;
        }
        if ((privateFlags & 4) == 4) {
            if (numFlags > 0) {
                output = output + " ";
            }
            output = output + "SELECTED";
            numFlags++;
        }
        if ((privateFlags & 8) == 8) {
            if (numFlags > 0) {
                output = output + " ";
            }
            output = output + "IS_ROOT_NAMESPACE";
            numFlags++;
        }
        if ((privateFlags & 16) == 16) {
            if (numFlags > 0) {
                output = output + " ";
            }
            output = output + "HAS_BOUNDS";
            numFlags++;
        }
        if ((privateFlags & 32) == 32) {
            if (numFlags > 0) {
                output = output + " ";
            }
            return output + "DRAWN";
        }
        return output;
    }

    public boolean isLayoutRequested() {
        return (this.mPrivateFlags & 4096) == 4096;
    }

    public static boolean isLayoutModeOptical(Object o) {
        return (o instanceof ViewGroup) && ((ViewGroup) o).isLayoutModeOptical();
    }

    public static void setTraceLayoutSteps(boolean traceLayoutSteps) {
        sTraceLayoutSteps = traceLayoutSteps;
    }

    public static void setTracedRequestLayoutClassClass(String s) {
        sTraceRequestLayoutClass = s;
    }

    private boolean setOpticalFrame(int left, int top, int right, int bottom) {
        Insets parentInsets =
                this.mParent instanceof View
                        ? ((View) this.mParent).getOpticalInsets()
                        : Insets.NONE;
        Insets childInsets = getOpticalInsets();
        return setFrame(
                (parentInsets.left + left) - childInsets.left,
                (parentInsets.top + top) - childInsets.top,
                parentInsets.left + right + childInsets.right,
                parentInsets.top + bottom + childInsets.bottom);
    }

    public void layout(int l, int t, int r, int b) {
        View view;
        if ((this.mPrivateFlags3 & 8) != 0) {
            if (isTraversalTracingEnabled()) {
                Trace.beginSection(this.mTracingStrings.onMeasureBeforeLayout);
            }
            onMeasure(this.mOldWidthMeasureSpec, this.mOldHeightMeasureSpec);
            if (isTraversalTracingEnabled()) {
                Trace.endSection();
            }
            this.mPrivateFlags3 &= -9;
        }
        int oldL = this.mLeft;
        int oldT = this.mTop;
        int oldB = this.mBottom;
        int oldR = this.mRight;
        boolean changed =
                isLayoutModeOptical(this.mParent)
                        ? setOpticalFrame(l, t, r, b)
                        : setFrame(l, t, r, b);
        View view2 = null;
        if (!changed && (this.mPrivateFlags & 8192) != 8192) {
            view = null;
        } else {
            if (isTraversalTracingEnabled()) {
                Trace.beginSection(this.mTracingStrings.onLayout);
            }
            onLayout(changed, l, t, r, b);
            if (isTraversalTracingEnabled()) {
                Trace.endSection();
            }
            if (shouldDrawRoundScrollbar()) {
                if (this.mRoundScrollbarRenderer == null) {
                    this.mRoundScrollbarRenderer = new RoundScrollbarRenderer(this);
                }
            } else {
                this.mRoundScrollbarRenderer = null;
            }
            this.mPrivateFlags &= -8193;
            ListenerInfo li = this.mListenerInfo;
            if (li == null || li.mOnLayoutChangeListeners == null) {
                view = null;
            } else {
                ArrayList<OnLayoutChangeListener> listenersCopy =
                        (ArrayList) li.mOnLayoutChangeListeners.clone();
                int numListeners = listenersCopy.size();
                int i = 0;
                while (i < numListeners) {
                    int numListeners2 = numListeners;
                    int numListeners3 = oldL;
                    listenersCopy
                            .get(i)
                            .onLayoutChange(this, l, t, r, b, numListeners3, oldT, oldR, oldB);
                    i++;
                    view2 = view2;
                    numListeners = numListeners2;
                    listenersCopy = listenersCopy;
                    li = li;
                    oldL = oldL;
                }
                view = view2;
            }
        }
        boolean wasLayoutValid = isLayoutValid();
        this.mPrivateFlags &= -4097;
        this.mPrivateFlags3 |= 4;
        if (!wasLayoutValid && isFocused()) {
            this.mPrivateFlags &= -2;
            if (canTakeFocus()) {
                clearParentsWantFocus();
            } else if (getViewRootImpl() == null || !getViewRootImpl().isInLayout()) {
                clearFocusInternal(view, true, false);
                clearParentsWantFocus();
            } else if (!hasParentWantsFocus()) {
                clearFocusInternal(view, true, false);
            }
        } else if ((this.mPrivateFlags & 1) != 0) {
            this.mPrivateFlags &= -2;
            View focused = findFocus();
            if (focused != null && !restoreDefaultFocus() && !hasParentWantsFocus()) {
                focused.clearFocusInternal(view, true, false);
            }
        }
        if ((this.mPrivateFlags3 & 134217728) != 0) {
            this.mPrivateFlags3 &= -134217729;
            notifyEnterOrExitForAutoFillIfNeeded(true);
        }
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
    }

    private boolean hasParentWantsFocus() {
        ViewParent parent = this.mParent;
        while (parent instanceof ViewGroup) {
            ViewGroup pv = (ViewGroup) parent;
            if ((pv.mPrivateFlags & 1) != 0) {
                return true;
            }
            parent = pv.mParent;
        }
        return false;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {}

    public boolean setFrame(int left, int top, int right, int bottom) {
        boolean changed = false;
        if (this.mLeft != left
                || this.mRight != right
                || this.mTop != top
                || this.mBottom != bottom) {
            changed = true;
            int drawn = this.mPrivateFlags & 32;
            int oldWidth = this.mRight - this.mLeft;
            int oldHeight = this.mBottom - this.mTop;
            int newWidth = right - left;
            int newHeight = bottom - top;
            boolean sizeChanged = (newWidth == oldWidth && newHeight == oldHeight) ? false : true;
            invalidate(sizeChanged);
            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
            this.mRenderNode.setLeftTopRightBottom(
                    this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mPrivateFlags |= 16;
            if (sizeChanged) {
                sizeChange(newWidth, newHeight, oldWidth, oldHeight);
            }
            if ((this.mViewFlags & 12) == 0 || this.mGhostView != null) {
                this.mPrivateFlags |= 32;
                invalidate(sizeChanged);
                invalidateParentCaches();
            }
            this.mPrivateFlags |= drawn;
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
        return changed;
    }

    protected boolean semSetFrame(int left, int top, int right, int bottom) {
        return setFrame(left, top, right, bottom);
    }

    public final void setLeftTopRightBottom(int left, int top, int right, int bottom) {
        setFrame(left, top, right, bottom);
    }

    private void sizeChange(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        boolean isSmall;
        if (this.mAttachInfo != null && sToolkitFrameRateViewEnablingReadOnlyFlagValue) {
            boolean isLarge = false;
            if (sToolkitFrameRateSmallUsesPercentReadOnlyFlagValue) {
                int size = newWidth * newHeight;
                float percent = size / this.mAttachInfo.mDisplayPixelCount;
                isSmall = percent <= FRAME_RATE_SIZE_PERCENTAGE_THRESHOLD;
                if (CoreRune.FW_DVRR_TOOLKIT_SUPPORT_HIGH_FRAME_RATE) {
                    isSmall = percent >= FRAME_RATE_LAGRE_SIZE_PERCENTAGE_THRESHOLD;
                    isLarge = isSmall;
                }
            } else {
                float density = this.mAttachInfo.mDensity;
                int narrowSize = (int) (FRAME_RATE_NARROW_SIZE_DP * density);
                int smallSize = (int) (FRAME_RATE_SQUARE_SMALL_SIZE_DP * density);
                if (newWidth > narrowSize
                        && newHeight > narrowSize
                        && (newWidth > smallSize || newHeight > smallSize)) {
                    isSmall = false;
                }
                boolean z = CoreRune.FW_DVRR_TOOLKIT_SUPPORT_HIGH_FRAME_RATE;
                isLarge = false;
                isSmall = isSmall;
            }
            if (isSmall) {
                if (sToolkitFrameRateBySizeReadOnlyFlagValue) {
                    category = 2;
                }
                this.mSizeBasedFrameRateCategoryAndReason = 16777216 | category;
            } else if (CoreRune.FW_DVRR_TOOLKIT_SUPPORT_HIGH_FRAME_RATE
                    && isLarge
                    && this.mAttachInfo.mViewRootImpl != null
                    && this.mAttachInfo.mViewRootImpl.mWindowAttributes.type != 2011
                    && (this instanceof WebView)) {
                this.mSizeBasedFrameRateCategoryAndReason = 218103813;
            } else {
                category = sToolkitFrameRateDefaultNormalReadOnlyFlagValue ? 3 : 5;
                this.mSizeBasedFrameRateCategoryAndReason = 50331648 | category;
            }
            this.mPrivateFlags4 |= 268435456;
        }
        onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().setRight(newWidth);
            this.mOverlay.getOverlayView().setBottom(newHeight);
        }
        if (!sCanFocusZeroSized
                && isLayoutValid()
                && (!(this.mParent instanceof ViewGroup)
                        || !((ViewGroup) this.mParent).isLayoutSuppressed())) {
            if (newWidth <= 0 || newHeight <= 0) {
                if (hasFocus()) {
                    clearFocus();
                    if (this.mParent instanceof ViewGroup) {
                        ((ViewGroup) this.mParent).clearFocusedInCluster();
                    }
                }
                clearAccessibilityFocus();
            } else if ((oldWidth <= 0 || oldHeight <= 0)
                    && this.mParent != null
                    && canTakeFocus()) {
                this.mParent.focusableViewAvailable(this);
            }
        }
        rebuildOutline();
        if (onCheckIsTextEditor() || this.mHandwritingDelegatorCallback != null) {
            setHandwritingArea(new Rect(0, 0, newWidth, newHeight));
        }
    }

    protected void onFinishInflate() {}

    public Resources getResources() {
        return this.mResources;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (verifyDrawable(drawable)) {
            Rect dirty = drawable.getDirtyBounds();
            int scrollX = this.mScrollX;
            int scrollY = this.mScrollY;
            invalidate(
                    dirty.left + scrollX,
                    dirty.top + scrollY,
                    dirty.right + scrollX,
                    dirty.bottom + scrollY);
            rebuildOutline();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (verifyDrawable(who) && what != null) {
            long delay = when - SystemClock.uptimeMillis();
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(
                        1, what, who, Choreographer.subtractFrameDelay(delay));
            } else {
                getRunQueue().postDelayed(what, delay);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (verifyDrawable(who) && what != null) {
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, what, who);
            }
            getRunQueue().removeCallbacks(what);
        }
    }

    public void unscheduleDrawable(Drawable who) {
        if (this.mAttachInfo != null && who != null) {
            this.mAttachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, null, who);
        }
    }

    protected void resolveDrawables() {
        if (!isLayoutDirectionResolved() && getRawLayoutDirection() == 2) {
            return;
        }
        int layoutDirection =
                isLayoutDirectionResolved() ? getLayoutDirection() : getRawLayoutDirection();
        if (this.mBackground != null) {
            this.mBackground.setLayoutDirection(layoutDirection);
        }
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.setLayoutDirection(layoutDirection);
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.setLayoutDirection(layoutDirection);
        }
        this.mPrivateFlags2 |= 1073741824;
        onResolveDrawables(layoutDirection);
    }

    boolean areDrawablesResolved() {
        return (this.mPrivateFlags2 & 1073741824) == 1073741824;
    }

    public void onResolveDrawables(int layoutDirection) {}

    protected void resetResolvedDrawables() {
        resetResolvedDrawablesInternal();
    }

    void resetResolvedDrawablesInternal() {
        this.mPrivateFlags2 &= -1073741825;
    }

    protected boolean verifyDrawable(Drawable who) {
        return who == this.mBackground
                || (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable == who)
                || this.mDefaultFocusHighlight == who;
    }

    protected void drawableStateChanged() {
        Drawable scrollBar;
        int[] state = getDrawableState();
        boolean changed = false;
        Drawable bg = this.mBackground;
        if (bg != null && bg.isStateful()) {
            changed = false | bg.setState(state);
        }
        Drawable hl = this.mDefaultFocusHighlight;
        if (hl != null && hl.isStateful()) {
            changed |= hl.setState(state);
        }
        Drawable fg = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (fg != null && fg.isStateful()) {
            changed |= fg.setState(state);
        }
        if (this.mScrollCache != null
                && (scrollBar = this.mScrollCache.scrollBar) != null
                && scrollBar.isStateful()) {
            changed |= scrollBar.setState(state) && this.mScrollCache.state != 0;
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.setState(state);
        }
        if (!isAggregatedVisible()) {
            jumpDrawablesToCurrentState();
        }
        if (changed) {
            invalidate();
        }
    }

    public void drawableHotspotChanged(float x, float y) {
        if (this.mBackground != null) {
            this.mBackground.setHotspot(x, y);
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.setHotspot(x, y);
        }
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.setHotspot(x, y);
        }
        dispatchDrawableHotspotChanged(x, y);
    }

    public void dispatchDrawableHotspotChanged(float x, float y) {}

    public void refreshDrawableState() {
        this.mPrivateFlags |= 1024;
        drawableStateChanged();
        ViewParent parent = this.mParent;
        if (parent != null) {
            parent.childDrawableStateChanged(this);
        }
    }

    boolean isCarLifeDisplay() {
        return (getDisplay() == null || (getDisplay().getFlags() & 1048576) == 0) ? false : true;
    }

    private Drawable getDefaultFocusHighlightDrawable() {
        if (this.mDefaultFocusHighlightCache == null && this.mContext != null) {
            if (CoreRune.BAIDU_CARLIFE && isCarLifeDisplay()) {
                this.mDefaultFocusHighlightCache =
                        this.mContext.getDrawable(R.drawable.carlife_selector_background_focused);
            } else {
                int[] attrs = {16843534};
                TypedArray ta = this.mContext.obtainStyledAttributes(attrs);
                this.mDefaultFocusHighlightCache = ta.getDrawable(0);
                ta.recycle();
            }
        }
        return this.mDefaultFocusHighlightCache;
    }

    private void setDefaultFocusHighlight(Drawable highlight) {
        this.mDefaultFocusHighlight = highlight;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (highlight != null) {
            if ((this.mPrivateFlags & 128) != 0) {
                this.mPrivateFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            highlight.setLayoutDirection(getLayoutDirection());
            if (highlight.isStateful()) {
                highlight.setState(getDrawableState());
            }
            if (isAttachedToWindow()) {
                highlight.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
            highlight.setCallback(this);
        } else if ((this.mViewFlags & 128) != 0
                && this.mBackground == null
                && (this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
            this.mPrivateFlags |= 128;
        }
        invalidate();
    }

    public boolean isDefaultFocusHighlightNeeded(Drawable background, Drawable foreground) {
        boolean lackFocusState =
                ((background != null
                                        && background.isStateful()
                                        && background.hasFocusStateSpecified())
                                || (foreground != null
                                        && foreground.isStateful()
                                        && foreground.hasFocusStateSpecified()))
                        ? false
                        : true;
        return !isInTouchMode()
                && getDefaultFocusHighlightEnabled()
                && lackFocusState
                && isAttachedToWindow()
                && sUseDefaultFocusHighlight;
    }

    private void switchDefaultFocusHighlight() {
        if (isFocused()) {
            boolean needed =
                    isDefaultFocusHighlightNeeded(
                            this.mBackground,
                            this.mForegroundInfo == null ? null : this.mForegroundInfo.mDrawable);
            boolean active = this.mDefaultFocusHighlight != null;
            if (needed && !active) {
                setDefaultFocusHighlight(getDefaultFocusHighlightDrawable());
            } else if (!needed && active) {
                setDefaultFocusHighlight(null);
            }
        }
    }

    private void drawDefaultFocusHighlight(Canvas canvas) {
        if (this.mDefaultFocusHighlight != null && isFocused()) {
            if (this.mDefaultFocusHighlightSizeChanged) {
                this.mDefaultFocusHighlightSizeChanged = false;
                int l = this.mScrollX;
                int r = (this.mRight + l) - this.mLeft;
                int t = this.mScrollY;
                int b = (this.mBottom + t) - this.mTop;
                this.mDefaultFocusHighlight.setBounds(l, t, r, b);
            }
            this.mDefaultFocusHighlight.draw(canvas);
        }
    }

    public final int[] getDrawableState() {
        if (this.mDrawableState != null && (this.mPrivateFlags & 1024) == 0) {
            return this.mDrawableState;
        }
        this.mDrawableState = onCreateDrawableState(0);
        this.mPrivateFlags &= -1025;
        return this.mDrawableState;
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        if ((this.mViewFlags & 4194304) == 4194304 && (this.mParent instanceof View)) {
            return ((View) this.mParent).onCreateDrawableState(extraSpace);
        }
        int privateFlags = this.mPrivateFlags;
        int viewStateIndex = (privateFlags & 16384) != 0 ? 0 | 16 : 0;
        if ((this.mViewFlags & 32) == 0) {
            viewStateIndex |= 8;
        }
        if (isFocused()) {
            viewStateIndex |= 4;
        }
        if ((privateFlags & 4) != 0) {
            viewStateIndex |= 2;
        }
        if (hasWindowFocus()) {
            viewStateIndex |= 1;
        }
        if ((1073741824 & privateFlags) != 0) {
            viewStateIndex |= 32;
        }
        if (this.mAttachInfo != null
                && this.mAttachInfo.mHardwareAccelerationRequested
                && !CoreRune.GFW_DEBUG_DISABLE_HWRENDERING) {
            viewStateIndex |= 64;
        }
        if ((268435456 & privateFlags) != 0) {
            viewStateIndex |= 128;
        }
        int privateFlags2 = this.mPrivateFlags2;
        if ((privateFlags2 & 1) != 0) {
            viewStateIndex |= 256;
        }
        if ((privateFlags2 & 2) != 0) {
            viewStateIndex |= 512;
        }
        if (CoreRune.FW_SPEN_HOVER && (this.mSemViewFlags & 1) != 0) {
            viewStateIndex |= 1024;
        }
        int[] drawableState = StateSet.get(viewStateIndex);
        if (extraSpace == 0) {
            return drawableState;
        }
        if (drawableState != null) {
            int[] fullState = new int[drawableState.length + extraSpace];
            System.arraycopy(drawableState, 0, fullState, 0, drawableState.length);
            return fullState;
        }
        return new int[extraSpace];
    }

    protected static int[] mergeDrawableStates(int[] baseState, int[] additionalState) {
        int N = baseState.length;
        int i = N - 1;
        while (i >= 0 && baseState[i] == 0) {
            i--;
        }
        System.arraycopy(additionalState, 0, baseState, i + 1, additionalState.length);
        return baseState;
    }

    public void jumpDrawablesToCurrentState() {
        if (this.mBackground != null) {
            this.mBackground.jumpToCurrentState();
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.jumpToCurrentState();
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.jumpToCurrentState();
        }
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.jumpToCurrentState();
        }
    }

    @RemotableViewMethod
    public void setBackgroundColor(int color) {
        if (this.mBackground instanceof ColorDrawable) {
            ((ColorDrawable) this.mBackground.mutate()).setColor(color);
            computeOpaqueFlags();
            this.mBackgroundResource = 0;
            idsUiUpdated(1);
            return;
        }
        setBackground(new ColorDrawable(color));
    }

    @RemotableViewMethod
    public void setBackgroundResource(int resid) {
        if (resid != 0 && resid == this.mBackgroundResource) {
            return;
        }
        Drawable d = null;
        if (resid != 0) {
            d = this.mContext.getDrawable(resid);
        }
        setBackground(d);
        this.mBackgroundResource = resid;
    }

    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        boolean z;
        computeOpaqueFlags();
        if (background == this.mBackground) {
            return;
        }
        boolean requestLayout = false;
        this.mBackgroundResource = 0;
        if (this.mBackground != null) {
            if (isAttachedToWindow()) {
                this.mBackground.setVisible(false, false);
            }
            this.mBackground.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        if (background != null) {
            Rect padding = sThreadLocal.get();
            if (padding == null) {
                padding = new Rect();
                sThreadLocal.set(padding);
            }
            resetResolvedDrawablesInternal();
            background.setLayoutDirection(getLayoutDirection());
            if (background.getPadding(padding)) {
                resetResolvedPaddingInternal();
                switch (background.getLayoutDirection()) {
                    case 1:
                        this.mUserPaddingLeftInitial = padding.right;
                        this.mUserPaddingRightInitial = padding.left;
                        internalSetPadding(
                                padding.right, padding.top, padding.left, padding.bottom);
                        break;
                    default:
                        this.mUserPaddingLeftInitial = padding.left;
                        this.mUserPaddingRightInitial = padding.right;
                        internalSetPadding(
                                padding.left, padding.top, padding.right, padding.bottom);
                        break;
                }
                this.mLeftPaddingDefined = false;
                this.mRightPaddingDefined = false;
            }
            if (this.mBackground == null
                    || this.mBackground.getMinimumHeight() != background.getMinimumHeight()
                    || this.mBackground.getMinimumWidth() != background.getMinimumWidth()) {
                requestLayout = true;
            }
            this.mBackground = background;
            if (background.isStateful()) {
                background.setState(getDrawableState());
            }
            if (isAttachedToWindow()) {
                if (getWindowVisibility() != 0 || !isShown()) {
                    z = false;
                } else {
                    z = true;
                }
                background.setVisible(z, false);
            }
            applyBackgroundTint();
            background.setCallback(this);
            if ((this.mPrivateFlags & 128) != 0) {
                this.mPrivateFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
                requestLayout = true;
            }
        } else {
            if (isBlurDebug()) {
                Log.i(VIEW_LOG_TAG, "setBackgroundDrawable null Callers=" + Debug.getCallers(7));
            }
            this.mBackground = null;
            if ((this.mViewFlags & 128) != 0
                    && this.mDefaultFocusHighlight == null
                    && (this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
                this.mPrivateFlags |= 128;
            }
            requestLayout = true;
        }
        computeOpaqueFlags();
        if (requestLayout) {
            requestLayout();
        }
        this.mBackgroundSizeChanged = true;
        invalidate(true);
        invalidateOutline();
        idsUiUpdated(0);
    }

    private void idsUiUpdated(int reason) {
        ActivityThread.currentActivityThread().getIdsController().uiUpdated(reason);
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    @RemotableViewMethod
    public void setBackgroundTintList(ColorStateList tint) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = tint;
        this.mBackgroundTint.mHasTintList = true;
        applyBackgroundTint();
    }

    public ColorStateList getBackgroundTintList() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintList;
        }
        return null;
    }

    public void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        BlendMode mode = null;
        if (tintMode != null) {
            mode = BlendMode.fromValue(tintMode.nativeInt);
        }
        setBackgroundTintBlendMode(mode);
    }

    @RemotableViewMethod
    public void setBackgroundTintBlendMode(BlendMode blendMode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mBlendMode = blendMode;
        this.mBackgroundTint.mHasTintMode = true;
        applyBackgroundTint();
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        if (this.mBackgroundTint != null && this.mBackgroundTint.mBlendMode != null) {
            PorterDuff.Mode porterDuffMode =
                    BlendMode.blendModeToPorterDuffMode(this.mBackgroundTint.mBlendMode);
            return porterDuffMode;
        }
        return null;
    }

    public BlendMode getBackgroundTintBlendMode() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mBlendMode;
        }
        return null;
    }

    private void applyBackgroundTint() {
        if (this.mBackground != null && this.mBackgroundTint != null) {
            TintInfo tintInfo = this.mBackgroundTint;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                this.mBackground = this.mBackground.mutate();
                if (tintInfo.mHasTintList) {
                    this.mBackground.setTintList(tintInfo.mTintList);
                }
                if (tintInfo.mHasTintMode) {
                    this.mBackground.setTintBlendMode(tintInfo.mBlendMode);
                }
                if (this.mBackground.isStateful()) {
                    this.mBackground.setState(getDrawableState());
                }
            }
        }
    }

    public Drawable getForeground() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mDrawable;
        }
        return null;
    }

    public void setForeground(Drawable foreground) {
        if (this.mForegroundInfo == null) {
            if (foreground == null) {
                return;
            } else {
                this.mForegroundInfo = new ForegroundInfo();
            }
        }
        if (foreground == this.mForegroundInfo.mDrawable) {
            return;
        }
        if (this.mForegroundInfo.mDrawable != null) {
            if (isAttachedToWindow()) {
                this.mForegroundInfo.mDrawable.setVisible(false, false);
            }
            this.mForegroundInfo.mDrawable.setCallback(null);
            unscheduleDrawable(this.mForegroundInfo.mDrawable);
        }
        this.mForegroundInfo.mDrawable = foreground;
        this.mForegroundInfo.mBoundsChanged = true;
        if (foreground != null) {
            if ((this.mPrivateFlags & 128) != 0) {
                this.mPrivateFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            foreground.setLayoutDirection(getLayoutDirection());
            if (foreground.isStateful()) {
                foreground.setState(getDrawableState());
            }
            applyForegroundTint();
            if (isAttachedToWindow()) {
                foreground.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
            foreground.setCallback(this);
        } else if ((this.mViewFlags & 128) != 0
                && this.mBackground == null
                && this.mDefaultFocusHighlight == null) {
            this.mPrivateFlags |= 128;
        }
        requestLayout();
        invalidate();
    }

    public boolean isForegroundInsidePadding() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mInsidePadding;
        }
        return true;
    }

    public int getForegroundGravity() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mGravity;
        }
        return 8388659;
    }

    public void setForegroundGravity(int gravity) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mGravity != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= Gravity.START;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            this.mForegroundInfo.mGravity = gravity;
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setForegroundTintList(ColorStateList tint) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            this.mForegroundInfo.mTintInfo = new TintInfo();
        }
        this.mForegroundInfo.mTintInfo.mTintList = tint;
        this.mForegroundInfo.mTintInfo.mHasTintList = true;
        applyForegroundTint();
    }

    public ColorStateList getForegroundTintList() {
        if (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null) {
            return null;
        }
        return this.mForegroundInfo.mTintInfo.mTintList;
    }

    public void setForegroundTintMode(PorterDuff.Mode tintMode) {
        BlendMode mode = null;
        if (tintMode != null) {
            mode = BlendMode.fromValue(tintMode.nativeInt);
        }
        setForegroundTintBlendMode(mode);
    }

    @RemotableViewMethod
    public void setForegroundTintBlendMode(BlendMode blendMode) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            this.mForegroundInfo.mTintInfo = new TintInfo();
        }
        this.mForegroundInfo.mTintInfo.mBlendMode = blendMode;
        this.mForegroundInfo.mTintInfo.mHasTintMode = true;
        applyForegroundTint();
    }

    public PorterDuff.Mode getForegroundTintMode() {
        BlendMode blendMode =
                (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null)
                        ? null
                        : this.mForegroundInfo.mTintInfo.mBlendMode;
        if (blendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public BlendMode getForegroundTintBlendMode() {
        if (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null) {
            return null;
        }
        return this.mForegroundInfo.mTintInfo.mBlendMode;
    }

    private void applyForegroundTint() {
        if (this.mForegroundInfo != null
                && this.mForegroundInfo.mDrawable != null
                && this.mForegroundInfo.mTintInfo != null) {
            TintInfo tintInfo = this.mForegroundInfo.mTintInfo;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                this.mForegroundInfo.mDrawable = this.mForegroundInfo.mDrawable.mutate();
                if (tintInfo.mHasTintList) {
                    this.mForegroundInfo.mDrawable.setTintList(tintInfo.mTintList);
                }
                if (tintInfo.mHasTintMode) {
                    this.mForegroundInfo.mDrawable.setTintBlendMode(tintInfo.mBlendMode);
                }
                if (this.mForegroundInfo.mDrawable.isStateful()) {
                    this.mForegroundInfo.mDrawable.setState(getDrawableState());
                }
            }
        }
    }

    private Drawable getAutofilledDrawable() {
        if (this.mAttachInfo == null) {
            return null;
        }
        if (this.mAttachInfo.mAutofilledDrawable == null) {
            Context rootContext = getRootView().getContext();
            TypedArray a = rootContext.getTheme().obtainStyledAttributes(AUTOFILL_HIGHLIGHT_ATTR);
            int attributeResourceId = a.getResourceId(0, 0);
            this.mAttachInfo.mAutofilledDrawable = rootContext.getDrawable(attributeResourceId);
            a.recycle();
        }
        return this.mAttachInfo.mAutofilledDrawable;
    }

    private void drawAutofilledHighlight(Canvas canvas) {
        Drawable autofilledHighlight;
        if (isAutofilled()
                && !hideAutofillHighlight()
                && (autofilledHighlight = getAutofilledDrawable()) != null) {
            autofilledHighlight.setBounds(0, 0, getWidth(), getHeight());
            autofilledHighlight.draw(canvas);
        }
    }

    public void onDrawForeground(Canvas canvas) {
        onDrawScrollIndicators(canvas);
        onDrawScrollBars(canvas);
        Drawable foreground = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (foreground != null) {
            if (this.mForegroundInfo.mBoundsChanged) {
                this.mForegroundInfo.mBoundsChanged = false;
                Rect selfBounds = this.mForegroundInfo.mSelfBounds;
                Rect overlayBounds = this.mForegroundInfo.mOverlayBounds;
                if (this.mForegroundInfo.mInsidePadding) {
                    selfBounds.set(0, 0, getWidth(), getHeight());
                } else {
                    selfBounds.set(
                            getPaddingLeft(),
                            getPaddingTop(),
                            getWidth() - getPaddingRight(),
                            getHeight() - getPaddingBottom());
                }
                int ld = getLayoutDirection();
                Gravity.apply(
                        this.mForegroundInfo.mGravity,
                        foreground.getIntrinsicWidth(),
                        foreground.getIntrinsicHeight(),
                        selfBounds,
                        overlayBounds,
                        ld);
                foreground.setBounds(overlayBounds);
            }
            foreground.draw(canvas);
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        resetResolvedPaddingInternal();
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mUserPaddingLeftInitial = left;
        this.mUserPaddingRightInitial = right;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        internalSetPadding(left, top, right, bottom);
    }

    protected void internalSetPadding(int left, int top, int right, int bottom) {
        this.mUserPaddingLeft = left;
        this.mUserPaddingRight = right;
        this.mUserPaddingBottom = bottom;
        int viewFlags = this.mViewFlags;
        boolean changed = false;
        if ((viewFlags & 768) != 0) {
            if ((viewFlags & 512) != 0) {
                int offset = (viewFlags & 16777216) == 0 ? 0 : getVerticalScrollbarWidth();
                switch (this.mVerticalScrollbarPosition) {
                    case 0:
                        if (isLayoutRtl()) {
                            left += offset;
                            break;
                        } else {
                            right += offset;
                            break;
                        }
                    case 1:
                        left += offset;
                        break;
                    case 2:
                        right += offset;
                        break;
                }
            }
            if ((viewFlags & 256) != 0) {
                bottom += (viewFlags & 16777216) != 0 ? getHorizontalScrollbarHeight() : 0;
            }
        }
        if (this.mPaddingLeft != left) {
            changed = true;
            this.mPaddingLeft = left;
        }
        if (this.mPaddingTop != top) {
            changed = true;
            this.mPaddingTop = top;
        }
        if (this.mPaddingRight != right) {
            changed = true;
            this.mPaddingRight = right;
        }
        if (this.mPaddingBottom != bottom) {
            changed = true;
            this.mPaddingBottom = bottom;
        }
        if (changed) {
            requestLayout();
            invalidateOutline();
        }
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
        resetResolvedPaddingInternal();
        this.mUserPaddingStart = start;
        this.mUserPaddingEnd = end;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        switch (getLayoutDirection()) {
            case 1:
                this.mUserPaddingLeftInitial = end;
                this.mUserPaddingRightInitial = start;
                internalSetPadding(end, top, start, bottom);
                break;
            default:
                this.mUserPaddingLeftInitial = start;
                this.mUserPaddingRightInitial = end;
                internalSetPadding(start, top, end, bottom);
                break;
        }
    }

    public int getSourceLayoutResId() {
        return this.mSourceLayoutId;
    }

    public int getPaddingTop() {
        return this.mPaddingTop;
    }

    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public int getPaddingLeft() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return this.mPaddingLeft;
    }

    public int getPaddingStart() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return getLayoutDirection() == 1 ? this.mPaddingRight : this.mPaddingLeft;
    }

    public int getPaddingRight() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return this.mPaddingRight;
    }

    public int getPaddingEnd() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return getLayoutDirection() == 1 ? this.mPaddingLeft : this.mPaddingRight;
    }

    public boolean isPaddingRelative() {
        return (this.mUserPaddingStart == Integer.MIN_VALUE
                        && this.mUserPaddingEnd == Integer.MIN_VALUE)
                ? false
                : true;
    }

    Insets computeOpticalInsets() {
        return this.mBackground == null ? Insets.NONE : this.mBackground.getOpticalInsets();
    }

    public void resetPaddingToInitialValues() {
        if (isRtlCompatibilityMode()) {
            this.mPaddingLeft = this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingRightInitial;
        } else if (isLayoutRtl()) {
            this.mPaddingLeft =
                    this.mUserPaddingEnd >= 0 ? this.mUserPaddingEnd : this.mUserPaddingLeftInitial;
            this.mPaddingRight =
                    this.mUserPaddingStart >= 0
                            ? this.mUserPaddingStart
                            : this.mUserPaddingRightInitial;
        } else {
            this.mPaddingLeft =
                    this.mUserPaddingStart >= 0
                            ? this.mUserPaddingStart
                            : this.mUserPaddingLeftInitial;
            this.mPaddingRight =
                    this.mUserPaddingEnd >= 0
                            ? this.mUserPaddingEnd
                            : this.mUserPaddingRightInitial;
        }
    }

    public Insets getOpticalInsets() {
        if (this.mLayoutInsets == null) {
            this.mLayoutInsets = computeOpticalInsets();
        }
        return this.mLayoutInsets;
    }

    public void setOpticalInsets(Insets insets) {
        this.mLayoutInsets = insets;
    }

    public void setSelected(boolean selected) {
        if (((this.mPrivateFlags & 4) != 0) != selected) {
            this.mPrivateFlags = (this.mPrivateFlags & (-5)) | (selected ? 4 : 0);
            if (!selected) {
                resetPressedState();
            }
            invalidate(true);
            refreshDrawableState();
            dispatchSetSelected(selected);
            if (selected) {
                sendAccessibilityEvent(4);
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    protected void dispatchSetSelected(boolean selected) {}

    @ViewDebug.ExportedProperty
    public boolean isSelected() {
        return (this.mPrivateFlags & 4) != 0;
    }

    public void setActivated(boolean activated) {
        if (((this.mPrivateFlags & 1073741824) != 0) != activated) {
            this.mPrivateFlags =
                    (this.mPrivateFlags & (-1073741825)) | (activated ? 1073741824 : 0);
            invalidate(true);
            refreshDrawableState();
            dispatchSetActivated(activated);
        }
    }

    protected void dispatchSetActivated(boolean activated) {}

    @ViewDebug.ExportedProperty
    public boolean isActivated() {
        return (this.mPrivateFlags & 1073741824) != 0;
    }

    public ViewTreeObserver getViewTreeObserver() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mTreeObserver;
        }
        if (this.mFloatingTreeObserver == null) {
            this.mFloatingTreeObserver = new ViewTreeObserver(this.mContext);
        }
        return this.mFloatingTreeObserver;
    }

    public View getRootView() {
        View v;
        if (this.mAttachInfo != null && (v = this.mAttachInfo.mRootView) != null) {
            return v;
        }
        View parent = this;
        while (parent.mParent instanceof View) {
            parent = (View) parent.mParent;
        }
        return parent;
    }

    public boolean toGlobalMotionEvent(MotionEvent ev) {
        AttachInfo info = this.mAttachInfo;
        if (info == null) {
            return false;
        }
        Matrix m = info.mTmpMatrix;
        m.set(Matrix.IDENTITY_MATRIX);
        transformMatrixToGlobal(m);
        ev.transform(m);
        return true;
    }

    public boolean toLocalMotionEvent(MotionEvent ev) {
        AttachInfo info = this.mAttachInfo;
        if (info == null) {
            return false;
        }
        Matrix m = info.mTmpMatrix;
        m.set(Matrix.IDENTITY_MATRIX);
        transformMatrixToLocal(m);
        ev.transform(m);
        return true;
    }

    public void transformMatrixToGlobal(Matrix matrix) {
        Object obj = this.mParent;
        if (obj instanceof View) {
            View vp = (View) obj;
            vp.transformMatrixToGlobal(matrix);
            matrix.preTranslate(-vp.mScrollX, -vp.mScrollY);
        } else if (obj instanceof ViewRootImpl) {
            ViewRootImpl vr = (ViewRootImpl) obj;
            vr.transformMatrixToGlobal(matrix);
            matrix.preTranslate(0.0f, -vr.mCurScrollY);
        }
        matrix.preTranslate(this.mLeft, this.mTop);
        if (!hasIdentityMatrix()) {
            matrix.preConcat(getMatrix());
        }
    }

    public void transformMatrixToLocal(Matrix matrix) {
        Object obj = this.mParent;
        if (obj instanceof View) {
            View vp = (View) obj;
            vp.transformMatrixToLocal(matrix);
            matrix.postTranslate(vp.mScrollX, vp.mScrollY);
        } else if (obj instanceof ViewRootImpl) {
            ViewRootImpl vr = (ViewRootImpl) obj;
            vr.transformMatrixToLocal(matrix);
            matrix.postTranslate(0.0f, vr.mCurScrollY);
        }
        matrix.postTranslate(-this.mLeft, -this.mTop);
        if (!hasIdentityMatrix()) {
            matrix.postConcat(getInverseMatrix());
        }
    }

    @ViewDebug.ExportedProperty(
            category = TtmlUtils.TAG_LAYOUT,
            indexMapping = {
                @ViewDebug.IntToString(from = 0, to = "x"),
                @ViewDebug.IntToString(from = 1, to = "y")
            })
    public int[] getLocationOnScreen() {
        int[] location = new int[2];
        getLocationOnScreen(location);
        return location;
    }

    public void getLocationOnScreen(int[] outLocation) {
        getLocationInWindow(outLocation);
        AttachInfo info = this.mAttachInfo;
        if (info != null) {
            outLocation[0] = outLocation[0] + info.mWindowLeft;
            outLocation[1] = outLocation[1] + info.mWindowTop;
            info.mViewRootImpl.applyViewLocationSandboxingIfNeeded(outLocation);
        }
    }

    public void getLocationInWindow(int[] outLocation) {
        if (outLocation == null || outLocation.length < 2) {
            throw new IllegalArgumentException("outLocation must be an array of two integers");
        }
        outLocation[0] = 0;
        outLocation[1] = 0;
        transformFromViewToWindowSpace(outLocation);
    }

    public void transformFromViewToWindowSpace(int[] inOutLocation) {
        if (inOutLocation == null || inOutLocation.length < 2) {
            throw new IllegalArgumentException("inOutLocation must be an array of two integers");
        }
        if (this.mAttachInfo == null) {
            inOutLocation[1] = 0;
            inOutLocation[0] = 0;
            return;
        }
        float[] position = this.mAttachInfo.mTmpTransformLocation;
        position[0] = inOutLocation[0];
        position[1] = inOutLocation[1];
        if (!hasIdentityMatrix()) {
            getMatrix().mapPoints(position);
        }
        position[0] = position[0] + this.mLeft;
        position[1] = position[1] + this.mTop;
        ViewParent viewParent = this.mParent;
        while (viewParent instanceof View) {
            View view = (View) viewParent;
            position[0] = position[0] - view.mScrollX;
            position[1] = position[1] - view.mScrollY;
            if (!view.hasIdentityMatrix()) {
                view.getMatrix().mapPoints(position);
            }
            position[0] = position[0] + view.mLeft;
            position[1] = position[1] + view.mTop;
            viewParent = view.mParent;
        }
        if (viewParent instanceof ViewRootImpl) {
            ViewRootImpl vr = (ViewRootImpl) viewParent;
            position[1] = position[1] - vr.mCurScrollY;
        }
        inOutLocation[0] = Math.round(position[0]);
        inOutLocation[1] = Math.round(position[1]);
    }

    protected <T extends View> T findViewTraversal(int id) {
        if (id == this.mID) {
            return this;
        }
        return null;
    }

    protected <T extends View> T findViewWithTagTraversal(Object tag) {
        if (tag != null && tag.equals(this.mTag)) {
            return this;
        }
        return null;
    }

    protected <T extends View> T findViewByPredicateTraversal(
            Predicate<View> predicate, View childToSkip) {
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final <T extends View> T findViewById(int i) {
        if (i == -1) {
            return null;
        }
        return (T) findViewTraversal(i);
    }

    public final <T extends View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new IllegalArgumentException("ID does not reference a View inside this View");
        }
        return t;
    }

    public <T extends View> T findViewByAccessibilityIdTraversal(int accessibilityId) {
        if (getAccessibilityViewId() == accessibilityId) {
            return this;
        }
        return null;
    }

    public <T extends View> T findViewByAutofillIdTraversal(int autofillId) {
        if (getAutofillViewId() == autofillId) {
            return this;
        }
        return null;
    }

    public final <T extends View> T findViewWithTag(Object obj) {
        if (obj == null) {
            return null;
        }
        return (T) findViewWithTagTraversal(obj);
    }

    public final <T extends View> T findViewByPredicate(Predicate<View> predicate) {
        return (T) findViewByPredicateTraversal(predicate, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x001c, code lost:

       return r1;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final <T extends android.view.View> T findViewByPredicateInsideOut(
            android.view.View r5, java.util.function.Predicate<android.view.View> r6) {
        /*
            r4 = this;
            r0 = 0
        L1:
            android.view.View r1 = r5.findViewByPredicateTraversal(r6, r0)
            if (r1 != 0) goto L1c
            if (r5 != r4) goto La
            goto L1c
        La:
            android.view.ViewParent r2 = r5.getParent()
            if (r2 == 0) goto L1a
            boolean r3 = r2 instanceof android.view.View
            if (r3 != 0) goto L15
            goto L1a
        L15:
            r0 = r5
            r5 = r2
            android.view.View r5 = (android.view.View) r5
            goto L1
        L1a:
            r3 = 0
            return r3
        L1c:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.View.findViewByPredicateInsideOut(android.view.View,"
                    + " java.util.function.Predicate):android.view.View");
    }

    public void setId(int id) {
        this.mID = id;
        if (this.mID == -1 && this.mLabelForId != -1) {
            this.mID = generateViewId();
        }
    }

    public void setIsRootNamespace(boolean isRoot) {
        if (isRoot) {
            this.mPrivateFlags |= 8;
        } else {
            this.mPrivateFlags &= -9;
        }
    }

    public boolean isRootNamespace() {
        return (this.mPrivateFlags & 8) != 0;
    }

    @ViewDebug.CapturedViewProperty
    public int getId() {
        return this.mID;
    }

    public long getUniqueDrawingId() {
        return this.mRenderNode.getUniqueId();
    }

    @ViewDebug.ExportedProperty
    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public Object getTag(int key) {
        if (this.mKeyedTags != null) {
            return this.mKeyedTags.get(key);
        }
        return null;
    }

    public void setTag(int key, Object tag) {
        if ((key >>> 24) < 2) {
            throw new IllegalArgumentException(
                    "The key must be an application-specific resource id.");
        }
        setKeyedTag(key, tag);
    }

    public void setTagInternal(int key, Object tag) {
        if ((key >>> 24) != 1) {
            throw new IllegalArgumentException("The key must be a framework-specific resource id.");
        }
        setKeyedTag(key, tag);
    }

    private void setKeyedTag(int key, Object tag) {
        if (this.mKeyedTags == null) {
            this.mKeyedTags = new SparseArray<>(2);
        }
        this.mKeyedTags.put(key, tag);
    }

    public void debug() {
        debug(0);
    }

    protected void debug(int depth) {
        String output;
        String output2 = debugIndent(depth - 1) + "+ " + this;
        int id = getId();
        if (id != -1) {
            output2 = output2 + " (id=" + id + NavigationBarInflaterView.KEY_CODE_END;
        }
        Object tag = getTag();
        if (tag != null) {
            output2 = output2 + " (tag=" + tag + NavigationBarInflaterView.KEY_CODE_END;
        }
        Log.d(VIEW_LOG_TAG, output2);
        if ((this.mPrivateFlags & 2) != 0) {
            Log.d(VIEW_LOG_TAG, debugIndent(depth) + " FOCUSED");
        }
        Log.d(
                VIEW_LOG_TAG,
                debugIndent(depth)
                        + "frame={"
                        + this.mLeft
                        + ", "
                        + this.mTop
                        + ", "
                        + this.mRight
                        + ", "
                        + this.mBottom
                        + "} scroll={"
                        + this.mScrollX
                        + ", "
                        + this.mScrollY
                        + "} ");
        if (this.mPaddingLeft != 0
                || this.mPaddingTop != 0
                || this.mPaddingRight != 0
                || this.mPaddingBottom != 0) {
            Log.d(
                    VIEW_LOG_TAG,
                    debugIndent(depth)
                            + "padding={"
                            + this.mPaddingLeft
                            + ", "
                            + this.mPaddingTop
                            + ", "
                            + this.mPaddingRight
                            + ", "
                            + this.mPaddingBottom
                            + "}");
        }
        Log.d(
                VIEW_LOG_TAG,
                debugIndent(depth)
                        + "mMeasureWidth="
                        + this.mMeasuredWidth
                        + " mMeasureHeight="
                        + this.mMeasuredHeight);
        String output3 = debugIndent(depth);
        if (this.mLayoutParams == null) {
            output = output3 + "BAD! no layout params";
        } else {
            output = this.mLayoutParams.debug(output3);
        }
        Log.d(VIEW_LOG_TAG, output);
        Log.d(VIEW_LOG_TAG, ((debugIndent(depth) + "flags={") + printFlags(this.mViewFlags)) + "}");
        Log.d(
                VIEW_LOG_TAG,
                ((debugIndent(depth) + "privateFlags={") + printPrivateFlags(this.mPrivateFlags))
                        + "}");
    }

    protected static String debugIndent(int depth) {
        StringBuilder spaces = new StringBuilder(((depth * 2) + 3) * 2);
        for (int i = 0; i < (depth * 2) + 3; i++) {
            spaces.append(' ').append(' ');
        }
        return spaces.toString();
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public int getBaseline() {
        return -1;
    }

    public boolean isInLayout() {
        ViewRootImpl viewRoot = getViewRootImpl();
        return viewRoot != null && viewRoot.isInLayout();
    }

    private void printStackStrace(String name) {
        Log.d(VIEW_LOG_TAG, "---- ST:" + name);
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        int endIndex = Math.min(stackTraceElements.length, 1 + 20);
        for (int i = 1; i < endIndex; i++) {
            StackTraceElement s = stackTraceElements[i];
            sb.append(s.getMethodName())
                    .append(NavigationBarInflaterView.KEY_CODE_START)
                    .append(s.getFileName())
                    .append(":")
                    .append(s.getLineNumber())
                    .append(") <- ");
        }
        Log.d(VIEW_LOG_TAG, name + ": " + ((Object) sb));
    }

    public void requestLayout() {
        if (isRelayoutTracingEnabled()) {
            Trace.instantForTrack(
                    4096L, "requestLayoutTracing", this.mTracingStrings.classSimpleName);
            printStackStrace(this.mTracingStrings.requestLayoutStacktracePrefix);
        }
        if (this.mMeasureCache != null) {
            this.mMeasureCache.clear();
        }
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRequestingLayout == null) {
            ViewRootImpl viewRoot = getViewRootImpl();
            if (viewRoot != null
                    && viewRoot.isInLayout()
                    && !viewRoot.requestLayoutDuringLayout(this)) {
                return;
            } else {
                this.mAttachInfo.mViewRequestingLayout = this;
            }
        }
        this.mPrivateFlags |= 4096;
        this.mPrivateFlags |= Integer.MIN_VALUE;
        if (this.mParent != null && !this.mParent.isLayoutRequested()) {
            this.mParent.requestLayout();
        }
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRequestingLayout == this) {
            this.mAttachInfo.mViewRequestingLayout = null;
        }
    }

    public void forceLayout() {
        if (this.mMeasureCache != null) {
            this.mMeasureCache.clear();
        }
        this.mPrivateFlags |= 4096;
        this.mPrivateFlags |= Integer.MIN_VALUE;
    }

    private String getMeasureSpecMode(int measureSpec) {
        int ms = MeasureSpec.getMode(measureSpec);
        new String();
        switch (ms) {
            case Integer.MIN_VALUE:
                return "AT_MOST";
            case 0:
                return "UNSPECIFIED";
            case 1073741824:
                return "EXACTLY";
            default:
                return null;
        }
    }

    public final void measure(int widthMeasureSpec, int heightMeasureSpec) {
        int cacheIndex;
        int widthMeasureSpec2 = widthMeasureSpec;
        int heightMeasureSpec2 = heightMeasureSpec;
        if (ViewRootImpl.DEBUG_MEASURE) {
            Trace.traceBegin(
                    8L,
                    "measure for "
                            + getClass().getSimpleName()
                            + " w:"
                            + widthMeasureSpec2
                            + "/"
                            + getMeasureSpecMode(widthMeasureSpec)
                            + "/"
                            + MeasureSpec.getSize(widthMeasureSpec)
                            + " h:"
                            + heightMeasureSpec2
                            + "/"
                            + getMeasureSpecMode(heightMeasureSpec2)
                            + "/"
                            + MeasureSpec.getSize(heightMeasureSpec)
                            + " "
                            + this);
        }
        boolean optical = isLayoutModeOptical(this);
        if (optical != isLayoutModeOptical(this.mParent)) {
            Insets insets = getOpticalInsets();
            int oWidth = insets.left + insets.right;
            int oHeight = insets.top + insets.bottom;
            widthMeasureSpec2 = MeasureSpec.adjust(widthMeasureSpec2, optical ? -oWidth : oWidth);
            heightMeasureSpec2 =
                    MeasureSpec.adjust(heightMeasureSpec2, optical ? -oHeight : oHeight);
        }
        long key = (widthMeasureSpec2 << 32) | (heightMeasureSpec2 & 4294967295L);
        if (this.mMeasureCache == null) {
            this.mMeasureCache = new LongSparseLongArray(2);
        }
        boolean forceLayout = (this.mPrivateFlags & 4096) == 4096;
        boolean specChanged =
                (widthMeasureSpec2 == this.mOldWidthMeasureSpec
                                && heightMeasureSpec2 == this.mOldHeightMeasureSpec)
                        ? false
                        : true;
        boolean isSpecExactly =
                MeasureSpec.getMode(widthMeasureSpec2) == 1073741824
                        && MeasureSpec.getMode(heightMeasureSpec2) == 1073741824;
        boolean matchesSpecSize =
                getMeasuredWidth() == MeasureSpec.getSize(widthMeasureSpec2)
                        && getMeasuredHeight() == MeasureSpec.getSize(heightMeasureSpec2);
        boolean needsLayout =
                specChanged && !(!sAlwaysRemeasureExactly && isSpecExactly && matchesSpecSize);
        if (forceLayout || needsLayout) {
            this.mPrivateFlags &= -2049;
            resolveRtlPropertiesIfNeeded();
            if (sUseMeasureCacheDuringForceLayoutFlagValue) {
                cacheIndex = this.mMeasureCache.indexOfKey(key);
            } else {
                cacheIndex = forceLayout ? -1 : this.mMeasureCache.indexOfKey(key);
            }
            if (cacheIndex < 0) {
                if (isTraversalTracingEnabled()) {
                    Trace.beginSection(this.mTracingStrings.onMeasure);
                }
                if (com.android.internal.hidden_from_bootclasspath.android.os.Flags
                        .adpfMeasureDuringInputEventBoost()) {
                    boolean notifyRenderer = hasExpensiveMeasuresDuringInputEvent();
                    if (notifyRenderer) {
                        Trace.traceBegin(8L, "CPU_LOAD_UP: hasExpensiveMeasuresDuringInputEvent");
                        getViewRootImpl().notifyRendererOfExpensiveFrame();
                        Trace.traceEnd(8L);
                    }
                }
                onMeasure(widthMeasureSpec2, heightMeasureSpec2);
                if (isTraversalTracingEnabled()) {
                    Trace.endSection();
                }
                this.mPrivateFlags3 &= -9;
            } else {
                long value = this.mMeasureCache.valueAt(cacheIndex);
                setMeasuredDimensionRaw((int) (value >> 32), (int) value);
                this.mPrivateFlags3 |= 8;
            }
            if ((this.mPrivateFlags & 2048) == 2048) {
                this.mPrivateFlags |= 8192;
            } else {
                throw new IllegalStateException(
                        "View with id "
                                + getId()
                                + ": "
                                + getClass().getName()
                                + "#onMeasure() did not set the measured dimension by calling"
                                + " setMeasuredDimension()");
            }
        }
        this.mOldWidthMeasureSpec = widthMeasureSpec2;
        this.mOldHeightMeasureSpec = heightMeasureSpec2;
        this.mMeasureCache.put(
                key, (this.mMeasuredWidth << 32) | (this.mMeasuredHeight & 4294967295L));
        if (ViewRootImpl.DEBUG_MEASURE) {
            Trace.traceEnd(8L);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    public final void setMeasuredDimension(int measuredWidth, int measuredHeight) {
        boolean optical = isLayoutModeOptical(this);
        if (optical != isLayoutModeOptical(this.mParent)) {
            Insets insets = getOpticalInsets();
            int opticalWidth = insets.left + insets.right;
            int opticalHeight = insets.top + insets.bottom;
            measuredWidth += optical ? opticalWidth : -opticalWidth;
            measuredHeight += optical ? opticalHeight : -opticalHeight;
        }
        setMeasuredDimensionRaw(measuredWidth, measuredHeight);
    }

    private void setMeasuredDimensionRaw(int measuredWidth, int measuredHeight) {
        this.mMeasuredWidth = measuredWidth;
        this.mMeasuredHeight = measuredHeight;
        this.mPrivateFlags |= 2048;
    }

    public static int combineMeasuredStates(int curState, int newState) {
        return curState | newState;
    }

    public static int resolveSize(int size, int measureSpec) {
        return resolveSizeAndState(size, measureSpec, 0) & 16777215;
    }

    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
                if (specSize < size) {
                    result = 16777216 | specSize;
                    break;
                } else {
                    result = size;
                    break;
                }
            case 1073741824:
                result = specSize;
                break;
            default:
                result = size;
                break;
        }
        return ((-16777216) & childMeasuredState) | result;
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
            case 1073741824:
                return specSize;
            case 0:
                return size;
            default:
                return size;
        }
    }

    protected int getSuggestedMinimumHeight() {
        return this.mBackground == null
                ? this.mMinHeight
                : Math.max(this.mMinHeight, this.mBackground.getMinimumHeight());
    }

    protected int getSuggestedMinimumWidth() {
        return this.mBackground == null
                ? this.mMinWidth
                : Math.max(this.mMinWidth, this.mBackground.getMinimumWidth());
    }

    public int getMinimumHeight() {
        return this.mMinHeight;
    }

    @RemotableViewMethod
    public void setMinimumHeight(int minHeight) {
        this.mMinHeight = minHeight;
        requestLayout();
    }

    public int getMinimumWidth() {
        return this.mMinWidth;
    }

    @RemotableViewMethod
    public void setMinimumWidth(int minWidth) {
        this.mMinWidth = minWidth;
        requestLayout();
    }

    public Animation getAnimation() {
        return this.mCurrentAnimation;
    }

    public void startAnimation(Animation animation) {
        animation.setStartTime(-1L);
        setAnimation(animation);
        invalidateParentCaches();
        invalidate(true);
    }

    public void clearAnimation() {
        if (this.mCurrentAnimation != null) {
            this.mCurrentAnimation.detach();
        }
        this.mCurrentAnimation = null;
        invalidateParentIfNeeded();
    }

    public void setAnimation(Animation animation) {
        this.mCurrentAnimation = animation;
        if (animation != null) {
            if (this.mAttachInfo != null
                    && this.mAttachInfo.mDisplayState == 1
                    && animation.getStartTime() == -1) {
                animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());
            }
            animation.reset();
        }
    }

    protected void onAnimationStart() {
        this.mPrivateFlags |= 65536;
    }

    protected void onAnimationEnd() {
        this.mPrivateFlags &= -65537;
    }

    protected boolean onSetAlpha(int alpha) {
        return false;
    }

    public boolean gatherTransparentRegion(Region region) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (region != null && attachInfo != null) {
            int pflags = this.mPrivateFlags;
            if ((pflags & 128) == 0) {
                int[] location = attachInfo.mTransparentLocation;
                getLocationInWindow(location);
                int shadowOffset = getZ() > 0.0f ? (int) getZ() : 0;
                region.op(
                        location[0] - shadowOffset,
                        location[1] - shadowOffset,
                        ((location[0] + this.mRight) - this.mLeft) + shadowOffset,
                        ((location[1] + this.mBottom) - this.mTop) + (shadowOffset * 3),
                        Region.Op.DIFFERENCE);
            } else {
                if (this.mBackground != null && this.mBackground.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mBackground, region);
                }
                if (this.mForegroundInfo != null
                        && this.mForegroundInfo.mDrawable != null
                        && this.mForegroundInfo.mDrawable.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mForegroundInfo.mDrawable, region);
                }
                if (this.mDefaultFocusHighlight != null
                        && this.mDefaultFocusHighlight.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mDefaultFocusHighlight, region);
                }
            }
        }
        return true;
    }

    public void playSoundEffect(int soundConstant) {
        if (this.mAttachInfo == null
                || this.mAttachInfo.mRootCallbacks == null
                || !isSoundEffectsEnabled()) {
            return;
        }
        this.mAttachInfo.mRootCallbacks.playSoundEffect(soundConstant);
    }

    public boolean performHapticFeedback(int feedbackConstant) {
        return performHapticFeedback(feedbackConstant, 0);
    }

    public boolean performHapticFeedback(int feedbackConstant, int flags) {
        if (feedbackConstant == -1 || this.mAttachInfo == null) {
            return false;
        }
        if ((flags & 1) == 0 && !isHapticFeedbackEnabled()) {
            return false;
        }
        boolean always = (flags & 2) != 0;
        boolean fromIme = false;
        if (this.mAttachInfo.mViewRootImpl != null) {
            fromIme = this.mAttachInfo.mViewRootImpl.mWindowAttributes.type == 2011;
        }
        if (android.os.vibrator.Flags.useVibratorHapticFeedback()) {
            if (!this.mAttachInfo.canPerformHapticFeedback()) {
                return false;
            }
            if (this.mContext.getDisplayId() != 0
                    && this.mContext.getResources().getConfiguration().isDesktopModeEnabled()) {
                return false;
            }
            getSystemVibrator()
                    .performHapticFeedback(
                            feedbackConstant, always, "View#performHapticFeedback", fromIme);
            return true;
        }
        return this.mAttachInfo.mRootCallbacks.performHapticFeedback(
                feedbackConstant, always, fromIme);
    }

    private Vibrator getSystemVibrator() {
        if (this.mVibrator != null) {
            return this.mVibrator;
        }
        Vibrator vibrator = (Vibrator) this.mContext.getSystemService(Vibrator.class);
        this.mVibrator = vibrator;
        return vibrator;
    }

    @Deprecated
    public void setSystemUiVisibility(int visibility) {
        if (visibility != this.mSystemUiVisibility) {
            this.mSystemUiVisibility = visibility;
            if (this.mParent != null
                    && this.mAttachInfo != null
                    && !this.mAttachInfo.mRecomputeGlobalAttributes) {
                this.mParent.recomputeViewAttributes(this);
            }
        }
    }

    @Deprecated
    public int getSystemUiVisibility() {
        return this.mSystemUiVisibility;
    }

    @Deprecated
    public int getWindowSystemUiVisibility() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mSystemUiVisibility;
        }
        return 0;
    }

    @Deprecated
    public void onWindowSystemUiVisibilityChanged(int visible) {}

    @Deprecated
    public void dispatchWindowSystemUiVisiblityChanged(int visible) {
        onWindowSystemUiVisibilityChanged(visible);
    }

    @Deprecated
    public void setOnSystemUiVisibilityChangeListener(OnSystemUiVisibilityChangeListener l) {
        getListenerInfo().mOnSystemUiVisibilityChangeListener = l;
        if (this.mParent != null
                && this.mAttachInfo != null
                && !this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mParent.recomputeViewAttributes(this);
        }
    }

    @Deprecated
    public void dispatchSystemUiVisibilityChanged(int visibility) {
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnSystemUiVisibilityChangeListener != null) {
            li.mOnSystemUiVisibilityChangeListener.onSystemUiVisibilityChange(
                    visibility & PUBLIC_STATUS_BAR_VISIBILITY_MASK);
        }
    }

    boolean updateLocalSystemUiVisibility(int localValue, int localChanges) {
        int val = (this.mSystemUiVisibility & (~localChanges)) | (localValue & localChanges);
        if (val != this.mSystemUiVisibility) {
            setSystemUiVisibility(val);
            return true;
        }
        return false;
    }

    public void setDisabledSystemUiVisibility(int flags) {
        if (this.mAttachInfo != null && this.mAttachInfo.mDisabledSystemUiVisibility != flags) {
            this.mAttachInfo.mDisabledSystemUiVisibility = flags;
            if (this.mParent != null) {
                this.mParent.recomputeViewAttributes(this);
            }
        }
    }

    public void onSystemBarAppearanceChanged(int appearance) {}

    public static class DragShadowBuilder {
        private Point mSemLastTouchPoint;
        private final WeakReference<View> mView;

        public DragShadowBuilder(View view) {
            this.mSemLastTouchPoint = null;
            this.mView = new WeakReference<>(view);
        }

        public DragShadowBuilder() {
            this.mSemLastTouchPoint = null;
            this.mView = new WeakReference<>(null);
        }

        public final View getView() {
            return this.mView.get();
        }

        public void semSetLastTouchPoint(float x, float y) {
            this.mSemLastTouchPoint = new Point((int) x, (int) y);
        }

        public Point semGetLastTouchPoint() {
            return this.mSemLastTouchPoint;
        }

        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            View view = this.mView.get();
            if (view != null) {
                outShadowSize.set(view.getWidth(), view.getHeight());
                outShadowTouchPoint.set(outShadowSize.x / 2, outShadowSize.y / 2);
            } else {
                Log.e(View.VIEW_LOG_TAG, "Asked for drag thumb metrics but no view");
            }
        }

        public void onDrawShadow(Canvas canvas) {
            View view = this.mView.get();
            if (view != null) {
                view.draw(canvas);
            } else {
                Log.e(View.VIEW_LOG_TAG, "Asked to draw drag shadow but no view");
            }
        }
    }

    public final boolean semUpdateClipData(ClipData data) {
        if (data == null) {
            Log.w(VIEW_LOG_TAG, "updateClipData: data is null");
            return false;
        }
        if (this.mAttachInfo == null) {
            Log.w(VIEW_LOG_TAG, "updateClipData called on a detached view.");
            return false;
        }
        if (!this.mAttachInfo.mViewRootImpl.mSurface.isValid()) {
            Log.w(VIEW_LOG_TAG, "updateClipData called with an invalid surface.");
            return false;
        }
        if (this.mAttachInfo.mDragToken == null || this.mAttachInfo.mDragSurface == null) {
            Log.w(VIEW_LOG_TAG, "updateClipData called without a drag start");
            return false;
        }
        data.prepareToLeaveProcess(true);
        try {
            this.mAttachInfo.mSession.performClipDataUpdate(data);
            return true;
        } catch (Exception e) {
            Log.e(VIEW_LOG_TAG, "Unable to update ClipData : ", e);
            return false;
        }
    }

    @Deprecated
    public final boolean startDrag(
            ClipData data, DragShadowBuilder shadowBuilder, Object myLocalState, int flags) {
        return startDragAndDrop(data, shadowBuilder, myLocalState, flags);
    }

    public final boolean startDragAndDrop(
            ClipData data, DragShadowBuilder shadowBuilder, Object myLocalState, int flags) {
        return startDragAndDrop(data, shadowBuilder, myLocalState, flags, null, null);
    }

    public final boolean hidden_startDragAndDrop(
            ClipData data,
            DragShadowBuilder shadowBuilder,
            Object myLocalState,
            int flags,
            RectF selectedArea,
            Point location) {
        return startDragAndDrop(data, shadowBuilder, myLocalState, flags, selectedArea, location);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean startDragAndDrop(
            android.content.ClipData r38,
            android.view.View.DragShadowBuilder r39,
            java.lang.Object r40,
            int r41,
            android.graphics.RectF r42,
            android.graphics.Point r43) {
        /*
            Method dump skipped, instructions count: 1394
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.View.startDragAndDrop(android.content.ClipData,"
                    + " android.view.View$DragShadowBuilder, java.lang.Object, int,"
                    + " android.graphics.RectF, android.graphics.Point):boolean");
    }

    static boolean hasActivityPendingIntents(ClipData data) {
        int size = data.getItemCount();
        for (int i = 0; i < size; i++) {
            ClipData.Item item = data.getItemAt(i);
            if (item.getIntentSender() != null) {
                PendingIntent pi = new PendingIntent(item.getIntentSender().getTarget());
                if (pi.isActivity()) {
                    return true;
                }
            }
        }
        return false;
    }

    static void cleanUpPendingIntents(ClipData data) {
        int size = data.getItemCount();
        for (int i = 0; i < size; i++) {
            ClipData.Item item = data.getItemAt(i);
            if (item.getIntentSender() != null) {
                PendingIntent pi = new PendingIntent(item.getIntentSender().getTarget());
                pi.cancel();
            }
        }
    }

    void setAccessibilityDragStarted(boolean started) {
        int pflags4;
        int pflags42 = this.mPrivateFlags4;
        if (started) {
            pflags4 = pflags42 | 32768;
        } else {
            pflags4 = pflags42 & (-32769);
        }
        if (pflags4 != this.mPrivateFlags4) {
            this.mPrivateFlags4 = pflags4;
            sendWindowContentChangedAccessibilityEvent(0);
        }
    }

    private boolean startedSystemDragForAccessibility() {
        return (this.mPrivateFlags4 & 32768) != 0;
    }

    public final void cancelDragAndDrop() {
        if (this.mAttachInfo == null) {
            Log.w(VIEW_LOG_TAG, "cancelDragAndDrop called on a detached view.");
            return;
        }
        if (this.mAttachInfo.mDragToken != null) {
            try {
                this.mAttachInfo.mSession.cancelDragAndDrop(this.mAttachInfo.mDragToken, false);
            } catch (Exception e) {
                Log.e(VIEW_LOG_TAG, "Unable to cancel drag", e);
            }
            this.mAttachInfo.mDragToken = null;
            return;
        }
        Log.e(VIEW_LOG_TAG, "No active drag to cancel");
    }

    public final void updateDragShadow(DragShadowBuilder shadowBuilder) {
        Canvas canvas;
        if (this.mAttachInfo == null) {
            Log.w(VIEW_LOG_TAG, "updateDragShadow called on a detached view.");
            return;
        }
        if (this.mAttachInfo.mDragToken != null) {
            try {
                if (isHardwareAccelerated()) {
                    canvas = this.mAttachInfo.mDragSurface.lockHardwareCanvas();
                } else {
                    canvas = this.mAttachInfo.mDragSurface.lockCanvas(null);
                }
                try {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    shadowBuilder.onDrawShadow(canvas);
                    this.mAttachInfo.mDragSurface.unlockCanvasAndPost(canvas);
                    return;
                } catch (Throwable th) {
                    this.mAttachInfo.mDragSurface.unlockCanvasAndPost(canvas);
                    throw th;
                }
            } catch (Exception e) {
                Log.e(VIEW_LOG_TAG, "Unable to update drag shadow", e);
                return;
            }
        }
        Log.e(VIEW_LOG_TAG, "No active drag");
    }

    public final boolean startMovingTask(float startX, float startY) {
        try {
            return this.mAttachInfo.mSession.startMovingTask(
                    this.mAttachInfo.mWindow, startX, startY);
        } catch (RemoteException e) {
            Log.e(VIEW_LOG_TAG, "Unable to start moving", e);
            return false;
        }
    }

    public void finishMovingTask() {
        try {
            this.mAttachInfo.mSession.finishMovingTask(this.mAttachInfo.mWindow);
        } catch (RemoteException e) {
            Log.e(VIEW_LOG_TAG, "Unable to finish moving", e);
        }
    }

    public boolean onDragEvent(DragEvent event) {
        if (this.mListenerInfo == null || this.mListenerInfo.mOnReceiveContentListener == null) {
            return false;
        }
        if (event.getAction() == 1) {
            return true;
        }
        if (event.getAction() != 3) {
            return false;
        }
        DragAndDropPermissions permissions = DragAndDropPermissions.obtain(event);
        if (permissions != null) {
            permissions.takeTransient();
        }
        ContentInfo payload =
                new ContentInfo.Builder(event.getClipData(), 3)
                        .setDragAndDropPermissions(permissions)
                        .build();
        ContentInfo remainingPayload = performReceiveContent(payload);
        return remainingPayload != payload;
    }

    boolean dispatchDragEnterExitInPreN(DragEvent event) {
        return callDragEventHandler(event);
    }

    public boolean dispatchDragEvent(DragEvent event) {
        event.mEventHandlerWasCalled = true;
        if (event.mAction == 2 || event.mAction == 3) {
            getViewRootImpl().setDragFocus(this, event);
        }
        return callDragEventHandler(event);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x006b, code lost:

       return r1;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final boolean callDragEventHandler(android.view.DragEvent r5) {
        /*
            r4 = this;
            android.view.View$ListenerInfo r0 = r4.mListenerInfo
            if (r0 == 0) goto L1c
            android.view.View$OnDragListener r1 = android.view.View.ListenerInfo.m5648$$Nest$fgetmOnDragListener(r0)
            if (r1 == 0) goto L1c
            int r1 = r4.mViewFlags
            r1 = r1 & 32
            if (r1 != 0) goto L1c
            android.view.View$OnDragListener r1 = android.view.View.ListenerInfo.m5648$$Nest$fgetmOnDragListener(r0)
            boolean r1 = r1.onDrag(r4, r5)
            if (r1 == 0) goto L1c
            r1 = 1
            goto L20
        L1c:
            boolean r1 = r4.onDragEvent(r5)
        L20:
            int r2 = r5.mAction
            r3 = 0
            switch(r2) {
                case 1: goto L5e;
                case 2: goto L26;
                case 3: goto L48;
                case 4: goto L3b;
                case 5: goto L31;
                case 6: goto L27;
                default: goto L26;
            }
        L26:
            goto L6b
        L27:
            int r2 = r4.mPrivateFlags2
            r2 = r2 & (-3)
            r4.mPrivateFlags2 = r2
            r4.refreshDrawableState()
            goto L6b
        L31:
            int r2 = r4.mPrivateFlags2
            r2 = r2 | 2
            r4.mPrivateFlags2 = r2
            r4.refreshDrawableState()
            goto L6b
        L3b:
            r4.sendWindowContentChangedAccessibilityEvent(r3)
            int r2 = r4.mPrivateFlags2
            r2 = r2 & (-4)
            r4.mPrivateFlags2 = r2
            r4.refreshDrawableState()
            goto L6b
        L48:
            if (r1 == 0) goto L6b
            if (r0 == 0) goto L6b
            android.view.View$OnDragListener r2 = android.view.View.ListenerInfo.m5648$$Nest$fgetmOnDragListener(r0)
            if (r2 != 0) goto L58
            android.view.OnReceiveContentListener r2 = android.view.View.ListenerInfo.m5653$$Nest$fgetmOnReceiveContentListener(r0)
            if (r2 == 0) goto L6b
        L58:
            r2 = 256(0x100, float:3.59E-43)
            r4.sendWindowContentChangedAccessibilityEvent(r2)
            goto L6b
        L5e:
            if (r1 == 0) goto L6b
            if (r0 == 0) goto L6b
            android.view.View$OnDragListener r2 = android.view.View.ListenerInfo.m5648$$Nest$fgetmOnDragListener(r0)
            if (r2 == 0) goto L6b
            r4.sendWindowContentChangedAccessibilityEvent(r3)
        L6b:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.View.callDragEventHandler(android.view.DragEvent):boolean");
    }

    boolean canAcceptDrag() {
        return (this.mPrivateFlags2 & 1) != 0;
    }

    void sendWindowContentChangedAccessibilityEvent(int changeType) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            AccessibilityEvent event = AccessibilityEvent.obtain();
            event.setEventType(2048);
            event.setContentChangeTypes(changeType);
            sendAccessibilityEventUnchecked(event);
        }
    }

    public void onCloseSystemDialogs(String reason) {}

    public void applyDrawableToTransparentRegion(Drawable dr, Region region) {
        Region r = dr.getTransparentRegion();
        Rect db = dr.getBounds();
        AttachInfo attachInfo = this.mAttachInfo;
        if (r != null && attachInfo != null) {
            int w = getRight() - getLeft();
            int h = getBottom() - getTop();
            if (db.left > 0) {
                r.op(0, 0, db.left, h, Region.Op.UNION);
            }
            if (db.right < w) {
                r.op(db.right, 0, w, h, Region.Op.UNION);
            }
            if (db.top > 0) {
                r.op(0, 0, w, db.top, Region.Op.UNION);
            }
            if (db.bottom < h) {
                r.op(0, db.bottom, w, h, Region.Op.UNION);
            }
            int[] location = attachInfo.mTransparentLocation;
            getLocationInWindow(location);
            r.translate(location[0], location[1]);
            region.op(r, Region.Op.INTERSECT);
            return;
        }
        region.op(db, Region.Op.DIFFERENCE);
    }

    public void checkForLongClick(long delay, float x, float y, int classification) {
        boolean isTooltipAvailable =
                (this.mViewFlags & 1073741824) == 1073741824 && (this.mSemViewFlags & 2) != 2;
        if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
            Log.i(
                    VIEW_LOG_TAG,
                    "checkForLongClick delay : "
                            + delay
                            + ", x : "
                            + x
                            + ", y : "
                            + y
                            + ", classification : "
                            + classification
                            + ", caller="
                            + Debug.getCallers(3));
        }
        if ((this.mViewFlags & 2097152) == 2097152 || isTooltipAvailable) {
            this.mHasPerformedLongPress = false;
            if (this.mPendingCheckForLongPress == null) {
                this.mPendingCheckForLongPress = new CheckForLongPress();
            }
            this.mPendingCheckForLongPress.setAnchor(x, y);
            this.mPendingCheckForLongPress.rememberWindowAttachCount();
            this.mPendingCheckForLongPress.rememberPressedState();
            this.mPendingCheckForLongPress.setClassification(classification);
            postDelayed(this.mPendingCheckForLongPress, delay);
        }
    }

    public static View inflate(Context context, int resource, ViewGroup root) {
        LayoutInflater factory = LayoutInflater.from(context);
        return factory.inflate(resource, root);
    }

    public boolean overScrollBy(
            int deltaX,
            int deltaY,
            int scrollX,
            int scrollY,
            int scrollRangeX,
            int scrollRangeY,
            int maxOverScrollX,
            int maxOverScrollY,
            boolean isTouchEvent) {
        int maxOverScrollX2;
        int maxOverScrollY2;
        boolean clampedX;
        boolean clampedY;
        int overScrollMode = this.mOverScrollMode;
        boolean canScrollHorizontal =
                computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        boolean canScrollVertical = computeVerticalScrollRange() > computeVerticalScrollExtent();
        boolean overScrollHorizontal =
                overScrollMode == 0 || (overScrollMode == 1 && canScrollHorizontal);
        boolean overScrollVertical =
                overScrollMode == 0 || (overScrollMode == 1 && canScrollVertical);
        int newScrollX = scrollX + deltaX;
        if (overScrollHorizontal) {
            maxOverScrollX2 = maxOverScrollX;
        } else {
            maxOverScrollX2 = 0;
        }
        int newScrollY = scrollY + deltaY;
        if (overScrollVertical) {
            maxOverScrollY2 = maxOverScrollY;
        } else {
            maxOverScrollY2 = 0;
        }
        int left = -maxOverScrollX2;
        int right = maxOverScrollX2 + scrollRangeX;
        int top = -maxOverScrollY2;
        int bottom = maxOverScrollY2 + scrollRangeY;
        if (newScrollX > right) {
            newScrollX = right;
            clampedX = true;
        } else if (newScrollX >= left) {
            clampedX = false;
        } else {
            newScrollX = left;
            clampedX = true;
        }
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY >= top) {
            clampedY = false;
        } else {
            newScrollY = top;
            clampedY = true;
        }
        onOverScrolled(newScrollX, newScrollY, clampedX, clampedY);
        return clampedX || clampedY;
    }

    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {}

    public int getOverScrollMode() {
        return this.mOverScrollMode;
    }

    public void setOverScrollMode(int overScrollMode) {
        if (overScrollMode != 0 && overScrollMode != 1 && overScrollMode != 2) {
            throw new IllegalArgumentException("Invalid overscroll mode " + overScrollMode);
        }
        this.mOverScrollMode = overScrollMode;
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        if (enabled) {
            this.mPrivateFlags3 |= 128;
        } else {
            stopNestedScroll();
            this.mPrivateFlags3 &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
    }

    public boolean isNestedScrollingEnabled() {
        return (this.mPrivateFlags3 & 128) == 128;
    }

    public boolean startNestedScroll(int axes) {
        if (hasNestedScrollingParent()) {
            return true;
        }
        if (isNestedScrollingEnabled()) {
            View child = this;
            for (ViewParent p = getParent(); p != null; p = p.getParent()) {
                try {
                    if (p.onStartNestedScroll(child, this, axes)) {
                        this.mNestedScrollingParent = p;
                        p.onNestedScrollAccepted(child, this, axes);
                        return true;
                    }
                } catch (AbstractMethodError e) {
                    Log.e(
                            VIEW_LOG_TAG,
                            "ViewParent "
                                    + p
                                    + " does not implement interface method onStartNestedScroll",
                            e);
                }
                if (p instanceof View) {
                    View child2 = p;
                    child = child2;
                }
            }
            return false;
        }
        return false;
    }

    public void stopNestedScroll() {
        if (this.mNestedScrollingParent != null) {
            this.mNestedScrollingParent.onStopNestedScroll(this);
            this.mNestedScrollingParent = null;
        }
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingParent != null;
    }

    public boolean dispatchNestedScroll(
            int dxConsumed,
            int dyConsumed,
            int dxUnconsumed,
            int dyUnconsumed,
            int[] offsetInWindow) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (dxConsumed != 0 || dyConsumed != 0 || dxUnconsumed != 0 || dyUnconsumed != 0) {
                int startX = 0;
                int startY = 0;
                if (offsetInWindow != null) {
                    getLocationInWindow(offsetInWindow);
                    startX = offsetInWindow[0];
                    startY = offsetInWindow[1];
                }
                this.mNestedScrollingParent.onNestedScroll(
                        this, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
                if (offsetInWindow != null) {
                    getLocationInWindow(offsetInWindow);
                    offsetInWindow[0] = offsetInWindow[0] - startX;
                    offsetInWindow[1] = offsetInWindow[1] - startY;
                }
                return true;
            }
            if (offsetInWindow != null) {
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (dx != 0 || dy != 0) {
                int startX = 0;
                int startY = 0;
                if (offsetInWindow != null) {
                    getLocationInWindow(offsetInWindow);
                    startX = offsetInWindow[0];
                    startY = offsetInWindow[1];
                }
                if (consumed == null) {
                    if (this.mTempNestedScrollConsumed == null) {
                        this.mTempNestedScrollConsumed = new int[2];
                    }
                    consumed = this.mTempNestedScrollConsumed;
                }
                consumed[0] = 0;
                consumed[1] = 0;
                this.mNestedScrollingParent.onNestedPreScroll(this, dx, dy, consumed);
                if (offsetInWindow != null) {
                    getLocationInWindow(offsetInWindow);
                    offsetInWindow[0] = offsetInWindow[0] - startX;
                    offsetInWindow[1] = offsetInWindow[1] - startY;
                }
                return (consumed[0] == 0 && consumed[1] == 0) ? false : true;
            }
            if (offsetInWindow != null) {
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            return this.mNestedScrollingParent.onNestedFling(this, velocityX, velocityY, consumed);
        }
        return false;
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            return this.mNestedScrollingParent.onNestedPreFling(this, velocityX, velocityY);
        }
        return false;
    }

    public float getVerticalScrollFactor() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue outValue = new TypedValue();
            if (!this.mContext.getTheme().resolveAttribute(16842829, outValue, true)) {
                throw new IllegalStateException(
                        "Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor =
                    outValue.getDimension(this.mContext.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    public float semGetVerticalScrollFactor() {
        return getVerticalScrollFactor();
    }

    public float getHorizontalScrollFactor() {
        return getVerticalScrollFactor();
    }

    @ViewDebug.ExportedProperty(
            category = "text",
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "INHERIT"),
                @ViewDebug.IntToString(from = 1, to = "FIRST_STRONG"),
                @ViewDebug.IntToString(from = 2, to = "ANY_RTL"),
                @ViewDebug.IntToString(from = 3, to = "LTR"),
                @ViewDebug.IntToString(from = 4, to = "RTL"),
                @ViewDebug.IntToString(from = 5, to = "LOCALE"),
                @ViewDebug.IntToString(from = 6, to = "FIRST_STRONG_LTR"),
                @ViewDebug.IntToString(from = 7, to = "FIRST_STRONG_RTL")
            })
    public int getRawTextDirection() {
        return (this.mPrivateFlags2 & 448) >> 6;
    }

    public void setTextDirection(int textDirection) {
        if (getRawTextDirection() != textDirection) {
            this.mPrivateFlags2 &= -449;
            resetResolvedTextDirection();
            this.mPrivateFlags2 |= (textDirection << 6) & 448;
            resolveTextDirection();
            onRtlPropertiesChanged(getLayoutDirection());
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(
            category = "text",
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "INHERIT"),
                @ViewDebug.IntToString(from = 1, to = "FIRST_STRONG"),
                @ViewDebug.IntToString(from = 2, to = "ANY_RTL"),
                @ViewDebug.IntToString(from = 3, to = "LTR"),
                @ViewDebug.IntToString(from = 4, to = "RTL"),
                @ViewDebug.IntToString(from = 5, to = "LOCALE"),
                @ViewDebug.IntToString(from = 6, to = "FIRST_STRONG_LTR"),
                @ViewDebug.IntToString(from = 7, to = "FIRST_STRONG_RTL")
            })
    public int getTextDirection() {
        return (this.mPrivateFlags2 & 7168) >> 10;
    }

    public boolean resolveTextDirection() {
        int parentResolvedDirection;
        this.mPrivateFlags2 &= -7681;
        if (hasRtlSupport()) {
            int textDirection = getRawTextDirection();
            switch (textDirection) {
                case 0:
                    if (!canResolveTextDirection()) {
                        this.mPrivateFlags2 |= 1024;
                        return false;
                    }
                    try {
                        if (this.mParent == null || !this.mParent.isTextDirectionResolved()) {
                            this.mPrivateFlags2 |= 1024;
                            return false;
                        }
                        try {
                            if (this.mParent == null) {
                                parentResolvedDirection = 3;
                            } else {
                                parentResolvedDirection = this.mParent.getTextDirection();
                            }
                        } catch (AbstractMethodError e) {
                            Log.e(
                                    VIEW_LOG_TAG,
                                    this.mParent.getClass().getSimpleName()
                                            + " does not fully implement ViewParent",
                                    e);
                            parentResolvedDirection = 3;
                        }
                        switch (parentResolvedDirection) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                                this.mPrivateFlags2 |= parentResolvedDirection << 10;
                                break;
                            default:
                                this.mPrivateFlags2 |= 1024;
                                break;
                        }
                    } catch (AbstractMethodError e2) {
                        Log.e(
                                VIEW_LOG_TAG,
                                this.mParent.getClass().getSimpleName()
                                        + " does not fully implement ViewParent",
                                e2);
                        this.mPrivateFlags2 |= 1536;
                        return true;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    this.mPrivateFlags2 |= textDirection << 10;
                    break;
                default:
                    this.mPrivateFlags2 |= 1024;
                    break;
            }
        } else {
            this.mPrivateFlags2 |= 1024;
        }
        this.mPrivateFlags2 |= 512;
        return true;
    }

    public boolean canResolveTextDirection() {
        switch (getRawTextDirection()) {
            case 0:
                if (this.mParent != null) {
                    try {
                        return this.mParent.canResolveTextDirection();
                    } catch (AbstractMethodError e) {
                        Log.e(
                                VIEW_LOG_TAG,
                                this.mParent.getClass().getSimpleName()
                                        + " does not fully implement ViewParent",
                                e);
                        return false;
                    }
                }
                return false;
            default:
                return true;
        }
    }

    public void resetResolvedTextDirection() {
        this.mPrivateFlags2 &= -7681;
        this.mPrivateFlags2 |= 1024;
    }

    public boolean isTextDirectionInherited() {
        return getRawTextDirection() == 0;
    }

    public boolean isTextDirectionResolved() {
        return (this.mPrivateFlags2 & 512) == 512;
    }

    @ViewDebug.ExportedProperty(
            category = "text",
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "INHERIT"),
                @ViewDebug.IntToString(from = 1, to = "GRAVITY"),
                @ViewDebug.IntToString(from = 2, to = "TEXT_START"),
                @ViewDebug.IntToString(from = 3, to = "TEXT_END"),
                @ViewDebug.IntToString(from = 4, to = "CENTER"),
                @ViewDebug.IntToString(from = 5, to = "VIEW_START"),
                @ViewDebug.IntToString(from = 6, to = "VIEW_END")
            })
    public int getRawTextAlignment() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_MASK) >> 13;
    }

    public void setTextAlignment(int textAlignment) {
        if (textAlignment != getRawTextAlignment()) {
            this.mPrivateFlags2 &= -57345;
            resetResolvedTextAlignment();
            this.mPrivateFlags2 |= (textAlignment << 13) & PFLAG2_TEXT_ALIGNMENT_MASK;
            resolveTextAlignment();
            onRtlPropertiesChanged(getLayoutDirection());
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(
            category = "text",
            mapping = {
                @ViewDebug.IntToString(from = 0, to = "INHERIT"),
                @ViewDebug.IntToString(from = 1, to = "GRAVITY"),
                @ViewDebug.IntToString(from = 2, to = "TEXT_START"),
                @ViewDebug.IntToString(from = 3, to = "TEXT_END"),
                @ViewDebug.IntToString(from = 4, to = "CENTER"),
                @ViewDebug.IntToString(from = 5, to = "VIEW_START"),
                @ViewDebug.IntToString(from = 6, to = "VIEW_END")
            })
    public int getTextAlignment() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK) >> 17;
    }

    public boolean resolveTextAlignment() {
        int parentResolvedTextAlignment;
        this.mPrivateFlags2 &= -983041;
        if (hasRtlSupport()) {
            int textAlignment = getRawTextAlignment();
            switch (textAlignment) {
                case 0:
                    if (!canResolveTextAlignment()) {
                        this.mPrivateFlags2 |= 131072;
                        return false;
                    }
                    try {
                        if (this.mParent == null || !this.mParent.isTextAlignmentResolved()) {
                            this.mPrivateFlags2 = 131072 | this.mPrivateFlags2;
                            return false;
                        }
                        try {
                            parentResolvedTextAlignment = this.mParent.getTextAlignment();
                        } catch (AbstractMethodError e) {
                            Log.e(
                                    VIEW_LOG_TAG,
                                    this.mParent.getClass().getSimpleName()
                                            + " does not fully implement ViewParent",
                                    e);
                            parentResolvedTextAlignment = 1;
                        }
                        switch (parentResolvedTextAlignment) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                this.mPrivateFlags2 |= parentResolvedTextAlignment << 17;
                                break;
                            default:
                                this.mPrivateFlags2 |= 131072;
                                break;
                        }
                    } catch (AbstractMethodError e2) {
                        Log.e(
                                VIEW_LOG_TAG,
                                this.mParent.getClass().getSimpleName()
                                        + " does not fully implement ViewParent",
                                e2);
                        this.mPrivateFlags2 |= 196608;
                        return true;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    this.mPrivateFlags2 |= textAlignment << 17;
                    break;
                default:
                    this.mPrivateFlags2 |= 131072;
                    break;
            }
        } else {
            this.mPrivateFlags2 |= 131072;
        }
        this.mPrivateFlags2 |= 65536;
        return true;
    }

    public boolean canResolveTextAlignment() {
        switch (getRawTextAlignment()) {
            case 0:
                if (this.mParent != null) {
                    try {
                        return this.mParent.canResolveTextAlignment();
                    } catch (AbstractMethodError e) {
                        Log.e(
                                VIEW_LOG_TAG,
                                this.mParent.getClass().getSimpleName()
                                        + " does not fully implement ViewParent",
                                e);
                        return false;
                    }
                }
                return false;
            default:
                return true;
        }
    }

    public void resetResolvedTextAlignment() {
        this.mPrivateFlags2 &= -983041;
        this.mPrivateFlags2 |= 131072;
    }

    public boolean isTextAlignmentInherited() {
        return getRawTextAlignment() == 0;
    }

    public boolean isTextAlignmentResolved() {
        return (this.mPrivateFlags2 & 65536) == 65536;
    }

    public static int generateViewId() {
        int result;
        int newValue;
        do {
            result = sNextGeneratedId.get();
            newValue = result + 1;
            if (newValue > 16777215) {
                newValue = 1;
            }
        } while (!sNextGeneratedId.compareAndSet(result, newValue));
        return result;
    }

    private static boolean isViewIdGenerated(int id) {
        return ((-16777216) & id) == 0 && (16777215 & id) != 0;
    }

    public void captureTransitioningViews(List<View> transitioningViews) {
        if (getVisibility() == 0) {
            transitioningViews.add(this);
        }
    }

    public void findNamedViews(Map<String, View> namedElements) {
        String transitionName;
        if ((getVisibility() == 0 || this.mGhostView != null)
                && (transitionName = getTransitionName()) != null) {
            namedElements.put(transitionName, this);
        }
    }

    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        if (isDraggingScrollBar() || isOnScrollbarThumb(x, y)) {
            return null;
        }
        int toolType = event.getToolType(pointerIndex);
        if (toolType == 2) {
            if (this.mPointerIconForStylus != null) {
                return this.mPointerIconForStylus;
            }
            if (this.mMousePointerIcon != null && this.mMousePointerIcon.getType() == -1) {
                this.mMousePointerIcon.setType(20000);
            } else if ((this.mViewFlags & 1073741824) == 1073741824
                    && (this.mViewFlags & 32) == 0
                    && isSPenHoveringSettingsEnabled()) {
                return PointerIcon.getSystemIcon(this.mContext, 20010);
            }
        } else if (toolType == 3) {
            if (this.mPointerIconForMouse != null) {
                return this.mPointerIconForMouse;
            }
            if (this.mMousePointerIcon != null && this.mMousePointerIcon.getType() == 20000) {
                this.mMousePointerIcon.setType(-1);
            }
        }
        if (event.isFromSource(8194)) {
            return this.mMousePointerIcon;
        }
        return null;
    }

    public void setPointerIcon(PointerIcon pointerIcon) {
        this.mMousePointerIcon = pointerIcon;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        viewRootImpl.refreshPointerIcon();
    }

    public void semSetPointerIcon(int toolType, PointerIcon pointerIcon) {
        if (pointerIcon != null) {
            Log.i(
                    "PointerIcon",
                    "[View]semSetPointerIcon = "
                            + pointerIcon.getType()
                            + ", toolType = "
                            + toolType
                            + ", view = "
                            + this
                            + ", callingPid = "
                            + Binder.getCallingPid());
        } else {
            Log.i(
                    "PointerIcon",
                    "[View]semSetPointerIcon is NULL , toolType = "
                            + toolType
                            + ", view = "
                            + this
                            + ", callingPid = "
                            + Binder.getCallingPid());
        }
        if (toolType == 2) {
            if (pointerIcon != null && pointerIcon.getType() == -1) {
                pointerIcon.setType(20000);
            }
            this.mPointerIconForStylus = pointerIcon;
        } else if (toolType == 3) {
            this.mPointerIconForMouse = pointerIcon;
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        viewRootImpl.refreshPointerIcon();
    }

    private void hidden_semSetPointerIcon(int toolType, PointerIcon pointerIcon) {
        semSetPointerIcon(toolType, pointerIcon);
    }

    public PointerIcon getPointerIcon() {
        return this.mMousePointerIcon;
    }

    public boolean hasPointerCapture() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return false;
        }
        return viewRootImpl.hasPointerCapture();
    }

    public void requestPointerCapture() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestPointerCapture(true);
        }
    }

    public void releasePointerCapture() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestPointerCapture(false);
        }
    }

    public void onPointerCaptureChange(boolean hasCapture) {}

    public void dispatchPointerCaptureChanged(boolean hasCapture) {
        onPointerCaptureChange(hasCapture);
    }

    public boolean onCapturedPointerEvent(MotionEvent event) {
        return false;
    }

    public void setOnCapturedPointerListener(OnCapturedPointerListener l) {
        getListenerInfo().mOnCapturedPointerListener = l;
    }

    public static class MeasureSpec {
        public static final int AT_MOST = Integer.MIN_VALUE;
        public static final int EXACTLY = 1073741824;
        private static final int MODE_MASK = -1073741824;
        private static final int MODE_SHIFT = 30;
        public static final int UNSPECIFIED = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface MeasureSpecMode {}

        public static int makeMeasureSpec(int size, int mode) {
            return (1073741823 & size) | ((-1073741824) & mode);
        }

        public static int makeSafeMeasureSpec(int size, int mode) {
            return makeMeasureSpec(size, mode);
        }

        public static int getMode(int measureSpec) {
            return (-1073741824) & measureSpec;
        }

        public static int getSize(int measureSpec) {
            return 1073741823 & measureSpec;
        }

        static int adjust(int measureSpec, int delta) {
            int mode = getMode(measureSpec);
            int size = getSize(measureSpec);
            if (mode == 0) {
                return makeMeasureSpec(size, 0);
            }
            int size2 = size + delta;
            if (size2 < 0) {
                Log.e(
                        View.VIEW_LOG_TAG,
                        "MeasureSpec.adjust: new size would be negative! ("
                                + size2
                                + ") spec: "
                                + toString(measureSpec)
                                + " delta: "
                                + delta);
                size2 = 0;
            }
            return makeMeasureSpec(size2, mode);
        }

        public static String toString(int measureSpec) {
            int mode = getMode(measureSpec);
            int size = getSize(measureSpec);
            StringBuilder sb = new StringBuilder("MeasureSpec: ");
            if (mode == 0) {
                sb.append("UNSPECIFIED ");
            } else if (mode == 1073741824) {
                sb.append("EXACTLY ");
            } else if (mode == Integer.MIN_VALUE) {
                sb.append("AT_MOST ");
            } else {
                sb.append(mode).append(" ");
            }
            sb.append(size);
            return sb.toString();
        }
    }

    private final class CheckForLongPress implements Runnable {
        private int mClassification;
        private boolean mOriginalPressedState;
        private int mOriginalWindowAttachCount;
        private float mX;
        private float mY;

        private CheckForLongPress() {}

        @Override // java.lang.Runnable
        public void run() {
            if (this.mOriginalPressedState == View.this.isPressed()
                    && View.this.mParent != null
                    && this.mOriginalWindowAttachCount == View.this.mWindowAttachCount) {
                View.this.recordGestureClassification(this.mClassification);
                if (View.this.performLongClick(this.mX, this.mY)) {
                    View.this.mHasPerformedLongPress = true;
                }
            }
        }

        public void setAnchor(float x, float y) {
            this.mX = x;
            this.mY = y;
        }

        public void rememberWindowAttachCount() {
            this.mOriginalWindowAttachCount = View.this.mWindowAttachCount;
        }

        public void rememberPressedState() {
            this.mOriginalPressedState = View.this.isPressed();
        }

        public void setClassification(int classification) {
            this.mClassification = classification;
        }
    }

    private final class CheckForTap implements Runnable {
        public float x;
        public float y;

        private CheckForTap() {}

        @Override // java.lang.Runnable
        public void run() {
            View.this.mPrivateFlags &= -33554433;
            View.this.setPressed(true, this.x, this.y);
            long delay =
                    ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout();
            View.this.checkForLongClick(delay, this.x, this.y, 3);
        }
    }

    private final class PerformClick implements Runnable {
        private PerformClick() {}

        @Override // java.lang.Runnable
        public void run() {
            View.this.recordGestureClassification(1);
            View.this.performClickInternal();
        }
    }

    public void recordGestureClassification(int classification) {
        if (classification == 0) {
            return;
        }
        FrameworkStatsLog.write(177, getClass().getName(), classification);
    }

    public ViewPropertyAnimator animate() {
        if (this.mAnimator == null) {
            this.mAnimator = new ViewPropertyAnimator(this);
        }
        return this.mAnimator;
    }

    public final void setTransitionName(String transitionName) {
        this.mTransitionName = transitionName;
    }

    @ViewDebug.ExportedProperty
    public String getTransitionName() {
        return this.mTransitionName;
    }

    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> data, int deviceId) {}

    public interface OnLongClickListener {
        boolean onLongClick(View view);

        default boolean onLongClickUseDefaultHapticFeedback(View v) {
            return true;
        }
    }

    private final class UnsetPressedState implements Runnable {
        private UnsetPressedState() {}

        @Override // java.lang.Runnable
        public void run() {
            View.this.setPressed(false);
        }
    }

    private static class VisibilityChangeForAutofillHandler extends Handler {
        private final AutofillManager mAfm;
        private final View mView;

        private VisibilityChangeForAutofillHandler(AutofillManager afm, View view) {
            this.mAfm = afm;
            this.mView = view;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            this.mAfm.notifyViewVisibilityChanged(this.mView, this.mView.isShown());
        }
    }

    public static class BaseSavedState extends AbsSavedState {
        static final int AUTOFILL_ID = 4;
        public static final Parcelable.Creator<BaseSavedState> CREATOR =
                new Parcelable.ClassLoaderCreator<
                        BaseSavedState>() { // from class: android.view.View.BaseSavedState.1
                    @Override // android.os.Parcelable.Creator
                    public BaseSavedState createFromParcel(Parcel in) {
                        return new BaseSavedState(in);
                    }

                    @Override // android.os.Parcelable.ClassLoaderCreator
                    public BaseSavedState createFromParcel(Parcel in, ClassLoader loader) {
                        return new BaseSavedState(in, loader);
                    }

                    @Override // android.os.Parcelable.Creator
                    public BaseSavedState[] newArray(int size) {
                        return new BaseSavedState[size];
                    }
                };
        static final int IS_AUTOFILLED = 2;
        static final int START_ACTIVITY_REQUESTED_WHO_SAVED = 1;
        int mAutofillViewId;
        boolean mHideHighlight;
        boolean mIsAutofilled;
        int mSavedData;
        String mStartActivityRequestWhoSaved;

        public BaseSavedState(Parcel source) {
            this(source, null);
        }

        public BaseSavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.mSavedData = source.readInt();
            this.mStartActivityRequestWhoSaved = source.readString();
            this.mIsAutofilled = source.readBoolean();
            this.mHideHighlight = source.readBoolean();
            this.mAutofillViewId = source.readInt();
        }

        public BaseSavedState(Parcelable superState) {
            super(superState);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.mSavedData);
            out.writeString(this.mStartActivityRequestWhoSaved);
            out.writeBoolean(this.mIsAutofilled);
            out.writeBoolean(this.mHideHighlight);
            out.writeInt(this.mAutofillViewId);
        }
    }

    static final class AttachInfo {
        int mAccessibilityFetchFlags;
        Drawable mAccessibilityFocusDrawable;
        boolean mAlwaysConsumeSystemBars;
        float mApplicationScale;
        Drawable mAutofilledDrawable;
        Canvas mCanvas;
        SparseArray<ArrayList<Object>> mContentCaptureEvents;
        ContentCaptureManager mContentCaptureManager;
        Window.OnContentApplyWindowInsetsListener mContentOnApplyWindowInsetsListener;
        final float mDensity;
        int mDisabledSystemUiVisibility;
        Display mDisplay;
        final float mDisplayPixelCount;
        ClipData mDragData;
        public Surface mDragSurface;
        IBinder mDragToken;
        long mDrawingTime;
        boolean mForceReportNewAttributes;
        final Handler mHandler;
        boolean mHandlingPointerEvent;
        boolean mHardwareAccelerated;
        boolean mHardwareAccelerationRequested;
        boolean mHasNonEmptyGivenInternalInsets;
        boolean mHasSystemUiListeners;
        boolean mHasWindowFocus;
        IWindowId mIWindowId;
        boolean mInTouchMode;
        boolean mKeepScreenOn;
        int mLeashedParentAccessibilityViewId;
        IBinder mLeashedParentToken;
        boolean mNeedsUpdateLightCenter;
        IBinder mPanelParentWindowToken;
        List<RenderNode> mPendingAnimatingRenderNodes;
        boolean mReadyForContentCaptureUpdates;
        boolean mRecomputeGlobalAttributes;
        final Callbacks mRootCallbacks;
        View mRootView;
        boolean mScalingRequired;
        ScrollCaptureInternal mScrollCaptureInternal;
        int mSensitiveViewsCount;
        final IWindowSession mSession;
        int mSystemUiVisibility;
        ThreadedRenderer mThreadedRenderer;
        View mTooltipHost;
        final ViewTreeObserver mTreeObserver;
        boolean mUnbufferedDispatchRequested;
        boolean mUse32BitDrawingCache;
        View mViewRequestingLayout;
        final ViewRootImpl mViewRootImpl;
        boolean mViewScrollChanged;
        boolean mViewVisibilityChanged;
        final IWindow mWindow;
        WindowId mWindowId;
        int mWindowLeft;
        Matrix mWindowMatrixInEmbeddedHierarchy;
        final IBinder mWindowToken;
        int mWindowTop;
        int mWindowVisibility;
        int mDisplayState = 0;
        final Rect mContentInsets = new Rect();
        final Rect mVisibleInsets = new Rect();
        final Rect mStableInsets = new Rect();
        final Rect mCaptionInsets = new Rect();
        final ViewTreeObserver.InternalInsetsInfo mGivenInternalInsets =
                new ViewTreeObserver.InternalInsetsInfo();
        final ArrayList<View> mScrollContainers = new ArrayList<>();
        final KeyEvent.DispatcherState mKeyDispatchState = new KeyEvent.DispatcherState();
        final int[] mTransparentLocation = new int[2];
        final int[] mInvalidateChildLocation = new int[2];
        final int[] mTmpLocation = new int[2];
        final float[] mTmpTransformLocation = new float[2];
        final Rect mTmpInvalRect = new Rect();
        final RectF mTmpTransformRect = new RectF();
        final RectF mTmpTransformRect1 = new RectF();
        final List<RectF> mTmpRectList = new ArrayList();
        final Matrix mTmpMatrix = new Matrix();
        final Transformation mTmpTransformation = new Transformation();
        final Outline mTmpOutline = new Outline();
        final ArrayList<View> mTempArrayList = new ArrayList<>(24);
        boolean mNextFocusLooped = false;
        int mAccessibilityWindowId = -1;
        boolean mDebugLayout = DisplayProperties.debug_layout().orElse(false).booleanValue();
        final Point mPoint = new Point();
        final boolean mViewVelocityApi = Flags.viewVelocityApi();

        interface Callbacks {
            boolean performHapticFeedback(int i, boolean z, boolean z2);

            void playSoundEffect(int i);
        }

        static class InvalidateInfo {
            private static final int POOL_LIMIT = 10;
            private static final Pools.SynchronizedPool<InvalidateInfo> sPool =
                    new Pools.SynchronizedPool<>(10);
            int bottom;
            int left;
            int right;
            View target;
            int top;

            InvalidateInfo() {}

            public static InvalidateInfo obtain() {
                InvalidateInfo instance = sPool.acquire();
                return instance != null ? instance : new InvalidateInfo();
            }

            public void recycle() {
                this.target = null;
                sPool.release(this);
            }
        }

        AttachInfo(
                IWindowSession session,
                IWindow window,
                Display display,
                ViewRootImpl viewRootImpl,
                Handler handler,
                Callbacks effectPlayer,
                Context context) {
            this.mSession = session;
            this.mWindow = window;
            this.mWindowToken = window.asBinder();
            this.mDisplay = display;
            this.mViewRootImpl = viewRootImpl;
            this.mHandler = handler;
            this.mRootCallbacks = effectPlayer;
            this.mTreeObserver = new ViewTreeObserver(context);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            this.mDensity = displayMetrics.density;
            float pixelCount = displayMetrics.widthPixels * displayMetrics.heightPixels;
            this.mDisplayPixelCount = pixelCount == 0.0f ? Float.POSITIVE_INFINITY : pixelCount;
        }

        void increaseSensitiveViewsCount() {
            if (this.mSensitiveViewsCount == 0) {
                this.mViewRootImpl.addSensitiveContentAppProtection();
            }
            this.mSensitiveViewsCount++;
        }

        void decreaseSensitiveViewsCount() {
            this.mSensitiveViewsCount--;
            if (this.mSensitiveViewsCount == 0) {
                this.mViewRootImpl.removeSensitiveContentAppProtection();
            }
            if (this.mSensitiveViewsCount < 0) {
                Log.wtf(
                        View.VIEW_LOG_TAG,
                        "mSensitiveViewsCount is negative" + this.mSensitiveViewsCount);
                this.mSensitiveViewsCount = 0;
            }
        }

        ContentCaptureManager getContentCaptureManager(Context context) {
            if (this.mContentCaptureManager != null) {
                return this.mContentCaptureManager;
            }
            this.mContentCaptureManager =
                    (ContentCaptureManager) context.getSystemService(ContentCaptureManager.class);
            return this.mContentCaptureManager;
        }

        void delayNotifyContentCaptureInsetsEvent(Insets insets) {
            if (this.mContentCaptureManager == null) {
                return;
            }
            ArrayList<Object> events =
                    ensureEvents(this.mContentCaptureManager.getMainContentCaptureSession());
            events.add(insets);
        }

        public void delayNotifyContentCaptureEvent(
                ContentCaptureSession session, View view, boolean appeared) {
            ArrayList<Object> events = ensureEvents(session);
            events.add(appeared ? view : view.getAutofillId());
        }

        private ArrayList<Object> ensureEvents(ContentCaptureSession session) {
            if (this.mContentCaptureEvents == null) {
                this.mContentCaptureEvents = new SparseArray<>(1);
            }
            int sessionId = session.getId();
            ArrayList<Object> events = this.mContentCaptureEvents.get(sessionId);
            if (events == null) {
                ArrayList<Object> events2 = new ArrayList<>();
                this.mContentCaptureEvents.put(sessionId, events2);
                return events2;
            }
            return events;
        }

        public boolean canPerformHapticFeedback() {
            return this.mSession != null && (this.mDisplay.getFlags() & 1024) == 0;
        }

        ScrollCaptureInternal getScrollCaptureInternal() {
            if (this.mScrollCaptureInternal != null) {
                this.mScrollCaptureInternal = new ScrollCaptureInternal();
            }
            return this.mScrollCaptureInternal;
        }

        AttachedSurfaceControl getRootSurfaceControl() {
            return this.mViewRootImpl;
        }

        public void dump(String prefix, PrintWriter writer) {
            String innerPrefix = prefix + "  ";
            writer.println(prefix + "AttachInfo:");
            writer.println(innerPrefix + "mHasWindowFocus=" + this.mHasWindowFocus);
            writer.println(innerPrefix + "mWindowVisibility=" + this.mWindowVisibility);
            writer.println(innerPrefix + "mInTouchMode=" + this.mInTouchMode);
            writer.println(
                    innerPrefix
                            + "mUnbufferedDispatchRequested="
                            + this.mUnbufferedDispatchRequested);
        }
    }

    private static class ScrollabilityCache implements Runnable {
        public static final int DRAGGING_HORIZONTAL_SCROLL_BAR = 2;
        public static final int DRAGGING_VERTICAL_SCROLL_BAR = 1;
        public static final int FADING = 2;
        public static final int NOT_DRAGGING = 0;
        public static final int OFF = 0;
        public static final int ON = 1;
        private static final float[] OPAQUE = {255.0f};
        private static final float[] TRANSPARENT = {0.0f};
        public boolean fadeScrollBars;
        public long fadeStartTime;
        public int fadingEdgeLength;
        public View host;
        public float[] interpolatorValues;
        private int mLastColor;
        public ScrollBarDrawable scrollBar;
        public int scrollBarMinTouchTarget;
        public int scrollBarSize;
        public final Interpolator scrollBarInterpolator = new Interpolator(1, 2);
        public int state = 0;
        public final Rect mScrollBarBounds = new Rect();
        public final Rect mScrollBarTouchBounds = new Rect();
        public int mScrollBarDraggingState = 0;
        public float mScrollBarDraggingPos = 0.0f;
        public int scrollBarDefaultDelayBeforeFade = ViewConfiguration.getScrollDefaultDelay();
        public int scrollBarFadeDuration = ViewConfiguration.getScrollBarFadeDuration();
        public final Paint paint = new Paint();
        public final Matrix matrix = new Matrix();
        public Shader shader =
                new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);

        public ScrollabilityCache(ViewConfiguration configuration, View host) {
            this.fadingEdgeLength = configuration.getScaledFadingEdgeLength();
            this.scrollBarSize = configuration.getScaledScrollBarSize();
            this.scrollBarMinTouchTarget = host.semGetScaledMinScrollbarTouchTarget(configuration);
            this.paint.setShader(this.shader);
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            this.host = host;
        }

        public void setFadeColor(int color) {
            if (color != this.mLastColor) {
                this.mLastColor = color;
                if (color != 0) {
                    this.shader =
                            new LinearGradient(
                                    0.0f,
                                    0.0f,
                                    0.0f,
                                    1.0f,
                                    color | (-16777216),
                                    color & 16777215,
                                    Shader.TileMode.CLAMP);
                    this.paint.setShader(this.shader);
                    this.paint.setXfermode(null);
                } else {
                    this.shader =
                            new LinearGradient(
                                    0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
                    this.paint.setShader(this.shader);
                    this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            long now = AnimationUtils.currentAnimationTimeMillis();
            if (now >= this.fadeStartTime) {
                int nextFrame = (int) now;
                Interpolator interpolator = this.scrollBarInterpolator;
                int framesCount = 0 + 1;
                interpolator.setKeyFrame(0, nextFrame, OPAQUE);
                interpolator.setKeyFrame(
                        framesCount, nextFrame + this.scrollBarFadeDuration, TRANSPARENT);
                this.state = 2;
                this.host.invalidate(true);
            }
        }
    }

    private class SendAccessibilityEventThrottle implements Runnable {
        private AccessibilityEvent mAccessibilityEvent;
        public volatile boolean mIsPending;

        private SendAccessibilityEventThrottle() {}

        public void post(AccessibilityEvent accessibilityEvent) {
            updateWithAccessibilityEvent(accessibilityEvent);
            if (!this.mIsPending) {
                this.mIsPending = true;
                View.this.postDelayed(
                        this, ViewConfiguration.getSendRecurringAccessibilityEventsInterval());
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (AccessibilityManager.getInstance(View.this.mContext).isEnabled()
                    && View.this.isShown()) {
                View.this.requestParentSendAccessibilityEvent(this.mAccessibilityEvent);
            }
            reset();
        }

        public void updateWithAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            this.mAccessibilityEvent = accessibilityEvent;
        }

        public void reset() {
            this.mIsPending = false;
            this.mAccessibilityEvent = null;
        }
    }

    private class SendViewScrolledAccessibilityEvent extends SendAccessibilityEventThrottle {
        public int mDeltaX;
        public int mDeltaY;

        private SendViewScrolledAccessibilityEvent() {
            super();
        }

        @Override // android.view.View.SendAccessibilityEventThrottle
        public void updateWithAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.updateWithAccessibilityEvent(accessibilityEvent);
            this.mDeltaX += accessibilityEvent.getScrollDeltaX();
            this.mDeltaY += accessibilityEvent.getScrollDeltaY();
            accessibilityEvent.setScrollDeltaX(this.mDeltaX);
            accessibilityEvent.setScrollDeltaY(this.mDeltaY);
        }

        @Override // android.view.View.SendAccessibilityEventThrottle
        public void reset() {
            super.reset();
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }
    }

    private void cancel(SendAccessibilityEventThrottle callback) {
        if (callback == null || !callback.mIsPending) {
            return;
        }
        removeCallbacks(callback);
        callback.reset();
    }

    public static class AccessibilityDelegate {
        public void sendAccessibilityEvent(View host, int eventType) {
            host.sendAccessibilityEventInternal(eventType);
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            return host.performAccessibilityActionInternal(action, args);
        }

        public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
            host.sendAccessibilityEventUncheckedInternal(event);
        }

        public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            return host.dispatchPopulateAccessibilityEventInternal(event);
        }

        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            host.onPopulateAccessibilityEventInternal(event);
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            host.onInitializeAccessibilityEventInternal(event);
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            host.onInitializeAccessibilityNodeInfoInternal(info);
        }

        public void addExtraDataToAccessibilityNodeInfo(
                View host, AccessibilityNodeInfo info, String extraDataKey, Bundle arguments) {
            host.addExtraDataToAccessibilityNodeInfo(info, extraDataKey, arguments);
        }

        public boolean onRequestSendAccessibilityEvent(
                ViewGroup host, View child, AccessibilityEvent event) {
            return host.onRequestSendAccessibilityEventInternal(child, event);
        }

        public AccessibilityNodeProvider getAccessibilityNodeProvider(View host) {
            return null;
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(View host) {
            return host.createAccessibilityNodeInfoInternal();
        }
    }

    private static class MatchIdPredicate implements Predicate<View> {
        public int mId;

        private MatchIdPredicate() {}

        @Override // java.util.function.Predicate
        public boolean test(View view) {
            return view.mID == this.mId;
        }
    }

    private static class MatchLabelForPredicate implements Predicate<View> {
        private int mLabeledId;

        private MatchLabelForPredicate() {}

        @Override // java.util.function.Predicate
        public boolean test(View view) {
            return view.mLabelForId == this.mLabeledId;
        }
    }

    private static class SensitiveAutofillHintsHelper {
        private static final ArraySet<String> SENSITIVE_CONTENT_AUTOFILL_HINTS = new ArraySet<>();

        private SensitiveAutofillHintsHelper() {}

        static {
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(View.AUTOFILL_HINT_USERNAME);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(View.AUTOFILL_HINT_PASSWORD_AUTO);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add("password");
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(View.AUTOFILL_HINT_CREDIT_CARD_NUMBER);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(View.AUTOFILL_HINT_CREDIT_CARD_SECURITY_CODE);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DAY);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_MONTH);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_YEAR);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add("credential");
        }

        static boolean containsSensitiveAutofillHint(String[] autofillHints) {
            if (autofillHints == null) {
                return false;
            }
            for (String str : autofillHints) {
                if (SENSITIVE_CONTENT_AUTOFILL_HINTS.contains(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    public int getScrollCaptureHint() {
        return (this.mPrivateFlags4 & 7168) >> 10;
    }

    public void setScrollCaptureHint(int hint) {
        this.mPrivateFlags4 &= -7169;
        if ((hint & 1) != 0) {
            hint &= -3;
        }
        this.mPrivateFlags4 |= (hint << 10) & 7168;
    }

    public final void setScrollCaptureCallback(ScrollCaptureCallback callback) {
        getListenerInfo().mScrollCaptureCallback = callback;
    }

    public ScrollCaptureCallback createScrollCaptureCallbackInternal(
            Rect localVisibleRect, Point windowOffset) {
        if (this.mAttachInfo == null) {
            return null;
        }
        if (this.mAttachInfo.mScrollCaptureInternal == null) {
            this.mAttachInfo.mScrollCaptureInternal = new ScrollCaptureInternal();
        }
        return this.mAttachInfo.mScrollCaptureInternal.requestCallback(
                this, localVisibleRect, windowOffset);
    }

    public void dispatchScrollCaptureSearch(
            Rect localVisibleRect, Point windowOffset, Consumer<ScrollCaptureTarget> targets) {
        onScrollCaptureSearch(localVisibleRect, windowOffset, targets);
    }

    public void onScrollCaptureSearch(
            Rect localVisibleRect, Point windowOffset, Consumer<ScrollCaptureTarget> targets) {
        int hint = getScrollCaptureHint();
        if ((hint & 1) != 0) {
            return;
        }
        boolean rectIsVisible = true;
        if (this.mClipBounds != null) {
            rectIsVisible = localVisibleRect.intersect(this.mClipBounds);
        }
        if (!rectIsVisible) {
            return;
        }
        ScrollCaptureCallback callback =
                this.mListenerInfo == null ? null : this.mListenerInfo.mScrollCaptureCallback;
        if (callback == null) {
            callback = createScrollCaptureCallbackInternal(localVisibleRect, windowOffset);
        }
        if (callback != null) {
            Point offset = new Point(windowOffset.x, windowOffset.y);
            Rect rect = new Rect(localVisibleRect);
            targets.accept(new ScrollCaptureTarget(this, rect, offset, callback));
        }
    }

    private static void dumpFlags() {
        HashMap<String, String> found = Maps.newHashMap();
        try {
            for (Field field : View.class.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                    if (field.getType().equals(Integer.TYPE)) {
                        int value = field.getInt(null);
                        dumpFlag(found, field.getName(), value);
                    } else if (field.getType().equals(int[].class)) {
                        int[] values = (int[]) field.get(null);
                        for (int i = 0; i < values.length; i++) {
                            dumpFlag(
                                    found,
                                    field.getName()
                                            + NavigationBarInflaterView.SIZE_MOD_START
                                            + i
                                            + NavigationBarInflaterView.SIZE_MOD_END,
                                    values[i]);
                        }
                    }
                }
            }
            ArrayList<String> keys = Lists.newArrayList();
            keys.addAll(found.keySet());
            Collections.sort(keys);
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                Log.d(VIEW_LOG_TAG, found.get(key));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void dumpFlag(HashMap<String, String> found, String name, int value) {
        String bits = String.format("%32s", Integer.toBinaryString(value)).replace('0', ' ');
        int prefix = name.indexOf(95);
        String key = (prefix > 0 ? name.substring(0, prefix) : name) + bits + name;
        String output = bits + " " + name;
        found.put(key, output);
    }

    public void encode(ViewHierarchyEncoder stream) {
        stream.beginObject(this);
        encodeProperties(stream);
        stream.endObject();
    }

    protected void encodeProperties(ViewHierarchyEncoder stream) {
        Object resolveId = ViewDebug.resolveId(getContext(), this.mID);
        if (resolveId instanceof String) {
            stream.addProperty("id", (String) resolveId);
        } else {
            stream.addProperty("id", this.mID);
        }
        stream.addProperty(
                "misc:transformation.alpha",
                this.mTransformationInfo != null ? this.mTransformationInfo.mAlpha : 0.0f);
        stream.addProperty("misc:transitionName", getTransitionName());
        stream.addProperty("layout:left", this.mLeft);
        stream.addProperty("layout:right", this.mRight);
        stream.addProperty("layout:top", this.mTop);
        stream.addProperty("layout:bottom", this.mBottom);
        stream.addProperty("layout:width", getWidth());
        stream.addProperty("layout:height", getHeight());
        stream.addProperty("layout:layoutDirection", getLayoutDirection());
        stream.addProperty("layout:layoutRtl", isLayoutRtl());
        stream.addProperty("layout:hasTransientState", hasTransientState());
        stream.addProperty("layout:baseline", getBaseline());
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            stream.addPropertyKey("layoutParams");
            layoutParams.encode(stream);
        }
        stream.addProperty("scrolling:scrollX", this.mScrollX);
        stream.addProperty("scrolling:scrollY", this.mScrollY);
        stream.addProperty("padding:paddingLeft", this.mPaddingLeft);
        stream.addProperty("padding:paddingRight", this.mPaddingRight);
        stream.addProperty("padding:paddingTop", this.mPaddingTop);
        stream.addProperty("padding:paddingBottom", this.mPaddingBottom);
        stream.addProperty("padding:userPaddingRight", this.mUserPaddingRight);
        stream.addProperty("padding:userPaddingLeft", this.mUserPaddingLeft);
        stream.addProperty("padding:userPaddingBottom", this.mUserPaddingBottom);
        stream.addProperty("padding:userPaddingStart", this.mUserPaddingStart);
        stream.addProperty("padding:userPaddingEnd", this.mUserPaddingEnd);
        stream.addProperty("measurement:minHeight", this.mMinHeight);
        stream.addProperty("measurement:minWidth", this.mMinWidth);
        stream.addProperty("measurement:measuredWidth", this.mMeasuredWidth);
        stream.addProperty("measurement:measuredHeight", this.mMeasuredHeight);
        stream.addProperty("drawing:elevation", getElevation());
        stream.addProperty("drawing:translationX", getTranslationX());
        stream.addProperty("drawing:translationY", getTranslationY());
        stream.addProperty("drawing:translationZ", getTranslationZ());
        stream.addProperty("drawing:rotation", getRotation());
        stream.addProperty("drawing:rotationX", getRotationX());
        stream.addProperty("drawing:rotationY", getRotationY());
        stream.addProperty("drawing:scaleX", getScaleX());
        stream.addProperty("drawing:scaleY", getScaleY());
        stream.addProperty("drawing:pivotX", getPivotX());
        stream.addProperty("drawing:pivotY", getPivotY());
        stream.addProperty(
                "drawing:clipBounds",
                this.mClipBounds == null ? null : this.mClipBounds.toString());
        stream.addProperty("drawing:opaque", isOpaque());
        stream.addProperty("drawing:alpha", getAlpha());
        stream.addProperty("drawing:transitionAlpha", getTransitionAlpha());
        stream.addProperty("drawing:shadow", hasShadow());
        stream.addProperty("drawing:solidColor", getSolidColor());
        stream.addProperty("drawing:layerType", this.mLayerType);
        stream.addProperty("drawing:willNotDraw", willNotDraw());
        stream.addProperty("drawing:hardwareAccelerated", isHardwareAccelerated());
        stream.addProperty("drawing:willNotCacheDrawing", willNotCacheDrawing());
        stream.addProperty("drawing:drawingCacheEnabled", isDrawingCacheEnabled());
        stream.addProperty("drawing:overlappingRendering", hasOverlappingRendering());
        stream.addProperty("drawing:outlineAmbientShadowColor", getOutlineAmbientShadowColor());
        stream.addProperty("drawing:outlineSpotShadowColor", getOutlineSpotShadowColor());
        stream.addProperty("focus:hasFocus", hasFocus());
        stream.addProperty("focus:isFocused", isFocused());
        stream.addProperty("focus:focusable", getFocusable());
        stream.addProperty("focus:isFocusable", isFocusable());
        stream.addProperty("focus:isFocusableInTouchMode", isFocusableInTouchMode());
        stream.addProperty("misc:clickable", isClickable());
        stream.addProperty("misc:pressed", isPressed());
        stream.addProperty("misc:selected", isSelected());
        stream.addProperty("misc:touchMode", isInTouchMode());
        stream.addProperty("misc:hovered", isHovered());
        stream.addProperty("misc:activated", isActivated());
        stream.addProperty("misc:visibility", getVisibility());
        stream.addProperty("misc:fitsSystemWindows", getFitsSystemWindows());
        stream.addProperty("misc:filterTouchesWhenObscured", getFilterTouchesWhenObscured());
        stream.addProperty("misc:enabled", isEnabled());
        stream.addProperty("misc:soundEffectsEnabled", isSoundEffectsEnabled());
        stream.addProperty("misc:hapticFeedbackEnabled", isHapticFeedbackEnabled());
        Resources.Theme theme = getContext().getTheme();
        if (theme != null) {
            stream.addPropertyKey("theme");
            theme.encode(stream);
        }
        int n = this.mAttributes != null ? this.mAttributes.length : 0;
        stream.addProperty("meta:__attrCount__", n / 2);
        for (int i = 0; i < n; i += 2) {
            stream.addProperty("meta:__attr__" + this.mAttributes[i], this.mAttributes[i + 1]);
        }
        stream.addProperty("misc:scrollBarStyle", getScrollBarStyle());
        stream.addProperty("text:textDirection", getTextDirection());
        stream.addProperty("text:textAlignment", getTextAlignment());
        CharSequence contentDescription = getContentDescription();
        stream.addUserProperty(
                "accessibility:contentDescription",
                contentDescription == null ? "" : contentDescription.toString());
        stream.addProperty("accessibility:labelFor", getLabelFor());
        stream.addProperty(
                "accessibility:importantForAccessibility", getImportantForAccessibility());
    }

    boolean shouldDrawRoundScrollbar() {
        if (!this.mResources.getConfiguration().isScreenRound() || this.mAttachInfo == null) {
            return false;
        }
        View rootView = getRootView();
        getRootWindowInsets();
        int height = getHeight();
        int width = getWidth();
        int displayHeight = rootView.getHeight();
        int displayWidth = rootView.getWidth();
        return height == displayHeight && width == displayWidth;
    }

    @RemotableViewMethod
    public void semSetHoverPopupType(int type) {
        if (isHoveringUIEnabled()) {
            this.mHoverPopupType = type;
            if (this.mHoverPopupType == 1) {
                semSetTooltipText(getContentDescription());
            } else if ((this.mSemViewFlags & 2) == 2) {
                semSetTooltipText(null);
            }
        }
    }

    private void hidden_semSetHoverPopupType(int type) {
        semSetHoverPopupType(type);
    }

    public int semGetHoverPopupType() {
        if (isHoveringUIEnabled()) {
            return this.mHoverPopupType;
        }
        return 0;
    }

    public SemHoverPopupWindow semGetHoverPopup(boolean createIfNotExist) {
        if (!isHoveringUIEnabled()) {
            return null;
        }
        if (this.mHoverPopup == null) {
            if (!createIfNotExist) {
                return null;
            }
            this.mHoverPopup = new SemHoverPopupWindow(this, this.mHoverPopupType);
        }
        if (semIsDesktopMode()) {
            setSemHoverPopupWindowSettings(3);
        } else {
            setSemHoverPopupWindowSettings(2);
        }
        return this.mHoverPopup;
    }

    private SemHoverPopupWindow hidden_semGetHoverPopup(boolean createIfNotExist) {
        return semGetHoverPopup(createIfNotExist);
    }

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
                            this.mHoverPopup = new SemHoverPopupWindow(this, this.mHoverPopupType);
                            break;
                    }
                case 2:
                case 3:
                    this.mHoverPopup = new SemHoverPopupWindow(this, this.mHoverPopupType);
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

    public SemHoverPopupWindow semGetHoverPopup(int tooltype, boolean calledByApp) {
        int backupHoverPopupToolTypeByApp = this.mHoverPopupToolTypeByApp;
        semGetHoverPopup(tooltype);
        if (!calledByApp) {
            this.mHoverPopupToolTypeByApp = backupHoverPopupToolTypeByApp;
        }
        return this.mHoverPopup;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001d, code lost:

       return true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean setSemHoverPopupWindowSettings(int r4) {
        /*
            r3 = this;
            com.samsung.android.widget.SemHoverPopupWindow r0 = r3.mHoverPopup
            if (r0 == 0) goto L1e
            r0 = 1
            switch(r4) {
                case 1: goto L17;
                case 2: goto L10;
                case 3: goto L9;
                default: goto L8;
            }
        L8:
            goto L1d
        L9:
            com.samsung.android.widget.SemHoverPopupWindow r1 = r3.mHoverPopup
            r2 = 3
            r1.setHoverPopupToolType(r2)
            goto L1d
        L10:
            com.samsung.android.widget.SemHoverPopupWindow r1 = r3.mHoverPopup
            r2 = 2
            r1.setHoverPopupToolType(r2)
            goto L1d
        L17:
            com.samsung.android.widget.SemHoverPopupWindow r1 = r3.mHoverPopup
            r1.setHoverPopupToolType(r0)
        L1d:
            return r0
        L1e:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.View.setSemHoverPopupWindowSettings(int):boolean");
    }

    protected boolean isHoveringUIEnabled() {
        if ((sHoverUIEnableFlag & 15) == 0) {
            sHoverUIEnableFlag &= -16;
            sHoverUIEnableFlag |= ViewRune.WIDGET_PEN_SUPPORTED ? 1 : 2;
        }
        return (sHoverUIEnableFlag & 15) == 1 || semIsDesktopMode();
    }

    protected int getHoverUIFeatureLevel() {
        if (sHoverUIFeatureLevel < 0 && !sIsCheckedHoverUIFeatureLevel) {
            sIsCheckedHoverUIFeatureLevel = true;
            try {
                boolean z = ViewRune.WIDGET_PEN_SUPPORTED;
                sHoverUIFeatureLevel = 2;
            } catch (AbstractMethodError e) {
                Log.d(VIEW_LOG_TAG, "AbstractMethodError occured.");
            }
        }
        return sHoverUIFeatureLevel;
    }

    public void setTooltipText(CharSequence tooltipText) {
        if (TextUtils.isEmpty(tooltipText)) {
            setFlags(0, 1073741824);
            hideTooltip();
            this.mTooltipInfo = null;
            return;
        }
        setFlags(1073741824, 1073741824);
        if (this.mTooltipInfo == null) {
            this.mTooltipInfo = new TooltipInfo();
            this.mTooltipInfo.mShowTooltipRunnable =
                    new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            View.this.showHoverTooltip();
                        }
                    };
            this.mTooltipInfo.mHideTooltipRunnable =
                    new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda14
                        @Override // java.lang.Runnable
                        public final void run() {
                            View.this.hideTooltip();
                        }
                    };
            this.mTooltipInfo.mHoverSlop =
                    ViewConfiguration.get(this.mContext).getScaledHoverSlop();
            this.mTooltipInfo.clearAnchorPos();
        }
        this.mTooltipInfo.mTooltipText = tooltipText;
    }

    public void setTooltip(CharSequence tooltipText) {
        setTooltipText(tooltipText);
    }

    private void semSetTooltipText(CharSequence tooltipText) {
        if (TextUtils.isEmpty(tooltipText)) {
            if ((this.mSemViewFlags & 2) != 0) {
                this.mSemViewFlags &= -3;
            }
        } else if ((this.mSemViewFlags & 2) == 0) {
            this.mSemViewFlags |= 2;
        }
        setTooltipText(tooltipText);
    }

    public CharSequence getTooltipText() {
        if (this.mTooltipInfo != null) {
            return this.mTooltipInfo.mTooltipText;
        }
        return null;
    }

    public CharSequence getTooltip() {
        return getTooltipText();
    }

    public void setTooltipPosition(int x, int y) {
        if (this.mTooltipInfo != null) {
            this.mTooltipInfo.mSemX = x;
            this.mTooltipInfo.mSemY = y;
            this.mTooltipInfo.mSemSetTooltipPosition = true;
        }
    }

    public int getTooltipPositionX() {
        if (this.mTooltipInfo != null) {
            return this.mTooltipInfo.mSemX;
        }
        return 0;
    }

    public int getTooltipPositionY() {
        if (this.mTooltipInfo != null) {
            return this.mTooltipInfo.mSemY;
        }
        return 0;
    }

    public void setTooltipNull(boolean tooltipNull) {
        if (this.mTooltipInfo != null) {
            this.mTooltipInfo.mSemIsTooltipNull = tooltipNull;
        }
    }

    private boolean showTooltip(int x, int y, boolean fromLongClick) {
        ViewRootImpl root;
        if (this.mAttachInfo == null || this.mTooltipInfo == null) {
            return false;
        }
        if ((fromLongClick && (this.mViewFlags & 32) != 0)
                || TextUtils.isEmpty(this.mTooltipInfo.mTooltipText)) {
            return false;
        }
        hideTooltip();
        this.mTooltipInfo.mTooltipFromLongClick = fromLongClick;
        this.mTooltipInfo.mTooltipPopup = new TooltipPopup(getContext());
        boolean fromTouch = (this.mPrivateFlags3 & 131072) == 131072;
        if (CoreRune.MW_CAPTION_SHELL && (root = getViewRootImpl()) != null) {
            int multiWindowFlags = root.mWindowAttributes.multiWindowFlags;
            if ((multiWindowFlags & 1) != 0) {
                this.mTooltipInfo.mTooltipPopup.setForCaptionMenuButton();
                fromTouch = true;
            } else if ((multiWindowFlags & 2) != 0) {
                this.mTooltipInfo.mTooltipPopup.setForCaptionPopupButton();
                fromTouch = fromLongClick;
            }
        }
        this.mTooltipInfo.mTooltipPopup.show(this, x, y, fromTouch, this.mTooltipInfo.mTooltipText);
        this.mAttachInfo.mTooltipHost = this;
        notifyViewAccessibilityStateChangedIfNeeded(0);
        return true;
    }

    void hideTooltip() {
        if (this.mTooltipInfo == null) {
            return;
        }
        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        if (this.mTooltipInfo.mTooltipPopup == null) {
            return;
        }
        this.mTooltipInfo.mTooltipPopup.hide();
        this.mTooltipInfo.mTooltipPopup = null;
        this.mTooltipInfo.mTooltipFromLongClick = false;
        this.mTooltipInfo.clearAnchorPos();
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mTooltipHost = null;
        }
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public void semHideTooltip() {
        hideTooltip();
    }

    private boolean showLongClickTooltip(int x, int y) {
        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        return showTooltip(x, y, true);
    }

    public boolean showHoverTooltip() {
        return showTooltip(this.mTooltipInfo.mAnchorX, this.mTooltipInfo.mAnchorY, false);
    }

    boolean dispatchTooltipHoverEvent(MotionEvent event) {
        int timeout;
        if (this.mTooltipInfo == null) {
            return false;
        }
        boolean isDeviceDefault =
                this.mAttachInfo != null && this.mAttachInfo.mViewRootImpl.mIsDeviceDefault;
        if (isDeviceDefault && event.isFromSource(16386) && !isSPenHoveringSettingsEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case 7:
                if ((this.mViewFlags & 1073741824) == 1073741824
                        && (!isDeviceDefault || (this.mViewFlags & 32) == 0)) {
                    if (!this.mTooltipInfo.mTooltipFromLongClick
                            && this.mTooltipInfo.updateAnchorPos(event)) {
                        if (this.mTooltipInfo.mTooltipPopup == null) {
                            removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
                            postDelayed(
                                    this.mTooltipInfo.mShowTooltipRunnable,
                                    ViewConfiguration.getHoverTooltipShowTimeout());
                        }
                        if ((getWindowSystemUiVisibility() & 1) == 1) {
                            timeout = ViewConfiguration.getHoverTooltipHideShortTimeout();
                        } else {
                            timeout = ViewConfiguration.getHoverTooltipHideTimeout();
                        }
                        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
                        postDelayed(this.mTooltipInfo.mHideTooltipRunnable, timeout);
                    }
                    return true;
                }
                return false;
            case 10:
                this.mTooltipInfo.clearAnchorPos();
                if (!this.mTooltipInfo.mTooltipFromLongClick) {
                    hideTooltip();
                }
                return false;
            default:
                return false;
        }
    }

    void handleTooltipKey(KeyEvent event) {
        switch (event.getAction()) {
            case 0:
                if (event.getRepeatCount() == 0) {
                    hideTooltip();
                    break;
                }
                break;
            case 1:
                handleTooltipUp();
                break;
        }
    }

    private void handleTooltipUp() {
        if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
            return;
        }
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        postDelayed(
                this.mTooltipInfo.mHideTooltipRunnable,
                ViewConfiguration.getLongPressTooltipHideTimeout());
    }

    private int getFocusableAttribute(TypedArray attributes) {
        TypedValue val = new TypedValue();
        if (attributes.getValue(19, val)) {
            if (val.type == 18) {
                return val.data == 0 ? 0 : 1;
            }
            return val.data;
        }
        return 16;
    }

    public View getTooltipView() {
        if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
            return null;
        }
        return this.mTooltipInfo.mTooltipPopup.getContentView();
    }

    public static boolean isDefaultFocusHighlightEnabled() {
        return sUseDefaultFocusHighlight;
    }

    View dispatchUnhandledKeyEvent(KeyEvent evt) {
        if (onUnhandledKeyEvent(evt)) {
            return this;
        }
        return null;
    }

    boolean onUnhandledKeyEvent(KeyEvent event) {
        if (this.mListenerInfo != null && this.mListenerInfo.mUnhandledKeyListeners != null) {
            for (int i = this.mListenerInfo.mUnhandledKeyListeners.size() - 1; i >= 0; i--) {
                if (((OnUnhandledKeyEventListener) this.mListenerInfo.mUnhandledKeyListeners.get(i))
                        .onUnhandledKeyEvent(this, event)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    boolean hasUnhandledKeyListener() {
        return (this.mListenerInfo == null
                        || this.mListenerInfo.mUnhandledKeyListeners == null
                        || this.mListenerInfo.mUnhandledKeyListeners.isEmpty())
                ? false
                : true;
    }

    public void addOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        ArrayList<OnUnhandledKeyEventListener> listeners = getListenerInfo().mUnhandledKeyListeners;
        if (listeners == null) {
            listeners = new ArrayList<>();
            getListenerInfo().mUnhandledKeyListeners = listeners;
        }
        listeners.add(listener);
        if (listeners.size() == 1 && (this.mParent instanceof ViewGroup)) {
            ((ViewGroup) this.mParent).incrementChildUnhandledKeyListeners();
        }
    }

    public void removeOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        if (this.mListenerInfo != null
                && this.mListenerInfo.mUnhandledKeyListeners != null
                && !this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
            this.mListenerInfo.mUnhandledKeyListeners.remove(listener);
            if (this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
                this.mListenerInfo.mUnhandledKeyListeners = null;
                if (this.mParent instanceof ViewGroup) {
                    ((ViewGroup) this.mParent).decrementChildUnhandledKeyListeners();
                }
            }
        }
    }

    protected void setDetached(boolean detached) {
        if (detached) {
            this.mPrivateFlags4 |= 8192;
        } else {
            this.mPrivateFlags4 &= -8193;
        }
    }

    public void setIsCredential(boolean isCredential) {
        if (isCredential) {
            this.mPrivateFlags4 |= 131072;
        } else {
            this.mPrivateFlags4 &= -131073;
        }
    }

    public boolean isCredential() {
        return (this.mPrivateFlags4 & 131072) == 131072;
    }

    private boolean isSPenHoveringSettingsEnabled() {
        return Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0, -3)
                == 1;
    }

    public boolean semIsDesktopMode() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            return viewRootImpl.isDesktopMode();
        }
        return this.mResources.getConfiguration().semDesktopModeEnabled == 1;
    }

    public void setAutoHandwritingEnabled(boolean enabled) {
        if (enabled) {
            this.mPrivateFlags4 |= 65536;
        } else {
            this.mPrivateFlags4 &= -65537;
        }
        updatePositionUpdateListener();
        postUpdate(new View$$ExternalSyntheticLambda0(this));
    }

    public boolean isAutoHandwritingEnabled() {
        return (this.mPrivateFlags4 & 65536) == 65536;
    }

    public boolean isStylusHandwritingAvailable() {
        return ((InputMethodManager) getContext().getSystemService(InputMethodManager.class))
                .isStylusHandwritingAvailable();
    }

    private void setTraversalTracingEnabled(boolean enabled) {
        if (enabled) {
            if (this.mTracingStrings == null) {
                this.mTracingStrings = new ViewTraversalTracingStrings(this);
            }
            this.mPrivateFlags4 |= 262144;
            return;
        }
        this.mPrivateFlags4 &= -262145;
    }

    private boolean isTraversalTracingEnabled() {
        return (this.mPrivateFlags4 & 262144) == 262144;
    }

    private void setRelayoutTracingEnabled(boolean enabled) {
        if (enabled) {
            if (this.mTracingStrings == null) {
                this.mTracingStrings = new ViewTraversalTracingStrings(this);
            }
            this.mPrivateFlags4 |= 524288;
            return;
        }
        this.mPrivateFlags4 &= -524289;
    }

    private boolean isRelayoutTracingEnabled() {
        return (this.mPrivateFlags4 & 524288) == 524288;
    }

    public void onCreateViewTranslationRequest(
            int[] supportedFormats, Consumer<ViewTranslationRequest> requestsCollector) {}

    public void onCreateVirtualViewTranslationRequests(
            long[] virtualIds,
            int[] supportedFormats,
            Consumer<ViewTranslationRequest> requestsCollector) {}

    public ViewTranslationCallback getViewTranslationCallback() {
        return this.mViewTranslationCallback;
    }

    public void setViewTranslationCallback(ViewTranslationCallback callback) {
        this.mViewTranslationCallback = callback;
    }

    public void clearViewTranslationCallback() {
        this.mViewTranslationCallback = null;
    }

    public ViewTranslationResponse getViewTranslationResponse() {
        return this.mViewTranslationResponse;
    }

    public void onViewTranslationResponse(ViewTranslationResponse response) {
        this.mViewTranslationResponse = response;
    }

    public void clearViewTranslationResponse() {
        this.mViewTranslationResponse = null;
    }

    public void onVirtualViewTranslationResponses(
            LongSparseArray<ViewTranslationResponse> response) {}

    public void dispatchCreateViewTranslationRequest(
            Map<AutofillId, long[]> viewIds,
            int[] supportedFormats,
            TranslationCapability capability,
            final List<ViewTranslationRequest> requests) {
        AutofillId autofillId = getAutofillId();
        if (viewIds.containsKey(autofillId)) {
            if (viewIds.get(autofillId) == null) {
                onCreateViewTranslationRequest(
                        supportedFormats, new ViewTranslationRequestConsumer(requests));
            } else {
                onCreateVirtualViewTranslationRequests(
                        viewIds.get(autofillId),
                        supportedFormats,
                        new Consumer() { // from class: android.view.View$$ExternalSyntheticLambda12
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                requests.add((ViewTranslationRequest) obj);
                            }
                        });
            }
        }
    }

    private class ViewTranslationRequestConsumer implements Consumer<ViewTranslationRequest> {
        private boolean mCalled;
        private final List<ViewTranslationRequest> mRequests;

        ViewTranslationRequestConsumer(List<ViewTranslationRequest> requests) {
            this.mRequests = requests;
        }

        @Override // java.util.function.Consumer
        public void accept(ViewTranslationRequest request) {
            if (this.mCalled) {
                throw new IllegalStateException("The translation Consumer is not reusable.");
            }
            this.mCalled = true;
            if (request != null && request.getKeys().size() > 0) {
                this.mRequests.add(request);
                if (Log.isLoggable(View.CONTENT_CAPTURE_LOG_TAG, 2)) {
                    Log.v(
                            View.CONTENT_CAPTURE_LOG_TAG,
                            "Calling setHasTransientState(true) for " + View.this.getAutofillId());
                }
                View.this.setHasTransientState(true);
                View.this.setHasTranslationTransientState(true);
            }
        }
    }

    public void generateDisplayHash(
            String hashAlgorithm,
            Rect bounds,
            final Executor executor,
            final DisplayHashResultCallback callback) {
        IWindowSession session = getWindowSession();
        if (session == null) {
            callback.onDisplayHashError(-3);
            return;
        }
        IWindow window = getWindow();
        if (window == null) {
            callback.onDisplayHashError(-3);
            return;
        }
        Rect visibleBounds = new Rect();
        getGlobalVisibleRect(visibleBounds);
        if (bounds != null && bounds.isEmpty()) {
            callback.onDisplayHashError(-2);
            return;
        }
        if (bounds != null) {
            bounds.offset(visibleBounds.left, visibleBounds.top);
            visibleBounds.intersectUnchecked(bounds);
        }
        if (visibleBounds.isEmpty()) {
            callback.onDisplayHashError(-4);
            return;
        }
        RemoteCallback remoteCallback =
                new RemoteCallback(
                        new RemoteCallback
                                .OnResultListener() { // from class:
                                                      // android.view.View$$ExternalSyntheticLambda7
                            @Override // android.os.RemoteCallback.OnResultListener
                            public final void onResult(Bundle bundle) {
                                executor.execute(
                                        new Runnable() { // from class:
                                                         // android.view.View$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                View.lambda$generateDisplayHash$6(Bundle.this, r2);
                                            }
                                        });
                            }
                        });
        try {
            session.generateDisplayHash(window, visibleBounds, hashAlgorithm, remoteCallback);
        } catch (RemoteException e) {
            Log.e(VIEW_LOG_TAG, "Failed to call generateDisplayHash");
            callback.onDisplayHashError(-1);
        }
    }

    static /* synthetic */ void lambda$generateDisplayHash$6(
            Bundle result, DisplayHashResultCallback callback) {
        DisplayHash displayHash =
                (DisplayHash)
                        result.getParcelable(
                                DisplayHashResultCallback.EXTRA_DISPLAY_HASH, DisplayHash.class);
        int errorCode = result.getInt(DisplayHashResultCallback.EXTRA_DISPLAY_HASH_ERROR_CODE, -1);
        if (displayHash != null) {
            callback.onDisplayHashResult(displayHash);
        } else {
            callback.onDisplayHashError(errorCode);
        }
    }

    public AttachedSurfaceControl getRootSurfaceControl() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.getRootSurfaceControl();
        }
        return null;
    }

    protected int calculateFrameRateCategory() {
        int category;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        Object obj = this.mParent;
        boolean isInputMethodWindowType = viewRootImpl.mWindowAttributes.type == 2011;
        if (((this.mPrivateFlags4 & 402653184) == 402653184
                        || this.mLastFrameLeft != this.mLeft
                        || this.mLastFrameTop != this.mTop)
                && viewRootImpl.shouldCheckFrameRateCategory()
                && (obj instanceof View)
                && ((View) obj).getFrameContentVelocity() <= 0.0f
                && ((!CoreRune.FW_DVRR_TOOLKIT_POLICY_FOR_SCROLL
                                || !viewRootImpl.getScrollFlingState())
                        && !isInputMethodWindowType)) {
            if (CoreRune.FW_DVRR_TOOLKIT_FORCE_DEFAULT_NORMAL) {
                return 134217731;
            }
            return 134217732;
        }
        switch (viewRootImpl.intermittentUpdateState()) {
            case 0:
                if (!sToolkitFrameRateBySizeReadOnlyFlagValue) {
                    category = 3;
                } else {
                    int category2 = this.mSizeBasedFrameRateCategoryAndReason;
                    category = Math.min(category2 & 65535, 3);
                }
                return category | 33554432;
            case 1:
                int category3 = this.mSizeBasedFrameRateCategoryAndReason;
                return category3;
            default:
                int category4 = this.mLastFrameRateCategory;
                return category4;
        }
    }

    protected void votePreferredFrameRate() {
        int frameRateCategory;
        int compatibility;
        float frameRateToSet;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        float velocity = this.mFrameContentVelocity;
        float frameRate = this.mPreferredFrameRate;
        if (viewRootImpl.shouldCheckFrameRate(frameRate > 0.0f)
                && (frameRate > 0.0f || (this.mAttachInfo.mViewVelocityApi && velocity > 0.0f))) {
            float velocityFrameRate = 0.0f;
            if (this.mAttachInfo.mViewVelocityApi && velocity > 0.0f) {
                velocityFrameRate = convertVelocityToFrameRate(velocity);
            }
            if (frameRate >= velocityFrameRate) {
                compatibility = 1;
                frameRateToSet = frameRate;
            } else {
                compatibility = 103;
                frameRateToSet = velocityFrameRate;
            }
            viewRootImpl.votePreferredFrameRate(frameRateToSet, compatibility);
        }
        if (viewRootImpl.shouldCheckFrameRateCategory()) {
            if (sToolkitMetricsForFrameRateDecisionFlagValue) {
                int width = this.mRight - this.mLeft;
                int height = this.mBottom - this.mTop;
                float sizePercentage = (width * height) / this.mAttachInfo.mDisplayPixelCount;
                viewRootImpl.recordViewPercentage(sizePercentage);
            }
            if (Float.isNaN(frameRate)) {
                frameRateCategory = calculateFrameRateCategory();
            } else if (frameRate < 0.0f) {
                switch ((int) frameRate) {
                    case -4:
                        frameRateCategory = 67108869;
                        break;
                    case -3:
                        frameRateCategory = 67108867;
                        break;
                    case -2:
                        frameRateCategory = Enums.AUDIO_FORMAT_AAC_LC;
                        break;
                    case -1:
                        frameRateCategory = Enums.AUDIO_FORMAT_AAC_MAIN;
                        break;
                    default:
                        int category = sToolkitFrameRateDefaultNormalReadOnlyFlagValue ? 3 : 5;
                        frameRateCategory = category | 83886080;
                        break;
                }
            } else {
                frameRateCategory = Enums.AUDIO_FORMAT_AAC_MAIN;
            }
            int category2 = 65535 & frameRateCategory;
            int reason = (-65536) & frameRateCategory;
            viewRootImpl.votePreferredFrameRateCategory(category2, reason, this);
            this.mLastFrameRateCategory = frameRateCategory;
        }
        int frameRateCategory2 = this.mLeft;
        this.mLastFrameLeft = frameRateCategory2;
        this.mLastFrameTop = this.mTop;
    }

    private float convertVelocityToFrameRate(float velocityPps) {
        float density = this.mAttachInfo.mDensity;
        float velocityDps = velocityPps / density;
        if (!CoreRune.FW_DVRR_TOOLKIT_POLICY) {
            if (velocityDps > 300.0f) {
                return MAX_FRAME_RATE;
            }
            if (velocityDps > 125.0f) {
                return 80.0f;
            }
            return 60.0f;
        }
        if (velocityDps > this.VELOCITY_THRESHOLD_T1) {
            float frameRate = this.VELOCITY_FRAMERATE1;
            return frameRate;
        }
        float frameRate2 = this.VELOCITY_THRESHOLD_T2;
        if (velocityDps > frameRate2) {
            float frameRate3 = this.VELOCITY_FRAMERATE2;
            return frameRate3;
        }
        float frameRate4 = this.VELOCITY_FRAMERATE3;
        return frameRate4;
    }

    public void setFrameContentVelocity(float pixelsPerSecond) {
        ViewRootImpl viewRoot;
        if (this.mAttachInfo != null && this.mAttachInfo.mViewVelocityApi) {
            this.mFrameContentVelocity = Math.abs(pixelsPerSecond);
            if (CoreRune.FW_DVRR_TOOLKIT_POLICY_FOR_SCROLL
                    && (viewRoot = getViewRootImpl()) != null) {
                boolean isFlingState = pixelsPerSecond > 0.0f;
                if (isFlingState != this.mIsFlingState) {
                    if (isFlingState) {
                        Log.d(
                                VIEW_LOG_TAG,
                                "pixelsPerSecond="
                                        + pixelsPerSecond
                                        + " T1="
                                        + this.VELOCITY_THRESHOLD_T1
                                        + " T2="
                                        + this.VELOCITY_THRESHOLD_T2
                                        + " T3="
                                        + this.VELOCITY_THRESHOLD_T3
                                        + " frameRate1="
                                        + this.VELOCITY_FRAMERATE1
                                        + " frameRate2="
                                        + this.VELOCITY_FRAMERATE2
                                        + " frameRate3="
                                        + this.VELOCITY_FRAMERATE3
                                        + " frameRate4="
                                        + this.VELOCITY_FRAMERATE4);
                    }
                    this.mIsFlingState = isFlingState;
                    viewRoot.setScrollFlingState(isFlingState);
                }
            }
            if (sToolkitMetricsForFrameRateDecisionFlagValue) {
                Trace.setCounter("Set frame velocity", (long) this.mFrameContentVelocity);
            }
        }
    }

    public float getFrameContentVelocity() {
        if (this.mAttachInfo == null || !this.mAttachInfo.mViewVelocityApi) {
            return 0.0f;
        }
        return Math.max(this.mFrameContentVelocity, 0.0f);
    }

    public void setRequestedFrameRate(float frameRate) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            Log.i(
                    VIEW_LOG_TAG,
                    "setRequestedFrameRate frameRate="
                            + frameRate
                            + ", this="
                            + this
                            + ", caller="
                            + Debug.getCallers(5));
            this.mPreferredFrameRate = frameRate;
        }
    }

    public float getRequestedFrameRate() {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            return this.mPreferredFrameRate;
        }
        return 0.0f;
    }

    public boolean onKeyTextMultiSelection(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean dispatchKeyEventTextMultiSelection(KeyEvent event) {
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            return onKeyTextMultiSelection(event.getKeyCode(), event);
        }
        return false;
    }

    public boolean hasCallbacks(Runnable action) {
        AttachInfo attachInfo;
        if (action != null && (attachInfo = this.mAttachInfo) != null) {
            return attachInfo.mHandler.hasCallbacks(action);
        }
        return false;
    }

    @RemotableViewMethod
    @Deprecated(forRemoval = true, since = "13.0")
    public void semSetBlurEnabled(boolean enabled) {
        if (TEST_BLUR_DISABLED != 0) {
            return;
        }
        if (enabled) {
            semSetBlurMode(0);
        } else {
            clearBlurMode();
        }
    }

    public boolean semGetBlurEnabled() {
        return this.mBlurMode == 0;
    }

    @RemotableViewMethod
    public void semSetBlurMode(int blurMode) {
        if (TEST_BLUR_DISABLED != 0) {
            return;
        }
        if (blurMode < -1 || blurMode > 2) {
            Log.e(VIEW_LOG_TAG, "blurMode " + blurMode + " is not valid!");
        } else {
            if (this.mBlurMode == blurMode) {
                return;
            }
            clearBlurMode();
            this.mBlurMode = blurMode;
            invalidateBlur();
        }
    }

    @RemotableViewMethod
    public void semSetBlurRadius(int blurRadius) {
        this.mBlurRadius = blurRadius;
        if (this.mBlurMode == 2) {
            this.mBlurRadius /= this.mCanvasDownScale;
        }
        invalidateBlur();
    }

    @RemotableViewMethod
    public void semSetBackgroundBlurCornerRadius(float cornerRadius) {
        if (cornerRadius >= 0.0f) {
            this.mBackgroundBlurCornerRadiusTL = cornerRadius;
            this.mBackgroundBlurCornerRadiusTR = cornerRadius;
            this.mBackgroundBlurCornerRadiusBL = cornerRadius;
            this.mBackgroundBlurCornerRadiusBR = cornerRadius;
            invalidateBlur();
        }
    }

    @RemotableViewMethod
    public void semSetBackgroundBlurColor(int color) {
        this.mBackgroundBlurColor = color;
        invalidateBlur();
    }

    @RemotableViewMethod
    public void semSetBlurInfo(SemBlurInfo blurInfo) {
        Log.d(VIEW_LOG_TAG, "semSetBlurInfo, blurInfo : " + blurInfo + ", " + this);
        if (TEST_BLUR_DISABLED != 0) {}
        if (blurInfo == null) {
            clearBlurMode();
            return;
        }
        if (this.mBlurMode != blurInfo.getBlurMode()) {
            clearBlurMode();
        }
        this.mBlurInfo = blurInfo;
        this.mBlurMode = this.mBlurInfo.getBlurMode();
        this.mBlurColorCurve = this.mBlurInfo.getColorCurve();
        switch (this.mBlurMode) {
            case 0:
                boolean isBlurRadiusChanged = CoreRune.FW_WINDOW_BLUR_SUPPORTED;
                if (isBlurRadiusChanged) {
                    this.mBlurRadius = this.mBlurInfo.getBlurRadius();
                    this.mBackgroundBlurColor = this.mBlurInfo.getBackgroundBlurColor();
                    float[] cornerRadius = new float[4];
                    this.mBlurInfo.getBackgroundBlurCornerRadius(cornerRadius);
                    this.mBackgroundBlurCornerRadiusTL = cornerRadius[0];
                    this.mBackgroundBlurCornerRadiusTR = cornerRadius[1];
                    this.mBackgroundBlurCornerRadiusBL = cornerRadius[2];
                    this.mBackgroundBlurCornerRadiusBR = cornerRadius[3];
                    invalidateBlurBackground();
                    break;
                } else {
                    this.mBlurMode = -1;
                    this.mBlurInfo = null;
                    Log.e(VIEW_LOG_TAG, "This model doesn't support window blur!");
                    break;
                }
            case 1:
                this.mCapturedBitmap = this.mBlurInfo.getCapturedBitmap();
                this.mBlurRadius = this.mBlurInfo.getBlurRadius();
                boolean isBlurRadiusChanged2 = this.mLastBlurRadius != this.mBlurRadius;
                invalidateCapturedBlur(isBlurRadiusChanged2);
                this.mLastBlurRadius = this.mBlurRadius;
                break;
            case 2:
                this.mCanvasDownScale = this.mBlurInfo.getCanvasDownScale();
                this.mBlurRadius = this.mBlurInfo.getBlurRadius() / this.mCanvasDownScale;
                invalidateCanvasBlur();
                postInvalidate();
                break;
        }
    }

    private void hidden_semSetBlurInfo(SemBlurInfo blurInfo) {
        semSetBlurInfo(blurInfo);
    }

    private void invalidateBlur() {
        if (this.mBlurInfo != null) {
            semSetBlurInfo(this.mBlurInfo);
            return;
        }
        switch (this.mBlurMode) {
            case 0:
                if (CoreRune.FW_WINDOW_BLUR_SUPPORTED) {
                    invalidateBlurBackground();
                    break;
                } else {
                    this.mBlurMode = -1;
                    Log.e(VIEW_LOG_TAG, "This model doesn't support window blur!");
                    break;
                }
            case 1:
                invalidateCapturedBlur(false);
                break;
            case 2:
                invalidateCanvasBlur();
                break;
        }
    }

    private void clearBlurMode() {
        switch (this.mBlurMode) {
            case 0:
                clearBlurBackground();
                break;
            case 1:
                clearCapturedBlur();
                break;
            case 2:
                clearCanvasBlur();
                break;
        }
        this.mBlurMode = -1;
    }

    private void invalidateBlurBackground() {
        BackgroundBlurDrawable drawable;
        if (this.mBlurMode == 0) {
            if (this.mBackground instanceof BackgroundBlurDrawable) {
                drawable = (BackgroundBlurDrawable) this.mBackground;
            } else if (this.mAttachInfo != null) {
                drawable =
                        this.mAttachInfo.mViewRootImpl.createBackgroundBlurDrawable(isBlurDebug());
                setBackground(drawable);
            } else {
                return;
            }
            drawable.setBlurRadius(this.mBlurRadius);
            drawable.setCornerRadius(
                    this.mBackgroundBlurCornerRadiusTL,
                    this.mBackgroundBlurCornerRadiusTR,
                    this.mBackgroundBlurCornerRadiusBL,
                    this.mBackgroundBlurCornerRadiusBR);
            drawable.setColor(this.mBackgroundBlurColor);
            if (this.mBlurColorCurve != null) {
                drawable.setBlurColorCurve(this.mBlurColorCurve);
            }
            invalidateDrawable(drawable);
            return;
        }
        clearBlurBackground();
    }

    private void clearBlurBackground() {
        if (this.mBackground instanceof BackgroundBlurDrawable) {
            setBackground(null);
        }
    }

    private void invalidateCapturedBlur(boolean isBlurRadiusChanged) {
        if (this.mBlurMode == 1) {
            if (this.mBlurFilter == null) {
                this.mBlurFilter = new SemGfxImageFilter();
            }
            this.mBlurFilter.setBlurRadius(this.mBlurRadius);
            if (this.mBlurColorCurve != null) {
                this.mBlurFilter.setProportionalSaturation(this.mBlurColorCurve.mSaturation);
                this.mBlurFilter.setCurveLevel(this.mBlurColorCurve.mCurveBias);
                this.mBlurFilter.setCurveMinX(this.mBlurColorCurve.mMinX);
                this.mBlurFilter.setCurveMaxX(this.mBlurColorCurve.mMaxX);
                this.mBlurFilter.setCurveMinY(this.mBlurColorCurve.mMinY);
                this.mBlurFilter.setCurveMaxY(this.mBlurColorCurve.mMaxY);
            }
            if (isBlurRadiusChanged) {
                invalidate();
                return;
            }
            return;
        }
        clearCapturedBlur();
    }

    private void clearCapturedBlur() {
        if (this.mBlurredBitmap != null) {
            this.mBlurredBitmap.recycle();
            this.mBlurredBitmap = null;
        }
        this.mBlurFilter = null;
        this.mBlurBitmapBounds = null;
        this.mBlurViewBounds = null;
        this.mCapturedBitmap = null;
    }

    private void invalidateCanvasBlur() {
        if (this.mBlurMode == 2 && this.mBlurRadius > 0) {
            if (this.mBlurFilter == null) {
                this.mBlurFilter = new SemGfxImageFilter();
            }
            this.mBlurFilter.setBlurRadius(this.mBlurRadius);
            if (this.mBlurColorCurve != null) {
                this.mBlurFilter.setProportionalSaturation(this.mBlurColorCurve.mSaturation);
                this.mBlurFilter.setCurveLevel(this.mBlurColorCurve.mCurveBias);
                this.mBlurFilter.setCurveMinX(this.mBlurColorCurve.mMinX);
                this.mBlurFilter.setCurveMaxX(this.mBlurColorCurve.mMaxX);
                this.mBlurFilter.setCurveMinY(this.mBlurColorCurve.mMinY);
                this.mBlurFilter.setCurveMaxY(this.mBlurColorCurve.mMaxY);
                return;
            }
            return;
        }
        clearCanvasBlur();
    }

    private void clearCanvasBlur() {
        if (this.mCanvasBlurBitmap != null) {
            this.mCanvasBlurBitmap.recycle();
            this.mCanvasBlurBitmap = null;
        }
        this.mBlurFilter = null;
        this.mBlurBitmapBounds = null;
        this.mBlurViewBounds = null;
    }

    private void drawBlurBitmap(Canvas canvas) {
        switch (this.mBlurMode) {
            case -1:
                break;
            case 0:
                break;
            default:
                if (this.mBlurBitmapBounds == null) {
                    this.mBlurBitmapBounds = new Rect();
                }
                if (this.mBlurViewBounds == null) {
                    this.mBlurViewBounds = new Rect();
                }
                if (this.mBlurMode == 2) {
                    if (this.mAttachInfo != null) {
                        int width = getWidth() / this.mCanvasDownScale;
                        int height = getHeight() / this.mCanvasDownScale;
                        if (width > 0 && height > 0) {
                            if (this.mCanvasBlurBitmap == null) {
                                this.mCanvasBlurBitmap =
                                        Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                            }
                            if (this.mCanvasBlurBitmap != null) {
                                if (this.mCanvasBlurBitmap.getHeight() != height
                                        || this.mCanvasBlurBitmap.getWidth() != width) {
                                    this.mCanvasBlurBitmap.recycle();
                                    this.mCanvasBlurBitmap =
                                            Bitmap.createBitmap(
                                                    width, height, Bitmap.Config.ARGB_8888);
                                }
                                this.mCapturingCanvas = true;
                                Canvas blurCanvas = new Canvas(this.mCanvasBlurBitmap);
                                int restoreCount = blurCanvas.save();
                                int[] locations = new int[2];
                                getLocationInWindow(locations);
                                float scaleValue = 1.0f / this.mCanvasDownScale;
                                blurCanvas.scale(scaleValue, scaleValue);
                                blurCanvas.translate(-locations[0], -locations[1]);
                                Trace.traceBegin(8L, "capturing contents for blur " + this);
                                this.mAttachInfo.mRootView.draw(blurCanvas);
                                Trace.traceEnd(8L);
                                blurCanvas.restoreToCount(restoreCount);
                                this.mCapturingCanvas = false;
                            }
                            Bitmap bitmap = this.mCanvasBlurBitmap;
                            if (bitmap != null && this.mBlurRadius > 0) {
                                getLocationInWindow(this.mAttachInfo.mTmpLocation);
                                this.mBlurBitmapBounds.right = bitmap.getWidth();
                                this.mBlurBitmapBounds.bottom = bitmap.getHeight();
                                this.mBlurViewBounds.right = getWidth();
                                this.mBlurViewBounds.bottom = getHeight();
                                Trace.traceBegin(8L, "applying canvas blur " + this);
                                this.mBlurFilter.applyToBitmap(bitmap, bitmap);
                                canvas.drawBitmap(
                                        bitmap,
                                        this.mBlurBitmapBounds,
                                        this.mBlurViewBounds,
                                        (Paint) null);
                                Trace.traceEnd(8L);
                                break;
                            }
                        }
                    }
                } else if (this.mBlurMode == 1) {
                    if (this.mBlurFilter != null) {
                        if (this.mBlurredBitmap != null) {
                            this.mBlurredBitmap.recycle();
                            this.mBlurredBitmap = null;
                        }
                        if (this.mCapturedBitmap != null) {
                            this.mBlurredBitmap =
                                    this.mBlurFilter.applyToBitmap(this.mCapturedBitmap);
                        }
                    }
                    if (this.mBlurredBitmap != null && this.mBlurRadius > 0) {
                        this.mBlurBitmapBounds.right = this.mBlurredBitmap.getWidth();
                        this.mBlurBitmapBounds.bottom = this.mBlurredBitmap.getHeight();
                        this.mBlurViewBounds.right = getWidth();
                        this.mBlurViewBounds.bottom = getHeight();
                        canvas.drawBitmap(
                                this.mBlurredBitmap,
                                this.mBlurBitmapBounds,
                                this.mBlurViewBounds,
                                (Paint) null);
                        break;
                    }
                }
                break;
        }
    }

    public boolean isBlurDebug() {
        return getClass().getName().contains(DEBUG_BLUR_TARGET_NAME);
    }

    public void semSetGfxImageFilter(SemGfxImageFilter gfxImageFilter) {
        if (this.mGfxImageFilter != null) {
            this.mGfxImageFilter.onDetachedFromView();
        }
        this.mGfxImageFilter = gfxImageFilter;
        if (gfxImageFilter != null) {
            gfxImageFilter.onAttachToView(this);
        }
        invalidate(true);
    }

    public SemGfxImageFilter semGetGfxImageFilter() {
        return this.mGfxImageFilter;
    }

    public void semSetScrollBarTopPadding(int scrollBarTopPadding) {
        this.mAppWidgetScrollBarTopPadding = scrollBarTopPadding;
    }

    public void semSetScrollBarBottomPadding(int scrollBarBottomPadding) {
        this.mAppWidgetScrollBarBottomPadding = scrollBarBottomPadding;
    }

    public SemSmartClipDataExtractionListener semGetSmartClipDataExtractionListener() {
        return this.mSmartClipDataExtractionListener;
    }

    public boolean semSetSmartClipDataExtractionListener(
            SemSmartClipDataExtractionListener listener) {
        this.mSmartClipDataExtractionListener = listener;
        return true;
    }

    public SemSmartClipMetaTagArray semGetSmartClipTags() {
        return this.mSmartClipDataTag;
    }

    public boolean semSetSmartClipTags(SemSmartClipMetaTagArray tagArray) {
        this.mSmartClipDataTag = tagArray;
        return true;
    }

    public int semExtractSmartClipData(
            SemSmartClipCroppedArea croppedArea, SemSmartClipDataElement resultElement) {
        SmartClipDataElementImpl elementImpl = (SmartClipDataElementImpl) resultElement;
        if (elementImpl == null) {
            return 0;
        }
        SemSmartClipDataRepository repository = elementImpl.getDataRepository();
        SmartClipDataCropperImpl cropper =
                repository != null
                        ? (SmartClipDataCropperImpl) repository.getSmartClipDataCropper()
                        : null;
        if (cropper == null) {
            return 0;
        }
        return cropper.extractDefaultSmartClipData(this, croppedArea, elementImpl);
    }

    public int getLastBackgroundResource() {
        return -1;
    }

    void setBackgroundBounds(int squareSize) {
        if (this.mBackgroundSizeChanged && this.mBackground != null) {
            this.mBackground.setBounds(0, 0, squareSize, squareSize);
            this.mBackgroundSizeChanged = false;
            rebuildOutline();
        }
    }

    private void translateToWindowIfNeeded(Rect outRect) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        CompatTranslator translator =
                viewRootImpl != null ? viewRootImpl.getCompatTranslator() : null;
        if (translator != null) {
            translator.translateToWindow(outRect);
        }
    }

    private void postRequestSendStickyDragStartedEvent() {
        post(
                new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        View.this.lambda$postRequestSendStickyDragStartedEvent$8();
                    }
                });
    }

    public /* synthetic */ void lambda$postRequestSendStickyDragStartedEvent$8() {
        if (this.mParent != null) {
            this.mParent.requestSendStickyDragStartedEvent(this);
        }
    }

    public View semDispatchFindView(float x, float y, boolean findImage) {
        this.mBixbyTouchFoundText =
                SemPressGestureDetector.getText(
                        this.mContext, this.mContext.getPackageName(), this);
        if (this.mBixbyTouchFoundText != null) {
            return this;
        }
        return null;
    }

    public void semSetBixbyTouchFoundText(String text) {
        this.mBixbyTouchFoundText = text;
    }

    public String semGetBixbyTouchFoundText() {
        return this.mBixbyTouchFoundText;
    }
}
