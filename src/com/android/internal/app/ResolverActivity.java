package com.android.internal.app;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.KeyguardManager;
import android.app.VoiceInteractor;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.Slog;
import android.util.TypedValue;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.IWindowManager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.PathInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.NoCrossProfileEmptyStateProvider;
import com.android.internal.app.ResolverListAdapter;
import com.android.internal.app.chooser.ChooserTargetInfo;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.SemSelectTaskListAdapter;
import com.android.internal.app.chooser.TargetInfo;
import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.service.chooser.Flags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LinearLayoutManager;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.ResolverDrawerLayout;
import com.android.internal.widget.ViewPager;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.share.SemShareCommon;
import com.samsung.android.share.SemShareConstants;
import com.samsung.android.share.SemShareLogging;
import com.samsung.android.util.SemA11yEvent;
import com.samsung.android.widget.SemTipPopup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class ResolverActivity extends Activity implements ResolverListAdapter.ResolverListCommunicator {
    public static boolean ENABLE_TABBED_VIEW = true;
    static final String EXTRA_CALLING_USER = "com.android.internal.app.ResolverActivity.EXTRA_CALLING_USER";
    private static final String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
    public static final String EXTRA_IS_AUDIO_CAPTURE_DEVICE = "is_audio_capture_device";
    public static final String EXTRA_PRIVATE_RETAIN_IN_ON_STOP = "com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP";
    protected static final String EXTRA_RESTRICT_TO_SINGLE_USER = "com.android.internal.app.ResolverActivity.EXTRA_RESTRICT_TO_SINGLE_USER";
    protected static final String EXTRA_SELECTED_PROFILE = "com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE";
    private static final String EXTRA_SHOW_FRAGMENT_ARGS = ":settings:show_fragment_args";
    private static final boolean IS_OVERLAY_THEMES_ENABLED = true;
    private static final String LAST_SHOWN_TAB_KEY = "last_shown_tab_key";
    protected static final String METRICS_CATEGORY_CHOOSER = "intent_chooser";
    protected static final String METRICS_CATEGORY_RESOLVER = "intent_resolver";
    private static final String OPEN_LINKS_COMPONENT_KEY = "app_link_state";
    protected static final int PROFILE_PERSONAL = 0;
    protected static final int PROFILE_WORK = 1;
    public static final String SAVE_STATE_RECREATE_FROM_ALT_AI = "recreate_from_alt_ai";
    private static final String SEM_INTENT_ACTION_AI_ASSIST = "com.samsung.android.intent.action.AI_ASSIST";
    private static final String SEM_INTENT_EXTRA_AI_ALT_PRESS = "is_alt_press";
    private static final String SEM_INTENT_EXTRA_FROM_KEYBOARD_SHORTCUT = "from_keyboard_shortcut";
    private static final float SEM_RESOLVER_REDUCE_TRANSPARENCY_DIM_AMOUNT_DARK = 0.65f;
    private static final float SEM_RESOLVER_REDUCE_TRANSPARENCY_DIM_AMOUNT_LIGHT = 0.35f;
    private static final String TAB_TAG_PERSONAL = "personal";
    private static final String TAB_TAG_WORK = "work";
    private static final String TAG = "ResolverActivity";
    private Button mAlwaysButton;
    private final int mAnimDuration;
    private String mAppIconTheme;
    private UserHandle mCloneProfileUserHandle;
    private Context mContext;
    private int mDefaultTitleResId;
    protected Animator mExitAnimator;
    protected List<Intent> mExtraIntentList;
    private Space mFooterSpacer;
    protected boolean mForceTitleHide;
    private RecyclerView mGalleryRecyclerView;
    private boolean mHasSubclassSpecifiedResolutions;
    private UserHandle mHeaderCreatorUser;
    protected final ArrayList<Intent> mIntents;
    protected boolean mIsAiAssist;
    protected boolean mIsAiFromKeyboardShortcut;
    protected boolean mIsAltAiPressed;
    protected boolean mIsDeskTopMode;
    protected boolean mIsDeviceDefault;
    private final boolean mIsIntentPicker;
    private boolean mIsKeyDownPressed;
    protected boolean mIsNight;
    protected boolean mIsPopOver;
    protected boolean mIsReduceTransparency;
    private int mLastSelected;
    protected final LatencyTracker mLatencyTracker;
    protected int mLaunchedFromUid;
    private UserHandle mLaunchedFromUserHandle;
    private int mLayoutId;
    protected int mMaxColumns;
    protected FrameLayout mMultiParent;
    protected AbstractMultiProfilePagerAdapter mMultiProfilePagerAdapter;
    protected boolean mNeedUpdateAfterPinned;
    private int mOldItemCount;
    private AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener mOnSwitchOnWorkSelectedListener;
    private Button mOnceButton;
    protected int mOrientation;
    private PackageMonitor mPersonalPackageMonitor;
    private UserHandle mPersonalProfileUserHandle;
    private PickTargetOptionRequest mPickOptionRequest;
    protected PackageManager mPm;
    private UserHandle mPrivateProfileUserHandle;
    private String mProfileSwitchMessage;
    protected View mProfileView;
    protected AbstractMultiProfilePagerAdapter.QuietModeManager mQuietModeManager;
    private boolean mRecreatedFromAltAi;
    private String mReferrerPackage;
    private boolean mRegistered;
    protected ResolverDrawerLayout mResolverDrawerLayout;
    private boolean mResolvingHome;
    private boolean mRetainInOnStop;
    private boolean mSafeForwardingMode;
    private boolean mSecondDepth;
    private RecyclerView mSelectTaskRecyclerView;
    private SemSelectTaskListAdapter mSemSelectTaskListAdapter;
    protected SemShareCommon mSemShareCommon;
    protected SemShareLogging mSemShareLogging;
    protected boolean mSupportButtons;
    protected boolean mSupportsAlwaysUseOption;
    protected Insets mSystemWindowInsets;
    private UserHandle mTabOwnerUserHandleForLaunch;
    private SemTipPopup mTipsDescriptionPopup;
    private ImageView mTipsIcon;
    private CharSequence mTitle;
    private IWindowManager mWindowManager;
    private PackageMonitor mWorkPackageMonitor;
    private boolean mWorkProfileHasBeenEnabled;
    private BroadcastReceiver mWorkProfileStateReceiver;
    private UserHandle mWorkProfileUserHandle;

    static final boolean isSpecificUriMatch(int i) {
        int i2 = i & IntentFilter.MATCH_CATEGORY_MASK;
        return i2 >= 3145728 && i2 <= 5242880;
    }

    public void addUseDifferentAppLabelIfNecessary(ResolverListAdapter resolverListAdapter) {
    }

    protected int appliedThemeResId() {
        return R.style.Theme_DeviceDefault_Resolver;
    }

    protected void applyFooterView(int i) {
    }

    public Intent getReplacementIntent(ActivityInfo activityInfo, Intent intent) {
        return intent;
    }

    protected void maybeLogProfileChange() {
    }

    public void onActivityStarted(TargetInfo targetInfo) {
    }

    void onHorizontalSwipeStateChanged(int i) {
    }

    protected void onProfileTabSelected() {
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean semIsOverlayThemesEnabled() {
        return true;
    }

    public boolean shouldGetActivityMetadata() {
        return false;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean useLayoutWithDefault() {
        return false;
    }

    public ResolverActivity() {
        this.mLastSelected = -1;
        this.mResolvingHome = false;
        this.mIntents = new ArrayList<>();
        this.mSystemWindowInsets = null;
        this.mFooterSpacer = null;
        this.mWorkProfileHasBeenEnabled = false;
        this.mAnimDuration = 330;
        this.mOldItemCount = 0;
        this.mExtraIntentList = new ArrayList();
        this.mIsDeviceDefault = true;
        this.mIsDeskTopMode = false;
        this.mForceTitleHide = false;
        this.mNeedUpdateAfterPinned = false;
        this.mIsReduceTransparency = false;
        this.mSecondDepth = false;
        this.mIsPopOver = false;
        this.mIsAiAssist = false;
        this.mIsAltAiPressed = false;
        this.mIsAiFromKeyboardShortcut = false;
        this.mRecreatedFromAltAi = false;
        this.mIsKeyDownPressed = false;
        this.mLatencyTracker = getLatencyTracker();
        this.mIsIntentPicker = getClass().equals(ResolverActivity.class);
    }

    protected ResolverActivity(boolean z) {
        this.mLastSelected = -1;
        this.mResolvingHome = false;
        this.mIntents = new ArrayList<>();
        this.mSystemWindowInsets = null;
        this.mFooterSpacer = null;
        this.mWorkProfileHasBeenEnabled = false;
        this.mAnimDuration = 330;
        this.mOldItemCount = 0;
        this.mExtraIntentList = new ArrayList();
        this.mIsDeviceDefault = true;
        this.mIsDeskTopMode = false;
        this.mForceTitleHide = false;
        this.mNeedUpdateAfterPinned = false;
        this.mIsReduceTransparency = false;
        this.mSecondDepth = false;
        this.mIsPopOver = false;
        this.mIsAiAssist = false;
        this.mIsAltAiPressed = false;
        this.mIsAiFromKeyboardShortcut = false;
        this.mRecreatedFromAltAi = false;
        this.mIsKeyDownPressed = false;
        this.mLatencyTracker = getLatencyTracker();
        this.mIsIntentPicker = z;
    }

    private LatencyTracker getLatencyTracker() {
        return LatencyTracker.getInstance(this);
    }

    public static int getLabelRes(String str) {
        return ActionTitle.forAction(str).labelRes;
    }

    private enum ActionTitle {
        VIEW("android.intent.action.VIEW", R.string.whichViewApplication, R.string.whichViewApplicationNamed, R.string.whichViewApplicationLabel),
        EDIT(Intent.ACTION_EDIT, R.string.whichEditApplication, R.string.whichEditApplicationNamed, R.string.whichEditApplicationLabel),
        SEND(Intent.ACTION_SEND, R.string.whichSendApplication, R.string.whichSendApplicationNamed, R.string.whichSendApplicationLabel),
        SENDTO(Intent.ACTION_SENDTO, R.string.whichSendToApplication, R.string.whichSendToApplicationNamed, R.string.whichSendToApplicationLabel),
        SEND_MULTIPLE(Intent.ACTION_SEND_MULTIPLE, R.string.whichSendApplication, R.string.whichSendApplicationNamed, R.string.whichSendApplicationLabel),
        CAPTURE_IMAGE("android.media.action.IMAGE_CAPTURE", R.string.whichImageCaptureApplication, R.string.whichImageCaptureApplicationNamed, R.string.whichImageCaptureApplicationLabel),
        DEFAULT(null, R.string.whichApplication, R.string.whichApplicationNamed, R.string.whichApplicationLabel),
        HOME(Intent.ACTION_MAIN, R.string.whichHomeApplication, R.string.whichHomeApplicationNamed, R.string.whichHomeApplicationLabel),
        AI_ASSIST(ResolverActivity.SEM_INTENT_ACTION_AI_ASSIST, R.string.resolver_sem_ai_key_title, R.string.resolver_sem_ai_key_title, R.string.resolver_sem_ai_key_title);

        public static final int BROWSABLE_APP_TITLE_RES = 17043680;
        public static final int BROWSABLE_HOST_APP_TITLE_RES = 17043678;
        public static final int BROWSABLE_HOST_TITLE_RES = 17043677;
        public static final int BROWSABLE_TITLE_RES = 17043679;
        public final String action;
        public final int labelRes;
        public final int namedTitleRes;
        public final int titleRes;

        ActionTitle(String str, int i, int i2, int i3) {
            this.action = str;
            this.titleRes = i;
            this.namedTitleRes = i2;
            this.labelRes = i3;
        }

        public static ActionTitle forAction(String str) {
            for (ActionTitle actionTitle : values()) {
                if (actionTitle != HOME && str != null && str.equals(actionTitle.action)) {
                    return actionTitle;
                }
            }
            return DEFAULT;
        }
    }

    protected PackageMonitor createPackageMonitor(final ResolverListAdapter resolverListAdapter) {
        return new PackageMonitor() { // from class: com.android.internal.app.ResolverActivity.1
            @Override // com.android.internal.content.PackageMonitor
            public boolean onPackageChanged(String str, int i, String[] strArr) {
                return true;
            }

            @Override // com.android.internal.content.PackageMonitor
            public void onSomePackagesChanged() {
                resolverListAdapter.handlePackagesChanged();
                ResolverActivity.this.updateProfileViewButton();
            }
        };
    }

    private Intent makeMyIntent() {
        Intent intent = new Intent(getIntent());
        intent.setComponent(null);
        intent.setFlags(intent.getFlags() & (-8388609));
        if ((intent.getFlags() & 4096) != 0) {
            intent.setFlags(intent.getFlags() & (-4097));
        }
        return intent;
    }

    protected void super_onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        this.mContext = this;
        Intent makeMyIntent = makeMyIntent();
        Set<String> categories = makeMyIntent.getCategories();
        if (Intent.ACTION_MAIN.equals(makeMyIntent.getAction()) && categories != null && categories.size() == 1 && categories.contains(Intent.CATEGORY_HOME)) {
            this.mResolvingHome = true;
        }
        setSafeForwardingMode(true);
        onCreate(bundle, makeMyIntent, null, 0, null, null, true);
    }

    protected void onCreate(Bundle bundle, Intent intent, CharSequence charSequence, Intent[] intentArr, List<ResolveInfo> list, boolean z) {
        onCreate(bundle, intent, charSequence, 0, intentArr, list, z);
    }

    protected void onCreate(Bundle bundle, Intent intent, CharSequence charSequence, int i, Intent[] intentArr, List<ResolveInfo> list, boolean z) {
        setTheme(appliedThemeResId());
        Configuration configuration = getResources().getConfiguration();
        this.mIsNight = (configuration.uiMode & 48) == 32;
        super.onCreate(bundle);
        this.mContext = this;
        if (!z) {
            String[] strArr = {"true"};
            String action = intent.getAction();
            if (action != null && (action.endsWith(Intent.ACTION_SEND) || action.endsWith(Intent.ACTION_SEND_MULTIPLE) || action.endsWith(Intent.ACTION_SENDTO) || action.endsWith("UNLIMITED_SHARE"))) {
                boolean enterprisePolicyEnabled = getEnterprisePolicyEnabled(getBaseContext(), "content://com.sec.knox.provider/RestrictionPolicy3", "isShareListAllowed", strArr);
                Log.d(TAG, "action - " + action);
                Log.d(TAG, "onCreate(): isShareListAllowed(" + enterprisePolicyEnabled + NavigationBarInflaterView.KEY_CODE_END);
                if (!enterprisePolicyEnabled) {
                    Log.d(TAG, "onCreate(): ShareList is not allowed");
                    finish();
                    return;
                }
            }
        }
        this.mHasSubclassSpecifiedResolutions = list != null;
        this.mQuietModeManager = createQuietModeManager();
        setProfileSwitchMessage(intent.getContentUserHint());
        this.mOrientation = configuration.orientation;
        this.mMaxColumns = getResources().getInteger(R.integer.config_maxResolverActivityColumns);
        this.mAppIconTheme = Settings.System.getString(getContentResolver(), "current_sec_appicon_theme_package");
        this.mIsDeskTopMode = configuration.semDesktopModeEnabled == 1;
        setRetainInOnStop(intent.getBooleanExtra("com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP", false));
        int launchedFromUid = getLaunchedFromUid();
        this.mLaunchedFromUid = launchedFromUid;
        this.mLaunchedFromUserHandle = UserHandle.getUserHandleForUid(launchedFromUid);
        int i2 = this.mLaunchedFromUid;
        if (i2 < 0 || UserHandle.isIsolated(i2)) {
            finish();
            return;
        }
        this.mPm = getPackageManager();
        this.mReferrerPackage = getReferrerPackageName();
        boolean equals = TextUtils.equals(intent.getAction(), SEM_INTENT_ACTION_AI_ASSIST);
        this.mIsAiAssist = equals;
        if (equals && !"android".equals(this.mReferrerPackage)) {
            Log.i(TAG, "[AI Key] It was not executed with AI key.");
            finish();
            return;
        }
        this.mIsAltAiPressed = intent.getBooleanExtra(SEM_INTENT_EXTRA_AI_ALT_PRESS, false);
        this.mIsAiFromKeyboardShortcut = "true".equals(intent.getStringExtra(SEM_INTENT_EXTRA_FROM_KEYBOARD_SHORTCUT));
        this.mIsKeyDownPressed = false;
        Log.i(TAG, "[AI Key] mIsAiAssist : " + this.mIsAiAssist + ", mIsAltAiPressed : " + this.mIsAltAiPressed + ", mIsAiFromKeyboardShortcut : " + this.mIsAiFromKeyboardShortcut);
        if (bundle != null) {
            boolean z2 = bundle.getBoolean(SAVE_STATE_RECREATE_FROM_ALT_AI);
            this.mRecreatedFromAltAi = z2;
            if (z2) {
                this.mIsAiAssist = true;
                this.mIsAltAiPressed = true;
                this.mRecreatedFromAltAi = false;
            }
        }
        this.mIntents.add(0, new Intent(intent));
        this.mTitle = charSequence;
        this.mDefaultTitleResId = i;
        this.mSupportsAlwaysUseOption = z;
        this.mPersonalProfileUserHandle = fetchPersonalProfileUserHandle();
        this.mWorkProfileUserHandle = fetchWorkProfileUserProfile();
        this.mCloneProfileUserHandle = fetchCloneProfileUserHandle();
        this.mPrivateProfileUserHandle = fetchPrivateProfileUserHandle();
        this.mTabOwnerUserHandleForLaunch = fetchTabOwnerUserHandleForLaunch();
        this.mMultiProfilePagerAdapter = createMultiProfilePagerAdapter(intentArr, list, (!this.mSupportsAlwaysUseOption || isVoiceInteraction() || shouldShowTabs() || hasCloneProfile()) ? false : true);
        if (configureContentView()) {
            return;
        }
        PackageMonitor createPackageMonitor = createPackageMonitor(this.mMultiProfilePagerAdapter.getPersonalListAdapter());
        this.mPersonalPackageMonitor = createPackageMonitor;
        createPackageMonitor.register(this, getMainLooper(), getPersonalProfileUserHandle(), false);
        if (shouldShowTabs()) {
            PackageMonitor createPackageMonitor2 = createPackageMonitor(this.mMultiProfilePagerAdapter.getWorkListAdapter());
            this.mWorkPackageMonitor = createPackageMonitor2;
            createPackageMonitor2.register(this, getMainLooper(), getWorkProfileUserHandle(), false);
        }
        this.mRegistered = true;
        final FrameLayout frameLayout = (FrameLayout) findViewById(16908290);
        if (frameLayout != null) {
            frameLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.internal.app.ResolverActivity.2
                @Override // android.view.View.OnApplyWindowInsetsListener
                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                    int dimensionPixelSize = ResolverActivity.this.getResources().getDimensionPixelSize(R.dimen.sem_resolver_padding_bottom);
                    int dimensionPixelSize2 = ResolverActivity.this.getResources().getDimensionPixelSize(R.dimen.sem_resolver_padding_right);
                    frameLayout.setPadding(insets.left + dimensionPixelSize2, insets.top + dimensionPixelSize2, insets.right + dimensionPixelSize2, insets.bottom + dimensionPixelSize);
                    return windowInsets.consumeSystemWindowInsets();
                }
            });
        }
        ResolverDrawerLayout resolverDrawerLayout = (ResolverDrawerLayout) findViewById(R.id.contentPanel);
        if (resolverDrawerLayout != null) {
            resolverDrawerLayout.setOnDismissedListener(new ResolverDrawerLayout.OnDismissedListener() { // from class: com.android.internal.app.ResolverActivity.3
                @Override // com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener
                public void onDismissed() {
                    ResolverActivity.this.semFinishAfterAnimation();
                }
            });
            boolean hasSystemFeature = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN);
            if (isVoiceInteraction() || !hasSystemFeature) {
                resolverDrawerLayout.setCollapsed(false);
            }
            resolverDrawerLayout.setSystemUiVisibility(768);
            resolverDrawerLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda14
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    return ResolverActivity.this.onApplyWindowInsets(view, windowInsets);
                }
            });
            this.mResolverDrawerLayout = resolverDrawerLayout;
            resolverDrawerLayout.semDisableDrag(true);
            int count = this.mMultiProfilePagerAdapter.getCount();
            for (int i3 = 0; i3 < count; i3++) {
                View findViewById = this.mMultiProfilePagerAdapter.getItem(i3).rootView.findViewById(R.id.resolver_list);
                if (findViewById != null) {
                    findViewById.setAccessibilityDelegate(new AppListAccessibilityDelegate(resolverDrawerLayout));
                }
            }
        }
        View findViewById2 = findViewById(R.id.profile_button);
        this.mProfileView = findViewById2;
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda15
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ResolverActivity.this.onProfileClick(view);
                }
            });
            updateProfileViewButton();
        }
        Set<String> categories = intent.getCategories();
        int i4 = this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem() ? 451 : 453;
        StringBuilder sb = new StringBuilder();
        sb.append(intent.getAction());
        sb.append(":");
        sb.append(intent.getType());
        sb.append(":");
        sb.append(categories != null ? Arrays.toString(categories.toArray()) : "");
        MetricsLogger.action(this, i4, sb.toString());
        semTransitionOverride(this, 0);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (this.mIsPopOver) {
            attributes.flags &= -1025;
            getWindow().setAttributes(attributes);
        }
        attributes.flags |= 16777216;
        getWindow().setAttributes(attributes);
        boolean z3 = Settings.System.getInt(getContentResolver(), "accessibility_reduce_transparency", 0) == 1;
        this.mIsReduceTransparency = z3;
        if (z3) {
            getWindow().setDimAmount(this.mIsNight ? SEM_RESOLVER_REDUCE_TRANSPARENCY_DIM_AMOUNT_DARK : SEM_RESOLVER_REDUCE_TRANSPARENCY_DIM_AMOUNT_LIGHT);
        }
        if (this.mSupportsAlwaysUseOption) {
            ImageView imageView = (ImageView) findViewById(R.id.sem_resolver_tips_icon);
            this.mTipsIcon = imageView;
            if (imageView != null) {
                imageView.setVisibility(0);
                this.mTipsIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda16
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ResolverActivity.this.lambda$onCreate$0(view);
                    }
                });
            }
            TextView textView = (TextView) findViewById(16908310);
            if (textView != null) {
                textView.sendAccessibilityEvent(8);
            }
        }
        if (CompatSandbox.isAppCompatOverrideEnabled(getResources().getConfiguration())) {
            getWindow().setElevation(0.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        SemTipPopup semTipPopup = this.mTipsDescriptionPopup;
        if (semTipPopup != null && semTipPopup.isShowing()) {
            this.mTipsDescriptionPopup.dismiss(false);
        } else {
            semCreateAndShowTipsPopup(this.mTipsIcon);
        }
    }

    protected AbstractMultiProfilePagerAdapter createMultiProfilePagerAdapter(Intent[] intentArr, List<ResolveInfo> list, boolean z) {
        if (shouldShowTabs()) {
            return createResolverMultiProfilePagerAdapterForTwoProfiles(intentArr, list, z);
        }
        return createResolverMultiProfilePagerAdapterForOneProfile(intentArr, list, z);
    }

    protected AbstractMultiProfilePagerAdapter.MyUserIdProvider createMyUserIdProvider() {
        return new AbstractMultiProfilePagerAdapter.MyUserIdProvider();
    }

    protected AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker createCrossProfileIntentsChecker() {
        return new AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker(getContentResolver());
    }

    /* renamed from: com.android.internal.app.ResolverActivity$4, reason: invalid class name */
    class AnonymousClass4 implements AbstractMultiProfilePagerAdapter.QuietModeManager {
        private boolean mIsWaitingToEnableWorkProfile = false;
        final /* synthetic */ UserManager val$userManager;

        AnonymousClass4(ResolverActivity resolverActivity, UserManager userManager) {
            this.val$userManager = userManager;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public boolean isQuietModeEnabled(UserHandle userHandle) {
            return this.val$userManager.isQuietModeEnabled(userHandle);
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public void requestQuietModeEnabled(final boolean z, final UserHandle userHandle) {
            Executor executor = AsyncTask.THREAD_POOL_EXECUTOR;
            final UserManager userManager = this.val$userManager;
            executor.execute(new Runnable() { // from class: com.android.internal.app.ResolverActivity$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserManager.this.requestQuietModeEnabled(z, userHandle);
                }
            });
            this.mIsWaitingToEnableWorkProfile = true;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public void markWorkProfileEnabledBroadcastReceived() {
            this.mIsWaitingToEnableWorkProfile = false;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public boolean isWaitingToEnableWorkProfile() {
            return this.mIsWaitingToEnableWorkProfile;
        }
    }

    protected AbstractMultiProfilePagerAdapter.QuietModeManager createQuietModeManager() {
        return new AnonymousClass4(this, (UserManager) getSystemService(UserManager.class));
    }

    protected AbstractMultiProfilePagerAdapter.EmptyStateProvider createBlockerEmptyStateProvider() {
        if (!getUser().equals(getIntentUser())) {
            return new AbstractMultiProfilePagerAdapter.EmptyStateProvider(this) { // from class: com.android.internal.app.ResolverActivity.5
            };
        }
        return new NoCrossProfileEmptyStateProvider(getPersonalProfileUserHandle(), new NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, R.string.resolver_cross_profile_blocked, DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_PERSONAL, R.string.resolver_cant_access_personal_apps_explanation, 158, METRICS_CATEGORY_RESOLVER), new NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, R.string.resolver_cross_profile_blocked, DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_WORK, R.string.resolver_cant_access_work_apps_explanation, 159, METRICS_CATEGORY_RESOLVER), createCrossProfileIntentsChecker(), getTabOwnerUserHandleForLaunch());
    }

    protected AbstractMultiProfilePagerAdapter.EmptyStateProvider createEmptyStateProvider(UserHandle userHandle) {
        return new AbstractMultiProfilePagerAdapter.CompositeEmptyStateProvider(createBlockerEmptyStateProvider(), new WorkProfilePausedEmptyStateProvider(this, userHandle, this.mQuietModeManager, new AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda13
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener
            public final void onSwitchOnWorkSelected() {
                ResolverActivity.this.lambda$createEmptyStateProvider$1();
            }
        }, getMetricsCategory()), new NoAppsAvailableEmptyStateProvider(this, userHandle, getPersonalProfileUserHandle(), getMetricsCategory(), getTabOwnerUserHandleForLaunch()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createEmptyStateProvider$1() {
        AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener onSwitchOnWorkSelectedListener = this.mOnSwitchOnWorkSelectedListener;
        if (onSwitchOnWorkSelectedListener != null) {
            onSwitchOnWorkSelectedListener.onSwitchOnWorkSelected();
        }
    }

    private ResolverMultiProfilePagerAdapter createResolverMultiProfilePagerAdapterForOneProfile(Intent[] intentArr, List<ResolveInfo> list, boolean z) {
        return new ResolverMultiProfilePagerAdapter(this, createResolverListAdapter(this, this.mIntents, intentArr, list, z, getPersonalProfileUserHandle()), createEmptyStateProvider(null), createQuietModeManager(), null, getCloneProfileUserHandle());
    }

    private UserHandle getIntentUser() {
        if (getIntent().hasExtra(EXTRA_CALLING_USER)) {
            return (UserHandle) getIntent().getParcelableExtra(EXTRA_CALLING_USER, UserHandle.class);
        }
        return getUser();
    }

    private ResolverMultiProfilePagerAdapter createResolverMultiProfilePagerAdapterForTwoProfiles(Intent[] intentArr, List<ResolveInfo> list, boolean z) {
        int i;
        int currentProfile = getCurrentProfile();
        UserHandle intentUser = getIntentUser();
        if (!getTabOwnerUserHandleForLaunch().equals(intentUser)) {
            if (getPersonalProfileUserHandle().equals(intentUser)) {
                i = 0;
            } else {
                if (getWorkProfileUserHandle().equals(intentUser)) {
                    i = 1;
                }
                i = currentProfile;
            }
        } else {
            int selectedProfileExtra = getSelectedProfileExtra();
            if (selectedProfileExtra != -1) {
                i = selectedProfileExtra;
            }
            i = currentProfile;
        }
        ResolverListAdapter createResolverListAdapter = createResolverListAdapter(this, this.mIntents, i == 0 ? intentArr : null, list, z && UserHandle.myUserId() == getPersonalProfileUserHandle().getIdentifier(), getPersonalProfileUserHandle());
        UserHandle workProfileUserHandle = getWorkProfileUserHandle();
        return new ResolverMultiProfilePagerAdapter(this, createResolverListAdapter, createResolverListAdapter(this, this.mIntents, i == 1 ? intentArr : null, list, z && UserHandle.myUserId() == workProfileUserHandle.getIdentifier(), workProfileUserHandle), createEmptyStateProvider(getWorkProfileUserHandle()), createQuietModeManager(), i, getWorkProfileUserHandle(), getCloneProfileUserHandle());
    }

    int getSelectedProfileExtra() {
        if (!getIntent().hasExtra(EXTRA_SELECTED_PROFILE)) {
            return -1;
        }
        int intExtra = getIntent().getIntExtra(EXTRA_SELECTED_PROFILE, -1);
        if (intExtra == 0 || intExtra == 1) {
            return intExtra;
        }
        throw new IllegalArgumentException("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE has invalid value " + intExtra + ". Must be either ResolverActivity.PROFILE_PERSONAL or ResolverActivity.PROFILE_WORK.");
    }

    protected int getCurrentProfile() {
        return UserHandle.myUserId() == getPersonalProfileUserHandle().getIdentifier() ? 0 : 1;
    }

    protected UserHandle getPersonalProfileUserHandle() {
        if (privateSpaceEnabled() && isLaunchedInSingleUserMode()) {
            return getTabOwnerUserHandleForLaunch();
        }
        return this.mPersonalProfileUserHandle;
    }

    protected UserHandle getWorkProfileUserHandle() {
        return this.mWorkProfileUserHandle;
    }

    protected UserHandle getCloneProfileUserHandle() {
        return this.mCloneProfileUserHandle;
    }

    protected UserHandle getTabOwnerUserHandleForLaunch() {
        return this.mTabOwnerUserHandleForLaunch;
    }

    protected UserHandle getPrivateProfileUserHandle() {
        return this.mPrivateProfileUserHandle;
    }

    protected UserHandle fetchPersonalProfileUserHandle() {
        if (SemPersonaManager.isAppSeparationUserId(UserHandle.myUserId())) {
            this.mPersonalProfileUserHandle = UserHandle.of(UserHandle.myUserId());
        } else {
            this.mPersonalProfileUserHandle = UserHandle.of(ActivityManager.getCurrentUser());
        }
        return this.mPersonalProfileUserHandle;
    }

    protected UserHandle fetchWorkProfileUserProfile() {
        this.mWorkProfileUserHandle = null;
        if (this.mIsAiAssist) {
            return null;
        }
        for (UserInfo userInfo : ((UserManager) getSystemService(UserManager.class)).getProfiles(this.mPersonalProfileUserHandle.getIdentifier())) {
            if (userInfo.isManagedProfile()) {
                this.mWorkProfileUserHandle = userInfo.getUserHandle();
            }
        }
        return this.mWorkProfileUserHandle;
    }

    protected UserHandle fetchCloneProfileUserHandle() {
        this.mCloneProfileUserHandle = null;
        for (UserInfo userInfo : ((UserManager) getSystemService(UserManager.class)).getProfiles(this.mPersonalProfileUserHandle.getIdentifier())) {
            if (userInfo.isCloneProfile()) {
                this.mCloneProfileUserHandle = userInfo.getUserHandle();
            }
        }
        return this.mCloneProfileUserHandle;
    }

    protected UserHandle fetchPrivateProfileUserHandle() {
        this.mPrivateProfileUserHandle = null;
        Iterator<UserInfo> it = ((UserManager) getSystemService(UserManager.class)).getProfiles(this.mPersonalProfileUserHandle.getIdentifier()).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UserInfo next = it.next();
            if (next.isPrivateProfile()) {
                this.mPrivateProfileUserHandle = next.getUserHandle();
                break;
            }
        }
        return this.mPrivateProfileUserHandle;
    }

    private UserHandle fetchTabOwnerUserHandleForLaunch() {
        if (UserHandle.of(UserHandle.myUserId()).equals(getWorkProfileUserHandle())) {
            return this.mWorkProfileUserHandle;
        }
        if (privateSpaceEnabled() && isLaunchedAsPrivateProfile()) {
            return this.mPrivateProfileUserHandle;
        }
        return this.mPersonalProfileUserHandle;
    }

    private boolean hasWorkProfile() {
        return getWorkProfileUserHandle() != null;
    }

    private boolean hasCloneProfile() {
        return getCloneProfileUserHandle() != null;
    }

    protected final boolean isLaunchedAsCloneProfile() {
        return hasCloneProfile() && UserHandle.myUserId() == getCloneProfileUserHandle().getIdentifier();
    }

    protected final boolean isLaunchedAsPrivateProfile() {
        return getPrivateProfileUserHandle() != null && UserHandle.myUserId() == getPrivateProfileUserHandle().getIdentifier();
    }

    protected final boolean isLaunchedInSingleUserMode() {
        if (isLaunchedAsPrivateProfile()) {
            return true;
        }
        return getIntent().getBooleanExtra(EXTRA_RESTRICT_TO_SINGLE_USER, false);
    }

    protected boolean shouldShowTabs() {
        return !(privateSpaceEnabled() && isLaunchedInSingleUserMode()) && hasWorkProfile() && ENABLE_TABBED_VIEW && !SemPersonaManager.isDoEnabled(0);
    }

    protected void onProfileClick(View view) {
        DisplayResolveInfo otherProfile = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile();
        if (otherProfile == null) {
            return;
        }
        this.mProfileSwitchMessage = null;
        onTargetSelected(otherProfile, false);
        finish();
    }

    protected boolean shouldAddFooterView() {
        View findViewById;
        return useLayoutWithDefault() || (findViewById = findViewById(R.id.button_bar)) == null || findViewById.getVisibility() == 8;
    }

    protected WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        View findViewById;
        Context context = this.mContext;
        if (context != null && (context.getDisplayId() != 1 || !hasCutout(this.mContext.getDisplay()))) {
            this.mSystemWindowInsets = windowInsets.getInsets(WindowInsets.Type.systemBars());
        } else {
            this.mSystemWindowInsets = windowInsets.getSystemWindowInsets();
        }
        this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        resetButtonBar();
        if (shouldUseMiniResolver() && (findViewById = findViewById(R.id.button_bar_container)) != null) {
            findViewById.setPadding(0, 0, 0, this.mSystemWindowInsets.bottom + getResources().getDimensionPixelOffset(R.dimen.resolver_button_bar_spacing));
        }
        if (shouldAddFooterView()) {
            applyFooterView(this.mSystemWindowInsets.bottom);
        }
        return windowInsets.consumeSystemWindowInsets();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        this.mIsKeyDownPressed = true;
        if (!this.mIsAiAssist) {
            return super.onKeyDown(i, keyEvent);
        }
        Log.i(TAG, "[AI Key] KeyDown : " + i + ", isAltPressed : " + keyEvent.isAltPressed());
        if (this.mIsAltAiPressed && keyEvent.isAltPressed() && (i == 1104 || (i == 336 && (keyEvent.getMetaState() & 1) != 0))) {
            AbsListView absListView = (AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
            ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
            int checkedItemPosition = absListView.getCheckedItemPosition();
            int count = activeListAdapter.getCount();
            if (count > 0) {
                absListView.setItemChecked((checkedItemPosition + 1) % count, true);
                absListView.getChildAt(absListView.getCheckedItemPosition()).sendAccessibilityEvent(8);
            }
            activeListAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.mIsAiAssist) {
            Log.i(TAG, "[AI Key] KeyUp : " + i + ", isAltPressed : " + keyEvent.isAltPressed());
            if (!this.mIsAltAiPressed && keyEvent.isAltPressed() && i == 1104) {
                this.mIsKeyDownPressed = false;
                this.mRecreatedFromAltAi = true;
                recreate();
                return true;
            }
            boolean z = this.mIsAltAiPressed;
            if (z && (i == 57 || i == 58)) {
                int checkedItemPosition = ((AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView()).getCheckedItemPosition();
                if (checkedItemPosition != -1) {
                    startSelected(checkedItemPosition, true, true);
                }
                this.mIsKeyDownPressed = false;
                return true;
            }
            if (!z && this.mIsKeyDownPressed && i == 1104) {
                finish();
                this.mIsKeyDownPressed = false;
                return true;
            }
            this.mIsKeyDownPressed = false;
            return super.onKeyUp(i, keyEvent);
        }
        this.mIsKeyDownPressed = false;
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mOrientation = getResources().getConfiguration().orientation;
        if (!this.mIsAiAssist) {
            this.mResolverDrawerLayout.semSetMaxWidth(getResources().getDimensionPixelSize(R.dimen.resolver_max_width));
        }
        this.mMultiProfilePagerAdapter.getActiveListAdapter().handlePackagesChanged();
        if (this.mIsIntentPicker && shouldShowTabs() && !useLayoutWithDefault() && !shouldUseMiniResolver()) {
            updateIntentPickerPaddings();
        }
        Insets insets = this.mSystemWindowInsets;
        if (insets != null) {
            this.mResolverDrawerLayout.setPadding(insets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        }
        SemTipPopup semTipPopup = this.mTipsDescriptionPopup;
        if (semTipPopup == null || !semTipPopup.isShowing()) {
            return;
        }
        this.mTipsDescriptionPopup.dismiss(false);
    }

    private void updateIntentPickerPaddings() {
        View findViewById = findViewById(R.id.title_container);
        if (findViewById != null) {
            findViewById.setPadding(findViewById.getPaddingLeft(), findViewById.getPaddingTop(), findViewById.getPaddingRight(), getResources().getDimensionPixelSize(R.dimen.resolver_title_padding_bottom));
        }
        View findViewById2 = findViewById(R.id.button_bar);
        if (findViewById2 != null) {
            findViewById2.setPadding(findViewById2.getPaddingLeft(), getResources().getDimensionPixelSize(R.dimen.resolver_button_bar_spacing), findViewById2.getPaddingRight(), getResources().getDimensionPixelSize(R.dimen.resolver_button_bar_spacing));
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void sendVoiceChoicesIfNeeded() {
        if (isVoiceInteraction()) {
            int count = this.mMultiProfilePagerAdapter.getActiveListAdapter().getCount();
            VoiceInteractor.PickOptionRequest.Option[] optionArr = new VoiceInteractor.PickOptionRequest.Option[count];
            for (int i = 0; i < count; i++) {
                TargetInfo item = this.mMultiProfilePagerAdapter.getActiveListAdapter().getItem(i);
                if (item == null) {
                    return;
                }
                optionArr[i] = optionForChooserTarget(item, i);
            }
            this.mPickOptionRequest = new PickTargetOptionRequest(new VoiceInteractor.Prompt(getTitle()), optionArr, null);
            getVoiceInteractor().submitRequest(this.mPickOptionRequest);
        }
    }

    VoiceInteractor.PickOptionRequest.Option optionForChooserTarget(TargetInfo targetInfo, int i) {
        return new VoiceInteractor.PickOptionRequest.Option(targetInfo.getDisplayLabel(), i);
    }

    protected final void setAdditionalTargets(Intent[] intentArr) {
        if (intentArr != null) {
            for (Intent intent : intentArr) {
                this.mIntents.add(intent);
            }
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public Intent getTargetIntent() {
        if (this.mIntents.isEmpty()) {
            return null;
        }
        return this.mIntents.get(0);
    }

    protected String getReferrerPackageName() {
        Uri uri;
        try {
            uri = getReferrer();
        } catch (Exception e) {
            Log.e(TAG, "getReferrer error!!!" + e);
            uri = null;
        }
        if (uri == null || !"android-app".equals(uri.getScheme())) {
            return null;
        }
        return uri.getHost();
    }

    public int getLayoutResource() {
        return this.mIsAiAssist ? this.mIsAltAiPressed ? R.layout.sem_resolver_ai_alt_press : R.layout.sem_resolver_ai : R.layout.sem_resolver_grid;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void updateProfileViewButton() {
        if (this.mProfileView == null) {
            return;
        }
        DisplayResolveInfo otherProfile = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile();
        if (otherProfile != null && !shouldShowTabs()) {
            this.mProfileView.setVisibility(0);
            View findViewById = this.mProfileView.findViewById(R.id.profile_button);
            if (!(findViewById instanceof TextView)) {
                findViewById = this.mProfileView.findViewById(16908308);
            }
            TextView textView = (TextView) findViewById;
            textView.lambda$setTextAsync$0(otherProfile.getDisplayLabel());
            semSetTextSizeByMaxFontScale(textView, R.dimen.sem_resolver_pagemode_titlepanel_text_size);
            return;
        }
        this.mProfileView.setVisibility(8);
    }

    private void setProfileSwitchMessage(int i) {
        if (i == -2 || i == UserHandle.myUserId()) {
            return;
        }
        UserManager userManager = (UserManager) getSystemService("user");
        UserInfo userInfo = userManager.getUserInfo(i);
        boolean isManagedProfile = userInfo != null ? userInfo.isManagedProfile() : false;
        boolean isManagedProfile2 = userManager.isManagedProfile();
        if (isManagedProfile && !isManagedProfile2) {
            this.mProfileSwitchMessage = getForwardToPersonalMsg();
        } else {
            if (isManagedProfile || !isManagedProfile2) {
                return;
            }
            this.mProfileSwitchMessage = getForwardToWorkMsg();
        }
    }

    private String getForwardToPersonalMsg() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_PERSONAL, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getForwardToPersonalMsg$2;
                lambda$getForwardToPersonalMsg$2 = ResolverActivity.this.lambda$getForwardToPersonalMsg$2();
                return lambda$getForwardToPersonalMsg$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getForwardToPersonalMsg$2() {
        return getString(R.string.forward_intent_to_owner);
    }

    private String getForwardToWorkMsg() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_WORK, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda10
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getForwardToWorkMsg$3;
                lambda$getForwardToWorkMsg$3 = ResolverActivity.this.lambda$getForwardToWorkMsg$3();
                return lambda$getForwardToWorkMsg$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getForwardToWorkMsg$3() {
        return getString(R.string.forward_intent_to_work);
    }

    public void setSafeForwardingMode(boolean z) {
        this.mSafeForwardingMode = z;
    }

    protected CharSequence getTitleForAction(Intent intent, int i) {
        ActionTitle forAction;
        if (this.mResolvingHome) {
            forAction = ActionTitle.HOME;
        } else {
            forAction = ActionTitle.forAction(intent.getAction());
        }
        this.mMultiProfilePagerAdapter.getActiveListAdapter().getFilteredPosition();
        if (forAction == ActionTitle.DEFAULT && i != 0) {
            return getString(i);
        }
        return getString(forAction.titleRes);
    }

    void dismiss() {
        if (isFinishing()) {
            return;
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        if (!this.mRegistered) {
            this.mPersonalPackageMonitor.register(this, getMainLooper(), getPersonalProfileUserHandle(), false);
            if (shouldShowTabs()) {
                if (this.mWorkPackageMonitor == null) {
                    this.mWorkPackageMonitor = createPackageMonitor(this.mMultiProfilePagerAdapter.getWorkListAdapter());
                }
                this.mWorkPackageMonitor.register(this, getMainLooper(), getWorkProfileUserHandle(), false);
            }
            this.mRegistered = true;
        }
        if (shouldShowTabs() && this.mQuietModeManager.isWaitingToEnableWorkProfile() && this.mQuietModeManager.isQuietModeEnabled(getWorkProfileUserHandle())) {
            this.mQuietModeManager.markWorkProfileEnabledBroadcastReceived();
        }
        this.mMultiProfilePagerAdapter.getActiveListAdapter().handlePackagesChanged();
        updateProfileViewButton();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        getWindow().addSystemFlags(524288);
        if (shouldShowTabs()) {
            this.mWorkProfileStateReceiver = createWorkProfileStateReceiver();
            registerWorkProfileStateReceiver();
            this.mWorkProfileHasBeenEnabled = isWorkProfileEnabled();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWorkProfileEnabled() {
        UserHandle workProfileUserHandle = getWorkProfileUserHandle();
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        return !userManager.isQuietModeEnabled(workProfileUserHandle) && userManager.isUserUnlocked(workProfileUserHandle);
    }

    private void registerWorkProfileStateReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_UNLOCKED);
        intentFilter.addAction(Intent.ACTION_MANAGED_PROFILE_AVAILABLE);
        intentFilter.addAction(Intent.ACTION_MANAGED_PROFILE_UNAVAILABLE);
        registerReceiverAsUser(this.mWorkProfileStateReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    @Override // android.app.Activity
    protected void onStop() {
        KeyguardManager keyguardManager;
        super.onStop();
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags &= -524289;
        window.setAttributes(attributes);
        if (this.mRegistered) {
            this.mPersonalPackageMonitor.unregister();
            PackageMonitor packageMonitor = this.mWorkPackageMonitor;
            if (packageMonitor != null) {
                packageMonitor.unregister();
            }
            this.mRegistered = false;
        }
        if ((getIntent().getFlags() & 268435456) != 0 && !isVoiceInteraction() && !this.mResolvingHome && !this.mRetainInOnStop && !isChangingConfigurations()) {
            Context baseContext = getBaseContext();
            if (this.mLaunchedFromUid == 1001 && (keyguardManager = (KeyguardManager) baseContext.getSystemService(Context.KEYGUARD_SERVICE)) != null && keyguardManager.isKeyguardLocked()) {
                Log.w(TAG, "we don't finish resolver for this exceptional case");
                return;
            }
            finish();
        }
        if (this.mWorkPackageMonitor != null) {
            unregisterReceiver(this.mWorkProfileStateReceiver);
            this.mWorkPackageMonitor = null;
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.mIsAiAssist) {
            try {
                IWindowManager iWindowManager = this.mWindowManager;
                if (iWindowManager != null) {
                    iWindowManager.unregisterSystemKeyEvent(1104, getComponentName());
                }
                Log.i(TAG, "[AI Key] unregisterSystemKeyEvent : " + getComponentName());
            } catch (RemoteException | IllegalArgumentException | SecurityException e) {
                Log.e(TAG, "[AI Key] failed to unregisterSystemKeyEvent : " + e.getCause() + ", " + e.getMessage());
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        ResolverListAdapter inactiveListAdapter;
        PickTargetOptionRequest pickTargetOptionRequest;
        super.onDestroy();
        if (!isChangingConfigurations() && (pickTargetOptionRequest = this.mPickOptionRequest) != null) {
            pickTargetOptionRequest.cancel();
        }
        AbstractMultiProfilePagerAdapter abstractMultiProfilePagerAdapter = this.mMultiProfilePagerAdapter;
        if (abstractMultiProfilePagerAdapter != null) {
            ResolverListAdapter activeListAdapter = abstractMultiProfilePagerAdapter.getActiveListAdapter();
            if (activeListAdapter != null) {
                activeListAdapter.onDestroy();
            }
            if (!Flags.fixResolverMemoryLeak() || (inactiveListAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter()) == null) {
                return;
            }
            inactiveListAdapter.onDestroy();
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        ViewPager viewPager = (ViewPager) findViewById(R.id.profile_pager);
        if (viewPager != null) {
            bundle.putInt(LAST_SHOWN_TAB_KEY, viewPager.getCurrentItem());
        }
        if (this.mIsAiAssist) {
            bundle.putBoolean(SAVE_STATE_RECREATE_FROM_ALT_AI, this.mRecreatedFromAltAi);
        }
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        resetButtonBar();
        ViewPager viewPager = (ViewPager) findViewById(R.id.profile_pager);
        if (viewPager != null) {
            viewPager.setCurrentItem(bundle.getInt(LAST_SHOWN_TAB_KEY));
        }
        this.mMultiProfilePagerAdapter.clearInactiveProfileCache();
    }

    private boolean hasManagedProfile() {
        UserManager userManager = (UserManager) getSystemService("user");
        if (userManager == null) {
            return false;
        }
        try {
            for (UserInfo userInfo : userManager.getProfiles(getUserId())) {
                if (userInfo != null && userInfo.isManagedProfile()) {
                    return true;
                }
            }
        } catch (SecurityException unused) {
        }
        return false;
    }

    private boolean supportsManagedProfiles(ResolveInfo resolveInfo) {
        return getPackageManager().getApplicationInfo(resolveInfo.activityInfo.packageName, 0).targetSdkVersion >= 21;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAlwaysButtonEnabled(boolean z, int i, boolean z2) {
        ResolveInfo resolveInfo;
        boolean z3;
        if (!this.mMultiProfilePagerAdapter.getCurrentUserHandle().equals(getUser())) {
            this.mAlwaysButton.setEnabled(false);
            return;
        }
        if (z) {
            resolveInfo = this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(i, z2);
            if (resolveInfo == null) {
                Log.e(TAG, "Invalid position supplied to setAlwaysButtonEnabled");
                return;
            }
            if (resolveInfo.targetUserId != -2) {
                Log.e(TAG, "Attempted to set selection to resolve info for another user");
                z3 = false;
            } else {
                z3 = true;
            }
            this.mAlwaysButton.lambda$setTextAsync$0(getResources().getString(R.string.activity_resolver_use_always));
        } else {
            resolveInfo = null;
            z3 = false;
        }
        if (resolveInfo != null) {
            if (this.mPm.checkPermission(Manifest.permission.RECORD_AUDIO, resolveInfo.activityInfo.packageName) != 0) {
                z3 = !getIntent().getBooleanExtra(EXTRA_IS_AUDIO_CAPTURE_DEVICE, false);
            }
        }
        this.mAlwaysButton.setEnabled(z3);
    }

    public void onButtonClick(View view) {
        int id = view.getId();
        if (this.mSecondDepth) {
            startSelected(this.mSemSelectTaskListAdapter.mSelectedItem, id == 16908898, true);
            return;
        }
        AbsListView absListView = (AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
        ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (activeListAdapter.hasFilteredItem()) {
            activeListAdapter.getFilteredPosition();
        } else {
            absListView.getCheckedItemPosition();
        }
        boolean z = !activeListAdapter.hasFilteredItem();
        int checkedItemPosition = absListView.getCheckedItemPosition();
        if (checkedItemPosition == -1) {
            return;
        }
        startSelected(checkedItemPosition, id == 16908898, z);
    }

    public void startSelected(int i, boolean z, boolean z2) {
        ResolveInfo resolveInfoForPosition;
        TargetInfo targetInfoForPosition;
        if (isFinishing()) {
            return;
        }
        if (this.mSecondDepth) {
            resolveInfoForPosition = this.mSemSelectTaskListAdapter.resolveInfoForPosition(i, z2);
        } else {
            resolveInfoForPosition = this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(i, z2);
        }
        if (this.mResolvingHome && hasManagedProfile() && !supportsManagedProfiles(resolveInfoForPosition)) {
            Toast.makeText(this, getWorkProfileNotSupportedMsg(resolveInfoForPosition.activityInfo.loadLabel(getPackageManager()).toString()), 1).show();
            return;
        }
        if (this.mSecondDepth) {
            targetInfoForPosition = this.mSemSelectTaskListAdapter.targetInfoForPosition(i, z2);
        } else {
            int lastChosenActivityIndex = this.mMultiProfilePagerAdapter.getActiveListAdapter().getLastChosenActivityIndex();
            DisplayResolveInfo displayResolveInfo = this.mMultiProfilePagerAdapter.getActiveListAdapter().getDisplayResolveInfo(i);
            String lastChosenActivity = this.mMultiProfilePagerAdapter.getActiveListAdapter().getLastChosenActivity();
            boolean z3 = false;
            if (displayResolveInfo.getExtendedInfo() != null && !TextUtils.isEmpty(lastChosenActivity)) {
                z3 = lastChosenActivity.equals(displayResolveInfo.getExtendedInfo().toString());
            }
            Log.i(TAG, "lastChosenIndex : " + lastChosenActivityIndex + ", lastChosenActivity" + lastChosenActivity);
            if (lastChosenActivityIndex >= 0 && z3) {
                SemSelectTaskListAdapter semSelectTaskListAdapter = new SemSelectTaskListAdapter(displayResolveInfo.getSimilarList(), this.mMultiProfilePagerAdapter, new ResolverActivity$$ExternalSyntheticLambda0(this));
                this.mSemSelectTaskListAdapter = semSelectTaskListAdapter;
                targetInfoForPosition = semSelectTaskListAdapter.targetInfoForPosition(lastChosenActivityIndex, z2);
            } else {
                targetInfoForPosition = this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(i, z2);
            }
        }
        if (targetInfoForPosition != null && onTargetSelected(targetInfoForPosition, z)) {
            if (z && this.mSupportsAlwaysUseOption) {
                MetricsLogger.action(this, 455);
            } else if (this.mSupportsAlwaysUseOption) {
                MetricsLogger.action(this, 456);
            } else {
                MetricsLogger.action(this, 457);
            }
            MetricsLogger.action(this, this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem() ? 452 : 454);
            finish();
        }
    }

    private String getWorkProfileNotSupportedMsg(final String str) {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_PROFILE_NOT_SUPPORTED, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getWorkProfileNotSupportedMsg$4;
                lambda$getWorkProfileNotSupportedMsg$4 = ResolverActivity.this.lambda$getWorkProfileNotSupportedMsg$4(str);
                return lambda$getWorkProfileNotSupportedMsg$4;
            }
        }, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getWorkProfileNotSupportedMsg$4(String str) {
        return getString(R.string.activity_resolver_work_profiles_support, str);
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public final void onPostListReady(ResolverListAdapter resolverListAdapter, boolean z, boolean z2, boolean z3) {
        if (isDestroyed() || isAutolaunching()) {
            return;
        }
        if (this.mIsIntentPicker) {
            ((ResolverMultiProfilePagerAdapter) this.mMultiProfilePagerAdapter).setUseLayoutWithDefault(useLayoutWithDefault());
        }
        if (this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(resolverListAdapter)) {
            this.mMultiProfilePagerAdapter.showEmptyResolverListEmptyState(resolverListAdapter);
        } else {
            this.mMultiProfilePagerAdapter.showListView(resolverListAdapter);
        }
        if (!z3 && z2 && maybeAutolaunchActivity()) {
            Log.e(TAG, "onPostListReady return skipAutoLaunch = " + z3 + ", rebuildComplete = " + z2);
            return;
        }
        if (z) {
            maybeCreateHeader(resolverListAdapter);
            resetButtonBar();
            if (z2) {
                onListRebuilt(resolverListAdapter, z2);
            }
        }
    }

    protected void onListRebuilt(ResolverListAdapter resolverListAdapter, boolean z) {
        ResolverDrawerLayout resolverDrawerLayout;
        semSetupAdapterListView((AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView(), new ItemClickListener());
        if (shouldShowTabs() && this.mIsIntentPicker && (resolverDrawerLayout = (ResolverDrawerLayout) findViewById(R.id.contentPanel)) != null) {
            resolverDrawerLayout.setMaxCollapsedHeight(getResources().getDimensionPixelSize(useLayoutWithDefault() ? R.dimen.resolver_max_collapsed_height_with_default_with_tabs : R.dimen.resolver_max_collapsed_height_with_tabs));
        }
        if (this.mOnceButton == null || this.mAlwaysButton == null) {
            return;
        }
        if ((this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem() || this.mIsAiAssist) && (this.mMultiProfilePagerAdapter.getActiveAdapterView() instanceof AbsListView)) {
            AbsListView absListView = (AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
            if (this.mIsAiAssist) {
                if (this.mIsAltAiPressed) {
                    absListView.setItemChecked(0, true);
                } else {
                    absListView.setSelectionForcely(-1);
                }
            } else {
                absListView.setItemChecked(this.mMultiProfilePagerAdapter.getActiveListAdapter().getFilteredPosition(), true);
            }
            int checkedItemPosition = absListView.getCheckedItemPosition();
            boolean z2 = checkedItemPosition != -1;
            if (this.mIsAiAssist && this.mIsAltAiPressed) {
                try {
                    ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
                    int count = activeListAdapter.getCount();
                    if (count > 0) {
                        if (z2) {
                            absListView.setItemChecked((activeListAdapter.getFilteredPosition() + 1) % count, true);
                        } else {
                            ResolveInfo lastChosen = activeListAdapter.mResolverListController.getLastChosen();
                            for (int i = 0; i < activeListAdapter.getUnfilteredResolveList().size(); i++) {
                                if (activeListAdapter.semIsComponentEqual(activeListAdapter.getUnfilteredResolveList().get(i).getResolveInfoAt(0), lastChosen)) {
                                    absListView.setItemChecked((i + 1) % count, true);
                                    break;
                                }
                            }
                        }
                    }
                } catch (RemoteException unused) {
                }
            }
            if (this.mSupportsAlwaysUseOption) {
                if (!(z2 && this.mLastSelected == checkedItemPosition) && this.mSupportButtons) {
                    setAlwaysButtonEnabled(z2, checkedItemPosition, true);
                    this.mOnceButton.setEnabled(z2);
                    if (z2) {
                        absListView.smoothScrollToPosition(checkedItemPosition);
                    }
                    this.mLastSelected = checkedItemPosition;
                }
            }
        }
    }

    protected boolean onTargetSelected(TargetInfo targetInfo, boolean z) {
        return onTargetSelected(targetInfo, z, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x028e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean onTargetSelected(com.android.internal.app.chooser.TargetInfo r19, boolean r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 671
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.ResolverActivity.onTargetSelected(com.android.internal.app.chooser.TargetInfo, boolean, boolean):boolean");
    }

    protected void addPreferredActivity(PackageManager packageManager, IntentFilter intentFilter, int i, ComponentName[] componentNameArr, Intent intent) {
        packageManager.addUniquePreferredActivity(intentFilter, i, componentNameArr, intent.getComponent());
    }

    public void safelyStartActivity(TargetInfo targetInfo) {
        safelyStartActivityAsUser(targetInfo, targetInfo.getResolveInfo().userHandle, null);
    }

    public final void safelyStartActivityAsUser(TargetInfo targetInfo, UserHandle userHandle) {
        safelyStartActivityAsUser(targetInfo, userHandle, null);
    }

    protected final void safelyStartActivityAsUser(TargetInfo targetInfo, UserHandle userHandle, Bundle bundle) {
        StrictMode.disableDeathOnFileUriExposure();
        try {
            safelyStartActivityInternal(targetInfo, userHandle, bundle);
        } finally {
            StrictMode.enableDeathOnFileUriExposure();
        }
    }

    protected void safelyStartActivityInternal(TargetInfo targetInfo, UserHandle userHandle, Bundle bundle) {
        if (!targetInfo.isSuspended() && this.mRegistered) {
            PackageMonitor packageMonitor = this.mPersonalPackageMonitor;
            if (packageMonitor != null) {
                packageMonitor.unregister();
            }
            PackageMonitor packageMonitor2 = this.mWorkPackageMonitor;
            if (packageMonitor2 != null) {
                packageMonitor2.unregister();
            }
            this.mRegistered = false;
        }
        if (this.mIsAiAssist) {
            int intExtra = targetInfo.getResolvedIntent().getIntExtra(MultiWindowManager.EXTRA_AI_LAUNCH_MODE, -1);
            if (intExtra == 3) {
                Rect rect = new Rect((Rect) targetInfo.getResolvedIntent().getParcelableExtra(MultiWindowManager.EXTRA_AI_HOT_KEY_LAUNCH_BOUNDS, Rect.class));
                if (!rect.isEmpty()) {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchBounds(rect);
                    bundle = makeBasic.toBundle();
                }
            } else if (intExtra == 2) {
                MultiWindowManager.getInstance().startAssistantActivityToSplit(targetInfo.getResolvedIntent(), targetInfo.getResolvedIntent().getFloatExtra(MultiWindowManager.EXTRA_AI_LAUNCH_SPLIT_RATIO, 0.5f));
                return;
            }
        }
        String str = this.mProfileSwitchMessage;
        if (str != null) {
            Toast.makeText(this, str, 1).show();
        }
        if (!this.mSafeForwardingMode) {
            if (targetInfo.startAsUser(this, bundle, userHandle)) {
                onActivityStarted(targetInfo);
                maybeLogCrossProfileTargetLaunch(targetInfo, userHandle);
                return;
            }
            return;
        }
        try {
            if (targetInfo.startAsCaller(this, bundle, userHandle.getIdentifier())) {
                onActivityStarted(targetInfo);
                maybeLogCrossProfileTargetLaunch(targetInfo, userHandle);
            }
        } catch (RuntimeException e) {
            Slog.wtf(TAG, "Unable to launch as uid " + this.mLaunchedFromUid + " package " + getLaunchedFromPackage() + ", while running in " + ActivityThread.currentProcessName(), e);
        }
    }

    private void maybeLogCrossProfileTargetLaunch(TargetInfo targetInfo, UserHandle userHandle) {
        if (!hasWorkProfile() || userHandle.equals(getUser())) {
            return;
        }
        DevicePolicyEventLogger.createEvent(155).setBoolean(userHandle.equals(getPersonalProfileUserHandle())).setStrings(getMetricsCategory(), targetInfo instanceof ChooserTargetInfo ? "direct_share" : "other_target").write();
    }

    public boolean shouldAutoLaunchSingleChoice(TargetInfo targetInfo) {
        return !targetInfo.isSuspended();
    }

    void showTargetDetails(ResolveInfo resolveInfo) {
        startActivityAsUser(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", resolveInfo.activityInfo.packageName, null)).addFlags(524288), this.mMultiProfilePagerAdapter.getCurrentUserHandle());
    }

    protected ResolverListAdapter createResolverListAdapter(Context context, List<Intent> list, Intent[] intentArr, List<ResolveInfo> list2, boolean z, UserHandle userHandle) {
        return new ResolverListAdapter(context, list, intentArr, list2, z, createListController(userHandle), this, getIntent().getBooleanExtra(EXTRA_IS_AUDIO_CAPTURE_DEVICE, false), (isLaunchedAsCloneProfile() && userHandle.equals(getPersonalProfileUserHandle())) ? getCloneProfileUserHandle() : userHandle);
    }

    private AbstractResolverComparator makeResolverComparator(UserHandle userHandle) {
        if (this.mHasSubclassSpecifiedResolutions) {
            return new NoOpResolverComparator(this, getTargetIntent(), getResolverRankerServiceUserHandleList(userHandle));
        }
        return new ResolverRankerServiceResolverComparator(this, getTargetIntent(), getReferrerPackageName(), (AbstractResolverComparator.AfterCompute) null, (ChooserActivityLogger) null, getResolverRankerServiceUserHandleList(userHandle));
    }

    protected ResolverListController createListController(UserHandle userHandle) {
        UserHandle queryIntentsUser = getQueryIntentsUser(userHandle);
        return new ResolverListController(this, this.mPm, getTargetIntent(), getReferrerPackageName(), this.mLaunchedFromUid, userHandle, makeResolverComparator(userHandle), queryIntentsUser);
    }

    private boolean configureContentView() {
        TextView textView;
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter() == null) {
            throw new IllegalStateException("mMultiProfilePagerAdapter.getCurrentListAdapter() cannot be null.");
        }
        Trace.beginSection("configureContentView");
        if (checkIfNeedFRPWorkaround()) {
            ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
            this.mMultiProfilePagerAdapter.showEmptyResolverListEmptyState(activeListAdapter);
            setContentView(getLayoutResource());
            this.mMultiProfilePagerAdapter.setupViewPager((ViewPager) findViewById(R.id.profile_pager));
            maybeCreateHeader(activeListAdapter);
            return false;
        }
        SemShareCommon semShareCommon = new SemShareCommon(getBaseContext(), this.mIntents.get(0), this.mIsDeviceDefault, this.mSupportsAlwaysUseOption, this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem(), this.mLaunchedFromUid, this.mExtraIntentList);
        this.mSemShareCommon = semShareCommon;
        if (semShareCommon.isFeatureSupported(SemShareConstants.SUPPORT_LOGGING)) {
            this.mSemShareLogging = new SemShareLogging(getBaseContext());
        }
        this.mSupportButtons = true;
        boolean z = this.mMultiProfilePagerAdapter.rebuildActiveTab(true) || this.mMultiProfilePagerAdapter.getActiveListAdapter().isTabLoaded();
        if (shouldShowTabs()) {
            z = z && (this.mMultiProfilePagerAdapter.rebuildInactiveTab(false) || this.mMultiProfilePagerAdapter.getInactiveListAdapter().isTabLoaded());
        }
        if (shouldUseMiniResolver()) {
            configureMiniResolverContent();
            Trace.endSection();
            return false;
        }
        this.mOldItemCount = this.mMultiProfilePagerAdapter.getActiveListAdapter().getPlaceholderCount();
        if (useLayoutWithDefault()) {
            this.mLayoutId = R.layout.resolver_list_with_default;
        } else {
            this.mLayoutId = getLayoutResource();
        }
        setContentView(this.mLayoutId);
        this.mMultiProfilePagerAdapter.setupViewPager((ViewPager) findViewById(R.id.profile_pager));
        boolean postRebuildList = postRebuildList(z);
        getWindow().setNavigationBarContrastEnforced(false);
        setVisibilityBlurEffect();
        if (this.mIsAiAssist) {
            View findViewById = findViewById(R.id.profile_pager);
            if (findViewById != null) {
                findViewById.setFocusable(false);
            }
            if (!this.mIsAltAiPressed && (textView = (TextView) findViewById(R.id.sem_resolver_ai_description)) != null) {
                if (KeyCharacterMap.deviceHasKey(1104) && !this.mIsAiFromKeyboardShortcut) {
                    String string = getResources().getString(R.string.resolver_sem_ai_switch_app_string);
                    String string2 = getResources().getString(R.string.resolver_sem_ai_key_name_string);
                    String format = String.format(string, string2);
                    int indexOf = format.indexOf(string2);
                    int length = string2.length() + indexOf;
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(format);
                    Drawable drawable = getResources().getDrawable(R.drawable.sem_ai_stars_icon);
                    drawable.setBounds(0, 0, 50, 50);
                    spannableStringBuilder.setSpan(new ImageSpan(drawable, 2), indexOf, length, 33);
                    textView.lambda$setTextAsync$0(spannableStringBuilder);
                } else {
                    textView.setVisibility(8);
                }
            }
        }
        Trace.endSection();
        return postRebuildList;
    }

    private void configureMiniResolverContent() {
        this.mLayoutId = R.layout.miniresolver;
        setContentView(R.layout.miniresolver);
        final DisplayResolveInfo displayResolveInfo = this.mMultiProfilePagerAdapter.getActiveListAdapter().mDisplayList.get(0);
        boolean z = getCurrentProfile() == 1;
        final ResolverListAdapter inactiveListAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter();
        final DisplayResolveInfo displayResolveInfo2 = inactiveListAdapter.mDisplayList.get(0);
        ImageView imageView = (ImageView) findViewById(16908294);
        Objects.requireNonNull(inactiveListAdapter);
        new ResolverListAdapter.LoadIconTask(inactiveListAdapter, displayResolveInfo2, displayResolveInfo2, imageView) { // from class: com.android.internal.app.ResolverActivity.6
            final /* synthetic */ ImageView val$icon;
            final /* synthetic */ DisplayResolveInfo val$otherProfileResolveInfo;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(displayResolveInfo2);
                this.val$otherProfileResolveInfo = displayResolveInfo2;
                this.val$icon = imageView;
                Objects.requireNonNull(inactiveListAdapter);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.app.ResolverListAdapter.LoadIconTask, android.os.AsyncTask
            public void onPostExecute(Drawable drawable) {
                if (ResolverActivity.this.isDestroyed()) {
                    return;
                }
                this.val$otherProfileResolveInfo.setDisplayIcon(drawable);
                new ResolverListAdapter.ViewHolder(this.val$icon).bindIcon(this.val$otherProfileResolveInfo);
            }
        }.execute(new Void[0]);
        final CharSequence displayLabel = displayResolveInfo2.getDisplayLabel();
        DevicePolicyResourcesManager resources = ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources();
        if (z) {
            ((TextView) findViewById(R.id.open_cross_profile)).lambda$setTextAsync$0(resources.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$configureMiniResolverContent$5;
                    lambda$configureMiniResolverContent$5 = ResolverActivity.this.lambda$configureMiniResolverContent$5(displayLabel);
                    return lambda$configureMiniResolverContent$5;
                }
            }, displayLabel));
            ((Button) findViewById(R.id.use_same_profile_browser)).lambda$setTextAsync$0(resources.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$configureMiniResolverContent$6;
                    lambda$configureMiniResolverContent$6 = ResolverActivity.this.lambda$configureMiniResolverContent$6();
                    return lambda$configureMiniResolverContent$6;
                }
            }));
        } else {
            ((TextView) findViewById(R.id.open_cross_profile)).lambda$setTextAsync$0(resources.getString(DevicePolicyResources.Strings.Core.MINIRESOLVER_OPEN_IN_WORK, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$configureMiniResolverContent$7;
                    lambda$configureMiniResolverContent$7 = ResolverActivity.this.lambda$configureMiniResolverContent$7(displayLabel);
                    return lambda$configureMiniResolverContent$7;
                }
            }, displayLabel));
            ((Button) findViewById(R.id.use_same_profile_browser)).lambda$setTextAsync$0(resources.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$configureMiniResolverContent$8;
                    lambda$configureMiniResolverContent$8 = ResolverActivity.this.lambda$configureMiniResolverContent$8();
                    return lambda$configureMiniResolverContent$8;
                }
            }));
        }
        findViewById(R.id.use_same_profile_browser).setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ResolverActivity.this.lambda$configureMiniResolverContent$9(displayResolveInfo, view);
            }
        });
        findViewById(R.id.button_open).setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ResolverActivity.this.lambda$configureMiniResolverContent$10(displayResolveInfo2, inactiveListAdapter, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$configureMiniResolverContent$5(CharSequence charSequence) {
        return getString(R.string.miniresolver_open_in_personal, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$configureMiniResolverContent$6() {
        return getString(R.string.miniresolver_use_work_browser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$configureMiniResolverContent$7(CharSequence charSequence) {
        return getString(R.string.miniresolver_open_in_work, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$configureMiniResolverContent$8() {
        return getString(R.string.miniresolver_use_personal_browser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$configureMiniResolverContent$9(DisplayResolveInfo displayResolveInfo, View view) {
        safelyStartActivity(displayResolveInfo);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$configureMiniResolverContent$10(DisplayResolveInfo displayResolveInfo, ResolverListAdapter resolverListAdapter, View view) {
        safelyStartActivityAsUser(displayResolveInfo, resolverListAdapter.mResolverListController.getUserHandle());
        finish();
    }

    private boolean shouldUseMiniResolver() {
        if (!this.mIsIntentPicker || this.mMultiProfilePagerAdapter.getActiveListAdapter() == null || this.mMultiProfilePagerAdapter.getInactiveListAdapter() == null) {
            return false;
        }
        List<DisplayResolveInfo> list = this.mMultiProfilePagerAdapter.getActiveListAdapter().mDisplayList;
        List<DisplayResolveInfo> list2 = this.mMultiProfilePagerAdapter.getInactiveListAdapter().mDisplayList;
        if (list.isEmpty()) {
            Log.d(TAG, "No targets in the current profile");
            return false;
        }
        if (list2.size() != 1) {
            Log.d(TAG, "Found " + list2.size() + " resolvers in the other profile");
            return false;
        }
        if (list2.get(0).getResolveInfo().handleAllWebDataURI) {
            Log.d(TAG, "Other profile is a web browser");
            return false;
        }
        Iterator<DisplayResolveInfo> it = list.iterator();
        while (it.hasNext()) {
            if (!it.next().getResolveInfo().handleAllWebDataURI) {
                Log.d(TAG, "Non-browser found in this profile");
                return false;
            }
        }
        return true;
    }

    protected boolean postRebuildList(boolean z) {
        return postRebuildListInternal(z);
    }

    final boolean postRebuildListInternal(boolean z) {
        if (z && maybeAutolaunchActivity()) {
            return true;
        }
        setupViewVisibilities();
        if (!shouldShowTabs()) {
            return false;
        }
        setupProfileTabs();
        return false;
    }

    private int isPermissionGranted(String str, int i) {
        return ActivityManager.checkComponentPermission(str, i, -1, true);
    }

    private boolean maybeAutolaunchActivity() {
        int itemCount = this.mMultiProfilePagerAdapter.getItemCount();
        if (itemCount == 1 && maybeAutolaunchIfSingleTarget()) {
            return true;
        }
        if (itemCount == 2 && this.mMultiProfilePagerAdapter.getActiveListAdapter().isTabLoaded() && this.mMultiProfilePagerAdapter.getInactiveListAdapter().isTabLoaded()) {
            return maybeAutolaunchIfNoAppsOnInactiveTab() || maybeAutolaunchIfCrossProfileSupported();
        }
        return false;
    }

    private boolean maybeAutolaunchIfSingleTarget() {
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredCount() != 1 || this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile() != null || this.mMultiProfilePagerAdapter.getActiveListAdapter().getDisplayResolveInfo(0).getSimilarList().size() > 1) {
            return false;
        }
        TargetInfo targetInfoForPosition = this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(0, false);
        if (!shouldAutoLaunchSingleChoice(targetInfoForPosition)) {
            return false;
        }
        safelyStartActivity(targetInfoForPosition);
        finish();
        return true;
    }

    private boolean maybeAutolaunchIfNoAppsOnInactiveTab() {
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredCount() != 1 || this.mMultiProfilePagerAdapter.getInactiveListAdapter().getUnfilteredCount() != 0) {
            return false;
        }
        safelyStartActivity(this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(0, false));
        finish();
        return true;
    }

    private boolean maybeAutolaunchIfCrossProfileSupported() {
        ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (activeListAdapter.getUnfilteredCount() != 1) {
            return false;
        }
        ResolverListAdapter inactiveListAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter();
        if (inactiveListAdapter.getUnfilteredCount() != 1) {
            return false;
        }
        TargetInfo targetInfoForPosition = activeListAdapter.targetInfoForPosition(0, false);
        if (!Objects.equals(targetInfoForPosition.getResolvedComponentName(), inactiveListAdapter.targetInfoForPosition(0, false).getResolvedComponentName()) || !shouldAutoLaunchSingleChoice(targetInfoForPosition) || !canAppInteractCrossProfiles(targetInfoForPosition.getResolvedComponentName().getPackageName())) {
            return false;
        }
        DevicePolicyEventLogger.createEvent(161).setBoolean(activeListAdapter.getUserHandle().equals(getPersonalProfileUserHandle())).setStrings(getMetricsCategory()).write();
        safelyStartActivity(targetInfoForPosition);
        finish();
        return true;
    }

    private boolean canAppInteractCrossProfiles(String str) {
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(str, 0);
            if (!applicationInfo.crossProfile) {
                return false;
            }
            int i = applicationInfo.uid;
            return isPermissionGranted(Manifest.permission.INTERACT_ACROSS_USERS_FULL, i) == 0 || isPermissionGranted(Manifest.permission.INTERACT_ACROSS_USERS, i) == 0 || PermissionChecker.checkPermissionForPreflight(this, Manifest.permission.INTERACT_ACROSS_PROFILES, -1, i, str) == 0;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "Package " + str + " does not exist on current user.");
            return false;
        }
    }

    private boolean isAutolaunching() {
        return !this.mRegistered && isFinishing();
    }

    private void setupProfileTabs() {
        maybeHideDivider();
        final TabHost tabHost = (TabHost) findViewById(R.id.profile_tabhost);
        tabHost.setup();
        final ViewPager viewPager = (ViewPager) findViewById(R.id.profile_pager);
        viewPager.setSaveEnabled(false);
        Button button = (Button) getLayoutInflater().inflate(R.layout.sem_resolver_profile_tab_button, (ViewGroup) tabHost.getTabWidget(), false);
        button.lambda$setTextAsync$0(getPersonalTabLabel());
        button.setContentDescription(getPersonalTabAccessibilityLabel());
        tabHost.addTab(tabHost.newTabSpec("personal").setContent(R.id.profile_pager).setIndicator(button));
        Button button2 = (Button) getLayoutInflater().inflate(R.layout.sem_resolver_profile_tab_button, (ViewGroup) tabHost.getTabWidget(), false);
        button2.lambda$setTextAsync$0(getWorkTabLabel());
        button2.setContentDescription(getWorkTabAccessibilityLabel());
        tabHost.addTab(tabHost.newTabSpec(TAB_TAG_WORK).setContent(R.id.profile_pager).setIndicator(button2));
        tabHost.getTabWidget().setVisibility(0);
        updateActiveTabStyle(tabHost);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda18
            @Override // android.widget.TabHost.OnTabChangeListener
            public final void onTabChanged(String str) {
                ResolverActivity.this.lambda$setupProfileTabs$11(tabHost, viewPager, str);
            }
        });
        viewPager.setVisibility(0);
        tabHost.setCurrentTab(this.mMultiProfilePagerAdapter.getCurrentPage());
        this.mMultiProfilePagerAdapter.setOnProfileSelectedListener(new AbstractMultiProfilePagerAdapter.OnProfileSelectedListener() { // from class: com.android.internal.app.ResolverActivity.7
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener
            public void onProfileSelected(int i) {
                tabHost.setCurrentTab(i);
                ResolverActivity.this.resetButtonBar();
                ResolverActivity.this.resetCheckedItem();
            }

            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener
            public void onProfilePageStateChanged(int i) {
                ResolverActivity.this.onHorizontalSwipeStateChanged(i);
            }
        });
        this.mOnSwitchOnWorkSelectedListener = new AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda19
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener
            public final void onSwitchOnWorkSelected() {
                ResolverActivity.lambda$setupProfileTabs$12(TabHost.this);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupProfileTabs$11(TabHost tabHost, ViewPager viewPager, String str) {
        updateActiveTabStyle(tabHost);
        if ("personal".equals(str)) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }
        setupViewVisibilities();
        maybeLogProfileChange();
        onProfileTabSelected();
        DevicePolicyEventLogger.createEvent(156).setInt(viewPager.getCurrentItem()).setStrings(getMetricsCategory()).write();
    }

    static /* synthetic */ void lambda$setupProfileTabs$12(TabHost tabHost) {
        View childAt = tabHost.getTabWidget().getChildAt(1);
        childAt.setFocusable(true);
        childAt.setFocusableInTouchMode(true);
        childAt.requestFocus();
    }

    private String getPersonalTabLabel() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_PERSONAL_TAB, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getPersonalTabLabel$13;
                lambda$getPersonalTabLabel$13 = ResolverActivity.this.lambda$getPersonalTabLabel$13();
                return lambda$getPersonalTabLabel$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getPersonalTabLabel$13() {
        return getString(R.string.resolver_personal_tab);
    }

    private String getWorkTabLabel() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getWorkTabLabel$14;
                lambda$getWorkTabLabel$14 = ResolverActivity.this.lambda$getWorkTabLabel$14();
                return lambda$getWorkTabLabel$14;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getWorkTabLabel$14() {
        return getString(R.string.resolver_work_tab);
    }

    private void maybeHideDivider() {
        View findViewById;
        if (this.mIsIntentPicker && (findViewById = findViewById(R.id.divider)) != null) {
            findViewById.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetCheckedItem() {
        if (this.mIsIntentPicker) {
            this.mLastSelected = -1;
            AbsListView absListView = (AbsListView) this.mMultiProfilePagerAdapter.getInactiveAdapterView();
            if (absListView.getCheckedItemCount() > 0) {
                absListView.setItemChecked(absListView.getCheckedItemPosition(), false);
            }
        }
    }

    private String getPersonalTabAccessibilityLabel() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_PERSONAL_TAB_ACCESSIBILITY, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getPersonalTabAccessibilityLabel$15;
                lambda$getPersonalTabAccessibilityLabel$15 = ResolverActivity.this.lambda$getPersonalTabAccessibilityLabel$15();
                return lambda$getPersonalTabAccessibilityLabel$15;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getPersonalTabAccessibilityLabel$15() {
        return getString(R.string.resolver_personal_tab_accessibility);
    }

    private String getWorkTabAccessibilityLabel() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB_ACCESSIBILITY, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda17
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getWorkTabAccessibilityLabel$16;
                lambda$getWorkTabAccessibilityLabel$16 = ResolverActivity.this.lambda$getWorkTabAccessibilityLabel$16();
                return lambda$getWorkTabAccessibilityLabel$16;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getWorkTabAccessibilityLabel$16() {
        return getString(R.string.resolver_work_tab_accessibility);
    }

    private static int getAttrColor(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    private void updateActiveTabStyle(TabHost tabHost) {
        int currentTab = tabHost.getCurrentTab();
        TextView textView = (TextView) tabHost.getTabWidget().getChildAt(currentTab);
        TextView textView2 = (TextView) tabHost.getTabWidget().getChildAt(1 - currentTab);
        textView.setSelected(true);
        textView2.setSelected(false);
    }

    private void setupViewVisibilities() {
        ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(activeListAdapter)) {
            return;
        }
        addUseDifferentAppLabelIfNecessary(activeListAdapter);
    }

    private void setupAdapterListView(ListView listView, ItemClickListener itemClickListener) {
        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemClickListener);
        if (this.mSupportsAlwaysUseOption) {
            listView.setChoiceMode(1);
        }
    }

    private void maybeCreateHeader(ResolverListAdapter resolverListAdapter) {
        TextView textView;
        if (this.mHeaderCreatorUser == null || resolverListAdapter.getUserHandle().equals(this.mHeaderCreatorUser)) {
            if (!shouldShowTabs() && resolverListAdapter.getCount() == 0 && resolverListAdapter.getPlaceholderCount() == 0 && (textView = (TextView) findViewById(16908310)) != null) {
                textView.setVisibility(8);
            }
            if (this.mForceTitleHide) {
                TextView textView2 = (TextView) findViewById(16908310);
                if (textView2 != null) {
                    textView2.setVisibility(8);
                    return;
                }
                return;
            }
            CharSequence charSequence = this.mTitle;
            if (charSequence == null) {
                charSequence = getTitleForAction(getTargetIntent(), this.mDefaultTitleResId);
            }
            if (!TextUtils.isEmpty(charSequence)) {
                TextView textView3 = (TextView) findViewById(16908310);
                if (textView3 != null) {
                    textView3.lambda$setTextAsync$0(charSequence);
                }
                setTitle(charSequence);
                semSetTextSizeByMaxFontScale(textView3, R.dimen.sem_resolver_pagemode_titlepanel_text_size);
            }
            this.mHeaderCreatorUser = resolverListAdapter.getUserHandle();
        }
    }

    protected void resetButtonBar() {
        if (this.mSupportsAlwaysUseOption && this.mSupportButtons) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.button_bar);
            if (viewGroup == null) {
                Log.e(TAG, "Layout unexpectedly does not have a button bar");
                return;
            }
            ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
            View findViewById = findViewById(R.id.resolver_button_bar_divider);
            if (!useLayoutWithDefault()) {
                Insets insets = this.mSystemWindowInsets;
                if (insets != null) {
                    int i = insets.bottom;
                }
                viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), getResources().getDimensionPixelSize(R.dimen.resolver_button_bar_spacing));
            }
            if (activeListAdapter.isTabLoaded() && this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(activeListAdapter) && !useLayoutWithDefault()) {
                viewGroup.setVisibility(4);
                if (findViewById != null) {
                    findViewById.setVisibility(4);
                }
                setButtonBarIgnoreOffset(false);
                return;
            }
            if (findViewById != null) {
                findViewById.setVisibility(0);
            }
            viewGroup.setVisibility(0);
            setButtonBarIgnoreOffset(true);
            this.mOnceButton = (Button) viewGroup.findViewById(R.id.button_once);
            Button button = (Button) viewGroup.findViewById(R.id.button_always);
            this.mAlwaysButton = button;
            Button button2 = this.mOnceButton;
            if (button2 != null && button != null) {
                button2.setContentDescription(getString(R.string.sem_resolver_button_just_once_accessibility));
                this.mAlwaysButton.setContentDescription(getString(R.string.sem_resolver_button_always_accessibility));
            }
            resetAlwaysOrOnceButtonBar();
        }
    }

    private void setButtonBarIgnoreOffset(boolean z) {
        View findViewById = findViewById(R.id.button_bar_container);
        if (findViewById != null) {
            ResolverDrawerLayout.LayoutParams layoutParams = (ResolverDrawerLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.ignoreOffset = z;
            findViewById.setLayoutParams(layoutParams);
        }
    }

    private void resetAlwaysOrOnceButtonBar() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sem_dialog_button_text_size);
        float f = dimensionPixelSize;
        this.mAlwaysButton.setTextSize(0, f);
        semCheckMaxFontScale(this.mAlwaysButton, dimensionPixelSize);
        this.mOnceButton.setTextSize(0, f);
        semCheckMaxFontScale(this.mOnceButton, dimensionPixelSize);
        SemShareCommon semShareCommon = this.mSemShareCommon;
        if (semShareCommon != null && semShareCommon.isFeatureSupported(SemShareConstants.SUPPORT_SHOW_BUTTON_SHAPES)) {
            this.mAlwaysButton.semSetButtonShapeEnabled(true);
            this.mOnceButton.semSetButtonShapeEnabled(true);
        }
        setAlwaysButtonEnabled(false, -1, false);
        this.mOnceButton.setEnabled(false);
        int filteredPosition = this.mMultiProfilePagerAdapter.getActiveListAdapter().getFilteredPosition();
        if (useLayoutWithDefault() && filteredPosition != -1 && this.mSupportButtons) {
            setAlwaysButtonEnabled(true, filteredPosition, false);
            this.mOnceButton.setEnabled(true);
            this.mOnceButton.requestFocus();
            return;
        }
        AbsListView absListView = (AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
        if (absListView == null || !this.mSupportButtons || absListView.getCheckedItemPosition() == -1) {
            return;
        }
        setAlwaysButtonEnabled(true, absListView.getCheckedItemPosition(), true);
        this.mOnceButton.setEnabled(true);
    }

    protected void setRetainInOnStop(boolean z) {
        this.mRetainInOnStop = z;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean resolveInfoMatch(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
        return resolveInfo == null ? resolveInfo2 == null : resolveInfo.activityInfo == null ? resolveInfo2.activityInfo == null : Objects.equals(resolveInfo.activityInfo.name, resolveInfo2.activityInfo.name) && Objects.equals(resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName) && Objects.equals(resolveInfo.userHandle, resolveInfo2.userHandle);
    }

    protected String getMetricsCategory() {
        return METRICS_CATEGORY_RESOLVER;
    }

    public void onHandlePackagesChanged(ResolverListAdapter resolverListAdapter) {
        if (resolverListAdapter == this.mMultiProfilePagerAdapter.getActiveListAdapter()) {
            if (resolverListAdapter.getUserHandle().equals(getWorkProfileUserHandle()) && this.mQuietModeManager.isWaitingToEnableWorkProfile()) {
                return;
            }
            this.mAppIconTheme = Settings.System.getString(getContentResolver(), "current_sec_appicon_theme_package");
            this.mOldItemCount = this.mMultiProfilePagerAdapter.getActiveListAdapter().getPlaceholderCount();
            if (this.mMultiProfilePagerAdapter.rebuildActiveTab(true)) {
                ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
                activeListAdapter.notifyDataSetChanged();
                if (activeListAdapter.getCount() == 0 && !inactiveListAdapterHasItems()) {
                    finish();
                }
                ViewGroup activeAdapterView = this.mMultiProfilePagerAdapter.getActiveAdapterView();
                if (activeAdapterView instanceof GridView) {
                    ((GridView) activeAdapterView).setNumColumns(Math.min(activeListAdapter.getCount(), this.mMaxColumns));
                    return;
                }
                return;
            }
            return;
        }
        this.mMultiProfilePagerAdapter.clearInactiveProfileCache();
    }

    private boolean inactiveListAdapterHasItems() {
        return shouldShowTabs() && this.mMultiProfilePagerAdapter.getInactiveListAdapter().getCount() > 0;
    }

    private BroadcastReceiver createWorkProfileStateReceiver() {
        return new BroadcastReceiver() { // from class: com.android.internal.app.ResolverActivity.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ((TextUtils.equals(action, Intent.ACTION_USER_UNLOCKED) || TextUtils.equals(action, Intent.ACTION_MANAGED_PROFILE_UNAVAILABLE) || TextUtils.equals(action, Intent.ACTION_MANAGED_PROFILE_AVAILABLE)) && intent.getIntExtra("android.intent.extra.user_handle", -1) == ResolverActivity.this.getWorkProfileUserHandle().getIdentifier()) {
                    if (ResolverActivity.this.isWorkProfileEnabled()) {
                        if (ResolverActivity.this.mWorkProfileHasBeenEnabled) {
                            return;
                        }
                        ResolverActivity.this.mWorkProfileHasBeenEnabled = true;
                        ResolverActivity.this.mQuietModeManager.markWorkProfileEnabledBroadcastReceived();
                    } else {
                        ResolverActivity.this.mWorkProfileHasBeenEnabled = false;
                    }
                    if (ResolverActivity.this.mMultiProfilePagerAdapter.getCurrentUserHandle().equals(ResolverActivity.this.getWorkProfileUserHandle())) {
                        ResolverActivity.this.mMultiProfilePagerAdapter.rebuildActiveTab(true);
                    } else {
                        ResolverActivity.this.mMultiProfilePagerAdapter.clearInactiveProfileCache();
                    }
                }
            }
        };
    }

    public static final class ResolvedComponentInfo {
        private boolean mFixedAtTop;
        private boolean mPinned;
        public final ComponentName name;
        private final List<Intent> mIntents = new ArrayList();
        private final List<ResolveInfo> mResolveInfos = new ArrayList();
        private ArrayList<ResolvedComponentInfo> mSimilarList = new ArrayList<>();

        public ResolvedComponentInfo(ComponentName componentName, Intent intent, ResolveInfo resolveInfo) {
            this.name = componentName;
            add(intent, resolveInfo);
        }

        public void add(Intent intent, ResolveInfo resolveInfo) {
            this.mIntents.add(intent);
            this.mResolveInfos.add(resolveInfo);
        }

        public int getCount() {
            return this.mIntents.size();
        }

        public Intent getIntentAt(int i) {
            if (i >= 0) {
                return this.mIntents.get(i);
            }
            return null;
        }

        public ResolveInfo getResolveInfoAt(int i) {
            if (i >= 0) {
                return this.mResolveInfos.get(i);
            }
            return null;
        }

        public int findIntent(Intent intent) {
            int size = this.mIntents.size();
            for (int i = 0; i < size; i++) {
                if (intent.equals(this.mIntents.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public int findResolveInfo(ResolveInfo resolveInfo) {
            int size = this.mResolveInfos.size();
            for (int i = 0; i < size; i++) {
                if (resolveInfo.equals(this.mResolveInfos.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public boolean isPinned() {
            return this.mPinned;
        }

        public void setPinned(boolean z) {
            this.mPinned = z;
        }

        public boolean isFixedAtTop() {
            return this.mFixedAtTop;
        }

        public void setFixedAtTop(boolean z) {
            this.mFixedAtTop = z;
        }

        public ArrayList<ResolvedComponentInfo> getSimilarList() {
            return this.mSimilarList;
        }
    }

    class ItemClickListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        ItemClickListener() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (i >= 0 && ResolverActivity.this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(i, true) != null) {
                DisplayResolveInfo displayResolveInfo = ResolverActivity.this.mMultiProfilePagerAdapter.getActiveListAdapter().getDisplayResolveInfo(i);
                ViewPager viewPager = (ViewPager) ResolverActivity.this.findViewById(R.id.profile_pager);
                boolean z = false;
                if (displayResolveInfo != null && displayResolveInfo.getSimilarList().size() > 1) {
                    ResolverActivity.this.mSecondDepth = true;
                    if (ResolverActivity.this.mAlwaysButton != null) {
                        ResolverActivity.this.mOnceButton.setEnabled(true);
                        ResolverActivity.this.mAlwaysButton.setEnabled(true);
                    }
                    viewPager.findViewById(R.id.resolver_list).setVisibility(8);
                    viewPager.findViewById(R.id.sem_resolver_second_depth_recycler_view).setVisibility(0);
                    TextView textView = (TextView) ResolverActivity.this.findViewById(16908310);
                    if (textView != null) {
                        textView.setText(R.string.sem_resolver_choose_what_to_do);
                    }
                    ResolverActivity resolverActivity = ResolverActivity.this;
                    resolverActivity.mSelectTaskRecyclerView = (RecyclerView) resolverActivity.findViewById(R.id.sem_resolver_second_depth_recycler_view);
                    ResolverActivity.this.mSemSelectTaskListAdapter = new SemSelectTaskListAdapter(displayResolveInfo.getSimilarList(), ResolverActivity.this.mMultiProfilePagerAdapter, new ResolverActivity$$ExternalSyntheticLambda0(ResolverActivity.this));
                    ResolverActivity.this.mSelectTaskRecyclerView.setLayoutManager(new LinearLayoutManager(ResolverActivity.this.mContext));
                    ResolverActivity.this.mSelectTaskRecyclerView.setAdapter(ResolverActivity.this.mSemSelectTaskListAdapter);
                    return;
                }
                viewPager.findViewById(R.id.resolver_list).setVisibility(0);
                AbsListView absListView = (AbsListView) ResolverActivity.this.mMultiProfilePagerAdapter.getActiveAdapterView();
                int checkedItemPosition = absListView.getCheckedItemPosition();
                boolean z2 = checkedItemPosition != -1;
                if (!ResolverActivity.this.useLayoutWithDefault() && ResolverActivity.this.mSupportButtons && ((!z2 || ResolverActivity.this.mLastSelected != checkedItemPosition) && ResolverActivity.this.mAlwaysButton != null)) {
                    ResolverActivity.this.setAlwaysButtonEnabled(z2, checkedItemPosition, true);
                    ResolverActivity.this.mOnceButton.setEnabled(z2);
                    if (z2) {
                        absListView.smoothScrollToPosition(checkedItemPosition);
                        ResolverActivity.this.mOnceButton.requestFocus();
                    }
                    ResolverActivity.this.mLastSelected = checkedItemPosition;
                    SemA11yEvent.sendA11yEvent(ResolverActivity.this.getApplicationContext(), ResolverActivity.this.getPackageName(), ResolverActivity.this.getString(R.string.checked));
                    return;
                }
                ResolverActivity resolverActivity2 = ResolverActivity.this;
                if (resolverActivity2.mIsAltAiPressed || (ResolverActivity.this.mSupportsAlwaysUseOption && !ResolverActivity.this.mSupportButtons)) {
                    z = true;
                }
                resolverActivity2.startSelected(i, z, true);
            }
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (i < 0) {
                return false;
            }
            ResolverActivity.this.showTargetDetails(ResolverActivity.this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(i, true));
            return true;
        }
    }

    private boolean getEnterprisePolicyEnabled(Context context, String str, String str2, String[] strArr) {
        Cursor query = context.getContentResolver().query(Uri.parse(str), null, str2, strArr, null);
        if (query == null) {
            return true;
        }
        try {
            try {
                query.moveToFirst();
                boolean equals = query.getString(query.getColumnIndex(str2)).equals("true");
                query.close();
                return equals;
            } catch (Exception e) {
                Log.e(TAG, "Exception at getEnterprisePolicyEnabled ", e);
                query.close();
                return true;
            }
        } catch (Throwable th) {
            query.close();
            throw th;
        }
    }

    static class PickTargetOptionRequest extends VoiceInteractor.PickOptionRequest {
        public PickTargetOptionRequest(VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] optionArr, Bundle bundle) {
            super(prompt, optionArr, bundle);
        }

        @Override // android.app.VoiceInteractor.Request
        public void onCancel() {
            super.onCancel();
            ResolverActivity resolverActivity = (ResolverActivity) getActivity();
            if (resolverActivity != null) {
                resolverActivity.mPickOptionRequest = null;
                resolverActivity.finish();
            }
        }

        @Override // android.app.VoiceInteractor.PickOptionRequest
        public void onPickOptionResult(boolean z, VoiceInteractor.PickOptionRequest.Option[] optionArr, Bundle bundle) {
            ResolverActivity resolverActivity;
            super.onPickOptionResult(z, optionArr, bundle);
            if (optionArr.length == 1 && (resolverActivity = (ResolverActivity) getActivity()) != null && resolverActivity.onTargetSelected(resolverActivity.mMultiProfilePagerAdapter.getActiveListAdapter().getItem(optionArr[0].getIndex()), false)) {
                resolverActivity.mPickOptionRequest = null;
                resolverActivity.finish();
            }
        }
    }

    protected final UserHandle getQueryIntentsUser(UserHandle userHandle) {
        return (isLaunchedAsCloneProfile() && userHandle.equals(getPersonalProfileUserHandle())) ? getCloneProfileUserHandle() : userHandle;
    }

    public final List<UserHandle> getResolverRankerServiceUserHandleList(UserHandle userHandle) {
        return getResolverRankerServiceUserHandleListInternal(userHandle);
    }

    protected List<UserHandle> getResolverRankerServiceUserHandleListInternal(UserHandle userHandle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(userHandle);
        if (userHandle.equals(getPersonalProfileUserHandle()) && getCloneProfileUserHandle() != null) {
            arrayList.add(getCloneProfileUserHandle());
        }
        return arrayList;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean semIsDestroyed() {
        return isDestroyed();
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean semIsFinishing() {
        return isFinishing();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.mIsAiAssist) {
            try {
                IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
                this.mWindowManager = asInterface;
                if (asInterface != null) {
                    asInterface.registerSystemKeyEvent(1104, getComponentName(), 3);
                    Log.i(TAG, "[AI Key] registerSystemKeyEvent : " + getComponentName());
                }
            } catch (RemoteException | IllegalArgumentException | SecurityException e) {
                Log.e(TAG, "[AI Key] failed to registerSystemKeyEvent : " + e.getCause() + ", " + e.getMessage());
            }
        }
    }

    @Override // android.app.Activity
    public void finish() {
        if (this.mExitAnimator != null) {
            return;
        }
        super.finish();
        semTransitionOverride(this, 1);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        TextView textView;
        if (this.mSecondDepth) {
            this.mSecondDepth = false;
            ViewPager viewPager = (ViewPager) findViewById(R.id.profile_pager);
            viewPager.findViewById(R.id.sem_resolver_second_depth_recycler_view).setVisibility(8);
            viewPager.findViewById(R.id.resolver_list).setVisibility(0);
            CharSequence charSequence = this.mTitle;
            if (charSequence == null) {
                charSequence = getTitleForAction(getTargetIntent(), this.mDefaultTitleResId);
            }
            if (TextUtils.isEmpty(charSequence) || (textView = (TextView) findViewById(16908310)) == null) {
                return;
            }
            textView.lambda$setTextAsync$0(charSequence);
            return;
        }
        semFinishAfterAnimation();
    }

    @Override // android.app.Activity
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        this.mMultiProfilePagerAdapter.getActiveListAdapter().semForceHandlePackagesChanged();
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void semOnForceHandlePackagesChanged(ResolverListAdapter resolverListAdapter) {
        if (resolverListAdapter == this.mMultiProfilePagerAdapter.getActiveListAdapter()) {
            if (this.mMultiProfilePagerAdapter.rebuildActiveTab(true)) {
                this.mMultiProfilePagerAdapter.getActiveListAdapter().notifyDataSetChanged();
                ViewGroup activeAdapterView = this.mMultiProfilePagerAdapter.getActiveAdapterView();
                if (activeAdapterView instanceof GridView) {
                    int count = resolverListAdapter.getCount();
                    int integer = getResources().getInteger(R.integer.config_maxResolverActivityColumns);
                    this.mMaxColumns = integer;
                    ((GridView) activeAdapterView).setNumColumns(Math.min(count, integer));
                    return;
                }
                return;
            }
            return;
        }
        this.mMultiProfilePagerAdapter.clearInactiveProfileCache();
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean semNeedSortAfterPinned() {
        return this.mNeedUpdateAfterPinned;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void semSetNeedSortAfterPinned(boolean z) {
        this.mNeedUpdateAfterPinned = z;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean semIsSupportsAlwaysUseOption() {
        return this.mSupportsAlwaysUseOption;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public int semGetOldItemCount() {
        return this.mOldItemCount;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public String semGetAppIconTheme() {
        return this.mAppIconTheme;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void semSetNeedSortingInRebuildList(boolean z) {
        this.mMultiProfilePagerAdapter.semSetNeedSortingInRebuildList(z);
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean semIsNeedSortingInRebuildList() {
        return this.mMultiProfilePagerAdapter.semIsNeedSortingInRebuildList();
    }

    private void semCheckMaxFontScale(TextView textView, int i) {
        float f = getResources().getConfiguration().fontScale;
        if (f > 1.2f) {
            textView.setTextSize(0, (i / f) * 1.2f);
        }
    }

    void semSetTextSizeByMaxFontScale(TextView textView, int i) {
        if (textView != null) {
            textView.setTextSize(0, getResources().getDimensionPixelSize(i) * getFontScale());
        }
    }

    float getFontScale() {
        float f = getResources().getConfiguration().fontScale;
        if (f > 1.2f) {
            return 1.2f;
        }
        return f;
    }

    private void semTransitionOverride(Context context, int i) {
        if (this.mIsPopOver) {
            return;
        }
        if (isInMultiWindowMode() || this.mIsDeskTopMode) {
            overridePendingTransition(R.anim.sem_resolver_fade_in, R.anim.sem_resolver_fade_out);
        } else if (i == 0) {
            overridePendingTransition(R.anim.sem_resolver_panel_enter, 0);
        } else {
            overridePendingTransition(R.anim.sem_resolver_finish_panel_enter, R.anim.sem_resolver_exit);
        }
    }

    private void semSafelyStartActivtyAfterAnimation(final TargetInfo targetInfo) {
        View findViewById = findViewById(R.id.contentPanel);
        FrameLayout frameLayout = this.mMultiParent;
        if (frameLayout != null && this.mGalleryRecyclerView != null) {
            findViewById = frameLayout;
        } else if (findViewById == null || isInMultiWindowMode() || this.mIsDeskTopMode || this.mIsPopOver) {
            safelyStartActivity(targetInfo);
            return;
        }
        Animator createExitAnimation = createExitAnimation(findViewById);
        this.mExitAnimator = createExitAnimation;
        createExitAnimation.addListener(new Animator.AnimatorListener() { // from class: com.android.internal.app.ResolverActivity.9
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ResolverActivity.this.mExitAnimator = null;
                ResolverActivity.this.safelyStartActivity(targetInfo);
                ResolverActivity.this.finish();
            }
        });
        this.mExitAnimator.start();
    }

    private Animator createExitAnimation(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", 0.0f, getResources().getDisplayMetrics().heightPixels);
        ofFloat.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f));
        ofFloat.setDuration(330L);
        ofFloat.start();
        return ofFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semFinishAfterAnimation() {
        View findViewById = findViewById(R.id.contentPanel);
        FrameLayout frameLayout = this.mMultiParent;
        if (frameLayout != null && this.mGalleryRecyclerView != null) {
            findViewById = frameLayout;
        } else if (findViewById == null || isInMultiWindowMode() || this.mIsDeskTopMode || this.mIsPopOver) {
            finish();
            return;
        }
        Animator animator = this.mExitAnimator;
        if (animator == null || !animator.isStarted()) {
            Animator createExitAnimation = createExitAnimation(findViewById);
            this.mExitAnimator = createExitAnimation;
            createExitAnimation.addListener(new Animator.AnimatorListener() { // from class: com.android.internal.app.ResolverActivity.10
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator2) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator2) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    ResolverActivity.this.mExitAnimator = null;
                    ResolverActivity.this.finish();
                }
            });
            this.mExitAnimator.start();
        }
    }

    private boolean checkIfNeedFRPWorkaround() {
        boolean z = Settings.Secure.getInt(getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 1) != 0;
        ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (z || this.mSupportsAlwaysUseOption || activeListAdapter.hasFilteredItem()) {
            return false;
        }
        Log.i(TAG, "Blocked for security reason!! Setup is not completed!!");
        return true;
    }

    private void semSetupAdapterListView(AbsListView absListView, ItemClickListener itemClickListener) {
        absListView.setOnItemClickListener(itemClickListener);
        absListView.setOnItemLongClickListener(itemClickListener);
        if (absListView instanceof GridView) {
            ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
            int count = activeListAdapter.getCount();
            Log.d(TAG, "ActiveProfile : " + this.mMultiProfilePagerAdapter.getActiveListAdapter().toString() + "itemCount : " + count);
            ((GridView) absListView).setNumColumns(Math.min(count, this.mMaxColumns));
            for (int i = 0; i < activeListAdapter.getCount(); i++) {
                Log.d(TAG, activeListAdapter.getItem(i).getResolvedComponentName().toString());
            }
        }
        if (this.mSupportsAlwaysUseOption) {
            absListView.setChoiceMode(1);
        }
    }

    private void semCreateAndShowTipsPopup(View view) {
        SemTipPopup semTipPopup = new SemTipPopup(view);
        this.mTipsDescriptionPopup = semTipPopup;
        semTipPopup.setExpanded(true);
        this.mTipsDescriptionPopup.setMessage(getString(R.string.sem_resolver_go_to_settings_tips_description));
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        this.mTipsDescriptionPopup.setTargetPosition(iArr[0] + (this.mTipsIcon.getWidth() / 2), iArr[1] + this.mTipsIcon.getHeight() + getResources().getDimensionPixelSize(R.dimen.sem_resolver_tips_popup_yoffset));
        this.mTipsDescriptionPopup.show(view.getLayoutDirection() != 0 ? 3 : 2);
    }

    private boolean hasCutout(Display display) {
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (displayInfo.displayCutout != null) {
            return (displayInfo.displayCutout.getBoundingRectTop().isEmpty() && displayInfo.displayCutout.getBoundingRectBottom().isEmpty()) ? false : true;
        }
        return false;
    }

    private boolean privateSpaceEnabled() {
        return this.mIsIntentPicker && com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.allowResolverSheetForPrivateSpace() && android.multiuser.Flags.enablePrivateSpaceFeatures();
    }

    public static class AppListAccessibilityDelegate extends View.AccessibilityDelegate {
        private final View mBottomBar;
        private final ResolverDrawerLayout mDrawer;
        private final Rect mRect = new Rect();

        public AppListAccessibilityDelegate(ResolverDrawerLayout resolverDrawerLayout) {
            this.mDrawer = resolverDrawerLayout;
            this.mBottomBar = resolverDrawerLayout.findViewById(R.id.button_bar_container);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            boolean onRequestSendAccessibilityEvent = super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            if (onRequestSendAccessibilityEvent && accessibilityEvent.getEventType() == 32768 && this.mDrawer.isCollapsed()) {
                view.getBoundsOnScreen(this.mRect);
                int i = this.mRect.top;
                int i2 = this.mRect.bottom;
                this.mDrawer.getBoundsOnScreen(this.mRect, true);
                View view2 = this.mBottomBar;
                int height = view2 == null ? 0 : view2.getHeight();
                int i3 = this.mRect.top;
                int i4 = this.mRect.bottom - height;
                if (i3 > i || i2 > i4) {
                    this.mDrawer.setCollapsed(false);
                }
            }
            return onRequestSendAccessibilityEvent;
        }
    }

    private void setVisibilityBlurEffect() {
        Context context = this.mContext;
        if (context != null) {
            boolean z = Settings.System.getInt(context.getContentResolver(), "accessibility_reduce_transparency", 0) == 1;
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_popup_menu_corner_radius);
            int color = this.mContext.getResources().getColor(R.color.sem_resolver_bg_color);
            View findViewById = findViewById(R.id.sem_resolver_header);
            View findViewById2 = findViewById(R.id.profile_tabhost);
            View findViewById3 = findViewById(R.id.button_bar_container);
            FrameLayout frameLayout = (FrameLayout) findViewById(16908305);
            if (findViewById2 != null) {
                findViewById2.setFocusable(false);
            }
            frameLayout.setFocusable(false);
            if (this.mIsAltAiPressed) {
                ViewGroup activeAdapterView = this.mMultiProfilePagerAdapter.getActiveAdapterView();
                if (activeAdapterView != null) {
                    activeAdapterView.setFocusable(false);
                }
                if (findViewById != null) {
                    findViewById.setVisibility(8);
                }
                findViewById3.setVisibility(8);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(color);
                float f = dimensionPixelSize;
                gradientDrawable.setCornerRadius(f);
                if (findViewById2 != null) {
                    findViewById2.setBackgroundDrawable(gradientDrawable);
                }
                if (findViewById2 == null || z) {
                    return;
                }
                findViewById2.semSetBlurInfo(createBlurInfoBuilder().setBackgroundCornerRadius(f, f, f, f).build());
                return;
            }
            GradientDrawable gradientDrawable2 = new GradientDrawable();
            gradientDrawable2.setColor(color);
            float f2 = dimensionPixelSize;
            gradientDrawable2.setCornerRadii(new float[]{f2, f2, f2, f2, 0.0f, 0.0f, 0.0f, 0.0f});
            if (findViewById != null) {
                findViewById.setBackgroundDrawable(gradientDrawable2);
            }
            GradientDrawable gradientDrawable3 = new GradientDrawable();
            gradientDrawable3.setColor(color);
            gradientDrawable3.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, f2, f2, f2, f2});
            if (findViewById3 != null) {
                findViewById3.setBackgroundDrawable(gradientDrawable3);
            }
            if (!z) {
                if (findViewById != null) {
                    findViewById.semSetBlurInfo(createBlurInfoBuilder().setBackgroundCornerRadius(f2, f2, 0.0f, 0.0f).build());
                }
                if (findViewById2 != null) {
                    findViewById2.semSetBlurInfo(createBlurInfoBuilder().build());
                }
                if (findViewById3 != null) {
                    findViewById3.semSetBlurInfo(createBlurInfoBuilder().setBackgroundCornerRadius(0.0f, 0.0f, f2, f2).build());
                }
            }
            if (!this.mSupportsAlwaysUseOption || (findViewById3 == null && findViewById2 != null)) {
                if (findViewById3 != null) {
                    findViewById3.setVisibility(8);
                }
                GradientDrawable gradientDrawable4 = new GradientDrawable();
                gradientDrawable4.setColor(color);
                gradientDrawable4.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, f2, f2, f2, f2});
                if (findViewById2 != null) {
                    findViewById2.setBackgroundDrawable(gradientDrawable4);
                }
                if (findViewById2 == null || z) {
                    return;
                }
                findViewById2.semSetBlurInfo(createBlurInfoBuilder().setBackgroundCornerRadius(0.0f, 0.0f, f2, f2).build());
            }
        }
    }

    private SemBlurInfo.Builder createBlurInfoBuilder() {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue, true);
        return new SemBlurInfo.Builder(0).setRadius(120).setBackgroundColor(this.mContext.getResources().getColor(typedValue.data != 0 ? R.color.sem_popup_menu_blur_background_dark : R.color.sem_popup_menu_blur_background, this.mContext.getTheme()));
    }
}
