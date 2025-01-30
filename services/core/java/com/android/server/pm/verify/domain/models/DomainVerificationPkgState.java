package com.android.server.pm.verify.domain.models;

import android.annotation.NonNull;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes3.dex */
public class DomainVerificationPkgState {
  public final String mBackupSignatureHash;
  public final boolean mHasAutoVerifyDomains;
  public UUID mId;
  public final String mPackageName;
  public final ArrayMap mStateMap;
  public final SparseArray mUserStates;

  public DomainVerificationPkgState(String str, UUID uuid, boolean z) {
    this(str, uuid, z, new ArrayMap(0), new SparseArray(0), null);
  }

  public DomainVerificationPkgState(
      DomainVerificationPkgState domainVerificationPkgState, UUID uuid, boolean z) {
    this(
        domainVerificationPkgState.getPackageName(),
        uuid,
        z,
        domainVerificationPkgState.getStateMap(),
        domainVerificationPkgState.getUserStates(),
        null);
  }

  public DomainVerificationInternalUserState getUserState(int i) {
    return (DomainVerificationInternalUserState) this.mUserStates.get(i);
  }

  public DomainVerificationInternalUserState getOrCreateUserState(int i) {
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

  public void removeUser(int i) {
    this.mUserStates.remove(i);
  }

  public void removeAllUsers() {
    this.mUserStates.clear();
  }

  public final int userStatesHashCode() {
    return this.mUserStates.contentHashCode();
  }

  public final boolean userStatesEquals(SparseArray sparseArray) {
    return this.mUserStates.contentEquals(sparseArray);
  }

  public DomainVerificationPkgState(
      String str, UUID uuid, boolean z, ArrayMap arrayMap, SparseArray sparseArray, String str2) {
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
  }

  public String getPackageName() {
    return this.mPackageName;
  }

  public UUID getId() {
    return this.mId;
  }

  public boolean isHasAutoVerifyDomains() {
    return this.mHasAutoVerifyDomains;
  }

  public ArrayMap getStateMap() {
    return this.mStateMap;
  }

  public SparseArray getUserStates() {
    return this.mUserStates;
  }

  public String getBackupSignatureHash() {
    return this.mBackupSignatureHash;
  }

  public String toString() {
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
        + " }";
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DomainVerificationPkgState domainVerificationPkgState = (DomainVerificationPkgState) obj;
    return Objects.equals(this.mPackageName, domainVerificationPkgState.mPackageName)
        && Objects.equals(this.mId, domainVerificationPkgState.mId)
        && this.mHasAutoVerifyDomains == domainVerificationPkgState.mHasAutoVerifyDomains
        && Objects.equals(this.mStateMap, domainVerificationPkgState.mStateMap)
        && userStatesEquals(domainVerificationPkgState.mUserStates)
        && Objects.equals(
            this.mBackupSignatureHash, domainVerificationPkgState.mBackupSignatureHash);
  }

  public int hashCode() {
    return ((((((((((Objects.hashCode(this.mPackageName) + 31) * 31) + Objects.hashCode(this.mId))
                                    * 31)
                                + Boolean.hashCode(this.mHasAutoVerifyDomains))
                            * 31)
                        + Objects.hashCode(this.mStateMap))
                    * 31)
                + userStatesHashCode())
            * 31)
        + Objects.hashCode(this.mBackupSignatureHash);
  }
}
