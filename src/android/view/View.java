package android.view;

import android.animation.StateListAnimator;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.app.jank.AppJankStats;
import android.app.jank.JankTracker;
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
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.PointF;
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
import android.provider.CalendarContract;
import android.provider.Settings;
import android.security.keystore.KeyProperties;
import android.service.credentials.CredentialProviderService;
import android.sysprop.DisplayProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArraySet;
import android.util.AttributeSet;
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
import android.view.AccessibilityIterators;
import android.view.ActionMode;
import android.view.ContentInfo;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.SemBlurInfo;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
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
import com.samsung.android.graphics.RenderEffectImageFilter;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.media.SemMediaPostProcessor;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;
import com.samsung.android.wallpaperbackup.GenerateXML;
import com.samsung.android.widget.ISemTouchApi;
import com.samsung.android.widget.SemHoverPopupWindow;
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

/* loaded from: classes4.dex */
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
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE = "creditCardExpirationDate";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DAY = "creditCardExpirationDay";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_MONTH = "creditCardExpirationMonth";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_YEAR = "creditCardExpirationYear";
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
    public static boolean DEBUG_DRAW = false;
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

    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_AUTO = 0;

    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_HIGH = 1048576;

    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_LOW = 524288;
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
    public static final int FRAME_RATE_CATEGORY_REASON_CATEGORY_COUNTS = 218103808;
    public static final int FRAME_RATE_CATEGORY_REASON_CONFLICTED = 167772160;
    public static final int FRAME_RATE_CATEGORY_REASON_IDLE_TIMEOUT = 201326592;
    public static final int FRAME_RATE_CATEGORY_REASON_INTERMITTENT = 33554432;
    public static final int FRAME_RATE_CATEGORY_REASON_INVALID = 83886080;
    public static final int FRAME_RATE_CATEGORY_REASON_LARGE = 50331648;
    private static final int FRAME_RATE_CATEGORY_REASON_MASK = -65536;
    public static final int FRAME_RATE_CATEGORY_REASON_REQUESTED = 67108864;
    public static final int FRAME_RATE_CATEGORY_REASON_SMALL = 16777216;
    public static final int FRAME_RATE_CATEGORY_REASON_TOUCH = 150994944;
    public static final int FRAME_RATE_CATEGORY_REASON_UNKNOWN = 0;
    public static final int FRAME_RATE_CATEGORY_REASON_VELOCITY = 100663296;
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
    private static final int PFLAG4_FORCED_OVERRIDE_FRAME_RATE = 1073741824;
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
    private static final int PFLAG4_SELF_REQUESTED_FRAME_RATE = Integer.MIN_VALUE;
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
    protected static final String SAMSUNG_BASIC_INTERACTION_METADATA_NAME = "SamsungBasicInteraction";
    protected static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP10 = "SEP10";
    protected static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP11 = "SEP11";
    protected static final String SAMSUNG_DISPLAY_CUTOUT_BG_METADATA_NAME = "DisplayCutoutBackground";
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

    @Deprecated
    public static final int STATUS_BAR_HIDDEN = 1;

    @Deprecated
    public static final int STATUS_BAR_VISIBLE = 0;
    public static final int SYSTEM_UI_CLEARABLE_FLAGS = 7;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_FULLSCREEN = 4;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = 2;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_IMMERSIVE = 2048;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 4096;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 1024;
    public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = 512;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE = 256;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR = 16;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_LOW_PROFILE = 1;

    @Deprecated
    public static final int SYSTEM_UI_FLAG_VISIBLE = 0;

    @Deprecated
    public static final int SYSTEM_UI_LAYOUT_FLAGS = 1536;
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
    private static SparseArray<String> mAttributeMap = null;
    private static boolean sAcceptZeroSizeDragShadow = false;
    private static boolean sAlwaysAssignFocus = false;
    private static boolean sAutoFocusableOffUIThreadWontNotifyParents = false;
    static boolean sBrokenInsetsDispatch = false;
    protected static boolean sBrokenWindowBackground = false;
    private static boolean sCalculateBoundsInParentFromBoundsInScreenFlagValue = false;
    private static boolean sCanFocusZeroSized = false;
    static boolean sCascadedDragDrop = false;
    private static Paint sDebugPaint = null;
    public static boolean sDebugViewAttributes = false;
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
    private Rect mBlurBitmapBounds;
    private SemBlurInfo.ColorCurve mBlurColorCurve;
    private SemGfxImageFilter mBlurFilter;
    private SemBlurInfo mBlurInfo;

    @ViewDebug.ExportedProperty
    private int mBlurMode;
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
    private RenderNode mCanvasBlurRenderNode;
    private Bitmap mCapturedBitmap;
    private boolean mCapturingCanvas;

    @ViewDebug.ExportedProperty(category = "drawing")
    Rect mClipBounds;
    private int mClipRectBottom;
    private int mClipRectLeft;
    private int mClipRectRight;
    private int mClipRectTop;
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
    private RenderEffectImageFilter mImageFilter;
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

    @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "FORCE_LAYOUT"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LAYOUT_REQUIRED"), @ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "DRAWING_CACHE_INVALID", outputIf = false), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "DRAWN", outputIf = true), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "NOT_DRAWN", outputIf = false), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "DIRTY")}, formatToHexString = true)
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

    @ViewDebug.ExportedProperty(category = AppJankStats.WIDGET_STATE_SCROLLING)
    protected int mScrollX;

    @ViewDebug.ExportedProperty(category = AppJankStats.WIDGET_STATE_SCROLLING)
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
    private CharSequence mSupplementalDescription;

    @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "LOW_PROFILE"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "HIDE_NAVIGATION"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "FULLSCREEN"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "LAYOUT_STABLE"), @ViewDebug.FlagToString(equals = 512, mask = 512, name = "LAYOUT_HIDE_NAVIGATION"), @ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "LAYOUT_FULLSCREEN"), @ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "IMMERSIVE"), @ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "IMMERSIVE_STICKY"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LIGHT_STATUS_BAR"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "LIGHT_NAVIGATION_BAR"), @ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "STATUS_BAR_DISABLE_EXPAND"), @ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "STATUS_BAR_DISABLE_NOTIFICATION_ICONS"), @ViewDebug.FlagToString(equals = 262144, mask = 262144, name = "STATUS_BAR_DISABLE_NOTIFICATION_ALERTS"), @ViewDebug.FlagToString(equals = 524288, mask = 524288, name = "STATUS_BAR_DISABLE_NOTIFICATION_TICKER"), @ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "STATUS_BAR_DISABLE_SYSTEM_INFO"), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "STATUS_BAR_DISABLE_HOME"), @ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "STATUS_BAR_DISABLE_BACK"), @ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "STATUS_BAR_DISABLE_CLOCK"), @ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "STATUS_BAR_DISABLE_RECENT"), @ViewDebug.FlagToString(equals = 33554432, mask = 33554432, name = "STATUS_BAR_DISABLE_SEARCH"), @ViewDebug.FlagToString(equals = 67108864, mask = 67108864, name = "STATUS_BAR_DISABLE_ONGOING_CALL_CHIP")}, formatToHexString = true)
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
    private ViewCredentialHandler mViewCredentialHandler;

    @ViewDebug.ExportedProperty(formatToHexString = true)
    int mViewFlags;
    private ViewTranslationCallback mViewTranslationCallback;
    private ViewTranslationResponse mViewTranslationResponse;
    private Handler mVisibilityChangeForAutofillHandler;
    int mWindowAttachCount;
    private static final int[] AUTOFILL_HIGHLIGHT_ATTR = {16844136};
    private static boolean sCompatibilityDone = false;
    private static boolean sAlwaysRemeasureExactly = false;
    static boolean sTextureViewIgnoresDrawableSetters = false;
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static final int[] DRAWING_CACHE_QUALITY_FLAGS = {0, 524288, 1048576};
    private static final boolean sToolkitFrameRateDefaultNormalReadOnlyFlagValue = Flags.toolkitFrameRateDefaultNormalReadOnly();
    private static final boolean sToolkitFrameRateBySizeReadOnlyFlagValue = Flags.toolkitFrameRateBySizeReadOnly();
    private static final boolean sToolkitFrameRateSmallUsesPercentReadOnlyFlagValue = Flags.toolkitFrameRateSmallUsesPercentReadOnly();
    private static final boolean sToolkitFrameRateViewEnablingReadOnlyFlagValue = Flags.toolkitFrameRateViewEnablingReadOnly();
    private static boolean sToolkitFrameRateVelocityMappingReadOnlyFlagValue = Flags.toolkitFrameRateVelocityMappingReadOnly();
    private static boolean sToolkitFrameRateAnimationBugfix25q1FlagValue = Flags.toolkitFrameRateAnimationBugfix25q1();
    private static boolean sToolkitViewGroupFrameRateApiFlagValue = Flags.toolkitViewgroupSetRequestedFrameRateApi();
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
    protected static final int[] ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(15);
    protected static final int[] PRESSED_STATE_SET = StateSet.get(16);
    protected static final int[] PRESSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(17);
    protected static final int[] PRESSED_SELECTED_STATE_SET = StateSet.get(18);
    protected static final int[] PRESSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(19);
    protected static final int[] PRESSED_FOCUSED_STATE_SET = StateSet.get(20);
    protected static final int[] PRESSED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(21);
    protected static final int[] PRESSED_FOCUSED_SELECTED_STATE_SET = StateSet.get(22);
    protected static final int[] PRESSED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(23);
    protected static final int[] PRESSED_ENABLED_STATE_SET = StateSet.get(24);
    protected static final int[] PRESSED_ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(25);
    protected static final int[] PRESSED_ENABLED_SELECTED_STATE_SET = StateSet.get(26);
    protected static final int[] PRESSED_ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(27);
    protected static final int[] PRESSED_ENABLED_FOCUSED_STATE_SET = StateSet.get(28);
    protected static final int[] PRESSED_ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(29);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(30);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(31);
    protected static boolean sToolkitSetFrameRateReadOnlyFlagValue = Flags.toolkitSetFrameRateReadOnly();
    private static boolean sToolkitMetricsForFrameRateDecisionFlagValue = Flags.toolkitMetricsForFrameRateDecision();
    private static boolean sUseMeasureCacheDuringForceLayoutFlagValue = Flags.enableUseMeasureCacheDuringForceLayout();
    static final int DEBUG_CORNERS_COLOR = Color.rgb(63, 127, 255);
    static final ThreadLocal<Rect> sThreadLocal = ThreadLocal.withInitial(new Supplier() { // from class: android.view.View$$ExternalSyntheticLambda3
        @Override // java.util.function.Supplier
        public final Object get() {
            return new Rect();
        }
    });
    private static final int[] LAYOUT_DIRECTION_FLAGS = {0, 1, 2, 3};
    private static final int[] PFLAG2_TEXT_DIRECTION_FLAGS = {0, 64, 128, 192, 256, 320, 384, 448};
    private static final int[] PFLAG2_TEXT_ALIGNMENT_FLAGS = {0, 8192, 16384, 24576, 32768, UsbManager.USB_DATA_TRANSFER_RATE_40G, AudioChannelLayout.VOICE_CALL_MONO};
    private static final boolean DEBUG_ROUNDED_CORNER = SystemProperties.getBoolean("view.debug.rounded_corner", false);
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static boolean sIsSamsungBasicInteraction = false;
    public static int sSEP_Version = 0;
    public static boolean sIsDisplayCutoutBackground = false;
    private static boolean sMetaDataNeedCheck = true;
    public static final Property<View, Float> ALPHA = new FloatProperty<View>("alpha") { // from class: android.view.View.3
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            if (view != null) {
                view.setAlpha(f);
            }
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getAlpha());
        }
    };
    public static final Property<View, Float> TRANSLATION_X = new FloatProperty<View>("translationX") { // from class: android.view.View.4
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setTranslationX(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getTranslationX());
        }
    };
    public static final Property<View, Float> TRANSLATION_Y = new FloatProperty<View>("translationY") { // from class: android.view.View.5
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setTranslationY(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getTranslationY());
        }
    };
    public static final Property<View, Float> TRANSLATION_Z = new FloatProperty<View>("translationZ") { // from class: android.view.View.6
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setTranslationZ(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getTranslationZ());
        }
    };
    public static final Property<View, Float> X = new FloatProperty<View>("x") { // from class: android.view.View.7
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setX(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getX());
        }
    };
    public static final Property<View, Float> Y = new FloatProperty<View>("y") { // from class: android.view.View.8
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setY(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getY());
        }
    };
    public static final Property<View, Float> Z = new FloatProperty<View>("z") { // from class: android.view.View.9
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setZ(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getZ());
        }
    };
    public static final Property<View, Float> ROTATION = new FloatProperty<View>(GenerateXML.ROTATION) { // from class: android.view.View.10
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setRotation(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getRotation());
        }
    };
    public static final Property<View, Float> ROTATION_X = new FloatProperty<View>("rotationX") { // from class: android.view.View.11
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setRotationX(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getRotationX());
        }
    };
    public static final Property<View, Float> ROTATION_Y = new FloatProperty<View>("rotationY") { // from class: android.view.View.12
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setRotationY(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getRotationY());
        }
    };
    public static final Property<View, Float> SCALE_X = new FloatProperty<View>("scaleX") { // from class: android.view.View.13
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setScaleX(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getScaleX());
        }
    };
    public static final Property<View, Float> SCALE_Y = new FloatProperty<View>("scaleY") { // from class: android.view.View.14
        @Override // android.util.FloatProperty
        public void setValue(View view, float f) {
            view.setScaleY(f);
        }

        @Override // android.util.Property
        public Float get(View view) {
            return Float.valueOf(view.getScaleY());
        }
    };
    private static int sHoverUIEnableFlag = 0;
    protected static int sHoverUIFeatureLevel = -1;
    protected static boolean sIsCheckedHoverUIFeatureLevel = false;
    static final int TEST_BLUR_DISABLED = SystemProperties.getInt("test.debug.blur_disabled", 0);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessibilityDataSensitive {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillImportance {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentCaptureImportance {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentSensitivity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawingCacheQuality {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FindViewFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusRealDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Focusable {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusableMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayerType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutDir {
    }

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
        void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo);
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
        void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);
    }

    public interface OnLongClickListener {
        boolean onLongClick(View view);

        default boolean onLongClickUseDefaultHapticFeedback(View view) {
            return true;
        }
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
    public @interface ResolvedLayoutDir {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollBarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollCaptureHint {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollIndicators {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemUiVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextAlignment {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewStructureType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public static int combineMeasuredStates(int i, int i2) {
        return i | i2;
    }

    private boolean initialAwakenScrollBars() {
        return false;
    }

    private static boolean isViewIdGenerated(int i) {
        return ((-16777216) & i) == 0 && (i & 16777215) != 0;
    }

    public void addChildrenForAccessibility(ArrayList<View> arrayList) {
    }

    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo, String str, Bundle bundle) {
    }

    public void autofill(AutofillValue autofillValue) {
    }

    public boolean checkInputConnectionProxy(View view) {
        return false;
    }

    public void computeScroll() {
    }

    protected void dispatchDraw(Canvas canvas) {
    }

    public void dispatchDrawableHotspotChanged(float f, float f2) {
    }

    protected boolean dispatchGenericFocusedEvent(MotionEvent motionEvent) {
        return false;
    }

    protected boolean dispatchGenericPointerEvent(MotionEvent motionEvent) {
        return false;
    }

    protected void dispatchGetDisplayList() {
    }

    protected void dispatchSetActivated(boolean z) {
    }

    protected void dispatchSetPressed(boolean z) {
    }

    protected void dispatchSetSelected(boolean z) {
    }

    public boolean dispatchUnhandledMove(View view, int i) {
        return false;
    }

    protected boolean findSetFingerHovedInAppWidget(View view) {
        return true;
    }

    public int getAutofillType() {
        return 0;
    }

    public AutofillValue getAutofillValue() {
        return null;
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public int getBaseline() {
        return -1;
    }

    protected int getBottomPaddingOffset() {
        return 0;
    }

    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return null;
    }

    public int getLastBackgroundResource() {
        return -1;
    }

    protected int getLeftPaddingOffset() {
        return 0;
    }

    protected int getRightPaddingOffset() {
        return 0;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public int getSolidColor() {
        return 0;
    }

    protected int getTopPaddingOffset() {
        return 0;
    }

    protected boolean hasHoveredChild() {
        return false;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean hasOverlappingRendering() {
        return true;
    }

    void invalidateInheritedLayoutMode(int i) {
    }

    public boolean isAccessibilitySelectionExtendable() {
        return false;
    }

    public boolean isInEditMode() {
        return false;
    }

    protected boolean isPaddingOffsetRequired() {
        return false;
    }

    protected boolean isVerticalScrollBarHidden() {
        return false;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public boolean onCapturedPointerEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onCheckIsTextEditor() {
        return false;
    }

    public void onCloseSystemDialogs(String str) {
    }

    protected void onConfigurationChanged(Configuration configuration) {
    }

    protected void onCreateContextMenu(ContextMenu contextMenu) {
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return null;
    }

    public void onCreateViewTranslationRequest(int[] iArr, Consumer<ViewTranslationRequest> consumer) {
    }

    public void onCreateVirtualViewTranslationRequests(long[] jArr, int[] iArr, Consumer<ViewTranslationRequest> consumer) {
    }

    protected void onDetachedFromWindow() {
    }

    protected void onDisplayHint(int i) {
    }

    protected void onDraw(Canvas canvas) {
    }

    protected void onFinishInflate() {
    }

    public void onFinishTemporaryDetach() {
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onHoverChanged(boolean z) {
    }

    public void onInputConnectionClosedInternal() {
    }

    public void onInputConnectionOpenedInternal(InputConnection inputConnection, EditorInfo editorInfo, Handler handler) {
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyTextMultiSelection(int i, KeyEvent keyEvent) {
        return false;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    public void onMovedToDisplay(int i, Configuration configuration) {
    }

    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
    }

    public void onPointerCaptureChange(boolean z) {
    }

    public ContentInfo onReceiveContent(ContentInfo contentInfo) {
        return contentInfo;
    }

    public void onResolveDrawables(int i) {
    }

    public void onRtlPropertiesChanged(int i) {
    }

    public void onScreenStateChanged(int i) {
    }

    protected boolean onSetAlpha(int i) {
        return false;
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
    }

    public void onSystemBarAppearanceChanged(int i) {
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onVirtualViewTranslationResponses(LongSparseArray<ViewTranslationResponse> longSparseArray) {
    }

    protected void onVisibilityChanged(View view, int i) {
    }

    @Deprecated
    public void onWindowSystemUiVisibilityChanged(int i) {
    }

    protected boolean pointInHoveredChild(MotionEvent motionEvent) {
        return false;
    }

    public void prepareForExtendedAccessibilitySelection() {
    }

    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int i) {
    }

    public boolean semIsDesktopMode() {
        return false;
    }

    protected boolean semIsHorizontalScrollBarHidden() {
        return false;
    }

    protected boolean setOverrideRoundedCornerBounds(Rect rect) {
        return false;
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<View> {
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
        private int mSupplementalDescriptionId;
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
            this.mAccessibilityHeadingId = propertyMapper.mapBoolean("accessibilityHeading", 16844160);
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(0, "none");
            sparseArray.put(1, "polite");
            sparseArray.put(2, "assertive");
            this.mAccessibilityLiveRegionId = propertyMapper.mapIntEnum("accessibilityLiveRegion", 16843758, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mAccessibilityPaneTitleId = propertyMapper.mapObject("accessibilityPaneTitle", 16844156);
            this.mAccessibilityTraversalAfterId = propertyMapper.mapResourceId("accessibilityTraversalAfter", 16843986);
            this.mAccessibilityTraversalBeforeId = propertyMapper.mapResourceId("accessibilityTraversalBefore", 16843985);
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
            this.mDefaultFocusHighlightEnabledId = propertyMapper.mapBoolean("defaultFocusHighlightEnabled", 16844130);
            SparseArray sparseArray2 = new SparseArray();
            sparseArray2.put(0, "auto");
            sparseArray2.put(524288, "low");
            sparseArray2.put(1048576, "high");
            this.mDrawingCacheQualityId = propertyMapper.mapIntEnum("drawingCacheQuality", 16842984, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
            this.mDuplicateParentStateId = propertyMapper.mapBoolean("duplicateParentState", 16842985);
            this.mElevationId = propertyMapper.mapFloat(SemMediaPostProcessor.ProcessingFormat.Key.ELEVATION, 16843840);
            this.mEnabledId = propertyMapper.mapBoolean("enabled", 16842766);
            this.mFadingEdgeLengthId = propertyMapper.mapInt("fadingEdgeLength", 16842976);
            this.mFilterTouchesWhenObscuredId = propertyMapper.mapBoolean("filterTouchesWhenObscured", 16843460);
            this.mFitsSystemWindowsId = propertyMapper.mapBoolean("fitsSystemWindows", 16842973);
            SparseArray sparseArray3 = new SparseArray();
            sparseArray3.put(0, "false");
            sparseArray3.put(1, "true");
            sparseArray3.put(16, "auto");
            this.mFocusableId = propertyMapper.mapIntEnum("focusable", 16842970, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray3));
            this.mFocusableInTouchModeId = propertyMapper.mapBoolean("focusableInTouchMode", 16842971);
            this.mFocusedId = propertyMapper.mapBoolean("focused", 0);
            this.mFocusedByDefaultId = propertyMapper.mapBoolean("focusedByDefault", 16844100);
            this.mForceDarkAllowedId = propertyMapper.mapBoolean("forceDarkAllowed", 16844172);
            this.mForegroundId = propertyMapper.mapObject("foreground", 16843017);
            this.mForegroundGravityId = propertyMapper.mapGravity("foregroundGravity", 16843264);
            this.mForegroundTintId = propertyMapper.mapObject("foregroundTint", 16843885);
            this.mForegroundTintModeId = propertyMapper.mapObject("foregroundTintMode", 16843886);
            this.mHapticFeedbackEnabledId = propertyMapper.mapBoolean("hapticFeedbackEnabled", 16843358);
            this.mIdId = propertyMapper.mapResourceId("id", 16842960);
            SparseArray sparseArray4 = new SparseArray();
            sparseArray4.put(0, "auto");
            sparseArray4.put(1, "yes");
            sparseArray4.put(2, "no");
            sparseArray4.put(4, "noHideDescendants");
            this.mImportantForAccessibilityId = propertyMapper.mapIntEnum("importantForAccessibility", 16843690, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray4));
            SparseArray sparseArray5 = new SparseArray();
            sparseArray5.put(0, "auto");
            sparseArray5.put(1, "yes");
            sparseArray5.put(2, "no");
            sparseArray5.put(4, "yesExcludeDescendants");
            sparseArray5.put(8, "noExcludeDescendants");
            this.mImportantForAutofillId = propertyMapper.mapIntEnum("importantForAutofill", 16844120, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray5));
            SparseArray sparseArray6 = new SparseArray();
            sparseArray6.put(0, "auto");
            sparseArray6.put(1, "yes");
            sparseArray6.put(2, "no");
            sparseArray6.put(4, "yesExcludeDescendants");
            sparseArray6.put(8, "noExcludeDescendants");
            this.mImportantForContentCaptureId = propertyMapper.mapIntEnum("importantForContentCapture", 16844295, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray6));
            this.mIsScrollContainerId = propertyMapper.mapBoolean("isScrollContainer", 16843342);
            this.mKeepScreenOnId = propertyMapper.mapBoolean("keepScreenOn", 16843286);
            this.mKeyboardNavigationClusterId = propertyMapper.mapBoolean("keyboardNavigationCluster", 16844096);
            this.mLabelForId = propertyMapper.mapResourceId("labelFor", 16843718);
            SparseArray sparseArray7 = new SparseArray();
            sparseArray7.put(0, "none");
            sparseArray7.put(1, "software");
            sparseArray7.put(2, "hardware");
            this.mLayerTypeId = propertyMapper.mapIntEnum("layerType", 16843604, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray7));
            SparseArray sparseArray8 = new SparseArray();
            sparseArray8.put(0, "ltr");
            sparseArray8.put(1, "rtl");
            this.mLayoutDirectionId = propertyMapper.mapIntEnum("layoutDirection", 16843698, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray8));
            this.mLongClickableId = propertyMapper.mapBoolean("longClickable", 16842982);
            this.mMinHeightId = propertyMapper.mapInt("minHeight", 16843072);
            this.mMinWidthId = propertyMapper.mapInt("minWidth", 16843071);
            this.mNestedScrollingEnabledId = propertyMapper.mapBoolean("nestedScrollingEnabled", 16843830);
            this.mNextClusterForwardId = propertyMapper.mapResourceId("nextClusterForward", 16844098);
            this.mNextFocusDownId = propertyMapper.mapResourceId("nextFocusDown", 16842980);
            this.mNextFocusForwardId = propertyMapper.mapResourceId("nextFocusForward", 16843580);
            this.mNextFocusLeftId = propertyMapper.mapResourceId("nextFocusLeft", 16842977);
            this.mNextFocusRightId = propertyMapper.mapResourceId("nextFocusRight", 16842978);
            this.mNextFocusUpId = propertyMapper.mapResourceId("nextFocusUp", 16842979);
            this.mOutlineAmbientShadowColorId = propertyMapper.mapColor("outlineAmbientShadowColor", 16844162);
            this.mOutlineProviderId = propertyMapper.mapObject("outlineProvider", 16843960);
            this.mOutlineSpotShadowColorId = propertyMapper.mapColor("outlineSpotShadowColor", 16844161);
            SparseArray sparseArray9 = new SparseArray();
            sparseArray9.put(0, "always");
            sparseArray9.put(1, "ifContentScrolls");
            sparseArray9.put(2, "never");
            this.mOverScrollModeId = propertyMapper.mapIntEnum("overScrollMode", 16843457, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray9));
            this.mPaddingBottomId = propertyMapper.mapInt("paddingBottom", 16842969);
            this.mPaddingLeftId = propertyMapper.mapInt("paddingLeft", 16842966);
            this.mPaddingRightId = propertyMapper.mapInt("paddingRight", 16842968);
            this.mPaddingTopId = propertyMapper.mapInt("paddingTop", 16842967);
            this.mPointerIconId = propertyMapper.mapObject("pointerIcon", 16844041);
            this.mPressedId = propertyMapper.mapBoolean("pressed", 0);
            SparseArray sparseArray10 = new SparseArray();
            sparseArray10.put(0, "ltr");
            sparseArray10.put(1, "rtl");
            sparseArray10.put(2, "inherit");
            sparseArray10.put(3, "locale");
            this.mRawLayoutDirectionId = propertyMapper.mapIntEnum("rawLayoutDirection", 0, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray10));
            SparseArray sparseArray11 = new SparseArray();
            sparseArray11.put(0, "inherit");
            sparseArray11.put(1, "gravity");
            sparseArray11.put(2, "textStart");
            sparseArray11.put(3, "textEnd");
            sparseArray11.put(4, "center");
            sparseArray11.put(5, "viewStart");
            sparseArray11.put(6, "viewEnd");
            this.mRawTextAlignmentId = propertyMapper.mapIntEnum("rawTextAlignment", 0, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray11));
            SparseArray sparseArray12 = new SparseArray();
            sparseArray12.put(0, "inherit");
            sparseArray12.put(1, "firstStrong");
            sparseArray12.put(2, "anyRtl");
            sparseArray12.put(3, "ltr");
            sparseArray12.put(4, "rtl");
            sparseArray12.put(5, "locale");
            sparseArray12.put(6, "firstStrongLtr");
            sparseArray12.put(7, "firstStrongRtl");
            this.mRawTextDirectionId = propertyMapper.mapIntEnum("rawTextDirection", 0, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray12));
            IntFlagMapping intFlagMapping = new IntFlagMapping();
            intFlagMapping.add(4096, 4096, Slice.HINT_HORIZONTAL);
            intFlagMapping.add(12288, 0, "none");
            intFlagMapping.add(8192, 8192, "vertical");
            this.mRequiresFadingEdgeId = propertyMapper.mapIntFlag("requiresFadingEdge", 16843685, new View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping));
            this.mRotationId = propertyMapper.mapFloat(GenerateXML.ROTATION, 16843558);
            this.mRotationXId = propertyMapper.mapFloat("rotationX", 16843559);
            this.mRotationYId = propertyMapper.mapFloat("rotationY", 16843560);
            this.mSaveEnabledId = propertyMapper.mapBoolean("saveEnabled", 16842983);
            this.mScaleXId = propertyMapper.mapFloat("scaleX", 16843556);
            this.mScaleYId = propertyMapper.mapFloat("scaleY", 16843557);
            this.mScreenReaderFocusableId = propertyMapper.mapBoolean("screenReaderFocusable", 16844148);
            IntFlagMapping intFlagMapping2 = new IntFlagMapping();
            intFlagMapping2.add(2, 2, GenerateXML.BOTTOM);
            intFlagMapping2.add(32, 32, "end");
            intFlagMapping2.add(4, 4, "left");
            intFlagMapping2.add(-1, 0, "none");
            intFlagMapping2.add(8, 8, "right");
            intFlagMapping2.add(16, 16, "start");
            intFlagMapping2.add(1, 1, GenerateXML.TOP);
            this.mScrollIndicatorsId = propertyMapper.mapIntFlag("scrollIndicators", 16844006, new View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping2));
            this.mScrollXId = propertyMapper.mapInt("scrollX", 16842962);
            this.mScrollYId = propertyMapper.mapInt("scrollY", 16842963);
            this.mScrollbarDefaultDelayBeforeFadeId = propertyMapper.mapInt("scrollbarDefaultDelayBeforeFade", 16843433);
            this.mScrollbarFadeDurationId = propertyMapper.mapInt("scrollbarFadeDuration", 16843432);
            this.mScrollbarSizeId = propertyMapper.mapInt("scrollbarSize", 16842851);
            SparseArray sparseArray13 = new SparseArray();
            sparseArray13.put(0, "insideOverlay");
            sparseArray13.put(16777216, "insideInset");
            sparseArray13.put(33554432, "outsideOverlay");
            sparseArray13.put(50331648, "outsideInset");
            this.mScrollbarStyleId = propertyMapper.mapIntEnum("scrollbarStyle", 16842879, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray13));
            this.mSelectedId = propertyMapper.mapBoolean(Slice.HINT_SELECTED, 0);
            this.mSolidColorId = propertyMapper.mapColor("solidColor", 16843594);
            this.mSoundEffectsEnabledId = propertyMapper.mapBoolean("soundEffectsEnabled", 16843285);
            this.mStateListAnimatorId = propertyMapper.mapObject("stateListAnimator", 16843848);
            this.mSupplementalDescriptionId = propertyMapper.mapObject("supplementalDescription", 16844456);
            this.mTagId = propertyMapper.mapObject("tag", 16842961);
            SparseArray sparseArray14 = new SparseArray();
            sparseArray14.put(1, "gravity");
            sparseArray14.put(2, "textStart");
            sparseArray14.put(3, "textEnd");
            sparseArray14.put(4, "center");
            sparseArray14.put(5, "viewStart");
            sparseArray14.put(6, "viewEnd");
            this.mTextAlignmentId = propertyMapper.mapIntEnum("textAlignment", 16843697, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray14));
            SparseArray sparseArray15 = new SparseArray();
            sparseArray15.put(1, "firstStrong");
            sparseArray15.put(2, "anyRtl");
            sparseArray15.put(3, "ltr");
            sparseArray15.put(4, "rtl");
            sparseArray15.put(5, "locale");
            sparseArray15.put(6, "firstStrongLtr");
            sparseArray15.put(7, "firstStrongRtl");
            this.mTextDirectionId = propertyMapper.mapIntEnum("textDirection", 0, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray15));
            this.mTooltipTextId = propertyMapper.mapObject("tooltipText", 16844084);
            this.mTransformPivotXId = propertyMapper.mapFloat("transformPivotX", 16843552);
            this.mTransformPivotYId = propertyMapper.mapFloat("transformPivotY", 16843553);
            this.mTransitionNameId = propertyMapper.mapObject("transitionName", 16843776);
            this.mTranslationXId = propertyMapper.mapFloat("translationX", 16843554);
            this.mTranslationYId = propertyMapper.mapFloat("translationY", 16843555);
            this.mTranslationZId = propertyMapper.mapFloat("translationZ", 16843770);
            SparseArray sparseArray16 = new SparseArray();
            sparseArray16.put(0, CalendarContract.CalendarColumns.VISIBLE);
            sparseArray16.put(4, "invisible");
            sparseArray16.put(8, "gone");
            this.mVisibilityId = propertyMapper.mapIntEnum("visibility", 16842972, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray16));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(View view, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAccessibilityFocusedId, view.isAccessibilityFocused());
            propertyReader.readBoolean(this.mAccessibilityHeadingId, view.isAccessibilityHeading());
            propertyReader.readIntEnum(this.mAccessibilityLiveRegionId, view.getAccessibilityLiveRegion());
            propertyReader.readObject(this.mAccessibilityPaneTitleId, view.getAccessibilityPaneTitle());
            propertyReader.readResourceId(this.mAccessibilityTraversalAfterId, view.getAccessibilityTraversalAfter());
            propertyReader.readResourceId(this.mAccessibilityTraversalBeforeId, view.getAccessibilityTraversalBefore());
            propertyReader.readBoolean(this.mActivatedId, view.isActivated());
            propertyReader.readFloat(this.mAlphaId, view.getAlpha());
            propertyReader.readObject(this.mAutofillHintsId, view.getAutofillHints());
            propertyReader.readObject(this.mBackgroundId, view.getBackground());
            propertyReader.readObject(this.mBackgroundTintId, view.getBackgroundTintList());
            propertyReader.readObject(this.mBackgroundTintModeId, view.getBackgroundTintMode());
            propertyReader.readInt(this.mBaselineId, view.getBaseline());
            propertyReader.readBoolean(this.mClickableId, view.isClickable());
            propertyReader.readObject(this.mContentDescriptionId, view.getContentDescription());
            propertyReader.readBoolean(this.mContextClickableId, view.isContextClickable());
            propertyReader.readBoolean(this.mDefaultFocusHighlightEnabledId, view.getDefaultFocusHighlightEnabled());
            propertyReader.readIntEnum(this.mDrawingCacheQualityId, view.getDrawingCacheQuality());
            propertyReader.readBoolean(this.mDuplicateParentStateId, view.isDuplicateParentStateEnabled());
            propertyReader.readFloat(this.mElevationId, view.getElevation());
            propertyReader.readBoolean(this.mEnabledId, view.isEnabled());
            propertyReader.readInt(this.mFadingEdgeLengthId, view.getFadingEdgeLength());
            propertyReader.readBoolean(this.mFilterTouchesWhenObscuredId, view.getFilterTouchesWhenObscured());
            propertyReader.readBoolean(this.mFitsSystemWindowsId, view.getFitsSystemWindows());
            propertyReader.readIntEnum(this.mFocusableId, view.getFocusable());
            propertyReader.readBoolean(this.mFocusableInTouchModeId, view.isFocusableInTouchMode());
            propertyReader.readBoolean(this.mFocusedId, view.isFocused());
            propertyReader.readBoolean(this.mFocusedByDefaultId, view.isFocusedByDefault());
            propertyReader.readBoolean(this.mForceDarkAllowedId, view.isForceDarkAllowed());
            propertyReader.readObject(this.mForegroundId, view.getForeground());
            propertyReader.readGravity(this.mForegroundGravityId, view.getForegroundGravity());
            propertyReader.readObject(this.mForegroundTintId, view.getForegroundTintList());
            propertyReader.readObject(this.mForegroundTintModeId, view.getForegroundTintMode());
            propertyReader.readBoolean(this.mHapticFeedbackEnabledId, view.isHapticFeedbackEnabled());
            propertyReader.readResourceId(this.mIdId, view.getId());
            propertyReader.readIntEnum(this.mImportantForAccessibilityId, view.getImportantForAccessibility());
            propertyReader.readIntEnum(this.mImportantForAutofillId, view.getImportantForAutofill());
            propertyReader.readIntEnum(this.mImportantForContentCaptureId, view.getImportantForContentCapture());
            propertyReader.readBoolean(this.mIsScrollContainerId, view.isScrollContainer());
            propertyReader.readBoolean(this.mKeepScreenOnId, view.getKeepScreenOn());
            propertyReader.readBoolean(this.mKeyboardNavigationClusterId, view.isKeyboardNavigationCluster());
            propertyReader.readResourceId(this.mLabelForId, view.getLabelFor());
            propertyReader.readIntEnum(this.mLayerTypeId, view.getLayerType());
            propertyReader.readIntEnum(this.mLayoutDirectionId, view.getLayoutDirection());
            propertyReader.readBoolean(this.mLongClickableId, view.isLongClickable());
            propertyReader.readInt(this.mMinHeightId, view.getMinimumHeight());
            propertyReader.readInt(this.mMinWidthId, view.getMinimumWidth());
            propertyReader.readBoolean(this.mNestedScrollingEnabledId, view.isNestedScrollingEnabled());
            propertyReader.readResourceId(this.mNextClusterForwardId, view.getNextClusterForwardId());
            propertyReader.readResourceId(this.mNextFocusDownId, view.getNextFocusDownId());
            propertyReader.readResourceId(this.mNextFocusForwardId, view.getNextFocusForwardId());
            propertyReader.readResourceId(this.mNextFocusLeftId, view.getNextFocusLeftId());
            propertyReader.readResourceId(this.mNextFocusRightId, view.getNextFocusRightId());
            propertyReader.readResourceId(this.mNextFocusUpId, view.getNextFocusUpId());
            propertyReader.readColor(this.mOutlineAmbientShadowColorId, view.getOutlineAmbientShadowColor());
            propertyReader.readObject(this.mOutlineProviderId, view.getOutlineProvider());
            propertyReader.readColor(this.mOutlineSpotShadowColorId, view.getOutlineSpotShadowColor());
            propertyReader.readIntEnum(this.mOverScrollModeId, view.getOverScrollMode());
            propertyReader.readInt(this.mPaddingBottomId, view.getPaddingBottom());
            propertyReader.readInt(this.mPaddingLeftId, view.getPaddingLeft());
            propertyReader.readInt(this.mPaddingRightId, view.getPaddingRight());
            propertyReader.readInt(this.mPaddingTopId, view.getPaddingTop());
            propertyReader.readObject(this.mPointerIconId, view.getPointerIcon());
            propertyReader.readBoolean(this.mPressedId, view.isPressed());
            propertyReader.readIntEnum(this.mRawLayoutDirectionId, view.getRawLayoutDirection());
            propertyReader.readIntEnum(this.mRawTextAlignmentId, view.getRawTextAlignment());
            propertyReader.readIntEnum(this.mRawTextDirectionId, view.getRawTextDirection());
            propertyReader.readIntFlag(this.mRequiresFadingEdgeId, view.getFadingEdge());
            propertyReader.readFloat(this.mRotationId, view.getRotation());
            propertyReader.readFloat(this.mRotationXId, view.getRotationX());
            propertyReader.readFloat(this.mRotationYId, view.getRotationY());
            propertyReader.readBoolean(this.mSaveEnabledId, view.isSaveEnabled());
            propertyReader.readFloat(this.mScaleXId, view.getScaleX());
            propertyReader.readFloat(this.mScaleYId, view.getScaleY());
            propertyReader.readBoolean(this.mScreenReaderFocusableId, view.isScreenReaderFocusable());
            propertyReader.readIntFlag(this.mScrollIndicatorsId, view.getScrollIndicators());
            propertyReader.readInt(this.mScrollXId, view.getScrollX());
            propertyReader.readInt(this.mScrollYId, view.getScrollY());
            propertyReader.readInt(this.mScrollbarDefaultDelayBeforeFadeId, view.getScrollBarDefaultDelayBeforeFade());
            propertyReader.readInt(this.mScrollbarFadeDurationId, view.getScrollBarFadeDuration());
            propertyReader.readInt(this.mScrollbarSizeId, view.getScrollBarSize());
            propertyReader.readIntEnum(this.mScrollbarStyleId, view.getScrollBarStyle());
            propertyReader.readBoolean(this.mSelectedId, view.isSelected());
            propertyReader.readColor(this.mSolidColorId, view.getSolidColor());
            propertyReader.readBoolean(this.mSoundEffectsEnabledId, view.isSoundEffectsEnabled());
            propertyReader.readObject(this.mStateListAnimatorId, view.getStateListAnimator());
            propertyReader.readObject(this.mSupplementalDescriptionId, view.getSupplementalDescription());
            propertyReader.readObject(this.mTagId, view.getTag());
            propertyReader.readIntEnum(this.mTextAlignmentId, view.getTextAlignment());
            propertyReader.readIntEnum(this.mTextDirectionId, view.getTextDirection());
            propertyReader.readObject(this.mTooltipTextId, view.getTooltipText());
            propertyReader.readFloat(this.mTransformPivotXId, view.getPivotX());
            propertyReader.readFloat(this.mTransformPivotYId, view.getPivotY());
            propertyReader.readObject(this.mTransitionNameId, view.getTransitionName());
            propertyReader.readFloat(this.mTranslationXId, view.getTranslationX());
            propertyReader.readFloat(this.mTranslationYId, view.getTranslationY());
            propertyReader.readFloat(this.mTranslationZId, view.getTranslationZ());
            propertyReader.readIntEnum(this.mVisibilityId, view.getVisibility());
        }
    }

    static {
        sCalculateBoundsInParentFromBoundsInScreenFlagValue = false;
        sCalculateBoundsInParentFromBoundsInScreenFlagValue = Flags.calculateBoundsInParentFromBoundsInScreen();
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

        @ViewDebug.ExportedProperty
        private float mAlpha = 1.0f;
        float mTransitionAlpha = 1.0f;

        TransformationInfo() {
        }
    }

    static class TintInfo {
        BlendMode mBlendMode;
        boolean mHasTintList;
        boolean mHasTintMode;
        ColorStateList mTintList;

        TintInfo() {
        }
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
        private ArrayList<Rect> mSystemGestureExclusionRects = null;
        private ArrayList<Rect> mKeepClearRects = null;
        private ArrayList<Rect> mUnrestrictedKeepClearRects = null;
        private boolean mPreferKeepClear = false;
        private Rect mHandwritingArea = null;

        ListenerInfo() {
        }
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

        /* JADX INFO: Access modifiers changed from: private */
        public boolean updateAnchorPos(MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (Math.abs(x - this.mAnchorX) <= this.mHoverSlop && Math.abs(y - this.mAnchorY) <= this.mHoverSlop) {
                return false;
            }
            this.mAnchorX = x;
            this.mAnchorY = y;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAnchorPos() {
            this.mAnchorX = Integer.MAX_VALUE;
            this.mAnchorY = Integer.MAX_VALUE;
        }
    }

    public View(Context context) {
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
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
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
        this.VELOCITY_FRAMERATE1 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate1", 80);
        this.VELOCITY_FRAMERATE2 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate2", 120);
        this.VELOCITY_FRAMERATE3 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate3", 80);
        this.VELOCITY_FRAMERATE4 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate4", 60);
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
        this.mClipRectLeft = 0;
        this.mClipRectTop = 0;
        this.mClipRectRight = Integer.MAX_VALUE;
        this.mClipRectBottom = Integer.MAX_VALUE;
        this.mBackgroundBlurColor = 0;
        this.mImageFilter = null;
        this.mCanvasBlurRenderNode = null;
        this.mCapturingCanvas = false;
        this.mAppWidgetScrollBarBottomPadding = 0;
        this.mAppWidgetScrollBarTopPadding = 0;
        this.mSmartClipDataTag = null;
        this.mSmartClipDataExtractionListener = null;
        this.mContext = context;
        this.mResources = context != null ? context.getResources() : null;
        this.mViewFlags = 402653200;
        this.mPrivateFlags2 = 140296;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mTouchSlop = scaledTouchSlop;
        this.mExtendedTouchSlop = scaledTouchSlop * 4;
        this.mAmbiguousGestureMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        setOverScrollMode(1);
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mRenderNode = RenderNode.create(getClass().getName(), new ViewAnimationHostBridge(this));
        if (!sCompatibilityDone && context != null) {
            int i = context.getApplicationInfo().targetSdkVersion;
            sAlwaysRemeasureExactly = i <= 23;
            sTextureViewIgnoresDrawableSetters = i <= 23;
            sPreserveMarginParamsInLayoutParamConversion = i >= 24;
            sCascadedDragDrop = i < 24;
            sHasFocusableExcludeAutoFocusable = i < 26;
            sAutoFocusableOffUIThreadWontNotifyParents = i < 26;
            sUseDefaultFocusHighlight = context.getResources().getBoolean(R.bool.config_useDefaultFocusHighlight);
            sThrowOnInvalidFloatProperties = i >= 28;
            sCanFocusZeroSized = i < 28;
            sAlwaysAssignFocus = i < 28;
            sAcceptZeroSizeDragShadow = i < 28;
            sBrokenInsetsDispatch = i < 30;
            sBrokenWindowBackground = i < 29;
            GradientDrawable.sWrapNegativeAngleMeasurements = i >= 29;
            sForceLayoutWhenInsetsChanged = i < 30;
            sCompatibilityDone = true;
        }
        try {
            if (!ViewRune.WIDGET_PEN_SUPPORTED || Settings.System.getInt(context.getContentResolver(), "disable_pen_gesture", 0) != 0) {
                this.mDisablePenGestureforfactorytest = true;
            }
        } catch (Exception e) {
            Log.i(VIEW_LOG_TAG, "Setting disable_pen_gesture is not accessible.", e);
        }
        if (sMetaDataNeedCheck) {
            try {
                try {
                    ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
                    if (applicationInfo.metaData != null) {
                        String string = applicationInfo.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME);
                        if (string != null && string.startsWith("SEP") && string.length() == 5) {
                            sIsSamsungBasicInteraction = true;
                            sSEP_Version = Integer.parseInt(string.substring(3, 5));
                        }
                        sIsDisplayCutoutBackground = applicationInfo.metaData.getBoolean(SAMSUNG_DISPLAY_CUTOUT_BG_METADATA_NAME);
                    }
                } finally {
                    sMetaDataNeedCheck = false;
                }
            } catch (Exception unused) {
                Log.d(VIEW_LOG_TAG, "Unable to get SamsungBasicInteraction metadata");
            }
        }
    }

    public View(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public View(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01d9 A[LOOP:1: B:74:0x01d7->B:75:0x01d9, LOOP_END] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [android.graphics.BlendMode, android.graphics.Paint, android.view.View-IA] */
    /* JADX WARN: Type inference failed for: r8v46 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public View(android.content.Context r46, android.util.AttributeSet r47, int r48, int r49) {
        /*
            Method dump skipped, instructions count: 2148
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int):void");
    }

    public int[] getAttributeResolutionStack(int i) {
        SparseArray<int[]> sparseArray;
        int i2;
        if (!sDebugViewAttributes || (sparseArray = this.mAttributeResolutionStacks) == null || sparseArray.get(i) == null) {
            return new int[0];
        }
        int[] iArr = this.mAttributeResolutionStacks.get(i);
        int length = iArr.length;
        int i3 = this.mSourceLayoutId;
        if (i3 != 0) {
            length++;
        }
        int[] iArr2 = new int[length];
        if (i3 != 0) {
            iArr2[0] = i3;
            i2 = 1;
        } else {
            i2 = 0;
        }
        for (int i4 : iArr) {
            iArr2[i2] = i4;
            i2++;
        }
        return iArr2;
    }

    public Map<Integer, Integer> getAttributeSourceResourceMap() {
        HashMap hashMap = new HashMap();
        if (sDebugViewAttributes && this.mAttributeSourceResId != null) {
            for (int i = 0; i < this.mAttributeSourceResId.size(); i++) {
                hashMap.put(Integer.valueOf(this.mAttributeSourceResId.keyAt(i)), Integer.valueOf(this.mAttributeSourceResId.valueAt(i)));
            }
        }
        return hashMap;
    }

    public int getExplicitStyle() {
        if (sDebugViewAttributes) {
            return this.mExplicitStyle;
        }
        return 0;
    }

    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View view, String str) {
            this.mHostView = view;
            this.mMethodName = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, view);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        private void resolveMethod(Context context, String str) {
            String str2;
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, View.class)) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException unused) {
                }
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
            int id = this.mHostView.getId();
            if (id == -1) {
                str2 = "";
            } else {
                str2 = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            }
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + str2);
        }
    }

    View() {
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
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
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
        this.VELOCITY_FRAMERATE1 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate1", 80);
        this.VELOCITY_FRAMERATE2 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate2", 120);
        this.VELOCITY_FRAMERATE3 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate3", 80);
        this.VELOCITY_FRAMERATE4 = SystemProperties.getInt("sys.toolkit.dvrr.velocity_framerate4", 60);
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
        this.mClipRectLeft = 0;
        this.mClipRectTop = 0;
        this.mClipRectRight = Integer.MAX_VALUE;
        this.mClipRectBottom = Integer.MAX_VALUE;
        this.mBackgroundBlurColor = 0;
        this.mImageFilter = null;
        this.mCanvasBlurRenderNode = null;
        this.mCapturingCanvas = false;
        this.mAppWidgetScrollBarBottomPadding = 0;
        this.mAppWidgetScrollBarTopPadding = 0;
        this.mSmartClipDataTag = null;
        this.mSmartClipDataExtractionListener = null;
        this.mResources = null;
        this.mRenderNode = RenderNode.create(getClass().getName(), new ViewAnimationHostBridge(this));
    }

    public final boolean isShowingLayoutBounds() {
        if (DEBUG_DRAW) {
            return true;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        return attachInfo != null && attachInfo.mDebugLayout;
    }

    public final void setShowingLayoutBounds(boolean z) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mDebugLayout = z;
        }
    }

    private static SparseArray<String> getAttributeMap() {
        if (mAttributeMap == null) {
            mAttributeMap = new SparseArray<>();
        }
        return mAttributeMap;
    }

    private void retrieveExplicitStyle(Resources.Theme theme, AttributeSet attributeSet) {
        if (sDebugViewAttributes) {
            this.mExplicitStyle = theme.getExplicitStyle(attributeSet);
        }
    }

    public final void saveAttributeDataForStyleable(Context context, int[] iArr, AttributeSet attributeSet, TypedArray typedArray, int i, int i2) {
        if (sDebugViewAttributes) {
            int[] attributeResolutionStack = context.getTheme().getAttributeResolutionStack(i, i2, this.mExplicitStyle);
            if (this.mAttributeResolutionStacks == null) {
                this.mAttributeResolutionStacks = new SparseArray<>();
            }
            if (this.mAttributeSourceResId == null) {
                this.mAttributeSourceResId = new SparseIntArray();
            }
            int indexCount = typedArray.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = typedArray.getIndex(i3);
                this.mAttributeSourceResId.append(iArr[index], typedArray.getSourceResourceId(index, 0));
                this.mAttributeResolutionStacks.append(iArr[index], attributeResolutionStack);
            }
        }
    }

    private void saveAttributeData(AttributeSet attributeSet, TypedArray typedArray) {
        int resourceId;
        int attributeCount = attributeSet == null ? 0 : attributeSet.getAttributeCount();
        int indexCount = typedArray.getIndexCount();
        String[] strArr = new String[(attributeCount + indexCount) * 2];
        int i = 0;
        for (int i2 = 0; i2 < attributeCount; i2++) {
            strArr[i] = attributeSet.getAttributeName(i2);
            strArr[i + 1] = attributeSet.getAttributeValue(i2);
            i += 2;
        }
        Resources resources = typedArray.getResources();
        SparseArray<String> attributeMap = getAttributeMap();
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = typedArray.getIndex(i3);
            if (typedArray.hasValueOrEmpty(index) && (resourceId = typedArray.getResourceId(index, 0)) != 0) {
                String str = attributeMap.get(resourceId);
                if (str == null) {
                    try {
                        str = resources.getResourceName(resourceId);
                    } catch (Resources.NotFoundException unused) {
                        str = "0x" + Integer.toHexString(resourceId);
                    }
                    attributeMap.put(resourceId, str);
                }
                strArr[i] = str;
                strArr[i + 1] = typedArray.getString(index);
                i += 2;
            }
        }
        String[] strArr2 = new String[i];
        System.arraycopy(strArr, 0, strArr2, 0, i);
        this.mAttributes = strArr2;
    }

    protected void semEnableHorizontalScrollbar() {
        this.mViewFlags = (this.mViewFlags & (-769)) | 256;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append(getClass().getName());
        sb.append('{');
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(' ');
        int i = this.mViewFlags & 12;
        if (i == 0) {
            sb.append('V');
        } else if (i == 4) {
            sb.append('I');
        } else if (i == 8) {
            sb.append('G');
        } else {
            sb.append('.');
        }
        sb.append((this.mViewFlags & 1) == 1 ? 'F' : '.');
        sb.append((this.mViewFlags & 32) == 0 ? DateFormat.DAY : '.');
        sb.append((this.mViewFlags & 128) == 128 ? '.' : 'D');
        sb.append((256 & this.mViewFlags) != 0 ? 'H' : '.');
        sb.append((this.mViewFlags & 512) == 0 ? '.' : 'V');
        sb.append((this.mViewFlags & 16384) != 0 ? 'C' : '.');
        sb.append((this.mViewFlags & 2097152) != 0 ? DateFormat.STANDALONE_MONTH : '.');
        sb.append((this.mViewFlags & 8388608) != 0 ? 'X' : '.');
        sb.append(' ');
        sb.append((this.mPrivateFlags & 8) != 0 ? 'R' : '.');
        sb.append((this.mPrivateFlags & 2) == 0 ? '.' : 'F');
        sb.append((this.mPrivateFlags & 4) != 0 ? 'S' : '.');
        int i2 = this.mPrivateFlags;
        if ((33554432 & i2) != 0) {
            sb.append('p');
        } else {
            sb.append((i2 & 16384) != 0 ? 'P' : '.');
        }
        sb.append((this.mPrivateFlags & 268435456) == 0 ? '.' : 'H');
        sb.append((this.mPrivateFlags & 1073741824) != 0 ? DateFormat.CAPITAL_AM_PM : '.');
        sb.append((this.mPrivateFlags & Integer.MIN_VALUE) == 0 ? '.' : 'I');
        sb.append((this.mPrivateFlags & 2097152) != 0 ? 'D' : '.');
        sb.append(' ');
        sb.append(this.mLeft);
        sb.append(',');
        sb.append(this.mTop);
        sb.append('-');
        sb.append(this.mRight);
        sb.append(',');
        sb.append(this.mBottom);
        appendId(sb);
        if (this.mAutofillId != null) {
            sb.append(" aid=");
            sb.append(this.mAutofillId);
        }
        sb.append("}");
        return sb.toString();
    }

    void appendId(StringBuilder sb) {
        String str;
        int id = getId();
        if (id != -1) {
            sb.append(" #");
            sb.append(Integer.toHexString(id));
            Resources resources = this.mResources;
            if (id <= 0 || !Resources.resourceHasPackage(id) || resources == null) {
                return;
            }
            int i = (-16777216) & id;
            if (i == 16777216) {
                str = "android";
            } else if (i == 2130706432) {
                str = "app";
            } else {
                try {
                    str = resources.getResourcePackageName(id);
                } catch (Resources.NotFoundException unused) {
                    return;
                }
            }
            String resourceTypeName = resources.getResourceTypeName(id);
            String resourceEntryName = resources.getResourceEntryName(id);
            sb.append(" ");
            sb.append(str);
            sb.append(":");
            sb.append(resourceTypeName);
            sb.append("/");
            sb.append(resourceEntryName);
        }
    }

    protected void initializeFadingEdge(TypedArray typedArray) {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.View);
        initializeFadingEdgeInternal(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    protected void initializeFadingEdgeInternal(TypedArray typedArray) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = typedArray.getDimensionPixelSize(25, ViewConfiguration.get(this.mContext).getScaledFadingEdgeLength());
    }

    public int getVerticalFadingEdgeLength() {
        ScrollabilityCache scrollabilityCache;
        if (!isVerticalFadingEdgeEnabled() || (scrollabilityCache = this.mScrollCache) == null) {
            return 0;
        }
        return scrollabilityCache.fadingEdgeLength;
    }

    public void setFadingEdgeLength(int i) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = i;
    }

    public void clearPendingCredentialRequest() {
        if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            Log.v(AUTOFILL_LOG_TAG, "clearPendingCredentialRequest called");
        }
        this.mViewCredentialHandler = null;
    }

    public void setPendingCredentialRequest(GetCredentialRequest getCredentialRequest, OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
        Preconditions.checkNotNull(getCredentialRequest, "request must not be null");
        Preconditions.checkNotNull(outcomeReceiver, "callback must not be null");
        for (CredentialOption credentialOption : getCredentialRequest.getCredentialOptions()) {
            ArrayList<? extends Parcelable> parcelableArrayList = credentialOption.getCandidateQueryData().getParcelableArrayList(CredentialProviderService.EXTRA_AUTOFILL_ID, AutofillId.class);
            if (parcelableArrayList == null) {
                parcelableArrayList = new ArrayList<>();
            }
            if (!parcelableArrayList.contains(getAutofillId())) {
                parcelableArrayList.add(getAutofillId());
            }
            credentialOption.getCandidateQueryData().putParcelableArrayList(CredentialProviderService.EXTRA_AUTOFILL_ID, parcelableArrayList);
        }
        this.mViewCredentialHandler = new ViewCredentialHandler(getCredentialRequest, outcomeReceiver);
    }

    public ViewCredentialHandler getViewCredentialHandler() {
        return this.mViewCredentialHandler;
    }

    public int getHorizontalFadingEdgeLength() {
        ScrollabilityCache scrollabilityCache;
        if (!isHorizontalFadingEdgeEnabled() || (scrollabilityCache = this.mScrollCache) == null) {
            return 0;
        }
        return scrollabilityCache.fadingEdgeLength;
    }

    public int getVerticalScrollbarWidth() {
        ScrollBarDrawable scrollBarDrawable;
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        int i = 0;
        return (scrollabilityCache == null || (scrollBarDrawable = scrollabilityCache.scrollBar) == null || (i = scrollBarDrawable.getSize(true)) > 0) ? i : scrollabilityCache.scrollBarSize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHorizontalScrollbarHeight() {
        ScrollBarDrawable scrollBarDrawable;
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        int i = 0;
        return (scrollabilityCache == null || (scrollBarDrawable = scrollabilityCache.scrollBar) == null || (i = scrollBarDrawable.getSize(false)) > 0) ? i : scrollabilityCache.scrollBarSize;
    }

    protected void initializeScrollbars(TypedArray typedArray) {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.View);
        initializeScrollbarsInternal(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    private void initializeScrollBarDrawable() {
        initScrollCache();
        if (this.mScrollCache.scrollBar == null) {
            this.mScrollCache.scrollBar = new ScrollBarDrawable();
            this.mScrollCache.scrollBar.setState(getDrawableState());
            this.mScrollCache.scrollBar.setCallback(this);
        }
    }

    protected void initializeScrollbarsInternal(TypedArray typedArray) {
        initScrollCache();
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache.scrollBar == null) {
            scrollabilityCache.scrollBar = new ScrollBarDrawable(this);
            scrollabilityCache.scrollBar.setState(getDrawableState());
            scrollabilityCache.scrollBar.setCallback(this);
        }
        boolean z = typedArray.getBoolean(47, true);
        if (!z) {
            scrollabilityCache.state = 1;
        }
        scrollabilityCache.fadeScrollBars = z;
        scrollabilityCache.scrollBarFadeDuration = typedArray.getInt(45, ViewConfiguration.getScrollBarFadeDuration());
        scrollabilityCache.scrollBarDefaultDelayBeforeFade = typedArray.getInt(46, ViewConfiguration.getScrollDefaultDelay());
        scrollabilityCache.scrollBarSize = typedArray.getDimensionPixelSize(1, ViewConfiguration.get(this.mContext).getScaledScrollBarSize());
        scrollabilityCache.scrollBar.setHorizontalTrackDrawable(typedArray.getDrawable(4));
        Drawable drawable = typedArray.getDrawable(2);
        if (drawable != null) {
            scrollabilityCache.scrollBar.setHorizontalThumbDrawable(drawable);
        }
        if (typedArray.getBoolean(6, false)) {
            scrollabilityCache.scrollBar.setAlwaysDrawHorizontalTrack(true);
        }
        Drawable drawable2 = typedArray.getDrawable(5);
        scrollabilityCache.scrollBar.setVerticalTrackDrawable(drawable2);
        Drawable drawable3 = typedArray.getDrawable(3);
        if (drawable3 != null) {
            scrollabilityCache.scrollBar.setVerticalThumbDrawable(drawable3);
            Context context = this.mContext;
            if (context != null) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.sem_scroller_default_vertical_padding);
                this.mAppWidgetScrollBarBottomPadding = dimensionPixelSize;
                this.mAppWidgetScrollBarTopPadding = dimensionPixelSize;
            }
        }
        if (typedArray.getBoolean(7, false)) {
            scrollabilityCache.scrollBar.setAlwaysDrawVerticalTrack(true);
        }
        int layoutDirection = getLayoutDirection();
        if (drawable2 != null) {
            drawable2.setLayoutDirection(layoutDirection);
        }
        if (drawable3 != null) {
            drawable3.setLayoutDirection(layoutDirection);
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
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null) {
            return scrollabilityCache.scrollBar.getVerticalThumbDrawable();
        }
        return null;
    }

    public Drawable getVerticalScrollbarTrackDrawable() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null) {
            return scrollabilityCache.scrollBar.getVerticalTrackDrawable();
        }
        return null;
    }

    public Drawable getHorizontalScrollbarThumbDrawable() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null) {
            return scrollabilityCache.scrollBar.getHorizontalThumbDrawable();
        }
        return null;
    }

    public Drawable getHorizontalScrollbarTrackDrawable() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null) {
            return scrollabilityCache.scrollBar.getHorizontalTrackDrawable();
        }
        return null;
    }

    private void initializeScrollIndicatorsInternal() {
        if (this.mScrollIndicatorDrawable == null) {
            this.mScrollIndicatorDrawable = this.mContext.getDrawable(R.drawable.scroll_indicator_material);
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

    public void setVerticalScrollbarPosition(int i) {
        if (this.mVerticalScrollbarPosition != i) {
            this.mVerticalScrollbarPosition = i;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public int getVerticalScrollbarPosition() {
        return this.mVerticalScrollbarPosition;
    }

    public void semSetVerticalScrollBarPadding(boolean z) {
        this.mNeededToChangedScrollBarPosition = z;
    }

    public void semSetVerticalScrollBarPaddingPosition(int i) {
        this.mScrollBarPositionPadding = i;
    }

    boolean isOnScrollbar(float f, float f2) {
        if (this.mScrollCache == null) {
            return false;
        }
        float scrollX = f + getScrollX();
        float scrollY = f2 + getScrollY();
        boolean z = computeVerticalScrollRange() > computeVerticalScrollExtent();
        if (isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && z) {
            Rect rect = this.mScrollCache.mScrollBarTouchBounds;
            getVerticalScrollBarBounds(null, rect);
            if (rect.contains((int) scrollX, (int) scrollY)) {
                return true;
            }
        }
        boolean z2 = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        if (isHorizontalScrollBarEnabled() && z2) {
            Rect rect2 = this.mScrollCache.mScrollBarTouchBounds;
            getHorizontalScrollBarBounds(null, rect2);
            if (rect2.contains((int) scrollX, (int) scrollY)) {
                return true;
            }
        }
        return false;
    }

    boolean isOnScrollbarThumb(float f, float f2) {
        return isOnVerticalScrollbarThumb(f, f2) || isOnHorizontalScrollbarThumb(f, f2);
    }

    private boolean isOnVerticalScrollbarThumb(float f, float f2) {
        int computeVerticalScrollRange;
        int computeVerticalScrollExtent;
        if (this.mScrollCache != null && isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && (computeVerticalScrollRange = computeVerticalScrollRange()) > (computeVerticalScrollExtent = computeVerticalScrollExtent())) {
            float scrollX = f + getScrollX();
            float scrollY = f2 + getScrollY();
            Rect rect = this.mScrollCache.mScrollBarBounds;
            getVerticalScrollBarBounds(rect, this.mScrollCache.mScrollBarTouchBounds);
            int computeVerticalScrollOffset = computeVerticalScrollOffset();
            int thumbLength = ScrollBarUtils.getThumbLength(rect.height(), rect.width(), computeVerticalScrollExtent, computeVerticalScrollRange);
            int thumbOffset = rect.top + ScrollBarUtils.getThumbOffset(rect.height(), thumbLength, computeVerticalScrollExtent, computeVerticalScrollRange, computeVerticalScrollOffset);
            int max = Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (scrollX >= r4.left && scrollX <= r4.right && scrollY >= thumbOffset - max && scrollY <= thumbOffset + thumbLength + max) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnHorizontalScrollbarThumb(float f, float f2) {
        int computeHorizontalScrollRange;
        int computeHorizontalScrollExtent;
        if (this.mScrollCache != null && isHorizontalScrollBarEnabled() && (computeHorizontalScrollRange = computeHorizontalScrollRange()) > (computeHorizontalScrollExtent = computeHorizontalScrollExtent())) {
            float scrollX = f + getScrollX();
            float scrollY = f2 + getScrollY();
            Rect rect = this.mScrollCache.mScrollBarBounds;
            getHorizontalScrollBarBounds(rect, this.mScrollCache.mScrollBarTouchBounds);
            int computeHorizontalScrollOffset = computeHorizontalScrollOffset();
            int thumbLength = ScrollBarUtils.getThumbLength(rect.width(), rect.height(), computeHorizontalScrollExtent, computeHorizontalScrollRange);
            int thumbOffset = rect.left + ScrollBarUtils.getThumbOffset(rect.width(), thumbLength, computeHorizontalScrollExtent, computeHorizontalScrollRange, computeHorizontalScrollOffset);
            int max = Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (scrollX >= thumbOffset - max && scrollX <= thumbOffset + thumbLength + max && scrollY >= r4.top && scrollY <= r4.bottom) {
                return true;
            }
        }
        return false;
    }

    boolean isDraggingScrollBar() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        return (scrollabilityCache == null || scrollabilityCache.mScrollBarDraggingState == 0) ? false : true;
    }

    @RemotableViewMethod
    public void setScrollIndicators(int i) {
        setScrollIndicators(i, 63);
    }

    public void setScrollIndicators(int i, int i2) {
        int i3 = (i2 << 8) & SCROLL_INDICATORS_PFLAG3_MASK;
        int i4 = (i << 8) & i3;
        int i5 = this.mPrivateFlags3;
        int i6 = ((~i3) & i5) | i4;
        if (i5 != i6) {
            this.mPrivateFlags3 = i6;
            if (i4 != 0) {
                initializeScrollIndicatorsInternal();
            }
            invalidate();
        }
    }

    public int getScrollIndicators() {
        return (this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) >>> 8;
    }

    public void setSemHorizontalScrollbarPosition(int i) {
        if (this.mHorizontalScrollbarPosition != i) {
            this.mHorizontalScrollbarPosition = i;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public int semGetHorizontalScrollbarPosition() {
        return this.mHorizontalScrollbarPosition;
    }

    protected int semGetScaledMinScrollbarTouchTarget(ViewConfiguration viewConfiguration) {
        if (sIsSamsungBasicInteraction) {
            return 0;
        }
        return viewConfiguration.getScaledMinScrollbarTouchTarget();
    }

    ListenerInfo getListenerInfo() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null) {
            return listenerInfo;
        }
        ListenerInfo listenerInfo2 = new ListenerInfo();
        this.mListenerInfo = listenerInfo2;
        return listenerInfo2;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        getListenerInfo().mOnScrollChangeListener = onScrollChangeListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        getListenerInfo().mOnFocusChangeListener = onFocusChangeListener;
    }

    public void addOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        ListenerInfo listenerInfo = getListenerInfo();
        if (listenerInfo.mOnLayoutChangeListeners == null) {
            listenerInfo.mOnLayoutChangeListeners = new ArrayList();
        }
        if (listenerInfo.mOnLayoutChangeListeners.contains(onLayoutChangeListener)) {
            return;
        }
        listenerInfo.mOnLayoutChangeListeners.add(onLayoutChangeListener);
    }

    public void removeOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnLayoutChangeListeners == null) {
            return;
        }
        listenerInfo.mOnLayoutChangeListeners.remove(onLayoutChangeListener);
    }

    public void addOnAttachStateChangeListener(OnAttachStateChangeListener onAttachStateChangeListener) {
        ListenerInfo listenerInfo = getListenerInfo();
        if (listenerInfo.mOnAttachStateChangeListeners == null) {
            listenerInfo.mOnAttachStateChangeListeners = new CopyOnWriteArrayList();
        }
        listenerInfo.mOnAttachStateChangeListeners.add(onAttachStateChangeListener);
    }

    public void removeOnAttachStateChangeListener(OnAttachStateChangeListener onAttachStateChangeListener) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnAttachStateChangeListeners == null) {
            return;
        }
        listenerInfo.mOnAttachStateChangeListeners.remove(onAttachStateChangeListener);
    }

    public OnFocusChangeListener getOnFocusChangeListener() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null) {
            return listenerInfo.mOnFocusChangeListener;
        }
        return null;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            TypedValue typedValue = new TypedValue();
            Context context = getContext();
            if (context != null && context.getTheme() != null) {
                this.mIsDeviceDefault = context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true) && typedValue.data != 0;
            }
        }
        if (!isClickable()) {
            setClickable(true);
        }
        getListenerInfo().mOnClickListener = onClickListener;
    }

    public boolean hasOnClickListeners() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        return (listenerInfo == null || listenerInfo.mOnClickListener == null) ? false : true;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        getListenerInfo().mOnLongClickListener = onLongClickListener;
    }

    public boolean hasOnLongClickListeners() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        return (listenerInfo == null || listenerInfo.mOnLongClickListener == null) ? false : true;
    }

    public OnLongClickListener getOnLongClickListener() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null) {
            return listenerInfo.mOnLongClickListener;
        }
        return null;
    }

    public void setOnContextClickListener(OnContextClickListener onContextClickListener) {
        if (!isContextClickable()) {
            setContextClickable(true);
        }
        getListenerInfo().mOnContextClickListener = onContextClickListener;
    }

    public void setOnCreateContextMenuListener(OnCreateContextMenuListener onCreateContextMenuListener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        getListenerInfo().mOnCreateContextMenuListener = onCreateContextMenuListener;
    }

    public void addFrameMetricsListener(Window window, Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener, Handler handler) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            if (attachInfo.mThreadedRenderer != null) {
                if (this.mFrameMetricsObservers == null) {
                    this.mFrameMetricsObservers = new ArrayList<>();
                }
                FrameMetricsObserver frameMetricsObserver = new FrameMetricsObserver(window, handler, onFrameMetricsAvailableListener);
                this.mFrameMetricsObservers.add(frameMetricsObserver);
                this.mAttachInfo.mThreadedRenderer.addObserver(frameMetricsObserver.getRendererObserver());
                return;
            }
            Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
            return;
        }
        if (this.mFrameMetricsObservers == null) {
            this.mFrameMetricsObservers = new ArrayList<>();
        }
        this.mFrameMetricsObservers.add(new FrameMetricsObserver(window, handler, onFrameMetricsAvailableListener));
    }

    public boolean isFrameMetricsObservers() {
        return this.mFrameMetricsObservers != null;
    }

    public void removeFrameMetricsListener(Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        ThreadedRenderer threadedRenderer = getThreadedRenderer();
        FrameMetricsObserver findFrameMetricsObserver = findFrameMetricsObserver(onFrameMetricsAvailableListener);
        if (findFrameMetricsObserver == null) {
            throw new IllegalArgumentException("attempt to remove OnFrameMetricsAvailableListener that was never added");
        }
        ArrayList<FrameMetricsObserver> arrayList = this.mFrameMetricsObservers;
        if (arrayList != null) {
            arrayList.remove(findFrameMetricsObserver);
            if (threadedRenderer != null) {
                threadedRenderer.removeObserver(findFrameMetricsObserver.getRendererObserver());
            }
        }
    }

    private void registerPendingFrameMetricsObservers() {
        if (this.mFrameMetricsObservers != null) {
            ThreadedRenderer threadedRenderer = getThreadedRenderer();
            if (threadedRenderer != null) {
                Iterator<FrameMetricsObserver> it = this.mFrameMetricsObservers.iterator();
                while (it.hasNext()) {
                    threadedRenderer.addObserver(it.next().getRendererObserver());
                }
                return;
            }
            Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
        }
    }

    private FrameMetricsObserver findFrameMetricsObserver(Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        if (this.mFrameMetricsObservers == null) {
            return null;
        }
        for (int i = 0; i < this.mFrameMetricsObservers.size(); i++) {
            FrameMetricsObserver frameMetricsObserver = this.mFrameMetricsObservers.get(i);
            if (frameMetricsObserver.mListener == onFrameMetricsAvailableListener) {
                return frameMetricsObserver;
            }
        }
        return null;
    }

    public void setNotifyAutofillManagerOnClick(boolean z) {
        if (z) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performClickInternal() {
        notifyAutofillManagerOnClick();
        return performClick();
    }

    public boolean performClick() {
        notifyAutofillManagerOnClick();
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean z = false;
        if (listenerInfo != null && listenerInfo.mOnClickListener != null) {
            playSoundEffect(0);
            listenerInfo.mOnClickListener.onClick(this);
            z = true;
        }
        sendAccessibilityEvent(1);
        notifyEnterOrExitForAutoFillIfNeeded(true);
        return z;
    }

    public boolean callOnClick() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnClickListener == null) {
            return false;
        }
        listenerInfo.mOnClickListener.onClick(this);
        return true;
    }

    public boolean performLongClick() {
        return performLongClickInternal(this.mLongClickX, this.mLongClickY);
    }

    public boolean performLongClick(float f, float f2) {
        this.mLongClickX = f;
        this.mLongClickY = f2;
        boolean performLongClick = performLongClick();
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        return performLongClick;
    }

    private boolean performLongClickInternal(float f, float f2) {
        boolean z;
        sendAccessibilityEvent(2);
        ListenerInfo listenerInfo = this.mListenerInfo;
        OnLongClickListener onLongClickListener = listenerInfo == null ? null : listenerInfo.mOnLongClickListener;
        boolean z2 = true;
        if (onLongClickListener != null) {
            z = onLongClickListener.onLongClick(this);
            if (z) {
                z2 = onLongClickListener.onLongClickUseDefaultHapticFeedback(this);
            }
        } else {
            z = false;
        }
        if (!z) {
            z = (Float.isNaN(f) || Float.isNaN(f2)) ? showContextMenu() : showContextMenu(f, f2);
        }
        if ((this.mViewFlags & 1073741824) == 1073741824 && !z) {
            z = showLongClickTooltip((int) f, (int) f2);
        }
        if (z && z2) {
            performHapticFeedback(0);
        }
        return z;
    }

    public boolean performContextClick(float f, float f2) {
        return performContextClick();
    }

    public boolean performContextClick() {
        sendAccessibilityEvent(8388608);
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean onContextClick = (listenerInfo == null || listenerInfo.mOnContextClickListener == null) ? false : listenerInfo.mOnContextClickListener.onContextClick(this);
        if (onContextClick) {
            performHapticFeedback(6);
        }
        return onContextClick;
    }

    protected boolean performButtonActionOnTouchDown(MotionEvent motionEvent) {
        if (!motionEvent.isFromSource(8194) || (motionEvent.getButtonState() & 2) == 0) {
            return false;
        }
        showContextMenu(motionEvent.getX(), motionEvent.getY());
        this.mPrivateFlags |= 67108864;
        return true;
    }

    public boolean showContextMenu() {
        return getParent().showContextMenuForChild(this);
    }

    public boolean showContextMenu(float f, float f2) {
        return getParent().showContextMenuForChild(this, f, f2);
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    public ActionMode startActionMode(ActionMode.Callback callback, int i) {
        ViewParent parent = getParent();
        if (parent == null) {
            return null;
        }
        try {
            return parent.startActionModeForChild(this, callback, i);
        } catch (AbstractMethodError unused) {
            return parent.startActionModeForChild(this, callback);
        }
    }

    public void startActivityForResult(Intent intent, int i) {
        this.mStartActivityRequestWho = "@android:view:" + System.identityHashCode(this);
        getContext().startActivityForResult(this.mStartActivityRequestWho, intent, i, null);
    }

    public boolean dispatchActivityResult(String str, int i, int i2, Intent intent) {
        String str2 = this.mStartActivityRequestWho;
        if (str2 == null || !str2.equals(str)) {
            return false;
        }
        onActivityResult(i, i2, intent);
        this.mStartActivityRequestWho = null;
        return true;
    }

    public void setOnKeyListener(OnKeyListener onKeyListener) {
        getListenerInfo().mOnKeyListener = onKeyListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        getListenerInfo().mOnTouchListener = onTouchListener;
    }

    public void setOnGenericMotionListener(OnGenericMotionListener onGenericMotionListener) {
        getListenerInfo().mOnGenericMotionListener = onGenericMotionListener;
    }

    public void setOnHoverListener(OnHoverListener onHoverListener) {
        getListenerInfo().mOnHoverListener = onHoverListener;
    }

    public void setOnDragListener(OnDragListener onDragListener) {
        if (onDragListener != null && getListenerInfo().mOnDragListener == null) {
            if (isAttachedToWindow()) {
                postRequestSendStickyDragStartedEvent();
            } else {
                this.mNeedToSendSavedStickyDragEvent = true;
            }
        }
        getListenerInfo().mOnDragListener = onDragListener;
    }

    void handleFocusGainInternal(int i, Rect rect) {
        int i2 = this.mPrivateFlags;
        if ((i2 & 2) == 0) {
            this.mPrivateFlags = i2 | 2;
            View findFocus = this.mAttachInfo != null ? getRootView().findFocus() : null;
            ViewParent viewParent = this.mParent;
            if (viewParent != null) {
                viewParent.requestChildFocus(this, this);
                updateFocusedInCluster(findFocus, i);
            }
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mTreeObserver.dispatchOnGlobalFocusChange(findFocus, this);
            }
            onFocusChanged(true, i, rect);
            refreshDrawableState();
        }
    }

    public final void setRevealOnFocusHint(boolean z) {
        if (z) {
            this.mPrivateFlags3 &= -67108865;
        } else {
            this.mPrivateFlags3 |= 67108864;
        }
    }

    public final boolean getRevealOnFocusHint() {
        return (this.mPrivateFlags3 & 67108864) == 0;
    }

    public void getHotspotBounds(Rect rect) {
        Drawable background = getBackground();
        if (background != null) {
            background.getHotspotBounds(rect);
        } else {
            getBoundsOnScreen(rect);
        }
    }

    public boolean requestRectangleOnScreen(Rect rect) {
        return requestRectangleOnScreen(rect, false);
    }

    public boolean requestRectangleOnScreen(Rect rect, boolean z) {
        boolean z2 = false;
        if (this.mParent == null) {
            return false;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        RectF rectF = attachInfo != null ? attachInfo.mTmpTransformRect : new RectF();
        rectF.set(rect);
        ViewParent viewParent = this.mParent;
        while (viewParent != null) {
            rect.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            z2 |= viewParent.requestChildRectangleOnScreen(this, rect, z);
            if (!(viewParent instanceof View)) {
                return z2;
            }
            rectF.offset(this.mLeft - this.getScrollX(), this.mTop - this.getScrollY());
            this = viewParent;
            viewParent = this.getParent();
        }
        return z2;
    }

    public void clearFocus() {
        clearFocusInternal(null, true, sAlwaysAssignFocus || !isInTouchMode());
    }

    public void clearFocusInternal(View view, boolean z, boolean z2) {
        ViewParent viewParent;
        int i = this.mPrivateFlags;
        if ((i & 2) != 0) {
            this.mPrivateFlags = i & (-3);
            clearParentsWantFocus();
            if (z && (viewParent = this.mParent) != null) {
                viewParent.clearChildFocus(this);
            }
            onFocusChanged(false, 0, null);
            refreshDrawableState();
            if (z) {
                if (z2 && rootViewRequestFocus()) {
                    return;
                }
                notifyGlobalFocusCleared(this);
            }
        }
    }

    void notifyGlobalFocusCleared(View view) {
        AttachInfo attachInfo;
        if (view == null || (attachInfo = this.mAttachInfo) == null) {
            return;
        }
        attachInfo.mTreeObserver.dispatchOnGlobalFocusChange(view, null);
    }

    boolean rootViewRequestFocus() {
        View rootView = getRootView();
        return rootView != null && rootView.requestFocus();
    }

    void unFocus(View view) {
        clearFocusInternal(view, false, false);
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

    boolean hasFocusable(boolean z, boolean z2) {
        if (!isFocusableInTouchMode()) {
            for (ViewParent viewParent = this.mParent; viewParent instanceof ViewGroup; viewParent = viewParent.getParent()) {
                if (((ViewGroup) viewParent).shouldBlockFocusForTouchscreen()) {
                    return false;
                }
            }
        }
        int i = this.mViewFlags;
        return (i & 12) == 0 && (i & 32) == 0 && (z || getFocusable() != 16) && isFocusable();
    }

    protected void onFocusChanged(boolean z, int i, Rect rect) {
        if (z) {
            sendAccessibilityEvent(8);
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
        switchDefaultFocusHighlight();
        if (!z) {
            if (isPressed()) {
                setPressed(false);
            }
            if (hasWindowFocus()) {
                notifyFocusChangeToImeFocusController(false);
            }
            onFocusLost();
        } else if (hasWindowFocus()) {
            notifyFocusChangeToImeFocusController(true);
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null) {
                if (this.mIsHandwritingDelegate) {
                    viewRootImpl.getHandwritingInitiator().onDelegateViewFocused(this);
                } else if (android.view.inputmethod.Flags.initiationWithoutInputConnection() && onCheckIsTextEditor()) {
                    viewRootImpl.getHandwritingInitiator().onEditorFocused(this);
                }
            }
        }
        invalidate(true);
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnFocusChangeListener != null) {
            listenerInfo.mOnFocusChangeListener.onFocusChange(this, z);
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mKeyDispatchState.reset(this);
        }
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.onDescendantUnbufferedRequested();
        }
        notifyEnterOrExitForAutoFillIfNeeded(z);
        updatePreferKeepClearForFocus();
    }

    private void notifyFocusChangeToImeFocusController(boolean z) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return;
        }
        attachInfo.mViewRootImpl.getImeFocusController().onViewFocusChanged(this, z);
    }

    public void notifyEnterOrExitForAutoFillIfNeeded(boolean z) {
        AutofillManager autofillManager;
        if (!canNotifyAutofillEnterExitEvent() || (autofillManager = getAutofillManager()) == null) {
            return;
        }
        if (z) {
            if (!isLaidOut() || !isVisibleToUser()) {
                this.mPrivateFlags3 |= 134217728;
                return;
            } else {
                if (isVisibleToUser()) {
                    if (isFocused()) {
                        autofillManager.notifyViewEntered(this);
                        return;
                    } else {
                        autofillManager.notifyViewEnteredForFillDialog(this);
                        return;
                    }
                }
                return;
            }
        }
        if (isFocused()) {
            return;
        }
        autofillManager.notifyViewExited(this);
    }

    public void setAccessibilityPaneTitle(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.mAccessibilityPaneTitle)) {
            return;
        }
        boolean z = this.mAccessibilityPaneTitle == null;
        boolean z2 = charSequence == null;
        this.mAccessibilityPaneTitle = charSequence;
        if (charSequence != null && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        if (z) {
            notifyViewAccessibilityStateChangedIfNeeded(16);
        } else if (z2) {
            notifyViewAccessibilityStateChangedIfNeeded(32);
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(8);
        }
    }

    public CharSequence getAccessibilityPaneTitle() {
        return this.mAccessibilityPaneTitle;
    }

    private boolean isAccessibilityPane() {
        return this.mAccessibilityPaneTitle != null;
    }

    @Override // android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEvent(int i) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.sendAccessibilityEvent(this, i);
        } else {
            sendAccessibilityEventInternal(i);
        }
    }

    @Deprecated
    public void announceForAccessibility(CharSequence charSequence) {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled() || this.mParent == null) {
            return;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain(16384);
        onInitializeAccessibilityEvent(obtain);
        obtain.getText().add(charSequence);
        obtain.setContentDescription(null);
        this.mParent.requestSendAccessibilityEvent(this, obtain);
    }

    public void sendAccessibilityEventInternal(int i) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            sendAccessibilityEventUnchecked(AccessibilityEvent.obtain(i));
        }
    }

    @Override // android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.sendAccessibilityEventUnchecked(this, accessibilityEvent);
        } else {
            sendAccessibilityEventUncheckedInternal(accessibilityEvent);
        }
    }

    public void sendAccessibilityEventUncheckedInternal(final AccessibilityEvent accessibilityEvent) {
        boolean z = accessibilityEvent.getEventType() == 32 && (accessibilityEvent.getContentChangeTypes() & 32) != 0;
        boolean detached = detached();
        if (isShown() || z || detached) {
            onInitializeAccessibilityEvent(accessibilityEvent);
            if ((accessibilityEvent.getEventType() & POPULATING_ACCESSIBILITY_EVENT_TYPES) != 0) {
                dispatchPopulateAccessibilityEvent(accessibilityEvent);
            }
            SendAccessibilityEventThrottle throttleForAccessibilityEvent = getThrottleForAccessibilityEvent(accessibilityEvent);
            if (throttleForAccessibilityEvent != null) {
                throttleForAccessibilityEvent.post(accessibilityEvent);
            } else if (!z && detached) {
                postDelayed(new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        View.this.lambda$sendAccessibilityEventUncheckedInternal$0(accessibilityEvent);
                    }
                }, ViewConfiguration.getSendRecurringAccessibilityEventsInterval());
            } else {
                requestParentSendAccessibilityEvent(accessibilityEvent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendAccessibilityEventUncheckedInternal$0(AccessibilityEvent accessibilityEvent) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && isShown()) {
            requestParentSendAccessibilityEvent(accessibilityEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestParentSendAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (getParent() != null) {
            getParent().requestSendAccessibilityEvent(this, accessibilityEvent);
        }
    }

    private SendAccessibilityEventThrottle getThrottleForAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
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

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            return accessibilityDelegate.dispatchPopulateAccessibilityEvent(this, accessibilityEvent);
        }
        return dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
    }

    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return false;
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.onPopulateAccessibilityEvent(this, accessibilityEvent);
        } else {
            onPopulateAccessibilityEventInternal(accessibilityEvent);
        }
    }

    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32 && isAccessibilityPane()) {
            accessibilityEvent.getText().add(getAccessibilityPaneTitle());
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.onInitializeAccessibilityEvent(this, accessibilityEvent);
        } else {
            onInitializeAccessibilityEventInternal(accessibilityEvent);
        }
    }

    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        CharSequence iterableTextForAccessibility;
        accessibilityEvent.setSource(this);
        accessibilityEvent.setClassName(getAccessibilityClassName());
        accessibilityEvent.setPackageName(getContext().getPackageName());
        accessibilityEvent.setEnabled(isEnabled());
        accessibilityEvent.setContentDescription(this.mContentDescription);
        accessibilityEvent.setScrollX(getScrollX());
        accessibilityEvent.setScrollY(getScrollY());
        int eventType = accessibilityEvent.getEventType();
        if (eventType == 8) {
            AttachInfo attachInfo = this.mAttachInfo;
            ArrayList<View> arrayList = attachInfo != null ? attachInfo.mTempArrayList : new ArrayList<>();
            getRootView().addFocusables(arrayList, 2, 0);
            accessibilityEvent.setItemCount(arrayList.size());
            accessibilityEvent.setCurrentItemIndex(arrayList.indexOf(this));
            if (this.mAttachInfo != null) {
                arrayList.clear();
                return;
            }
            return;
        }
        if (eventType == 8192 && (iterableTextForAccessibility = getIterableTextForAccessibility()) != null && iterableTextForAccessibility.length() > 0) {
            accessibilityEvent.setFromIndex(getAccessibilitySelectionStart());
            accessibilityEvent.setToIndex(getAccessibilitySelectionEnd());
            accessibilityEvent.setItemCount(iterableTextForAccessibility.length());
        }
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            return accessibilityDelegate.createAccessibilityNodeInfo(this);
        }
        return createAccessibilityNodeInfoInternal();
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfoInternal() {
        AccessibilityNodeProvider accessibilityNodeProvider = getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            return accessibilityNodeProvider.createAccessibilityNodeInfo(-1);
        }
        AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain(this);
        onInitializeAccessibilityNodeInfo(obtain);
        return obtain;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(this, accessibilityNodeInfo);
        } else {
            onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        }
    }

    public void getBoundsOnScreen(Rect rect) {
        getBoundsOnScreen(rect, false);
    }

    public void getBoundsOnScreen(Rect rect, boolean z) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return;
        }
        RectF rectF = attachInfo.mTmpTransformRect;
        getBoundsToScreenInternal(rectF, z);
        rect.set(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
        this.mAttachInfo.mViewRootImpl.applyViewBoundsSandboxingIfNeeded(rect);
    }

    public void getBoundsOnScreen(RectF rectF, boolean z) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return;
        }
        RectF rectF2 = attachInfo.mTmpTransformRect;
        getBoundsToScreenInternal(rectF2, z);
        rectF.set(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom);
    }

    public void getBoundsInWindow(Rect rect, boolean z) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return;
        }
        RectF rectF = attachInfo.mTmpTransformRect;
        getBoundsToWindowInternal(rectF, z);
        rect.set(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private void getBoundsToScreenInternal(RectF rectF, boolean z) {
        rectF.set(0.0f, 0.0f, this.mRight - this.mLeft, this.mBottom - this.mTop);
        mapRectFromViewToScreenCoords(rectF, z);
    }

    private void getBoundsToWindowInternal(RectF rectF, boolean z) {
        rectF.set(0.0f, 0.0f, this.mRight - this.mLeft, this.mBottom - this.mTop);
        mapRectFromViewToWindowCoords(rectF, z);
    }

    public void mapRectFromViewToScreenCoords(RectF rectF, boolean z) {
        mapRectFromViewToWindowCoords(rectF, z);
        rectF.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    public void mapRectFromViewToWindowCoords(RectF rectF, boolean z) {
        if (!hasIdentityMatrix()) {
            getMatrix().mapRect(rectF);
        }
        rectF.offset(this.mLeft, this.mTop);
        Object obj = this.mParent;
        while (obj instanceof View) {
            View view = (View) obj;
            rectF.offset(-view.mScrollX, -view.mScrollY);
            if (z) {
                rectF.left = Math.max(rectF.left, 0.0f);
                rectF.top = Math.max(rectF.top, 0.0f);
                rectF.right = Math.min(rectF.right, view.getWidth());
                rectF.bottom = Math.min(rectF.bottom, view.getHeight());
            }
            if (!view.hasIdentityMatrix()) {
                view.getMatrix().mapRect(rectF);
            }
            rectF.offset(view.mLeft, view.mTop);
            obj = view.mParent;
        }
        if (obj instanceof ViewRootImpl) {
            rectF.offset(0.0f, -((ViewRootImpl) obj).mCurScrollY);
        }
    }

    public CharSequence getAccessibilityClassName() {
        return View.class.getName();
    }

    public void onProvideStructure(ViewStructure viewStructure) {
        onProvideStructure(viewStructure, 0, 0);
    }

    public void onProvideAutofillStructure(ViewStructure viewStructure, int i) {
        onProvideStructure(viewStructure, 1, i);
    }

    public void onProvideContentCaptureStructure(ViewStructure viewStructure, int i) {
        onProvideStructure(viewStructure, 2, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onProvideStructure(ViewStructure viewStructure, int i, int i2) {
        int i3;
        String str;
        String str2;
        String str3;
        int i4 = this.mID;
        if (i4 != -1 && !isViewIdGenerated(i4)) {
            try {
                Resources resources = getResources();
                str2 = resources.getResourceEntryName(i4);
                str3 = resources.getResourceTypeName(i4);
                str = resources.getResourcePackageName(i4);
            } catch (Resources.NotFoundException unused) {
                str = null;
                str2 = null;
                str3 = null;
            }
            viewStructure.setId(i4, str, str3, str2);
        } else {
            viewStructure.setId(i4, null, null, null);
        }
        if (i == 1 || i == 2) {
            int autofillType = getAutofillType();
            if (autofillType != 0) {
                viewStructure.setAutofillType(autofillType);
                viewStructure.setAutofillHints(getAutofillHints());
                viewStructure.setAutofillValue(getAutofillValue());
                viewStructure.setIsCredential(isCredential());
            }
            if (getViewCredentialHandler() != null) {
                viewStructure.setPendingCredentialRequest(getViewCredentialHandler().getRequest(), getViewCredentialHandler().getCallback());
            }
            viewStructure.setImportantForAutofill(getImportantForAutofill());
            viewStructure.setReceiveContentMimeTypes(getReceiveContentMimeTypes());
        }
        int i5 = 0;
        if (i == 1 && (i2 & 1) == 0) {
            Object parent = getParent();
            View view = parent instanceof View ? (View) parent : null;
            i3 = 0;
            while (view != null && !view.isImportantForAutofill()) {
                i5 += view.mLeft - view.mScrollX;
                i3 += view.mTop - view.mScrollY;
                Object parent2 = view.getParent();
                if (!(parent2 instanceof View)) {
                    break;
                } else {
                    view = (View) parent2;
                }
            }
        } else {
            i3 = 0;
        }
        int i6 = this.mLeft;
        int i7 = i5 + i6;
        int i8 = this.mTop;
        viewStructure.setDimens(i7, i3 + i8, this.mScrollX, this.mScrollY, this.mRight - i6, this.mBottom - i8);
        if (i == 0) {
            if (!hasIdentityMatrix()) {
                viewStructure.setTransformation(getMatrix());
            }
            viewStructure.setElevation(getZ());
        }
        viewStructure.setVisibility(getVisibility());
        viewStructure.setEnabled(isEnabled());
        if (isClickable()) {
            viewStructure.setClickable(true);
        }
        if (isFocusable()) {
            viewStructure.setFocusable(true);
        }
        if (isFocused()) {
            viewStructure.setFocused(true);
        }
        if (isAccessibilityFocused()) {
            viewStructure.setAccessibilityFocused(true);
        }
        if (isSelected()) {
            viewStructure.setSelected(true);
        }
        if (isActivated()) {
            viewStructure.setActivated(true);
        }
        if (isLongClickable()) {
            viewStructure.setLongClickable(true);
        }
        if (this instanceof Checkable) {
            viewStructure.setCheckable(true);
            if (((Checkable) this).isChecked()) {
                viewStructure.setChecked(true);
            }
        }
        if (isOpaque()) {
            viewStructure.setOpaque(true);
        }
        if (isContextClickable()) {
            viewStructure.setContextClickable(true);
        }
        viewStructure.setClassName(getAccessibilityClassName().toString());
        viewStructure.setContentDescription(getContentDescription());
    }

    public void onProvideVirtualStructure(ViewStructure viewStructure) {
        onProvideVirtualStructureCompat(viewStructure, false);
    }

    private void onProvideVirtualStructureCompat(ViewStructure viewStructure, boolean z) {
        AccessibilityNodeProvider accessibilityNodeProvider = getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            if (z && Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                Log.v(AUTOFILL_LOG_TAG, "onProvideVirtualStructureCompat() for " + this);
            }
            AccessibilityNodeInfo createAccessibilityNodeInfo = createAccessibilityNodeInfo();
            viewStructure.setChildCount(1);
            ViewStructure newChild = viewStructure.newChild(0);
            if (createAccessibilityNodeInfo == null) {
                Log.w(AUTOFILL_LOG_TAG, "AccessibilityNodeInfo is null.");
            } else {
                populateVirtualStructure(newChild, accessibilityNodeProvider, createAccessibilityNodeInfo, null, z);
                createAccessibilityNodeInfo.recycle();
            }
        }
    }

    public void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int i) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            onProvideVirtualStructureCompat(viewStructure, true);
        }
    }

    public void setOnReceiveContentListener(String[] strArr, OnReceiveContentListener onReceiveContentListener) {
        if (onReceiveContentListener != null) {
            Preconditions.checkArgument(strArr != null && strArr.length > 0, "When the listener is set, MIME types must also be set");
        }
        if (strArr != null) {
            Preconditions.checkArgument(Arrays.stream(strArr).noneMatch(new Predicate() { // from class: android.view.View$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean startsWith;
                    startsWith = ((String) obj).startsWith("*");
                    return startsWith;
                }
            }), "A MIME type set here must not start with *: " + Arrays.toString(strArr));
        }
        if (ArrayUtils.isEmpty(strArr)) {
            strArr = null;
        }
        this.mReceiveContentMimeTypes = strArr;
        getListenerInfo().mOnReceiveContentListener = onReceiveContentListener;
    }

    public ContentInfo performReceiveContent(ContentInfo contentInfo) {
        OnReceiveContentListener onReceiveContentListener = this.mListenerInfo == null ? null : getListenerInfo().mOnReceiveContentListener;
        if (onReceiveContentListener != null) {
            ContentInfo onReceiveContent = onReceiveContentListener.onReceiveContent(this, contentInfo);
            if (onReceiveContent == null) {
                return null;
            }
            return onReceiveContent(onReceiveContent);
        }
        return onReceiveContent(contentInfo);
    }

    public String[] getReceiveContentMimeTypes() {
        return this.mReceiveContentMimeTypes;
    }

    public void autofill(SparseArray<AutofillValue> sparseArray) {
        AccessibilityNodeProvider accessibilityNodeProvider;
        if (this.mContext.isAutofillCompatibilityEnabled() && (accessibilityNodeProvider = getAccessibilityNodeProvider()) != null) {
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                AutofillValue valueAt = sparseArray.valueAt(i);
                if (valueAt.isText()) {
                    int keyAt = sparseArray.keyAt(i);
                    CharSequence textValue = valueAt.getTextValue();
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, textValue);
                    accessibilityNodeProvider.performAction(keyAt, 2097152, bundle);
                }
            }
        }
    }

    public void onGetCredentialResponse(GetCredentialResponse getCredentialResponse) {
        if (getPendingCredentialCallback() == null) {
            Log.w(AUTOFILL_LOG_TAG, "onGetCredentialResponse called but no callback found");
        } else {
            getPendingCredentialCallback().onResult(getCredentialResponse);
        }
    }

    public void onGetCredentialException(String str, String str2) {
        if (getPendingCredentialCallback() == null) {
            Log.w(AUTOFILL_LOG_TAG, "onGetCredentialException called but no callback found");
        } else {
            getPendingCredentialCallback().onError(new GetCredentialException(str, str2));
        }
    }

    public final AutofillId getAutofillId() {
        if (this.mAutofillId == null) {
            this.mAutofillId = new AutofillId(getAutofillViewId());
        }
        return this.mAutofillId;
    }

    public final GetCredentialRequest getPendingCredentialRequest() {
        ViewCredentialHandler viewCredentialHandler = this.mViewCredentialHandler;
        if (viewCredentialHandler == null) {
            return null;
        }
        return viewCredentialHandler.getRequest();
    }

    public final OutcomeReceiver<GetCredentialResponse, GetCredentialException> getPendingCredentialCallback() {
        ViewCredentialHandler viewCredentialHandler = this.mViewCredentialHandler;
        if (viewCredentialHandler == null) {
            return null;
        }
        return viewCredentialHandler.getCallback();
    }

    public void setAutofillId(AutofillId autofillId) {
        if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            Log.v(AUTOFILL_LOG_TAG, "setAutofill(): from " + this.mAutofillId + " to " + autofillId);
        }
        if (isAttachedToWindow()) {
            throw new IllegalStateException("Cannot set autofill id when view is attached");
        }
        if (autofillId != null && !autofillId.isNonVirtual()) {
            throw new IllegalStateException("Cannot set autofill id assigned to virtual views");
        }
        if (autofillId == null && (this.mPrivateFlags3 & 1073741824) == 0) {
            return;
        }
        this.mAutofillId = autofillId;
        if (autofillId != null) {
            this.mAutofillViewId = autofillId.getViewId();
            this.mPrivateFlags3 |= 1073741824;
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

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, to = "auto"), @ViewDebug.IntToString(from = 1, to = "yes"), @ViewDebug.IntToString(from = 2, to = "no"), @ViewDebug.IntToString(from = 4, to = "yesExcludeDescendants"), @ViewDebug.IntToString(from = 8, to = "noExcludeDescendants")})
    public int getImportantForAutofill() {
        return (this.mPrivateFlags3 & PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK) >> 19;
    }

    public void setImportantForAutofill(int i) {
        this.mPrivateFlags3 = ((i << 19) & PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK) | (this.mPrivateFlags3 & (-7864321));
    }

    public final boolean isImportantForAutofill() {
        String str;
        for (ViewParent viewParent = this.mParent; viewParent instanceof View; viewParent = viewParent.getParent()) {
            int importantForAutofill = ((View) viewParent).getImportantForAutofill();
            if (importantForAutofill == 8 || importantForAutofill == 4) {
                if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                    Log.v(AUTOFILL_LOG_TAG, "View (" + this + ") is not important for autofill because parent " + viewParent + "'s importance is " + importantForAutofill);
                }
                return false;
            }
        }
        int importantForAutofill2 = getImportantForAutofill();
        if (importantForAutofill2 == 4 || importantForAutofill2 == 1) {
            return true;
        }
        if (importantForAutofill2 == 8 || importantForAutofill2 == 2) {
            if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                Log.v(AUTOFILL_LOG_TAG, "View (" + this + ") is not important for autofill because its importance is " + importantForAutofill2);
            }
            return false;
        }
        if (importantForAutofill2 != 0) {
            Log.w(AUTOFILL_LOG_TAG, "invalid autofill importance (" + importantForAutofill2 + " on view " + this);
            return false;
        }
        int i = this.mID;
        if (i != -1 && !isViewIdGenerated(i)) {
            Resources resources = getResources();
            String str2 = null;
            try {
                str = resources.getResourceEntryName(i);
                try {
                    str2 = resources.getResourcePackageName(i);
                } catch (Resources.NotFoundException unused) {
                }
            } catch (Resources.NotFoundException unused2) {
                str = null;
            }
            if (str != null && str2 != null && str2.equals(this.mContext.getPackageName())) {
                return true;
            }
        }
        return getAutofillHints() != null;
    }

    public final void setContentSensitivity(int i) {
        this.mPrivateFlags4 = ((i << 24) & 50331648) | (this.mPrivateFlags4 & (-50331649));
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

    private void updateSensitiveViewsCountIfNeeded(boolean z) {
        if (!Flags.sensitiveContentAppProtection() || this.mAttachInfo == null) {
            return;
        }
        if (z && isContentSensitive()) {
            int i = this.mPrivateFlags4;
            if ((i & 67108864) == 0) {
                this.mPrivateFlags4 = i | 67108864;
                this.mAttachInfo.increaseSensitiveViewsCount();
                return;
            }
            return;
        }
        int i2 = this.mPrivateFlags4;
        if ((67108864 & i2) != 0) {
            this.mPrivateFlags4 = i2 & (-67108865);
            this.mAttachInfo.decreaseSensitiveViewsCount();
        }
    }

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, to = "auto"), @ViewDebug.IntToString(from = 1, to = "yes"), @ViewDebug.IntToString(from = 2, to = "no"), @ViewDebug.IntToString(from = 4, to = "yesExcludeDescendants"), @ViewDebug.IntToString(from = 8, to = "noExcludeDescendants")})
    public int getImportantForContentCapture() {
        return this.mPrivateFlags4 & 15;
    }

    public void setImportantForContentCapture(int i) {
        this.mPrivateFlags4 = (i & 15) | (this.mPrivateFlags4 & (-16));
    }

    public final boolean isImportantForContentCapture() {
        int i = this.mPrivateFlags4;
        if ((i & 64) != 0) {
            return (i & 128) != 0;
        }
        boolean calculateIsImportantForContentCapture = calculateIsImportantForContentCapture();
        int i2 = this.mPrivateFlags4 & PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        this.mPrivateFlags4 = i2;
        if (calculateIsImportantForContentCapture) {
            this.mPrivateFlags4 = i2 | 128;
        }
        this.mPrivateFlags4 |= 64;
        return calculateIsImportantForContentCapture;
    }

    private boolean calculateIsImportantForContentCapture() {
        for (ViewParent viewParent = this.mParent; viewParent instanceof View; viewParent = viewParent.getParent()) {
            int importantForContentCapture = ((View) viewParent).getImportantForContentCapture();
            if (importantForContentCapture == 8 || importantForContentCapture == 4) {
                if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 2)) {
                    Log.v(CONTENT_CAPTURE_LOG_TAG, "View (" + this + ") is not important for content capture because parent " + viewParent + "'s importance is " + importantForContentCapture);
                }
                return false;
            }
        }
        int importantForContentCapture2 = getImportantForContentCapture();
        if (importantForContentCapture2 == 4 || importantForContentCapture2 == 1) {
            return true;
        }
        if (importantForContentCapture2 == 8 || importantForContentCapture2 == 2) {
            if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 2)) {
                Log.v(CONTENT_CAPTURE_LOG_TAG, "View (" + this + ") is not important for content capture because its importance is " + importantForContentCapture2);
            }
            return false;
        }
        if (importantForContentCapture2 != 0) {
            Log.w(CONTENT_CAPTURE_LOG_TAG, "invalid content capture importance (" + importantForContentCapture2 + " on view " + this);
            return false;
        }
        if (this instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) this;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i).isImportantForContentCapture()) {
                    return true;
                }
            }
        }
        return getAutofillHints() != null;
    }

    private void notifyAppearedOrDisappearedForContentCaptureIfNeeded(boolean z) {
        AttachInfo attachInfo = this.mAttachInfo;
        if ((attachInfo == null || attachInfo.mReadyForContentCaptureUpdates) && this.mContext.getContentCaptureOptions() != null) {
            if (z) {
                boolean z2 = getNotifiedContentCaptureDisappeared() && getVisibility() == 0 && !isLayoutRequested();
                if (getVisibility() != 0 || getNotifiedContentCaptureAppeared()) {
                    return;
                }
                if (!isLaidOut() && !z2) {
                    return;
                }
            } else if (!getNotifiedContentCaptureAppeared() || getNotifiedContentCaptureDisappeared()) {
                return;
            }
            ContentCaptureSession contentCaptureSession = getContentCaptureSession();
            if (contentCaptureSession != null && isImportantForContentCapture()) {
                if (z) {
                    setNotifiedContentCaptureAppeared();
                    if (attachInfo != null) {
                        makeParentImportantAndNotifyAppearedEventIfNeed();
                        attachInfo.delayNotifyContentCaptureEvent(contentCaptureSession, this, z);
                        return;
                    }
                    return;
                }
                this.mPrivateFlags4 = (this.mPrivateFlags4 | 32) & (-17);
                if (attachInfo != null) {
                    attachInfo.delayNotifyContentCaptureEvent(contentCaptureSession, this, z);
                }
                if (isTemporarilyDetached()) {
                    return;
                }
                clearTranslationState();
            }
        }
    }

    private void makeParentImportantAndNotifyAppearedEventIfNeed() {
        Object parent = getParent();
        if (parent instanceof View) {
            View view = (View) parent;
            if (view.getNotifiedContentCaptureAppeared()) {
                return;
            }
            view.mPrivateFlags4 |= 192;
            view.notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
        }
    }

    private void setNotifiedContentCaptureAppeared() {
        this.mPrivateFlags4 = (this.mPrivateFlags4 | 16) & (-33);
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
        ContentCaptureSession andCacheContentCaptureSession = getAndCacheContentCaptureSession();
        this.mContentCaptureSession = andCacheContentCaptureSession;
        this.mContentCaptureSessionCached = true;
        return andCacheContentCaptureSession;
    }

    private ContentCaptureSession getAndCacheContentCaptureSession() {
        ContentCaptureSession contentCaptureSession = this.mContentCaptureSession;
        if (contentCaptureSession != null) {
            return contentCaptureSession;
        }
        Object obj = this.mParent;
        ContentCaptureSession contentCaptureSession2 = obj instanceof View ? ((View) obj).getContentCaptureSession() : null;
        if (contentCaptureSession2 != null) {
            return contentCaptureSession2;
        }
        ContentCaptureManager contentCaptureManager = (ContentCaptureManager) this.mContext.getSystemService(ContentCaptureManager.class);
        if (contentCaptureManager == null) {
            return null;
        }
        return contentCaptureManager.getMainContentCaptureSession();
    }

    private AutofillManager getAutofillManager() {
        return (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
    }

    final boolean isActivityDeniedForAutofillForUnimportantView() {
        AutofillManager autofillManager = getAutofillManager();
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.isActivityDeniedForAutofill();
    }

    final boolean isMatchingAutofillableHeuristics() {
        AutofillManager autofillManager = getAutofillManager();
        if (autofillManager != null && autofillManager.isTriggerFillRequestOnUnimportantViewEnabled()) {
            return autofillManager.isAutofillable(this);
        }
        return false;
    }

    private boolean isAutofillable() {
        AutofillManager autofillManager;
        if (getAutofillType() == 0 || (autofillManager = getAutofillManager()) == null || getAutofillViewId() <= 1073741823) {
            return false;
        }
        if ((isImportantForAutofill() && autofillManager.isTriggerFillRequestOnFilteredImportantViewsEnabled()) || (!isImportantForAutofill() && autofillManager.isTriggerFillRequestOnUnimportantViewEnabled())) {
            if (autofillManager.isAutofillable(this)) {
                return true;
            }
            return notifyAugmentedAutofillIfNeeded(autofillManager);
        }
        if (isImportantForAutofill()) {
            return true;
        }
        return notifyAugmentedAutofillIfNeeded(autofillManager);
    }

    private boolean notifyAugmentedAutofillIfNeeded(AutofillManager autofillManager) {
        AutofillOptions autofillOptions = this.mContext.getAutofillOptions();
        if (autofillOptions == null || !autofillOptions.isAugmentedAutofillEnabled(this.mContext)) {
            return false;
        }
        autofillManager.notifyViewEnteredForAugmentedAutofill(this);
        return true;
    }

    public boolean canNotifyAutofillEnterExitEvent() {
        return isAutofillable() && isAttachedToWindow();
    }

    private void populateVirtualStructure(ViewStructure viewStructure, AccessibilityNodeProvider accessibilityNodeProvider, AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityNodeInfo accessibilityNodeInfo2, boolean z) {
        View view;
        AccessibilityNodeProvider accessibilityNodeProvider2;
        AccessibilityNodeInfo accessibilityNodeInfo3;
        boolean z2;
        viewStructure.setId(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId()), null, null, accessibilityNodeInfo.getViewIdResourceName());
        Rect tempRect = viewStructure.getTempRect();
        if (sCalculateBoundsInParentFromBoundsInScreenFlagValue) {
            getBoundsInParent(accessibilityNodeInfo, accessibilityNodeInfo2, tempRect);
        } else {
            accessibilityNodeInfo.getBoundsInParent(tempRect);
        }
        viewStructure.setDimens(tempRect.left, tempRect.top, 0, 0, tempRect.width(), tempRect.height());
        int i = 0;
        viewStructure.setVisibility(0);
        viewStructure.setEnabled(accessibilityNodeInfo.isEnabled());
        if (accessibilityNodeInfo.isClickable()) {
            viewStructure.setClickable(true);
        }
        if (accessibilityNodeInfo.isFocusable()) {
            viewStructure.setFocusable(true);
        }
        if (accessibilityNodeInfo.isFocused()) {
            viewStructure.setFocused(true);
        }
        if (accessibilityNodeInfo.isAccessibilityFocused()) {
            viewStructure.setAccessibilityFocused(true);
        }
        if (accessibilityNodeInfo.isSelected()) {
            viewStructure.setSelected(true);
        }
        if (accessibilityNodeInfo.isLongClickable()) {
            viewStructure.setLongClickable(true);
        }
        if (accessibilityNodeInfo.isCheckable()) {
            viewStructure.setCheckable(true);
            if (accessibilityNodeInfo.isChecked()) {
                viewStructure.setChecked(true);
            }
        }
        if (accessibilityNodeInfo.isContextClickable()) {
            viewStructure.setContextClickable(true);
        }
        if (z) {
            viewStructure.setAutofillId(new AutofillId(getAutofillId(), AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId())));
        }
        if (getViewCredentialHandler() != null) {
            viewStructure.setPendingCredentialRequest(getViewCredentialHandler().getRequest(), getViewCredentialHandler().getCallback());
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        viewStructure.setClassName(className != null ? className.toString() : null);
        viewStructure.setContentDescription(accessibilityNodeInfo.getContentDescription());
        if (z) {
            int maxTextLength = accessibilityNodeInfo.getMaxTextLength();
            if (maxTextLength != -1) {
                viewStructure.setMaxTextLength(maxTextLength);
            }
            viewStructure.setHint(accessibilityNodeInfo.getHintText());
        }
        CharSequence text = accessibilityNodeInfo.getText();
        boolean z3 = (text == null && accessibilityNodeInfo.getError() == null) ? false : true;
        if (z3) {
            viewStructure.setText(text, accessibilityNodeInfo.getTextSelectionStart(), accessibilityNodeInfo.getTextSelectionEnd());
        }
        if (z) {
            if (accessibilityNodeInfo.isEditable()) {
                viewStructure.setDataIsSensitive(true);
                if (z3) {
                    viewStructure.setAutofillType(1);
                    viewStructure.setAutofillValue(AutofillValue.forText(text));
                }
                int inputType = accessibilityNodeInfo.getInputType();
                if (inputType == 0 && accessibilityNodeInfo.isPassword()) {
                    inputType = 129;
                }
                viewStructure.setInputType(inputType);
            } else {
                viewStructure.setDataIsSensitive(false);
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        if (childCount > 0) {
            viewStructure.setChildCount(childCount);
            while (i < childCount) {
                if (AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getChildNodeIds().get(i)) == -1) {
                    Log.e(VIEW_LOG_TAG, "Virtual view pointing to its host. Ignoring");
                } else {
                    AccessibilityNodeInfo createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getChildId(i)));
                    if (createAccessibilityNodeInfo != null) {
                        view = this;
                        accessibilityNodeProvider2 = accessibilityNodeProvider;
                        accessibilityNodeInfo3 = accessibilityNodeInfo;
                        z2 = z;
                        view.populateVirtualStructure(viewStructure.newChild(i), accessibilityNodeProvider2, createAccessibilityNodeInfo, accessibilityNodeInfo3, z2);
                        createAccessibilityNodeInfo.recycle();
                        i++;
                        this = view;
                        accessibilityNodeProvider = accessibilityNodeProvider2;
                        accessibilityNodeInfo = accessibilityNodeInfo3;
                        z = z2;
                    }
                }
                view = this;
                accessibilityNodeProvider2 = accessibilityNodeProvider;
                accessibilityNodeInfo3 = accessibilityNodeInfo;
                z2 = z;
                i++;
                this = view;
                accessibilityNodeProvider = accessibilityNodeProvider2;
                accessibilityNodeInfo = accessibilityNodeInfo3;
                z = z2;
            }
        }
    }

    private void getBoundsInParent(AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityNodeInfo accessibilityNodeInfo2, Rect rect) {
        accessibilityNodeInfo.getBoundsInParent(rect);
        if ((rect.left | rect.top | rect.right | rect.bottom) == 0) {
            if (accessibilityNodeInfo2 != null) {
                Rect boundsInScreen = accessibilityNodeInfo2.getBoundsInScreen();
                Rect boundsInScreen2 = accessibilityNodeInfo.getBoundsInScreen();
                rect.set(boundsInScreen2.left - boundsInScreen.left, boundsInScreen2.top - boundsInScreen.top, boundsInScreen2.right - boundsInScreen.left, boundsInScreen2.bottom - boundsInScreen.top);
                return;
            }
            accessibilityNodeInfo.getBoundsInScreen(rect);
        }
    }

    public void dispatchProvideStructure(ViewStructure viewStructure) {
        dispatchProvideStructure(viewStructure, 0, 0);
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        dispatchProvideStructure(viewStructure, 1, i);
    }

    private void dispatchProvideStructure(ViewStructure viewStructure, int i, int i2) {
        if (i == 1) {
            viewStructure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(viewStructure, i2);
            onProvideAutofillVirtualStructure(viewStructure, i2);
        } else if (!isAssistBlocked()) {
            onProvideStructure(viewStructure);
            onProvideVirtualStructure(viewStructure);
        } else {
            viewStructure.setClassName(getAccessibilityClassName().toString());
            viewStructure.setAssistBlocked(true);
        }
    }

    public void dispatchInitialProvideContentCaptureStructure() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            Log.w(CONTENT_CAPTURE_LOG_TAG, "dispatchProvideContentCaptureStructure(): no AttachInfo for " + this);
            return;
        }
        ContentCaptureManager contentCaptureManager = attachInfo.mContentCaptureManager;
        if (contentCaptureManager == null) {
            Log.w(CONTENT_CAPTURE_LOG_TAG, "dispatchProvideContentCaptureStructure(): no ContentCaptureManager for " + this);
            return;
        }
        attachInfo.mReadyForContentCaptureUpdates = true;
        if (!isImportantForContentCapture()) {
            if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 3)) {
                Log.d(CONTENT_CAPTURE_LOG_TAG, "dispatchProvideContentCaptureStructure(): decorView is not important");
                return;
            }
            return;
        }
        attachInfo.mContentCaptureManager = contentCaptureManager;
        ContentCaptureSession contentCaptureSession = getContentCaptureSession();
        if (contentCaptureSession == null) {
            if (Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 3)) {
                Log.d(CONTENT_CAPTURE_LOG_TAG, "dispatchProvideContentCaptureStructure(): no session for " + this);
                return;
            }
            return;
        }
        contentCaptureSession.notifyViewTreeEvent(true);
        try {
            dispatchProvideContentCaptureStructure();
        } finally {
            contentCaptureSession.notifyViewTreeEvent(false);
        }
    }

    void dispatchProvideContentCaptureStructure() {
        ContentCaptureSession contentCaptureSession = getContentCaptureSession();
        if (contentCaptureSession != null) {
            ViewStructure newViewStructure = contentCaptureSession.newViewStructure(this);
            onProvideContentCaptureStructure(newViewStructure, 0);
            setNotifiedContentCaptureAppeared();
            contentCaptureSession.notifyViewAppeared(newViewStructure);
        }
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction;
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return;
        }
        Rect rect = attachInfo.mTmpInvalRect;
        getDrawingRect(rect);
        accessibilityNodeInfo.setBoundsInParent(rect);
        getBoundsOnScreen(rect, true);
        accessibilityNodeInfo.setBoundsInScreen(rect);
        getBoundsInWindow(rect, true);
        accessibilityNodeInfo.setBoundsInWindow(rect);
        Object parentForAccessibility = getParentForAccessibility();
        if (parentForAccessibility instanceof View) {
            accessibilityNodeInfo.setParent((View) parentForAccessibility);
        }
        if (this.mID != -1) {
            View rootView = getRootView();
            if (rootView == null) {
                rootView = this;
            }
            View findLabelForView = rootView.findLabelForView(this, this.mID);
            if (findLabelForView != null) {
                if (android.view.accessibility.Flags.supportMultipleLabeledby()) {
                    accessibilityNodeInfo.addLabeledBy(findLabelForView);
                } else {
                    accessibilityNodeInfo.setLabeledBy(findLabelForView);
                }
            }
            if ((this.mAttachInfo.mAccessibilityFetchFlags & 256) != 0 && Resources.resourceHasPackage(this.mID)) {
                try {
                    accessibilityNodeInfo.setViewIdResourceName(getResources().getResourceName(this.mID));
                } catch (Resources.NotFoundException unused) {
                }
            }
        }
        if (this.mLabelForId != -1) {
            View rootView2 = getRootView();
            if (rootView2 == null) {
                rootView2 = this;
            }
            View findViewInsideOutShouldExist = rootView2.findViewInsideOutShouldExist(this, this.mLabelForId);
            if (findViewInsideOutShouldExist != null) {
                accessibilityNodeInfo.setLabelFor(findViewInsideOutShouldExist);
            }
        }
        if (this.mAccessibilityTraversalBeforeId != -1) {
            View rootView3 = getRootView();
            if (rootView3 == null) {
                rootView3 = this;
            }
            View findViewInsideOutShouldExist2 = rootView3.findViewInsideOutShouldExist(this, this.mAccessibilityTraversalBeforeId);
            if (findViewInsideOutShouldExist2 != null && findViewInsideOutShouldExist2.includeForAccessibility()) {
                accessibilityNodeInfo.setTraversalBefore(findViewInsideOutShouldExist2);
            }
        }
        if (this.mAccessibilityTraversalAfterId != -1) {
            View rootView4 = getRootView();
            if (rootView4 == null) {
                rootView4 = this;
            }
            View findViewInsideOutShouldExist3 = rootView4.findViewInsideOutShouldExist(this, this.mAccessibilityTraversalAfterId);
            if (findViewInsideOutShouldExist3 != null && findViewInsideOutShouldExist3.includeForAccessibility()) {
                accessibilityNodeInfo.setTraversalAfter(findViewInsideOutShouldExist3);
            }
        }
        accessibilityNodeInfo.setVisibleToUser(isVisibleToUser());
        accessibilityNodeInfo.setImportantForAccessibility(isImportantForAccessibility());
        accessibilityNodeInfo.setAccessibilityDataSensitive(isAccessibilityDataSensitive());
        accessibilityNodeInfo.setPackageName(this.mContext.getPackageName());
        accessibilityNodeInfo.setClassName(getAccessibilityClassName());
        accessibilityNodeInfo.setStateDescription(getStateDescription());
        accessibilityNodeInfo.setContentDescription(getContentDescription());
        accessibilityNodeInfo.setEnabled(isEnabled());
        accessibilityNodeInfo.setClickable(isClickable());
        accessibilityNodeInfo.setFocusable(isFocusable());
        accessibilityNodeInfo.setScreenReaderFocusable(isScreenReaderFocusable());
        accessibilityNodeInfo.setFocused(isFocused());
        accessibilityNodeInfo.setAccessibilityFocused(isAccessibilityFocused());
        accessibilityNodeInfo.setSelected(isSelected());
        accessibilityNodeInfo.setLongClickable(isLongClickable());
        accessibilityNodeInfo.setContextClickable(isContextClickable());
        accessibilityNodeInfo.setLiveRegion(getAccessibilityLiveRegion());
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo != null && tooltipInfo.mTooltipText != null) {
            accessibilityNodeInfo.setTooltipText(this.mTooltipInfo.mTooltipText);
            if (this.mTooltipInfo.mTooltipPopup == null) {
                accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP;
            } else {
                accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
            }
            accessibilityNodeInfo.addAction(accessibilityAction);
        }
        accessibilityNodeInfo.addAction(4);
        accessibilityNodeInfo.addAction(8);
        if (isFocusable()) {
            if (isFocused()) {
                accessibilityNodeInfo.addAction(2);
            } else {
                accessibilityNodeInfo.addAction(1);
            }
        }
        if (!isAccessibilityFocused()) {
            accessibilityNodeInfo.addAction(64);
        } else {
            accessibilityNodeInfo.addAction(128);
        }
        if (isClickable() && isEnabled()) {
            accessibilityNodeInfo.addAction(16);
        }
        if (isLongClickable() && isEnabled()) {
            accessibilityNodeInfo.addAction(32);
        }
        if (isContextClickable() && isEnabled()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK);
        }
        CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
        if (iterableTextForAccessibility != null && iterableTextForAccessibility.length() > 0) {
            accessibilityNodeInfo.setTextSelection(getAccessibilitySelectionStart(), getAccessibilitySelectionEnd());
            accessibilityNodeInfo.addAction(131072);
            accessibilityNodeInfo.addAction(256);
            accessibilityNodeInfo.addAction(512);
            accessibilityNodeInfo.setMovementGranularities(11);
        }
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN);
        populateAccessibilityNodeInfoDrawingOrderInParent(accessibilityNodeInfo);
        accessibilityNodeInfo.setPaneTitle(this.mAccessibilityPaneTitle);
        accessibilityNodeInfo.setHeading(isAccessibilityHeading());
        TouchDelegate touchDelegate = this.mTouchDelegate;
        if (touchDelegate != null) {
            accessibilityNodeInfo.setTouchDelegateInfo(touchDelegate.getTouchDelegateInfo());
        }
        if (startedSystemDragForAccessibility()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_CANCEL);
        }
        if (canAcceptAccessibilityDrop()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_DROP);
        }
    }

    private void populateAccessibilityNodeInfoDrawingOrderInParent(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i = 0;
        if ((this.mPrivateFlags & 16) == 0) {
            accessibilityNodeInfo.setDrawingOrder(0);
            return;
        }
        ViewParent parentForAccessibility = getParentForAccessibility();
        int i2 = 1;
        while (true) {
            if (this == parentForAccessibility) {
                i = i2;
                break;
            }
            Object parent = this.getParent();
            if (!(parent instanceof ViewGroup)) {
                break;
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            int childCount = viewGroup.getChildCount();
            if (childCount > 1) {
                ArrayList<View> buildOrderedChildList = viewGroup.buildOrderedChildList();
                if (buildOrderedChildList != null) {
                    int indexOf = buildOrderedChildList.indexOf(this);
                    for (int i3 = 0; i3 < indexOf; i3++) {
                        i2 += numViewsForAccessibility(buildOrderedChildList.get(i3));
                    }
                    buildOrderedChildList.clear();
                } else {
                    int indexOfChild = viewGroup.indexOfChild(this);
                    boolean isChildrenDrawingOrderEnabled = viewGroup.isChildrenDrawingOrderEnabled();
                    if (indexOfChild >= 0 && isChildrenDrawingOrderEnabled) {
                        indexOfChild = viewGroup.getChildDrawingOrder(childCount, indexOfChild);
                    }
                    int i4 = isChildrenDrawingOrderEnabled ? childCount : indexOfChild;
                    if (indexOfChild != 0) {
                        for (int i5 = 0; i5 < i4; i5++) {
                            if ((isChildrenDrawingOrderEnabled ? viewGroup.getChildDrawingOrder(childCount, i5) : i5) < indexOfChild) {
                                i2 += numViewsForAccessibility(viewGroup.getChildAt(i5));
                            }
                        }
                    }
                }
            }
            this = (View) parent;
        }
        accessibilityNodeInfo.setDrawingOrder(i);
    }

    private static int numViewsForAccessibility(View view) {
        if (view == null) {
            return 0;
        }
        if (view.includeForAccessibility()) {
            return 1;
        }
        if (view instanceof ViewGroup) {
            return ((ViewGroup) view).getNumChildrenForAccessibility();
        }
        return 0;
    }

    private View findLabelForView(View view, int i) {
        if (this.mMatchLabelForPredicate == null) {
            this.mMatchLabelForPredicate = new MatchLabelForPredicate();
        }
        this.mMatchLabelForPredicate.mLabeledId = i;
        return findViewByPredicateInsideOut(view, this.mMatchLabelForPredicate);
    }

    public boolean isVisibleToUserForAutofill(int i) {
        if (!this.mContext.isAutofillCompatibilityEnabled()) {
            return true;
        }
        AccessibilityNodeProvider accessibilityNodeProvider = getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            AccessibilityNodeInfo createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(i);
            if (createAccessibilityNodeInfo != null) {
                return createAccessibilityNodeInfo.isVisibleToUser();
            }
            return false;
        }
        Log.w(VIEW_LOG_TAG, "isVisibleToUserForAutofill(" + i + "): no provider");
        return false;
    }

    public boolean isVisibleToUser() {
        return isVisibleToUser(null);
    }

    protected boolean isVisibleToUser(Rect rect) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null || attachInfo.mWindowVisibility != 0) {
            return false;
        }
        Object obj = this;
        while (obj instanceof View) {
            View view = (View) obj;
            if (view.getAlpha() <= 0.0f || view.getTransitionAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            obj = view.mParent;
        }
        Rect rect2 = this.mAttachInfo.mTmpInvalRect;
        Point point = this.mAttachInfo.mPoint;
        if (!getGlobalVisibleRect(rect2, point)) {
            return false;
        }
        if (rect == null) {
            return true;
        }
        rect2.offset(-point.x, -point.y);
        return rect.intersect(rect2);
    }

    public AccessibilityDelegate getAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegate(AccessibilityDelegate accessibilityDelegate) {
        this.mAccessibilityDelegate = accessibilityDelegate;
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            return accessibilityDelegate.getAccessibilityNodeProvider(this);
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
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mAccessibilityWindowId;
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

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE)
    public CharSequence getSupplementalDescription() {
        return this.mSupplementalDescription;
    }

    @RemotableViewMethod
    public void setStateDescription(CharSequence charSequence) {
        CharSequence charSequence2 = this.mStateDescription;
        if (charSequence2 == null) {
            if (charSequence == null) {
                return;
            }
        } else if (charSequence2.equals(charSequence)) {
            return;
        }
        this.mStateDescription = charSequence;
        if (!TextUtils.isEmpty(charSequence) && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(64);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    @RemotableViewMethod
    public void setContentDescription(CharSequence charSequence) {
        CharSequence charSequence2 = this.mContentDescription;
        if (charSequence2 == null) {
            if (charSequence == null) {
                return;
            }
        } else if (charSequence2.equals(charSequence)) {
            return;
        }
        if (isHoveringUIEnabled() && this.mHoverPopupType == 1) {
            semSetTooltipText(charSequence);
        }
        this.mContentDescription = charSequence;
        if (charSequence != null && charSequence.length() > 0 && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
            notifySubtreeAccessibilityStateChangedIfNeeded();
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(4);
        }
    }

    @RemotableViewMethod
    public void setSupplementalDescription(CharSequence charSequence) {
        CharSequence charSequence2 = this.mSupplementalDescription;
        if (charSequence2 == null) {
            if (charSequence == null) {
                return;
            }
        } else if (charSequence2.equals(charSequence)) {
            return;
        }
        this.mSupplementalDescription = charSequence;
        if (charSequence != null && !charSequence.isEmpty() && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
            notifySubtreeAccessibilityStateChangedIfNeeded();
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(32768);
        }
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalBefore(int i) {
        if (this.mAccessibilityTraversalBeforeId == i) {
            return;
        }
        this.mAccessibilityTraversalBeforeId = i;
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public int getAccessibilityTraversalBefore() {
        return this.mAccessibilityTraversalBeforeId;
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalAfter(int i) {
        if (this.mAccessibilityTraversalAfterId == i) {
            return;
        }
        this.mAccessibilityTraversalAfterId = i;
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
    public void setLabelFor(int i) {
        if (this.mLabelForId == i) {
            return;
        }
        this.mLabelForId = i;
        if (i != -1 && this.mID == -1) {
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
            if (this.mHasPerformedLongPress) {
                return;
            }
            removeLongPressCallback();
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

    public void setScrollContainer(boolean z) {
        if (z) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null && (this.mPrivateFlags & 1048576) == 0) {
                attachInfo.mScrollContainers.add(this);
                this.mPrivateFlags |= 1048576;
            }
            this.mPrivateFlags |= 524288;
            return;
        }
        if ((this.mPrivateFlags & 1048576) != 0) {
            this.mAttachInfo.mScrollContainers.remove(this);
        }
        this.mPrivateFlags &= -1572865;
    }

    @Deprecated
    public int getDrawingCacheQuality() {
        return this.mViewFlags & 1572864;
    }

    @Deprecated
    public void setDrawingCacheQuality(int i) {
        setFlags(i, 1572864);
    }

    public boolean getKeepScreenOn() {
        return (this.mViewFlags & 67108864) != 0;
    }

    public void setKeepScreenOn(boolean z) {
        setFlags(z ? 67108864 : 0, 67108864);
    }

    public int getNextFocusLeftId() {
        return this.mNextFocusLeftId;
    }

    public void setNextFocusLeftId(int i) {
        this.mNextFocusLeftId = i;
    }

    public int getNextFocusRightId() {
        return this.mNextFocusRightId;
    }

    public void setNextFocusRightId(int i) {
        this.mNextFocusRightId = i;
    }

    public int getNextFocusUpId() {
        return this.mNextFocusUpId;
    }

    public void setNextFocusUpId(int i) {
        this.mNextFocusUpId = i;
    }

    public int getNextFocusDownId() {
        return this.mNextFocusDownId;
    }

    public void setNextFocusDownId(int i) {
        this.mNextFocusDownId = i;
    }

    public int getNextFocusForwardId() {
        return this.mNextFocusForwardId;
    }

    public void setNextFocusForwardId(int i) {
        this.mNextFocusForwardId = i;
    }

    public int getNextClusterForwardId() {
        return this.mNextClusterForwardId;
    }

    public void setNextClusterForwardId(int i) {
        this.mNextClusterForwardId = i;
    }

    public boolean isShown() {
        Object obj;
        while ((this.mViewFlags & 12) == 0 && (obj = this.mParent) != null) {
            if (!(obj instanceof View)) {
                return true;
            }
            this = (View) obj;
            if (this == null) {
                return false;
            }
        }
        return false;
    }

    private boolean detached() {
        while ((this.mPrivateFlags4 & 8192) == 0) {
            Object obj = this.mParent;
            if (obj == null || !(obj instanceof View) || (this = (View) obj) == null) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    protected boolean fitSystemWindows(Rect rect) {
        int i = this.mPrivateFlags3;
        if ((i & 32) != 0) {
            return fitSystemWindowsInt(rect);
        }
        if (rect == null) {
            return false;
        }
        try {
            this.mPrivateFlags3 = i | 64;
            return dispatchApplyWindowInsets(new WindowInsets(rect)).isConsumed();
        } finally {
            this.mPrivateFlags3 &= -65;
        }
    }

    private boolean fitSystemWindowsInt(Rect rect) {
        if ((this.mViewFlags & 2) != 2) {
            return false;
        }
        Rect rect2 = sThreadLocal.get();
        boolean computeFitSystemWindows = computeFitSystemWindows(rect, rect2);
        applyInsets(rect2);
        return computeFitSystemWindows;
    }

    private void applyInsets(Rect rect) {
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mUserPaddingLeftInitial = rect.left;
        this.mUserPaddingRightInitial = rect.right;
        internalSetPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if ((this.mPrivateFlags4 & 256) != 0 && (this.mViewFlags & 2) != 0) {
            return onApplyFrameworkOptionalFitSystemWindows(windowInsets);
        }
        if ((this.mPrivateFlags3 & 64) == 0) {
            if (fitSystemWindows(windowInsets.getSystemWindowInsetsAsRect())) {
                return windowInsets.consumeSystemWindowInsets();
            }
        } else if (fitSystemWindowsInt(windowInsets.getSystemWindowInsetsAsRect())) {
            return windowInsets.consumeSystemWindowInsets();
        }
        return windowInsets;
    }

    private WindowInsets onApplyFrameworkOptionalFitSystemWindows(WindowInsets windowInsets) {
        Rect rect = sThreadLocal.get();
        WindowInsets computeSystemWindowInsets = computeSystemWindowInsets(windowInsets, rect);
        applyInsets(rect);
        return computeSystemWindowInsets;
    }

    public void setOnApplyWindowInsetsListener(OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        getListenerInfo().mOnApplyWindowInsetsListener = onApplyWindowInsetsListener;
    }

    public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsets onApplyWindowInsets;
        try {
            this.mPrivateFlags3 |= 32;
            ListenerInfo listenerInfo = this.mListenerInfo;
            if (listenerInfo != null && listenerInfo.mOnApplyWindowInsetsListener != null) {
                onApplyWindowInsets = this.mListenerInfo.mOnApplyWindowInsetsListener.onApplyWindowInsets(this, windowInsets);
            } else {
                onApplyWindowInsets = onApplyWindowInsets(windowInsets);
            }
            return onApplyWindowInsets;
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

    public void dispatchWindowInsetsAnimationPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mWindowInsetsAnimationCallback == null) {
            return;
        }
        this.mListenerInfo.mWindowInsetsAnimationCallback.onPrepare(windowInsetsAnimation);
    }

    public WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        return (listenerInfo == null || listenerInfo.mWindowInsetsAnimationCallback == null) ? bounds : this.mListenerInfo.mWindowInsetsAnimationCallback.onStart(windowInsetsAnimation, bounds);
    }

    public WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        return (listenerInfo == null || listenerInfo.mWindowInsetsAnimationCallback == null) ? windowInsets : this.mListenerInfo.mWindowInsetsAnimationCallback.onProgress(windowInsets, list);
    }

    public void dispatchWindowInsetsAnimationEnd(WindowInsetsAnimation windowInsetsAnimation) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mWindowInsetsAnimationCallback == null) {
            return;
        }
        this.mListenerInfo.mWindowInsetsAnimationCallback.onEnd(windowInsetsAnimation);
    }

    public void setSystemGestureExclusionRects(List<Rect> list) {
        if (list.isEmpty() && this.mListenerInfo == null) {
            return;
        }
        ListenerInfo listenerInfo = getListenerInfo();
        boolean z = (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.reduceChangedExclusionRectsMsgs() && Objects.deepEquals(listenerInfo.mSystemGestureExclusionRects, list)) ? false : true;
        if (listenerInfo.mSystemGestureExclusionRects == null) {
            listenerInfo.mSystemGestureExclusionRects = new ArrayList();
        }
        if (z) {
            deepCopyRectsObjectRecycling(listenerInfo.mSystemGestureExclusionRects, list);
            updatePositionUpdateListener();
            postUpdate(new View$$ExternalSyntheticLambda5(this));
        }
    }

    private void deepCopyRectsObjectRecycling(ArrayList<Rect> arrayList, List<Rect> list) {
        arrayList.ensureCapacity(list.size());
        for (int i = 0; i < list.size(); i++) {
            if (i < arrayList.size()) {
                arrayList.get(i).set(list.get(i));
            } else {
                arrayList.add(Rect.copyOrNull(list.get(i)));
            }
        }
        while (arrayList.size() > list.size()) {
            arrayList.removeLast();
        }
    }

    private void updatePositionUpdateListener() {
        final ListenerInfo listenerInfo = getListenerInfo();
        if (getSystemGestureExclusionRects().isEmpty() && collectPreferKeepClearRects().isEmpty() && collectUnrestrictedPreferKeepClearRects().isEmpty() && (listenerInfo.mHandwritingArea == null || !shouldTrackHandwritingArea())) {
            if (listenerInfo.mPositionUpdateListener != null) {
                this.mRenderNode.removePositionUpdateListener(listenerInfo.mPositionUpdateListener);
                listenerInfo.mPositionUpdateListener = null;
                listenerInfo.mPositionChangedUpdate = null;
                return;
            }
            return;
        }
        if (listenerInfo.mPositionUpdateListener == null) {
            listenerInfo.mPositionChangedUpdate = new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    View.this.lambda$updatePositionUpdateListener$2();
                }
            };
            listenerInfo.mPositionUpdateListener = new RenderNode.PositionUpdateListener() { // from class: android.view.View.1
                @Override // android.graphics.RenderNode.PositionUpdateListener
                public void positionChanged(long j, int i, int i2, int i3, int i4) {
                    View.this.postUpdate(listenerInfo.mPositionChangedUpdate);
                }

                @Override // android.graphics.RenderNode.PositionUpdateListener
                public void positionLost(long j) {
                    View.this.postUpdate(listenerInfo.mPositionChangedUpdate);
                }
            };
            this.mRenderNode.addPositionUpdateListener(listenerInfo.mPositionUpdateListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePositionUpdateListener$2() {
        updateSystemGestureExclusionRects();
        updateKeepClearRects();
        updateHandwritingArea();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postUpdate(Runnable runnable) {
        Handler handler = getHandler();
        if (handler != null) {
            handler.postAtFrontOfQueue(runnable);
        }
    }

    void updateSystemGestureExclusionRects() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.updateSystemGestureExclusionRectsForView(this);
        }
    }

    public List<Rect> getSystemGestureExclusionRects() {
        ArrayList arrayList;
        ListenerInfo listenerInfo = this.mListenerInfo;
        return (listenerInfo == null || (arrayList = listenerInfo.mSystemGestureExclusionRects) == null) ? Collections.EMPTY_LIST : arrayList;
    }

    public final void setPreferKeepClear(boolean z) {
        getListenerInfo().mPreferKeepClear = z;
        updatePositionUpdateListener();
        postUpdate(new View$$ExternalSyntheticLambda6(this));
    }

    public final boolean isPreferKeepClear() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        return listenerInfo != null && listenerInfo.mPreferKeepClear;
    }

    public final void setPreferKeepClearRects(List<Rect> list) {
        ListenerInfo listenerInfo = getListenerInfo();
        boolean z = (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.reduceChangedExclusionRectsMsgs() && Objects.deepEquals(listenerInfo.mKeepClearRects, list)) ? false : true;
        if (listenerInfo.mKeepClearRects == null) {
            listenerInfo.mKeepClearRects = new ArrayList();
        }
        if (z) {
            deepCopyRectsObjectRecycling(listenerInfo.mKeepClearRects, list);
            updatePositionUpdateListener();
            postUpdate(new View$$ExternalSyntheticLambda6(this));
        }
    }

    public final List<Rect> getPreferKeepClearRects() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mKeepClearRects != null) {
            return new ArrayList(listenerInfo.mKeepClearRects);
        }
        return Collections.EMPTY_LIST;
    }

    @SystemApi
    public final void setUnrestrictedPreferKeepClearRects(List<Rect> list) {
        ListenerInfo listenerInfo = getListenerInfo();
        boolean z = (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.reduceChangedExclusionRectsMsgs() && Objects.deepEquals(listenerInfo.mUnrestrictedKeepClearRects, list)) ? false : true;
        if (listenerInfo.mUnrestrictedKeepClearRects == null) {
            listenerInfo.mUnrestrictedKeepClearRects = new ArrayList();
        }
        if (z) {
            deepCopyRectsObjectRecycling(listenerInfo.mUnrestrictedKeepClearRects, list);
            updatePositionUpdateListener();
            postUpdate(new View$$ExternalSyntheticLambda6(this));
        }
    }

    @SystemApi
    public final List<Rect> getUnrestrictedPreferKeepClearRects() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mUnrestrictedKeepClearRects != null) {
            return new ArrayList(listenerInfo.mUnrestrictedKeepClearRects);
        }
        return Collections.EMPTY_LIST;
    }

    void updateKeepClearRects() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.updateKeepClearRectsForView(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<Rect> collectPreferKeepClearRects() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean z = (listenerInfo != null && listenerInfo.mPreferKeepClear) || (isFocused() && ViewConfiguration.get(this.mContext).isPreferKeepClearForFocusEnabled());
        boolean z2 = (listenerInfo == null || listenerInfo.mKeepClearRects == null) ? false : true;
        if (!z && !z2) {
            return Collections.EMPTY_LIST;
        }
        if (z && !z2) {
            return Collections.singletonList(new Rect(0, 0, getWidth(), getHeight()));
        }
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.add(new Rect(0, 0, getWidth(), getHeight()));
        }
        if (z2) {
            arrayList.addAll(listenerInfo.mKeepClearRects);
        }
        return arrayList;
    }

    private void updatePreferKeepClearForFocus() {
        if (ViewConfiguration.get(this.mContext).isPreferKeepClearForFocusEnabled()) {
            updatePositionUpdateListener();
            post(new View$$ExternalSyntheticLambda6(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<Rect> collectUnrestrictedPreferKeepClearRects() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mUnrestrictedKeepClearRects != null) {
            return listenerInfo.mUnrestrictedKeepClearRects;
        }
        return Collections.EMPTY_LIST;
    }

    public void setHandwritingBoundsOffsets(float f, float f2, float f3, float f4) {
        this.mHandwritingBoundsOffsetLeft = f;
        this.mHandwritingBoundsOffsetTop = f2;
        this.mHandwritingBoundsOffsetRight = f3;
        this.mHandwritingBoundsOffsetBottom = f4;
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
        getListenerInfo().mHandwritingArea = rect;
        updatePositionUpdateListener();
        postUpdate(new View$$ExternalSyntheticLambda0(this));
    }

    public Rect getHandwritingArea() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mHandwritingArea == null) {
            return null;
        }
        return new Rect(listenerInfo.mHandwritingArea);
    }

    void updateHandwritingArea() {
        AttachInfo attachInfo;
        if (shouldTrackHandwritingArea() && (attachInfo = this.mAttachInfo) != null) {
            attachInfo.mViewRootImpl.getHandwritingInitiator().updateHandwritingAreasForView(this);
        }
    }

    boolean shouldInitiateHandwriting() {
        return isAutoHandwritingEnabled() || getHandwritingDelegatorCallback() != null;
    }

    public boolean shouldTrackHandwritingArea() {
        return shouldInitiateHandwriting();
    }

    public void setHandwritingDelegatorCallback(Runnable runnable) {
        this.mHandwritingDelegatorCallback = runnable;
        if (runnable != null) {
            setHandwritingArea(new Rect(0, 0, getWidth(), getHeight()));
        }
    }

    public Runnable getHandwritingDelegatorCallback() {
        return this.mHandwritingDelegatorCallback;
    }

    public void setAllowedHandwritingDelegatePackage(String str) {
        this.mAllowedHandwritingDelegatePackageName = str;
    }

    public String getAllowedHandwritingDelegatePackageName() {
        return this.mAllowedHandwritingDelegatePackageName;
    }

    public void setIsHandwritingDelegate(boolean z) {
        this.mIsHandwritingDelegate = z;
    }

    public boolean isHandwritingDelegate() {
        return this.mIsHandwritingDelegate;
    }

    public void setAllowedHandwritingDelegatorPackage(String str) {
        this.mAllowedHandwritingDelegatorPackageName = str;
    }

    public String getAllowedHandwritingDelegatorPackageName() {
        return this.mAllowedHandwritingDelegatorPackageName;
    }

    public void setHandwritingDelegateFlags(int i) {
        this.mHandwritingDelegateFlags = i;
    }

    public int getHandwritingDelegateFlags() {
        return this.mHandwritingDelegateFlags;
    }

    public void getLocationInSurface(int[] iArr) {
        getLocationInWindow(iArr);
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null || attachInfo.mViewRootImpl == null) {
            return;
        }
        iArr[0] = iArr[0] + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.left;
        iArr[1] = iArr[1] + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.top;
    }

    public WindowInsets getRootWindowInsets() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mViewRootImpl.getWindowInsets(false);
        }
        return null;
    }

    public WindowInsetsController getWindowInsetsController() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mViewRootImpl.getInsetsController();
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
    protected boolean computeFitSystemWindows(Rect rect, Rect rect2) {
        WindowInsets computeSystemWindowInsets = computeSystemWindowInsets(new WindowInsets(rect), rect2);
        rect.set(computeSystemWindowInsets.getSystemWindowInsetsAsRect());
        return computeSystemWindowInsets.isSystemWindowInsetsConsumed();
    }

    public WindowInsets computeSystemWindowInsets(WindowInsets windowInsets, Rect rect) {
        AttachInfo attachInfo;
        if (((this.mViewFlags & 2048) != 0 || (this.mPrivateFlags4 & 256) != 0) && (attachInfo = this.mAttachInfo) != null) {
            Window.OnContentApplyWindowInsetsListener onContentApplyWindowInsetsListener = attachInfo.mContentOnApplyWindowInsetsListener;
            if (onContentApplyWindowInsetsListener == null) {
                rect.setEmpty();
                return windowInsets;
            }
            Pair<Insets, WindowInsets> onContentApplyWindowInsets = onContentApplyWindowInsetsListener.onContentApplyWindowInsets(this, windowInsets);
            rect.set(onContentApplyWindowInsets.first.toRect());
            return onContentApplyWindowInsets.second;
        }
        rect.set(windowInsets.getSystemWindowInsetsAsRect());
        return windowInsets.consumeSystemWindowInsets().inset(rect);
    }

    protected boolean hasContentOnApplyWindowInsetsListener() {
        AttachInfo attachInfo = this.mAttachInfo;
        return (attachInfo == null || attachInfo.mContentOnApplyWindowInsetsListener == null) ? false : true;
    }

    public void setFitsSystemWindows(boolean z) {
        setFlags(z ? 2 : 0, 2);
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
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.requestFitSystemWindows();
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

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, to = "VISIBLE"), @ViewDebug.IntToString(from = 4, to = "INVISIBLE"), @ViewDebug.IntToString(from = 8, to = "GONE")})
    public int getVisibility() {
        return this.mViewFlags & 12;
    }

    @RemotableViewMethod
    public void setVisibility(int i) {
        setFlags(i, 12);
    }

    @ViewDebug.ExportedProperty
    public boolean isEnabled() {
        return (this.mViewFlags & 32) == 0;
    }

    @RemotableViewMethod
    public void setEnabled(boolean z) {
        if (z == isEnabled()) {
            return;
        }
        setFlags(z ? 0 : 32, 32);
        refreshDrawableState();
        invalidate(true);
        if (!z) {
            cancelPendingInputEvents();
        }
        notifyViewAccessibilityStateChangedIfNeeded(4096);
    }

    @RemotableViewMethod
    public void setFocusable(boolean z) {
        setFocusable(z ? 1 : 0);
    }

    @RemotableViewMethod
    public void setFocusable(int i) {
        if ((i & 17) == 0) {
            setFlags(0, 262144);
        }
        setFlags(i, 17);
    }

    @RemotableViewMethod
    public void setFocusableInTouchMode(boolean z) {
        setFlags(z ? 262144 : 0, 262144);
        if (z) {
            setFlags(1, 17);
        }
    }

    public void setAutofillHints(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            this.mAutofillHints = null;
        } else {
            this.mAutofillHints = strArr;
        }
        if (Flags.sensitiveContentAppProtection() && getContentSensitivity() == 0) {
            updateSensitiveViewsCountIfNeeded(isAggregatedVisible());
        }
    }

    public void setAutofilled(boolean z, boolean z2) {
        if (z != isAutofilled()) {
            if (z) {
                this.mPrivateFlags3 |= 65536;
            } else {
                this.mPrivateFlags3 &= -65537;
            }
            if (z2) {
                this.mPrivateFlags4 |= 512;
            } else {
                this.mPrivateFlags4 &= -513;
            }
            invalidate();
        }
    }

    public void setSoundEffectsEnabled(boolean z) {
        setFlags(z ? 134217728 : 0, 134217728);
    }

    @ViewDebug.ExportedProperty
    public boolean isSoundEffectsEnabled() {
        return 134217728 == (this.mViewFlags & 134217728);
    }

    public void setHapticFeedbackEnabled(boolean z) {
        setFlags(z ? 268435456 : 0, 268435456);
    }

    @ViewDebug.ExportedProperty
    public boolean isHapticFeedbackEnabled() {
        return 268435456 == (this.mViewFlags & 268435456);
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = 0, to = "LTR"), @ViewDebug.IntToString(from = 1, to = "RTL"), @ViewDebug.IntToString(from = 2, to = "INHERIT"), @ViewDebug.IntToString(from = 3, to = "LOCALE")})
    public int getRawLayoutDirection() {
        return (this.mPrivateFlags2 & 12) >> 2;
    }

    @RemotableViewMethod
    public void setLayoutDirection(int i) {
        if (getRawLayoutDirection() != i) {
            this.mPrivateFlags2 &= -13;
            resetRtlProperties();
            this.mPrivateFlags2 = ((i << 2) & 12) | this.mPrivateFlags2;
            resolveRtlPropertiesIfNeeded();
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = 0, to = "RESOLVED_DIRECTION_LTR"), @ViewDebug.IntToString(from = 1, to = "RESOLVED_DIRECTION_RTL")})
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

    public void setHasTransientState(boolean z) {
        int i;
        boolean hasTransientState = hasTransientState();
        if (z) {
            i = this.mTransientStateCount + 1;
        } else {
            i = this.mTransientStateCount - 1;
        }
        this.mTransientStateCount = i;
        if (i < 0) {
            this.mTransientStateCount = 0;
            Log.e(VIEW_LOG_TAG, "hasTransientState decremented below 0: unmatched pair of setHasTransientState calls");
            return;
        }
        if (!(z && i == 1) && (z || i != 0)) {
            return;
        }
        this.mPrivateFlags2 = (this.mPrivateFlags2 & Integer.MAX_VALUE) | (z ? Integer.MIN_VALUE : 0);
        boolean hasTransientState2 = hasTransientState();
        ViewParent viewParent = this.mParent;
        if (viewParent == null || hasTransientState2 == hasTransientState) {
            return;
        }
        try {
            viewParent.childHasTransientStateChanged(this, hasTransientState2);
        } catch (AbstractMethodError e) {
            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
        }
    }

    public void setHasTranslationTransientState(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 16384;
        } else {
            this.mPrivateFlags4 &= -16385;
        }
    }

    public boolean hasTranslationTransientState() {
        return (this.mPrivateFlags4 & 16384) == 16384;
    }

    public void clearTranslationState() {
        ViewTranslationCallback viewTranslationCallback = this.mViewTranslationCallback;
        if (viewTranslationCallback != null) {
            viewTranslationCallback.onClearTranslation(this);
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

    public void setWillNotDraw(boolean z) {
        setFlags(z ? 128 : 0, 128);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean willNotDraw() {
        return (this.mViewFlags & 128) == 128;
    }

    @Deprecated
    public void setWillNotCacheDrawing(boolean z) {
        setFlags(z ? 131072 : 0, 131072);
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

    public void setClickable(boolean z) {
        setFlags(z ? 16384 : 0, 16384);
    }

    public void setAllowClickWhenDisabled(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 4096;
        } else {
            this.mPrivateFlags4 &= -4097;
        }
    }

    public boolean isLongClickable() {
        return (this.mViewFlags & 2097152) == 2097152;
    }

    public void setLongClickable(boolean z) {
        setFlags(z ? 2097152 : 0, 2097152);
    }

    public boolean isContextClickable() {
        return (this.mViewFlags & 8388608) == 8388608;
    }

    public void setContextClickable(boolean z) {
        setFlags(z ? 8388608 : 0, 8388608);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPressed(boolean z, float f, float f2) {
        if (z) {
            drawableHotspotChanged(f, f2);
        }
        setPressed(z);
    }

    public void setPressed(boolean z) {
        int i = this.mPrivateFlags;
        boolean z2 = z != ((i & 16384) == 16384);
        if (z) {
            this.mPrivateFlags = i | 16384;
        } else {
            this.mPrivateFlags = i & (-16385);
        }
        if (z2) {
            refreshDrawableState();
        }
        dispatchSetPressed(z);
    }

    @ViewDebug.ExportedProperty
    public boolean isPressed() {
        return (this.mPrivateFlags & 16384) == 16384;
    }

    public boolean isAssistBlocked() {
        return (this.mPrivateFlags3 & 16384) != 0;
    }

    public void setAssistBlocked(boolean z) {
        if (z) {
            this.mPrivateFlags3 |= 16384;
        } else {
            this.mPrivateFlags3 &= -16385;
        }
    }

    public boolean isSaveEnabled() {
        return (this.mViewFlags & 65536) != 65536;
    }

    public void setSaveEnabled(boolean z) {
        setFlags(z ? 0 : 65536, 65536);
    }

    @ViewDebug.ExportedProperty
    public boolean getFilterTouchesWhenObscured() {
        return (this.mViewFlags & 1024) != 0;
    }

    public void setFilterTouchesWhenObscured(boolean z) {
        setFlags(z ? 1024 : 0, 1024);
        calculateAccessibilityDataSensitive();
    }

    public boolean isSaveFromParentEnabled() {
        return (this.mViewFlags & 536870912) != 536870912;
    }

    public void setSaveFromParentEnabled(boolean z) {
        setFlags(z ? 0 : 536870912, 536870912);
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusable() {
        return 1 == (this.mViewFlags & 1);
    }

    @ViewDebug.ExportedProperty(category = "focus", mapping = {@ViewDebug.IntToString(from = 0, to = "NOT_FOCUSABLE"), @ViewDebug.IntToString(from = 1, to = "FOCUSABLE"), @ViewDebug.IntToString(from = 16, to = "FOCUSABLE_AUTO")})
    public int getFocusable() {
        int i = this.mViewFlags;
        if ((i & 16) > 0) {
            return 16;
        }
        return i & 1;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusableInTouchMode() {
        return 262144 == (this.mViewFlags & 262144);
    }

    public boolean isScreenReaderFocusable() {
        return (this.mPrivateFlags3 & 268435456) != 0;
    }

    public void setScreenReaderFocusable(boolean z) {
        updatePflags3AndNotifyA11yIfChanged(268435456, z);
    }

    public boolean isAccessibilityHeading() {
        return (this.mPrivateFlags3 & Integer.MIN_VALUE) != 0;
    }

    public void setAccessibilityHeading(boolean z) {
        updatePflags3AndNotifyA11yIfChanged(Integer.MIN_VALUE, z);
    }

    private void updatePflags3AndNotifyA11yIfChanged(int i, boolean z) {
        int i2 = this.mPrivateFlags3;
        int i3 = z ? i | i2 : (~i) & i2;
        if (i3 != i2) {
            this.mPrivateFlags3 = i3;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public View focusSearch(int i) {
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            return viewParent.focusSearch(this, i);
        }
        return null;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isKeyboardNavigationCluster() {
        return (this.mPrivateFlags3 & 32768) != 0;
    }

    View findKeyboardNavigationCluster() {
        Object obj = this.mParent;
        if (!(obj instanceof View)) {
            return null;
        }
        View findKeyboardNavigationCluster = ((View) obj).findKeyboardNavigationCluster();
        if (findKeyboardNavigationCluster != null) {
            return findKeyboardNavigationCluster;
        }
        if (isKeyboardNavigationCluster()) {
            return this;
        }
        return null;
    }

    public void setKeyboardNavigationCluster(boolean z) {
        if (z) {
            this.mPrivateFlags3 |= 32768;
        } else {
            this.mPrivateFlags3 &= -32769;
        }
    }

    public final void setFocusedInCluster() {
        setFocusedInCluster(findKeyboardNavigationCluster());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    private void setFocusedInCluster(View view) {
        if (this instanceof ViewGroup) {
            ((ViewGroup) this).mFocusedInCluster = null;
        }
        if (view == this) {
            return;
        }
        for (ViewGroup viewGroup = this.mParent; viewGroup instanceof ViewGroup; viewGroup = viewGroup.getParent()) {
            viewGroup.mFocusedInCluster = this;
            if (viewGroup == view) {
                return;
            }
            this = viewGroup;
        }
    }

    private void updateFocusedInCluster(View view, int i) {
        View findKeyboardNavigationCluster;
        if (view == null || (findKeyboardNavigationCluster = view.findKeyboardNavigationCluster()) == findKeyboardNavigationCluster()) {
            return;
        }
        view.setFocusedInCluster(findKeyboardNavigationCluster);
        ViewParent viewParent = view.mParent;
        if (viewParent instanceof ViewGroup) {
            if (i == 2 || i == 1) {
                ((ViewGroup) viewParent).clearFocusedInCluster(view);
            } else if ((view instanceof ViewGroup) && ((ViewGroup) view).getDescendantFocusability() == 262144 && ViewRootImpl.isViewDescendantOf(this, view)) {
                ((ViewGroup) view.mParent).clearFocusedInCluster(view);
            }
        }
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusedByDefault() {
        return (this.mPrivateFlags3 & 262144) != 0;
    }

    @RemotableViewMethod
    public void setFocusedByDefault(boolean z) {
        int i = this.mPrivateFlags3;
        if (z == ((i & 262144) != 0)) {
            return;
        }
        if (z) {
            this.mPrivateFlags3 = i | 262144;
        } else {
            this.mPrivateFlags3 = i & (-262145);
        }
        ViewParent viewParent = this.mParent;
        if (viewParent instanceof ViewGroup) {
            if (z) {
                ((ViewGroup) viewParent).setDefaultFocus(this);
            } else {
                ((ViewGroup) viewParent).clearDefaultFocus(this);
            }
        }
    }

    boolean hasDefaultFocus() {
        return isFocusedByDefault();
    }

    public View keyboardNavigationClusterSearch(View view, int i) {
        if (isKeyboardNavigationCluster()) {
            view = this;
        }
        if (isRootNamespace()) {
            return FocusFinder.getInstance().findNextKeyboardNavigationCluster(this, view, i);
        }
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            return viewParent.keyboardNavigationClusterSearch(view, i);
        }
        return null;
    }

    public void setDefaultFocusHighlightEnabled(boolean z) {
        this.mDefaultFocusHighlightEnabled = z;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean getDefaultFocusHighlightEnabled() {
        return this.mDefaultFocusHighlightEnabled;
    }

    View findUserSetNextFocus(final View view, int i) {
        int i2;
        if (i == 1) {
            if (this.mID == -1) {
                return null;
            }
            return view.findViewByPredicateInsideOut(this, new Predicate() { // from class: android.view.View$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$findUserSetNextFocus$3;
                    lambda$findUserSetNextFocus$3 = View.this.lambda$findUserSetNextFocus$3(view, this, (View) obj);
                    return lambda$findUserSetNextFocus$3;
                }
            });
        }
        if (i == 2) {
            int i3 = this.mNextFocusForwardId;
            if (i3 == -1) {
                return null;
            }
            return findViewInsideOutShouldExist(view, i3);
        }
        if (i == 17) {
            int i4 = this.mNextFocusLeftId;
            if (i4 == -1) {
                return null;
            }
            return findViewInsideOutShouldExist(view, i4);
        }
        if (i == 33) {
            int i5 = this.mNextFocusUpId;
            if (i5 == -1) {
                return null;
            }
            return findViewInsideOutShouldExist(view, i5);
        }
        if (i != 66) {
            if (i == 130 && (i2 = this.mNextFocusDownId) != -1) {
                return findViewInsideOutShouldExist(view, i2);
            }
            return null;
        }
        int i6 = this.mNextFocusRightId;
        if (i6 == -1) {
            return null;
        }
        return findViewInsideOutShouldExist(view, i6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$findUserSetNextFocus$3(View view, View view2, View view3) {
        return findViewInsideOutShouldExist(view, view3, view3.mNextFocusForwardId) == view2;
    }

    View findUserSetNextKeyboardNavigationCluster(View view, int i) {
        int i2;
        if (i != 1) {
            if (i == 2 && (i2 = this.mNextClusterForwardId) != -1) {
                return findViewInsideOutShouldExist(view, i2);
            }
            return null;
        }
        final int i3 = this.mID;
        if (i3 == -1) {
            return null;
        }
        return view.findViewByPredicateInsideOut(this, new Predicate() { // from class: android.view.View$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return View.lambda$findUserSetNextKeyboardNavigationCluster$4(i3, (View) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$findUserSetNextKeyboardNavigationCluster$4(int i, View view) {
        return view.mNextClusterForwardId == i;
    }

    private View findViewInsideOutShouldExist(View view, int i) {
        return findViewInsideOutShouldExist(view, this, i);
    }

    private View findViewInsideOutShouldExist(View view, View view2, int i) {
        if (this.mMatchIdPredicate == null) {
            this.mMatchIdPredicate = new MatchIdPredicate();
        }
        this.mMatchIdPredicate.mId = i;
        View findViewByPredicateInsideOut = view.findViewByPredicateInsideOut(view2, this.mMatchIdPredicate);
        if (findViewByPredicateInsideOut == null) {
            Log.w(VIEW_LOG_TAG, "couldn't find view with id " + i);
        }
        return findViewByPredicateInsideOut;
    }

    public ArrayList<View> getFocusables(int i) {
        ArrayList<View> arrayList = new ArrayList<>(24);
        addFocusables(arrayList, i);
        return arrayList;
    }

    public void addFocusables(ArrayList<View> arrayList, int i) {
        addFocusables(arrayList, i, isInTouchMode() ? 1 : 0);
    }

    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        if (arrayList != null && canTakeFocus()) {
            if ((i2 & 1) != 1 || isFocusableInTouchMode()) {
                arrayList.add(this);
            }
        }
    }

    public void addKeyboardNavigationClusters(Collection<View> collection, int i) {
        if (isKeyboardNavigationCluster() && hasFocusable()) {
            collection.add(this);
        }
    }

    public void findViewsWithText(ArrayList<View> arrayList, CharSequence charSequence, int i) {
        CharSequence charSequence2;
        if (getAccessibilityNodeProvider() != null) {
            if ((i & 4) != 0) {
                arrayList.add(this);
            }
        } else {
            if ((i & 2) == 0 || charSequence == null || charSequence.length() <= 0 || (charSequence2 = this.mContentDescription) == null || charSequence2.length() <= 0) {
                return;
            }
            if (this.mContentDescription.toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                arrayList.add(this);
            }
        }
    }

    public ArrayList<View> getTouchables() {
        ArrayList<View> arrayList = new ArrayList<>();
        addTouchables(arrayList);
        return arrayList;
    }

    public void addTouchables(ArrayList<View> arrayList) {
        int i = this.mViewFlags;
        if (((i & 16384) == 16384 || (i & 2097152) == 2097152 || (i & 8388608) == 8388608) && (i & 32) == 0) {
            arrayList.add(this);
        }
    }

    public boolean isAccessibilityFocused() {
        return (this.mPrivateFlags2 & 67108864) != 0;
    }

    public boolean requestAccessibilityFocus() {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
        if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled() || (this.mViewFlags & 12) != 0) {
            return false;
        }
        int i = this.mPrivateFlags2;
        if ((i & 67108864) == 0) {
            this.mPrivateFlags2 = i | 67108864;
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null) {
                viewRootImpl.setAccessibilityFocus(this, null);
            }
            invalidate();
            sendAccessibilityEvent(32768);
            return true;
        }
        return false;
    }

    public boolean semRequestAccessibilityFocus() {
        return requestAccessibilityFocus();
    }

    public void clearAccessibilityFocus() {
        View accessibilityFocusedHost;
        clearAccessibilityFocusNoCallbacks(0);
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null || (accessibilityFocusedHost = viewRootImpl.getAccessibilityFocusedHost()) == null || !ViewRootImpl.isViewDescendantOf(accessibilityFocusedHost, this)) {
            return;
        }
        viewRootImpl.setAccessibilityFocus(null, null);
    }

    public void semClearAccessibilityFocus() {
        clearAccessibilityFocus();
    }

    private void sendAccessibilityHoverEvent(int i) {
        while (!this.includeForAccessibility(false)) {
            Object parent = this.getParent();
            if (!(parent instanceof View)) {
                return;
            } else {
                this = (View) parent;
            }
        }
        this.sendAccessibilityEvent(i);
    }

    void clearAccessibilityFocusNoCallbacks(int i) {
        int i2 = this.mPrivateFlags2;
        if ((67108864 & i2) != 0) {
            this.mPrivateFlags2 = i2 & (-67108865);
            invalidate();
            if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(65536);
                obtain.setAction(i);
                AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
                if (accessibilityDelegate != null) {
                    accessibilityDelegate.sendAccessibilityEventUnchecked(this, obtain);
                } else {
                    sendAccessibilityEventUnchecked(obtain);
                }
            }
            updatePreferKeepClearForFocus();
        }
    }

    public final boolean requestFocus() {
        return requestFocus(130);
    }

    public boolean restoreFocusInCluster(int i) {
        if (restoreDefaultFocus()) {
            return true;
        }
        return requestFocus(i);
    }

    public boolean restoreFocusNotInCluster() {
        return requestFocus(130);
    }

    public boolean restoreDefaultFocus() {
        return requestFocus(130);
    }

    public final boolean requestFocus(int i) {
        return requestFocus(i, null);
    }

    public boolean requestFocus(int i, Rect rect) {
        return requestFocusNoSearch(i, rect);
    }

    private boolean requestFocusNoSearch(int i, Rect rect) {
        if (!canTakeFocus()) {
            return false;
        }
        if ((isInTouchMode() && 262144 != (this.mViewFlags & 262144)) || hasAncestorThatBlocksDescendantFocus()) {
            return false;
        }
        if (!isLayoutValid()) {
            this.mPrivateFlags |= 1;
        } else {
            clearParentsWantFocus();
        }
        handleFocusGainInternal(i, rect);
        return true;
    }

    void clearParentsWantFocus() {
        Object obj = this.mParent;
        if (obj instanceof View) {
            ((View) obj).mPrivateFlags &= -2;
            ((View) obj).clearParentsWantFocus();
        }
    }

    public final boolean requestFocusFromTouch() {
        ViewRootImpl viewRootImpl;
        if (isInTouchMode() && (viewRootImpl = getViewRootImpl()) != null) {
            viewRootImpl.ensureTouchMode(false);
        }
        return requestFocus(130);
    }

    private boolean hasAncestorThatBlocksDescendantFocus() {
        boolean isFocusableInTouchMode = isFocusableInTouchMode();
        ViewParent viewParent = this.mParent;
        while (viewParent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) viewParent;
            if (viewGroup.getDescendantFocusability() == 393216) {
                return true;
            }
            if (!isFocusableInTouchMode && viewGroup.shouldBlockFocusForTouchscreen()) {
                return true;
            }
            viewParent = viewGroup.getParent();
        }
        return false;
    }

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE, mapping = {@ViewDebug.IntToString(from = 0, to = "auto"), @ViewDebug.IntToString(from = 1, to = "yes"), @ViewDebug.IntToString(from = 2, to = "no"), @ViewDebug.IntToString(from = 4, to = "noHideDescendants")})
    public int getImportantForAccessibility() {
        return (this.mPrivateFlags2 & 7340032) >> 20;
    }

    public void setAccessibilityLiveRegion(int i) {
        if (i != getAccessibilityLiveRegion()) {
            this.mPrivateFlags2 = ((i << 23) & 25165824) | (this.mPrivateFlags2 & (-25165825));
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public int getAccessibilityLiveRegion() {
        return (this.mPrivateFlags2 & 25165824) >> 23;
    }

    public void setImportantForAccessibility(int i) {
        View findAccessibilityFocusHost;
        int importantForAccessibility = getImportantForAccessibility();
        if (i != importantForAccessibility) {
            boolean z = i == 4;
            if ((i == 2 || z) && (findAccessibilityFocusHost = findAccessibilityFocusHost(z)) != null) {
                findAccessibilityFocusHost.clearAccessibilityFocus();
            }
            boolean z2 = importantForAccessibility == 0 || i == 0;
            boolean z3 = z2 && includeForAccessibility(false);
            this.mPrivateFlags2 = ((i << 20) & 7340032) | (this.mPrivateFlags2 & (-7340033));
            if (!z2 || z3 != includeForAccessibility(false)) {
                notifySubtreeAccessibilityStateChangedIfNeeded();
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    private View findAccessibilityFocusHost(boolean z) {
        ViewRootImpl viewRootImpl;
        View accessibilityFocusedHost;
        if (isAccessibilityFocusedViewOrHost()) {
            return this;
        }
        if (!z || (viewRootImpl = getViewRootImpl()) == null || (accessibilityFocusedHost = viewRootImpl.getAccessibilityFocusedHost()) == null || !ViewRootImpl.isViewDescendantOf(accessibilityFocusedHost, this)) {
            return null;
        }
        return accessibilityFocusedHost;
    }

    public boolean isImportantForAccessibility() {
        int importantForAccessibility = getImportantForAccessibility();
        if (importantForAccessibility == 2 || importantForAccessibility == 4) {
            return false;
        }
        for (ViewParent viewParent = this.mParent; viewParent instanceof View; viewParent = viewParent.getParent()) {
            if (((View) viewParent).getImportantForAccessibility() == 4) {
                return false;
            }
        }
        return importantForAccessibility == 1 || isActionableForAccessibility() || hasListenersForAccessibility() || getAccessibilityNodeProvider() != null || getAccessibilityDelegate() != null || getAccessibilityLiveRegion() != 0 || isAccessibilityPane() || isAccessibilityHeading();
    }

    public ViewParent getParentForAccessibility() {
        Object obj = this.mParent;
        if (!(obj instanceof View)) {
            return null;
        }
        if (((View) obj).includeForAccessibility()) {
            return this.mParent;
        }
        return this.mParent.getParentForAccessibility();
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

    public boolean includeForAccessibility() {
        return includeForAccessibility(true);
    }

    public boolean includeForAccessibility(boolean z) {
        if (this.mAttachInfo == null) {
            return false;
        }
        if (z && !AccessibilityManager.getInstance(this.mContext).isRequestFromAccessibilityTool() && isAccessibilityDataSensitive()) {
            return false;
        }
        return (this.mAttachInfo.mAccessibilityFetchFlags & 128) != 0 || isImportantForAccessibility();
    }

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE)
    public boolean isAccessibilityDataSensitive() {
        if (this.mInferredAccessibilityDataSensitive == 0) {
            calculateAccessibilityDataSensitive();
        }
        return this.mInferredAccessibilityDataSensitive == 1;
    }

    void calculateAccessibilityDataSensitive() {
        int i = this.mExplicitAccessibilityDataSensitive;
        if (i != 0) {
            this.mInferredAccessibilityDataSensitive = i;
            return;
        }
        if (getFilterTouchesWhenObscured()) {
            this.mInferredAccessibilityDataSensitive = 1;
            return;
        }
        Object obj = this.mParent;
        if ((obj instanceof View) && ((View) obj).isAccessibilityDataSensitive()) {
            this.mInferredAccessibilityDataSensitive = 1;
        } else {
            this.mInferredAccessibilityDataSensitive = 2;
        }
    }

    public void setAccessibilityDataSensitive(int i) {
        this.mExplicitAccessibilityDataSensitive = i;
        calculateAccessibilityDataSensitive();
    }

    public boolean isActionableForAccessibility() {
        return isClickable() || isLongClickable() || isFocusable() || isContextClickable() || isScreenReaderFocusable();
    }

    private boolean hasListenersForAccessibility() {
        ListenerInfo listenerInfo = getListenerInfo();
        return (this.mTouchDelegate == null && listenerInfo.mOnKeyListener == null && listenerInfo.mOnTouchListener == null && listenerInfo.mOnGenericMotionListener == null && listenerInfo.mOnHoverListener == null && listenerInfo.mOnDragListener == null) ? false : true;
    }

    public void notifyViewAccessibilityStateChangedIfNeeded(int i) {
        AttachInfo attachInfo;
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled() || (attachInfo = this.mAttachInfo) == null) {
            return;
        }
        if (attachInfo == null || attachInfo.mViewRootImpl.mThread == Thread.currentThread()) {
            if (i != 1 && ((isAccessibilityPane() || (i == 32 && isAggregatedVisible())) && (isAggregatedVisible() || i == 32))) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain();
                onInitializeAccessibilityEvent(obtain);
                obtain.setEventType(32);
                obtain.setContentChangeTypes(i);
                obtain.setSource(this);
                onPopulateAccessibilityEvent(obtain);
                ViewParent viewParent = this.mParent;
                if (viewParent != null) {
                    try {
                        viewParent.requestSendAccessibilityEvent(this, obtain);
                        return;
                    } catch (AbstractMethodError e) {
                        Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                        return;
                    }
                }
                return;
            }
            if (getAccessibilityLiveRegion() != 0) {
                AccessibilityEvent obtain2 = AccessibilityEvent.obtain();
                obtain2.setEventType(2048);
                obtain2.setContentChangeTypes(i);
                sendAccessibilityEventUnchecked(obtain2);
                return;
            }
            ViewParent viewParent2 = this.mParent;
            if (viewParent2 != null) {
                try {
                    viewParent2.notifySubtreeAccessibilityStateChanged(this, this, i);
                } catch (AbstractMethodError e2) {
                    Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                }
            }
        }
    }

    private void hidden_notifyViewAccessibilityStateChangedIfNeeded(int i) {
        notifyViewAccessibilityStateChangedIfNeeded(i);
    }

    public void notifySubtreeAccessibilityStateChangedIfNeeded() {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled() || this.mAttachInfo == null) {
            return;
        }
        int i = this.mPrivateFlags2;
        if ((i & 134217728) == 0) {
            this.mPrivateFlags2 = i | 134217728;
            ViewParent viewParent = this.mParent;
            if (viewParent != null) {
                try {
                    viewParent.notifySubtreeAccessibilityStateChanged(this, this, 1);
                } catch (AbstractMethodError e) {
                    Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                }
            }
        }
    }

    private void notifySubtreeAccessibilityStateChangedByParentIfNeeded() {
        View view;
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && (view = (View) getParentForAccessibility()) != null && view.isShown()) {
            view.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTransitionVisibility(int i) {
        this.mViewFlags = i | (this.mViewFlags & (-13));
    }

    void resetSubtreeAccessibilityStateChanged() {
        this.mPrivateFlags2 &= -134217729;
    }

    public boolean dispatchNestedPrePerformAccessibilityAction(int i, Bundle bundle) {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent.onNestedPrePerformAccessibilityAction(this, i, bundle)) {
                return true;
            }
        }
        return false;
    }

    public boolean performAccessibilityAction(int i, Bundle bundle) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            return accessibilityDelegate.performAccessibilityAction(this, i, bundle);
        }
        return performAccessibilityActionInternal(i, bundle);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        AttachInfo attachInfo;
        if (isNestedScrollingEnabled() && ((i == 8192 || i == 4096 || i == 16908344 || i == 16908345 || i == 16908346 || i == 16908347) && dispatchNestedPrePerformAccessibilityAction(i, bundle))) {
            return true;
        }
        switch (i) {
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
                if (isAccessibilityFocused()) {
                    clearAccessibilityFocus();
                    return true;
                }
                return false;
            case 256:
                if (bundle != null) {
                    return traverseAtGranularity(bundle.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT), true, bundle.getBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN));
                }
                return false;
            case 512:
                if (bundle != null) {
                    return traverseAtGranularity(bundle.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT), false, bundle.getBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN));
                }
                return false;
            case 131072:
                if (getIterableTextForAccessibility() == null) {
                    return false;
                }
                int i2 = bundle != null ? bundle.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, -1) : -1;
                int i3 = bundle != null ? bundle.getInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, -1) : -1;
                if ((getAccessibilitySelectionStart() != i2 || getAccessibilitySelectionEnd() != i3) && i2 == i3) {
                    setAccessibilitySelection(i2, i3);
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                    return true;
                }
                return false;
            case 16908342:
                AttachInfo attachInfo2 = this.mAttachInfo;
                if (attachInfo2 != null) {
                    Rect rect = attachInfo2.mTmpInvalRect;
                    getDrawingRect(rect);
                    return requestRectangleOnScreen(rect, true);
                }
                return false;
            case 16908348:
                if (isContextClickable()) {
                    performContextClick();
                    return true;
                }
                return false;
            case 16908356:
                TooltipInfo tooltipInfo = this.mTooltipInfo;
                if (tooltipInfo == null || tooltipInfo.mTooltipPopup == null) {
                    return showLongClickTooltip(0, 0);
                }
                return false;
            case 16908357:
                TooltipInfo tooltipInfo2 = this.mTooltipInfo;
                if (tooltipInfo2 == null || tooltipInfo2.mTooltipPopup == null) {
                    return false;
                }
                hideTooltip();
                return true;
            case 16908374:
                if (!canAcceptAccessibilityDrop()) {
                    return false;
                }
                try {
                    AttachInfo attachInfo3 = this.mAttachInfo;
                    if (attachInfo3 != null && attachInfo3.mSession != null) {
                        int[] iArr = new int[2];
                        getLocationInWindow(iArr);
                        return this.mAttachInfo.mSession.dropForAccessibility(this.mAttachInfo.mWindow, iArr[0] + (getWidth() / 2), iArr[1] + (getHeight() / 2));
                    }
                } catch (RemoteException e) {
                    Log.e(VIEW_LOG_TAG, "Unable to drop for accessibility", e);
                }
                return false;
            case 16908375:
                if (!startedSystemDragForAccessibility() || (attachInfo = this.mAttachInfo) == null || attachInfo.mDragToken == null) {
                    return false;
                }
                cancelDragAndDrop();
                return true;
            default:
                return false;
        }
    }

    private boolean canAcceptAccessibilityDrop() {
        ListenerInfo listenerInfo;
        return (!canAcceptDrag() || (listenerInfo = this.mListenerInfo) == null || (listenerInfo.mOnDragListener == null && listenerInfo.mOnReceiveContentListener == null)) ? false : true;
    }

    private boolean traverseAtGranularity(int i, boolean z, boolean z2) {
        AccessibilityIterators.TextSegmentIterator iteratorForGranularity;
        int i2;
        int i3;
        CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
        if (iterableTextForAccessibility == null || iterableTextForAccessibility.length() == 0 || (iteratorForGranularity = getIteratorForGranularity(i)) == null) {
            return false;
        }
        int accessibilitySelectionEnd = getAccessibilitySelectionEnd();
        if (accessibilitySelectionEnd == -1) {
            accessibilitySelectionEnd = z ? 0 : iterableTextForAccessibility.length();
        }
        int[] following = z ? iteratorForGranularity.following(accessibilitySelectionEnd) : iteratorForGranularity.preceding(accessibilitySelectionEnd);
        if (following == null) {
            return false;
        }
        int i4 = following[0];
        int i5 = following[1];
        if (z2 && isAccessibilitySelectionExtendable()) {
            prepareForExtendedAccessibilitySelection();
            i2 = getAccessibilitySelectionStart();
            if (i2 == -1) {
                i2 = z ? i4 : i5;
            }
            i3 = z ? i5 : i4;
        } else {
            i2 = z ? i5 : i4;
            i3 = i2;
        }
        setAccessibilitySelection(i2, i3);
        sendViewTextTraversedAtGranularityEvent(z ? 256 : 512, i, i4, i5);
        return true;
    }

    public CharSequence getIterableTextForAccessibility() {
        return getContentDescription();
    }

    public int getAccessibilitySelectionStart() {
        return this.mAccessibilityCursorPosition;
    }

    public int getAccessibilitySelectionEnd() {
        return getAccessibilitySelectionStart();
    }

    public void setAccessibilitySelection(int i, int i2) {
        if (i == i2 && i2 == this.mAccessibilityCursorPosition) {
            return;
        }
        if (i >= 0 && i == i2 && i2 <= getIterableTextForAccessibility().length()) {
            this.mAccessibilityCursorPosition = i;
        } else {
            this.mAccessibilityCursorPosition = -1;
        }
        sendAccessibilityEvent(8192);
    }

    private void sendViewTextTraversedAtGranularityEvent(int i, int i2, int i3, int i4) {
        if (this.mParent == null) {
            return;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain(131072);
        onInitializeAccessibilityEvent(obtain);
        onPopulateAccessibilityEvent(obtain);
        obtain.setFromIndex(i3);
        obtain.setToIndex(i4);
        obtain.setAction(i);
        obtain.setMovementGranularity(i2);
        this.mParent.requestSendAccessibilityEvent(this, obtain);
    }

    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int i) {
        CharSequence iterableTextForAccessibility;
        if (i == 1) {
            CharSequence iterableTextForAccessibility2 = getIterableTextForAccessibility();
            if (iterableTextForAccessibility2 == null || iterableTextForAccessibility2.length() <= 0) {
                return null;
            }
            AccessibilityIterators.CharacterTextSegmentIterator characterTextSegmentIterator = AccessibilityIterators.CharacterTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
            characterTextSegmentIterator.initialize(iterableTextForAccessibility2.toString());
            return characterTextSegmentIterator;
        }
        if (i == 2) {
            CharSequence iterableTextForAccessibility3 = getIterableTextForAccessibility();
            if (iterableTextForAccessibility3 == null || iterableTextForAccessibility3.length() <= 0) {
                return null;
            }
            AccessibilityIterators.WordTextSegmentIterator wordTextSegmentIterator = AccessibilityIterators.WordTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
            wordTextSegmentIterator.initialize(iterableTextForAccessibility3.toString());
            return wordTextSegmentIterator;
        }
        if (i != 8 || (iterableTextForAccessibility = getIterableTextForAccessibility()) == null || iterableTextForAccessibility.length() <= 0) {
            return null;
        }
        AccessibilityIterators.ParagraphTextSegmentIterator paragraphTextSegmentIterator = AccessibilityIterators.ParagraphTextSegmentIterator.getInstance();
        paragraphTextSegmentIterator.initialize(iterableTextForAccessibility.toString());
        return paragraphTextSegmentIterator;
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

    public KeyEvent.DispatcherState getKeyDispatcherState() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mKeyDispatchState;
        }
        return null;
    }

    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        return onKeyPreIme(keyEvent.getKeyCode(), keyEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onKeyEvent(keyEvent, 0);
        }
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnKeyListener != null && (this.mViewFlags & 32) == 0 && listenerInfo.mOnKeyListener.onKey(this, keyEvent.getKeyCode(), keyEvent)) {
            return true;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (keyEvent.dispatch(this, attachInfo != null ? attachInfo.mKeyDispatchState : null, this)) {
            return true;
        }
        InputEventConsistencyVerifier inputEventConsistencyVerifier2 = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier2 != null) {
            inputEventConsistencyVerifier2.onUnhandledEvent(keyEvent, 0);
        }
        return false;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    protected boolean semIsShowingScrollbar() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        return (scrollabilityCache == null || scrollabilityCache.state == 0) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x003c, code lost:
    
        if (r6.isPenSideButton != false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0057, code lost:
    
        if (r0 != 213) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x005c, code lost:
    
        if (r6.isPenSideButton != false) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r7.isTargetAccessibilityFocus()
            r1 = 0
            if (r0 == 0) goto L11
            boolean r0 = r6.isAccessibilityFocusedViewOrHost()
            if (r0 != 0) goto Le
            return r1
        Le:
            r7.setTargetAccessibilityFocus(r1)
        L11:
            boolean r0 = com.samsung.android.rune.ViewRune.WIDGET_PEN_SUPPORTED
            r2 = 3
            r3 = 1
            if (r0 == 0) goto L6a
            boolean r0 = r6.mDisablePenGestureforfactorytest
            if (r0 != 0) goto L6a
            int r0 = r7.getAction()
            int r4 = r7.getToolType(r1)
            r5 = 2
            if (r4 != r5) goto L49
            int r4 = r7.getButtonState()
            r4 = r4 & 32
            if (r4 == 0) goto L49
            if (r0 == 0) goto L46
            if (r0 == r3) goto L3f
            if (r0 == r5) goto L3a
            if (r0 == r2) goto L3f
            switch(r0) {
                case 211: goto L5e;
                case 212: goto L5e;
                case 213: goto L5e;
                case 214: goto L5e;
                default: goto L39;
            }
        L39:
            goto L6a
        L3a:
            boolean r0 = r6.isPenSideButton
            if (r0 == 0) goto L6a
            goto L5e
        L3f:
            boolean r0 = r6.isPenSideButton
            if (r0 == 0) goto L6a
            r6.isPenSideButton = r1
            goto L5e
        L46:
            r6.isPenSideButton = r3
            goto L5e
        L49:
            if (r0 == 0) goto L68
            if (r0 == r3) goto L68
            if (r0 == r5) goto L5a
            if (r0 == r2) goto L68
            r4 = 212(0xd4, float:2.97E-43)
            if (r0 == r4) goto L5e
            r4 = 213(0xd5, float:2.98E-43)
            if (r0 == r4) goto L5e
            goto L6a
        L5a:
            boolean r0 = r6.isPenSideButton
            if (r0 == 0) goto L6a
        L5e:
            android.view.View$AttachInfo r0 = r6.mAttachInfo
            if (r0 == 0) goto L6a
            android.view.ViewTreeObserver r0 = r0.mTreeObserver
            r0.dispatchOnPenButtonEventListener(r7)
            goto L6a
        L68:
            r6.isPenSideButton = r1
        L6a:
            android.view.InputEventConsistencyVerifier r0 = r6.mInputEventConsistencyVerifier
            if (r0 == 0) goto L71
            r0.onTouchEvent(r7, r1)
        L71:
            int r0 = r7.getActionMasked()
            if (r0 != 0) goto L7a
            r6.stopNestedScroll()
        L7a:
            boolean r4 = r6.onFilterTouchEventForSecurity(r7)
            if (r4 == 0) goto L85
            boolean r4 = r6.performOnTouchCallback(r7)
            goto L86
        L85:
            r4 = r1
        L86:
            if (r4 != 0) goto L8f
            android.view.InputEventConsistencyVerifier r5 = r6.mInputEventConsistencyVerifier
            if (r5 == 0) goto L8f
            r5.onUnhandledEvent(r7, r1)
        L8f:
            if (r0 == r3) goto L99
            if (r0 == r2) goto L99
            if (r0 != 0) goto L98
            if (r4 != 0) goto L98
            goto L99
        L98:
            return r4
        L99:
            r6.stopNestedScroll()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean performOnTouchCallback(MotionEvent motionEvent) {
        boolean z = (this.mViewFlags & 32) == 0 && handleScrollBarDragging(motionEvent);
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnTouchListener != null && (this.mViewFlags & 32) == 0) {
            try {
                if (Trace.isTagEnabled(8L)) {
                    Trace.traceBegin(8L, "View.onTouchListener#onTouch - " + getClass().getSimpleName() + ", eventId - " + motionEvent.getId());
                }
                z = listenerInfo.mOnTouchListener.onTouch(this, motionEvent);
            } finally {
            }
        }
        if (z) {
            return true;
        }
        try {
            Trace.traceBegin(8L, "View#onTouchEvent");
            return onTouchEvent(motionEvent);
        } finally {
        }
    }

    boolean isAccessibilityFocusedViewOrHost() {
        if (isAccessibilityFocused()) {
            return true;
        }
        return getViewRootImpl() != null && getViewRootImpl().getAccessibilityFocusedHost() == this;
    }

    protected boolean canReceivePointerEvents() {
        return (this.mViewFlags & 12) == 0 || getAnimation() != null;
    }

    public boolean onFilterTouchEventForSecurity(MotionEvent motionEvent) {
        if ((this.mViewFlags & 1024) == 0 || (motionEvent.getFlags() & 1) == 0) {
            return (android.view.accessibility.Flags.preventA11yNontoolFromInjectingIntoSensitiveViews() && motionEvent.isInjectedFromAccessibilityService() && !motionEvent.isInjectedFromAccessibilityTool() && isAccessibilityDataSensitive()) ? false : true;
        }
        return false;
    }

    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onTrackballEvent(motionEvent, 0);
        }
        return onTrackballEvent(motionEvent);
    }

    public boolean dispatchCapturedPointerEvent(MotionEvent motionEvent) {
        if (!hasPointerCapture()) {
            return false;
        }
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnCapturedPointerListener == null || !listenerInfo.mOnCapturedPointerListener.onCapturedPointer(this, motionEvent)) {
            return onCapturedPointerEvent(motionEvent);
        }
        return true;
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onGenericMotionEvent(motionEvent, 0);
        }
        if ((motionEvent.getSource() & 2) != 0) {
            int action = motionEvent.getAction();
            if (action == 9 || action == 7 || action == 10) {
                if (dispatchHoverEvent(motionEvent)) {
                    return true;
                }
            } else if (dispatchGenericPointerEvent(motionEvent)) {
                return true;
            }
        } else if (dispatchGenericFocusedEvent(motionEvent)) {
            return true;
        }
        if (dispatchGenericMotionEventInternal(motionEvent)) {
            return true;
        }
        InputEventConsistencyVerifier inputEventConsistencyVerifier2 = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier2 != null) {
            inputEventConsistencyVerifier2.onUnhandledEvent(motionEvent, 0);
        }
        return false;
    }

    private boolean dispatchGenericMotionEventInternal(MotionEvent motionEvent) {
        boolean isFromSource = motionEvent.isFromSource(4194304);
        if (isFromSource && (this.mPrivateFlags4 & 1048576) == 0) {
            if (ViewConfiguration.get(this.mContext).isViewBasedRotaryEncoderHapticScrollFeedbackEnabled()) {
                this.mPrivateFlags4 |= 2097152;
            }
            this.mPrivateFlags4 |= 1048576;
        }
        if (isFromSource) {
            int i = this.mPrivateFlags4;
            if ((i & 2097152) != 0) {
                this.mPrivateFlags4 = (i & (-4194305)) | 8388608;
            }
        }
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnGenericMotionListener != null && (this.mViewFlags & 32) == 0 && listenerInfo.mOnGenericMotionListener.onGenericMotion(this, motionEvent)) {
            return true;
        }
        boolean onGenericMotionEvent = onGenericMotionEvent(motionEvent);
        if (isFromSource) {
            int i2 = this.mPrivateFlags4;
            if ((2097152 & i2) != 0) {
                if ((4194304 & i2) != 0) {
                    doRotaryProgressForScrollHaptics(motionEvent);
                } else {
                    doRotaryLimitForScrollHaptics(motionEvent);
                }
            }
        }
        if (onGenericMotionEvent) {
            return true;
        }
        int actionButton = motionEvent.getActionButton();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 11) {
            if (isContextClickable() && !this.mInContextButtonPress && !this.mHasPerformedLongPress && ((actionButton == 32 || actionButton == 2) && performContextClick(motionEvent.getX(), motionEvent.getY()))) {
                this.mInContextButtonPress = true;
                setPressed(true, motionEvent.getX(), motionEvent.getY());
                removeTapCallback();
                removeLongPressCallback();
                return true;
            }
        } else if (actionMasked == 12 && this.mInContextButtonPress && (actionButton == 32 || actionButton == 2)) {
            this.mInContextButtonPress = false;
            this.mIgnoreNextUpEvent = true;
        }
        InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 0);
        }
        return false;
    }

    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnHoverListener == null || (this.mViewFlags & 32) != 0 || !listenerInfo.mOnHoverListener.onHover(this, motionEvent)) {
            return onHoverEvent(motionEvent);
        }
        return true;
    }

    public final boolean dispatchPointerEvent(MotionEvent motionEvent) {
        if (motionEvent.isTouchEvent()) {
            return dispatchTouchEvent(motionEvent);
        }
        return dispatchGenericMotionEvent(motionEvent);
    }

    public void dispatchWindowFocusChanged(boolean z) {
        onWindowFocusChanged(z);
    }

    public void onWindowFocusChanged(boolean z) {
        if (!z) {
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
                int i = this.mPrivateFlags;
                if ((268435456 & i) != 0) {
                    this.mPrivateFlags = i & (-268435457);
                }
                int i2 = this.mSemViewFlags;
                if ((i2 & 1) != 0) {
                    this.mSemViewFlags = i2 & (-2);
                }
            }
            SemHoverPopupWindow semHoverPopupWindow = this.mHoverPopup;
            if (semHoverPopupWindow != null) {
                semHoverPopupWindow.dismiss();
            }
        } else if ((this.mPrivateFlags & 2) != 0) {
            notifyFocusChangeToImeFocusController(true);
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null && android.view.inputmethod.Flags.initiationWithoutInputConnection() && onCheckIsTextEditor()) {
                viewRootImpl.getHandwritingInitiator().onEditorFocused(this);
            }
        }
        refreshDrawableState();
    }

    public boolean hasWindowFocus() {
        AttachInfo attachInfo = this.mAttachInfo;
        return attachInfo != null && attachInfo.mHasWindowFocus;
    }

    public boolean hasImeFocus() {
        return getViewRootImpl() != null && getViewRootImpl().getImeFocusController().hasImeFocus();
    }

    protected void dispatchVisibilityChanged(View view, int i) {
        onVisibilityChanged(view, i);
    }

    public void dispatchDisplayHint(int i) {
        onDisplayHint(i);
    }

    public void dispatchWindowVisibilityChanged(int i) {
        onWindowVisibilityChanged(i);
    }

    protected void onWindowVisibilityChanged(int i) {
        if (i == 0) {
            initialAwakenScrollBars();
        }
    }

    public boolean isAggregatedVisible() {
        return (this.mPrivateFlags3 & 536870912) != 0;
    }

    boolean dispatchVisibilityAggregated(boolean z) {
        boolean z2 = getVisibility() == 0;
        if (z2 || !z) {
            onVisibilityAggregated(z);
        }
        return z2 && z;
    }

    public void onVisibilityAggregated(boolean z) {
        int i;
        boolean isAggregatedVisible = isAggregatedVisible();
        if (z) {
            i = this.mPrivateFlags3 | 536870912;
        } else {
            i = this.mPrivateFlags3 & (-536870913);
        }
        this.mPrivateFlags3 = i;
        if (z && this.mAttachInfo != null) {
            initialAwakenScrollBars();
        }
        Drawable drawable = this.mBackground;
        if (drawable != null && z != drawable.isVisible()) {
            drawable.setVisible(z, false);
        }
        Drawable drawable2 = this.mDefaultFocusHighlight;
        if (drawable2 != null && z != drawable2.isVisible()) {
            drawable2.setVisible(z, false);
        }
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        Drawable drawable3 = foregroundInfo != null ? foregroundInfo.mDrawable : null;
        if (drawable3 != null && z != drawable3.isVisible()) {
            drawable3.setVisible(z, false);
        }
        notifyAutofillManagerViewVisibilityChanged(z);
        if (z != isAggregatedVisible) {
            if (isAccessibilityPane()) {
                notifyViewAccessibilityStateChangedIfNeeded(z ? 16 : 32);
            }
            notifyAppearedOrDisappearedForContentCaptureIfNeeded(z);
            updateSensitiveViewsCountIfNeeded(z);
            if (!getSystemGestureExclusionRects().isEmpty()) {
                postUpdate(new View$$ExternalSyntheticLambda5(this));
            }
            if (collectPreferKeepClearRects().isEmpty()) {
                return;
            }
            postUpdate(new View$$ExternalSyntheticLambda6(this));
        }
    }

    private void notifyAutofillManagerViewVisibilityChanged(boolean z) {
        AutofillManager autofillManager;
        if (!isAutofillable() || (autofillManager = getAutofillManager()) == null || getAutofillViewId() <= 1073741823) {
            return;
        }
        Handler handler = this.mVisibilityChangeForAutofillHandler;
        if (handler != null) {
            handler.removeMessages(0);
        }
        if (z) {
            autofillManager.notifyViewVisibilityChanged(this, true);
            return;
        }
        if (this.mVisibilityChangeForAutofillHandler == null) {
            this.mVisibilityChangeForAutofillHandler = new VisibilityChangeForAutofillHandler(autofillManager, this);
        }
        this.mVisibilityChangeForAutofillHandler.obtainMessage(0, this).sendToTarget();
    }

    public int getWindowVisibility() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mWindowVisibility;
        }
        return 8;
    }

    public void getWindowVisibleDisplayFrame(Rect rect) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.getWindowVisibleDisplayFrame(rect);
            return;
        }
        WindowMetrics maximumWindowMetrics = ((WindowManager) this.mContext.getSystemService(WindowManager.class)).getMaximumWindowMetrics();
        Insets insets = maximumWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        rect.set(maximumWindowMetrics.getBounds());
        rect.inset(insets);
        rect.offsetTo(0, 0);
    }

    public void getWindowDisplayFrame(Rect rect) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.getDisplayFrame(rect);
        } else {
            DisplayManagerGlobal.getInstance().getRealDisplay(0).getRectSize(rect);
        }
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        onConfigurationChanged(configuration);
    }

    void dispatchCollectViewAttributes(AttachInfo attachInfo, int i) {
        performCollectViewAttributes(attachInfo, i);
    }

    void performCollectViewAttributes(AttachInfo attachInfo, int i) {
        if ((i & 12) == 0) {
            if ((this.mViewFlags & 67108864) == 67108864) {
                attachInfo.mKeepScreenOn = true;
            }
            attachInfo.mSystemUiVisibility |= this.mSystemUiVisibility;
            ListenerInfo listenerInfo = this.mListenerInfo;
            if (listenerInfo == null || listenerInfo.mOnSystemUiVisibilityChangeListener == null) {
                return;
            }
            attachInfo.mHasSystemUiListeners = true;
        }
    }

    void needGlobalAttributesUpdate(boolean z) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null || attachInfo.mRecomputeGlobalAttributes) {
            return;
        }
        if (z || attachInfo.mKeepScreenOn || attachInfo.mSystemUiVisibility != 0 || attachInfo.mHasSystemUiListeners) {
            attachInfo.mRecomputeGlobalAttributes = true;
        }
    }

    @ViewDebug.ExportedProperty
    public boolean isInTouchMode() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mInTouchMode;
        }
        return this.mResources.getBoolean(R.bool.config_defaultInTouchMode);
    }

    @ViewDebug.CapturedViewProperty
    public final Context getContext() {
        return this.mContext;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (KeyEvent.isConfirmKey(i) && keyEvent.hasNoModifiers()) {
            if ((this.mViewFlags & 32) == 32) {
                return true;
            }
            if (keyEvent.getRepeatCount() == 0) {
                int i2 = this.mViewFlags;
                boolean z = (i2 & 16384) == 16384 || (i2 & 2097152) == 2097152;
                boolean z2 = (i2 & 1073741824) == 1073741824 && (this.mSemViewFlags & 2) != 2;
                if (z || z2) {
                    float width = getWidth() / 2.0f;
                    float height = getHeight() / 2.0f;
                    if (z) {
                        setPressed(true, width, height);
                    }
                    checkForLongClick(ViewConfiguration.getLongPressTimeout(), width, height, 0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (KeyEvent.isConfirmKey(i) && keyEvent.hasNoModifiers()) {
            int i2 = this.mViewFlags;
            if ((i2 & 32) == 32) {
                return true;
            }
            if ((i2 & 16384) == 16384 && isPressed()) {
                setPressed(false);
                if (!this.mHasPerformedLongPress) {
                    removeLongPressCallback();
                    if (!keyEvent.isCanceled()) {
                        return performClickInternal();
                    }
                }
            }
        }
        return false;
    }

    public void semSetDisplayCutoutBackgroundColor(final int i) {
        post(new Runnable() { // from class: android.view.View.2
            @Override // java.lang.Runnable
            public void run() {
                ViewRootImpl viewRootImpl = View.this.getViewRootImpl();
                if (viewRootImpl != null) {
                    View view = viewRootImpl.getView();
                    if (view instanceof DecorView) {
                        ((DecorView) view).setDisplayCutoutBackgroundColor(i);
                        Log.d(viewRootImpl.getTag(), "DecorView.setDisplayCutoutBackgroundColor() #" + Integer.toHexString(i));
                    }
                }
            }
        });
    }

    public void semSetRoundedCorners(int i) {
        if ((i & (-16)) != 0) {
            throw new IllegalArgumentException("Use wrong rounded corners to the param, corners = " + i);
        }
        if (this.mRoundRadius == -1) {
            initRoundedCorner();
        }
        if (this.mRoundedCornerMode == i) {
            return;
        }
        this.mRoundedCornerMode = i;
        removeRoundedCorner((~i) & 15);
        if (i == 0) {
            return;
        }
        if (this.mTopLeftRound == null && (i & 1) != 0) {
            Drawable topLeftRound = getTopLeftRound();
            this.mTopLeftRound = topLeftRound;
            topLeftRound.setTint(this.mTopLeftRoundColor);
            if (DEBUG_ROUNDED_CORNER) {
                Log.i(VIEW_LOG_TAG, "set TL bounds[ " + this.mTopLeftRound.getBounds() + " ] color[ " + this.mTopLeftRoundColor + " ] this - " + this);
            }
        }
        if (this.mTopRightRound == null && (i & 2) != 0) {
            Drawable topRightRound = getTopRightRound();
            this.mTopRightRound = topRightRound;
            topRightRound.setTint(this.mTopRightRoundColor);
            if (DEBUG_ROUNDED_CORNER) {
                Log.i(VIEW_LOG_TAG, "set TR bounds[ " + this.mTopRightRound.getBounds() + " ] color[ " + this.mTopRightRoundColor + " ] this - " + this);
            }
        }
        if (this.mBottomLeftRound == null && (i & 4) != 0) {
            Drawable bottomLeftRound = getBottomLeftRound();
            this.mBottomLeftRound = bottomLeftRound;
            bottomLeftRound.setTint(this.mBottomLeftRoundColor);
            if (DEBUG_ROUNDED_CORNER) {
                Log.i(VIEW_LOG_TAG, "set BL bounds[ " + this.mBottomLeftRound.getBounds() + " ] color[ " + this.mBottomLeftRoundColor + " ] this - " + this);
            }
        }
        if (this.mBottomRightRound != null || (i & 8) == 0) {
            return;
        }
        Drawable bottomRightRound = getBottomRightRound();
        this.mBottomRightRound = bottomRightRound;
        bottomRightRound.setTint(this.mBottomRightRoundColor);
        if (DEBUG_ROUNDED_CORNER) {
            Log.i(VIEW_LOG_TAG, "set BR bounds[ " + this.mBottomRightRound.getBounds() + " ] color[ " + this.mBottomRightRoundColor + " ] this - " + this);
        }
    }

    public void semSetRoundedCorners(int i, Pair<Integer, Integer> pair) {
        semSetRoundedCorners(i);
        this.mRoundedCornerOffset = pair;
        Log.i(VIEW_LOG_TAG, "RoundedCornerOffset " + this.mRoundedCornerOffset);
    }

    public void semSetRoundedCorners(int i, int i2) {
        if (this.mRoundRadius == -1) {
            initRoundedCorner();
        }
        if (this.mRoundRadius != i2) {
            if (i2 < 0) {
                throw new IllegalArgumentException("Invalid radius value " + i2);
            }
            this.mRoundRadius = i2;
        }
        semSetRoundedCorners(i);
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
            Drawable drawable = this.mTopLeftRound;
            if (drawable != null) {
                drawable.setBounds(this.mRoundedCornerBounds.left + this.mRoundedCornerOffset.first.intValue(), this.mRoundedCornerBounds.top, this.mRoundedCornerBounds.left + this.mRoundRadius + this.mRoundedCornerOffset.first.intValue(), this.mRoundedCornerBounds.top + this.mRoundRadius);
                this.mTopLeftRound.draw(canvas);
            }
            Drawable drawable2 = this.mTopRightRound;
            if (drawable2 != null) {
                drawable2.setBounds((this.mRoundedCornerBounds.right - this.mRoundRadius) - this.mRoundedCornerOffset.second.intValue(), this.mRoundedCornerBounds.top, this.mRoundedCornerBounds.right - this.mRoundedCornerOffset.second.intValue(), this.mRoundedCornerBounds.top + this.mRoundRadius);
                this.mTopRightRound.draw(canvas);
            }
            Drawable drawable3 = this.mBottomLeftRound;
            if (drawable3 != null) {
                drawable3.setBounds(this.mRoundedCornerBounds.left + this.mRoundedCornerOffset.first.intValue(), this.mRoundedCornerBounds.bottom - this.mRoundRadius, this.mRoundedCornerBounds.left + this.mRoundRadius + this.mRoundedCornerOffset.first.intValue(), this.mRoundedCornerBounds.bottom);
                this.mBottomLeftRound.draw(canvas);
            }
            Drawable drawable4 = this.mBottomRightRound;
            if (drawable4 != null) {
                drawable4.setBounds((this.mRoundedCornerBounds.right - this.mRoundRadius) - this.mRoundedCornerOffset.second.intValue(), this.mRoundedCornerBounds.bottom - this.mRoundRadius, this.mRoundedCornerBounds.right - this.mRoundedCornerOffset.second.intValue(), this.mRoundedCornerBounds.bottom);
                this.mBottomRightRound.draw(canvas);
            }
        } catch (NullPointerException e) {
            Log.e("SemRoundedCorner", "semDrawRoundedCorner: view=" + this + " ex=" + e);
            throw null;
        }
    }

    public void semSetRoundedCornerColor(int i, int i2) {
        if (i == 0) {
            throw new IllegalArgumentException("There is no rounded corner on = " + this);
        }
        if ((i & (-16)) != 0) {
            throw new IllegalArgumentException("Use wrong rounded corners to the param, corners = " + i);
        }
        if (DEBUG_ROUNDED_CORNER) {
            return;
        }
        if (this.mRoundRadius == -1) {
            initRoundedCorner();
        }
        if ((i & 1) != 0) {
            this.mTopLeftRoundColor = i2;
            Drawable drawable = this.mTopLeftRound;
            if (drawable != null) {
                drawable.setTint(i2);
            }
        }
        if ((i & 2) != 0) {
            this.mTopRightRoundColor = i2;
            Drawable drawable2 = this.mTopRightRound;
            if (drawable2 != null) {
                drawable2.setTint(i2);
            }
        }
        if ((i & 4) != 0) {
            this.mBottomLeftRoundColor = i2;
            Drawable drawable3 = this.mBottomLeftRound;
            if (drawable3 != null) {
                drawable3.setTint(i2);
            }
        }
        if ((i & 8) != 0) {
            this.mBottomRightRoundColor = i2;
            Drawable drawable4 = this.mBottomRightRound;
            if (drawable4 != null) {
                drawable4.setTint(i2);
            }
        }
    }

    public void semSetRoundedCornerOffset(int i) {
        this.mCornerOffset = i;
    }

    private void initRoundedCorner() {
        this.mRoundRadius = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_rounded_corner_radius);
        int color = DEBUG_ROUNDED_CORNER ? -16776961 : this.mResources.getColor(R.color.sem_round_and_bgcolor_dark, null);
        this.mBottomRightRoundColor = color;
        this.mBottomLeftRoundColor = color;
        this.mTopRightRoundColor = color;
        this.mTopLeftRoundColor = color;
    }

    private void removeRoundedCorner(int i) {
        if ((i & 1) != 0) {
            this.mTopLeftRound = null;
        }
        if ((i & 2) != 0) {
            this.mTopRightRound = null;
        }
        if ((i & 4) != 0) {
            this.mBottomLeftRound = null;
        }
        if ((i & 8) != 0) {
            this.mBottomRightRound = null;
        }
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
        Drawable drawable = this.mTopLeftRound;
        if (drawable != null && !drawable.getBounds().isEmpty()) {
            region.op(this.mTopLeftRound.getBounds(), Region.Op.UNION);
        }
        Drawable drawable2 = this.mTopRightRound;
        if (drawable2 != null && !drawable2.getBounds().isEmpty()) {
            region.op(this.mTopRightRound.getBounds(), Region.Op.UNION);
        }
        Drawable drawable3 = this.mBottomLeftRound;
        if (drawable3 != null && !drawable3.getBounds().isEmpty()) {
            region.op(this.mBottomLeftRound.getBounds(), Region.Op.UNION);
        }
        Drawable drawable4 = this.mBottomRightRound;
        if (drawable4 == null || drawable4.getBounds().isEmpty()) {
            return;
        }
        region.op(this.mBottomRightRound.getBounds(), Region.Op.UNION);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void createContextMenu(ContextMenu contextMenu) {
        ContextMenu.ContextMenuInfo contextMenuInfo = getContextMenuInfo();
        MenuBuilder menuBuilder = (MenuBuilder) contextMenu;
        menuBuilder.setCurrentMenuInfo(contextMenuInfo);
        onCreateContextMenu(contextMenu);
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnCreateContextMenuListener != null) {
            listenerInfo.mOnCreateContextMenuListener.onCreateContextMenu(contextMenu, this, contextMenuInfo);
        }
        menuBuilder.setCurrentMenuInfo(null);
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.createContextMenu(contextMenu);
        }
    }

    private boolean dispatchTouchExplorationHoverEvent(MotionEvent motionEvent) {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            boolean z = this.mHoveringTouchDelegate;
            int actionMasked = motionEvent.getActionMasked();
            AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo = this.mTouchDelegate.getTouchDelegateInfo();
            boolean z2 = false;
            for (int i = 0; i < touchDelegateInfo.getRegionCount(); i++) {
                if (touchDelegateInfo.getRegionAt(i).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    z2 = true;
                }
            }
            if (!z) {
                if (android.view.accessibility.Flags.removeChildHoverCheckForTouchExploration()) {
                    if ((actionMasked == 9 || actionMasked == 7) && z2) {
                        this.mHoveringTouchDelegate = true;
                    }
                } else if ((actionMasked == 9 || actionMasked == 7) && !pointInHoveredChild(motionEvent) && z2) {
                    this.mHoveringTouchDelegate = true;
                }
            } else if (android.view.accessibility.Flags.removeChildHoverCheckForTouchExploration()) {
                if ((actionMasked == 10 || actionMasked == 7) && !z2) {
                    this.mHoveringTouchDelegate = false;
                }
            } else if (actionMasked == 10 || (actionMasked == 7 && (pointInHoveredChild(motionEvent) || !z2))) {
                this.mHoveringTouchDelegate = false;
            }
            if (actionMasked == 7) {
                if (z && this.mHoveringTouchDelegate) {
                    return this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                }
                if (!z && this.mHoveringTouchDelegate) {
                    if (motionEvent.getHistorySize() != 0) {
                        motionEvent = MotionEvent.obtainNoHistory(motionEvent);
                    }
                    motionEvent.setAction(9);
                    boolean onTouchExplorationHoverEvent = this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                    motionEvent.setAction(actionMasked);
                    return this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent) | onTouchExplorationHoverEvent;
                }
                if (z && !this.mHoveringTouchDelegate) {
                    boolean isHoverExitPending = motionEvent.isHoverExitPending();
                    motionEvent.setHoverExitPending(true);
                    this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                    if (motionEvent.getHistorySize() != 0) {
                        motionEvent = MotionEvent.obtainNoHistory(motionEvent);
                    }
                    motionEvent.setHoverExitPending(isHoverExitPending);
                    motionEvent.setAction(10);
                    this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                }
            } else if (actionMasked != 9) {
                if (actionMasked == 10 && z) {
                    this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                    return false;
                }
            } else if (!z && this.mHoveringTouchDelegate) {
                return this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
            }
        }
        return false;
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (this.mTouchDelegate != null && dispatchTouchExplorationHoverEvent(motionEvent)) {
            return true;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (!this.mSendingHoverAccessibilityEvents) {
            if ((actionMasked == 9 || actionMasked == 7) && !hasHoveredChild() && pointInView(motionEvent.getX(), motionEvent.getY())) {
                sendAccessibilityHoverEvent(128);
                this.mSendingHoverAccessibilityEvents = true;
            }
        } else if (actionMasked == 10 || (actionMasked == 7 && !pointInView(motionEvent.getX(), motionEvent.getY()))) {
            this.mSendingHoverAccessibilityEvents = false;
            sendAccessibilityHoverEvent(256);
        }
        if ((actionMasked == 9 || actionMasked == 7) && motionEvent.isFromSource(8194) && isOnScrollbar(motionEvent.getX(), motionEvent.getY())) {
            awakenScrollBars();
        }
        getViewRootImpl();
        boolean z = ViewRune.WIDGET_PEN_SUPPORTED;
        int toolType = motionEvent.getToolType(0);
        boolean z2 = toolType == 2 || toolType == 1 || toolType == 3;
        int i = this.mHoverPopupToolTypeByApp;
        if ((i == 0 || i == toolType) && z2 && this.mHoverPopupType != 0) {
            if (toolType == 1 && semIsDesktopMode() && motionEvent.isFromSource(8194)) {
                toolType = 3;
            }
            SemHoverPopupWindow semGetHoverPopup = semGetHoverPopup(toolType, false);
            if (this.mTooltipInfo == null && semGetHoverPopup != null && !semGetHoverPopup.onHoverEvent(motionEvent) && isFingerHoveredInAppWidget()) {
                if (actionMasked == 9 && (motionEvent.getButtonState() & 32) == 0) {
                    semGetHoverPopup.setHoveringPoint((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    semIsDesktopMode();
                    semGetHoverPopup.show();
                } else if (actionMasked == 10) {
                    semGetHoverPopup.dismiss();
                }
            }
        }
        boolean z3 = motionEvent.getToolType(0) == 2;
        boolean z4 = (motionEvent.getFlags() & 67108864) != 0;
        if (!isHoverable() && !isHovered()) {
            return false;
        }
        if (actionMasked == 9) {
            if (CoreRune.FW_SPEN_HOVER && z3 && !z4) {
                setSpenHovered(true);
            }
            setHovered(true);
        } else if (actionMasked == 10) {
            if (CoreRune.FW_SPEN_HOVER && z3 && !z4) {
                setSpenHovered(false);
            }
            setHovered(false);
        }
        dispatchGenericMotionEventInternal(motionEvent);
        return true;
    }

    private boolean isHoverable() {
        int i = this.mViewFlags;
        if ((i & 32) != 32 || isHovered()) {
            return (i & 16384) == 16384 || (i & 2097152) == 2097152 || (i & 8388608) == 8388608;
        }
        return false;
    }

    @ViewDebug.ExportedProperty
    public boolean isHovered() {
        return (this.mPrivateFlags & 268435456) != 0;
    }

    public void setHovered(boolean z) {
        if (z) {
            int i = this.mPrivateFlags;
            if ((i & 268435456) == 0) {
                this.mPrivateFlags = i | 268435456;
                refreshDrawableState();
                onHoverChanged(true);
                return;
            }
            return;
        }
        int i2 = this.mPrivateFlags;
        if ((268435456 & i2) != 0) {
            this.mPrivateFlags = i2 & (-268435457);
            refreshDrawableState();
            onHoverChanged(false);
        }
    }

    protected boolean handleScrollBarDragging(MotionEvent motionEvent) {
        int round;
        int round2;
        if (this.mScrollCache == null) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if ((this.mScrollCache.mScrollBarDraggingState == 0 && action != 0) || !motionEvent.isFromSource(8194) || !motionEvent.isButtonPressed(1)) {
            this.mScrollCache.mScrollBarDraggingState = 0;
            return false;
        }
        if (action != 0) {
            if (action == 2) {
                if (this.mScrollCache.mScrollBarDraggingState == 0) {
                    return false;
                }
                if (this.mScrollCache.mScrollBarDraggingState == 1) {
                    Rect rect = this.mScrollCache.mScrollBarBounds;
                    getVerticalScrollBarBounds(rect, null);
                    int computeVerticalScrollRange = computeVerticalScrollRange();
                    int computeVerticalScrollOffset = computeVerticalScrollOffset();
                    int computeVerticalScrollExtent = computeVerticalScrollExtent();
                    int thumbLength = ScrollBarUtils.getThumbLength(rect.height(), rect.width(), computeVerticalScrollExtent, computeVerticalScrollRange);
                    int thumbOffset = ScrollBarUtils.getThumbOffset(rect.height(), thumbLength, computeVerticalScrollExtent, computeVerticalScrollRange, computeVerticalScrollOffset);
                    float f = y - this.mScrollCache.mScrollBarDraggingPos;
                    float height = rect.height() - thumbLength;
                    float min = Math.min(Math.max(thumbOffset + f, 0.0f), height);
                    int height2 = getHeight();
                    if (Math.round(min) != thumbOffset && height > 0.0f && height2 > 0 && computeVerticalScrollExtent > 0 && (round2 = Math.round(((computeVerticalScrollRange - computeVerticalScrollExtent) / (computeVerticalScrollExtent / height2)) * (min / height))) != getScrollY()) {
                        this.mScrollCache.mScrollBarDraggingPos = y;
                        setScrollY(round2);
                    }
                    return true;
                }
                if (this.mScrollCache.mScrollBarDraggingState == 2) {
                    Rect rect2 = this.mScrollCache.mScrollBarBounds;
                    getHorizontalScrollBarBounds(rect2, null);
                    int computeHorizontalScrollRange = computeHorizontalScrollRange();
                    int computeHorizontalScrollOffset = computeHorizontalScrollOffset();
                    int computeHorizontalScrollExtent = computeHorizontalScrollExtent();
                    int thumbLength2 = ScrollBarUtils.getThumbLength(rect2.width(), rect2.height(), computeHorizontalScrollExtent, computeHorizontalScrollRange);
                    int thumbOffset2 = ScrollBarUtils.getThumbOffset(rect2.width(), thumbLength2, computeHorizontalScrollExtent, computeHorizontalScrollRange, computeHorizontalScrollOffset);
                    float f2 = x - this.mScrollCache.mScrollBarDraggingPos;
                    float width = rect2.width() - thumbLength2;
                    float min2 = Math.min(Math.max(thumbOffset2 + f2, 0.0f), width);
                    int width2 = getWidth();
                    if (Math.round(min2) != thumbOffset2 && width > 0.0f && width2 > 0 && computeHorizontalScrollExtent > 0 && (round = Math.round(((computeHorizontalScrollRange - computeHorizontalScrollExtent) / (computeHorizontalScrollExtent / width2)) * (min2 / width))) != getScrollX()) {
                        this.mScrollCache.mScrollBarDraggingPos = x;
                        setScrollX(round);
                    }
                    return true;
                }
            }
            this.mScrollCache.mScrollBarDraggingState = 0;
            return false;
        }
        if (this.mScrollCache.state == 0) {
            return false;
        }
        if (isOnVerticalScrollbarThumb(x, y)) {
            this.mScrollCache.mScrollBarDraggingState = 1;
            this.mScrollCache.mScrollBarDraggingPos = y;
            return true;
        }
        if (isOnHorizontalScrollbarThumb(x, y)) {
            this.mScrollCache.mScrollBarDraggingState = 2;
            this.mScrollCache.mScrollBarDraggingPos = x;
            return true;
        }
        this.mScrollCache.mScrollBarDraggingState = 0;
        return false;
    }

    @ViewDebug.ExportedProperty
    public boolean isSpenHovered() {
        return (this.mSemViewFlags & 1) != 0;
    }

    public void setSpenHovered(boolean z) {
        if (z) {
            int i = this.mSemViewFlags;
            if ((i & 1) == 0) {
                this.mSemViewFlags = i | 1;
                return;
            }
            return;
        }
        int i2 = this.mSemViewFlags;
        if ((i2 & 1) != 0) {
            this.mSemViewFlags = i2 & (-2);
        }
    }

    public boolean isFingerHoveredInAppWidget() {
        return this.mIsSetFingerHoveredInAppWidget;
    }

    @RemotableViewMethod
    public void setFingerHoveredInAppWidget(boolean z) {
        this.mIsSetFingerHoveredInAppWidget = z;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int i = this.mViewFlags;
        int action = motionEvent.getAction();
        boolean z = (i & 1073741824) == 1073741824 && (this.mSemViewFlags & 2) != 2;
        boolean z2 = (i & 16384) == 16384 || (i & 2097152) == 2097152 || (i & 8388608) == 8388608;
        if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
            Log.i(VIEW_LOG_TAG, "onTouchEvent " + motionEvent);
        }
        if (motionEvent.getToolType(0) == 2 && (motionEvent.getButtonState() & 32) != 0) {
            return z2;
        }
        if ((i & 32) == 32 && (this.mPrivateFlags4 & 4096) == 0) {
            if (action == 1 && (this.mPrivateFlags & 16384) != 0) {
                setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.i(VIEW_LOG_TAG, "onTouchEvent return_1");
            }
            return z2;
        }
        TouchDelegate touchDelegate = this.mTouchDelegate;
        if (touchDelegate != null && touchDelegate.onTouchEvent(motionEvent)) {
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.i(VIEW_LOG_TAG, "onTouchEvent return_2");
            }
            return true;
        }
        if (z2 || z) {
            if (action == 0) {
                if (motionEvent.getSource() == 4098) {
                    this.mPrivateFlags3 |= 131072;
                }
                this.mHasPerformedLongPress = false;
                if (!z2) {
                    checkForLongClick(ViewConfiguration.getLongPressTimeout(), x, y, 3);
                } else if (!performButtonActionOnTouchDown(motionEvent)) {
                    if (isInScrollingContainer()) {
                        this.mPrivateFlags = 33554432 | this.mPrivateFlags;
                        if (this.mPendingCheckForTap == null) {
                            this.mPendingCheckForTap = new CheckForTap();
                        }
                        this.mPendingCheckForTap.x = motionEvent.getX();
                        this.mPendingCheckForTap.y = motionEvent.getY();
                        postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                    } else {
                        setPressed(true, x, y);
                        checkForLongClick(ViewConfiguration.getLongPressTimeout(), x, y, 3);
                    }
                }
            } else if (action == 1) {
                this.mPrivateFlags3 &= -131073;
                if (z) {
                    handleTooltipUp();
                }
                if (!z2) {
                    removeTapCallback();
                    removeLongPressCallback();
                    this.mInContextButtonPress = false;
                    this.mHasPerformedLongPress = false;
                    this.mIgnoreNextUpEvent = false;
                } else {
                    int i2 = this.mPrivateFlags;
                    boolean z3 = (33554432 & i2) != 0;
                    if ((i2 & 16384) != 0 || z3) {
                        boolean requestFocus = (isFocusable() && isFocusableInTouchMode() && !isFocused()) ? requestFocus() : false;
                        if (z3) {
                            setPressed(true, x, y);
                        }
                        if (!this.mHasPerformedLongPress && !this.mIgnoreNextUpEvent) {
                            removeLongPressCallback();
                            if (!requestFocus) {
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
                        if (z3) {
                            postDelayed(this.mUnsetPressedState, ViewConfiguration.getPressedStateDuration());
                        } else if (!post(this.mUnsetPressedState)) {
                            this.mUnsetPressedState.run();
                        }
                        removeTapCallback();
                    }
                    this.mIgnoreNextUpEvent = false;
                }
            } else if (action == 2) {
                if (z2) {
                    drawableHotspotChanged(x, y);
                }
                int classification = motionEvent.getClassification();
                boolean z4 = classification == 1;
                int i3 = this.mIsDeviceDefault ? this.mExtendedTouchSlop : this.mTouchSlop;
                if (z4 && hasPendingLongPressCallback()) {
                    float f = i3;
                    if (!pointInView(x, y, f)) {
                        removeLongPressCallback();
                        checkForLongClick(((long) (ViewConfiguration.getLongPressTimeout() * this.mAmbiguousGestureMultiplier)) - (motionEvent.getEventTime() - motionEvent.getDownTime()), x, y, 3);
                    }
                    i3 = (int) (f * this.mAmbiguousGestureMultiplier);
                }
                if (!pointInView(x, y, i3)) {
                    removeTapCallback();
                    removeLongPressCallback();
                    if ((this.mPrivateFlags & 16384) != 0) {
                        setPressed(false);
                    }
                    this.mPrivateFlags3 &= -131073;
                }
                if (classification == 2 && hasPendingLongPressCallback()) {
                    removeLongPressCallback();
                    checkForLongClick(0L, x, y, 4);
                }
            } else if (action == 3) {
                if (z2) {
                    setPressed(false);
                }
                if (z) {
                    handleTooltipUp();
                }
                removeTapCallback();
                removeLongPressCallback();
                this.mInContextButtonPress = false;
                this.mHasPerformedLongPress = false;
                this.mIgnoreNextUpEvent = false;
                this.mPrivateFlags3 &= -131073;
            }
            if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
                Log.i(VIEW_LOG_TAG, "onTouchEvent return_3");
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
        if (attachInfo == null || attachInfo.mRootView == null || !attachInfo.mHandlingPointerEvent) {
            return false;
        }
        return (System.nanoTime() - Choreographer.getInstance().getLastFrameTimeNanos()) / 1000000 > 3 || attachInfo.mViewRootImpl.mViewFrameInfo.getAndIncreaseViewMeasuredCount() > 10;
    }

    public boolean isInScrollingContainer() {
        for (ViewParent parent = getParent(); parent != null && (parent instanceof ViewGroup); parent = parent.getParent()) {
            if (((ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
        }
        return false;
    }

    private boolean hidden_isInScrollingContainer() {
        return isInScrollingContainer();
    }

    private void removeLongPressCallback() {
        CheckForLongPress checkForLongPress = this.mPendingCheckForLongPress;
        if (checkForLongPress != null) {
            removeCallbacks(checkForLongPress);
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
        PerformClick performClick = this.mPerformClick;
        if (performClick != null) {
            removeCallbacks(performClick);
        }
    }

    private void removeUnsetPressCallback() {
        if ((this.mPrivateFlags & 16384) == 0 || this.mUnsetPressedState == null) {
            return;
        }
        setPressed(false);
        removeCallbacks(this.mUnsetPressedState);
    }

    private void removeTapCallback() {
        CheckForTap checkForTap = this.mPendingCheckForTap;
        if (checkForTap != null) {
            this.mPrivateFlags &= -33554433;
            removeCallbacks(checkForTap);
        }
    }

    public void cancelLongPress() {
        removeLongPressCallback();
        removeTapCallback();
    }

    public void setTouchDelegate(TouchDelegate touchDelegate) {
        this.mTouchDelegate = touchDelegate;
    }

    public TouchDelegate getTouchDelegate() {
        return this.mTouchDelegate;
    }

    public final void requestUnbufferedDispatch(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.mAttachInfo != null) {
            if ((action == 0 || action == 2) && motionEvent.isTouchEvent()) {
                this.mAttachInfo.mUnbufferedDispatchRequested = true;
            }
        }
    }

    public final void requestUnbufferedDispatch(int i) {
        if (this.mUnbufferedInputSource == i) {
            return;
        }
        this.mUnbufferedInputSource = i;
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.onDescendantUnbufferedRequested();
        }
    }

    private boolean hasSize() {
        return this.mBottom > this.mTop && this.mRight > this.mLeft;
    }

    private boolean canTakeFocus() {
        int i = this.mViewFlags;
        if ((i & 12) == 0 && (i & 1) == 1 && (i & 32) == 0) {
            return sCanFocusZeroSized || !isLayoutValid() || hasSize();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:176:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void setFlags(int r10, int r11) {
        /*
            Method dump skipped, instructions count: 570
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.setFlags(int, int):void");
    }

    public void bringToFront() {
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.bringChildToFront(this);
        }
    }

    private HapticScrollFeedbackProvider getScrollFeedbackProvider() {
        if (this.mScrollFeedbackProvider == null) {
            this.mScrollFeedbackProvider = new HapticScrollFeedbackProvider(this, ViewConfiguration.get(this.mContext), true);
        }
        return this.mScrollFeedbackProvider;
    }

    private void doRotaryProgressForScrollHaptics(MotionEvent motionEvent) {
        getScrollFeedbackProvider().onScrollProgress(motionEvent.getDeviceId(), 4194304, 26, -Math.round(motionEvent.getAxisValue(26) * ViewConfiguration.get(this.mContext).getScaledVerticalScrollFactor()));
    }

    private void doRotaryLimitForScrollHaptics(MotionEvent motionEvent) {
        getScrollFeedbackProvider().onScrollLimit(motionEvent.getDeviceId(), 4194304, 26, motionEvent.getAxisValue(26) > 0.0f);
    }

    private void processScrollEventForRotaryEncoderHaptics() {
        int i = this.mPrivateFlags4;
        int i2 = 8388608 | i;
        this.mPrivateFlags4 = i2;
        if (i2 != 0) {
            this.mPrivateFlags4 = (i | 12582912) & (-8388609);
        }
    }

    void disableRotaryScrollFeedback() {
        this.mPrivateFlags4 = (this.mPrivateFlags4 | 1048576) & (-2097153);
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        notifySubtreeAccessibilityStateChangedIfNeeded();
        postSendViewScrolledAccessibilityEventCallback(i - i3, i2 - i4);
        processScrollEventForRotaryEncoderHaptics();
        this.mBackgroundSizeChanged = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo != null) {
            foregroundInfo.mBoundsChanged = true;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewScrollChanged = true;
        }
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnScrollChangeListener == null) {
            return;
        }
        this.mListenerInfo.mOnScrollChangeListener.onScrollChange(this, i, i2, i3, i4);
    }

    public final ViewParent getParent() {
        return this.mParent;
    }

    public void setScrollX(int i) {
        scrollTo(i, this.mScrollY);
    }

    public void setScrollY(int i) {
        scrollTo(this.mScrollX, i);
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

    public void getDrawingRect(Rect rect) {
        rect.left = this.mScrollX;
        rect.top = this.mScrollY;
        rect.right = this.mScrollX + (this.mRight - this.mLeft);
        rect.bottom = this.mScrollY + (this.mBottom - this.mTop);
    }

    public final int getMeasuredWidth() {
        return this.mMeasuredWidth & 16777215;
    }

    @ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@ViewDebug.FlagToString(equals = 16777216, mask = -16777216, name = "MEASURED_STATE_TOO_SMALL")})
    public final int getMeasuredWidthAndState() {
        return this.mMeasuredWidth;
    }

    public final int getMeasuredHeight() {
        return this.mMeasuredHeight & 16777215;
    }

    @ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@ViewDebug.FlagToString(equals = 16777216, mask = -16777216, name = "MEASURED_STATE_TOO_SMALL")})
    public final int getMeasuredHeightAndState() {
        return this.mMeasuredHeight;
    }

    public final int getMeasuredState() {
        return ((this.mMeasuredHeight >> 16) & (-256)) | (this.mMeasuredWidth & (-16777216));
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
        return this.mRenderNode.getCameraDistance() * this.mResources.getDisplayMetrics().densityDpi;
    }

    public void setCameraDistance(float f) {
        float f2 = this.mResources.getDisplayMetrics().densityDpi;
        invalidateViewProperty(true, false);
        this.mRenderNode.setCameraDistance(Math.abs(f) / f2);
        invalidateViewProperty(false, false);
        invalidateParentIfNeededAndWasQuickRejected();
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getRotation() {
        return this.mRenderNode.getRotationZ();
    }

    @RemotableViewMethod
    public void setRotation(float f) {
        if (f != getRotation()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationZ(f);
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
    public void setRotationY(float f) {
        if (f != getRotationY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationY(f);
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
    public void setRotationX(float f) {
        if (f != getRotationX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationX(f);
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
    public void setScaleX(float f) {
        if (f != getScaleX()) {
            float sanitizeFloatPropertyValue = sanitizeFloatPropertyValue(f, "scaleX");
            invalidateViewProperty(true, false);
            this.mRenderNode.setScaleX(sanitizeFloatPropertyValue);
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
    public void setScaleY(float f) {
        if (f != getScaleY()) {
            float sanitizeFloatPropertyValue = sanitizeFloatPropertyValue(f, "scaleY");
            invalidateViewProperty(true, false);
            this.mRenderNode.setScaleY(sanitizeFloatPropertyValue);
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
    public void setPivotX(float f) {
        if (this.mRenderNode.isPivotExplicitlySet() && f == getPivotX()) {
            return;
        }
        invalidateViewProperty(true, false);
        this.mRenderNode.setPivotX(f);
        invalidateViewProperty(false, true);
        invalidateParentIfNeededAndWasQuickRejected();
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getPivotY() {
        return this.mRenderNode.getPivotY();
    }

    @RemotableViewMethod
    public void setPivotY(float f) {
        if (this.mRenderNode.isPivotExplicitlySet() && f == getPivotY()) {
            return;
        }
        invalidateViewProperty(true, false);
        this.mRenderNode.setPivotY(f);
        invalidateViewProperty(false, true);
        invalidateParentIfNeededAndWasQuickRejected();
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
        TransformationInfo transformationInfo = this.mTransformationInfo;
        if (transformationInfo != null) {
            return transformationInfo.mAlpha;
        }
        return 1.0f;
    }

    public void forceHasOverlappingRendering(boolean z) {
        int i = this.mPrivateFlags3;
        int i2 = 16777216 | i;
        this.mPrivateFlags3 = i2;
        if (z) {
            this.mPrivateFlags3 = 25165824 | i;
        } else {
            this.mPrivateFlags3 = (-8388609) & i2;
        }
    }

    public final boolean getHasOverlappingRendering() {
        int i = this.mPrivateFlags3;
        if ((16777216 & i) != 0) {
            return (8388608 & i) != 0;
        }
        return hasOverlappingRendering();
    }

    @RemotableViewMethod
    public void setAlpha(float f) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != f) {
            setAlphaInternal(f);
            if (onSetAlpha((int) (f * 255.0f))) {
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

    boolean setAlphaNoInvalidation(float f) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha == f) {
            return false;
        }
        setAlphaInternal(f);
        if (onSetAlpha((int) (f * 255.0f))) {
            this.mPrivateFlags |= 262144;
            return true;
        }
        this.mPrivateFlags &= -262145;
        this.mRenderNode.setAlpha(getFinalAlpha());
        return false;
    }

    void setAlphaInternal(float f) {
        float f2 = this.mTransformationInfo.mAlpha;
        this.mTransformationInfo.mAlpha = f;
        if ((f == 0.0f) ^ (f2 == 0.0f)) {
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTransitionAlpha(float f) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mTransitionAlpha != f) {
            this.mTransformationInfo.mTransitionAlpha = f;
            this.mPrivateFlags &= -262145;
            invalidateViewProperty(true, false);
            this.mRenderNode.setAlpha(getFinalAlpha());
        }
    }

    private float getFinalAlpha() {
        TransformationInfo transformationInfo = this.mTransformationInfo;
        if (transformationInfo != null) {
            return transformationInfo.mAlpha * this.mTransformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTransitionAlpha() {
        TransformationInfo transformationInfo = this.mTransformationInfo;
        if (transformationInfo != null) {
            return transformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    public void setForceDarkAllowed(boolean z) {
        if (this.mRenderNode.setForceDarkAllowed(z)) {
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

    public final void setTop(int i) {
        int i2;
        int i3;
        if (i != this.mTop) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (this.mAttachInfo != null) {
                    int i4 = this.mTop;
                    if (i < i4) {
                        i3 = i - i4;
                        i2 = i;
                    } else {
                        i2 = i4;
                        i3 = 0;
                    }
                    invalidate(0, i3, this.mRight - this.mLeft, this.mBottom - i2);
                }
            } else {
                invalidate(true);
            }
            int i5 = this.mRight - this.mLeft;
            int i6 = this.mBottom - this.mTop;
            this.mTop = i;
            this.mRenderNode.setTop(i);
            sizeChange(i5, this.mBottom - this.mTop, i5, i6);
            if (!hasIdentityMatrix) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
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

    public final void setBottom(int i) {
        if (i != this.mBottom) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (this.mAttachInfo != null) {
                    int i2 = this.mBottom;
                    if (i >= i2) {
                        i2 = i;
                    }
                    invalidate(0, 0, this.mRight - this.mLeft, i2 - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int i3 = this.mRight - this.mLeft;
            int i4 = this.mBottom - this.mTop;
            this.mBottom = i;
            this.mRenderNode.setBottom(i);
            sizeChange(i3, this.mBottom - this.mTop, i3, i4);
            if (!hasIdentityMatrix) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    @ViewDebug.CapturedViewProperty
    public final int getLeft() {
        return this.mLeft;
    }

    public final void setLeft(int i) {
        int i2;
        int i3;
        if (i != this.mLeft) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (this.mAttachInfo != null) {
                    int i4 = this.mLeft;
                    if (i < i4) {
                        i3 = i - i4;
                        i2 = i;
                    } else {
                        i2 = i4;
                        i3 = 0;
                    }
                    invalidate(i3, 0, this.mRight - i2, this.mBottom - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int i5 = this.mRight - this.mLeft;
            int i6 = this.mBottom - this.mTop;
            this.mLeft = i;
            this.mRenderNode.setLeft(i);
            sizeChange(this.mRight - this.mLeft, i6, i5, i6);
            if (!hasIdentityMatrix) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    @ViewDebug.CapturedViewProperty
    public final int getRight() {
        return this.mRight;
    }

    public final void setRight(int i) {
        if (i != this.mRight) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (this.mAttachInfo != null) {
                    int i2 = this.mRight;
                    if (i >= i2) {
                        i2 = i;
                    }
                    invalidate(0, 0, i2 - this.mLeft, this.mBottom - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int i3 = this.mRight - this.mLeft;
            int i4 = this.mBottom - this.mTop;
            this.mRight = i;
            this.mRenderNode.setRight(i);
            sizeChange(this.mRight - this.mLeft, i4, i3, i4);
            if (!hasIdentityMatrix) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    private static float sanitizeFloatPropertyValue(float f, String str) {
        return sanitizeFloatPropertyValue(f, str, -3.4028235E38f, Float.MAX_VALUE);
    }

    private static float sanitizeFloatPropertyValue(float f, String str, float f2, float f3) {
        if (f >= f2 && f <= f3) {
            return f;
        }
        if (f < f2 || f == Float.NEGATIVE_INFINITY) {
            if (!sThrowOnInvalidFloatProperties) {
                return f2;
            }
            throw new IllegalArgumentException("Cannot set '" + str + "' to " + f + ", the value must be >= " + f2);
        }
        if (f > f3 || f == Float.POSITIVE_INFINITY) {
            if (!sThrowOnInvalidFloatProperties) {
                return f3;
            }
            throw new IllegalArgumentException("Cannot set '" + str + "' to " + f + ", the value must be <= " + f3);
        }
        if (Float.isNaN(f)) {
            if (!sThrowOnInvalidFloatProperties) {
                return 0.0f;
            }
            throw new IllegalArgumentException("Cannot set '" + str + "' to Float.NaN");
        }
        throw new IllegalStateException("How do you get here?? " + f);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getX() {
        return this.mLeft + getTranslationX();
    }

    public void setX(float f) {
        setTranslationX(f - this.mLeft);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getY() {
        return this.mTop + getTranslationY();
    }

    public void setY(float f) {
        setTranslationY(f - this.mTop);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getZ() {
        return getElevation() + getTranslationZ();
    }

    public void setZ(float f) {
        setTranslationZ(f - getElevation());
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getElevation() {
        return this.mRenderNode.getElevation();
    }

    @RemotableViewMethod
    public void setElevation(float f) {
        if (f != getElevation()) {
            float sanitizeFloatPropertyValue = sanitizeFloatPropertyValue(f, SemMediaPostProcessor.ProcessingFormat.Key.ELEVATION);
            invalidateViewProperty(true, false);
            this.mRenderNode.setElevation(sanitizeFloatPropertyValue);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationX() {
        return this.mRenderNode.getTranslationX();
    }

    @RemotableViewMethod
    public void setTranslationX(float f) {
        if (f != getTranslationX()) {
            this.mPrivateFlags4 |= 268435456;
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationX(f);
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
    public void setTranslationY(float f) {
        if (f != getTranslationY()) {
            this.mPrivateFlags4 |= 268435456;
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationY(f);
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
    public void setTranslationZ(float f) {
        if (f != getTranslationZ()) {
            float sanitizeFloatPropertyValue = sanitizeFloatPropertyValue(f, "translationZ");
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationZ(sanitizeFloatPropertyValue);
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
        StateListAnimator stateListAnimator2 = this.mStateListAnimator;
        if (stateListAnimator2 == stateListAnimator) {
            return;
        }
        if (stateListAnimator2 != null) {
            stateListAnimator2.setTarget(null);
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
    public void setClipToOutline(boolean z) {
        damageInParent();
        if (getClipToOutline() != z) {
            this.mRenderNode.setClipToOutline(z);
        }
    }

    private void setOutlineProviderFromAttribute(int i) {
        if (i == 0) {
            setOutlineProvider(ViewOutlineProvider.BACKGROUND);
            return;
        }
        if (i == 1) {
            setOutlineProvider(null);
        } else if (i == 2) {
            setOutlineProvider(ViewOutlineProvider.BOUNDS);
        } else {
            if (i != 3) {
                return;
            }
            setOutlineProvider(ViewOutlineProvider.PADDED_BOUNDS);
        }
    }

    public void setOutlineProvider(ViewOutlineProvider viewOutlineProvider) {
        if (this.mOutlineProvider != viewOutlineProvider) {
            this.mOutlineProvider = viewOutlineProvider;
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
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return;
        }
        if (this.mOutlineProvider == null) {
            this.mRenderNode.setOutline(null);
            return;
        }
        Outline outline = attachInfo.mTmpOutline;
        outline.setEmpty();
        outline.setAlpha(1.0f);
        this.mOutlineProvider.getOutline(this, outline);
        this.mRenderNode.setOutline(outline);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean hasShadow() {
        return this.mRenderNode.hasShadow();
    }

    public void setOutlineSpotShadowColor(int i) {
        if (this.mRenderNode.setSpotShadowColor(i)) {
            invalidateViewProperty(true, true);
        }
    }

    public int getOutlineSpotShadowColor() {
        return this.mRenderNode.getSpotShadowColor();
    }

    public void setOutlineAmbientShadowColor(int i) {
        if (this.mRenderNode.setAmbientShadowColor(i)) {
            invalidateViewProperty(true, true);
        }
    }

    public int getOutlineAmbientShadowColor() {
        return this.mRenderNode.getAmbientShadowColor();
    }

    public void setRevealClip(boolean z, float f, float f2, float f3) {
        this.mRenderNode.setRevealClip(z, f, f2, f3);
        invalidateViewProperty(false, false);
    }

    public void getHitRect(Rect rect) {
        AttachInfo attachInfo;
        if (hasIdentityMatrix() || (attachInfo = this.mAttachInfo) == null) {
            rect.set(this.mLeft, this.mTop, this.mRight, this.mBottom);
            return;
        }
        RectF rectF = attachInfo.mTmpTransformRect;
        rectF.set(0.0f, 0.0f, getWidth(), getHeight());
        getMatrix().mapRect(rectF);
        rect.set(((int) rectF.left) + this.mLeft, ((int) rectF.top) + this.mTop, ((int) rectF.right) + this.mLeft, ((int) rectF.bottom) + this.mTop);
    }

    final boolean pointInView(float f, float f2) {
        return pointInView(f, f2, 0.0f);
    }

    public boolean pointInView(float f, float f2, float f3) {
        float f4 = -f3;
        return f >= f4 && f2 >= f4 && f < ((float) (this.mRight - this.mLeft)) + f3 && f2 < ((float) (this.mBottom - this.mTop)) + f3;
    }

    public void getFocusedRect(Rect rect) {
        getDrawingRect(rect);
    }

    public boolean getGlobalVisibleRect(Rect rect, Point point) {
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        if (i <= 0 || i2 <= 0) {
            return false;
        }
        rect.set(0, 0, i, i2);
        if (point != null) {
            point.set(-this.mScrollX, -this.mScrollY);
        }
        ViewParent viewParent = this.mParent;
        return viewParent == null || viewParent.getChildVisibleRect(this, rect, point);
    }

    public final boolean getGlobalVisibleRect(Rect rect) {
        return getGlobalVisibleRect(rect, null);
    }

    public final boolean getLocalVisibleRect(Rect rect) {
        AttachInfo attachInfo = this.mAttachInfo;
        Point point = attachInfo != null ? attachInfo.mPoint : new Point();
        if (!getGlobalVisibleRect(rect, point)) {
            return false;
        }
        rect.offset(-point.x, -point.y);
        return true;
    }

    public void offsetTopAndBottom(int i) {
        AttachInfo attachInfo;
        int i2;
        int i3;
        int i4;
        if (i != 0) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (isHardwareAccelerated()) {
                    invalidateViewProperty(false, false);
                } else {
                    ViewParent viewParent = this.mParent;
                    if (viewParent != null && (attachInfo = this.mAttachInfo) != null) {
                        Rect rect = attachInfo.mTmpInvalRect;
                        if (i < 0) {
                            i2 = this.mTop + i;
                            i3 = this.mBottom;
                            i4 = i;
                        } else {
                            i2 = this.mTop;
                            i3 = this.mBottom + i;
                            i4 = 0;
                        }
                        rect.set(0, i4, this.mRight - this.mLeft, i3 - i2);
                        viewParent.invalidateChild(this, rect);
                    }
                }
            } else {
                invalidateViewProperty(false, false);
            }
            this.mTop += i;
            this.mBottom += i;
            this.mRenderNode.offsetTopAndBottom(i);
            if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
                invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!hasIdentityMatrix) {
                    invalidateViewProperty(false, true);
                }
                invalidateParentIfNeeded();
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void offsetLeftAndRight(int i) {
        AttachInfo attachInfo;
        int i2;
        int i3;
        if (i != 0) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (isHardwareAccelerated()) {
                    invalidateViewProperty(false, false);
                } else {
                    ViewParent viewParent = this.mParent;
                    if (viewParent != null && (attachInfo = this.mAttachInfo) != null) {
                        Rect rect = attachInfo.mTmpInvalRect;
                        if (i < 0) {
                            i2 = this.mLeft + i;
                            i3 = this.mRight;
                        } else {
                            i2 = this.mLeft;
                            i3 = this.mRight + i;
                        }
                        rect.set(0, 0, i3 - i2, this.mBottom - this.mTop);
                        viewParent.invalidateChild(this, rect);
                    }
                }
            } else {
                invalidateViewProperty(false, false);
            }
            this.mLeft += i;
            this.mRight += i;
            this.mRenderNode.offsetLeftAndRight(i);
            if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
                invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!hasIdentityMatrix) {
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

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            throw new NullPointerException("Layout parameters cannot be null");
        }
        this.mLayoutParams = layoutParams;
        resolveLayoutParams();
        ViewParent viewParent = this.mParent;
        if (viewParent instanceof ViewGroup) {
            ((ViewGroup) viewParent).onSetLayoutParams(this, layoutParams);
        }
        requestLayout();
    }

    public void resolveLayoutParams() {
        ViewGroup.LayoutParams layoutParams = this.mLayoutParams;
        if (layoutParams != null) {
            layoutParams.resolveLayoutDirection(getLayoutDirection());
        }
    }

    public void scrollTo(int i, int i2) {
        int i3 = this.mScrollX;
        if (i3 == i && this.mScrollY == i2) {
            return;
        }
        int i4 = this.mScrollY;
        this.mScrollX = i;
        this.mScrollY = i2;
        invalidateParentCaches();
        onScrollChanged(this.mScrollX, this.mScrollY, i3, i4);
        if (awakenScrollBars()) {
            return;
        }
        postInvalidateOnAnimation();
    }

    public void scrollBy(int i, int i2) {
        scrollTo(this.mScrollX + i, this.mScrollY + i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean awakenScrollBars() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        return scrollabilityCache != null && awakenScrollBars(scrollabilityCache.scrollBarDefaultDelayBeforeFade, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean awakenScrollBars(int i) {
        return awakenScrollBars(i, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean awakenScrollBars(int i, boolean z) {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache == null || !scrollabilityCache.fadeScrollBars) {
            return false;
        }
        if (scrollabilityCache.scrollBar == null) {
            scrollabilityCache.scrollBar = new ScrollBarDrawable();
            scrollabilityCache.scrollBar.setState(getDrawableState());
            scrollabilityCache.scrollBar.setCallback(this);
        }
        if (!isHorizontalScrollBarEnabled() && !isVerticalScrollBarEnabled()) {
            return false;
        }
        if (z) {
            postInvalidateOnAnimation();
        }
        if (scrollabilityCache.state == 0) {
            i = Math.max(750, i);
        }
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() + i;
        scrollabilityCache.fadeStartTime = currentAnimationTimeMillis;
        scrollabilityCache.state = 1;
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mHandler.removeCallbacks(scrollabilityCache);
            this.mAttachInfo.mHandler.postAtTime(scrollabilityCache, currentAnimationTimeMillis);
        }
        return true;
    }

    private boolean skipInvalidate() {
        if ((this.mViewFlags & 12) == 0 || this.mCurrentAnimation != null) {
            return false;
        }
        ViewParent viewParent = this.mParent;
        return ((viewParent instanceof ViewGroup) && ((ViewGroup) viewParent).isViewTransitioning(this)) ? false : true;
    }

    @Deprecated
    public void invalidate(Rect rect) {
        int i = this.mScrollX;
        int i2 = this.mScrollY;
        invalidateInternal(rect.left - i, rect.top - i2, rect.right - i, rect.bottom - i2, true, false);
    }

    @Deprecated
    public void invalidate(int i, int i2, int i3, int i4) {
        int i5 = this.mScrollX;
        int i6 = this.mScrollY;
        invalidateInternal(i - i5, i2 - i6, i3 - i5, i4 - i6, true, false);
    }

    public void invalidate() {
        invalidate(true);
    }

    public void invalidate(boolean z) {
        invalidateInternal(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, z, true);
    }

    void invalidateInternal(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        View projectionReceiver;
        GhostView ghostView = this.mGhostView;
        if (ghostView != null) {
            ghostView.invalidate(true);
            return;
        }
        if (skipInvalidate()) {
            return;
        }
        this.mPrivateFlags4 &= -193;
        this.mContentCaptureSessionCached = false;
        int i5 = this.mPrivateFlags;
        if ((i5 & 48) == 48 || ((z && (i5 & 32768) == 32768) || (i5 & Integer.MIN_VALUE) != Integer.MIN_VALUE || (z2 && isOpaque() != this.mLastIsOpaque))) {
            if (z2) {
                this.mLastIsOpaque = isOpaque();
                this.mPrivateFlags &= -33;
            }
            int i6 = this.mPrivateFlags;
            this.mPrivateFlags = 2097152 | i6;
            if (z) {
                this.mPrivateFlags = ((-2145386496) | i6) & (-32769);
            }
            AttachInfo attachInfo = this.mAttachInfo;
            ViewParent viewParent = this.mParent;
            if (viewParent != null && attachInfo != null && i < i3 && i2 < i4) {
                Rect rect = attachInfo.mTmpInvalRect;
                rect.set(i, i2, i3, i4);
                viewParent.invalidateChild(this, rect);
            }
            Drawable drawable = this.mBackground;
            if (drawable == null || !drawable.isProjected() || (projectionReceiver = getProjectionReceiver()) == null) {
                return;
            }
            projectionReceiver.damageInParent();
        }
    }

    private View getProjectionReceiver() {
        for (ViewParent parent = getParent(); parent != null && (parent instanceof View); parent = parent.getParent()) {
            View view = (View) parent;
            if (view.isProjectionReceiver()) {
                return view;
            }
        }
        return null;
    }

    private boolean isProjectionReceiver() {
        return this.mBackground != null;
    }

    void invalidateViewProperty(boolean z, boolean z2) {
        if (!isHardwareAccelerated() || !this.mRenderNode.hasDisplayList() || (this.mPrivateFlags & 64) != 0) {
            if (z) {
                invalidateParentCaches();
            }
            if (z2) {
                this.mPrivateFlags |= 32;
            }
            invalidate(false);
        } else {
            damageInParent();
        }
        this.mPrivateFlags4 |= 536870912;
    }

    protected void damageInParent() {
        ViewParent viewParent = this.mParent;
        if (viewParent == null || this.mAttachInfo == null) {
            return;
        }
        viewParent.onDescendantInvalidated(this, this);
    }

    protected void invalidateParentCaches() {
        Object obj = this.mParent;
        if (obj instanceof View) {
            ((View) obj).mPrivateFlags |= Integer.MIN_VALUE;
        }
    }

    protected void invalidateParentIfNeeded() {
        if (isHardwareAccelerated()) {
            Object obj = this.mParent;
            if (obj instanceof View) {
                ((View) obj).invalidate(true);
            }
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
        int i;
        Drawable drawable = this.mBackground;
        if (drawable != null && drawable.getOpacity() == -1) {
            this.mPrivateFlags |= 8388608;
        } else {
            this.mPrivateFlags &= -8388609;
        }
        int i2 = this.mViewFlags;
        if (((i2 & 512) == 0 && (i2 & 256) == 0) || (i = i2 & 50331648) == 0 || i == 33554432) {
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
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mViewRootImpl;
        }
        return null;
    }

    public ThreadedRenderer getThreadedRenderer() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mThreadedRenderer;
        }
        return null;
    }

    public boolean post(Runnable runnable) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.post(runnable);
        }
        getRunQueue().post(runnable);
        return true;
    }

    public boolean postDelayed(Runnable runnable, long j) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.postDelayed(runnable, j);
        }
        getRunQueue().postDelayed(runnable, j);
        return true;
    }

    public void postOnAnimation(Runnable runnable) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallback(1, runnable, null);
        } else {
            getRunQueue().post(runnable);
        }
    }

    public void postOnAnimationDelayed(Runnable runnable, long j) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, runnable, null, j);
        } else {
            getRunQueue().postDelayed(runnable, j);
        }
    }

    public boolean removeCallbacks(Runnable runnable) {
        if (runnable != null) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mHandler.removeCallbacks(runnable);
                attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, runnable, null);
            }
            getRunQueue().removeCallbacks(runnable);
        }
        return true;
    }

    public void postInvalidate() {
        postInvalidateDelayed(0L);
    }

    public void postInvalidate(int i, int i2, int i3, int i4) {
        postInvalidateDelayed(0L, i, i2, i3, i4);
    }

    public void postInvalidateDelayed(long j) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateDelayed(this, j);
        }
    }

    public void postInvalidateDelayed(long j, int i, int i2, int i3, int i4) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            AttachInfo.InvalidateInfo obtain = AttachInfo.InvalidateInfo.obtain();
            obtain.target = this;
            obtain.left = i;
            obtain.top = i2;
            obtain.right = i3;
            obtain.bottom = i4;
            attachInfo.mViewRootImpl.dispatchInvalidateRectDelayed(obtain, j);
        }
    }

    public void postInvalidateOnAnimation() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateOnAnimation(this);
        }
    }

    public void postInvalidateOnAnimation(int i, int i2, int i3, int i4) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            AttachInfo.InvalidateInfo obtain = AttachInfo.InvalidateInfo.obtain();
            obtain.target = this;
            obtain.left = i;
            obtain.top = i2;
            obtain.right = i3;
            obtain.bottom = i4;
            attachInfo.mViewRootImpl.dispatchInvalidateRectOnAnimation(obtain);
        }
    }

    private void postSendViewScrolledAccessibilityEventCallback(int i, int i2) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(4096);
            obtain.setScrollDeltaX(i);
            obtain.setScrollDeltaY(i2);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public boolean isHorizontalFadingEdgeEnabled() {
        return (this.mViewFlags & 4096) == 4096;
    }

    public void setHorizontalFadingEdgeEnabled(boolean z) {
        if (isHorizontalFadingEdgeEnabled() != z) {
            if (z) {
                initScrollCache();
            }
            this.mViewFlags ^= 4096;
        }
    }

    public boolean isVerticalFadingEdgeEnabled() {
        return (this.mViewFlags & 8192) == 8192;
    }

    public void setVerticalFadingEdgeEnabled(boolean z) {
        if (isVerticalFadingEdgeEnabled() != z) {
            if (z) {
                initScrollCache();
            }
            this.mViewFlags ^= 8192;
        }
    }

    public int getFadingEdge() {
        return this.mViewFlags & 12288;
    }

    public int getFadingEdgeLength() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache == null || (this.mViewFlags & 12288) == 0) {
            return 0;
        }
        return scrollabilityCache.fadingEdgeLength;
    }

    protected float getTopFadingEdgeStrength() {
        return computeVerticalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    protected float getBottomFadingEdgeStrength() {
        return computeVerticalScrollOffset() + computeVerticalScrollExtent() < computeVerticalScrollRange() ? 1.0f : 0.0f;
    }

    protected float getLeftFadingEdgeStrength() {
        return computeHorizontalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    protected float getRightFadingEdgeStrength() {
        return computeHorizontalScrollOffset() + computeHorizontalScrollExtent() < computeHorizontalScrollRange() ? 1.0f : 0.0f;
    }

    public boolean isHorizontalScrollBarEnabled() {
        return (this.mViewFlags & 256) == 256;
    }

    public void setHorizontalScrollBarEnabled(boolean z) {
        if (isHorizontalScrollBarEnabled() != z) {
            this.mViewFlags ^= 256;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public boolean isVerticalScrollBarEnabled() {
        return (this.mViewFlags & 512) == 512;
    }

    public void setVerticalScrollBarEnabled(boolean z) {
        if (isVerticalScrollBarEnabled() != z) {
            this.mViewFlags ^= 512;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    protected void recomputePadding() {
        internalSetPadding(this.mUserPaddingLeft, this.mPaddingTop, this.mUserPaddingRight, this.mUserPaddingBottom);
    }

    public void setScrollbarFadingEnabled(boolean z) {
        initScrollCache();
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        scrollabilityCache.fadeScrollBars = z;
        if (z) {
            scrollabilityCache.state = 0;
        } else {
            scrollabilityCache.state = 1;
        }
    }

    public boolean isScrollbarFadingEnabled() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        return scrollabilityCache != null && scrollabilityCache.fadeScrollBars;
    }

    public int getScrollBarDefaultDelayBeforeFade() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        return scrollabilityCache == null ? ViewConfiguration.getScrollDefaultDelay() : scrollabilityCache.scrollBarDefaultDelayBeforeFade;
    }

    public void setScrollBarDefaultDelayBeforeFade(int i) {
        getScrollCache().scrollBarDefaultDelayBeforeFade = i;
    }

    public int getScrollBarFadeDuration() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        return scrollabilityCache == null ? ViewConfiguration.getScrollBarFadeDuration() : scrollabilityCache.scrollBarFadeDuration;
    }

    public void setScrollBarFadeDuration(int i) {
        getScrollCache().scrollBarFadeDuration = i;
    }

    public int getScrollBarSize() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        return scrollabilityCache == null ? ViewConfiguration.get(this.mContext).getScaledScrollBarSize() : scrollabilityCache.scrollBarSize;
    }

    public void setScrollBarSize(int i) {
        getScrollCache().scrollBarSize = i;
    }

    public void setScrollBarStyle(int i) {
        int i2 = this.mViewFlags;
        if (i != (i2 & 50331648)) {
            this.mViewFlags = (i & 50331648) | (i2 & (-50331649));
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, to = "INSIDE_OVERLAY"), @ViewDebug.IntToString(from = 16777216, to = "INSIDE_INSET"), @ViewDebug.IntToString(from = 33554432, to = "OUTSIDE_OVERLAY"), @ViewDebug.IntToString(from = 50331648, to = "OUTSIDE_INSET")})
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

    public boolean canScrollHorizontally(int i) {
        int computeHorizontalScrollOffset = computeHorizontalScrollOffset();
        int computeHorizontalScrollRange = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        if (computeHorizontalScrollRange == 0) {
            return false;
        }
        return i < 0 ? computeHorizontalScrollOffset > 0 : computeHorizontalScrollOffset < computeHorizontalScrollRange - 1;
    }

    public boolean canScrollVertically(int i) {
        int computeVerticalScrollOffset = computeVerticalScrollOffset();
        int computeVerticalScrollRange = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (computeVerticalScrollRange == 0) {
            return false;
        }
        return i < 0 ? computeVerticalScrollOffset > 0 : computeVerticalScrollOffset < computeVerticalScrollRange - 1;
    }

    void getScrollIndicatorBounds(Rect rect) {
        rect.left = this.mScrollX;
        rect.right = (this.mScrollX + this.mRight) - this.mLeft;
        rect.top = this.mScrollY;
        rect.bottom = (this.mScrollY + this.mBottom) - this.mTop;
    }

    private void onDrawScrollIndicators(Canvas canvas) {
        Drawable drawable;
        if ((this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) == 0 || (drawable = this.mScrollIndicatorDrawable) == null || this.mAttachInfo == null) {
            return;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        Rect rect = this.mAttachInfo.mTmpInvalRect;
        getScrollIndicatorBounds(rect);
        if ((this.mPrivateFlags3 & 256) != 0 && canScrollVertically(-1)) {
            drawable.setBounds(rect.left, rect.top, rect.right, rect.top + intrinsicHeight);
            drawable.draw(canvas);
        }
        if ((this.mPrivateFlags3 & 512) != 0 && canScrollVertically(1)) {
            drawable.setBounds(rect.left, rect.bottom - intrinsicHeight, rect.right, rect.bottom);
            drawable.draw(canvas);
        }
        int i = 8192;
        int i2 = 4096;
        if (getLayoutDirection() != 1) {
            i2 = 8192;
            i = 4096;
        }
        if (((i | 1024) & this.mPrivateFlags3) != 0 && canScrollHorizontally(-1)) {
            drawable.setBounds(rect.left, rect.top, rect.left + intrinsicWidth, rect.bottom);
            drawable.draw(canvas);
        }
        if (((i2 | 2048) & this.mPrivateFlags3) == 0 || !canScrollHorizontally(1)) {
            return;
        }
        drawable.setBounds(rect.right - intrinsicWidth, rect.top, rect.right, rect.bottom);
        drawable.draw(canvas);
    }

    private void getHorizontalScrollBarBounds(Rect rect, Rect rect2) {
        if (rect == null) {
            rect = rect2;
        }
        if (rect == null) {
            return;
        }
        int i = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
        boolean z = isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
        int horizontalScrollbarHeight = getHorizontalScrollbarHeight();
        int verticalScrollbarWidth = z ? getVerticalScrollbarWidth() : 0;
        int i2 = this.mRight - this.mLeft;
        int i3 = this.mBottom - this.mTop;
        rect.top = ((this.mScrollY + i3) - horizontalScrollbarHeight) - (this.mUserPaddingBottom & i);
        rect.left = this.mScrollX + (this.mPaddingLeft & i);
        rect.right = ((this.mScrollX + i2) - (i & this.mUserPaddingRight)) - verticalScrollbarWidth;
        rect.bottom = rect.top + horizontalScrollbarHeight;
        if (rect2 == null) {
            return;
        }
        if (rect2 != rect) {
            rect2.set(rect);
        }
        int i4 = this.mScrollCache.scrollBarMinTouchTarget;
        if (rect2.height() < i4) {
            rect2.bottom = Math.min(rect2.bottom + ((i4 - rect2.height()) / 2), this.mScrollY + i3);
            rect2.top = rect2.bottom - i4;
        }
        if (rect2.width() < i4) {
            rect2.left -= (i4 - rect2.width()) / 2;
            rect2.right = rect2.left + i4;
        }
    }

    private void getVerticalScrollBarBounds(Rect rect, Rect rect2) {
        RoundScrollbarRenderer roundScrollbarRenderer = this.mRoundScrollbarRenderer;
        if (roundScrollbarRenderer == null) {
            getStraightVerticalScrollBarBounds(rect, rect2);
            return;
        }
        if (rect == null) {
            rect = rect2;
        }
        roundScrollbarRenderer.getRoundVerticalScrollBarBounds(rect);
    }

    private void getStraightVerticalScrollBarBounds(Rect rect, Rect rect2) {
        if (rect == null) {
            rect = rect2;
        }
        if (rect == null) {
            return;
        }
        int i = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
        int verticalScrollbarWidth = getVerticalScrollbarWidth();
        int i2 = this.mVerticalScrollbarPosition;
        if (i2 == 0) {
            i2 = isLayoutRtl() ? 1 : 2;
        }
        int i3 = this.mRight - this.mLeft;
        int i4 = this.mBottom - this.mTop;
        if (i2 != 1) {
            rect.left = ((this.mScrollX + i3) - verticalScrollbarWidth) - (this.mUserPaddingRight & i);
        } else {
            rect.left = this.mScrollX + (this.mUserPaddingLeft & i);
        }
        rect.top = this.mScrollY + (this.mPaddingTop & i) + this.mAppWidgetScrollBarTopPadding;
        rect.right = rect.left + verticalScrollbarWidth;
        rect.bottom = ((this.mScrollY + i4) - (i & this.mUserPaddingBottom)) - this.mAppWidgetScrollBarBottomPadding;
        if (rect2 == null) {
            return;
        }
        if (rect2 != rect) {
            rect2.set(rect);
        }
        int i5 = this.mScrollCache.scrollBarMinTouchTarget;
        if (rect2.width() < i5) {
            int width = (i5 - rect2.width()) / 2;
            if (i2 == 2) {
                rect2.right = Math.min(rect2.right + width, this.mScrollX + i3);
                rect2.left = rect2.right - i5;
            } else {
                rect2.left = Math.max(rect2.left + width, this.mScrollX);
                rect2.right = rect2.left + i5;
            }
        }
        if (rect2.height() < i5) {
            rect2.top -= (i5 - rect2.height()) / 2;
            rect2.bottom = rect2.top + i5;
        }
    }

    protected final void onDrawScrollBars(Canvas canvas) {
        int i;
        boolean z;
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache == null || (i = scrollabilityCache.state) == 0) {
            return;
        }
        boolean z2 = true;
        if (i == 2) {
            if (scrollabilityCache.interpolatorValues == null) {
                scrollabilityCache.interpolatorValues = new float[1];
            }
            float[] fArr = scrollabilityCache.interpolatorValues;
            if (scrollabilityCache.scrollBarInterpolator.timeToValues(fArr) == Interpolator.Result.FREEZE_END) {
                scrollabilityCache.state = 0;
            } else {
                scrollabilityCache.scrollBar.mutate().setAlpha(Math.round(fArr[0]));
            }
            z = true;
        } else {
            scrollabilityCache.scrollBar.mutate().setAlpha(255);
            z = false;
        }
        boolean isHorizontalScrollBarEnabled = isHorizontalScrollBarEnabled();
        boolean z3 = isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
        if (this.mRoundScrollbarRenderer != null) {
            if (z3) {
                Rect rect = scrollabilityCache.mScrollBarBounds;
                getVerticalScrollBarBounds(rect, null);
                int i2 = this.mVerticalScrollbarPosition;
                if (i2 != 1 && (i2 != 0 || !isLayoutRtl())) {
                    z2 = false;
                }
                this.mRoundScrollbarRenderer.drawRoundScrollbars(canvas, scrollabilityCache.scrollBar.getAlpha() / 255.0f, rect, z2);
                if (z) {
                    invalidate();
                    return;
                }
                return;
            }
            return;
        }
        if (z3 || isHorizontalScrollBarEnabled) {
            ScrollBarDrawable scrollBarDrawable = scrollabilityCache.scrollBar;
            if (isHorizontalScrollBarEnabled) {
                scrollBarDrawable.setParameters(computeHorizontalScrollRange(), computeHorizontalScrollOffset(), computeHorizontalScrollExtent(), false);
                Rect rect2 = scrollabilityCache.mScrollBarBounds;
                getHorizontalScrollBarBounds(rect2, null);
                onDrawHorizontalScrollBar(canvas, scrollBarDrawable, rect2.left, rect2.top, rect2.right, rect2.bottom);
                if (z) {
                    invalidate(rect2);
                }
            }
            if (z3) {
                scrollBarDrawable.setParameters(computeVerticalScrollRange(), computeVerticalScrollOffset(), computeVerticalScrollExtent(), true);
                Rect rect3 = scrollabilityCache.mScrollBarBounds;
                getVerticalScrollBarBounds(rect3, null);
                if (this.mNeededToChangedScrollBarPosition) {
                    rect3.left -= this.mScrollBarPositionPadding;
                    rect3.right -= this.mScrollBarPositionPadding;
                }
                onDrawVerticalScrollBar(canvas, scrollBarDrawable, rect3.left, rect3.top, rect3.right, rect3.bottom);
                if (z) {
                    invalidate(rect3);
                }
            }
        }
    }

    protected void onDrawHorizontalScrollBar(Canvas canvas, Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setBounds(i, i2, i3, i4);
        drawable.draw(canvas);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDrawVerticalScrollBar(Canvas canvas, Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setBounds(i, i2, i3, i4);
        drawable.draw(canvas);
    }

    void assignParent(ViewParent viewParent) {
        if (this.mParent == null) {
            this.mParent = viewParent;
        } else {
            if (viewParent == null) {
                this.mParent = null;
                return;
            }
            throw new RuntimeException("view " + this + " being added, but it already has a parent");
        }
    }

    protected void onAttachedToWindow() {
        ViewParent viewParent = this.mParent;
        if (viewParent != null && (this.mPrivateFlags & 512) != 0) {
            viewParent.requestTransparentRegion(this);
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
        String str = sTraceRequestLayoutClass;
        if (str == null || !str.equals(getClass().getSimpleName())) {
            return;
        }
        setRelayoutTracingEnabled(true);
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

    void dispatchScreenStateChanged(int i) {
        onScreenStateChanged(i);
    }

    void dispatchMovedToDisplay(Display display, Configuration configuration) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mDisplay = display;
            this.mAttachInfo.mDisplayState = display.getState();
        }
        onMovedToDisplay(display.getDisplayId(), configuration);
    }

    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    private boolean isRtlCompatibilityMode() {
        return !hasRtlSupport();
    }

    private boolean needRtlPropertiesResolution() {
        return (this.mPrivateFlags2 & ALL_RTL_PROPERTIES_RESOLVED) != ALL_RTL_PROPERTIES_RESOLVED;
    }

    public boolean resolveLayoutDirection() {
        this.mPrivateFlags2 &= -49;
        if (hasRtlSupport()) {
            int i = this.mPrivateFlags2;
            int i2 = (i & 12) >> 2;
            if (i2 == 1) {
                this.mPrivateFlags2 = i | 16;
            } else if (i2 == 2) {
                if (!canResolveLayoutDirection()) {
                    return false;
                }
                try {
                    ViewParent viewParent = this.mParent;
                    if (viewParent == null || !viewParent.isLayoutDirectionResolved()) {
                        return false;
                    }
                    if (this.mParent.getLayoutDirection() == 1) {
                        this.mPrivateFlags2 |= 16;
                    }
                } catch (AbstractMethodError e) {
                    Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                }
            } else if (i2 == 3 && 1 == TextUtils.getLayoutDirectionFromLocale(Locale.getDefault())) {
                this.mPrivateFlags2 |= 16;
            }
        }
        this.mPrivateFlags2 |= 32;
        return true;
    }

    public boolean canResolveLayoutDirection() {
        if (getRawLayoutDirection() != 2) {
            return true;
        }
        ViewParent viewParent = this.mParent;
        if (viewParent == null) {
            return false;
        }
        try {
            return viewParent.canResolveLayoutDirection();
        } catch (AbstractMethodError e) {
            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            return false;
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
        int layoutDirection = getLayoutDirection();
        if (!isRtlCompatibilityMode()) {
            if (this.mBackground != null && (!this.mLeftPaddingDefined || !this.mRightPaddingDefined)) {
                ThreadLocal<Rect> threadLocal = sThreadLocal;
                Rect rect = threadLocal.get();
                if (rect == null) {
                    rect = new Rect();
                    threadLocal.set(rect);
                }
                this.mBackground.getPadding(rect);
                if (!this.mLeftPaddingDefined) {
                    this.mUserPaddingLeftInitial = rect.left;
                }
                if (!this.mRightPaddingDefined) {
                    this.mUserPaddingRightInitial = rect.right;
                }
            }
            if (layoutDirection == 1) {
                int i = this.mUserPaddingStart;
                if (i != Integer.MIN_VALUE) {
                    this.mUserPaddingRight = i;
                } else {
                    this.mUserPaddingRight = this.mUserPaddingRightInitial;
                }
                int i2 = this.mUserPaddingEnd;
                if (i2 != Integer.MIN_VALUE) {
                    this.mUserPaddingLeft = i2;
                } else {
                    this.mUserPaddingLeft = this.mUserPaddingLeftInitial;
                }
            } else {
                int i3 = this.mUserPaddingStart;
                if (i3 != Integer.MIN_VALUE) {
                    this.mUserPaddingLeft = i3;
                } else {
                    this.mUserPaddingLeft = this.mUserPaddingLeftInitial;
                }
                int i4 = this.mUserPaddingEnd;
                if (i4 != Integer.MIN_VALUE) {
                    this.mUserPaddingRight = i4;
                } else {
                    this.mUserPaddingRight = this.mUserPaddingRightInitial;
                }
            }
            int i5 = this.mUserPaddingBottom;
            if (i5 < 0) {
                i5 = this.mPaddingBottom;
            }
            this.mUserPaddingBottom = i5;
        }
        internalSetPadding(this.mUserPaddingLeft, this.mPaddingTop, this.mUserPaddingRight, this.mUserPaddingBottom);
        onRtlPropertiesChanged(layoutDirection);
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

    protected void onDetachedFromWindowInternal() {
        SemHoverPopupWindow semHoverPopupWindow;
        if (this.mHoverPopupType != 0 && (semHoverPopupWindow = this.mHoverPopup) != null) {
            semHoverPopupWindow.dismiss();
            this.mHoverPopup = null;
        }
        this.mPrivateFlags &= -67108865;
        this.mPrivateFlags3 &= -33554437;
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
        RenderNode renderNode = this.mBackgroundRenderNode;
        if (renderNode != null) {
            renderNode.forceEndAnimators();
        }
        this.mRenderNode.forceEndAnimators();
    }

    private void cleanupDraw() {
        resetDisplayList();
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.cancelInvalidate(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindowAttachCount() {
        return this.mWindowAttachCount;
    }

    public IBinder getWindowToken() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mWindowToken;
        }
        return null;
    }

    public WindowId getWindowId() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return null;
        }
        if (attachInfo.mWindowId == null) {
            try {
                attachInfo.mIWindowId = attachInfo.mSession.getWindowId(attachInfo.mWindowToken);
                if (attachInfo.mIWindowId != null) {
                    attachInfo.mWindowId = new WindowId(attachInfo.mIWindowId);
                }
            } catch (RemoteException unused) {
            }
        }
        return attachInfo.mWindowId;
    }

    public IBinder getApplicationWindowToken() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return null;
        }
        IBinder iBinder = attachInfo.mPanelParentWindowToken;
        return iBinder == null ? attachInfo.mWindowToken : iBinder;
    }

    public Display getDisplay() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mDisplay;
        }
        return null;
    }

    IWindowSession getWindowSession() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mSession;
        }
        return null;
    }

    protected IWindow getWindow() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mWindow;
        }
        return null;
    }

    int combineVisibility(int i, int i2) {
        return Math.max(i, i2);
    }

    public void fakeFocusAfterAttachingToWindow() {
        this.mShouldFakeFocus = true;
    }

    void dispatchAttachedToWindow(AttachInfo attachInfo, int i) {
        this.mAttachInfo = attachInfo;
        ViewOverlay viewOverlay = this.mOverlay;
        if (viewOverlay != null) {
            viewOverlay.getOverlayView().dispatchAttachedToWindow(attachInfo, i);
        }
        this.mWindowAttachCount++;
        this.mPrivateFlags |= 1024;
        if (this.mFloatingTreeObserver != null) {
            attachInfo.mTreeObserver.merge(this.mFloatingTreeObserver);
            this.mFloatingTreeObserver = null;
        }
        registerPendingFrameMetricsObservers();
        if ((this.mPrivateFlags & 524288) != 0) {
            this.mAttachInfo.mScrollContainers.add(this);
            this.mPrivateFlags |= 1048576;
        }
        HandlerActionQueue handlerActionQueue = this.mRunQueue;
        if (handlerActionQueue != null) {
            handlerActionQueue.executeActions(attachInfo.mHandler);
            this.mRunQueue = null;
        }
        performCollectViewAttributes(this.mAttachInfo, i);
        onAttachedToWindow();
        ListenerInfo listenerInfo = this.mListenerInfo;
        CopyOnWriteArrayList copyOnWriteArrayList = listenerInfo != null ? listenerInfo.mOnAttachStateChangeListeners : null;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                ((OnAttachStateChangeListener) it.next()).onViewAttachedToWindow(this);
            }
        }
        int i2 = attachInfo.mWindowVisibility;
        if (i2 != 8) {
            onWindowVisibilityChanged(i2);
            if (isShown()) {
                onVisibilityAggregated(i2 == 0);
            }
        }
        onVisibilityChanged(this, i);
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
        if (!this.mNeedToSendSavedStickyDragEvent || this.mParent == null) {
            return;
        }
        postRequestSendStickyDragStartedEvent();
    }

    void dispatchDetachedFromWindow() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mWindowVisibility != 8) {
            onWindowVisibilityChanged(8);
            if (isShown()) {
                onVisibilityAggregated(false);
            } else {
                notifyAutofillManagerViewVisibilityChanged(false);
            }
        }
        onDetachedFromWindow();
        onDetachedFromWindowInternal();
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.getImeFocusController().onViewDetachedFromWindow(this);
        }
        ListenerInfo listenerInfo = this.mListenerInfo;
        CopyOnWriteArrayList copyOnWriteArrayList = listenerInfo != null ? listenerInfo.mOnAttachStateChangeListeners : null;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                ((OnAttachStateChangeListener) it.next()).onViewDetachedFromWindow(this);
            }
        }
        if ((this.mPrivateFlags & 1048576) != 0) {
            this.mAttachInfo.mScrollContainers.remove(this);
            this.mPrivateFlags &= -1048577;
        }
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(false);
        updateSensitiveViewsCountIfNeeded(false);
        this.mAttachInfo = null;
        ViewOverlay viewOverlay = this.mOverlay;
        if (viewOverlay != null) {
            viewOverlay.getOverlayView().dispatchDetachedFromWindow();
        }
        notifyEnterOrExitForAutoFillIfNeeded(false);
        if (attachInfo != null && !collectPreferKeepClearRects().isEmpty()) {
            attachInfo.mViewRootImpl.updateKeepClearRectsForView(this);
        }
        clearBlurMode();
    }

    public final void cancelPendingInputEvents() {
        dispatchCancelPendingInputEvents();
    }

    void dispatchCancelPendingInputEvents() {
        this.mPrivateFlags3 &= -17;
        onCancelPendingInputEvents();
        if ((this.mPrivateFlags3 & 16) == 16) {
            return;
        }
        throw new SuperNotCalledException("View " + getClass().getSimpleName() + " did not call through to super.onCancelPendingInputEvents()");
    }

    public void onCancelPendingInputEvents() {
        removePerformClickCallback();
        cancelLongPress();
        this.mPrivateFlags3 |= 16;
    }

    public void saveHierarchyState(SparseArray<Parcelable> sparseArray) {
        dispatchSaveInstanceState(sparseArray);
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        if (this.mID == -1 || (this.mViewFlags & 65536) != 0) {
            return;
        }
        this.mPrivateFlags &= -131073;
        Parcelable onSaveInstanceState = onSaveInstanceState();
        if ((this.mPrivateFlags & 131072) == 0) {
            throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
        }
        if (onSaveInstanceState != null) {
            sparseArray.put(this.mID, onSaveInstanceState);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        this.mPrivateFlags |= 131072;
        if (this.mStartActivityRequestWho != null || isAutofilled() || this.mAutofillViewId > 1073741823) {
            BaseSavedState baseSavedState = new BaseSavedState(AbsSavedState.EMPTY_STATE);
            if (this.mStartActivityRequestWho != null) {
                baseSavedState.mSavedData |= 1;
            }
            if (isAutofilled()) {
                baseSavedState.mSavedData |= 2;
            }
            if (this.mAutofillViewId > 1073741823) {
                baseSavedState.mSavedData |= 4;
            }
            baseSavedState.mStartActivityRequestWhoSaved = this.mStartActivityRequestWho;
            baseSavedState.mIsAutofilled = isAutofilled();
            baseSavedState.mHideHighlight = hideAutofillHighlight();
            baseSavedState.mAutofillViewId = this.mAutofillViewId;
            return baseSavedState;
        }
        return BaseSavedState.EMPTY_STATE;
    }

    public void restoreHierarchyState(SparseArray<Parcelable> sparseArray) {
        dispatchRestoreInstanceState(sparseArray);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        Parcelable parcelable;
        int i = this.mID;
        if (i == -1 || (parcelable = sparseArray.get(i)) == null) {
            return;
        }
        this.mPrivateFlags &= -131073;
        onRestoreInstanceState(parcelable);
        if ((this.mPrivateFlags & 131072) == 0) {
            throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        this.mPrivateFlags |= 131072;
        if (parcelable != null && !(parcelable instanceof AbsSavedState)) {
            throw new IllegalArgumentException("Wrong state class, expecting View State but received " + parcelable.getClass().toString() + " instead. This usually happens when two views of different type have the same id in the same hierarchy. This view's id is " + ViewDebug.resolveId(this.mContext, getId()) + ". Make sure other views do not use the same id.");
        }
        if (parcelable == null || !(parcelable instanceof BaseSavedState)) {
            return;
        }
        BaseSavedState baseSavedState = (BaseSavedState) parcelable;
        if ((baseSavedState.mSavedData & 1) != 0) {
            this.mStartActivityRequestWho = baseSavedState.mStartActivityRequestWhoSaved;
        }
        if ((baseSavedState.mSavedData & 2) != 0) {
            setAutofilled(baseSavedState.mIsAutofilled, baseSavedState.mHideHighlight);
        }
        if ((baseSavedState.mSavedData & 4) != 0) {
            baseSavedState.mSavedData &= -5;
            if ((this.mPrivateFlags3 & 1073741824) != 0) {
                if (Log.isLoggable(AUTOFILL_LOG_TAG, 3)) {
                    Log.d(AUTOFILL_LOG_TAG, "onRestoreInstanceState(): not setting autofillId to " + baseSavedState.mAutofillViewId + " because view explicitly set it to " + this.mAutofillId);
                    return;
                }
                return;
            }
            this.mAutofillViewId = baseSavedState.mAutofillViewId;
            this.mAutofillId = null;
        }
    }

    public long getDrawingTime() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mDrawingTime;
        }
        return 0L;
    }

    public void setDuplicateParentStateEnabled(boolean z) {
        setFlags(z ? 4194304 : 0, 4194304);
    }

    public boolean isDuplicateParentStateEnabled() {
        return (this.mViewFlags & 4194304) == 4194304;
    }

    public void setLayerType(int i, Paint paint) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("Layer type can only be one of: LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE");
        }
        if (!this.mRenderNode.setLayerType(i)) {
            setLayerPaint(paint);
            return;
        }
        if (i != 1) {
            destroyDrawingCache();
        }
        this.mLayerType = i;
        if (i == 0) {
            paint = null;
        }
        this.mLayerPaint = paint;
        this.mRenderNode.setLayerPaint(paint);
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

    @ViewDebug.ExportedProperty(category = "drawing", mapping = {@ViewDebug.IntToString(from = 0, to = KeyProperties.DIGEST_NONE), @ViewDebug.IntToString(from = 1, to = "SOFTWARE"), @ViewDebug.IntToString(from = 2, to = "HARDWARE")})
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
        int i = this.mLayerType;
        if (i == 1) {
            buildDrawingCache(true);
            return;
        }
        if (i != 2) {
            return;
        }
        updateDisplayListIfDirty();
        if (attachInfo.mThreadedRenderer == null || !this.mRenderNode.hasDisplayList()) {
            return;
        }
        attachInfo.mThreadedRenderer.buildLayer(this.mRenderNode);
    }

    public boolean probablyHasInput() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return false;
        }
        return viewRootImpl.probablyHasInput();
    }

    protected void destroyHardwareResources() {
        ViewOverlay viewOverlay = this.mOverlay;
        if (viewOverlay != null) {
            viewOverlay.getOverlayView().destroyHardwareResources();
        }
        GhostView ghostView = this.mGhostView;
        if (ghostView != null) {
            ghostView.destroyHardwareResources();
        }
    }

    @Deprecated
    public void setDrawingCacheEnabled(boolean z) {
        this.mCachingFailed = false;
        setFlags(z ? 32768 : 0, 32768);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    @Deprecated
    public boolean isDrawingCacheEnabled() {
        return (this.mViewFlags & 32768) == 32768;
    }

    public void outputDirtyFlags(String str, boolean z, int i) {
        Log.d(VIEW_LOG_TAG, str + this + "             DIRTY(" + (this.mPrivateFlags & 2097152) + ") DRAWN(" + (this.mPrivateFlags & 32) + ") CACHE_VALID(" + (this.mPrivateFlags & 32768) + ") INVALIDATED(" + (this.mPrivateFlags & Integer.MIN_VALUE) + NavigationBarInflaterView.KEY_CODE_END);
        if (z) {
            this.mPrivateFlags &= i;
        }
        if (this instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) this;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                viewGroup.getChildAt(i2).outputDirtyFlags(str + "  ", z, i);
            }
        }
    }

    public boolean canHaveDisplayList() {
        AttachInfo attachInfo = this.mAttachInfo;
        return (attachInfo == null || attachInfo.mThreadedRenderer == null) ? false : true;
    }

    public RenderNode updateDisplayListIfDirty() {
        RenderNode renderNode = this.mRenderNode;
        if (!canHaveDisplayList()) {
            return renderNode;
        }
        if ((this.mPrivateFlags & 32768) == 0 || !renderNode.hasDisplayList() || this.mRecreateDisplayList) {
            if (renderNode.hasDisplayList() && !this.mRecreateDisplayList) {
                this.mPrivateFlags = (this.mPrivateFlags | 32800) & (-2097153);
                dispatchGetDisplayList();
                return renderNode;
            }
            this.mRecreateDisplayList = true;
            int i = this.mRight - this.mLeft;
            int i2 = this.mBottom - this.mTop;
            int layerType = getLayerType();
            renderNode.clearStretch();
            RecordingCanvas beginRecording = renderNode.beginRecording(i, i2);
            try {
                if (layerType == 1) {
                    buildDrawingCache(true);
                    Bitmap drawingCache = getDrawingCache(true);
                    if (drawingCache != null) {
                        beginRecording.drawBitmap(drawingCache, 0.0f, 0.0f, this.mLayerPaint);
                    }
                } else {
                    computeScroll();
                    beginRecording.translate(-this.mScrollX, -this.mScrollY);
                    this.mPrivateFlags = (this.mPrivateFlags | 32800) & (-2097153);
                    this.mPrivateFlags4 |= 134217728;
                    drawBlurEffect(beginRecording);
                    if ((this.mPrivateFlags & 128) == 128 && this.mGfxImageFilter == null) {
                        dispatchDraw(beginRecording);
                        if (this.mRoundedCornerMode != 0) {
                            semDrawRoundedCorner(beginRecording);
                        }
                        drawAutofilledHighlight(beginRecording);
                        ViewOverlay viewOverlay = this.mOverlay;
                        if (viewOverlay != null && !viewOverlay.isEmpty()) {
                            this.mOverlay.getOverlayView().draw(beginRecording);
                        }
                        if (isShowingLayoutBounds()) {
                            debugDrawFocus(beginRecording);
                        }
                    } else {
                        draw(beginRecording);
                    }
                    if (CoreRune.FW_VRR_DISCRETE && sToolkitSetFrameRateReadOnlyFlagValue && sToolkitFrameRateViewEnablingReadOnlyFlagValue) {
                        votePreferredFrameRate();
                    }
                }
            } finally {
                renderNode.endRecording();
                setDisplayListProperties(renderNode);
            }
        } else {
            if ((this.mPrivateFlags4 & 536870912) == 536870912) {
                if (CoreRune.FW_VRR_DISCRETE && sToolkitSetFrameRateReadOnlyFlagValue && sToolkitFrameRateViewEnablingReadOnlyFlagValue) {
                    votePreferredFrameRate();
                }
                this.mPrivateFlags4 &= -536870913;
            }
            this.mPrivateFlags = (this.mPrivateFlags | 32800) & (-2097153);
        }
        this.mPrivateFlags4 &= -268435457;
        this.mFrameContentVelocity = -1.0f;
        return renderNode;
    }

    private void resetDisplayList() {
        this.mRenderNode.discardDisplayList();
        RenderNode renderNode = this.mBackgroundRenderNode;
        if (renderNode != null) {
            renderNode.discardDisplayList();
        }
    }

    @Deprecated
    public Bitmap getDrawingCache() {
        return getDrawingCache(false);
    }

    @Deprecated
    public Bitmap getDrawingCache(boolean z) {
        int i = this.mViewFlags;
        if ((i & 131072) == 131072) {
            return null;
        }
        if ((i & 32768) == 32768) {
            buildDrawingCache(z);
        }
        return z ? this.mDrawingCache : this.mUnscaledDrawingCache;
    }

    @Deprecated
    public void destroyDrawingCache() {
        Bitmap bitmap = this.mDrawingCache;
        if (bitmap != null) {
            bitmap.recycle();
            this.mDrawingCache = null;
        }
        Bitmap bitmap2 = this.mUnscaledDrawingCache;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.mUnscaledDrawingCache = null;
        }
    }

    @Deprecated
    public void setDrawingCacheBackgroundColor(int i) {
        if (i != this.mDrawingCacheBackgroundColor) {
            this.mDrawingCacheBackgroundColor = i;
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
    public void buildDrawingCache(boolean z) {
        if ((this.mPrivateFlags & 32768) != 0) {
            if (z) {
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
            buildDrawingCacheImpl(z);
        } finally {
            Trace.traceEnd(8L);
        }
    }

    private void buildDrawingCacheImpl(boolean z) {
        Bitmap.Config config;
        Canvas canvas;
        this.mCachingFailed = false;
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        AttachInfo attachInfo = this.mAttachInfo;
        boolean z2 = true;
        boolean z3 = attachInfo != null && attachInfo.mScalingRequired;
        if (z && z3) {
            i = (int) ((i * attachInfo.mApplicationScale) + 0.5f);
            i2 = (int) ((i2 * attachInfo.mApplicationScale) + 0.5f);
        }
        int i3 = this.mDrawingCacheBackgroundColor;
        boolean z4 = i3 != 0 || isOpaque();
        boolean z5 = attachInfo != null && attachInfo.mUse32BitDrawingCache;
        long j = i * i2 * ((!z4 || z5) ? 4 : 2);
        long scaledMaximumDrawingCacheSize = ViewConfiguration.get(this.mContext).getScaledMaximumDrawingCacheSize();
        if (i <= 0 || i2 <= 0 || j > scaledMaximumDrawingCacheSize) {
            if (i > 0 && i2 > 0) {
                Log.w(VIEW_LOG_TAG, getClass().getSimpleName() + " not displayed because it is too large to fit into a software layer (or drawing cache), needs " + j + " bytes, only " + scaledMaximumDrawingCacheSize + " available");
            }
            destroyDrawingCache();
            this.mCachingFailed = true;
            return;
        }
        Bitmap bitmap = z ? this.mDrawingCache : this.mUnscaledDrawingCache;
        if (bitmap == null || bitmap.getWidth() != i || bitmap.getHeight() != i2) {
            if (!z4) {
                config = Bitmap.Config.ARGB_8888;
            } else {
                config = z5 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
            try {
                bitmap = Bitmap.createBitmap(this.mResources.getDisplayMetrics(), i, i2, config);
                bitmap.setDensity(getResources().getDisplayMetrics().densityDpi);
                if (z) {
                    this.mDrawingCache = bitmap;
                } else {
                    this.mUnscaledDrawingCache = bitmap;
                }
                if (z4 && z5) {
                    bitmap.setHasAlpha(false);
                }
                z2 = i3 != 0;
            } catch (OutOfMemoryError unused) {
                if (z) {
                    this.mDrawingCache = null;
                } else {
                    this.mUnscaledDrawingCache = null;
                }
                this.mCachingFailed = true;
                return;
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
        if (z2) {
            bitmap.eraseColor(i3);
        }
        computeScroll();
        int save = canvas.save();
        if (z && z3) {
            float f = attachInfo.mApplicationScale;
            canvas.scale(f, f);
        }
        canvas.translate(-this.mScrollX, -this.mScrollY);
        this.mPrivateFlags |= 32;
        AttachInfo attachInfo2 = this.mAttachInfo;
        if (attachInfo2 == null || !attachInfo2.mHardwareAccelerated || this.mLayerType != 0) {
            this.mPrivateFlags |= 32768;
        }
        int i4 = this.mPrivateFlags;
        if ((i4 & 128) == 128) {
            this.mPrivateFlags = i4 & (-2097153);
            dispatchDraw(canvas);
            drawAutofilledHighlight(canvas);
            ViewOverlay viewOverlay = this.mOverlay;
            if (viewOverlay != null && !viewOverlay.isEmpty()) {
                this.mOverlay.getOverlayView().draw(canvas);
            }
        } else {
            draw(canvas);
        }
        canvas.restoreToCount(save);
        canvas.setBitmap(null);
        if (attachInfo != null) {
            attachInfo.mCanvas = canvas;
        }
    }

    public Bitmap createSnapshot(ViewDebug.CanvasProvider canvasProvider, boolean z) {
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        AttachInfo attachInfo = this.mAttachInfo;
        float f = attachInfo != null ? attachInfo.mApplicationScale : 1.0f;
        int i3 = (int) ((i * f) + 0.5f);
        int i4 = (int) ((i2 * f) + 0.5f);
        if (i3 <= 0) {
            i3 = 1;
        }
        if (i4 <= 0) {
            i4 = 1;
        }
        Canvas canvas = null;
        try {
            Canvas canvas2 = canvasProvider.getCanvas(this, i3, i4);
            if (attachInfo != null) {
                Canvas canvas3 = attachInfo.mCanvas;
                try {
                    attachInfo.mCanvas = null;
                    canvas = canvas3;
                } catch (Throwable th) {
                    th = th;
                    canvas = canvas3;
                    if (canvas != null) {
                        attachInfo.mCanvas = canvas;
                    }
                    throw th;
                }
            }
            computeScroll();
            int save = canvas2.save();
            canvas2.scale(f, f);
            canvas2.translate(-this.mScrollX, -this.mScrollY);
            int i5 = this.mPrivateFlags;
            this.mPrivateFlags = (-2097153) & i5;
            if ((i5 & 128) == 128) {
                dispatchDraw(canvas2);
                drawAutofilledHighlight(canvas2);
                ViewOverlay viewOverlay = this.mOverlay;
                if (viewOverlay != null && !viewOverlay.isEmpty()) {
                    this.mOverlay.getOverlayView().draw(canvas2);
                }
            } else {
                draw(canvas2);
            }
            this.mPrivateFlags = i5;
            canvas2.restoreToCount(save);
            Bitmap createBitmap = canvasProvider.createBitmap();
            if (canvas != null) {
                attachInfo.mCanvas = canvas;
            }
            return createBitmap;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    protected int getFadeTop(boolean z) {
        int i = this.mPaddingTop;
        return z ? i + getTopPaddingOffset() : i;
    }

    protected int getFadeHeight(boolean z) {
        int i = this.mPaddingTop;
        if (z) {
            i += getTopPaddingOffset();
        }
        return ((this.mBottom - this.mTop) - this.mPaddingBottom) - i;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean isHardwareAccelerated() {
        AttachInfo attachInfo = this.mAttachInfo;
        return attachInfo != null && attachInfo.mHardwareAccelerated;
    }

    public void setClipBounds(Rect rect) {
        Rect rect2 = this.mClipBounds;
        if (rect != rect2) {
            if (rect == null || !rect.equals(rect2)) {
                if (rect != null) {
                    Rect rect3 = this.mClipBounds;
                    if (rect3 == null) {
                        this.mClipBounds = new Rect(rect);
                    } else {
                        rect3.set(rect);
                    }
                } else {
                    this.mClipBounds = null;
                }
                this.mRenderNode.setClipRect(this.mClipBounds);
                invalidateViewProperty(false, false);
            }
        }
    }

    public Rect getClipBounds() {
        if (this.mClipBounds != null) {
            return new Rect(this.mClipBounds);
        }
        return null;
    }

    public boolean getClipBounds(Rect rect) {
        Rect rect2 = this.mClipBounds;
        if (rect2 == null) {
            return false;
        }
        rect.set(rect2);
        return true;
    }

    private boolean applyLegacyAnimation(ViewGroup viewGroup, long j, Animation animation, boolean z) {
        Transformation transformation;
        int i = viewGroup.mGroupFlags;
        if (!animation.isInitialized()) {
            animation.initialize(this.mRight - this.mLeft, this.mBottom - this.mTop, viewGroup.getWidth(), viewGroup.getHeight());
            animation.initializeInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                animation.setListenerHandler(attachInfo.mHandler);
            }
            onAnimationStart();
        }
        Transformation childTransformation = viewGroup.getChildTransformation();
        boolean transformation2 = animation.getTransformation(j, childTransformation, 1.0f);
        if (!z || this.mAttachInfo.mApplicationScale == 1.0f) {
            transformation = childTransformation;
        } else {
            if (viewGroup.mInvalidationTransformation == null) {
                viewGroup.mInvalidationTransformation = new Transformation();
            }
            transformation = viewGroup.mInvalidationTransformation;
            animation.getTransformation(j, transformation, 1.0f);
        }
        if (sToolkitFrameRateAnimationBugfix25q1FlagValue && (childTransformation.getTransformationType() & 2) != 0) {
            this.mPrivateFlags4 |= 805306368;
        }
        if (transformation2) {
            if (animation.willChangeBounds()) {
                if (viewGroup.mInvalidateRegion == null) {
                    viewGroup.mInvalidateRegion = new RectF();
                }
                RectF rectF = viewGroup.mInvalidateRegion;
                animation.getInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, rectF, transformation);
                viewGroup.mPrivateFlags |= 64;
                int i2 = this.mLeft + ((int) rectF.left);
                int i3 = this.mTop + ((int) rectF.top);
                viewGroup.invalidate(i2, i3, ((int) (rectF.width() + 0.5f)) + i2, ((int) (rectF.height() + 0.5f)) + i3);
            } else {
                if ((i & 144) == 128) {
                    viewGroup.mGroupFlags |= 4;
                    return transformation2;
                }
                if ((i & 4) == 0) {
                    viewGroup.mPrivateFlags |= 64;
                    viewGroup.invalidate(this.mLeft, this.mTop, this.mRight, this.mBottom);
                    return transformation2;
                }
            }
        }
        return transformation2;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void setDisplayListProperties(android.graphics.RenderNode r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L7c
            boolean r0 = r4.getHasOverlappingRendering()
            r5.setHasOverlappingRendering(r0)
            android.view.ViewParent r0 = r4.mParent
            boolean r1 = r0 instanceof android.view.ViewGroup
            if (r1 == 0) goto L19
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            boolean r0 = r0.getClipChildren()
            if (r0 == 0) goto L19
            r0 = 1
            goto L1a
        L19:
            r0 = 0
        L1a:
            r5.setClipToBounds(r0)
            android.view.ViewParent r0 = r4.mParent
            boolean r1 = r0 instanceof android.view.ViewGroup
            r2 = 1065353216(0x3f800000, float:1.0)
            if (r1 == 0) goto L57
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            int r0 = r0.mGroupFlags
            r0 = r0 & 2048(0x800, float:2.87E-42)
            if (r0 == 0) goto L57
            android.view.ViewParent r0 = r4.mParent
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.animation.Transformation r1 = r0.getChildTransformation()
            boolean r0 = r0.getChildStaticTransformation(r4, r1)
            if (r0 == 0) goto L57
            int r0 = r1.getTransformationType()
            if (r0 == 0) goto L57
            r3 = r0 & 1
            if (r3 == 0) goto L4a
            float r3 = r1.getAlpha()
            goto L4b
        L4a:
            r3 = r2
        L4b:
            r0 = r0 & 2
            if (r0 == 0) goto L58
            android.graphics.Matrix r0 = r1.getMatrix()
            r5.setStaticMatrix(r0)
            goto L58
        L57:
            r3 = r2
        L58:
            android.view.View$TransformationInfo r0 = r4.mTransformationInfo
            if (r0 == 0) goto L75
            float r0 = r4.getFinalAlpha()
            float r3 = r3 * r0
            int r0 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r0 >= 0) goto L70
            r0 = 1132396544(0x437f0000, float:255.0)
            float r0 = r0 * r3
            int r0 = (int) r0
            boolean r4 = r4.onSetAlpha(r0)
            if (r4 == 0) goto L70
            goto L71
        L70:
            r2 = r3
        L71:
            r5.setAlpha(r2)
            return
        L75:
            int r4 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r4 >= 0) goto L7c
            r5.setAlpha(r3)
        L7c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.setDisplayListProperties(android.graphics.RenderNode):void");
    }

    protected final boolean drawsWithRenderNode(Canvas canvas) {
        AttachInfo attachInfo = this.mAttachInfo;
        return attachInfo != null && attachInfo.mHardwareAccelerated && canvas.isHardwareAccelerated();
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0359  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean draw(android.graphics.Canvas r29, android.view.ViewGroup r30, long r31) {
        /*
            Method dump skipped, instructions count: 906
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.draw(android.graphics.Canvas, android.view.ViewGroup, long):boolean");
    }

    static Paint getDebugPaint() {
        if (sDebugPaint == null) {
            Paint paint = new Paint();
            sDebugPaint = paint;
            paint.setAntiAlias(false);
        }
        return sDebugPaint;
    }

    final int dipsToPixels(int i) {
        return (int) ((i * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void debugDrawFocus(Canvas canvas) {
        if (isFocused()) {
            int dipsToPixels = dipsToPixels(8);
            int i = this.mScrollX;
            int i2 = (this.mRight + i) - this.mLeft;
            int i3 = this.mScrollY;
            int i4 = (this.mBottom + i3) - this.mTop;
            Paint debugPaint = getDebugPaint();
            debugPaint.setColor(DEBUG_CORNERS_COLOR);
            debugPaint.setStyle(Paint.Style.FILL);
            float f = i;
            float f2 = i3;
            float f3 = i + dipsToPixels;
            float f4 = i3 + dipsToPixels;
            canvas.drawRect(f, f2, f3, f4, debugPaint);
            float f5 = i2 - dipsToPixels;
            float f6 = i2;
            canvas.drawRect(f5, f2, f6, f4, debugPaint);
            float f7 = i4 - dipsToPixels;
            float f8 = i4;
            canvas.drawRect(f, f7, f3, f8, debugPaint);
            canvas.drawRect(f5, f7, f6, f8, debugPaint);
            debugPaint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(f, f2, f6, f8, debugPaint);
            canvas.drawLine(f, f8, f6, f2, debugPaint);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0219  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r28) {
        /*
            Method dump skipped, instructions count: 618
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.draw(android.graphics.Canvas):void");
    }

    private void drawBackground(Canvas canvas) {
        int i;
        AttachInfo attachInfo;
        Drawable drawable = this.mBackground;
        if (drawable == null) {
            if (isBlurDebug()) {
                Log.i(VIEW_LOG_TAG, "return mBackground is null, this=" + this);
                return;
            }
            return;
        }
        int i2 = 0;
        if (getLastBackgroundResource() == 17304907 && this.mContext.getResources().getAssets().getSamsungThemeOverlays().size() > 0) {
            int i3 = this.mRight - this.mLeft;
            int i4 = this.mBottom - this.mTop;
            if (i3 > i4) {
                i = (i3 - i4) / 2;
            } else {
                i2 = (i4 - i3) / 2;
                i3 = i4;
                i = 0;
            }
            setBackgroundBounds(i3);
        } else {
            setBackgroundBounds();
            i = 0;
        }
        if (canvas.isHardwareAccelerated() && (attachInfo = this.mAttachInfo) != null && attachInfo.mThreadedRenderer != null) {
            RenderNode drawableRenderNode = getDrawableRenderNode(drawable, this.mBackgroundRenderNode);
            this.mBackgroundRenderNode = drawableRenderNode;
            if (drawableRenderNode != null && drawableRenderNode.hasDisplayList()) {
                setBackgroundRenderNodeProperties(drawableRenderNode);
                if ((i2 | i) != 0) {
                    canvas.translate(-i2, -i);
                    ((RecordingCanvas) canvas).drawRenderNode(drawableRenderNode);
                    canvas.translate(i2, i);
                    return;
                }
                ((RecordingCanvas) canvas).drawRenderNode(drawableRenderNode);
                return;
            }
        }
        if ((this.mScrollX | this.mScrollY | i2 | i) == 0) {
            drawable.draw(canvas);
            return;
        }
        canvas.translate((-i2) + r1, (-i) + r7);
        drawable.draw(canvas);
        canvas.translate((-r1) + i2, (-r7) + i);
    }

    void setBackgroundBounds() {
        Drawable drawable;
        if (!this.mBackgroundSizeChanged || (drawable = this.mBackground) == null) {
            return;
        }
        drawable.setBounds(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
        this.mBackgroundSizeChanged = false;
        rebuildOutline();
    }

    private void setBackgroundRenderNodeProperties(RenderNode renderNode) {
        renderNode.setTranslationX(this.mScrollX);
        renderNode.setTranslationY(this.mScrollY);
    }

    private RenderNode getDrawableRenderNode(Drawable drawable, RenderNode renderNode) {
        if (renderNode == null) {
            renderNode = RenderNode.create(drawable.getClass().getName(), new ViewAnimationHostBridge(this));
            renderNode.setUsageHint(1);
        }
        Rect bounds = drawable.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        renderNode.clearStretch();
        RecordingCanvas beginRecording = renderNode.beginRecording(width, height);
        beginRecording.translate(-bounds.left, -bounds.top);
        try {
            drawable.draw(beginRecording);
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

    private static String printFlags(int i) {
        String str;
        char c = 1;
        if ((i & 1) != 1) {
            str = "";
            c = 0;
        } else {
            str = "TAKES_FOCUS";
        }
        int i2 = i & 12;
        if (i2 == 4) {
            if (c > 0) {
                str = str + " ";
            }
            return str + "INVISIBLE";
        }
        if (i2 != 8) {
            return str;
        }
        if (c > 0) {
            str = str + " ";
        }
        return str + "GONE";
    }

    private static String printPrivateFlags(int i) {
        String str;
        int i2 = 1;
        if ((i & 1) != 1) {
            str = "";
            i2 = 0;
        } else {
            str = "WANTS_FOCUS";
        }
        if ((i & 2) == 2) {
            if (i2 > 0) {
                str = str + " ";
            }
            str = str + "FOCUSED";
            i2++;
        }
        if ((i & 4) == 4) {
            if (i2 > 0) {
                str = str + " ";
            }
            str = str + "SELECTED";
            i2++;
        }
        if ((i & 8) == 8) {
            if (i2 > 0) {
                str = str + " ";
            }
            str = str + "IS_ROOT_NAMESPACE";
            i2++;
        }
        if ((i & 16) == 16) {
            if (i2 > 0) {
                str = str + " ";
            }
            str = str + "HAS_BOUNDS";
            i2++;
        }
        if ((i & 32) != 32) {
            return str;
        }
        if (i2 > 0) {
            str = str + " ";
        }
        return str + "DRAWN";
    }

    public boolean isLayoutRequested() {
        return (this.mPrivateFlags & 4096) == 4096;
    }

    public static boolean isLayoutModeOptical(Object obj) {
        return (obj instanceof ViewGroup) && ((ViewGroup) obj).isLayoutModeOptical();
    }

    public static void setTraceLayoutSteps(boolean z) {
        sTraceLayoutSteps = z;
    }

    public static void setTracedRequestLayoutClassClass(String str) {
        sTraceRequestLayoutClass = str;
    }

    private boolean setOpticalFrame(int i, int i2, int i3, int i4) {
        Object obj = this.mParent;
        Insets opticalInsets = obj instanceof View ? ((View) obj).getOpticalInsets() : Insets.NONE;
        Insets opticalInsets2 = getOpticalInsets();
        return setFrame((i + opticalInsets.left) - opticalInsets2.left, (i2 + opticalInsets.top) - opticalInsets2.top, i3 + opticalInsets.left + opticalInsets2.right, i4 + opticalInsets.top + opticalInsets2.bottom);
    }

    public void layout(int i, int i2, int i3, int i4) {
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
        int i5 = this.mLeft;
        int i6 = this.mTop;
        int i7 = this.mBottom;
        int i8 = this.mRight;
        boolean opticalFrame = isLayoutModeOptical(this.mParent) ? setOpticalFrame(i, i2, i3, i4) : setFrame(i, i2, i3, i4);
        if (opticalFrame || (this.mPrivateFlags & 8192) == 8192) {
            if (isTraversalTracingEnabled()) {
                Trace.beginSection(this.mTracingStrings.onLayout);
            }
            onLayout(opticalFrame, i, i2, i3, i4);
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
            ListenerInfo listenerInfo = this.mListenerInfo;
            if (listenerInfo != null && listenerInfo.mOnLayoutChangeListeners != null) {
                ArrayList arrayList = (ArrayList) listenerInfo.mOnLayoutChangeListeners.clone();
                int size = arrayList.size();
                for (int i9 = 0; i9 < size; i9++) {
                    ((OnLayoutChangeListener) arrayList.get(i9)).onLayoutChange(this, i, i2, i3, i4, i5, i6, i8, i7);
                }
            }
        }
        boolean isLayoutValid = isLayoutValid();
        this.mPrivateFlags &= -4097;
        this.mPrivateFlags3 |= 4;
        if (!isLayoutValid && isFocused()) {
            this.mPrivateFlags &= -2;
            if (canTakeFocus()) {
                clearParentsWantFocus();
            } else if (getViewRootImpl() == null || !getViewRootImpl().isInLayout()) {
                clearFocusInternal(null, true, false);
                clearParentsWantFocus();
            } else if (!hasParentWantsFocus()) {
                clearFocusInternal(null, true, false);
            }
        } else {
            int i10 = this.mPrivateFlags;
            if ((i10 & 1) != 0) {
                this.mPrivateFlags = i10 & (-2);
                View findFocus = findFocus();
                if (findFocus != null && !restoreDefaultFocus() && !hasParentWantsFocus()) {
                    findFocus.clearFocusInternal(null, true, false);
                }
            }
        }
        int i11 = this.mPrivateFlags3;
        if ((134217728 & i11) != 0) {
            this.mPrivateFlags3 = i11 & (-134217729);
            notifyEnterOrExitForAutoFillIfNeeded(true);
        }
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
    }

    private boolean hasParentWantsFocus() {
        ViewParent viewParent = this.mParent;
        while (viewParent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) viewParent;
            if ((viewGroup.mPrivateFlags & 1) != 0) {
                return true;
            }
            viewParent = viewGroup.mParent;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean setFrame(int i, int i2, int i3, int i4) {
        int i5 = this.mLeft;
        if (i5 == i && this.mRight == i3 && this.mTop == i2 && this.mBottom == i4) {
            return false;
        }
        int i6 = this.mPrivateFlags & 32;
        int i7 = this.mRight - i5;
        int i8 = this.mBottom - this.mTop;
        int i9 = i3 - i;
        int i10 = i4 - i2;
        boolean z = (i9 == i7 && i10 == i8) ? false : true;
        invalidate(z);
        this.mLeft = i;
        this.mTop = i2;
        this.mRight = i3;
        this.mBottom = i4;
        this.mRenderNode.setLeftTopRightBottom(i, i2, i3, i4);
        this.mPrivateFlags |= 16;
        if (z) {
            sizeChange(i9, i10, i7, i8);
        }
        if ((this.mViewFlags & 12) == 0 || this.mGhostView != null) {
            this.mPrivateFlags |= 32;
            invalidate(z);
            invalidateParentCaches();
        }
        this.mPrivateFlags |= i6;
        this.mBackgroundSizeChanged = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo != null) {
            foregroundInfo.mBoundsChanged = true;
        }
        notifySubtreeAccessibilityStateChangedIfNeeded();
        return true;
    }

    protected boolean semSetFrame(int i, int i2, int i3, int i4) {
        return setFrame(i, i2, i3, i4);
    }

    public final void setLeftTopRightBottom(int i, int i2, int i3, int i4) {
        setFrame(i, i2, i3, i4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x002b, code lost:
    
        if (r6 > r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0018, code lost:
    
        if (((r5 * r6) / r0.mDisplayPixelCount) <= android.view.View.FRAME_RATE_SIZE_PERCENTAGE_THRESHOLD) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void sizeChange(int r5, int r6, int r7, int r8) {
        /*
            r4 = this;
            android.view.View$AttachInfo r0 = r4.mAttachInfo
            if (r0 == 0) goto L4b
            boolean r1 = android.view.View.sToolkitFrameRateViewEnablingReadOnlyFlagValue
            if (r1 == 0) goto L4b
            boolean r1 = android.view.View.sToolkitFrameRateSmallUsesPercentReadOnlyFlagValue
            r2 = 3
            if (r1 == 0) goto L1b
            int r1 = r5 * r6
            float r1 = (float) r1
            float r0 = r0.mDisplayPixelCount
            float r1 = r1 / r0
            r0 = 1032805417(0x3d8f5c29, float:0.07)
            int r0 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r0 > 0) goto L2e
            goto L3a
        L1b:
            float r0 = r0.mDensity
            r1 = 1092616192(0x41200000, float:10.0)
            float r1 = r1 * r0
            int r1 = (int) r1
            r3 = 1109393408(0x42200000, float:40.0)
            float r0 = r0 * r3
            int r0 = (int) r0
            if (r5 <= r1) goto L3a
            if (r6 <= r1) goto L3a
            if (r5 > r0) goto L2e
            if (r6 > r0) goto L2e
            goto L3a
        L2e:
            boolean r0 = android.view.View.sToolkitFrameRateDefaultNormalReadOnlyFlagValue
            if (r0 == 0) goto L33
            goto L34
        L33:
            r2 = 6
        L34:
            r0 = 50331648(0x3000000, float:3.761582E-37)
            r0 = r0 | r2
            r4.mSizeBasedFrameRateCategoryAndReason = r0
            goto L44
        L3a:
            boolean r0 = android.view.View.sToolkitFrameRateBySizeReadOnlyFlagValue
            if (r0 == 0) goto L3f
            r2 = 2
        L3f:
            r0 = 16777216(0x1000000, float:2.3509887E-38)
            r0 = r0 | r2
            r4.mSizeBasedFrameRateCategoryAndReason = r0
        L44:
            int r0 = r4.mPrivateFlags4
            r1 = 268435456(0x10000000, float:2.524355E-29)
            r0 = r0 | r1
            r4.mPrivateFlags4 = r0
        L4b:
            r4.onSizeChanged(r5, r6, r7, r8)
            android.view.ViewOverlay r0 = r4.mOverlay
            if (r0 == 0) goto L62
            android.view.ViewGroup r0 = r0.getOverlayView()
            r0.setRight(r5)
            android.view.ViewOverlay r0 = r4.mOverlay
            android.view.ViewGroup r0 = r0.getOverlayView()
            r0.setBottom(r6)
        L62:
            boolean r0 = android.view.View.sCanFocusZeroSized
            if (r0 != 0) goto Laa
            boolean r0 = r4.isLayoutValid()
            if (r0 == 0) goto Laa
            android.view.ViewParent r0 = r4.mParent
            boolean r1 = r0 instanceof android.view.ViewGroup
            if (r1 == 0) goto L7a
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            boolean r0 = r0.isLayoutSuppressed()
            if (r0 != 0) goto Laa
        L7a:
            if (r5 <= 0) goto L93
            if (r6 > 0) goto L7f
            goto L93
        L7f:
            if (r7 <= 0) goto L83
            if (r8 > 0) goto Laa
        L83:
            android.view.ViewParent r7 = r4.mParent
            if (r7 == 0) goto Laa
            boolean r7 = r4.canTakeFocus()
            if (r7 == 0) goto Laa
            android.view.ViewParent r7 = r4.mParent
            r7.focusableViewAvailable(r4)
            goto Laa
        L93:
            boolean r7 = r4.hasFocus()
            if (r7 == 0) goto La7
            r4.clearFocus()
            android.view.ViewParent r7 = r4.mParent
            boolean r8 = r7 instanceof android.view.ViewGroup
            if (r8 == 0) goto La7
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            r7.clearFocusedInCluster()
        La7:
            r4.clearAccessibilityFocus()
        Laa:
            r4.rebuildOutline()
            boolean r7 = r4.onCheckIsTextEditor()
            if (r7 != 0) goto Lb9
            java.lang.Runnable r7 = r4.mHandwritingDelegatorCallback
            if (r7 == 0) goto Lb8
            goto Lb9
        Lb8:
            return
        Lb9:
            android.graphics.Rect r7 = new android.graphics.Rect
            r8 = 0
            r7.<init>(r8, r8, r5, r6)
            r4.setHandwritingArea(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.sizeChange(int, int, int, int):void");
    }

    public Resources getResources() {
        return this.mResources;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (verifyDrawable(drawable)) {
            Rect dirtyBounds = drawable.getDirtyBounds();
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            invalidate(dirtyBounds.left + i, dirtyBounds.top + i2, dirtyBounds.right + i, dirtyBounds.bottom + i2);
            rebuildOutline();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (!verifyDrawable(drawable) || runnable == null) {
            return;
        }
        long uptimeMillis = j - SystemClock.uptimeMillis();
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, runnable, drawable, Choreographer.subtractFrameDelay(uptimeMillis));
        } else {
            getRunQueue().postDelayed(runnable, uptimeMillis);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        if (!verifyDrawable(drawable) || runnable == null) {
            return;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, runnable, drawable);
        }
        getRunQueue().removeCallbacks(runnable);
    }

    public void unscheduleDrawable(Drawable drawable) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null || drawable == null) {
            return;
        }
        attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, null, drawable);
    }

    protected void resolveDrawables() {
        if (isLayoutDirectionResolved() || getRawLayoutDirection() != 2) {
            int layoutDirection = isLayoutDirectionResolved() ? getLayoutDirection() : getRawLayoutDirection();
            Drawable drawable = this.mBackground;
            if (drawable != null) {
                drawable.setLayoutDirection(layoutDirection);
            }
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null && foregroundInfo.mDrawable != null) {
                this.mForegroundInfo.mDrawable.setLayoutDirection(layoutDirection);
            }
            Drawable drawable2 = this.mDefaultFocusHighlight;
            if (drawable2 != null) {
                drawable2.setLayoutDirection(layoutDirection);
            }
            this.mPrivateFlags2 |= 1073741824;
            onResolveDrawables(layoutDirection);
        }
    }

    boolean areDrawablesResolved() {
        return (this.mPrivateFlags2 & 1073741824) == 1073741824;
    }

    protected void resetResolvedDrawables() {
        resetResolvedDrawablesInternal();
    }

    void resetResolvedDrawablesInternal() {
        this.mPrivateFlags2 &= -1073741825;
    }

    protected boolean verifyDrawable(Drawable drawable) {
        if (drawable == this.mBackground) {
            return true;
        }
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        return (foregroundInfo != null && foregroundInfo.mDrawable == drawable) || this.mDefaultFocusHighlight == drawable;
    }

    protected void drawableStateChanged() {
        ScrollBarDrawable scrollBarDrawable;
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mBackground;
        boolean z = false;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.mDefaultFocusHighlight;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        Drawable drawable3 = foregroundInfo != null ? foregroundInfo.mDrawable : null;
        if (drawable3 != null && drawable3.isStateful()) {
            state |= drawable3.setState(drawableState);
        }
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null && (scrollBarDrawable = scrollabilityCache.scrollBar) != null && scrollBarDrawable.isStateful()) {
            if (scrollBarDrawable.setState(drawableState) && this.mScrollCache.state != 0) {
                z = true;
            }
            state |= z;
        }
        StateListAnimator stateListAnimator = this.mStateListAnimator;
        if (stateListAnimator != null) {
            stateListAnimator.setState(drawableState);
        }
        if (!isAggregatedVisible()) {
            jumpDrawablesToCurrentState();
        }
        if (state) {
            invalidate();
        }
    }

    public void drawableHotspotChanged(float f, float f2) {
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
        Drawable drawable2 = this.mDefaultFocusHighlight;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo != null && foregroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.setHotspot(f, f2);
        }
        dispatchDrawableHotspotChanged(f, f2);
    }

    public void refreshDrawableState() {
        this.mPrivateFlags |= 1024;
        drawableStateChanged();
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.childDrawableStateChanged(this);
        }
    }

    boolean isCarLifeDisplay() {
        return (getDisplay() == null || (getDisplay().getFlags() & 1048576) == 0) ? false : true;
    }

    private Drawable getDefaultFocusHighlightDrawable() {
        if (this.mDefaultFocusHighlightCache == null && this.mContext != null) {
            if (CoreRune.BAIDU_CARLIFE && isCarLifeDisplay()) {
                this.mDefaultFocusHighlightCache = this.mContext.getDrawable(R.drawable.carlife_selector_background_focused);
            } else {
                TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16843534});
                this.mDefaultFocusHighlightCache = obtainStyledAttributes.getDrawable(0);
                obtainStyledAttributes.recycle();
            }
        }
        return this.mDefaultFocusHighlightCache;
    }

    private void setDefaultFocusHighlight(Drawable drawable) {
        ForegroundInfo foregroundInfo;
        this.mDefaultFocusHighlight = drawable;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (drawable != null) {
            int i = this.mPrivateFlags;
            if ((i & 128) != 0) {
                this.mPrivateFlags = i & PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            if (isAttachedToWindow()) {
                drawable.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
            drawable.setCallback(this);
        } else if ((this.mViewFlags & 128) != 0 && this.mBackground == null && ((foregroundInfo = this.mForegroundInfo) == null || foregroundInfo.mDrawable == null)) {
            this.mPrivateFlags |= 128;
        }
        invalidate();
    }

    public boolean isDefaultFocusHighlightNeeded(Drawable drawable, Drawable drawable2) {
        return !isInTouchMode() && getDefaultFocusHighlightEnabled() && ((drawable == null || !drawable.isStateful() || !drawable.hasFocusStateSpecified()) && (drawable2 == null || !drawable2.isStateful() || !drawable2.hasFocusStateSpecified())) && isAttachedToWindow() && sUseDefaultFocusHighlight;
    }

    private void switchDefaultFocusHighlight() {
        if (isFocused()) {
            Drawable drawable = this.mBackground;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            boolean isDefaultFocusHighlightNeeded = isDefaultFocusHighlightNeeded(drawable, foregroundInfo == null ? null : foregroundInfo.mDrawable);
            boolean z = this.mDefaultFocusHighlight != null;
            if (isDefaultFocusHighlightNeeded && !z) {
                setDefaultFocusHighlight(getDefaultFocusHighlightDrawable());
            } else {
                if (isDefaultFocusHighlightNeeded || !z) {
                    return;
                }
                setDefaultFocusHighlight(null);
            }
        }
    }

    private void drawDefaultFocusHighlight(Canvas canvas) {
        if (this.mDefaultFocusHighlight == null || !isFocused()) {
            return;
        }
        if (this.mDefaultFocusHighlightSizeChanged) {
            this.mDefaultFocusHighlightSizeChanged = false;
            int i = this.mScrollX;
            int i2 = (this.mRight + i) - this.mLeft;
            int i3 = this.mScrollY;
            this.mDefaultFocusHighlight.setBounds(i, i3, i2, (this.mBottom + i3) - this.mTop);
        }
        this.mDefaultFocusHighlight.draw(canvas);
    }

    public final int[] getDrawableState() {
        int[] iArr = this.mDrawableState;
        if (iArr != null && (this.mPrivateFlags & 1024) == 0) {
            return iArr;
        }
        int[] onCreateDrawableState = onCreateDrawableState(0);
        this.mDrawableState = onCreateDrawableState;
        this.mPrivateFlags &= -1025;
        return onCreateDrawableState;
    }

    protected int[] onCreateDrawableState(int i) {
        int i2 = this.mViewFlags;
        if ((i2 & 4194304) == 4194304) {
            Object obj = this.mParent;
            if (obj instanceof View) {
                return ((View) obj).onCreateDrawableState(i);
            }
        }
        int i3 = this.mPrivateFlags;
        int i4 = (i3 & 16384) != 0 ? 16 : 0;
        if ((i2 & 32) == 0) {
            i4 |= 8;
        }
        if (isFocused()) {
            i4 |= 4;
        }
        if ((i3 & 4) != 0) {
            i4 |= 2;
        }
        if (hasWindowFocus()) {
            i4 |= 1;
        }
        if ((1073741824 & i3) != 0) {
            i4 |= 32;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mHardwareAccelerationRequested && !CoreRune.GFW_DEBUG_DISABLE_HWRENDERING) {
            i4 |= 64;
        }
        if ((268435456 & i3) != 0) {
            i4 |= 128;
        }
        int i5 = this.mPrivateFlags2;
        if ((i5 & 1) != 0) {
            i4 |= 256;
        }
        if ((i5 & 2) != 0) {
            i4 |= 512;
        }
        if (CoreRune.FW_SPEN_HOVER && (this.mSemViewFlags & 1) != 0) {
            i4 |= 1024;
        }
        int[] iArr = StateSet.get(i4);
        if (i == 0) {
            return iArr;
        }
        if (iArr != null) {
            int[] iArr2 = new int[iArr.length + i];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            return iArr2;
        }
        return new int[i];
    }

    protected static int[] mergeDrawableStates(int[] iArr, int[] iArr2) {
        int length = iArr.length - 1;
        while (length >= 0 && iArr[length] == 0) {
            length--;
        }
        System.arraycopy(iArr2, 0, iArr, length + 1, iArr2.length);
        return iArr;
    }

    public void jumpDrawablesToCurrentState() {
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        StateListAnimator stateListAnimator = this.mStateListAnimator;
        if (stateListAnimator != null) {
            stateListAnimator.jumpToCurrentState();
        }
        Drawable drawable2 = this.mDefaultFocusHighlight;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo == null || foregroundInfo.mDrawable == null) {
            return;
        }
        this.mForegroundInfo.mDrawable.jumpToCurrentState();
    }

    @RemotableViewMethod
    public void setBackgroundColor(int i) {
        Drawable drawable = this.mBackground;
        if (drawable instanceof ColorDrawable) {
            ((ColorDrawable) drawable.mutate()).setColor(i);
            computeOpaqueFlags();
            this.mBackgroundResource = 0;
            idsUiUpdated(1);
            return;
        }
        setBackground(new ColorDrawable(i));
    }

    @RemotableViewMethod
    public void setBackgroundResource(int i) {
        if (i == 0 || i != this.mBackgroundResource) {
            setBackground(i != 0 ? this.mContext.getDrawable(i) : null);
            this.mBackgroundResource = i;
        }
    }

    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0113  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setBackgroundDrawable(android.graphics.drawable.Drawable r7) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.setBackgroundDrawable(android.graphics.drawable.Drawable):void");
    }

    private void idsUiUpdated(int i) {
        ActivityThread.currentActivityThread().getIdsController().uiUpdated(i);
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    @RemotableViewMethod
    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = colorStateList;
        this.mBackgroundTint.mHasTintList = true;
        applyBackgroundTint();
    }

    public ColorStateList getBackgroundTintList() {
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        setBackgroundTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
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
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo == null || tintInfo.mBlendMode == null) {
            return null;
        }
        return BlendMode.blendModeToPorterDuffMode(this.mBackgroundTint.mBlendMode);
    }

    public BlendMode getBackgroundTintBlendMode() {
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo != null) {
            return tintInfo.mBlendMode;
        }
        return null;
    }

    private void applyBackgroundTint() {
        TintInfo tintInfo;
        if (this.mBackground == null || (tintInfo = this.mBackgroundTint) == null) {
            return;
        }
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

    public Drawable getForeground() {
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo != null) {
            return foregroundInfo.mDrawable;
        }
        return null;
    }

    public void setForeground(Drawable drawable) {
        if (this.mForegroundInfo == null) {
            if (drawable == null) {
                return;
            } else {
                this.mForegroundInfo = new ForegroundInfo();
            }
        }
        if (drawable == this.mForegroundInfo.mDrawable) {
            return;
        }
        if (this.mForegroundInfo.mDrawable != null) {
            if (isAttachedToWindow()) {
                this.mForegroundInfo.mDrawable.setVisible(false, false);
            }
            this.mForegroundInfo.mDrawable.setCallback(null);
            unscheduleDrawable(this.mForegroundInfo.mDrawable);
        }
        this.mForegroundInfo.mDrawable = drawable;
        this.mForegroundInfo.mBoundsChanged = true;
        if (drawable != null) {
            int i = this.mPrivateFlags;
            if ((i & 128) != 0) {
                this.mPrivateFlags = i & PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            applyForegroundTint();
            if (isAttachedToWindow()) {
                drawable.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
            drawable.setCallback(this);
        } else if ((this.mViewFlags & 128) != 0 && this.mBackground == null && this.mDefaultFocusHighlight == null) {
            this.mPrivateFlags |= 128;
        }
        requestLayout();
        invalidate();
    }

    public boolean isForegroundInsidePadding() {
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo != null) {
            return foregroundInfo.mInsidePadding;
        }
        return true;
    }

    public int getForegroundGravity() {
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo != null) {
            return foregroundInfo.mGravity;
        }
        return 8388659;
    }

    public void setForegroundGravity(int i) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= Gravity.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mForegroundInfo.mGravity = i;
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setForegroundTintList(ColorStateList colorStateList) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            this.mForegroundInfo.mTintInfo = new TintInfo();
        }
        this.mForegroundInfo.mTintInfo.mTintList = colorStateList;
        this.mForegroundInfo.mTintInfo.mHasTintList = true;
        applyForegroundTint();
    }

    public ColorStateList getForegroundTintList() {
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo == null || foregroundInfo.mTintInfo == null) {
            return null;
        }
        return this.mForegroundInfo.mTintInfo.mTintList;
    }

    public void setForegroundTintMode(PorterDuff.Mode mode) {
        setForegroundTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
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
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        BlendMode blendMode = (foregroundInfo == null || foregroundInfo.mTintInfo == null) ? null : this.mForegroundInfo.mTintInfo.mBlendMode;
        if (blendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public BlendMode getForegroundTintBlendMode() {
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo == null || foregroundInfo.mTintInfo == null) {
            return null;
        }
        return this.mForegroundInfo.mTintInfo.mBlendMode;
    }

    private void applyForegroundTint() {
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        if (foregroundInfo == null || foregroundInfo.mDrawable == null || this.mForegroundInfo.mTintInfo == null) {
            return;
        }
        TintInfo tintInfo = this.mForegroundInfo.mTintInfo;
        if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
            ForegroundInfo foregroundInfo2 = this.mForegroundInfo;
            foregroundInfo2.mDrawable = foregroundInfo2.mDrawable.mutate();
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

    private Drawable getAutofilledDrawable() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return null;
        }
        if (attachInfo.mAutofilledDrawable == null) {
            Context context = getRootView().getContext();
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(AUTOFILL_HIGHLIGHT_ATTR);
            int resourceId = obtainStyledAttributes.getResourceId(0, 0);
            this.mAttachInfo.mAutofilledDrawable = context.getDrawable(resourceId);
            obtainStyledAttributes.recycle();
        }
        return this.mAttachInfo.mAutofilledDrawable;
    }

    private void drawAutofilledHighlight(Canvas canvas) {
        Drawable autofilledDrawable;
        if (!isAutofilled() || hideAutofillHighlight() || (autofilledDrawable = getAutofilledDrawable()) == null) {
            return;
        }
        autofilledDrawable.setBounds(0, 0, getWidth(), getHeight());
        autofilledDrawable.draw(canvas);
    }

    public void onDrawForeground(Canvas canvas) {
        onDrawScrollIndicators(canvas);
        onDrawScrollBars(canvas);
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        Drawable drawable = foregroundInfo != null ? foregroundInfo.mDrawable : null;
        if (drawable != null) {
            if (this.mForegroundInfo.mBoundsChanged) {
                this.mForegroundInfo.mBoundsChanged = false;
                Rect rect = this.mForegroundInfo.mSelfBounds;
                Rect rect2 = this.mForegroundInfo.mOverlayBounds;
                if (this.mForegroundInfo.mInsidePadding) {
                    rect.set(0, 0, getWidth(), getHeight());
                } else {
                    rect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                }
                Gravity.apply(this.mForegroundInfo.mGravity, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), rect, rect2, getLayoutDirection());
                drawable.setBounds(rect2);
            }
            drawable.draw(canvas);
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        resetResolvedPaddingInternal();
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mUserPaddingLeftInitial = i;
        this.mUserPaddingRightInitial = i3;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        internalSetPadding(i, i2, i3, i4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0025, code lost:
    
        if (r5 != 2) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0028, code lost:
    
        r10 = r10 + r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0030, code lost:
    
        if (isLayoutRtl() != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void internalSetPadding(int r8, int r9, int r10, int r11) {
        /*
            r7 = this;
            r7.mUserPaddingLeft = r8
            r7.mUserPaddingRight = r10
            r7.mUserPaddingBottom = r11
            int r0 = r7.mViewFlags
            r1 = r0 & 768(0x300, float:1.076E-42)
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L41
            r1 = r0 & 512(0x200, float:7.17E-43)
            r4 = 16777216(0x1000000, float:2.3509887E-38)
            if (r1 == 0) goto L33
            r1 = r0 & r4
            if (r1 != 0) goto L1a
            r1 = r3
            goto L1e
        L1a:
            int r1 = r7.getVerticalScrollbarWidth()
        L1e:
            int r5 = r7.mVerticalScrollbarPosition
            if (r5 == 0) goto L2c
            if (r5 == r2) goto L2a
            r6 = 2
            if (r5 == r6) goto L28
            goto L33
        L28:
            int r10 = r10 + r1
            goto L33
        L2a:
            int r8 = r8 + r1
            goto L33
        L2c:
            boolean r5 = r7.isLayoutRtl()
            if (r5 == 0) goto L28
            goto L2a
        L33:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L41
            r0 = r0 & r4
            if (r0 != 0) goto L3c
            r0 = r3
            goto L40
        L3c:
            int r0 = r7.getHorizontalScrollbarHeight()
        L40:
            int r11 = r11 + r0
        L41:
            int r0 = r7.mPaddingLeft
            if (r0 == r8) goto L48
            r7.mPaddingLeft = r8
            r3 = r2
        L48:
            int r8 = r7.mPaddingTop
            if (r8 == r9) goto L4f
            r7.mPaddingTop = r9
            r3 = r2
        L4f:
            int r8 = r7.mPaddingRight
            if (r8 == r10) goto L56
            r7.mPaddingRight = r10
            r3 = r2
        L56:
            int r8 = r7.mPaddingBottom
            if (r8 == r11) goto L5d
            r7.mPaddingBottom = r11
            goto L5e
        L5d:
            r2 = r3
        L5e:
            if (r2 == 0) goto L66
            r7.requestLayout()
            r7.invalidateOutline()
        L66:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.internalSetPadding(int, int, int, int):void");
    }

    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        resetResolvedPaddingInternal();
        this.mUserPaddingStart = i;
        this.mUserPaddingEnd = i3;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        if (getLayoutDirection() == 1) {
            this.mUserPaddingLeftInitial = i3;
            this.mUserPaddingRightInitial = i;
            internalSetPadding(i3, i2, i, i4);
        } else {
            this.mUserPaddingLeftInitial = i;
            this.mUserPaddingRightInitial = i3;
            internalSetPadding(i, i2, i3, i4);
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
        return (this.mUserPaddingStart == Integer.MIN_VALUE && this.mUserPaddingEnd == Integer.MIN_VALUE) ? false : true;
    }

    Insets computeOpticalInsets() {
        Drawable drawable = this.mBackground;
        return drawable == null ? Insets.NONE : drawable.getOpticalInsets();
    }

    public void resetPaddingToInitialValues() {
        if (isRtlCompatibilityMode()) {
            this.mPaddingLeft = this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingRightInitial;
            return;
        }
        if (isLayoutRtl()) {
            int i = this.mUserPaddingEnd;
            if (i < 0) {
                i = this.mUserPaddingLeftInitial;
            }
            this.mPaddingLeft = i;
            int i2 = this.mUserPaddingStart;
            if (i2 < 0) {
                i2 = this.mUserPaddingRightInitial;
            }
            this.mPaddingRight = i2;
            return;
        }
        int i3 = this.mUserPaddingStart;
        if (i3 < 0) {
            i3 = this.mUserPaddingLeftInitial;
        }
        this.mPaddingLeft = i3;
        int i4 = this.mUserPaddingEnd;
        if (i4 < 0) {
            i4 = this.mUserPaddingRightInitial;
        }
        this.mPaddingRight = i4;
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

    public void setSelected(boolean z) {
        int i = this.mPrivateFlags;
        if (((i & 4) != 0) != z) {
            this.mPrivateFlags = (i & (-5)) | (z ? 4 : 0);
            if (!z) {
                resetPressedState();
            }
            invalidate(true);
            refreshDrawableState();
            dispatchSetSelected(z);
            if (z) {
                sendAccessibilityEvent(4);
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    @ViewDebug.ExportedProperty
    public boolean isSelected() {
        return (this.mPrivateFlags & 4) != 0;
    }

    public void setActivated(boolean z) {
        int i = this.mPrivateFlags;
        if (((i & 1073741824) != 0) != z) {
            this.mPrivateFlags = (i & (-1073741825)) | (z ? 1073741824 : 0);
            invalidate(true);
            refreshDrawableState();
            dispatchSetActivated(z);
        }
    }

    @ViewDebug.ExportedProperty
    public boolean isActivated() {
        return (this.mPrivateFlags & 1073741824) != 0;
    }

    public ViewTreeObserver getViewTreeObserver() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mTreeObserver;
        }
        if (this.mFloatingTreeObserver == null) {
            this.mFloatingTreeObserver = new ViewTreeObserver(this.mContext);
        }
        return this.mFloatingTreeObserver;
    }

    public View getRootView() {
        View view;
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && (view = attachInfo.mRootView) != null) {
            return view;
        }
        while (true) {
            Object obj = this.mParent;
            if (!(obj instanceof View)) {
                return this;
            }
            this = (View) obj;
        }
    }

    public boolean toGlobalMotionEvent(MotionEvent motionEvent) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return false;
        }
        Matrix matrix = attachInfo.mTmpMatrix;
        matrix.set(Matrix.IDENTITY_MATRIX);
        transformMatrixToGlobal(matrix);
        motionEvent.transform(matrix);
        return true;
    }

    public boolean toLocalMotionEvent(MotionEvent motionEvent) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return false;
        }
        Matrix matrix = attachInfo.mTmpMatrix;
        matrix.set(Matrix.IDENTITY_MATRIX);
        transformMatrixToLocal(matrix);
        motionEvent.transform(matrix);
        return true;
    }

    public void transformMatrixToGlobal(Matrix matrix) {
        Object obj = this.mParent;
        if (obj instanceof View) {
            ((View) obj).transformMatrixToGlobal(matrix);
            matrix.preTranslate(-r0.mScrollX, -r0.mScrollY);
        } else if (obj instanceof ViewRootImpl) {
            ((ViewRootImpl) obj).transformMatrixToGlobal(matrix);
            matrix.preTranslate(0.0f, -r0.mCurScrollY);
        }
        matrix.preTranslate(this.mLeft, this.mTop);
        if (hasIdentityMatrix()) {
            return;
        }
        matrix.preConcat(getMatrix());
    }

    public void transformMatrixToLocal(Matrix matrix) {
        Object obj = this.mParent;
        if (obj instanceof View) {
            ((View) obj).transformMatrixToLocal(matrix);
            matrix.postTranslate(r0.mScrollX, r0.mScrollY);
        } else if (obj instanceof ViewRootImpl) {
            ((ViewRootImpl) obj).transformMatrixToLocal(matrix);
            matrix.postTranslate(0.0f, r0.mCurScrollY);
        }
        matrix.postTranslate(-this.mLeft, -this.mTop);
        if (hasIdentityMatrix()) {
            return;
        }
        matrix.postConcat(getInverseMatrix());
    }

    public void transformMatrixRootToLocal(Matrix matrix) {
        Object obj = this.mParent;
        if (obj instanceof View) {
            ((View) obj).transformMatrixRootToLocal(matrix);
            matrix.postTranslate(r0.mScrollX, r0.mScrollY);
        }
        matrix.postTranslate(-this.mLeft, -this.mTop);
        if (hasIdentityMatrix()) {
            return;
        }
        matrix.postConcat(getInverseMatrix());
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, indexMapping = {@ViewDebug.IntToString(from = 0, to = "x"), @ViewDebug.IntToString(from = 1, to = "y")})
    public int[] getLocationOnScreen() {
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        return iArr;
    }

    public void getLocationOnScreen(int[] iArr) {
        getLocationInWindow(iArr);
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            iArr[0] = iArr[0] + attachInfo.mWindowLeft;
            iArr[1] = iArr[1] + attachInfo.mWindowTop;
            attachInfo.mViewRootImpl.applyViewLocationSandboxingIfNeeded(iArr);
        }
    }

    public void getLocationInWindow(int[] iArr) {
        if (iArr == null || iArr.length < 2) {
            throw new IllegalArgumentException("outLocation must be an array of two integers");
        }
        iArr[0] = 0;
        iArr[1] = 0;
        transformFromViewToWindowSpace(iArr);
    }

    public void transformFromViewToWindowSpace(int[] iArr) {
        if (iArr == null || iArr.length < 2) {
            throw new IllegalArgumentException("inOutLocation must be an array of two integers");
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            iArr[1] = 0;
            iArr[0] = 0;
            return;
        }
        float[] fArr = attachInfo.mTmpTransformLocation;
        fArr[0] = iArr[0];
        fArr[1] = iArr[1];
        if (!hasIdentityMatrix()) {
            getMatrix().mapPoints(fArr);
        }
        fArr[0] = fArr[0] + this.mLeft;
        fArr[1] = fArr[1] + this.mTop;
        Object obj = this.mParent;
        while (obj instanceof View) {
            View view = (View) obj;
            fArr[0] = fArr[0] - view.mScrollX;
            fArr[1] = fArr[1] - view.mScrollY;
            if (!view.hasIdentityMatrix()) {
                view.getMatrix().mapPoints(fArr);
            }
            fArr[0] = fArr[0] + view.mLeft;
            fArr[1] = fArr[1] + view.mTop;
            obj = view.mParent;
        }
        if (obj instanceof ViewRootImpl) {
            fArr[1] = fArr[1] - ((ViewRootImpl) obj).mCurScrollY;
        }
        iArr[0] = Math.round(fArr[0]);
        iArr[1] = Math.round(fArr[1]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends View> T findViewTraversal(int i) {
        if (i == this.mID) {
            return this;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends View> T findViewWithTagTraversal(Object obj) {
        if (obj == null || !obj.equals(this.mTag)) {
            return null;
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
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
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this View");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends View> T findViewByAccessibilityIdTraversal(int i) {
        if (getAccessibilityViewId() == i) {
            return this;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends View> T findViewByAutofillIdTraversal(int i) {
        if (getAutofillViewId() == i) {
            return this;
        }
        return null;
    }

    public void findAutofillableViewsByTraversal(List<View> list) {
        if (isAutofillable()) {
            list.add(this);
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x001d, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final <T extends android.view.View> T findViewByPredicateInsideOut(android.view.View r5, java.util.function.Predicate<android.view.View> r6) {
        /*
            r4 = this;
            r0 = 0
            r1 = r0
        L2:
            android.view.View r1 = r5.findViewByPredicateTraversal(r6, r1)
            if (r1 != 0) goto L1d
            if (r5 != r4) goto Lb
            goto L1d
        Lb:
            android.view.ViewParent r1 = r5.getParent()
            if (r1 == 0) goto L1c
            boolean r2 = r1 instanceof android.view.View
            if (r2 != 0) goto L16
            goto L1c
        L16:
            android.view.View r1 = (android.view.View) r1
            r3 = r1
            r1 = r5
            r5 = r3
            goto L2
        L1c:
            return r0
        L1d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.findViewByPredicateInsideOut(android.view.View, java.util.function.Predicate):android.view.View");
    }

    public void setId(int i) {
        this.mID = i;
        if (i != -1 || this.mLabelForId == -1) {
            return;
        }
        this.mID = generateViewId();
    }

    public void setIsRootNamespace(boolean z) {
        if (z) {
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

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public Object getTag(int i) {
        SparseArray<Object> sparseArray = this.mKeyedTags;
        if (sparseArray != null) {
            return sparseArray.get(i);
        }
        return null;
    }

    public void setTag(int i, Object obj) {
        if ((i >>> 24) < 2) {
            throw new IllegalArgumentException("The key must be an application-specific resource id.");
        }
        setKeyedTag(i, obj);
    }

    public void setTagInternal(int i, Object obj) {
        if ((i >>> 24) != 1) {
            throw new IllegalArgumentException("The key must be a framework-specific resource id.");
        }
        setKeyedTag(i, obj);
    }

    private void setKeyedTag(int i, Object obj) {
        if (this.mKeyedTags == null) {
            this.mKeyedTags = new SparseArray<>(2);
        }
        this.mKeyedTags.put(i, obj);
    }

    public void debug() {
        debug(0);
    }

    protected void debug(int i) {
        String debug;
        String str = debugIndent(i - 1) + "+ " + this;
        int id = getId();
        if (id != -1) {
            str = str + " (id=" + id + NavigationBarInflaterView.KEY_CODE_END;
        }
        Object tag = getTag();
        if (tag != null) {
            str = str + " (tag=" + tag + NavigationBarInflaterView.KEY_CODE_END;
        }
        Log.d(VIEW_LOG_TAG, str);
        if ((this.mPrivateFlags & 2) != 0) {
            Log.d(VIEW_LOG_TAG, debugIndent(i) + " FOCUSED");
        }
        Log.d(VIEW_LOG_TAG, debugIndent(i) + "frame={" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + "} scroll={" + this.mScrollX + ", " + this.mScrollY + "} ");
        if (this.mPaddingLeft != 0 || this.mPaddingTop != 0 || this.mPaddingRight != 0 || this.mPaddingBottom != 0) {
            Log.d(VIEW_LOG_TAG, debugIndent(i) + "padding={" + this.mPaddingLeft + ", " + this.mPaddingTop + ", " + this.mPaddingRight + ", " + this.mPaddingBottom + "}");
        }
        Log.d(VIEW_LOG_TAG, debugIndent(i) + "mMeasureWidth=" + this.mMeasuredWidth + " mMeasureHeight=" + this.mMeasuredHeight);
        String debugIndent = debugIndent(i);
        ViewGroup.LayoutParams layoutParams = this.mLayoutParams;
        if (layoutParams == null) {
            debug = debugIndent + "BAD! no layout params";
        } else {
            debug = layoutParams.debug(debugIndent);
        }
        Log.d(VIEW_LOG_TAG, debug);
        Log.d(VIEW_LOG_TAG, ((debugIndent(i) + "flags={") + printFlags(this.mViewFlags)) + "}");
        Log.d(VIEW_LOG_TAG, ((debugIndent(i) + "privateFlags={") + printPrivateFlags(this.mPrivateFlags)) + "}");
    }

    protected static String debugIndent(int i) {
        int i2 = (i * 2) + 3;
        StringBuilder sb = new StringBuilder(i2 * 2);
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    public boolean isInLayout() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        return viewRootImpl != null && viewRootImpl.isInLayout();
    }

    private void printStackStrace(String str) {
        Log.d(VIEW_LOG_TAG, "---- ST:" + str);
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int min = Math.min(stackTrace.length, 21);
        for (int i = 1; i < min; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            sb.append(stackTraceElement.getMethodName());
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(stackTraceElement.getFileName());
            sb.append(":");
            sb.append(stackTraceElement.getLineNumber());
            sb.append(") <- ");
        }
        Log.d(VIEW_LOG_TAG, str + ": " + ((Object) sb));
    }

    public void requestLayout() {
        if (isRelayoutTracingEnabled()) {
            Trace.instantForTrack(4096L, "requestLayoutTracing", this.mTracingStrings.classSimpleName);
            printStackStrace(this.mTracingStrings.requestLayoutStacktracePrefix);
        }
        LongSparseLongArray longSparseLongArray = this.mMeasureCache;
        if (longSparseLongArray != null) {
            longSparseLongArray.clear();
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mViewRequestingLayout == null) {
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null && viewRootImpl.isInLayout() && !viewRootImpl.requestLayoutDuringLayout(this)) {
                return;
            } else {
                this.mAttachInfo.mViewRequestingLayout = this;
            }
        }
        this.mPrivateFlags |= -2147479552;
        ViewParent viewParent = this.mParent;
        if (viewParent != null && !viewParent.isLayoutRequested()) {
            this.mParent.requestLayout();
        }
        AttachInfo attachInfo2 = this.mAttachInfo;
        if (attachInfo2 == null || attachInfo2.mViewRequestingLayout != this) {
            return;
        }
        this.mAttachInfo.mViewRequestingLayout = null;
    }

    public void forceLayout() {
        LongSparseLongArray longSparseLongArray = this.mMeasureCache;
        if (longSparseLongArray != null) {
            longSparseLongArray.clear();
        }
        this.mPrivateFlags |= -2147479552;
    }

    private String getMeasureSpecMode(int i) {
        int mode = MeasureSpec.getMode(i);
        new String();
        if (mode == Integer.MIN_VALUE) {
            return "AT_MOST";
        }
        if (mode == 0) {
            return "UNSPECIFIED";
        }
        if (mode != 1073741824) {
            return null;
        }
        return "EXACTLY";
    }

    public final void measure(int i, int i2) {
        int indexOfKey;
        int i3 = i;
        int i4 = i2;
        if (ViewRootImpl.DEBUG_MEASURE) {
            Trace.traceBegin(8L, "measure for " + getClass().getSimpleName() + " w:" + i3 + "/" + getMeasureSpecMode(i) + "/" + MeasureSpec.getSize(i3) + " h:" + i4 + "/" + getMeasureSpecMode(i4) + "/" + MeasureSpec.getSize(i4) + " " + this);
        }
        boolean isLayoutModeOptical = isLayoutModeOptical(this);
        if (isLayoutModeOptical != isLayoutModeOptical(this.mParent)) {
            Insets opticalInsets = getOpticalInsets();
            int i5 = opticalInsets.left + opticalInsets.right;
            int i6 = opticalInsets.top + opticalInsets.bottom;
            if (isLayoutModeOptical) {
                i5 = -i5;
            }
            i3 = MeasureSpec.adjust(i3, i5);
            if (isLayoutModeOptical) {
                i6 = -i6;
            }
            i4 = MeasureSpec.adjust(i4, i6);
        }
        long j = (i3 << 32) | (i4 & 4294967295L);
        if (this.mMeasureCache == null) {
            this.mMeasureCache = new LongSparseLongArray(2);
        }
        boolean z = true;
        boolean z2 = (this.mPrivateFlags & 4096) == 4096;
        boolean z3 = (i3 == this.mOldWidthMeasureSpec && i4 == this.mOldHeightMeasureSpec) ? false : true;
        boolean z4 = MeasureSpec.getMode(i3) == 1073741824 && MeasureSpec.getMode(i4) == 1073741824;
        boolean z5 = getMeasuredWidth() == MeasureSpec.getSize(i3) && getMeasuredHeight() == MeasureSpec.getSize(i4);
        if (!z3 || (!sAlwaysRemeasureExactly && z4 && z5)) {
            z = false;
        }
        if (z2 || z) {
            this.mPrivateFlags &= -2049;
            resolveRtlPropertiesIfNeeded();
            if (sUseMeasureCacheDuringForceLayoutFlagValue) {
                indexOfKey = this.mMeasureCache.indexOfKey(j);
            } else {
                indexOfKey = z2 ? -1 : this.mMeasureCache.indexOfKey(j);
            }
            if (indexOfKey < 0) {
                if (isTraversalTracingEnabled()) {
                    Trace.beginSection(this.mTracingStrings.onMeasure);
                }
                if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.adpfMeasureDuringInputEventBoost() && hasExpensiveMeasuresDuringInputEvent()) {
                    getViewRootImpl().notifyRendererOfExpensiveFrame("ADPF_SendHint: hasExpensiveMeasuresDuringInputEvent");
                }
                onMeasure(i3, i4);
                if (isTraversalTracingEnabled()) {
                    Trace.endSection();
                }
                this.mPrivateFlags3 &= -9;
            } else {
                long valueAt = this.mMeasureCache.valueAt(indexOfKey);
                setMeasuredDimensionRaw((int) (valueAt >> 32), (int) valueAt);
                this.mPrivateFlags3 |= 8;
            }
            int i7 = this.mPrivateFlags;
            if ((i7 & 2048) != 2048) {
                throw new IllegalStateException("View with id " + getId() + ": " + getClass().getName() + "#onMeasure() did not set the measured dimension by calling setMeasuredDimension()");
            }
            this.mPrivateFlags = i7 | 8192;
        }
        this.mOldWidthMeasureSpec = i3;
        this.mOldHeightMeasureSpec = i4;
        this.mMeasureCache.put(j, (this.mMeasuredWidth << 32) | (this.mMeasuredHeight & 4294967295L));
        if (ViewRootImpl.DEBUG_MEASURE) {
            Trace.traceEnd(8L);
        }
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), i), getDefaultSize(getSuggestedMinimumHeight(), i2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setMeasuredDimension(int i, int i2) {
        boolean isLayoutModeOptical = isLayoutModeOptical(this);
        if (isLayoutModeOptical != isLayoutModeOptical(this.mParent)) {
            Insets opticalInsets = getOpticalInsets();
            int i3 = opticalInsets.left + opticalInsets.right;
            int i4 = opticalInsets.top + opticalInsets.bottom;
            if (!isLayoutModeOptical) {
                i3 = -i3;
            }
            i += i3;
            if (!isLayoutModeOptical) {
                i4 = -i4;
            }
            i2 += i4;
        }
        setMeasuredDimensionRaw(i, i2);
    }

    private void setMeasuredDimensionRaw(int i, int i2) {
        this.mMeasuredWidth = i;
        this.mMeasuredHeight = i2;
        this.mPrivateFlags |= 2048;
    }

    public static int resolveSize(int i, int i2) {
        return resolveSizeAndState(i, i2, 0) & 16777215;
    }

    public static int resolveSizeAndState(int i, int i2, int i3) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        if (mode != Integer.MIN_VALUE) {
            if (mode == 1073741824) {
                i = size;
            }
        } else if (size < i) {
            i = 16777216 | size;
        }
        return i | ((-16777216) & i3);
    }

    public static int getDefaultSize(int i, int i2) {
        int mode = MeasureSpec.getMode(i2);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? MeasureSpec.getSize(i2) : i;
    }

    protected int getSuggestedMinimumHeight() {
        Drawable drawable = this.mBackground;
        int i = this.mMinHeight;
        return drawable == null ? i : Math.max(i, drawable.getMinimumHeight());
    }

    protected int getSuggestedMinimumWidth() {
        Drawable drawable = this.mBackground;
        int i = this.mMinWidth;
        return drawable == null ? i : Math.max(i, drawable.getMinimumWidth());
    }

    public int getMinimumHeight() {
        return this.mMinHeight;
    }

    @RemotableViewMethod
    public void setMinimumHeight(int i) {
        this.mMinHeight = i;
        requestLayout();
    }

    public int getMinimumWidth() {
        return this.mMinWidth;
    }

    @RemotableViewMethod
    public void setMinimumWidth(int i) {
        this.mMinWidth = i;
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
        Animation animation = this.mCurrentAnimation;
        if (animation != null) {
            animation.detach();
        }
        this.mCurrentAnimation = null;
        invalidateParentIfNeeded();
    }

    public void setAnimation(Animation animation) {
        this.mCurrentAnimation = animation;
        if (animation != null) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null && attachInfo.mDisplayState == 1 && animation.getStartTime() == -1) {
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

    public boolean gatherTransparentRegion(Region region) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (region != null && attachInfo != null) {
            if ((this.mPrivateFlags & 128) == 0) {
                int[] iArr = attachInfo.mTransparentLocation;
                getLocationInWindow(iArr);
                int z = getZ() > 0.0f ? (int) getZ() : 0;
                int i = iArr[0];
                int i2 = iArr[1];
                region.op(i - z, i2 - z, ((i + this.mRight) - this.mLeft) + z, ((i2 + this.mBottom) - this.mTop) + (z * 3), Region.Op.DIFFERENCE);
            } else {
                Drawable drawable = this.mBackground;
                if (drawable != null && drawable.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mBackground, region);
                }
                ForegroundInfo foregroundInfo = this.mForegroundInfo;
                if (foregroundInfo != null && foregroundInfo.mDrawable != null && this.mForegroundInfo.mDrawable.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mForegroundInfo.mDrawable, region);
                }
                Drawable drawable2 = this.mDefaultFocusHighlight;
                if (drawable2 != null && drawable2.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mDefaultFocusHighlight, region);
                }
            }
        }
        return true;
    }

    public void playSoundEffect(int i) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null || attachInfo.mRootCallbacks == null || !isSoundEffectsEnabled()) {
            return;
        }
        this.mAttachInfo.mRootCallbacks.playSoundEffect(i);
    }

    public boolean performHapticFeedback(int i) {
        return performHapticFeedback(i, 0);
    }

    public boolean performHapticFeedback(int i, int i2) {
        if (isPerformHapticFeedbackSuppressed(i, i2)) {
            return false;
        }
        return this.mAttachInfo.mRootCallbacks.performHapticFeedback(i, i2, computeHapticFeedbackPrivateFlags());
    }

    public void performHapticFeedbackForInputDevice(int i, int i2, int i3, int i4) {
        if (isPerformHapticFeedbackSuppressed(i, i4)) {
            return;
        }
        this.mAttachInfo.mRootCallbacks.performHapticFeedbackForInputDevice(i, i2, i3, i4, computeHapticFeedbackPrivateFlags());
    }

    private boolean isPerformHapticFeedbackSuppressed(int i, int i2) {
        AttachInfo attachInfo;
        if (i == -1 || (attachInfo = this.mAttachInfo) == null || attachInfo.mSession == null) {
            return true;
        }
        return (i2 & 1) == 0 && !isHapticFeedbackEnabled();
    }

    private int computeHapticFeedbackPrivateFlags() {
        return (this.mAttachInfo.mViewRootImpl == null || this.mAttachInfo.mViewRootImpl.mWindowAttributes.type != 2011) ? 0 : 1;
    }

    @Deprecated
    public void setSystemUiVisibility(int i) {
        AttachInfo attachInfo;
        if (i != this.mSystemUiVisibility) {
            this.mSystemUiVisibility = i;
            if (this.mParent == null || (attachInfo = this.mAttachInfo) == null || attachInfo.mRecomputeGlobalAttributes) {
                return;
            }
            this.mParent.recomputeViewAttributes(this);
        }
    }

    @Deprecated
    public int getSystemUiVisibility() {
        return this.mSystemUiVisibility;
    }

    @Deprecated
    public int getWindowSystemUiVisibility() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mSystemUiVisibility;
        }
        return 0;
    }

    @Deprecated
    public void dispatchWindowSystemUiVisiblityChanged(int i) {
        onWindowSystemUiVisibilityChanged(i);
    }

    @Deprecated
    public void setOnSystemUiVisibilityChangeListener(OnSystemUiVisibilityChangeListener onSystemUiVisibilityChangeListener) {
        AttachInfo attachInfo;
        getListenerInfo().mOnSystemUiVisibilityChangeListener = onSystemUiVisibilityChangeListener;
        if (this.mParent == null || (attachInfo = this.mAttachInfo) == null || attachInfo.mRecomputeGlobalAttributes) {
            return;
        }
        this.mParent.recomputeViewAttributes(this);
    }

    @Deprecated
    public void dispatchSystemUiVisibilityChanged(int i) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnSystemUiVisibilityChangeListener == null) {
            return;
        }
        listenerInfo.mOnSystemUiVisibilityChangeListener.onSystemUiVisibilityChange(i & PUBLIC_STATUS_BAR_VISIBILITY_MASK);
    }

    boolean updateLocalSystemUiVisibility(int i, int i2) {
        int i3 = this.mSystemUiVisibility;
        int i4 = (i & i2) | ((~i2) & i3);
        if (i4 == i3) {
            return false;
        }
        setSystemUiVisibility(i4);
        return true;
    }

    public void setDisabledSystemUiVisibility(int i) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null || attachInfo.mDisabledSystemUiVisibility == i) {
            return;
        }
        this.mAttachInfo.mDisabledSystemUiVisibility = i;
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.recomputeViewAttributes(this);
        }
    }

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

        public void semSetLastTouchPoint(float f, float f2) {
            this.mSemLastTouchPoint = new Point((int) f, (int) f2);
        }

        public Point semGetLastTouchPoint() {
            return this.mSemLastTouchPoint;
        }

        public void onProvideShadowMetrics(Point point, Point point2) {
            View view = this.mView.get();
            if (view != null) {
                point.set(view.getWidth(), view.getHeight());
                point2.set(point.x / 2, point.y / 2);
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

    public final boolean semUpdateClipData(ClipData clipData) {
        if (clipData == null) {
            Log.w(VIEW_LOG_TAG, "updateClipData: data is null");
            return false;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            Log.w(VIEW_LOG_TAG, "updateClipData called on a detached view.");
            return false;
        }
        if (!attachInfo.mViewRootImpl.mSurface.isValid()) {
            Log.w(VIEW_LOG_TAG, "updateClipData called with an invalid surface.");
            return false;
        }
        if (this.mAttachInfo.mDragToken == null || this.mAttachInfo.mDragSurface == null) {
            Log.w(VIEW_LOG_TAG, "updateClipData called without a drag start");
            return false;
        }
        clipData.prepareToLeaveProcess(true);
        try {
            this.mAttachInfo.mSession.performClipDataUpdate(clipData);
            return true;
        } catch (Exception e) {
            Log.e(VIEW_LOG_TAG, "Unable to update ClipData : ", e);
            return false;
        }
    }

    @Deprecated
    public final boolean startDrag(ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        return startDragAndDrop(clipData, dragShadowBuilder, obj, i);
    }

    public final boolean startDragAndDrop(ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        return startDragAndDrop(clipData, dragShadowBuilder, obj, i, null, null);
    }

    public final boolean hidden_startDragAndDrop(ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i, RectF rectF, Point point) {
        return startDragAndDrop(clipData, dragShadowBuilder, obj, i, rectF, point);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0397  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03a1  */
    /* JADX WARN: Type inference failed for: r10v18, types: [android.view.IWindowSession] */
    /* JADX WARN: Type inference failed for: r15v0, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r15v1, types: [android.view.Surface] */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v14 */
    /* JADX WARN: Type inference failed for: r15v15 */
    /* JADX WARN: Type inference failed for: r15v16 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5, types: [android.view.Surface] */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9, types: [android.view.Surface] */
    /* JADX WARN: Type inference failed for: r1v9, types: [android.view.IWindowSession] */
    /* JADX WARN: Type inference failed for: r22v10, types: [long] */
    /* JADX WARN: Type inference failed for: r22v11 */
    /* JADX WARN: Type inference failed for: r22v12 */
    /* JADX WARN: Type inference failed for: r22v13 */
    /* JADX WARN: Type inference failed for: r22v14 */
    /* JADX WARN: Type inference failed for: r22v15 */
    /* JADX WARN: Type inference failed for: r22v16 */
    /* JADX WARN: Type inference failed for: r22v4 */
    /* JADX WARN: Type inference failed for: r22v5 */
    /* JADX WARN: Type inference failed for: r22v6 */
    /* JADX WARN: Type inference failed for: r22v7, types: [long] */
    /* JADX WARN: Type inference failed for: r22v8 */
    /* JADX WARN: Type inference failed for: r22v9 */
    /* JADX WARN: Type inference failed for: r4v5, types: [android.view.SurfaceControl] */
    /* JADX WARN: Type inference failed for: r4v9, types: [android.view.SurfaceControl] */
    /* JADX WARN: Type inference failed for: r5v3, types: [android.view.ViewRootImpl] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean startDragAndDrop(android.content.ClipData r27, android.view.View.DragShadowBuilder r28, java.lang.Object r29, int r30, android.graphics.RectF r31, android.graphics.Point r32) {
        /*
            Method dump skipped, instructions count: 952
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.startDragAndDrop(android.content.ClipData, android.view.View$DragShadowBuilder, java.lang.Object, int, android.graphics.RectF, android.graphics.Point):boolean");
    }

    static boolean hasActivityPendingIntents(ClipData clipData) {
        int itemCount = clipData.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            ClipData.Item itemAt = clipData.getItemAt(i);
            if (itemAt.getIntentSender() != null && new PendingIntent(itemAt.getIntentSender().getTarget()).isActivity()) {
                return true;
            }
        }
        return false;
    }

    static void cleanUpPendingIntents(ClipData clipData) {
        int itemCount = clipData.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            ClipData.Item itemAt = clipData.getItemAt(i);
            if (itemAt.getIntentSender() != null) {
                new PendingIntent(itemAt.getIntentSender().getTarget()).cancel();
            }
        }
    }

    void setAccessibilityDragStarted(boolean z) {
        int i = this.mPrivateFlags4;
        int i2 = z ? 32768 | i : (-32769) & i;
        if (i2 != i) {
            this.mPrivateFlags4 = i2;
            sendWindowContentChangedAccessibilityEvent(0);
        }
    }

    private boolean startedSystemDragForAccessibility() {
        return (this.mPrivateFlags4 & 32768) != 0;
    }

    public final void cancelDragAndDrop() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            Log.w(VIEW_LOG_TAG, "cancelDragAndDrop called on a detached view.");
            return;
        }
        if (attachInfo.mDragToken != null) {
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

    public final void updateDragShadow(DragShadowBuilder dragShadowBuilder) {
        Canvas lockCanvas;
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            Log.w(VIEW_LOG_TAG, "updateDragShadow called on a detached view.");
            return;
        }
        if (attachInfo.mDragToken != null) {
            try {
                if (isHardwareAccelerated()) {
                    lockCanvas = this.mAttachInfo.mDragSurface.lockHardwareCanvas();
                } else {
                    lockCanvas = this.mAttachInfo.mDragSurface.lockCanvas(null);
                }
                try {
                    lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    dragShadowBuilder.onDrawShadow(lockCanvas);
                    return;
                } finally {
                    this.mAttachInfo.mDragSurface.unlockCanvasAndPost(lockCanvas);
                }
            } catch (Exception e) {
                Log.e(VIEW_LOG_TAG, "Unable to update drag shadow", e);
                return;
            }
        }
        Log.e(VIEW_LOG_TAG, "No active drag");
    }

    public final boolean startMovingTask(float f, float f2) {
        try {
            return this.mAttachInfo.mSession.startMovingTask(this.mAttachInfo.mWindow, f, f2);
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

    public boolean onDragEvent(DragEvent dragEvent) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnReceiveContentListener != null) {
            if (dragEvent.getAction() == 1) {
                return true;
            }
            if (dragEvent.getAction() == 3) {
                DragAndDropPermissions obtain = DragAndDropPermissions.obtain(dragEvent);
                if (obtain != null) {
                    obtain.takeTransient();
                }
                ContentInfo build = new ContentInfo.Builder(dragEvent.getClipData(), 3).setDragAndDropPermissions(obtain).build();
                if (performReceiveContent(build) != build) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean dispatchDragEnterExitInPreN(DragEvent dragEvent) {
        return callDragEventHandler(dragEvent);
    }

    public boolean dispatchDragEvent(DragEvent dragEvent) {
        dragEvent.mEventHandlerWasCalled = true;
        if (dragEvent.mAction == 2 || dragEvent.mAction == 3) {
            getViewRootImpl().setDragFocus(this, dragEvent);
        }
        return callDragEventHandler(dragEvent);
    }

    final boolean callDragEventHandler(DragEvent dragEvent) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean onDragEvent = (listenerInfo == null || listenerInfo.mOnDragListener == null || (this.mViewFlags & 32) != 0 || !listenerInfo.mOnDragListener.onDrag(this, dragEvent)) ? onDragEvent(dragEvent) : true;
        int i = dragEvent.mAction;
        if (i != 1) {
            if (i != 3) {
                if (i == 4) {
                    sendWindowContentChangedAccessibilityEvent(0);
                    this.mPrivateFlags2 &= -4;
                    refreshDrawableState();
                    return onDragEvent;
                }
                if (i == 5) {
                    this.mPrivateFlags2 |= 2;
                    refreshDrawableState();
                    return onDragEvent;
                }
                if (i == 6) {
                    this.mPrivateFlags2 &= -3;
                    refreshDrawableState();
                    return onDragEvent;
                }
            } else if (onDragEvent && listenerInfo != null && (listenerInfo.mOnDragListener != null || listenerInfo.mOnReceiveContentListener != null)) {
                sendWindowContentChangedAccessibilityEvent(256);
                return onDragEvent;
            }
        } else if (onDragEvent && listenerInfo != null && listenerInfo.mOnDragListener != null) {
            sendWindowContentChangedAccessibilityEvent(0);
        }
        return onDragEvent;
    }

    boolean canAcceptDrag() {
        return (this.mPrivateFlags2 & 1) != 0;
    }

    void sendWindowContentChangedAccessibilityEvent(int i) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(i);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public void applyDrawableToTransparentRegion(Drawable drawable, Region region) {
        int i;
        Region transparentRegion = drawable.getTransparentRegion();
        Rect bounds = drawable.getBounds();
        AttachInfo attachInfo = this.mAttachInfo;
        if (transparentRegion != null && attachInfo != null) {
            int right = getRight() - getLeft();
            int bottom = getBottom() - getTop();
            if (bounds.left > 0) {
                transparentRegion.op(0, 0, bounds.left, bottom, Region.Op.UNION);
            }
            if (bounds.right < right) {
                i = right;
                transparentRegion.op(bounds.right, 0, i, bottom, Region.Op.UNION);
            } else {
                i = right;
            }
            if (bounds.top > 0) {
                transparentRegion.op(0, 0, i, bounds.top, Region.Op.UNION);
            }
            if (bounds.bottom < bottom) {
                transparentRegion.op(0, bounds.bottom, i, bottom, Region.Op.UNION);
            }
            int[] iArr = attachInfo.mTransparentLocation;
            getLocationInWindow(iArr);
            transparentRegion.translate(iArr[0], iArr[1]);
            region.op(transparentRegion, Region.Op.INTERSECT);
            return;
        }
        region.op(bounds, Region.Op.DIFFERENCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkForLongClick(long j, float f, float f2, int i) {
        boolean z = (this.mViewFlags & 1073741824) == 1073741824 && (this.mSemViewFlags & 2) != 2;
        if (ViewRootImpl.DEBUG_TOUCH_EVENT) {
            Log.i(VIEW_LOG_TAG, "checkForLongClick delay : " + j + ", x : " + f + ", y : " + f2 + ", classification : " + i + ", caller=" + Debug.getCallers(3));
        }
        if ((this.mViewFlags & 2097152) == 2097152 || z) {
            this.mHasPerformedLongPress = false;
            if (this.mPendingCheckForLongPress == null) {
                this.mPendingCheckForLongPress = new CheckForLongPress();
            }
            this.mPendingCheckForLongPress.setAnchor(f, f2);
            this.mPendingCheckForLongPress.rememberWindowAttachCount();
            this.mPendingCheckForLongPress.rememberPressedState();
            this.mPendingCheckForLongPress.setClassification(i);
            postDelayed(this.mPendingCheckForLongPress, j);
        }
    }

    public static View inflate(Context context, int i, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(i, viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0057 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean overScrollBy(int r6, int r7, int r8, int r9, int r10, int r11, int r12, int r13, boolean r14) {
        /*
            r5 = this;
            int r14 = r5.mOverScrollMode
            int r0 = r5.computeHorizontalScrollRange()
            int r1 = r5.computeHorizontalScrollExtent()
            r2 = 0
            r3 = 1
            if (r0 <= r1) goto L10
            r0 = r3
            goto L11
        L10:
            r0 = r2
        L11:
            int r1 = r5.computeVerticalScrollRange()
            int r4 = r5.computeVerticalScrollExtent()
            if (r1 <= r4) goto L1d
            r1 = r3
            goto L1e
        L1d:
            r1 = r2
        L1e:
            if (r14 == 0) goto L27
            if (r14 != r3) goto L25
            if (r0 == 0) goto L25
            goto L27
        L25:
            r0 = r2
            goto L28
        L27:
            r0 = r3
        L28:
            if (r14 == 0) goto L31
            if (r14 != r3) goto L2f
            if (r1 == 0) goto L2f
            goto L31
        L2f:
            r14 = r2
            goto L32
        L31:
            r14 = r3
        L32:
            int r8 = r8 + r6
            if (r0 != 0) goto L36
            r12 = r2
        L36:
            int r9 = r9 + r7
            if (r14 != 0) goto L3a
            r13 = r2
        L3a:
            int r6 = -r12
            int r12 = r12 + r10
            int r7 = -r13
            int r13 = r13 + r11
            if (r8 <= r12) goto L43
            r8 = r12
        L41:
            r6 = r3
            goto L48
        L43:
            if (r8 >= r6) goto L47
            r8 = r6
            goto L41
        L47:
            r6 = r2
        L48:
            if (r9 <= r13) goto L4d
            r9 = r13
        L4b:
            r7 = r3
            goto L52
        L4d:
            if (r9 >= r7) goto L51
            r9 = r7
            goto L4b
        L51:
            r7 = r2
        L52:
            r5.onOverScrolled(r8, r9, r6, r7)
            if (r6 != 0) goto L5b
            if (r7 == 0) goto L5a
            goto L5b
        L5a:
            return r2
        L5b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.overScrollBy(int, int, int, int, int, int, int, int, boolean):boolean");
    }

    public int getOverScrollMode() {
        return this.mOverScrollMode;
    }

    public void setOverScrollMode(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("Invalid overscroll mode " + i);
        }
        this.mOverScrollMode = i;
    }

    public void setNestedScrollingEnabled(boolean z) {
        if (z) {
            this.mPrivateFlags3 |= 128;
        } else {
            stopNestedScroll();
            this.mPrivateFlags3 &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
    }

    public boolean isNestedScrollingEnabled() {
        return (this.mPrivateFlags3 & 128) == 128;
    }

    public boolean startNestedScroll(int i) {
        if (hasNestedScrollingParent()) {
            return true;
        }
        if (!isNestedScrollingEnabled()) {
            return false;
        }
        View view = this;
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            try {
                if (parent.onStartNestedScroll(view, this, i)) {
                    this.mNestedScrollingParent = parent;
                    parent.onNestedScrollAccepted(view, this, i);
                    return true;
                }
            } catch (AbstractMethodError e) {
                Log.e(VIEW_LOG_TAG, "ViewParent " + parent + " does not implement interface method onStartNestedScroll", e);
            }
            if (parent instanceof View) {
                view = parent;
            }
        }
        return false;
    }

    public void stopNestedScroll() {
        ViewParent viewParent = this.mNestedScrollingParent;
        if (viewParent != null) {
            viewParent.onStopNestedScroll(this);
            this.mNestedScrollingParent = null;
        }
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingParent != null;
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        int i5;
        int i6;
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (i != 0 || i2 != 0 || i3 != 0 || i4 != 0) {
                if (iArr != null) {
                    getLocationInWindow(iArr);
                    i5 = iArr[0];
                    i6 = iArr[1];
                } else {
                    i5 = 0;
                    i6 = 0;
                }
                this.mNestedScrollingParent.onNestedScroll(this, i, i2, i3, i4);
                if (iArr != null) {
                    getLocationInWindow(iArr);
                    iArr[0] = iArr[0] - i5;
                    iArr[1] = iArr[1] - i6;
                }
                return true;
            }
            if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        int i3;
        int i4;
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (i != 0 || i2 != 0) {
                if (iArr2 != null) {
                    getLocationInWindow(iArr2);
                    i3 = iArr2[0];
                    i4 = iArr2[1];
                } else {
                    i3 = 0;
                    i4 = 0;
                }
                if (iArr == null) {
                    if (this.mTempNestedScrollConsumed == null) {
                        this.mTempNestedScrollConsumed = new int[2];
                    }
                    iArr = this.mTempNestedScrollConsumed;
                }
                iArr[0] = 0;
                iArr[1] = 0;
                this.mNestedScrollingParent.onNestedPreScroll(this, i, i2, iArr);
                if (iArr2 != null) {
                    getLocationInWindow(iArr2);
                    iArr2[0] = iArr2[0] - i3;
                    iArr2[1] = iArr2[1] - i4;
                }
                return (iArr[0] == 0 && iArr[1] == 0) ? false : true;
            }
            if (iArr2 != null) {
                iArr2[0] = 0;
                iArr2[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        ViewParent viewParent;
        if (!isNestedScrollingEnabled() || (viewParent = this.mNestedScrollingParent) == null) {
            return false;
        }
        return viewParent.onNestedFling(this, f, f2, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        ViewParent viewParent;
        if (!isNestedScrollingEnabled() || (viewParent = this.mNestedScrollingParent) == null) {
            return false;
        }
        return viewParent.onNestedPreFling(this, f, f2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getVerticalScrollFactor() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = new TypedValue();
            if (!this.mContext.getTheme().resolveAttribute(16842829, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = typedValue.getDimension(this.mContext.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    public float semGetVerticalScrollFactor() {
        return getVerticalScrollFactor();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getHorizontalScrollFactor() {
        return getVerticalScrollFactor();
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "INHERIT"), @ViewDebug.IntToString(from = 1, to = "FIRST_STRONG"), @ViewDebug.IntToString(from = 2, to = "ANY_RTL"), @ViewDebug.IntToString(from = 3, to = "LTR"), @ViewDebug.IntToString(from = 4, to = "RTL"), @ViewDebug.IntToString(from = 5, to = "LOCALE"), @ViewDebug.IntToString(from = 6, to = "FIRST_STRONG_LTR"), @ViewDebug.IntToString(from = 7, to = "FIRST_STRONG_RTL")})
    public int getRawTextDirection() {
        return (this.mPrivateFlags2 & 448) >> 6;
    }

    public void setTextDirection(int i) {
        if (getRawTextDirection() != i) {
            this.mPrivateFlags2 &= -449;
            resetResolvedTextDirection();
            this.mPrivateFlags2 = ((i << 6) & 448) | this.mPrivateFlags2;
            resolveTextDirection();
            onRtlPropertiesChanged(getLayoutDirection());
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "INHERIT"), @ViewDebug.IntToString(from = 1, to = "FIRST_STRONG"), @ViewDebug.IntToString(from = 2, to = "ANY_RTL"), @ViewDebug.IntToString(from = 3, to = "LTR"), @ViewDebug.IntToString(from = 4, to = "RTL"), @ViewDebug.IntToString(from = 5, to = "LOCALE"), @ViewDebug.IntToString(from = 6, to = "FIRST_STRONG_LTR"), @ViewDebug.IntToString(from = 7, to = "FIRST_STRONG_RTL")})
    public int getTextDirection() {
        return (this.mPrivateFlags2 & 7168) >> 10;
    }

    public boolean resolveTextDirection() {
        this.mPrivateFlags2 &= -7681;
        if (hasRtlSupport()) {
            int rawTextDirection = getRawTextDirection();
            switch (rawTextDirection) {
                case 0:
                    if (!canResolveTextDirection()) {
                        this.mPrivateFlags2 |= 1024;
                        return false;
                    }
                    try {
                        ViewParent viewParent = this.mParent;
                        if (viewParent == null || !viewParent.isTextDirectionResolved()) {
                            this.mPrivateFlags2 |= 1024;
                            return false;
                        }
                        int i = 3;
                        try {
                            ViewParent viewParent2 = this.mParent;
                            if (viewParent2 != null) {
                                i = viewParent2.getTextDirection();
                            }
                        } catch (AbstractMethodError e) {
                            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                        }
                        switch (i) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                                this.mPrivateFlags2 |= i << 10;
                                break;
                            default:
                                this.mPrivateFlags2 |= 1024;
                                break;
                        }
                    } catch (AbstractMethodError e2) {
                        Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                        this.mPrivateFlags2 = this.mPrivateFlags2 | 1536;
                        return true;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    this.mPrivateFlags2 |= rawTextDirection << 10;
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
        if (getRawTextDirection() != 0) {
            return true;
        }
        ViewParent viewParent = this.mParent;
        if (viewParent == null) {
            return false;
        }
        try {
            return viewParent.canResolveTextDirection();
        } catch (AbstractMethodError e) {
            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            return false;
        }
    }

    public void resetResolvedTextDirection() {
        this.mPrivateFlags2 = (this.mPrivateFlags2 & (-7681)) | 1024;
    }

    public boolean isTextDirectionInherited() {
        return getRawTextDirection() == 0;
    }

    public boolean isTextDirectionResolved() {
        return (this.mPrivateFlags2 & 512) == 512;
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "INHERIT"), @ViewDebug.IntToString(from = 1, to = "GRAVITY"), @ViewDebug.IntToString(from = 2, to = "TEXT_START"), @ViewDebug.IntToString(from = 3, to = "TEXT_END"), @ViewDebug.IntToString(from = 4, to = "CENTER"), @ViewDebug.IntToString(from = 5, to = "VIEW_START"), @ViewDebug.IntToString(from = 6, to = "VIEW_END")})
    public int getRawTextAlignment() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_MASK) >> 13;
    }

    public void setTextAlignment(int i) {
        if (i != getRawTextAlignment()) {
            this.mPrivateFlags2 &= -57345;
            resetResolvedTextAlignment();
            this.mPrivateFlags2 = ((i << 13) & PFLAG2_TEXT_ALIGNMENT_MASK) | this.mPrivateFlags2;
            resolveTextAlignment();
            onRtlPropertiesChanged(getLayoutDirection());
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "INHERIT"), @ViewDebug.IntToString(from = 1, to = "GRAVITY"), @ViewDebug.IntToString(from = 2, to = "TEXT_START"), @ViewDebug.IntToString(from = 3, to = "TEXT_END"), @ViewDebug.IntToString(from = 4, to = "CENTER"), @ViewDebug.IntToString(from = 5, to = "VIEW_START"), @ViewDebug.IntToString(from = 6, to = "VIEW_END")})
    public int getTextAlignment() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK) >> 17;
    }

    public boolean resolveTextAlignment() {
        int i;
        this.mPrivateFlags2 &= -983041;
        if (hasRtlSupport()) {
            int rawTextAlignment = getRawTextAlignment();
            switch (rawTextAlignment) {
                case 0:
                    if (!canResolveTextAlignment()) {
                        this.mPrivateFlags2 |= 131072;
                        return false;
                    }
                    try {
                        ViewParent viewParent = this.mParent;
                        if (viewParent == null || !viewParent.isTextAlignmentResolved()) {
                            this.mPrivateFlags2 |= 131072;
                            return false;
                        }
                        try {
                            i = this.mParent.getTextAlignment();
                        } catch (AbstractMethodError e) {
                            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                            i = 1;
                        }
                        switch (i) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                this.mPrivateFlags2 = (i << 17) | this.mPrivateFlags2;
                                break;
                            default:
                                this.mPrivateFlags2 |= 131072;
                                break;
                        }
                    } catch (AbstractMethodError e2) {
                        Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                        this.mPrivateFlags2 = this.mPrivateFlags2 | 196608;
                        return true;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    this.mPrivateFlags2 |= rawTextAlignment << 17;
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
        if (getRawTextAlignment() != 0) {
            return true;
        }
        ViewParent viewParent = this.mParent;
        if (viewParent == null) {
            return false;
        }
        try {
            return viewParent.canResolveTextAlignment();
        } catch (AbstractMethodError e) {
            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            return false;
        }
    }

    public void resetResolvedTextAlignment() {
        this.mPrivateFlags2 = (this.mPrivateFlags2 & (-983041)) | 131072;
    }

    public boolean isTextAlignmentInherited() {
        return getRawTextAlignment() == 0;
    }

    public boolean isTextAlignmentResolved() {
        return (this.mPrivateFlags2 & 65536) == 65536;
    }

    public static int generateViewId() {
        AtomicInteger atomicInteger;
        int i;
        int i2;
        do {
            atomicInteger = sNextGeneratedId;
            i = atomicInteger.get();
            i2 = i + 1;
            if (i2 > 16777215) {
                i2 = 1;
            }
        } while (!atomicInteger.compareAndSet(i, i2));
        return i;
    }

    public void captureTransitioningViews(List<View> list) {
        if (getVisibility() == 0) {
            list.add(this);
        }
    }

    public void findNamedViews(Map<String, View> map) {
        String transitionName;
        if ((getVisibility() == 0 || this.mGhostView != null) && (transitionName = getTransitionName()) != null) {
            map.put(transitionName, this);
        }
    }

    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        float x = motionEvent.getX(i);
        float y = motionEvent.getY(i);
        if (!isDraggingScrollBar() && !isOnScrollbarThumb(x, y)) {
            int toolType = motionEvent.getToolType(i);
            if (toolType == 2) {
                PointerIcon pointerIcon = this.mPointerIconForStylus;
                if (pointerIcon != null) {
                    return pointerIcon;
                }
                PointerIcon pointerIcon2 = this.mMousePointerIcon;
                if (pointerIcon2 != null && pointerIcon2.getType() == -1) {
                    this.mMousePointerIcon.setType(20000);
                } else {
                    int i2 = this.mViewFlags;
                    if ((i2 & 1073741824) == 1073741824 && (i2 & 32) == 0 && isSPenHoveringSettingsEnabled()) {
                        return PointerIcon.getSystemIcon(this.mContext, 20010);
                    }
                }
            } else if (toolType == 3) {
                PointerIcon pointerIcon3 = this.mPointerIconForMouse;
                if (pointerIcon3 != null) {
                    return pointerIcon3;
                }
                PointerIcon pointerIcon4 = this.mMousePointerIcon;
                if (pointerIcon4 != null && pointerIcon4.getType() == 20000) {
                    this.mMousePointerIcon.setType(-1);
                }
            }
            if (motionEvent.isFromSource(8194)) {
                return this.mMousePointerIcon;
            }
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

    public void semSetPointerIcon(int i, PointerIcon pointerIcon) {
        if (pointerIcon != null) {
            Log.i("PointerIcon", "[View]semSetPointerIcon = " + pointerIcon.getType() + ", toolType = " + i + ", view = " + this + ", callingPid = " + Binder.getCallingPid());
        } else {
            Log.i("PointerIcon", "[View]semSetPointerIcon is NULL , toolType = " + i + ", view = " + this + ", callingPid = " + Binder.getCallingPid());
        }
        if (i == 2) {
            if (pointerIcon != null && pointerIcon.getType() == -1) {
                pointerIcon.setType(20000);
            }
            this.mPointerIconForStylus = pointerIcon;
        } else if (i == 3) {
            this.mPointerIconForMouse = pointerIcon;
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        viewRootImpl.refreshPointerIcon();
    }

    private void hidden_semSetPointerIcon(int i, PointerIcon pointerIcon) {
        semSetPointerIcon(i, pointerIcon);
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

    public void dispatchPointerCaptureChanged(boolean z) {
        onPointerCaptureChange(z);
    }

    public void setOnCapturedPointerListener(OnCapturedPointerListener onCapturedPointerListener) {
        getListenerInfo().mOnCapturedPointerListener = onCapturedPointerListener;
    }

    public static class MeasureSpec {
        public static final int AT_MOST = Integer.MIN_VALUE;
        public static final int EXACTLY = 1073741824;
        private static final int MODE_MASK = -1073741824;
        private static final int MODE_SHIFT = 30;
        public static final int UNSPECIFIED = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface MeasureSpecMode {
        }

        public static int getMode(int i) {
            return i & (-1073741824);
        }

        public static int getSize(int i) {
            return i & View.LAST_APP_AUTOFILL_ID;
        }

        public static int makeMeasureSpec(int i, int i2) {
            return (i & View.LAST_APP_AUTOFILL_ID) | (i2 & (-1073741824));
        }

        public static int makeSafeMeasureSpec(int i, int i2) {
            return makeMeasureSpec(i, i2);
        }

        static int adjust(int i, int i2) {
            int mode = getMode(i);
            int size = getSize(i);
            int i3 = 0;
            if (mode == 0) {
                return makeMeasureSpec(size, 0);
            }
            int i4 = size + i2;
            if (i4 < 0) {
                Log.e(View.VIEW_LOG_TAG, "MeasureSpec.adjust: new size would be negative! (" + i4 + ") spec: " + toString(i) + " delta: " + i2);
            } else {
                i3 = i4;
            }
            return makeMeasureSpec(i3, mode);
        }

        public static String toString(int i) {
            int mode = getMode(i);
            int size = getSize(i);
            StringBuilder sb = new StringBuilder("MeasureSpec: ");
            if (mode == 0) {
                sb.append("UNSPECIFIED ");
            } else if (mode == 1073741824) {
                sb.append("EXACTLY ");
            } else if (mode == Integer.MIN_VALUE) {
                sb.append("AT_MOST ");
            } else {
                sb.append(mode);
                sb.append(" ");
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

        private CheckForLongPress() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mOriginalPressedState == View.this.isPressed() && View.this.mParent != null && this.mOriginalWindowAttachCount == View.this.mWindowAttachCount) {
                View.this.recordGestureClassification(this.mClassification);
                if (View.this.performLongClick(this.mX, this.mY)) {
                    View.this.mHasPerformedLongPress = true;
                }
            }
        }

        public void setAnchor(float f, float f2) {
            this.mX = f;
            this.mY = f2;
        }

        public void rememberWindowAttachCount() {
            this.mOriginalWindowAttachCount = View.this.mWindowAttachCount;
        }

        public void rememberPressedState() {
            this.mOriginalPressedState = View.this.isPressed();
        }

        public void setClassification(int i) {
            this.mClassification = i;
        }
    }

    private final class CheckForTap implements Runnable {
        public float x;
        public float y;

        private CheckForTap() {
        }

        @Override // java.lang.Runnable
        public void run() {
            View.this.mPrivateFlags &= -33554433;
            View.this.setPressed(true, this.x, this.y);
            View.this.checkForLongClick(ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout(), this.x, this.y, 3);
        }
    }

    private final class PerformClick implements Runnable {
        private PerformClick() {
        }

        @Override // java.lang.Runnable
        public void run() {
            View.this.recordGestureClassification(1);
            View.this.performClickInternal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordGestureClassification(int i) {
        if (i == 0) {
            return;
        }
        FrameworkStatsLog.write(177, getClass().getName(), i);
    }

    public ViewPropertyAnimator animate() {
        if (this.mAnimator == null) {
            this.mAnimator = new ViewPropertyAnimator(this);
        }
        return this.mAnimator;
    }

    public final void setTransitionName(String str) {
        this.mTransitionName = str;
    }

    @ViewDebug.ExportedProperty
    public String getTransitionName() {
        return this.mTransitionName;
    }

    private final class UnsetPressedState implements Runnable {
        private UnsetPressedState() {
        }

        @Override // java.lang.Runnable
        public void run() {
            View.this.setPressed(false);
        }
    }

    private static class VisibilityChangeForAutofillHandler extends Handler {
        private final AutofillManager mAfm;
        private final View mView;

        private VisibilityChangeForAutofillHandler(AutofillManager autofillManager, View view) {
            this.mAfm = autofillManager;
            this.mView = view;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AutofillManager autofillManager = this.mAfm;
            View view = this.mView;
            autofillManager.notifyViewVisibilityChanged(view, view.isShown());
        }
    }

    public static class BaseSavedState extends AbsSavedState {
        static final int AUTOFILL_ID = 4;
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.ClassLoaderCreator<BaseSavedState>() { // from class: android.view.View.BaseSavedState.1
            @Override // android.os.Parcelable.Creator
            public BaseSavedState createFromParcel(Parcel parcel) {
                return new BaseSavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public BaseSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new BaseSavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public BaseSavedState[] newArray(int i) {
                return new BaseSavedState[i];
            }
        };
        static final int IS_AUTOFILLED = 2;
        static final int START_ACTIVITY_REQUESTED_WHO_SAVED = 1;
        int mAutofillViewId;
        boolean mHideHighlight;
        boolean mIsAutofilled;
        int mSavedData;
        String mStartActivityRequestWhoSaved;

        public BaseSavedState(Parcel parcel) {
            this(parcel, null);
        }

        public BaseSavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mSavedData = parcel.readInt();
            this.mStartActivityRequestWhoSaved = parcel.readString();
            this.mIsAutofilled = parcel.readBoolean();
            this.mHideHighlight = parcel.readBoolean();
            this.mAutofillViewId = parcel.readInt();
        }

        public BaseSavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mSavedData);
            parcel.writeString(this.mStartActivityRequestWhoSaved);
            parcel.writeBoolean(this.mIsAutofilled);
            parcel.writeBoolean(this.mHideHighlight);
            parcel.writeInt(this.mAutofillViewId);
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
        final ViewTreeObserver.InternalInsetsInfo mGivenInternalInsets = new ViewTreeObserver.InternalInsetsInfo();
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
            boolean performHapticFeedback(int i, int i2, int i3);

            void performHapticFeedbackForInputDevice(int i, int i2, int i3, int i4, int i5);

            void playSoundEffect(int i);
        }

        static class InvalidateInfo {
            private static final int POOL_LIMIT = 10;
            private static final Pools.SynchronizedPool<InvalidateInfo> sPool = new Pools.SynchronizedPool<>(10);
            int bottom;
            int left;
            int right;
            View target;
            int top;

            InvalidateInfo() {
            }

            public static InvalidateInfo obtain() {
                InvalidateInfo acquire = sPool.acquire();
                return acquire != null ? acquire : new InvalidateInfo();
            }

            public void recycle() {
                this.target = null;
                sPool.release(this);
            }
        }

        AttachInfo(IWindowSession iWindowSession, IWindow iWindow, Display display, ViewRootImpl viewRootImpl, Handler handler, Callbacks callbacks, Context context) {
            this.mSession = iWindowSession;
            this.mWindow = iWindow;
            this.mWindowToken = iWindow.asBinder();
            this.mDisplay = display;
            this.mViewRootImpl = viewRootImpl;
            this.mHandler = handler;
            this.mRootCallbacks = callbacks;
            this.mTreeObserver = new ViewTreeObserver(context);
            this.mDensity = context.getResources().getDisplayMetrics().density;
            float f = r4.widthPixels * r4.heightPixels;
            this.mDisplayPixelCount = f == 0.0f ? Float.POSITIVE_INFINITY : f;
        }

        void increaseSensitiveViewsCount() {
            if (this.mSensitiveViewsCount == 0) {
                this.mViewRootImpl.addSensitiveContentAppProtection();
            }
            this.mSensitiveViewsCount++;
        }

        void decreaseSensitiveViewsCount() {
            int i = this.mSensitiveViewsCount - 1;
            this.mSensitiveViewsCount = i;
            if (i == 0) {
                this.mViewRootImpl.removeSensitiveContentAppProtection();
            }
            if (this.mSensitiveViewsCount < 0) {
                Log.wtf(View.VIEW_LOG_TAG, "mSensitiveViewsCount is negative" + this.mSensitiveViewsCount);
                this.mSensitiveViewsCount = 0;
            }
        }

        ContentCaptureManager getContentCaptureManager(Context context) {
            ContentCaptureManager contentCaptureManager = this.mContentCaptureManager;
            if (contentCaptureManager != null) {
                return contentCaptureManager;
            }
            ContentCaptureManager contentCaptureManager2 = (ContentCaptureManager) context.getSystemService(ContentCaptureManager.class);
            this.mContentCaptureManager = contentCaptureManager2;
            return contentCaptureManager2;
        }

        void delayNotifyContentCaptureInsetsEvent(Insets insets) {
            ContentCaptureManager contentCaptureManager = this.mContentCaptureManager;
            if (contentCaptureManager == null) {
                return;
            }
            ensureEvents(contentCaptureManager.getMainContentCaptureSession()).add(insets);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void delayNotifyContentCaptureEvent(ContentCaptureSession contentCaptureSession, View view, boolean z) {
            ArrayList<Object> ensureEvents = ensureEvents(contentCaptureSession);
            AutofillId autofillId = view;
            if (!z) {
                autofillId = view.getAutofillId();
            }
            ensureEvents.add(autofillId);
        }

        private ArrayList<Object> ensureEvents(ContentCaptureSession contentCaptureSession) {
            if (this.mContentCaptureEvents == null) {
                this.mContentCaptureEvents = new SparseArray<>(1);
            }
            int id = contentCaptureSession.getId();
            ArrayList<Object> arrayList = this.mContentCaptureEvents.get(id);
            if (arrayList != null) {
                return arrayList;
            }
            ArrayList<Object> arrayList2 = new ArrayList<>();
            this.mContentCaptureEvents.put(id, arrayList2);
            return arrayList2;
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

        public void dump(String str, PrintWriter printWriter) {
            String str2 = str + "  ";
            printWriter.println(str + "AttachInfo:");
            printWriter.println(str2 + "mHasWindowFocus=" + this.mHasWindowFocus);
            printWriter.println(str2 + "mWindowVisibility=" + this.mWindowVisibility);
            printWriter.println(str2 + "mInTouchMode=" + this.mInTouchMode);
            printWriter.println(str2 + "mUnbufferedDispatchRequested=" + this.mUnbufferedDispatchRequested);
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
        public final Matrix matrix;
        public final Paint paint;
        public ScrollBarDrawable scrollBar;
        public int scrollBarMinTouchTarget;
        public int scrollBarSize;
        public Shader shader;
        public final Interpolator scrollBarInterpolator = new Interpolator(1, 2);
        public int state = 0;
        public final Rect mScrollBarBounds = new Rect();
        public final Rect mScrollBarTouchBounds = new Rect();
        public int mScrollBarDraggingState = 0;
        public float mScrollBarDraggingPos = 0.0f;
        public int scrollBarDefaultDelayBeforeFade = ViewConfiguration.getScrollDefaultDelay();
        public int scrollBarFadeDuration = ViewConfiguration.getScrollBarFadeDuration();

        public ScrollabilityCache(ViewConfiguration viewConfiguration, View view) {
            this.fadingEdgeLength = viewConfiguration.getScaledFadingEdgeLength();
            this.scrollBarSize = viewConfiguration.getScaledScrollBarSize();
            this.scrollBarMinTouchTarget = view.semGetScaledMinScrollbarTouchTarget(viewConfiguration);
            Paint paint = new Paint();
            this.paint = paint;
            this.matrix = new Matrix();
            LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
            this.shader = linearGradient;
            paint.setShader(linearGradient);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            this.host = view;
        }

        public void setFadeColor(int i) {
            if (i != this.mLastColor) {
                this.mLastColor = i;
                if (i != 0) {
                    LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, i | (-16777216), i & 16777215, Shader.TileMode.CLAMP);
                    this.shader = linearGradient;
                    this.paint.setShader(linearGradient);
                    this.paint.setXfermode(null);
                    return;
                }
                LinearGradient linearGradient2 = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
                this.shader = linearGradient2;
                this.paint.setShader(linearGradient2);
                this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            if (currentAnimationTimeMillis >= this.fadeStartTime) {
                int i = (int) currentAnimationTimeMillis;
                Interpolator interpolator = this.scrollBarInterpolator;
                interpolator.setKeyFrame(0, i, OPAQUE);
                interpolator.setKeyFrame(1, i + this.scrollBarFadeDuration, TRANSPARENT);
                this.state = 2;
                this.host.invalidate(true);
            }
        }
    }

    private class SendAccessibilityEventThrottle implements Runnable {
        private AccessibilityEvent mAccessibilityEvent;
        public volatile boolean mIsPending;

        private SendAccessibilityEventThrottle() {
        }

        public void post(AccessibilityEvent accessibilityEvent) {
            updateWithAccessibilityEvent(accessibilityEvent);
            if (this.mIsPending) {
                return;
            }
            this.mIsPending = true;
            View.this.postDelayed(this, ViewConfiguration.getSendRecurringAccessibilityEventsInterval());
        }

        @Override // java.lang.Runnable
        public void run() {
            if (AccessibilityManager.getInstance(View.this.mContext).isEnabled() && View.this.isShown()) {
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

        private SendViewScrolledAccessibilityEvent(View view) {
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

    private void cancel(SendAccessibilityEventThrottle sendAccessibilityEventThrottle) {
        if (sendAccessibilityEventThrottle == null || !sendAccessibilityEventThrottle.mIsPending) {
            return;
        }
        removeCallbacks(sendAccessibilityEventThrottle);
        sendAccessibilityEventThrottle.reset();
    }

    public static class AccessibilityDelegate {
        public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
            return null;
        }

        public void sendAccessibilityEvent(View view, int i) {
            view.sendAccessibilityEventInternal(i);
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return view.performAccessibilityActionInternal(i, bundle);
        }

        public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            view.sendAccessibilityEventUncheckedInternal(accessibilityEvent);
        }

        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            return view.dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            view.onPopulateAccessibilityEventInternal(accessibilityEvent);
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            view.onInitializeAccessibilityEventInternal(accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            view.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        }

        public void addExtraDataToAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo, String str, Bundle bundle) {
            view.addExtraDataToAccessibilityNodeInfo(accessibilityNodeInfo, str, bundle);
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return viewGroup.onRequestSendAccessibilityEventInternal(view, accessibilityEvent);
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(View view) {
            return view.createAccessibilityNodeInfoInternal();
        }
    }

    private static class MatchIdPredicate implements Predicate<View> {
        public int mId;

        private MatchIdPredicate() {
        }

        @Override // java.util.function.Predicate
        public boolean test(View view) {
            return view.mID == this.mId;
        }
    }

    private static class MatchLabelForPredicate implements Predicate<View> {
        private int mLabeledId;

        private MatchLabelForPredicate() {
        }

        @Override // java.util.function.Predicate
        public boolean test(View view) {
            return view.mLabelForId == this.mLabeledId;
        }
    }

    private static class SensitiveAutofillHintsHelper {
        private static final ArraySet<String> SENSITIVE_CONTENT_AUTOFILL_HINTS;

        private SensitiveAutofillHintsHelper() {
        }

        static {
            ArraySet<String> arraySet = new ArraySet<>();
            SENSITIVE_CONTENT_AUTOFILL_HINTS = arraySet;
            arraySet.add(View.AUTOFILL_HINT_USERNAME);
            arraySet.add(View.AUTOFILL_HINT_PASSWORD_AUTO);
            arraySet.add("password");
            arraySet.add(View.AUTOFILL_HINT_CREDIT_CARD_NUMBER);
            arraySet.add(View.AUTOFILL_HINT_CREDIT_CARD_SECURITY_CODE);
            arraySet.add(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE);
            arraySet.add(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DAY);
            arraySet.add(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_MONTH);
            arraySet.add(View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_YEAR);
            arraySet.add("credential");
        }

        static boolean containsSensitiveAutofillHint(String[] strArr) {
            if (strArr == null) {
                return false;
            }
            for (String str : strArr) {
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

    public void setScrollCaptureHint(int i) {
        int i2 = this.mPrivateFlags4 & (-7169);
        this.mPrivateFlags4 = i2;
        if ((i & 1) != 0) {
            i &= -3;
        }
        this.mPrivateFlags4 = ((i << 10) & 7168) | i2;
    }

    public final void setScrollCaptureCallback(ScrollCaptureCallback scrollCaptureCallback) {
        getListenerInfo().mScrollCaptureCallback = scrollCaptureCallback;
    }

    public ScrollCaptureCallback createScrollCaptureCallbackInternal(Rect rect, Point point) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return null;
        }
        if (attachInfo.mScrollCaptureInternal == null) {
            this.mAttachInfo.mScrollCaptureInternal = new ScrollCaptureInternal();
        }
        return this.mAttachInfo.mScrollCaptureInternal.requestCallback(this, rect, point);
    }

    public void dispatchScrollCaptureSearch(Rect rect, Point point, Consumer<ScrollCaptureTarget> consumer) {
        onScrollCaptureSearch(rect, point, consumer);
    }

    public void onScrollCaptureSearch(Rect rect, Point point, Consumer<ScrollCaptureTarget> consumer) {
        if ((getScrollCaptureHint() & 1) != 0) {
            return;
        }
        Rect rect2 = this.mClipBounds;
        if (rect2 != null ? rect.intersect(rect2) : true) {
            ListenerInfo listenerInfo = this.mListenerInfo;
            ScrollCaptureCallback scrollCaptureCallback = listenerInfo == null ? null : listenerInfo.mScrollCaptureCallback;
            if (scrollCaptureCallback == null) {
                scrollCaptureCallback = createScrollCaptureCallbackInternal(rect, point);
            }
            if (scrollCaptureCallback != null) {
                consumer.accept(new ScrollCaptureTarget(this, new Rect(rect), new Point(point.x, point.y), scrollCaptureCallback));
            }
        }
    }

    private static void dumpFlags() {
        HashMap newHashMap = Maps.newHashMap();
        try {
            for (Field field : View.class.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                    if (field.getType().equals(Integer.TYPE)) {
                        dumpFlag(newHashMap, field.getName(), field.getInt(null));
                    } else if (field.getType().equals(int[].class)) {
                        int[] iArr = (int[]) field.get(null);
                        for (int i = 0; i < iArr.length; i++) {
                            dumpFlag(newHashMap, field.getName() + NavigationBarInflaterView.SIZE_MOD_START + i + NavigationBarInflaterView.SIZE_MOD_END, iArr[i]);
                        }
                    }
                }
            }
            ArrayList newArrayList = Lists.newArrayList();
            newArrayList.addAll(newHashMap.keySet());
            Collections.sort(newArrayList);
            Iterator it = newArrayList.iterator();
            while (it.hasNext()) {
                Log.d(VIEW_LOG_TAG, (String) newHashMap.get((String) it.next()));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void dumpFlag(HashMap<String, String> hashMap, String str, int i) {
        String replace = String.format("%32s", Integer.toBinaryString(i)).replace('0', ' ');
        int indexOf = str.indexOf(95);
        StringBuilder sb = new StringBuilder();
        sb.append(indexOf > 0 ? str.substring(0, indexOf) : str);
        sb.append(replace);
        sb.append(str);
        hashMap.put(sb.toString(), replace + " " + str);
    }

    public void encode(ViewHierarchyEncoder viewHierarchyEncoder) {
        viewHierarchyEncoder.beginObject(this);
        encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.endObject();
    }

    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        Object resolveId = ViewDebug.resolveId(getContext(), this.mID);
        if (resolveId instanceof String) {
            viewHierarchyEncoder.addProperty("id", (String) resolveId);
        } else {
            viewHierarchyEncoder.addProperty("id", this.mID);
        }
        TransformationInfo transformationInfo = this.mTransformationInfo;
        viewHierarchyEncoder.addProperty("misc:transformation.alpha", transformationInfo != null ? transformationInfo.mAlpha : 0.0f);
        viewHierarchyEncoder.addProperty("misc:transitionName", getTransitionName());
        viewHierarchyEncoder.addProperty("layout:left", this.mLeft);
        viewHierarchyEncoder.addProperty("layout:right", this.mRight);
        viewHierarchyEncoder.addProperty("layout:top", this.mTop);
        viewHierarchyEncoder.addProperty("layout:bottom", this.mBottom);
        viewHierarchyEncoder.addProperty("layout:width", getWidth());
        viewHierarchyEncoder.addProperty("layout:height", getHeight());
        viewHierarchyEncoder.addProperty("layout:layoutDirection", getLayoutDirection());
        viewHierarchyEncoder.addProperty("layout:layoutRtl", isLayoutRtl());
        viewHierarchyEncoder.addProperty("layout:hasTransientState", hasTransientState());
        viewHierarchyEncoder.addProperty("layout:baseline", getBaseline());
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            viewHierarchyEncoder.addPropertyKey("layoutParams");
            layoutParams.encode(viewHierarchyEncoder);
        }
        viewHierarchyEncoder.addProperty("scrolling:scrollX", this.mScrollX);
        viewHierarchyEncoder.addProperty("scrolling:scrollY", this.mScrollY);
        viewHierarchyEncoder.addProperty("padding:paddingLeft", this.mPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:paddingRight", this.mPaddingRight);
        viewHierarchyEncoder.addProperty("padding:paddingTop", this.mPaddingTop);
        viewHierarchyEncoder.addProperty("padding:paddingBottom", this.mPaddingBottom);
        viewHierarchyEncoder.addProperty("padding:userPaddingRight", this.mUserPaddingRight);
        viewHierarchyEncoder.addProperty("padding:userPaddingLeft", this.mUserPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:userPaddingBottom", this.mUserPaddingBottom);
        viewHierarchyEncoder.addProperty("padding:userPaddingStart", this.mUserPaddingStart);
        viewHierarchyEncoder.addProperty("padding:userPaddingEnd", this.mUserPaddingEnd);
        viewHierarchyEncoder.addProperty("measurement:minHeight", this.mMinHeight);
        viewHierarchyEncoder.addProperty("measurement:minWidth", this.mMinWidth);
        viewHierarchyEncoder.addProperty("measurement:measuredWidth", this.mMeasuredWidth);
        viewHierarchyEncoder.addProperty("measurement:measuredHeight", this.mMeasuredHeight);
        viewHierarchyEncoder.addProperty("drawing:elevation", getElevation());
        viewHierarchyEncoder.addProperty("drawing:translationX", getTranslationX());
        viewHierarchyEncoder.addProperty("drawing:translationY", getTranslationY());
        viewHierarchyEncoder.addProperty("drawing:translationZ", getTranslationZ());
        viewHierarchyEncoder.addProperty("drawing:rotation", getRotation());
        viewHierarchyEncoder.addProperty("drawing:rotationX", getRotationX());
        viewHierarchyEncoder.addProperty("drawing:rotationY", getRotationY());
        viewHierarchyEncoder.addProperty("drawing:scaleX", getScaleX());
        viewHierarchyEncoder.addProperty("drawing:scaleY", getScaleY());
        viewHierarchyEncoder.addProperty("drawing:pivotX", getPivotX());
        viewHierarchyEncoder.addProperty("drawing:pivotY", getPivotY());
        Rect rect = this.mClipBounds;
        viewHierarchyEncoder.addProperty("drawing:clipBounds", rect == null ? null : rect.toString());
        viewHierarchyEncoder.addProperty("drawing:opaque", isOpaque());
        viewHierarchyEncoder.addProperty("drawing:alpha", getAlpha());
        viewHierarchyEncoder.addProperty("drawing:transitionAlpha", getTransitionAlpha());
        viewHierarchyEncoder.addProperty("drawing:shadow", hasShadow());
        viewHierarchyEncoder.addProperty("drawing:solidColor", getSolidColor());
        viewHierarchyEncoder.addProperty("drawing:layerType", this.mLayerType);
        viewHierarchyEncoder.addProperty("drawing:willNotDraw", willNotDraw());
        viewHierarchyEncoder.addProperty("drawing:hardwareAccelerated", isHardwareAccelerated());
        viewHierarchyEncoder.addProperty("drawing:willNotCacheDrawing", willNotCacheDrawing());
        viewHierarchyEncoder.addProperty("drawing:drawingCacheEnabled", isDrawingCacheEnabled());
        viewHierarchyEncoder.addProperty("drawing:overlappingRendering", hasOverlappingRendering());
        viewHierarchyEncoder.addProperty("drawing:outlineAmbientShadowColor", getOutlineAmbientShadowColor());
        viewHierarchyEncoder.addProperty("drawing:outlineSpotShadowColor", getOutlineSpotShadowColor());
        viewHierarchyEncoder.addProperty("focus:hasFocus", hasFocus());
        viewHierarchyEncoder.addProperty("focus:isFocused", isFocused());
        viewHierarchyEncoder.addProperty("focus:focusable", getFocusable());
        viewHierarchyEncoder.addProperty("focus:isFocusable", isFocusable());
        viewHierarchyEncoder.addProperty("focus:isFocusableInTouchMode", isFocusableInTouchMode());
        viewHierarchyEncoder.addProperty("misc:clickable", isClickable());
        viewHierarchyEncoder.addProperty("misc:pressed", isPressed());
        viewHierarchyEncoder.addProperty("misc:selected", isSelected());
        viewHierarchyEncoder.addProperty("misc:touchMode", isInTouchMode());
        viewHierarchyEncoder.addProperty("misc:hovered", isHovered());
        viewHierarchyEncoder.addProperty("misc:activated", isActivated());
        viewHierarchyEncoder.addProperty("misc:visibility", getVisibility());
        viewHierarchyEncoder.addProperty("misc:fitsSystemWindows", getFitsSystemWindows());
        viewHierarchyEncoder.addProperty("misc:filterTouchesWhenObscured", getFilterTouchesWhenObscured());
        viewHierarchyEncoder.addProperty("misc:enabled", isEnabled());
        viewHierarchyEncoder.addProperty("misc:soundEffectsEnabled", isSoundEffectsEnabled());
        viewHierarchyEncoder.addProperty("misc:hapticFeedbackEnabled", isHapticFeedbackEnabled());
        Resources.Theme theme = getContext().getTheme();
        if (theme != null) {
            viewHierarchyEncoder.addPropertyKey("theme");
            theme.encode(viewHierarchyEncoder);
        }
        String[] strArr = this.mAttributes;
        int length = strArr != null ? strArr.length : 0;
        viewHierarchyEncoder.addProperty("meta:__attrCount__", length / 2);
        for (int i = 0; i < length; i += 2) {
            viewHierarchyEncoder.addProperty("meta:__attr__" + this.mAttributes[i], this.mAttributes[i + 1]);
        }
        viewHierarchyEncoder.addProperty("misc:scrollBarStyle", getScrollBarStyle());
        viewHierarchyEncoder.addProperty("text:textDirection", getTextDirection());
        viewHierarchyEncoder.addProperty("text:textAlignment", getTextAlignment());
        CharSequence contentDescription = getContentDescription();
        viewHierarchyEncoder.addUserProperty("accessibility:contentDescription", contentDescription == null ? "" : contentDescription.toString());
        viewHierarchyEncoder.addProperty("accessibility:labelFor", getLabelFor());
        viewHierarchyEncoder.addProperty("accessibility:importantForAccessibility", getImportantForAccessibility());
    }

    boolean shouldDrawRoundScrollbar() {
        if (this.mResources.getConfiguration().isScreenRound() && this.mAttachInfo != null) {
            View rootView = getRootView();
            getRootWindowInsets();
            int height = getHeight();
            int width = getWidth();
            int height2 = rootView.getHeight();
            int width2 = rootView.getWidth();
            if (height == height2 && width == width2) {
                return true;
            }
        }
        return false;
    }

    @RemotableViewMethod
    public void semSetHoverPopupType(int i) {
        if (isHoveringUIEnabled()) {
            this.mHoverPopupType = i;
            if (i == 1) {
                semSetTooltipText(getContentDescription());
            } else if ((this.mSemViewFlags & 2) == 2) {
                semSetTooltipText(null);
            }
        }
    }

    private void hidden_semSetHoverPopupType(int i) {
        semSetHoverPopupType(i);
    }

    public int semGetHoverPopupType() {
        if (isHoveringUIEnabled()) {
            return this.mHoverPopupType;
        }
        return 0;
    }

    public SemHoverPopupWindow semGetHoverPopup(boolean z) {
        if (!isHoveringUIEnabled()) {
            return null;
        }
        if (this.mHoverPopup == null) {
            if (!z) {
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

    private SemHoverPopupWindow hidden_semGetHoverPopup(boolean z) {
        return semGetHoverPopup(z);
    }

    public SemHoverPopupWindow semGetHoverPopup(int i) {
        SemHoverPopupWindow semHoverPopupWindow;
        if (!isHoveringUIEnabled()) {
            return null;
        }
        if (this.mHoverPopup == null) {
            if (i == 1) {
                int i2 = this.mHoverPopupType;
                if (i2 == 2 || i2 == 3) {
                    this.mHoverPopup = new SemHoverPopupWindow(this, this.mHoverPopupType);
                }
            } else if (i == 2 || i == 3) {
                this.mHoverPopup = new SemHoverPopupWindow(this, this.mHoverPopupType);
            }
        }
        setSemHoverPopupWindowSettings(i);
        this.mHoverPopupToolTypeByApp = i;
        if (i == 1 && this.mHoverPopupType == 1 && (semHoverPopupWindow = this.mHoverPopup) != null) {
            semHoverPopupWindow.dismiss();
            this.mHoverPopup = null;
        }
        return this.mHoverPopup;
    }

    public SemHoverPopupWindow semGetHoverPopup(int i, boolean z) {
        int i2 = this.mHoverPopupToolTypeByApp;
        semGetHoverPopup(i);
        if (!z) {
            this.mHoverPopupToolTypeByApp = i2;
        }
        return this.mHoverPopup;
    }

    protected boolean setSemHoverPopupWindowSettings(int i) {
        SemHoverPopupWindow semHoverPopupWindow = this.mHoverPopup;
        if (semHoverPopupWindow == null) {
            return false;
        }
        if (i == 1) {
            semHoverPopupWindow.setHoverPopupToolType(1);
        } else if (i == 2) {
            semHoverPopupWindow.setHoverPopupToolType(2);
        } else if (i == 3) {
            semHoverPopupWindow.setHoverPopupToolType(3);
        }
        return true;
    }

    protected boolean isHoveringUIEnabled() {
        int i = sHoverUIEnableFlag;
        if ((i & 15) == 0) {
            int i2 = i & (-16);
            sHoverUIEnableFlag = i2;
            sHoverUIEnableFlag = i2 | (ViewRune.WIDGET_PEN_SUPPORTED ? 1 : 2);
        }
        return (sHoverUIEnableFlag & 15) == 1 || semIsDesktopMode();
    }

    protected int getHoverUIFeatureLevel() {
        if (sHoverUIFeatureLevel < 0 && !sIsCheckedHoverUIFeatureLevel) {
            sIsCheckedHoverUIFeatureLevel = true;
            try {
                if (ViewRune.WIDGET_PEN_SUPPORTED) {
                    sHoverUIFeatureLevel = 2;
                }
            } catch (AbstractMethodError unused) {
                Log.d(VIEW_LOG_TAG, "AbstractMethodError occured.");
            }
        }
        return sHoverUIFeatureLevel;
    }

    public void setTooltipText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            setFlags(0, 1073741824);
            hideTooltip();
            this.mTooltipInfo = null;
            return;
        }
        setFlags(1073741824, 1073741824);
        if (this.mTooltipInfo == null) {
            TooltipInfo tooltipInfo = new TooltipInfo();
            this.mTooltipInfo = tooltipInfo;
            tooltipInfo.mShowTooltipRunnable = new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    View.this.showHoverTooltip();
                }
            };
            this.mTooltipInfo.mHideTooltipRunnable = new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    View.this.hideTooltip();
                }
            };
            this.mTooltipInfo.mHoverSlop = ViewConfiguration.get(this.mContext).getScaledHoverSlop();
            this.mTooltipInfo.clearAnchorPos();
        }
        this.mTooltipInfo.mTooltipText = charSequence;
    }

    public void setTooltip(CharSequence charSequence) {
        setTooltipText(charSequence);
    }

    private void semSetTooltipText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            int i = this.mSemViewFlags;
            if ((i & 2) != 0) {
                this.mSemViewFlags = i & (-3);
            }
        } else {
            int i2 = this.mSemViewFlags;
            if ((i2 & 2) == 0) {
                this.mSemViewFlags = i2 | 2;
            }
        }
        setTooltipText(charSequence);
    }

    public CharSequence getTooltipText() {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo != null) {
            return tooltipInfo.mTooltipText;
        }
        return null;
    }

    public CharSequence getTooltip() {
        return getTooltipText();
    }

    public void setTooltipPosition(int i, int i2) {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo != null) {
            tooltipInfo.mSemX = i;
            this.mTooltipInfo.mSemY = i2;
            this.mTooltipInfo.mSemSetTooltipPosition = true;
        }
    }

    public int getTooltipPositionX() {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo != null) {
            return tooltipInfo.mSemX;
        }
        return 0;
    }

    public int getTooltipPositionY() {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo != null) {
            return tooltipInfo.mSemY;
        }
        return 0;
    }

    public void setTooltipNull(boolean z) {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo != null) {
            tooltipInfo.mSemIsTooltipNull = z;
        }
    }

    private boolean showTooltip(int i, int i2, boolean z) {
        TooltipInfo tooltipInfo;
        boolean z2;
        ViewRootImpl viewRootImpl;
        if (this.mAttachInfo == null || (tooltipInfo = this.mTooltipInfo) == null) {
            return false;
        }
        if ((z && (this.mViewFlags & 32) != 0) || TextUtils.isEmpty(tooltipInfo.mTooltipText)) {
            return false;
        }
        hideTooltip();
        this.mTooltipInfo.mTooltipFromLongClick = z;
        this.mTooltipInfo.mTooltipPopup = new TooltipPopup(getContext());
        boolean z3 = (this.mPrivateFlags3 & 131072) == 131072;
        if (CoreRune.MW_CAPTION_TOOLTIP && (viewRootImpl = getViewRootImpl()) != null) {
            int i3 = viewRootImpl.mWindowAttributes.multiWindowFlags;
            if ((i3 & 1) != 0) {
                this.mTooltipInfo.mTooltipPopup.setForCaptionMenuButton();
                z2 = true;
            } else if ((i3 & 2) != 0) {
                this.mTooltipInfo.mTooltipPopup.setForCaptionPopupButton();
                z2 = z;
            }
            this.mTooltipInfo.mTooltipPopup.show(this, i, i2, z2, this.mTooltipInfo.mTooltipText);
            this.mAttachInfo.mTooltipHost = this;
            notifyViewAccessibilityStateChangedIfNeeded(0);
            return true;
        }
        z2 = z3;
        this.mTooltipInfo.mTooltipPopup.show(this, i, i2, z2, this.mTooltipInfo.mTooltipText);
        this.mAttachInfo.mTooltipHost = this;
        notifyViewAccessibilityStateChangedIfNeeded(0);
        return true;
    }

    void hideTooltip() {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo == null) {
            return;
        }
        removeCallbacks(tooltipInfo.mShowTooltipRunnable);
        if (this.mTooltipInfo.mTooltipPopup == null) {
            return;
        }
        this.mTooltipInfo.mTooltipPopup.hide();
        this.mTooltipInfo.mTooltipPopup = null;
        this.mTooltipInfo.mTooltipFromLongClick = false;
        this.mTooltipInfo.clearAnchorPos();
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mTooltipHost = null;
        }
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public void semHideTooltip() {
        hideTooltip();
    }

    private boolean showLongClickTooltip(int i, int i2) {
        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        return showTooltip(i, i2, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showHoverTooltip() {
        return showTooltip(this.mTooltipInfo.mAnchorX, this.mTooltipInfo.mAnchorY, false);
    }

    boolean dispatchTooltipHoverEvent(MotionEvent motionEvent) {
        int hoverTooltipHideTimeout;
        if (this.mTooltipInfo == null) {
            return false;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        boolean z = attachInfo != null && attachInfo.mViewRootImpl.mIsDeviceDefault;
        if (z && motionEvent.isFromSource(16386) && !isSPenHoveringSettingsEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 7) {
            int i = this.mViewFlags;
            if ((i & 1073741824) == 1073741824 && (!z || (i & 32) == 0)) {
                if (!this.mTooltipInfo.mTooltipFromLongClick && this.mTooltipInfo.updateAnchorPos(motionEvent)) {
                    if (this.mTooltipInfo.mTooltipPopup == null) {
                        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
                        postDelayed(this.mTooltipInfo.mShowTooltipRunnable, ViewConfiguration.getHoverTooltipShowTimeout());
                    }
                    if ((getWindowSystemUiVisibility() & 1) == 1) {
                        hoverTooltipHideTimeout = ViewConfiguration.getHoverTooltipHideShortTimeout();
                    } else {
                        hoverTooltipHideTimeout = ViewConfiguration.getHoverTooltipHideTimeout();
                    }
                    removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
                    postDelayed(this.mTooltipInfo.mHideTooltipRunnable, hoverTooltipHideTimeout);
                }
                return true;
            }
        } else if (action == 10) {
            this.mTooltipInfo.clearAnchorPos();
            if (!this.mTooltipInfo.mTooltipFromLongClick) {
                hideTooltip();
            }
        }
        return false;
    }

    void handleTooltipKey(KeyEvent keyEvent) {
        int action = keyEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                return;
            }
            handleTooltipUp();
        } else if (keyEvent.getRepeatCount() == 0) {
            hideTooltip();
        }
    }

    private void handleTooltipUp() {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo == null || tooltipInfo.mTooltipPopup == null) {
            return;
        }
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        postDelayed(this.mTooltipInfo.mHideTooltipRunnable, ViewConfiguration.getLongPressTooltipHideTimeout());
    }

    private int getFocusableAttribute(TypedArray typedArray) {
        TypedValue typedValue = new TypedValue();
        if (!typedArray.getValue(19, typedValue)) {
            return 16;
        }
        if (typedValue.type == 18) {
            return typedValue.data == 0 ? 0 : 1;
        }
        return typedValue.data;
    }

    public View getTooltipView() {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo == null || tooltipInfo.mTooltipPopup == null) {
            return null;
        }
        return this.mTooltipInfo.mTooltipPopup.getContentView();
    }

    public static boolean isDefaultFocusHighlightEnabled() {
        return sUseDefaultFocusHighlight;
    }

    View dispatchUnhandledKeyEvent(KeyEvent keyEvent) {
        if (onUnhandledKeyEvent(keyEvent)) {
            return this;
        }
        return null;
    }

    boolean onUnhandledKeyEvent(KeyEvent keyEvent) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mUnhandledKeyListeners == null) {
            return false;
        }
        for (int size = this.mListenerInfo.mUnhandledKeyListeners.size() - 1; size >= 0; size--) {
            if (((OnUnhandledKeyEventListener) this.mListenerInfo.mUnhandledKeyListeners.get(size)).onUnhandledKeyEvent(this, keyEvent)) {
                return true;
            }
        }
        return false;
    }

    boolean hasUnhandledKeyListener() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        return (listenerInfo == null || listenerInfo.mUnhandledKeyListeners == null || this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) ? false : true;
    }

    public void addOnUnhandledKeyEventListener(OnUnhandledKeyEventListener onUnhandledKeyEventListener) {
        ArrayList arrayList = getListenerInfo().mUnhandledKeyListeners;
        if (arrayList == null) {
            arrayList = new ArrayList();
            getListenerInfo().mUnhandledKeyListeners = arrayList;
        }
        arrayList.add(onUnhandledKeyEventListener);
        if (arrayList.size() == 1) {
            ViewParent viewParent = this.mParent;
            if (viewParent instanceof ViewGroup) {
                ((ViewGroup) viewParent).incrementChildUnhandledKeyListeners();
            }
        }
    }

    public void removeOnUnhandledKeyEventListener(OnUnhandledKeyEventListener onUnhandledKeyEventListener) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mUnhandledKeyListeners == null || this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
            return;
        }
        this.mListenerInfo.mUnhandledKeyListeners.remove(onUnhandledKeyEventListener);
        if (this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
            this.mListenerInfo.mUnhandledKeyListeners = null;
            ViewParent viewParent = this.mParent;
            if (viewParent instanceof ViewGroup) {
                ((ViewGroup) viewParent).decrementChildUnhandledKeyListeners();
            }
        }
    }

    protected void setDetached(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 8192;
        } else {
            this.mPrivateFlags4 &= -8193;
        }
    }

    public void setIsCredential(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 131072;
        } else {
            this.mPrivateFlags4 &= -131073;
        }
    }

    public boolean isCredential() {
        return (this.mPrivateFlags4 & 131072) == 131072;
    }

    private boolean isSPenHoveringSettingsEnabled() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0, -3) == 1;
    }

    public void setAutoHandwritingEnabled(boolean z) {
        if (z) {
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
        return ((InputMethodManager) getContext().getSystemService(InputMethodManager.class)).isStylusHandwritingAvailable();
    }

    private void setTraversalTracingEnabled(boolean z) {
        if (z) {
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

    private void setRelayoutTracingEnabled(boolean z) {
        if (z) {
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

    public ViewTranslationCallback getViewTranslationCallback() {
        return this.mViewTranslationCallback;
    }

    public void setViewTranslationCallback(ViewTranslationCallback viewTranslationCallback) {
        this.mViewTranslationCallback = viewTranslationCallback;
    }

    public void clearViewTranslationCallback() {
        this.mViewTranslationCallback = null;
    }

    public ViewTranslationResponse getViewTranslationResponse() {
        return this.mViewTranslationResponse;
    }

    public void onViewTranslationResponse(ViewTranslationResponse viewTranslationResponse) {
        this.mViewTranslationResponse = viewTranslationResponse;
    }

    public void clearViewTranslationResponse() {
        this.mViewTranslationResponse = null;
    }

    public void dispatchCreateViewTranslationRequest(Map<AutofillId, long[]> map, int[] iArr, TranslationCapability translationCapability, final List<ViewTranslationRequest> list) {
        AutofillId autofillId = getAutofillId();
        if (map.containsKey(autofillId)) {
            if (map.get(autofillId) == null) {
                onCreateViewTranslationRequest(iArr, new ViewTranslationRequestConsumer(list));
            } else {
                onCreateVirtualViewTranslationRequests(map.get(autofillId), iArr, new Consumer() { // from class: android.view.View$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        list.add((ViewTranslationRequest) obj);
                    }
                });
            }
        }
    }

    private class ViewTranslationRequestConsumer implements Consumer<ViewTranslationRequest> {
        private boolean mCalled;
        private final List<ViewTranslationRequest> mRequests;

        ViewTranslationRequestConsumer(List<ViewTranslationRequest> list) {
            this.mRequests = list;
        }

        @Override // java.util.function.Consumer
        public void accept(ViewTranslationRequest viewTranslationRequest) {
            if (this.mCalled) {
                throw new IllegalStateException("The translation Consumer is not reusable.");
            }
            this.mCalled = true;
            if (viewTranslationRequest == null || viewTranslationRequest.getKeys().size() <= 0) {
                return;
            }
            this.mRequests.add(viewTranslationRequest);
            if (Log.isLoggable(View.CONTENT_CAPTURE_LOG_TAG, 2)) {
                Log.v(View.CONTENT_CAPTURE_LOG_TAG, "Calling setHasTransientState(true) for " + View.this.getAutofillId());
            }
            View.this.setHasTransientState(true);
            View.this.setHasTranslationTransientState(true);
        }
    }

    public void generateDisplayHash(String str, Rect rect, final Executor executor, final DisplayHashResultCallback displayHashResultCallback) {
        IWindowSession windowSession = getWindowSession();
        if (windowSession == null) {
            displayHashResultCallback.onDisplayHashError(-3);
            return;
        }
        IWindow window = getWindow();
        if (window == null) {
            displayHashResultCallback.onDisplayHashError(-3);
            return;
        }
        Rect rect2 = new Rect();
        getGlobalVisibleRect(rect2);
        if (rect != null && rect.isEmpty()) {
            displayHashResultCallback.onDisplayHashError(-2);
            return;
        }
        if (rect != null) {
            rect.offset(rect2.left, rect2.top);
            rect2.intersectUnchecked(rect);
        }
        if (rect2.isEmpty()) {
            displayHashResultCallback.onDisplayHashError(-4);
            return;
        }
        try {
            windowSession.generateDisplayHash(window, rect2, str, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.view.View$$ExternalSyntheticLambda7
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    executor.execute(new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            View.lambda$generateDisplayHash$6(Bundle.this, r2);
                        }
                    });
                }
            }));
        } catch (RemoteException unused) {
            Log.e(VIEW_LOG_TAG, "Failed to call generateDisplayHash");
            displayHashResultCallback.onDisplayHashError(-1);
        }
    }

    static /* synthetic */ void lambda$generateDisplayHash$6(Bundle bundle, DisplayHashResultCallback displayHashResultCallback) {
        DisplayHash displayHash = (DisplayHash) bundle.getParcelable(DisplayHashResultCallback.EXTRA_DISPLAY_HASH, DisplayHash.class);
        int i = bundle.getInt(DisplayHashResultCallback.EXTRA_DISPLAY_HASH_ERROR_CODE, -1);
        if (displayHash != null) {
            displayHashResultCallback.onDisplayHashResult(displayHash);
        } else {
            displayHashResultCallback.onDisplayHashError(i);
        }
    }

    public AttachedSurfaceControl getRootSurfaceControl() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.getRootSurfaceControl();
        }
        return null;
    }

    protected int calculateFrameRateCategory() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        Object obj = this.mParent;
        boolean z = viewRootImpl.mWindowAttributes.type == 2011;
        if (!((this.mPrivateFlags4 & 402653184) != 402653184 && this.mLastFrameLeft == this.mLeft && this.mLastFrameTop == this.mTop) && viewRootImpl.shouldCheckFrameRateCategory() && (obj instanceof View) && ((View) obj).getFrameContentVelocity() <= 0.0f && !z && viewRootImpl.getFrameRateCompatibility() != 2) {
            return CoreRune.FW_ARR_SUPPORT_DIRTY_HINT ? 134217732 : 134217733;
        }
        int intermittentUpdateState = viewRootImpl.intermittentUpdateState();
        if (intermittentUpdateState == 0) {
            return 33554432 | (sToolkitFrameRateBySizeReadOnlyFlagValue ? Math.min(this.mSizeBasedFrameRateCategoryAndReason & 65535, 3) : 3);
        }
        if (intermittentUpdateState == 1) {
            return this.mSizeBasedFrameRateCategoryAndReason;
        }
        return this.mLastFrameRateCategory;
    }

    protected void votePreferredFrameRate() {
        int i;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        float f = this.mFrameContentVelocity;
        float f2 = this.mPreferredFrameRate;
        int i2 = 1;
        if (viewRootImpl.shouldCheckFrameRate(f2 > 0.0f) && (f2 > 0.0f || (this.mAttachInfo.mViewVelocityApi && f > 0.0f))) {
            float convertVelocityToFrameRate = (!this.mAttachInfo.mViewVelocityApi || f <= 0.0f) ? 0.0f : convertVelocityToFrameRate(f);
            if (f2 >= convertVelocityToFrameRate) {
                convertVelocityToFrameRate = f2;
            } else {
                i2 = 2;
            }
            viewRootImpl.votePreferredFrameRate(convertVelocityToFrameRate, i2);
            if (Trace.isTagEnabled(8L)) {
                Trace.instant(8L, getClass().getSimpleName() + " - votePreferredFrameRate: " + convertVelocityToFrameRate);
            }
        }
        if (viewRootImpl.shouldCheckFrameRateCategory()) {
            if (sToolkitMetricsForFrameRateDecisionFlagValue) {
                viewRootImpl.recordViewPercentage(((this.mRight - this.mLeft) * (this.mBottom - this.mTop)) / this.mAttachInfo.mDisplayPixelCount);
            }
            if (Float.isNaN(f2)) {
                i = calculateFrameRateCategory();
            } else if (f2 < 0.0f) {
                int i3 = (int) f2;
                if (i3 == -4) {
                    i = 67108870;
                } else if (i3 == -3) {
                    i = 67108867;
                } else if (i3 == -2) {
                    i = Enums.AUDIO_FORMAT_AAC_LC;
                } else if (i3 != -1) {
                    i = (sToolkitFrameRateDefaultNormalReadOnlyFlagValue ? 3 : 6) | 83886080;
                } else {
                    i = 67108865;
                }
                if (Trace.isTagEnabled(8L)) {
                    Trace.instant(8L, getClass().getSimpleName() + " - votePreferredFrameRate: " + ViewRootImpl.categoryToString(i & 65535));
                }
            } else {
                i = 67108865;
            }
            viewRootImpl.votePreferredFrameRateCategory(i & 65535, (-65536) & i, this);
            this.mLastFrameRateCategory = i;
        }
        this.mLastFrameLeft = this.mLeft;
        this.mLastFrameTop = this.mTop;
    }

    private float convertVelocityToFrameRate(float f) {
        float f2 = f / this.mAttachInfo.mDensity;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (!CoreRune.FW_DVRR_TOOLKIT_POLICY) {
            return f2 > 300.0f ? MAX_FRAME_RATE : f2 > 125.0f ? 80.0f : 60.0f;
        }
        if (!CoreRune.FW_ARR_FLING_FLEXIBLE_FRAME_RATE || viewRootImpl == null || !viewRootImpl.isFlingFrameRateChange()) {
            return MAX_FRAME_RATE;
        }
        if (f2 > this.VELOCITY_THRESHOLD_T1) {
            return this.VELOCITY_FRAMERATE1;
        }
        if (f2 > this.VELOCITY_THRESHOLD_T2) {
            return this.VELOCITY_FRAMERATE2;
        }
        return this.VELOCITY_FRAMERATE3;
    }

    public void setFrameContentVelocity(float f) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null || !attachInfo.mViewVelocityApi) {
            return;
        }
        float abs = Math.abs(f);
        this.mFrameContentVelocity = abs;
        if (sToolkitMetricsForFrameRateDecisionFlagValue) {
            Trace.setCounter("Set frame velocity", (long) abs);
        }
    }

    public float getFrameContentVelocity() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null || !attachInfo.mViewVelocityApi) {
            return 0.0f;
        }
        return Math.max(this.mFrameContentVelocity, 0.0f);
    }

    public void setRequestedFrameRate(float f) {
        if (sToolkitViewGroupFrameRateApiFlagValue && getForcedOverrideFrameRateFlag()) {
            return;
        }
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            Log.i(VIEW_LOG_TAG, "setRequestedFrameRate frameRate=" + f + ", this=" + this + ", caller=" + Debug.getCallers(5));
            this.mPreferredFrameRate = f;
        }
        if (sToolkitViewGroupFrameRateApiFlagValue) {
            setSelfRequestedFrameRateFlag(!Float.isNaN(this.mPreferredFrameRate));
        }
    }

    public float getRequestedFrameRate() {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            return this.mPreferredFrameRate;
        }
        return 0.0f;
    }

    void overrideFrameRate(float f, boolean z) {
        setForcedOverrideFrameRateFlag(z);
        if (z || !getSelfRequestedFrameRateFlag()) {
            this.mPreferredFrameRate = f;
        }
    }

    void setForcedOverrideFrameRateFlag(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 1073741824;
        } else {
            this.mPrivateFlags4 &= -1073741825;
        }
    }

    boolean getForcedOverrideFrameRateFlag() {
        return (this.mPrivateFlags4 & 1073741824) != 0;
    }

    void setSelfRequestedFrameRateFlag(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= Integer.MIN_VALUE;
        } else {
            this.mPrivateFlags4 &= Integer.MAX_VALUE;
        }
    }

    boolean getSelfRequestedFrameRateFlag() {
        return (this.mPrivateFlags4 & Integer.MIN_VALUE) != 0;
    }

    public void reportAppJankStats(AppJankStats appJankStats) {
        View rootView = getRootView();
        if (rootView == this) {
            return;
        }
        rootView.reportAppJankStats(appJankStats);
    }

    public JankTracker getJankTracker() {
        View rootView = getRootView();
        if (rootView == this) {
            return null;
        }
        return rootView.getJankTracker();
    }

    public boolean dispatchKeyEventTextMultiSelection(KeyEvent keyEvent) {
        if (ViewRune.WIDGET_PEN_SUPPORTED) {
            return onKeyTextMultiSelection(keyEvent.getKeyCode(), keyEvent);
        }
        return false;
    }

    public boolean hasCallbacks(Runnable runnable) {
        AttachInfo attachInfo;
        if (runnable == null || (attachInfo = this.mAttachInfo) == null) {
            return false;
        }
        return attachInfo.mHandler.hasCallbacks(runnable);
    }

    @RemotableViewMethod
    @Deprecated(forRemoval = true, since = "13.0")
    public void semSetBlurEnabled(boolean z) {
        if (TEST_BLUR_DISABLED != 0) {
            return;
        }
        if (z) {
            semSetBlurMode(0);
        } else {
            clearBlurMode();
        }
    }

    public boolean semGetBlurEnabled() {
        return this.mBlurMode == 0;
    }

    @RemotableViewMethod
    public void semSetBlurMode(int i) {
        if (TEST_BLUR_DISABLED != 0) {
            return;
        }
        if (i < -1 || i > 2) {
            Log.e(VIEW_LOG_TAG, "blurMode " + i + " is not valid!");
            return;
        }
        if (this.mBlurMode == i) {
            return;
        }
        clearBlurMode();
        this.mBlurMode = i;
        invalidateBlur();
    }

    @RemotableViewMethod
    public void semSetBlurRadius(int i) {
        this.mBlurRadius = i;
        invalidateBlur();
    }

    @RemotableViewMethod
    public void semSetBackgroundBlurCornerRadius(float f) {
        if (f >= 0.0f) {
            this.mBackgroundBlurCornerRadiusTL = f;
            this.mBackgroundBlurCornerRadiusTR = f;
            this.mBackgroundBlurCornerRadiusBL = f;
            this.mBackgroundBlurCornerRadiusBR = f;
            invalidateBlur();
        }
    }

    @RemotableViewMethod
    public void semSetBackgroundBlurColor(int i) {
        this.mBackgroundBlurColor = i;
        invalidateBlur();
    }

    @RemotableViewMethod
    public void semSetBlurInfo(SemBlurInfo semBlurInfo) {
        Log.d(VIEW_LOG_TAG, "semSetBlurInfo, blurInfo : " + semBlurInfo + ", " + this);
        if (TEST_BLUR_DISABLED != 0) {
            return;
        }
        if (semBlurInfo == null) {
            clearBlurMode();
            this.mBlurInfo = semBlurInfo;
            return;
        }
        if (this.mBlurMode != semBlurInfo.getBlurMode()) {
            clearBlurMode();
        }
        this.mBlurInfo = semBlurInfo;
        this.mBlurMode = semBlurInfo.getBlurMode();
        this.mBlurColorCurve = this.mBlurInfo.getColorCurve();
        int i = this.mBlurMode;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                this.mBlurRadius = this.mBlurInfo.getBlurRadius();
                invalidateCanvasBlur();
                postInvalidate();
                return;
            }
            this.mCapturedBitmap = this.mBlurInfo.getCapturedBitmap();
            int blurRadius = this.mBlurInfo.getBlurRadius();
            this.mBlurRadius = blurRadius;
            invalidateCapturedBlur(this.mLastBlurRadius != blurRadius);
            this.mLastBlurRadius = this.mBlurRadius;
            return;
        }
        if (CoreRune.FW_WINDOW_BLUR_SUPPORTED) {
            this.mBlurRadius = this.mBlurInfo.getBlurRadius();
            this.mBackgroundBlurColor = this.mBlurInfo.getBackgroundBlurColor();
            float[] fArr = new float[4];
            this.mBlurInfo.getBackgroundBlurCornerRadius(fArr);
            this.mBackgroundBlurCornerRadiusTL = fArr[0];
            this.mBackgroundBlurCornerRadiusTR = fArr[1];
            this.mBackgroundBlurCornerRadiusBL = fArr[2];
            this.mBackgroundBlurCornerRadiusBR = fArr[3];
            int[] iArr = new int[4];
            this.mBlurInfo.getBackgroundClipRect(iArr);
            this.mClipRectLeft = iArr[0];
            this.mClipRectTop = iArr[1];
            this.mClipRectRight = iArr[2];
            this.mClipRectBottom = iArr[3];
            invalidateBlurBackground();
            return;
        }
        this.mBlurMode = -1;
        this.mBlurInfo = null;
        Log.e(VIEW_LOG_TAG, "This model doesn't support window blur!");
    }

    private void hidden_semSetBlurInfo(SemBlurInfo semBlurInfo) {
        semSetBlurInfo(semBlurInfo);
    }

    private void invalidateBlur() {
        SemBlurInfo semBlurInfo = this.mBlurInfo;
        if (semBlurInfo != null) {
            semSetBlurInfo(semBlurInfo);
            return;
        }
        int i = this.mBlurMode;
        if (i == 0) {
            if (CoreRune.FW_WINDOW_BLUR_SUPPORTED) {
                invalidateBlurBackground();
                return;
            } else {
                this.mBlurMode = -1;
                Log.e(VIEW_LOG_TAG, "This model doesn't support window blur!");
                return;
            }
        }
        if (i == 1) {
            invalidateCapturedBlur(false);
        } else {
            if (i != 2) {
                return;
            }
            invalidateCanvasBlur();
        }
    }

    private void clearBlurMode() {
        int i = this.mBlurMode;
        if (i == 0) {
            clearBlurBackground();
        } else if (i == 1) {
            clearCapturedBlur();
        } else if (i == 2) {
            clearCanvasBlur();
        }
        this.mBlurMode = -1;
    }

    private void invalidateBlurBackground() {
        BackgroundBlurDrawable createBackgroundBlurDrawable;
        if (this.mBlurMode == 0) {
            Drawable drawable = this.mBackground;
            if (drawable instanceof BackgroundBlurDrawable) {
                createBackgroundBlurDrawable = (BackgroundBlurDrawable) drawable;
            } else {
                AttachInfo attachInfo = this.mAttachInfo;
                if (attachInfo == null) {
                    return;
                }
                createBackgroundBlurDrawable = attachInfo.mViewRootImpl.createBackgroundBlurDrawable();
                setBackground(createBackgroundBlurDrawable);
            }
            createBackgroundBlurDrawable.setBlurRadius(this.mBlurRadius);
            createBackgroundBlurDrawable.setCornerRadius(this.mBackgroundBlurCornerRadiusTL, this.mBackgroundBlurCornerRadiusTR, this.mBackgroundBlurCornerRadiusBL, this.mBackgroundBlurCornerRadiusBR);
            createBackgroundBlurDrawable.setClipRect(this.mClipRectLeft, this.mClipRectTop, this.mClipRectRight, this.mClipRectBottom);
            createBackgroundBlurDrawable.setColor(this.mBackgroundBlurColor);
            SemBlurInfo.ColorCurve colorCurve = this.mBlurColorCurve;
            if (colorCurve != null) {
                createBackgroundBlurDrawable.setBlurColorCurve(colorCurve);
            }
            invalidateDrawable(createBackgroundBlurDrawable);
            return;
        }
        clearBlurBackground();
    }

    private void clearBlurBackground() {
        if (this.mBackground instanceof BackgroundBlurDrawable) {
            setBackground(null);
        }
    }

    private void invalidateCapturedBlur(boolean z) {
        if (this.mBlurMode == 1) {
            if (CoreRune.GRAPHICS_RENDERER_IMAGEFILTER) {
                if (this.mImageFilter == null) {
                    this.mImageFilter = new RenderEffectImageFilter();
                }
                this.mImageFilter.setBlurRadius(this.mBlurRadius);
                SemBlurInfo.ColorCurve colorCurve = this.mBlurColorCurve;
                if (colorCurve != null) {
                    this.mImageFilter.setProportionalSaturation(colorCurve.mSaturation);
                    this.mImageFilter.setCurveLevel(this.mBlurColorCurve.mCurveBias);
                    this.mImageFilter.setCurveMinX(this.mBlurColorCurve.mMinX);
                    this.mImageFilter.setCurveMaxX(this.mBlurColorCurve.mMaxX);
                    this.mImageFilter.setCurveMinY(this.mBlurColorCurve.mMinY);
                    this.mImageFilter.setCurveMaxY(this.mBlurColorCurve.mMaxY);
                }
            } else {
                if (this.mBlurFilter == null) {
                    this.mBlurFilter = new SemGfxImageFilter();
                }
                this.mBlurFilter.setBlurRadius(this.mBlurRadius);
                SemBlurInfo.ColorCurve colorCurve2 = this.mBlurColorCurve;
                if (colorCurve2 != null) {
                    this.mBlurFilter.setProportionalSaturation(colorCurve2.mSaturation);
                    this.mBlurFilter.setCurveLevel(this.mBlurColorCurve.mCurveBias);
                    this.mBlurFilter.setCurveMinX(this.mBlurColorCurve.mMinX);
                    this.mBlurFilter.setCurveMaxX(this.mBlurColorCurve.mMaxX);
                    this.mBlurFilter.setCurveMinY(this.mBlurColorCurve.mMinY);
                    this.mBlurFilter.setCurveMaxY(this.mBlurColorCurve.mMaxY);
                }
            }
            if (z) {
                invalidate();
                return;
            }
            return;
        }
        clearCapturedBlur();
    }

    private void clearCapturedBlur() {
        Bitmap bitmap = this.mBlurredBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mBlurredBitmap = null;
        }
        if (CoreRune.GRAPHICS_RENDERER_IMAGEFILTER) {
            RenderEffectImageFilter renderEffectImageFilter = this.mImageFilter;
            if (renderEffectImageFilter != null) {
                renderEffectImageFilter.clear();
                this.mImageFilter = null;
            }
        } else {
            SemGfxImageFilter semGfxImageFilter = this.mBlurFilter;
            if (semGfxImageFilter != null) {
                semGfxImageFilter.onDetachedFromView();
                this.mBlurFilter = null;
            }
        }
        this.mBlurBitmapBounds = null;
        this.mBlurViewBounds = null;
        this.mCapturedBitmap = null;
    }

    private void invalidateCanvasBlur() {
        if (this.mBlurMode == 2 && this.mBlurRadius > 0) {
            if (this.mCanvasBlurRenderNode == null) {
                this.mCanvasBlurRenderNode = new RenderNode("canvas blur");
            }
            if (CoreRune.GRAPHICS_RENDERER_IMAGEFILTER) {
                if (this.mImageFilter == null) {
                    this.mImageFilter = new RenderEffectImageFilter();
                }
                this.mImageFilter.setBlurRadius(this.mBlurRadius);
                SemBlurInfo.ColorCurve colorCurve = this.mBlurColorCurve;
                if (colorCurve != null) {
                    this.mImageFilter.setProportionalSaturation(colorCurve.mSaturation);
                    this.mImageFilter.setCurveLevel(this.mBlurColorCurve.mCurveBias);
                    this.mImageFilter.setCurveMinX(this.mBlurColorCurve.mMinX);
                    this.mImageFilter.setCurveMaxX(this.mBlurColorCurve.mMaxX);
                    this.mImageFilter.setCurveMinY(this.mBlurColorCurve.mMinY);
                    this.mImageFilter.setCurveMaxY(this.mBlurColorCurve.mMaxY);
                    return;
                }
                return;
            }
            if (this.mBlurFilter == null) {
                SemGfxImageFilter semGfxImageFilter = new SemGfxImageFilter();
                this.mBlurFilter = semGfxImageFilter;
                semGfxImageFilter.onAttachToView(this);
            }
            this.mBlurFilter.setBlurRadius(this.mBlurRadius);
            SemBlurInfo.ColorCurve colorCurve2 = this.mBlurColorCurve;
            if (colorCurve2 != null) {
                this.mBlurFilter.setProportionalSaturation(colorCurve2.mSaturation);
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
        Bitmap bitmap = this.mCanvasBlurBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mCanvasBlurBitmap = null;
        }
        RenderNode renderNode = this.mCanvasBlurRenderNode;
        if (renderNode != null) {
            renderNode.discardDisplayList();
            this.mCanvasBlurRenderNode = null;
        }
        if (CoreRune.GRAPHICS_RENDERER_IMAGEFILTER) {
            RenderEffectImageFilter renderEffectImageFilter = this.mImageFilter;
            if (renderEffectImageFilter != null) {
                renderEffectImageFilter.clear();
                this.mImageFilter = null;
            }
        } else {
            SemGfxImageFilter semGfxImageFilter = this.mBlurFilter;
            if (semGfxImageFilter != null) {
                semGfxImageFilter.onDetachedFromView();
                this.mBlurFilter = null;
            }
        }
        this.mBlurBitmapBounds = null;
        this.mBlurViewBounds = null;
    }

    private void drawBlurEffect(Canvas canvas) {
        Bitmap bitmap;
        int i = this.mBlurMode;
        if (i == -1 || i == 0) {
            return;
        }
        if (this.mBlurBitmapBounds == null) {
            this.mBlurBitmapBounds = new Rect();
        }
        if (this.mBlurViewBounds == null) {
            this.mBlurViewBounds = new Rect();
        }
        int i2 = this.mBlurMode;
        if (i2 == 2) {
            if (this.mAttachInfo != null) {
                int width = getWidth();
                int height = getHeight();
                if (width <= 0 || height <= 0 || this.mBlurRadius <= 0 || this.mCanvasBlurRenderNode == null) {
                    return;
                }
                Picture picture = new Picture();
                Canvas beginRecording = picture.beginRecording(width, height);
                getLocationInWindow(new int[2]);
                beginRecording.translate(-r1[0], -r1[1]);
                this.mCapturingCanvas = true;
                this.mAttachInfo.mRootView.draw(beginRecording);
                this.mCapturingCanvas = false;
                picture.endRecording();
                this.mCanvasBlurRenderNode.discardDisplayList();
                this.mCanvasBlurRenderNode.setPosition(0, 0, width, height);
                RecordingCanvas beginRecording2 = this.mCanvasBlurRenderNode.beginRecording(width, height);
                beginRecording2.drawPicture(picture);
                if (CoreRune.GRAPHICS_RENDERER_IMAGEFILTER) {
                    this.mCanvasBlurRenderNode.endRecording();
                    RenderEffectImageFilter renderEffectImageFilter = this.mImageFilter;
                    if (renderEffectImageFilter != null) {
                        renderEffectImageFilter.setSize(width, height);
                        this.mCanvasBlurRenderNode.setRenderEffect(this.mImageFilter.build());
                        canvas.drawRenderNode(this.mCanvasBlurRenderNode);
                        return;
                    }
                    return;
                }
                this.mBlurFilter.draw(beginRecording2);
                this.mCanvasBlurRenderNode.endRecording();
                canvas.drawRenderNode(this.mCanvasBlurRenderNode);
                return;
            }
            return;
        }
        if (i2 == 1) {
            RenderNode renderNode = null;
            if (CoreRune.GRAPHICS_RENDERER_IMAGEFILTER) {
                RenderEffectImageFilter renderEffectImageFilter2 = this.mImageFilter;
                if (renderEffectImageFilter2 != null && (bitmap = this.mCapturedBitmap) != null) {
                    renderEffectImageFilter2.setSize(bitmap.getWidth(), this.mCapturedBitmap.getHeight());
                    renderNode = this.mImageFilter.getRenderNode(this.mCapturedBitmap);
                }
                if (this.mBlurRadius <= 0 || renderNode == null) {
                    return;
                }
                this.mBlurViewBounds.right = getWidth();
                this.mBlurViewBounds.bottom = getHeight();
                this.mBlurBitmapBounds.right = this.mCapturedBitmap.getWidth();
                this.mBlurBitmapBounds.bottom = this.mCapturedBitmap.getHeight();
                if (this.mBlurViewBounds.width() > 0 && this.mBlurViewBounds.height() > 0 && this.mBlurBitmapBounds.width() > 0 && this.mBlurBitmapBounds.height() > 0) {
                    canvas.save();
                    canvas.translate(this.mBlurViewBounds.left, this.mBlurViewBounds.top);
                    canvas.scale(this.mBlurViewBounds.width() / this.mBlurBitmapBounds.width(), this.mBlurViewBounds.height() / this.mBlurBitmapBounds.height());
                    canvas.drawRenderNode(renderNode);
                    canvas.restore();
                    return;
                }
                Log.w(VIEW_LOG_TAG, "blur resolution Error [" + this.mBlurViewBounds.width() + "," + this.mBlurViewBounds.height() + "," + this.mBlurBitmapBounds.width() + "," + this.mBlurBitmapBounds.width() + ",]");
                return;
            }
            if (this.mBlurFilter != null) {
                Bitmap bitmap2 = this.mBlurredBitmap;
                if (bitmap2 != null) {
                    bitmap2.recycle();
                    this.mBlurredBitmap = null;
                }
                Bitmap bitmap3 = this.mCapturedBitmap;
                if (bitmap3 != null) {
                    this.mBlurredBitmap = this.mBlurFilter.applyToBitmap(bitmap3);
                }
            }
            Bitmap bitmap4 = this.mBlurredBitmap;
            if (bitmap4 == null || this.mBlurRadius <= 0) {
                return;
            }
            this.mBlurBitmapBounds.right = bitmap4.getWidth();
            this.mBlurBitmapBounds.bottom = this.mBlurredBitmap.getHeight();
            this.mBlurViewBounds.right = getWidth();
            this.mBlurViewBounds.bottom = getHeight();
            canvas.drawBitmap(this.mBlurredBitmap, this.mBlurBitmapBounds, this.mBlurViewBounds, (Paint) null);
        }
    }

    public boolean isBlurDebug() {
        return getClass().getName().contains(DEBUG_BLUR_TARGET_NAME);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void semSetGfxImageFilter(SemGfxImageFilter semGfxImageFilter) {
        SemGfxImageFilter semGfxImageFilter2 = this.mGfxImageFilter;
        if (semGfxImageFilter2 != null) {
            semGfxImageFilter2.onDetachedFromView();
        }
        this.mGfxImageFilter = semGfxImageFilter;
        if (semGfxImageFilter != null) {
            semGfxImageFilter.onAttachToView(this);
        }
        invalidate(true);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public SemGfxImageFilter semGetGfxImageFilter() {
        return this.mGfxImageFilter;
    }

    public void semSetScrollBarTopPadding(int i) {
        this.mAppWidgetScrollBarTopPadding = i;
    }

    public void semSetScrollBarBottomPadding(int i) {
        this.mAppWidgetScrollBarBottomPadding = i;
    }

    public SemSmartClipDataExtractionListener semGetSmartClipDataExtractionListener() {
        return this.mSmartClipDataExtractionListener;
    }

    public boolean semSetSmartClipDataExtractionListener(SemSmartClipDataExtractionListener semSmartClipDataExtractionListener) {
        this.mSmartClipDataExtractionListener = semSmartClipDataExtractionListener;
        return true;
    }

    public SemSmartClipMetaTagArray semGetSmartClipTags() {
        return this.mSmartClipDataTag;
    }

    public boolean semSetSmartClipTags(SemSmartClipMetaTagArray semSmartClipMetaTagArray) {
        this.mSmartClipDataTag = semSmartClipMetaTagArray;
        return true;
    }

    public int semExtractSmartClipData(SemSmartClipCroppedArea semSmartClipCroppedArea, SemSmartClipDataElement semSmartClipDataElement) {
        SmartClipDataElementImpl smartClipDataElementImpl = (SmartClipDataElementImpl) semSmartClipDataElement;
        if (smartClipDataElementImpl == null) {
            return 0;
        }
        SemSmartClipDataRepository dataRepository = smartClipDataElementImpl.getDataRepository();
        SmartClipDataCropperImpl smartClipDataCropperImpl = dataRepository != null ? (SmartClipDataCropperImpl) dataRepository.getSmartClipDataCropper() : null;
        if (smartClipDataCropperImpl != null) {
            return smartClipDataCropperImpl.extractDefaultSmartClipData(this, semSmartClipCroppedArea, smartClipDataElementImpl);
        }
        return 0;
    }

    void setBackgroundBounds(int i) {
        Drawable drawable;
        if (!this.mBackgroundSizeChanged || (drawable = this.mBackground) == null) {
            return;
        }
        drawable.setBounds(0, 0, i, i);
        this.mBackgroundSizeChanged = false;
        rebuildOutline();
    }

    private void postRequestSendStickyDragStartedEvent() {
        post(new Runnable() { // from class: android.view.View$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                View.this.lambda$postRequestSendStickyDragStartedEvent$8();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postRequestSendStickyDragStartedEvent$8() {
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.requestSendStickyDragStartedEvent(this);
        }
    }

    public View semDispatchFindView(PointF pointF, boolean z, ISemTouchApi iSemTouchApi) {
        Context context = this.mContext;
        if (iSemTouchApi.getViewContent(context, context.getPackageName(), this, pointF, null)) {
            return this;
        }
        return null;
    }
}
