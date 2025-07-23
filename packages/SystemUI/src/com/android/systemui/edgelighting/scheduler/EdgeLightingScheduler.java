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
import com.android.systemui.edgelighting.scheduler.NotificationLightingScheduler;
import com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.SemEdgeLightingInfoUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.edge.SemEdgeManager;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class EdgeLightingScheduler {
    public ApplicationLightingScheduler mApplicationLightingScheduler;
    public EdgeLightingService mContext;
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
        /* JADX WARN: Code restructure failed: missing block: B:180:0x0469, code lost:
        
            if (r0 != 6) goto L211;
         */
        /* JADX WARN: Removed duplicated region for block: B:156:0x0442  */
        /* JADX WARN: Removed duplicated region for block: B:158:0x0448  */
        /* JADX WARN: Removed duplicated region for block: B:183:0x04a7  */
        /* JADX WARN: Removed duplicated region for block: B:186:0x04b5  */
        /* JADX WARN: Removed duplicated region for block: B:206:0x0547  */
        /* JADX WARN: Removed duplicated region for block: B:351:0x04aa  */
        /* JADX WARN: Removed duplicated region for block: B:408:0x09a2 A[DONT_GENERATE] */
        /* JADX WARN: Removed duplicated region for block: B:409:0x09a4 A[Catch: all -> 0x08ec, TryCatch #1 {all -> 0x08ec, blocks: (B:379:0x08d8, B:381:0x08e3, B:382:0x08ea, B:384:0x08ef, B:386:0x08ff, B:388:0x091b, B:390:0x0932, B:392:0x093b, B:393:0x0943, B:395:0x0947, B:397:0x094b, B:398:0x0951, B:399:0x096e, B:401:0x0972, B:403:0x0978, B:405:0x0982, B:406:0x098a, B:409:0x09a4, B:411:0x09ae, B:412:0x09b3, B:414:0x09bb, B:415:0x09c0, B:417:0x09d4, B:419:0x09da, B:420:0x09dd, B:422:0x0a28), top: B:378:0x08d8 }] */
        /* JADX WARN: Removed duplicated region for block: B:72:0x01b8 A[DONT_GENERATE] */
        /* JADX WARN: Removed duplicated region for block: B:73:0x01ba A[Catch: all -> 0x010d, TryCatch #2 {all -> 0x010d, blocks: (B:45:0x00fb, B:47:0x0104, B:48:0x010b, B:50:0x0110, B:52:0x0120, B:54:0x014c, B:56:0x0155, B:57:0x015d, B:59:0x0161, B:61:0x0165, B:62:0x016a, B:63:0x0187, B:65:0x018b, B:67:0x0191, B:69:0x0199, B:70:0x01a1, B:73:0x01ba, B:75:0x01c4, B:76:0x01c9, B:78:0x01d1, B:79:0x01d6, B:81:0x01ea, B:83:0x01f0, B:84:0x01f3, B:86:0x0272, B:88:0x023d, B:90:0x024b, B:92:0x0255, B:94:0x0261, B:96:0x0267, B:97:0x026d), top: B:44:0x00fb }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r22) {
            /*
                Method dump skipped, instructions count: 2605
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$3, reason: invalid class name */
    public class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$4, reason: invalid class name */
    public class AnonymousClass4 {
        public AnonymousClass4() {
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x007e  */
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
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$StateIdle r1 = r0.mCurrentTurnMode
                r1.getClass()
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$StateIdle r2 = r0.mCurrentTurnMode
                int r2 = r2.getMode()
                r3 = 1
                java.lang.String r4 = "EdgeLightingScheduler"
                if (r2 == r3) goto L60
                r3 = 2
                if (r2 == r3) goto L5d
                r0.mCurrentTurnMode = r1
                java.lang.String r0 = "stopNotification"
                android.util.Slog.d(r4, r0)
                r0 = 0
                if (r6 == 0) goto L31
                java.lang.String r6 = "stop Notification to turn to heads up"
                android.util.Slog.d(r4, r6)
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                r6.requestStopService()
                r5.releaseWakeLock()
                goto L51
            L31:
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                boolean r6 = r6.isUIControllerExist()
                if (r6 == 0) goto L43
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher r6 = r6.getUIController(r0)
                r6.stopEdgeEffect()
                goto L51
            L43:
                java.lang.String r6 = "stopNotification not exist. so stop service"
                android.util.Slog.d(r4, r6)
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                r6.requestStopService()
                r5.releaseWakeLock()
            L51:
                boolean r6 = com.android.systemui.edgelighting.utils.Utils.isLargeCoverFlipFolded()
                if (r6 == 0) goto L68
                com.android.systemui.edgelighting.EdgeLightingService$4 r6 = r5.mRequester
                r6.requestDozeStateSubScreen(r0)
                goto L68
            L5d:
                r0.mCurrentTurnMode = r1
                goto L62
            L60:
                r0.mCurrentTurnMode = r1
            L62:
                java.lang.String r6 = "stopNotification: end with turnover"
                android.util.Slog.d(r4, r6)
            L68:
                android.content.Intent r6 = new android.content.Intent
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting r0 = r5.mTurnOverEdgeLighting
                android.content.Context r0 = r0.mContext
                java.lang.Class<com.android.systemui.edgelighting.EdgeLightingForegroundService> r1 = com.android.systemui.edgelighting.EdgeLightingForegroundService.class
                r6.<init>(r0, r1)
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting r0 = r5.mTurnOverEdgeLighting
                android.content.Context r0 = r0.mContext
                r0.stopService(r6)
                com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$7 r6 = r5.mOneHandOperationObserver
                if (r6 == 0) goto L8e
                com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting r6 = r5.mTurnOverEdgeLighting
                android.content.Context r6 = r6.mContext
                android.content.ContentResolver r6 = r6.getContentResolver()
                com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$7 r0 = r5.mOneHandOperationObserver
                r6.unregisterContentObserver(r0)
                r6 = 0
                r5.mOneHandOperationObserver = r6
            L8e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.AnonymousClass4.stopNotification(boolean):void");
        }
    }

    /* renamed from: -$$Nest$misNeedToBlockedByPolicy, reason: not valid java name */
    public static boolean m2559$$Nest$misNeedToBlockedByPolicy(EdgeLightingScheduler edgeLightingScheduler, String str, int i) {
        SemStatusBarManager semStatusBarManager = (SemStatusBarManager) EdgeLightingService.this.getSystemService(SemStatusBarManager.class);
        if (semStatusBarManager != null ? semStatusBarManager.isPanelExpanded() : false) {
            Slog.d("EdgeLightingScheduler", "isNeedToBlockedByPolicy: not work on statusbar");
            return true;
        }
        if (Settings.System.getIntForUser(EdgeLightingService.this.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2) != 1 || str == null || !str.startsWith("com.samsung.android.messaging") || edgeLightingScheduler.mIsScreenOnReceived) {
            if (EdgeLightingCoverManager.getInstance().mSwitchState) {
                return false;
            }
            Slog.d("EdgeLightingScheduler", "isNeedToBlockedByPolicy: not work when cover");
            return true;
        }
        Slog.d("EdgeLightingScheduler", "isNeedToBlockedByPolicy: skip by screen on order policy " + i + " " + str);
        return true;
    }

    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$7] */
    /* renamed from: -$$Nest$mstartNotiEffect, reason: not valid java name */
    public static void m2560$$Nest$mstartNotiEffect(EdgeLightingScheduler edgeLightingScheduler, boolean z) {
        ArrayList arrayList;
        boolean z2;
        boolean z3;
        boolean z4;
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
                public final void onChange(boolean z5) {
                    NotificationLightingScheduler notificationLightingScheduler;
                    EdgeLightingScheduler edgeLightingScheduler2 = EdgeLightingScheduler.this;
                    Context context = edgeLightingScheduler2.mTurnOverEdgeLighting.mContext;
                    edgeLightingScheduler2.getClass();
                    boolean z6 = Settings.System.getIntForUser(context.getContentResolver(), SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING, 0, -2) == 1;
                    Slog.i("EdgeLightingScheduler", " mOneHandOperationObserver value = " + z6);
                    if (!z6 || (notificationLightingScheduler = EdgeLightingScheduler.this.mNotificationLightingScheduler) == null) {
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
        String str = lightingScheduleInfo.mPackageName;
        edgeEffectInfo.mEffectColors = EdgeLightingSettingUtils.getLightingColor(context, notiText, str, effectColors);
        edgeEffectInfo.mIsBlackBG = z;
        edgeLightingScheduler.mTurnOverEdgeLighting.getClass();
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
            boolean shouldHideNotiForAppLockByPackage = edgeLightingScheduler.mRequester.isSupportAppLock() ? edgeLightingScheduler.mRequester.isAppLockEnabled() : false ? edgeLightingScheduler.mRequester.shouldHideNotiForAppLockByPackage(str) : false;
            edgeEffectInfo.mAppIcon = edgeLightingScheduler.getAppIcon(lightingScheduleInfo);
            edgeEffectInfo.mIsSmallIcon = edgeLightingScheduler.isSmallIcon(str, lightingScheduleInfo.mLightingInfo.getExtra().getBoolean("show_small_icon", false));
            edgeEffectInfo.mIsUsingAppIcon = EdgeLightingService.this.mIsUsingAppIcon;
            edgeEffectInfo.mText = lightingScheduleInfo.getNotiText();
            int preloadIndex = EdgeLightingStyleManager.getInstance().getPreloadIndex(EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver()));
            boolean z5 = Settings.System.getInt(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), SettingsHelper.INDEX_REMOVE_ANIMATION, 0) == 1;
            Uri parse = Uri.parse("content://com.samsung.android.systemui.edgelighting.plus.provider");
            if (edgeLightingScheduler.mContext.getContentResolver().acquireContentProviderClient(parse) != null) {
                Bundle bundle = new Bundle();
                z4 = true;
                String[] strArr = edgeEffectInfo.mText;
                if (strArr != null) {
                    z2 = isSensitiveStateActive;
                    bundle.putString(UniversalCredentialUtil.AGENT_TITLE, strArr[0]);
                    bundle.putString("description", edgeEffectInfo.mText[1]);
                } else {
                    z2 = isSensitiveStateActive;
                }
                z3 = isScreenOn;
                edgeEffectInfo.mPlusEffectBundle = edgeLightingScheduler.mContext.getContentResolver().call(parse, "getData()", (String) null, bundle);
            } else {
                z2 = isSensitiveStateActive;
                z3 = isScreenOn;
                z4 = true;
            }
            Bundle bundle2 = edgeEffectInfo.mPlusEffectBundle;
            if (bundle2 == null || !bundle2.getBoolean("isUsingCustomEffect")) {
                if (z5) {
                    preloadIndex = 0;
                }
                edgeEffectInfo.mEffectType = preloadIndex;
            } else {
                edgeEffectInfo.mEffectType = 100;
            }
            edgeEffectInfo.mPackageName = str;
            edgeEffectInfo.mIsMultiResolutionSupoorted = z4;
            edgeEffectInfo.mIsGrayScaled = ContrastColorUtil.getInstance(edgeLightingScheduler.mTurnOverEdgeLighting.mContext).isGrayscaleIcon(lightingScheduleInfo.mIcon);
            EdgeLightingService.AnonymousClass4 anonymousClass4 = edgeLightingScheduler.mRequester;
            anonymousClass4.getClass();
            boolean z6 = EdgeLightingService.sConfigured;
            KeyguardManager keyguardManager = (KeyguardManager) EdgeLightingService.this.getSystemService("keyguard");
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                stringBuffer.append("+locked");
                if (Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 0, -2) == 1) {
                    boolean z7 = Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), "lock_screen_allow_private_notifications", 1, -2) == 0;
                    boolean z8 = lightingScheduleInfo.getPackageVisibility() == 0;
                    if (lightingScheduleInfo.getPackageVisibility() == -1000) {
                        z8 = z7;
                    }
                    int visibility = lightingScheduleInfo.getVisibility();
                    if (isNeedToSanitized || visibility == 0 || visibility == -1 || z8 || z2 || shouldHideNotiForAppLockByPackage) {
                        edgeEffectInfo.mText = new String[]{edgeLightingScheduler.getAppName(lightingScheduleInfo.getUserId(), str), null};
                        if (z3 && !Utils.isLargeCoverFlipFolded()) {
                            Slog.i("EdgeLightingScheduler", "Not showing edgelighting because suppressAwakeHeadsUp is true");
                            return;
                        }
                    }
                    stringBuffer.append("+notiOn");
                    stringBuffer.append(z7 ? "+hideContent" : " ");
                    stringBuffer.append(z8 ? "+hideContentPackageName" : " ");
                    stringBuffer.append("notiVisibility: ");
                    stringBuffer.append(visibility);
                }
            } else if (isNeedToSanitized || z2 || shouldHideNotiForAppLockByPackage) {
                edgeEffectInfo.mText = new String[]{edgeLightingScheduler.getAppName(lightingScheduleInfo.getUserId(), str), null};
            }
            PendingIntent contentIntent = lightingScheduleInfo.getContentIntent();
            if (contentIntent != null) {
                edgeEffectInfo.mPendingIntent = contentIntent;
            }
            edgeEffectInfo.mNotificationKey = lightingScheduleInfo.getNotificationKey();
            edgeLightingScheduler.mRequester.getUIController(false).startEdgeEffect(edgeEffectInfo);
            Slog.d("EdgeLightingScheduler", "EdgeLightingEventStyleInfo," + EdgeLightingSettingUtils.effectInfoToString(edgeEffectInfo, EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver())));
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

    public final String getAppName(int i, String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, 8704, i);
            if (applicationInfoAsUser != null) {
                return String.valueOf(packageManager.getApplicationLabel(applicationInfoAsUser));
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return str;
    }

    public final boolean isSmallIcon(String str, boolean z) {
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 4202624);
            if (str.equals("android") || str.equals("com.android.systemui") || applicationInfo.icon == 0) {
                return true;
            }
            return !EdgeLightingService.this.mIsUsingAppIcon || z;
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
