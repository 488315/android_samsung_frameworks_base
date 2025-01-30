package com.android.systemui.media;

import android.app.ActivityOptions;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.WaitResult;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.projection.IMediaProjection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.app.ChooserActivity;
import com.android.internal.app.ResolverListController;
import com.android.internal.app.chooser.NotSelectableTargetInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.android.systemui.R;
import com.android.systemui.media.MediaProjectionServiceHelper;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorResultHandler;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorView;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AsyncActivityLauncher;
import com.android.systemui.util.recycler.HorizontalSpacerItemDecoration;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaProjectionAppSelectorActivity extends ChooserActivity implements MediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler {
    public static final Companion Companion = new Companion(null);
    public final AsyncActivityLauncher activityLauncher;
    public MediaProjectionAppSelectorComponent component;
    public final MediaProjectionAppSelectorComponent.Factory componentFactory;
    public ConfigurationController configurationController;
    public MediaProjectionAppSelectorController controller;
    public final Function1 listControllerFactory;
    public MediaProjectionRecentsViewController recentsViewController;
    public boolean reviewGrantedConsentRequired;
    public boolean taskSelected;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaProjectionAppSelectorActivity(MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher, Function1 function1) {
        this.componentFactory = factory;
        this.activityLauncher = asyncActivityLauncher;
        this.listControllerFactory = function1;
    }

    public final int appliedThemeResId() {
        return 2132018539;
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
        ViewGroup viewGroup3 = (ViewGroup) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_projection_recent_tasks, viewGroup, false);
        View findViewById = viewGroup3.findViewById(R.id.media_projection_recent_tasks_container);
        mediaProjectionRecentsViewController.setTaskHeightSize(findViewById);
        View requireViewById = viewGroup3.requireViewById(R.id.media_projection_recent_tasks_loader);
        RecyclerView recyclerView = (RecyclerView) viewGroup3.requireViewById(R.id.media_projection_recent_tasks_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext(), 0, false));
        recyclerView.addItemDecoration(new HorizontalSpacerItemDecoration(viewGroup.getResources().getDimensionPixelOffset(R.dimen.media_projection_app_selector_recents_padding)));
        MediaProjectionRecentsViewController.Views views2 = new MediaProjectionRecentsViewController.Views(viewGroup3, findViewById, requireViewById, recyclerView);
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
        return new AbstractMultiProfilePagerAdapter.MyUserIdProvider() { // from class: com.android.systemui.media.MediaProjectionAppSelectorActivity$createMyUserIdProvider$1
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

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ConfigurationController configurationController = this.configurationController;
        if (configurationController == null) {
            configurationController = null;
        }
        ((ConfigurationControllerImpl) configurationController).onConfigurationChanged(configuration);
    }

    public final void onCreate(Bundle bundle) {
        MediaProjectionAppSelectorComponent create = this.componentFactory.create(this, this, this);
        this.component = create;
        if (create == null) {
            create = null;
        }
        this.configurationController = create.getConfigurationController();
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent = this.component;
        if (mediaProjectionAppSelectorComponent == null) {
            mediaProjectionAppSelectorComponent = null;
        }
        this.controller = mediaProjectionAppSelectorComponent.getController();
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent2 = this.component;
        if (mediaProjectionAppSelectorComponent2 == null) {
            mediaProjectionAppSelectorComponent2 = null;
        }
        this.recentsViewController = mediaProjectionAppSelectorComponent2.getRecentsViewController();
        Companion companion = Companion;
        Intent intent = getIntent();
        Resources resources = getResources();
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent3 = this.component;
        if (mediaProjectionAppSelectorComponent3 == null) {
            mediaProjectionAppSelectorComponent3 = null;
        }
        UserHandle hostUserHandle = mediaProjectionAppSelectorComponent3.getHostUserHandle();
        MediaProjectionAppSelectorComponent mediaProjectionAppSelectorComponent4 = this.component;
        if (mediaProjectionAppSelectorComponent4 == null) {
            mediaProjectionAppSelectorComponent4 = null;
        }
        UserHandle personalProfileUserHandle = mediaProjectionAppSelectorComponent4.getPersonalProfileUserHandle();
        companion.getClass();
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.LAUNCHER");
        intent.putExtra("android.intent.extra.INTENT", intent2);
        intent.putExtra("android.intent.extra.TITLE", resources.getString(R.string.screen_share_permission_app_selector_title));
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", !Intrinsics.areEqual(hostUserHandle, personalProfileUserHandle) ? 1 : 0);
        this.reviewGrantedConsentRequired = getIntent().getBooleanExtra("extra_media_projection_user_consent_required", false);
        super.onCreate(bundle);
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.controller;
        (mediaProjectionAppSelectorController != null ? mediaProjectionAppSelectorController : null).init();
    }

    public final void onDestroy() {
        if (!this.taskSelected) {
            MediaProjectionServiceHelper.Companion companion = MediaProjectionServiceHelper.Companion;
            boolean z = this.reviewGrantedConsentRequired;
            companion.getClass();
            MediaProjectionServiceHelper.Companion.setReviewedConsentIfNeeded(0, z, null);
        }
        this.activityLauncher.pendingCallback = null;
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.controller;
        CoroutineScopeKt.cancel$default((mediaProjectionAppSelectorController != null ? mediaProjectionAppSelectorController : null).scope);
        super.onDestroy();
    }

    public final void returnSelectedApp(IBinder iBinder) {
        this.taskSelected = true;
        if (getIntent().hasExtra("capture_region_result_receiver")) {
            ResultReceiver resultReceiver = (ResultReceiver) getIntent().getParcelableExtra("capture_region_result_receiver", ResultReceiver.class);
            MediaProjectionCaptureTarget mediaProjectionCaptureTarget = new MediaProjectionCaptureTarget(iBinder);
            Bundle bundle = new Bundle();
            bundle.putParcelable("capture_region", mediaProjectionCaptureTarget);
            resultReceiver.send(-1, bundle);
        } else {
            IMediaProjection asInterface = IMediaProjection.Stub.asInterface(getIntent().getIBinderExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION"));
            asInterface.setLaunchCookie(iBinder);
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
        return true;
    }

    public final boolean shouldShowContentPreviewWhenEmpty() {
        return true;
    }

    public final void startSelected(int i, boolean z, boolean z2) {
        TargetInfo targetInfoForPosition = ((ChooserActivity) this).mChooserMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(i, z2);
        if (targetInfoForPosition == null || (targetInfoForPosition instanceof NotSelectableTargetInfo)) {
            return;
        }
        final Intent intent = new Intent(targetInfoForPosition.getResolvedIntent());
        intent.setFlags(intent.getFlags() | QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.setFlags(intent.getFlags() & (-33554433));
        final Binder binder = new Binder("media_projection_launch_token");
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchCookie(binder);
        final UserHandle userHandle = ((ChooserActivity) this).mMultiProfilePagerAdapter.getActiveListAdapter().getUserHandle();
        final AsyncActivityLauncher asyncActivityLauncher = this.activityLauncher;
        final Bundle bundle = makeBasic.toBundle();
        Function1 function1 = new Function1() { // from class: com.android.systemui.media.MediaProjectionAppSelectorActivity$startSelected$activityStarted$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                MediaProjectionAppSelectorActivity.this.returnSelectedApp(binder);
                return Unit.INSTANCE;
            }
        };
        if (asyncActivityLauncher.pendingCallback == null) {
            asyncActivityLauncher.pendingCallback = function1;
            intent.setFlags(intent.getFlags() | QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            asyncActivityLauncher.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.util.AsyncActivityLauncher$startActivityAsUser$1
                @Override // java.lang.Runnable
                public final void run() {
                    AsyncActivityLauncher asyncActivityLauncher2 = AsyncActivityLauncher.this;
                    final WaitResult startActivityAndWait = asyncActivityLauncher2.activityTaskManager.startActivityAndWait((IApplicationThread) null, asyncActivityLauncher2.context.getPackageName(), AsyncActivityLauncher.this.context.getAttributionTag(), intent, (String) null, (IBinder) null, (String) null, 0, 0, (ProfilerInfo) null, bundle, userHandle.getIdentifier());
                    final AsyncActivityLauncher asyncActivityLauncher3 = AsyncActivityLauncher.this;
                    asyncActivityLauncher3.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.util.AsyncActivityLauncher$startActivityAsUser$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Function1 function12 = AsyncActivityLauncher.this.pendingCallback;
                            if (function12 != null) {
                                function12.invoke(startActivityAndWait);
                            }
                        }
                    });
                }
            });
        }
        targetInfoForPosition.isSuspended();
    }

    public MediaProjectionAppSelectorActivity(MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher) {
        this(factory, asyncActivityLauncher, null);
    }

    public final void onActivityStarted(TargetInfo targetInfo) {
    }
}
