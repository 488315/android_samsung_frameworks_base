package com.android.systemui.aibrief.control;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.R;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
public final class BriefNowBarController {
    public static final String NOWBAR_KEY = "AI_BRIEF_KEY";
    public static final String NOWBAR_PACKAGE = "com.android.systemui.aibrief";
    public static final String NOWBAR_REMOTE_VIEW_KEY = "AI_BRIEF_REMOTE_VIEW_KEY";
    public static final int NOWBAR_VIEW_STYLE = 7;
    public static final String SHOW_BRIEF_WITHOUT_UNLOCK = "show_brief_without_unlock";
    private static final String START_BRIEF_ACTIVITY = "com.samsung.android.smartsuggestions.intent.action.START_BRIEF_ACTIVITY";
    public static final String SUGGESTION_ACTIVITY = "com.samsung.android.smartsuggestions.feature.aisuggestion.ui.activity.SuggestionUiContentActivity";
    public static final String SUGGESTION_ONBOARDING_ACTIVITY = "com.samsung.android.smartsuggestions.feature.aisuggestion.ui.onboarding.OnboardingActivity";
    public static final String SUGGESTION_PACKAGE = "com.samsung.android.smartsuggestions";
    public static final String SUGGESTION_SPLASH_ACTIVITY = "com.samsung.android.smartsuggestions.feature.aisuggestion.ui.activity.SuggestionUiActivity";
    private static final String SUGGESTION_START_RECEIVER = "com.samsung.android.smartsuggestions.feature.aisuggestion.ui.receiver.AiSuggestionStartActivityReceiver";
    public static final String TAG = "NowBarController";
    private final ConfigurationController configurationController;
    private final BriefNowBarController$configurationListener$1 configurationListener;
    private final Context context;
    private final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
    private final BriefLogger logger;
    private final Handler mainHandler;
    private boolean needToUnlock;
    private final NotificationShadeWindowController notificationShadeWindowController;
    private final BriefNowBarController$nowBarCallback$1 nowBarCallback;
    private View nowBarCoverView;
    private View nowBarFullView;
    private View nowBarNormalView;
    private RemoteViews nowBarRemoteExpandView;
    private RemoteViews nowBarRemoteNormalCoverView;
    private RemoteViews nowBarRemoteNormalView;
    private PendingIntent nowBarRemotePendingIntent;
    private boolean nowBarShowing;
    private final PowerInteractor powerInteractor;
    private boolean prevFullScreenShowing;
    private boolean remoteNowBarShowing;
    private boolean screenOn;
    private final BriefViewController viewController;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private NowBarItem nowBarItem = new NowBarItem();
    private NowBarItem nowBarRemoteItem = new NowBarItem();

    /* renamed from: com.android.systemui.aibrief.control.BriefNowBarController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BriefNowBarController.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DistinctFlowImpl distinctFlowImpl = BriefNowBarController.this.powerInteractor.isAwake;
                final BriefNowBarController briefNowBarController = BriefNowBarController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.aibrief.control.BriefNowBarController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Boolean) obj2).booleanValue(), continuation);
                    }

                    public final Object emit(boolean z, Continuation continuation) {
                        briefNowBarController.screenOn = z;
                        briefNowBarController.updateNowBar(z);
                        briefNowBarController.updateRemoteNowBar(z);
                        briefNowBarController.viewController.updateViewAlpha(z);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (distinctFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.aibrief.control.BriefNowBarController$nowBarCallback$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.aibrief.control.BriefNowBarController$configurationListener$1, java.lang.Object] */
    public BriefNowBarController(Context context, BriefLogger briefLogger, BriefViewController briefViewController, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, NotificationShadeWindowController notificationShadeWindowController, PowerInteractor powerInteractor, ConfigurationController configurationController, Handler handler) {
        this.context = context;
        this.logger = briefLogger;
        this.viewController = briefViewController;
        this.faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.powerInteractor = powerInteractor;
        this.configurationController = configurationController;
        this.mainHandler = handler;
        ?? r1 = new BriefNowBarCallBack() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$nowBarCallback$1
            private final void onFullScreenShown() {
                Handler handler2 = this.this$0.mainHandler;
                final BriefNowBarController briefNowBarController = this.this$0;
                handler2.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$nowBarCallback$1$onFullScreenShown$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        briefNowBarController.startFullViewActivity();
                    }
                });
            }

            @Override // com.android.systemui.aibrief.control.BriefNowBarCallBack
            public void onFullScreenShowingChanged(boolean z) {
                if (this.this$0.prevFullScreenShowing != z) {
                    this.this$0.prevFullScreenShowing = z;
                    this.this$0.logger.d(BriefNowBarController.TAG, "onFullScreenShowingChanged, showing: " + z);
                    this.this$0.viewController.hideContainer();
                    if (z) {
                        onFullScreenShown();
                    }
                }
            }
        };
        this.nowBarCallback = r1;
        ?? r2 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onConfigChanged(Configuration configuration) {
                BriefNowBarController briefNowBarController = this.this$0;
                briefNowBarController.updateNowBar(briefNowBarController.screenOn);
                BriefNowBarController.updateRemoteNowBar$default(this.this$0, false, 1, null);
                this.this$0.viewController.onConfigurationChanged(configuration);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onDensityOrFontScaleChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onDisplayDeviceTypeChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onLocaleListChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onMaxBoundsChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onSmallestScreenWidthChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onThemeChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onUiModeChanged() {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onLayoutDirectionChanged(boolean z) {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onOrientationChanged(int i) {
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public /* bridge */ /* synthetic */ void onMovedToDisplay(int i, Configuration configuration) {
            }
        };
        this.configurationListener = r2;
        synchronized (faceWidgetNotificationControllerWrapper.mBriefCallbacks) {
            faceWidgetNotificationControllerWrapper.mBriefCallbacks.add(r1);
        }
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new AnonymousClass1(null), 3);
        ((ConfigurationControllerImpl) configurationController).addCallback(r2);
    }

    private final Intent briefIntent(String str, String str2) {
        Intent intent = new Intent();
        if (str != null) {
            intent.setAction(str);
        }
        intent.setClassName(SUGGESTION_PACKAGE, str2);
        intent.addFlags(335544352);
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromKeyguard", !this.needToUnlock);
        bundle.putBoolean("fromNowBar", true);
        intent.putExtras(bundle);
        return intent;
    }

    public static /* synthetic */ Intent briefIntent$default(BriefNowBarController briefNowBarController, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return briefNowBarController.briefIntent(str, str2);
    }

    private final Icon convertDrawableToIcon() {
        Drawable activityIcon = this.viewController.getActivityIcon(this.context);
        if (activityIcon == null) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(activityIcon.getIntrinsicWidth(), activityIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        activityIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        activityIcon.draw(canvas);
        return Icon.createWithBitmap(bitmapCreateBitmap);
    }

    private final PendingIntent createBroadcastPendingIntent() {
        return PendingIntent.getBroadcast(this.context, 0, briefIntent(START_BRIEF_ACTIVITY, SUGGESTION_START_RECEIVER), 201326592);
    }

    private final Bundle createExtraData(boolean z, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("nowbar_key_need_to_fullview", !z2);
        bundle.putBoolean("nowbar_key_ignore_bouncer", !z2);
        List<Integer> background = this.viewController.getBackground(z, z2);
        bundle.putIntArray("nowbar_key_linear_gradient", background != null ? CollectionsKt___CollectionsKt.toIntArray(background) : null);
        bundle.putString("nowbar_key_settings_card_title", this.context.getResources().getString(R.string.now_brief_app_name));
        bundle.putParcelable("nowbar_key_app_icon", convertDrawableToIcon());
        return bundle;
    }

    private final void hideNowBarItem(final NowBarItem nowBarItem, String str) {
        nowBarItem.setNowBarKey(str);
        nowBarItem.setNowBarPackage(NOWBAR_PACKAGE);
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController.hideNowBarItem.1
            @Override // java.lang.Runnable
            public final void run() {
                BriefNowBarController.this.faceWidgetNotificationControllerWrapper.removeItem(nowBarItem);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startFullViewActivity() {
        Object failure;
        KeyguardManager keyguardManager = (KeyguardManager) this.context.getSystemService("keyguard");
        Intent intentBriefIntent = briefIntent(null, SUGGESTION_SPLASH_ACTIVITY);
        PendingIntent activity = PendingIntent.getActivity(this.context, 0, intentBriefIntent, 67108864);
        try {
            int i = Result.$r8$clinit;
            if (this.needToUnlock) {
                Intent intent = new Intent();
                intent.putExtra("ignoreKeyguardState", true);
                keyguardManager.semSetPendingIntentAfterUnlock(activity, intent);
            } else {
                this.context.startActivity(intentBriefIntent);
            }
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
        if (thM3442exceptionOrNullimpl != null) {
            Log.e(TAG, "ERROR startFullViewActivity() " + thM3442exceptionOrNullimpl);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateNowBar(boolean z) {
        if (!this.nowBarShowing) {
            this.logger.d(TAG, "do not updateNowBar: showing false");
            return;
        }
        updateNowBarItem(this.nowBarItem, createExtraData(z, false), this.nowBarNormalView, this.nowBarCoverView);
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController.updateNowBar.1
            @Override // java.lang.Runnable
            public final void run() {
                BriefNowBarController.this.faceWidgetNotificationControllerWrapper.updateItem(BriefNowBarController.this.nowBarItem);
            }
        });
    }

    private final void updateNowBarItem(NowBarItem nowBarItem, Bundle bundle, View view, View view2) {
        nowBarItem.setOngoingNowbarView(null);
        nowBarItem.setOngoingExpandView(null);
        nowBarItem.setContentViewForExpandCard(this.nowBarFullView);
        nowBarItem.setContentViewForNormalCard(view);
        nowBarItem.setContentViewForNormalCardOnSubScreen(view2);
        nowBarItem.setContentViewForExpandCardOnSubScreen(this.nowBarFullView);
        nowBarItem.setNowBarKey(NOWBAR_KEY);
        nowBarItem.setNowBarPackage(NOWBAR_PACKAGE);
        nowBarItem.setNowBarViewStyle(7);
        nowBarItem.setExtraData(bundle);
        nowBarItem.setPendingIntentOnSubScreen(createBroadcastPendingIntent());
    }

    public static /* synthetic */ void updateNowBarItem$default(BriefNowBarController briefNowBarController, NowBarItem nowBarItem, Bundle bundle, View view, View view2, int i, Object obj) {
        if ((i & 8) != 0) {
            view2 = null;
        }
        briefNowBarController.updateNowBarItem(nowBarItem, bundle, view, view2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateRemoteNowBar(boolean z) {
        if (!this.remoteNowBarShowing) {
            this.logger.d(TAG, "do not updateRemoteNowBar: showing false");
        } else {
            updateRemoteNowbarItem(this.nowBarRemoteItem, z);
            this.mainHandler.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController.updateRemoteNowBar.1
                @Override // java.lang.Runnable
                public final void run() {
                    BriefNowBarController.this.faceWidgetNotificationControllerWrapper.updateItem(BriefNowBarController.this.nowBarRemoteItem);
                }
            });
        }
    }

    public static /* synthetic */ void updateRemoteNowBar$default(BriefNowBarController briefNowBarController, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        briefNowBarController.updateRemoteNowBar(z);
    }

    private final void updateRemoteNowbarItem(NowBarItem nowBarItem, boolean z) {
        Bundle bundleCreateExtraData = createExtraData(z, true);
        nowBarItem.setContentViewForExpandCard(null);
        nowBarItem.setContentViewForNormalCard(null);
        nowBarItem.setOngoingNowbarView(this.nowBarRemoteNormalView);
        nowBarItem.setOngoingExpandView(this.nowBarRemoteExpandView);
        RemoteViews remoteViews = this.nowBarRemoteNormalCoverView;
        if (remoteViews == null) {
            remoteViews = this.nowBarRemoteNormalView;
        }
        nowBarItem.setOngoingNowbarViewOnSubScreen(remoteViews);
        nowBarItem.setOngoingExpandViewOnSubScreen(this.nowBarRemoteExpandView);
        nowBarItem.setPendingIntent(this.nowBarRemotePendingIntent);
        nowBarItem.setNowBarKey(NOWBAR_REMOTE_VIEW_KEY);
        nowBarItem.setNowBarPackage(NOWBAR_PACKAGE);
        nowBarItem.setNowBarViewStyle(7);
        nowBarItem.setExtraData(bundleCreateExtraData);
    }

    public final void hideNowBar() {
        updateNowBarItem(this.nowBarItem, createExtraData(false, false), this.nowBarNormalView, this.nowBarCoverView);
        hideNowBarItem(this.nowBarItem, NOWBAR_KEY);
        this.nowBarNormalView = null;
        this.nowBarFullView = null;
        this.nowBarCoverView = null;
        this.nowBarShowing = false;
    }

    public final void hideRemoteNowBar() {
        hideNowBarItem(this.nowBarRemoteItem, NOWBAR_REMOTE_VIEW_KEY);
        this.remoteNowBarShowing = false;
        this.nowBarRemoteNormalView = null;
        this.nowBarRemoteExpandView = null;
        this.nowBarRemoteNormalCoverView = null;
        this.nowBarRemotePendingIntent = null;
    }

    public final void showNowBar(View view, View view2, View view3) {
        this.nowBarShowing = true;
        this.nowBarNormalView = view;
        this.nowBarFullView = view2;
        this.nowBarCoverView = view3;
        updateNowBar(this.screenOn);
    }

    public final void showNowBarRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2, RemoteViews remoteViews3, PendingIntent pendingIntent) {
        this.remoteNowBarShowing = true;
        this.nowBarRemoteNormalView = remoteViews;
        this.nowBarRemoteExpandView = remoteViews2;
        this.nowBarRemoteNormalCoverView = remoteViews3;
        this.nowBarRemotePendingIntent = pendingIntent;
        updateRemoteNowBar$default(this, false, 1, null);
        hideNowBar();
    }

    public final boolean startCircleAnimation(final Runnable runnable) {
        Log.d(TAG, "startCircleAnimation");
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController.startCircleAnimation.1
            @Override // java.lang.Runnable
            public final void run() {
                BriefNowBarController.this.viewController.showCircleAnimation(runnable);
            }
        });
        return true;
    }

    public final void updateNowBarNeedToUnlock(boolean z) {
        this.needToUnlock = z;
    }
}
