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
import android.content.res.Resources;
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
import android.util.Slog;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
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
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass3;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass4;
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
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
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

/* loaded from: classes2.dex */
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
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            EdgeLightingService edgeLightingService = EdgeLightingService.this;
            if (edgeLightingService.mShouldKillMyself) {
                edgeLightingService.setProcessForeground(false);
                edgeLightingService.stopForeground(true);
                edgeLightingService.stopSelf();
            }
        }
    }

    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$3, reason: invalid class name */
    public class AnonymousClass3 implements OnEdgeLightingCallback {
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
    public class AnonymousClass4 {
        public AnonymousClass4() {
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
                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void doActionNotification() throws PendingIntent.CanceledException {
                        ArrayList parcelableArrayList;
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        edgeLightingScheduler.getClass();
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null) {
                            Bundle extra = notificationLightingScheduler.mCurrentLightingScheduleInfo.mLightingInfo.getExtra();
                            if (extra == null || (parcelableArrayList = extra.getParcelableArrayList("noti_actions")) == null) {
                                parcelableArrayList = null;
                            }
                            String string = edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getString(R.string.restrict_mark_as_read);
                            if (parcelableArrayList != null) {
                                int size = parcelableArrayList.size();
                                int i = 0;
                                while (i < size) {
                                    Object obj = parcelableArrayList.get(i);
                                    i++;
                                    Notification.Action action = (Notification.Action) obj;
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
                        LightingScheduleInfo lightingScheduleInfo;
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
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingService2.mScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null && (lightingScheduleInfo = notificationLightingScheduler.mCurrentLightingScheduleInfo) != null && lightingScheduleInfo.getNotificationKey().equals(str)) {
                            NotificationLightingScheduler.AnonymousClass1 anonymousClass1 = notificationLightingScheduler.mNotificationScheduleHandler;
                            anonymousClass1.removeMessages(0);
                            anonymousClass1.sendMessage(anonymousClass1.obtainMessage(0, "turnToHeadsUp"));
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
                        LightingScheduleInfo lightingScheduleInfo;
                        NotificationLightingScheduler notificationLightingScheduler = EdgeLightingService.this.mScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler == null || (lightingScheduleInfo = notificationLightingScheduler.mCurrentLightingScheduleInfo) == null || lightingScheduleInfo.getDuration() >= 5500) {
                            return;
                        }
                        Slog.d("NotificationLightingScheduler", "extendLightingDuration for verification");
                        notificationLightingScheduler.mCurrentLightingScheduleInfo.setDuration(5500);
                        NotificationLightingScheduler.AnonymousClass1 anonymousClass1 = notificationLightingScheduler.mNotificationScheduleHandler;
                        anonymousClass1.removeMessages(0);
                        anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0, notificationLightingScheduler.mCurrentLightingScheduleInfo.getNotificationKey()), 5500);
                    }

                    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
                    public final void onFling(boolean z3) {
                        EdgeLightingAnalytics.sendEventLog(EdgeLightingAnalytics.sCurrentScreenID, "QPNE0103");
                        if (CoreRune.MW_SA_LOGGING) {
                            CoreSaLogger.logForAdvanced("2004", "From Noti_Swipedown");
                        }
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler == null || notificationLightingScheduler.mCurrentLightingScheduleInfo == null) {
                            return;
                        }
                        notificationLightingScheduler.flushNotiNow();
                        LightingScheduleInfo lightingScheduleInfo = edgeLightingScheduler.mNotificationLightingScheduler.mCurrentLightingScheduleInfo;
                        if (!z3) {
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
                    public final void onFlingDownInWindow(boolean z3) throws PackageManager.NameNotFoundException {
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
                            if (uIController == null) {
                                uIController = null;
                            }
                            if (uIController != null) {
                                EdgeLightingService edgeLightingService2 = edgeLightingScheduler.mContext;
                                PendingIntent contentIntent = lightingScheduleInfo.getContentIntent();
                                String str = lightingScheduleInfo.mPackageName;
                                NotificationEffect notificationEffect = uIController.mDialog.mNotificationEffect;
                                int i = Utils.$r8$clinit;
                                ?? packageManager = edgeLightingService2.getPackageManager();
                                try {
                                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 795136);
                                    packageManager = applicationInfo != null ? packageManager.getApplicationIcon(applicationInfo) : packageManager.getDefaultActivityIcon();
                                } catch (PackageManager.NameNotFoundException unused) {
                                    packageManager = packageManager.getDefaultActivityIcon();
                                }
                                Bitmap bitmapDrawableToBitmap = DrawableUtils.drawableToBitmap(packageManager);
                                ImageView imageView = new ImageView(edgeLightingService2);
                                imageView.setImageBitmap(bitmapDrawableToBitmap);
                                imageView.layout(0, 0, edgeLightingService2.getResources().getDimensionPixelSize(R.dimen.drag_and_drop_icon_size), edgeLightingService2.getResources().getDimensionPixelSize(R.dimen.drag_and_drop_icon_size));
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
                        EdgeLightingScheduler edgeLightingScheduler = EdgeLightingService.this.mScheduler;
                        NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                        if (notificationLightingScheduler != null) {
                            LightingScheduleInfo lightingScheduleInfo = notificationLightingScheduler.mCurrentLightingScheduleInfo;
                            if (lightingScheduleInfo != null) {
                                String str = lightingScheduleInfo.mPackageName;
                                try {
                                    SemEdgeManager semEdgeManager = edgeLightingScheduler.mEdgeManager;
                                    String notificationTag = lightingScheduleInfo.getNotificationTag();
                                    int notificationID = lightingScheduleInfo.getNotificationID();
                                    int userId = lightingScheduleInfo.getUserId();
                                    String notificationKey = lightingScheduleInfo.getNotificationKey();
                                    Bundle extra = lightingScheduleInfo.mLightingInfo.getExtra();
                                    semEdgeManager.cancelNotificationByGroupKey(str, notificationTag, notificationID, userId, notificationKey, extra != null ? extra.getString("group_key") : null);
                                    StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m(" swipe cancel pkg: ", str, " , tag :  ");
                                    sbM.append(lightingScheduleInfo.getNotificationTag());
                                    sbM.append(" id: ");
                                    sbM.append(lightingScheduleInfo.getNotificationID());
                                    sbM.append(" , userid : ");
                                    sbM.append(lightingScheduleInfo.getUserId());
                                    sbM.append(" , key : ");
                                    sbM.append(lightingScheduleInfo.getNotificationKey());
                                    sbM.append(" , groupKey : ");
                                    Bundle extra2 = lightingScheduleInfo.mLightingInfo.getExtra();
                                    sbM.append(extra2 != null ? extra2.getString("group_key") : null);
                                    Slog.i("EdgeLightingScheduler", sbM.toString());
                                } catch (RuntimeException unused) {
                                    edgeLightingScheduler.mEdgeManager.cancelNotification(lightingScheduleInfo.mPackageName, lightingScheduleInfo.getNotificationTag(), lightingScheduleInfo.getNotificationID(), lightingScheduleInfo.getUserId(), lightingScheduleInfo.getNotificationKey());
                                    StringBuilder sbM2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m(" swipe cancel pkg: ", str, " , tag :  ");
                                    sbM2.append(lightingScheduleInfo.getNotificationTag());
                                    sbM2.append(" id: ");
                                    sbM2.append(lightingScheduleInfo.getNotificationID());
                                    sbM2.append(" , userid : ");
                                    sbM2.append(lightingScheduleInfo.getUserId());
                                    sbM2.append(" , key : ");
                                    sbM2.append(lightingScheduleInfo.getNotificationKey());
                                    Slog.i("EdgeLightingScheduler", sbM2.toString());
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
                if (iSystemUIConditionListener == null || str == null) {
                    return;
                }
                iSystemUIConditionListener.sendClickEvent(str);
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
    public class AnonymousClass7 {
        public AnonymousClass7() {
        }
    }

    /* renamed from: com.android.systemui.edgelighting.EdgeLightingService$9, reason: invalid class name */
    public class AnonymousClass9 extends ContentObserver {
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

    public class MainHandler extends Handler {
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

    public class StatusbarStateReceiver extends BroadcastReceiver {
        public /* synthetic */ StatusbarStateReceiver(EdgeLightingService edgeLightingService, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            NotificationLightingScheduler notificationLightingScheduler;
            String action = intent.getAction();
            boolean zEquals = "com.samsung.systemui.statusbar.ANIMATING".equals(action);
            boolean zEquals2 = "com.samsung.systemui.statusbar.EXPANDED".equals(action);
            if (zEquals || zEquals2) {
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
        this.mKillBot = new AnonymousClass1();
        this.mEdgeLightingObserver = new EdgeLightingSettingsObserver.EdgeLightingObserver() { // from class: com.android.systemui.edgelighting.EdgeLightingService.2
            @Override // com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.EdgeLightingObserver
            public final Handler getHandler() {
                return EdgeLightingService.this.mHandler;
            }

            @Override // com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.EdgeLightingObserver
            public final void onChange() {
                EdgeLightingService edgeLightingService = EdgeLightingService.this;
                boolean zIsEdgeLightingEnabled = EdgeLightingSettingUtils.isEdgeLightingEnabled(edgeLightingService.getContentResolver());
                boolean z = EdgeLightingService.sConfigured;
                Slog.i("EdgeLightingService", "EdgeLightingObserver: !!!! enable " + zIsEdgeLightingEnabled);
                if (!zIsEdgeLightingEnabled) {
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
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISystemUIConditionListener)) ? new ISystemUIConditionListener.Stub.Proxy(iBinder) : (ISystemUIConditionListener) iInterfaceQueryLocalInterface;
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
        int iSemGetMyUserId = UserHandle.semGetMyUserId();
        Slog.i("Utils", "isCurrentUser current = " + iSemGetMyUserId + ", ownerId = 0");
        return iSemGetMyUserId == 0 ? !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION") ? "not Support" : "" : "not Owner";
    }

    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.getInstance(getApplicationContext());
        edgeLightingSettingManager.getClass();
        StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m("Enable pkg ( ");
        if (edgeLightingSettingManager.mAllApplication) {
            sbM.append("ALL");
        } else {
            sbM.append(edgeLightingSettingManager.mEnableSet.size());
        }
        sbM.append(" )  : ");
        Iterator it = edgeLightingSettingManager.mEnableSet.entrySet().iterator();
        while (it.hasNext()) {
            sbM.append((String) ((Map.Entry) it.next()).getKey());
            sbM.append(", ");
        }
        printWriter.println(sbM);
        super.dump(fileDescriptor, printWriter, strArr);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() throws Resources.NotFoundException {
        super.onCreate();
        Slog.d("EdgeLightingService", "onCreate");
        this.mShouldKillMyself = true;
        String strCheckEdgeLightingAvailable = checkEdgeLightingAvailable();
        if (!"".equals(strCheckEdgeLightingAvailable)) {
            Slog.e("EdgeLightingService", "OnCreate : edgelighting is not availabe now : ".concat(strCheckEdgeLightingAvailable));
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
        this.mIsStarted = false;
        this.mCondition = 0;
        if (this.mConditionListener != null) {
            unbindService(this.mConnection);
            this.mConditionListener = null;
        }
        super.onDestroy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0367  */
    /* JADX WARN: Type inference failed for: r0v36, types: [com.android.systemui.edgelighting.device.EdgeLightingCoverManager$1] */
    /* JADX WARN: Type inference failed for: r0v48, types: [com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$5] */
    /* JADX WARN: Type inference failed for: r11v52, types: [com.android.systemui.edgelighting.EdgeLightingService$6] */
    /* JADX WARN: Type inference failed for: r3v23, types: [com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$6] */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int onStartCommand(Intent intent, int i, int i2) {
        EdgeLightingService edgeLightingService;
        boolean z;
        HashMap map;
        Uri uriFor;
        String strCheckEdgeLightingAvailable = checkEdgeLightingAvailable();
        if (!"".equals(strCheckEdgeLightingAvailable)) {
            Slog.e("EdgeLightingService", "onStartCommand : edgelighting is not availabe now : ".concat(strCheckEdgeLightingAvailable));
            this.mKillBot.run();
            return 2;
        }
        if (this.mEdgeManager == null) {
            this.mEdgeManager = (SemEdgeManager) getSystemService("edge");
            Slog.e("EdgeLightingService", "onStartCommand : mEdgeManager = " + this.mEdgeManager);
        }
        int i3 = 0;
        if (SemEmergencyManager.isEmergencyMode(this)) {
            setProcessForeground(false);
            stopForeground(true);
            stopSelf();
            return 2;
        }
        setProcessForeground(true);
        if (this.mScheduler == null) {
            final EdgeLightingScheduler edgeLightingScheduler = new EdgeLightingScheduler(this.mEdgeManager);
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
                ?? r0 = new Object() { // from class: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.5
                    public AnonymousClass5() {
                    }
                };
                ?? r3 = new Object() { // from class: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.6
                    public AnonymousClass6() {
                    }
                };
                TurnOverEdgeLighting turnOverEdgeLighting = edgeLightingScheduler.mTurnOverEdgeLighting;
                turnOverEdgeLighting.mListener = r0;
                turnOverEdgeLighting.mRequestor = r3;
                turnOverEdgeLighting.setEnable();
            }
            EdgeLightingScheduler edgeLightingScheduler2 = this.mScheduler;
            AnonymousClass4 anonymousClass4 = new AnonymousClass4();
            edgeLightingScheduler2.mRequester = anonymousClass4;
            edgeLightingScheduler2.mIsScreenOnReceived = anonymousClass4.isScreenOn();
            EdgeLightingScreenStatus edgeLightingScreenStatus = edgeLightingScheduler2.mScreenStatusChecker;
            boolean zIsScreenOn = edgeLightingScheduler2.mRequester.isScreenOn();
            edgeLightingScreenStatus.getClass();
            if (zIsScreenOn) {
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
                    ?? r02 = new ScoverManager.CoverStateListener() { // from class: com.android.systemui.edgelighting.device.EdgeLightingCoverManager.1
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
                            ArrayList arrayList = edgeLightingCoverManager2.mCoverStateListeners;
                            int size = arrayList.size();
                            int i4 = 0;
                            while (i4 < size) {
                                Object obj = arrayList.get(i4);
                                i4++;
                                ((EdgeLightingService.AnonymousClass7) obj).getClass();
                            }
                        }

                        @Override // com.samsung.android.sdk.cover.ScoverManager.CoverStateListener
                        public final void onCoverSwitchStateChanged(boolean z2) {
                            if (EdgeLightingCoverManager.DEBUG) {
                                Slog.d("EdgeLightingCoverManager", "onCoverSwitchStateChanged : " + z2);
                            }
                            EdgeLightingCoverManager edgeLightingCoverManager2 = EdgeLightingCoverManager.this;
                            edgeLightingCoverManager2.mSwitchState = z2;
                            ArrayList arrayList = edgeLightingCoverManager2.mCoverStateListeners;
                            int size = arrayList.size();
                            int i4 = 0;
                            while (i4 < size) {
                                Object obj = arrayList.get(i4);
                                i4++;
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
                    edgeLightingCoverManager.mSCoverStateListener = r02;
                    try {
                        edgeLightingCoverManager.mSCoverManager.registerListener(r02);
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
                map = edgeLightingSettingsObserver.mSystemObservers;
                uriFor = Settings.System.getUriFor(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
            } else if (Settings.System.class == Settings.Global.class) {
                map = edgeLightingSettingsObserver.mGlobalObservers;
                uriFor = Settings.Global.getUriFor(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
            } else {
                Slog.e("EdgeLightingSettingsObserver", "registerContentObserver : wrong table");
                getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_THEME_APP_ICON), false, this.mDBObserver);
                getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON), false, this.mDBObserver);
                this.mDBObserver.onChange(true, Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_THEME_APP_ICON));
                this.mDBObserver.onChange(true, Settings.System.getUriFor(SettingsHelper.NOTI_SETTINGS_SHOW_NOTIFICATION_APP_ICON));
            }
            EdgeLightingSettingsObserver.ContentObserverWrapper contentObserverWrapper = (EdgeLightingSettingsObserver.ContentObserverWrapper) map.get(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
            if (contentObserverWrapper == null) {
                EdgeLightingSettingsObserver.ContentObserverWrapper contentObserverWrapper2 = new EdgeLightingSettingsObserver.ContentObserverWrapper(null);
                map.put(SettingsHelper.INDEX_EDGE_LIGHTING_ON, contentObserverWrapper2);
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
                setProcessForeground(false);
                stopForeground(true);
                stopSelf();
            } else if (!extras.getBoolean("forUpdatePolicy", false) || this.mIsStarted) {
                String string = extras.getString("packagename");
                SemEdgeLightingInfo parcelable = extras.getParcelable("info");
                int i5 = extras.getInt("reason");
                Slog.d("EdgeLightingService", "onStartCommand pkg=" + string + ",info=" + parcelable + ",reason=" + i5);
                if (string != null && parcelable != null) {
                    edgeLightingService = this;
                    this.mHandler.post(new EdgeLightingService$$ExternalSyntheticLambda0(edgeLightingService, string, parcelable, i5, 0));
                    edgeLightingService.mShouldKillMyself = false;
                }
            } else {
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
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.remove("silent_add_list");
                    editorEdit.apply();
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
                    SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
                    editorEdit2.remove("silent_remove_list");
                    editorEdit2.apply();
                    z2 = true;
                }
                if (z2) {
                    SharedPreferences.Editor editorEdit3 = sharedPreferences.edit();
                    editorEdit3.putInt("version", 1);
                    editorEdit3.putBoolean("all_application", false);
                    editorEdit3.putStringSet("enable_list", edgeLightingSettingManager.mEnableSet.keySet());
                    editorEdit3.apply();
                }
                edgeLightingSettingManager.removeBlockListInEnabledEdgeLightingList(this, (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(2));
                edgeLightingPolicyManager.updateEdgeLightingPolicy(this, edgeLightingSettingManager.mAllApplication);
                setProcessForeground(false);
                stopForeground(true);
                stopSelf();
            }
            return 2;
        }
        edgeLightingService = this;
        edgeLightingService.mIsStarted = true;
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
    /* JADX WARN: Removed duplicated region for block: B:131:0x0241  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
        Drawable drawableLoadDrawable;
        PolicyInfo policyInfo;
        int deviceWallPaperColorIndex;
        Bundle extra;
        int i2;
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
            if (Settings.Secure.getIntForUser(getContentResolver(), SettingsHelper.INDEX_COVER_SCREEN_SHOW_NOTIFICATION, 1, -2) != 1) {
                MainHandler mainHandler4 = this.mHandler;
                mainHandler4.sendMessage(mainHandler4.obtainMessage(1, "reason is turn off subscreen notification"));
                return;
            } else if (Settings.Secure.getIntForUser(getContentResolver(), SettingsHelper.INDEX_TURN_ON_COVER_SCREEN_FOR_NOTIFICATION, 1, -2) == 0 && !this.mPowerManager.isInteractive()) {
                MainHandler mainHandler5 = this.mHandler;
                mainHandler5.sendMessage(mainHandler5.obtainMessage(1, "reason is turn off \"Turn on screen for notifications\""));
                return;
            }
        }
        if (this.mConditionListener != null) {
            String string = semEdgeLightingInfo.getExtra().getString("noti_key");
            try {
                if (this.mConditionListener.isInterrupted(string)) {
                    boolean z = SemEdgeLightingInfoUtils.DEBUG;
                    Bundle extra2 = semEdgeLightingInfo.getExtra();
                    if ((8 & (extra2 != null ? extra2.getInt("flag", 0) : 0)) != 0) {
                        MainHandler mainHandler6 = this.mHandler;
                        mainHandler6.sendMessage(mainHandler6.obtainMessage(1, "interrupted"));
                        return;
                    }
                }
                if (this.mConditionListener.isRowPinned(string)) {
                    MainHandler mainHandler7 = this.mHandler;
                    mainHandler7.sendMessage(mainHandler7.obtainMessage(1, "isRowPinned"));
                    return;
                } else if (this.mConditionListener.isOngoingAcitivty(string)) {
                    MainHandler mainHandler8 = this.mHandler;
                    mainHandler8.sendMessage(mainHandler8.obtainMessage(1, "isOngoingActivity"));
                    return;
                } else if (!this.mConditionListener.isPanelsEnabled()) {
                    MainHandler mainHandler9 = this.mHandler;
                    mainHandler9.sendMessage(mainHandler9.obtainMessage(1, "isPanelsEnabled"));
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
        if (str.equals("com.sec.android.app.desktoplauncher") && charSequence != null && charSequence.equals("desktop_launcher_chnnel_id")) {
            Slog.i("EdgeLightingService", "disable desktop_launcher channel");
            return;
        }
        if (this.mPowerManager.isInteractive() && ((KeyguardManager) getSystemService("keyguard")).semIsKeyguardShowingAndNotOccluded() && !Utils.isLargeCoverFlipFolded()) {
            MainHandler mainHandler10 = this.mHandler;
            mainHandler10.sendMessage(mainHandler10.obtainMessage(1, "keyguard && screenOn"));
            return;
        }
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
        Drawable applicationIcon = null;
        if ((keyguardManager != null && keyguardManager.isKeyguardLocked()) != false) {
            if ((this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, UserHandle.semGetMyUserId()) & 4) != 0) {
                MainHandler mainHandler11 = this.mHandler;
                mainHandler11.sendMessage(mainHandler11.obtainMessage(1, "blockByDPM"));
                return;
            }
            if (Settings.Secure.getIntForUser(getContentResolver(), SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 0, -2) != 1) {
                MainHandler mainHandler12 = this.mHandler;
                mainHandler12.sendMessage(mainHandler12.obtainMessage(1, "keygaurdNotiOff"));
                return;
            }
            boolean z2 = SemEdgeLightingInfoUtils.DEBUG;
            Bundle extra3 = semEdgeLightingInfo.getExtra();
            if ((extra3 != null ? extra3.getInt("noti_visiblity", 0) : 0) == -1) {
                MainHandler mainHandler13 = this.mHandler;
                mainHandler13.sendMessage(mainHandler13.obtainMessage(1, "secret && keyguard"));
                return;
            }
            Bundle extra4 = semEdgeLightingInfo.getExtra();
            if (extra4 != null && extra4.getInt("package_visiblity") == -1) {
                MainHandler mainHandler14 = this.mHandler;
                mainHandler14.sendMessage(mainHandler14.obtainMessage(1, "secret package && keyguard"));
                return;
            }
        }
        boolean z3 = SemEdgeLightingInfoUtils.DEBUG;
        Bundle extra5 = semEdgeLightingInfo.getExtra();
        if ((extra5 != null ? extra5.getBoolean(SystemUIAnalytics.QPNE_VID_BUBBLE, false) : false) && this.mPowerManager.isInteractive()) {
            MainHandler mainHandler15 = this.mHandler;
            mainHandler15.sendMessage(mainHandler15.obtainMessage(1, SystemUIAnalytics.QPNE_VID_BUBBLE));
            return;
        }
        Configuration configuration = getResources().getConfiguration();
        if (configuration != null && (i2 = configuration.FlipFont) > 0 && sFlipFont != i2) {
            Typeface.setFlipFonts();
            sFlipFont = configuration.FlipFont;
        }
        if (this.mScheduler != null) {
            AppIconCache appIconCache = this.mAppIconCache;
            appIconCache.getClass();
            Bundle extra6 = semEdgeLightingInfo.getExtra();
            if (extra6 != null) {
                Parcelable parcelable = extra6.getParcelable(appIconCache.KEY_SMALL_ICON);
                drawableLoadDrawable = parcelable instanceof Icon ? ((Icon) parcelable).loadDrawable(appIconCache.mContext) : null;
            }
            if (drawableLoadDrawable != null) {
                appIconCache.mIconCache.put(str, drawableLoadDrawable);
            } else {
                drawableLoadDrawable = (Drawable) appIconCache.mIconCache.get(str);
                if (drawableLoadDrawable == null) {
                    try {
                        applicationIcon = appIconCache.mContext.getPackageManager().getApplicationIcon(str);
                        appIconCache.mIconCache.put(str, applicationIcon);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    drawableLoadDrawable = applicationIcon;
                    if (drawableLoadDrawable != null) {
                        appIconCache.mIconCache.put(str, drawableLoadDrawable);
                    }
                }
            }
            Drawable drawable = drawableLoadDrawable;
            if (i != 0) {
                boolean z4 = SemEdgeLightingInfoUtils.DEBUG;
                int i3 = (semEdgeLightingInfo.getExtra() == null || semEdgeLightingInfo.getExtra().getParcelable(SemEdgeLightingInfoUtils.EXTRA_KEY_SMALL_ICON) == null || (extra = semEdgeLightingInfo.getExtra()) == null) ? 0 : extra.getInt("notification_color", 0);
                int intForUser = Settings.System.getIntForUser(getContentResolver(), "edge_lighting_color_type", 1, -2);
                if (intForUser == 0) {
                    deviceWallPaperColorIndex = EdgeLightingSettingUtils.getEdgeLightingStylePreDefineColor(getBaseContext(), EdgeLightingSettingUtils.getEdgeLightingBasicColorIndex(getContentResolver()), false);
                } else if (intForUser == 1) {
                    deviceWallPaperColorIndex = EdgeLightingSettingUtils.loadAppCustomColor(getBaseContext(), str);
                    if (deviceWallPaperColorIndex == 0 && (deviceWallPaperColorIndex = EdgeLightingPolicyManager.getInstance(getApplicationContext(), false).getEdgeLightingColor(getBaseContext(), str)) == -11761985 && i3 != 0) {
                        Slog.i("EdgeLightingService", "Not exist color in white list.So using notification color  : " + Integer.toHexString(i3));
                        deviceWallPaperColorIndex = i3;
                    }
                } else {
                    deviceWallPaperColorIndex = intForUser == 3 ? DeviceColorMonitor.getDeviceWallPaperColorIndex(getContentResolver()) : Settings.Global.getInt(getApplicationContext().getContentResolver(), "edgelighting_custom_color", -11761985);
                }
                semEdgeLightingInfo.setEffectColors(new int[]{deviceWallPaperColorIndex, i3});
            }
            HashMap map = (HashMap) EdgeLightingPolicyManager.getInstance(getApplicationContext(), false).mPolicyInfoData.get(10);
            int i4 = (map == null || (policyInfo = (PolicyInfo) map.get(str)) == null) ? 0 : policyInfo.priority;
            EdgeLightingScheduler edgeLightingScheduler = this.mScheduler;
            edgeLightingScheduler.getClass();
            Slog.d("EdgeLightingScheduler", "startEdgeLighting: " + i + " " + str + " onGo=" + SemEdgeLightingInfoUtils.isOnGoing(semEdgeLightingInfo));
            LightingScheduleInfo lightingScheduleInfo = new LightingScheduleInfo(str, null, semEdgeLightingInfo, drawable, i, i4);
            int intForUser2 = Settings.System.getIntForUser(EdgeLightingService.this.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2);
            LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy = new LightingScheduleInfo.LightingLogicPolicy();
            lightingScheduleInfo.mLightingLogicPolicy = lightingLogicPolicy;
            if (intForUser2 == 0) {
                lightingLogicPolicy.isNeedToKeepWhenLcdOff = true;
            } else if (intForUser2 != 1 && intForUser2 == 2) {
                lightingLogicPolicy.isNeedToKeepWhenLcdOff = true;
            }
            lightingScheduleInfo.setDuration(EdgeLightingSettingUtils.getEdgeLightingDuration(EdgeLightingSettingUtils.loadEdgeLightingDurationOptionType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext)));
            EdgeLightingScheduler.AnonymousClass1 anonymousClass1 = edgeLightingScheduler.mHandler;
            anonymousClass1.sendMessage(Message.obtain(anonymousClass1, 0, lightingScheduleInfo));
            ContextStatusLoggingManager.getInstance().updateStatusLoggingItem(this);
            if (this.mConditionListener != null) {
                try {
                    this.mConditionListener.setInterruption(semEdgeLightingInfo.getExtra().getString("noti_key"));
                } catch (RemoteException unused2) {
                }
            }
        }
    }
}
