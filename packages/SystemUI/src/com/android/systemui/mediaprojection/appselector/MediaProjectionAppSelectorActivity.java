package com.android.systemui.mediaprojection.appselector;

import android.app.ActivityOptions;
import android.app.IActivityTaskManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.projection.IMediaProjection;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.app.ChooserActivity;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverListController;
import com.android.internal.app.chooser.NotSelectableTargetInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.RecyclerViewAccessibilityDelegate;
import com.android.internal.widget.ResolverDrawerLayout;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.mediaprojection.MediaProjectionCaptureTarget;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.MediaProjectionServiceHelper;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AsyncActivityLauncher;
import com.android.systemui.util.recycler.HorizontalSpacerItemDecoration;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes2.dex */
public final class MediaProjectionAppSelectorActivity extends ChooserActivity implements MediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler, LifecycleOwner {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AsyncActivityLauncher activityLauncher;
    public final ActivityManagerWrapper activityManager;
    public final IActivityTaskManager activityTaskManagerService;
    public MediaProjectionAppSelectorComponent component;
    public final MediaProjectionAppSelectorComponent.Factory componentFactory;
    public ConfigurationController configurationController;
    public MediaProjectionAppSelectorController controller;
    public final LifecycleRegistry lifecycle;
    public final LifecycleRegistry lifecycleRegistry;
    public final Function1 listControllerFactory;
    public final PluginAODManager pluginAODManager;
    public MediaProjectionRecentsViewController recentsViewController;
    public boolean reviewGrantedConsentRequired;
    public boolean taskSelected;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class RecyclerViewExpandingAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
        public final ResolverActivity.AppListAccessibilityDelegate delegate;

        public RecyclerViewExpandingAccessibilityDelegate(ResolverDrawerLayout resolverDrawerLayout, RecyclerView recyclerView) {
            super(recyclerView);
            this.delegate = new ResolverActivity.AppListAccessibilityDelegate(resolverDrawerLayout);
        }

        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            return this.delegate.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class ScreenShareType {
        public static final /* synthetic */ ScreenShareType[] $VALUES;
        public static final ScreenShareType ScreenRecord;
        public static final ScreenShareType ShareToApp;
        public static final ScreenShareType SystemCast;

        static {
            ScreenShareType screenShareType = new ScreenShareType("SystemCast", 0);
            SystemCast = screenShareType;
            ScreenShareType screenShareType2 = new ScreenShareType("ShareToApp", 1);
            ShareToApp = screenShareType2;
            ScreenShareType screenShareType3 = new ScreenShareType("ScreenRecord", 2);
            ScreenRecord = screenShareType3;
            ScreenShareType[] screenShareTypeArr = {screenShareType, screenShareType2, screenShareType3};
            $VALUES = screenShareTypeArr;
            EnumEntriesKt.enumEntries(screenShareTypeArr);
        }

        private ScreenShareType(String str, int i) {
        }

        public static ScreenShareType valueOf(String str) {
            return (ScreenShareType) Enum.valueOf(ScreenShareType.class, str);
        }

        public static ScreenShareType[] values() {
            return (ScreenShareType[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ScreenShareType.values().length];
            try {
                iArr[ScreenShareType.ShareToApp.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ScreenShareType.SystemCast.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ScreenShareType.ScreenRecord.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public MediaProjectionAppSelectorActivity(IActivityTaskManager iActivityTaskManager, PluginAODManager pluginAODManager, MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher, ActivityManagerWrapper activityManagerWrapper, Function1 function1) {
        this.activityTaskManagerService = iActivityTaskManager;
        this.pluginAODManager = pluginAODManager;
        this.componentFactory = factory;
        this.activityLauncher = asyncActivityLauncher;
        this.activityManager = activityManagerWrapper;
        this.listControllerFactory = function1;
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.lifecycleRegistry = lifecycleRegistry;
        this.lifecycle = lifecycleRegistry;
    }

    public final int appliedThemeResId() {
        return R.style.Theme_SystemUI_MediaProjectionAppSelector;
    }

    public final void bind(List list) {
        MediaProjectionRecentsViewController mediaProjectionRecentsViewController = this.recentsViewController;
        if (mediaProjectionRecentsViewController == null) {
            mediaProjectionRecentsViewController = null;
        }
        mediaProjectionRecentsViewController.bind(list);
        if (((ChooserActivity) this).mMultiProfilePagerAdapter.getCount() > 1) {
            return;
        }
        ((ChooserActivity) this).mMultiProfilePagerAdapter.getPersonalListAdapter().notifyDataSetChanged();
    }

    public final AbstractMultiProfilePagerAdapter.EmptyStateProvider createBlockerEmptyStateProvider() {
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent = this.component;
        if (mediaProjectionAppSelectorComponent == null) {
            mediaProjectionAppSelectorComponent = null;
        }
        return mediaProjectionAppSelectorComponent.getEmptyStateProvider();
    }

    public final ViewGroup createContentPreviewView(ViewGroup viewGroup) {
        ViewGroup viewGroup2;
        MediaProjectionRecentsViewController mediaProjectionRecentsViewController = this.recentsViewController;
        if (mediaProjectionRecentsViewController == null) {
            mediaProjectionRecentsViewController = null;
        }
        MediaProjectionRecentsViewController.Views views = mediaProjectionRecentsViewController.views;
        if (views != null && (viewGroup2 = views.root) != null) {
            return viewGroup2;
        }
        ViewGroup viewGroup3 = (ViewGroup) KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.media_projection_recent_tasks, viewGroup, false);
        View viewRequireViewById = viewGroup3.requireViewById(R.id.media_projection_recent_tasks_container);
        mediaProjectionRecentsViewController.setTaskHeightSize(viewRequireViewById);
        View viewRequireViewById2 = viewGroup3.requireViewById(R.id.media_projection_recent_tasks_loader);
        androidx.recyclerview.widget.RecyclerView recyclerView = (androidx.recyclerview.widget.RecyclerView) viewGroup3.requireViewById(R.id.media_projection_recent_tasks_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext(), 0, false));
        recyclerView.addItemDecoration(new HorizontalSpacerItemDecoration(viewGroup.getResources().getDimensionPixelOffset(R.dimen.media_projection_app_selector_recents_padding)));
        MediaProjectionRecentsViewController.Views views2 = new MediaProjectionRecentsViewController.Views(viewGroup3, viewRequireViewById, viewRequireViewById2, recyclerView);
        mediaProjectionRecentsViewController.views = views2;
        List list = mediaProjectionRecentsViewController.lastBoundData;
        if (list != null) {
            mediaProjectionRecentsViewController.bind(list);
        }
        return views2.root;
    }

    public final ResolverListController createListController(UserHandle userHandle) {
        ResolverListController resolverListController;
        Function1 function1 = this.listControllerFactory;
        return (function1 == null || (resolverListController = (ResolverListController) function1.mo781invoke(userHandle)) == null) ? super.createListController(userHandle) : resolverListController;
    }

    public final AbstractMultiProfilePagerAdapter.MyUserIdProvider createMyUserIdProvider() {
        return new AbstractMultiProfilePagerAdapter.MyUserIdProvider() { // from class: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity.createMyUserIdProvider.1
            public final int getMyUserId() {
                MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent = MediaProjectionAppSelectorActivity.this.component;
                if (mediaProjectionAppSelectorComponent == null) {
                    mediaProjectionAppSelectorComponent = null;
                }
                return mediaProjectionAppSelectorComponent.getHostUserHandle().getIdentifier();
            }
        };
    }

    public final int getLayoutResource() {
        return R.layout.media_projection_app_selector;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.lifecycle;
    }

    public final ScreenShareType getScreenShareType() {
        String stringExtra;
        if (!getIntent().hasExtra("screen_share_type") || (stringExtra = getIntent().getStringExtra("screen_share_type")) == null) {
            return null;
        }
        try {
            return ScreenShareType.valueOf(stringExtra);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ConfigurationController configurationController = this.configurationController;
        if (configurationController == null) {
            configurationController = null;
        }
        ((ConfigurationControllerImpl) configurationController).onConfigurationChanged(configuration);
    }

    public final void onCreate(Bundle bundle) {
        int i;
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        MediaProjectionAppSelectorComponent.Factory factory = this.componentFactory;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new IllegalStateException("MediaProjectionAppSelectorActivity should be launched with extras");
        }
        UserHandle userHandle = (UserHandle) extras.getParcelable("launched_from_user_handle");
        if (userHandle == null) {
            throw new IllegalStateException("MediaProjectionAppSelectorActivity should be provided with launched_from_user_handle extra");
        }
        if (!getIntent().hasExtra("launched_from_host_uid")) {
            throw new IllegalStateException("MediaProjectionAppSelectorActivity should be provided with launched_from_host_uid extra");
        }
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponentCreate = factory.create(userHandle, getIntent().getIntExtra("launched_from_host_uid", -1), getCallingPackage(), this, this, bundle == null);
        this.component = mediaProjectionAppSelectorComponentCreate;
        Iterator<T> it = ((DaggerReferenceGlobalRootComponent.MediaProjectionAppSelectorComponentImpl) mediaProjectionAppSelectorComponentCreate).getLifecycleObservers().iterator();
        while (it.hasNext()) {
            this.lifecycle.addObserver((DefaultLifecycleObserver) it.next());
        }
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent = this.component;
        if (mediaProjectionAppSelectorComponent == null) {
            mediaProjectionAppSelectorComponent = null;
        }
        this.configurationController = mediaProjectionAppSelectorComponent.getConfigurationController();
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent2 = this.component;
        if (mediaProjectionAppSelectorComponent2 == null) {
            mediaProjectionAppSelectorComponent2 = null;
        }
        this.controller = mediaProjectionAppSelectorComponent2.getController();
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent3 = this.component;
        if (mediaProjectionAppSelectorComponent3 == null) {
            mediaProjectionAppSelectorComponent3 = null;
        }
        this.recentsViewController = mediaProjectionAppSelectorComponent3.getRecentsViewController();
        Intent intent = getIntent();
        Resources resources = getResources();
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent4 = this.component;
        if (mediaProjectionAppSelectorComponent4 == null) {
            mediaProjectionAppSelectorComponent4 = null;
        }
        UserHandle hostUserHandle = mediaProjectionAppSelectorComponent4.getHostUserHandle();
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent5 = this.component;
        if (mediaProjectionAppSelectorComponent5 == null) {
            mediaProjectionAppSelectorComponent5 = null;
        }
        UserHandle personalProfileUserHandle = mediaProjectionAppSelectorComponent5.getPersonalProfileUserHandle();
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.LAUNCHER");
        intent.putExtra("android.intent.extra.INTENT", intent2);
        ScreenShareType screenShareType = getScreenShareType();
        int i2 = screenShareType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[screenShareType.ordinal()];
        if (i2 == -1) {
            i = R.string.screen_share_generic_app_selector_title;
        } else if (i2 == 1) {
            i = R.string.media_projection_entry_share_app_selector_title;
        } else if (i2 == 2) {
            i = R.string.media_projection_entry_cast_app_selector_title;
        } else {
            if (i2 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            i = R.string.screenrecord_app_selector_title;
        }
        intent.putExtra("android.intent.extra.TITLE", resources.getString(i));
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", !Intrinsics.areEqual(hostUserHandle, personalProfileUserHandle) ? 1 : 0);
        this.reviewGrantedConsentRequired = getIntent().getBooleanExtra("extra_media_projection_user_consent_required", false);
        super.onCreate(bundle);
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.controller;
        if (mediaProjectionAppSelectorController == null) {
            mediaProjectionAppSelectorController = null;
        }
        if (mediaProjectionAppSelectorController.isFirstStart) {
            int i3 = mediaProjectionAppSelectorController.hostUid;
            MediaProjectionMetricsLogger mediaProjectionMetricsLogger = mediaProjectionAppSelectorController.logger;
            mediaProjectionMetricsLogger.getClass();
            try {
                mediaProjectionMetricsLogger.service.notifyAppSelectorDisplayed(i3);
            } catch (RemoteException e) {
                Log.e("MediaProjectionMetricsLogger", "Error notifying server of app selector displayed", e);
            }
        }
        CoroutineTracingKt.launchTraced$default(mediaProjectionAppSelectorController.scope, null, null, new MediaProjectionAppSelectorController$init$1(mediaProjectionAppSelectorController, null), 7);
        ImageView imageView = (ImageView) findViewById(R.id.media_projection_app_selector_icon);
        if (imageView != null) {
            ScreenShareType screenShareType2 = getScreenShareType();
            int i4 = screenShareType2 == null ? -1 : WhenMappings.$EnumSwitchMapping$0[screenShareType2.ordinal()];
            int i5 = R.drawable.ic_present_to_all;
            if (i4 != -1 && i4 != 1) {
                if (i4 == 2) {
                    i5 = R.drawable.ic_cast_connected;
                } else {
                    if (i4 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    i5 = R.drawable.ic_screenrecord;
                }
            }
            imageView.setImageResource(i5);
            ScreenShareType screenShareType3 = getScreenShareType();
            Integer numValueOf = (screenShareType3 != null ? WhenMappings.$EnumSwitchMapping$0[screenShareType3.ordinal()] : -1) == 3 ? Integer.valueOf(R.color.screenrecord_icon_color) : null;
            if (numValueOf != null) {
                imageView.setColorFilter(getResources().getColor(numValueOf.intValue(), getTheme()));
            }
        }
        ResolverDrawerLayout resolverDrawerLayoutRequireViewById = requireViewById(android.R.id.date_picker_year_picker);
        int count = ((ChooserActivity) this).mMultiProfilePagerAdapter.getCount();
        for (int i6 = 0; i6 < count; i6++) {
            RecyclerView recyclerViewFindViewById = ((ChooserActivity) this).mMultiProfilePagerAdapter.getItem(i6).rootView.findViewById(android.R.id.sync);
            if (recyclerViewFindViewById == null || !(recyclerViewFindViewById instanceof RecyclerView)) {
                Log.wtf("MediaProjectionAppSelectorActivity", "MediaProjection only supports RecyclerView");
            } else {
                RecyclerView recyclerView = recyclerViewFindViewById;
                recyclerView.setAccessibilityDelegate(new RecyclerViewExpandingAccessibilityDelegate(resolverDrawerLayoutRequireViewById, recyclerView));
            }
        }
    }

    public final void onDestroy() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent = this.component;
        if (mediaProjectionAppSelectorComponent == null) {
            mediaProjectionAppSelectorComponent = null;
        }
        Iterator it = mediaProjectionAppSelectorComponent.getLifecycleObservers().iterator();
        while (it.hasNext()) {
            this.lifecycle.removeObserver((DefaultLifecycleObserver) it.next());
        }
        if (!this.taskSelected) {
            MediaProjectionServiceHelper.Companion companion = MediaProjectionServiceHelper.Companion;
            boolean z = this.reviewGrantedConsentRequired;
            companion.getClass();
            MediaProjectionServiceHelper.Companion.setReviewedConsentIfNeeded(0, z, null);
            if (isFinishing()) {
                MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.controller;
                if (mediaProjectionAppSelectorController == null) {
                    mediaProjectionAppSelectorController = null;
                }
                MediaProjectionMetricsLogger mediaProjectionMetricsLogger = mediaProjectionAppSelectorController.logger;
                int i = mediaProjectionAppSelectorController.hostUid;
                mediaProjectionMetricsLogger.getClass();
                try {
                    mediaProjectionMetricsLogger.service.notifyPermissionRequestCancelled(i);
                } catch (RemoteException e) {
                    Log.e("MediaProjectionMetricsLogger", "Error notifying server of projection cancelled", e);
                }
            }
        }
        this.activityLauncher.destroy();
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController2 = this.controller;
        if (mediaProjectionAppSelectorController2 == null) {
            mediaProjectionAppSelectorController2 = null;
        }
        CoroutineScopeKt.cancel(mediaProjectionAppSelectorController2.scope, null);
        super.onDestroy();
    }

    public final void onPause() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        super.onPause();
    }

    public final void onResume() {
        super.onResume();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    public final void onStart() {
        super.onStart();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    public final void onStop() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        super.onStop();
    }

    public final void returnSelectedApp(ActivityOptions.LaunchCookie launchCookie, int i) {
        this.taskSelected = true;
        if (getIntent().hasExtra("capture_region_result_receiver")) {
            ResultReceiver resultReceiver = (ResultReceiver) getIntent().getParcelableExtra("capture_region_result_receiver", ResultReceiver.class);
            MediaProjectionCaptureTarget mediaProjectionCaptureTarget = new MediaProjectionCaptureTarget(launchCookie, i);
            Bundle bundle = new Bundle();
            bundle.putParcelable("capture_region", mediaProjectionCaptureTarget);
            resultReceiver.send(-1, bundle);
        } else {
            IMediaProjection iMediaProjectionAsInterface = IMediaProjection.Stub.asInterface(getIntent().getIBinderExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION"));
            iMediaProjectionAsInterface.setLaunchCookie(launchCookie);
            iMediaProjectionAsInterface.setTaskId(i);
            Intent intent = new Intent();
            intent.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", iMediaProjectionAsInterface.asBinder());
            setResult(-1, intent);
            setForceSendResultForMediaProjection();
            MediaProjectionServiceHelper.Companion companion = MediaProjectionServiceHelper.Companion;
            boolean z = this.reviewGrantedConsentRequired;
            companion.getClass();
            MediaProjectionServiceHelper.Companion.setReviewedConsentIfNeeded(2, z, iMediaProjectionAsInterface);
        }
        finish();
    }

    public final boolean shouldGetOnlyDefaultActivities() {
        return false;
    }

    public final boolean shouldShowContentPreview() {
        if (((ChooserActivity) this).mMultiProfilePagerAdapter.getCount() > 1) {
            return true;
        }
        MediaProjectionRecentsViewController mediaProjectionRecentsViewController = this.recentsViewController;
        if (mediaProjectionRecentsViewController == null) {
            mediaProjectionRecentsViewController = null;
        }
        if (mediaProjectionRecentsViewController.lastBoundData != null) {
            return !r3.isEmpty();
        }
        return false;
    }

    public final boolean shouldShowServiceTargets() {
        return false;
    }

    public final boolean shouldShowStickyContentPreviewWhenEmpty() {
        return shouldShowContentPreview();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void startSelected(int i, boolean z, boolean z2) {
        String packageName;
        final TargetInfo targetInfoTargetInfoForPosition = ((ChooserActivity) this).mChooserMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(i, z2);
        if (targetInfoTargetInfoForPosition == null || (targetInfoTargetInfoForPosition instanceof NotSelectableTargetInfo)) {
            return;
        }
        Intent intent = new Intent(targetInfoTargetInfoForPosition.getResolvedIntent());
        intent.setFlags(intent.getFlags() | 268435456);
        intent.setFlags(intent.getFlags() & (-33554433));
        if (BasicRune.POPUPUI_FOLDERBLE_TYPE_FLIP && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            ComponentName component = intent.getComponent();
            if (component == null || (packageName = component.getPackageName()) == null) {
                packageName = "";
            }
            PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 67108864);
            if (!this.activityTaskManagerService.isPackageEnabledForCoverLauncher(packageName, UserHandle.semGetMyUserId())) {
                this.pluginAODManager.showCoverToast(activity, intent);
            }
        }
        final ActivityOptions.LaunchCookie launchCookie = new ActivityOptions.LaunchCookie("media_projection_launch_token");
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchCookie(launchCookie);
        UserHandle userHandle = ((ChooserActivity) this).mMultiProfilePagerAdapter.getActiveListAdapter().getUserHandle();
        AsyncActivityLauncher asyncActivityLauncher = this.activityLauncher;
        userHandle.getClass();
        asyncActivityLauncher.startActivityAsUser(intent, userHandle, activityOptionsMakeBasic.toBundle(), new Function1() { // from class: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                TargetInfo targetInfo = targetInfoTargetInfoForPosition;
                ActivityOptions.LaunchCookie launchCookie2 = launchCookie;
                int i2 = MediaProjectionAppSelectorActivity.$r8$clinit;
                ComponentName resolvedComponentName = targetInfo.getResolvedComponentName();
                MediaProjectionAppSelectorActivity mediaProjectionAppSelectorActivity = this;
                if (Intrinsics.areEqual(resolvedComponentName, mediaProjectionAppSelectorActivity.getCallingActivity())) {
                    mediaProjectionAppSelectorActivity.returnSelectedApp(launchCookie2, mediaProjectionAppSelectorActivity.activityManager.getRunningTask().taskId);
                } else {
                    mediaProjectionAppSelectorActivity.returnSelectedApp(launchCookie2, -1);
                }
                return Unit.INSTANCE;
            }
        });
        targetInfoTargetInfoForPosition.isSuspended();
    }

    public MediaProjectionAppSelectorActivity(IActivityTaskManager iActivityTaskManager, PluginAODManager pluginAODManager, MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher, ActivityManagerWrapper activityManagerWrapper) {
        this(iActivityTaskManager, pluginAODManager, factory, asyncActivityLauncher, activityManagerWrapper, null);
    }

    public final void onActivityStarted(TargetInfo targetInfo) {
    }
}
