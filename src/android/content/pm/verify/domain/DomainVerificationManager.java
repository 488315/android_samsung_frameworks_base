package android.content.pm.verify.domain;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.UriRelativeFilterGroup;
import android.content.UriRelativeFilterGroupParcel;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.ArrayMap;
import com.android.internal.util.CollectionUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.ToIntFunction;

/* loaded from: classes.dex */
public final class DomainVerificationManager {

    @SystemApi
    public static final int ERROR_DOMAIN_SET_ID_INVALID = 1;

    @SystemApi
    public static final int ERROR_UNABLE_TO_APPROVE = 3;

    @SystemApi
    public static final int ERROR_UNKNOWN_DOMAIN = 2;

    @SystemApi
    public static final String EXTRA_VERIFICATION_REQUEST = "android.content.pm.verify.domain.extra.VERIFICATION_REQUEST";
    public static final int INTERNAL_ERROR_NAME_NOT_FOUND = 1;

    @SystemApi
    public static final int STATUS_OK = 0;
    private final Context mContext;
    private final IDomainVerificationManager mDomainVerificationManager;

    public @interface Error {
    }

    public DomainVerificationManager(Context context, IDomainVerificationManager iDomainVerificationManager) {
        this.mContext = context;
        this.mDomainVerificationManager = iDomainVerificationManager;
    }

    @SystemApi
    public void setUriRelativeFilterGroups(String str, Map<String, List<UriRelativeFilterGroup>> map) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(map);
        Bundle bundle = new Bundle();
        for (String str2 : map.keySet()) {
            bundle.putParcelableList(str2, UriRelativeFilterGroup.groupsToParcels(map.get(str2)));
        }
        try {
            this.mDomainVerificationManager.setUriRelativeFilterGroups(str, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Map<String, List<UriRelativeFilterGroup>> getUriRelativeFilterGroups(String str, List<String> list) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(list);
        if (list.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        try {
            Bundle uriRelativeFilterGroups = this.mDomainVerificationManager.getUriRelativeFilterGroups(str, list);
            ArrayMap arrayMap = new ArrayMap();
            if (!uriRelativeFilterGroups.isEmpty()) {
                for (String str2 : uriRelativeFilterGroups.keySet()) {
                    arrayMap.put(str2, UriRelativeFilterGroup.parcelsToGroups(uriRelativeFilterGroups.getParcelableArrayList(str2, UriRelativeFilterGroupParcel.class)));
                }
            }
            return arrayMap;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> queryValidVerificationPackageNames() {
        try {
            return this.mDomainVerificationManager.queryValidVerificationPackageNames();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public DomainVerificationInfo getDomainVerificationInfo(String str) throws PackageManager.NameNotFoundException {
        try {
            return this.mDomainVerificationManager.getDomainVerificationInfo(str);
        } catch (Exception e) {
            Exception rethrow = this.rethrow(e, str);
            if (rethrow instanceof PackageManager.NameNotFoundException) {
                throw ((PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof RuntimeException) {
                throw ((RuntimeException) rethrow);
            }
            throw new RuntimeException(rethrow);
        }
    }

    @SystemApi
    public int setDomainVerificationStatus(UUID uuid, Set<String> set, int i) throws PackageManager.NameNotFoundException {
        validateInput(uuid, set);
        try {
            return this.mDomainVerificationManager.setDomainVerificationStatus(uuid.toString(), new DomainSet(set), i);
        } catch (Exception e) {
            Exception rethrow = this.rethrow(e, null);
            if (rethrow instanceof PackageManager.NameNotFoundException) {
                throw ((PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof RuntimeException) {
                throw ((RuntimeException) rethrow);
            }
            throw new RuntimeException(rethrow);
        }
    }

    @SystemApi
    public void setDomainVerificationLinkHandlingAllowed(String str, boolean z) throws PackageManager.NameNotFoundException {
        try {
            this.mDomainVerificationManager.setDomainVerificationLinkHandlingAllowed(str, z, this.mContext.getUserId());
        } catch (Exception e) {
            Exception rethrow = rethrow(e, null);
            if (rethrow instanceof PackageManager.NameNotFoundException) {
                throw ((PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof RuntimeException) {
                throw ((RuntimeException) rethrow);
            }
            throw new RuntimeException(rethrow);
        }
    }

    @SystemApi
    public int setDomainVerificationUserSelection(UUID uuid, Set<String> set, boolean z) throws PackageManager.NameNotFoundException {
        validateInput(uuid, set);
        try {
            return this.mDomainVerificationManager.setDomainVerificationUserSelection(uuid.toString(), new DomainSet(set), z, this.mContext.getUserId());
        } catch (Exception e) {
            Exception rethrow = this.rethrow(e, null);
            if (rethrow instanceof PackageManager.NameNotFoundException) {
                throw ((PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof RuntimeException) {
                throw ((RuntimeException) rethrow);
            }
            throw new RuntimeException(rethrow);
        }
    }

    public DomainVerificationUserState getDomainVerificationUserState(String str) throws PackageManager.NameNotFoundException {
        try {
            return this.mDomainVerificationManager.getDomainVerificationUserState(str, this.mContext.getUserId());
        } catch (Exception e) {
            Exception rethrow = this.rethrow(e, str);
            if (rethrow instanceof PackageManager.NameNotFoundException) {
                throw ((PackageManager.NameNotFoundException) rethrow);
            }
            if (rethrow instanceof RuntimeException) {
                throw ((RuntimeException) rethrow);
            }
            throw new RuntimeException(rethrow);
        }
    }

    @SystemApi
    public SortedSet<DomainOwner> getOwnersForDomain(String str) {
        try {
            Objects.requireNonNull(str);
            final List<DomainOwner> ownersForDomain = this.mDomainVerificationManager.getOwnersForDomain(str, this.mContext.getUserId());
            Objects.requireNonNull(ownersForDomain);
            TreeSet treeSet = new TreeSet(Comparator.comparingInt(new ToIntFunction() { // from class: android.content.pm.verify.domain.DomainVerificationManager$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ownersForDomain.indexOf((DomainOwner) obj);
                }
            }));
            treeSet.addAll(ownersForDomain);
            return treeSet;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private Exception rethrow(Exception exc, String str) {
        if (exc instanceof ServiceSpecificException) {
            int i = ((ServiceSpecificException) exc).errorCode;
            if (str == null) {
                str = exc.getMessage();
            }
            if (i == 1) {
                return new PackageManager.NameNotFoundException(str);
            }
        } else if (exc instanceof RemoteException) {
            return ((RemoteException) exc).rethrowFromSystemServer();
        }
        return exc;
    }

    private void validateInput(UUID uuid, Set<String> set) {
        if (uuid == null) {
            throw new IllegalArgumentException("domainSetId cannot be null");
        }
        if (CollectionUtils.isEmpty(set)) {
            throw new IllegalArgumentException("Provided domain set cannot be empty");
        }
    }
}
