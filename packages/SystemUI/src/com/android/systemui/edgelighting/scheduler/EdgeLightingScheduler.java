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
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.view.accessibility.AccessibilityManager;
import android.widget.RelativeLayout;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.internal.util.ContrastColorUtil;
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
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.edge.SemEdgeManager;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.util.SemLog;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        /* JADX WARN: Removed duplicated region for block: B:161:0x03df  */
        /* JADX WARN: Removed duplicated region for block: B:186:0x0442  */
        /* JADX WARN: Removed duplicated region for block: B:188:0x0448  */
        /* JADX WARN: Removed duplicated region for block: B:203:0x0476  */
        /* JADX WARN: Removed duplicated region for block: B:419:0x0987  */
        /* JADX WARN: Removed duplicated region for block: B:72:0x019e  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void handleMessage(Message message) throws NoSuchMethodException, ClassNotFoundException, SecurityException {
            int i;
            HashMap map;
            boolean z;
            boolean zIsOnGoing;
            ArrayList parcelableArrayList;
            Class<?> cls;
            Bundle extra;
            boolean z2;
            AccessibilityManager accessibilityManager;
            int recommendedTimeoutMillis;
            int i2;
            super.handleMessage(message);
            int i3 = message.what;
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
                    NotificationLightingScheduler.AnonymousClass1 anonymousClass1 = notificationLightingScheduler.mNotificationScheduleHandler;
                    if (i4 == 4 && anonymousClass1.hasMessages(0)) {
                        Slog.d("NotificationLightingScheduler", "removeLighting: return by reason" + i4);
                        return;
                    }
                    LightingScheduleInfo lightingScheduleInfo2 = notificationLightingScheduler.mCurrentLightingScheduleInfo;
                    String notificationKey2 = lightingScheduleInfo2 != null ? lightingScheduleInfo2.getNotificationKey() : null;
                    StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i4, "removeLighting: ", str, " reason=", " cur=");
                    sbM890m.append(notificationKey2);
                    Slog.d("NotificationLightingScheduler", sbM890m.toString());
                    if (!notificationKey.equals(notificationKey2)) {
                        if (anonymousClass1.hasEqualMessages(0, notificationKey)) {
                            anonymousClass1.removeEqualMessages(0, notificationKey);
                            anonymousClass1.sendMessage(anonymousClass1.obtainMessage(0, notificationKey));
                            return;
                        }
                        return;
                    }
                    LightingScheduleInfo lightingScheduleInfo3 = notificationLightingScheduler.mCurrentLightingScheduleInfo;
                    if (lightingScheduleInfo3 != null) {
                        lightingScheduleInfo3.mReason = 1;
                        lightingScheduleInfo3.setDuration(ImsProfile.DEFAULT_DEREG_TIMEOUT);
                    }
                    anonymousClass1.removeMessages(0);
                    anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0, notificationKey), 0L);
                    return;
                }
                ApplicationLightingScheduler applicationLightingScheduler = edgeLightingScheduler.mApplicationLightingScheduler;
                if (applicationLightingScheduler != null) {
                    String str2 = lightingScheduleInfo.mPackageName;
                    if (str2 == null) {
                        Slog.d("ApplicationLightingScheduler", "removeLighting: invalid param " + str2);
                    }
                    synchronized (applicationLightingScheduler.mLinkedInfo) {
                        try {
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
                                            i2 = effectColors[0] | (-16777216);
                                            int i5 = effectColors[1];
                                            if (i5 != 0) {
                                                applicationEffect3.mLightEffectView.mSubColor = i5;
                                            }
                                        } else {
                                            i2 = -15750429;
                                        }
                                        applicationEffect3.mLightEffectView.setMainColor(i2);
                                        applicationEffect3.mLightEffectView.mIsAnimating = false;
                                        ApplicationEffect applicationEffect4 = edgeLightingDialog.mApplicationEffect;
                                        applicationEffect4.setVisibility(0);
                                        applicationEffect4.containerAlphaAnimation(0.0f, 1.0f);
                                        final EdgeLightAppEffectView edgeLightAppEffectView = applicationEffect4.mLightEffectView;
                                        if (!edgeLightAppEffectView.mIsAnimating) {
                                            edgeLightAppEffectView.mIsAnimating = true;
                                            if (edgeLightAppEffectView.mTopLayer.getDrawable() == null) {
                                                edgeLightAppEffectView.mTopLayer.setImageResource(R.drawable.music_gradient);
                                            }
                                            if (edgeLightAppEffectView.mBottomLayer.getDrawable() == null) {
                                                edgeLightAppEffectView.mBottomLayer.setImageResource(R.drawable.music_gradient);
                                            }
                                            edgeLightAppEffectView.startRotation(edgeLightAppEffectView.mRotateDuration);
                                            AbsEdgeLightingMaskView.changeRingImageAlpha(edgeLightAppEffectView.mContainer, edgeLightAppEffectView.mStrokeAlpha, edgeLightAppEffectView.lineDuration * 3);
                                            int i6 = edgeLightAppEffectView.mSubColor;
                                            if (i6 != 0) {
                                                int i7 = edgeLightAppEffectView.mMainColor;
                                                ValueAnimator valueAnimator = edgeLightAppEffectView.repeatColorAnimation;
                                                if (valueAnimator != null) {
                                                    valueAnimator.cancel();
                                                }
                                                SemLog.i(edgeLightAppEffectView.TAG, "repeat Color Animation from : " + i7 + " toColor " + i6);
                                                ValueAnimator valueAnimatorOfArgb = ValueAnimator.ofArgb(i7, i6);
                                                edgeLightAppEffectView.repeatColorAnimation = valueAnimatorOfArgb;
                                                valueAnimatorOfArgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.EdgeLightAppEffectView.1
                                                    public AnonymousClass1() {
                                                    }

                                                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                                        EdgeLightAppEffectView.this.setMainColor(((Integer) valueAnimator2.getAnimatedValue()).intValue());
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
                        } finally {
                        }
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
                        try {
                            applicationLightingScheduler2.mLinkedInfo.put(lightingScheduleInfo5.mPackageName, lightingScheduleInfo5);
                            if (applicationLightingScheduler2.mListener == null) {
                                Slog.d("ApplicationLightingScheduler", "putLighting: no listener");
                                return;
                            }
                            if (applicationLightingScheduler2.mLinkedInfo.entrySet().iterator().hasNext() && lightingScheduleInfo5.mPackageName.equals(((Map.Entry) applicationLightingScheduler2.mLinkedInfo.entrySet().iterator().next()).getKey())) {
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
                                            i = effectColors2[0] | (-16777216);
                                            int i8 = effectColors2[1];
                                            if (i8 != 0) {
                                                applicationEffect8.mLightEffectView.mSubColor = i8;
                                            }
                                        } else {
                                            i = -15750429;
                                        }
                                        applicationEffect8.mLightEffectView.setMainColor(i);
                                        applicationEffect8.mLightEffectView.mIsAnimating = false;
                                        ApplicationEffect applicationEffect9 = edgeLightingDialog3.mApplicationEffect;
                                        applicationEffect9.setVisibility(0);
                                        applicationEffect9.containerAlphaAnimation(0.0f, 1.0f);
                                        final EdgeLightAppEffectView edgeLightAppEffectView2 = applicationEffect9.mLightEffectView;
                                        if (!edgeLightAppEffectView2.mIsAnimating) {
                                            edgeLightAppEffectView2.mIsAnimating = true;
                                            if (edgeLightAppEffectView2.mTopLayer.getDrawable() == null) {
                                                edgeLightAppEffectView2.mTopLayer.setImageResource(R.drawable.music_gradient);
                                            }
                                            if (edgeLightAppEffectView2.mBottomLayer.getDrawable() == null) {
                                                edgeLightAppEffectView2.mBottomLayer.setImageResource(R.drawable.music_gradient);
                                            }
                                            edgeLightAppEffectView2.startRotation(edgeLightAppEffectView2.mRotateDuration);
                                            AbsEdgeLightingMaskView.changeRingImageAlpha(edgeLightAppEffectView2.mContainer, edgeLightAppEffectView2.mStrokeAlpha, edgeLightAppEffectView2.lineDuration * 3);
                                            int i9 = edgeLightAppEffectView2.mSubColor;
                                            if (i9 != 0) {
                                                int i10 = edgeLightAppEffectView2.mMainColor;
                                                ValueAnimator valueAnimator2 = edgeLightAppEffectView2.repeatColorAnimation;
                                                if (valueAnimator2 != null) {
                                                    valueAnimator2.cancel();
                                                }
                                                SemLog.i(edgeLightAppEffectView2.TAG, "repeat Color Animation from : " + i10 + " toColor " + i9);
                                                ValueAnimator valueAnimatorOfArgb2 = ValueAnimator.ofArgb(i10, i9);
                                                edgeLightAppEffectView2.repeatColorAnimation = valueAnimatorOfArgb2;
                                                valueAnimatorOfArgb2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.EdgeLightAppEffectView.1
                                                    public AnonymousClass1() {
                                                    }

                                                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                    public final void onAnimationUpdate(ValueAnimator valueAnimator22) {
                                                        EdgeLightAppEffectView.this.setMainColor(((Integer) valueAnimator22.getAnimatedValue()).intValue());
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
                            }
                            return;
                        } finally {
                        }
                    }
                }
                return;
            }
            if (lightingScheduleInfo5.getDuration() != -1 && edgeLightingScheduler3.mRequester.isScreenOn() && (accessibilityManager = (AccessibilityManager) edgeLightingScheduler3.mTurnOverEdgeLighting.mContext.getSystemService("accessibility")) != null && (recommendedTimeoutMillis = accessibilityManager.getRecommendedTimeoutMillis(lightingScheduleInfo5.mDuration, 4)) != 0) {
                lightingScheduleInfo5.setDuration(recommendedTimeoutMillis);
                Slog.d("LightingScheduleInfo", "updateTimeToTakeAction time=" + recommendedTimeoutMillis);
            }
            NotificationLightingScheduler notificationLightingScheduler2 = edgeLightingScheduler3.mNotificationLightingScheduler;
            boolean zIsScreenOn = edgeLightingScheduler3.mRequester.isScreenOn();
            Context context = edgeLightingScheduler3.mTurnOverEdgeLighting.mContext;
            String str3 = lightingScheduleInfo5.mPackageName;
            if (SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo5.mLightingInfo) || (map = (HashMap) EdgeLightingPolicyManager.getInstance(context.getApplicationContext(), false).mPolicyInfoData.get(11)) == null || !map.containsKey(str3)) {
                z = true;
            } else {
                Slog.d("EdgeLightingScheduler", "don't need keep notificaton (" + str3 + ")");
                z = false;
            }
            LightingScheduleInfo lightingScheduleInfo6 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
            String str4 = lightingScheduleInfo6 != null ? lightingScheduleInfo6.mPackageName : null;
            String str5 = lightingScheduleInfo5.mPackageName;
            if (z) {
                zIsOnGoing = true;
            } else {
                NotificationLightingScheduler.EdgeLightingDataKeeper edgeLightingDataKeeper = notificationLightingScheduler2.mEdgeLightingDataKeeper;
                edgeLightingDataKeeper.getClass();
                Slog.i("NotificationLightingScheduler", " getOldLightingInfo " + str5);
                zIsOnGoing = SemEdgeLightingInfoUtils.isOnGoing(edgeLightingDataKeeper.mNotificationMap.containsKey(str5) ? ((NotificationLightingScheduler.EdgeLightingDataKeeper.SemEdgeLightingInfoData) edgeLightingDataKeeper.mNotificationMap.get(str5)).mEdgeLightingInfo : null);
            }
            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("putLighting: ", str5, " reason=");
            sbM.append(lightingScheduleInfo5.mReason);
            sbM.append(" cur=");
            sbM.append(str4);
            sbM.append(", isNeedKeepPackage=");
            sbM.append(z);
            sbM.append(", isNeedKeepNoti=");
            sbM.append(zIsOnGoing);
            Slog.d("NotificationLightingScheduler", sbM.toString());
            if (notificationLightingScheduler2.mListener == null) {
                Slog.d("NotificationLightingScheduler", "putLighting: no listener");
                return;
            }
            if (notificationLightingScheduler2.mCurrentLightingScheduleInfo != null) {
                LightingScheduleInfo.LightingLogicPolicy lightingLogicPolicy = lightingScheduleInfo5.mLightingLogicPolicy;
                if (lightingLogicPolicy == null) {
                    lightingLogicPolicy = new LightingScheduleInfo.LightingLogicPolicy();
                }
                if (lightingLogicPolicy.isNeedToKeepWhenLcdOff && !zIsScreenOn) {
                    Slog.d("NotificationLightingScheduler", "putLighting: mCurrentLightingScheduleInfo= " + SemEdgeLightingInfoUtils.toString(notificationLightingScheduler2.mCurrentLightingScheduleInfo.mLightingInfo) + ",new=" + SemEdgeLightingInfoUtils.toString(lightingScheduleInfo5.mLightingInfo));
                    LightingScheduleInfo lightingScheduleInfo7 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
                    if ((lightingScheduleInfo7 == null || lightingScheduleInfo5.mLightingInfo == null || lightingScheduleInfo7.mLightingInfo == null) ? false : TextUtils.equals(str5, lightingScheduleInfo7.mPackageName)) {
                        lightingScheduleInfo7.getClass();
                    }
                    LightingScheduleInfo lightingScheduleInfo8 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
                    if (lightingScheduleInfo8 != null && SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo8.mLightingInfo) && SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo5.mLightingInfo)) {
                        SemEdgeLightingInfo semEdgeLightingInfo = notificationLightingScheduler2.mCurrentLightingScheduleInfo.mLightingInfo;
                        SemEdgeLightingInfo semEdgeLightingInfo2 = lightingScheduleInfo5.mLightingInfo;
                        if ((semEdgeLightingInfo == null && semEdgeLightingInfo2 == null) || (semEdgeLightingInfo != null && semEdgeLightingInfo2 != null && TextUtils.equals(SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "tickerText"), SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo2, "tickerText")) && TextUtils.equals(SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "text"), SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo2, "text")) && TextUtils.equals(SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "text"), SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo2, "text")) && TextUtils.equals(SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo, "subText"), SemEdgeLightingInfoUtils.getText(semEdgeLightingInfo2, "subText")))) {
                            z2 = true;
                        }
                        if (!z2) {
                        }
                    } else {
                        z2 = false;
                        if (!z2) {
                            Slog.d("NotificationLightingScheduler", "putLighting: skip by isDuplicatedOnGoing");
                            return;
                        } else if (SemEdgeLightingInfoUtils.isOnGoing(notificationLightingScheduler2.mCurrentLightingScheduleInfo.mLightingInfo) && !SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo5.mLightingInfo)) {
                            Slog.d("NotificationLightingScheduler", "putLighting: skip by isOnGoing notification showing");
                            return;
                        }
                    }
                }
            }
            int i11 = lightingScheduleInfo5.mReason;
            NotificationLightingScheduler.AnonymousClass1 anonymousClass12 = notificationLightingScheduler2.mNotificationScheduleHandler;
            if (i11 == 4) {
                DeviceWakeLockManager deviceWakeLockManager2 = DeviceWakeLockManager.getInstance();
                deviceWakeLockManager2.getClass();
                if (str5 != null && !str5.isEmpty()) {
                    Slog.d("DeviceWakeLockManager", "setWakeLockPackage : ".concat(str5));
                    deviceWakeLockManager2.mWakeLockMap.put(str5, 1);
                }
                if (anonymousClass12.hasMessages(0)) {
                    anonymousClass12.removeMessages(0);
                }
            } else if (i11 != 5) {
                if (i11 == 6) {
                }
            } else if (anonymousClass12.hasMessages(0)) {
                anonymousClass12.removeMessages(0);
            }
            LightingScheduleInfo lightingScheduleInfo9 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
            boolean zEquals = str5.equals(lightingScheduleInfo9 != null ? lightingScheduleInfo9.mPackageName : null);
            boolean z3 = NotificationLightingScheduler.DEBUG;
            if (!zEquals) {
                if (z3) {
                    Slog.d("NotificationLightingScheduler", "replaceToNewNoti : " + SemEdgeLightingInfoUtils.toString(lightingScheduleInfo5.mLightingInfo));
                }
                if (notificationLightingScheduler2.mCurrentLightingScheduleInfo != null) {
                    anonymousClass12.removeMessages(0);
                    notificationLightingScheduler2.expireNotiLighting(notificationLightingScheduler2.mCurrentLightingScheduleInfo.getNotificationKey());
                }
                notificationLightingScheduler2.mCurrentLightingScheduleInfo = lightingScheduleInfo5;
                AnonymousClass4 anonymousClass4 = notificationLightingScheduler2.mListener;
                anonymousClass4.getClass();
                StringBuffer stringBuffer = new StringBuffer("startNotification: ");
                stringBuffer.append(str5);
                int i12 = lightingScheduleInfo5.mReason;
                EdgeLightingScheduler edgeLightingScheduler4 = EdgeLightingScheduler.this;
                if (EdgeLightingScheduler.m2574$$Nest$misNeedToBlockedByPolicy(edgeLightingScheduler4, str5, i12)) {
                    stringBuffer.append(" +isBlockedByPolicy");
                    Slog.d("EdgeLightingScheduler", stringBuffer.toString());
                    edgeLightingScheduler4.mNotificationLightingScheduler.flushNotiNow();
                } else {
                    TurnOverEdgeLighting turnOverEdgeLighting = edgeLightingScheduler4.mTurnOverEdgeLighting;
                    TurnOverEdgeLighting.StateIdle stateIdle = turnOverEdgeLighting.mCurrentTurnMode;
                    stateIdle.onNotification();
                    int mode = stateIdle.getMode();
                    if (mode != 1) {
                        if (mode != 2) {
                            turnOverEdgeLighting.mCurrentTurnMode = stateIdle;
                            EdgeLightingScheduler.m2575$$Nest$mstartNotiEffect(edgeLightingScheduler4, false);
                        }
                        Slog.d("EdgeLightingScheduler", stringBuffer.toString());
                    } else {
                        turnOverEdgeLighting.mCurrentTurnMode = stateIdle;
                    }
                    stringBuffer.append(" +ShowWithTurnOver");
                    Slog.d("EdgeLightingScheduler", stringBuffer.toString());
                }
                if (lightingScheduleInfo5.getDuration() != -1) {
                    anonymousClass12.sendMessageDelayed(anonymousClass12.obtainMessage(0, lightingScheduleInfo5.getNotificationKey()), lightingScheduleInfo5.getDuration());
                    return;
                }
                return;
            }
            LightingScheduleInfo lightingScheduleInfo10 = notificationLightingScheduler2.mCurrentLightingScheduleInfo;
            if (lightingScheduleInfo10 != null && str5.equals(lightingScheduleInfo10.mPackageName)) {
                int visibility = lightingScheduleInfo10.getVisibility();
                boolean z4 = LightingScheduleInfo.DEBUG;
                if (z4) {
                    Slog.i("LightingScheduleInfo", " getVisibility : " + lightingScheduleInfo5.getVisibility() + " preVisibility : " + visibility + " getReason : " + lightingScheduleInfo5.mReason);
                }
                if ((visibility == 0 || visibility == 1) && lightingScheduleInfo5.mReason != 1) {
                    if (lightingScheduleInfo5.mLightingInfo.getExtra() == null) {
                        lightingScheduleInfo5.mLightingInfo.setExtra(new Bundle());
                    }
                    Bundle extra2 = lightingScheduleInfo5.mLightingInfo.getExtra();
                    if (extra2 != null) {
                        extra2.putInt("noti_visiblity", visibility);
                    }
                }
                int i13 = lightingScheduleInfo10.mReason;
                if (i13 == 4 || i13 == 6) {
                    lightingScheduleInfo5.mReason = i13;
                }
                lightingScheduleInfo5.mNotiTextPolicyChain.mergeText(lightingScheduleInfo10);
                boolean zIsTextDirty = lightingScheduleInfo5.mNotiTextPolicyChain.isTextDirty();
                lightingScheduleInfo5.mIsDirty = zIsTextDirty;
                if (!zIsTextDirty && SemEdgeLightingInfoUtils.isOnGoing(lightingScheduleInfo10.mLightingInfo)) {
                    Bundle extra3 = lightingScheduleInfo5.mLightingInfo.getExtra();
                    if (extra3 == null) {
                        extra3 = new Bundle();
                    }
                    extra3.putInt("flag", extra3.getInt("flag") | 2);
                    lightingScheduleInfo5.mLightingInfo.setExtra(extra3);
                }
                if (lightingScheduleInfo10.getContentIntent() != null && lightingScheduleInfo5.getContentIntent() == null && (extra = lightingScheduleInfo5.mLightingInfo.getExtra()) != null) {
                    extra.putParcelable("content_intent", lightingScheduleInfo10.getContentIntent());
                }
                if (z4) {
                    StringBuffer stringBuffer2 = new StringBuffer("mergeInfo tick=");
                    stringBuffer2.append(Arrays.toString(lightingScheduleInfo5.mNotiTextPolicyChain.getChainText()));
                    stringBuffer2.append(" dirty=");
                    stringBuffer2.append(lightingScheduleInfo5.mIsDirty);
                    stringBuffer2.append(" vis=");
                    stringBuffer2.append(lightingScheduleInfo5.getVisibility());
                    Slog.d("LightingScheduleInfo", stringBuffer2.toString());
                }
            }
            if (z3) {
                Slog.d("NotificationLightingScheduler", "updateCurrentNoti : " + SemEdgeLightingInfoUtils.toString(lightingScheduleInfo5.mLightingInfo));
            }
            notificationLightingScheduler2.mCurrentLightingScheduleInfo = lightingScheduleInfo5;
            EdgeLightingScheduler edgeLightingScheduler5 = EdgeLightingScheduler.this;
            if (edgeLightingScheduler5.mTurnOverEdgeLighting.mIsUpsideDown == 1) {
                Slog.d("EdgeLightingScheduler", "updateText: restart edge lighting for turn over");
                EdgeLightingScheduler.m2575$$Nest$mstartNotiEffect(edgeLightingScheduler5, true);
                return;
            }
            if (EdgeLightingScheduler.m2574$$Nest$misNeedToBlockedByPolicy(edgeLightingScheduler5, str5, lightingScheduleInfo5.mReason)) {
                Slog.d("EdgeLightingScheduler", "updateText: skip by Blocking Policy");
            } else {
                StringBuffer stringBuffer3 = new StringBuffer("updateNotiText: isDirty = ");
                stringBuffer3.append(lightingScheduleInfo5.mIsDirty);
                boolean zIsScreenOn2 = edgeLightingScheduler5.mRequester.isScreenOn();
                EdgeEffectInfo edgeEffectInfo = new EdgeEffectInfo();
                Bundle extra4 = lightingScheduleInfo5.mLightingInfo.getExtra();
                if (extra4 == null || (parcelableArrayList = extra4.getParcelableArrayList("noti_actions")) == null) {
                    parcelableArrayList = null;
                }
                edgeEffectInfo.mHasActionButton = parcelableArrayList != null;
                stringBuffer3.append(" dur=");
                stringBuffer3.append(lightingScheduleInfo5.getDuration());
                boolean zIsSensitiveStateActive = edgeLightingScheduler5.mRequester.isSensitiveStateActive();
                boolean zIsNeedToSanitized = edgeLightingScheduler5.mRequester.isNeedToSanitized(lightingScheduleInfo5.getUserId(), lightingScheduleInfo5.getVisibility(), lightingScheduleInfo5.getNotificationKey());
                boolean zShouldHideNotiForAppLockByPackage = edgeLightingScheduler5.mRequester.isSupportAppLock() ? edgeLightingScheduler5.mRequester.isAppLockEnabled() : false ? edgeLightingScheduler5.mRequester.shouldHideNotiForAppLockByPackage(str5) : false;
                edgeEffectInfo.mAppIcon = edgeLightingScheduler5.getAppIcon(lightingScheduleInfo5);
                edgeEffectInfo.mIsSmallIcon = edgeLightingScheduler5.isSmallIcon(str5, lightingScheduleInfo5.mLightingInfo.getExtra().getBoolean("show_small_icon", false));
                EdgeLightingService edgeLightingService = EdgeLightingService.this;
                edgeEffectInfo.mIsUsingAppIcon = edgeLightingService.mIsUsingAppIcon;
                boolean z5 = EdgeLightingService.sConfigured;
                KeyguardManager keyguardManager = (KeyguardManager) edgeLightingService.getSystemService("keyguard");
                if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                    stringBuffer3.append("+locked");
                    if (Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 0, -2) == 1) {
                        boolean z6 = Settings.Secure.getIntForUser(EdgeLightingService.this.getContentResolver(), "lock_screen_allow_private_notifications", 1, -2) == 0;
                        boolean z7 = lightingScheduleInfo5.getPackageVisibility() == 0;
                        if (lightingScheduleInfo5.getPackageVisibility() == -1000) {
                            z7 = z6;
                        }
                        int visibility2 = lightingScheduleInfo5.getVisibility();
                        if (zIsNeedToSanitized || visibility2 == 0 || visibility2 == -1 || z7 || zIsSensitiveStateActive || zShouldHideNotiForAppLockByPackage) {
                            edgeEffectInfo.mText = new String[]{edgeLightingScheduler5.getAppName(lightingScheduleInfo5.getUserId(), str5), null};
                            if (zIsScreenOn2 && !Utils.isLargeCoverFlipFolded()) {
                                Slog.i("EdgeLightingScheduler", "Not showing edgelighting because suppressAwakeHeadsUp is true");
                            }
                        } else {
                            edgeEffectInfo.mText = lightingScheduleInfo5.getNotiText();
                        }
                        stringBuffer3.append("+notiOn");
                        stringBuffer3.append(z6 ? "+hidePriv" : " ");
                        stringBuffer3.append(z7 ? "+hideContentPackageName" : " ");
                        stringBuffer3.append("notiVisibility : ");
                        stringBuffer3.append(visibility2);
                    }
                } else if (zIsNeedToSanitized || zIsSensitiveStateActive || zShouldHideNotiForAppLockByPackage) {
                    edgeEffectInfo.mText = new String[]{edgeLightingScheduler5.getAppName(lightingScheduleInfo5.getUserId(), str5), null};
                } else {
                    edgeEffectInfo.mText = lightingScheduleInfo5.getNotiText();
                }
                PendingIntent contentIntent = lightingScheduleInfo5.getContentIntent();
                edgeEffectInfo.mIsGrayScaled = ContrastColorUtil.getInstance(edgeLightingScheduler5.mTurnOverEdgeLighting.mContext).isGrayscaleIcon(lightingScheduleInfo5.mIcon);
                edgeEffectInfo.mEffectColors = EdgeLightingSettingUtils.getLightingColor(edgeLightingScheduler5.mTurnOverEdgeLighting.mContext, lightingScheduleInfo5.getNotiText(), str5, lightingScheduleInfo5.mLightingInfo.getEffectColors());
                edgeEffectInfo.mPendingIntent = contentIntent;
                edgeEffectInfo.mNotificationKey = lightingScheduleInfo5.getNotificationKey();
                edgeLightingScheduler5.mTurnOverEdgeLighting.getClass();
                edgeEffectInfo.mLightingDuration = EdgeLightingSettingUtils.getEdgeLightingDuration(EdgeLightingSettingUtils.loadEdgeLightingDurationOptionType(edgeLightingScheduler5.mTurnOverEdgeLighting.mContext));
                edgeEffectInfo.mIsMultiResolutionSupoorted = true;
                edgeEffectInfo.mPackageName = str5;
                if (edgeLightingScheduler5.mRequester.isUIControllerExist()) {
                    EdgeLightingDispatcher uIController4 = edgeLightingScheduler5.mRequester.getUIController(false);
                    boolean z8 = lightingScheduleInfo5.mIsDirty;
                    EdgeLightingDialog edgeLightingDialog4 = uIController4.mDialog;
                    if (edgeLightingDialog4 == null) {
                        EffectServiceController effectServiceController = uIController4.mEffectServiceConrtroller;
                        effectServiceController.getClass();
                        Slog.i("EffectServiceController", "dispatchUpdate");
                        EffectInfoReflection effectInfoReflectionConvertEffectInfo = effectServiceController.convertEffectInfo(edgeEffectInfo);
                        AbsEdgeLightingEffectReflection absEdgeLightingEffectReflection = effectServiceController.mAbsEdgeLightingEffectReflection;
                        try {
                            cls = Class.forName("com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$EffectInfo", true, absEdgeLightingEffectReflection.mClassLoader);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            cls = null;
                        }
                        absEdgeLightingEffectReflection.invokeNormalMethod(absEdgeLightingEffectReflection.mInstance, "update", new Class[]{cls}, effectInfoReflectionConvertEffectInfo.mInstance);
                    } else if (edgeLightingDialog4.isShowing()) {
                        NotificationEffect notificationEffect = edgeLightingDialog4.mNotificationEffect;
                        if (notificationEffect != null) {
                            notificationEffect.setEdgeEffectInfo(edgeEffectInfo);
                            edgeLightingDialog4.mNotificationEffect.updateText(z8);
                            edgeLightingDialog4.mHandler.removeMessages(1);
                        }
                    } else {
                        Slog.i("EdgeLightingDialog", "updateNotification not showing");
                    }
                }
                long duration = lightingScheduleInfo5.getDuration() + 2000;
                PowerManager.WakeLock wakeLock = edgeLightingScheduler5.mWakeLock;
                if (wakeLock != null) {
                    wakeLock.acquire(duration);
                }
                PowerManager.WakeLock wakeLock2 = edgeLightingScheduler5.mDrawWakeLock;
                if (wakeLock2 != null) {
                    wakeLock2.acquire(duration);
                }
                Slog.d("EdgeLightingScheduler", stringBuffer3.toString());
            }
            if (lightingScheduleInfo5.getDuration() == -1) {
                anonymousClass12.removeMessages(0);
            } else {
                anonymousClass12.removeMessages(0);
                anonymousClass12.sendMessageDelayed(anonymousClass12.obtainMessage(0, lightingScheduleInfo5.getNotificationKey()), lightingScheduleInfo5.getDuration());
            }
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

    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$3, reason: invalid class name */
    public class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    /* renamed from: com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$4, reason: invalid class name */
    public class AnonymousClass4 {
        public AnonymousClass4() {
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x007e  */
        /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void stopNotification(boolean z) {
            EdgeLightingScheduler edgeLightingScheduler = EdgeLightingScheduler.this;
            TurnOverEdgeLighting turnOverEdgeLighting = edgeLightingScheduler.mTurnOverEdgeLighting;
            TurnOverEdgeLighting.StateIdle stateIdle = turnOverEdgeLighting.mCurrentTurnMode;
            stateIdle.getClass();
            int mode = turnOverEdgeLighting.mCurrentTurnMode.getMode();
            if (mode == 1 || mode == 2) {
                turnOverEdgeLighting.mCurrentTurnMode = stateIdle;
                Slog.d("EdgeLightingScheduler", "stopNotification: end with turnover");
                edgeLightingScheduler.mTurnOverEdgeLighting.mContext.stopService(new Intent(edgeLightingScheduler.mTurnOverEdgeLighting.mContext, (Class<?>) EdgeLightingForegroundService.class));
                if (edgeLightingScheduler.mOneHandOperationObserver == null) {
                    edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver().unregisterContentObserver(edgeLightingScheduler.mOneHandOperationObserver);
                    edgeLightingScheduler.mOneHandOperationObserver = null;
                    return;
                }
                return;
            }
            turnOverEdgeLighting.mCurrentTurnMode = stateIdle;
            Slog.d("EdgeLightingScheduler", "stopNotification");
            if (z) {
                Slog.d("EdgeLightingScheduler", "stop Notification to turn to heads up");
                edgeLightingScheduler.mRequester.requestStopService();
                edgeLightingScheduler.releaseWakeLock();
            } else if (edgeLightingScheduler.mRequester.isUIControllerExist()) {
                edgeLightingScheduler.mRequester.getUIController(false).stopEdgeEffect();
            } else {
                Slog.d("EdgeLightingScheduler", "stopNotification not exist. so stop service");
                edgeLightingScheduler.mRequester.requestStopService();
                edgeLightingScheduler.releaseWakeLock();
            }
            if (Utils.isLargeCoverFlipFolded()) {
                edgeLightingScheduler.mRequester.requestDozeStateSubScreen(false);
            }
            edgeLightingScheduler.mTurnOverEdgeLighting.mContext.stopService(new Intent(edgeLightingScheduler.mTurnOverEdgeLighting.mContext, (Class<?>) EdgeLightingForegroundService.class));
            if (edgeLightingScheduler.mOneHandOperationObserver == null) {
            }
        }
    }

    /* renamed from: -$$Nest$misNeedToBlockedByPolicy, reason: not valid java name */
    public static boolean m2574$$Nest$misNeedToBlockedByPolicy(EdgeLightingScheduler edgeLightingScheduler, String str, int i) {
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
    public static void m2575$$Nest$mstartNotiEffect(EdgeLightingScheduler edgeLightingScheduler, boolean z) {
        ArrayList parcelableArrayList;
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
        boolean zIsScreenOn = edgeLightingScheduler.mRequester.isScreenOn();
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
        if (extra == null || (parcelableArrayList = extra.getParcelableArrayList("noti_actions")) == null) {
            parcelableArrayList = null;
        }
        edgeEffectInfo.mHasActionButton = parcelableArrayList != null;
        Context context = edgeLightingScheduler.mTurnOverEdgeLighting.mContext;
        String[] notiText = lightingScheduleInfo.getNotiText();
        int[] effectColors = lightingScheduleInfo.mLightingInfo.getEffectColors();
        String str = lightingScheduleInfo.mPackageName;
        edgeEffectInfo.mEffectColors = EdgeLightingSettingUtils.getLightingColor(context, notiText, str, effectColors);
        edgeEffectInfo.mIsBlackBG = z;
        edgeLightingScheduler.mTurnOverEdgeLighting.getClass();
        if (!zIsScreenOn) {
            edgeLightingScheduler.mTurnOverEdgeLighting.mContext.startService(new Intent(edgeLightingScheduler.mTurnOverEdgeLighting.mContext, (Class<?>) EdgeLightingForegroundService.class));
        }
        if (zIsScreenOn || !z) {
            edgeEffectInfo.mStrokeAlpha = 1.0f - (Settings.System.getIntForUser(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), "edge_lighting_transparency", 0, -2) / 100.0f);
            Context context2 = edgeLightingScheduler.mTurnOverEdgeLighting.mContext;
            EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver());
            float edgeLightingStyleWidth = EdgeLightingSettingUtils.getEdgeLightingStyleWidth(Settings.System.getIntForUser(context2.getContentResolver(), "edge_lighting_thickness", 0, -2), context2);
            int intForUser = Settings.System.getIntForUser(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), "edge_lighting_thickness", 0, -2);
            edgeEffectInfo.mStrokeWidth = edgeLightingStyleWidth;
            edgeEffectInfo.mWidthDepth = intForUser;
            edgeEffectInfo.mLightingDuration = EdgeLightingSettingUtils.getEdgeLightingDuration(EdgeLightingSettingUtils.loadEdgeLightingDurationOptionType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext));
            if (zIsScreenOn) {
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
            boolean zIsSensitiveStateActive = edgeLightingScheduler.mRequester.isSensitiveStateActive();
            boolean zIsNeedToSanitized = edgeLightingScheduler.mRequester.isNeedToSanitized(lightingScheduleInfo.getUserId(), lightingScheduleInfo.getVisibility(), lightingScheduleInfo.getNotificationKey());
            boolean zShouldHideNotiForAppLockByPackage = edgeLightingScheduler.mRequester.isSupportAppLock() ? edgeLightingScheduler.mRequester.isAppLockEnabled() : false ? edgeLightingScheduler.mRequester.shouldHideNotiForAppLockByPackage(str) : false;
            edgeEffectInfo.mAppIcon = edgeLightingScheduler.getAppIcon(lightingScheduleInfo);
            edgeEffectInfo.mIsSmallIcon = edgeLightingScheduler.isSmallIcon(str, lightingScheduleInfo.mLightingInfo.getExtra().getBoolean("show_small_icon", false));
            edgeEffectInfo.mIsUsingAppIcon = EdgeLightingService.this.mIsUsingAppIcon;
            edgeEffectInfo.mText = lightingScheduleInfo.getNotiText();
            int preloadIndex = EdgeLightingStyleManager.getInstance().getPreloadIndex(EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver()));
            boolean z5 = Settings.System.getInt(edgeLightingScheduler.mTurnOverEdgeLighting.mContext.getContentResolver(), SettingsHelper.INDEX_REMOVE_ANIMATION, 0) == 1;
            Uri uri = Uri.parse("content://com.samsung.android.systemui.edgelighting.plus.provider");
            if (edgeLightingScheduler.mContext.getContentResolver().acquireContentProviderClient(uri) != null) {
                Bundle bundle = new Bundle();
                z4 = true;
                String[] strArr = edgeEffectInfo.mText;
                if (strArr != null) {
                    z2 = zIsSensitiveStateActive;
                    bundle.putString(UniversalCredentialUtil.AGENT_TITLE, strArr[0]);
                    bundle.putString("description", edgeEffectInfo.mText[1]);
                } else {
                    z2 = zIsSensitiveStateActive;
                }
                z3 = zIsScreenOn;
                edgeEffectInfo.mPlusEffectBundle = edgeLightingScheduler.mContext.getContentResolver().call(uri, "getData()", (String) null, bundle);
            } else {
                z2 = zIsSensitiveStateActive;
                z3 = zIsScreenOn;
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
                    if (zIsNeedToSanitized || visibility == 0 || visibility == -1 || z8 || z2 || zShouldHideNotiForAppLockByPackage) {
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
            } else if (zIsNeedToSanitized || z2 || zShouldHideNotiForAppLockByPackage) {
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
        boolean zIsScreenOn2 = edgeLightingScheduler.mRequester.isScreenOn();
        edgeLightingScheduler.mRequester.isScreenOn();
        edgeLightingScheduler.mRequester.getClass();
        if (text != null && zIsScreenOn2) {
            edgeLightingScheduler.mRequester.getClass();
        }
        Slog.d("EdgeLightingScheduler", stringBuffer.toString());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler$2] */
    public EdgeLightingScheduler(SemEdgeManager semEdgeManager) {
        this.mEdgeManager = semEdgeManager;
    }

    public final Drawable getAppIcon(LightingScheduleInfo lightingScheduleInfo) throws PackageManager.NameNotFoundException {
        String str = lightingScheduleInfo.mPackageName;
        Drawable drawableSemGetApplicationIconForIconTray = null;
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 4202624);
            if (!isSmallIcon(str, lightingScheduleInfo.mLightingInfo.getExtra().getBoolean("show_small_icon", false))) {
                if (EdgeLightingService.this.mIsColorThemeEnabled) {
                    List<LauncherActivityInfo> activityList = ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).getActivityList(str, UserHandle.getUserHandleForUid(applicationInfo.uid));
                    drawableSemGetApplicationIconForIconTray = !activityList.isEmpty() ? activityList.get(0).semGetBadgedIconForIconTray(this.mContext.getResources().getDisplayMetrics().densityDpi) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                } else {
                    drawableSemGetApplicationIconForIconTray = packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return drawableSemGetApplicationIconForIconTray == null ? lightingScheduleInfo.mIcon : drawableSemGetApplicationIconForIconTray;
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

    public final boolean isSmallIcon(String str, boolean z) throws PackageManager.NameNotFoundException {
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
