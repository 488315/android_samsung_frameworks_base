package com.android.systemui.edgelighting;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.LruCache;
import android.util.Slog;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.EdgeLightingService;
import com.android.systemui.edgelighting.data.EdgeLightingSettingItem;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.edgelighting.device.EdgeLightingCoverManager;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.container.NotificationEffect;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback;
import com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher;
import com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener;
import com.android.systemui.edgelighting.manager.ContextStatusLoggingManager;
import com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager;
import com.android.systemui.edgelighting.manager.EdgeLightingSettingManager;
import com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver;
import com.android.systemui.edgelighting.scheduler.ApplicationLightingScheduler;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.C13553;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.C13564;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.C13575;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.C13586;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScreenStatus;
import com.android.systemui.edgelighting.scheduler.LightingScheduleInfo;
import com.android.systemui.edgelighting.scheduler.NotificationLightingScheduler;
import com.android.systemui.edgelighting.turnover.CallStateObserver;
import com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting;
import com.android.systemui.edgelighting.utils.AppIconCache;
import com.android.systemui.edgelighting.utils.DeviceColorMonitor;
import com.android.systemui.edgelighting.utils.DrawableUtils;
import com.android.systemui.edgelighting.utils.EdgeLightingAnalytics;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.SemEdgeLightingInfoUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.android.systemui.shade.SecHideInformationMirroringModel;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.edge.OnEdgeLightingCallback;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.edge.SemEdgeManager;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.cover.ScoverManager;
import com.samsung.android.sdk.cover.ScoverState;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.presence.ServiceTuple;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class EdgeLightingService extends Service {
    public static boolean sConfigured;
    public static int sFlipFont;
    public AppIconCache mAppIconCache;
    public AudioManager mAudioManager;
    public ISystemUIConditionListener mConditionListener;
    public final ServiceConnectionC13038 mConnection;
    public final C13049 mDBObserver;
    public DevicePolicyManager mDevicePolicyManager;
    public EdgeLightingDispatcher mDispatcher;
    public final C12972 mEdgeLightingObserver;
    public SemEdgeManager mEdgeManager;
    public C13016 mFoldStateListener;
    public final MainHandler mHandler;
    public boolean mIsColorThemeEnabled;
    public final RunnableC12961 mKillBot;
    public final C12983 mOnEdgeLightingCallback;
    public PowerManager mPowerManager;
    public EdgeLightingScheduler mScheduler;
    public boolean mShouldKillMyself;
    public boolean mShouldShowAppIcon;
    public StatusbarStateReceiver mStatusBarReceiver;
    public final IBinder mForegroundToken = new Binder();
    public C13027 mCoverStateListener = null;
    public boolean mIsStarted = false;
    public int mCondition = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$1 */
    public final class RunnableC12961 implements Runnable {
        public RunnableC12961() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            EdgeLightingService edgeLightingService = EdgeLightingService.this;
            if (edgeLightingService.mShouldKillMyself) {
                edgeLightingService.stopEdgeLightingService();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$3 */
    public final class C12983 implements OnEdgeLightingCallback {
        public C12983() {
        }

        public final void onScreenChanged(boolean z) {
            if (!z) {
                EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                if (edgeLightingScheduler != null) {
                    edgeLightingScheduler.notifyScreenOff();
                    return;
                }
                return;
            }
            EdgeLightingScheduler edgeLightingScheduler2 = EdgeLightingService.this.mScheduler;
            if (edgeLightingScheduler2 != null) {
                edgeLightingScheduler2.notifyScreenOn();
            }
            EdgeLightingDispatcher edgeLightingDispatcher = EdgeLightingService.this.mDispatcher;
            if (edgeLightingDispatcher != null) {
                EdgeLightingDialog edgeLightingDialog = edgeLightingDispatcher.mDialog;
                if (edgeLightingDialog != null ? edgeLightingDialog.isShowing() : edgeLightingDispatcher.mEffectServiceConrtroller.mStarting) {
                    EdgeLightingService.this.mDispatcher.refreshBackground();
                }
            }
        }

        public final void onStartEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
            EdgeLightingService.this.mHandler.post(new EdgeLightingService$$ExternalSyntheticLambda0(this, str, semEdgeLightingInfo, i, 1));
            EdgeLightingService.this.mHandler.removeMessages(1);
            EdgeLightingService.this.mShouldKillMyself = false;
        }

        public final void onStopEdgeLighting(String str, int i) {
            EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
            if (edgeLightingScheduler != null) {
                Slog.d("EdgeLightingScheduler", "stopEdgeLighting: " + i + " " + str);
                LightingScheduleInfo lightingScheduleInfo = new LightingScheduleInfo(str, "", null, null, i, 0);
                EdgeLightingScheduler.HandlerC13531 handlerC13531 = edgeLightingScheduler.mHandler;
                handlerC13531.sendMessage(Message.obtain(handlerC13531, 1, lightingScheduleInfo));
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$4 */
    public final class C12994 {
        public C12994() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final EdgeLightingDispatcher getUIController(boolean z) {
            final EdgeLightingService edgeLightingService = EdgeLightingService.this;
            if (edgeLightingService.mDispatcher == null) {
                Slog.i("EdgeLightingService", "createEdgeLightingDialog make dispatcher " + z);
                int intForUser = Settings.System.getIntForUser(edgeLightingService.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2);
                boolean z2 = ((intForUser == 1 ? (char) 1 : intForUser == 2 ? (char) 2 : (char) 3) & 2) != 0;
                EdgeLightingDispatcher edgeLightingDispatcher = new EdgeLightingDispatcher(edgeLightingService.getBaseContext(), z2 ? 2227 : 2228, z);
                edgeLightingService.mDispatcher = edgeLightingDispatcher;
                edgeLightingDispatcher.registerEdgeWindowCallback(new IEdgeLightingWindowCallback() { // from class: com.android.systemui.edgelighting.EdgeLightingService.5
                    public final SecHideInformationMirroringModel mMirroringModel = new SecHideInformationMirroringModel();

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void doActionNotification() {
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        edgeLightingScheduler.getClass();
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null) {
                            ArrayList actionList = notificationLightingScheduler.mCurrentLightingScheduleInfo.getActionList();
                            String string = edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getString(R.string.restrict_mark_as_read);
                            if (actionList != null) {
                                Iterator it = actionList.iterator();
                                while (it.hasNext()) {
                                    Notification.Action action = (Notification.Action) it.next();
                                    if (TextUtils.equals(string, action.title)) {
                                        try {
                                            action.actionIntent.send();
                                            edgeLightingScheduler.mNotificationLightingScheduler.flushNotiNow();
                                        } catch (PendingIntent.CanceledException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onClickExpandButton(String str) {
                        boolean z3 = EdgeLightingService.sConfigured;
                        EdgeLightingService edgeLightingService2 = EdgeLightingService.this;
                        edgeLightingService2.getClass();
                        try {
                            ISystemUIConditionListener iSystemUIConditionListener = edgeLightingService2.mConditionListener;
                            if (iSystemUIConditionListener != null) {
                                iSystemUIConditionListener.turnToHeadsUp(str);
                            }
                        } catch (RemoteException unused) {
                        }
                        EdgeLightingAnalytics.sendEventLog(EdgeLightingAnalytics.sCurrentScreenID, "QPNE0104");
                    }

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onClickToastInWindow() {
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null) {
                            notificationLightingScheduler.flushNotiNow();
                            if (edgeLightingScheduler.mNotificationLightingScheduler.mCurrentLightingScheduleInfo != null) {
                                edgeLightingScheduler.mRequester.getClass();
                                edgeLightingScheduler.mRequester.sendClickEvent(edgeLightingScheduler.mNotificationLightingScheduler.mCurrentLightingScheduleInfo.getNotificationKey());
                            }
                        }
                    }

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onDismissEdgeWindow() {
                        boolean z3 = EdgeLightingService.sConfigured;
                        Slog.i("EdgeLightingService", " onDismissEdgeWindow");
                        EdgeLightingService edgeLightingService2 = EdgeLightingService.this;
                        EdgeLightingScheduler edgeLightingScheduler = edgeLightingService2.mScheduler;
                        if (edgeLightingScheduler != null) {
                            edgeLightingScheduler.notifyEdgeLightingPackageList(true);
                        }
                        long j = (edgeLightingService2.mPowerManager.isInteractive() || Utils.isLargeCoverFlipFolded()) ? 500L : 5000L;
                        MainHandler mainHandler = edgeLightingService2.mHandler;
                        mainHandler.sendMessageDelayed(mainHandler.obtainMessage(1, "onDismissEdgeWindow"), j);
                        EdgeLightingScheduler edgeLightingScheduler2 = edgeLightingService2.mScheduler;
                        PowerManager.WakeLock wakeLock = edgeLightingScheduler2.mWakeLock;
                        if (wakeLock == null || !wakeLock.isHeld()) {
                            return;
                        }
                        edgeLightingScheduler2.mWakeLock.release();
                    }

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onExtendLightingDuration() {
                        NotificationLightingScheduler notificationLightingScheduler = EdgeLightingService.this.mScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null) {
                            notificationLightingScheduler.extendLightingDuration(5500, false);
                        }
                    }

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onFling(boolean z3, boolean z4) {
                        if (z3) {
                            EdgeLightingAnalytics.sendEventLog(EdgeLightingAnalytics.sCurrentScreenID, "QPNE0103");
                        }
                        if (CoreRune.MW_SA_LOGGING && z3) {
                            CoreSaLogger.logForAdvanced("2004", "From Noti_Swipedown");
                        }
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        if (!z3) {
                            NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                            if (notificationLightingScheduler != null) {
                                notificationLightingScheduler.flushNotiNow();
                                return;
                            }
                            return;
                        }
                        NotificationLightingScheduler notificationLightingScheduler2 = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler2 == null || notificationLightingScheduler2.mCurrentLightingScheduleInfo == null) {
                            return;
                        }
                        notificationLightingScheduler2.flushNotiNow();
                        LightingScheduleInfo lightingScheduleInfo = edgeLightingScheduler.mNotificationLightingScheduler.mCurrentLightingScheduleInfo;
                        if (!z4) {
                            Slog.i("EdgeLightingScheduler", " Not activity pending intent. : " + lightingScheduleInfo.mPackageName);
                            Toast.makeText(edgeLightingScheduler.mTurnOverEdgeLighting.mContext, R.string.edge_lighting_can_not_open_popup_view, 0).show();
                        }
                        edgeLightingScheduler.mRequester.sendClickEvent(lightingScheduleInfo.getNotificationKey());
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r6v1, types: [android.content.pm.PackageManager] */
                    /* JADX WARN: Type inference failed for: r6v13 */
                    /* JADX WARN: Type inference failed for: r6v15 */
                    /* JADX WARN: Type inference failed for: r6v16 */
                    /* JADX WARN: Type inference failed for: r6v2, types: [android.content.pm.PackageManager] */
                    /* JADX WARN: Type inference failed for: r6v3, types: [android.graphics.drawable.Drawable] */
                    /* JADX WARN: Type inference failed for: r6v4, types: [android.graphics.drawable.Drawable] */
                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onFlingDownInWindow(boolean z3) {
                        boolean z4 = EdgeLightingService.sConfigured;
                        Slog.i("EdgeLightingService", "onFlingDownInWindow " + z3);
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler == null || notificationLightingScheduler.mCurrentLightingScheduleInfo == null) {
                            return;
                        }
                        notificationLightingScheduler.flushNotiNow();
                        LightingScheduleInfo lightingScheduleInfo = edgeLightingScheduler.mNotificationLightingScheduler.mCurrentLightingScheduleInfo;
                        if (z3) {
                            EdgeLightingDispatcher uIController = edgeLightingScheduler.mRequester.getUIController(false);
                            if (!(uIController instanceof EdgeLightingDispatcher)) {
                                uIController = null;
                            }
                            if (uIController != null) {
                                Context context = edgeLightingScheduler.mContext;
                                PendingIntent contentIntent = lightingScheduleInfo.getContentIntent();
                                String str = lightingScheduleInfo.mPackageName;
                                NotificationEffect notificationEffect = uIController.mDialog.mNotificationEffect;
                                int i = Utils.$r8$clinit;
                                ?? packageManager = context.getPackageManager();
                                try {
                                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 795136);
                                    packageManager = applicationInfo != null ? packageManager.getApplicationIcon(applicationInfo) : packageManager.getDefaultActivityIcon();
                                } catch (PackageManager.NameNotFoundException unused) {
                                    packageManager = packageManager.getDefaultActivityIcon();
                                }
                                Bitmap drawableToBitmap = DrawableUtils.drawableToBitmap(packageManager);
                                ImageView imageView = new ImageView(context);
                                imageView.setImageBitmap(drawableToBitmap);
                                imageView.layout(0, 0, context.getResources().getDimensionPixelSize(R.dimen.drag_and_drop_icon_size), context.getResources().getDimensionPixelSize(R.dimen.drag_and_drop_icon_size));
                                ClipDescription clipDescription = new ClipDescription("Drag And Drop(E)", new String[]{"application/vnd.android.activity"});
                                Intent intent = new Intent();
                                intent.putExtra("android.intent.extra.PENDING_INTENT", contentIntent);
                                intent.putExtra("android.intent.extra.USER", Process.myUserHandle());
                                intent.putExtra("com.samsung.android.intent.extra.DRAG_AND_DROP_REQUESTER", "edgelighting");
                                if (!notificationEffect.startDragAndDrop(new ClipData(clipDescription, new ClipData.Item(intent)), new View.DragShadowBuilder(imageView), null, 1048832)) {
                                    Slog.i("EdgeLightingScheduler", " Not activity pending intent. : " + str);
                                    Toast.makeText(edgeLightingScheduler.mTurnOverEdgeLighting.mContext, R.string.edge_lighting_can_not_open_popup_view, 0).show();
                                }
                            }
                        }
                        edgeLightingScheduler.mRequester.sendClickEvent(lightingScheduleInfo.getNotificationKey());
                    }

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onShowEdgeWindow() {
                        boolean z3 = EdgeLightingService.sConfigured;
                        Slog.i("EdgeLightingService", " onShowEdgeWindow");
                        EdgeLightingService edgeLightingService2 = EdgeLightingService.this;
                        edgeLightingService2.mHandler.removeMessages(1);
                        if (this.mMirroringModel.shouldHideInformation()) {
                            Slog.d("EdgeLightingService", "HideInformationMirroring addInternalPresentationWindowFlag()");
                            EdgeLightingDispatcher edgeLightingDispatcher2 = edgeLightingService2.mDispatcher;
                            if (edgeLightingDispatcher2 != null && edgeLightingDispatcher2.getWindow() != null && edgeLightingService2.mDispatcher.getWindow().getDecorView() != null) {
                                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) edgeLightingService2.mDispatcher.getWindow().getDecorView().getLayoutParams();
                                if (layoutParams != null) {
                                    layoutParams.semAddExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
                                }
                                WindowManager windowManager = edgeLightingService2.mDispatcher.getWindow().getWindowManager();
                                if (windowManager != null) {
                                    windowManager.updateViewLayout(edgeLightingService2.mDispatcher.getWindow().getDecorView(), layoutParams);
                                }
                            }
                        }
                        edgeLightingService2.mScheduler.notifyEdgeLightingPackageList(false);
                        EdgeLightingDispatcher edgeLightingDispatcher3 = edgeLightingService2.mDispatcher;
                        if (edgeLightingDispatcher3 == null || edgeLightingDispatcher3.getWindow() == null || edgeLightingService2.mDispatcher.getWindow().getDecorView() == null) {
                            return;
                        }
                        edgeLightingService2.mDispatcher.getWindow().getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.edgelighting.EdgeLightingService.5.1
                            @Override // android.view.View.AccessibilityDelegate
                            public final void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
                                if (accessibilityEvent.getEventType() == 32) {
                                    return;
                                }
                                super.sendAccessibilityEventUnchecked(view, accessibilityEvent);
                            }
                        });
                    }

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onSwipeToastInWindow() {
                        boolean z3;
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null) {
                            LightingScheduleInfo lightingScheduleInfo = notificationLightingScheduler.mCurrentLightingScheduleInfo;
                            if (lightingScheduleInfo != null) {
                                try {
                                    SemEdgeManager semEdgeManager = edgeLightingScheduler.mEdgeManager;
                                    String str = lightingScheduleInfo.mPackageName;
                                    String notificationTag = lightingScheduleInfo.getNotificationTag();
                                    int notificationID = lightingScheduleInfo.getNotificationID();
                                    int userId = lightingScheduleInfo.getUserId();
                                    String notificationKey = lightingScheduleInfo.getNotificationKey();
                                    Bundle extra = lightingScheduleInfo.mLightingInfo.getExtra();
                                    semEdgeManager.cancelNotificationByGroupKey(str, notificationTag, notificationID, userId, notificationKey, extra != null ? extra.getString("group_key") : null);
                                    z3 = true;
                                } catch (RuntimeException unused) {
                                    z3 = false;
                                }
                                String str2 = lightingScheduleInfo.mPackageName;
                                if (z3) {
                                    StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m(" swipe cancel pkg: ", str2, " , tag :  ");
                                    m4m.append(lightingScheduleInfo.getNotificationTag());
                                    m4m.append(" id: ");
                                    m4m.append(lightingScheduleInfo.getNotificationID());
                                    m4m.append(" , userid : ");
                                    m4m.append(lightingScheduleInfo.getUserId());
                                    m4m.append(" , key : ");
                                    m4m.append(lightingScheduleInfo.getNotificationKey());
                                    m4m.append(" , groupKey : ");
                                    Bundle extra2 = lightingScheduleInfo.mLightingInfo.getExtra();
                                    m4m.append(extra2 != null ? extra2.getString("group_key") : null);
                                    Slog.i("EdgeLightingScheduler", m4m.toString());
                                } else {
                                    edgeLightingScheduler.mEdgeManager.cancelNotification(str2, lightingScheduleInfo.getNotificationTag(), lightingScheduleInfo.getNotificationID(), lightingScheduleInfo.getUserId(), lightingScheduleInfo.getNotificationKey());
                                    StringBuilder m4m2 = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m(" swipe cancel pkg: ", str2, " , tag :  ");
                                    m4m2.append(lightingScheduleInfo.getNotificationTag());
                                    m4m2.append(" id: ");
                                    m4m2.append(lightingScheduleInfo.getNotificationID());
                                    m4m2.append(" , userid : ");
                                    m4m2.append(lightingScheduleInfo.getUserId());
                                    m4m2.append(" , key : ");
                                    m4m2.append(lightingScheduleInfo.getNotificationKey());
                                    Slog.i("EdgeLightingScheduler", m4m2.toString());
                                }
                            }
                            edgeLightingScheduler.mNotificationLightingScheduler.flushNotiNow();
                        }
                    }
                });
                EdgeLightingDialog edgeLightingDialog = edgeLightingService.mDispatcher.mDialog;
                if (edgeLightingDialog != null) {
                    edgeLightingDialog.mDozeDraw = z2;
                }
            }
            return edgeLightingService.mDispatcher;
        }

        public final boolean isNeedToSanitized(int i, int i2, String str) {
            try {
                ISystemUIConditionListener iSystemUIConditionListener = EdgeLightingService.this.mConditionListener;
                if (iSystemUIConditionListener != null) {
                    return iSystemUIConditionListener.isNeedToSanitize(i, i2, str);
                }
                return false;
            } catch (RemoteException e) {
                boolean z = EdgeLightingService.sConfigured;
                Slog.d("EdgeLightingService", "Remote exception in isNeedToSanitized " + e.getMessage());
                return false;
            }
        }

        public final boolean isScreenOn() {
            return EdgeLightingService.this.mPowerManager.isInteractive();
        }

        public final boolean isUIControllerExist() {
            return EdgeLightingService.this.mDispatcher != null;
        }

        public final void requestStopService() {
            EdgeLightingService edgeLightingService = EdgeLightingService.this;
            if (edgeLightingService.mHandler.hasMessages(1)) {
                edgeLightingService.mHandler.removeMessages(1);
            }
            MainHandler mainHandler = edgeLightingService.mHandler;
            mainHandler.sendMessageDelayed(mainHandler.obtainMessage(1, "requestStopService"), 500L);
        }

        public final void sendClickEvent(String str) {
            try {
                ISystemUIConditionListener iSystemUIConditionListener = EdgeLightingService.this.mConditionListener;
                if (iSystemUIConditionListener != null) {
                    iSystemUIConditionListener.sendClickEvent(str);
                }
            } catch (RemoteException e) {
                boolean z = EdgeLightingService.sConfigured;
                Slog.i("EdgeLightingService", "Remote exception ");
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$7 */
    public final class C13027 {
        public C13027() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$9 */
    public final class C13049 extends ContentObserver {
        public C13049(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            if (Settings.System.getUriFor("colortheme_app_icon").equals(uri)) {
                EdgeLightingService edgeLightingService = EdgeLightingService.this;
                edgeLightingService.mIsColorThemeEnabled = Settings.System.getIntForUser(edgeLightingService.getContentResolver(), "colortheme_app_icon", 0, -2) == 1;
            }
            if (Settings.System.getUriFor("show_notification_app_icon").equals(uri)) {
                EdgeLightingService edgeLightingService2 = EdgeLightingService.this;
                edgeLightingService2.mShouldShowAppIcon = Settings.System.getIntForUser(edgeLightingService2.getContentResolver(), "show_notification_app_icon", 0, -2) == 1;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MainHandler extends Handler {
        public /* synthetic */ MainHandler(EdgeLightingService edgeLightingService, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void dispatchMessage(Message message) {
            if (message.what != 1) {
                super.dispatchMessage(message);
                return;
            }
            EdgeLightingService edgeLightingService = EdgeLightingService.this;
            String str = (String) message.obj;
            boolean z = EdgeLightingService.sConfigured;
            edgeLightingService.getClass();
            Slog.i("EdgeLightingService", "stopService by " + str);
            EdgeLightingScheduler edgeLightingScheduler = edgeLightingService.mScheduler;
            if (edgeLightingScheduler != null) {
                edgeLightingScheduler.notifyEdgeLightingPackageList(true);
            }
            edgeLightingService.setProcessForeground(false);
            edgeLightingService.stopForeground(true);
            edgeLightingService.stopSelf();
        }

        private MainHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StatusbarStateReceiver extends BroadcastReceiver {
        public /* synthetic */ StatusbarStateReceiver(EdgeLightingService edgeLightingService, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            NotificationLightingScheduler notificationLightingScheduler;
            String action = intent.getAction();
            boolean equals = "com.samsung.systemui.statusbar.ANIMATING".equals(action);
            boolean equals2 = "com.samsung.systemui.statusbar.EXPANDED".equals(action);
            if (equals || equals2) {
                boolean z = EdgeLightingService.sConfigured;
                Slog.d("EdgeLightingService", "ACTION_STATUS_OPEN");
                EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                if (edgeLightingScheduler == null || (notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler) == null) {
                    return;
                }
                notificationLightingScheduler.flushNotiNow();
            }
        }

        private StatusbarStateReceiver() {
        }
    }

    static {
        Debug.semIsProductDev();
        sConfigured = false;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.edgelighting.EdgeLightingService$2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.edgelighting.EdgeLightingService$8] */
    public EdgeLightingService() {
        MainHandler mainHandler = new MainHandler(this, 0);
        this.mHandler = mainHandler;
        this.mConditionListener = null;
        this.mFoldStateListener = null;
        this.mKillBot = new RunnableC12961();
        this.mEdgeLightingObserver = new EdgeLightingSettingsObserver.EdgeLightingObserver() { // from class: com.android.systemui.edgelighting.EdgeLightingService.2
            @Override // com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.EdgeLightingObserver
            public final Handler getHandler() {
                return EdgeLightingService.this.mHandler;
            }

            @Override // com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.EdgeLightingObserver
            public final void onChange() {
                EdgeLightingService edgeLightingService = EdgeLightingService.this;
                boolean isEdgeLightingEnabled = EdgeLightingSettingUtils.isEdgeLightingEnabled(edgeLightingService.getContentResolver());
                boolean z = EdgeLightingService.sConfigured;
                Slog.i("EdgeLightingService", "EdgeLightingObserver: !!!! enable " + isEdgeLightingEnabled);
                if (!isEdgeLightingEnabled) {
                    edgeLightingService.setProcessForeground(false);
                    edgeLightingService.stopForeground(true);
                    edgeLightingService.stopSelf();
                }
                ContextStatusLoggingManager.getInstance().updateStatusLoggingItem(edgeLightingService);
            }
        };
        this.mOnEdgeLightingCallback = new C12983();
        this.mConnection = new ServiceConnection() { // from class: com.android.systemui.edgelighting.EdgeLightingService.8
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ISystemUIConditionListener proxy;
                EdgeLightingService edgeLightingService = EdgeLightingService.this;
                int i = ISystemUIConditionListener.Stub.$r8$clinit;
                if (iBinder == null) {
                    proxy = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof ISystemUIConditionListener)) ? new ISystemUIConditionListener.Stub.Proxy(iBinder) : (ISystemUIConditionListener) queryLocalInterface;
                }
                edgeLightingService.mConditionListener = proxy;
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                boolean z = EdgeLightingService.sConfigured;
                Slog.i("EdgeLightingService", " onServiceDisconnected " + componentName.flattenToShortString());
                EdgeLightingService.this.mConditionListener = null;
            }
        };
        this.mDBObserver = new C13049(mainHandler);
    }

    public static String checkEdgeLightingAvailable() {
        int i = Utils.$r8$clinit;
        int semGetMyUserId = UserHandle.semGetMyUserId();
        Slog.i("Utils", "isCurrentUser current = " + semGetMyUserId + ", ownerId = 0");
        return !(semGetMyUserId == 0) ? "not Owner" : !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION") ? "not Support" : "";
    }

    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.getInstance(getApplicationContext());
        edgeLightingSettingManager.getClass();
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m("Enable pkg ( ");
        boolean z = edgeLightingSettingManager.mAllApplication;
        HashMap hashMap = edgeLightingSettingManager.mEnableSet;
        if (z) {
            m18m.append("ALL");
        } else {
            m18m.append(hashMap.size());
        }
        m18m.append(" )  : ");
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            m18m.append((String) ((Map.Entry) it.next()).getKey());
            m18m.append(", ");
        }
        printWriter.println(m18m);
        super.dump(fileDescriptor, printWriter, strArr);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        Slog.d("EdgeLightingService", "onCreate");
        this.mShouldKillMyself = true;
        String checkEdgeLightingAvailable = checkEdgeLightingAvailable();
        if (!"".equals(checkEdgeLightingAvailable)) {
            Slog.e("EdgeLightingService", "OnCreate : edgelighting is not availabe now : ".concat(checkEdgeLightingAvailable));
            this.mKillBot.run();
            return;
        }
        Slog.d("EdgeLightingService", "connectToSystemUI");
        if (this.mConditionListener == null) {
            Intent intent = new Intent(this, (Class<?>) SystemUIConditionListenerService.class);
            intent.setAction(ISystemUIConditionListener.class.getName());
            bindService(intent, this.mConnection, 1);
        }
        if (!sConfigured) {
            EdgeLightingAnalytics.initEdgeLightingAnalyticsStates(getApplication());
            sConfigured = true;
        }
        SemEdgeManager semEdgeManager = (SemEdgeManager) getSystemService("edge");
        this.mEdgeManager = semEdgeManager;
        if (semEdgeManager == null) {
            Slog.e("EdgeLightingService", "OnCreate : mEdgeManager is null.");
        }
        this.mPowerManager = (PowerManager) getSystemService("power");
        setProcessForeground(true);
        this.mAppIconCache = new AppIconCache(this);
        this.mHandler.removeCallbacks(this.mKillBot);
        this.mHandler.postDelayed(this.mKillBot, 1000L);
        this.mDevicePolicyManager = (DevicePolicyManager) getSystemService("device_policy");
    }

    @Override // android.app.Service
    public final void onDestroy() {
        EdgeLightingScheduler edgeLightingScheduler = this.mScheduler;
        if (edgeLightingScheduler != null) {
            EdgeLightingSettingsObserver.getInstance().unregisterContentObserver(getContentResolver(), Settings.System.class, edgeLightingScheduler.mEdgeLightingObserver);
            TurnOverEdgeLighting turnOverEdgeLighting = edgeLightingScheduler.mTurnOverEdgeLighting;
            if (turnOverEdgeLighting != null) {
                CallStateObserver callStateObserver = turnOverEdgeLighting.mCallStateObserver;
                if (callStateObserver != null) {
                    callStateObserver.mTelephonyManager.listen(callStateObserver.mPhoneStateListener, 0);
                    turnOverEdgeLighting.mCallStateObserver.mStateListener = null;
                    turnOverEdgeLighting.mCallStateObserver = null;
                }
                turnOverEdgeLighting.mUpsideDownChecker.cancel();
            }
        }
        setProcessForeground(false);
        StatusbarStateReceiver statusbarStateReceiver = this.mStatusBarReceiver;
        if (statusbarStateReceiver != null) {
            unregisterReceiver(statusbarStateReceiver);
            this.mStatusBarReceiver = null;
        }
        if (this.mFoldStateListener != null) {
            SemWindowManager.getInstance().unregisterFoldStateListener(this.mFoldStateListener);
            this.mFoldStateListener = null;
        }
        if (this.mCoverStateListener != null) {
            EdgeLightingCoverManager edgeLightingCoverManager = EdgeLightingCoverManager.getInstance();
            C13027 c13027 = this.mCoverStateListener;
            if (edgeLightingCoverManager.mSCoverStateListener != null) {
                ArrayList arrayList = edgeLightingCoverManager.mCoverStateListeners;
                arrayList.remove(c13027);
                if (arrayList.size() == 0) {
                    try {
                        edgeLightingCoverManager.mSCoverManager.unregisterListener(edgeLightingCoverManager.mSCoverStateListener);
                    } catch (SsdkUnsupportedException e) {
                        e.printStackTrace();
                    }
                    edgeLightingCoverManager.mSCoverManager = null;
                    edgeLightingCoverManager.mSCoverStateListener = null;
                    edgeLightingCoverManager.mCoverType = 2;
                }
            }
            this.mCoverStateListener = null;
        }
        EdgeLightingSettingsObserver.getInstance().unregisterContentObserver(getContentResolver(), Settings.System.class, this.mEdgeLightingObserver);
        getContentResolver().unregisterContentObserver(this.mDBObserver);
        SemEdgeManager semEdgeManager = this.mEdgeManager;
        if (semEdgeManager != null) {
            semEdgeManager.unbindEdgeLightingService(this.mOnEdgeLightingCallback);
        } else {
            Slog.e("EdgeLightingService", "onDestroy : mEdgeManager = " + this.mEdgeManager);
        }
        EdgeLightingDispatcher edgeLightingDispatcher = this.mDispatcher;
        if (edgeLightingDispatcher != null) {
            edgeLightingDispatcher.unRegisterEdgeWindowCallback();
            EdgeLightingDispatcher edgeLightingDispatcher2 = this.mDispatcher;
            if (edgeLightingDispatcher2.mSettingObserver != null) {
                edgeLightingDispatcher2.mContext.getContentResolver().unregisterContentObserver(edgeLightingDispatcher2.mSettingObserver);
                edgeLightingDispatcher2.mSettingObserver = null;
            }
            if (edgeLightingDispatcher2.mDialog != null) {
                Slog.i("EdgeLightingDispatcher", " mDialog showing : " + edgeLightingDispatcher2.mDialog.isShowing());
                edgeLightingDispatcher2.mDialog.stopEdgeEffect();
            } else if (edgeLightingDispatcher2.mEffectServiceConrtroller != null) {
                Slog.i("EdgeLightingDispatcher", " mEffectServiceConrtroller showing : " + edgeLightingDispatcher2.mEffectServiceConrtroller.mStarting);
                edgeLightingDispatcher2.mEffectServiceConrtroller.dispatchStop();
            }
            this.mDispatcher = null;
        }
        System.gc();
        this.mIsStarted = false;
        this.mCondition = 0;
        if (this.mConditionListener != null) {
            unbindService(this.mConnection);
            this.mConditionListener = null;
        }
        super.onDestroy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v50, types: [com.android.systemui.edgelighting.EdgeLightingService$6] */
    /* JADX WARN: Type inference failed for: r4v16, types: [com.android.systemui.edgelighting.device.EdgeLightingCoverManager$1] */
    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        boolean z;
        boolean z2;
        HashMap hashMap;
        Uri uriFor;
        String checkEdgeLightingAvailable = checkEdgeLightingAvailable();
        if (!"".equals(checkEdgeLightingAvailable)) {
            Slog.e("EdgeLightingService", "onStartCommand : edgelighting is not availabe now : ".concat(checkEdgeLightingAvailable));
            this.mKillBot.run();
            return 2;
        }
        if (this.mEdgeManager == null) {
            this.mEdgeManager = (SemEdgeManager) getSystemService("edge");
            Slog.e("EdgeLightingService", "onStartCommand : mEdgeManager = " + this.mEdgeManager);
        }
        if (SemEmergencyManager.isEmergencyMode(this)) {
            stopEdgeLightingService();
            return 2;
        }
        if (!EdgeLightingSettingUtils.isEdgeLightingEnabled(getContentResolver())) {
            stopEdgeLightingService();
            return 2;
        }
        setProcessForeground(true);
        int i3 = 0;
        if (this.mScheduler == null) {
            EdgeLightingScheduler edgeLightingScheduler = new EdgeLightingScheduler(this.mEdgeManager);
            this.mScheduler = edgeLightingScheduler;
            edgeLightingScheduler.mContext = this;
            if (edgeLightingScheduler.mScreenStatusChecker == null) {
                edgeLightingScheduler.mScreenStatusChecker = new EdgeLightingScreenStatus(this);
            }
            if (edgeLightingScheduler.mApplicationLightingScheduler == null) {
                ApplicationLightingScheduler applicationLightingScheduler = new ApplicationLightingScheduler();
                edgeLightingScheduler.mApplicationLightingScheduler = applicationLightingScheduler;
                EdgeLightingScheduler.C13553 c13553 = edgeLightingScheduler.new C13553();
                synchronized (applicationLightingScheduler.mLinkedInfo) {
                    applicationLightingScheduler.mListener = c13553;
                }
            }
            if (edgeLightingScheduler.mNotificationLightingScheduler == null) {
                NotificationLightingScheduler notificationLightingScheduler = new NotificationLightingScheduler();
                edgeLightingScheduler.mNotificationLightingScheduler = notificationLightingScheduler;
                notificationLightingScheduler.mListener = edgeLightingScheduler.new C13564();
            }
            PowerManager powerManager = (PowerManager) getSystemService("power");
            edgeLightingScheduler.mPm = powerManager;
            edgeLightingScheduler.mWakeLock = powerManager.newWakeLock(1, "EdgeLighting:edge");
            if (edgeLightingScheduler.mTurnOverEdgeLighting == null) {
                edgeLightingScheduler.mTurnOverEdgeLighting = new TurnOverEdgeLighting(this);
                EdgeLightingScheduler.C13575 c13575 = edgeLightingScheduler.new C13575();
                EdgeLightingScheduler.C13586 c13586 = edgeLightingScheduler.new C13586();
                TurnOverEdgeLighting turnOverEdgeLighting = edgeLightingScheduler.mTurnOverEdgeLighting;
                turnOverEdgeLighting.mListener = c13575;
                turnOverEdgeLighting.mRequestor = c13586;
                turnOverEdgeLighting.setEnable();
            }
            EdgeLightingScheduler edgeLightingScheduler2 = this.mScheduler;
            C12994 c12994 = new C12994();
            edgeLightingScheduler2.mRequester = c12994;
            edgeLightingScheduler2.mIsScreenOnReceived = c12994.isScreenOn();
            EdgeLightingScreenStatus edgeLightingScreenStatus = edgeLightingScheduler2.mScreenStatusChecker;
            boolean isScreenOn = edgeLightingScheduler2.mRequester.isScreenOn();
            edgeLightingScreenStatus.getClass();
            if (isScreenOn) {
                Slog.d("EdgeLightingScreenStatus", UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                System.currentTimeMillis();
            }
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (this.mFoldStateListener == null) {
                this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.edgelighting.EdgeLightingService.6
                    public final void onFoldStateChanged(boolean z3) {
                        NotificationLightingScheduler notificationLightingScheduler2;
                        EdgeLightingScheduler edgeLightingScheduler3 = EdgeLightingService.this.mScheduler;
                        if (edgeLightingScheduler3 == null || (notificationLightingScheduler2 = edgeLightingScheduler3.mNotificationLightingScheduler) == null) {
                            return;
                        }
                        notificationLightingScheduler2.flushNotiNow();
                    }

                    public final void onTableModeChanged(boolean z3) {
                    }
                };
                SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
            }
            if (this.mStatusBarReceiver == null) {
                this.mStatusBarReceiver = new StatusbarStateReceiver(this, i3);
                IntentFilter intentFilter = new IntentFilter("com.samsung.systemui.statusbar.ANIMATING");
                intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
                registerReceiver(this.mStatusBarReceiver, intentFilter);
            }
            if (this.mCoverStateListener == null) {
                this.mCoverStateListener = new C13027();
                final EdgeLightingCoverManager edgeLightingCoverManager = EdgeLightingCoverManager.getInstance();
                C13027 c13027 = this.mCoverStateListener;
                EdgeLightingCoverManager.C13081 c13081 = edgeLightingCoverManager.mSCoverStateListener;
                ArrayList arrayList = edgeLightingCoverManager.mCoverStateListeners;
                if (c13081 == null) {
                    edgeLightingCoverManager.mSCoverManager = new ScoverManager(this);
                    ?? r4 = new ScoverManager.CoverStateListener() { // from class: com.android.systemui.edgelighting.device.EdgeLightingCoverManager.1
                        @Override // com.samsung.android.sdk.cover.ScoverManager.CoverStateListener
                        public final void onCoverAttachStateChanged(boolean z3) {
                            boolean z4 = EdgeLightingCoverManager.DEBUG;
                            if (z4) {
                                Slog.d("EdgeLightingCoverManager", "onCoverAttachStateChanged : " + z3);
                            }
                            EdgeLightingCoverManager edgeLightingCoverManager2 = EdgeLightingCoverManager.this;
                            ScoverManager scoverManager = edgeLightingCoverManager2.mSCoverManager;
                            if (scoverManager == null) {
                                Slog.d("EdgeLightingCoverManager", "onCoverAttachStateChanged : coverManager is null");
                                return;
                            }
                            if (z3) {
                                ScoverState coverState = scoverManager.getCoverState();
                                if (coverState != null) {
                                    edgeLightingCoverManager2.mCoverType = coverState.type;
                                    if (z4) {
                                        Slog.d("EdgeLightingCoverManager", "updateCoverType : " + edgeLightingCoverManager2.mCoverType);
                                    }
                                }
                            } else {
                                edgeLightingCoverManager2.mCoverType = 2;
                            }
                            edgeLightingCoverManager2.getClass();
                            Iterator it = edgeLightingCoverManager2.mCoverStateListeners.iterator();
                            while (it.hasNext()) {
                                ((EdgeLightingService.C13027) it.next()).getClass();
                            }
                        }

                        @Override // com.samsung.android.sdk.cover.ScoverManager.CoverStateListener
                        public final void onCoverSwitchStateChanged(boolean z3) {
                            if (EdgeLightingCoverManager.DEBUG) {
                                Slog.d("EdgeLightingCoverManager", "onCoverSwitchStateChanged : " + z3);
                            }
                            EdgeLightingCoverManager edgeLightingCoverManager2 = EdgeLightingCoverManager.this;
                            edgeLightingCoverManager2.mSwitchState = z3;
                            Iterator it = edgeLightingCoverManager2.mCoverStateListeners.iterator();
                            while (it.hasNext()) {
                                EdgeLightingScheduler edgeLightingScheduler3 = EdgeLightingService.this.mScheduler;
                                if (edgeLightingScheduler3 != null) {
                                    if (z3) {
                                        edgeLightingScheduler3.notifyScreenOn();
                                    } else {
                                        edgeLightingScheduler3.notifyScreenOff();
                                    }
                                }
                            }
                        }
                    };
                    edgeLightingCoverManager.mSCoverStateListener = r4;
                    try {
                        edgeLightingCoverManager.mSCoverManager.registerListener(r4);
                    } catch (SsdkUnsupportedException e) {
                        e.printStackTrace();
                    }
                    arrayList.add(c13027);
                    ScoverState coverState = edgeLightingCoverManager.mSCoverManager.getCoverState();
                    if (coverState != null) {
                        edgeLightingCoverManager.mSwitchState = coverState.switchState;
                        edgeLightingCoverManager.mCoverType = coverState.type;
                    }
                } else if (!arrayList.contains(c13027)) {
                    arrayList.add(c13027);
                }
            }
            EdgeLightingSettingsObserver edgeLightingSettingsObserver = EdgeLightingSettingsObserver.getInstance();
            ContentResolver contentResolver = getContentResolver();
            C12972 c12972 = this.mEdgeLightingObserver;
            edgeLightingSettingsObserver.getClass();
            if (Settings.System.class == Settings.System.class) {
                hashMap = edgeLightingSettingsObserver.mSystemObservers;
                uriFor = Settings.System.getUriFor("edge_lighting");
            } else if (Settings.System.class == Settings.Global.class) {
                hashMap = edgeLightingSettingsObserver.mGlobalObservers;
                uriFor = Settings.Global.getUriFor("edge_lighting");
            } else {
                Slog.e("EdgeLightingSettingsObserver", "registerContentObserver : wrong table");
                getContentResolver().registerContentObserver(Settings.System.getUriFor("colortheme_app_icon"), false, this.mDBObserver);
                getContentResolver().registerContentObserver(Settings.System.getUriFor("show_notification_app_icon"), false, this.mDBObserver);
                this.mDBObserver.onChange(true, Settings.System.getUriFor("colortheme_app_icon"));
                this.mDBObserver.onChange(true, Settings.System.getUriFor("show_notification_app_icon"));
            }
            EdgeLightingSettingsObserver.ContentObserverWrapper contentObserverWrapper = (EdgeLightingSettingsObserver.ContentObserverWrapper) hashMap.get("edge_lighting");
            if (contentObserverWrapper == null) {
                EdgeLightingSettingsObserver.ContentObserverWrapper contentObserverWrapper2 = new EdgeLightingSettingsObserver.ContentObserverWrapper(null);
                hashMap.put("edge_lighting", contentObserverWrapper2);
                contentObserverWrapper2.mObservers.add(c12972);
                contentResolver.registerContentObserver(uriFor, false, contentObserverWrapper2);
            } else if (!contentObserverWrapper.mObservers.contains(c12972)) {
                contentObserverWrapper.mObservers.add(c12972);
            }
            getContentResolver().registerContentObserver(Settings.System.getUriFor("colortheme_app_icon"), false, this.mDBObserver);
            getContentResolver().registerContentObserver(Settings.System.getUriFor("show_notification_app_icon"), false, this.mDBObserver);
            this.mDBObserver.onChange(true, Settings.System.getUriFor("colortheme_app_icon"));
            this.mDBObserver.onChange(true, Settings.System.getUriFor("show_notification_app_icon"));
        }
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        }
        if (this.mEdgeManager != null) {
            int intForUser = Settings.System.getIntForUser(getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2);
            int i4 = intForUser == 1 ? 1 : intForUser == 2 ? 2 : 3;
            if (this.mCondition != i4) {
                this.mCondition = i4;
                this.mEdgeManager.bindEdgeLightingService(this.mOnEdgeLightingCallback, i4);
            }
        }
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras == null) {
                stopEdgeLightingService();
                return 2;
            }
            if (extras.getBoolean("forUpdatePolicy", false) && !this.mIsStarted) {
                Slog.d("EdgeLightingService", "start service for policy update");
                EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.getInstance(this);
                EdgeLightingPolicyManager edgeLightingPolicyManager = EdgeLightingPolicyManager.getInstance(this, false);
                edgeLightingSettingManager.getClass();
                SharedPreferences sharedPreferences = getSharedPreferences("edge_lighting_settings", 0);
                Set<String> stringSet = sharedPreferences.getStringSet("silent_add_list", new HashSet());
                int size = stringSet.size();
                HashMap hashMap2 = edgeLightingSettingManager.mEnableSet;
                if (size > 0) {
                    for (String str : stringSet) {
                        hashMap2.put(str, new EdgeLightingSettingItem(str, -11761985));
                    }
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.remove("silent_add_list");
                    edit.apply();
                    z2 = true;
                } else {
                    z2 = false;
                }
                Set<String> stringSet2 = sharedPreferences.getStringSet("silent_remove_list", new HashSet());
                boolean z3 = z2;
                if (stringSet2.size() > 0) {
                    Iterator<String> it = stringSet2.iterator();
                    while (it.hasNext()) {
                        hashMap2.remove(it.next());
                    }
                    SharedPreferences.Editor edit2 = sharedPreferences.edit();
                    edit2.remove("silent_remove_list");
                    edit2.apply();
                    z3 = true;
                }
                if (z3) {
                    SharedPreferences.Editor edit3 = sharedPreferences.edit();
                    edit3.putInt("version", 1);
                    edit3.putBoolean("all_application", false);
                    edit3.putStringSet("enable_list", hashMap2.keySet());
                    edit3.apply();
                }
                edgeLightingSettingManager.removeBlockListInEnabledEdgeLightingList(this, (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(2));
                edgeLightingPolicyManager.updateEdgeLightingPolicy(this, edgeLightingSettingManager.mAllApplication);
                stopEdgeLightingService();
                return 2;
            }
            String string = extras.getString("packagename");
            SemEdgeLightingInfo parcelable = extras.getParcelable("info");
            int i5 = extras.getInt("reason");
            Slog.d("EdgeLightingService", "onStartCommand pkg=" + string + ",info=" + parcelable + ",reason=" + i5);
            if (string != null && parcelable != null) {
                this.mHandler.post(new EdgeLightingService$$ExternalSyntheticLambda0(this, string, parcelable, i5, 0));
                this.mShouldKillMyself = false;
            }
        }
        this.mIsStarted = true;
        return 1;
    }

    public final void setProcessForeground(boolean z) {
        try {
            ActivityManager.getService().setProcessImportant(this.mForegroundToken, Process.myPid(), z, "EdgeLightingService");
        } catch (Exception e) {
            Slog.e("EdgeLightingService", "cant set to foreground" + e.toString());
            e.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:178:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0245  */
    /* JADX WARN: Type inference failed for: r0v103 */
    /* JADX WARN: Type inference failed for: r0v104 */
    /* JADX WARN: Type inference failed for: r0v107 */
    /* JADX WARN: Type inference failed for: r0v108 */
    /* JADX WARN: Type inference failed for: r0v112 */
    /* JADX WARN: Type inference failed for: r0v114 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v44 */
    /* JADX WARN: Type inference failed for: r0v45 */
    /* JADX WARN: Type inference failed for: r0v53 */
    /* JADX WARN: Type inference failed for: r0v62 */
    /* JADX WARN: Type inference failed for: r0v63 */
    /* JADX WARN: Type inference failed for: r0v66 */
    /* JADX WARN: Type inference failed for: r0v67 */
    /* JADX WARN: Type inference failed for: r0v70 */
    /* JADX WARN: Type inference failed for: r0v71 */
    /* JADX WARN: Type inference failed for: r0v73 */
    /* JADX WARN: Type inference failed for: r0v74 */
    /* JADX WARN: Type inference failed for: r0v79 */
    /* JADX WARN: Type inference failed for: r0v81 */
    /* JADX WARN: Type inference failed for: r0v83 */
    /* JADX WARN: Type inference failed for: r0v85 */
    /* JADX WARN: Type inference failed for: r0v87 */
    /* JADX WARN: Type inference failed for: r2v51 */
    /* JADX WARN: Type inference failed for: r2v52 */
    /* JADX WARN: Type inference failed for: r2v55 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
        Drawable drawable;
        Drawable drawable2;
        int intForUser;
        PolicyInfo policyInfo;
        int deviceWallPaperColorIndex;
        int i2;
        Integer valueOf;
        if (str == null) {
            MainHandler mainHandler = this.mHandler;
            mainHandler.sendMessage(mainHandler.obtainMessage(1, "packageName null"));
            return;
        }
        if (semEdgeLightingInfo.getExtra() == null) {
            MainHandler mainHandler2 = this.mHandler;
            mainHandler2.sendMessage(mainHandler2.obtainMessage(1, "reason is not notification"));
            return;
        }
        SemStatusBarManager semStatusBarManager = (SemStatusBarManager) getSystemService(SemStatusBarManager.class);
        if (i != 8 && (semStatusBarManager.getDisableFlags() & 262144) != 0) {
            MainHandler mainHandler3 = this.mHandler;
            mainHandler3.sendMessage(mainHandler3.obtainMessage(1, "disable_alert"));
            return;
        }
        if (Utils.isLargeCoverFlipFolded()) {
            if ((Settings.Secure.getIntForUser(getContentResolver(), "cover_screen_show_notification", 1, -2) == 1) != true) {
                MainHandler mainHandler4 = this.mHandler;
                mainHandler4.sendMessage(mainHandler4.obtainMessage(1, "reason is turn off subscreen notification"));
                return;
            } else {
                if ((Settings.Secure.getIntForUser(getContentResolver(), "turn_on_cover_screen_for_notification", 1, -2) != 0) == false && !this.mPowerManager.isInteractive()) {
                    MainHandler mainHandler5 = this.mHandler;
                    mainHandler5.sendMessage(mainHandler5.obtainMessage(1, "reason is turn off \"Turn on screen for notifications\""));
                    return;
                }
            }
        }
        if (this.mConditionListener != null) {
            String string = semEdgeLightingInfo.getExtra().getString("noti_key");
            try {
                if (this.mConditionListener.isInterrupted(string)) {
                    if (((8 & SemEdgeLightingInfoUtils.getInt(semEdgeLightingInfo, "flag")) != 0) != false) {
                        MainHandler mainHandler6 = this.mHandler;
                        mainHandler6.sendMessage(mainHandler6.obtainMessage(1, "interrupted"));
                        return;
                    }
                }
                if (this.mConditionListener.isAlertingHeadsUp(string)) {
                    MainHandler mainHandler7 = this.mHandler;
                    mainHandler7.sendMessage(mainHandler7.obtainMessage(1, "isAlertingHeadsUp"));
                    return;
                } else if (!this.mConditionListener.isPanelsEnabled()) {
                    MainHandler mainHandler8 = this.mHandler;
                    mainHandler8.sendMessage(mainHandler8.obtainMessage(1, "isPanelsEnabled"));
                    return;
                }
            } catch (RemoteException unused) {
            }
        }
        CharSequence charSequence = semEdgeLightingInfo.getExtra().getCharSequence("channel_id");
        if ((str.equals("com.android.systemui") || str.equals("com.samsung.android.app.cocktailbarservice")) && charSequence != null && charSequence.equals("edge_lighting_chnnel_id")) {
            Slog.i("EdgeLightingService", "disable edge_lighting channel");
            return;
        }
        if (this.mPowerManager.isInteractive() && ((KeyguardManager) getSystemService("keyguard")).semIsKeyguardShowingAndNotOccluded() && !Utils.isLargeCoverFlipFolded()) {
            MainHandler mainHandler9 = this.mHandler;
            mainHandler9.sendMessage(mainHandler9.obtainMessage(1, "keyguard && screenOn"));
            return;
        }
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
        Drawable drawable3 = null;
        if ((keyguardManager != null && keyguardManager.isKeyguardLocked()) != false) {
            if (((this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, UserHandle.semGetMyUserId()) & 4) == 0) != true) {
                MainHandler mainHandler10 = this.mHandler;
                mainHandler10.sendMessage(mainHandler10.obtainMessage(1, "blockByDPM"));
                return;
            }
            if ((Settings.Secure.getIntForUser(getContentResolver(), "lock_screen_show_notifications", 0, -2) == 1) != true) {
                MainHandler mainHandler11 = this.mHandler;
                mainHandler11.sendMessage(mainHandler11.obtainMessage(1, "keygaurdNotiOff"));
                return;
            }
            if ((SemEdgeLightingInfoUtils.getInt(semEdgeLightingInfo, "noti_visiblity") == -1) == true) {
                MainHandler mainHandler12 = this.mHandler;
                mainHandler12.sendMessage(mainHandler12.obtainMessage(1, "secret && keyguard"));
                return;
            } else {
                Bundle extra = semEdgeLightingInfo.getExtra();
                if (((extra == null || (valueOf = Integer.valueOf(extra.getInt("package_visiblity"))) == null || valueOf.intValue() != -1) ? false : true) != false) {
                    MainHandler mainHandler13 = this.mHandler;
                    mainHandler13.sendMessage(mainHandler13.obtainMessage(1, "secret package && keyguard"));
                    return;
                }
            }
        }
        boolean z = SemEdgeLightingInfoUtils.DEBUG;
        Bundle extra2 = semEdgeLightingInfo.getExtra();
        if ((extra2 != null ? extra2.getBoolean("bubble", false) : false) && this.mPowerManager.isInteractive()) {
            MainHandler mainHandler14 = this.mHandler;
            mainHandler14.sendMessage(mainHandler14.obtainMessage(1, "bubble"));
            return;
        }
        Configuration configuration = getResources().getConfiguration();
        if (configuration != null && (i2 = configuration.FlipFont) > 0 && sFlipFont != i2) {
            Typeface.setFlipFonts();
            sFlipFont = configuration.FlipFont;
        }
        if (this.mScheduler == null) {
            return;
        }
        AppIconCache appIconCache = this.mAppIconCache;
        appIconCache.getClass();
        Bundle extra3 = semEdgeLightingInfo.getExtra();
        Context context = appIconCache.mContext;
        if (extra3 != null) {
            Parcelable parcelable = extra3.getParcelable(appIconCache.KEY_SMALL_ICON);
            if (parcelable instanceof Icon) {
                drawable = ((Icon) parcelable).loadDrawable(context);
                LruCache lruCache = appIconCache.mIconCache;
                if (drawable == null) {
                    lruCache.put(str, drawable);
                    drawable2 = drawable;
                } else {
                    Drawable drawable4 = (Drawable) lruCache.get(str);
                    if (drawable4 != null) {
                        drawable2 = drawable4;
                    } else {
                        try {
                            drawable3 = context.getPackageManager().getApplicationIcon(str);
                            lruCache.put(str, drawable3);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (drawable3 != null) {
                            lruCache.put(str, drawable3);
                        }
                        drawable2 = drawable3;
                    }
                }
                if (i != 0) {
                    boolean z2 = SemEdgeLightingInfoUtils.DEBUG;
                    int i3 = (semEdgeLightingInfo.getExtra() != null && semEdgeLightingInfo.getExtra().getParcelable(SemEdgeLightingInfoUtils.EXTRA_KEY_SMALL_ICON) != null) != false ? SemEdgeLightingInfoUtils.getInt(semEdgeLightingInfo, "notification_color") : 0;
                    int intForUser2 = Settings.System.getIntForUser(getContentResolver(), "edge_lighting_color_type", 1, -2);
                    if (intForUser2 == 0) {
                        deviceWallPaperColorIndex = EdgeLightingSettingUtils.getEdgeLightingStylePreDefineColor(getBaseContext(), EdgeLightingSettingUtils.getEdgeLightingBasicColorIndex(getContentResolver()), false);
                    } else if (intForUser2 == 1) {
                        deviceWallPaperColorIndex = EdgeLightingSettingUtils.loadAppCustomColor(getBaseContext(), str);
                        if (deviceWallPaperColorIndex == 0 && (deviceWallPaperColorIndex = EdgeLightingPolicyManager.getInstance(getApplicationContext(), false).getEdgeLightingColor(getBaseContext(), str)) == -11761985 && i3 != 0) {
                            Slog.i("EdgeLightingService", "Not exist color in white list.So using notification color  : " + Integer.toHexString(i3));
                            deviceWallPaperColorIndex = i3;
                        }
                    } else {
                        deviceWallPaperColorIndex = intForUser2 == 3 ? DeviceColorMonitor.getDeviceWallPaperColorIndex(getContentResolver()) : Settings.Global.getInt(getApplicationContext().getContentResolver(), "edgelighting_custom_color", -11761985);
                    }
                    semEdgeLightingInfo.setEffectColors(new int[]{deviceWallPaperColorIndex, i3});
                }
                HashMap hashMap = (HashMap) EdgeLightingPolicyManager.getInstance(getApplicationContext(), false).mPolicyInfoData.get(10);
                int i4 = (hashMap != null || (policyInfo = (PolicyInfo) hashMap.get(str)) == null) ? 0 : policyInfo.priority;
                EdgeLightingScheduler edgeLightingScheduler = this.mScheduler;
                edgeLightingScheduler.getClass();
                Slog.d("EdgeLightingScheduler", "startEdgeLighting: " + i + " " + str + " onGo=" + SemEdgeLightingInfoUtils.isOnGoing(semEdgeLightingInfo));
                LightingScheduleInfo lightingScheduleInfo = new LightingScheduleInfo(str, null, semEdgeLightingInfo, drawable2, i, i4);
                intForUser = Settings.System.getIntForUser(EdgeLightingService.this.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2);
                LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy = new LightingScheduleInfo.LightingLogicPolicy();
                lightingScheduleInfo.mLightingLogicPolicy = lightingLogicPolicy;
                if (intForUser != 0) {
                    lightingLogicPolicy.isNeedToKeepWhenLcdOff = true;
                    lightingLogicPolicy.isNeedLightOnWhenTurnOveredLcdOff = true;
                    lightingLogicPolicy.isNeedLightOnWhenTurnOveredLcdOn = true;
                    lightingLogicPolicy.isNeedLightOnWhenTurnRightedLcdOff = true;
                } else if (intForUser == 1) {
                    lightingLogicPolicy.isNeedLightOnWhenTurnOveredLcdOn = true;
                    lightingLogicPolicy.isNeedLightOnWhenTurnRightedLcdOn = true;
                } else if (intForUser == 2) {
                    lightingLogicPolicy.isNeedToKeepWhenLcdOff = true;
                    lightingLogicPolicy.isNeedLightOnWhenTurnOveredLcdOff = true;
                    lightingLogicPolicy.isNeedLightOnWhenTurnRightedLcdOff = true;
                }
                lightingScheduleInfo.setDuration(EdgeLightingSettingUtils.getEdgeLightingDuration(EdgeLightingSettingUtils.loadEdgeLightingDurationOptionType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext)));
                EdgeLightingScheduler.HandlerC13531 handlerC13531 = edgeLightingScheduler.mHandler;
                handlerC13531.sendMessage(Message.obtain(handlerC13531, 0, lightingScheduleInfo));
                ContextStatusLoggingManager.getInstance().updateStatusLoggingItem(this);
                if (this.mConditionListener == null) {
                    try {
                        this.mConditionListener.setInterruption(semEdgeLightingInfo.getExtra().getString("noti_key"));
                        return;
                    } catch (RemoteException unused2) {
                        return;
                    }
                }
                return;
            }
        }
        drawable = null;
        LruCache lruCache2 = appIconCache.mIconCache;
        if (drawable == null) {
        }
        if (i != 0) {
        }
        HashMap hashMap2 = (HashMap) EdgeLightingPolicyManager.getInstance(getApplicationContext(), false).mPolicyInfoData.get(10);
        if (hashMap2 != null) {
        }
        EdgeLightingScheduler edgeLightingScheduler2 = this.mScheduler;
        edgeLightingScheduler2.getClass();
        Slog.d("EdgeLightingScheduler", "startEdgeLighting: " + i + " " + str + " onGo=" + SemEdgeLightingInfoUtils.isOnGoing(semEdgeLightingInfo));
        LightingScheduleInfo lightingScheduleInfo2 = new LightingScheduleInfo(str, null, semEdgeLightingInfo, drawable2, i, i4);
        intForUser = Settings.System.getIntForUser(EdgeLightingService.this.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2);
        LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy2 = new LightingScheduleInfo.LightingLogicPolicy();
        lightingScheduleInfo2.mLightingLogicPolicy = lightingLogicPolicy2;
        if (intForUser != 0) {
        }
        lightingScheduleInfo2.setDuration(EdgeLightingSettingUtils.getEdgeLightingDuration(EdgeLightingSettingUtils.loadEdgeLightingDurationOptionType(edgeLightingScheduler2.mTurnOverEdgeLighting.mContext)));
        EdgeLightingScheduler.HandlerC13531 handlerC135312 = edgeLightingScheduler2.mHandler;
        handlerC135312.sendMessage(Message.obtain(handlerC135312, 0, lightingScheduleInfo2));
        ContextStatusLoggingManager.getInstance().updateStatusLoggingItem(this);
        if (this.mConditionListener == null) {
        }
    }

    public final void stopEdgeLightingService() {
        setProcessForeground(false);
        stopForeground(true);
        stopSelf();
    }
}
