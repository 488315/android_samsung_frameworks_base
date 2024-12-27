package com.android.systemui.aibrief.control;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.aibrief.data.NowBarData;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BriefNowBarController {
    public static final String NOWBAR_KEY = "AI_BRIEF_KEY";
    public static final String NOWBAR_PACKAGE = "com.android.systemui.aibrief";
    public static final String NOWBAR_REMOTE_VIEW_KEY = "AI_BRIEF_REMOTE_VIEW_KEY";
    public static final int NOWBAR_VIEW_STYLE = 7;
    public static final String SUGGESTION_ACTIVITY = "com.samsung.android.smartsuggestions.feature.aisuggestion.ui.activity.SuggestionUiActivity";
    public static final String SUGGESTION_PACKAGE = "com.samsung.android.smartsuggestions";
    public static final String TAG = "NowBarController";
    private final ArrayList<Integer> aodNowBarBG;
    private final ConfigurationController configurationController;
    private final BriefNowBarController$configurationListener$1 configurationListener;
    private final Context context;
    private final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
    private final BriefLogger logger;
    private final Handler mainHandler;
    private final NotificationShadeWindowController notificationShadeWindowController;
    private final BriefNowBarController$nowBarCallback$1 nowBarCallback;
    private NowBarData nowBarData;
    private NowBarItem nowBarItem = new NowBarItem();
    private NowBarItem nowBarRemoteItem = new NowBarItem();
    private final PowerInteractor powerInteractor;
    private final BriefViewController viewController;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                        if (LsRune.LOCKUI_NOW_BAR_SUPPORT_GUIDING_EFFECT) {
                            BriefNowBarController.this.updateNowBar(z);
                        } else {
                            BriefNowBarController.this.viewController.setNowBarBackground(BriefNowBarController.this.viewController.getNowBarBackground(BriefNowBarController.this.nowBarData, z, BriefNowBarController.this.aodNowBarBG));
                        }
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.aibrief.control.BriefNowBarController$nowBarCallback$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.aibrief.control.BriefNowBarController$configurationListener$1] */
    public BriefNowBarController(Context context, BriefLogger briefLogger, BriefViewController briefViewController, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, NotificationShadeWindowController notificationShadeWindowController, PowerInteractor powerInteractor, ConfigurationController configurationController, Handler handler) {
        this.context = context;
        this.logger = briefLogger;
        this.viewController = briefViewController;
        this.faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.powerInteractor = powerInteractor;
        this.configurationController = configurationController;
        this.mainHandler = handler;
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(Color.parseColor("#8C000000")));
        arrayList.add(Integer.valueOf(Color.parseColor("#8C000000")));
        this.aodNowBarBG = arrayList;
        ?? r1 = new BriefNowBarCallBack() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$nowBarCallback$1
            private final void onFullScreenHidden() {
                BriefNowBarController.this.viewController.hideContainer();
            }

            private final void onFullScreenShown() {
                BriefLogger briefLogger2;
                Handler handler2;
                Log.d(BriefNowBarController.TAG, "onShownFullView");
                if (BriefNowBarController.this.nowBarData != null) {
                    final BriefNowBarController briefNowBarController = BriefNowBarController.this;
                    briefLogger2 = briefNowBarController.logger;
                    briefLogger2.d(BriefNowBarController.TAG, "notificationShadeWindowController.setDemoUserTimeout 10min");
                    handler2 = briefNowBarController.mainHandler;
                    handler2.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$nowBarCallback$1$onFullScreenShown$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BriefNowBarController.this.startFullViewActivity();
                        }
                    });
                }
            }

            @Override // com.android.systemui.aibrief.control.BriefNowBarCallBack
            public void onFullScreenShowingChanged(boolean z) {
                if (z) {
                    onFullScreenShown();
                } else {
                    onFullScreenHidden();
                }
            }
        };
        this.nowBarCallback = r1;
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onConfigChanged(Configuration configuration) {
                if (LsRune.LOCKUI_NOW_BAR_SUPPORT_GUIDING_EFFECT) {
                    BriefNowBarController.updateNowBar$default(BriefNowBarController.this, false, 1, null);
                    return;
                }
                BriefNowBarController.this.viewController.setNowBarBackground(BriefViewController.getNowBarBackground$default(BriefNowBarController.this.viewController, BriefNowBarController.this.nowBarData, false, null, 6, null));
                BriefNowBarController.this.viewController.setNowBarIconBackgroundOnUpdate(BriefNowBarController.this.nowBarData);
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
        };
        synchronized (faceWidgetNotificationControllerWrapper.mBriefCallbacks) {
            faceWidgetNotificationControllerWrapper.mBriefCallbacks.add(r1);
        }
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new AnonymousClass1(null), 3);
    }

    private final void hideHowBarItem(final NowBarItem nowBarItem, String str) {
        nowBarItem.setNowBarKey(str);
        nowBarItem.setNowBarPackage(NOWBAR_PACKAGE);
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$hideHowBarItem$1
            @Override // java.lang.Runnable
            public final void run() {
                FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
                faceWidgetNotificationControllerWrapper = BriefNowBarController.this.faceWidgetNotificationControllerWrapper;
                faceWidgetNotificationControllerWrapper.removeItem(nowBarItem);
            }
        });
    }

    private final boolean isDarkMode() {
        return (this.context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startFullViewActivity() {
        Object failure;
        try {
            int i = Result.$r8$clinit;
            ActivityStarter activityStarter = (ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class);
            Intent intent = new Intent();
            intent.setClassName(SUGGESTION_PACKAGE, SUGGESTION_ACTIVITY);
            intent.addFlags(335544352);
            activityStarter.startActivityDismissingKeyguard(intent, true, true);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
        if (m2527exceptionOrNullimpl != null) {
            Log.e(TAG, "ERROR startFullViewActivity() " + m2527exceptionOrNullimpl);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateNowBar(boolean z) {
        ArrayList<Integer> arrayList;
        if (this.nowBarData == null) {
            return;
        }
        if (!z) {
            arrayList = this.aodNowBarBG;
        } else if (isDarkMode()) {
            NowBarData nowBarData = this.nowBarData;
            if (nowBarData != null) {
                arrayList = nowBarData.getBackgroundForDark();
            }
            arrayList = null;
        } else {
            NowBarData nowBarData2 = this.nowBarData;
            if (nowBarData2 != null) {
                arrayList = nowBarData2.getBackground();
            }
            arrayList = null;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("nowbar_key_need_to_fullview", true);
        bundle.putIntArray("nowbar_key_linear_gradient", arrayList != null ? CollectionsKt___CollectionsKt.toIntArray(arrayList) : null);
        this.nowBarItem.setExtraData(bundle);
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$updateNowBar$1
            @Override // java.lang.Runnable
            public final void run() {
                FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
                NowBarItem nowBarItem;
                faceWidgetNotificationControllerWrapper = BriefNowBarController.this.faceWidgetNotificationControllerWrapper;
                nowBarItem = BriefNowBarController.this.nowBarItem;
                faceWidgetNotificationControllerWrapper.updateItem(nowBarItem);
            }
        });
    }

    public static /* synthetic */ void updateNowBar$default(BriefNowBarController briefNowBarController, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        briefNowBarController.updateNowBar(z);
    }

    public final void hideNowBar() {
        hideHowBarItem(this.nowBarItem, NOWBAR_KEY);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationListener);
        this.nowBarData = null;
    }

    public final void hideRemoteNowBar() {
        hideHowBarItem(this.nowBarRemoteItem, NOWBAR_REMOTE_VIEW_KEY);
    }

    public final void showNowBar(View view, View view2, NowBarData nowBarData) {
        this.nowBarItem.setNowBarKey(NOWBAR_KEY);
        this.nowBarItem.setNowBarPackage(NOWBAR_PACKAGE);
        this.nowBarItem.setNowBarViewStyle(7);
        this.nowBarItem.setOngoingNowbarView(null);
        this.nowBarItem.setOngoingExpandView(null);
        this.nowBarItem.setContentViewForExpandCard(view2);
        this.nowBarItem.setContentViewForNormalCard(view);
        this.nowBarData = nowBarData;
        updateNowBar$default(this, false, 1, null);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
    }

    public final void showNowBarRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
        this.nowBarRemoteItem.setNowBarKey(NOWBAR_REMOTE_VIEW_KEY);
        this.nowBarRemoteItem.setNowBarPackage(NOWBAR_PACKAGE);
        this.nowBarRemoteItem.setNowBarViewStyle(7);
        this.nowBarRemoteItem.setContentViewForExpandCard(null);
        this.nowBarRemoteItem.setContentViewForNormalCard(null);
        this.nowBarRemoteItem.setOngoingNowbarView(remoteViews);
        this.nowBarRemoteItem.setOngoingExpandView(remoteViews2);
        Bundle bundle = new Bundle();
        bundle.putBoolean("nowbar_key_need_to_fullview", false);
        this.nowBarRemoteItem.setExtraData(bundle);
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$showNowBarRemoteView$1
            @Override // java.lang.Runnable
            public final void run() {
                FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
                NowBarItem nowBarItem;
                faceWidgetNotificationControllerWrapper = BriefNowBarController.this.faceWidgetNotificationControllerWrapper;
                nowBarItem = BriefNowBarController.this.nowBarRemoteItem;
                faceWidgetNotificationControllerWrapper.updateItem(nowBarItem);
            }
        });
    }

    public final boolean startCircleAnimation(final Runnable runnable) {
        Log.d(TAG, "startCircleAnimation");
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.aibrief.control.BriefNowBarController$startCircleAnimation$1
            @Override // java.lang.Runnable
            public final void run() {
                BriefNowBarController.this.viewController.showCircleAnimation(runnable);
            }
        });
        return true;
    }
}
