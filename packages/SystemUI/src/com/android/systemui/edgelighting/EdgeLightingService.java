package com.android.systemui.edgelighting;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
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
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.EdgeLightingService;
import com.android.systemui.edgelighting.data.EdgeLightingSettingItem;
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
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass3;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass4;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass5;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass6;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScreenStatus;
import com.android.systemui.edgelighting.scheduler.LightingScheduleInfo;
import com.android.systemui.edgelighting.scheduler.NotificationLightingScheduler;
import com.android.systemui.edgelighting.turnover.CallStateObserver;
import com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting;
import com.android.systemui.edgelighting.utils.AppIconCache;
import com.android.systemui.edgelighting.utils.DrawableUtils;
import com.android.systemui.edgelighting.utils.EdgeLightingAnalytics;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.edge.OnEdgeLightingCallback;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.edge.SemEdgeManager;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
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

public class EdgeLightingService extends Service {
    public static boolean sConfigured;
    public static int sFlipFont;
    public AppIconCache mAppIconCache;
    public AudioManager mAudioManager;
    public ISystemUIConditionListener mConditionListener;
    public final AnonymousClass8 mConnection;
    public final AnonymousClass9 mDBObserver;
    public DevicePolicyManager mDevicePolicyManager;
    public EdgeLightingDispatcher mDispatcher;
    public final AnonymousClass2 mEdgeLightingObserver;
    public SemEdgeManager mEdgeManager;
    public AnonymousClass6 mFoldStateListener;
    public final MainHandler mHandler;
    public boolean mIsColorThemeEnabled;
    public boolean mIsUsingAppIcon;
    public final AnonymousClass1 mKillBot;
    public final AnonymousClass3 mOnEdgeLightingCallback;
    public PowerManager mPowerManager;
    public EdgeLightingScheduler mScheduler;
    public boolean mShouldKillMyself;
    public StatusbarStateReceiver mStatusBarReceiver;
    public final IBinder mForegroundToken = new Binder();
    public AnonymousClass7 mCoverStateListener = null;
    public boolean mIsStarted = false;
    public int mCondition = 0;

    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            EdgeLightingService edgeLightingService = EdgeLightingService.this;
            if (edgeLightingService.mShouldKillMyself) {
                edgeLightingService.stopEdgeLightingService();
            }
        }
    }

    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$3, reason: invalid class name */
    public final class AnonymousClass3 implements OnEdgeLightingCallback {
        public AnonymousClass3() {
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
                EdgeLightingScheduler.AnonymousClass1 anonymousClass1 = edgeLightingScheduler.mHandler;
                anonymousClass1.sendMessage(Message.obtain(anonymousClass1, 1, lightingScheduleInfo));
            }
        }
    }

    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$4, reason: invalid class name */
    public final class AnonymousClass4 {
        public AnonymousClass4() {
        }

        public final EdgeLightingDispatcher getUIController(boolean z) {
            final EdgeLightingService edgeLightingService = EdgeLightingService.this;
            if (edgeLightingService.mDispatcher == null) {
                Slog.i("EdgeLightingService", "createEdgeLightingDialog make dispatcher " + z);
                int intForUser = Settings.System.getIntForUser(edgeLightingService.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2);
                boolean z2 = ((intForUser == 1 ? 1 : intForUser == 2 ? 2 : 3) & 2) != 0;
                EdgeLightingDispatcher edgeLightingDispatcher = new EdgeLightingDispatcher(edgeLightingService.getBaseContext(), z2 ? 2227 : 2228, z);
                edgeLightingService.mDispatcher = edgeLightingDispatcher;
                edgeLightingDispatcher.registerEdgeWindowCallback(new IEdgeLightingWindowCallback() { // from class: com.android.systemui.edgelighting.EdgeLightingService.5
                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void doActionNotification() {
                        ArrayList arrayList;
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        edgeLightingScheduler.getClass();
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null) {
                            Bundle extra = notificationLightingScheduler.mCurrentLightingScheduleInfo.mLightingInfo.getExtra();
                            if (extra == null || (arrayList = extra.getParcelableArrayList("noti_actions")) == null) {
                                arrayList = null;
                            }
                            String string = edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getString(R.string.restrict_mark_as_read);
                            if (arrayList != null) {
                                Iterator it = arrayList.iterator();
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
                        edgeLightingService2.mScheduler.releaseWakeLock();
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
                        if (Settings.Global.getInt(edgeLightingService2.getContentResolver(), SettingsHelper.INDEX_SMART_VIEW_SHOW_NOTIFICATION_ON, 1) == 0) {
                            Slog.d("EdgeLightingService", "HideNotificationShadeInMirror updateInternalPresentationWindowFlag()");
                            EdgeLightingDispatcher edgeLightingDispatcher2 = edgeLightingService2.mDispatcher;
                            if (edgeLightingDispatcher2 != null && edgeLightingDispatcher2.getWindow() != null) {
                                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) edgeLightingService2.mDispatcher.getWindow().getDecorView().getLayoutParams();
                                WindowManager windowManager = edgeLightingService2.mDispatcher.getWindow().getWindowManager();
                                if (layoutParams != null && windowManager != null) {
                                    layoutParams.setTitle("EdgeLightingService");
                                    layoutParams.semAddExtensionFlags(Integer.MIN_VALUE);
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
                        SemEdgeManager semEdgeManager;
                        String notificationTag;
                        int notificationID;
                        int userId;
                        String notificationKey;
                        EdgeLightingScheduler edgeLightingScheduler;
                        Bundle extra;
                        EdgeLightingScheduler edgeLightingScheduler2 = EdgeLightingService.this.mScheduler;
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler2.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null) {
                            LightingScheduleInfo lightingScheduleInfo = notificationLightingScheduler.mCurrentLightingScheduleInfo;
                            if (lightingScheduleInfo != null) {
                                String str = lightingScheduleInfo.mPackageName;
                                try {
                                    semEdgeManager = edgeLightingScheduler2.mEdgeManager;
                                    notificationTag = lightingScheduleInfo.getNotificationTag();
                                    notificationID = lightingScheduleInfo.getNotificationID();
                                    userId = lightingScheduleInfo.getUserId();
                                    notificationKey = lightingScheduleInfo.getNotificationKey();
                                    try {
                                        extra = lightingScheduleInfo.mLightingInfo.getExtra();
                                        edgeLightingScheduler = edgeLightingScheduler2;
                                    } catch (RuntimeException unused) {
                                        edgeLightingScheduler = edgeLightingScheduler2;
                                    }
                                } catch (RuntimeException unused2) {
                                }
                                try {
                                    semEdgeManager.cancelNotificationByGroupKey(str, notificationTag, notificationID, userId, notificationKey, extra != null ? extra.getString("group_key") : null);
                                    StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m(" swipe cancel pkg: ", str, " , tag :  ");
                                    m.append(lightingScheduleInfo.getNotificationTag());
                                    m.append(" id: ");
                                    m.append(lightingScheduleInfo.getNotificationID());
                                    m.append(" , userid : ");
                                    m.append(lightingScheduleInfo.getUserId());
                                    m.append(" , key : ");
                                    m.append(lightingScheduleInfo.getNotificationKey());
                                    m.append(" , groupKey : ");
                                    Bundle extra2 = lightingScheduleInfo.mLightingInfo.getExtra();
                                    m.append(extra2 != null ? extra2.getString("group_key") : null);
                                    Slog.i("EdgeLightingScheduler", m.toString());
                                    edgeLightingScheduler2 = edgeLightingScheduler;
                                } catch (RuntimeException unused3) {
                                    edgeLightingScheduler2 = edgeLightingScheduler;
                                    edgeLightingScheduler2.mEdgeManager.cancelNotification(lightingScheduleInfo.mPackageName, lightingScheduleInfo.getNotificationTag(), lightingScheduleInfo.getNotificationID(), lightingScheduleInfo.getUserId(), lightingScheduleInfo.getNotificationKey());
                                    StringBuilder m2 = ActivityResultRegistry$$ExternalSyntheticOutline0.m(" swipe cancel pkg: ", str, " , tag :  ");
                                    m2.append(lightingScheduleInfo.getNotificationTag());
                                    m2.append(" id: ");
                                    m2.append(lightingScheduleInfo.getNotificationID());
                                    m2.append(" , userid : ");
                                    m2.append(lightingScheduleInfo.getUserId());
                                    m2.append(" , key : ");
                                    m2.append(lightingScheduleInfo.getNotificationKey());
                                    Slog.i("EdgeLightingScheduler", m2.toString());
                                    edgeLightingScheduler2.mNotificationLightingScheduler.flushNotiNow();
                                }
                            }
                            edgeLightingScheduler2.mNotificationLightingScheduler.flushNotiNow();
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

        public final boolean isAppLockEnabled() {
            try {
                ISystemUIConditionListener iSystemUIConditionListener = EdgeLightingService.this.mConditionListener;
                if (iSystemUIConditionListener != null) {
                    return iSystemUIConditionListener.isAppLockEnabled();
                }
                return false;
            } catch (RemoteException e) {
                boolean z = EdgeLightingService.sConfigured;
                Slog.d("EdgeLightingService", "Remote exception in isAppLockEnabled " + e.getMessage());
                return false;
            }
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

        public final boolean isSensitiveStateActive() {
            try {
                ISystemUIConditionListener iSystemUIConditionListener = EdgeLightingService.this.mConditionListener;
                if (iSystemUIConditionListener != null) {
                    return iSystemUIConditionListener.isSensitiveStateActive();
                }
                return false;
            } catch (RemoteException e) {
                boolean z = EdgeLightingService.sConfigured;
                Slog.d("EdgeLightingService", "Remote exception in isSensitiveStateActive " + e.getMessage());
                return false;
            }
        }

        public final boolean isSupportAppLock() {
            try {
                ISystemUIConditionListener iSystemUIConditionListener = EdgeLightingService.this.mConditionListener;
                if (iSystemUIConditionListener != null) {
                    return iSystemUIConditionListener.isSupportAppLock();
                }
                return false;
            } catch (RemoteException e) {
                boolean z = EdgeLightingService.sConfigured;
                Slog.d("EdgeLightingService", "Remote exception in isSupportAppLock " + e.getMessage());
                return false;
            }
        }

        public final boolean isUIControllerExist() {
            return EdgeLightingService.this.mDispatcher != null;
        }

        public final void requestDozeStateSubScreen(boolean z) {
            EdgeLightingService edgeLightingService = EdgeLightingService.this;
            try {
                if (edgeLightingService.mConditionListener == null || isScreenOn()) {
                    return;
                }
                edgeLightingService.mConditionListener.requestDozeStateSubScreen(z);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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
            }
        }

        public final boolean shouldHideNotiForAppLockByPackage(String str) {
            try {
                ISystemUIConditionListener iSystemUIConditionListener = EdgeLightingService.this.mConditionListener;
                if (iSystemUIConditionListener != null) {
                    return iSystemUIConditionListener.shouldHideNotiForAppLockByPackage(str);
                }
                return false;
            } catch (RemoteException e) {
                boolean z = EdgeLightingService.sConfigured;
                Slog.d("EdgeLightingService", "Remote exception in shouldHideNotiForAppLock " + e.getMessage());
                return false;
            }
        }
    }

    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$7, reason: invalid class name */
    public final class AnonymousClass7 {
        public AnonymousClass7() {
        }
    }

    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$9, reason: invalid class name */
    public final class AnonymousClass9 extends ContentObserver {
        public AnonymousClass9(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            if (Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_THEME_APP_ICON).equals(uri)) {
                EdgeLightingService edgeLightingService = EdgeLightingService.this;
                edgeLightingService.mIsColorThemeEnabled = Settings.System.getIntForUser(edgeLightingService.getContentResolver(), SettingsHelper.INDEX_COLOR_THEME_APP_ICON, 0, -2) == 1;
            }
            if (Settings.System.getUriFor(SettingsHelper.NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON).equals(uri)) {
                EdgeLightingService edgeLightingService2 = EdgeLightingService.this;
                edgeLightingService2.mIsUsingAppIcon = Settings.System.getIntForUser(edgeLightingService2.getContentResolver(), SettingsHelper.NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON, 1, -2) == 1;
            }
        }
    }

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
            String str = (String) message.obj;
            boolean z = EdgeLightingService.sConfigured;
            EdgeLightingService edgeLightingService = EdgeLightingService.this;
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

    public EdgeLightingService() {
        MainHandler mainHandler = new MainHandler(this, 0);
        this.mHandler = mainHandler;
        this.mConditionListener = null;
        this.mFoldStateListener = null;
        this.mKillBot = new AnonymousClass1();
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
        this.mOnEdgeLightingCallback = new AnonymousClass3();
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
        this.mDBObserver = new AnonymousClass9(mainHandler);
    }

    public static String checkEdgeLightingAvailable() {
        int i = Utils.$r8$clinit;
        int semGetMyUserId = UserHandle.semGetMyUserId();
        Slog.i("Utils", "isCurrentUser current = " + semGetMyUserId + ", ownerId = 0");
        return semGetMyUserId == 0 ? !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION") ? "not Support" : "" : "not Owner";
    }

    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.getInstance(getApplicationContext());
        edgeLightingSettingManager.getClass();
        StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m("Enable pkg ( ");
        if (edgeLightingSettingManager.mAllApplication) {
            m.append("ALL");
        } else {
            m.append(edgeLightingSettingManager.mEnableSet.size());
        }
        m.append(" )  : ");
        Iterator it = edgeLightingSettingManager.mEnableSet.entrySet().iterator();
        while (it.hasNext()) {
            m.append((String) ((Map.Entry) it.next()).getKey());
            m.append(", ");
        }
        printWriter.println(m);
        super.dump(fileDescriptor, printWriter, strArr);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
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
            AnonymousClass7 anonymousClass7 = this.mCoverStateListener;
            if (edgeLightingCoverManager.mSCoverStateListener != null) {
                edgeLightingCoverManager.mCoverStateListeners.remove(anonymousClass7);
                if (edgeLightingCoverManager.mCoverStateListeners.size() == 0) {
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

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        boolean z;
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
                EdgeLightingScheduler.AnonymousClass3 anonymousClass3 = edgeLightingScheduler.new AnonymousClass3();
                synchronized (applicationLightingScheduler.mLinkedInfo) {
                    applicationLightingScheduler.mListener = anonymousClass3;
                }
            }
            if (edgeLightingScheduler.mNotificationLightingScheduler == null) {
                NotificationLightingScheduler notificationLightingScheduler = new NotificationLightingScheduler();
                edgeLightingScheduler.mNotificationLightingScheduler = notificationLightingScheduler;
                notificationLightingScheduler.mListener = edgeLightingScheduler.new AnonymousClass4();
            }
            PowerManager powerManager = (PowerManager) getSystemService("power");
            edgeLightingScheduler.mPm = powerManager;
            edgeLightingScheduler.mWakeLock = powerManager.newWakeLock(1, "EdgeLighting:edge");
            edgeLightingScheduler.mDrawWakeLock = edgeLightingScheduler.mPm.newWakeLock(128, "Window:EdgeLightingWindow");
            if (edgeLightingScheduler.mTurnOverEdgeLighting == null) {
                edgeLightingScheduler.mTurnOverEdgeLighting = new TurnOverEdgeLighting(this);
                EdgeLightingScheduler.AnonymousClass5 anonymousClass5 = edgeLightingScheduler.new AnonymousClass5();
                EdgeLightingScheduler.AnonymousClass6 anonymousClass6 = edgeLightingScheduler.new AnonymousClass6();
                TurnOverEdgeLighting turnOverEdgeLighting = edgeLightingScheduler.mTurnOverEdgeLighting;
                turnOverEdgeLighting.mListener = anonymousClass5;
                turnOverEdgeLighting.mRequestor = anonymousClass6;
                turnOverEdgeLighting.setEnable();
            }
            EdgeLightingScheduler edgeLightingScheduler2 = this.mScheduler;
            AnonymousClass4 anonymousClass4 = new AnonymousClass4();
            edgeLightingScheduler2.mRequester = anonymousClass4;
            edgeLightingScheduler2.mIsScreenOnReceived = anonymousClass4.isScreenOn();
            EdgeLightingScreenStatus edgeLightingScreenStatus = edgeLightingScheduler2.mScreenStatusChecker;
            boolean isScreenOn = edgeLightingScheduler2.mRequester.isScreenOn();
            edgeLightingScreenStatus.getClass();
            if (isScreenOn) {
                Slog.d("EdgeLightingScreenStatus", UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
                System.currentTimeMillis();
            }
            if (this.mFoldStateListener == null) {
                this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.edgelighting.EdgeLightingService.6
                    public final void onFoldStateChanged(boolean z2) {
                        NotificationLightingScheduler notificationLightingScheduler2;
                        EdgeLightingScheduler edgeLightingScheduler3 = EdgeLightingService.this.mScheduler;
                        if (edgeLightingScheduler3 == null || (notificationLightingScheduler2 = edgeLightingScheduler3.mNotificationLightingScheduler) == null) {
                            return;
                        }
                        notificationLightingScheduler2.flushNotiNow();
                    }

                    public final void onTableModeChanged(boolean z2) {
                    }
                };
                SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
            }
            if (this.mStatusBarReceiver == null) {
                this.mStatusBarReceiver = new StatusbarStateReceiver(this, i3);
                IntentFilter intentFilter = new IntentFilter("com.samsung.systemui.statusbar.ANIMATING");
                intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
                registerReceiver(this.mStatusBarReceiver, intentFilter, 2);
            }
            if (this.mCoverStateListener == null) {
                this.mCoverStateListener = new AnonymousClass7();
                final EdgeLightingCoverManager edgeLightingCoverManager = EdgeLightingCoverManager.getInstance();
                AnonymousClass7 anonymousClass7 = this.mCoverStateListener;
                if (edgeLightingCoverManager.mSCoverStateListener == null) {
                    edgeLightingCoverManager.mSCoverManager = new ScoverManager(this);
                    ?? r4 = new ScoverManager.CoverStateListener() { // from class: com.android.systemui.edgelighting.device.EdgeLightingCoverManager.1
                        @Override // com.samsung.android.sdk.cover.ScoverManager.CoverStateListener
                        public final void onCoverAttachStateChanged(boolean z2) {
                            boolean z3 = EdgeLightingCoverManager.DEBUG;
                            if (z3) {
                                Slog.d("EdgeLightingCoverManager", "onCoverAttachStateChanged : " + z2);
                            }
                            EdgeLightingCoverManager edgeLightingCoverManager2 = EdgeLightingCoverManager.this;
                            ScoverManager scoverManager = edgeLightingCoverManager2.mSCoverManager;
                            if (scoverManager == null) {
                                Slog.d("EdgeLightingCoverManager", "onCoverAttachStateChanged : coverManager is null");
                                return;
                            }
                            if (z2) {
                                ScoverState coverState = scoverManager.getCoverState();
                                if (coverState != null) {
                                    edgeLightingCoverManager2.mCoverType = coverState.type;
                                    if (z3) {
                                        Slog.d("EdgeLightingCoverManager", "updateCoverType : " + edgeLightingCoverManager2.mCoverType);
                                    }
                                }
                            } else {
                                edgeLightingCoverManager2.mCoverType = 2;
                            }
                            edgeLightingCoverManager2.getClass();
                            Iterator it = edgeLightingCoverManager2.mCoverStateListeners.iterator();
                            while (it.hasNext()) {
                                ((EdgeLightingService.AnonymousClass7) it.next()).getClass();
                            }
                        }

                        @Override // com.samsung.android.sdk.cover.ScoverManager.CoverStateListener
                        public final void onCoverSwitchStateChanged(boolean z2) {
                            if (EdgeLightingCoverManager.DEBUG) {
                                Slog.d("EdgeLightingCoverManager", "onCoverSwitchStateChanged : " + z2);
                            }
                            EdgeLightingCoverManager edgeLightingCoverManager2 = EdgeLightingCoverManager.this;
                            edgeLightingCoverManager2.mSwitchState = z2;
                            Iterator it = edgeLightingCoverManager2.mCoverStateListeners.iterator();
                            while (it.hasNext()) {
                                EdgeLightingScheduler edgeLightingScheduler3 = EdgeLightingService.this.mScheduler;
                                if (edgeLightingScheduler3 != null) {
                                    if (z2) {
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
                    edgeLightingCoverManager.mCoverStateListeners.add(anonymousClass7);
                    ScoverState coverState = edgeLightingCoverManager.mSCoverManager.getCoverState();
                    if (coverState != null) {
                        edgeLightingCoverManager.mSwitchState = coverState.switchState;
                        edgeLightingCoverManager.mCoverType = coverState.type;
                    }
                } else if (!edgeLightingCoverManager.mCoverStateListeners.contains(anonymousClass7)) {
                    edgeLightingCoverManager.mCoverStateListeners.add(anonymousClass7);
                }
            }
            EdgeLightingSettingsObserver edgeLightingSettingsObserver = EdgeLightingSettingsObserver.getInstance();
            ContentResolver contentResolver = getContentResolver();
            AnonymousClass2 anonymousClass2 = this.mEdgeLightingObserver;
            edgeLightingSettingsObserver.getClass();
            if (Settings.System.class == Settings.System.class) {
                hashMap = edgeLightingSettingsObserver.mSystemObservers;
                uriFor = Settings.System.getUriFor(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
            } else if (Settings.System.class == Settings.Global.class) {
                hashMap = edgeLightingSettingsObserver.mGlobalObservers;
                uriFor = Settings.Global.getUriFor(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
            } else {
                Slog.e("EdgeLightingSettingsObserver", "registerContentObserver : wrong table");
                getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_THEME_APP_ICON), false, this.mDBObserver);
                getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON), false, this.mDBObserver);
                this.mDBObserver.onChange(true, Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_THEME_APP_ICON));
                this.mDBObserver.onChange(true, Settings.System.getUriFor(SettingsHelper.NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON));
            }
            EdgeLightingSettingsObserver.ContentObserverWrapper contentObserverWrapper = (EdgeLightingSettingsObserver.ContentObserverWrapper) hashMap.get(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
            if (contentObserverWrapper == null) {
                EdgeLightingSettingsObserver.ContentObserverWrapper contentObserverWrapper2 = new EdgeLightingSettingsObserver.ContentObserverWrapper(null);
                hashMap.put(SettingsHelper.INDEX_EDGE_LIGHTING_ON, contentObserverWrapper2);
                contentObserverWrapper2.mObservers.add(anonymousClass2);
                contentResolver.registerContentObserver(uriFor, false, contentObserverWrapper2);
            } else if (!contentObserverWrapper.mObservers.contains(anonymousClass2)) {
                contentObserverWrapper.mObservers.add(anonymousClass2);
            }
            getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_THEME_APP_ICON), false, this.mDBObserver);
            getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON), false, this.mDBObserver);
            this.mDBObserver.onChange(true, Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_THEME_APP_ICON));
            this.mDBObserver.onChange(true, Settings.System.getUriFor(SettingsHelper.NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON));
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
                if (stringSet.size() > 0) {
                    for (String str : stringSet) {
                        edgeLightingSettingManager.mEnableSet.put(str, new EdgeLightingSettingItem(str, -11761985));
                    }
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.remove("silent_add_list");
                    edit.apply();
                    z = true;
                } else {
                    z = false;
                }
                Set<String> stringSet2 = sharedPreferences.getStringSet("silent_remove_list", new HashSet());
                boolean z2 = z;
                if (stringSet2.size() > 0) {
                    Iterator<String> it = stringSet2.iterator();
                    while (it.hasNext()) {
                        edgeLightingSettingManager.mEnableSet.remove(it.next());
                    }
                    SharedPreferences.Editor edit2 = sharedPreferences.edit();
                    edit2.remove("silent_remove_list");
                    edit2.apply();
                    z2 = true;
                }
                if (z2) {
                    SharedPreferences.Editor edit3 = sharedPreferences.edit();
                    edit3.putInt("version", 1);
                    edit3.putBoolean("all_application", false);
                    edit3.putStringSet("enable_list", edgeLightingSettingManager.mEnableSet.keySet());
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startEdgeLighting(java.lang.String r16, com.samsung.android.edge.SemEdgeLightingInfo r17, int r18) {
        /*
            Method dump skipped, instructions count: 984
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.EdgeLightingService.startEdgeLighting(java.lang.String, com.samsung.android.edge.SemEdgeLightingInfo, int):void");
    }

    public final void stopEdgeLightingService() {
        setProcessForeground(false);
        stopForeground(true);
        stopSelf();
    }
}
