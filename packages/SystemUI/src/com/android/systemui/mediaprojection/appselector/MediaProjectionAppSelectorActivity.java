package com.android.systemui.mediaprojection.appselector;

import android.app.ActivityOptions;
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
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.app.ChooserActivity;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverListController;
import com.android.internal.app.chooser.NotSelectableTargetInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.RecyclerViewAccessibilityDelegate;
import com.android.internal.widget.ResolverDrawerLayout;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.MediaProjectionCaptureTarget;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.MediaProjectionServiceHelper;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AsyncActivityLauncher;
import com.android.systemui.util.recycler.HorizontalSpacerItemDecoration;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaProjectionAppSelectorActivity extends ChooserActivity implements MediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler, LifecycleOwner {
    public static final Companion Companion = new Companion(null);
    public final AsyncActivityLauncher activityLauncher;
    public MediaProjectionAppSelectorComponent component;
    public final MediaProjectionAppSelectorComponent.Factory componentFactory;
    public ConfigurationController configurationController;
    public MediaProjectionAppSelectorController controller;
    public final LifecycleRegistry lifecycle;
    public final LifecycleRegistry lifecycleRegistry;
    public final Function1 listControllerFactory;
    public MediaProjectionRecentsViewController recentsViewController;
    public boolean reviewGrantedConsentRequired;
    public boolean taskSelected;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public MediaProjectionAppSelectorActivity(MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher, Function1 function1) {
        this.componentFactory = factory;
        this.activityLauncher = asyncActivityLauncher;
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
        ViewGroup viewGroup3 = (ViewGroup) MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.media_projection_recent_tasks, viewGroup, false);
        View requireViewById = viewGroup3.requireViewById(R.id.media_projection_recent_tasks_container);
        mediaProjectionRecentsViewController.setTaskHeightSize(requireViewById);
        View requireViewById2 = viewGroup3.requireViewById(R.id.media_projection_recent_tasks_loader);
        androidx.recyclerview.widget.RecyclerView recyclerView = (androidx.recyclerview.widget.RecyclerView) viewGroup3.requireViewById(R.id.media_projection_recent_tasks_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext(), 0, false));
        recyclerView.addItemDecoration(new HorizontalSpacerItemDecoration(viewGroup.getResources().getDimensionPixelOffset(R.dimen.media_projection_app_selector_recents_padding)));
        MediaProjectionRecentsViewController.Views views2 = new MediaProjectionRecentsViewController.Views(viewGroup3, requireViewById, requireViewById2, recyclerView);
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
        return (function1 == null || (resolverListController = (ResolverListController) function1.invoke(userHandle)) == null) ? super.createListController(userHandle) : resolverListController;
    }

    public final AbstractMultiProfilePagerAdapter.MyUserIdProvider createMyUserIdProvider() {
        return new AbstractMultiProfilePagerAdapter.MyUserIdProvider() { // from class: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity$createMyUserIdProvider$1
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

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ConfigurationController configurationController = this.configurationController;
        if (configurationController == null) {
            configurationController = null;
        }
        ((ConfigurationControllerImpl) configurationController).onConfigurationChanged(configuration);
    }

    public final void onCreate(Bundle bundle) {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        MediaProjectionAppSelectorComponent.Factory factory = this.componentFactory;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new IllegalStateException("MediaProjectionAppSelectorActivity should be launched with extras".toString());
        }
        UserHandle userHandle = (UserHandle) extras.getParcelable("launched_from_user_handle");
        if (userHandle == null) {
            throw new IllegalStateException("MediaProjectionAppSelectorActivity should be provided with launched_from_user_handle extra".toString());
        }
        if (!getIntent().hasExtra("launched_from_host_uid")) {
            throw new IllegalStateException("MediaProjectionAppSelectorActivity should be provided with launched_from_host_uid extra".toString());
        }
        MediaProjectionAppSelectorComponent create = factory.create(userHandle, getIntent().getIntExtra("launched_from_host_uid", -1), getCallingPackage(), this, this, bundle == null);
        this.component = create;
        if (create == null) {
            create = null;
        }
        Iterator it = create.getLifecycleObservers().iterator();
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
        Companion companion = Companion;
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
        companion.getClass();
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.LAUNCHER");
        intent.putExtra("android.intent.extra.INTENT", intent2);
        intent.putExtra("android.intent.extra.TITLE", resources.getString(R.string.screen_share_permission_app_selector_title));
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", 1 ^ (Intrinsics.areEqual(hostUserHandle, personalProfileUserHandle) ? 1 : 0));
        this.reviewGrantedConsentRequired = getIntent().getBooleanExtra("extra_media_projection_user_consent_required", false);
        super.onCreate(bundle);
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.controller;
        if (mediaProjectionAppSelectorController == null) {
            mediaProjectionAppSelectorController = null;
        }
        if (mediaProjectionAppSelectorController.isFirstStart) {
            int i = mediaProjectionAppSelectorController.hostUid;
            MediaProjectionMetricsLogger mediaProjectionMetricsLogger = mediaProjectionAppSelectorController.logger;
            mediaProjectionMetricsLogger.getClass();
            try {
                mediaProjectionMetricsLogger.service.notifyAppSelectorDisplayed(i);
            } catch (RemoteException e) {
                Log.e("MediaProjectionMetricsLogger", "Error notifying server of app selector displayed", e);
            }
        }
        BuildersKt.launch$default(mediaProjectionAppSelectorController.scope, null, null, new MediaProjectionAppSelectorController$init$1(mediaProjectionAppSelectorController, null), 3);
        ResolverDrawerLayout requireViewById = requireViewById(android.R.id.content_preview_text_layout);
        int count = ((ChooserActivity) this).mMultiProfilePagerAdapter.getCount();
        for (int i2 = 0; i2 < count; i2++) {
            RecyclerView findViewById = ((ChooserActivity) this).mMultiProfilePagerAdapter.getItem(i2).rootView.findViewById(android.R.id.sms_short_code_remember_choice_text);
            if (findViewById == null || !(findViewById instanceof RecyclerView)) {
                Log.wtf("MediaProjectionAppSelectorActivity", "MediaProjection only supports RecyclerView");
            } else {
                RecyclerView recyclerView = findViewById;
                recyclerView.setAccessibilityDelegate(new RecyclerViewExpandingAccessibilityDelegate(requireViewById, recyclerView));
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
            IMediaProjection asInterface = IMediaProjection.Stub.asInterface(getIntent().getIBinderExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION"));
            asInterface.setLaunchCookie(launchCookie);
            asInterface.setTaskId(i);
            Intent intent = new Intent();
            intent.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", asInterface.asBinder());
            setResult(-1, intent);
            setForceSendResultForMediaProjection();
            MediaProjectionServiceHelper.Companion companion = MediaProjectionServiceHelper.Companion;
            boolean z = this.reviewGrantedConsentRequired;
            companion.getClass();
            MediaProjectionServiceHelper.Companion.setReviewedConsentIfNeeded(2, z, asInterface);
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
        return mediaProjectionRecentsViewController.lastBoundData != null ? !r3.isEmpty() : false;
    }

    public final boolean shouldShowServiceTargets() {
        return false;
    }

    public final boolean shouldShowStickyContentPreviewWhenEmpty() {
        return shouldShowContentPreview();
    }

    public final void startSelected(int i, boolean z, boolean z2) {
        TargetInfo targetInfoForPosition = ((ChooserActivity) this).mChooserMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(i, z2);
        if (targetInfoForPosition == null || (targetInfoForPosition instanceof NotSelectableTargetInfo)) {
            return;
        }
        Intent intent = new Intent(targetInfoForPosition.getResolvedIntent());
        intent.setFlags(intent.getFlags() | QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.setFlags(intent.getFlags() & (-33554433));
        final ActivityOptions.LaunchCookie launchCookie = new ActivityOptions.LaunchCookie("media_projection_launch_token");
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchCookie(launchCookie);
        UserHandle userHandle = ((ChooserActivity) this).mMultiProfilePagerAdapter.getActiveListAdapter().getUserHandle();
        AsyncActivityLauncher asyncActivityLauncher = this.activityLauncher;
        Intrinsics.checkNotNull(userHandle);
        asyncActivityLauncher.startActivityAsUser(intent, userHandle, makeBasic.toBundle(), new Function1() { // from class: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity$startSelected$activityStarted$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                MediaProjectionAppSelectorActivity.this.returnSelectedApp(launchCookie, -1);
                return Unit.INSTANCE;
            }
        });
        targetInfoForPosition.isSuspended();
    }

    public MediaProjectionAppSelectorActivity(MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher) {
        this(factory, asyncActivityLauncher, null);
    }

    public final void onActivityStarted(TargetInfo targetInfo) {
    }
}
