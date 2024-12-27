package com.android.server.pm.verify.domain.models;

import android.annotation.NonNull;
import android.annotation.UserIdInt;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.util.ArraySet;

import com.android.internal.util.AnnotationValidations;

import java.util.Objects;

public final class DomainVerificationInternalUserState {
    public final ArraySet mEnabledHosts;
    public boolean mLinkHandlingAllowed;
    public final int mUserId;

    public DomainVerificationInternalUserState(int i) {
        this.mLinkHandlingAllowed = true;
        this.mUserId = i;
        this.mEnabledHosts = new ArraySet();
    }

    public DomainVerificationInternalUserState(int i, ArraySet arraySet, boolean z) {
        this.mLinkHandlingAllowed = true;
        this.mUserId = i;
        AnnotationValidations.validate(UserIdInt.class, (UserIdInt) null, i);
        this.mEnabledHosts = arraySet;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, arraySet);
        this.mLinkHandlingAllowed = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || DomainVerificationInternalUserState.class != obj.getClass()) {
            return false;
        }
        DomainVerificationInternalUserState domainVerificationInternalUserState =
                (DomainVerificationInternalUserState) obj;
        return this.mUserId == domainVerificationInternalUserState.mUserId
                && Objects.equals(
                        this.mEnabledHosts, domainVerificationInternalUserState.mEnabledHosts)
                && this.mLinkHandlingAllowed
                        == domainVerificationInternalUserState.mLinkHandlingAllowed;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.mLinkHandlingAllowed)
                + ((Objects.hashCode(this.mEnabledHosts) + ((this.mUserId + 31) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DomainVerificationInternalUserState { userId = ");
        sb.append(this.mUserId);
        sb.append(", enabledHosts = ");
        sb.append(this.mEnabledHosts);
        sb.append(", linkHandlingAllowed = ");
        return OptionalBool$$ExternalSyntheticOutline0.m(" }", sb, this.mLinkHandlingAllowed);
    }
}
