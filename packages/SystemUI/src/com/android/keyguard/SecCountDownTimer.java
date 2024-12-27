package com.android.keyguard;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class SecCountDownTimer extends CountDownTimer {
    public final int mAttempt;
    public final int mAttemptRemainingBeforePermanentLock;
    public final int mAttemptRemainingBeforeWipe;
    public final Context mContext;
    public final boolean mIsBouncer;
    public final KeyguardTextBuilder mKeyguardTextBuilder;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public String mTimerText;

    public SecCountDownTimer(long j, long j2, Context context, SelectedUserInteractor selectedUserInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardTextBuilder keyguardTextBuilder, boolean z) {
        super(j, j2);
        this.mTimerText = "";
        this.mContext = context;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardTextBuilder = keyguardTextBuilder;
        this.mIsBouncer = z;
        this.mAttemptRemainingBeforeWipe = keyguardUpdateMonitor.getRemainingAttempt(1);
        this.mAttemptRemainingBeforePermanentLock = keyguardUpdateMonitor.getRemainingAttemptsBeforePermanentLock();
        this.mAttempt = keyguardUpdateMonitor.getFailedUnlockAttempts(selectedUserInteractor.getSelectedUserId(false));
    }

    @Override // android.os.CountDownTimer
    public void onTick(long j) {
        String sb;
        String quantityString;
        int round = Math.round(j / 1000) % 60;
        int floor = ((int) Math.floor(j / 60000)) % 60;
        int floor2 = (int) Math.floor(j / 3600000);
        if (this.mAttemptRemainingBeforeWipe <= 0) {
            int i = this.mAttemptRemainingBeforePermanentLock;
            if (3 < i || i <= 0) {
                if (this.mIsBouncer) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.mContext.getString(R.string.kg_too_many_failed_attempts_warning));
                    sb2.append(this.mKeyguardUpdateMonitor.isRemoteLockMode() ? "\n" : "\n\n");
                    sb = sb2.toString();
                }
                sb = "";
            } else {
                if (this.mIsBouncer) {
                    StringBuilder sb3 = new StringBuilder();
                    Resources resources = this.mContext.getResources();
                    int i2 = this.mAttemptRemainingBeforePermanentLock;
                    sb3.append(resources.getQuantityString(R.plurals.kg_attempt_left_before_permanent_locking, i2, Integer.valueOf(i2)));
                    sb3.append(this.mKeyguardUpdateMonitor.isRemoteLockMode() ? "\n" : "\n\n");
                    sb = sb3.toString();
                }
                sb = "";
            }
        } else if (this.mIsBouncer) {
            sb = this.mKeyguardTextBuilder.getWarningAutoWipeMessage(this.mAttempt, this.mAttemptRemainingBeforeWipe) + "\n\n";
        } else {
            StringBuilder sb4 = new StringBuilder();
            Resources resources2 = this.mContext.getResources();
            int i3 = this.mAttemptRemainingBeforeWipe;
            sb = ComponentActivity$1$$ExternalSyntheticOutline0.m(sb4, resources2.getQuantityString(R.plurals.kg_attempt_left, i3, Integer.valueOf(i3)), "\n");
        }
        StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb);
        int i4 = floor + 1;
        int i5 = round + 1;
        if (floor2 <= 0) {
            quantityString = i4 > 1 ? this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_min, i4, Integer.valueOf(i4)) : this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_sec, i5, Integer.valueOf(i5));
        } else if (i4 == 60) {
            int i6 = floor2 + 1;
            quantityString = this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_hour, i6, Integer.valueOf(i6));
        } else {
            quantityString = floor2 == 1 ? this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_1_hour_and_min, i4, Integer.valueOf(i4)) : (floor2 <= 1 || i4 != 1) ? this.mContext.getString(R.string.kg_too_many_failed_attempts_countdown_hour_and_min, Integer.valueOf(floor2), Integer.valueOf(i4)) : this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_hour_and_1_min, floor2, Integer.valueOf(floor2));
        }
        m.append(quantityString);
        this.mTimerText = m.toString();
    }

    @Override // android.os.CountDownTimer
    public void onFinish() {
    }
}
