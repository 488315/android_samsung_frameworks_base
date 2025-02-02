package com.android.server.pm.verify.domain;

import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainSet;
import android.content.pm.verify.domain.DomainVerificationInfo;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.content.pm.verify.domain.IDomainVerificationManager;
import android.os.ServiceSpecificException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes3.dex */
public class DomainVerificationManagerStub extends IDomainVerificationManager.Stub {
  public final DomainVerificationService mService;

  public DomainVerificationManagerStub(DomainVerificationService domainVerificationService) {
    this.mService = domainVerificationService;
  }

  public List queryValidVerificationPackageNames() {
    try {
      return this.mService.queryValidVerificationPackageNames();
    } catch (Exception e) {
      throw this.rethrow(e);
    }
  }

  public DomainVerificationInfo getDomainVerificationInfo(String str) {
    try {
      return this.mService.getDomainVerificationInfo(str);
    } catch (Exception e) {
      throw this.rethrow(e);
    }
  }

  public int setDomainVerificationStatus(String str, DomainSet domainSet, int i) {
    try {
      return this.mService.setDomainVerificationStatus(
          UUID.fromString(str), domainSet.getDomains(), i);
    } catch (Exception e) {
      throw this.rethrow(e);
    }
  }

  public void setDomainVerificationLinkHandlingAllowed(String str, boolean z, int i) {
    try {
      this.mService.setDomainVerificationLinkHandlingAllowed(str, z, i);
    } catch (Exception e) {
      throw rethrow(e);
    }
  }

  public int setDomainVerificationUserSelection(String str, DomainSet domainSet, boolean z, int i) {
    try {
      return this.mService.setDomainVerificationUserSelection(
          UUID.fromString(str), domainSet.getDomains(), z, i);
    } catch (Exception e) {
      throw this.rethrow(e);
    }
  }

  public DomainVerificationUserState getDomainVerificationUserState(String str, int i) {
    try {
      return this.mService.getDomainVerificationUserState(str, i);
    } catch (Exception e) {
      throw this.rethrow(e);
    }
  }

  public List getOwnersForDomain(String str, int i) {
    try {
      Objects.requireNonNull(str);
      return this.mService.getOwnersForDomain(str, i);
    } catch (Exception e) {
      throw this.rethrow(e);
    }
  }

  public final RuntimeException rethrow(Exception exc) {
    if (exc instanceof PackageManager.NameNotFoundException) {
      return new ServiceSpecificException(1);
    }
    if (exc instanceof RuntimeException) {
      return (RuntimeException) exc;
    }
    return new RuntimeException(exc);
  }
}
