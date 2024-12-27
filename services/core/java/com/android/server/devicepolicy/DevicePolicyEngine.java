package com.android.server.devicepolicy;

import android.app.BroadcastOptions;
import android.app.admin.DevicePolicyIdentifiers;
import android.app.admin.PolicyKey;
import android.app.admin.PolicyValue;
import android.app.admin.UserRestrictionPolicyKey;
import android.app.admin.flags.Flags;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Environment;
import android.os.Parcel;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DevicePolicyEngine {
    public static final String CELLULAR_2G_USER_RESTRICTION_ID =
            DevicePolicyIdentifiers.getIdentifierForUserRestriction("no_cellular_2g");
    public final SparseArray mAdminPolicySize;
    public final Context mContext;
    public final DeviceAdminServiceController mDeviceAdminServiceController;
    public final SparseArray mEnforcingAdmins;
    public final Map mGlobalPolicies;
    public final SparseArray mLocalPolicies;
    public final Object mLock;
    public int mPolicySizeLimit = -1;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DevicePoliciesReaderWriter {
        public final File mFile =
                new File(Environment.getDataSystemDirectory(), "device_policy_state.xml");

        public DevicePoliciesReaderWriter() {}

        public static void createBackup() {
            try {
                File file = new File(Environment.getDataSystemDirectory(), "device_policy_backups");
                file.mkdir();
                Path of =
                        Path.of(
                                file.getPath(),
                                "device_policy_state.%s.xml".formatted("35.1.unmanaged-mode"));
                if (of.toFile().exists()) {
                    Log.w("DevicePolicyEngine", "Backup already exist: " + of);
                } else {
                    Files.copy(
                            new File(
                                            Environment.getDataSystemDirectory(),
                                            "device_policy_state.xml")
                                    .toPath(),
                            of,
                            StandardCopyOption.REPLACE_EXISTING);
                    Log.i("DevicePolicyEngine", "Backup created at " + of);
                }
            } catch (Exception e) {
                Log.e("DevicePolicyEngine", "Cannot create backup 35.1.unmanaged-mode", e);
            }
        }

        public final void readFromFileLocked() {
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
            DevicePolicyEngine devicePolicyEngine;
            int depth = typedXmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                name = typedXmlPullParser.getName();
                name.getClass();
                devicePolicyEngine = DevicePolicyEngine.this;
                switch (name) {
                    case "global-policy-entry":
                        Object obj = null;
                        int depth2 = typedXmlPullParser.getDepth();
                        PolicyKey policyKey = null;
                        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                            String name2 = typedXmlPullParser.getName();
                            name2.getClass();
                            if (name2.equals("policy-key-entry")) {
                                policyKey =
                                        PolicyDefinition.readPolicyKeyFromXml(typedXmlPullParser);
                            } else if (name2.equals("policy-state-entry")) {
                                obj = PolicyState.readFromXml(typedXmlPullParser);
                            } else {
                                Slogf.wtf(
                                        "DevicePolicyEngine",
                                        "Unknown tag for local policy entry".concat(name2));
                            }
                        }
                        if (policyKey != null && obj != null) {
                            ((HashMap) devicePolicyEngine.mGlobalPolicies).put(policyKey, obj);
                            break;
                        } else {
                            StringBuilder sb =
                                    new StringBuilder("Error parsing global policy, policyKey is ");
                            if (policyKey == null) {
                                policyKey = "null";
                            }
                            sb.append(policyKey);
                            sb.append(", and policyState is ");
                            sb.append(obj != null ? obj : "null");
                            sb.append(".");
                            Slogf.wtf("DevicePolicyEngine", sb.toString());
                            break;
                        }
                    case "local-policy-entry":
                        Object obj2 = null;
                        int attributeInt =
                                typedXmlPullParser.getAttributeInt((String) null, "user-id");
                        int depth3 = typedXmlPullParser.getDepth();
                        PolicyKey policyKey2 = null;
                        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                            String name3 = typedXmlPullParser.getName();
                            name3.getClass();
                            if (name3.equals("policy-key-entry")) {
                                policyKey2 =
                                        PolicyDefinition.readPolicyKeyFromXml(typedXmlPullParser);
                            } else if (name3.equals("policy-state-entry")) {
                                obj2 = PolicyState.readFromXml(typedXmlPullParser);
                            } else {
                                Slogf.wtf(
                                        "DevicePolicyEngine",
                                        "Unknown tag for local policy entry".concat(name3));
                            }
                        }
                        if (policyKey2 != null && obj2 != null) {
                            if (!devicePolicyEngine.mLocalPolicies.contains(attributeInt)) {
                                devicePolicyEngine.mLocalPolicies.put(attributeInt, new HashMap());
                            }
                            ((Map) devicePolicyEngine.mLocalPolicies.get(attributeInt))
                                    .put(policyKey2, obj2);
                            break;
                        } else {
                            StringBuilder sb2 =
                                    new StringBuilder("Error parsing local policy, policyKey is ");
                            if (policyKey2 == null) {
                                policyKey2 = "null";
                            }
                            sb2.append(policyKey2);
                            sb2.append(", and policyState is ");
                            sb2.append(obj2 != null ? obj2 : "null");
                            sb2.append(".");
                            Slogf.wtf("DevicePolicyEngine", sb2.toString());
                            break;
                        }
                    case "enforcing-admin-and-size":
                        int depth4 = typedXmlPullParser.getDepth();
                        EnforcingAdmin enforcingAdmin = null;
                        int i = 0;
                        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth4)) {
                            String name4 = typedXmlPullParser.getName();
                            name4.getClass();
                            if (name4.equals("enforcing-admin")) {
                                enforcingAdmin = EnforcingAdmin.readFromXml(typedXmlPullParser);
                            } else if (name4.equals("policy-sum-size")) {
                                i = typedXmlPullParser.getAttributeInt((String) null, "size");
                            } else {
                                Slogf.wtf("DevicePolicyEngine", "Unknown tag ".concat(name4));
                            }
                        }
                        if (enforcingAdmin != null) {
                            if (i > 0) {
                                SparseArray sparseArray = devicePolicyEngine.mAdminPolicySize;
                                int i2 = enforcingAdmin.mUserId;
                                if (!sparseArray.contains(i2)) {
                                    devicePolicyEngine.mAdminPolicySize.put(i2, new HashMap());
                                }
                                ((HashMap) devicePolicyEngine.mAdminPolicySize.get(i2))
                                        .put(enforcingAdmin, Integer.valueOf(i));
                                break;
                            } else {
                                Slogf.wtf(
                                        "DevicePolicyEngine",
                                        "Error parsing policy size, size is " + i);
                                break;
                            }
                        } else {
                            Slogf.wtf(
                                    "DevicePolicyEngine",
                                    "Error parsing enforcingAdmins, EnforcingAdmin is null.");
                            break;
                        }
                    case "max-policy-size-limit":
                        if (!Flags.devicePolicySizeTrackingInternalBugFixEnabled()) {
                            break;
                        } else {
                            devicePolicyEngine.mPolicySizeLimit =
                                    typedXmlPullParser.getAttributeInt((String) null, "size");
                            break;
                        }
                    case "enforcing-admins-entry":
                        EnforcingAdmin readFromXml = EnforcingAdmin.readFromXml(typedXmlPullParser);
                        if (readFromXml != null) {
                            SparseArray sparseArray2 = devicePolicyEngine.mEnforcingAdmins;
                            int i3 = readFromXml.mUserId;
                            if (!sparseArray2.contains(i3)) {
                                devicePolicyEngine.mEnforcingAdmins.put(i3, new HashSet());
                            }
                            ((Set) devicePolicyEngine.mEnforcingAdmins.get(i3)).add(readFromXml);
                            break;
                        } else {
                            Slogf.wtf(
                                    "DevicePolicyEngine",
                                    "Error parsing enforcingAdmins, EnforcingAdmin is null.");
                            break;
                        }
                    default:
                        Slogf.wtf("DevicePolicyEngine", "Unknown tag ".concat(name));
                        break;
                }
            }
        }

        public final void writeInner(TypedXmlSerializer typedXmlSerializer) {
            DevicePolicyEngine devicePolicyEngine = DevicePolicyEngine.this;
            if (devicePolicyEngine.mLocalPolicies != null) {
                for (int i = 0; i < devicePolicyEngine.mLocalPolicies.size(); i++) {
                    int keyAt = devicePolicyEngine.mLocalPolicies.keyAt(i);
                    for (Map.Entry entry :
                            ((Map) devicePolicyEngine.mLocalPolicies.get(keyAt)).entrySet()) {
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
            Map map = devicePolicyEngine.mGlobalPolicies;
            if (map != null) {
                for (Map.Entry entry2 : ((HashMap) map).entrySet()) {
                    typedXmlSerializer.startTag((String) null, "global-policy-entry");
                    typedXmlSerializer.startTag((String) null, "policy-key-entry");
                    ((PolicyKey) entry2.getKey()).saveToXml(typedXmlSerializer);
                    typedXmlSerializer.endTag((String) null, "policy-key-entry");
                    typedXmlSerializer.startTag((String) null, "policy-state-entry");
                    ((PolicyState) entry2.getValue()).saveToXml(typedXmlSerializer);
                    typedXmlSerializer.endTag((String) null, "policy-state-entry");
                    typedXmlSerializer.endTag((String) null, "global-policy-entry");
                }
            }
            if (devicePolicyEngine.mEnforcingAdmins != null) {
                for (int i2 = 0; i2 < devicePolicyEngine.mEnforcingAdmins.size(); i2++) {
                    for (EnforcingAdmin enforcingAdmin :
                            (Set)
                                    devicePolicyEngine.mEnforcingAdmins.get(
                                            devicePolicyEngine.mEnforcingAdmins.keyAt(i2))) {
                        typedXmlSerializer.startTag((String) null, "enforcing-admins-entry");
                        enforcingAdmin.saveToXml(typedXmlSerializer);
                        typedXmlSerializer.endTag((String) null, "enforcing-admins-entry");
                    }
                }
            }
            if (Flags.devicePolicySizeTrackingInternalBugFixEnabled()
                    && devicePolicyEngine.mAdminPolicySize != null) {
                for (int i3 = 0; i3 < devicePolicyEngine.mAdminPolicySize.size(); i3++) {
                    int keyAt2 = devicePolicyEngine.mAdminPolicySize.keyAt(i3);
                    for (EnforcingAdmin enforcingAdmin2 :
                            ((HashMap) devicePolicyEngine.mAdminPolicySize.get(keyAt2)).keySet()) {
                        typedXmlSerializer.startTag((String) null, "enforcing-admin-and-size");
                        typedXmlSerializer.startTag((String) null, "enforcing-admin");
                        enforcingAdmin2.saveToXml(typedXmlSerializer);
                        typedXmlSerializer.endTag((String) null, "enforcing-admin");
                        typedXmlSerializer.startTag((String) null, "policy-sum-size");
                        typedXmlSerializer.attributeInt(
                                (String) null,
                                "size",
                                ((Integer)
                                                ((HashMap)
                                                                devicePolicyEngine.mAdminPolicySize
                                                                        .get(keyAt2))
                                                        .get(enforcingAdmin2))
                                        .intValue());
                        typedXmlSerializer.endTag((String) null, "policy-sum-size");
                        typedXmlSerializer.endTag((String) null, "enforcing-admin-and-size");
                    }
                }
            }
            if (Flags.devicePolicySizeTrackingInternalBugFixEnabled()) {
                typedXmlSerializer.startTag((String) null, "max-policy-size-limit");
                typedXmlSerializer.attributeInt(
                        (String) null, "size", devicePolicyEngine.mPolicySizeLimit);
                typedXmlSerializer.endTag((String) null, "max-policy-size-limit");
            }
        }

        public final void writeToFileLocked() {
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
    }

    public DevicePolicyEngine(
            Context context,
            DeviceAdminServiceController deviceAdminServiceController,
            Object obj) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mDeviceAdminServiceController = deviceAdminServiceController;
        Objects.requireNonNull(obj);
        this.mLock = obj;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mLocalPolicies = new SparseArray();
        this.mGlobalPolicies = new HashMap();
        this.mEnforcingAdmins = new SparseArray();
        this.mAdminPolicySize = new SparseArray();
    }

    public static Set getUserRestrictionPolicyKeysForAdminLocked(
            Map map, EnforcingAdmin enforcingAdmin) {
        PolicyValue policyValue;
        HashSet hashSet = new HashSet();
        for (UserRestrictionPolicyKey userRestrictionPolicyKey : map.keySet()) {
            if ((((PolicyState) map.get(userRestrictionPolicyKey)).mPolicyDefinition.mPolicyFlags
                                    & 16)
                            != 0
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

    public static boolean shouldApplyPackageSetUnionPolicyHack(PolicyDefinition policyDefinition) {
        String identifier = policyDefinition.mPolicyKey.getIdentifier();
        return identifier.equals("userControlDisabledPackages")
                || identifier.equals("packagesSuspended");
    }

    public static int sizeOf(PolicyValue policyValue) {
        try {
            Parcel obtain = Parcel.obtain();
            obtain.writeParcelable(policyValue, 0);
            obtain.setDataPosition(0);
            return obtain.marshall().length;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "Error calculating size of policy: ", "DevicePolicyEngine");
            return 0;
        }
    }

    public final boolean applyGlobalPolicyOnUsersWithLocalPoliciesLocked(
            PolicyDefinition policyDefinition,
            EnforcingAdmin enforcingAdmin,
            PolicyValue policyValue) {
        boolean equals;
        if (policyDefinition.isGlobalOnlyPolicy()) {
            return true;
        }
        boolean z = true;
        for (int i = 0; i < this.mLocalPolicies.size(); i++) {
            int keyAt = this.mLocalPolicies.keyAt(i);
            if (hasLocalPolicyLocked(policyDefinition, keyAt)) {
                PolicyState localPolicyStateLocked =
                        getLocalPolicyStateLocked(policyDefinition, keyAt);
                if (localPolicyStateLocked.resolvePolicy(
                        getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins())) {
                    enforcePolicy(
                            policyDefinition, localPolicyStateLocked.mCurrentResolvedPolicy, keyAt);
                    sendPolicyChangedToAdminsLocked(
                            localPolicyStateLocked, enforcingAdmin, policyDefinition, keyAt);
                }
                if (!shouldApplyPackageSetUnionPolicyHack(policyDefinition)) {
                    equals =
                            Objects.equals(
                                    policyValue, localPolicyStateLocked.mCurrentResolvedPolicy);
                } else if (!Objects.equals(
                        policyValue, localPolicyStateLocked.mCurrentResolvedPolicy)) {
                    PolicyValue policyValue2 = localPolicyStateLocked.mCurrentResolvedPolicy;
                    equals =
                            (policyValue2 == null
                                            || policyValue == null
                                            || !((Set) policyValue2.getValue())
                                                    .containsAll(
                                                            (Collection) policyValue.getValue()))
                                    ? false
                                    : true;
                }
                z &= equals;
            }
        }
        return z;
    }

    public final void clear() {
        synchronized (this.mLock) {
            ((HashMap) this.mGlobalPolicies).clear();
            this.mLocalPolicies.clear();
            this.mEnforcingAdmins.clear();
            this.mAdminPolicySize.clear();
        }
    }

    public final void decreasePolicySizeForAdmin(
            PolicyState policyState, EnforcingAdmin enforcingAdmin) {
        if (policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)
                && this.mAdminPolicySize.contains(enforcingAdmin.mUserId)) {
            SparseArray sparseArray = this.mAdminPolicySize;
            int i = enforcingAdmin.mUserId;
            if (((HashMap) sparseArray.get(i)).containsKey(enforcingAdmin)) {
                ((HashMap) this.mAdminPolicySize.get(i))
                        .put(
                                enforcingAdmin,
                                Integer.valueOf(
                                        ((Integer)
                                                                ((HashMap)
                                                                                this
                                                                                        .mAdminPolicySize
                                                                                        .get(i))
                                                                        .get(enforcingAdmin))
                                                        .intValue()
                                                - sizeOf(
                                                        (PolicyValue)
                                                                policyState
                                                                        .getPoliciesSetByAdmins()
                                                                        .get(enforcingAdmin))));
                if (((Integer) ((HashMap) this.mAdminPolicySize.get(i)).get(enforcingAdmin))
                                .intValue()
                        <= 0) {
                    ((HashMap) this.mAdminPolicySize.get(i)).remove(enforcingAdmin);
                }
                if (((HashMap) this.mAdminPolicySize.get(i)).isEmpty()) {
                    this.mAdminPolicySize.remove(i);
                }
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Local Policies: ");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mLocalPolicies.size(); i++) {
                    int keyAt = this.mLocalPolicies.keyAt(i);
                    indentingPrintWriter.printf(
                            "User %d:\n", new Object[] {Integer.valueOf(keyAt)});
                    indentingPrintWriter.increaseIndent();
                    Iterator it = ((Map) this.mLocalPolicies.get(keyAt)).keySet().iterator();
                    while (it.hasNext()) {
                        ((PolicyState)
                                        ((Map) this.mLocalPolicies.get(keyAt))
                                                .get((PolicyKey) it.next()))
                                .dump(indentingPrintWriter);
                        indentingPrintWriter.println();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Global Policies: ");
                indentingPrintWriter.increaseIndent();
                Iterator it2 = ((HashMap) this.mGlobalPolicies).keySet().iterator();
                while (it2.hasNext()) {
                    ((PolicyState) ((HashMap) this.mGlobalPolicies).get((PolicyKey) it2.next()))
                            .dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
                if (Flags.devicePolicySizeTrackingInternalBugFixEnabled()) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Default admin policy size limit: -1");
                    indentingPrintWriter.println(
                            "Current admin policy size limit: " + this.mPolicySizeLimit);
                    indentingPrintWriter.println("Admin Policies size: ");
                    for (int i2 = 0; i2 < this.mAdminPolicySize.size(); i2++) {
                        int keyAt2 = this.mAdminPolicySize.keyAt(i2);
                        indentingPrintWriter.printf(
                                "User %d:\n", new Object[] {Integer.valueOf(keyAt2)});
                        indentingPrintWriter.increaseIndent();
                        for (EnforcingAdmin enforcingAdmin :
                                ((HashMap) this.mAdminPolicySize.get(keyAt2)).keySet()) {
                            indentingPrintWriter.printf(
                                    "Admin : "
                                            + enforcingAdmin
                                            + " : "
                                            + ((HashMap) this.mAdminPolicySize.get(keyAt2))
                                                    .get(enforcingAdmin),
                                    new Object[0]);
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enforcePolicy(
            PolicyDefinition policyDefinition, PolicyValue policyValue, int i) {
        ((Boolean)
                        policyDefinition.mPolicyEnforcerCallback.apply(
                                policyValue == null ? null : policyValue.getValue(),
                                this.mContext,
                                Integer.valueOf(i),
                                policyDefinition.mPolicyKey))
                .booleanValue();
    }

    public final Set getEnforcingAdminsOnUser(int i) {
        Set hashSet;
        synchronized (this.mLock) {
            try {
                hashSet =
                        this.mEnforcingAdmins.contains(i)
                                ? new HashSet((Collection) this.mEnforcingAdmins.get(i))
                                : Collections.emptySet();
            } catch (Throwable th) {
                throw th;
            }
        }
        return hashSet;
    }

    public final LinkedHashMap getGlobalPoliciesSetByAdmins(PolicyDefinition policyDefinition) {
        synchronized (this.mLock) {
            try {
                if (hasGlobalPolicyLocked(policyDefinition)) {
                    return getGlobalPolicyStateLocked(policyDefinition).getPoliciesSetByAdmins();
                }
                return new LinkedHashMap();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Object getGlobalPolicySetByAdmin(
            PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin) {
        Objects.requireNonNull(policyDefinition);
        synchronized (this.mLock) {
            try {
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
            } finally {
            }
        }
    }

    public final PolicyState getGlobalPolicyStateLocked(PolicyDefinition policyDefinition) {
        if ((policyDefinition.mPolicyFlags & 2) != 0) {
            throw new IllegalArgumentException(
                    policyDefinition.mPolicyKey + " is a local only policy.");
        }
        if (!((HashMap) this.mGlobalPolicies).containsKey(policyDefinition.mPolicyKey)) {
            ((HashMap) this.mGlobalPolicies)
                    .put(policyDefinition.mPolicyKey, new PolicyState(policyDefinition));
        }
        try {
            return (PolicyState) ((HashMap) this.mGlobalPolicies).get(policyDefinition.mPolicyKey);
        } catch (ClassCastException unused) {
            throw new IllegalArgumentException();
        }
    }

    public final LinkedHashMap getLocalPoliciesSetByAdmins(
            PolicyDefinition policyDefinition, int i) {
        Objects.requireNonNull(policyDefinition);
        synchronized (this.mLock) {
            try {
                if (hasLocalPolicyLocked(policyDefinition, i)) {
                    return getLocalPolicyStateLocked(policyDefinition, i).getPoliciesSetByAdmins();
                }
                return new LinkedHashMap();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Set getLocalPolicyKeysSetByAllAdmins(PolicyDefinition policyDefinition, int i) {
        Objects.requireNonNull(policyDefinition);
        synchronized (this.mLock) {
            try {
                if (!policyDefinition.isGlobalOnlyPolicy() && this.mLocalPolicies.contains(i)) {
                    HashSet hashSet = new HashSet();
                    for (PolicyKey policyKey : ((Map) this.mLocalPolicies.get(i)).keySet()) {
                        if (policyKey.hasSameIdentifierAs(policyDefinition.mPolicyKey)) {
                            hashSet.add(policyKey);
                        }
                    }
                    return hashSet;
                }
                return Set.of();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Object getLocalPolicySetByAdmin(
            PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin, int i) {
        Objects.requireNonNull(policyDefinition);
        Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
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
            } finally {
            }
        }
    }

    public final PolicyState getLocalPolicyStateLocked(PolicyDefinition policyDefinition, int i) {
        if (policyDefinition.isGlobalOnlyPolicy()) {
            throw new IllegalArgumentException(
                    policyDefinition.mPolicyKey + " is a global only policy.");
        }
        if (!this.mLocalPolicies.contains(i)) {
            this.mLocalPolicies.put(i, new HashMap());
        }
        if (!((Map) this.mLocalPolicies.get(i)).containsKey(policyDefinition.mPolicyKey)) {
            ((Map) this.mLocalPolicies.get(i))
                    .put(policyDefinition.mPolicyKey, new PolicyState(policyDefinition));
        }
        try {
            return (PolicyState)
                    ((Map) this.mLocalPolicies.get(i)).get(policyDefinition.mPolicyKey);
        } catch (ClassCastException unused) {
            throw new IllegalArgumentException();
        }
    }

    public final Object getResolvedPolicy(PolicyDefinition policyDefinition, int i) {
        PolicyValue resolvedPolicyValue = getResolvedPolicyValue(policyDefinition, i);
        if (resolvedPolicyValue == null) {
            return null;
        }
        return resolvedPolicyValue.getValue();
    }

    public final PolicyValue getResolvedPolicyValue(PolicyDefinition policyDefinition, int i) {
        PolicyValue policyValue;
        Objects.requireNonNull(policyDefinition);
        synchronized (this.mLock) {
            try {
                policyValue =
                        hasLocalPolicyLocked(policyDefinition, i)
                                ? getLocalPolicyStateLocked(policyDefinition, i)
                                        .mCurrentResolvedPolicy
                                : hasGlobalPolicyLocked(policyDefinition)
                                        ? getGlobalPolicyStateLocked(policyDefinition)
                                                .mCurrentResolvedPolicy
                                        : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return policyValue;
    }

    public final boolean handleAdminPolicySizeLimit(
            PolicyState policyState,
            EnforcingAdmin enforcingAdmin,
            PolicyValue policyValue,
            PolicyDefinition policyDefinition,
            int i) {
        boolean contains = this.mAdminPolicySize.contains(enforcingAdmin.mUserId);
        int i2 = enforcingAdmin.mUserId;
        int intValue =
                (contains && ((HashMap) this.mAdminPolicySize.get(i2)).containsKey(enforcingAdmin))
                        ? ((Integer) ((HashMap) this.mAdminPolicySize.get(i2)).get(enforcingAdmin))
                                .intValue()
                        : 0;
        int sizeOf =
                policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)
                        ? sizeOf(
                                (PolicyValue)
                                        policyState.getPoliciesSetByAdmins().get(enforcingAdmin))
                        : 0;
        int sizeOf2 = sizeOf(policyValue);
        int i3 = this.mPolicySizeLimit;
        if (i3 != -1 && (intValue + sizeOf2) - sizeOf >= i3) {
            Log.w(
                    "DevicePolicyEngine",
                    "Admin " + enforcingAdmin + "reached max allowed storage limit.");
            sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 3, i);
            return false;
        }
        int i4 = sizeOf2 - sizeOf;
        if (!this.mAdminPolicySize.contains(i2)) {
            this.mAdminPolicySize.put(i2, new HashMap());
        }
        if (!((HashMap) this.mAdminPolicySize.get(i2)).containsKey(enforcingAdmin)) {
            ((HashMap) this.mAdminPolicySize.get(i2)).put(enforcingAdmin, 0);
        }
        ((HashMap) this.mAdminPolicySize.get(i2))
                .put(
                        enforcingAdmin,
                        Integer.valueOf(
                                ((Integer)
                                                        ((HashMap) this.mAdminPolicySize.get(i2))
                                                                .get(enforcingAdmin))
                                                .intValue()
                                        + i4));
        return true;
    }

    public final void handleUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mLocalPolicies.contains(i)) {
                    Iterator it =
                            new HashSet(((Map) this.mLocalPolicies.get(i)).keySet()).iterator();
                    while (it.hasNext()) {
                        PolicyState policyState =
                                (PolicyState)
                                        ((Map) this.mLocalPolicies.get(i))
                                                .get((PolicyKey) it.next());
                        Iterator it2 =
                                new HashSet(policyState.getPoliciesSetByAdmins().keySet())
                                        .iterator();
                        while (it2.hasNext()) {
                            removeLocalPolicy(
                                    policyState.mPolicyDefinition, (EnforcingAdmin) it2.next(), i);
                        }
                    }
                    this.mLocalPolicies.remove(i);
                }
            } finally {
            }
        }
        Iterator it3 = getEnforcingAdminsOnUser(i).iterator();
        while (it3.hasNext()) {
            removePoliciesForAdmin((EnforcingAdmin) it3.next());
        }
    }

    public final boolean hasGlobalPolicyLocked(PolicyDefinition policyDefinition) {
        if ((policyDefinition.mPolicyFlags & 2) != 0) {
            return false;
        }
        if (((HashMap) this.mGlobalPolicies).containsKey(policyDefinition.mPolicyKey)) {
            return !((PolicyState)
                            ((HashMap) this.mGlobalPolicies).get(policyDefinition.mPolicyKey))
                    .getPoliciesSetByAdmins()
                    .isEmpty();
        }
        return false;
    }

    public final boolean hasLocalPolicyLocked(PolicyDefinition policyDefinition, int i) {
        if (!policyDefinition.isGlobalOnlyPolicy()
                && this.mLocalPolicies.contains(i)
                && ((Map) this.mLocalPolicies.get(i)).containsKey(policyDefinition.mPolicyKey)) {
            return !((PolicyState)
                            ((Map) this.mLocalPolicies.get(i)).get(policyDefinition.mPolicyKey))
                    .getPoliciesSetByAdmins()
                    .isEmpty();
        }
        return false;
    }

    public final void maybeForceEnforcementRefreshLocked(PolicyDefinition policyDefinition) {
        if (policyDefinition == null) {
            return;
        }
        try {
            PolicyKey policyKey = policyDefinition.mPolicyKey;
            if (policyKey != null && (policyKey instanceof UserRestrictionPolicyKey)) {
                Binder.withCleanCallingIdentity(
                        new DevicePolicyEngine$$ExternalSyntheticLambda3(
                                this, policyDefinition, 0));
            }
        } catch (Throwable th) {
            Log.e(
                    "DevicePolicyEngine",
                    "Exception throw during maybeForceEnforcementRefreshLocked",
                    th);
        }
    }

    public final void maybeSendIntentToAdminReceivers(
            Intent intent, UserHandle userHandle, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) it.next();
            if ("android.permission.BIND_DEVICE_ADMIN"
                    .equals(resolveInfo.activityInfo.permission)) {
                this.mContext.sendBroadcastAsUser(intent, userHandle);
            } else {
                Log.w(
                        "DevicePolicyEngine",
                        "Receiver "
                                + resolveInfo.activityInfo
                                + " is not protected by BIND_DEVICE_ADMIN permission!");
            }
        }
    }

    public final void reapplyAllPoliciesOnBootLocked() {
        Iterator it = ((HashMap) this.mGlobalPolicies).keySet().iterator();
        while (it.hasNext()) {
            PolicyState policyState =
                    (PolicyState) ((HashMap) this.mGlobalPolicies).get((PolicyKey) it.next());
            PolicyDefinition policyDefinition = policyState.mPolicyDefinition;
            if ((policyDefinition.mPolicyFlags & 32) == 0) {
                enforcePolicy(policyDefinition, policyState.mCurrentResolvedPolicy, -1);
            }
        }
        for (int i = 0; i < this.mLocalPolicies.size(); i++) {
            int keyAt = this.mLocalPolicies.keyAt(i);
            Iterator it2 = ((Map) this.mLocalPolicies.get(keyAt)).keySet().iterator();
            while (it2.hasNext()) {
                PolicyState policyState2 =
                        (PolicyState)
                                ((Map) this.mLocalPolicies.get(keyAt)).get((PolicyKey) it2.next());
                PolicyDefinition policyDefinition2 = policyState2.mPolicyDefinition;
                if ((policyDefinition2.mPolicyFlags & 32) == 0) {
                    enforcePolicy(policyDefinition2, policyState2.mCurrentResolvedPolicy, keyAt);
                }
            }
        }
    }

    public final void removeGlobalPolicy(
            PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin) {
        Objects.requireNonNull(policyDefinition);
        Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                PolicyState globalPolicyStateLocked = getGlobalPolicyStateLocked(policyDefinition);
                if (Flags.devicePolicySizeTrackingInternalBugFixEnabled()) {
                    decreasePolicySizeForAdmin(globalPolicyStateLocked, enforcingAdmin);
                }
                globalPolicyStateLocked.getClass();
                boolean z = false;
                if (globalPolicyStateLocked.mPoliciesSetByAdmins.remove(enforcingAdmin) != null) {
                    PolicyDefinition policyDefinition2 = globalPolicyStateLocked.mPolicyDefinition;
                    if (!policyDefinition2.isNonCoexistablePolicy()) {
                        PolicyValue resolve =
                                policyDefinition2.mResolutionMechanism.resolve(
                                        globalPolicyStateLocked.mPoliciesSetByAdmins);
                        z =
                                !Objects.equals(
                                        resolve, globalPolicyStateLocked.mCurrentResolvedPolicy);
                        globalPolicyStateLocked.mCurrentResolvedPolicy = resolve;
                    }
                }
                maybeForceEnforcementRefreshLocked(policyDefinition);
                if (z) {
                    PolicyState globalPolicyStateLocked2 =
                            getGlobalPolicyStateLocked(policyDefinition);
                    enforcePolicy(
                            policyDefinition, globalPolicyStateLocked2.mCurrentResolvedPolicy, -1);
                    sendPolicyChangedToAdminsLocked(
                            globalPolicyStateLocked2, enforcingAdmin, policyDefinition, -1);
                    sendDevicePolicyChangedToSystem(-1);
                }
                applyGlobalPolicyOnUsersWithLocalPoliciesLocked(
                        policyDefinition, enforcingAdmin, null);
                sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 2, -1);
                if (globalPolicyStateLocked.getPoliciesSetByAdmins().isEmpty()) {
                    ((HashMap) this.mGlobalPolicies).remove(policyDefinition.mPolicyKey);
                }
                updateDeviceAdminServiceOnPolicyRemoveLocked(enforcingAdmin);
                write();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeLocalPolicy(
            PolicyDefinition policyDefinition, EnforcingAdmin enforcingAdmin, int i) {
        Objects.requireNonNull(policyDefinition);
        Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                maybeForceEnforcementRefreshLocked(policyDefinition);
                if (hasLocalPolicyLocked(policyDefinition, i)) {
                    PolicyState localPolicyStateLocked =
                            getLocalPolicyStateLocked(policyDefinition, i);
                    if (Flags.devicePolicySizeTrackingInternalBugFixEnabled()) {
                        decreasePolicySizeForAdmin(localPolicyStateLocked, enforcingAdmin);
                    }
                    if (policyDefinition.isNonCoexistablePolicy()) {
                        setNonCoexistableLocalPolicyLocked(
                                policyDefinition,
                                localPolicyStateLocked,
                                enforcingAdmin,
                                null,
                                i,
                                false);
                        return;
                    }
                    boolean z = false;
                    if (hasGlobalPolicyLocked(policyDefinition)) {
                        LinkedHashMap policiesSetByAdmins =
                                getGlobalPolicyStateLocked(policyDefinition)
                                        .getPoliciesSetByAdmins();
                        localPolicyStateLocked.getClass();
                        if (localPolicyStateLocked.mPoliciesSetByAdmins.remove(enforcingAdmin)
                                != null) {
                            z = localPolicyStateLocked.resolvePolicy(policiesSetByAdmins);
                        }
                    } else {
                        localPolicyStateLocked.getClass();
                        if (localPolicyStateLocked.mPoliciesSetByAdmins.remove(enforcingAdmin)
                                != null) {
                            PolicyDefinition policyDefinition2 =
                                    localPolicyStateLocked.mPolicyDefinition;
                            if (!policyDefinition2.isNonCoexistablePolicy()) {
                                PolicyValue resolve =
                                        policyDefinition2.mResolutionMechanism.resolve(
                                                localPolicyStateLocked.mPoliciesSetByAdmins);
                                z =
                                        !Objects.equals(
                                                resolve,
                                                localPolicyStateLocked.mCurrentResolvedPolicy);
                                localPolicyStateLocked.mCurrentResolvedPolicy = resolve;
                            }
                        }
                    }
                    if (z) {
                        PolicyState localPolicyStateLocked2 =
                                getLocalPolicyStateLocked(policyDefinition, i);
                        enforcePolicy(
                                policyDefinition,
                                localPolicyStateLocked2.mCurrentResolvedPolicy,
                                i);
                        sendPolicyChangedToAdminsLocked(
                                localPolicyStateLocked2, enforcingAdmin, policyDefinition, i);
                        if (hasGlobalPolicyLocked(policyDefinition)) {
                            sendPolicyChangedToAdminsLocked(
                                    getGlobalPolicyStateLocked(policyDefinition),
                                    enforcingAdmin,
                                    policyDefinition,
                                    i);
                        }
                        sendDevicePolicyChangedToSystem(i);
                    }
                    sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, 2, i);
                    if (localPolicyStateLocked.getPoliciesSetByAdmins().isEmpty()
                            && this.mLocalPolicies.contains(i)) {
                        ((Map) this.mLocalPolicies.get(i)).remove(policyDefinition.mPolicyKey);
                    }
                    updateDeviceAdminServiceOnPolicyRemoveLocked(enforcingAdmin);
                    write();
                    if ((policyDefinition.mPolicyFlags & 4) != 0) {
                        Binder.withCleanCallingIdentity(
                                new DevicePolicyEngine$$ExternalSyntheticLambda2(
                                        this, i, null, policyDefinition, enforcingAdmin));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removePoliciesForAdmin(EnforcingAdmin enforcingAdmin) {
        synchronized (this.mLock) {
            try {
                Iterator it = new HashSet(((HashMap) this.mGlobalPolicies).keySet()).iterator();
                while (it.hasNext()) {
                    PolicyState policyState =
                            (PolicyState)
                                    ((HashMap) this.mGlobalPolicies).get((PolicyKey) it.next());
                    if (policyState.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                        removeGlobalPolicy(policyState.mPolicyDefinition, enforcingAdmin);
                    }
                }
                for (int i = 0; i < this.mLocalPolicies.size(); i++) {
                    SparseArray sparseArray = this.mLocalPolicies;
                    Iterator it2 =
                            new HashSet(((Map) sparseArray.get(sparseArray.keyAt(i))).keySet())
                                    .iterator();
                    while (it2.hasNext()) {
                        PolicyKey policyKey = (PolicyKey) it2.next();
                        SparseArray sparseArray2 = this.mLocalPolicies;
                        PolicyState policyState2 =
                                (PolicyState)
                                        ((Map) sparseArray2.get(sparseArray2.keyAt(i)))
                                                .get(policyKey);
                        if (policyState2.getPoliciesSetByAdmins().containsKey(enforcingAdmin)) {
                            removeLocalPolicy(
                                    policyState2.mPolicyDefinition,
                                    enforcingAdmin,
                                    this.mLocalPolicies.keyAt(i));
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendDevicePolicyChangedToSystem(int i) {
        Intent intent = new Intent("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intent.setFlags(1073741824);
        Binder.withCleanCallingIdentity(
                new DevicePolicyEngine$$ExternalSyntheticLambda0(
                        this,
                        intent,
                        i,
                        new BroadcastOptions()
                                .setDeliveryGroupPolicy(1)
                                .setDeferralPolicy(2)
                                .toBundle()));
    }

    public final void sendPolicyChangedToAdminsLocked(
            PolicyState policyState,
            EnforcingAdmin enforcingAdmin,
            PolicyDefinition policyDefinition,
            int i) {
        for (EnforcingAdmin enforcingAdmin2 : policyState.getPoliciesSetByAdmins().keySet()) {
            if (!enforcingAdmin2.equals(enforcingAdmin)) {
                int i2 =
                        !Objects.equals(
                                        policyState.getPoliciesSetByAdmins().get(enforcingAdmin2),
                                        policyState.mCurrentResolvedPolicy)
                                ? 1
                                : 0;
                Intent intent = new Intent("android.app.admin.action.DEVICE_POLICY_CHANGED");
                intent.setPackage(enforcingAdmin2.mPackageName);
                Binder.withCleanCallingIdentity(
                        new DevicePolicyEngine$$ExternalSyntheticLambda1(
                                this, intent, enforcingAdmin2, policyDefinition, i, i2, 1));
            }
        }
    }

    public final void sendPolicyResultToAdmin(
            EnforcingAdmin enforcingAdmin, PolicyDefinition policyDefinition, int i, int i2) {
        Intent intent = new Intent("android.app.admin.action.DEVICE_POLICY_SET_RESULT");
        intent.setPackage(enforcingAdmin.mPackageName);
        Binder.withCleanCallingIdentity(
                new DevicePolicyEngine$$ExternalSyntheticLambda1(
                        this, intent, enforcingAdmin, policyDefinition, i2, i, 0));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0053 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:4:0x0009, B:6:0x0013, B:8:0x001f, B:12:0x0025, B:17:0x0053, B:18:0x005a, B:21:0x005c, B:23:0x0069, B:24:0x0078, B:26:0x0080, B:28:0x0086, B:30:0x008a, B:37:0x00a4, B:38:0x00af, B:41:0x0038, B:44:0x004d), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005c A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:4:0x0009, B:6:0x0013, B:8:0x001f, B:12:0x0025, B:17:0x0053, B:18:0x005a, B:21:0x005c, B:23:0x0069, B:24:0x0078, B:26:0x0080, B:28:0x0086, B:30:0x008a, B:37:0x00a4, B:38:0x00af, B:41:0x0038, B:44:0x004d), top: B:3:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setGlobalPolicy(
            com.android.server.devicepolicy.PolicyDefinition r9,
            com.android.server.devicepolicy.EnforcingAdmin r10,
            android.app.admin.PolicyValue r11) {
        /*
            r8 = this;
            java.util.Objects.requireNonNull(r9)
            java.util.Objects.requireNonNull(r11)
            java.lang.Object r0 = r8.mLock
            monitor-enter(r0)
            com.android.server.devicepolicy.PolicyState r7 = r8.getGlobalPolicyStateLocked(r9)     // Catch: java.lang.Throwable -> L22
            boolean r1 = android.app.admin.flags.Flags.devicePolicySizeTrackingInternalBugFixEnabled()     // Catch: java.lang.Throwable -> L22
            if (r1 == 0) goto L25
            r6 = -1
            r1 = r8
            r2 = r7
            r3 = r10
            r4 = r11
            r5 = r9
            boolean r1 = r1.handleAdminPolicySizeLimit(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L22
            if (r1 != 0) goto L25
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L22
            goto Lb0
        L22:
            r8 = move-exception
            goto Lb1
        L25:
            android.app.admin.PolicyKey r1 = r9.mPolicyKey     // Catch: java.lang.Throwable -> L22
            java.lang.String r1 = r1.getIdentifier()     // Catch: java.lang.Throwable -> L22
            java.lang.String r2 = com.android.server.devicepolicy.DevicePolicyEngine.CELLULAR_2G_USER_RESTRICTION_ID     // Catch: java.lang.Throwable -> L22
            boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L22
            r2 = -1
            r3 = 0
            r4 = 1
            if (r1 != 0) goto L38
        L36:
            r1 = r3
            goto L51
        L38:
            android.content.Context r1 = r8.mContext     // Catch: java.lang.Throwable -> L22 java.lang.IllegalStateException -> L49
            java.lang.Class<android.telephony.TelephonyManager> r5 = android.telephony.TelephonyManager.class
            java.lang.Object r1 = r1.getSystemService(r5)     // Catch: java.lang.Throwable -> L22 java.lang.IllegalStateException -> L49
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1     // Catch: java.lang.Throwable -> L22 java.lang.IllegalStateException -> L49
            java.lang.String r5 = "CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK"
            boolean r1 = r1.isRadioInterfaceCapabilitySupported(r5)     // Catch: java.lang.Throwable -> L22 java.lang.IllegalStateException -> L49
            goto L4a
        L49:
            r1 = r3
        L4a:
            if (r1 != 0) goto L36
            r1 = 4
            r8.sendPolicyResultToAdmin(r10, r9, r1, r2)     // Catch: java.lang.Throwable -> L22
            r1 = r4
        L51:
            if (r1 == 0) goto L5c
            java.lang.String r8 = "DevicePolicyEngine"
            java.lang.String r9 = "Device does not support capabilities required to disable 2g. Not setting global policy state."
            android.util.Log.i(r8, r9)     // Catch: java.lang.Throwable -> L22
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L22
            goto Lb0
        L5c:
            boolean r1 = r7.addPolicy(r10, r11)     // Catch: java.lang.Throwable -> L22
            boolean r5 = r8.applyGlobalPolicyOnUsersWithLocalPoliciesLocked(r9, r10, r11)     // Catch: java.lang.Throwable -> L22
            r8.maybeForceEnforcementRefreshLocked(r9)     // Catch: java.lang.Throwable -> L22
            if (r1 == 0) goto L78
            com.android.server.devicepolicy.PolicyState r1 = r8.getGlobalPolicyStateLocked(r9)     // Catch: java.lang.Throwable -> L22
            android.app.admin.PolicyValue r6 = r1.mCurrentResolvedPolicy     // Catch: java.lang.Throwable -> L22
            r8.enforcePolicy(r9, r6, r2)     // Catch: java.lang.Throwable -> L22
            r8.sendPolicyChangedToAdminsLocked(r1, r10, r9, r2)     // Catch: java.lang.Throwable -> L22
            r8.sendDevicePolicyChangedToSystem(r2)     // Catch: java.lang.Throwable -> L22
        L78:
            android.app.admin.PolicyValue r1 = r7.mCurrentResolvedPolicy     // Catch: java.lang.Throwable -> L22
            boolean r1 = java.util.Objects.equals(r1, r11)     // Catch: java.lang.Throwable -> L22
            if (r1 != 0) goto L9f
            boolean r6 = shouldApplyPackageSetUnionPolicyHack(r9)     // Catch: java.lang.Throwable -> L22
            if (r6 == 0) goto L9f
            android.app.admin.PolicyValue r1 = r7.mCurrentResolvedPolicy     // Catch: java.lang.Throwable -> L22
            if (r1 == 0) goto L9e
            java.lang.Object r1 = r1.getValue()     // Catch: java.lang.Throwable -> L22
            java.util.Set r1 = (java.util.Set) r1     // Catch: java.lang.Throwable -> L22
            java.lang.Object r11 = r11.getValue()     // Catch: java.lang.Throwable -> L22
            java.util.Collection r11 = (java.util.Collection) r11     // Catch: java.lang.Throwable -> L22
            boolean r11 = r1.containsAll(r11)     // Catch: java.lang.Throwable -> L22
            if (r11 == 0) goto L9e
            r1 = r4
            goto L9f
        L9e:
            r1 = r3
        L9f:
            if (r1 == 0) goto La4
            if (r5 == 0) goto La4
            r3 = r4
        La4:
            r11 = r3 ^ 1
            r8.sendPolicyResultToAdmin(r10, r9, r11, r2)     // Catch: java.lang.Throwable -> L22
            r8.updateDeviceAdminServiceOnPolicyAddLocked(r10)     // Catch: java.lang.Throwable -> L22
            r8.write()     // Catch: java.lang.Throwable -> L22
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L22
        Lb0:
            return
        Lb1:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L22
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.devicepolicy.DevicePolicyEngine.setGlobalPolicy(com.android.server.devicepolicy.PolicyDefinition,"
                    + " com.android.server.devicepolicy.EnforcingAdmin,"
                    + " android.app.admin.PolicyValue):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setLocalPolicy(
            PolicyDefinition policyDefinition,
            EnforcingAdmin enforcingAdmin,
            PolicyValue policyValue,
            int i,
            boolean z) {
        boolean addPolicy;
        Objects.requireNonNull(policyDefinition);
        Objects.requireNonNull(enforcingAdmin);
        synchronized (this.mLock) {
            try {
                PolicyState localPolicyStateLocked = getLocalPolicyStateLocked(policyDefinition, i);
                if (!Flags.devicePolicySizeTrackingInternalBugFixEnabled()
                        || handleAdminPolicySizeLimit(
                                localPolicyStateLocked,
                                enforcingAdmin,
                                policyValue,
                                policyDefinition,
                                i)) {
                    if (policyDefinition.isNonCoexistablePolicy()) {
                        setNonCoexistableLocalPolicyLocked(
                                policyDefinition,
                                localPolicyStateLocked,
                                enforcingAdmin,
                                policyValue,
                                i,
                                z);
                        return;
                    }
                    if (hasGlobalPolicyLocked(policyDefinition)) {
                        LinkedHashMap policiesSetByAdmins =
                                getGlobalPolicyStateLocked(policyDefinition)
                                        .getPoliciesSetByAdmins();
                        localPolicyStateLocked.mPoliciesSetByAdmins.put(
                                enforcingAdmin, policyValue);
                        addPolicy = localPolicyStateLocked.resolvePolicy(policiesSetByAdmins);
                    } else {
                        addPolicy = localPolicyStateLocked.addPolicy(enforcingAdmin, policyValue);
                    }
                    if (!z) {
                        maybeForceEnforcementRefreshLocked(policyDefinition);
                        if (addPolicy) {
                            PolicyState localPolicyStateLocked2 =
                                    getLocalPolicyStateLocked(policyDefinition, i);
                            enforcePolicy(
                                    policyDefinition,
                                    localPolicyStateLocked2.mCurrentResolvedPolicy,
                                    i);
                            sendPolicyChangedToAdminsLocked(
                                    localPolicyStateLocked2, enforcingAdmin, policyDefinition, i);
                            if (hasGlobalPolicyLocked(policyDefinition)) {
                                sendPolicyChangedToAdminsLocked(
                                        getGlobalPolicyStateLocked(policyDefinition),
                                        enforcingAdmin,
                                        policyDefinition,
                                        i);
                            }
                            sendDevicePolicyChangedToSystem(i);
                        }
                        boolean equals =
                                Objects.equals(
                                        localPolicyStateLocked.mCurrentResolvedPolicy, policyValue);
                        int i2 = equals;
                        if (equals == 0) {
                            i2 = equals;
                            if (shouldApplyPackageSetUnionPolicyHack(policyDefinition)) {
                                PolicyValue policyValue2 =
                                        localPolicyStateLocked.mCurrentResolvedPolicy;
                                i2 =
                                        (policyValue2 == null
                                                        || policyValue == null
                                                        || !((Set) policyValue2.getValue())
                                                                .containsAll(
                                                                        (Collection)
                                                                                policyValue
                                                                                        .getValue()))
                                                ? 0
                                                : 1;
                            }
                        }
                        sendPolicyResultToAdmin(enforcingAdmin, policyDefinition, i2 ^ 1, i);
                    }
                    updateDeviceAdminServiceOnPolicyAddLocked(enforcingAdmin);
                    write();
                    if ((policyDefinition.mPolicyFlags & 4) != 0) {
                        Binder.withCleanCallingIdentity(
                                new DevicePolicyEngine$$ExternalSyntheticLambda2(
                                        this, i, policyValue, policyDefinition, enforcingAdmin));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
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
            policyState.getClass();
            Objects.requireNonNull(enforcingAdmin);
            if (policyState.mPoliciesSetByAdmins.remove(enforcingAdmin) != null) {
                PolicyDefinition policyDefinition2 = policyState.mPolicyDefinition;
                if (!policyDefinition2.isNonCoexistablePolicy()) {
                    PolicyValue resolve =
                            policyDefinition2.mResolutionMechanism.resolve(
                                    policyState.mPoliciesSetByAdmins);
                    Objects.equals(resolve, policyState.mCurrentResolvedPolicy);
                    policyState.mCurrentResolvedPolicy = resolve;
                }
            }
        } else {
            policyState.addPolicy(enforcingAdmin, policyValue);
        }
        if (!z) {
            enforcePolicy(policyDefinition, policyValue, i);
        }
        if (policyState.getPoliciesSetByAdmins().isEmpty() && this.mLocalPolicies.contains(i)) {
            ((Map) this.mLocalPolicies.get(i)).remove(policyDefinition.mPolicyKey);
        }
        updateDeviceAdminServiceOnPolicyAddLocked(enforcingAdmin);
        write();
        if ((policyDefinition.mPolicyFlags & 4) != 0) {
            Binder.withCleanCallingIdentity(
                    new DevicePolicyEngine$$ExternalSyntheticLambda2(
                            this, i, policyValue, policyDefinition, enforcingAdmin));
        }
    }

    public final void updateDeviceAdminServiceOnPolicyAddLocked(EnforcingAdmin enforcingAdmin) {
        int i = enforcingAdmin.mUserId;
        if (this.mEnforcingAdmins.contains(i)
                && ((Set) this.mEnforcingAdmins.get(i)).contains(enforcingAdmin)) {
            return;
        }
        SparseArray sparseArray = this.mEnforcingAdmins;
        int i2 = enforcingAdmin.mUserId;
        if (!sparseArray.contains(i2)) {
            this.mEnforcingAdmins.put(i2, new HashSet());
        }
        ((Set) this.mEnforcingAdmins.get(i2)).add(enforcingAdmin);
        if (enforcingAdmin.hasAuthority("enterprise")) {
            return;
        }
        this.mDeviceAdminServiceController.startServiceForAdmin(i, enforcingAdmin.mPackageName);
    }

    public final void updateDeviceAdminServiceOnPolicyRemoveLocked(EnforcingAdmin enforcingAdmin) {
        Iterator it = ((HashMap) this.mGlobalPolicies).keySet().iterator();
        while (it.hasNext()) {
            if (((PolicyState) ((HashMap) this.mGlobalPolicies).get((PolicyKey) it.next()))
                    .getPoliciesSetByAdmins()
                    .containsKey(enforcingAdmin)) {
                return;
            }
        }
        for (int i = 0; i < this.mLocalPolicies.size(); i++) {
            SparseArray sparseArray = this.mLocalPolicies;
            for (PolicyKey policyKey : ((Map) sparseArray.get(sparseArray.keyAt(i))).keySet()) {
                SparseArray sparseArray2 = this.mLocalPolicies;
                if (((PolicyState) ((Map) sparseArray2.get(sparseArray2.keyAt(i))).get(policyKey))
                        .getPoliciesSetByAdmins()
                        .containsKey(enforcingAdmin)) {
                    return;
                }
            }
        }
        int i2 = enforcingAdmin.mUserId;
        if (this.mEnforcingAdmins.contains(i2)) {
            ((Set) this.mEnforcingAdmins.get(i2)).remove(enforcingAdmin);
            if (((Set) this.mEnforcingAdmins.get(i2)).isEmpty()) {
                this.mEnforcingAdmins.remove(enforcingAdmin.mUserId);
            }
        }
        if (enforcingAdmin.hasAuthority("enterprise")) {
            return;
        }
        this.mDeviceAdminServiceController.stopServiceForAdmin(i2, enforcingAdmin.mPackageName);
    }

    public final void updateDeviceAdminsServicesForUser(int i, boolean z) {
        DeviceAdminServiceController deviceAdminServiceController =
                this.mDeviceAdminServiceController;
        if (!z) {
            deviceAdminServiceController.stopServicesForUser(i);
            return;
        }
        for (EnforcingAdmin enforcingAdmin : getEnforcingAdminsOnUser(i)) {
            if (!enforcingAdmin.hasAuthority("enterprise")) {
                deviceAdminServiceController.startServiceForAdmin(i, enforcingAdmin.mPackageName);
            }
        }
    }

    public final void write() {
        synchronized (this.mLock) {
            Log.d("DevicePolicyEngine", "Writing device policies to file.");
            new DevicePoliciesReaderWriter().writeToFileLocked();
        }
    }
}
