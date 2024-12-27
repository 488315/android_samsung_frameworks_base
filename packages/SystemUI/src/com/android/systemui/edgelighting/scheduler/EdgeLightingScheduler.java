package com.android.systemui.edgelighting.scheduler;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.R;
import com.android.systemui.edgelighting.EdgeLightingForegroundService;
import com.android.systemui.edgelighting.EdgeLightingService;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.device.EdgeLightingCoverManager;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.scheduler.LightingScheduleInfo;
import com.android.systemui.edgelighting.scheduler.NotificationLightingScheduler;
import com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.SemEdgeLightingInfoUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.edge.SemEdgeManager;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EdgeLightingScheduler {
    public ApplicationLightingScheduler mApplicationLightingScheduler;
    public Context mContext;
    public PowerManager.WakeLock mDrawWakeLock;
    public final SemEdgeManager mEdgeManager;
    public boolean mIsScreenOnReceived;
    public NotificationLightingScheduler mNotificationLightingScheduler;
    public AnonymousClass7 mOneHandOperationObserver;
    public PowerManager mPm;
    public EdgeLightingService.AnonymousClass4 mRequester;
    public EdgeLightingScreenStatus mScreenStatusChecker;
    public TurnOverEdgeLighting mTurnOverEdgeLighting;
    public PowerManager.WakeLock mWakeLock;
    public final AnonymousClass1 mHandler = new Handler() { // from class: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.1
        /* JADX WARN: Code restructure failed: missing block: B:179:0x0470, code lost:
        
            if (r0 != 6) goto L200;
         */
        /* JADX WARN: Removed duplicated region for block: B:155:0x0447  */
        /* JADX WARN: Removed duplicated region for block: B:157:0x044e  */
        /* JADX WARN: Removed duplicated region for block: B:182:0x04ae  */
        /* JADX WARN: Removed duplicated region for block: B:185:0x04bc  */
        /* JADX WARN: Removed duplicated region for block: B:206:0x0552  */
        /* JADX WARN: Removed duplicated region for block: B:352:0x04b1  */
        /* JADX WARN: Removed duplicated region for block: B:410:0x09aa  */
        /* JADX WARN: Removed duplicated region for block: B:411:0x09ac A[Catch: all -> 0x08f6, TryCatch #1 {all -> 0x08f6, blocks: (B:380:0x08e1, B:382:0x08ec, B:383:0x08f3, B:386:0x08f9, B:388:0x0909, B:390:0x0925, B:392:0x093c, B:394:0x0945, B:395:0x094d, B:397:0x0951, B:399:0x0955, B:400:0x095b, B:401:0x0978, B:403:0x097c, B:405:0x0982, B:407:0x098a, B:408:0x0992, B:411:0x09ac, B:413:0x09b6, B:414:0x09bb, B:416:0x09c3, B:417:0x09c8, B:419:0x09dd, B:421:0x09e3, B:422:0x09e6, B:424:0x0a31), top: B:379:0x08e1 }] */
        /* JADX WARN: Removed duplicated region for block: B:70:0x01b8  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x01ba A[Catch: all -> 0x010d, TryCatch #2 {all -> 0x010d, blocks: (B:42:0x00fa, B:44:0x0103, B:45:0x010a, B:48:0x0110, B:50:0x0120, B:52:0x014c, B:54:0x0155, B:55:0x015d, B:57:0x0161, B:59:0x0165, B:60:0x016a, B:61:0x0187, B:63:0x018b, B:65:0x0191, B:67:0x0199, B:68:0x01a1, B:71:0x01ba, B:73:0x01c4, B:74:0x01c9, B:76:0x01d1, B:77:0x01d6, B:79:0x01eb, B:81:0x01f1, B:82:0x01f4, B:84:0x0275, B:87:0x0240, B:89:0x024e, B:91:0x0258, B:93:0x0264, B:95:0x026a, B:96:0x0270), top: B:41:0x00fa }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r17) {
            /*
                Method dump skipped, instructions count: 2614
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass1.handleMessage(android.os.Message):void");
        }
    };
    public final AnonymousClass2 mEdgeLightingObserver = new EdgeLightingSettingsObserver.EdgeLightingObserver() { // from class: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.2
        @Override // com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.EdgeLightingObserver
        public final Handler getHandler() {
            return EdgeLightingScheduler.this.mHandler;
        }

        @Override // com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.EdgeLightingObserver
        public final void onChange() {
            EdgeLightingScheduler.this.mTurnOverEdgeLighting.setEnable();
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$4, reason: invalid class name */
    public final class AnonymousClass4 {
        public AnonymousClass4() {
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x007f  */
        /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void stopNotification(boolean r6) {
            /*
                r5 = this;
                com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler r5 = com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.this
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting r0 = r5.mTurnOverEdgeLighting
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$ITurnModeState r1 = r0.mCurrentTurnMode
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$ITurnModeState r1 = r1.onNotificationEnd()
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$ITurnModeState r2 = r0.mCurrentTurnMode
                int r2 = r2.getMode()
                r3 = 1
                java.lang.String r4 = "EdgeLightingScheduler"
                if (r2 == r3) goto L61
                r3 = 2
                if (r2 == r3) goto L5e
                r0.mCurrentTurnMode = r1
                java.lang.String r0 = "stopNotification"
                android.util.Slog.d(r4, r0)
                r0 = 0
                if (r6 == 0) goto L32
                java.lang.String r6 = "stop Notification to turn to heads up"
                android.util.Slog.d(r4, r6)
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                r6.requestStopService()
                r5.releaseWakeLock()
                goto L52
            L32:
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                boolean r6 = r6.isUIControllerExist()
                if (r6 == 0) goto L44
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher r6 = r6.getUIController(r0)
                r6.stopEdgeEffect()
                goto L52
            L44:
                java.lang.String r6 = "stopNotification not exist. so stop service"
                android.util.Slog.d(r4, r6)
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                r6.requestStopService()
                r5.releaseWakeLock()
            L52:
                boolean r6 = com.android.systemui.edgelighting.utils.Utils.isLargeCoverFlipFolded()
                if (r6 == 0) goto L69
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                r6.requestDozeStateSubScreen(r0)
                goto L69
            L5e:
                r0.mCurrentTurnMode = r1
                goto L63
            L61:
                r0.mCurrentTurnMode = r1
            L63:
                java.lang.String r6 = "stopNotification: end with turnover"
                android.util.Slog.d(r4, r6)
            L69:
                android.content.Intent r6 = new android.content.Intent
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting r0 = r5.mTurnOverEdgeLighting
                android.content.Context r0 = r0.mContext
                java.lang.Class<com.android.systemui.edgelighting.EdgeLightingForegroundService> r1 = com.android.systemui.edgelighting.EdgeLightingForegroundService.class
                r6.<init>(r0, r1)
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting r0 = r5.mTurnOverEdgeLighting
                android.content.Context r0 = r0.mContext
                r0.stopService(r6)
                com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$7 r6 = r5.mOneHandOperationObserver
                if (r6 == 0) goto L8f
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting r6 = r5.mTurnOverEdgeLighting
                android.content.Context r6 = r6.mContext
                android.content.ContentResolver r6 = r6.getContentResolver()
                com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$7 r0 = r5.mOneHandOperationObserver
                r6.unregisterContentObserver(r0)
                r6 = 0
                r5.mOneHandOperationObserver = r6
            L8f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass4.stopNotification(boolean):void");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$5, reason: invalid class name */
    public final class AnonymousClass5 {
        public AnonymousClass5() {
        }

        public final void onIdle() {
            EdgeLightingScheduler edgeLightingScheduler = EdgeLightingScheduler.this;
            if (edgeLightingScheduler.mRequester.isUIControllerExist()) {
                edgeLightingScheduler.mRequester.getUIController(false).stopEdgeEffect();
            }
        }

        public final void onTurnOver(boolean z) {
            EdgeLightingScheduler edgeLightingScheduler = EdgeLightingScheduler.this;
            if (z) {
                if (Settings.System.getIntForUser(EdgeLightingService.this.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2) == 1) {
                    Slog.d("EdgeLightingScheduler", "onTurnOver: calling not available with screen on setting");
                    return;
                }
                Slog.d("EdgeLightingScheduler", "startCallingEffect");
                EdgeEffectInfo edgeEffectInfo = new EdgeEffectInfo();
                edgeEffectInfo.mEffectColors = new int[]{-9980597};
                edgeEffectInfo.mIsBlackBG = true;
                edgeEffectInfo.mStrokeWidth = edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getResources().getDimensionPixelSize(R.dimen.edge_lighting_turnover_width);
                edgeEffectInfo.mWidthDepth = -1;
                edgeEffectInfo.mInfiniteLighting = true;
                edgeLightingScheduler.mRequester.getUIController(true).startEdgeEffect(edgeEffectInfo);
                Slog.d("EdgeLightingScheduler", "EdgeLightingEventStyleInfo," + EdgeLightingSettingUtils.effectInfoToString(edgeEffectInfo, EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver())));
                edgeLightingScheduler.mRequester.isScreenOn();
                edgeLightingScheduler.mRequester.isScreenOn();
                edgeLightingScheduler.mRequester.getClass();
                return;
            }
            NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
            LightingScheduleInfo lightingScheduleInfo = notificationLightingScheduler.mCurrentLightingScheduleInfo;
            if (lightingScheduleInfo == null) {
                Slog.d("EdgeLightingScheduler", "onTurnOver: noti info empty");
                return;
            }
            notificationLightingScheduler.extendLightingDuration(PluginEdgeLightingPlus.VERSION, true);
            if (edgeLightingScheduler.mRequester.isScreenOn()) {
                LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy = lightingScheduleInfo.mLightingLogicPolicy;
                if (lightingLogicPolicy == null) {
                    lightingLogicPolicy = new LightingScheduleInfo.LightingLogicPolicy();
                }
                if (lightingLogicPolicy.isNeedLightOnWhenTurnOveredLcdOn) {
                    EdgeLightingScheduler.m1939$$Nest$mstartNotiEffect(edgeLightingScheduler, true);
                    return;
                }
                return;
            }
            LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy2 = lightingScheduleInfo.mLightingLogicPolicy;
            if (lightingLogicPolicy2 == null) {
                lightingLogicPolicy2 = new LightingScheduleInfo.LightingLogicPolicy();
            }
            if (lightingLogicPolicy2.isNeedLightOnWhenTurnOveredLcdOff) {
                EdgeLightingScheduler.m1939$$Nest$mstartNotiEffect(edgeLightingScheduler, true);
            }
        }

        public final void onTurnRight() {
            EdgeLightingScheduler edgeLightingScheduler = EdgeLightingScheduler.this;
            LightingScheduleInfo lightingScheduleInfo = edgeLightingScheduler.mNotificationLightingScheduler.mCurrentLightingScheduleInfo;
            if (lightingScheduleInfo == null) {
                Slog.d("EdgeLightingScheduler", "onTurnRight: noti info empty");
                return;
            }
            if (!edgeLightingScheduler.mRequester.isScreenOn()) {
                LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy = lightingScheduleInfo.mLightingLogicPolicy;
                if (lightingLogicPolicy == null) {
                    lightingLogicPolicy = new LightingScheduleInfo.LightingLogicPolicy();
                }
                if (lightingLogicPolicy.isNeedLightOnWhenTurnRightedLcdOff) {
                    EdgeLightingScheduler.m1939$$Nest$mstartNotiEffect(edgeLightingScheduler, false);
                    return;
                }
                return;
            }
            EdgeLightingService.AnonymousClass4 anonymousClass4 = edgeLightingScheduler.mRequester;
            anonymousClass4.getClass();
            boolean z = EdgeLightingService.sConfigured;
            KeyguardManager keyguardManager = (KeyguardManager) EdgeLightingService.this.getSystemService("keyguard");
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                Slog.d("EdgeLightingScheduler", "onTurnRight: keyguard + screenon");
                return;
            }
            LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy2 = lightingScheduleInfo.mLightingLogicPolicy;
            if (lightingLogicPolicy2 == null) {
                lightingLogicPolicy2 = new LightingScheduleInfo.LightingLogicPolicy();
            }
            if (lightingLogicPolicy2.isNeedLightOnWhenTurnRightedLcdOn) {
                EdgeLightingScheduler.m1939$$Nest$mstartNotiEffect(edgeLightingScheduler, false);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$6, reason: invalid class name */
    public final class AnonymousClass6 {
        public AnonymousClass6() {
        }
    }

    /* renamed from: -$$Nest$misNeedToBlockedByPolicy, reason: not valid java name */
    public static boolean m1938$$Nest$misNeedToBlockedByPolicy(EdgeLightingScheduler edgeLightingScheduler, String str, int i) {
        SemStatusBarManager semStatusBarManager = (SemStatusBarManager) EdgeLightingService.this.getSystemService(SemStatusBarManager.class);
        if (semStatusBarManager != null ? semStatusBarManager.isPanelExpanded() : false) {
            Slog.d("EdgeLightingScheduler", "isNeedToBlockedByPolicy: not work on statusbar");
        } else if (Settings.System.getIntForUser(EdgeLightingService.this.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2) == 1 && str != null && str.startsWith("com.samsung.android.messaging") && !edgeLightingScheduler.mIsScreenOnReceived) {
            Slog.d("EdgeLightingScheduler", "isNeedToBlockedByPolicy: skip by screen on order policy " + i + " " + str);
        } else {
            if (EdgeLightingCoverManager.getInstance().mSwitchState) {
                return false;
            }
            Slog.d("EdgeLightingScheduler", "isNeedToBlockedByPolicy: not work when cover");
        }
        return true;
    }

    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$7] */
    /* renamed from: -$$Nest$mstartNotiEffect, reason: not valid java name */
    public static void m1939$$Nest$mstartNotiEffect(EdgeLightingScheduler edgeLightingScheduler, boolean z) {
        ArrayList arrayList;
        int i;
        String str;
        StringBuffer stringBuffer = new StringBuffer("startNotiEffect:  dur=");
        LightingScheduleInfo lightingScheduleInfo = edgeLightingScheduler.mNotificationLightingScheduler.mCurrentLightingScheduleInfo;
        if (lightingScheduleInfo == null) {
            Slog.d("EdgeLightingScheduler", "startNotiEffect: noti info empty");
            return;
        }
        if (Utils.isLargeCoverFlipFolded()) {
            edgeLightingScheduler.mRequester.requestDozeStateSubScreen(true);
        }
        if (Utils.isLargeCoverFlipFolded()) {
            edgeLightingScheduler.mPm.userActivity(SystemClock.uptimeMillis(), 0, 0);
        }
        boolean isScreenOn = edgeLightingScheduler.mRequester.isScreenOn();
        if (edgeLightingScheduler.mOneHandOperationObserver != null) {
            edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver().unregisterContentObserver(edgeLightingScheduler.mOneHandOperationObserver);
            edgeLightingScheduler.mOneHandOperationObserver = null;
        }
        if (edgeLightingScheduler.mOneHandOperationObserver == null) {
            edgeLightingScheduler.mOneHandOperationObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.7
                @Override // android.database.ContentObserver
                public final void onChange(boolean z2) {
                    NotificationLightingScheduler notificationLightingScheduler;
                    EdgeLightingScheduler edgeLightingScheduler2 = EdgeLightingScheduler.this;
                    Context context = edgeLightingScheduler2.mTurnOverEdgeLighting.mContext;
                    edgeLightingScheduler2.getClass();
                    boolean z3 = Settings.System.getIntForUser(context.getContentResolver(), SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING, 0, -2) == 1;
                    Slog.i("EdgeLightingScheduler", " mOneHandOperationObserver value = " + z3);
                    if (!z3 || (notificationLightingScheduler = EdgeLightingScheduler.this.mNotificationLightingScheduler) == null) {
                        return;
                    }
                    notificationLightingScheduler.flushNotiNow();
                }
            };
        }
        edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING), false, edgeLightingScheduler.mOneHandOperationObserver);
        stringBuffer.append(lightingScheduleInfo.getDuration());
        EdgeEffectInfo edgeEffectInfo = new EdgeEffectInfo();
        Bundle extra = lightingScheduleInfo.mLightingInfo.getExtra();
        if (extra == null || (arrayList = extra.getParcelableArrayList("noti_actions")) == null) {
            arrayList = null;
        }
        edgeEffectInfo.mHasActionButton = arrayList != null;
        Context context = edgeLightingScheduler.mTurnOverEdgeLighting.mContext;
        String[] notiText = lightingScheduleInfo.getNotiText();
        int[] effectColors = lightingScheduleInfo.mLightingInfo.getEffectColors();
        String str2 = lightingScheduleInfo.mPackageName;
        edgeEffectInfo.mEffectColors = EdgeLightingSettingUtils.getLightingColor(context, notiText, str2, effectColors);
        edgeEffectInfo.mIsBlackBG = z;
        edgeLightingScheduler.mTurnOverEdgeLighting.getClass();
        edgeEffectInfo.mEdgeLightingAction = true;
        if (!isScreenOn) {
            edgeLightingScheduler.mTurnOverEdgeLighting.mContext.startService(new Intent(edgeLightingScheduler.mTurnOverEdgeLighting.mContext, (Class<?>) EdgeLightingForegroundService.class));
        }
        if (isScreenOn || !z) {
            edgeEffectInfo.mStrokeAlpha = 1.0f - (Settings.System.getIntForUser(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), "edge_lighting_transparency", 0, -2) / 100.0f);
            Context context2 = edgeLightingScheduler.mTurnOverEdgeLighting.mContext;
            EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver());
            float edgeLightingStyleWidth = EdgeLightingSettingUtils.getEdgeLightingStyleWidth(Settings.System.getIntForUser(context2.getContentResolver(), "edge_lighting_thickness", 0, -2), context2);
            int intForUser = Settings.System.getIntForUser(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), "edge_lighting_thickness", 0, -2);
            edgeEffectInfo.mStrokeWidth = edgeLightingStyleWidth;
            edgeEffectInfo.mWidthDepth = intForUser;
            edgeEffectInfo.mLightingDuration = EdgeLightingSettingUtils.getEdgeLightingDuration(EdgeLightingSettingUtils.loadEdgeLightingDurationOptionType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext));
            if (isScreenOn) {
                stringBuffer.append(" +On");
            } else {
                stringBuffer.append(" +Off");
            }
        } else {
            stringBuffer.append(" +TurnOver");
            edgeEffectInfo.mLightingDuration = 6000L;
            edgeEffectInfo.mStrokeWidth = edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getResources().getDimensionPixelSize(R.dimen.edge_lighting_turnover_width);
            edgeEffectInfo.mWidthDepth = -1;
        }
        if (z) {
            stringBuffer.append(" +TurnOver");
            edgeLightingScheduler.mRequester.getUIController(true).startEdgeEffect(edgeEffectInfo);
            Slog.d("EdgeLightingScheduler", "EdgeLightingEventStyleInfo," + EdgeLightingSettingUtils.effectInfoToString(edgeEffectInfo, EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver())));
            long duration = (long) (lightingScheduleInfo.getDuration() + 2000);
            PowerManager.WakeLock wakeLock = edgeLightingScheduler.mWakeLock;
            if (wakeLock != null) {
                wakeLock.acquire(duration);
            }
            PowerManager.WakeLock wakeLock2 = edgeLightingScheduler.mDrawWakeLock;
            if (wakeLock2 != null) {
                wakeLock2.acquire(duration);
            }
        } else {
            boolean isSensitiveStateActive = edgeLightingScheduler.mRequester.isSensitiveStateActive();
            boolean isNeedToSanitized = edgeLightingScheduler.mRequester.isNeedToSanitized(lightingScheduleInfo.getUserId(), lightingScheduleInfo.getVisibility(), lightingScheduleInfo.getNotificationKey());
            boolean shouldHideNotiForAppLockByPackage = edgeLightingScheduler.mRequester.isSupportAppLock() ? edgeLightingScheduler.mRequester.isAppLockEnabled() : false ? edgeLightingScheduler.mRequester.shouldHideNotiForAppLockByPackage(str2) : false;
            edgeEffectInfo.mAppIcon = edgeLightingScheduler.getAppIcon(lightingScheduleInfo);
            edgeEffectInfo.mIsSmallIcon = edgeLightingScheduler.isSmallIcon(str2, lightingScheduleInfo.mLightingInfo.getExtra().getBoolean("show_small_icon", false));
            edgeEffectInfo.mIsUsingAppIcon = EdgeLightingService.this.mIsUsingAppIcon;
            edgeEffectInfo.mText = lightingScheduleInfo.getNotiText();
            int preloadIndex = EdgeLightingStyleManager.getInstance().getPreloadIndex(EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver()));
            boolean z2 = Settings.System.getInt(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), SettingsHelper.INDEX_REMOVE_ANIMATION, 0) == 1;
            Uri parse = Uri.parse("content://com.samsung.android.systemui.edgelighting.plus.provider");
            if (edgeLightingScheduler.mContext.getContentResolver().acquireContentProviderClient(parse) != null) {
                Bundle bundle = new Bundle();
                String[] strArr = edgeEffectInfo.mText;
                if (strArr != null) {
                    i = preloadIndex;
                    bundle.putString(UniversalCredentialUtil.AGENT_TITLE, strArr[0]);
                    bundle.putString("description", edgeEffectInfo.mText[1]);
                } else {
                    i = preloadIndex;
                }
                str = "EdgeLightingEventStyleInfo,";
                edgeEffectInfo.mPlusEffectBundle = edgeLightingScheduler.mContext.getContentResolver().call(parse, "getData()", (String) null, bundle);
            } else {
                i = preloadIndex;
                str = "EdgeLightingEventStyleInfo,";
            }
            Bundle bundle2 = edgeEffectInfo.mPlusEffectBundle;
            if (bundle2 == null || !bundle2.getBoolean("isUsingCustomEffect")) {
                edgeEffectInfo.mEffectType = z2 ? 0 : i;
            } else {
                edgeEffectInfo.mEffectType = 100;
            }
            edgeEffectInfo.mPackageName = str2;
            edgeEffectInfo.mIsMultiResolutionSupoorted = true;
            edgeEffectInfo.mIsGrayScaled = ContrastColorUtil.getInstance(edgeLightingScheduler.mTurnOverEdgeLighting.mContext).isGrayscaleIcon(lightingScheduleInfo.mIcon);
            EdgeLightingService.AnonymousClass4 anonymousClass4 = edgeLightingScheduler.mRequester;
            anonymousClass4.getClass();
            boolean z3 = EdgeLightingService.sConfigured;
            KeyguardManager keyguardManager = (KeyguardManager) EdgeLightingService.this.getSystemService("keyguard");
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                stringBuffer.append("+locked");
                if (Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 0, -2) == 1) {
                    boolean z4 = Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), "lock_screen_allow_private_notifications", 1, -2) == 0;
                    Bundle extra2 = lightingScheduleInfo.mLightingInfo.getExtra();
                    boolean z5 = (extra2 != null ? extra2.getInt("package_visiblity") : -1000) == 0;
                    int visibility = lightingScheduleInfo.getVisibility();
                    if (isNeedToSanitized || visibility == 0 || visibility == -1 || z4 || z5 || isSensitiveStateActive || shouldHideNotiForAppLockByPackage) {
                        edgeEffectInfo.mText = new String[]{edgeLightingScheduler.getAppName(str2), null};
                        if (isScreenOn && !Utils.isLargeCoverFlipFolded()) {
                            Slog.i("EdgeLightingScheduler", "Not showing edgelighting because suppressAwakeHeadsUp is true");
                            return;
                        }
                    }
                    stringBuffer.append("+notiOn");
                    stringBuffer.append(z4 ? "+hideContent" : " ");
                    stringBuffer.append(z5 ? "+hideContentPackageName" : " ");
                    stringBuffer.append("notiVisibility: ");
                    stringBuffer.append(visibility);
                }
            } else if (isNeedToSanitized || isSensitiveStateActive || shouldHideNotiForAppLockByPackage) {
                edgeEffectInfo.mText = new String[]{edgeLightingScheduler.getAppName(str2), null};
            }
            PendingIntent contentIntent = lightingScheduleInfo.getContentIntent();
            if (contentIntent != null) {
                edgeEffectInfo.mPendingIntent = contentIntent;
            }
            edgeEffectInfo.mNotificationKey = lightingScheduleInfo.getNotificationKey();
            edgeLightingScheduler.mRequester.getUIController(false).startEdgeEffect(edgeEffectInfo);
            Slog.d("EdgeLightingScheduler", str + EdgeLightingSettingUtils.effectInfoToString(edgeEffectInfo, EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver())));
            long duration2 = (long) (lightingScheduleInfo.getDuration() + 2000);
            PowerManager.WakeLock wakeLock3 = edgeLightingScheduler.mWakeLock;
            if (wakeLock3 != null) {
                wakeLock3.acquire(duration2);
            }
            PowerManager.WakeLock wakeLock4 = edgeLightingScheduler.mDrawWakeLock;
            if (wakeLock4 != null) {
                wakeLock4.acquire(duration2);
            }
        }
        String text = SemEdgeLightingInfoUtils.getText(lightingScheduleInfo.mLightingInfo, "component");
        boolean isScreenOn2 = edgeLightingScheduler.mRequester.isScreenOn();
        edgeLightingScheduler.mRequester.isScreenOn();
        edgeLightingScheduler.mRequester.getClass();
        if (text != null && isScreenOn2) {
            edgeLightingScheduler.mRequester.getClass();
        }
        Slog.d("EdgeLightingScheduler", stringBuffer.toString());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$2] */
    public EdgeLightingScheduler(SemEdgeManager semEdgeManager) {
        this.mEdgeManager = semEdgeManager;
    }

    public final Drawable getAppIcon(LightingScheduleInfo lightingScheduleInfo) {
        String str = lightingScheduleInfo.mPackageName;
        Drawable drawable = null;
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 4202624);
            if (!isSmallIcon(str, lightingScheduleInfo.mLightingInfo.getExtra().getBoolean("show_small_icon", false))) {
                if (EdgeLightingService.this.mIsColorThemeEnabled) {
                    List<LauncherActivityInfo> activityList = ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).getActivityList(str, UserHandle.getUserHandleForUid(applicationInfo.uid));
                    drawable = !activityList.isEmpty() ? activityList.get(0).semGetBadgedIconForIconTray(this.mContext.getResources().getDisplayMetrics().densityDpi) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                } else {
                    drawable = packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return drawable == null ? lightingScheduleInfo.mIcon : drawable;
    }

    public final String getAppName(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 8704);
            if (applicationInfo != null) {
                return String.valueOf(packageManager.getApplicationLabel(applicationInfo));
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return str;
    }

    public final boolean isSmallIcon(String str, boolean z) {
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 4202624);
            if (!str.equals("android") && !str.equals("com.android.systemui") && applicationInfo.icon != 0) {
                if (EdgeLightingService.this.mIsUsingAppIcon && !z) {
                    return false;
                }
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void notifyEdgeLightingPackageList(boolean z) {
        LightingScheduleInfo lightingScheduleInfo;
        LightingScheduleInfo lightingScheduleInfo2;
        ArrayList arrayList = new ArrayList();
        if (!z) {
            ApplicationLightingScheduler applicationLightingScheduler = this.mApplicationLightingScheduler;
            if (applicationLightingScheduler != null) {
                synchronized (applicationLightingScheduler.mLinkedInfo) {
                    lightingScheduleInfo2 = applicationLightingScheduler.mCurrentLightingScheduleInfo;
                }
                if (lightingScheduleInfo2 != null) {
                    arrayList.add(lightingScheduleInfo2.mPackageName);
                }
            }
            NotificationLightingScheduler notificationLightingScheduler = this.mNotificationLightingScheduler;
            if (notificationLightingScheduler != null && (lightingScheduleInfo = notificationLightingScheduler.mCurrentLightingScheduleInfo) != null) {
                arrayList.add(lightingScheduleInfo.mPackageName);
            }
        }
        Slog.d("EdgeLightingScheduler", "notifyEdgeLightingPackageList :" + arrayList.toString() + ", empty = " + z);
        this.mEdgeManager.updateEdgeLightingPackageList(arrayList);
    }

    public final void notifyScreenOff() {
        this.mIsScreenOnReceived = false;
        NotificationLightingScheduler notificationLightingScheduler = this.mNotificationLightingScheduler;
        if (notificationLightingScheduler != null) {
            notificationLightingScheduler.flushNotiNow();
        }
        if (this.mRequester.isUIControllerExist()) {
            this.mRequester.getUIController(false).stopEdgeEffect();
        }
    }

    public final void notifyScreenOn() {
        this.mIsScreenOnReceived = true;
        if (this.mScreenStatusChecker != null) {
            Slog.d("EdgeLightingScreenStatus", UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
            System.currentTimeMillis();
        }
        if (this.mTurnOverEdgeLighting.mIsUpsideDown == 1) {
            Slog.d("EdgeLightingScheduler", "notifyScreenOn: isUpsideDown is true");
            return;
        }
        NotificationLightingScheduler notificationLightingScheduler = this.mNotificationLightingScheduler;
        if (notificationLightingScheduler == null || notificationLightingScheduler.mCurrentLightingScheduleInfo == null) {
            return;
        }
        NotificationLightingScheduler.AnonymousClass1 anonymousClass1 = notificationLightingScheduler.mNotificationScheduleHandler;
        if (anonymousClass1.hasMessages(0)) {
            return;
        }
        anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0, notificationLightingScheduler.mCurrentLightingScheduleInfo.getNotificationKey()), 4000L);
    }

    public final void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.mWakeLock.release();
        }
        PowerManager.WakeLock wakeLock2 = this.mDrawWakeLock;
        if (wakeLock2 == null || !wakeLock2.isHeld()) {
            return;
        }
        this.mDrawWakeLock.release();
    }
}
