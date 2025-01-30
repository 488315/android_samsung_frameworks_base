package com.android.systemui.edgelighting.scheduler;

import android.animation.ValueAnimator;
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
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.view.accessibility.AccessibilityManager;
import android.widget.RelativeLayout;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.R;
import com.android.systemui.edgelighting.EdgeLightingForegroundService;
import com.android.systemui.edgelighting.EdgeLightingService;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.device.DeviceWakeLockManager;
import com.android.systemui.edgelighting.device.EdgeLightingCoverManager;
import com.android.systemui.edgelighting.effect.container.ApplicationEffect;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.container.NotificationEffect;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.view.AbsEdgeLightingMaskView;
import com.android.systemui.edgelighting.effect.view.EdgeLightAppEffectView;
import com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher;
import com.android.systemui.edgelighting.effectservice.EffectServiceController;
import com.android.systemui.edgelighting.manager.EdgeLightingPolicyManager;
import com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.reflection.AbsEdgeLightingEffectReflection;
import com.android.systemui.edgelighting.reflection.EffectInfoReflection;
import com.android.systemui.edgelighting.scheduler.LightingScheduleInfo;
import com.android.systemui.edgelighting.scheduler.NotificationLightingScheduler;
import com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.SemEdgeLightingInfoUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.edge.SemEdgeManager;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.util.SemLog;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeLightingScheduler {
    public ApplicationLightingScheduler mApplicationLightingScheduler;
    public Context mContext;
    public final SemEdgeManager mEdgeManager;
    public boolean mIsScreenOnReceived;
    public NotificationLightingScheduler mNotificationLightingScheduler;
    public C13597 mOneHandOperationObserver;
    public PowerManager mPm;
    public EdgeLightingService.C12994 mRequester;
    public EdgeLightingScreenStatus mScreenStatusChecker;
    public TurnOverEdgeLighting mTurnOverEdgeLighting;
    public PowerManager.WakeLock mWakeLock;
    public final HandlerC13531 mHandler = new Handler() { // from class: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.1
        /* JADX WARN: Code restructure failed: missing block: B:178:0x045b, code lost:
        
            if (r14 != 6) goto L199;
         */
        /* JADX WARN: Removed duplicated region for block: B:181:0x0499  */
        /* JADX WARN: Removed duplicated region for block: B:184:0x04a5  */
        /* JADX WARN: Removed duplicated region for block: B:207:0x053d  */
        /* JADX WARN: Removed duplicated region for block: B:390:0x0948  */
        /* JADX WARN: Removed duplicated region for block: B:391:0x0949 A[Catch: all -> 0x09b9, TryCatch #0 {, blocks: (B:360:0x0881, B:362:0x088c, B:363:0x0894, B:366:0x0897, B:368:0x08a7, B:370:0x08c3, B:372:0x08da, B:374:0x08e3, B:375:0x08eb, B:377:0x08ef, B:379:0x08f3, B:380:0x08f9, B:381:0x0916, B:383:0x091a, B:385:0x0920, B:387:0x0928, B:388:0x0930, B:391:0x0949, B:393:0x0963, B:395:0x0969, B:396:0x096c, B:398:0x09b7), top: B:359:0x0881 }] */
        /* JADX WARN: Removed duplicated region for block: B:70:0x01b3  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x01b5 A[Catch: all -> 0x025c, TryCatch #1 {, blocks: (B:42:0x00f8, B:44:0x0101, B:45:0x0109, B:48:0x010c, B:50:0x011c, B:52:0x0148, B:54:0x0151, B:55:0x0159, B:57:0x015d, B:59:0x0161, B:60:0x0166, B:61:0x0183, B:63:0x0187, B:65:0x018d, B:67:0x0194, B:68:0x019c, B:71:0x01b5, B:73:0x01cf, B:75:0x01d5, B:76:0x01d8, B:78:0x0259, B:81:0x0224, B:83:0x0232, B:85:0x023c, B:87:0x0248, B:89:0x024e, B:90:0x0254), top: B:41:0x00f8 }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void handleMessage(Message message) {
            int i;
            EdgeLightAppEffectView edgeLightAppEffectView;
            boolean z;
            boolean isOnGoing;
            boolean equals;
            Class<?> cls;
            Integer valueOf;
            Bundle extra;
            SemEdgeLightingInfo semEdgeLightingInfo;
            HashMap hashMap;
            AccessibilityManager accessibilityManager;
            int recommendedTimeoutMillis;
            int i2;
            EdgeLightAppEffectView edgeLightAppEffectView2;
            super.handleMessage(message);
            int i3 = message.what;
            boolean z2 = true;
            if (i3 != 0) {
                if (i3 != 1) {
                    return;
                }
                EdgeLightingScheduler edgeLightingScheduler = EdgeLightingScheduler.this;
                LightingScheduleInfo lightingScheduleInfo = (LightingScheduleInfo) message.obj;
                edgeLightingScheduler.getClass();
                if (lightingScheduleInfo.mReason != 0) {
                    NotificationLightingScheduler notificationLightingScheduler = edgeLightingScheduler.mNotificationLightingScheduler;
                    notificationLightingScheduler.getClass();
                    String str = lightingScheduleInfo.mPackageName;
                    String notificationKey = lightingScheduleInfo.getNotificationKey();
                    int i4 = lightingScheduleInfo.mReason;
                    if (str == null) {
                        Slog.d("NotificationLightingScheduler", "removeLighting: invalid param " + str);
                        return;
                    }
                    if (i4 == 4 || i4 == 6) {
                        DeviceWakeLockManager deviceWakeLockManager = DeviceWakeLockManager.getInstance();
                        deviceWakeLockManager.getClass();
                        if (!str.isEmpty()) {
                            Slog.d("DeviceWakeLockManager", "releaseWakeLockPackage : ".concat(str));
                            deviceWakeLockManager.mWakeLockMap.remove(str);
                        }
                    }
                    NotificationLightingScheduler.HandlerC13601 handlerC13601 = notificationLightingScheduler.mNotificationScheduleHandler;
                    if (i4 == 4 && handlerC13601.hasMessages(0)) {
                        Slog.d("NotificationLightingScheduler", "removeLighting: return by reason" + i4);
                        return;
                    }
                    LightingScheduleInfo lightingScheduleInfo2 = notificationLightingScheduler.mCurrentLightingScheduleInfo;
                    String notificationKey2 = lightingScheduleInfo2 != null ? lightingScheduleInfo2.getNotificationKey() : null;
                    StringBuilder m92m = AbstractC0950x8906c950.m92m("removeLighting: ", str, " reason=", i4, " cur=");
                    m92m.append(notificationKey2);
                    Slog.d("NotificationLightingScheduler", m92m.toString());
                    if (!notificationKey.equals(notificationKey2)) {
                        if (handlerC13601.hasEqualMessages(0, notificationKey)) {
                            handlerC13601.removeEqualMessages(0, notificationKey);
                            handlerC13601.sendMessage(handlerC13601.obtainMessage(0, notificationKey));
                            return;
                        }
                        return;
                    }
                    LightingScheduleInfo lightingScheduleInfo3 = notificationLightingScheduler.mCurrentLightingScheduleInfo;
                    lightingScheduleInfo3.mReason = 1;
                    lightingScheduleInfo3.setDuration(ImsProfile.DEFAULT_DEREG_TIMEOUT);
                    handlerC13601.removeMessages(0);
                    handlerC13601.sendMessageDelayed(handlerC13601.obtainMessage(0, notificationKey), 0L);
                    return;
                }
                ApplicationLightingScheduler applicationLightingScheduler = edgeLightingScheduler.mApplicationLightingScheduler;
                if (applicationLightingScheduler != null) {
                    String str2 = lightingScheduleInfo.mPackageName;
                    if (str2 == null) {
                        Slog.d("ApplicationLightingScheduler", "removeLighting: invalid param " + str2);
                    }
                    synchronized (applicationLightingScheduler.mLinkedInfo) {
                        applicationLightingScheduler.mLinkedInfo.remove(str2);
                        if (applicationLightingScheduler.mListener == null) {
                            Slog.d("ApplicationLightingScheduler", "removeLighting: no listener");
                            return;
                        }
                        if (applicationLightingScheduler.mLinkedInfo.entrySet().iterator().hasNext()) {
                            LightingScheduleInfo lightingScheduleInfo4 = (LightingScheduleInfo) ((Map.Entry) applicationLightingScheduler.mLinkedInfo.entrySet().iterator().next()).getValue();
                            applicationLightingScheduler.mCurrentLightingScheduleInfo = lightingScheduleInfo4;
                            EdgeLightingDispatcher uIController = EdgeLightingScheduler.this.mRequester.getUIController(false);
                            int[] effectColors = lightingScheduleInfo4.mLightingInfo.getEffectColors();
                            EdgeLightingDialog edgeLightingDialog = uIController.mDialog;
                            if (edgeLightingDialog != null) {
                                edgeLightingDialog.mUsingBlackBG = false;
                                edgeLightingDialog.show();
                                if (edgeLightingDialog.mDialogMain == null) {
                                    edgeLightingDialog.mDialogMain = (RelativeLayout) edgeLightingDialog.findViewById(R.id.dialog_main);
                                }
                                RelativeLayout relativeLayout = edgeLightingDialog.mDialogMain;
                                if (relativeLayout != null) {
                                    ApplicationEffect applicationEffect = edgeLightingDialog.mApplicationEffect;
                                    if (applicationEffect != null) {
                                        relativeLayout.removeView(applicationEffect);
                                        edgeLightingDialog.mApplicationEffect = null;
                                    }
                                    ApplicationEffect applicationEffect2 = new ApplicationEffect(edgeLightingDialog.getContext());
                                    edgeLightingDialog.mApplicationEffect = applicationEffect2;
                                    edgeLightingDialog.mDialogMain.addView(applicationEffect2);
                                    edgeLightingDialog.mApplicationEffect.mEdgeListener = edgeLightingDialog.mEdgeAnimationListener;
                                    edgeLightingDialog.getWindow().addFlags(16);
                                }
                                ApplicationEffect applicationEffect3 = edgeLightingDialog.mApplicationEffect;
                                if (effectColors != null) {
                                    applicationEffect3.getClass();
                                    if (effectColors.length > 0) {
                                        i2 = (-16777216) | effectColors[0];
                                        int i5 = effectColors[1];
                                        if (i5 != 0) {
                                            applicationEffect3.mLightEffectView.mSubColor = i5;
                                        }
                                        applicationEffect3.mLightEffectView.setMainColor(i2);
                                        applicationEffect3.mLightEffectView.mIsAnimating = false;
                                        ApplicationEffect applicationEffect4 = edgeLightingDialog.mApplicationEffect;
                                        applicationEffect4.setVisibility(0);
                                        applicationEffect4.containerAlphaAnimation(0.0f, 1.0f);
                                        edgeLightAppEffectView2 = applicationEffect4.mLightEffectView;
                                        if (edgeLightAppEffectView2.mIsAnimating) {
                                            edgeLightAppEffectView2.mIsAnimating = true;
                                            edgeLightAppEffectView2.setImageDrawable();
                                            edgeLightAppEffectView2.startRotation(edgeLightAppEffectView2.mRotateDuration);
                                            AbsEdgeLightingMaskView.changeRingImageAlpha(edgeLightAppEffectView2.mContainer, edgeLightAppEffectView2.mStrokeAlpha, edgeLightAppEffectView2.lineDuration * 3);
                                            int i6 = edgeLightAppEffectView2.mSubColor;
                                            if (i6 != 0) {
                                                int i7 = edgeLightAppEffectView2.mMainColor;
                                                ValueAnimator valueAnimator = edgeLightAppEffectView2.repeatColorAnimation;
                                                if (valueAnimator != null) {
                                                    valueAnimator.cancel();
                                                }
                                                SemLog.i(edgeLightAppEffectView2.TAG, "repeat Color Animation from : " + i7 + " toColor " + i6);
                                                ValueAnimator ofArgb = ValueAnimator.ofArgb(i7, i6);
                                                edgeLightAppEffectView2.repeatColorAnimation = ofArgb;
                                                ofArgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.EdgeLightAppEffectView.1
                                                    public C13241() {
                                                    }

                                                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                                        EdgeLightAppEffectView.this.setMainColor(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                                                    }
                                                });
                                                edgeLightAppEffectView2.repeatColorAnimation.setRepeatMode(2);
                                                edgeLightAppEffectView2.repeatColorAnimation.setRepeatCount(-1);
                                                edgeLightAppEffectView2.repeatColorAnimation.setStartDelay(0L);
                                                edgeLightAppEffectView2.repeatColorAnimation.setDuration(10000L);
                                                edgeLightAppEffectView2.repeatColorAnimation.start();
                                            }
                                        }
                                    }
                                }
                                i2 = -15750429;
                                applicationEffect3.mLightEffectView.setMainColor(i2);
                                applicationEffect3.mLightEffectView.mIsAnimating = false;
                                ApplicationEffect applicationEffect42 = edgeLightingDialog.mApplicationEffect;
                                applicationEffect42.setVisibility(0);
                                applicationEffect42.containerAlphaAnimation(0.0f, 1.0f);
                                edgeLightAppEffectView2 = applicationEffect42.mLightEffectView;
                                if (edgeLightAppEffectView2.mIsAnimating) {
                                }
                            }
                        } else {
                            applicationLightingScheduler.mCurrentLightingScheduleInfo = null;
                            EdgeLightingScheduler edgeLightingScheduler2 = EdgeLightingScheduler.this;
                            if (edgeLightingScheduler2.mRequester.isUIControllerExist()) {
                                EdgeLightingDispatcher uIController2 = edgeLightingScheduler2.mRequester.getUIController(false);
                                EdgeLightingDialog edgeLightingDialog2 = uIController2.mDialog;
                                if (edgeLightingDialog2 != null) {
                                    SemLog.i("EdgeLightingDialog", "stopApplication");
                                    ApplicationEffect applicationEffect5 = edgeLightingDialog2.mApplicationEffect;
                                    if (applicationEffect5 != null && applicationEffect5.isShown()) {
                                        edgeLightingDialog2.mApplicationEffect.containerAlphaAnimation(1.0f, 0.0f);
                                    }
                                } else {
                                    uIController2.mEffectServiceConrtroller.dispatchStop();
                                }
                            }
                        }
                        return;
                    }
                }
                return;
            }
            EdgeLightingScheduler edgeLightingScheduler3 = EdgeLightingScheduler.this;
            LightingScheduleInfo lightingScheduleInfo5 = (LightingScheduleInfo) message.obj;
            edgeLightingScheduler3.getClass();
            if (lightingScheduleInfo5.mReason == 0) {
                ApplicationLightingScheduler applicationLightingScheduler2 = edgeLightingScheduler3.mApplicationLightingScheduler;
                if (applicationLightingScheduler2 != null) {
                    synchronized (applicationLightingScheduler2.mLinkedInfo) {
                        applicationLightingScheduler2.mLinkedInfo.put(lightingScheduleInfo5.mPackageName, lightingScheduleInfo5);
                        if (applicationLightingScheduler2.mListener == null) {
                            Slog.d("ApplicationLightingScheduler", "putLighting: no listener");
                            return;
                        }
                        if (applicationLightingScheduler2.mLinkedInfo.entrySet().iterator().hasNext()) {
                            if (lightingScheduleInfo5.mPackageName.equals(((Map.Entry) applicationLightingScheduler2.mLinkedInfo.entrySet().iterator().next()).getKey())) {
                                applicationLightingScheduler2.mCurrentLightingScheduleInfo = lightingScheduleInfo5;
                                EdgeLightingDispatcher uIController3 = EdgeLightingScheduler.this.mRequester.getUIController(false);
                                int[] effectColors2 = lightingScheduleInfo5.mLightingInfo.getEffectColors();
                                EdgeLightingDialog edgeLightingDialog3 = uIController3.mDialog;
                                if (edgeLightingDialog3 != null) {
                                    edgeLightingDialog3.mUsingBlackBG = false;
                                    edgeLightingDialog3.show();
                                    if (edgeLightingDialog3.mDialogMain == null) {
                                        edgeLightingDialog3.mDialogMain = (RelativeLayout) edgeLightingDialog3.findViewById(R.id.dialog_main);
                                    }
                                    RelativeLayout relativeLayout2 = edgeLightingDialog3.mDialogMain;
                                    if (relativeLayout2 != null) {
                                        ApplicationEffect applicationEffect6 = edgeLightingDialog3.mApplicationEffect;
                                        if (applicationEffect6 != null) {
                                            relativeLayout2.removeView(applicationEffect6);
                                            edgeLightingDialog3.mApplicationEffect = null;
                                        }
                                        ApplicationEffect applicationEffect7 = new ApplicationEffect(edgeLightingDialog3.getContext());
                                        edgeLightingDialog3.mApplicationEffect = applicationEffect7;
                                        edgeLightingDialog3.mDialogMain.addView(applicationEffect7);
                                        edgeLightingDialog3.mApplicationEffect.mEdgeListener = edgeLightingDialog3.mEdgeAnimationListener;
                                        edgeLightingDialog3.getWindow().addFlags(16);
                                    }
                                    ApplicationEffect applicationEffect8 = edgeLightingDialog3.mApplicationEffect;
                                    if (effectColors2 != null) {
                                        applicationEffect8.getClass();
                                        if (effectColors2.length > 0) {
                                            i = (-16777216) | effectColors2[0];
                                            int i8 = effectColors2[1];
                                            if (i8 != 0) {
                                                applicationEffect8.mLightEffectView.mSubColor = i8;
                                            }
                                            applicationEffect8.mLightEffectView.setMainColor(i);
                                            applicationEffect8.mLightEffectView.mIsAnimating = false;
                                            ApplicationEffect applicationEffect9 = edgeLightingDialog3.mApplicationEffect;
                                            applicationEffect9.setVisibility(0);
                                            applicationEffect9.containerAlphaAnimation(0.0f, 1.0f);
                                            edgeLightAppEffectView = applicationEffect9.mLightEffectView;
                                            if (edgeLightAppEffectView.mIsAnimating) {
                                                edgeLightAppEffectView.mIsAnimating = true;
                                                edgeLightAppEffectView.setImageDrawable();
                                                edgeLightAppEffectView.startRotation(edgeLightAppEffectView.mRotateDuration);
                                                AbsEdgeLightingMaskView.changeRingImageAlpha(edgeLightAppEffectView.mContainer, edgeLightAppEffectView.mStrokeAlpha, edgeLightAppEffectView.lineDuration * 3);
                                                int i9 = edgeLightAppEffectView.mSubColor;
                                                if (i9 != 0) {
                                                    int i10 = edgeLightAppEffectView.mMainColor;
                                                    ValueAnimator valueAnimator2 = edgeLightAppEffectView.repeatColorAnimation;
                                                    if (valueAnimator2 != null) {
                                                        valueAnimator2.cancel();
                                                    }
                                                    SemLog.i(edgeLightAppEffectView.TAG, "repeat Color Animation from : " + i10 + " toColor " + i9);
                                                    ValueAnimator ofArgb2 = ValueAnimator.ofArgb(i10, i9);
                                                    edgeLightAppEffectView.repeatColorAnimation = ofArgb2;
                                                    ofArgb2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.EdgeLightAppEffectView.1
                                                        public C13241() {
                                                        }

                                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                        public final void onAnimationUpdate(ValueAnimator valueAnimator22) {
                                                            EdgeLightAppEffectView.this.setMainColor(((Integer) valueAnimator22.getAnimatedValue()).intValue());
                                                        }
                                                    });
                                                    edgeLightAppEffectView.repeatColorAnimation.setRepeatMode(2);
                                                    edgeLightAppEffectView.repeatColorAnimation.setRepeatCount(-1);
                                                    edgeLightAppEffectView.repeatColorAnimation.setStartDelay(0L);
                                                    edgeLightAppEffectView.repeatColorAnimation.setDuration(10000L);
                                                    edgeLightAppEffectView.repeatColorAnimation.start();
                                                }
                                            }
                                        }
                                    }
                                    i = -15750429;
                                    applicationEffect8.mLightEffectView.setMainColor(i);
                                    applicationEffect8.mLightEffectView.mIsAnimating = false;
                                    ApplicationEffect applicationEffect92 = edgeLightingDialog3.mApplicationEffect;
                                    applicationEffect92.setVisibility(0);
                                    applicationEffect92.containerAlphaAnimation(0.0f, 1.0f);
                                    edgeLightAppEffectView = applicationEffect92.mLightEffectView;
                                    if (edgeLightAppEffectView.mIsAnimating) {
                                    }
                                }
                            }
                        }
                        return;
                    }
                }
                return;
            }
            if (lightingScheduleInfo5.getDuration() != -1 && edgeLightingScheduler3.mRequester.isScreenOn() && (accessibilityManager = (AccessibilityManager) edgeLightingScheduler3.mTurnOverEdgeLighting.mContext.getSystemService("accessibility")) != null && (recommendedTimeoutMillis = accessibilityManager.getRecommendedTimeoutMillis(lightingScheduleInfo5.mDuration, 4)) != 0) {
                lightingScheduleInfo5.setDuration(recommendedTimeoutMillis);
                Slog.d("LightingScheduleInfo", "updateTimeToTakeAction time=" + recommendedTimeoutMillis);
            }
            NotificationLightingScheduler notificationLightingScheduler2 = edgeLightingScheduler3.mNotificationLightingScheduler;
            boolean isScreenOn = edgeLightingScheduler3.mRequester.isScreenOn();
            Context context = edgeLightingScheduler3.mTurnOverEdgeLighting.mContext;
            String str3 = lightingScheduleInfo5.mPackageName;
            if (SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo5.mLightingInfo) || (hashMap = (HashMap) EdgeLightingPolicyManager.getInstance(context.getApplicationContext(), false).mPolicyInfoData.get(11)) == null || !hashMap.containsKey(str3)) {
                z = true;
            } else {
                Slog.d("EdgeLightingScheduler", "don't need keep notificaton (" + str3 + ")");
                z = false;
            }
            LightingScheduleInfo lightingScheduleInfo6 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
            String str4 = lightingScheduleInfo6 != null ? lightingScheduleInfo6.mPackageName : null;
            String str5 = lightingScheduleInfo5.mPackageName;
            if (z) {
                isOnGoing = true;
            } else {
                NotificationLightingScheduler.EdgeLightingDataKeeper edgeLightingDataKeeper = notificationLightingScheduler2.mEdgeLightingDataKeeper;
                edgeLightingDataKeeper.getClass();
                Slog.i("NotificationLightingScheduler", " getOldLightingInfo " + str5);
                HashMap hashMap2 = edgeLightingDataKeeper.mNotificationMap;
                isOnGoing = SemEdgeLightingInfoUtils.isOnGoing(hashMap2.containsKey(str5) ? ((NotificationLightingScheduler.EdgeLightingDataKeeper.SemEdgeLightingInfoData) hashMap2.get(str5)).mEdgeLightingInfo : null);
            }
            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("putLighting: ", str5, " reason=");
            m4m.append(lightingScheduleInfo5.mReason);
            m4m.append(" cur=");
            m4m.append(str4);
            m4m.append(", isNeedKeepPackage=");
            m4m.append(z);
            m4m.append(", isNeedKeepNoti=");
            m4m.append(isOnGoing);
            Slog.d("NotificationLightingScheduler", m4m.toString());
            if (notificationLightingScheduler2.mListener == null) {
                Slog.d("NotificationLightingScheduler", "putLighting: no listener");
                return;
            }
            boolean z3 = notificationLightingScheduler2.mCurrentLightingScheduleInfo != null;
            SemEdgeLightingInfo semEdgeLightingInfo2 = lightingScheduleInfo5.mLightingInfo;
            if (z3) {
                LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy = lightingScheduleInfo5.mLightingLogicPolicy;
                if (lightingLogicPolicy == null) {
                    lightingLogicPolicy = new LightingScheduleInfo.LightingLogicPolicy();
                }
                if (lightingLogicPolicy.isNeedToKeepWhenLcdOff && !isScreenOn) {
                    Slog.d("NotificationLightingScheduler", "putLighting: mCurrentLightingScheduleInfo= " + SemEdgeLightingInfoUtils.toString(notificationLightingScheduler2.mCurrentLightingScheduleInfo.mLightingInfo) + ",new=" + SemEdgeLightingInfoUtils.toString(semEdgeLightingInfo2));
                    LightingScheduleInfo lightingScheduleInfo7 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
                    if ((lightingScheduleInfo7 == null || semEdgeLightingInfo2 == null || lightingScheduleInfo7.mLightingInfo == null) ? false : TextUtils.equals(str5, lightingScheduleInfo7.mPackageName)) {
                        lightingScheduleInfo7.getClass();
                    }
                    LightingScheduleInfo lightingScheduleInfo8 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
                    if (lightingScheduleInfo8 != null && SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo8.mLightingInfo) && SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo5.mLightingInfo) && (((semEdgeLightingInfo = notificationLightingScheduler2.mCurrentLightingScheduleInfo.mLightingInfo) == null && semEdgeLightingInfo2 == null) || (semEdgeLightingInfo != null && semEdgeLightingInfo2 != null && TextUtils.equals(SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "tickerText"), SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo2, "tickerText")) && TextUtils.equals(SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "text"), SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo2, "text")) && TextUtils.equals(SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "text"), SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo2, "text")) && TextUtils.equals(SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "subText"), SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo2, "subText"))))) {
                        Slog.d("NotificationLightingScheduler", "putLighting: skip by isDuplicatedOnGoing");
                        return;
                    } else if (SemEdgeLightingInfoUtils.isOnGoing(notificationLightingScheduler2.mCurrentLightingScheduleInfo.mLightingInfo) && !SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo5.mLightingInfo)) {
                        Slog.d("NotificationLightingScheduler", "putLighting: skip by isOnGoing notification showing");
                        return;
                    }
                }
            }
            int i11 = lightingScheduleInfo5.mReason;
            NotificationLightingScheduler.HandlerC13601 handlerC136012 = notificationLightingScheduler2.mNotificationScheduleHandler;
            if (i11 != 4) {
                if (i11 == 5) {
                    if (handlerC136012.hasMessages(0)) {
                        handlerC136012.removeMessages(0);
                    }
                }
                LightingScheduleInfo lightingScheduleInfo9 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
                equals = str5.equals(lightingScheduleInfo9 != null ? lightingScheduleInfo9.mPackageName : null);
                boolean z4 = NotificationLightingScheduler.DEBUG;
                if (equals) {
                    if (z4) {
                        Slog.d("NotificationLightingScheduler", "replaceToNewNoti : " + SemEdgeLightingInfoUtils.toString(semEdgeLightingInfo2));
                    }
                    if (notificationLightingScheduler2.mCurrentLightingScheduleInfo != null) {
                        handlerC136012.removeMessages(0);
                        notificationLightingScheduler2.expireNotiLighting(notificationLightingScheduler2.mCurrentLightingScheduleInfo.getNotificationKey());
                    }
                    notificationLightingScheduler2.mCurrentLightingScheduleInfo = lightingScheduleInfo5;
                    C13564 c13564 = notificationLightingScheduler2.mListener;
                    c13564.getClass();
                    StringBuffer stringBuffer = new StringBuffer("startNotification: ");
                    stringBuffer.append(str5);
                    int i12 = lightingScheduleInfo5.mReason;
                    EdgeLightingScheduler edgeLightingScheduler4 = EdgeLightingScheduler.this;
                    if (EdgeLightingScheduler.m1539$$Nest$misNeedToBlockedByPolicy(edgeLightingScheduler4, str5, i12)) {
                        stringBuffer.append(" +isBlockedByPolicy");
                        Slog.d("EdgeLightingScheduler", stringBuffer.toString());
                        edgeLightingScheduler4.mNotificationLightingScheduler.flushNotiNow();
                    } else {
                        TurnOverEdgeLighting turnOverEdgeLighting = edgeLightingScheduler4.mTurnOverEdgeLighting;
                        TurnOverEdgeLighting.ITurnModeState onNotification = turnOverEdgeLighting.mCurrentTurnMode.onNotification();
                        int mode = onNotification.getMode();
                        if (mode == 1) {
                            turnOverEdgeLighting.mCurrentTurnMode = onNotification;
                        } else if (mode != 2) {
                            turnOverEdgeLighting.mCurrentTurnMode = onNotification;
                            z2 = false;
                        }
                        if (z2) {
                            stringBuffer.append(" +ShowWithTurnOver");
                        } else {
                            EdgeLightingScheduler.m1540$$Nest$mstartNotiEffect(edgeLightingScheduler4, false);
                        }
                        Slog.d("EdgeLightingScheduler", stringBuffer.toString());
                    }
                    if (lightingScheduleInfo5.getDuration() != -1) {
                        handlerC136012.sendMessageDelayed(handlerC136012.obtainMessage(0, lightingScheduleInfo5.getNotificationKey()), lightingScheduleInfo5.getDuration());
                        return;
                    }
                    return;
                }
                LightingScheduleInfo lightingScheduleInfo10 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
                if (lightingScheduleInfo10 != null && str5.equals(lightingScheduleInfo10.mPackageName)) {
                    int visibility = lightingScheduleInfo10.getVisibility();
                    boolean z5 = LightingScheduleInfo.DEBUG;
                    if (z5) {
                        Slog.i("LightingScheduleInfo", " getVisibility : " + lightingScheduleInfo5.getVisibility() + " preVisibility : " + visibility + " getReason : " + lightingScheduleInfo5.mReason);
                    }
                    if ((visibility == 0 || visibility == 1) && lightingScheduleInfo5.mReason != 1) {
                        if (semEdgeLightingInfo2.getExtra() == null) {
                            semEdgeLightingInfo2.setExtra(new Bundle());
                        }
                        Bundle extra2 = semEdgeLightingInfo2.getExtra();
                        if (extra2 != null) {
                            extra2.putInt("noti_visiblity", visibility);
                        }
                    }
                    int i13 = lightingScheduleInfo10.mReason;
                    if (i13 == 4 || i13 == 6) {
                        lightingScheduleInfo5.mReason = i13;
                    }
                    lightingScheduleInfo5.mNotiTextPolicyChain.mergeText(lightingScheduleInfo10);
                    boolean isTextDirty = lightingScheduleInfo5.mNotiTextPolicyChain.isTextDirty();
                    lightingScheduleInfo5.mIsDirty = isTextDirty;
                    if (!isTextDirty && SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo10.mLightingInfo)) {
                        Bundle extra3 = semEdgeLightingInfo2.getExtra();
                        if (extra3 == null) {
                            extra3 = new Bundle();
                        }
                        extra3.putInt("flag", extra3.getInt("flag") | 2);
                        semEdgeLightingInfo2.setExtra(extra3);
                    }
                    if (lightingScheduleInfo10.getContentIntent() != null && lightingScheduleInfo5.getContentIntent() == null && (extra = semEdgeLightingInfo2.getExtra()) != null) {
                        extra.putParcelable("content_intent", lightingScheduleInfo10.getContentIntent());
                    }
                    if (z5) {
                        StringBuffer stringBuffer2 = new StringBuffer("mergeInfo tick=");
                        stringBuffer2.append(Arrays.toString(lightingScheduleInfo5.mNotiTextPolicyChain.getChainText()));
                        stringBuffer2.append(" dirty=");
                        stringBuffer2.append(lightingScheduleInfo5.mIsDirty);
                        stringBuffer2.append(" vis=");
                        stringBuffer2.append(lightingScheduleInfo5.getVisibility());
                        Slog.d("LightingScheduleInfo", stringBuffer2.toString());
                    }
                }
                if (z4) {
                    Slog.d("NotificationLightingScheduler", "updateCurrentNoti : " + SemEdgeLightingInfoUtils.toString(semEdgeLightingInfo2));
                }
                notificationLightingScheduler2.mCurrentLightingScheduleInfo = lightingScheduleInfo5;
                EdgeLightingScheduler edgeLightingScheduler5 = EdgeLightingScheduler.this;
                if (edgeLightingScheduler5.mTurnOverEdgeLighting.mIsUpsideDown == 1) {
                    Slog.d("EdgeLightingScheduler", "updateText: restart edge lighting for turn over");
                    EdgeLightingScheduler.m1540$$Nest$mstartNotiEffect(edgeLightingScheduler5, true);
                    z2 = false;
                } else if (EdgeLightingScheduler.m1539$$Nest$misNeedToBlockedByPolicy(edgeLightingScheduler5, str5, lightingScheduleInfo5.mReason)) {
                    Slog.d("EdgeLightingScheduler", "updateText: skip by Blocking Policy");
                } else {
                    StringBuffer stringBuffer3 = new StringBuffer("updateNotiText: isDirty = ");
                    stringBuffer3.append(lightingScheduleInfo5.mIsDirty);
                    boolean isScreenOn2 = edgeLightingScheduler5.mRequester.isScreenOn();
                    EdgeEffectInfo edgeEffectInfo = new EdgeEffectInfo();
                    edgeEffectInfo.mHasActionButton = lightingScheduleInfo5.getActionList() != null;
                    stringBuffer3.append(" dur=");
                    stringBuffer3.append(lightingScheduleInfo5.getDuration());
                    boolean isNeedToSanitized = edgeLightingScheduler5.mRequester.isNeedToSanitized(lightingScheduleInfo5.getUserId(), lightingScheduleInfo5.getVisibility(), lightingScheduleInfo5.getNotificationKey());
                    edgeEffectInfo.mAppIcon = edgeLightingScheduler5.getAppIcon(lightingScheduleInfo5);
                    edgeEffectInfo.mIsSupportAppIcon = edgeLightingScheduler5.isSupportAppIcon(str5);
                    EdgeLightingService edgeLightingService = EdgeLightingService.this;
                    edgeEffectInfo.mShouldShowAppIcon = edgeLightingService.mShouldShowAppIcon;
                    boolean z6 = EdgeLightingService.sConfigured;
                    KeyguardManager keyguardManager = (KeyguardManager) edgeLightingService.getSystemService("keyguard");
                    if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                        stringBuffer3.append("+locked");
                        if (Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), "lock_screen_show_notifications", 0, -2) == 1) {
                            boolean z7 = Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), "lock_screen_allow_private_notifications", 1, -2) == 0;
                            Bundle extra4 = lightingScheduleInfo5.mLightingInfo.getExtra();
                            boolean z8 = ((extra4 == null || (valueOf = Integer.valueOf(extra4.getInt("package_visiblity"))) == null) ? -1000 : valueOf.intValue()) == 0;
                            int visibility2 = lightingScheduleInfo5.getVisibility();
                            if (isNeedToSanitized || visibility2 == 0 || visibility2 == -1 || z7 || z8) {
                                edgeEffectInfo.mText = new String[]{edgeLightingScheduler5.getAppName(str5), null};
                                if (isScreenOn2 && !Utils.isLargeCoverFlipFolded()) {
                                    Slog.i("EdgeLightingScheduler", "Not showing edgelighting because suppressAwakeHeadsUp is true");
                                }
                            } else {
                                edgeEffectInfo.mText = lightingScheduleInfo5.getNotiText();
                            }
                            stringBuffer3.append("+notiOn");
                            stringBuffer3.append(z7 ? "+hidePriv" : " ");
                            stringBuffer3.append(z8 ? "+hideContentPackageName" : " ");
                            stringBuffer3.append("notiVisibility : ");
                            stringBuffer3.append(visibility2);
                        }
                    } else if (isNeedToSanitized) {
                        edgeEffectInfo.mText = new String[]{edgeLightingScheduler5.getAppName(str5), null};
                    } else {
                        edgeEffectInfo.mText = lightingScheduleInfo5.getNotiText();
                    }
                    PendingIntent contentIntent = lightingScheduleInfo5.getContentIntent();
                    edgeEffectInfo.mIsGrayScaled = ContrastColorUtil.getInstance(edgeLightingScheduler5.mTurnOverEdgeLighting.mContext).isGrayscaleIcon(lightingScheduleInfo5.mIcon);
                    edgeEffectInfo.mEffectColors = EdgeLightingSettingUtils.getLightingColor(edgeLightingScheduler5.mTurnOverEdgeLighting.mContext, lightingScheduleInfo5.getNotiText(), str5, lightingScheduleInfo5.mLightingInfo.getEffectColors());
                    edgeEffectInfo.mPendingIntent = contentIntent;
                    edgeEffectInfo.mNotificationKey = lightingScheduleInfo5.getNotificationKey();
                    edgeLightingScheduler5.mTurnOverEdgeLighting.getClass();
                    edgeEffectInfo.mEdgeLightingAction = true;
                    edgeEffectInfo.mLightingDuration = EdgeLightingSettingUtils.getEdgeLightingDuration(EdgeLightingSettingUtils.loadEdgeLightingDurationOptionType(edgeLightingScheduler5.mTurnOverEdgeLighting.mContext));
                    edgeEffectInfo.mIsMultiResolutionSupoorted = false;
                    edgeEffectInfo.mPackageName = str5;
                    if (edgeLightingScheduler5.mRequester.isUIControllerExist()) {
                        EdgeLightingDispatcher uIController4 = edgeLightingScheduler5.mRequester.getUIController(false);
                        boolean z9 = lightingScheduleInfo5.mIsDirty;
                        EdgeLightingDialog edgeLightingDialog4 = uIController4.mDialog;
                        if (edgeLightingDialog4 == null) {
                            EffectServiceController effectServiceController = uIController4.mEffectServiceConrtroller;
                            effectServiceController.getClass();
                            Slog.i("EffectServiceController", "dispatchUpdate");
                            EffectInfoReflection convertEffectInfo = effectServiceController.convertEffectInfo(edgeEffectInfo);
                            AbsEdgeLightingEffectReflection absEdgeLightingEffectReflection = effectServiceController.mAbsEdgeLightingEffectReflection;
                            try {
                                cls = Class.forName("com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$EffectInfo", true, absEdgeLightingEffectReflection.mClassLoader);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                                cls = null;
                            }
                            absEdgeLightingEffectReflection.invokeNormalMethod(absEdgeLightingEffectReflection.mInstance, "update", new Class[]{cls}, convertEffectInfo.mInstance);
                        } else if (edgeLightingDialog4.isShowing()) {
                            NotificationEffect notificationEffect = edgeLightingDialog4.mNotificationEffect;
                            if (notificationEffect != null) {
                                notificationEffect.setEdgeEffectInfo(edgeEffectInfo);
                                edgeLightingDialog4.mNotificationEffect.updateText(z9);
                                edgeLightingDialog4.mHandler.removeMessages(1);
                            }
                        } else {
                            Slog.i("EdgeLightingDialog", "updateNotification not showing");
                        }
                    }
                    edgeLightingScheduler5.mWakeLock.acquire(lightingScheduleInfo5.getDuration() + 2000);
                    Slog.d("EdgeLightingScheduler", stringBuffer3.toString());
                }
                if (z2) {
                    if (lightingScheduleInfo5.getDuration() == -1) {
                        handlerC136012.removeMessages(0);
                        return;
                    } else {
                        handlerC136012.removeMessages(0);
                        handlerC136012.sendMessageDelayed(handlerC136012.obtainMessage(0, lightingScheduleInfo5.getNotificationKey()), lightingScheduleInfo5.getDuration());
                        return;
                    }
                }
                return;
            }
            DeviceWakeLockManager deviceWakeLockManager2 = DeviceWakeLockManager.getInstance();
            deviceWakeLockManager2.getClass();
            if (str5 != null && !str5.isEmpty()) {
                Slog.d("DeviceWakeLockManager", "setWakeLockPackage : ".concat(str5));
                deviceWakeLockManager2.mWakeLockMap.put(str5, 1);
            }
            if (handlerC136012.hasMessages(0)) {
                handlerC136012.removeMessages(0);
            }
            LightingScheduleInfo lightingScheduleInfo92 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
            equals = str5.equals(lightingScheduleInfo92 != null ? lightingScheduleInfo92.mPackageName : null);
            boolean z42 = NotificationLightingScheduler.DEBUG;
            if (equals) {
            }
        }
    };
    public final C13542 mEdgeLightingObserver = new EdgeLightingSettingsObserver.EdgeLightingObserver() { // from class: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler.2
        @Override // com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.EdgeLightingObserver
        public final Handler getHandler() {
            return EdgeLightingScheduler.this.mHandler;
        }

        @Override // com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.EdgeLightingObserver
        public final void onChange() {
            EdgeLightingScheduler.this.mTurnOverEdgeLighting.setEnable();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$3 */
    public final class C13553 {
        public C13553() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$4 */
    public final class C13564 {
        public C13564() {
        }

        public final void stopNotification(boolean z) {
            EdgeLightingScheduler edgeLightingScheduler = EdgeLightingScheduler.this;
            TurnOverEdgeLighting turnOverEdgeLighting = edgeLightingScheduler.mTurnOverEdgeLighting;
            TurnOverEdgeLighting.ITurnModeState onNotificationEnd = turnOverEdgeLighting.mCurrentTurnMode.onNotificationEnd();
            int mode = turnOverEdgeLighting.mCurrentTurnMode.getMode();
            boolean z2 = true;
            if (mode == 1) {
                turnOverEdgeLighting.mCurrentTurnMode = onNotificationEnd;
            } else if (mode != 2) {
                turnOverEdgeLighting.mCurrentTurnMode = onNotificationEnd;
                z2 = false;
            } else {
                turnOverEdgeLighting.mCurrentTurnMode = onNotificationEnd;
            }
            if (z2) {
                Slog.d("EdgeLightingScheduler", "stopNotification: end with turnover");
            } else {
                Slog.d("EdgeLightingScheduler", "stopNotification");
                if (z) {
                    Slog.d("EdgeLightingScheduler", "stop Notification to turn to heads up");
                    edgeLightingScheduler.mRequester.requestStopService();
                    PowerManager.WakeLock wakeLock = edgeLightingScheduler.mWakeLock;
                    if (wakeLock != null && wakeLock.isHeld()) {
                        edgeLightingScheduler.mWakeLock.release();
                    }
                } else if (edgeLightingScheduler.mRequester.isUIControllerExist()) {
                    edgeLightingScheduler.mRequester.getUIController(false).stopEdgeEffect();
                } else {
                    Slog.d("EdgeLightingScheduler", "stopNotification not exist. so stop service");
                    edgeLightingScheduler.mRequester.requestStopService();
                    PowerManager.WakeLock wakeLock2 = edgeLightingScheduler.mWakeLock;
                    if (wakeLock2 != null && wakeLock2.isHeld()) {
                        edgeLightingScheduler.mWakeLock.release();
                    }
                }
                if (Utils.isLargeCoverFlipFolded()) {
                    EdgeLightingService.C12994 c12994 = edgeLightingScheduler.mRequester;
                    EdgeLightingService edgeLightingService = EdgeLightingService.this;
                    try {
                        if (edgeLightingService.mConditionListener != null && !c12994.isScreenOn()) {
                            edgeLightingService.mConditionListener.requestDozeStateSubScreen(false);
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            edgeLightingScheduler.mTurnOverEdgeLighting.mContext.stopService(new Intent(edgeLightingScheduler.mTurnOverEdgeLighting.mContext, (Class<?>) EdgeLightingForegroundService.class));
            if (edgeLightingScheduler.mOneHandOperationObserver != null) {
                edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver().unregisterContentObserver(edgeLightingScheduler.mOneHandOperationObserver);
                edgeLightingScheduler.mOneHandOperationObserver = null;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$5 */
    public final class C13575 {
        public C13575() {
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
                    EdgeLightingScheduler.m1540$$Nest$mstartNotiEffect(edgeLightingScheduler, true);
                    return;
                }
                return;
            }
            LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy2 = lightingScheduleInfo.mLightingLogicPolicy;
            if (lightingLogicPolicy2 == null) {
                lightingLogicPolicy2 = new LightingScheduleInfo.LightingLogicPolicy();
            }
            if (lightingLogicPolicy2.isNeedLightOnWhenTurnOveredLcdOff) {
                EdgeLightingScheduler.m1540$$Nest$mstartNotiEffect(edgeLightingScheduler, true);
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
                    EdgeLightingScheduler.m1540$$Nest$mstartNotiEffect(edgeLightingScheduler, false);
                    return;
                }
                return;
            }
            EdgeLightingService.C12994 c12994 = edgeLightingScheduler.mRequester;
            c12994.getClass();
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
                EdgeLightingScheduler.m1540$$Nest$mstartNotiEffect(edgeLightingScheduler, false);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$6 */
    public final class C13586 {
        public C13586() {
        }
    }

    /* renamed from: -$$Nest$misNeedToBlockedByPolicy, reason: not valid java name */
    public static boolean m1539$$Nest$misNeedToBlockedByPolicy(EdgeLightingScheduler edgeLightingScheduler, String str, int i) {
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
    public static void m1540$$Nest$mstartNotiEffect(EdgeLightingScheduler edgeLightingScheduler, boolean z) {
        int i;
        Integer valueOf;
        StringBuffer stringBuffer = new StringBuffer("startNotiEffect:  dur=");
        LightingScheduleInfo lightingScheduleInfo = edgeLightingScheduler.mNotificationLightingScheduler.mCurrentLightingScheduleInfo;
        if (lightingScheduleInfo == null) {
            Slog.d("EdgeLightingScheduler", "startNotiEffect: noti info empty");
            return;
        }
        if (Utils.isLargeCoverFlipFolded()) {
            EdgeLightingService.C12994 c12994 = edgeLightingScheduler.mRequester;
            EdgeLightingService edgeLightingService = EdgeLightingService.this;
            try {
                if (edgeLightingService.mConditionListener != null && !c12994.isScreenOn()) {
                    edgeLightingService.mConditionListener.requestDozeStateSubScreen(true);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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
                    boolean z3 = Settings.System.getIntForUser(context.getContentResolver(), "any_screen_running", 0, -2) == 1;
                    Slog.i("EdgeLightingScheduler", " mOneHandOperationObserver value = " + z3);
                    if (!z3 || (notificationLightingScheduler = EdgeLightingScheduler.this.mNotificationLightingScheduler) == null) {
                        return;
                    }
                    notificationLightingScheduler.flushNotiNow();
                }
            };
        }
        edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("any_screen_running"), false, edgeLightingScheduler.mOneHandOperationObserver);
        stringBuffer.append(lightingScheduleInfo.getDuration());
        EdgeEffectInfo edgeEffectInfo = new EdgeEffectInfo();
        edgeEffectInfo.mHasActionButton = lightingScheduleInfo.getActionList() != null;
        Context context = edgeLightingScheduler.mTurnOverEdgeLighting.mContext;
        String[] notiText = lightingScheduleInfo.getNotiText();
        SemEdgeLightingInfo semEdgeLightingInfo = lightingScheduleInfo.mLightingInfo;
        int[] effectColors = semEdgeLightingInfo.getEffectColors();
        String str = lightingScheduleInfo.mPackageName;
        edgeEffectInfo.mEffectColors = EdgeLightingSettingUtils.getLightingColor(context, notiText, str, effectColors);
        edgeEffectInfo.mIsBlackBG = z;
        edgeLightingScheduler.mTurnOverEdgeLighting.getClass();
        edgeEffectInfo.mEdgeLightingAction = true;
        if (!isScreenOn) {
            edgeLightingScheduler.mTurnOverEdgeLighting.mContext.startService(new Intent(edgeLightingScheduler.mTurnOverEdgeLighting.mContext, (Class<?>) EdgeLightingForegroundService.class));
        }
        if (isScreenOn || !z) {
            edgeEffectInfo.mStrokeAlpha = 1.0f - (Settings.System.getIntForUser(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), "edge_lighting_transparency", 0, -2) / 100.0f);
            Context context2 = edgeLightingScheduler.mTurnOverEdgeLighting.mContext;
            float edgeLightingStyleWidth = EdgeLightingSettingUtils.getEdgeLightingStyleWidth(context2, EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver()), Settings.System.getIntForUser(context2.getContentResolver(), "edge_lighting_thickness", 0, -2));
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
            edgeLightingScheduler.mWakeLock.acquire((long) (lightingScheduleInfo.getDuration() + 2000));
        } else {
            boolean isNeedToSanitized = edgeLightingScheduler.mRequester.isNeedToSanitized(lightingScheduleInfo.getUserId(), lightingScheduleInfo.getVisibility(), lightingScheduleInfo.getNotificationKey());
            edgeEffectInfo.mAppIcon = edgeLightingScheduler.getAppIcon(lightingScheduleInfo);
            edgeEffectInfo.mIsSupportAppIcon = edgeLightingScheduler.isSupportAppIcon(str);
            edgeEffectInfo.mShouldShowAppIcon = EdgeLightingService.this.mShouldShowAppIcon;
            edgeEffectInfo.mText = lightingScheduleInfo.getNotiText();
            int preloadIndex = EdgeLightingStyleManager.getInstance().getPreloadIndex(EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver()));
            boolean z2 = Settings.System.getInt(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), "remove_animations", 0) == 1;
            Uri parse = Uri.parse("content://com.samsung.android.systemui.edgelighting.plus.provider");
            if (edgeLightingScheduler.mContext.getContentResolver().acquireContentProviderClient(parse) != null) {
                Bundle bundle = new Bundle();
                String[] strArr = edgeEffectInfo.mText;
                if (strArr != null) {
                    bundle.putString(UniversalCredentialUtil.AGENT_TITLE, strArr[0]);
                    bundle.putString("description", edgeEffectInfo.mText[1]);
                }
                i = preloadIndex;
                edgeEffectInfo.mPlusEffectBundle = edgeLightingScheduler.mContext.getContentResolver().call(parse, "getData()", (String) null, bundle);
            } else {
                i = preloadIndex;
            }
            if (edgeEffectInfo.mPlusEffectBundle != null) {
                edgeEffectInfo.mEffectType = 100;
            } else {
                edgeEffectInfo.mEffectType = z2 ? 0 : i;
            }
            edgeEffectInfo.mPackageName = str;
            edgeEffectInfo.mIsMultiResolutionSupoorted = false;
            edgeEffectInfo.mIsGrayScaled = ContrastColorUtil.getInstance(edgeLightingScheduler.mTurnOverEdgeLighting.mContext).isGrayscaleIcon(lightingScheduleInfo.mIcon);
            EdgeLightingService.C12994 c129942 = edgeLightingScheduler.mRequester;
            c129942.getClass();
            boolean z3 = EdgeLightingService.sConfigured;
            KeyguardManager keyguardManager = (KeyguardManager) EdgeLightingService.this.getSystemService("keyguard");
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                stringBuffer.append("+locked");
                if (Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), "lock_screen_show_notifications", 0, -2) == 1) {
                    boolean z4 = Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), "lock_screen_allow_private_notifications", 1, -2) == 0;
                    Bundle extra = semEdgeLightingInfo.getExtra();
                    boolean z5 = ((extra == null || (valueOf = Integer.valueOf(extra.getInt("package_visiblity"))) == null) ? -1000 : valueOf.intValue()) == 0;
                    int visibility = lightingScheduleInfo.getVisibility();
                    if (isNeedToSanitized || visibility == 0 || visibility == -1 || z4 || z5) {
                        edgeEffectInfo.mText = new String[]{edgeLightingScheduler.getAppName(str), null};
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
            } else if (isNeedToSanitized) {
                edgeEffectInfo.mText = new String[]{edgeLightingScheduler.getAppName(str), null};
            }
            PendingIntent contentIntent = lightingScheduleInfo.getContentIntent();
            if (contentIntent != null) {
                edgeEffectInfo.mPendingIntent = contentIntent;
            }
            edgeEffectInfo.mNotificationKey = lightingScheduleInfo.getNotificationKey();
            edgeLightingScheduler.mRequester.getUIController(false).startEdgeEffect(edgeEffectInfo);
            Slog.d("EdgeLightingScheduler", "EdgeLightingEventStyleInfo," + EdgeLightingSettingUtils.effectInfoToString(edgeEffectInfo, EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver())));
            edgeLightingScheduler.mWakeLock.acquire((long) (lightingScheduleInfo.getDuration() + 2000));
        }
        String text = SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "component");
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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005e, code lost:
    
        r5 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable getAppIcon(LightingScheduleInfo lightingScheduleInfo) {
        Drawable drawable;
        PackageManager packageManager;
        ApplicationInfo applicationInfo;
        String str = lightingScheduleInfo.mPackageName;
        try {
            packageManager = this.mContext.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(str, 4202624);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            drawable = null;
        }
        if (isSupportAppIcon(str)) {
            if (EdgeLightingService.this.mIsColorThemeEnabled) {
                List<LauncherActivityInfo> activityList = ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).getActivityList(str, UserHandle.getUserHandleForUid(applicationInfo.uid));
                drawable = !activityList.isEmpty() ? activityList.get(0).semGetBadgedIconForIconTray(this.mContext.getResources().getDisplayMetrics().densityDpi) : applicationInfo.loadIcon(packageManager);
            } else {
                drawable = applicationInfo.loadIcon(packageManager);
            }
            return drawable != null ? lightingScheduleInfo.mIcon : drawable;
        }
        drawable = null;
        if (drawable != null) {
        }
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

    public final boolean isSupportAppIcon(String str) {
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 4202624);
            if (str.equals("android") || str.equals("com.android.systemui") || applicationInfo.icon == 0) {
                return false;
            }
            return EdgeLightingService.this.mShouldShowAppIcon;
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
        NotificationLightingScheduler.HandlerC13601 handlerC13601 = notificationLightingScheduler.mNotificationScheduleHandler;
        if (handlerC13601.hasMessages(0)) {
            return;
        }
        handlerC13601.sendMessageDelayed(handlerC13601.obtainMessage(0, notificationLightingScheduler.mCurrentLightingScheduleInfo.getNotificationKey()), 4000L);
    }
}
