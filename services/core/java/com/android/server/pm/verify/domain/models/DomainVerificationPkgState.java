package com.android.server.pm.verify.domain.models;

import android.annotation.NonNull;
import android.util.ArrayMap;
import android.util.SparseArray;

import com.android.internal.util.AnnotationValidations;

import java.util.Objects;
import java.util.UUID;

public final class DomainVerificationPkgState {
    public final String mBackupSignatureHash;
    public final boolean mHasAutoVerifyDomains;
    public final UUID mId;
    public final String mPackageName;
    public final ArrayMap mStateMap;
    public final ArrayMap mUriRelativeFilterGroupMap;
    public final SparseArray mUserStates;

    public DomainVerificationPkgState(
            String str,
            UUID uuid,
            boolean z,
            ArrayMap arrayMap,
            SparseArray sparseArray,
            String str2,
            ArrayMap arrayMap2) {
        this.mPackageName = str;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
        this.mId = uuid;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, uuid);
        this.mHasAutoVerifyDomains = z;
        this.mStateMap = arrayMap;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, arrayMap);
        this.mUserStates = sparseArray;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, sparseArray);
        this.mBackupSignatureHash = str2;
        this.mUriRelativeFilterGroupMap = arrayMap2;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, arrayMap2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || DomainVerificationPkgState.class != obj.getClass()) {
            return false;
        }
        DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) obj;
        if (Objects.equals(this.mPackageName, domainVerificationPkgState.mPackageName)
                && Objects.equals(this.mId, domainVerificationPkgState.mId)
                && this.mHasAutoVerifyDomains == domainVerificationPkgState.mHasAutoVerifyDomains
                && Objects.equals(this.mStateMap, domainVerificationPkgState.mStateMap)) {
            if (this.mUserStates.contentEquals(domainVerificationPkgState.mUserStates)
                    && Objects.equals(
                            this.mBackupSignatureHash,
                            domainVerificationPkgState.mBackupSignatureHash)
                    && Objects.equals(
                            this.mUriRelativeFilterGroupMap,
                            domainVerificationPkgState.mUriRelativeFilterGroupMap)) {
                return true;
            }
        }
        return false;
    }

    public final DomainVerificationInternalUserState getOrCreateUserState(int i) {
        DomainVerificationInternalUserState domainVerificationInternalUserState =
                (DomainVerificationInternalUserState) this.mUserStates.get(i);
        if (domainVerificationInternalUserState != null) {
            return domainVerificationInternalUserState;
        }
        DomainVerificationInternalUserState domainVerificationInternalUserState2 =
                new DomainVerificationInternalUserState(i);
        this.mUserStates.put(i, domainVerificationInternalUserState2);
        return domainVerificationInternalUserState2;
    }

    public final int hashCode() {
        return Objects.hashCode(this.mUriRelativeFilterGroupMap)
                + ((Objects.hashCode(this.mBackupSignatureHash)
                                + ((this.mUserStates.contentHashCode()
                                                + ((Objects.hashCode(this.mStateMap)
                                                                + ((Boolean.hashCode(
                                                                                        this
                                                                                                .mHasAutoVerifyDomains)
                                                                                + ((Objects
                                                                                                        .hashCode(
                                                                                                                this
                                                                                                                        .mId)
                                                                                                + ((Objects
                                                                                                                        .hashCode(
                                                                                                                                this
                                                                                                                                        .mPackageName)
                                                                                                                + 31)
                                                                                                        * 31))
                                                                                        * 31))
                                                                        * 31))
                                                        * 31))
                                        * 31))
                        * 31);
    }

    public final void removeUser(int i) {
        this.mUserStates.remove(i);
    }

    public final String toString() {
        return "DomainVerificationPkgState { packageName = "
                + this.mPackageName
                + ", id = "
                + this.mId
                + ", hasAutoVerifyDomains = "
                + this.mHasAutoVerifyDomains
                + ", stateMap = "
                + this.mStateMap
                + ", userStates = "
                + this.mUserStates
                + ", backupSignatureHash = "
                + this.mBackupSignatureHash
                + ", uriRelativeFilterGroupMap = "
                + this.mUriRelativeFilterGroupMap
                + " }";
    }
}
