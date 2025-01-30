package com.android.systemui.sensorprivacy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.Dependency;
import com.android.systemui.ScRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KFunction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SensorUseStartedActivity extends Activity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final Handler bgHandler;
    public final DisplayLifecycle displayLifecycle;
    public final KeyguardStateController keyguardStateController;
    public SensorUseDialog mDialog;
    public boolean mShouldRunDisableSensorPrivacy;
    public final IndividualSensorPrivacyController sensorPrivacyController;
    public IndividualSensorPrivacyController.Callback sensorPrivacyListener;
    public String sensorUsePackageName;
    public final SubscreenSensorUseUtil subscreenSensorUseUtil;
    public boolean unsuppressImmediately;
    public int sensor = -1;
    public final KFunction mBackCallback = new SensorUseStartedActivity$mBackCallback$1(this);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SensorUseStartedActivity(IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController, KeyguardDismissUtil keyguardDismissUtil, ActivityStarter activityStarter, Handler handler, DisplayLifecycle displayLifecycle, SubscreenSensorUseUtil subscreenSensorUseUtil) {
        this.sensorPrivacyController = individualSensorPrivacyController;
        this.keyguardStateController = keyguardStateController;
        this.activityStarter = activityStarter;
        this.bgHandler = handler;
        this.displayLifecycle = displayLifecycle;
        this.subscreenSensorUseUtil = subscreenSensorUseUtil;
    }

    public final void disableSensorPrivacy() {
        int i = this.sensor;
        if (i == Integer.MAX_VALUE) {
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).setSensorBlocked(3, 1, false);
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).setSensorBlocked(3, 2, false);
        } else {
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).setSensorBlocked(3, i, false);
        }
        this.unsuppressImmediately = true;
        setResult(-1);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            this.unsuppressImmediately = false;
            String str = this.sensorUsePackageName;
            FrameworkStatsLog.write(VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB, 2, str != null ? str : null);
        } else if (i == -1) {
            if (((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).mSensorPrivacyManager.requiresAuthentication()) {
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
                if (keyguardStateControllerImpl.mSecure && keyguardStateControllerImpl.mShowing) {
                    if (ScRune.QUICK_TILE_SUBSCREEN_SENSOR_PRIVACY) {
                        DisplayLifecycle displayLifecycle = this.displayLifecycle;
                        if (!(displayLifecycle != null ? displayLifecycle.mIsFolderOpened : false)) {
                            SubscreenSensorUseUtil subscreenSensorUseUtil = this.subscreenSensorUseUtil;
                            subscreenSensorUseUtil.runnable = new Runnable() { // from class: com.android.systemui.sensorprivacy.SensorUseStartedActivity$onClick$1$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SensorUseStartedActivity sensorUseStartedActivity = SensorUseStartedActivity.this;
                                    int i2 = SensorUseStartedActivity.$r8$clinit;
                                    sensorUseStartedActivity.disableSensorPrivacy();
                                    String str2 = SensorUseStartedActivity.this.sensorUsePackageName;
                                    if (str2 == null) {
                                        str2 = null;
                                    }
                                    FrameworkStatsLog.write(VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB, 1, str2);
                                }
                            };
                            if (!subscreenSensorUseUtil.registered) {
                                subscreenSensorUseUtil.registered = true;
                                BroadcastDispatcher.registerReceiver$default(subscreenSensorUseUtil.broadcastDispatcher, subscreenSensorUseUtil.intentReceiver, new IntentFilter("com.android.systemui.sensorprivacy.SensorPolicyAction"), null, null, 0, null, 60);
                                subscreenSensorUseUtil.displayLifecycle.addObserver(subscreenSensorUseUtil.displayLifecycleObserver);
                            }
                            ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).showLockscreenOnCoverScreen(this, "com.android.systemui.sensorprivacy.SensorPolicyAction");
                        }
                    }
                    this.mShouldRunDisableSensorPrivacy = true;
                }
            }
            disableSensorPrivacy();
            String str2 = this.sensorUsePackageName;
            FrameworkStatsLog.write(VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB, 1, str2 != null ? str2 : null);
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setShowWhenLocked(true);
        setFinishOnTouchOutside(false);
        setResult(0);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(81);
        }
        String stringExtra = getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME");
        if (stringExtra == null) {
            return;
        }
        this.sensorUsePackageName = stringExtra;
        if (getIntent().getBooleanExtra(SensorPrivacyManager.EXTRA_ALL_SENSORS, false)) {
            this.sensor = Integer.MAX_VALUE;
            IndividualSensorPrivacyController.Callback callback = new IndividualSensorPrivacyController.Callback() { // from class: com.android.systemui.sensorprivacy.SensorUseStartedActivity$onCreate$callback$1
                @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
                public final void onSensorBlockedChanged(int i, boolean z) {
                    SensorUseStartedActivity sensorUseStartedActivity = SensorUseStartedActivity.this;
                    if (((IndividualSensorPrivacyControllerImpl) sensorUseStartedActivity.sensorPrivacyController).isSensorBlocked(1) || ((IndividualSensorPrivacyControllerImpl) sensorUseStartedActivity.sensorPrivacyController).isSensorBlocked(2)) {
                        return;
                    }
                    sensorUseStartedActivity.finish();
                }
            };
            this.sensorPrivacyListener = callback;
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).addCallback(callback);
            if (!((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).isSensorBlocked(1) && !((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).isSensorBlocked(2)) {
                finish();
                return;
            }
        } else {
            int intExtra = getIntent().getIntExtra(SensorPrivacyManager.EXTRA_SENSOR, -1);
            if (intExtra == -1) {
                finish();
                return;
            }
            this.sensor = intExtra;
            IndividualSensorPrivacyController.Callback callback2 = new IndividualSensorPrivacyController.Callback() { // from class: com.android.systemui.sensorprivacy.SensorUseStartedActivity$onCreate$callback$2
                @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
                public final void onSensorBlockedChanged(int i, boolean z) {
                    SensorUseStartedActivity sensorUseStartedActivity = SensorUseStartedActivity.this;
                    if (i != sensorUseStartedActivity.sensor || z) {
                        return;
                    }
                    sensorUseStartedActivity.finish();
                }
            };
            this.sensorPrivacyListener = callback2;
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).addCallback(callback2);
            if (!((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).isSensorBlocked(this.sensor)) {
                finish();
                return;
            }
        }
        SensorUseDialog sensorUseDialog = new SensorUseDialog(this, this.sensor, this, this);
        this.mDialog = sensorUseDialog;
        sensorUseDialog.show();
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, new C2365xe8a3393e((Function0) this.mBackCallback));
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        SensorUseDialog sensorUseDialog = this.mDialog;
        if (sensorUseDialog != null) {
            sensorUseDialog.dismiss();
        }
        IndividualSensorPrivacyController.Callback callback = this.sensorPrivacyListener;
        if (callback != null) {
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).removeCallback(callback);
        }
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(new C2365xe8a3393e((Function0) this.mBackCallback));
        if (this.mShouldRunDisableSensorPrivacy) {
            this.mShouldRunDisableSensorPrivacy = false;
            this.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.sensorprivacy.SensorUseStartedActivity$onDestroy$2
                @Override // java.lang.Runnable
                public final void run() {
                    final SensorUseStartedActivity sensorUseStartedActivity = SensorUseStartedActivity.this;
                    sensorUseStartedActivity.activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.sensorprivacy.SensorUseStartedActivity$onDestroy$2.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SensorUseStartedActivity sensorUseStartedActivity2 = SensorUseStartedActivity.this;
                            int i = SensorUseStartedActivity.$r8$clinit;
                            sensorUseStartedActivity2.disableSensorPrivacy();
                            String str = SensorUseStartedActivity.this.sensorUsePackageName;
                            if (str == null) {
                                str = null;
                            }
                            FrameworkStatsLog.write(VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB, 1, str);
                        }
                    });
                }
            }, 200L);
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        setIntent(intent);
        recreate();
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        setSuppressed(true);
        this.unsuppressImmediately = false;
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        if (this.unsuppressImmediately) {
            setSuppressed(false);
        } else {
            this.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.sensorprivacy.SensorUseStartedActivity$onStop$1
                @Override // java.lang.Runnable
                public final void run() {
                    SensorUseStartedActivity sensorUseStartedActivity = SensorUseStartedActivity.this;
                    int i = SensorUseStartedActivity.$r8$clinit;
                    sensorUseStartedActivity.setSuppressed(false);
                }
            }, 2000L);
        }
    }

    public final void setSuppressed(boolean z) {
        int i = this.sensor;
        if (i != Integer.MAX_VALUE) {
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).mSensorPrivacyManager.suppressSensorPrivacyReminders(i, z);
        } else {
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).mSensorPrivacyManager.suppressSensorPrivacyReminders(1, z);
            ((IndividualSensorPrivacyControllerImpl) this.sensorPrivacyController).mSensorPrivacyManager.suppressSensorPrivacyReminders(2, z);
        }
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
    }
}
