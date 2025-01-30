package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.app.BroadcastOptions;
import android.app.admin.BooleanPolicyValue;
import android.app.admin.DevicePolicyIdentifiers;
import android.app.admin.DevicePolicyState;
import android.app.admin.IntentFilterPolicyKey;
import android.app.admin.PolicyKey;
import android.app.admin.PolicyValue;
import android.app.admin.UserRestrictionPolicyKey;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.TelephonyManager;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.utils.Slogf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class DevicePolicyEngine {
  public static final String CELLULAR_2G_USER_RESTRICTION_ID =
      DevicePolicyIdentifiers.getIdentifierForUserRestriction("no_cellular_2g");
  public boolean isSimplifiedDump = true;
  public final Context mContext;
  public final DeviceAdminServiceController mDeviceAdminServiceController;
  public final SparseArray mEnforcingAdmins;
  public final Map mGlobalPolicies;
  public final SparseArray mLocalPolicies;
  public final Object mLock;
  public final UserManager mUserManager;

  public DevicePolicyEngine(
      Context context, DeviceAdminServiceController deviceAdminServiceController, Object obj) {
    Objects.requireNonNull(context);
    this.mContext = context;
    Objects.requireNonNull(deviceAdminServiceController);
    this.mDeviceAdminServiceController = deviceAdminServiceController;
    Objects.requireNonNull(obj);
    this.mLock = obj;
    this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    this.mLocalPolicies = new SparseArray();
    this.mGlobalPolicies = new HashMap();
    this.mEnforcingAdmins = new SparseArray();
  }

  public final void maybeForceEnforcementRefreshLocked(PolicyDefinition policyDefinition) {
    try {
      if (shouldForceEnforcementRefresh(policyDefinition)) {
        forceEnforcementRefreshLocked(policyDefinition);
      }
    } catch (Throwable th) {
      Log.e("DevicePolicyEngine", "Exception throw during maybeForceEnforcementRefreshLocked", th);
    }
  }

  public final boolean shouldForceEnforcementRefresh(PolicyDefinition policyDefinition) {
    PolicyKey policyKey;
    return (policyDefinition == null
            || (policyKey = policyDefinition.getPolicyKey()) == null
            || !(policyKey instanceof UserRestrictionPolicyKey))
        ? false
        : true;
  }

  public final void forceEnforcementRefreshLocked(final PolicyDefinition policyDefinition) {
    Binder.withCleanCallingIdentity(
        new FunctionalUtils.ThrowingRunnable() { // from class:
          // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda4
          public final void runOrThrow() {
            DevicePolicyEngine.this.lambda$forceEnforcementRefreshLocked$0(policyDefinition);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$forceEnforcementRefreshLocked$0(
      PolicyDefinition policyDefinition) {
    PolicyValue booleanPolicyValue = new BooleanPolicyValue(false);
    try {
      booleanPolicyValue = getGlobalPolicyStateLocked(policyDefinition).getCurrentResolvedPolicy();
    } catch (IllegalArgumentException unused) {
    }
    enforcePolicy(policyDefinition, booleanPolicyValue, -1);
    for (UserInfo userInfo : this.mUserManager.getUsers()) {
      PolicyValue booleanPolicyValue2 = new BooleanPolicyValue(false);
      try {
        booleanPolicyValue2 =
            getLocalPolicyStateLocked(policyDefinition, userInfo.id).getCurrentResolvedPolicy();
      } catch (IllegalArgumentException unused2) {
      }
      enforcePolicy(policyDefinition, booleanPolicyValue2, userInfo.id);
    }
  }

  public void setLocalPolicy(
      PolicyDefinition policyDefinition,
      EnforcingAdmin enforcingAdmin,
      PolicyValue policyValue,
      int i,
      boolean z) {
    boolean addPolicy;
    Objects.requireNonNull(policyDefinition);
    Objects.requireNonNull(enforcingAdmin);
    synchronized (this.mLock) {
      PolicyState localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, i);
      if (policyDefinition.isNonCoexistablePolicy()) {
        setNonCoexistableLocalPolicyLocked(
            policyDefinition, localPolicyStateLocked, enforcingAdmin, policyValue, i, z);
        return;
      }
      if (hasGlobalPolicyLocked(policyDefinition)) {
        addPolicy =
            localPolicyStateLocked.addPolicy(
                enforcingAdmin,
                policyValue,
                getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins());
      } else {
        addPolicy = localPolicyStateLocked.addPolicy(enforcingAdmin, policyValue);
      }
      if (!z) {
        maybeForceEnforcementRefreshLocked(policyDefinition);
        if (addPolicy) {
          onLocalPolicyChangedLocked(policyDefinition, enforcingAdmin, i);
        }
        boolean equals =
            Objects.equals(localPolicyStateLocked.getCurrentResolvedPolicy(), policyValue);
        int i2 = 0;
        if (!equals
            && policyDefinition
                .getPolicyKey()
                .getIdentifier()
                .equals("userControlDisabledPackages")) {
          PolicyValue currentResolvedPolicy = localPolicyStateLocked.getCurrentResolvedPolicy();
          equals =
              (currentResolvedPolicy == null
                      || policyValue == null
                      || !((Set) currentResolvedPolicy.getValue())
                          .containsAll((Collection) policyValue.getValue()))
                  ? false
                  : true;
        }
        if (!equals) {
          i2 = 1;
        }
        sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, i2, i);
      }
      updateDeviceAdminServiceOnPolicyAddLocked(enforcingAdmin);
      write();
      applyToInheritableProfiles(policyDefinition, enforcingAdmin, policyValue, i);
    }
  }

  public final void setNonCoexistableLocalPolicyLocked(
      PolicyDefinition policyDefinition,
      PolicyState policyState,
      EnforcingAdmin enforcingAdmin,
      PolicyValue policyValue,
      int i,
      boolean z) {
    if (policyValue == null) {
      policyState.removePolicy(enforcingAdmin);
    } else {
      policyState.addPolicy(enforcingAdmin, policyValue);
    }
    if (!z) {
      enforcePolicy(policyDefinition, policyValue, i);
    }
    if (policyState.getPoliciesSetByAdmins().isEmpty()) {
      removeLocalPolicyStateLocked(policyDefinition, i);
    }
    updateDeviceAdminServiceOnPolicyAddLocked(enforcingAdmin);
    write();
  }

  public void setLocalPolicy(
      PolicyDefinition policyDefinition,
      EnforcingAdmin enforcingAdmin,
      PolicyValue policyValue,
      int i) {
    setLocalPolicy(policyDefinition, enforcingAdmin, policyValue, i, false);
  }

  public void removeLocalPolicy(
      PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin, int i) {
    boolean removePolicy;
    Objects.requireNonNull(policyDefinition);
    Objects.requireNonNull(enforcingAdmin);
    synchronized (this.mLock) {
      maybeForceEnforcementRefreshLocked(policyDefinition);
      if (hasLocalPolicyLocked(policyDefinition, i)) {
        PolicyState localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, i);
        if (policyDefinition.isNonCoexistablePolicy()) {
          setNonCoexistableLocalPolicyLocked(
              policyDefinition, localPolicyStateLocked, enforcingAdmin, null, i, false);
          return;
        }
        if (hasGlobalPolicyLocked(policyDefinition)) {
          removePolicy =
              localPolicyStateLocked.removePolicy(
                  enforcingAdmin,
                  getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins());
        } else {
          removePolicy = localPolicyStateLocked.removePolicy(enforcingAdmin);
        }
        if (removePolicy) {
          onLocalPolicyChangedLocked(policyDefinition, enforcingAdmin, i);
        }
        sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 2, i);
        if (localPolicyStateLocked.getPoliciesSetByAdmins().isEmpty()) {
          removeLocalPolicyStateLocked(policyDefinition, i);
        }
        updateDeviceAdminServiceOnPolicyRemoveLocked(enforcingAdmin);
        write();
        applyToInheritableProfiles(policyDefinition, enforcingAdmin, null, i);
      }
    }
  }

  public final void applyToInheritableProfiles(
      final PolicyDefinition policyDefinition,
      final EnforcingAdmin enforcingAdmin,
      final PolicyValue policyValue,
      final int i) {
    if (policyDefinition.isInheritable()) {
      Binder.withCleanCallingIdentity(
          new FunctionalUtils.ThrowingRunnable() { // from class:
            // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda1
            public final void runOrThrow() {
              DevicePolicyEngine.this.lambda$applyToInheritableProfiles$1(
                  i, policyValue, policyDefinition, enforcingAdmin);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$applyToInheritableProfiles$1(
      int i,
      PolicyValue policyValue,
      PolicyDefinition policyDefinition,
      EnforcingAdmin enforcingAdmin) {
    for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
      int identifier = userInfo.getUserHandle().getIdentifier();
      if (isProfileOfUser(identifier, i) && isInheritDevicePolicyFromParent(userInfo)) {
        if (policyValue != null) {
          setLocalPolicy(policyDefinition, enforcingAdmin, policyValue, identifier);
        } else {
          removeLocalPolicy(policyDefinition, enforcingAdmin, identifier);
        }
      }
    }
  }

  public final boolean isProfileOfUser(int i, int i2) {
    UserInfo profileParent = this.mUserManager.getProfileParent(i);
    return (i == i2 || profileParent == null || profileParent.getUserHandle().getIdentifier() != i2)
        ? false
        : true;
  }

  public final boolean isInheritDevicePolicyFromParent(UserInfo userInfo) {
    return this.mUserManager.getUserProperties(userInfo.getUserHandle()) != null
        && this.mUserManager.getUserProperties(userInfo.getUserHandle()).getInheritDevicePolicy()
            == 1;
  }

  public final void onLocalPolicyChangedLocked(
      PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin, int i) {
    PolicyState localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, i);
    enforcePolicy(policyDefinition, localPolicyStateLocked.getCurrentResolvedPolicy(), i);
    sendPolicyChangedToAdminsLocked(localPolicyStateLocked, enforcingAdmin, policyDefinition, i);
    if (hasGlobalPolicyLocked(policyDefinition)) {
      sendPolicyChangedToAdminsLocked(
          getGlobalPolicyStateLocked(policyDefinition), enforcingAdmin, policyDefinition, i);
    }
    sendDevicePolicyChangedToSystem(i);
  }

  public void setGlobalPolicy(
      PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin, PolicyValue policyValue) {
    setGlobalPolicy(policyDefinition, enforcingAdmin, policyValue, false);
  }

  public void setGlobalPolicy(
      PolicyDefinition policyDefinition,
      EnforcingAdmin enforcingAdmin,
      PolicyValue policyValue,
      boolean z) {
    Objects.requireNonNull(policyDefinition);
    Objects.requireNonNull(enforcingAdmin);
    Objects.requireNonNull(policyValue);
    synchronized (this.mLock) {
      if (checkFor2gFailure(policyDefinition, enforcingAdmin)) {
        Log.i(
            "DevicePolicyEngine",
            "Device does not support capabilities required to disable 2g. Not setting global policy"
                + " state.");
        return;
      }
      PolicyState globalPolicyStateLocked = getGlobalPolicyStateLocked(policyDefinition);
      boolean addPolicy = globalPolicyStateLocked.addPolicy(enforcingAdmin, policyValue);
      boolean applyGlobalPolicyOnUsersWithLocalPoliciesLocked =
          applyGlobalPolicyOnUsersWithLocalPoliciesLocked(
              policyDefinition, enforcingAdmin, policyValue, z);
      if (!z) {
        maybeForceEnforcementRefreshLocked(policyDefinition);
        if (addPolicy) {
          onGlobalPolicyChangedLocked(policyDefinition, enforcingAdmin);
        }
        boolean equals =
            Objects.equals(globalPolicyStateLocked.getCurrentResolvedPolicy(), policyValue);
        if (!equals
            && policyDefinition
                .getPolicyKey()
                .getIdentifier()
                .equals("userControlDisabledPackages")) {
          PolicyValue currentResolvedPolicy = globalPolicyStateLocked.getCurrentResolvedPolicy();
          equals =
              currentResolvedPolicy != null
                  && ((Set) currentResolvedPolicy.getValue())
                      .containsAll((Collection) policyValue.getValue());
        }
        sendPolicyResultToAdmin(
            enforcingAdmin,
            policyDefinition,
            equals && applyGlobalPolicyOnUsersWithLocalPoliciesLocked ? 0 : 1,
            -1);
      }
      updateDeviceAdminServiceOnPolicyAddLocked(enforcingAdmin);
      write();
    }
  }

  public void removeGlobalPolicy(PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin) {
    Objects.requireNonNull(policyDefinition);
    Objects.requireNonNull(enforcingAdmin);
    synchronized (this.mLock) {
      PolicyState globalPolicyStateLocked = getGlobalPolicyStateLocked(policyDefinition);
      boolean removePolicy = globalPolicyStateLocked.removePolicy(enforcingAdmin);
      maybeForceEnforcementRefreshLocked(policyDefinition);
      if (removePolicy) {
        onGlobalPolicyChangedLocked(policyDefinition, enforcingAdmin);
      }
      applyGlobalPolicyOnUsersWithLocalPoliciesLocked(
          policyDefinition, enforcingAdmin, null, false);
      sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 2, -1);
      if (globalPolicyStateLocked.getPoliciesSetByAdmins().isEmpty()) {
        removeGlobalPolicyStateLocked(policyDefinition);
      }
      updateDeviceAdminServiceOnPolicyRemoveLocked(enforcingAdmin);
      write();
    }
  }

  public final void onGlobalPolicyChangedLocked(
      PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin) {
    PolicyState globalPolicyStateLocked = getGlobalPolicyStateLocked(policyDefinition);
    enforcePolicy(policyDefinition, globalPolicyStateLocked.getCurrentResolvedPolicy(), -1);
    sendPolicyChangedToAdminsLocked(globalPolicyStateLocked, enforcingAdmin, policyDefinition, -1);
    sendDevicePolicyChangedToSystem(-1);
  }

  public final boolean applyGlobalPolicyOnUsersWithLocalPoliciesLocked(
      PolicyDefinition policyDefinition,
      EnforcingAdmin enforcingAdmin,
      PolicyValue policyValue,
      boolean z) {
    boolean equals;
    if (policyDefinition.isGlobalOnlyPolicy()) {
      return true;
    }
    boolean z2 = true;
    for (int i = 0; i < this.mLocalPolicies.size(); i++) {
      int keyAt = this.mLocalPolicies.keyAt(i);
      if (hasLocalPolicyLocked(policyDefinition, keyAt)) {
        PolicyState localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, keyAt);
        if (localPolicyStateLocked.resolvePolicy(
                getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins())
            && !z) {
          enforcePolicy(policyDefinition, localPolicyStateLocked.getCurrentResolvedPolicy(), keyAt);
          sendPolicyChangedToAdminsLocked(
              localPolicyStateLocked, enforcingAdmin, policyDefinition, keyAt);
        }
        if (policyDefinition.getPolicyKey().getIdentifier().equals("userControlDisabledPackages")) {
          if (!Objects.equals(policyValue, localPolicyStateLocked.getCurrentResolvedPolicy())) {
            PolicyValue currentResolvedPolicy = localPolicyStateLocked.getCurrentResolvedPolicy();
            equals =
                (currentResolvedPolicy == null
                        || policyValue == null
                        || !((Set) currentResolvedPolicy.getValue())
                            .containsAll((Collection) policyValue.getValue()))
                    ? false
                    : true;
          }
        } else {
          equals = Objects.equals(policyValue, localPolicyStateLocked.getCurrentResolvedPolicy());
        }
        z2 &= equals;
      }
    }
    return z2;
  }

  public Object getResolvedPolicy(PolicyDefinition policyDefinition, int i) {
    Object obj;
    PolicyValue currentResolvedPolicy;
    Objects.requireNonNull(policyDefinition);
    synchronized (this.mLock) {
      obj = null;
      if (hasLocalPolicyLocked(policyDefinition, i)) {
        currentResolvedPolicy =
            getLocalPolicyStateLocked(policyDefinition, i).getCurrentResolvedPolicy();
      } else {
        currentResolvedPolicy =
            hasGlobalPolicyLocked(policyDefinition)
                ? getGlobalPolicyStateLocked(policyDefinition).getCurrentResolvedPolicy()
                : null;
      }
      if (currentResolvedPolicy != null) {
        obj = currentResolvedPolicy.getValue();
      }
    }
    return obj;
  }

  public Object getLocalPolicySetByAdmin(
      PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin, int i) {
    Objects.requireNonNull(policyDefinition);
    Objects.requireNonNull(enforcingAdmin);
    synchronized (this.mLock) {
      Object obj = null;
      if (!hasLocalPolicyLocked(policyDefinition, i)) {
        return null;
      }
      PolicyValue policyValue =
          (PolicyValue)
              getLocalPolicyStateLocked(policyDefinition, i)
                  .getPoliciesSetByAdmins()
                  .get(enforcingAdmin);
      if (policyValue != null) {
        obj = policyValue.getValue();
      }
      return obj;
    }
  }

  public Object getGlobalPolicySetByAdmin(
      PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin) {
    Objects.requireNonNull(policyDefinition);
    Objects.requireNonNull(enforcingAdmin);
    synchronized (this.mLock) {
      Object obj = null;
      if (!hasGlobalPolicyLocked(policyDefinition)) {
        return null;
      }
      PolicyValue policyValue =
          (PolicyValue)
              getGlobalPolicyStateLocked(policyDefinition)
                  .getPoliciesSetByAdmins()
                  .get(enforcingAdmin);
      if (policyValue != null) {
        obj = policyValue.getValue();
      }
      return obj;
    }
  }

  public LinkedHashMap getLocalPoliciesSetByAdmins(PolicyDefinition policyDefinition, int i) {
    Objects.requireNonNull(policyDefinition);
    synchronized (this.mLock) {
      if (!hasLocalPolicyLocked(policyDefinition, i)) {
        return new LinkedHashMap();
      }
      return getLocalPolicyStateLocked(policyDefinition, i).getPoliciesSetByAdmins();
    }
  }

  public LinkedHashMap getGlobalPoliciesSetByAdmins(PolicyDefinition policyDefinition) {
    Objects.requireNonNull(policyDefinition);
    synchronized (this.mLock) {
      if (!hasGlobalPolicyLocked(policyDefinition)) {
        return new LinkedHashMap();
      }
      return getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins();
    }
  }

  public Set getLocalPolicyKeysSetByAdmin(
      PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin, int i) {
    Objects.requireNonNull(policyDefinition);
    Objects.requireNonNull(enforcingAdmin);
    synchronized (this.mLock) {
      if (!policyDefinition.isGlobalOnlyPolicy() && this.mLocalPolicies.contains(i)) {
        HashSet hashSet = new HashSet();
        for (PolicyKey policyKey : ((Map) this.mLocalPolicies.get(i)).keySet()) {
          if (policyKey.hasSameIdentifierAs(policyDefinition.getPolicyKey())
              && ((PolicyState) ((Map) this.mLocalPolicies.get(i)).get(policyKey))
                  .getPoliciesSetByAdmins()
                  .containsKey(enforcingAdmin)) {
            hashSet.add(policyKey);
          }
        }
        return hashSet;
      }
      return Set.of();
    }
  }

  public Set getLocalPolicyKeysSetByAllAdmins(PolicyDefinition policyDefinition, int i) {
    Objects.requireNonNull(policyDefinition);
    synchronized (this.mLock) {
      if (!policyDefinition.isGlobalOnlyPolicy() && this.mLocalPolicies.contains(i)) {
        HashSet hashSet = new HashSet();
        for (PolicyKey policyKey : ((Map) this.mLocalPolicies.get(i)).keySet()) {
          if (policyKey.hasSameIdentifierAs(policyDefinition.getPolicyKey())) {
            hashSet.add(policyKey);
          }
        }
        return hashSet;
      }
      return Set.of();
    }
  }

  public Set getUserRestrictionPolicyKeysForAdmin(EnforcingAdmin enforcingAdmin, int i) {
    Objects.requireNonNull(enforcingAdmin);
    synchronized (this.mLock) {
      if (i == -1) {
        return getUserRestrictionPolicyKeysForAdminLocked(this.mGlobalPolicies, enforcingAdmin);
      }
      if (!this.mLocalPolicies.contains(i)) {
        return Set.of();
      }
      return getUserRestrictionPolicyKeysForAdminLocked(
          (Map) this.mLocalPolicies.get(i), enforcingAdmin);
    }
  }

  public void transferPolicies(EnforcingAdmin enforcingAdmin, EnforcingAdmin enforcingAdmin2) {
    synchronized (this.mLock) {
      Iterator it = new HashSet(this.mGlobalPolicies.keySet()).iterator();
      while (it.hasNext()) {
        PolicyState policyState = (PolicyState) this.mGlobalPolicies.get((PolicyKey) it.next());
        if (policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
          setGlobalPolicy(
              policyState.getPolicyDefinition(),
              enforcingAdmin2,
              (PolicyValue) policyState.getPoliciesSetByAdmins().get(enforcingAdmin));
        }
      }
      for (int i = 0; i < this.mLocalPolicies.size(); i++) {
        int keyAt = this.mLocalPolicies.keyAt(i);
        Iterator it2 = new HashSet(((Map) this.mLocalPolicies.get(keyAt)).keySet()).iterator();
        while (it2.hasNext()) {
          PolicyState policyState2 =
              (PolicyState) ((Map) this.mLocalPolicies.get(keyAt)).get((PolicyKey) it2.next());
          if (policyState2.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
            setLocalPolicy(
                policyState2.getPolicyDefinition(),
                enforcingAdmin2,
                (PolicyValue) policyState2.getPoliciesSetByAdmins().get(enforcingAdmin),
                keyAt);
          }
        }
      }
    }
    removePoliciesForAdmin(enforcingAdmin);
  }

  public final Set getUserRestrictionPolicyKeysForAdminLocked(
      Map map, EnforcingAdmin enforcingAdmin) {
    PolicyValue policyValue;
    HashSet hashSet = new HashSet();
    for (UserRestrictionPolicyKey userRestrictionPolicyKey : map.keySet()) {
      if (((PolicyState) map.get(userRestrictionPolicyKey))
              .getPolicyDefinition()
              .isUserRestrictionPolicy()
          && (policyValue =
                  (PolicyValue)
                      ((PolicyState) map.get(userRestrictionPolicyKey))
                          .getPoliciesSetByAdmins()
                          .get(enforcingAdmin))
              != null
          && ((Boolean) policyValue.getValue()).booleanValue()) {
        hashSet.add(userRestrictionPolicyKey);
      }
    }
    return hashSet;
  }

  public final boolean hasLocalPolicyLocked(PolicyDefinition policyDefinition, int i) {
    if (!policyDefinition.isGlobalOnlyPolicy()
        && this.mLocalPolicies.contains(i)
        && ((Map) this.mLocalPolicies.get(i)).containsKey(policyDefinition.getPolicyKey())) {
      return !((PolicyState)
              ((Map) this.mLocalPolicies.get(i)).get(policyDefinition.getPolicyKey()))
          .getPoliciesSetByAdmins()
          .isEmpty();
    }
    return false;
  }

  public final boolean hasGlobalPolicyLocked(PolicyDefinition policyDefinition) {
    if (!policyDefinition.isLocalOnlyPolicy()
        && this.mGlobalPolicies.containsKey(policyDefinition.getPolicyKey())) {
      return !((PolicyState) this.mGlobalPolicies.get(policyDefinition.getPolicyKey()))
          .getPoliciesSetByAdmins()
          .isEmpty();
    }
    return false;
  }

  public final PolicyState getLocalPolicyStateLocked(PolicyDefinition policyDefinition, int i) {
    if (policyDefinition.isGlobalOnlyPolicy()) {
      throw new IllegalArgumentException(
          policyDefinition.getPolicyKey() + " is a global only policy.");
    }
    if (!this.mLocalPolicies.contains(i)) {
      this.mLocalPolicies.put(i, new HashMap());
    }
    if (!((Map) this.mLocalPolicies.get(i)).containsKey(policyDefinition.getPolicyKey())) {
      ((Map) this.mLocalPolicies.get(i))
          .put(policyDefinition.getPolicyKey(), new PolicyState(policyDefinition));
    }
    return getPolicyStateLocked((Map) this.mLocalPolicies.get(i), policyDefinition);
  }

  public final void removeLocalPolicyStateLocked(PolicyDefinition policyDefinition, int i) {
    if (this.mLocalPolicies.contains(i)) {
      ((Map) this.mLocalPolicies.get(i)).remove(policyDefinition.getPolicyKey());
    }
  }

  public final PolicyState getGlobalPolicyStateLocked(PolicyDefinition policyDefinition) {
    if (policyDefinition.isLocalOnlyPolicy()) {
      throw new IllegalArgumentException(
          policyDefinition.getPolicyKey() + " is a local only policy.");
    }
    if (!this.mGlobalPolicies.containsKey(policyDefinition.getPolicyKey())) {
      this.mGlobalPolicies.put(policyDefinition.getPolicyKey(), new PolicyState(policyDefinition));
    }
    return getPolicyStateLocked(this.mGlobalPolicies, policyDefinition);
  }

  public final void removeGlobalPolicyStateLocked(PolicyDefinition policyDefinition) {
    this.mGlobalPolicies.remove(policyDefinition.getPolicyKey());
  }

  public static PolicyState getPolicyStateLocked(Map map, PolicyDefinition policyDefinition) {
    try {
      return (PolicyState) map.get(policyDefinition.getPolicyKey());
    } catch (ClassCastException unused) {
      throw new IllegalArgumentException();
    }
  }

  public final void enforcePolicy(
      PolicyDefinition policyDefinition, PolicyValue policyValue, int i) {
    policyDefinition.enforcePolicy(
        policyValue == null ? null : policyValue.getValue(), this.mContext, i);
  }

  public final void sendDevicePolicyChangedToSystem(final int i) {
    final Intent intent = new Intent("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
    intent.setFlags(1073741824);
    final Bundle bundle =
        new BroadcastOptions().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
    Binder.withCleanCallingIdentity(
        new FunctionalUtils.ThrowingRunnable() { // from class:
          // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda2
          public final void runOrThrow() {
            DevicePolicyEngine.this.lambda$sendDevicePolicyChangedToSystem$2(intent, i, bundle);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendDevicePolicyChangedToSystem$2(
      Intent intent, int i, Bundle bundle) {
    this.mContext.sendBroadcastAsUser(intent, new UserHandle(i), null, bundle);
  }

  public final void sendPolicyResultToAdmin(
      final EnforcingAdmin enforcingAdmin,
      final PolicyDefinition policyDefinition,
      final int i,
      final int i2) {
    final Intent intent = new Intent("android.app.admin.action.DEVICE_POLICY_SET_RESULT");
    intent.setPackage(enforcingAdmin.getPackageName());
    Binder.withCleanCallingIdentity(
        new FunctionalUtils.ThrowingRunnable() { // from class:
          // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda0
          public final void runOrThrow() {
            DevicePolicyEngine.this.lambda$sendPolicyResultToAdmin$3(
                intent, enforcingAdmin, policyDefinition, i2, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendPolicyResultToAdmin$3(
      Intent intent,
      EnforcingAdmin enforcingAdmin,
      PolicyDefinition policyDefinition,
      int i,
      int i2) {
    List queryBroadcastReceiversAsUser =
        this.mContext
            .getPackageManager()
            .queryBroadcastReceiversAsUser(
                intent, PackageManager.ResolveInfoFlags.of(2L), enforcingAdmin.getUserId());
    if (queryBroadcastReceiversAsUser.isEmpty()) {
      Log.i(
          "DevicePolicyEngine",
          "Couldn't find any receivers that handle ACTION_DEVICE_POLICY_SET_RESULT in package "
              + enforcingAdmin.getPackageName());
      return;
    }
    Bundle bundle = new Bundle();
    policyDefinition.getPolicyKey().writeToBundle(bundle);
    bundle.putInt(
        "android.app.admin.extra.POLICY_TARGET_USER_ID",
        getTargetUser(enforcingAdmin.getUserId(), i));
    bundle.putInt("android.app.admin.extra.POLICY_UPDATE_RESULT_KEY", i2);
    intent.putExtras(bundle);
    maybeSendIntentToAdminReceivers(
        intent, UserHandle.of(enforcingAdmin.getUserId()), queryBroadcastReceiversAsUser);
  }

  public final void sendPolicyChangedToAdminsLocked(
      PolicyState policyState,
      EnforcingAdmin enforcingAdmin,
      PolicyDefinition policyDefinition,
      int i) {
    for (EnforcingAdmin enforcingAdmin2 : policyState.getPoliciesSetByAdmins().keySet()) {
      if (!enforcingAdmin2.equals(enforcingAdmin)) {
        maybeSendOnPolicyChanged(
            enforcingAdmin2,
            policyDefinition,
            !Objects.equals(
                    policyState.getPoliciesSetByAdmins().get(enforcingAdmin2),
                    policyState.getCurrentResolvedPolicy())
                ? 1
                : 0,
            i);
      }
    }
  }

  public final void maybeSendOnPolicyChanged(
      final EnforcingAdmin enforcingAdmin,
      final PolicyDefinition policyDefinition,
      final int i,
      final int i2) {
    final Intent intent = new Intent("android.app.admin.action.DEVICE_POLICY_CHANGED");
    intent.setPackage(enforcingAdmin.getPackageName());
    Binder.withCleanCallingIdentity(
        new FunctionalUtils.ThrowingRunnable() { // from class:
          // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda5
          public final void runOrThrow() {
            DevicePolicyEngine.this.lambda$maybeSendOnPolicyChanged$4(
                intent, enforcingAdmin, policyDefinition, i2, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$maybeSendOnPolicyChanged$4(
      Intent intent,
      EnforcingAdmin enforcingAdmin,
      PolicyDefinition policyDefinition,
      int i,
      int i2) {
    List queryBroadcastReceiversAsUser =
        this.mContext
            .getPackageManager()
            .queryBroadcastReceiversAsUser(
                intent, PackageManager.ResolveInfoFlags.of(2L), enforcingAdmin.getUserId());
    if (queryBroadcastReceiversAsUser.isEmpty()) {
      Log.i(
          "DevicePolicyEngine",
          "Couldn't find any receivers that handle ACTION_DEVICE_POLICY_CHANGED in package "
              + enforcingAdmin.getPackageName());
      return;
    }
    Bundle bundle = new Bundle();
    policyDefinition.getPolicyKey().writeToBundle(bundle);
    bundle.putInt(
        "android.app.admin.extra.POLICY_TARGET_USER_ID",
        getTargetUser(enforcingAdmin.getUserId(), i));
    bundle.putInt("android.app.admin.extra.POLICY_UPDATE_RESULT_KEY", i2);
    intent.putExtras(bundle);
    intent.addFlags(268435456);
    maybeSendIntentToAdminReceivers(
        intent, UserHandle.of(enforcingAdmin.getUserId()), queryBroadcastReceiversAsUser);
  }

  public final void maybeSendIntentToAdminReceivers(
      Intent intent, UserHandle userHandle, List list) {
    Iterator it = list.iterator();
    while (it.hasNext()) {
      ResolveInfo resolveInfo = (ResolveInfo) it.next();
      if (!"android.permission.BIND_DEVICE_ADMIN".equals(resolveInfo.activityInfo.permission)) {
        Log.w(
            "DevicePolicyEngine",
            "Receiver "
                + resolveInfo.activityInfo
                + " is not protected by BIND_DEVICE_ADMIN permission!");
      } else {
        this.mContext.sendBroadcastAsUser(intent, userHandle);
      }
    }
  }

  public final int getTargetUser(int i, int i2) {
    if (i2 == -1) {
      return -3;
    }
    if (i == i2) {
      return -1;
    }
    return getProfileParentId(i) == i2 ? -2 : -3;
  }

  public final int getProfileParentId(final int i) {
    return ((Integer)
            Binder.withCleanCallingIdentity(
                new FunctionalUtils.ThrowingSupplier() { // from class:
                  // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda6
                  public final Object getOrThrow() {
                    Integer lambda$getProfileParentId$5;
                    lambda$getProfileParentId$5 =
                        DevicePolicyEngine.this.lambda$getProfileParentId$5(i);
                    return lambda$getProfileParentId$5;
                  }
                }))
        .intValue();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Integer lambda$getProfileParentId$5(int i) {
    UserInfo profileParent = this.mUserManager.getProfileParent(i);
    if (profileParent != null) {
      i = profileParent.id;
    }
    return Integer.valueOf(i);
  }

  public final void updateDeviceAdminsServicesForUser(int i, boolean z, String str) {
    if (!z) {
      this.mDeviceAdminServiceController.stopServicesForUser(i, str);
      return;
    }
    for (EnforcingAdmin enforcingAdmin : getEnforcingAdminsOnUser(i)) {
      if (!enforcingAdmin.hasAuthority("enterprise")) {
        this.mDeviceAdminServiceController.startServiceForAdmin(
            enforcingAdmin.getPackageName(), i, str);
      }
    }
  }

  public void handleStartUser(int i) {
    updateDeviceAdminsServicesForUser(i, true, "start-user");
  }

  public void handleUnlockUser(int i) {
    updateDeviceAdminsServicesForUser(i, true, "unlock-user");
  }

  public void handleStopUser(int i) {
    updateDeviceAdminsServicesForUser(i, false, "stop-user");
  }

  public void handlePackageChanged(final String str, final int i, final String str2) {
    Binder.withCleanCallingIdentity(
        new FunctionalUtils.ThrowingRunnable() { // from class:
          // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda3
          public final void runOrThrow() {
            DevicePolicyEngine.this.lambda$handlePackageChanged$6(i, str2, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handlePackageChanged$6(int i, String str, String str2) {
    Set<EnforcingAdmin> enforcingAdminsOnUser = getEnforcingAdminsOnUser(i);
    if (str != null) {
      for (EnforcingAdmin enforcingAdmin : enforcingAdminsOnUser) {
        if (str.equals(enforcingAdmin.getPackageName())) {
          removePoliciesForAdmin(enforcingAdmin);
          return;
        }
      }
    }
    for (EnforcingAdmin enforcingAdmin2 : enforcingAdminsOnUser) {
      if (str2 == null || str2.equals(enforcingAdmin2.getPackageName())) {
        if (!isPackageInstalled(enforcingAdmin2.getPackageName(), i)) {
          Slogf.m94i(
              "DevicePolicyEngine",
              String.format(
                  "Admin package %s not found for user %d, removing admin policies",
                  enforcingAdmin2.getPackageName(), Integer.valueOf(i)));
          removePoliciesForAdmin(enforcingAdmin2);
          return;
        }
      }
    }
    if (str2 != null) {
      updateDeviceAdminServiceOnPackageChanged(str2, i);
      removePersistentPreferredActivityPoliciesForPackage(str2, i);
    }
  }

  public final void removePersistentPreferredActivityPoliciesForPackage(String str, int i) {
    for (IntentFilterPolicyKey intentFilterPolicyKey :
        getLocalPolicyKeysSetByAllAdmins(
            PolicyDefinition.GENERIC_PERSISTENT_PREFERRED_ACTIVITY, i)) {
      if (!(intentFilterPolicyKey instanceof IntentFilterPolicyKey)) {
        throw new IllegalStateException(
            "PolicyKey for PERSISTENT_PREFERRED_ACTIVITY is not of type IntentFilterPolicyKey");
      }
      IntentFilter intentFilter = intentFilterPolicyKey.getIntentFilter();
      Objects.requireNonNull(intentFilter);
      PolicyDefinition PERSISTENT_PREFERRED_ACTIVITY =
          PolicyDefinition.PERSISTENT_PREFERRED_ACTIVITY(intentFilter);
      LinkedHashMap localPoliciesSetByAdmins =
          getLocalPoliciesSetByAdmins(PERSISTENT_PREFERRED_ACTIVITY, i);
      IPackageManager packageManager = AppGlobals.getPackageManager();
      for (EnforcingAdmin enforcingAdmin : localPoliciesSetByAdmins.keySet()) {
        if (((PolicyValue) localPoliciesSetByAdmins.get(enforcingAdmin)).getValue() != null
            && ((ComponentName)
                    ((PolicyValue) localPoliciesSetByAdmins.get(enforcingAdmin)).getValue())
                .getPackageName()
                .equals(str)) {
          try {
            if (packageManager.getPackageInfo(str, 0L, i) == null
                || packageManager.getActivityInfo(
                        (ComponentName)
                            ((PolicyValue) localPoliciesSetByAdmins.get(enforcingAdmin)).getValue(),
                        0L,
                        i)
                    == null) {
              Slogf.m90e(
                  "DevicePolicyEngine",
                  String.format(
                      "Persistent preferred activity in package %s not found for user %d, removing"
                          + " policy for admin",
                      str, Integer.valueOf(i)));
              removeLocalPolicy(PERSISTENT_PREFERRED_ACTIVITY, enforcingAdmin, i);
            }
          } catch (RemoteException e) {
            Slogf.wtf("DevicePolicyEngine", "Error handling package changes", e);
          }
        }
      }
    }
  }

  public final boolean isPackageInstalled(String str, int i) {
    try {
      return AppGlobals.getPackageManager().getPackageInfo(str, 0L, i) != null;
    } catch (RemoteException e) {
      Slogf.wtf("DevicePolicyEngine", "Error handling package changes", e);
      return true;
    }
  }

  public void handleUserRemoved(int i) {
    removeLocalPoliciesForUser(i);
    removePoliciesForAdminsOnUser(i);
  }

  public void handleUserCreated(UserInfo userInfo) {
    enforcePoliciesOnInheritableProfilesIfApplicable(userInfo);
  }

  public void handleRoleChanged(String str, int i) {
    if ("android.app.role.SYSTEM_FINANCED_DEVICE_CONTROLLER".equals(str)) {
      String roleAuthorityOf = EnforcingAdmin.getRoleAuthorityOf(str);
      for (EnforcingAdmin enforcingAdmin : getEnforcingAdminsOnUser(i)) {
        if (enforcingAdmin.hasAuthority(roleAuthorityOf)) {
          enforcingAdmin.reloadRoleAuthorities();
          if (!enforcingAdmin.hasAuthority(roleAuthorityOf)) {
            removePoliciesForAdmin(enforcingAdmin);
          }
        }
      }
    }
  }

  public final void enforcePoliciesOnInheritableProfilesIfApplicable(final UserInfo userInfo) {
    if (userInfo.isProfile()) {
      Binder.withCleanCallingIdentity(
          new FunctionalUtils.ThrowingRunnable() { // from class:
            // com.android.server.devicepolicy.DevicePolicyEngine$$ExternalSyntheticLambda7
            public final void runOrThrow() {
              DevicePolicyEngine.this.lambda$enforcePoliciesOnInheritableProfilesIfApplicable$7(
                  userInfo);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$enforcePoliciesOnInheritableProfilesIfApplicable$7(
      UserInfo userInfo) {
    int i;
    UserInfo profileParent;
    UserProperties userProperties = this.mUserManager.getUserProperties(userInfo.getUserHandle());
    if (userProperties == null
        || userProperties.getInheritDevicePolicy() != 1
        || (profileParent = this.mUserManager.getProfileParent((i = userInfo.id))) == null
        || profileParent.getUserHandle().getIdentifier() == i) {
      return;
    }
    synchronized (this.mLock) {
      if (this.mLocalPolicies.contains(profileParent.getUserHandle().getIdentifier())) {
        Iterator it =
            ((Map) this.mLocalPolicies.get(profileParent.getUserHandle().getIdentifier()))
                .entrySet()
                .iterator();
        while (it.hasNext()) {
          enforcePolicyOnUserLocked(i, (PolicyState) ((Map.Entry) it.next()).getValue());
        }
      }
    }
  }

  public final void enforcePolicyOnUserLocked(int i, PolicyState policyState) {
    if (policyState.getPolicyDefinition().isInheritable()) {
      for (Map.Entry entry : policyState.getPoliciesSetByAdmins().entrySet()) {
        setLocalPolicy(
            policyState.getPolicyDefinition(),
            (EnforcingAdmin) entry.getKey(),
            (PolicyValue) entry.getValue(),
            i);
      }
    }
  }

  public DevicePolicyState getDevicePolicyState() {
    DevicePolicyState devicePolicyState;
    synchronized (this.mLock) {
      HashMap hashMap = new HashMap();
      for (int i = 0; i < this.mLocalPolicies.size(); i++) {
        UserHandle of = UserHandle.of(this.mLocalPolicies.keyAt(i));
        hashMap.put(of, new HashMap());
        for (PolicyKey policyKey : ((Map) this.mLocalPolicies.valueAt(i)).keySet()) {
          ((Map) hashMap.get(of))
              .put(
                  policyKey,
                  ((PolicyState) ((Map) this.mLocalPolicies.valueAt(i)).get(policyKey))
                      .getParcelablePolicyState());
        }
      }
      if (!this.mGlobalPolicies.isEmpty()) {
        hashMap.put(UserHandle.ALL, new HashMap());
        for (PolicyKey policyKey2 : this.mGlobalPolicies.keySet()) {
          ((Map) hashMap.get(UserHandle.ALL))
              .put(
                  policyKey2,
                  ((PolicyState) this.mGlobalPolicies.get(policyKey2)).getParcelablePolicyState());
        }
      }
      devicePolicyState = new DevicePolicyState(hashMap);
    }
    return devicePolicyState;
  }

  public void removePoliciesForAdmin(EnforcingAdmin enforcingAdmin) {
    synchronized (this.mLock) {
      Iterator it = new HashSet(this.mGlobalPolicies.keySet()).iterator();
      while (it.hasNext()) {
        PolicyState policyState = (PolicyState) this.mGlobalPolicies.get((PolicyKey) it.next());
        if (policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
          removeGlobalPolicy(policyState.getPolicyDefinition(), enforcingAdmin);
        }
      }
      for (int i = 0; i < this.mLocalPolicies.size(); i++) {
        SparseArray sparseArray = this.mLocalPolicies;
        for (PolicyKey policyKey :
            new HashSet(((Map) sparseArray.get(sparseArray.keyAt(i))).keySet())) {
          SparseArray sparseArray2 = this.mLocalPolicies;
          PolicyState policyState2 =
              (PolicyState) ((Map) sparseArray2.get(sparseArray2.keyAt(i))).get(policyKey);
          if (policyState2.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
            removeLocalPolicy(
                policyState2.getPolicyDefinition(), enforcingAdmin, this.mLocalPolicies.keyAt(i));
          }
        }
      }
    }
  }

  public final void removeLocalPoliciesForUser(int i) {
    synchronized (this.mLock) {
      if (this.mLocalPolicies.contains(i)) {
        Iterator it = new HashSet(((Map) this.mLocalPolicies.get(i)).keySet()).iterator();
        while (it.hasNext()) {
          PolicyState policyState =
              (PolicyState) ((Map) this.mLocalPolicies.get(i)).get((PolicyKey) it.next());
          Iterator it2 = new HashSet(policyState.getPoliciesSetByAdmins().keySet()).iterator();
          while (it2.hasNext()) {
            removeLocalPolicy(policyState.getPolicyDefinition(), (EnforcingAdmin) it2.next(), i);
          }
        }
        this.mLocalPolicies.remove(i);
      }
    }
  }

  public final void removePoliciesForAdminsOnUser(int i) {
    Iterator it = getEnforcingAdminsOnUser(i).iterator();
    while (it.hasNext()) {
      removePoliciesForAdmin((EnforcingAdmin) it.next());
    }
  }

  public final void updateDeviceAdminServiceOnPackageChanged(String str, int i) {
    for (EnforcingAdmin enforcingAdmin : getEnforcingAdminsOnUser(i)) {
      if (!enforcingAdmin.hasAuthority("enterprise")
          && str.equals(enforcingAdmin.getPackageName())) {
        this.mDeviceAdminServiceController.startServiceForAdmin(str, i, "package-broadcast");
      }
    }
  }

  public final void updateDeviceAdminServiceOnPolicyAddLocked(EnforcingAdmin enforcingAdmin) {
    int userId = enforcingAdmin.getUserId();
    if (this.mEnforcingAdmins.contains(userId)
        && ((Set) this.mEnforcingAdmins.get(userId)).contains(enforcingAdmin)) {
      return;
    }
    if (!this.mEnforcingAdmins.contains(enforcingAdmin.getUserId())) {
      this.mEnforcingAdmins.put(enforcingAdmin.getUserId(), new HashSet());
    }
    ((Set) this.mEnforcingAdmins.get(enforcingAdmin.getUserId())).add(enforcingAdmin);
    if (enforcingAdmin.hasAuthority("enterprise")) {
      return;
    }
    this.mDeviceAdminServiceController.startServiceForAdmin(
        enforcingAdmin.getPackageName(), userId, "policy-added");
  }

  public final void updateDeviceAdminServiceOnPolicyRemoveLocked(EnforcingAdmin enforcingAdmin) {
    if (doesAdminHavePoliciesLocked(enforcingAdmin)) {
      return;
    }
    int userId = enforcingAdmin.getUserId();
    if (this.mEnforcingAdmins.contains(userId)) {
      ((Set) this.mEnforcingAdmins.get(userId)).remove(enforcingAdmin);
      if (((Set) this.mEnforcingAdmins.get(userId)).isEmpty()) {
        this.mEnforcingAdmins.remove(enforcingAdmin.getUserId());
      }
    }
    if (enforcingAdmin.hasAuthority("enterprise")) {
      return;
    }
    this.mDeviceAdminServiceController.stopServiceForAdmin(
        enforcingAdmin.getPackageName(), userId, "policy-removed");
  }

  public final boolean doesAdminHavePoliciesLocked(EnforcingAdmin enforcingAdmin) {
    Iterator it = this.mGlobalPolicies.keySet().iterator();
    while (it.hasNext()) {
      if (((PolicyState) this.mGlobalPolicies.get((PolicyKey) it.next()))
          .getPoliciesSetByAdmins()
          .containsKey(enforcingAdmin)) {
        return true;
      }
    }
    for (int i = 0; i < this.mLocalPolicies.size(); i++) {
      SparseArray sparseArray = this.mLocalPolicies;
      for (PolicyKey policyKey : ((Map) sparseArray.get(sparseArray.keyAt(i))).keySet()) {
        SparseArray sparseArray2 = this.mLocalPolicies;
        if (((PolicyState) ((Map) sparseArray2.get(sparseArray2.keyAt(i))).get(policyKey))
            .getPoliciesSetByAdmins()
            .containsKey(enforcingAdmin)) {
          return true;
        }
      }
    }
    return false;
  }

  public final Set getEnforcingAdminsOnUser(int i) {
    Set emptySet;
    synchronized (this.mLock) {
      emptySet =
          this.mEnforcingAdmins.contains(i)
              ? (Set) this.mEnforcingAdmins.get(i)
              : Collections.emptySet();
    }
    return emptySet;
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      indentingPrintWriter.println("Local Policies: ");
      indentingPrintWriter.increaseIndent();
      for (int i = 0; i < this.mLocalPolicies.size(); i++) {
        int keyAt = this.mLocalPolicies.keyAt(i);
        indentingPrintWriter.printf("User %d:\n", new Object[] {Integer.valueOf(keyAt)});
        indentingPrintWriter.increaseIndent();
        Iterator it = ((Map) this.mLocalPolicies.get(keyAt)).keySet().iterator();
        while (it.hasNext()) {
          ((PolicyState) ((Map) this.mLocalPolicies.get(keyAt)).get((PolicyKey) it.next()))
              .dump(indentingPrintWriter);
          indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
      }
      indentingPrintWriter.decreaseIndent();
      indentingPrintWriter.println();
      indentingPrintWriter.println("Global Policies: ");
      indentingPrintWriter.increaseIndent();
      Iterator it2 = this.mGlobalPolicies.keySet().iterator();
      while (it2.hasNext()) {
        ((PolicyState) this.mGlobalPolicies.get((PolicyKey) it2.next())).dump(indentingPrintWriter);
        indentingPrintWriter.println();
      }
      indentingPrintWriter.decreaseIndent();
    }
  }

  public final void write() {
    synchronized (this.mLock) {
      Log.d("DevicePolicyEngine", "Writing device policies to file.");
      new DevicePoliciesReaderWriter().writeToFileLocked();
    }
  }

  public void load() {
    Log.d("DevicePolicyEngine", "Reading device policies from file.");
    synchronized (this.mLock) {
      clear();
      new DevicePoliciesReaderWriter().readFromFileLocked();
    }
  }

  public void reapplyAllPoliciesLocked() {
    Iterator it = this.mGlobalPolicies.keySet().iterator();
    while (it.hasNext()) {
      PolicyState policyState = (PolicyState) this.mGlobalPolicies.get((PolicyKey) it.next());
      enforcePolicy(policyState.getPolicyDefinition(), policyState.getCurrentResolvedPolicy(), -1);
    }
    for (int i = 0; i < this.mLocalPolicies.size(); i++) {
      int keyAt = this.mLocalPolicies.keyAt(i);
      Iterator it2 = ((Map) this.mLocalPolicies.get(keyAt)).keySet().iterator();
      while (it2.hasNext()) {
        PolicyState policyState2 =
            (PolicyState) ((Map) this.mLocalPolicies.get(keyAt)).get((PolicyKey) it2.next());
        enforcePolicy(
            policyState2.getPolicyDefinition(), policyState2.getCurrentResolvedPolicy(), keyAt);
      }
    }
  }

  public void clearAllPolicies() {
    clear();
    write();
  }

  public final void clear() {
    synchronized (this.mLock) {
      this.mGlobalPolicies.clear();
      this.mLocalPolicies.clear();
      this.mEnforcingAdmins.clear();
    }
  }

  public final boolean checkFor2gFailure(
      PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin) {
    boolean z;
    if (!policyDefinition.getPolicyKey().getIdentifier().equals(CELLULAR_2G_USER_RESTRICTION_ID)) {
      return false;
    }
    try {
      z =
          ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
              .isRadioInterfaceCapabilitySupported("CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK");
    } catch (IllegalStateException unused) {
      z = false;
    }
    if (z) {
      return false;
    }
    sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 4, -1);
    return true;
  }

  public class DevicePoliciesReaderWriter {
    public final File mFile;

    public DevicePoliciesReaderWriter() {
      this.mFile = new File(Environment.getDataSystemDirectory(), "device_policy_state.xml");
    }

    public void writeToFileLocked() {
      Log.d("DevicePolicyEngine", "Writing to " + this.mFile);
      AtomicFile atomicFile = new AtomicFile(this.mFile);
      FileOutputStream fileOutputStream = null;
      try {
        FileOutputStream startWrite = atomicFile.startWrite();
        try {
          TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
          resolveSerializer.startDocument((String) null, Boolean.TRUE);
          writeInner(resolveSerializer);
          resolveSerializer.endDocument();
          resolveSerializer.flush();
          atomicFile.finishWrite(startWrite);
        } catch (IOException e) {
          e = e;
          fileOutputStream = startWrite;
          Log.e("DevicePolicyEngine", "Exception when writing", e);
          if (fileOutputStream != null) {
            atomicFile.failWrite(fileOutputStream);
          }
        }
      } catch (IOException e2) {
        e = e2;
      }
    }

    public void writeInner(TypedXmlSerializer typedXmlSerializer) {
      writeLocalPoliciesInner(typedXmlSerializer);
      writeGlobalPoliciesInner(typedXmlSerializer);
      writeEnforcingAdminsInner(typedXmlSerializer);
    }

    public final void writeLocalPoliciesInner(TypedXmlSerializer typedXmlSerializer) {
      if (DevicePolicyEngine.this.mLocalPolicies != null) {
        for (int i = 0; i < DevicePolicyEngine.this.mLocalPolicies.size(); i++) {
          int keyAt = DevicePolicyEngine.this.mLocalPolicies.keyAt(i);
          for (Map.Entry entry :
              ((Map) DevicePolicyEngine.this.mLocalPolicies.get(keyAt)).entrySet()) {
            typedXmlSerializer.startTag((String) null, "local-policy-entry");
            typedXmlSerializer.attributeInt((String) null, "user-id", keyAt);
            typedXmlSerializer.startTag((String) null, "policy-key-entry");
            ((PolicyKey) entry.getKey()).saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "policy-key-entry");
            typedXmlSerializer.startTag((String) null, "policy-state-entry");
            ((PolicyState) entry.getValue()).saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "policy-state-entry");
            typedXmlSerializer.endTag((String) null, "local-policy-entry");
          }
        }
      }
    }

    public final void writeGlobalPoliciesInner(TypedXmlSerializer typedXmlSerializer) {
      if (DevicePolicyEngine.this.mGlobalPolicies != null) {
        for (Map.Entry entry : DevicePolicyEngine.this.mGlobalPolicies.entrySet()) {
          typedXmlSerializer.startTag((String) null, "global-policy-entry");
          typedXmlSerializer.startTag((String) null, "policy-key-entry");
          ((PolicyKey) entry.getKey()).saveToXml(typedXmlSerializer);
          typedXmlSerializer.endTag((String) null, "policy-key-entry");
          typedXmlSerializer.startTag((String) null, "policy-state-entry");
          ((PolicyState) entry.getValue()).saveToXml(typedXmlSerializer);
          typedXmlSerializer.endTag((String) null, "policy-state-entry");
          typedXmlSerializer.endTag((String) null, "global-policy-entry");
        }
      }
    }

    public final void writeEnforcingAdminsInner(TypedXmlSerializer typedXmlSerializer) {
      if (DevicePolicyEngine.this.mEnforcingAdmins != null) {
        for (int i = 0; i < DevicePolicyEngine.this.mEnforcingAdmins.size(); i++) {
          for (EnforcingAdmin enforcingAdmin :
              (Set)
                  DevicePolicyEngine.this.mEnforcingAdmins.get(
                      DevicePolicyEngine.this.mEnforcingAdmins.keyAt(i))) {
            typedXmlSerializer.startTag((String) null, "enforcing-admins-entry");
            enforcingAdmin.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "enforcing-admins-entry");
          }
        }
      }
    }

    public void readFromFileLocked() {
      if (!this.mFile.exists()) {
        Log.d("DevicePolicyEngine", "" + this.mFile + " doesn't exist");
        return;
      }
      Log.d("DevicePolicyEngine", "Reading from " + this.mFile);
      FileInputStream fileInputStream = null;
      try {
        try {
          fileInputStream = new AtomicFile(this.mFile).openRead();
          readInner(Xml.resolvePullParser(fileInputStream));
        } catch (IOException | ClassNotFoundException | XmlPullParserException e) {
          Slogf.wtf("DevicePolicyEngine", "Error parsing resources file", e);
        }
      } finally {
        IoUtils.closeQuietly(fileInputStream);
      }
    }

    public final void readInner(TypedXmlPullParser typedXmlPullParser) {
      String name;
      int depth = typedXmlPullParser.getDepth();
      while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
        name = typedXmlPullParser.getName();
        name.hashCode();
        switch (name) {
          case "global-policy-entry":
            readGlobalPoliciesInner(typedXmlPullParser);
            break;
          case "local-policy-entry":
            readLocalPoliciesInner(typedXmlPullParser);
            break;
          case "enforcing-admins-entry":
            readEnforcingAdminsInner(typedXmlPullParser);
            break;
          default:
            Slogf.wtf("DevicePolicyEngine", "Unknown tag " + name);
            break;
        }
      }
    }

    public final void readLocalPoliciesInner(TypedXmlPullParser typedXmlPullParser) {
      PolicyKey policyKey = null;
      int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "user-id");
      int depth = typedXmlPullParser.getDepth();
      Object obj = null;
      while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
        String name = typedXmlPullParser.getName();
        name.hashCode();
        if (name.equals("policy-key-entry")) {
          policyKey = PolicyDefinition.readPolicyKeyFromXml(typedXmlPullParser);
        } else if (name.equals("policy-state-entry")) {
          obj = PolicyState.readFromXml(typedXmlPullParser);
        } else {
          Slogf.wtf("DevicePolicyEngine", "Unknown tag for local policy entry" + name);
        }
      }
      if (policyKey != null && obj != null) {
        if (!DevicePolicyEngine.this.mLocalPolicies.contains(attributeInt)) {
          DevicePolicyEngine.this.mLocalPolicies.put(attributeInt, new HashMap());
        }
        ((Map) DevicePolicyEngine.this.mLocalPolicies.get(attributeInt)).put(policyKey, obj);
        return;
      }
      StringBuilder sb = new StringBuilder();
      sb.append("Error parsing local policy, policyKey is ");
      if (policyKey == null) {
        policyKey = "null";
      }
      sb.append(policyKey);
      sb.append(", and policyState is ");
      if (obj == null) {
        obj = "null";
      }
      sb.append(obj);
      sb.append(".");
      Slogf.wtf("DevicePolicyEngine", sb.toString());
    }

    public final void readGlobalPoliciesInner(TypedXmlPullParser typedXmlPullParser) {
      int depth = typedXmlPullParser.getDepth();
      PolicyKey policyKey = null;
      Object obj = null;
      while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
        String name = typedXmlPullParser.getName();
        name.hashCode();
        if (name.equals("policy-key-entry")) {
          policyKey = PolicyDefinition.readPolicyKeyFromXml(typedXmlPullParser);
        } else if (name.equals("policy-state-entry")) {
          obj = PolicyState.readFromXml(typedXmlPullParser);
        } else {
          Slogf.wtf("DevicePolicyEngine", "Unknown tag for local policy entry" + name);
        }
      }
      if (policyKey != null && obj != null) {
        DevicePolicyEngine.this.mGlobalPolicies.put(policyKey, obj);
        return;
      }
      StringBuilder sb = new StringBuilder();
      sb.append("Error parsing global policy, policyKey is ");
      if (policyKey == null) {
        policyKey = "null";
      }
      sb.append(policyKey);
      sb.append(", and policyState is ");
      if (obj == null) {
        obj = "null";
      }
      sb.append(obj);
      sb.append(".");
      Slogf.wtf("DevicePolicyEngine", sb.toString());
    }

    public final void readEnforcingAdminsInner(TypedXmlPullParser typedXmlPullParser) {
      EnforcingAdmin readFromXml = EnforcingAdmin.readFromXml(typedXmlPullParser);
      if (readFromXml == null) {
        Slogf.wtf("DevicePolicyEngine", "Error parsing enforcingAdmins, EnforcingAdmin is null.");
        return;
      }
      if (!DevicePolicyEngine.this.mEnforcingAdmins.contains(readFromXml.getUserId())) {
        DevicePolicyEngine.this.mEnforcingAdmins.put(readFromXml.getUserId(), new HashSet());
      }
      ((Set) DevicePolicyEngine.this.mEnforcingAdmins.get(readFromXml.getUserId()))
          .add(readFromXml);
    }
  }
}
