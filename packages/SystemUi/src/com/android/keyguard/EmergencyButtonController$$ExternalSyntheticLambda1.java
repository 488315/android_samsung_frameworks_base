package com.android.keyguard;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.keyguard.EmergencyButtonController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.samsung.android.telecom.SemTelecomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class EmergencyButtonController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EmergencyButtonController f$0;

    public /* synthetic */ EmergencyButtonController$$ExternalSyntheticLambda1(EmergencyButtonController emergencyButtonController, int i) {
        this.$r8$classId = i;
        this.f$0 = emergencyButtonController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.updateEmergencyCallButton();
                break;
            default:
                final EmergencyButtonController emergencyButtonController = this.f$0;
                TelecomManager telecomManager = emergencyButtonController.mTelecomManager;
                final boolean z = telecomManager != null && telecomManager.isInCall();
                emergencyButtonController.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str;
                        View view;
                        String obj;
                        EmergencyButtonController emergencyButtonController2 = EmergencyButtonController.this;
                        if (z) {
                            emergencyButtonController2.getClass();
                            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                            Log.d("EmergencyButton", "takeEmergencyCallAction - showInCallScreen(false, " + currentUser + ")");
                            ((SemTelecomManager) emergencyButtonController2.getContext().getSystemService(SemTelecomManager.class)).showInCallScreen(false, UserHandle.of(currentUser));
                            EmergencyButtonController.EmergencyButtonCallback emergencyButtonCallback = emergencyButtonController2.mEmergencyButtonCallback;
                            if (emergencyButtonCallback != null) {
                                emergencyButtonCallback.onEmergencyButtonClickedWhenInCall();
                                return;
                            }
                            return;
                        }
                        emergencyButtonController2.mKeyguardUpdateMonitor.reportEmergencyCallAction();
                        if (LsRune.SECURITY_DIRECT_CALL_TO_ECC) {
                            try {
                                view = emergencyButtonController2.mPasswordEntry;
                            } catch (Exception unused) {
                            }
                            if (view instanceof SecPasswordTextView) {
                                str = ((SecPasswordTextView) view).mText;
                                ((PasswordTextView) view).reset(false, false);
                            } else {
                                if (view instanceof TextView) {
                                    obj = ((TextView) view).getText().toString();
                                    ((TextView) emergencyButtonController2.mPasswordEntry).setText("");
                                } else {
                                    if (view instanceof EditText) {
                                        obj = ((EditText) view).getText().toString();
                                        ((EditText) emergencyButtonController2.mPasswordEntry).setText("");
                                    }
                                    str = "";
                                }
                                str = obj;
                            }
                            if (!str.equals("") && PhoneNumberUtils.isEmergencyNumber(str)) {
                                Intent intent = new Intent("android.intent.action.CALL_EMERGENCY");
                                intent.setData(Uri.fromParts("tel", str, null));
                                intent.setFlags(343932928);
                                try {
                                    Log.w("EmergencyButton", "callToEmergencyLine");
                                    emergencyButtonController2.getContext().startActivityAsUser(intent, ActivityOptions.makeCustomAnimation(emergencyButtonController2.getContext(), 0, 0).toBundle(), new UserHandle(KeyguardUpdateMonitor.getCurrentUser()));
                                    return;
                                } catch (ActivityNotFoundException e) {
                                    Log.e("EmergencyButton", "Can't find the component " + e);
                                    return;
                                }
                            }
                        }
                        TelecomManager telecomManager2 = emergencyButtonController2.mTelecomManager;
                        if (telecomManager2 == null) {
                            Log.wtf("EmergencyButton", "TelecomManager was null, cannot launch emergency dialer");
                            return;
                        }
                        Intent putExtra = telecomManager2.createLaunchEmergencyDialerIntent(null).setFlags(343932928).putExtra("com.android.phone.EmergencyDialer.extra.ENTRY_TYPE", 1);
                        ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(emergencyButtonController2.getContext(), 0, 0);
                        Log.d("EmergencyButton", "takeEmergencyCallAction");
                        emergencyButtonController2.mImm.hideSoftInputFromWindow(((EmergencyButton) emergencyButtonController2.mView).getWindowToken(), 0);
                        makeCustomAnimation.setLaunchDisplayId(emergencyButtonController2.getContext().getDisplay().getDisplayId());
                        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isAdminLockEnabled()) {
                            putExtra.putExtra("enable_ice_contact_list", false);
                            putExtra.putExtra("enable_emergency_medical_info", false);
                        }
                        emergencyButtonController2.getContext().startActivityAsUser(putExtra, makeCustomAnimation.toBundle(), new UserHandle(KeyguardUpdateMonitor.getCurrentUser()));
                    }
                });
                break;
        }
    }
}
