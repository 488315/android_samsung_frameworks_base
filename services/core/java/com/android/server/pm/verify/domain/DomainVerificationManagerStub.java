package com.android.server.pm.verify.domain;

import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainSet;
import android.content.pm.verify.domain.DomainVerificationInfo;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.content.pm.verify.domain.IDomainVerificationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.ServiceSpecificException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class DomainVerificationManagerStub extends IDomainVerificationManager.Stub {
    public final DomainVerificationService mService;

    public DomainVerificationManagerStub(DomainVerificationService domainVerificationService) {
        this.mService = domainVerificationService;
    }

    public static RuntimeException rethrow(Exception exc) {
        return exc instanceof PackageManager.NameNotFoundException
                ? new ServiceSpecificException(1)
                : exc instanceof RuntimeException
                        ? (RuntimeException) exc
                        : new RuntimeException(exc);
    }

    public final DomainVerificationInfo getDomainVerificationInfo(String str) {
        try {
            return this.mService.getDomainVerificationInfo(str);
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    public final DomainVerificationUserState getDomainVerificationUserState(String str, int i) {
        try {
            return this.mService.getDomainVerificationUserState(str, i);
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    public final List getOwnersForDomain(String str, int i) {
        try {
            Objects.requireNonNull(str);
            return this.mService.getOwnersForDomain(str, i);
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    public final Bundle getUriRelativeFilterGroups(String str, List list) {
        try {
            return this.mService.getUriRelativeFilterGroups(str, list);
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    public final List queryValidVerificationPackageNames() {
        try {
            return this.mService.queryValidVerificationPackageNames();
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    public final void setDomainVerificationLinkHandlingAllowed(String str, boolean z, int i) {
        try {
            this.mService.setDomainVerificationLinkHandlingAllowed(str, z, i);
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    public final int setDomainVerificationStatus(String str, DomainSet domainSet, int i) {
        try {
            DomainVerificationService domainVerificationService = this.mService;
            UUID fromString = UUID.fromString(str);
            Set domains = domainSet.getDomains();
            domainVerificationService.getClass();
            if (i < 1024 && i != 1) {
                throw new IllegalArgumentException("Caller is not allowed to set state code " + i);
            }
            domainVerificationService.mConnection.getClass();
            return domainVerificationService.setDomainVerificationStatusInternal(
                    Binder.getCallingUid(), fromString, domains, i);
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    public final int setDomainVerificationUserSelection(
            String str, DomainSet domainSet, boolean z, int i) {
        try {
            return this.mService.setDomainVerificationUserSelection(
                    UUID.fromString(str), domainSet.getDomains(), z, i);
        } catch (Exception e) {
            throw rethrow(e);
        }
    }

    public final void setUriRelativeFilterGroups(String str, Bundle bundle) {
        try {
            this.mService.setUriRelativeFilterGroups(str, bundle);
        } catch (Exception e) {
            throw rethrow(e);
        }
    }
}
