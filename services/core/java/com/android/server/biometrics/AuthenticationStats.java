package com.android.server.biometrics;

public final class AuthenticationStats {
    public int mEnrollmentNotifications;
    public final int mModality;
    public int mRejectedAttempts;
    public int mTotalAttempts;
    public final int mUserId;

    public AuthenticationStats(int i, int i2) {
        this.mUserId = i;
        this.mTotalAttempts = 0;
        this.mRejectedAttempts = 0;
        this.mEnrollmentNotifications = 0;
        this.mModality = i2;
    }

    public AuthenticationStats(int i, int i2, int i3, int i4, int i5) {
        this.mUserId = i;
        this.mTotalAttempts = i2;
        this.mRejectedAttempts = i3;
        this.mEnrollmentNotifications = i4;
        this.mModality = i5;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthenticationStats)) {
            return false;
        }
        AuthenticationStats authenticationStats = (AuthenticationStats) obj;
        return this.mUserId == authenticationStats.mUserId
                && this.mTotalAttempts == authenticationStats.mTotalAttempts
                && this.mRejectedAttempts == authenticationStats.mRejectedAttempts
                && this.mEnrollmentNotifications == authenticationStats.mEnrollmentNotifications
                && this.mModality == authenticationStats.mModality;
    }

    public final int hashCode() {
        return String.format(
                        "userId: %d, totalAttempts: %d, rejectedAttempts: %d,"
                            + " enrollmentNotifications: %d, modality: %d",
                        Integer.valueOf(this.mUserId),
                        Integer.valueOf(this.mTotalAttempts),
                        Integer.valueOf(this.mRejectedAttempts),
                        Integer.valueOf(this.mEnrollmentNotifications),
                        Integer.valueOf(this.mModality))
                .hashCode();
    }
}
