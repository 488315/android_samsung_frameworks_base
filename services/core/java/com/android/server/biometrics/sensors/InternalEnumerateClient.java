package com.android.server.biometrics.sensors;

import android.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.BiometricAuthenticator;
import android.os.IBinder;
import android.os.UserHandle;
import android.text.BidiFormatter;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class InternalEnumerateClient extends HalClientMonitor implements EnumerateConsumer {
    public final List mEnrolledList;
    public final int mInitialEnrolledSize;
    public final List mUnknownHALTemplates;
    public final BiometricUtils mUtils;

    public InternalEnumerateClient(Context context, Supplier supplier, IBinder iBinder, int i, String str, List list, BiometricUtils biometricUtils, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext) {
        super(context, supplier, iBinder, null, i, str, 0, i2, biometricLogger, biometricContext, false);
        this.mUnknownHALTemplates = new ArrayList();
        this.mEnrolledList = list;
        this.mInitialEnrolledSize = list.size();
        this.mUtils = biometricUtils;
    }

    public abstract int getModality();

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final int getProtoEnum() {
        return 6;
    }

    @Override // com.android.server.biometrics.sensors.EnumerateConsumer
    public final void onEnumerationResult(BiometricAuthenticator.Identifier identifier, int i) {
        boolean z;
        if (identifier == null) {
            Slog.d("Biometrics/InternalEnumerateClient", "Null identifier");
        } else {
            Slog.v("Biometrics/InternalEnumerateClient", "handleEnumeratedTemplate: " + identifier.getBiometricId());
            int i2 = 0;
            while (true) {
                if (i2 >= this.mEnrolledList.size()) {
                    z = false;
                    break;
                } else {
                    if (((BiometricAuthenticator.Identifier) this.mEnrolledList.get(i2)).getBiometricId() == identifier.getBiometricId()) {
                        this.mEnrolledList.remove(i2);
                        z = true;
                        break;
                    }
                    i2++;
                }
            }
            if (!z && identifier.getBiometricId() != 0) {
                ((ArrayList) this.mUnknownHALTemplates).add(identifier);
            }
            Slog.v("Biometrics/InternalEnumerateClient", "Matched: " + z);
        }
        if (i == 0) {
            if (this.mEnrolledList == null) {
                Slog.d("Biometrics/InternalEnumerateClient", "Null enrolledList");
            } else {
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < this.mEnrolledList.size(); i3++) {
                    BiometricAuthenticator.Identifier identifier2 = (BiometricAuthenticator.Identifier) this.mEnrolledList.get(i3);
                    arrayList.add(identifier2.getName().toString());
                    Slog.e("Biometrics/InternalEnumerateClient", "doTemplateCleanup(): Removing dangling template from framework: " + identifier2.getBiometricId() + " " + ((Object) identifier2.getName()));
                    this.mUtils.removeBiometricForUser(this.mContext, this.mTargetUserId, identifier2.getBiometricId());
                    BiometricLogger biometricLogger = this.mLogger;
                    if (!biometricLogger.shouldSkipLogging()) {
                        biometricLogger.mSink.getClass();
                        FrameworkStatsLog.write(148, biometricLogger.mStatsModality, 2, -1);
                    }
                }
                if (!arrayList.isEmpty()) {
                    sendDanglingNotification(arrayList);
                }
                this.mEnrolledList.clear();
            }
            this.mCallback.onClientFinished(this, true);
        }
    }

    public void sendDanglingNotification(List list) {
        String string;
        if (list.isEmpty()) {
            return;
        }
        Slog.e("Biometrics/InternalEnumerateClient", "sendDanglingNotification(): initial enrolledSize=" + this.mInitialEnrolledSize + ", after clean up size=" + this.mEnrolledList.size());
        boolean z = this.mEnrolledList.size() == this.mInitialEnrolledSize;
        Context context = this.mContext;
        int modality = getModality();
        Intent intent = BiometricNotificationUtils.DISMISS_FRR_INTENT;
        boolean z2 = modality == 1;
        String str = z2 ? "FingerprintReEnroll" : "FaceReEnroll";
        if (list.isEmpty()) {
            Slog.v("BiometricNotificationUtils", "Skipping " + str + " notification : empty list");
            return;
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Showing ", str, " notification :[");
        m.append(list.size());
        m.append(" identifier(s) deleted, allIdentifiersDeleted=");
        m.append(z);
        m.append("]");
        Slog.d("BiometricNotificationUtils", m.toString());
        String string2 = context.getString(R.string.gsm_alphabet_default_charset);
        String string3 = context.getString(z2 ? R.string.lockscreen_sim_unlock_progress_dialog_message : R.string.language_picker_section_suggested);
        if (!z2) {
            string = context.getString(R.string.language_picker_section_all);
        } else if (list.isEmpty()) {
            string = null;
        } else {
            int size = list.size();
            StringBuilder sb = new StringBuilder();
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            if (size > 1) {
                String str2 = null;
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        str2 = bidiFormatter.unicodeWrap("\"" + ((String) list.get(i)) + "\"");
                    } else {
                        sb.append(bidiFormatter.unicodeWrap("\""));
                        sb.append(bidiFormatter.unicodeWrap((String) list.get(i)));
                        sb.append(bidiFormatter.unicodeWrap("\""));
                        if (i < size - 2) {
                            sb.append(bidiFormatter.unicodeWrap(", "));
                        }
                    }
                }
                string = String.format(context.getString(z ? R.string.lockscreen_sim_puk_locked_message : R.string.lockscreen_sim_locked_message), sb, str2);
            } else {
                int i2 = z ? R.string.lockscreen_sim_puk_locked_instructions : R.string.lockscreen_screen_locked;
                sb.append(bidiFormatter.unicodeWrap("\""));
                sb.append(bidiFormatter.unicodeWrap((String) list.get(0)));
                sb.append(bidiFormatter.unicodeWrap("\""));
                string = String.format(context.getString(i2), sb);
            }
        }
        Intent intent2 = new Intent(z2 ? "action_fingerprint_re_enroll_launch" : "action_face_re_enroll_launch");
        UserHandle userHandle = UserHandle.CURRENT;
        PendingIntent broadcastAsUser = PendingIntent.getBroadcastAsUser(context, 0, intent2, 67108864, userHandle);
        BiometricNotificationUtils.showNotificationHelper(context, string2, string3, string, broadcastAsUser, new Notification.Action.Builder((Icon) null, context.getString(R.string.config_defaultOnDeviceSpeechRecognitionService), broadcastAsUser).build(), new Notification.Action.Builder((Icon) null, context.getString(R.string.config_defaultOnDeviceSandboxedInferenceService), PendingIntent.getBroadcastAsUser(context, 0, new Intent(z2 ? "action_fingerprint_re_enroll_dismiss" : "action_face_re_enroll_dismiss"), 67108864, userHandle)).build(), "sys", z2 ? "FingerprintReEnrollNotificationChannel" : "FaceReEnrollNotificationChannel", z2 ? "FingerprintReEnroll" : "FaceReEnroll", -1, false, 32);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void unableToStart() {
    }
}
