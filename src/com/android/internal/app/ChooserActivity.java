package com.android.internal.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IUriGrantsManager;
import android.app.SharedElementCallback;
import android.app.UriGrantsManager;
import android.app.admin.DevicePolicyResources;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionManager;
import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.chooser.ChooserTarget;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.HashedStringCache;
import android.util.Log;
import android.util.PluralsMessageFormatter;
import android.util.Size;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.ChooserListAdapter;
import com.android.internal.app.NoCrossProfileEmptyStateProvider;
import com.android.internal.app.ResolverListAdapter;
import com.android.internal.app.chooser.ChooserTargetInfo;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.MultiDisplayResolveInfo;
import com.android.internal.app.chooser.NotSelectableTargetInfo;
import com.android.internal.app.chooser.SelectableTargetInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.android.internal.config.sysui.SystemUiDeviceConfigFlags;
import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.service.chooser.Flags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.widget.GridLayoutManager;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.ResolverDrawerLayout;
import com.android.internal.widget.ViewPager;
import com.google.android.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URISyntaxException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/* loaded from: classes5.dex */
public class ChooserActivity extends ResolverActivity implements ChooserListAdapter.ChooserListCommunicator, SelectableTargetInfo.SelectableTargetInfoCommunicator {
    public static final String APP_PREDICTION_INTENT_FILTER_KEY = "intent_filter";
    private static final int APP_PREDICTION_SHARE_TARGET_QUERY_PACKAGE_LIMIT = 20;
    private static final String APP_PREDICTION_SHARE_UI_SURFACE = "share";
    private static final String CHIP_ICON_METADATA_KEY = "android.service.chooser.chip_icon";
    private static final String CHIP_LABEL_METADATA_KEY = "android.service.chooser.chip_label";
    public static final String CHOOSER_TARGET = "chooser_target";
    protected static final int CONTENT_PREVIEW_FILE = 2;
    protected static final int CONTENT_PREVIEW_IMAGE = 1;
    protected static final int CONTENT_PREVIEW_TEXT = 3;
    private static final boolean DEBUG = true;
    private static final boolean DEFAULT_IS_NEARBY_SHARE_FIRST_TARGET_IN_RANKED_APP = false;
    private static final int DEFAULT_LIST_VIEW_UPDATE_DELAY_MS = 0;
    private static final int DEFAULT_SALT_EXPIRATION_DAYS = 7;
    private static final float DIRECT_SHARE_EXPANSION_RATE = 0.78f;
    public static final String EXTRA_PRIVATE_RETAIN_IN_ON_STOP = "com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP";
    public static final String FIRST_IMAGE_PREVIEW_TRANSITION_NAME = "screenshot_preview_image";
    private static final String IMAGE_EDITOR_SHARED_ELEMENT = "screenshot_preview_image";
    public static final String LAUNCH_LOCATION_DIRECT_SHARE = "direct_share";
    private static final int MAX_EXTRA_CHOOSER_TARGETS = 2;
    private static final int MAX_EXTRA_INITIAL_INTENTS = 2;
    private static final int MAX_LOG_RANK_POSITION = 12;
    private static final int NO_DIRECT_SHARE_ANIM_IN_MILLIS = 200;
    private static final String PINNED_SHARED_PREFS_NAME = "chooser_pin_settings";
    private static final String PLURALS_COUNT = "count";
    private static final String PLURALS_FILE_NAME = "file_name";
    private static final String PREF_NUM_SHEET_EXPANSIONS = "pref_num_sheet_expansions";
    private static final int SCROLL_STATUS_IDLE = 0;
    private static final int SCROLL_STATUS_SCROLLING_HORIZONTAL = 2;
    private static final int SCROLL_STATUS_SCROLLING_VERTICAL = 1;
    public static final int SELECTION_TYPE_APP = 2;
    public static final int SELECTION_TYPE_COPY = 4;
    public static final int SELECTION_TYPE_EDIT = 6;
    public static final int SELECTION_TYPE_NEARBY = 5;
    public static final int SELECTION_TYPE_SERVICE = 1;
    public static final int SELECTION_TYPE_STANDARD = 3;
    private static final String SHARED_TEXT_KEY = "shared_text";
    private static final String SHORTCUT_TARGET = "shortcut_target";
    private static final String TAG = "ChooserActivity";
    private static final String TARGET_DETAILS_FRAGMENT_TAG = "targetDetailsFragment";
    public static final int TARGET_TYPE_CHOOSER_TARGET = 1;
    public static final int TARGET_TYPE_DEFAULT = 0;
    public static final int TARGET_TYPE_SHORTCUTS_FROM_PREDICTION_SERVICE = 3;
    public static final int TARGET_TYPE_SHORTCUTS_FROM_SHORTCUT_MANAGER = 2;
    private static final int URI_PERMISSION_INTENT_FLAGS = 195;
    private static final boolean USE_PREDICTION_MANAGER_FOR_SHARE_ACTIVITIES = true;
    private ChooserTarget[] mCallerChooserTargets;
    protected ChooserActivityLogger mChooserActivityLogger;
    private final ChooserHandler mChooserHandler;
    protected ChooserMultiProfilePagerAdapter mChooserMultiProfilePagerAdapter;
    private long mChooserShownTime;
    private IntentSender mChosenComponentSender;
    private Map<ChooserTarget, AppTarget> mDirectShareAppTargetCache;
    private Map<ChooserTarget, ShortcutInfo> mDirectShareShortcutInfoCache;
    private final EnterTransitionAnimationDelegate mEnterTransitionAnimationDelegate;
    private ComponentName[] mFilteredComponentNames;
    private boolean mIsAppPredictorComponentAvailable;
    protected boolean mIsSuccessfullySelected;
    protected MetricsLogger mMetricsLogger;
    private AppPredictor mPersonalAppPredictor;
    private SharedPreferences mPinnedSharedPrefs;
    private ContentPreviewCoordinator mPreviewCoord;
    private long mQueriedSharingShortcutsTimeMs;
    private Intent mReferrerFillInIntent;
    private IntentSender mRefinementIntentSender;
    private RefinementResultReceiver mRefinementResultReceiver;
    private Bundle mReplacementExtras;
    private boolean mShouldDisplayLandscape;
    private AppPredictor mWorkAppPredictor;
    private int mMaxHashSaltDays = DeviceConfig.getInt("systemui", SystemUiDeviceConfigFlags.HASH_SALT_MAX_DAYS, 7);
    private boolean mIsNearbyShareFirstTargetInRankedApp = DeviceConfig.getBoolean("systemui", SystemUiDeviceConfigFlags.IS_NEARBY_SHARE_FIRST_TARGET_IN_RANKED_APP, false);
    int mListViewUpdateDelayMs = DeviceConfig.getInt("systemui", SystemUiDeviceConfigFlags.SHARESHEET_LIST_VIEW_UPDATE_DELAY, 0);
    private int mCurrAvailableWidth = 0;
    private Insets mLastAppliedInsets = null;
    private int mLastNumberOfChildren = -1;
    private int mMaxTargetsPerRow = 1;
    private int mScrollStatus = 0;
    private boolean mRemoveSharedElements = false;
    private View mContentView = null;

    @Retention(RetentionPolicy.SOURCE)
    private @interface ContentPreviewType {
    }

    protected static final class EmptyTargetInfo extends NotSelectableTargetInfo {
        @Override // com.android.internal.app.chooser.TargetInfo
        public Drawable getDisplayIcon(Context context) {
            return null;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShareTargetType {
    }

    @Override // com.android.internal.app.ResolverActivity
    protected int appliedThemeResId() {
        return R.style.Theme_DeviceDefault_Chooser;
    }

    @Override // com.android.internal.app.ResolverActivity
    public int getLayoutResource() {
        return R.layout.chooser_grid;
    }

    @Override // com.android.internal.app.ResolverActivity
    public void onButtonClick(View view) {
    }

    @Override // com.android.internal.app.ResolverActivity
    protected void resetButtonBar() {
    }

    @Override // com.android.internal.app.ResolverActivity
    protected boolean shouldAddFooterView() {
        return true;
    }

    @Override // com.android.internal.app.ResolverActivity, com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public boolean shouldGetActivityMetadata() {
        return true;
    }

    protected boolean shouldShowStickyContentPreviewWhenEmpty() {
        return false;
    }

    public ChooserActivity() {
        this.mEnterTransitionAnimationDelegate = new EnterTransitionAnimationDelegate();
        this.mChooserHandler = new ChooserHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ContentPreviewCoordinator {
        private static final int IMAGE_FADE_IN_MILLIS = 150;
        private static final int IMAGE_LOAD_INTO_VIEW = 2;
        private static final int IMAGE_LOAD_TIMEOUT = 1;
        private boolean mAtLeastOneLoaded = false;
        private final Handler mHandler = new Handler() { // from class: com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.1
            @Override // android.os.Handler
            public void handleMessage(Message message) throws Resources.NotFoundException {
                int i = message.what;
                if (i == 1) {
                    ContentPreviewCoordinator.this.maybeHideContentPreview();
                    return;
                }
                if (i == 2 && !ChooserActivity.this.isFinishing()) {
                    LoadUriTask loadUriTask = (LoadUriTask) message.obj;
                    RoundedRectImageView roundedRectImageView = (RoundedRectImageView) ContentPreviewCoordinator.this.mParentView.findViewById(loadUriTask.mImageResourceId);
                    if (loadUriTask.mBmp == null) {
                        roundedRectImageView.setVisibility(8);
                        ContentPreviewCoordinator.this.maybeHideContentPreview();
                        return;
                    }
                    ContentPreviewCoordinator.this.mAtLeastOneLoaded = true;
                    roundedRectImageView.setVisibility(0);
                    roundedRectImageView.setAlpha(0.0f);
                    roundedRectImageView.setImageBitmap(loadUriTask.mBmp);
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(roundedRectImageView, "alpha", 0.0f, 1.0f);
                    objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.0f));
                    objectAnimatorOfFloat.setDuration(150L);
                    objectAnimatorOfFloat.start();
                    if (loadUriTask.mExtraCount > 0) {
                        roundedRectImageView.setExtraImageCount(loadUriTask.mExtraCount);
                    }
                    ContentPreviewCoordinator.this.setupPreDrawForSharedElementTransition(roundedRectImageView);
                }
            }
        };
        private boolean mHideParentOnFail;
        private final int mImageLoadTimeoutMillis;
        private final View mParentView;

        class LoadUriTask {
            public final Bitmap mBmp;
            public final int mExtraCount;
            public final int mImageResourceId;
            public final Uri mUri;

            LoadUriTask(ContentPreviewCoordinator contentPreviewCoordinator, int i, Uri uri, int i2, Bitmap bitmap) {
                this.mImageResourceId = i;
                this.mUri = uri;
                this.mExtraCount = i2;
                this.mBmp = bitmap;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setupPreDrawForSharedElementTransition(final View view) {
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.app.ChooserActivity.ContentPreviewCoordinator.2
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (!ChooserActivity.this.mRemoveSharedElements && ChooserActivity.this.isActivityTransitionRunning()) {
                        ChooserActivity.this.getWindow().setWindowAnimations(0);
                    }
                    ChooserActivity.this.mEnterTransitionAnimationDelegate.markImagePreviewReady();
                    return true;
                }
            });
        }

        ContentPreviewCoordinator(View view, boolean z) {
            this.mImageLoadTimeoutMillis = ChooserActivity.this.getResources().getInteger(17694720);
            this.mParentView = view;
            this.mHideParentOnFail = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void loadUriIntoView(final int i, final Uri uri, final int i2) {
            this.mHandler.sendEmptyMessageDelayed(1, this.mImageLoadTimeoutMillis);
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.android.internal.app.ChooserActivity$ContentPreviewCoordinator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    this.f$0.lambda$loadUriIntoView$0(uri, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$loadUriIntoView$0(Uri uri, int i, int i2) throws Resources.NotFoundException {
            int dimensionPixelSize = ChooserActivity.this.getResources().getDimensionPixelSize(R.dimen.chooser_preview_image_max_dimen);
            Bitmap bitmapLoadThumbnail = ChooserActivity.this.loadThumbnail(uri, new Size(dimensionPixelSize, dimensionPixelSize));
            Message messageObtain = Message.obtain();
            messageObtain.what = 2;
            messageObtain.obj = new LoadUriTask(this, i, uri, i2, bitmapLoadThumbnail);
            this.mHandler.sendMessage(messageObtain);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cancelLoads() {
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void maybeHideContentPreview() throws Resources.NotFoundException {
            if (this.mAtLeastOneLoaded) {
                return;
            }
            if (this.mHideParentOnFail) {
                Log.i(ChooserActivity.TAG, "Hiding image preview area. Timed out waiting for preview to load within " + this.mImageLoadTimeoutMillis + "ms.");
                collapseParentView();
                if (ChooserActivity.this.shouldShowTabs()) {
                    ChooserActivity.this.hideStickyContentPreview();
                } else if (ChooserActivity.this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter() != null) {
                    ChooserActivity.this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter().hideContentPreview();
                }
                this.mHideParentOnFail = false;
            }
            ChooserActivity.this.mRemoveSharedElements = true;
            ChooserActivity.this.mEnterTransitionAnimationDelegate.markImagePreviewReady();
        }

        private void collapseParentView() throws Resources.NotFoundException {
            View view = this.mParentView;
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 1073741824));
            view.getLayoutParams().height = 0;
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getTop());
            view.invalidate();
        }
    }

    private class ChooserHandler extends Handler {
        private static final int LIST_VIEW_UPDATE_MESSAGE = 6;
        private static final int SHORTCUT_MANAGER_ALL_SHARE_TARGET_RESULTS = 7;

        private ChooserHandler() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeAllMessages() {
            removeMessages(6);
            removeMessages(7);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ChooserListAdapter listAdapterForUserHandle;
            if (ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveListAdapter() == null || ChooserActivity.this.isDestroyed()) {
                return;
            }
            int i = message.what;
            if (i == 6) {
                Log.d(ChooserActivity.TAG, "LIST_VIEW_UPDATE_MESSAGE; ");
                ChooserActivity.this.mChooserMultiProfilePagerAdapter.getListAdapterForUserHandle((UserHandle) message.obj).refreshListView();
                return;
            }
            if (i == 7) {
                Log.d(ChooserActivity.TAG, "SHORTCUT_MANAGER_ALL_SHARE_TARGET_RESULTS");
                for (ServiceResultInfo serviceResultInfo : (ServiceResultInfo[]) message.obj) {
                    if (serviceResultInfo.resultTargets != null && (listAdapterForUserHandle = ChooserActivity.this.mChooserMultiProfilePagerAdapter.getListAdapterForUserHandle(serviceResultInfo.userHandle)) != null) {
                        listAdapterForUserHandle.addServiceResults(serviceResultInfo.originalTarget, serviceResultInfo.resultTargets, message.arg1, ChooserActivity.this.mDirectShareShortcutInfoCache);
                    }
                }
                ChooserActivity.this.logDirectShareTargetReceived(MetricsProto.MetricsEvent.ACTION_DIRECT_SHARE_TARGETS_LOADED_SHORTCUT_MANAGER);
                ChooserActivity.this.sendVoiceChoicesIfNeeded();
                ChooserActivity.this.getChooserActivityLogger().logSharesheetDirectLoadComplete();
                ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().completeServiceTargetLoading();
                return;
            }
            super.handleMessage(message);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x022c  */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.content.Intent] */
    /* JADX WARN: Type inference failed for: r8v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8, types: [int] */
    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onCreate(Bundle bundle) {
        CharSequence charSequenceExtra;
        Intent[] intentArr;
        int i;
        boolean z;
        Parcelable[] parcelableArrayExtra;
        int i2;
        Intent[] intentArr2;
        ChooserTarget[] chooserTargetArr;
        int i3 = 0;
        boolean z2 = true;
        if (Settings.Secure.getIntForUser(getContentResolver(), "secure_frp_mode", 0, getUserId()) == 1) {
            Log.e(TAG, "Sharing disabled due to active FRP lock.");
            super.onCreate(bundle);
            finish();
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mLatencyTracker.onActionStart(16);
        getChooserActivityLogger().logSharesheetTriggered();
        this.mIsAppPredictorComponentAvailable = isAppPredictionServiceAvailable();
        this.mIsSuccessfullySelected = false;
        ?? intent = getIntent();
        Parcelable parcelableExtra = intent.getParcelableExtra("android.intent.extra.INTENT");
        if (parcelableExtra instanceof Uri) {
            try {
                parcelableExtra = Intent.parseUri(parcelableExtra.toString(), 1);
            } catch (URISyntaxException unused) {
            }
        }
        if (!(parcelableExtra instanceof Intent)) {
            Log.w(TAG, "Target is not an intent: " + parcelableExtra);
            finish();
            super.onCreate(null);
            return;
        }
        Intent intent2 = (Intent) parcelableExtra;
        if (intent2 != null) {
            modifyTargetIntent(intent2);
        }
        Parcelable[] parcelableArrayExtra2 = intent.getParcelableArrayExtra(Intent.EXTRA_ALTERNATE_INTENTS);
        if (parcelableArrayExtra2 != null) {
            boolean z3 = intent2 == null;
            int length = parcelableArrayExtra2.length;
            if (z3) {
                length--;
            }
            Intent[] intentArr3 = new Intent[length];
            for (int i4 = 0; i4 < parcelableArrayExtra2.length; i4++) {
                Parcelable parcelable = parcelableArrayExtra2[i4];
                if (!(parcelable instanceof Intent)) {
                    Log.w(TAG, "EXTRA_ALTERNATE_INTENTS array entry #" + i4 + " is not an Intent: " + parcelableArrayExtra2[i4]);
                    finish();
                    super.onCreate(null);
                    return;
                }
                Intent intent3 = (Intent) parcelable;
                if (i4 == 0 && intent2 == null) {
                    modifyTargetIntent(intent3);
                    intent2 = intent3;
                } else {
                    intentArr3[z3 ? i4 - 1 : i4] = intent3;
                    modifyTargetIntent(intent3);
                }
            }
            setAdditionalTargets(intentArr3);
        }
        this.mReplacementExtras = intent.getBundleExtra(Intent.EXTRA_REPLACEMENT_EXTRAS);
        if (intent2 == null) {
            charSequenceExtra = null;
        } else if (!isSendAction(intent2)) {
            charSequenceExtra = intent.getCharSequenceExtra(Intent.EXTRA_TITLE);
        } else {
            Log.w(TAG, "Ignoring intent's EXTRA_TITLE, deprecated in P. You may wish to set a preview title by using EXTRA_TITLE property of the wrapped EXTRA_INTENT.");
            charSequenceExtra = null;
        }
        int i5 = charSequenceExtra == null ? R.string.chooseActivity : 0;
        Parcelable[] parcelableArrayExtra3 = intent.getParcelableArrayExtra(Intent.EXTRA_INITIAL_INTENTS);
        if (parcelableArrayExtra3 != null) {
            int iMin = Math.min(parcelableArrayExtra3.length, 2);
            intentArr = new Intent[iMin];
            int i6 = 0;
            while (true) {
                i = i3;
                if (i6 >= iMin) {
                    break;
                }
                Parcelable parcelable2 = parcelableArrayExtra3[i6];
                if (!(parcelable2 instanceof Intent)) {
                    Log.w(TAG, "Initial intent #" + i6 + " not an Intent: " + parcelableArrayExtra3[i6]);
                    finish();
                    super.onCreate(null);
                    return;
                }
                Intent intent4 = (Intent) parcelable2;
                modifyTargetIntent(intent4);
                intentArr[i6] = intent4;
                i6++;
                i3 = i;
            }
        } else {
            intentArr = null;
            i = 0;
        }
        this.mReferrerFillInIntent = new Intent().putExtra(Intent.EXTRA_REFERRER, getReferrer());
        this.mChosenComponentSender = (IntentSender) intent.getParcelableExtra(Intent.EXTRA_CHOSEN_COMPONENT_INTENT_SENDER, IntentSender.class);
        this.mRefinementIntentSender = (IntentSender) intent.getParcelableExtra(Intent.EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER, IntentSender.class);
        setSafeForwardingMode(true);
        this.mPinnedSharedPrefs = getPinnedSharedPrefs(this);
        Parcelable[] parcelableArrayExtra4 = intent.getParcelableArrayExtra(Intent.EXTRA_EXCLUDE_COMPONENTS);
        ComponentName nearbySharingComponent = getNearbySharingComponent();
        int i7 = (shouldNearbyShareBeFirstInRankedRow() || nearbySharingComponent == null) ? i : 1;
        if (parcelableArrayExtra4 != null) {
            ComponentName[] componentNameArr = new ComponentName[parcelableArrayExtra4.length + i7];
            int i8 = i;
            while (true) {
                if (i8 >= parcelableArrayExtra4.length) {
                    z = z2;
                    break;
                }
                Parcelable parcelable3 = parcelableArrayExtra4[i8];
                z = z2;
                if (!(parcelable3 instanceof ComponentName)) {
                    Log.w(TAG, "Filtered component #" + i8 + " not a ComponentName: " + parcelableArrayExtra4[i8]);
                    componentNameArr = null;
                    break;
                }
                componentNameArr[i8] = (ComponentName) parcelable3;
                i8++;
                z2 = z;
            }
            if (i7 != 0) {
                componentNameArr[componentNameArr.length - 1] = nearbySharingComponent;
            }
            this.mFilteredComponentNames = componentNameArr;
        } else {
            z = true;
            if (i7 != 0) {
                ComponentName[] componentNameArr2 = new ComponentName[1];
                this.mFilteredComponentNames = componentNameArr2;
                componentNameArr2[i] = nearbySharingComponent;
            }
            parcelableArrayExtra = intent.getParcelableArrayExtra(Intent.EXTRA_CHOOSER_TARGETS);
            if (parcelableArrayExtra == null) {
                i2 = 2;
                int iMin2 = Math.min(parcelableArrayExtra.length, 2);
                ChooserTarget[] chooserTargetArr2 = new ChooserTarget[iMin2];
                int i9 = i;
                while (true) {
                    if (i9 >= iMin2) {
                        chooserTargetArr = chooserTargetArr2;
                        break;
                    }
                    Parcelable parcelable4 = parcelableArrayExtra[i9];
                    if (!(parcelable4 instanceof ChooserTarget)) {
                        Log.w(TAG, "Chooser target #" + i9 + " not a ChooserTarget: " + parcelableArrayExtra[i9]);
                        chooserTargetArr = null;
                        break;
                    }
                    ChooserTarget chooserTargetRemoveIcon = (ChooserTarget) parcelable4;
                    if (!hasValidIcon(chooserTargetRemoveIcon)) {
                        chooserTargetRemoveIcon = removeIcon(chooserTargetRemoveIcon);
                    }
                    chooserTargetArr2[i9] = chooserTargetRemoveIcon;
                    i9++;
                }
                this.mCallerChooserTargets = chooserTargetArr;
            } else {
                i2 = 2;
            }
            this.mMaxTargetsPerRow = getResources().getInteger(R.integer.config_chooser_max_targets_per_row);
            this.mShouldDisplayLandscape = shouldDisplayLandscape(getResources().getConfiguration().orientation);
            int length2 = i;
            setRetainInOnStop(intent.getBooleanExtra("com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP", length2));
            int i10 = i5;
            intentArr2 = intentArr;
            super.onCreate(bundle, intent2, charSequenceExtra, i10, intentArr2, null, false);
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            this.mChooserShownTime = jCurrentTimeMillis2;
            long j = jCurrentTimeMillis2 - jCurrentTimeMillis;
            MetricsLogger metricsLogger = getMetricsLogger();
            LogMaker logMaker = new LogMaker(214);
            if (!isWorkProfile()) {
                i2 = 1;
            }
            metricsLogger.write(logMaker.setSubtype(i2).addTaggedData(MetricsProto.MetricsEvent.FIELD_SHARESHEET_MIMETYPE, intent2.getType()).addTaggedData(MetricsProto.MetricsEvent.FIELD_TIME_TO_APP_TARGETS, Long.valueOf(j)));
            if (this.mResolverDrawerLayout != null) {
                this.mResolverDrawerLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18) throws Resources.NotFoundException {
                        this.f$0.handleLayoutChange(view, i11, i12, i13, i14, i15, i16, i17, i18);
                    }
                });
                if (isSendAction(intent2)) {
                    this.mResolverDrawerLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda5
                        @Override // android.view.View.OnScrollChangeListener
                        public final void onScrollChange(View view, int i11, int i12, int i13, int i14) throws Resources.NotFoundException {
                            this.f$0.handleScroll(view, i11, i12, i13, i14);
                        }
                    });
                }
                this.mResolverDrawerLayout.setOnCollapsedChangedListener(new ResolverDrawerLayout.OnCollapsedChangedListener() { // from class: com.android.internal.app.ChooserActivity.1
                    private boolean mWrittenOnce = false;

                    @Override // com.android.internal.widget.ResolverDrawerLayout.OnCollapsedChangedListener
                    public void onCollapsedChanged(boolean z4) {
                        if (!z4 && !this.mWrittenOnce) {
                            ChooserActivity.this.incrementNumSheetExpansions();
                            this.mWrittenOnce = true;
                        }
                        ChooserActivity.this.getChooserActivityLogger().logSharesheetExpansionChanged(z4);
                    }
                });
            }
            Log.d(TAG, "System Time Cost is " + j);
            ChooserActivityLogger chooserActivityLogger = getChooserActivityLogger();
            String referrerPackageName = getReferrerPackageName();
            String type = intent2.getType();
            ChooserTarget[] chooserTargetArr3 = this.mCallerChooserTargets;
            int length3 = chooserTargetArr3 != null ? length2 : chooserTargetArr3.length;
            if (intentArr2 != null) {
                length2 = intentArr2.length;
            }
            chooserActivityLogger.logShareStarted(259, referrerPackageName, type, length3, length2, isWorkProfile(), findPreferredContentPreview(getTargetIntent(), getContentResolver()), intent2.getAction());
            this.mDirectShareShortcutInfoCache = new HashMap();
            setEnterSharedElementCallback(new SharedElementCallback() { // from class: com.android.internal.app.ChooserActivity.2
                @Override // android.app.SharedElementCallback
                public void onMapSharedElements(List<String> list, Map<String, View> map) {
                    if (ChooserActivity.this.mRemoveSharedElements) {
                        list.remove("screenshot_preview_image");
                        map.remove("screenshot_preview_image");
                    }
                    super.onMapSharedElements(list, map);
                    ChooserActivity.this.mRemoveSharedElements = false;
                }
            });
            this.mEnterTransitionAnimationDelegate.postponeTransition();
        }
        parcelableArrayExtra = intent.getParcelableArrayExtra(Intent.EXTRA_CHOOSER_TARGETS);
        if (parcelableArrayExtra == null) {
        }
        this.mMaxTargetsPerRow = getResources().getInteger(R.integer.config_chooser_max_targets_per_row);
        this.mShouldDisplayLandscape = shouldDisplayLandscape(getResources().getConfiguration().orientation);
        int length22 = i;
        setRetainInOnStop(intent.getBooleanExtra("com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP", length22));
        int i102 = i5;
        intentArr2 = intentArr;
        super.onCreate(bundle, intent2, charSequenceExtra, i102, intentArr2, null, false);
        long jCurrentTimeMillis22 = System.currentTimeMillis();
        this.mChooserShownTime = jCurrentTimeMillis22;
        long j2 = jCurrentTimeMillis22 - jCurrentTimeMillis;
        MetricsLogger metricsLogger2 = getMetricsLogger();
        LogMaker logMaker2 = new LogMaker(214);
        if (!isWorkProfile()) {
        }
        metricsLogger2.write(logMaker2.setSubtype(i2).addTaggedData(MetricsProto.MetricsEvent.FIELD_SHARESHEET_MIMETYPE, intent2.getType()).addTaggedData(MetricsProto.MetricsEvent.FIELD_TIME_TO_APP_TARGETS, Long.valueOf(j2)));
        if (this.mResolverDrawerLayout != null) {
        }
        Log.d(TAG, "System Time Cost is " + j2);
        ChooserActivityLogger chooserActivityLogger2 = getChooserActivityLogger();
        String referrerPackageName2 = getReferrerPackageName();
        String type2 = intent2.getType();
        ChooserTarget[] chooserTargetArr32 = this.mCallerChooserTargets;
        if (chooserTargetArr32 != null) {
        }
        if (intentArr2 != null) {
        }
        chooserActivityLogger2.logShareStarted(259, referrerPackageName2, type2, length3, length22, isWorkProfile(), findPreferredContentPreview(getTargetIntent(), getContentResolver()), intent2.getAction());
        this.mDirectShareShortcutInfoCache = new HashMap();
        setEnterSharedElementCallback(new SharedElementCallback() { // from class: com.android.internal.app.ChooserActivity.2
            @Override // android.app.SharedElementCallback
            public void onMapSharedElements(List<String> list, Map<String, View> map) {
                if (ChooserActivity.this.mRemoveSharedElements) {
                    list.remove("screenshot_preview_image");
                    map.remove("screenshot_preview_image");
                }
                super.onMapSharedElements(list, map);
                ChooserActivity.this.mRemoveSharedElements = false;
            }
        });
        this.mEnterTransitionAnimationDelegate.postponeTransition();
    }

    private AppPredictor setupAppPredictorForUser(UserHandle userHandle, AppPredictor.Callback callback) {
        AppPredictor appPredictorForDirectShareIfEnabled = getAppPredictorForDirectShareIfEnabled(userHandle);
        if (appPredictorForDirectShareIfEnabled == null) {
            return null;
        }
        this.mDirectShareAppTargetCache = new HashMap();
        appPredictorForDirectShareIfEnabled.registerPredictionUpdates(getMainExecutor(), callback);
        return appPredictorForDirectShareIfEnabled;
    }

    private ResolverAppPredictorCallback createAppPredictorCallback(final ChooserListAdapter chooserListAdapter) {
        return new ResolverAppPredictorCallback(new Consumer() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$createAppPredictorCallback$0(chooserListAdapter, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createAppPredictorCallback$0(ChooserListAdapter chooserListAdapter, List list) {
        if (isFinishing() || isDestroyed() || chooserListAdapter.getCount() == 0) {
            return;
        }
        if (list.isEmpty() && shouldQueryShortcutManager(chooserListAdapter.getUserHandle())) {
            queryDirectShareTargets(chooserListAdapter, true);
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AppTarget appTarget = (AppTarget) it.next();
            if (appTarget.getShortcutInfo() != null) {
                arrayList2.add(appTarget);
            }
        }
        for (AppTarget appTarget2 : arrayList2) {
            arrayList.add(new ShortcutManager.ShareShortcutInfo(appTarget2.getShortcutInfo(), new ComponentName(appTarget2.getPackageName(), appTarget2.getClassName())));
        }
        sendShareShortcutInfoList(arrayList, chooserListAdapter, arrayList2, chooserListAdapter.getUserHandle());
    }

    static SharedPreferences getPinnedSharedPrefs(Context context) {
        return context.getSharedPreferences(new File(new File(Environment.getDataUserCePackageDirectory(StorageManager.UUID_PRIVATE_INTERNAL, context.getUserId(), context.getPackageName()), "shared_prefs"), "chooser_pin_settings.xml"), 0);
    }

    @Override // com.android.internal.app.ResolverActivity
    protected AbstractMultiProfilePagerAdapter createMultiProfilePagerAdapter(Intent[] intentArr, List<ResolveInfo> list, boolean z) {
        if (shouldShowTabs()) {
            this.mChooserMultiProfilePagerAdapter = createChooserMultiProfilePagerAdapterForTwoProfiles(intentArr, list, z);
        } else {
            this.mChooserMultiProfilePagerAdapter = createChooserMultiProfilePagerAdapterForOneProfile(intentArr, list, z);
        }
        return this.mChooserMultiProfilePagerAdapter;
    }

    @Override // com.android.internal.app.ResolverActivity
    protected AbstractMultiProfilePagerAdapter.EmptyStateProvider createBlockerEmptyStateProvider() {
        boolean zIsSendAction = isSendAction(getTargetIntent());
        return new NoCrossProfileEmptyStateProvider(getPersonalProfileUserHandle(), new NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, R.string.resolver_cross_profile_blocked, zIsSendAction ? DevicePolicyResources.Strings.Core.RESOLVER_CANT_SHARE_WITH_PERSONAL : DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_PERSONAL, zIsSendAction ? R.string.resolver_cant_share_with_personal_apps_explanation : R.string.resolver_cant_access_personal_apps_explanation, 158, "intent_chooser"), new NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState(this, DevicePolicyResources.Strings.Core.RESOLVER_CROSS_PROFILE_BLOCKED_TITLE, R.string.resolver_cross_profile_blocked, zIsSendAction ? DevicePolicyResources.Strings.Core.RESOLVER_CANT_SHARE_WITH_WORK : DevicePolicyResources.Strings.Core.RESOLVER_CANT_ACCESS_WORK, zIsSendAction ? R.string.resolver_cant_share_with_work_apps_explanation : R.string.resolver_cant_access_work_apps_explanation, 159, "intent_chooser"), createCrossProfileIntentsChecker(), getTabOwnerUserHandleForLaunch());
    }

    private ChooserMultiProfilePagerAdapter createChooserMultiProfilePagerAdapterForOneProfile(Intent[] intentArr, List<ResolveInfo> list, boolean z) {
        return new ChooserMultiProfilePagerAdapter(this, createChooserGridAdapter(this, this.mIntents, intentArr, list, z, getPersonalProfileUserHandle()), createEmptyStateProvider(null), this.mQuietModeManager, null, getCloneProfileUserHandle(), this.mMaxTargetsPerRow);
    }

    private ChooserMultiProfilePagerAdapter createChooserMultiProfilePagerAdapterForTwoProfiles(Intent[] intentArr, List<ResolveInfo> list, boolean z) {
        int iFindSelectedProfile = findSelectedProfile();
        return new ChooserMultiProfilePagerAdapter(this, createChooserGridAdapter(this, this.mIntents, iFindSelectedProfile == 0 ? intentArr : null, list, z, getPersonalProfileUserHandle()), createChooserGridAdapter(this, this.mIntents, iFindSelectedProfile == 1 ? intentArr : null, list, z, getWorkProfileUserHandle()), createEmptyStateProvider(getWorkProfileUserHandle()), this.mQuietModeManager, iFindSelectedProfile, getWorkProfileUserHandle(), getCloneProfileUserHandle(), this.mMaxTargetsPerRow);
    }

    private int findSelectedProfile() {
        int selectedProfileExtra = getSelectedProfileExtra();
        return selectedProfileExtra == -1 ? getProfileForUser(getTabOwnerUserHandleForLaunch()) : selectedProfileExtra;
    }

    @Override // com.android.internal.app.ResolverActivity
    protected boolean postRebuildList(boolean z) {
        updateStickyContentPreview();
        if (shouldShowStickyContentPreview() || this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter().getSystemRowCount() != 0) {
            logActionShareWithPreview();
        }
        return postRebuildListInternal(z);
    }

    private boolean isAppPredictionServiceAvailable() {
        return getPackageManager().getAppPredictionServicePackageName() != null;
    }

    protected boolean isWorkProfile() {
        return ((UserManager) getSystemService(UserManager.class)).getUserInfo(UserHandle.myUserId()).isManagedProfile();
    }

    @Override // com.android.internal.app.ResolverActivity
    protected PackageMonitor createPackageMonitor(final ResolverListAdapter resolverListAdapter) {
        return new PackageMonitor() { // from class: com.android.internal.app.ChooserActivity.3
            @Override // com.android.internal.content.PackageMonitor
            public void onSomePackagesChanged() {
                ChooserActivity.this.handlePackagesChanged(resolverListAdapter);
            }
        };
    }

    public void handlePackagesChanged() {
        handlePackagesChanged(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackagesChanged(ResolverListAdapter resolverListAdapter) {
        this.mPinnedSharedPrefs = getPinnedSharedPrefs(this);
        if (resolverListAdapter == null) {
            this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().handlePackagesChanged();
            if (this.mChooserMultiProfilePagerAdapter.getCount() > 1) {
                this.mChooserMultiProfilePagerAdapter.getInactiveListAdapter().handlePackagesChanged();
            }
        } else {
            resolverListAdapter.handlePackagesChanged();
        }
        updateProfileViewButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCopyButtonClicked(View view) {
        ClipData clipDataNewUri;
        Intent targetIntent = getTargetIntent();
        if (targetIntent == null) {
            finish();
            return;
        }
        String action = targetIntent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            String stringExtra = targetIntent.getStringExtra(Intent.EXTRA_TEXT);
            Uri uri = (Uri) targetIntent.getParcelableExtra(Intent.EXTRA_STREAM, Uri.class);
            if (stringExtra != null) {
                clipDataNewUri = ClipData.newPlainText(null, stringExtra);
            } else if (uri != null) {
                clipDataNewUri = ClipData.newUri(getContentResolver(), null, uri);
            } else {
                Log.w(TAG, "No data available to copy to clipboard");
                return;
            }
        } else {
            if (!Intent.ACTION_SEND_MULTIPLE.equals(action)) {
                Log.w(TAG, "Action (" + action + ") not supported for copying to clipboard");
                return;
            }
            ArrayList parcelableArrayListExtra = targetIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM, Uri.class);
            ClipData clipDataNewUri2 = ClipData.newUri(getContentResolver(), null, (Uri) parcelableArrayListExtra.get(0));
            for (int i = 1; i < parcelableArrayListExtra.size(); i++) {
                clipDataNewUri2.addItem(getContentResolver(), new ClipData.Item((Uri) parcelableArrayListExtra.get(i)));
            }
            clipDataNewUri = clipDataNewUri2;
        }
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClipAsPackage(clipDataNewUri, getReferrerPackageName());
        getMetricsLogger().write(new LogMaker(1749).setSubtype(1));
        getChooserActivityLogger().logShareTargetSelected(4, "", -1, false);
        setResult(-1);
        finish();
    }

    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + getComponentName().flattenToShortString());
        maybeCancelFinishAnimation();
    }

    @Override // com.android.internal.app.ResolverActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        ViewPager viewPager = (ViewPager) findViewById(R.id.profile_pager);
        if (viewPager.isLayoutRtl()) {
            this.mMultiProfilePagerAdapter.setupViewPager(viewPager);
        }
        this.mShouldDisplayLandscape = shouldDisplayLandscape(configuration.orientation);
        int integer = getResources().getInteger(R.integer.config_chooser_max_targets_per_row);
        this.mMaxTargetsPerRow = integer;
        this.mChooserMultiProfilePagerAdapter.setMaxTargetsPerRow(integer);
        adjustPreviewWidth(configuration.orientation, null);
        updateStickyContentPreview();
        updateTabPadding();
    }

    private boolean shouldDisplayLandscape(int i) {
        return i == 2 && !isInMultiWindowMode();
    }

    private void adjustPreviewWidth(int i, View view) {
        int dimensionPixelSize = this.mShouldDisplayLandscape ? getResources().getDimensionPixelSize(R.dimen.chooser_preview_width) : -1;
        if (view == null) {
            view = getWindow().getDecorView();
        }
        updateLayoutWidth(R.id.content_preview_text_layout, dimensionPixelSize, view);
        updateLayoutWidth(R.id.content_preview_title_layout, dimensionPixelSize, view);
        updateLayoutWidth(R.id.content_preview_file_layout, dimensionPixelSize, view);
    }

    private void updateTabPadding() throws Resources.NotFoundException {
        if (shouldShowTabs()) {
            View viewFindViewById = findViewById(16908307);
            float dimension = getResources().getDimension(R.dimen.chooser_icon_size);
            float width = viewFindViewById.getWidth();
            int i = this.mMaxTargetsPerRow;
            int dimension2 = (int) ((((width - (i * dimension)) / i) / 2.0f) - getResources().getDimension(R.dimen.resolver_profile_tab_margin));
            viewFindViewById.setPadding(dimension2, 0, dimension2, 0);
        }
    }

    private void updateLayoutWidth(int i, int i2, View view) {
        View viewFindViewById = view.findViewById(i);
        if (viewFindViewById == null || viewFindViewById.getLayoutParams() == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
        layoutParams.width = i2;
        viewFindViewById.setLayoutParams(layoutParams);
    }

    protected ViewGroup createContentPreviewView(ViewGroup viewGroup) {
        Intent targetIntent = getTargetIntent();
        return displayContentPreview(findPreferredContentPreview(targetIntent, getContentResolver()), targetIntent, getLayoutInflater(), viewGroup);
    }

    protected ComponentName getNearbySharingComponent() {
        String string = Settings.Secure.getString(getContentResolver(), Settings.Secure.NEARBY_SHARING_COMPONENT);
        if (TextUtils.isEmpty(string)) {
            string = getString(R.string.config_defaultNearbySharingComponent);
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return ComponentName.unflattenFromString(string);
    }

    protected ComponentName getEditSharingComponent() {
        String string = getApplicationContext().getString(R.string.config_systemImageEditor);
        if (string == null || TextUtils.isEmpty(string)) {
            return null;
        }
        return ComponentName.unflattenFromString(string);
    }

    protected TargetInfo getEditSharingTarget(Intent intent) {
        Uri uri;
        ComponentName editSharingComponent = getEditSharingComponent();
        Intent intent2 = new Intent(intent);
        intent2.setFlags(intent.getFlags() & 195);
        intent2.setComponent(editSharingComponent);
        intent2.setAction(Intent.ACTION_EDIT);
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            if (intent2.getData() == null && (uri = (Uri) intent2.getParcelableExtra(Intent.EXTRA_STREAM, Uri.class)) != null) {
                intent2.setDataAndType(uri, getContentResolver().getType(uri));
            }
            ResolveInfo resolveInfoResolveActivity = getPackageManager().resolveActivity(intent2, 128);
            if (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null) {
                Log.e(TAG, "Device-specified image edit component (" + editSharingComponent + ") not available");
                return null;
            }
            DisplayResolveInfo displayResolveInfo = new DisplayResolveInfo(intent, resolveInfoResolveActivity, getString(R.string.screenshot_edit), "", intent2, null);
            displayResolveInfo.setDisplayIcon(getDrawable(R.drawable.ic_screenshot_edit));
            return displayResolveInfo;
        }
        Log.e(TAG, action + " is not supported.");
        return null;
    }

    protected TargetInfo getNearbySharingTarget(Intent intent) {
        String string;
        Drawable drawableLoadIcon;
        ComponentName nearbySharingComponent = getNearbySharingComponent();
        Drawable drawable = null;
        drawable = null;
        CharSequence charSequenceLoadLabel = null;
        if (nearbySharingComponent == null) {
            return null;
        }
        Intent intent2 = new Intent(intent);
        intent2.setComponent(nearbySharingComponent);
        ResolveInfo resolveInfoResolveActivity = getPackageManager().resolveActivity(intent2, 128);
        if (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null) {
            Log.e(TAG, "Device-specified nearby sharing component (" + nearbySharingComponent + ") not available");
            return null;
        }
        Bundle bundle = resolveInfoResolveActivity.activityInfo.metaData;
        if (bundle != null) {
            try {
                Resources resourcesForActivity = getPackageManager().getResourcesForActivity(nearbySharingComponent);
                string = resourcesForActivity.getString(bundle.getInt(CHIP_LABEL_METADATA_KEY));
                try {
                    drawable = resourcesForActivity.getDrawable(bundle.getInt(CHIP_ICON_METADATA_KEY));
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
                }
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused2) {
                string = null;
            }
            drawableLoadIcon = drawable;
            charSequenceLoadLabel = string;
        } else {
            drawableLoadIcon = null;
        }
        if (TextUtils.isEmpty(charSequenceLoadLabel)) {
            charSequenceLoadLabel = resolveInfoResolveActivity.loadLabel(getPackageManager());
        }
        CharSequence charSequence = charSequenceLoadLabel;
        if (drawableLoadIcon == null) {
            drawableLoadIcon = resolveInfoResolveActivity.loadIcon(getPackageManager());
        }
        DisplayResolveInfo displayResolveInfo = new DisplayResolveInfo(intent, resolveInfoResolveActivity, charSequence, "", intent2, null);
        displayResolveInfo.setDisplayIcon(drawableLoadIcon);
        return displayResolveInfo;
    }

    private Button createActionButton(Drawable drawable, CharSequence charSequence, View.OnClickListener onClickListener) throws Resources.NotFoundException {
        Button button = (Button) LayoutInflater.from(this).inflate(R.layout.chooser_action_button, (ViewGroup) null);
        if (drawable != null) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.chooser_action_button_icon_size);
            drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            button.setCompoundDrawablesRelative(drawable, null, null, null);
        }
        button.lambda$setTextAsync$0(charSequence);
        button.setOnClickListener(onClickListener);
        return button;
    }

    private Button createCopyButton() throws Resources.NotFoundException {
        Button buttonCreateActionButton = createActionButton(getDrawable(R.drawable.ic_menu_copy_material), getString(17039361), new View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.onCopyButtonClicked(view);
            }
        });
        buttonCreateActionButton.setId(R.id.chooser_copy_button);
        return buttonCreateActionButton;
    }

    private Button createNearbyButton(Intent intent) throws Resources.NotFoundException {
        final TargetInfo nearbySharingTarget = getNearbySharingTarget(intent);
        if (nearbySharingTarget == null) {
            return null;
        }
        Button buttonCreateActionButton = createActionButton(nearbySharingTarget.getDisplayIcon(this), nearbySharingTarget.getDisplayLabel(), new View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createNearbyButton$1(nearbySharingTarget, view);
            }
        });
        buttonCreateActionButton.setId(R.id.chooser_nearby_button);
        return buttonCreateActionButton;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createNearbyButton$1(TargetInfo targetInfo, View view) {
        getChooserActivityLogger().logShareTargetSelected(5, "", -1, false);
        safelyStartActivityAsUser(targetInfo, getPersonalProfileUserHandle());
        finish();
    }

    private Button createEditButton(Intent intent) throws Resources.NotFoundException {
        final TargetInfo editSharingTarget = getEditSharingTarget(intent);
        if (editSharingTarget == null) {
            return null;
        }
        Button buttonCreateActionButton = createActionButton(editSharingTarget.getDisplayIcon(this), editSharingTarget.getDisplayLabel(), new View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createEditButton$2(editSharingTarget, view);
            }
        });
        buttonCreateActionButton.setId(R.id.chooser_edit_button);
        return buttonCreateActionButton;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createEditButton$2(TargetInfo targetInfo, View view) {
        getChooserActivityLogger().logShareTargetSelected(6, "", -1, false);
        View firstVisibleImgPreviewView = getFirstVisibleImgPreviewView();
        if (firstVisibleImgPreviewView == null) {
            safelyStartActivityAsUser(targetInfo, getPersonalProfileUserHandle());
            finish();
        } else {
            safelyStartActivityAsUser(targetInfo, getPersonalProfileUserHandle(), ActivityOptions.makeSceneTransitionAnimation(this, firstVisibleImgPreviewView, "screenshot_preview_image").toBundle());
            startFinishAnimation();
        }
    }

    private View getFirstVisibleImgPreviewView() {
        View viewFindViewById = findViewById(R.id.content_preview_image_1_large);
        if (viewFindViewById == null || !viewFindViewById.isVisibleToUser()) {
            return null;
        }
        return viewFindViewById;
    }

    private void addActionButton(ViewGroup viewGroup, Button button) {
        if (button == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.resolver_icon_margin) / 2;
        marginLayoutParams.setMarginsRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
        viewGroup.addView(button, marginLayoutParams);
    }

    private ViewGroup displayContentPreview(int i, Intent intent, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        ViewGroup viewGroupDisplayImageContentPreview;
        if (i == 1) {
            viewGroupDisplayImageContentPreview = displayImageContentPreview(intent, layoutInflater, viewGroup);
        } else if (i == 2) {
            viewGroupDisplayImageContentPreview = displayFileContentPreview(intent, layoutInflater, viewGroup);
        } else if (i == 3) {
            viewGroupDisplayImageContentPreview = displayTextContentPreview(intent, layoutInflater, viewGroup);
        } else {
            Log.e(TAG, "Unexpected content preview type: " + i);
            viewGroupDisplayImageContentPreview = null;
        }
        if (viewGroupDisplayImageContentPreview != null) {
            adjustPreviewWidth(getResources().getConfiguration().orientation, viewGroupDisplayImageContentPreview);
        }
        if (i != 1) {
            this.mEnterTransitionAnimationDelegate.markImagePreviewReady();
        }
        return viewGroupDisplayImageContentPreview;
    }

    private ViewGroup displayTextContentPreview(Intent intent, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.chooser_grid_preview_text, viewGroup, false);
        ViewGroup viewGroup3 = (ViewGroup) viewGroup2.findViewById(R.id.chooser_action_row);
        addActionButton(viewGroup3, createCopyButton());
        if (shouldNearbyShareBeIncludedAsActionButton()) {
            addActionButton(viewGroup3, createNearbyButton(intent));
        }
        CharSequence charSequenceExtra = intent.getCharSequenceExtra(Intent.EXTRA_TEXT);
        if (charSequenceExtra == null) {
            viewGroup2.findViewById(R.id.content_preview_text_layout).setVisibility(8);
        } else {
            ((TextView) viewGroup2.findViewById(R.id.content_preview_text)).lambda$setTextAsync$0(charSequenceExtra);
        }
        String stringExtra = intent.getStringExtra(Intent.EXTRA_TITLE);
        if (TextUtils.isEmpty(stringExtra)) {
            viewGroup2.findViewById(R.id.content_preview_title_layout).setVisibility(8);
            return viewGroup2;
        }
        ((TextView) viewGroup2.findViewById(R.id.content_preview_title)).lambda$setTextAsync$0(stringExtra);
        ClipData clipData = intent.getClipData();
        Uri uri = (clipData == null || clipData.getItemCount() <= 0) ? null : clipData.getItemAt(0).getUri();
        ImageView imageView = (ImageView) viewGroup2.findViewById(R.id.content_preview_thumbnail);
        if (!validForContentPreview(uri)) {
            imageView.setVisibility(8);
            return viewGroup2;
        }
        ContentPreviewCoordinator contentPreviewCoordinator = new ContentPreviewCoordinator(viewGroup2, false);
        this.mPreviewCoord = contentPreviewCoordinator;
        contentPreviewCoordinator.loadUriIntoView(R.id.content_preview_thumbnail, uri, 0);
        return viewGroup2;
    }

    private ViewGroup displayImageContentPreview(Intent intent, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.chooser_grid_preview_image, viewGroup, false);
        ViewGroup viewGroup3 = (ViewGroup) viewGroup2.findViewById(R.id.content_preview_image_area);
        ViewGroup viewGroup4 = (ViewGroup) viewGroup2.findViewById(R.id.chooser_action_row);
        if (shouldNearbyShareBeIncludedAsActionButton()) {
            addActionButton(viewGroup4, createNearbyButton(intent));
        }
        addActionButton(viewGroup4, createEditButton(intent));
        this.mPreviewCoord = new ContentPreviewCoordinator(viewGroup2, false);
        if (Intent.ACTION_SEND.equals(intent.getAction())) {
            Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri.class);
            if (!validForContentPreview(uri)) {
                viewGroup3.setVisibility(8);
                return viewGroup2;
            }
            viewGroup3.findViewById(R.id.content_preview_image_1_large).setTransitionName("screenshot_preview_image");
            this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_1_large, uri, 0);
            return viewGroup2;
        }
        ContentResolver contentResolver = getContentResolver();
        ArrayList<Uri> parcelableArrayListExtra = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM, Uri.class);
        ArrayList arrayList = new ArrayList();
        for (Uri uri2 : parcelableArrayListExtra) {
            if (validForContentPreview(uri2) && isImageType(contentResolver.getType(uri2))) {
                arrayList.add(uri2);
            }
        }
        if (arrayList.size() == 0) {
            Log.i(TAG, "Attempted to display image preview area with zero available images detected in EXTRA_STREAM list");
            viewGroup3.setVisibility(8);
            return viewGroup2;
        }
        viewGroup3.findViewById(R.id.content_preview_image_1_large).setTransitionName("screenshot_preview_image");
        this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_1_large, (Uri) arrayList.get(0), 0);
        if (arrayList.size() == 2) {
            this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_2_large, (Uri) arrayList.get(1), 0);
            return viewGroup2;
        }
        if (arrayList.size() > 2) {
            this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_2_small, (Uri) arrayList.get(1), 0);
            this.mPreviewCoord.loadUriIntoView(R.id.content_preview_image_3_small, (Uri) arrayList.get(2), arrayList.size() - 3);
        }
        return viewGroup2;
    }

    private static class FileInfo {
        public final boolean hasThumbnail;
        public final String name;

        FileInfo(String str, boolean z) {
            this.name = str;
            this.hasThumbnail = z;
        }
    }

    public Cursor queryResolver(ContentResolver contentResolver, Uri uri) {
        return contentResolver.query(uri, null, null, null, null);
    }

    private FileInfo extractFileInfo(Uri uri, ContentResolver contentResolver) {
        int iLastIndexOf;
        String path = null;
        boolean z = false;
        try {
            Cursor cursorQueryResolver = queryResolver(contentResolver, uri);
            if (cursorQueryResolver != null) {
                try {
                    if (cursorQueryResolver.getCount() > 0) {
                        int columnIndex = cursorQueryResolver.getColumnIndex("_display_name");
                        int columnIndex2 = cursorQueryResolver.getColumnIndex("title");
                        int columnIndex3 = cursorQueryResolver.getColumnIndex("flags");
                        cursorQueryResolver.moveToFirst();
                        if (columnIndex != -1) {
                            path = cursorQueryResolver.getString(columnIndex);
                        } else if (columnIndex2 != -1) {
                            path = cursorQueryResolver.getString(columnIndex2);
                        }
                        if (columnIndex3 != -1) {
                            if ((cursorQueryResolver.getInt(columnIndex3) & 1) != 0) {
                                z = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (cursorQueryResolver != null) {
                        try {
                            cursorQueryResolver.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (cursorQueryResolver != null) {
                cursorQueryResolver.close();
            }
        } catch (NullPointerException | SecurityException unused) {
            logContentPreviewWarning(uri);
        }
        if (TextUtils.isEmpty(path) && (iLastIndexOf = (path = uri.getPath()).lastIndexOf(47)) != -1) {
            path = path.substring(iLastIndexOf + 1);
        }
        return new FileInfo(path, z);
    }

    private void logContentPreviewWarning(Uri uri) {
        Log.w(TAG, "Could not load (" + uri.toString() + ") thumbnail/name for preview. If desired, consider using Intent#createChooser to launch the ChooserActivity, and set your Intent's clipData and flags in accordance with that method's documentation");
    }

    private ViewGroup displayFileContentPreview(Intent intent, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.chooser_grid_preview_file, viewGroup, false);
        ViewGroup viewGroup3 = (ViewGroup) viewGroup2.findViewById(R.id.chooser_action_row);
        if (shouldNearbyShareBeIncludedAsActionButton()) {
            addActionButton(viewGroup3, createNearbyButton(intent));
        }
        if (Intent.ACTION_SEND.equals(intent.getAction())) {
            Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri.class);
            if (!validForContentPreview(uri)) {
                viewGroup2.setVisibility(8);
                return viewGroup2;
            }
            loadFileUriIntoView(uri, viewGroup2);
            return viewGroup2;
        }
        List list = (List) intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM, Uri.class).stream().filter(new Predicate() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ChooserActivity.validForContentPreview((Uri) obj);
            }
        }).collect(Collectors.toList());
        int size = list.size();
        if (size == 0) {
            viewGroup2.setVisibility(8);
            Log.i(TAG, "Appears to be no uris available in EXTRA_STREAM, removing preview area");
            return viewGroup2;
        }
        if (size == 1) {
            loadFileUriIntoView((Uri) list.get(0), viewGroup2);
            return viewGroup2;
        }
        FileInfo fileInfoExtractFileInfo = extractFileInfo((Uri) list.get(0), getContentResolver());
        HashMap map = new HashMap();
        map.put("count", Integer.valueOf(size - 1));
        map.put(PLURALS_FILE_NAME, fileInfoExtractFileInfo.name);
        ((TextView) viewGroup2.findViewById(R.id.content_preview_filename)).lambda$setTextAsync$0(PluralsMessageFormatter.format(getResources(), map, R.string.file_count));
        viewGroup2.findViewById(R.id.content_preview_file_thumbnail).setVisibility(8);
        ImageView imageView = (ImageView) viewGroup2.findViewById(R.id.content_preview_file_icon);
        imageView.setVisibility(0);
        imageView.setImageResource(R.drawable.ic_file_copy);
        return viewGroup2;
    }

    private void loadFileUriIntoView(Uri uri, View view) {
        FileInfo fileInfoExtractFileInfo = extractFileInfo(uri, getContentResolver());
        ((TextView) view.findViewById(R.id.content_preview_filename)).lambda$setTextAsync$0(fileInfoExtractFileInfo.name);
        if (fileInfoExtractFileInfo.hasThumbnail) {
            ContentPreviewCoordinator contentPreviewCoordinator = new ContentPreviewCoordinator(view, false);
            this.mPreviewCoord = contentPreviewCoordinator;
            contentPreviewCoordinator.loadUriIntoView(R.id.content_preview_file_thumbnail, uri, 0);
        } else {
            view.findViewById(R.id.content_preview_file_thumbnail).setVisibility(8);
            ImageView imageView = (ImageView) view.findViewById(R.id.content_preview_file_icon);
            imageView.setVisibility(0);
            imageView.setImageResource(R.drawable.chooser_file_generic);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean validForContentPreview(Uri uri) throws SecurityException {
        if (uri == null) {
            return false;
        }
        int userIdFromUri = ContentProvider.getUserIdFromUri(uri, -2);
        if (userIdFromUri == -2 || userIdFromUri == UserHandle.myUserId()) {
            return true;
        }
        Log.e(TAG, "dropped invalid content URI belonging to user " + userIdFromUri);
        return false;
    }

    protected boolean isImageType(String str) {
        return str != null && str.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX);
    }

    private int findPreferredContentPreview(Uri uri, ContentResolver contentResolver) {
        if (uri == null) {
            return 3;
        }
        return isImageType(contentResolver.getType(uri)) ? 1 : 2;
    }

    private int findPreferredContentPreview(Intent intent, ContentResolver contentResolver) {
        ArrayList parcelableArrayListExtra;
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            return findPreferredContentPreview((Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri.class), contentResolver);
        }
        if (!Intent.ACTION_SEND_MULTIPLE.equals(action) || (parcelableArrayListExtra = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM, Uri.class)) == null || parcelableArrayListExtra.isEmpty()) {
            return 3;
        }
        Iterator it = parcelableArrayListExtra.iterator();
        while (it.hasNext()) {
            if (findPreferredContentPreview((Uri) it.next(), contentResolver) == 2) {
                return 2;
            }
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNumSheetExpansions() {
        return getPreferences(0).getInt(PREF_NUM_SHEET_EXPANSIONS, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void incrementNumSheetExpansions() {
        getPreferences(0).edit().putInt(PREF_NUM_SHEET_EXPANSIONS, getNumSheetExpansions() + 1).apply();
    }

    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        if (maybeCancelFinishAnimation()) {
            finish();
        }
    }

    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            this.mLatencyTracker.lambda$onActionStart$1(16);
        }
        RefinementResultReceiver refinementResultReceiver = this.mRefinementResultReceiver;
        if (refinementResultReceiver != null) {
            refinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        this.mChooserHandler.removeAllMessages();
        ContentPreviewCoordinator contentPreviewCoordinator = this.mPreviewCoord;
        if (contentPreviewCoordinator != null) {
            contentPreviewCoordinator.cancelLoads();
        }
        this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().destroyAppPredictor();
        if (this.mChooserMultiProfilePagerAdapter.getInactiveListAdapter() != null) {
            this.mChooserMultiProfilePagerAdapter.getInactiveListAdapter().destroyAppPredictor();
        }
        this.mPersonalAppPredictor = null;
        this.mWorkAppPredictor = null;
    }

    @Override // com.android.internal.app.ResolverActivity, com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public Intent getReplacementIntent(ActivityInfo activityInfo, Intent intent) {
        Bundle bundle;
        Bundle bundle2 = this.mReplacementExtras;
        if (bundle2 != null && (bundle = bundle2.getBundle(activityInfo.packageName)) != null) {
            Intent intent2 = new Intent(intent);
            intent2.putExtras(bundle);
            intent = intent2;
        }
        if (!activityInfo.name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_PARENT) && !activityInfo.name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            return intent;
        }
        Intent intentCreateChooser = Intent.createChooser(intent, getIntent().getCharSequenceExtra(Intent.EXTRA_TITLE));
        intentCreateChooser.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, false);
        return intentCreateChooser;
    }

    @Override // com.android.internal.app.ResolverActivity
    public void onActivityStarted(TargetInfo targetInfo) {
        ComponentName resolvedComponentName;
        if (this.mChosenComponentSender == null || (resolvedComponentName = targetInfo.getResolvedComponentName()) == null) {
            return;
        }
        try {
            this.mChosenComponentSender.sendIntent(this, -1, new Intent().putExtra(Intent.EXTRA_CHOSEN_COMPONENT, resolvedComponentName), null, null);
        } catch (IntentSender.SendIntentException e) {
            Slog.e(TAG, "Unable to launch supplied IntentSender to report the chosen component: " + e);
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    public void addUseDifferentAppLabelIfNecessary(ResolverListAdapter resolverListAdapter) {
        ChooserTarget[] chooserTargetArr = this.mCallerChooserTargets;
        if (chooserTargetArr == null || chooserTargetArr.length <= 0) {
            return;
        }
        this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().addServiceResults(null, Lists.newArrayList(this.mCallerChooserTargets), 0, null);
    }

    @Override // com.android.internal.app.ResolverActivity
    public boolean shouldAutoLaunchSingleChoice(TargetInfo targetInfo) {
        if (super.shouldAutoLaunchSingleChoice(targetInfo)) {
            return getIntent().getBooleanExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
        }
        return false;
    }

    private void modifyTargetIntent(Intent intent) {
        if (isSendAction(intent)) {
            intent.addFlags(134742016);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0070  */
    @Override // com.android.internal.app.ResolverActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean onTargetSelected(TargetInfo targetInfo, boolean z) {
        ChooserActivity chooserActivity;
        if (this.mRefinementIntentSender != null) {
            Intent intent = new Intent();
            List<Intent> allSourceIntents = targetInfo.getAllSourceIntents();
            if (allSourceIntents.isEmpty()) {
                chooserActivity = this;
            } else {
                intent.putExtra("android.intent.extra.INTENT", allSourceIntents.get(0));
                if (allSourceIntents.size() > 1) {
                    Intent[] intentArr = new Intent[allSourceIntents.size() - 1];
                    int size = allSourceIntents.size();
                    for (int i = 1; i < size; i++) {
                        intentArr[i - 1] = allSourceIntents.get(i);
                    }
                    intent.putExtra(Intent.EXTRA_ALTERNATE_INTENTS, intentArr);
                }
                RefinementResultReceiver refinementResultReceiver = this.mRefinementResultReceiver;
                if (refinementResultReceiver != null) {
                    refinementResultReceiver.destroy();
                }
                RefinementResultReceiver refinementResultReceiver2 = new RefinementResultReceiver(this, targetInfo, null);
                this.mRefinementResultReceiver = refinementResultReceiver2;
                intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, refinementResultReceiver2);
                try {
                    chooserActivity = this;
                    try {
                        this.mRefinementIntentSender.sendIntent(chooserActivity, 0, intent, null, null);
                        return false;
                    } catch (IntentSender.SendIntentException e) {
                        e = e;
                        Log.e(TAG, "Refinement IntentSender failed to send", e);
                        chooserActivity.updateModelAndChooserCounts(targetInfo);
                        return super.onTargetSelected(targetInfo, z);
                    }
                } catch (IntentSender.SendIntentException e2) {
                    e = e2;
                    chooserActivity = this;
                }
            }
        }
        chooserActivity.updateModelAndChooserCounts(targetInfo);
        return super.onTargetSelected(targetInfo, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00df  */
    @Override // com.android.internal.app.ResolverActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void startSelected(int i, boolean z, boolean z2) {
        int i2;
        int length;
        HashedStringCache.HashResult hashResultHashString;
        ChooserListAdapter activeListAdapter = this.mChooserMultiProfilePagerAdapter.getActiveListAdapter();
        TargetInfo targetInfoTargetInfoForPosition = activeListAdapter.targetInfoForPosition(i, z2);
        if (targetInfoTargetInfoForPosition == null || !(targetInfoTargetInfoForPosition instanceof NotSelectableTargetInfo)) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.mChooserShownTime;
            if (targetInfoTargetInfoForPosition instanceof MultiDisplayResolveInfo) {
                MultiDisplayResolveInfo multiDisplayResolveInfo = (MultiDisplayResolveInfo) targetInfoTargetInfoForPosition;
                if (!multiDisplayResolveInfo.hasSelected()) {
                    ChooserStackedAppDialogFragment chooserStackedAppDialogFragment = new ChooserStackedAppDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user_handle", targetInfoTargetInfoForPosition.getResolveInfo().userHandle);
                    bundle.putObject("multi_dri_key", multiDisplayResolveInfo);
                    bundle.putInt("which_key", i);
                    chooserStackedAppDialogFragment.setArguments(bundle);
                    chooserStackedAppDialogFragment.show(getFragmentManager(), TARGET_DETAILS_FRAGMENT_TAG);
                    return;
                }
            }
            super.startSelected(i, z, z2);
            if (activeListAdapter.getCount() > 0) {
                int positionTargetType = activeListAdapter.getPositionTargetType(i);
                int rankedPosition = -1;
                if (positionTargetType != 0) {
                    if (positionTargetType == 1) {
                        ChooserTarget chooserTargetForValue = activeListAdapter.getChooserTargetForValue(i);
                        hashResultHashString = HashedStringCache.getInstance().hashString(this, TAG, chooserTargetForValue.getComponentName().getPackageName() + chooserTargetForValue.getTitle().toString(), this.mMaxHashSaltDays);
                        SelectableTargetInfo selectableTargetInfo = (SelectableTargetInfo) targetInfoTargetInfoForPosition;
                        rankedPosition = getRankedPosition(selectableTargetInfo);
                        ChooserTarget[] chooserTargetArr = this.mCallerChooserTargets;
                        length = chooserTargetArr != null ? chooserTargetArr.length : 0;
                        getChooserActivityLogger().logShareTargetSelected(1, targetInfoTargetInfoForPosition.getResolveInfo().activityInfo.processName, i, selectableTargetInfo.isPinned());
                        int i3 = length;
                        length = 216;
                        i2 = i3;
                    } else if (positionTargetType == 2) {
                        i -= activeListAdapter.getSurfacedTargetInfo().size();
                        int callerTargetCount = activeListAdapter.getCallerTargetCount();
                        getChooserActivityLogger().logShareTargetSelected(2, targetInfoTargetInfoForPosition.getResolveInfo().activityInfo.processName, i, targetInfoTargetInfoForPosition.isPinned());
                        i2 = callerTargetCount;
                        length = 215;
                        hashResultHashString = null;
                    } else if (positionTargetType != 3) {
                        hashResultHashString = null;
                        i2 = 0;
                    } else {
                        getChooserActivityLogger().logShareTargetSelected(3, targetInfoTargetInfoForPosition.getResolveInfo().activityInfo.processName, -1, false);
                        hashResultHashString = null;
                        i2 = 0;
                        length = 217;
                        i = -1;
                    }
                }
                if (length != 0) {
                    LogMaker subtype = new LogMaker(length).setSubtype(i);
                    if (hashResultHashString != null) {
                        subtype.addTaggedData(MetricsProto.MetricsEvent.FIELD_HASHED_TARGET_NAME, hashResultHashString.hashedString);
                        subtype.addTaggedData(MetricsProto.MetricsEvent.FIELD_HASHED_TARGET_SALT_GEN, Integer.valueOf(hashResultHashString.saltGeneration));
                        subtype.addTaggedData(1087, Integer.valueOf(rankedPosition));
                    }
                    subtype.addTaggedData(1086, Integer.valueOf(i2));
                    getMetricsLogger().write(subtype);
                }
                if (this.mIsSuccessfullySelected) {
                    Log.d(TAG, "User Selection Time Cost is " + jCurrentTimeMillis);
                    Log.d(TAG, "position of selected app/service/caller is " + Integer.toString(i));
                    MetricsLogger.histogram(null, "user_selection_cost_for_smart_sharing", (int) jCurrentTimeMillis);
                    MetricsLogger.histogram(null, "app_position_for_smart_sharing", i);
                }
            }
        }
    }

    private int getRankedPosition(SelectableTargetInfo selectableTargetInfo) {
        String packageName = selectableTargetInfo.getChooserTarget().getComponentName().getPackageName();
        ChooserListAdapter activeListAdapter = this.mChooserMultiProfilePagerAdapter.getActiveListAdapter();
        int iMin = Math.min(activeListAdapter.mDisplayList.size(), 12);
        for (int i = 0; i < iMin; i++) {
            if (activeListAdapter.mDisplayList.get(i).getResolveInfo().activityInfo.packageName.equals(packageName)) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.android.internal.app.ResolverActivity
    protected void applyFooterView(int i) {
        int itemCount = this.mChooserMultiProfilePagerAdapter.getItemCount();
        for (int i2 = 0; i2 < itemCount; i2++) {
            this.mChooserMultiProfilePagerAdapter.getAdapterForIndex(i2).setFooterHeight(i);
        }
    }

    private IntentFilter getTargetIntentFilter() {
        try {
            Intent targetIntent = getTargetIntent();
            String dataString = targetIntent.getDataString();
            if (targetIntent.getType() == null) {
                if (TextUtils.isEmpty(dataString)) {
                    Log.e(TAG, "Failed to get target intent filter: intent data and type are null");
                    return null;
                }
                return new IntentFilter(targetIntent.getAction(), dataString);
            }
            IntentFilter intentFilter = new IntentFilter(targetIntent.getAction(), targetIntent.getType());
            ArrayList<Uri> arrayList = new ArrayList();
            if (Intent.ACTION_SEND.equals(targetIntent.getAction())) {
                Uri uri = (Uri) targetIntent.getParcelableExtra(Intent.EXTRA_STREAM, Uri.class);
                if (uri != null) {
                    arrayList.add(uri);
                }
            } else {
                ArrayList parcelableArrayListExtra = targetIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM, Uri.class);
                if (parcelableArrayListExtra != null) {
                    arrayList.addAll(parcelableArrayListExtra);
                }
            }
            for (Uri uri2 : arrayList) {
                intentFilter.addDataScheme(uri2.getScheme());
                intentFilter.addDataAuthority(uri2.getAuthority(), null);
                intentFilter.addDataPath(uri2.getPath(), 0);
            }
            return intentFilter;
        } catch (Exception e) {
            Log.e(TAG, "Failed to get target intent filter", e);
            return null;
        }
    }

    protected void queryDirectShareTargets(final ChooserListAdapter chooserListAdapter, boolean z) {
        AppPredictor appPredictorForDirectShareIfEnabled;
        this.mQueriedSharingShortcutsTimeMs = System.currentTimeMillis();
        final UserHandle userHandle = chooserListAdapter.getUserHandle();
        if (!z && (appPredictorForDirectShareIfEnabled = getAppPredictorForDirectShareIfEnabled(userHandle)) != null) {
            appPredictorForDirectShareIfEnabled.requestPredictionUpdate();
            return;
        }
        final IntentFilter targetIntentFilter = getTargetIntentFilter();
        if (targetIntentFilter == null) {
            return;
        }
        AsyncTask.execute(new Runnable() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$queryDirectShareTargets$3(userHandle, targetIntentFilter, chooserListAdapter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDirectShareTargets$3(UserHandle userHandle, IntentFilter intentFilter, ChooserListAdapter chooserListAdapter) {
        sendShareShortcutInfoList(((ShortcutManager) createContextAsUser(userHandle, 0).getSystemService("shortcut")).getShareTargets(intentFilter), chooserListAdapter, null, userHandle);
    }

    private boolean shouldQueryShortcutManager(UserHandle userHandle) {
        if (shouldShowTabs() && getWorkProfileUserHandle().equals(userHandle)) {
            return isUserRunning(userHandle) && isUserUnlocked(userHandle) && !isQuietModeEnabled(userHandle);
        }
        return true;
    }

    private void sendShareShortcutInfoList(List<ShortcutManager.ShareShortcutInfo> list, ChooserListAdapter chooserListAdapter, List<AppTarget> list2, UserHandle userHandle) {
        if (list2 != null && list2.size() != list.size()) {
            throw new RuntimeException("resultList and appTargets must have the same size. resultList.size()=" + list.size() + " appTargets.size()=" + list2.size());
        }
        Context contextCreateContextAsUser = createContextAsUser(userHandle, 0);
        for (int size = list.size() - 1; size >= 0; size--) {
            if (!isPackageEnabled(contextCreateContextAsUser, list.get(size).getTargetComponent().getPackageName())) {
                list.remove(size);
                if (list2 != null) {
                    list2.remove(size);
                }
            }
        }
        int i = list2 == null ? 2 : 3;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < chooserListAdapter.getDisplayResolveInfoCount(); i2++) {
            DisplayResolveInfo displayResolveInfo = chooserListAdapter.getDisplayResolveInfo(i2);
            List<ShortcutManager.ShareShortcutInfo> listFilterShortcutsByTargetComponentName = filterShortcutsByTargetComponentName(list, displayResolveInfo.getResolvedComponentName());
            if (!listFilterShortcutsByTargetComponentName.isEmpty()) {
                arrayList.add(new ServiceResultInfo(displayResolveInfo, convertToChooserTarget(listFilterShortcutsByTargetComponentName, list, list2, i), userHandle));
            }
        }
        sendShortcutManagerShareTargetResults(i, (ServiceResultInfo[]) arrayList.toArray(new ServiceResultInfo[0]));
    }

    private List<ShortcutManager.ShareShortcutInfo> filterShortcutsByTargetComponentName(List<ShortcutManager.ShareShortcutInfo> list, ComponentName componentName) {
        ArrayList arrayList = new ArrayList();
        for (ShortcutManager.ShareShortcutInfo shareShortcutInfo : list) {
            if (componentName.equals(shareShortcutInfo.getTargetComponent())) {
                arrayList.add(shareShortcutInfo);
            }
        }
        return arrayList;
    }

    protected void sendShortcutManagerShareTargetResults(int i, ServiceResultInfo[] serviceResultInfoArr) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 7;
        messageObtain.obj = serviceResultInfoArr;
        messageObtain.arg1 = i;
        this.mChooserHandler.sendMessage(messageObtain);
    }

    private boolean isPackageEnabled(Context context, String str) {
        ApplicationInfo applicationInfo;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return applicationInfo != null && applicationInfo.enabled && (applicationInfo.flags & 1073741824) == 0;
    }

    public List<ChooserTarget> convertToChooserTarget(List<ShortcutManager.ShareShortcutInfo> list, List<ShortcutManager.ShareShortcutInfo> list2, List<AppTarget> list3, int i) {
        float fMax;
        ArrayList arrayList = new ArrayList();
        if (i == 2) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                int rank = list.get(i2).getShortcutInfo().getRank();
                if (!arrayList.contains(Integer.valueOf(rank))) {
                    arrayList.add(Integer.valueOf(rank));
                }
            }
            Collections.sort(arrayList);
        }
        ArrayList arrayList2 = new ArrayList(list.size());
        for (int i3 = 0; i3 < list.size(); i3++) {
            ShortcutInfo shortcutInfo = list.get(i3).getShortcutInfo();
            int iIndexOf = list2.indexOf(list.get(i3));
            if (i == 3) {
                fMax = Math.max(1.0f - (iIndexOf * 0.01f), 0.0f);
            } else {
                fMax = Math.max(1.0f - (arrayList.indexOf(Integer.valueOf(shortcutInfo.getRank())) * 0.01f), 0.0f);
            }
            float f = fMax;
            Bundle bundle = new Bundle();
            bundle.putString(Intent.EXTRA_SHORTCUT_ID, shortcutInfo.getId());
            ChooserTarget chooserTarget = new ChooserTarget(shortcutInfo.getLabel(), null, f, list.get(i3).getTargetComponent().m930clone(), bundle);
            arrayList2.add(chooserTarget);
            Map<ChooserTarget, AppTarget> map = this.mDirectShareAppTargetCache;
            if (map != null && list3 != null) {
                map.put(chooserTarget, list3.get(iIndexOf));
            }
            Map<ChooserTarget, ShortcutInfo> map2 = this.mDirectShareShortcutInfoCache;
            if (map2 != null) {
                map2.put(chooserTarget, shortcutInfo);
            }
        }
        Collections.sort(arrayList2, new Comparator() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ChooserActivity.lambda$convertToChooserTarget$4((ChooserTarget) obj, (ChooserTarget) obj2);
            }
        });
        return arrayList2;
    }

    static /* synthetic */ int lambda$convertToChooserTarget$4(ChooserTarget chooserTarget, ChooserTarget chooserTarget2) {
        return -Float.compare(chooserTarget.getScore(), chooserTarget2.getScore());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logDirectShareTargetReceived(int i) {
        getMetricsLogger().write(new LogMaker(i).setSubtype((int) (System.currentTimeMillis() - this.mQueriedSharingShortcutsTimeMs)));
    }

    void updateModelAndChooserCounts(TargetInfo targetInfo) {
        if (targetInfo != null && (targetInfo instanceof MultiDisplayResolveInfo)) {
            targetInfo = ((MultiDisplayResolveInfo) targetInfo).getSelectedTarget();
        }
        if (targetInfo != null) {
            sendClickToAppPredictor(targetInfo);
            ResolveInfo resolveInfo = targetInfo.getResolveInfo();
            Intent targetIntent = getTargetIntent();
            if (resolveInfo == null || resolveInfo.activityInfo == null || targetIntent == null) {
                Log.d(TAG, "Can not log Chooser Counts of null ResovleInfo");
            } else {
                ChooserListAdapter activeListAdapter = this.mChooserMultiProfilePagerAdapter.getActiveListAdapter();
                if (activeListAdapter != null) {
                    sendImpressionToAppPredictor(targetInfo, activeListAdapter);
                    activeListAdapter.updateModel(targetInfo);
                    activeListAdapter.updateChooserCounts(resolveInfo.activityInfo.packageName, targetIntent.getAction(), resolveInfo.userHandle);
                }
                Log.d(TAG, "ResolveInfo Package is " + resolveInfo.activityInfo.packageName);
                Log.d(TAG, "Action to be updated is " + targetIntent.getAction());
            }
        }
        this.mIsSuccessfullySelected = true;
    }

    private void sendImpressionToAppPredictor(TargetInfo targetInfo, ChooserListAdapter chooserListAdapter) {
        AppPredictor appPredictorForDirectShareIfEnabled = getAppPredictorForDirectShareIfEnabled(this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle());
        if (appPredictorForDirectShareIfEnabled == null || (targetInfo instanceof ChooserTargetInfo)) {
            return;
        }
        List<ChooserTargetInfo> surfacedTargetInfo = chooserListAdapter.getSurfacedTargetInfo();
        ArrayList arrayList = new ArrayList();
        Iterator<ChooserTargetInfo> it = surfacedTargetInfo.iterator();
        while (it.hasNext()) {
            ChooserTarget chooserTarget = it.next().getChooserTarget();
            ComponentName componentName = chooserTarget.getComponentName();
            if (this.mDirectShareShortcutInfoCache.containsKey(chooserTarget)) {
                arrayList.add(new AppTargetId(String.format("%s/%s/%s", this.mDirectShareShortcutInfoCache.get(chooserTarget).getId(), componentName.flattenToString(), SHORTCUT_TARGET)));
            }
        }
        appPredictorForDirectShareIfEnabled.notifyLaunchLocationShown("direct_share", arrayList);
    }

    private void sendClickToAppPredictor(TargetInfo targetInfo) {
        AppPredictor appPredictorForDirectShareIfEnabled = getAppPredictorForDirectShareIfEnabled(this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle());
        if (appPredictorForDirectShareIfEnabled != null && (targetInfo instanceof ChooserTargetInfo)) {
            ChooserTarget chooserTarget = ((ChooserTargetInfo) targetInfo).getChooserTarget();
            Map<ChooserTarget, AppTarget> map = this.mDirectShareAppTargetCache;
            AppTarget appTarget = map != null ? map.get(chooserTarget) : null;
            if (appTarget != null) {
                appPredictorForDirectShareIfEnabled.notifyAppTargetEvent(new AppTargetEvent.Builder(appTarget, 1).setLaunchLocation("direct_share").build());
            }
        }
    }

    private AppPredictor createAppPredictor(UserHandle userHandle) {
        if (!this.mIsAppPredictorComponentAvailable) {
            return null;
        }
        if (getPersonalProfileUserHandle().equals(userHandle)) {
            AppPredictor appPredictor = this.mPersonalAppPredictor;
            if (appPredictor != null) {
                return appPredictor;
            }
        } else {
            AppPredictor appPredictor2 = this.mWorkAppPredictor;
            if (appPredictor2 != null) {
                return appPredictor2;
            }
        }
        Context contextCreateContextAsUser = createContextAsUser(userHandle, 0);
        IntentFilter targetIntentFilter = getTargetIntentFilter();
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent_filter", targetIntentFilter);
        populateTextContent(bundle);
        AppPredictor appPredictorCreateAppPredictionSession = ((AppPredictionManager) contextCreateContextAsUser.getSystemService(AppPredictionManager.class)).createAppPredictionSession(new AppPredictionContext.Builder(contextCreateContextAsUser).setUiSurface(APP_PREDICTION_SHARE_UI_SURFACE).setPredictedTargetCount(20).setExtras(bundle).build());
        if (getPersonalProfileUserHandle().equals(userHandle)) {
            this.mPersonalAppPredictor = appPredictorCreateAppPredictionSession;
            return appPredictorCreateAppPredictionSession;
        }
        this.mWorkAppPredictor = appPredictorCreateAppPredictionSession;
        return appPredictorCreateAppPredictionSession;
    }

    private void populateTextContent(Bundle bundle) {
        bundle.putString(SHARED_TEXT_KEY, getTargetIntent().getStringExtra(Intent.EXTRA_TEXT));
    }

    private AppPredictor getAppPredictorForDirectShareIfEnabled(UserHandle userHandle) {
        if (ActivityManager.isLowRamDeviceStatic()) {
            return null;
        }
        return createAppPredictor(userHandle);
    }

    private AppPredictor getAppPredictorForShareActivitiesIfEnabled(UserHandle userHandle) {
        if (getCloneProfileUserHandle() == null) {
            return createAppPredictor(userHandle);
        }
        return null;
    }

    void onRefinementResult(TargetInfo targetInfo, Intent intent) {
        RefinementResultReceiver refinementResultReceiver = this.mRefinementResultReceiver;
        if (refinementResultReceiver != null) {
            refinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        if (targetInfo == null) {
            Log.e(TAG, "Refinement result intent did not match any known targets; canceling");
        } else if (!checkTargetSourceIntent(targetInfo, intent)) {
            Log.e(TAG, "onRefinementResult: Selected target " + targetInfo + " cannot match refined source intent " + intent);
        } else {
            TargetInfo targetInfoCloneFilledIn = targetInfo.cloneFilledIn(intent, 0);
            if (super.onTargetSelected(targetInfoCloneFilledIn, false)) {
                updateModelAndChooserCounts(targetInfoCloneFilledIn);
                finish();
                return;
            }
        }
        onRefinementCanceled();
    }

    void onRefinementCanceled() {
        RefinementResultReceiver refinementResultReceiver = this.mRefinementResultReceiver;
        if (refinementResultReceiver != null) {
            refinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        finish();
    }

    boolean checkTargetSourceIntent(TargetInfo targetInfo, Intent intent) {
        List<Intent> allSourceIntents = targetInfo.getAllSourceIntents();
        int size = allSourceIntents.size();
        for (int i = 0; i < size; i++) {
            if (allSourceIntents.get(i).filterEquals(intent)) {
                return true;
            }
        }
        return false;
    }

    static class AzInfoComparator implements Comparator<DisplayResolveInfo> {
        Comparator<DisplayResolveInfo> mComparator;

        AzInfoComparator(Context context) {
            this.mComparator = Comparator.comparing(new Function() { // from class: com.android.internal.app.ChooserActivity$AzInfoComparator$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((DisplayResolveInfo) obj).getDisplayLabel();
                }
            }, Collator.getInstance(context.getResources().getConfiguration().locale)).thenComparingInt(new ToIntFunction() { // from class: com.android.internal.app.ChooserActivity$AzInfoComparator$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((DisplayResolveInfo) obj).getResolveInfo().userHandle.getIdentifier();
                }
            });
        }

        @Override // java.util.Comparator
        public int compare(DisplayResolveInfo displayResolveInfo, DisplayResolveInfo displayResolveInfo2) {
            return this.mComparator.compare(displayResolveInfo, displayResolveInfo2);
        }
    }

    protected MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    protected ChooserActivityLogger getChooserActivityLogger() {
        if (this.mChooserActivityLogger == null) {
            this.mChooserActivityLogger = new ChooserActivityLoggerImpl();
        }
        return this.mChooserActivityLogger;
    }

    public class ChooserListController extends ResolverListController {
        @Override // com.android.internal.app.ResolverListController
        public boolean isComponentPinned(ComponentName componentName) {
            return false;
        }

        public ChooserListController(Context context, PackageManager packageManager, Intent intent, String str, int i, UserHandle userHandle, AbstractResolverComparator abstractResolverComparator, UserHandle userHandle2) {
            super(context, packageManager, intent, str, i, userHandle, abstractResolverComparator, userHandle2);
        }

        @Override // com.android.internal.app.ResolverListController
        boolean isComponentFiltered(ComponentName componentName) {
            if (ChooserActivity.this.mFilteredComponentNames == null) {
                return false;
            }
            for (ComponentName componentName2 : ChooserActivity.this.mFilteredComponentNames) {
                if (componentName.equals(componentName2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.internal.app.ResolverListController
        public boolean isFixedAtTop(ComponentName componentName) {
            return componentName != null && componentName.equals(ChooserActivity.this.getNearbySharingComponent()) && ChooserActivity.this.shouldNearbyShareBeFirstInRankedRow();
        }
    }

    public ChooserGridAdapter createChooserGridAdapter(Context context, List<Intent> list, Intent[] intentArr, List<ResolveInfo> list2, boolean z, UserHandle userHandle) {
        ChooserListAdapter chooserListAdapterCreateChooserListAdapter = createChooserListAdapter(context, list, intentArr, list2, z, userHandle);
        ResolverAppPredictorCallback resolverAppPredictorCallbackCreateAppPredictorCallback = createAppPredictorCallback(chooserListAdapterCreateChooserListAdapter);
        AppPredictor.Callback callbackAsCallback = resolverAppPredictorCallbackCreateAppPredictorCallback.asCallback();
        chooserListAdapterCreateChooserListAdapter.setAppPredictor(setupAppPredictorForUser(userHandle, callbackAsCallback));
        chooserListAdapterCreateChooserListAdapter.setAppPredictorCallback(callbackAsCallback, resolverAppPredictorCallbackCreateAppPredictorCallback);
        return new ChooserGridAdapter(chooserListAdapterCreateChooserListAdapter);
    }

    public ChooserListAdapter createChooserListAdapter(Context context, List<Intent> list, Intent[] intentArr, List<ResolveInfo> list2, boolean z, UserHandle userHandle) {
        return new ChooserListAdapter(context, list, intentArr, list2, z, createListController(userHandle), this, this, context.getPackageManager(), getChooserActivityLogger(), (isLaunchedAsCloneProfile() && userHandle.equals(getPersonalProfileUserHandle())) ? getCloneProfileUserHandle() : userHandle);
    }

    @Override // com.android.internal.app.ResolverActivity
    protected ResolverListController createListController(UserHandle userHandle) {
        ChooserActivity chooserActivity;
        UserHandle userHandle2;
        AbstractResolverComparator resolverRankerServiceResolverComparator;
        AppPredictor appPredictorForShareActivitiesIfEnabled = getAppPredictorForShareActivitiesIfEnabled(userHandle);
        if (appPredictorForShareActivitiesIfEnabled != null) {
            userHandle2 = userHandle;
            resolverRankerServiceResolverComparator = new AppPredictionServiceResolverComparator(this, getTargetIntent(), getReferrerPackageName(), appPredictorForShareActivitiesIfEnabled, userHandle, getChooserActivityLogger());
            chooserActivity = this;
        } else {
            chooserActivity = this;
            userHandle2 = userHandle;
            resolverRankerServiceResolverComparator = new ResolverRankerServiceResolverComparator(chooserActivity, chooserActivity.getTargetIntent(), chooserActivity.getReferrerPackageName(), (AbstractResolverComparator.AfterCompute) null, chooserActivity.getChooserActivityLogger(), chooserActivity.getResolverRankerServiceUserHandleList(userHandle2));
        }
        UserHandle queryIntentsUser = chooserActivity.getQueryIntentsUser(userHandle2);
        return chooserActivity.new ChooserListController(chooserActivity, chooserActivity.mPm, chooserActivity.getTargetIntent(), chooserActivity.getReferrerPackageName(), chooserActivity.mLaunchedFromUid, userHandle2, resolverRankerServiceResolverComparator, queryIntentsUser == null ? userHandle2 : queryIntentsUser);
    }

    protected Bitmap loadThumbnail(Uri uri, Size size) {
        if (uri != null && size != null) {
            try {
                return getContentResolver().loadThumbnail(uri, size, null);
            } catch (IOException | NullPointerException | SecurityException unused) {
                this.logContentPreviewWarning(uri);
            }
        }
        return null;
    }

    static final class PlaceHolderTargetInfo extends NotSelectableTargetInfo {
        PlaceHolderTargetInfo() {
        }

        @Override // com.android.internal.app.chooser.TargetInfo
        public Drawable getDisplayIcon(Context context) {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) context.getDrawable(R.drawable.chooser_direct_share_icon_placeholder);
            animatedVectorDrawable.start();
            return animatedVectorDrawable;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScroll(View view, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        if (this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter() != null) {
            this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter().handleScroll(view, i2, i4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLayoutChange(View view, int i, final int i2, int i3, final int i4, int i5, int i6, int i7, int i8) throws Resources.NotFoundException {
        ChooserMultiProfilePagerAdapter chooserMultiProfilePagerAdapter = this.mChooserMultiProfilePagerAdapter;
        if (chooserMultiProfilePagerAdapter == null) {
            return;
        }
        final RecyclerView activeAdapterView = chooserMultiProfilePagerAdapter.getActiveAdapterView();
        final ChooserGridAdapter currentRootAdapter = this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter();
        if (currentRootAdapter == null || activeAdapterView == null || activeAdapterView.computeVerticalScrollOffset() != 0) {
            return;
        }
        int paddingLeft = ((i3 - i) - view.getPaddingLeft()) - view.getPaddingRight();
        boolean z = currentRootAdapter.consumeLayoutRequest() || currentRootAdapter.calculateChooserTargetWidth(paddingLeft) || activeAdapterView.getAdapter() == null || paddingLeft != this.mCurrAvailableWidth;
        boolean zEquals = Objects.equals(this.mLastAppliedInsets, this.mSystemWindowInsets);
        if (!z && zEquals && this.mLastNumberOfChildren == activeAdapterView.getChildCount()) {
            return;
        }
        this.mCurrAvailableWidth = paddingLeft;
        if (z) {
            activeAdapterView.setAdapter(currentRootAdapter);
            ((GridLayoutManager) activeAdapterView.getLayoutManager()).setSpanCount(this.mMaxTargetsPerRow);
            updateTabPadding();
        }
        if (getProfileForUser(this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle()) != findSelectedProfile()) {
            return;
        }
        if (this.mLastNumberOfChildren == activeAdapterView.getChildCount() && zEquals) {
            return;
        }
        getMainThreadHandler().post(new Runnable() { // from class: com.android.internal.app.ChooserActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleLayoutChange$5(currentRootAdapter, i2, i4, activeAdapterView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleLayoutChange$5(ChooserGridAdapter chooserGridAdapter, int i, int i2, RecyclerView recyclerView) {
        if (this.mResolverDrawerLayout == null || chooserGridAdapter == null) {
            return;
        }
        this.mResolverDrawerLayout.setCollapsibleHeightReserved(calculateDrawerOffset(i, i2, recyclerView, chooserGridAdapter));
        this.mEnterTransitionAnimationDelegate.markOffsetCalculated();
        this.mLastAppliedInsets = this.mSystemWindowInsets;
    }

    private int calculateDrawerOffset(int i, int i2, RecyclerView recyclerView, ChooserGridAdapter chooserGridAdapter) {
        int i3 = this.mSystemWindowInsets != null ? this.mSystemWindowInsets.bottom : 0;
        int systemRowCount = chooserGridAdapter.getSystemRowCount() + chooserGridAdapter.getProfileRowCount() + chooserGridAdapter.getServiceTargetRowCount() + chooserGridAdapter.getCallerAndRankedTargetRowCount();
        if (systemRowCount == 0) {
            systemRowCount = chooserGridAdapter.getRowCount();
        }
        if (systemRowCount == 0 && !shouldShowStickyContentPreview()) {
            return i3 + getResources().getDimensionPixelSize(R.dimen.chooser_max_collapsed_height);
        }
        int height = (shouldShowStickyContentPreview() && isStickyContentPreviewShowing()) ? findViewById(R.id.content_preview_container).getHeight() + i3 : i3;
        if (shouldShowTabs()) {
            height += findViewById(16908307).getHeight();
        }
        if (recyclerView.getVisibility() == 0) {
            int iMin = Math.min(4, systemRowCount);
            boolean zShouldShowExtraRow = shouldShowExtraRow(iMin);
            this.mLastNumberOfChildren = recyclerView.getChildCount();
            int childCount = recyclerView.getChildCount();
            int i4 = 0;
            int i5 = 0;
            while (true) {
                if (i4 >= childCount || iMin <= 0) {
                    break;
                }
                View childAt = recyclerView.getChildAt(i4);
                if (((GridLayoutManager.LayoutParams) childAt.getLayoutParams()).getSpanIndex() == 0) {
                    int height2 = childAt.getHeight();
                    height += height2;
                    if (zShouldShowExtraRow) {
                        height += height2;
                    }
                    if (chooserGridAdapter.getTargetType(recyclerView.getChildAdapterPosition(childAt)) == 1) {
                        i5 = height2;
                    }
                    iMin--;
                }
                i4++;
            }
            boolean z = getResources().getConfiguration().orientation == 1 && !isInMultiWindowMode();
            if (i5 != 0 && shouldShowContentPreview() && z) {
                height = Math.min(height, ((((i2 - i) - this.mResolverDrawerLayout.getAlwaysShowHeight()) - ((int) (i5 / DIRECT_SHARE_EXPANSION_RATE))) - (this.mSystemWindowInsets != null ? this.mSystemWindowInsets.top : 0)) - i3);
            }
        } else {
            ViewGroup activeEmptyStateView = getActiveEmptyStateView();
            if (activeEmptyStateView.getVisibility() == 0) {
                height += activeEmptyStateView.getHeight();
            }
        }
        return Math.min(height, i2 - i);
    }

    private boolean shouldShowExtraRow(int i) {
        if (!shouldShowTabs() || i != 1) {
            return false;
        }
        ChooserMultiProfilePagerAdapter chooserMultiProfilePagerAdapter = this.mChooserMultiProfilePagerAdapter;
        return chooserMultiProfilePagerAdapter.shouldShowEmptyStateScreen(chooserMultiProfilePagerAdapter.getInactiveListAdapter());
    }

    private int getProfileForUser(UserHandle userHandle) {
        return userHandle.equals(getWorkProfileUserHandle()) ? 1 : 0;
    }

    private ViewGroup getActiveEmptyStateView() {
        return this.mChooserMultiProfilePagerAdapter.getItem(this.mChooserMultiProfilePagerAdapter.getCurrentPage()).getEmptyStateView();
    }

    static class BaseChooserTargetComparator implements Comparator<ChooserTarget> {
        BaseChooserTargetComparator() {
        }

        @Override // java.util.Comparator
        public int compare(ChooserTarget chooserTarget, ChooserTarget chooserTarget2) {
            return (int) Math.signum(chooserTarget2.getScore() - chooserTarget.getScore());
        }
    }

    @Override // com.android.internal.app.ResolverActivity, com.android.internal.app.ResolverListAdapter.ResolverListCommunicator
    public void onHandlePackagesChanged(ResolverListAdapter resolverListAdapter) {
        this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().notifyDataSetChanged();
        super.onHandlePackagesChanged(resolverListAdapter);
    }

    @Override // com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator
    public ResolverListAdapter.ActivityInfoPresentationGetter makePresentationGetter(ActivityInfo activityInfo) {
        return this.mChooserMultiProfilePagerAdapter.getActiveListAdapter().makePresentationGetter(activityInfo);
    }

    @Override // com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator
    public Intent getReferrerFillInIntent() {
        return this.mReferrerFillInIntent;
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public int getMaxRankedTargets() {
        return this.mMaxTargetsPerRow;
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public void sendListViewUpdateMessage(UserHandle userHandle) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 6;
        messageObtain.obj = userHandle;
        this.mChooserHandler.sendMessageDelayed(messageObtain, this.mListViewUpdateDelayMs);
    }

    @Override // com.android.internal.app.ResolverActivity
    public void onListRebuilt(ResolverListAdapter resolverListAdapter, boolean z) {
        setupScrollListener();
        ChooserListAdapter chooserListAdapter = (ChooserListAdapter) resolverListAdapter;
        if (chooserListAdapter.getUserHandle().equals(this.mChooserMultiProfilePagerAdapter.getCurrentUserHandle())) {
            this.mChooserMultiProfilePagerAdapter.getActiveAdapterView().setAdapter(this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter());
            ChooserMultiProfilePagerAdapter chooserMultiProfilePagerAdapter = this.mChooserMultiProfilePagerAdapter;
            chooserMultiProfilePagerAdapter.setupListAdapter(chooserMultiProfilePagerAdapter.getCurrentPage());
        }
        if (chooserListAdapter.mDisplayList == null || chooserListAdapter.mDisplayList.isEmpty()) {
            chooserListAdapter.notifyDataSetChanged();
        } else {
            chooserListAdapter.updateAlphabeticalList();
        }
        if (z) {
            getChooserActivityLogger().logSharesheetAppLoadComplete();
            maybeQueryAdditionalPostProcessingTargets(chooserListAdapter);
            this.mLatencyTracker.onActionEnd(16);
        }
    }

    private void maybeQueryAdditionalPostProcessingTargets(ChooserListAdapter chooserListAdapter) {
        if (!ActivityManager.isLowRamDeviceStatic() && shouldQueryShortcutManager(chooserListAdapter.getUserHandle())) {
            Log.d(TAG, "querying direct share targets from ShortcutManager");
            queryDirectShareTargets(chooserListAdapter, false);
        }
    }

    protected boolean isUserRunning(UserHandle userHandle) {
        return ((UserManager) getSystemService(UserManager.class)).isUserRunning(userHandle);
    }

    protected boolean isUserUnlocked(UserHandle userHandle) {
        return ((UserManager) getSystemService(UserManager.class)).isUserUnlocked(userHandle);
    }

    protected boolean isQuietModeEnabled(UserHandle userHandle) {
        return ((UserManager) getSystemService(UserManager.class)).isQuietModeEnabled(userHandle);
    }

    private void setupScrollListener() {
        if (this.mResolverDrawerLayout == null) {
            return;
        }
        final View viewFindViewById = this.mResolverDrawerLayout.findViewById(shouldShowTabs() ? 16908307 : R.id.chooser_header);
        final float elevation = viewFindViewById.getElevation();
        final float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.chooser_header_scroll_elevation);
        this.mChooserMultiProfilePagerAdapter.getActiveAdapterView().addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.android.internal.app.ChooserActivity.4
            @Override // com.android.internal.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 0) {
                    if (ChooserActivity.this.mScrollStatus == 1) {
                        ChooserActivity.this.mScrollStatus = 0;
                        ChooserActivity.this.setHorizontalScrollingEnabled(true);
                        return;
                    }
                    return;
                }
                if (i == 1 && ChooserActivity.this.mScrollStatus == 0) {
                    ChooserActivity.this.mScrollStatus = 1;
                    ChooserActivity.this.setHorizontalScrollingEnabled(false);
                }
            }

            @Override // com.android.internal.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                View viewFindViewByPosition;
                if (recyclerView.getChildCount() > 0 && ((viewFindViewByPosition = recyclerView.getLayoutManager().findViewByPosition(0)) == null || viewFindViewByPosition.getTop() < 0)) {
                    viewFindViewById.setElevation(dimensionPixelSize);
                } else {
                    viewFindViewById.setElevation(elevation);
                }
            }
        });
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public boolean isSendAction(Intent intent) {
        String action;
        if (intent == null || (action = intent.getAction()) == null) {
            return false;
        }
        return Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE.equals(action);
    }

    private boolean shouldShowStickyContentPreview() {
        return shouldShowStickyContentPreviewNoOrientationCheck() && !getResources().getBoolean(R.bool.resolver_landscape_phone);
    }

    private boolean shouldShowStickyContentPreviewNoOrientationCheck() {
        ResolverListAdapter listAdapterForUserHandle = this.mMultiProfilePagerAdapter.getListAdapterForUserHandle(UserHandle.of(UserHandle.myUserId()));
        return shouldShowTabs() && (!(listAdapterForUserHandle == null || listAdapterForUserHandle.getCount() == 0) || shouldShowStickyContentPreviewWhenEmpty()) && shouldShowContentPreview();
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public boolean shouldShowContentPreview() {
        return isSendAction(getTargetIntent());
    }

    @Override // com.android.internal.app.ChooserListAdapter.ChooserListCommunicator
    public boolean shouldShowServiceTargets() {
        return shouldShowContentPreview() && !ActivityManager.isLowRamDeviceStatic();
    }

    private void updateStickyContentPreview() {
        if (shouldShowStickyContentPreviewNoOrientationCheck()) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_preview_container);
            if (viewGroup.getChildCount() == 0) {
                viewGroup.addView(createContentPreviewView(viewGroup));
            }
        }
        if (shouldShowStickyContentPreview()) {
            showStickyContentPreview();
        } else {
            hideStickyContentPreview();
        }
    }

    private void showStickyContentPreview() {
        if (isStickyContentPreviewShowing()) {
            return;
        }
        ((ViewGroup) findViewById(R.id.content_preview_container)).setVisibility(0);
    }

    private boolean isStickyContentPreviewShowing() {
        return ((ViewGroup) findViewById(R.id.content_preview_container)).getVisibility() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideStickyContentPreview() {
        if (isStickyContentPreviewShowing()) {
            ((ViewGroup) findViewById(R.id.content_preview_container)).setVisibility(8);
        }
    }

    private void logActionShareWithPreview() {
        getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_SHARE_WITH_PREVIEW).setSubtype(findPreferredContentPreview(getTargetIntent(), getContentResolver())));
    }

    private void startFinishAnimation() {
        View viewFindRootView = findRootView();
        if (viewFindRootView != null) {
            viewFindRootView.startAnimation(new FinishAnimation(this, viewFindRootView));
        }
    }

    private boolean maybeCancelFinishAnimation() {
        View viewFindRootView = findRootView();
        Animation animation = viewFindRootView == null ? null : viewFindRootView.getAnimation();
        if (!(animation instanceof FinishAnimation)) {
            return false;
        }
        boolean zHasEnded = animation.hasEnded();
        animation.cancel();
        viewFindRootView.clearAnimation();
        return !zHasEnded;
    }

    private View findRootView() {
        if (this.mContentView == null) {
            this.mContentView = findViewById(16908290);
        }
        return this.mContentView;
    }

    static abstract class ViewHolderBase extends RecyclerView.ViewHolder {
        private int mViewType;

        ViewHolderBase(View view, int i) {
            super(view);
            this.mViewType = i;
        }

        int getViewType() {
            return this.mViewType;
        }
    }

    final class ItemViewHolder extends ViewHolderBase {
        int mListPosition;
        ResolverListAdapter.ViewHolder mWrappedViewHolder;

        ItemViewHolder(View view, boolean z, int i) {
            super(view, i);
            this.mListPosition = -1;
            this.mWrappedViewHolder = new ResolverListAdapter.ViewHolder(view);
            if (z) {
                view.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$ItemViewHolder$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f$0.lambda$new$0(view2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            ChooserActivity.this.startSelected(this.mListPosition, false, true);
        }
    }

    static final class FooterViewHolder extends ViewHolderBase {
        FooterViewHolder(View view, int i) {
            super(view, i);
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    protected String getMetricsCategory() {
        return "intent_chooser";
    }

    @Override // com.android.internal.app.ResolverActivity
    protected void onProfileTabSelected() throws Resources.NotFoundException {
        this.mChooserMultiProfilePagerAdapter.getCurrentRootAdapter().updateDirectShareExpansion();
        setVerticalScrollEnabled(true);
        if (this.mResolverDrawerLayout != null) {
            this.mResolverDrawerLayout.scrollNestedScrollableChildBackToTop();
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    protected WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) throws Resources.NotFoundException {
        if (shouldShowTabs()) {
            this.mChooserMultiProfilePagerAdapter.setEmptyStateBottomOffset(windowInsets.getSystemWindowInsetBottom());
            this.mChooserMultiProfilePagerAdapter.setupContainerPadding(getActiveEmptyStateView().findViewById(R.id.resolver_empty_state_container));
        }
        WindowInsets windowInsetsOnApplyWindowInsets = super.onApplyWindowInsets(view, windowInsets);
        if (this.mResolverDrawerLayout != null) {
            this.mResolverDrawerLayout.requestLayout();
        }
        return windowInsetsOnApplyWindowInsets;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHorizontalScrollingEnabled(boolean z) {
        ((ResolverViewPager) findViewById(R.id.profile_pager)).setSwipingEnabled(z);
    }

    private void setVerticalScrollEnabled(boolean z) {
        ((ChooserGridLayoutManager) this.mChooserMultiProfilePagerAdapter.getActiveAdapterView().getLayoutManager()).setVerticalScrollEnabled(z);
    }

    @Override // com.android.internal.app.ResolverActivity
    void onHorizontalSwipeStateChanged(int i) {
        if (i == 1) {
            if (this.mScrollStatus == 0) {
                this.mScrollStatus = 2;
                setVerticalScrollEnabled(false);
                return;
            }
            return;
        }
        if (i == 0 && this.mScrollStatus == 2) {
            this.mScrollStatus = 0;
            setVerticalScrollEnabled(true);
        }
    }

    public final class ChooserGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int NUM_EXPANSIONS_TO_HIDE_AZ_LABEL = 20;
        private static final int VIEW_TYPE_AZ_LABEL = 4;
        private static final int VIEW_TYPE_CALLER_AND_RANK = 5;
        private static final int VIEW_TYPE_CONTENT_PREVIEW = 2;
        private static final int VIEW_TYPE_DIRECT_SHARE = 0;
        private static final int VIEW_TYPE_FOOTER = 6;
        private static final int VIEW_TYPE_NORMAL = 1;
        private static final int VIEW_TYPE_PROFILE = 3;
        private ChooserListAdapter mChooserListAdapter;
        private DirectShareViewHolder mDirectShareViewHolder;
        private final LayoutInflater mLayoutInflater;
        private boolean mShowAzLabelIfPoss;
        private int mChooserTargetWidth = 0;
        private boolean mLayoutRequested = false;
        private int mFooterHeight = 0;
        private final Set<ViewHolderBase> mBoundViewHolders = new HashSet();

        private boolean canExpandDirectShare() {
            return false;
        }

        public int getFooterRowCount() {
            return 1;
        }

        ChooserGridAdapter(ChooserListAdapter chooserListAdapter) {
            this.mChooserListAdapter = chooserListAdapter;
            this.mLayoutInflater = LayoutInflater.from(ChooserActivity.this);
            this.mShowAzLabelIfPoss = ChooserActivity.this.getNumSheetExpansions() < 20;
            chooserListAdapter.registerDataSetObserver(new DataSetObserver() { // from class: com.android.internal.app.ChooserActivity.ChooserGridAdapter.1
                @Override // android.database.DataSetObserver
                public void onChanged() {
                    super.onChanged();
                    ChooserGridAdapter.this.notifyDataSetChanged();
                }

                @Override // android.database.DataSetObserver
                public void onInvalidated() {
                    super.onInvalidated();
                    ChooserGridAdapter.this.notifyDataSetChanged();
                }
            });
            if (Flags.notifySingleItemChangeOnIconLoad()) {
                chooserListAdapter.setOnIconLoadedListener(new Consumer() { // from class: com.android.internal.app.ChooserActivity$ChooserGridAdapter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.onTargetIconLoaded((DisplayResolveInfo) obj);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onTargetIconLoaded(DisplayResolveInfo displayResolveInfo) {
            for (ViewHolderBase viewHolderBase : this.mBoundViewHolders) {
                int viewType = viewHolderBase.getViewType();
                if (viewType == 1) {
                    if (displayResolveInfo == this.mChooserListAdapter.getItem(((ItemViewHolder) viewHolderBase).mListPosition)) {
                        notifyItemChanged(viewHolderBase.getAdapterPosition());
                    }
                } else if (viewType == 5 && suggestedAppsGroupContainsTarget((ItemGroupViewHolder) viewHolderBase, displayResolveInfo)) {
                    notifyItemChanged(viewHolderBase.getAdapterPosition());
                }
            }
        }

        public void setFooterHeight(int i) {
            this.mFooterHeight = i;
        }

        public boolean calculateChooserTargetWidth(int i) {
            int iMin;
            if (i == 0 || (iMin = Math.min(ChooserActivity.this.getResources().getDimensionPixelSize(R.dimen.chooser_width), i) / ChooserActivity.this.mMaxTargetsPerRow) == this.mChooserTargetWidth) {
                return false;
            }
            this.mChooserTargetWidth = iMin;
            return true;
        }

        public void hideContentPreview() {
            this.mLayoutRequested = true;
            notifyDataSetChanged();
        }

        public boolean consumeLayoutRequest() {
            boolean z = this.mLayoutRequested;
            this.mLayoutRequested = false;
            return z;
        }

        public int getRowCount() {
            return (int) (getSystemRowCount() + getProfileRowCount() + getServiceTargetRowCount() + getCallerAndRankedTargetRowCount() + getAzLabelRowCount() + Math.ceil(this.mChooserListAdapter.getAlphaTargetCount() / ChooserActivity.this.mMaxTargetsPerRow));
        }

        public int getSystemRowCount() {
            ChooserListAdapter chooserListAdapter;
            return (ChooserActivity.this.shouldShowTabs() || !ChooserActivity.this.shouldShowContentPreview() || (chooserListAdapter = this.mChooserListAdapter) == null || chooserListAdapter.getCount() == 0) ? 0 : 1;
        }

        public int getProfileRowCount() {
            return (ChooserActivity.this.shouldShowTabs() || this.mChooserListAdapter.getOtherProfile() == null) ? 0 : 1;
        }

        public int getCallerAndRankedTargetRowCount() {
            return (int) Math.ceil((this.mChooserListAdapter.getCallerTargetCount() + this.mChooserListAdapter.getRankedTargetCount()) / ChooserActivity.this.mMaxTargetsPerRow);
        }

        public int getServiceTargetRowCount() {
            return ChooserActivity.this.shouldShowServiceTargets() ? 1 : 0;
        }

        public int getAzLabelRowCount() {
            return (!this.mShowAzLabelIfPoss || this.mChooserListAdapter.getAlphaTargetCount() <= 0) ? 0 : 1;
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public int getItemCount() {
            return getSystemRowCount() + getProfileRowCount() + getServiceTargetRowCount() + getCallerAndRankedTargetRowCount() + getAzLabelRowCount() + this.mChooserListAdapter.getAlphaTargetCount() + getFooterRowCount();
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            switch (i) {
                case 0:
                case 5:
                    return createItemGroupViewHolder(i, viewGroup);
                case 1:
                    return ChooserActivity.this.new ItemViewHolder(this.mChooserListAdapter.createView(viewGroup), true, i);
                case 2:
                    ChooserActivity chooserActivity = ChooserActivity.this;
                    return chooserActivity.new ItemViewHolder(chooserActivity.createContentPreviewView(viewGroup), false, i);
                case 3:
                    return ChooserActivity.this.new ItemViewHolder(createProfileView(viewGroup), false, i);
                case 4:
                    return ChooserActivity.this.new ItemViewHolder(createAzLabelView(viewGroup), false, i);
                case 6:
                    Space space = new Space(viewGroup.getContext());
                    space.setLayoutParams(new RecyclerView.LayoutParams(-1, this.mFooterHeight));
                    return new FooterViewHolder(space, i);
                default:
                    return null;
            }
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (Flags.notifySingleItemChangeOnIconLoad()) {
                this.mBoundViewHolders.add((ViewHolderBase) viewHolder);
            }
            int viewType = ((ViewHolderBase) viewHolder).getViewType();
            if (viewType != 0) {
                if (viewType == 1) {
                    bindItemViewHolder(i, (ItemViewHolder) viewHolder);
                    return;
                } else if (viewType != 5) {
                    return;
                }
            }
            bindItemGroupViewHolder(i, (ItemGroupViewHolder) viewHolder);
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
            if (Flags.notifySingleItemChangeOnIconLoad()) {
                this.mBoundViewHolders.remove((ViewHolderBase) viewHolder);
            }
            super.onViewRecycled(viewHolder);
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public boolean onFailedToRecycleView(RecyclerView.ViewHolder viewHolder) {
            if (Flags.notifySingleItemChangeOnIconLoad()) {
                this.mBoundViewHolders.remove((ViewHolderBase) viewHolder);
            }
            return super.onFailedToRecycleView(viewHolder);
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            int systemRowCount = getSystemRowCount();
            if (systemRowCount > 0 && i < systemRowCount) {
                return 2;
            }
            int profileRowCount = getProfileRowCount();
            int i2 = systemRowCount + profileRowCount;
            if (profileRowCount > 0 && i < i2) {
                return 3;
            }
            int serviceTargetRowCount = getServiceTargetRowCount();
            int i3 = i2 + serviceTargetRowCount;
            if (serviceTargetRowCount > 0 && i < i3) {
                return 0;
            }
            int callerAndRankedTargetRowCount = getCallerAndRankedTargetRowCount();
            int i4 = i3 + callerAndRankedTargetRowCount;
            if (callerAndRankedTargetRowCount > 0 && i < i4) {
                return 5;
            }
            int azLabelRowCount = getAzLabelRowCount();
            int i5 = i4 + azLabelRowCount;
            if (azLabelRowCount <= 0 || i >= i5) {
                return i == getItemCount() - 1 ? 6 : 1;
            }
            return 4;
        }

        public int getTargetType(int i) {
            return this.mChooserListAdapter.getPositionTargetType(getListPosition(i));
        }

        private View createProfileView(ViewGroup viewGroup) throws Resources.NotFoundException {
            View viewInflate = this.mLayoutInflater.inflate(R.layout.chooser_profile_row, viewGroup, false);
            ChooserActivity.this.mProfileView = viewInflate.findViewById(R.id.profile_button);
            View view = ChooserActivity.this.mProfileView;
            final ChooserActivity chooserActivity = ChooserActivity.this;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity$ChooserGridAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    chooserActivity.onProfileClick(view2);
                }
            });
            ChooserActivity.this.updateProfileViewButton();
            return viewInflate;
        }

        private View createAzLabelView(ViewGroup viewGroup) {
            return this.mLayoutInflater.inflate(R.layout.chooser_az_label_row, viewGroup, false);
        }

        private ItemGroupViewHolder loadViewsIntoGroup(final ItemGroupViewHolder itemGroupViewHolder) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mChooserTargetWidth, 1073741824);
            int columnCount = itemGroupViewHolder.getColumnCount();
            boolean z = itemGroupViewHolder instanceof DirectShareViewHolder;
            for (final int i = 0; i < columnCount; i++) {
                View viewCreateView = this.mChooserListAdapter.createView(itemGroupViewHolder.getRowByIndex(i));
                viewCreateView.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ChooserActivity.ChooserGridAdapter.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ChooserActivity.this.startSelected(itemGroupViewHolder.getItemIndex(i), false, true);
                    }
                });
                itemGroupViewHolder.addView(i, viewCreateView);
                if (z) {
                    ResolverListAdapter.ViewHolder viewHolder = (ResolverListAdapter.ViewHolder) viewCreateView.getTag();
                    viewHolder.text.setLines(2);
                    viewHolder.text.setHorizontallyScrolling(false);
                    viewHolder.text2.setVisibility(8);
                }
                viewCreateView.measure(iMakeMeasureSpec2, iMakeMeasureSpec);
                setViewBounds(viewCreateView, viewCreateView.getMeasuredWidth(), viewCreateView.getMeasuredHeight());
            }
            ViewGroup viewGroup = itemGroupViewHolder.getViewGroup();
            itemGroupViewHolder.measure();
            setViewBounds(viewGroup, -1, itemGroupViewHolder.getMeasuredRowHeight());
            if (z) {
                DirectShareViewHolder directShareViewHolder = (DirectShareViewHolder) itemGroupViewHolder;
                setViewBounds(directShareViewHolder.getRow(0), -1, directShareViewHolder.getMinRowHeight());
                setViewBounds(directShareViewHolder.getRow(1), -1, directShareViewHolder.getMinRowHeight());
            }
            viewGroup.setTag(itemGroupViewHolder);
            return itemGroupViewHolder;
        }

        private void setViewBounds(View view, int i, int i2) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(i, i2));
            } else {
                layoutParams.height = i2;
                layoutParams.width = i;
            }
        }

        ItemGroupViewHolder createItemGroupViewHolder(int i, ViewGroup viewGroup) {
            if (i == 0) {
                ViewGroup viewGroup2 = (ViewGroup) this.mLayoutInflater.inflate(R.layout.chooser_row_direct_share, viewGroup, false);
                ViewGroup viewGroup3 = (ViewGroup) this.mLayoutInflater.inflate(R.layout.chooser_row, viewGroup2, false);
                ViewGroup viewGroup4 = (ViewGroup) this.mLayoutInflater.inflate(R.layout.chooser_row, viewGroup2, false);
                viewGroup2.addView(viewGroup3);
                viewGroup2.addView(viewGroup4);
                ArrayList arrayListNewArrayList = Lists.newArrayList(viewGroup3, viewGroup4);
                int i2 = ChooserActivity.this.mMaxTargetsPerRow;
                final ChooserMultiProfilePagerAdapter chooserMultiProfilePagerAdapter = ChooserActivity.this.mChooserMultiProfilePagerAdapter;
                Objects.requireNonNull(chooserMultiProfilePagerAdapter);
                DirectShareViewHolder directShareViewHolder = new DirectShareViewHolder(viewGroup2, arrayListNewArrayList, i2, i, new Supplier() { // from class: com.android.internal.app.ChooserActivity$ChooserGridAdapter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return chooserMultiProfilePagerAdapter.getActiveListAdapter();
                    }
                });
                this.mDirectShareViewHolder = directShareViewHolder;
                loadViewsIntoGroup(directShareViewHolder);
                return this.mDirectShareViewHolder;
            }
            ItemGroupViewHolder singleRowViewHolder = new SingleRowViewHolder((ViewGroup) this.mLayoutInflater.inflate(R.layout.chooser_row, viewGroup, false), ChooserActivity.this.mMaxTargetsPerRow, i);
            loadViewsIntoGroup(singleRowViewHolder);
            return singleRowViewHolder;
        }

        int getRowType(int i) {
            int positionTargetType = this.mChooserListAdapter.getPositionTargetType(i);
            if (positionTargetType == 0) {
                return 2;
            }
            if (getAzLabelRowCount() <= 0 || positionTargetType != 3) {
                return positionTargetType;
            }
            return 2;
        }

        void bindItemViewHolder(int i, ItemViewHolder itemViewHolder) {
            View view = itemViewHolder.itemView;
            int listPosition = getListPosition(i);
            itemViewHolder.mListPosition = listPosition;
            this.mChooserListAdapter.bindView(listPosition, view);
        }

        void bindItemGroupViewHolder(int i, ItemGroupViewHolder itemGroupViewHolder) {
            ViewGroup viewGroup = (ViewGroup) itemGroupViewHolder.itemView;
            int listPosition = getListPosition(i);
            int rowType = getRowType(listPosition);
            int columnCount = itemGroupViewHolder.getColumnCount();
            int i2 = (listPosition + columnCount) - 1;
            while (getRowType(i2) != rowType && i2 >= listPosition) {
                i2--;
            }
            if (i2 == listPosition && (this.mChooserListAdapter.getItem(listPosition) instanceof EmptyTargetInfo)) {
                TextView textView = (TextView) viewGroup.findViewById(R.id.chooser_row_text_option);
                if (textView.getVisibility() != 0) {
                    textView.setAlpha(0.0f);
                    textView.setVisibility(0);
                    textView.setText(R.string.chooser_no_direct_share_targets);
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(textView, "alpha", 0.0f, 1.0f);
                    objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.0f));
                    textView.setTranslationY(ChooserActivity.this.getResources().getDimensionPixelSize(R.dimen.chooser_row_text_option_translate));
                    ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(textView, "translationY", 0.0f);
                    objectAnimatorOfFloat2.setInterpolator(new DecelerateInterpolator(1.0f));
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(200L);
                    animatorSet.setStartDelay(200L);
                    animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
                    animatorSet.start();
                }
            }
            for (int i3 = 0; i3 < columnCount; i3++) {
                View view = itemGroupViewHolder.getView(i3);
                int i4 = listPosition + i3;
                if (i4 <= i2) {
                    itemGroupViewHolder.setViewVisibility(i3, 0);
                    itemGroupViewHolder.setItemIndex(i3, i4);
                    this.mChooserListAdapter.bindView(itemGroupViewHolder.getItemIndex(i3), view);
                } else {
                    itemGroupViewHolder.setViewVisibility(i3, 4);
                }
            }
        }

        private boolean suggestedAppsGroupContainsTarget(ItemGroupViewHolder itemGroupViewHolder, DisplayResolveInfo displayResolveInfo) {
            int listPosition = getListPosition(itemGroupViewHolder.getAdapterPosition());
            int rowType = getRowType(listPosition);
            int columnCount = itemGroupViewHolder.getColumnCount();
            int i = (listPosition + columnCount) - 1;
            while (getRowType(i) != rowType && i >= listPosition) {
                i--;
            }
            for (int i2 = 0; i2 < columnCount; i2++) {
                if (listPosition + i2 <= i && this.mChooserListAdapter.getItem(itemGroupViewHolder.getItemIndex(i2)) == displayResolveInfo) {
                    return true;
                }
            }
            return false;
        }

        int getListPosition(int i) {
            int systemRowCount = i - (getSystemRowCount() + getProfileRowCount());
            int serviceTargetCount = this.mChooserListAdapter.getServiceTargetCount();
            int iCeil = (int) Math.ceil(serviceTargetCount / ChooserActivity.this.getMaxRankedTargets());
            if (systemRowCount < iCeil) {
                return systemRowCount * ChooserActivity.this.mMaxTargetsPerRow;
            }
            int i2 = systemRowCount - iCeil;
            int callerTargetCount = this.mChooserListAdapter.getCallerTargetCount() + this.mChooserListAdapter.getRankedTargetCount();
            int callerAndRankedTargetRowCount = getCallerAndRankedTargetRowCount();
            if (i2 < callerAndRankedTargetRowCount) {
                return serviceTargetCount + (i2 * ChooserActivity.this.mMaxTargetsPerRow);
            }
            return callerTargetCount + serviceTargetCount + (i2 - (getAzLabelRowCount() + callerAndRankedTargetRowCount));
        }

        public void handleScroll(View view, int i, int i2) throws Resources.NotFoundException {
            boolean zCanExpandDirectShare = canExpandDirectShare();
            DirectShareViewHolder directShareViewHolder = this.mDirectShareViewHolder;
            if (directShareViewHolder == null || !zCanExpandDirectShare) {
                return;
            }
            directShareViewHolder.handleScroll(ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveAdapterView(), i, i2, ChooserActivity.this.mMaxTargetsPerRow);
        }

        public ChooserListAdapter getListAdapter() {
            return this.mChooserListAdapter;
        }

        boolean shouldCellSpan(int i) {
            return getItemViewType(i) == 1;
        }

        void updateDirectShareExpansion() throws Resources.NotFoundException {
            if (this.mDirectShareViewHolder == null || !canExpandDirectShare()) {
                return;
            }
            RecyclerView activeAdapterView = ChooserActivity.this.mChooserMultiProfilePagerAdapter.getActiveAdapterView();
            if (ChooserActivity.this.mResolverDrawerLayout.isCollapsed()) {
                this.mDirectShareViewHolder.collapse(activeAdapterView);
            } else {
                this.mDirectShareViewHolder.expand(activeAdapterView);
            }
        }
    }

    static abstract class ItemGroupViewHolder extends ViewHolderBase {
        protected final View[] mCells;
        private final int mColumnCount;
        private int[] mItemIndices;
        protected int mMeasuredRowHeight;

        abstract ViewGroup addView(int i, View view);

        abstract ViewGroup getRow(int i);

        abstract ViewGroup getRowByIndex(int i);

        abstract ViewGroup getViewGroup();

        abstract void setViewVisibility(int i, int i2);

        ItemGroupViewHolder(int i, View view, int i2) {
            super(view, i2);
            this.mCells = new View[i];
            this.mItemIndices = new int[i];
            this.mColumnCount = i;
        }

        public int getColumnCount() {
            return this.mColumnCount;
        }

        public void measure() {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            getViewGroup().measure(iMakeMeasureSpec, iMakeMeasureSpec);
            this.mMeasuredRowHeight = getViewGroup().getMeasuredHeight();
        }

        public int getMeasuredRowHeight() {
            return this.mMeasuredRowHeight;
        }

        public void setItemIndex(int i, int i2) {
            this.mItemIndices[i] = i2;
        }

        public int getItemIndex(int i) {
            return this.mItemIndices[i];
        }

        public View getView(int i) {
            return this.mCells[i];
        }
    }

    static class SingleRowViewHolder extends ItemGroupViewHolder {
        private final ViewGroup mRow;

        SingleRowViewHolder(ViewGroup viewGroup, int i, int i2) {
            super(i, viewGroup, i2);
            this.mRow = viewGroup;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public ViewGroup getViewGroup() {
            return this.mRow;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public ViewGroup getRowByIndex(int i) {
            return this.mRow;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public ViewGroup getRow(int i) {
            if (i == 0) {
                return this.mRow;
            }
            return null;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public ViewGroup addView(int i, View view) {
            this.mRow.addView(view);
            this.mCells[i] = view;
            return this.mRow;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public void setViewVisibility(int i, int i2) {
            getView(i).setVisibility(i2);
        }
    }

    static class DirectShareViewHolder extends ItemGroupViewHolder {
        private int mCellCountPerRow;
        private final boolean[] mCellVisibility;
        private int mDirectShareCurrHeight;
        private int mDirectShareMaxHeight;
        private int mDirectShareMinHeight;
        private boolean mHideDirectShareExpansion;
        private final Supplier<ChooserListAdapter> mListAdapterSupplier;
        private final ViewGroup mParent;
        private final List<ViewGroup> mRows;

        DirectShareViewHolder(ViewGroup viewGroup, List<ViewGroup> list, int i, int i2, Supplier<ChooserListAdapter> supplier) {
            super(list.size() * i, viewGroup, i2);
            this.mHideDirectShareExpansion = false;
            this.mDirectShareMinHeight = 0;
            this.mDirectShareCurrHeight = 0;
            this.mDirectShareMaxHeight = 0;
            this.mParent = viewGroup;
            this.mRows = list;
            this.mCellCountPerRow = i;
            boolean[] zArr = new boolean[list.size() * i];
            this.mCellVisibility = zArr;
            Arrays.fill(zArr, true);
            this.mListAdapterSupplier = supplier;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public ViewGroup addView(int i, View view) {
            ViewGroup rowByIndex = getRowByIndex(i);
            rowByIndex.addView(view);
            this.mCells[i] = view;
            return rowByIndex;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public ViewGroup getViewGroup() {
            return this.mParent;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public ViewGroup getRowByIndex(int i) {
            return this.mRows.get(i / this.mCellCountPerRow);
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public ViewGroup getRow(int i) {
            return this.mRows.get(i);
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public void measure() {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            getRow(0).measure(iMakeMeasureSpec, iMakeMeasureSpec);
            getRow(1).measure(iMakeMeasureSpec, iMakeMeasureSpec);
            int measuredHeight = getRow(0).getMeasuredHeight();
            this.mDirectShareMinHeight = measuredHeight;
            int i = this.mDirectShareCurrHeight;
            if (i <= 0) {
                i = measuredHeight;
            }
            this.mDirectShareCurrHeight = i;
            this.mDirectShareMaxHeight = measuredHeight * 2;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public int getMeasuredRowHeight() {
            return this.mDirectShareCurrHeight;
        }

        public int getMinRowHeight() {
            return this.mDirectShareMinHeight;
        }

        @Override // com.android.internal.app.ChooserActivity.ItemGroupViewHolder
        public void setViewVisibility(int i, int i2) {
            final View view = getView(i);
            if (i2 == 0) {
                this.mCellVisibility[i] = true;
                view.setVisibility(i2);
                view.setAlpha(1.0f);
            } else if (i2 == 4) {
                boolean[] zArr = this.mCellVisibility;
                if (zArr[i]) {
                    zArr[i] = false;
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
                    objectAnimatorOfFloat.setDuration(200L);
                    objectAnimatorOfFloat.setInterpolator(new AccelerateInterpolator(1.0f));
                    objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.internal.app.ChooserActivity.DirectShareViewHolder.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            view.setVisibility(4);
                        }
                    });
                    objectAnimatorOfFloat.start();
                }
            }
        }

        public void handleScroll(RecyclerView recyclerView, int i, int i2, int i3) throws Resources.NotFoundException {
            if (this.mDirectShareCurrHeight == this.mDirectShareMinHeight) {
                if (this.mHideDirectShareExpansion) {
                    return;
                }
                if (this.mListAdapterSupplier.get().getSelectableServiceTargetCount() <= i3) {
                    this.mHideDirectShareExpansion = true;
                    return;
                }
            }
            int i4 = (int) ((i2 - i) * ChooserActivity.DIRECT_SHARE_EXPANSION_RATE);
            int i5 = this.mDirectShareCurrHeight;
            int iMax = Math.max(Math.min(i4 + i5, this.mDirectShareMaxHeight), this.mDirectShareMinHeight);
            updateDirectShareRowHeight(recyclerView, iMax - i5, iMax);
        }

        void expand(RecyclerView recyclerView) throws Resources.NotFoundException {
            int i = this.mDirectShareMaxHeight;
            updateDirectShareRowHeight(recyclerView, i - this.mDirectShareCurrHeight, i);
        }

        void collapse(RecyclerView recyclerView) throws Resources.NotFoundException {
            int i = this.mDirectShareMinHeight;
            updateDirectShareRowHeight(recyclerView, i - this.mDirectShareCurrHeight, i);
        }

        private void updateDirectShareRowHeight(RecyclerView recyclerView, int i, int i2) throws Resources.NotFoundException {
            if (recyclerView == null || recyclerView.getChildCount() == 0 || i == 0) {
                return;
            }
            boolean z = false;
            for (int i3 = 0; i3 < recyclerView.getChildCount(); i3++) {
                View childAt = recyclerView.getChildAt(i3);
                if (z) {
                    childAt.offsetTopAndBottom(i);
                } else if (childAt.getTag() != null && (childAt.getTag() instanceof DirectShareViewHolder)) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(childAt.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
                    childAt.getLayoutParams().height = childAt.getMeasuredHeight();
                    childAt.layout(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getTop() + childAt.getMeasuredHeight());
                    z = true;
                }
            }
            if (z) {
                this.mDirectShareCurrHeight = i2;
            }
        }
    }

    public static class ServiceResultInfo {
        public final DisplayResolveInfo originalTarget;
        public final List<ChooserTarget> resultTargets;
        public final UserHandle userHandle;

        public ServiceResultInfo(DisplayResolveInfo displayResolveInfo, List<ChooserTarget> list, UserHandle userHandle) {
            this.originalTarget = displayResolveInfo;
            this.resultTargets = list;
            this.userHandle = userHandle;
        }
    }

    static class ChooserTargetRankingInfo {
        public final List<AppTarget> scores;
        public final UserHandle userHandle;

        ChooserTargetRankingInfo(List<AppTarget> list, UserHandle userHandle) {
            this.scores = list;
            this.userHandle = userHandle;
        }
    }

    static class RefinementResultReceiver extends ResultReceiver {
        private ChooserActivity mChooserActivity;
        private TargetInfo mSelectedTarget;

        public RefinementResultReceiver(ChooserActivity chooserActivity, TargetInfo targetInfo, Handler handler) {
            super(handler);
            this.mChooserActivity = chooserActivity;
            this.mSelectedTarget = targetInfo;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            ChooserActivity chooserActivity = this.mChooserActivity;
            if (chooserActivity == null) {
                Log.e(ChooserActivity.TAG, "Destroyed RefinementResultReceiver received a result");
                return;
            }
            if (bundle == null) {
                Log.e(ChooserActivity.TAG, "RefinementResultReceiver received null resultData");
                return;
            }
            if (i == -1) {
                Parcelable parcelable = bundle.getParcelable("android.intent.extra.INTENT");
                if (parcelable instanceof Intent) {
                    this.mChooserActivity.onRefinementResult(this.mSelectedTarget, (Intent) parcelable);
                    return;
                } else {
                    Log.e(ChooserActivity.TAG, "RefinementResultReceiver received RESULT_OK but no Intent in resultData with key Intent.EXTRA_INTENT");
                    return;
                }
            }
            if (i == 0) {
                chooserActivity.onRefinementCanceled();
                return;
            }
            Log.w(ChooserActivity.TAG, "Unknown result code " + i + " sent to RefinementResultReceiver");
        }

        public void destroy() {
            this.mChooserActivity = null;
            this.mSelectedTarget = null;
        }
    }

    public static class RoundedRectImageView extends ImageView {
        private String mExtraImageCount;
        private Paint mOverlayPaint;
        private Path mPath;
        private int mRadius;
        private Paint mRoundRectPaint;
        private Paint mTextPaint;

        public RoundedRectImageView(Context context) {
            super(context);
            this.mRadius = 0;
            this.mPath = new Path();
            this.mOverlayPaint = new Paint(0);
            this.mRoundRectPaint = new Paint(0);
            this.mTextPaint = new Paint(1);
            this.mExtraImageCount = null;
        }

        public RoundedRectImageView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public RoundedRectImageView(Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        public RoundedRectImageView(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mRadius = 0;
            this.mPath = new Path();
            this.mOverlayPaint = new Paint(0);
            this.mRoundRectPaint = new Paint(0);
            this.mTextPaint = new Paint(1);
            this.mExtraImageCount = null;
            this.mRadius = context.getResources().getDimensionPixelSize(R.dimen.chooser_corner_radius);
            this.mOverlayPaint.setColor(-1728053248);
            this.mOverlayPaint.setStyle(Paint.Style.FILL);
            this.mRoundRectPaint.setColor(context.getResources().getColor(R.color.chooser_row_divider));
            this.mRoundRectPaint.setStyle(Paint.Style.STROKE);
            this.mRoundRectPaint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.chooser_preview_image_border));
            this.mTextPaint.setColor(-1);
            this.mTextPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.chooser_preview_image_font_size));
            this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        }

        private void updatePath(int i, int i2) {
            this.mPath.reset();
            int paddingRight = (i - getPaddingRight()) - getPaddingLeft();
            int paddingBottom = (i2 - getPaddingBottom()) - getPaddingTop();
            int i3 = this.mRadius;
            this.mPath.addRoundRect(getPaddingLeft(), getPaddingTop(), paddingRight, paddingBottom, i3, i3, Path.Direction.CW);
        }

        public void setRadius(int i) {
            this.mRadius = i;
            updatePath(getWidth(), getHeight());
        }

        public void setExtraImageCount(int i) {
            if (i > 0) {
                this.mExtraImageCount = "+" + i;
                return;
            }
            this.mExtraImageCount = null;
        }

        @Override // android.view.View
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            super.onSizeChanged(i, i2, i3, i4);
            updatePath(i, i2);
        }

        @Override // android.widget.ImageView, android.view.View
        protected void onDraw(Canvas canvas) {
            if (this.mRadius != 0) {
                canvas.clipPath(this.mPath);
            }
            super.onDraw(canvas);
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            if (this.mExtraImageCount != null) {
                canvas.drawRect(paddingLeft, paddingRight, width, height, this.mOverlayPaint);
                canvas.drawText(this.mExtraImageCount, canvas.getWidth() / 2, (int) ((canvas.getHeight() / 2.0f) - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f)), this.mTextPaint);
            }
            int i = this.mRadius;
            canvas.drawRoundRect(paddingLeft, paddingRight, width, height, i, i, this.mRoundRectPaint);
        }
    }

    private class EnterTransitionAnimationDelegate implements View.OnLayoutChangeListener {
        private boolean mOffsetCalculated;
        private boolean mPreviewReady;

        private EnterTransitionAnimationDelegate() {
            this.mPreviewReady = false;
            this.mOffsetCalculated = false;
        }

        void postponeTransition() {
            ChooserActivity.this.postponeEnterTransition();
        }

        void markImagePreviewReady() {
            if (this.mPreviewReady) {
                return;
            }
            this.mPreviewReady = true;
            maybeStartListenForLayout();
        }

        void markOffsetCalculated() {
            if (this.mOffsetCalculated) {
                return;
            }
            this.mOffsetCalculated = true;
            maybeStartListenForLayout();
        }

        private void maybeStartListenForLayout() {
            if (this.mPreviewReady && this.mOffsetCalculated && ChooserActivity.this.mResolverDrawerLayout != null) {
                if (ChooserActivity.this.mResolverDrawerLayout.isInLayout()) {
                    ChooserActivity.this.startPostponedEnterTransition();
                } else {
                    ChooserActivity.this.mResolverDrawerLayout.addOnLayoutChangeListener(this);
                    ChooserActivity.this.mResolverDrawerLayout.requestLayout();
                }
            }
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            view.removeOnLayoutChangeListener(this);
            ChooserActivity.this.startPostponedEnterTransition();
        }
    }

    private static class FinishAnimation extends AlphaAnimation implements Animation.AnimationListener {
        private Activity mActivity;
        private final float mFromAlpha;
        private View mRootView;

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        FinishAnimation(Activity activity, View view) {
            super(view.getAlpha(), 0.0f);
            this.mActivity = activity;
            this.mRootView = view;
            this.mFromAlpha = view.getAlpha();
            setInterpolator(new LinearInterpolator());
            long transitionBackgroundFadeDuration = activity.getWindow().getTransitionBackgroundFadeDuration();
            setDuration(transitionBackgroundFadeDuration);
            setStartOffset(transitionBackgroundFadeDuration);
            super.setAnimationListener(this);
        }

        @Override // android.view.animation.Animation
        public void setAnimationListener(Animation.AnimationListener animationListener) {
            throw new UnsupportedOperationException();
        }

        @Override // android.view.animation.Animation
        public void cancel() {
            View view = this.mRootView;
            if (view != null) {
                view.setAlpha(this.mFromAlpha);
            }
            cleanup();
            super.cancel();
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            Activity activity = this.mActivity;
            cleanup();
            if (activity != null) {
                activity.finish();
            }
        }

        private void cleanup() {
            this.mActivity = null;
            this.mRootView = null;
        }
    }

    @Override // com.android.internal.app.ResolverActivity
    protected void maybeLogProfileChange() {
        getChooserActivityLogger().logShareheetProfileChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldNearbyShareBeFirstInRankedRow() {
        return ActivityManager.isLowRamDeviceStatic() && this.mIsNearbyShareFirstTargetInRankedApp;
    }

    private boolean shouldNearbyShareBeIncludedAsActionButton() {
        return !shouldNearbyShareBeFirstInRankedRow();
    }

    private void semReplaceTargetInfoWithNewIntent(TargetInfo targetInfo, Intent intent, ChooserListAdapter chooserListAdapter, int i) {
        ActivityInfo activityInfo = targetInfo.getResolveInfo().activityInfo;
        if (activityInfo.name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_PARENT)) {
            return;
        }
        activityInfo.name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE);
    }

    private boolean hasValidIcon(ChooserTarget chooserTarget) {
        Icon icon = chooserTarget.getIcon();
        if (icon == null) {
            return true;
        }
        if (icon.getType() == 4 || icon.getType() == 6) {
            Uri uri = icon.getUri();
            try {
                getUriGrantsManager().checkGrantUriPermission_ignoreNonSystem(getLaunchedFromUid(), getPackageName(), ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri));
            } catch (RemoteException | SecurityException e) {
                Log.e(TAG, "Failed to get URI permission for: " + uri, e);
                return false;
            }
        }
        return true;
    }

    private IUriGrantsManager getUriGrantsManager() {
        return UriGrantsManager.getService();
    }

    private static ChooserTarget removeIcon(ChooserTarget chooserTarget) {
        if (chooserTarget == null) {
            return null;
        }
        return new ChooserTarget(chooserTarget.getTitle(), null, chooserTarget.getScore(), chooserTarget.getComponentName(), chooserTarget.getIntentExtras());
    }
}
