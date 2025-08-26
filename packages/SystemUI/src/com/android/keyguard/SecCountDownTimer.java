package com.android.keyguard;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

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
        this.mAttempt = keyguardUpdateMonitor.getFailedUnlockAttempts(selectedUserInteractor.getSelectedUserId());
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00cd  */
    @Override // android.os.CountDownTimer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onTick(long j) {
        String string;
        String quantityString;
        int iRound = Math.round(j / 1000) % 60;
        int iFloor = ((int) Math.floor(j / 60000)) % 60;
        int iFloor2 = (int) Math.floor(j / 3600000);
        if (this.mAttemptRemainingBeforeWipe <= 0) {
            int i = this.mAttemptRemainingBeforePermanentLock;
            if (3 < i || i <= 0) {
                if (this.mIsBouncer) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.mContext.getString(R.string.kg_too_many_failed_attempts_warning));
                    sb.append(this.mKeyguardUpdateMonitor.isRemoteLockMode() ? "\n" : "\n\n");
                    string = sb.toString();
                } else {
                    string = "";
                }
            } else if (this.mIsBouncer) {
                StringBuilder sb2 = new StringBuilder();
                Resources resources = this.mContext.getResources();
                int i2 = this.mAttemptRemainingBeforePermanentLock;
                sb2.append(resources.getQuantityString(R.plurals.kg_attempt_left_before_permanent_locking, i2, Integer.valueOf(i2)));
                sb2.append(this.mKeyguardUpdateMonitor.isRemoteLockMode() ? "\n" : "\n\n");
                string = sb2.toString();
            }
        } else if (this.mIsBouncer) {
            string = this.mKeyguardTextBuilder.getWarningAutoWipeMessage(this.mAttempt, this.mAttemptRemainingBeforeWipe) + "\n\n";
        } else {
            StringBuilder sb3 = new StringBuilder();
            Resources resources2 = this.mContext.getResources();
            int i3 = this.mAttemptRemainingBeforeWipe;
            string = TransitionKt$$ExternalSyntheticOutline0.m(sb3, resources2.getQuantityString(R.plurals.kg_attempt_left, i3, Integer.valueOf(i3)), "\n");
        }
        StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(string);
        int i4 = iFloor + 1;
        int i5 = iRound + 1;
        if (iFloor2 <= 0) {
            quantityString = i4 > 1 ? this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_min, i4, Integer.valueOf(i4)) : this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_sec, i5, Integer.valueOf(i5));
        } else if (i4 == 60) {
            int i6 = iFloor2 + 1;
            quantityString = this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_hour, i6, Integer.valueOf(i6));
        } else {
            quantityString = iFloor2 == 1 ? this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_1_hour_and_min, i4, Integer.valueOf(i4)) : (iFloor2 <= 1 || i4 != 1) ? this.mContext.getString(R.string.kg_too_many_failed_attempts_countdown_hour_and_min, Integer.valueOf(iFloor2), Integer.valueOf(i4)) : this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_hour_and_1_min, iFloor2, Integer.valueOf(iFloor2));
        }
        sbM.append(quantityString);
        this.mTimerText = sbM.toString();
    }

    @Override // android.os.CountDownTimer
    public void onFinish() {
    }
}
