package com.android.internal.app;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.chooser.Flags;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
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
import android.widget.TabWidget;
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
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LinearLayoutManager;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.ResolverDrawerLayout;
import com.android.internal.widget.ViewPager;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.share.SemShareCommon;
import com.samsung.android.share.SemShareConstants;
import com.samsung.android.share.SemShareLogging;
import com.samsung.android.widget.SemTipPopup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class ResolverActivity extends Activity implements ResolverListAdapter.ResolverListCommunicator {
    private static final boolean DEBUG = false;
    public static boolean ENABLE_TABBED_VIEW = true;
    static final String EXTRA_CALLING_USER = "com.android.internal.app.ResolverActivity.EXTRA_CALLING_USER";
    private static final String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
    public static final String EXTRA_IS_AUDIO_CAPTURE_DEVICE = "is_audio_capture_device";
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
    protected boolean mIsDeskTopMode;
    protected boolean mIsDeviceDefault;
    private final boolean mIsIntentPicker;
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
    private PackageMonitor mWorkPackageMonitor;
    private boolean mWorkProfileHasBeenEnabled;
    private BroadcastReceiver mWorkProfileStateReceiver;
    private UserHandle mWorkProfileUserHandle;

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
        this.mLatencyTracker = getLatencyTracker();
        this.mIsIntentPicker = getClass().equals(ResolverActivity.class);
    }

    protected ResolverActivity(boolean isIntentPicker) {
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
        this.mLatencyTracker = getLatencyTracker();
        this.mIsIntentPicker = isIntentPicker;
    }

    private LatencyTracker getLatencyTracker() {
        return LatencyTracker.getInstance(this);
    }

    public static int getLabelRes(String action) {
        return ActionTitle.forAction(action).labelRes;
    }

    private enum ActionTitle {
        VIEW("android.intent.action.VIEW", R.string.whichViewApplication, R.string.whichViewApplicationNamed, R.string.whichViewApplicationLabel),
        EDIT(Intent.ACTION_EDIT, R.string.whichEditApplication, R.string.whichEditApplicationNamed, R.string.whichEditApplicationLabel),
        SEND(Intent.ACTION_SEND, R.string.whichSendApplication, R.string.whichSendApplicationNamed, R.string.whichSendApplicationLabel),
        SENDTO(Intent.ACTION_SENDTO, R.string.whichSendToApplication, R.string.whichSendToApplicationNamed, R.string.whichSendToApplicationLabel),
        SEND_MULTIPLE(Intent.ACTION_SEND_MULTIPLE, R.string.whichSendApplication, R.string.whichSendApplicationNamed, R.string.whichSendApplicationLabel),
        CAPTURE_IMAGE("android.media.action.IMAGE_CAPTURE", R.string.whichImageCaptureApplication, R.string.whichImageCaptureApplicationNamed, R.string.whichImageCaptureApplicationLabel),
        DEFAULT(null, R.string.whichApplication, R.string.whichApplicationNamed, R.string.whichApplicationLabel),
        HOME(Intent.ACTION_MAIN, R.string.whichHomeApplication, R.string.whichHomeApplicationNamed, R.string.whichHomeApplicationLabel);

        public static final int BROWSABLE_APP_TITLE_RES = 17043531;
        public static final int BROWSABLE_HOST_APP_TITLE_RES = 17043529;
        public static final int BROWSABLE_HOST_TITLE_RES = 17043528;
        public static final int BROWSABLE_TITLE_RES = 17043530;
        public final String action;
        public final int labelRes;
        public final int namedTitleRes;
        public final int titleRes;

        ActionTitle(String action, int titleRes, int namedTitleRes, int labelRes) {
            this.action = action;
            this.titleRes = titleRes;
            this.namedTitleRes = namedTitleRes;
            this.labelRes = labelRes;
        }

        public static ActionTitle forAction(String action) {
            for (ActionTitle title : values()) {
                if (title != HOME && action != null && action.equals(title.action)) {
                    return title;
                }
            }
            return DEFAULT;
        }
    }

    protected PackageMonitor createPackageMonitor(final ResolverListAdapter listAdapter) {
        return new PackageMonitor() { // from class: com.android.internal.app.ResolverActivity.1
            @Override // com.android.internal.content.PackageMonitor
            public void onSomePackagesChanged() {
                listAdapter.handlePackagesChanged();
                ResolverActivity.this.updateProfileViewButton();
            }

            @Override // com.android.internal.content.PackageMonitor
            public boolean onPackageChanged(String packageName, int uid, String[] components) {
                return true;
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

    protected void super_onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        this.mContext = this;
        Intent intent = makeMyIntent();
        Set<String> categories = intent.getCategories();
        if (Intent.ACTION_MAIN.equals(intent.getAction()) && categories != null && categories.size() == 1 && categories.contains(Intent.CATEGORY_HOME)) {
            this.mResolvingHome = true;
        }
        setSafeForwardingMode(true);
        onCreate(savedInstanceState, intent, null, 0, null, null, true);
    }

    protected void onCreate(Bundle savedInstanceState, Intent intent, CharSequence title, Intent[] initialIntents, List<ResolveInfo> rList, boolean supportsAlwaysUseOption) {
        onCreate(savedInstanceState, intent, title, 0, initialIntents, rList, supportsAlwaysUseOption);
    }

    protected void onCreate(Bundle savedInstanceState, Intent intent, CharSequence title, int defaultTitleRes, Intent[] initialIntents, List<ResolveInfo> rList, boolean supportsAlwaysUseOption) {
        int i;
        setTheme(appliedThemeResId());
        Configuration config = getResources().getConfiguration();
        this.mIsNight = (config.uiMode & 48) == 32;
        super.onCreate(savedInstanceState);
        if (!supportsAlwaysUseOption) {
            String[] selectionArgs = {"true"};
            String action = intent.getAction();
            if (action != null && (action.endsWith(Intent.ACTION_SEND) || action.endsWith(Intent.ACTION_SEND_MULTIPLE) || action.endsWith(Intent.ACTION_SENDTO) || action.endsWith("UNLIMITED_SHARE"))) {
                boolean isShareListAllowed = getEnterprisePolicyEnabled(getBaseContext(), "content://com.sec.knox.provider/RestrictionPolicy3", "isShareListAllowed", selectionArgs);
                Log.d(TAG, "action - " + action);
                Log.d(TAG, "onCreate(): isShareListAllowed(" + isShareListAllowed + NavigationBarInflaterView.KEY_CODE_END);
                if (!isShareListAllowed) {
                    Log.d(TAG, "onCreate(): ShareList is not allowed");
                    finish();
                    return;
                }
            }
        }
        this.mHasSubclassSpecifiedResolutions = rList != null;
        this.mQuietModeManager = createQuietModeManager();
        setProfileSwitchMessage(intent.getContentUserHint());
        this.mOrientation = config.orientation;
        this.mMaxColumns = getResources().getInteger(R.integer.config_maxResolverActivityColumns);
        this.mAppIconTheme = Settings.System.getString(getContentResolver(), Settings.System.SEM_CURRENT_APP_ICON_PACKAGE);
        this.mIsDeskTopMode = config.semDesktopModeEnabled == 1;
        this.mLaunchedFromUid = getLaunchedFromUid();
        this.mLaunchedFromUserHandle = UserHandle.getUserHandleForUid(this.mLaunchedFromUid);
        if (this.mLaunchedFromUid >= 0 && !UserHandle.isIsolated(this.mLaunchedFromUid)) {
            this.mPm = getPackageManager();
            this.mReferrerPackage = getReferrerPackageName();
            this.mIntents.add(0, new Intent(intent));
            this.mTitle = title;
            this.mDefaultTitleResId = defaultTitleRes;
            this.mSupportsAlwaysUseOption = supportsAlwaysUseOption;
            this.mPersonalProfileUserHandle = fetchPersonalProfileUserHandle();
            this.mWorkProfileUserHandle = fetchWorkProfileUserProfile();
            this.mCloneProfileUserHandle = fetchCloneProfileUserHandle();
            this.mPrivateProfileUserHandle = fetchPrivateProfileUserHandle();
            this.mTabOwnerUserHandleForLaunch = fetchTabOwnerUserHandleForLaunch();
            boolean filterLastUsed = (!this.mSupportsAlwaysUseOption || isVoiceInteraction() || shouldShowTabs() || hasCloneProfile()) ? false : true;
            this.mMultiProfilePagerAdapter = createMultiProfilePagerAdapter(initialIntents, rList, filterLastUsed);
            if (!configureContentView()) {
                this.mPersonalPackageMonitor = createPackageMonitor(this.mMultiProfilePagerAdapter.getPersonalListAdapter());
                this.mPersonalPackageMonitor.register(this, getMainLooper(), getPersonalProfileUserHandle(), false);
                if (shouldShowTabs()) {
                    this.mWorkPackageMonitor = createPackageMonitor(this.mMultiProfilePagerAdapter.getWorkListAdapter());
                    this.mWorkPackageMonitor.register(this, getMainLooper(), getWorkProfileUserHandle(), false);
                }
                this.mRegistered = true;
                ResolverDrawerLayout rdl = (ResolverDrawerLayout) findViewById(R.id.contentPanel);
                if (rdl != null) {
                    rdl.setOnDismissedListener(new ResolverDrawerLayout.OnDismissedListener() { // from class: com.android.internal.app.ResolverActivity.2
                        @Override // com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener
                        public void onDismissed() {
                            ResolverActivity.this.semFinishAfterAnimation();
                        }
                    });
                    boolean hasTouchScreen = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN);
                    if (isVoiceInteraction() || !hasTouchScreen) {
                        rdl.setCollapsed(false);
                    }
                    this.mResolverDrawerLayout = rdl;
                    int size = this.mMultiProfilePagerAdapter.getCount();
                    for (int i2 = 0; i2 < size; i2++) {
                        View view = this.mMultiProfilePagerAdapter.getItem(i2).rootView.findViewById(R.id.resolver_list);
                        if (view != null) {
                            view.setAccessibilityDelegate(new AppListAccessibilityDelegate(rdl));
                        }
                    }
                }
                this.mProfileView = findViewById(R.id.profile_button);
                if (this.mProfileView != null) {
                    this.mProfileView.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda14
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            ResolverActivity.this.onProfileClick(view2);
                        }
                    });
                    updateProfileViewButton();
                }
                Set<String> categories = intent.getCategories();
                if (this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem()) {
                    i = 451;
                } else {
                    i = 453;
                }
                MetricsLogger.action(this, i, intent.getAction() + ":" + intent.getType() + ":" + (categories != null ? Arrays.toString(categories.toArray()) : ""));
                semTransitionOverride(this, 0);
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (this.mIsPopOver) {
                    attrs.flags &= -1025;
                    getWindow().setAttributes(attrs);
                }
                attrs.flags |= 16777216;
                getWindow().setAttributes(attrs);
                this.mIsReduceTransparency = Settings.System.getInt(getContentResolver(), "accessibility_reduce_transparency", 0) == 1;
                if (this.mIsReduceTransparency) {
                    getWindow().setDimAmount(this.mIsNight ? SEM_RESOLVER_REDUCE_TRANSPARENCY_DIM_AMOUNT_DARK : SEM_RESOLVER_REDUCE_TRANSPARENCY_DIM_AMOUNT_LIGHT);
                }
                if (this.mSupportsAlwaysUseOption) {
                    this.mTipsIcon = (ImageView) findViewById(R.id.sem_resolver_tips_icon);
                    if (this.mTipsIcon != null) {
                        this.mTipsIcon.setVisibility(0);
                        this.mTipsIcon.setAccessibilityTraversalAfter(R.id.button_always);
                        this.mTipsIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda15
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                ResolverActivity.this.lambda$onCreate$0(view2);
                            }
                        });
                    }
                }
                if (CompatSandbox.isAppCompatOverrideEnabled(getResources().getConfiguration())) {
                    getWindow().setElevation(0.0f);
                    return;
                }
                return;
            }
            return;
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View v) {
        if (this.mTipsDescriptionPopup != null && this.mTipsDescriptionPopup.isShowing()) {
            this.mTipsDescriptionPopup.dismiss(false);
        } else {
            semCreateAndShowTipsPopup(this.mTipsIcon);
        }
    }

    protected AbstractMultiProfilePagerAdapter createMultiProfilePagerAdapter(Intent[] initialIntents, List<ResolveInfo> rList, boolean filterLastUsed) {
        if (shouldShowTabs()) {
            AbstractMultiProfilePagerAdapter resolverMultiProfilePagerAdapter = createResolverMultiProfilePagerAdapterForTwoProfiles(initialIntents, rList, filterLastUsed);
            return resolverMultiProfilePagerAdapter;
        }
        AbstractMultiProfilePagerAdapter resolverMultiProfilePagerAdapter2 = createResolverMultiProfilePagerAdapterForOneProfile(initialIntents, rList, filterLastUsed);
        return resolverMultiProfilePagerAdapter2;
    }

    protected AbstractMultiProfilePagerAdapter.MyUserIdProvider createMyUserIdProvider() {
        return new AbstractMultiProfilePagerAdapter.MyUserIdProvider();
    }

    protected AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker createCrossProfileIntentsChecker() {
        return new AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker(getContentResolver());
    }

    /* renamed from: com.android.internal.app.ResolverActivity$3, reason: invalid class name */
    class AnonymousClass3 implements AbstractMultiProfilePagerAdapter.QuietModeManager {
        private boolean mIsWaitingToEnableWorkProfile = false;
        final /* synthetic */ UserManager val$userManager;

        AnonymousClass3(UserManager userManager) {
            this.val$userManager = userManager;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public boolean isQuietModeEnabled(UserHandle workProfileUserHandle) {
            return this.val$userManager.isQuietModeEnabled(workProfileUserHandle);
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager
        public void requestQuietModeEnabled(final boolean enabled, final UserHandle workProfileUserHandle) {
            Executor executor = AsyncTask.THREAD_POOL_EXECUTOR;
            final UserManager userManager = this.val$userManager;
            executor.execute(new Runnable() { // from class: com.android.internal.app.ResolverActivity$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserManager.this.requestQuietModeEnabled(enabled, workProfileUserHandle);
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
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        return new AnonymousClass3(userManager);
    }

    protected AbstractMultiProfilePagerAdapter.EmptyStateProvider createBlockerEmptyStateProvider() {
        boolean shouldShowNoCrossProfileIntentsEmptyState = getUser().equals(getIntentUser());
        if (!shouldShowNoCrossProfileIntentsEmptyState) {
            return new AbstractMultiProfilePagerAdapter.EmptyStateProvider() { // from class: com.android.internal.app.ResolverActivity.4
            };
        }
        AbstractMultiProfilePagerAdapter.EmptyState noWorkToPersonalEmptyState = new NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, R.string.resolver_cross_profile_blocked, DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_PERSONAL, R.string.resolver_cant_access_personal_apps_explanation, 158, METRICS_CATEGORY_RESOLVER);
        AbstractMultiProfilePagerAdapter.EmptyState noPersonalToWorkEmptyState = new NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, R.string.resolver_cross_profile_blocked, DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_WORK, R.string.resolver_cant_access_work_apps_explanation, 159, METRICS_CATEGORY_RESOLVER);
        return new NoCrossProfileEmptyStateProvider(getPersonalProfileUserHandle(), noWorkToPersonalEmptyState, noPersonalToWorkEmptyState, createCrossProfileIntentsChecker(), getTabOwnerUserHandleForLaunch());
    }

    protected AbstractMultiProfilePagerAdapter.EmptyStateProvider createEmptyStateProvider(UserHandle workProfileUserHandle) {
        AbstractMultiProfilePagerAdapter.EmptyStateProvider blockerEmptyStateProvider = createBlockerEmptyStateProvider();
        AbstractMultiProfilePagerAdapter.EmptyStateProvider workProfileOffEmptyStateProvider = new WorkProfilePausedEmptyStateProvider(this, workProfileUserHandle, this.mQuietModeManager, new AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda13
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener
            public final void onSwitchOnWorkSelected() {
                ResolverActivity.this.lambda$createEmptyStateProvider$1();
            }
        }, getMetricsCategory());
        AbstractMultiProfilePagerAdapter.EmptyStateProvider noAppsEmptyStateProvider = new NoAppsAvailableEmptyStateProvider(this, workProfileUserHandle, getPersonalProfileUserHandle(), getMetricsCategory(), getTabOwnerUserHandleForLaunch());
        return new AbstractMultiProfilePagerAdapter.CompositeEmptyStateProvider(blockerEmptyStateProvider, workProfileOffEmptyStateProvider, noAppsEmptyStateProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createEmptyStateProvider$1() {
        if (this.mOnSwitchOnWorkSelectedListener != null) {
            this.mOnSwitchOnWorkSelectedListener.onSwitchOnWorkSelected();
        }
    }

    private ResolverMultiProfilePagerAdapter createResolverMultiProfilePagerAdapterForOneProfile(Intent[] initialIntents, List<ResolveInfo> rList, boolean filterLastUsed) {
        ResolverListAdapter adapter = createResolverListAdapter(this, this.mIntents, initialIntents, rList, filterLastUsed, getPersonalProfileUserHandle());
        AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager = createQuietModeManager();
        return new ResolverMultiProfilePagerAdapter(this, adapter, createEmptyStateProvider(null), quietModeManager, null, getCloneProfileUserHandle());
    }

    private UserHandle getIntentUser() {
        if (getIntent().hasExtra(EXTRA_CALLING_USER)) {
            return (UserHandle) getIntent().getParcelableExtra(EXTRA_CALLING_USER, UserHandle.class);
        }
        return getUser();
    }

    private ResolverMultiProfilePagerAdapter createResolverMultiProfilePagerAdapterForTwoProfiles(Intent[] initialIntents, List<ResolveInfo> rList, boolean filterLastUsed) {
        int selectedProfile;
        int selectedProfile2 = getCurrentProfile();
        UserHandle intentUser = getIntentUser();
        if (!getTabOwnerUserHandleForLaunch().equals(intentUser)) {
            if (getPersonalProfileUserHandle().equals(intentUser)) {
                selectedProfile = 0;
            } else {
                if (getWorkProfileUserHandle().equals(intentUser)) {
                    selectedProfile = 1;
                }
                selectedProfile = selectedProfile2;
            }
        } else {
            int selectedProfileExtra = getSelectedProfileExtra();
            if (selectedProfileExtra != -1) {
                selectedProfile = selectedProfileExtra;
            }
            selectedProfile = selectedProfile2;
        }
        ResolverListAdapter personalAdapter = createResolverListAdapter(this, this.mIntents, selectedProfile == 0 ? initialIntents : null, rList, filterLastUsed && UserHandle.myUserId() == getPersonalProfileUserHandle().getIdentifier(), getPersonalProfileUserHandle());
        UserHandle workProfileUserHandle = getWorkProfileUserHandle();
        ResolverListAdapter workAdapter = createResolverListAdapter(this, this.mIntents, selectedProfile == 1 ? initialIntents : null, rList, filterLastUsed && UserHandle.myUserId() == workProfileUserHandle.getIdentifier(), workProfileUserHandle);
        AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager = createQuietModeManager();
        return new ResolverMultiProfilePagerAdapter(this, personalAdapter, workAdapter, createEmptyStateProvider(getWorkProfileUserHandle()), quietModeManager, selectedProfile, getWorkProfileUserHandle(), getCloneProfileUserHandle());
    }

    protected int appliedThemeResId() {
        return R.style.Theme_DeviceDefault_Resolver;
    }

    int getSelectedProfileExtra() {
        int selectedProfile = -1;
        if (getIntent().hasExtra(EXTRA_SELECTED_PROFILE) && (selectedProfile = getIntent().getIntExtra(EXTRA_SELECTED_PROFILE, -1)) != 0 && selectedProfile != 1) {
            throw new IllegalArgumentException("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE has invalid value " + selectedProfile + ". Must be either ResolverActivity.PROFILE_PERSONAL or ResolverActivity.PROFILE_WORK.");
        }
        return selectedProfile;
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
        if (SemPersonaManager.isAppSeparationUserId(UserHandle.myUserId()) || SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            this.mPersonalProfileUserHandle = UserHandle.of(UserHandle.myUserId());
        } else {
            this.mPersonalProfileUserHandle = UserHandle.of(ActivityManager.getCurrentUser());
        }
        return this.mPersonalProfileUserHandle;
    }

    protected UserHandle fetchWorkProfileUserProfile() {
        this.mWorkProfileUserHandle = null;
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        for (UserInfo userInfo : userManager.getProfiles(this.mPersonalProfileUserHandle.getIdentifier())) {
            if (userInfo.isManagedProfile() && !userInfo.isSecureFolder()) {
                this.mWorkProfileUserHandle = userInfo.getUserHandle();
            }
        }
        return this.mWorkProfileUserHandle;
    }

    protected UserHandle fetchCloneProfileUserHandle() {
        this.mCloneProfileUserHandle = null;
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        for (UserInfo userInfo : userManager.getProfiles(this.mPersonalProfileUserHandle.getIdentifier())) {
            if (userInfo.isCloneProfile()) {
                this.mCloneProfileUserHandle = userInfo.getUserHandle();
            }
        }
        return this.mCloneProfileUserHandle;
    }

    protected UserHandle fetchPrivateProfileUserHandle() {
        this.mPrivateProfileUserHandle = null;
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        Iterator<UserInfo> it = userManager.getProfiles(this.mPersonalProfileUserHandle.getIdentifier()).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UserInfo userInfo = it.next();
            if (userInfo.isPrivateProfile()) {
                this.mPrivateProfileUserHandle = userInfo.getUserHandle();
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
        return ((privateSpaceEnabled() && isLaunchedInSingleUserMode()) || !hasWorkProfile() || !ENABLE_TABBED_VIEW || SemPersonaManager.isDoEnabled(0) || SemDualAppManager.isDualAppId(UserHandle.myUserId()) || SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) ? false : true;
    }

    protected void onProfileClick(View v) {
        DisplayResolveInfo dri = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile();
        if (dri == null) {
            return;
        }
        this.mProfileSwitchMessage = null;
        onTargetSelected(dri, false);
        finish();
    }

    protected boolean shouldAddFooterView() {
        View buttonBar;
        return useLayoutWithDefault() || (buttonBar = findViewById(R.id.button_bar)) == null || buttonBar.getVisibility() == 8;
    }

    protected void applyFooterView(int height) {
    }

    protected WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
        this.mSystemWindowInsets = insets.getSystemWindowInsets();
        this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        resetButtonBar();
        if (shouldUseMiniResolver()) {
            View buttonContainer = findViewById(R.id.button_bar_container);
            buttonContainer.setPadding(0, 0, 0, this.mSystemWindowInsets.bottom + getResources().getDimensionPixelOffset(R.dimen.resolver_button_bar_spacing));
        }
        if (shouldAddFooterView()) {
            applyFooterView(this.mSystemWindowInsets.bottom);
        }
        return insets.consumeSystemWindowInsets();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mOrientation = getResources().getConfiguration().orientation;
        int width = getResources().getDimensionPixelSize(R.dimen.resolver_max_width);
        this.mResolverDrawerLayout.semSetMaxWidth(width);
        this.mMultiProfilePagerAdapter.getActiveListAdapter().handlePackagesChanged();
        if (this.mIsIntentPicker && shouldShowTabs() && !useLayoutWithDefault() && !shouldUseMiniResolver()) {
            updateIntentPickerPaddings();
        }
        if (this.mSystemWindowInsets != null) {
            this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        }
        if (this.mTipsDescriptionPopup != null && this.mTipsDescriptionPopup.isShowing()) {
            this.mTipsDescriptionPopup.dismiss(false);
        }
    }

    private void updateIntentPickerPaddings() {
        View titleCont = findViewById(R.id.title_container);
        if (titleCont != null) {
            titleCont.setPadding(titleCont.getPaddingLeft(), titleCont.getPaddingTop(), titleCont.getPaddingRight(), getResources().getDimensionPixelSize(R.dimen.resolver_title_padding_bottom));
        }
        View buttonBar = findViewById(R.id.button_bar);
        if (buttonBar != null) {
            buttonBar.setPadding(buttonBar.getPaddingLeft(), getResources().getDimensionPixelSize(R.dimen.resolver_button_bar_spacing), buttonBar.getPaddingRight(), getResources().getDimensionPixelSize(R.dimen.resolver_button_bar_spacing));
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void sendVoiceChoicesIfNeeded() {
        if (!isVoiceInteraction()) {
            return;
        }
        int count = this.mMultiProfilePagerAdapter.getActiveListAdapter().getCount();
        VoiceInteractor.PickOptionRequest.Option[] options = new VoiceInteractor.PickOptionRequest.Option[count];
        int N = options.length;
        for (int i = 0; i < N; i++) {
            TargetInfo target = this.mMultiProfilePagerAdapter.getActiveListAdapter().getItem(i);
            if (target == null) {
                return;
            }
            options[i] = optionForChooserTarget(target, i);
        }
        this.mPickOptionRequest = new PickTargetOptionRequest(new VoiceInteractor.Prompt(getTitle()), options, null);
        getVoiceInteractor().submitRequest(this.mPickOptionRequest);
    }

    VoiceInteractor.PickOptionRequest.Option optionForChooserTarget(TargetInfo target, int index) {
        return new VoiceInteractor.PickOptionRequest.Option(target.getDisplayLabel(), index);
    }

    protected final void setAdditionalTargets(Intent[] intents) {
        if (intents != null) {
            for (Intent intent : intents) {
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
        Uri referrer = null;
        try {
            referrer = getReferrer();
        } catch (Exception e) {
            Log.e(TAG, "getReferrer error!!!" + e);
        }
        if (referrer != null && "android-app".equals(referrer.getScheme())) {
            return referrer.getHost();
        }
        return null;
    }

    public int getLayoutResource() {
        return R.layout.sem_resolver_grid;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void updateProfileViewButton() {
        if (this.mProfileView == null) {
            return;
        }
        DisplayResolveInfo dri = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile();
        if (dri != null && !shouldShowTabs()) {
            this.mProfileView.setVisibility(0);
            View text = this.mProfileView.findViewById(R.id.profile_button);
            if (!(text instanceof TextView)) {
                text = this.mProfileView.findViewById(16908308);
            }
            ((TextView) text).lambda$setTextAsync$0(dri.getDisplayLabel());
            semSetTextSizeByMaxFontScale((TextView) text, R.dimen.sem_resolver_pagemode_titlepanel_text_size);
            return;
        }
        this.mProfileView.setVisibility(8);
    }

    private void setProfileSwitchMessage(int contentUserHint) {
        boolean originIsSecureFolder;
        if (contentUserHint != -2 && contentUserHint != UserHandle.myUserId()) {
            UserManager userManager = (UserManager) getSystemService("user");
            UserInfo originUserInfo = userManager.getUserInfo(contentUserHint);
            boolean targetIsSecureFolder = false;
            boolean originIsManaged = originUserInfo != null ? originUserInfo.isManagedProfile() : false;
            boolean targetIsManaged = userManager.isManagedProfile();
            if (originUserInfo == null) {
                originIsSecureFolder = false;
            } else {
                originIsSecureFolder = originUserInfo.isSecureFolder();
            }
            if (userManager.getUserInfo(UserHandle.myUserId()) != null) {
                targetIsSecureFolder = userManager.getUserInfo(UserHandle.myUserId()).isSecureFolder();
            }
            if (originIsSecureFolder || targetIsSecureFolder) {
                return;
            }
            if (originIsManaged && !targetIsManaged) {
                this.mProfileSwitchMessage = getForwardToPersonalMsg();
            } else if (!originIsManaged && targetIsManaged) {
                this.mProfileSwitchMessage = getForwardToWorkMsg();
            }
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

    public void setSafeForwardingMode(boolean safeForwarding) {
        this.mSafeForwardingMode = safeForwarding;
    }

    protected CharSequence getTitleForAction(Intent intent, int defaultTitleRes) {
        ActionTitle title;
        if (this.mResolvingHome) {
            title = ActionTitle.HOME;
        } else {
            title = ActionTitle.forAction(intent.getAction());
        }
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter().getFilteredPosition() >= 0) {
        }
        if (title == ActionTitle.DEFAULT && defaultTitleRes != 0) {
            return getString(defaultTitleRes);
        }
        return getString(title.titleRes);
    }

    void dismiss() {
        if (!isFinishing()) {
            finish();
        }
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
        UserHandle workUserHandle = getWorkProfileUserHandle();
        UserManager userManager = (UserManager) getSystemService(UserManager.class);
        return !userManager.isQuietModeEnabled(workUserHandle) && userManager.isUserUnlocked(workUserHandle);
    }

    private void registerWorkProfileStateReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_UNLOCKED);
        filter.addAction(Intent.ACTION_MANAGED_PROFILE_AVAILABLE);
        filter.addAction(Intent.ACTION_MANAGED_PROFILE_UNAVAILABLE);
        registerReceiverAsUser(this.mWorkProfileStateReceiver, UserHandle.ALL, filter, null, null);
    }

    @Override // android.app.Activity
    protected void onStop() {
        KeyguardManager km;
        super.onStop();
        Window window = getWindow();
        WindowManager.LayoutParams attrs = window.getAttributes();
        attrs.privateFlags &= -524289;
        window.setAttributes(attrs);
        if (this.mRegistered) {
            this.mPersonalPackageMonitor.unregister();
            if (this.mWorkPackageMonitor != null) {
                this.mWorkPackageMonitor.unregister();
            }
            this.mRegistered = false;
        }
        Intent intent = getIntent();
        if ((intent.getFlags() & 268435456) != 0 && !isVoiceInteraction() && !this.mResolvingHome && !this.mRetainInOnStop && !isChangingConfigurations()) {
            Context context = getBaseContext();
            if (this.mLaunchedFromUid == 1001 && (km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)) != null && km.isKeyguardLocked()) {
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
    protected void onDestroy() {
        ResolverListAdapter inactiveAdapter;
        super.onDestroy();
        if (!isChangingConfigurations() && this.mPickOptionRequest != null) {
            this.mPickOptionRequest.cancel();
        }
        if (this.mMultiProfilePagerAdapter != null) {
            ResolverListAdapter activeAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
            if (activeAdapter != null) {
                activeAdapter.onDestroy();
            }
            if (Flags.fixResolverMemoryLeak() && (inactiveAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter()) != null) {
                inactiveAdapter.onDestroy();
            }
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ViewPager viewPager = (ViewPager) findViewById(R.id.profile_pager);
        if (viewPager != null) {
            outState.putInt(LAST_SHOWN_TAB_KEY, viewPager.getCurrentItem());
        }
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resetButtonBar();
        ViewPager viewPager = (ViewPager) findViewById(R.id.profile_pager);
        if (viewPager != null) {
            viewPager.setCurrentItem(savedInstanceState.getInt(LAST_SHOWN_TAB_KEY));
        }
        this.mMultiProfilePagerAdapter.clearInactiveProfileCache();
    }

    private boolean hasManagedProfile() {
        UserManager userManager = (UserManager) getSystemService("user");
        if (userManager == null) {
            return false;
        }
        try {
            List<UserInfo> profiles = userManager.getProfiles(getUserId());
            for (UserInfo userInfo : profiles) {
                if (userInfo != null && userInfo.isManagedProfile()) {
                    return true;
                }
            }
            return false;
        } catch (SecurityException e) {
            return false;
        }
    }

    private boolean supportsManagedProfiles(ResolveInfo resolveInfo) {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(resolveInfo.activityInfo.packageName, 0);
            return appInfo.targetSdkVersion >= 21;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAlwaysButtonEnabled(boolean hasValidSelection, int checkedPos, boolean filtered) {
        if (!this.mMultiProfilePagerAdapter.getCurrentUserHandle().equals(getUser())) {
            this.mAlwaysButton.setEnabled(false);
            return;
        }
        boolean enabled = false;
        ResolveInfo ri = null;
        if (hasValidSelection) {
            ri = this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(checkedPos, filtered);
            if (ri == null) {
                Log.e(TAG, "Invalid position supplied to setAlwaysButtonEnabled");
                return;
            }
            if (ri.targetUserId != -2) {
                Log.e(TAG, "Attempted to set selection to resolve info for another user");
            } else {
                enabled = true;
            }
            this.mAlwaysButton.lambda$setTextAsync$0(getResources().getString(R.string.activity_resolver_use_always));
        }
        if (ri != null) {
            ActivityInfo activityInfo = ri.activityInfo;
            boolean hasRecordPermission = this.mPm.checkPermission(Manifest.permission.RECORD_AUDIO, activityInfo.packageName) == 0;
            if (!hasRecordPermission) {
                boolean hasAudioCapture = getIntent().getBooleanExtra(EXTRA_IS_AUDIO_CAPTURE_DEVICE, false);
                enabled = !hasAudioCapture;
            }
        }
        this.mAlwaysButton.setEnabled(enabled);
    }

    public void onButtonClick(View v) {
        int id = v.getId();
        if (this.mSecondDepth) {
            int pos = this.mSemSelectTaskListAdapter.mSelectedItem;
            startSelected(pos, id == 16908866, true);
            return;
        }
        AbsListView listView = (AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
        ResolverListAdapter currentListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (currentListAdapter.hasFilteredItem()) {
            currentListAdapter.getFilteredPosition();
        } else {
            listView.getCheckedItemPosition();
        }
        boolean hasIndexBeenFiltered = !currentListAdapter.hasFilteredItem();
        int indexPos = listView.getCheckedItemPosition();
        if (indexPos == -1) {
            return;
        }
        startSelected(indexPos, id == 16908866, hasIndexBeenFiltered);
    }

    public void startSelected(int which, boolean always, boolean hasIndexBeenFiltered) {
        ResolveInfo ri;
        TargetInfo target;
        int i;
        if (isFinishing()) {
            return;
        }
        if (this.mSecondDepth) {
            ri = this.mSemSelectTaskListAdapter.resolveInfoForPosition(which, hasIndexBeenFiltered);
        } else {
            ri = this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(which, hasIndexBeenFiltered);
        }
        if (this.mResolvingHome && hasManagedProfile() && !supportsManagedProfiles(ri)) {
            Toast.makeText(this, getWorkProfileNotSupportedMsg(ri.activityInfo.loadLabel(getPackageManager()).toString()), 1).show();
            return;
        }
        if (this.mSecondDepth) {
            target = this.mSemSelectTaskListAdapter.targetInfoForPosition(which, hasIndexBeenFiltered);
        } else {
            int lastChosenIndex = this.mMultiProfilePagerAdapter.getActiveListAdapter().getLastChosenActivityIndex();
            DisplayResolveInfo displayResolveInfo = this.mMultiProfilePagerAdapter.getActiveListAdapter().getDisplayResolveInfo(which);
            String lastChosenActivity = this.mMultiProfilePagerAdapter.getActiveListAdapter().getLastChosenActivity();
            boolean z = false;
            if (displayResolveInfo.getExtendedInfo() != null && !TextUtils.isEmpty(lastChosenActivity)) {
                z = lastChosenActivity.equals(displayResolveInfo.getExtendedInfo().toString());
            }
            boolean isLastChosenActivity = z;
            Log.i(TAG, "lastChosenIndex : " + lastChosenIndex + ", lastChosenActivity" + lastChosenActivity);
            if (lastChosenIndex >= 0 && isLastChosenActivity) {
                this.mSemSelectTaskListAdapter = new SemSelectTaskListAdapter(displayResolveInfo.getSimilarList(), this.mMultiProfilePagerAdapter, new ResolverActivity$$ExternalSyntheticLambda0(this));
                target = this.mSemSelectTaskListAdapter.targetInfoForPosition(lastChosenIndex, hasIndexBeenFiltered);
            } else {
                target = this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(which, hasIndexBeenFiltered);
            }
        }
        if (target != null && onTargetSelected(target, always)) {
            if (always && this.mSupportsAlwaysUseOption) {
                MetricsLogger.action(this, 455);
            } else if (this.mSupportsAlwaysUseOption) {
                MetricsLogger.action(this, 456);
            } else {
                MetricsLogger.action(this, 457);
            }
            if (this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem()) {
                i = 452;
            } else {
                i = 454;
            }
            MetricsLogger.action(this, i);
            finish();
        }
    }

    private String getWorkProfileNotSupportedMsg(final String launcherName) {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_PROFILE_NOT_SUPPORTED, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getWorkProfileNotSupportedMsg$4;
                lambda$getWorkProfileNotSupportedMsg$4 = ResolverActivity.this.lambda$getWorkProfileNotSupportedMsg$4(launcherName);
                return lambda$getWorkProfileNotSupportedMsg$4;
            }
        }, launcherName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getWorkProfileNotSupportedMsg$4(String launcherName) {
        return getString(R.string.activity_resolver_work_profiles_support, launcherName);
    }

    public Intent getReplacementIntent(ActivityInfo aInfo, Intent defIntent) {
        return defIntent;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public final void onPostListReady(ResolverListAdapter listAdapter, boolean doPostProcessing, boolean rebuildCompleted, boolean skipAutoLaunch) {
        if (isDestroyed() || isAutolaunching()) {
            return;
        }
        if (this.mIsIntentPicker) {
            ((ResolverMultiProfilePagerAdapter) this.mMultiProfilePagerAdapter).setUseLayoutWithDefault(useLayoutWithDefault());
        }
        if (this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(listAdapter)) {
            this.mMultiProfilePagerAdapter.showEmptyResolverListEmptyState(listAdapter);
        } else {
            this.mMultiProfilePagerAdapter.showListView(listAdapter);
        }
        if (!skipAutoLaunch && rebuildCompleted && maybeAutolaunchActivity()) {
            Log.e(TAG, "onPostListReady return skipAutoLaunch = " + skipAutoLaunch + ", rebuildComplete = " + rebuildCompleted);
            return;
        }
        if (doPostProcessing) {
            maybeCreateHeader(listAdapter);
            resetButtonBar();
            if (rebuildCompleted) {
                onListRebuilt(listAdapter, rebuildCompleted);
            }
        }
    }

    protected void onListRebuilt(ResolverListAdapter listAdapter, boolean rebuildCompleted) {
        ResolverDrawerLayout rdl;
        int i;
        ItemClickListener listener = new ItemClickListener();
        semSetupAdapterListView((AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView(), listener);
        if (shouldShowTabs() && this.mIsIntentPicker && (rdl = (ResolverDrawerLayout) findViewById(R.id.contentPanel)) != null) {
            Resources resources = getResources();
            if (useLayoutWithDefault()) {
                i = R.dimen.resolver_max_collapsed_height_with_default_with_tabs;
            } else {
                i = R.dimen.resolver_max_collapsed_height_with_tabs;
            }
            rdl.setMaxCollapsedHeight(resources.getDimensionPixelSize(i));
        }
        if (this.mOnceButton != null && this.mAlwaysButton != null && this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem()) {
            View v = this.mMultiProfilePagerAdapter.getActiveAdapterView();
            if (v instanceof AbsListView) {
                AbsListView adapterView = (AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
                adapterView.setItemChecked(this.mMultiProfilePagerAdapter.getActiveListAdapter().getFilteredPosition(), true);
                int checkedPos = adapterView.getCheckedItemPosition();
                boolean hasValidSelection = checkedPos != -1;
                if (this.mSupportsAlwaysUseOption) {
                    if ((!hasValidSelection || this.mLastSelected != checkedPos) && this.mSupportButtons) {
                        setAlwaysButtonEnabled(hasValidSelection, checkedPos, true);
                        this.mOnceButton.setEnabled(hasValidSelection);
                        if (hasValidSelection) {
                            adapterView.smoothScrollToPosition(checkedPos);
                        }
                        this.mLastSelected = checkedPos;
                    }
                }
            }
        }
    }

    protected boolean onTargetSelected(TargetInfo target, boolean always) {
        return onTargetSelected(target, always, false);
    }

    protected boolean onTargetSelected(TargetInfo target, boolean always, boolean noAnim) {
        Intent filterIntent;
        ComponentName[] set;
        int bestMatch;
        String str;
        String mimeType;
        ResolveInfo ri = target != null ? target.getResolveInfo() : null;
        Intent intent = target != null ? target.getResolvedIntent() : null;
        int i = 0;
        if (intent != null && ((this.mSupportsAlwaysUseOption || this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem()) && this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredResolveList() != null)) {
            IntentFilter filter = new IntentFilter();
            if (intent.getSelector() != null) {
                filterIntent = intent.getSelector();
            } else {
                filterIntent = intent;
            }
            String action = filterIntent.getAction();
            if (action != null) {
                filter.addAction(action);
            }
            Set<String> categories = filterIntent.getCategories();
            if (categories != null) {
                Iterator<String> it = categories.iterator();
                while (it.hasNext()) {
                    filter.addCategory(it.next());
                }
            }
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            int cat = ri.match & IntentFilter.MATCH_CATEGORY_MASK;
            Uri data = filterIntent.getData();
            if (cat == 6291456 && (mimeType = filterIntent.resolveType(this)) != null) {
                try {
                    filter.addDataType(mimeType);
                } catch (IntentFilter.MalformedMimeTypeException e) {
                    Log.w(TAG, e);
                    filter = null;
                }
            }
            if (data != null && data.getScheme() != null && filter != null && (cat != 6291456 || (!"file".equals(data.getScheme()) && !"content".equals(data.getScheme())))) {
                filter.addDataScheme(data.getScheme());
                Iterator<PatternMatcher> pIt = ri.filter.schemeSpecificPartsIterator();
                if (pIt != null) {
                    String ssp = data.getSchemeSpecificPart();
                    while (true) {
                        if (ssp == null || !pIt.hasNext()) {
                            break;
                        }
                        PatternMatcher p = pIt.next();
                        if (p.match(ssp)) {
                            filter.addDataSchemeSpecificPart(p.getPath(), p.getType());
                            break;
                        }
                    }
                }
                Iterator<IntentFilter.AuthorityEntry> aIt = ri.filter.authoritiesIterator();
                if (aIt != null) {
                    while (true) {
                        if (!aIt.hasNext()) {
                            break;
                        }
                        IntentFilter.AuthorityEntry a = aIt.next();
                        if (a.match(data) >= 0) {
                            int port = a.getPort();
                            filter.addDataAuthority(a.getHost(), port >= 0 ? Integer.toString(port) : null);
                        }
                    }
                }
                Iterator<PatternMatcher> pIt2 = ri.filter.pathsIterator();
                if (pIt2 != null) {
                    String path = data.getPath();
                    while (true) {
                        if (path == null || !pIt2.hasNext()) {
                            break;
                        }
                        PatternMatcher p2 = pIt2.next();
                        if (p2.match(path)) {
                            filter.addDataPath(p2.getPath(), p2.getType());
                            break;
                        }
                    }
                }
            }
            if (filter != null) {
                int N = this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredResolveList().size();
                boolean needToAddBackProfileForwardingComponent = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile() != null;
                if (!needToAddBackProfileForwardingComponent) {
                    set = new ComponentName[N];
                } else {
                    set = new ComponentName[N + 1];
                }
                int bestMatch2 = 0;
                int i2 = 0;
                while (i2 < N) {
                    ResolveInfo r = this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredResolveList().get(i2).getResolveInfoAt(i);
                    set[i2] = new ComponentName(r.activityInfo.packageName, r.activityInfo.name);
                    if (r.match > bestMatch2) {
                        bestMatch2 = r.match;
                    }
                    Log.i(TAG, "preferred : set = " + set[i2]);
                    i2++;
                    i = 0;
                }
                if (!needToAddBackProfileForwardingComponent) {
                    bestMatch = bestMatch2;
                } else {
                    set[N] = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile().getResolvedComponentName();
                    int otherProfileMatch = this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile().getResolveInfo().match;
                    if (otherProfileMatch > bestMatch2) {
                        bestMatch2 = otherProfileMatch;
                    }
                    Log.i(TAG, "preferred : otherProfileMatch = " + otherProfileMatch + ", set = " + set[N]);
                    bestMatch = bestMatch2;
                }
                if (!this.mSupportsAlwaysUseOption || this.mSemShareLogging == null) {
                    str = TAG;
                } else {
                    String caller = getReferrerPackageName();
                    ComponentName cn = filterIntent.getComponent();
                    String callee = cn != null ? cn.getPackageName() : ri.activityInfo.processName;
                    String mimeTypeValue = Optional.ofNullable(filterIntent.resolveType(this)).orElse(intent.resolveType(this));
                    SemShareLogging semShareLogging = this.mSemShareLogging;
                    str = TAG;
                    semShareLogging.semInsertStartSelectLog(caller, callee, mimeTypeValue, action, always);
                }
                if (always) {
                    int userId = getUserId();
                    PackageManager pm = getPackageManager();
                    Log.i(str, "preferred : bestMatch = " + bestMatch + ", comp = " + intent.getComponent());
                    addPreferredActivity(pm, filter, bestMatch, set, intent);
                    if (ri.handleAllWebDataURI) {
                        String packageName = pm.getDefaultBrowserPackageNameAsUser(userId);
                        if (TextUtils.isEmpty(packageName)) {
                            pm.setDefaultBrowserPackageNameAsUser(ri.activityInfo.packageName, userId);
                        }
                    }
                } else {
                    try {
                        this.mMultiProfilePagerAdapter.getActiveListAdapter().mResolverListController.setLastChosen(intent, filter, bestMatch);
                    } catch (RemoteException re) {
                        Log.d(str, "Error calling setLastChosenActivity\n" + re);
                    }
                }
            }
        }
        if (target != null) {
            if (!noAnim) {
                semSafelyStartActivtyAfterAnimation(target);
            } else {
                safelyStartActivity(target);
            }
            if (target.isSuspended()) {
                return false;
            }
            return true;
        }
        return true;
    }

    protected void addPreferredActivity(PackageManager pm, IntentFilter filter, int bestMatch, ComponentName[] set, Intent intent) {
        pm.addUniquePreferredActivity(filter, bestMatch, set, intent.getComponent());
    }

    public void safelyStartActivity(TargetInfo cti) {
        UserHandle activityUserHandle = getResolveInfoUserHandle(cti.getResolveInfo(), this.mMultiProfilePagerAdapter.getCurrentUserHandle());
        safelyStartActivityAsUser(cti, activityUserHandle, null);
    }

    public final void safelyStartActivityAsUser(TargetInfo cti, UserHandle user) {
        safelyStartActivityAsUser(cti, user, null);
    }

    protected final void safelyStartActivityAsUser(TargetInfo cti, UserHandle user, Bundle options) {
        StrictMode.disableDeathOnFileUriExposure();
        try {
            safelyStartActivityInternal(cti, user, options);
        } finally {
            StrictMode.enableDeathOnFileUriExposure();
        }
    }

    protected void safelyStartActivityInternal(TargetInfo cti, UserHandle user, Bundle options) {
        if (!cti.isSuspended() && this.mRegistered) {
            if (this.mPersonalPackageMonitor != null) {
                this.mPersonalPackageMonitor.unregister();
            }
            if (this.mWorkPackageMonitor != null) {
                this.mWorkPackageMonitor.unregister();
            }
            this.mRegistered = false;
        }
        if (this.mProfileSwitchMessage != null) {
            Toast.makeText(this, this.mProfileSwitchMessage, 1).show();
        }
        if (!this.mSafeForwardingMode) {
            if (cti.startAsUser(this, options, user)) {
                onActivityStarted(cti);
                maybeLogCrossProfileTargetLaunch(cti, user);
                return;
            }
            return;
        }
        try {
            if (cti.startAsCaller(this, options, user.getIdentifier())) {
                onActivityStarted(cti);
                maybeLogCrossProfileTargetLaunch(cti, user);
            }
        } catch (RuntimeException e) {
            Slog.wtf(TAG, "Unable to launch as uid " + this.mLaunchedFromUid + " package " + getLaunchedFromPackage() + ", while running in " + ActivityThread.currentProcessName(), e);
        }
    }

    private void maybeLogCrossProfileTargetLaunch(TargetInfo cti, UserHandle currentUserHandle) {
        if (!hasWorkProfile() || currentUserHandle.equals(getUser())) {
            return;
        }
        DevicePolicyEventLogger.createEvent(155).setBoolean(currentUserHandle.equals(getPersonalProfileUserHandle())).setStrings(getMetricsCategory(), cti instanceof ChooserTargetInfo ? "direct_share" : "other_target").write();
    }

    public void onActivityStarted(TargetInfo cti) {
    }

    public boolean shouldGetActivityMetadata() {
        return false;
    }

    public boolean shouldAutoLaunchSingleChoice(TargetInfo target) {
        return !target.isSuspended();
    }

    void showTargetDetails(ResolveInfo ri) {
        Intent in = new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", ri.activityInfo.packageName, null)).addFlags(524288);
        startActivityAsUser(in, this.mMultiProfilePagerAdapter.getCurrentUserHandle());
    }

    protected ResolverListAdapter createResolverListAdapter(Context context, List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList, boolean filterLastUsed, UserHandle userHandle) {
        UserHandle initialIntentsUserSpace;
        Intent startIntent = getIntent();
        boolean isAudioCaptureDevice = startIntent.getBooleanExtra(EXTRA_IS_AUDIO_CAPTURE_DEVICE, false);
        if (!isLaunchedAsCloneProfile() || !userHandle.equals(getPersonalProfileUserHandle())) {
            initialIntentsUserSpace = userHandle;
        } else {
            initialIntentsUserSpace = getCloneProfileUserHandle();
        }
        return new ResolverListAdapter(context, payloadIntents, initialIntents, rList, filterLastUsed, createListController(userHandle), this, isAudioCaptureDevice, initialIntentsUserSpace);
    }

    private AbstractResolverComparator makeResolverComparator(UserHandle userHandle) {
        if (this.mHasSubclassSpecifiedResolutions) {
            return new NoOpResolverComparator(this, getTargetIntent(), getResolverRankerServiceUserHandleList(userHandle));
        }
        return new ResolverRankerServiceResolverComparator(this, getTargetIntent(), getReferrerPackageName(), (AbstractResolverComparator.AfterCompute) null, (ChooserActivityLogger) null, getResolverRankerServiceUserHandleList(userHandle));
    }

    protected ResolverListController createListController(UserHandle userHandle) {
        UserHandle queryIntentsUser = getQueryIntentsUser(userHandle);
        AbstractResolverComparator resolverComparator = makeResolverComparator(userHandle);
        return new ResolverListController(this, this.mPm, getTargetIntent(), getReferrerPackageName(), this.mLaunchedFromUid, userHandle, resolverComparator, queryIntentsUser);
    }

    private boolean configureContentView() {
        if (this.mMultiProfilePagerAdapter.getActiveListAdapter() == null) {
            throw new IllegalStateException("mMultiProfilePagerAdapter.getCurrentListAdapter() cannot be null.");
        }
        Trace.beginSection("configureContentView");
        if (checkIfNeedFRPWorkaround()) {
            ResolverListAdapter currentListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
            this.mMultiProfilePagerAdapter.showEmptyResolverListEmptyState(currentListAdapter);
            setContentView(getLayoutResource());
            this.mMultiProfilePagerAdapter.setupViewPager((ViewPager) findViewById(R.id.profile_pager));
            maybeCreateHeader(currentListAdapter);
            return false;
        }
        this.mSemShareCommon = new SemShareCommon(getBaseContext(), this.mIntents.get(0), this.mIsDeviceDefault, this.mSupportsAlwaysUseOption, this.mMultiProfilePagerAdapter.getActiveListAdapter().hasFilteredItem(), this.mLaunchedFromUid, this.mExtraIntentList);
        if (this.mSemShareCommon != null && this.mSemShareCommon.isFeatureSupported(SemShareConstants.SUPPORT_LOGGING)) {
            this.mSemShareLogging = new SemShareLogging(getBaseContext());
        }
        this.mSupportButtons = true;
        boolean rebuildCompleted = this.mMultiProfilePagerAdapter.rebuildActiveTab(true) || this.mMultiProfilePagerAdapter.getActiveListAdapter().isTabLoaded();
        if (shouldShowTabs()) {
            boolean rebuildInactiveCompleted = this.mMultiProfilePagerAdapter.rebuildInactiveTab(false) || this.mMultiProfilePagerAdapter.getInactiveListAdapter().isTabLoaded();
            rebuildCompleted = rebuildCompleted && rebuildInactiveCompleted;
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
        boolean result = postRebuildList(rebuildCompleted);
        Trace.endSection();
        return result;
    }

    private void configureMiniResolverContent() {
        this.mLayoutId = R.layout.miniresolver;
        setContentView(this.mLayoutId);
        final DisplayResolveInfo sameProfileResolveInfo = this.mMultiProfilePagerAdapter.getActiveListAdapter().mDisplayList.get(0);
        boolean inWorkProfile = getCurrentProfile() == 1;
        final ResolverListAdapter inactiveAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter();
        final DisplayResolveInfo otherProfileResolveInfo = inactiveAdapter.mDisplayList.get(0);
        ImageView icon = (ImageView) findViewById(16908294);
        Objects.requireNonNull(inactiveAdapter);
        new ResolverListAdapter.LoadIconTask(inactiveAdapter, otherProfileResolveInfo, otherProfileResolveInfo, icon) { // from class: com.android.internal.app.ResolverActivity.5
            final /* synthetic */ ImageView val$icon;
            final /* synthetic */ DisplayResolveInfo val$otherProfileResolveInfo;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(otherProfileResolveInfo);
                this.val$otherProfileResolveInfo = otherProfileResolveInfo;
                this.val$icon = icon;
                Objects.requireNonNull(inactiveAdapter);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.app.ResolverListAdapter.LoadIconTask, android.os.AsyncTask
            public void onPostExecute(Drawable drawable) {
                if (!ResolverActivity.this.isDestroyed()) {
                    this.val$otherProfileResolveInfo.setDisplayIcon(drawable);
                    new ResolverListAdapter.ViewHolder(this.val$icon).bindIcon(this.val$otherProfileResolveInfo);
                }
            }
        }.execute(new Void[0]);
        final CharSequence targetDisplayLabel = otherProfileResolveInfo.getDisplayLabel();
        DevicePolicyResourcesManager devicePolicyResourcesManager = ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources();
        if (inWorkProfile) {
            ((TextView) findViewById(R.id.open_cross_profile)).lambda$setTextAsync$0(devicePolicyResourcesManager.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$configureMiniResolverContent$5;
                    lambda$configureMiniResolverContent$5 = ResolverActivity.this.lambda$configureMiniResolverContent$5(targetDisplayLabel);
                    return lambda$configureMiniResolverContent$5;
                }
            }, targetDisplayLabel));
            ((Button) findViewById(R.id.use_same_profile_browser)).lambda$setTextAsync$0(devicePolicyResourcesManager.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$configureMiniResolverContent$6;
                    lambda$configureMiniResolverContent$6 = ResolverActivity.this.lambda$configureMiniResolverContent$6();
                    return lambda$configureMiniResolverContent$6;
                }
            }));
        } else {
            ((TextView) findViewById(R.id.open_cross_profile)).lambda$setTextAsync$0(devicePolicyResourcesManager.getString(DevicePolicyResources.Strings.Core.MINIRESOLVER_OPEN_IN_WORK, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$configureMiniResolverContent$7;
                    lambda$configureMiniResolverContent$7 = ResolverActivity.this.lambda$configureMiniResolverContent$7(targetDisplayLabel);
                    return lambda$configureMiniResolverContent$7;
                }
            }, targetDisplayLabel));
            ((Button) findViewById(R.id.use_same_profile_browser)).lambda$setTextAsync$0(devicePolicyResourcesManager.getString("Core.MINIRESOLVER_OPEN_IN_PERSONAL", new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda7
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
                ResolverActivity.this.lambda$configureMiniResolverContent$9(sameProfileResolveInfo, view);
            }
        });
        findViewById(R.id.button_open).setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ResolverActivity.this.lambda$configureMiniResolverContent$10(otherProfileResolveInfo, inactiveAdapter, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$configureMiniResolverContent$5(CharSequence targetDisplayLabel) {
        return getString(R.string.miniresolver_open_in_personal, targetDisplayLabel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$configureMiniResolverContent$6() {
        return getString(R.string.miniresolver_use_work_browser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$configureMiniResolverContent$7(CharSequence targetDisplayLabel) {
        return getString(R.string.miniresolver_open_in_work, targetDisplayLabel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$configureMiniResolverContent$8() {
        return getString(R.string.miniresolver_use_personal_browser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$configureMiniResolverContent$9(DisplayResolveInfo sameProfileResolveInfo, View v) {
        safelyStartActivity(sameProfileResolveInfo);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$configureMiniResolverContent$10(DisplayResolveInfo otherProfileResolveInfo, ResolverListAdapter inactiveAdapter, View v) {
        safelyStartActivityAsUser(otherProfileResolveInfo, inactiveAdapter.mResolverListController.getUserHandle());
        finish();
    }

    private boolean shouldUseMiniResolver() {
        if (!this.mIsIntentPicker || this.mMultiProfilePagerAdapter.getActiveListAdapter() == null || this.mMultiProfilePagerAdapter.getInactiveListAdapter() == null) {
            return false;
        }
        List<DisplayResolveInfo> sameProfileList = this.mMultiProfilePagerAdapter.getActiveListAdapter().mDisplayList;
        List<DisplayResolveInfo> otherProfileList = this.mMultiProfilePagerAdapter.getInactiveListAdapter().mDisplayList;
        if (sameProfileList.isEmpty()) {
            Log.d(TAG, "No targets in the current profile");
            return false;
        }
        if (otherProfileList.size() != 1) {
            Log.d(TAG, "Found " + otherProfileList.size() + " resolvers in the other profile");
            return false;
        }
        if (otherProfileList.get(0).getResolveInfo().handleAllWebDataURI) {
            Log.d(TAG, "Other profile is a web browser");
            return false;
        }
        for (DisplayResolveInfo info : sameProfileList) {
            if (!info.getResolveInfo().handleAllWebDataURI) {
                Log.d(TAG, "Non-browser found in this profile");
                return false;
            }
        }
        return true;
    }

    protected boolean postRebuildList(boolean rebuildCompleted) {
        return postRebuildListInternal(rebuildCompleted);
    }

    final boolean postRebuildListInternal(boolean rebuildCompleted) {
        if (rebuildCompleted && maybeAutolaunchActivity()) {
            return true;
        }
        setupViewVisibilities();
        if (shouldShowTabs()) {
            setupProfileTabs();
            return false;
        }
        return false;
    }

    private int isPermissionGranted(String permission, int uid) {
        return ActivityManager.checkComponentPermission(permission, uid, -1, true);
    }

    private boolean maybeAutolaunchActivity() {
        int numberOfProfiles = this.mMultiProfilePagerAdapter.getItemCount();
        if (numberOfProfiles == 1 && maybeAutolaunchIfSingleTarget()) {
            return true;
        }
        if (numberOfProfiles == 2 && this.mMultiProfilePagerAdapter.getActiveListAdapter().isTabLoaded() && this.mMultiProfilePagerAdapter.getInactiveListAdapter().isTabLoaded()) {
            if (maybeAutolaunchIfNoAppsOnInactiveTab() || maybeAutolaunchIfCrossProfileSupported()) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean maybeAutolaunchIfSingleTarget() {
        int count = this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredCount();
        if (count != 1 || this.mMultiProfilePagerAdapter.getActiveListAdapter().getOtherProfile() != null || this.mMultiProfilePagerAdapter.getActiveListAdapter().getDisplayResolveInfo(0).getSimilarList().size() > 1) {
            return false;
        }
        TargetInfo target = this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(0, false);
        if (!shouldAutoLaunchSingleChoice(target)) {
            return false;
        }
        safelyStartActivity(target);
        finish();
        return true;
    }

    private boolean maybeAutolaunchIfNoAppsOnInactiveTab() {
        int count = this.mMultiProfilePagerAdapter.getActiveListAdapter().getUnfilteredCount();
        if (count != 1) {
            return false;
        }
        ResolverListAdapter inactiveListAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter();
        if (inactiveListAdapter.getUnfilteredCount() != 0) {
            return false;
        }
        TargetInfo target = this.mMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(0, false);
        safelyStartActivity(target);
        finish();
        return true;
    }

    private boolean maybeAutolaunchIfCrossProfileSupported() {
        ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        int count = activeListAdapter.getUnfilteredCount();
        if (count != 1) {
            return false;
        }
        ResolverListAdapter inactiveListAdapter = this.mMultiProfilePagerAdapter.getInactiveListAdapter();
        if (inactiveListAdapter.getUnfilteredCount() != 1) {
            return false;
        }
        TargetInfo activeProfileTarget = activeListAdapter.targetInfoForPosition(0, false);
        TargetInfo inactiveProfileTarget = inactiveListAdapter.targetInfoForPosition(0, false);
        if (!Objects.equals(activeProfileTarget.getResolvedComponentName(), inactiveProfileTarget.getResolvedComponentName()) || !shouldAutoLaunchSingleChoice(activeProfileTarget)) {
            return false;
        }
        String packageName = activeProfileTarget.getResolvedComponentName().getPackageName();
        if (!canAppInteractCrossProfiles(packageName)) {
            return false;
        }
        DevicePolicyEventLogger.createEvent(161).setBoolean(activeListAdapter.getUserHandle().equals(getPersonalProfileUserHandle())).setStrings(getMetricsCategory()).write();
        safelyStartActivity(activeProfileTarget);
        finish();
        return true;
    }

    private boolean canAppInteractCrossProfiles(String packageName) {
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(packageName, 0);
            if (!applicationInfo.crossProfile) {
                return false;
            }
            int packageUid = applicationInfo.uid;
            return isPermissionGranted(Manifest.permission.INTERACT_ACROSS_USERS_FULL, packageUid) == 0 || isPermissionGranted(Manifest.permission.INTERACT_ACROSS_USERS, packageUid) == 0 || PermissionChecker.checkPermissionForPreflight(this, Manifest.permission.INTERACT_ACROSS_PROFILES, -1, packageUid, packageName) == 0;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package " + packageName + " does not exist on current user.");
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
        Button personalButton = (Button) getLayoutInflater().inflate(R.layout.sem_resolver_profile_tab_button, (ViewGroup) tabHost.getTabWidget(), false);
        personalButton.lambda$setTextAsync$0(getPersonalTabLabel());
        personalButton.setContentDescription(getPersonalTabAccessibilityLabel());
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("personal").setContent(R.id.profile_pager).setIndicator(personalButton);
        tabHost.addTab(tabSpec);
        Button workButton = (Button) getLayoutInflater().inflate(R.layout.sem_resolver_profile_tab_button, (ViewGroup) tabHost.getTabWidget(), false);
        workButton.lambda$setTextAsync$0(getWorkTabLabel());
        workButton.setContentDescription(getWorkTabAccessibilityLabel());
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec(TAB_TAG_WORK).setContent(R.id.profile_pager).setIndicator(workButton);
        tabHost.addTab(tabSpec2);
        TabWidget tabWidget = tabHost.getTabWidget();
        tabWidget.setVisibility(0);
        updateActiveTabStyle(tabHost);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda17
            @Override // android.widget.TabHost.OnTabChangeListener
            public final void onTabChanged(String str) {
                ResolverActivity.this.lambda$setupProfileTabs$11(tabHost, viewPager, str);
            }
        });
        viewPager.setVisibility(0);
        tabHost.setCurrentTab(this.mMultiProfilePagerAdapter.getCurrentPage());
        this.mMultiProfilePagerAdapter.setOnProfileSelectedListener(new AbstractMultiProfilePagerAdapter.OnProfileSelectedListener() { // from class: com.android.internal.app.ResolverActivity.6
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener
            public void onProfileSelected(int index) {
                tabHost.setCurrentTab(index);
                ResolverActivity.this.resetButtonBar();
                ResolverActivity.this.resetCheckedItem();
            }

            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener
            public void onProfilePageStateChanged(int state) {
                ResolverActivity.this.onHorizontalSwipeStateChanged(state);
            }
        });
        this.mOnSwitchOnWorkSelectedListener = new AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda18
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener
            public final void onSwitchOnWorkSelected() {
                ResolverActivity.lambda$setupProfileTabs$12(TabHost.this);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupProfileTabs$11(TabHost tabHost, ViewPager viewPager, String tabId) {
        updateActiveTabStyle(tabHost);
        if ("personal".equals(tabId)) {
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
        View workTab = tabHost.getTabWidget().getChildAt(1);
        workTab.setFocusable(true);
        workTab.setFocusableInTouchMode(true);
        workTab.requestFocus();
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

    void onHorizontalSwipeStateChanged(int state) {
    }

    private void maybeHideDivider() {
        View divider;
        if (!this.mIsIntentPicker || (divider = findViewById(R.id.divider)) == null) {
            return;
        }
        divider.setVisibility(8);
    }

    protected void onProfileTabSelected() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetCheckedItem() {
        if (!this.mIsIntentPicker) {
            return;
        }
        this.mLastSelected = -1;
        AbsListView inactiveListView = (AbsListView) this.mMultiProfilePagerAdapter.getInactiveAdapterView();
        if (inactiveListView.getCheckedItemCount() > 0) {
            inactiveListView.setItemChecked(inactiveListView.getCheckedItemPosition(), false);
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
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_TAB_ACCESSIBILITY, new Supplier() { // from class: com.android.internal.app.ResolverActivity$$ExternalSyntheticLambda16
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

    private static int getAttrColor(Context context, int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        int colorAccent = ta.getColor(0, 0);
        ta.recycle();
        return colorAccent;
    }

    private void updateActiveTabStyle(TabHost tabHost) {
        int currentTab = tabHost.getCurrentTab();
        TextView selected = (TextView) tabHost.getTabWidget().getChildAt(currentTab);
        TextView unselected = (TextView) tabHost.getTabWidget().getChildAt(1 - currentTab);
        selected.setSelected(true);
        unselected.setSelected(false);
    }

    private void setupViewVisibilities() {
        ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (!this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(activeListAdapter)) {
            addUseDifferentAppLabelIfNecessary(activeListAdapter);
        }
    }

    public void addUseDifferentAppLabelIfNecessary(ResolverListAdapter adapter) {
    }

    private void setupAdapterListView(ListView listView, ItemClickListener listener) {
        listView.setOnItemClickListener(listener);
        listView.setOnItemLongClickListener(listener);
        if (this.mSupportsAlwaysUseOption) {
            listView.setChoiceMode(1);
        }
    }

    private void maybeCreateHeader(ResolverListAdapter listAdapter) {
        CharSequence title;
        TextView titleView;
        if (this.mHeaderCreatorUser != null && !listAdapter.getUserHandle().equals(this.mHeaderCreatorUser)) {
            return;
        }
        if (!shouldShowTabs() && listAdapter.getCount() == 0 && listAdapter.getPlaceholderCount() == 0 && (titleView = (TextView) findViewById(16908310)) != null) {
            titleView.setVisibility(8);
        }
        if (this.mForceTitleHide) {
            TextView titleView2 = (TextView) findViewById(16908310);
            if (titleView2 != null) {
                titleView2.setVisibility(8);
                return;
            }
            return;
        }
        if (this.mTitle != null) {
            title = this.mTitle;
        } else {
            title = getTitleForAction(getTargetIntent(), this.mDefaultTitleResId);
        }
        if (!TextUtils.isEmpty(title)) {
            TextView titleView3 = (TextView) findViewById(16908310);
            if (titleView3 != null) {
                titleView3.lambda$setTextAsync$0(title);
            }
            setTitle(title);
            semSetTextSizeByMaxFontScale(titleView3, R.dimen.sem_resolver_pagemode_titlepanel_text_size);
        }
        this.mHeaderCreatorUser = listAdapter.getUserHandle();
    }

    protected void resetButtonBar() {
        if (!this.mSupportsAlwaysUseOption || !this.mSupportButtons) {
            return;
        }
        ViewGroup buttonLayout = (ViewGroup) findViewById(R.id.button_bar);
        if (buttonLayout == null) {
            Log.e(TAG, "Layout unexpectedly does not have a button bar");
            return;
        }
        ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        View buttonBarDivider = findViewById(R.id.resolver_button_bar_divider);
        if (!useLayoutWithDefault()) {
            int inset = this.mSystemWindowInsets != null ? this.mSystemWindowInsets.bottom : 0;
            buttonLayout.setPadding(buttonLayout.getPaddingLeft(), buttonLayout.getPaddingTop(), buttonLayout.getPaddingRight(), getResources().getDimensionPixelSize(R.dimen.resolver_button_bar_spacing) + inset);
        }
        if (activeListAdapter.isTabLoaded() && this.mMultiProfilePagerAdapter.shouldShowEmptyStateScreen(activeListAdapter) && !useLayoutWithDefault()) {
            buttonLayout.setVisibility(4);
            if (buttonBarDivider != null) {
                buttonBarDivider.setVisibility(4);
            }
            setButtonBarIgnoreOffset(false);
            return;
        }
        if (buttonBarDivider != null) {
            buttonBarDivider.setVisibility(0);
        }
        buttonLayout.setVisibility(0);
        setButtonBarIgnoreOffset(true);
        this.mOnceButton = (Button) buttonLayout.findViewById(R.id.button_once);
        this.mAlwaysButton = (Button) buttonLayout.findViewById(R.id.button_always);
        if (this.mOnceButton != null && this.mAlwaysButton != null) {
            this.mOnceButton.setContentDescription(getString(R.string.sem_resolver_button_just_once_accessibility));
            this.mAlwaysButton.setContentDescription(getString(R.string.sem_resolver_button_always_accessibility));
        }
        resetAlwaysOrOnceButtonBar();
    }

    private void setButtonBarIgnoreOffset(boolean ignoreOffset) {
        View buttonBarContainer = findViewById(R.id.button_bar_container);
        if (buttonBarContainer != null) {
            ResolverDrawerLayout.LayoutParams layoutParams = (ResolverDrawerLayout.LayoutParams) buttonBarContainer.getLayoutParams();
            layoutParams.ignoreOffset = ignoreOffset;
            buttonBarContainer.setLayoutParams(layoutParams);
        }
    }

    private void resetAlwaysOrOnceButtonBar() {
        int btnTextSize = getResources().getDimensionPixelSize(R.dimen.sem_dialog_button_text_size);
        this.mAlwaysButton.setTextSize(0, btnTextSize);
        semCheckMaxFontScale(this.mAlwaysButton, btnTextSize);
        this.mOnceButton.setTextSize(0, btnTextSize);
        semCheckMaxFontScale(this.mOnceButton, btnTextSize);
        if (this.mSemShareCommon != null && this.mSemShareCommon.isFeatureSupported(SemShareConstants.SUPPORT_SHOW_BUTTON_SHAPES)) {
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
        AbsListView currentAdapterView = (AbsListView) this.mMultiProfilePagerAdapter.getActiveAdapterView();
        if (currentAdapterView != null && this.mSupportButtons && currentAdapterView.getCheckedItemPosition() != -1) {
            setAlwaysButtonEnabled(true, currentAdapterView.getCheckedItemPosition(), true);
            this.mOnceButton.setEnabled(true);
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean useLayoutWithDefault() {
        return false;
    }

    protected void setRetainInOnStop(boolean retainInOnStop) {
        this.mRetainInOnStop = retainInOnStop;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean resolveInfoMatch(ResolveInfo lhs, ResolveInfo rhs) {
        return lhs == null ? rhs == null : lhs.activityInfo == null ? rhs.activityInfo == null : Objects.equals(lhs.activityInfo.name, rhs.activityInfo.name) && Objects.equals(lhs.activityInfo.packageName, rhs.activityInfo.packageName) && Objects.equals(getResolveInfoUserHandle(lhs, this.mMultiProfilePagerAdapter.getActiveListAdapter().getUserHandle()), getResolveInfoUserHandle(rhs, this.mMultiProfilePagerAdapter.getActiveListAdapter().getUserHandle()));
    }

    protected String getMetricsCategory() {
        return METRICS_CATEGORY_RESOLVER;
    }

    public void onHandlePackagesChanged(ResolverListAdapter listAdapter) {
        if (listAdapter == this.mMultiProfilePagerAdapter.getActiveListAdapter()) {
            if (listAdapter.getUserHandle().equals(getWorkProfileUserHandle()) && this.mQuietModeManager.isWaitingToEnableWorkProfile()) {
                return;
            }
            this.mAppIconTheme = Settings.System.getString(getContentResolver(), Settings.System.SEM_CURRENT_APP_ICON_PACKAGE);
            this.mOldItemCount = this.mMultiProfilePagerAdapter.getActiveListAdapter().getPlaceholderCount();
            boolean listRebuilt = this.mMultiProfilePagerAdapter.rebuildActiveTab(true);
            if (listRebuilt) {
                ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
                activeListAdapter.notifyDataSetChanged();
                if (activeListAdapter.getCount() == 0 && !inactiveListAdapterHasItems()) {
                    finish();
                }
                View adapterView = this.mMultiProfilePagerAdapter.getActiveAdapterView();
                if (adapterView instanceof GridView) {
                    int itemCount = activeListAdapter.getCount();
                    ((GridView) adapterView).setNumColumns(Math.min(itemCount, this.mMaxColumns));
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
        return new BroadcastReceiver() { // from class: com.android.internal.app.ResolverActivity.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (!TextUtils.equals(action, Intent.ACTION_USER_UNLOCKED) && !TextUtils.equals(action, Intent.ACTION_MANAGED_PROFILE_UNAVAILABLE) && !TextUtils.equals(action, Intent.ACTION_MANAGED_PROFILE_AVAILABLE)) {
                    return;
                }
                int userId = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (userId != ResolverActivity.this.getWorkProfileUserHandle().getIdentifier()) {
                    return;
                }
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
        };
    }

    public static final class ResolvedComponentInfo {
        private boolean mFixedAtTop;
        private boolean mPinned;
        public final ComponentName name;
        private final List<Intent> mIntents = new ArrayList();
        private final List<ResolveInfo> mResolveInfos = new ArrayList();
        private ArrayList<ResolvedComponentInfo> mSimilarList = new ArrayList<>();

        public ResolvedComponentInfo(ComponentName name, Intent intent, ResolveInfo info) {
            this.name = name;
            add(intent, info);
        }

        public void add(Intent intent, ResolveInfo info) {
            this.mIntents.add(intent);
            this.mResolveInfos.add(info);
        }

        public int getCount() {
            return this.mIntents.size();
        }

        public Intent getIntentAt(int index) {
            if (index >= 0) {
                return this.mIntents.get(index);
            }
            return null;
        }

        public ResolveInfo getResolveInfoAt(int index) {
            if (index >= 0) {
                return this.mResolveInfos.get(index);
            }
            return null;
        }

        public int findIntent(Intent intent) {
            int N = this.mIntents.size();
            for (int i = 0; i < N; i++) {
                if (intent.equals(this.mIntents.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public int findResolveInfo(ResolveInfo info) {
            int N = this.mResolveInfos.size();
            for (int i = 0; i < N; i++) {
                if (info.equals(this.mResolveInfos.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public boolean isPinned() {
            return this.mPinned;
        }

        public void setPinned(boolean pinned) {
            this.mPinned = pinned;
        }

        public boolean isFixedAtTop() {
            return this.mFixedAtTop;
        }

        public void setFixedAtTop(boolean isFixedAtTop) {
            this.mFixedAtTop = isFixedAtTop;
        }

        public ArrayList<ResolvedComponentInfo> getSimilarList() {
            return this.mSimilarList;
        }
    }

    class ItemClickListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        ItemClickListener() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position < 0 || ResolverActivity.this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(position, true) == null) {
                return;
            }
            DisplayResolveInfo displayInfo = ResolverActivity.this.mMultiProfilePagerAdapter.getActiveListAdapter().getDisplayResolveInfo(position);
            ViewPager viewPager = (ViewPager) ResolverActivity.this.findViewById(R.id.profile_pager);
            boolean z = false;
            if (displayInfo != null && displayInfo.getSimilarList().size() > 1) {
                ResolverActivity.this.mSecondDepth = true;
                if (ResolverActivity.this.mAlwaysButton != null) {
                    ResolverActivity.this.mOnceButton.setEnabled(true);
                    ResolverActivity.this.mAlwaysButton.setEnabled(true);
                }
                viewPager.findViewById(R.id.resolver_list).setVisibility(8);
                viewPager.findViewById(R.id.sem_resolver_second_depth_recycler_view).setVisibility(0);
                TextView titleView = (TextView) ResolverActivity.this.findViewById(16908310);
                if (titleView != null) {
                    titleView.setText(R.string.sem_resolver_choose_what_to_do);
                }
                ResolverActivity.this.mSelectTaskRecyclerView = (RecyclerView) ResolverActivity.this.findViewById(R.id.sem_resolver_second_depth_recycler_view);
                ResolverActivity.this.mSemSelectTaskListAdapter = new SemSelectTaskListAdapter(displayInfo.getSimilarList(), ResolverActivity.this.mMultiProfilePagerAdapter, new ResolverActivity$$ExternalSyntheticLambda0(ResolverActivity.this));
                ResolverActivity.this.mSelectTaskRecyclerView.setLayoutManager(new LinearLayoutManager(ResolverActivity.this.mContext));
                ResolverActivity.this.mSelectTaskRecyclerView.setAdapter(ResolverActivity.this.mSemSelectTaskListAdapter);
                return;
            }
            viewPager.findViewById(R.id.resolver_list).setVisibility(0);
            AbsListView currentAdapterView = (AbsListView) ResolverActivity.this.mMultiProfilePagerAdapter.getActiveAdapterView();
            int checkedPos = currentAdapterView.getCheckedItemPosition();
            boolean hasValidSelection = checkedPos != -1;
            if (!ResolverActivity.this.useLayoutWithDefault() && ResolverActivity.this.mSupportButtons && ((!hasValidSelection || ResolverActivity.this.mLastSelected != checkedPos) && ResolverActivity.this.mAlwaysButton != null)) {
                ResolverActivity.this.setAlwaysButtonEnabled(hasValidSelection, checkedPos, true);
                ResolverActivity.this.mOnceButton.setEnabled(hasValidSelection);
                if (hasValidSelection) {
                    currentAdapterView.smoothScrollToPosition(checkedPos);
                    ResolverActivity.this.mOnceButton.requestFocus();
                }
                ResolverActivity.this.mLastSelected = checkedPos;
                return;
            }
            ResolverActivity resolverActivity = ResolverActivity.this;
            if (ResolverActivity.this.mSupportsAlwaysUseOption && !ResolverActivity.this.mSupportButtons) {
                z = true;
            }
            resolverActivity.startSelected(position, z, true);
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (position < 0) {
                return false;
            }
            ResolveInfo ri = ResolverActivity.this.mMultiProfilePagerAdapter.getActiveListAdapter().resolveInfoForPosition(position, true);
            ResolverActivity.this.showTargetDetails(ri);
            return true;
        }
    }

    private boolean getEnterprisePolicyEnabled(Context context, String edmUri, String projectionArgs, String[] selectionArgs) {
        Uri uri = Uri.parse(edmUri);
        Cursor cr = context.getContentResolver().query(uri, null, projectionArgs, selectionArgs, null);
        if (cr == null) {
            return true;
        }
        try {
            cr.moveToFirst();
            return cr.getString(cr.getColumnIndex(projectionArgs)).equals("true");
        } catch (Exception e) {
            Log.e(TAG, "Exception at getEnterprisePolicyEnabled ", e);
            return true;
        } finally {
            cr.close();
        }
    }

    static final boolean isSpecificUriMatch(int match) {
        int match2 = match & IntentFilter.MATCH_CATEGORY_MASK;
        return match2 >= 3145728 && match2 <= 5242880;
    }

    static class PickTargetOptionRequest extends VoiceInteractor.PickOptionRequest {
        public PickTargetOptionRequest(VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] options, Bundle extras) {
            super(prompt, options, extras);
        }

        @Override // android.app.VoiceInteractor.Request
        public void onCancel() {
            super.onCancel();
            ResolverActivity ra = (ResolverActivity) getActivity();
            if (ra != null) {
                ra.mPickOptionRequest = null;
                ra.finish();
            }
        }

        @Override // android.app.VoiceInteractor.PickOptionRequest
        public void onPickOptionResult(boolean finished, VoiceInteractor.PickOptionRequest.Option[] selections, Bundle result) {
            ResolverActivity ra;
            super.onPickOptionResult(finished, selections, result);
            if (selections.length == 1 && (ra = (ResolverActivity) getActivity()) != null) {
                TargetInfo ti = ra.mMultiProfilePagerAdapter.getActiveListAdapter().getItem(selections[0].getIndex());
                if (ra.onTargetSelected(ti, false)) {
                    ra.mPickOptionRequest = null;
                    ra.finish();
                }
            }
        }
    }

    protected void maybeLogProfileChange() {
    }

    protected final UserHandle getQueryIntentsUser(UserHandle userHandle) {
        if (!isLaunchedAsCloneProfile() || !userHandle.equals(getPersonalProfileUserHandle())) {
            return userHandle;
        }
        UserHandle queryIntentsUser = getCloneProfileUserHandle();
        return queryIntentsUser;
    }

    public final List<UserHandle> getResolverRankerServiceUserHandleList(UserHandle userHandle) {
        return getResolverRankerServiceUserHandleListInternal(userHandle);
    }

    protected List<UserHandle> getResolverRankerServiceUserHandleListInternal(UserHandle userHandle) {
        List<UserHandle> userList = new ArrayList<>();
        userList.add(userHandle);
        if (userHandle.equals(getPersonalProfileUserHandle()) && getCloneProfileUserHandle() != null) {
            userList.add(getCloneProfileUserHandle());
        }
        return userList;
    }

    public static UserHandle getResolveInfoUserHandle(ResolveInfo resolveInfo, UserHandle predictedHandle) {
        if (resolveInfo.userHandle == null) {
            Log.e(TAG, "ResolveInfo with null UserHandle found: " + resolveInfo);
        }
        return resolveInfo.userHandle;
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
        CharSequence title;
        TextView titleView;
        if (this.mSecondDepth) {
            this.mSecondDepth = false;
            ViewPager viewPager = (ViewPager) findViewById(R.id.profile_pager);
            viewPager.findViewById(R.id.sem_resolver_second_depth_recycler_view).setVisibility(8);
            viewPager.findViewById(R.id.resolver_list).setVisibility(0);
            if (this.mTitle != null) {
                title = this.mTitle;
            } else {
                title = getTitleForAction(getTargetIntent(), this.mDefaultTitleResId);
            }
            if (!TextUtils.isEmpty(title) && (titleView = (TextView) findViewById(16908310)) != null) {
                titleView.lambda$setTextAsync$0(title);
                return;
            }
            return;
        }
        semFinishAfterAnimation();
    }

    @Override // android.app.Activity
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        this.mMultiProfilePagerAdapter.getActiveListAdapter().semForceHandlePackagesChanged();
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void semOnForceHandlePackagesChanged(ResolverListAdapter listAdapter) {
        if (listAdapter == this.mMultiProfilePagerAdapter.getActiveListAdapter()) {
            boolean listRebuilt = this.mMultiProfilePagerAdapter.rebuildActiveTab(true);
            if (listRebuilt) {
                ResolverListAdapter activeListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
                activeListAdapter.notifyDataSetChanged();
                View adapterView = this.mMultiProfilePagerAdapter.getActiveAdapterView();
                if (adapterView instanceof GridView) {
                    int itemCount = listAdapter.getCount();
                    this.mMaxColumns = getResources().getInteger(R.integer.config_maxResolverActivityColumns);
                    ((GridView) adapterView).setNumColumns(Math.min(itemCount, this.mMaxColumns));
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
    public void semSetNeedSortAfterPinned(boolean needUpdate) {
        this.mNeedUpdateAfterPinned = needUpdate;
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
    public boolean semIsOverlayThemesEnabled() {
        return true;
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void semSetNeedSortingInRebuildList(boolean needSort) {
        this.mMultiProfilePagerAdapter.semSetNeedSortingInRebuildList(needSort);
    }

    @Override // com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean semIsNeedSortingInRebuildList() {
        return this.mMultiProfilePagerAdapter.semIsNeedSortingInRebuildList();
    }

    private void semCheckMaxFontScale(TextView textview, int baseSize) {
        float currentFontScale = getResources().getConfiguration().fontScale;
        if (currentFontScale > 1.2f) {
            float scaleBase = baseSize / currentFontScale;
            textview.setTextSize(0, 1.2f * scaleBase);
        }
    }

    void semSetTextSizeByMaxFontScale(TextView textview, int resId) {
        if (textview != null) {
            int defaultTextSize = getResources().getDimensionPixelSize(resId);
            textview.setTextSize(0, defaultTextSize * getFontScale());
        }
    }

    float getFontScale() {
        float fontScale = getResources().getConfiguration().fontScale;
        if (fontScale > 1.2f) {
            return 1.2f;
        }
        return fontScale;
    }

    private void semTransitionOverride(Context context, int type) {
        if (!this.mIsPopOver) {
            if (isInMultiWindowMode() || this.mIsDeskTopMode) {
                overridePendingTransition(R.anim.sem_resolver_fade_in, R.anim.sem_resolver_fade_out);
            } else if (type == 0) {
                overridePendingTransition(R.anim.sem_resolver_panel_enter, 0);
            } else {
                overridePendingTransition(R.anim.sem_resolver_finish_panel_enter, R.anim.sem_resolver_exit);
            }
        }
    }

    private void semSafelyStartActivtyAfterAnimation(final TargetInfo cti) {
        View v = findViewById(R.id.contentPanel);
        if (this.mMultiParent == null || this.mGalleryRecyclerView == null) {
            if (v == null || isInMultiWindowMode() || this.mIsDeskTopMode || this.mIsPopOver) {
                safelyStartActivity(cti);
                return;
            }
        } else {
            v = this.mMultiParent;
        }
        this.mExitAnimator = createExitAnimation(v);
        this.mExitAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.internal.app.ResolverActivity.8
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ResolverActivity.this.mExitAnimator = null;
                ResolverActivity.this.safelyStartActivity(cti);
                ResolverActivity.this.finish();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.mExitAnimator.start();
    }

    private Animator createExitAnimation(View v) {
        int height = getResources().getDisplayMetrics().heightPixels;
        Animator aniY = ObjectAnimator.ofFloat(v, "translationY", 0.0f, height);
        aniY.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f));
        aniY.setDuration(330L);
        aniY.start();
        return aniY;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semFinishAfterAnimation() {
        View v = findViewById(R.id.contentPanel);
        if (this.mMultiParent == null || this.mGalleryRecyclerView == null) {
            if (v == null || isInMultiWindowMode() || this.mIsDeskTopMode || this.mIsPopOver) {
                finish();
                return;
            }
        } else {
            v = this.mMultiParent;
        }
        if (this.mExitAnimator != null && this.mExitAnimator.isStarted()) {
            return;
        }
        this.mExitAnimator = createExitAnimation(v);
        this.mExitAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.internal.app.ResolverActivity.9
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ResolverActivity.this.mExitAnimator = null;
                ResolverActivity.this.finish();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.mExitAnimator.start();
    }

    private boolean checkIfNeedFRPWorkaround() {
        boolean userSetupComplete = Settings.Secure.getInt(getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 1) != 0;
        ResolverListAdapter currentListAdapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
        if (userSetupComplete || this.mSupportsAlwaysUseOption || currentListAdapter.hasFilteredItem()) {
            return false;
        }
        Log.i(TAG, "Blocked for security reason!! Setup is not completed!!");
        return true;
    }

    private void semSetupAdapterListView(AbsListView listView, ItemClickListener listener) {
        listView.setOnItemClickListener(listener);
        listView.setOnItemLongClickListener(listener);
        if (listView instanceof GridView) {
            ResolverListAdapter adapter = this.mMultiProfilePagerAdapter.getActiveListAdapter();
            int itemCount = adapter.getCount();
            Log.d(TAG, "ActiveProfile : " + this.mMultiProfilePagerAdapter.getActiveListAdapter().toString() + "itemCount : " + itemCount);
            ((GridView) listView).setNumColumns(Math.min(itemCount, this.mMaxColumns));
            for (int i = 0; i < adapter.getCount(); i++) {
                Log.d(TAG, adapter.getItem(i).getResolvedComponentName().toString());
            }
        }
        if (this.mSupportsAlwaysUseOption) {
            listView.setChoiceMode(1);
        }
    }

    private void semCreateAndShowTipsPopup(View anchor) {
        this.mTipsDescriptionPopup = new SemTipPopup(anchor);
        this.mTipsDescriptionPopup.setExpanded(true);
        this.mTipsDescriptionPopup.setMessage(getString(R.string.sem_resolver_go_to_settings_tips_description));
        int i = 2;
        int[] anchorPos = new int[2];
        anchor.getLocationInWindow(anchorPos);
        this.mTipsDescriptionPopup.setTargetPosition(anchorPos[0] + (this.mTipsIcon.getWidth() / 2), anchorPos[1] + this.mTipsIcon.getHeight() + getResources().getDimensionPixelSize(R.dimen.sem_resolver_tips_popup_yoffset));
        SemTipPopup semTipPopup = this.mTipsDescriptionPopup;
        if (anchor.getLayoutDirection() != 0) {
            i = 3;
        }
        semTipPopup.show(i);
    }

    private boolean privateSpaceEnabled() {
        return this.mIsIntentPicker && com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.allowResolverSheetForPrivateSpace() && android.multiuser.Flags.enablePrivateSpaceFeatures();
    }

    public static class AppListAccessibilityDelegate extends View.AccessibilityDelegate {
        private final View mBottomBar;
        private final ResolverDrawerLayout mDrawer;
        private final Rect mRect = new Rect();

        public AppListAccessibilityDelegate(ResolverDrawerLayout drawer) {
            this.mDrawer = drawer;
            this.mBottomBar = this.mDrawer.findViewById(R.id.button_bar_container);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
            boolean result = super.onRequestSendAccessibilityEvent(host, child, event);
            if (result && event.getEventType() == 32768 && this.mDrawer.isCollapsed()) {
                child.getBoundsOnScreen(this.mRect);
                int childTop = this.mRect.top;
                int childBottom = this.mRect.bottom;
                this.mDrawer.getBoundsOnScreen(this.mRect, true);
                int bottomBarHeight = this.mBottomBar == null ? 0 : this.mBottomBar.getHeight();
                int drawerTop = this.mRect.top;
                int drawerBottom = this.mRect.bottom - bottomBarHeight;
                if (drawerTop > childTop || childBottom > drawerBottom) {
                    this.mDrawer.setCollapsed(false);
                }
            }
            return result;
        }
    }
}
