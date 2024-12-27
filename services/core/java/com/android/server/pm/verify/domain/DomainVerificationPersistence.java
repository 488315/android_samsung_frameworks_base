package com.android.server.pm.verify.domain;

import android.content.UriRelativeFilter;
import android.content.UriRelativeFilterGroup;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.SettingsXml$ReadSectionImpl;
import com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState;
import com.android.server.pm.verify.domain.models.DomainVerificationPkgState;
import com.android.server.pm.verify.domain.models.DomainVerificationStateMap;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class DomainVerificationPersistence {

    public final class ReadResult {
        public final ArrayMap active;
        public final ArrayMap restored;

        public ReadResult(ArrayMap arrayMap, ArrayMap arrayMap2) {
            this.active = arrayMap;
            this.restored = arrayMap2;
        }
    }

    public static ReadResult readFromXml(TypedXmlPullParser typedXmlPullParser) {
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl =
                new SettingsXml$ReadSectionImpl(typedXmlPullParser);
        settingsXml$ReadSectionImpl.children();
        while (settingsXml$ReadSectionImpl.moveToNextInternal(null)) {
            String name = ((TypedXmlPullParser) settingsXml$ReadSectionImpl.mParser).getName();
            name.getClass();
            if (name.equals("active")) {
                readPackageStates(settingsXml$ReadSectionImpl, arrayMap);
            } else if (name.equals("restored")) {
                readPackageStates(settingsXml$ReadSectionImpl, arrayMap2);
            }
        }
        return new ReadResult(arrayMap, arrayMap2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:14:0x0097. Please report as an issue. */
    public static void readPackageStates(
            SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl, ArrayMap arrayMap) {
        boolean z;
        int i;
        int i2;
        UUID uuid;
        boolean z2;
        String str;
        DomainVerificationInternalUserState domainVerificationInternalUserState;
        int i3;
        boolean z3;
        UUID uuid2;
        boolean z4;
        String str2;
        int i4 = -1;
        boolean z5 = false;
        settingsXml$ReadSectionImpl.children();
        while (settingsXml$ReadSectionImpl.moveToNextInternal("package-state")) {
            String string = settingsXml$ReadSectionImpl.getString("packageName");
            String string2 = settingsXml$ReadSectionImpl.getString("id");
            DomainVerificationPkgState domainVerificationPkgState = null;
            String str3 = null;
            boolean attributeBoolean =
                    ((TypedXmlPullParser) settingsXml$ReadSectionImpl.mParser)
                            .getAttributeBoolean((String) null, "hasAutoVerifyDomains", z5);
            String string3 = settingsXml$ReadSectionImpl.getString("signature");
            if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                z = z5 ? 1 : 0;
                i = i4;
            } else {
                UUID fromString = UUID.fromString(string2);
                ArrayMap arrayMap2 = new ArrayMap();
                SparseArray sparseArray = new SparseArray();
                ArrayMap arrayMap3 = new ArrayMap();
                settingsXml$ReadSectionImpl.children();
                while (settingsXml$ReadSectionImpl.moveToNextInternal(str3)) {
                    String name =
                            ((TypedXmlPullParser) settingsXml$ReadSectionImpl.mParser).getName();
                    name.getClass();
                    switch (name.hashCode()) {
                        case -1576041916:
                            if (name.equals("user-states")) {
                                i2 = z5 ? 1 : 0;
                                break;
                            }
                            i2 = i4;
                            break;
                        case 109757585:
                            if (name.equals(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)) {
                                i2 = 1;
                                break;
                            }
                            i2 = i4;
                            break;
                        case 1632406025:
                            if (name.equals("uri-relative-filter-groups")) {
                                i2 = 2;
                                break;
                            }
                            i2 = i4;
                            break;
                        default:
                            i2 = i4;
                            break;
                    }
                    switch (i2) {
                        case 0:
                            uuid = fromString;
                            z2 = attributeBoolean;
                            str = string3;
                            settingsXml$ReadSectionImpl.children();
                            while (settingsXml$ReadSectionImpl.moveToNextInternal("user-state")) {
                                int i5 = settingsXml$ReadSectionImpl.getInt(-1, "userId");
                                if (i5 == -1) {
                                    domainVerificationInternalUserState = null;
                                } else {
                                    boolean attributeBoolean2 =
                                            ((TypedXmlPullParser)
                                                            settingsXml$ReadSectionImpl.mParser)
                                                    .getAttributeBoolean(
                                                            (String) null,
                                                            "allowLinkHandling",
                                                            false);
                                    ArraySet arraySet = new ArraySet();
                                    settingsXml$ReadSectionImpl.children();
                                    while (settingsXml$ReadSectionImpl.moveToNextInternal(
                                            "enabled-hosts")) {
                                        settingsXml$ReadSectionImpl.children();
                                        while (settingsXml$ReadSectionImpl.moveToNextInternal(
                                                "host")) {
                                            String string4 =
                                                    settingsXml$ReadSectionImpl.getString("name");
                                            if (!TextUtils.isEmpty(string4)) {
                                                arraySet.add(string4);
                                            }
                                        }
                                    }
                                    domainVerificationInternalUserState =
                                            new DomainVerificationInternalUserState(
                                                    i5, arraySet, attributeBoolean2);
                                }
                                if (domainVerificationInternalUserState != null) {
                                    sparseArray.put(
                                            domainVerificationInternalUserState.mUserId,
                                            domainVerificationInternalUserState);
                                }
                            }
                            i3 = -1;
                            str3 = null;
                            z3 = false;
                            break;
                        case 1:
                            uuid = fromString;
                            z2 = attributeBoolean;
                            str = string3;
                            settingsXml$ReadSectionImpl.children();
                            while (settingsXml$ReadSectionImpl.moveToNextInternal("domain")) {
                                arrayMap2.put(
                                        settingsXml$ReadSectionImpl.getString("name"),
                                        Integer.valueOf(
                                                settingsXml$ReadSectionImpl.getInt(
                                                        0,
                                                        LauncherConfigurationInternal
                                                                .KEY_STATE_BOOLEAN)));
                            }
                            i3 = -1;
                            str3 = null;
                            z3 = false;
                            break;
                        case 2:
                            settingsXml$ReadSectionImpl.children();
                            while (settingsXml$ReadSectionImpl.moveToNextInternal("domain")) {
                                String string5 = settingsXml$ReadSectionImpl.getString("name");
                                settingsXml$ReadSectionImpl.children();
                                ArrayList arrayList = new ArrayList();
                                while (settingsXml$ReadSectionImpl.moveToNextInternal(
                                        "uri-relative-filter-group")) {
                                    UriRelativeFilterGroup uriRelativeFilterGroup =
                                            new UriRelativeFilterGroup(
                                                    settingsXml$ReadSectionImpl.getInt(
                                                            i4, "action"));
                                    settingsXml$ReadSectionImpl.children();
                                    while (settingsXml$ReadSectionImpl.moveToNextInternal(
                                            "uri-relative-filter")) {
                                        String string6 =
                                                settingsXml$ReadSectionImpl.getString("filter");
                                        if (string6 != null) {
                                            str2 = string3;
                                            z4 = attributeBoolean;
                                            uuid2 = fromString;
                                            uriRelativeFilterGroup.addUriRelativeFilter(
                                                    new UriRelativeFilter(
                                                            settingsXml$ReadSectionImpl.getInt(
                                                                    -1, "uri-part"),
                                                            settingsXml$ReadSectionImpl.getInt(
                                                                    -1, "pattern-type"),
                                                            string6));
                                        } else {
                                            uuid2 = fromString;
                                            z4 = attributeBoolean;
                                            str2 = string3;
                                        }
                                        string3 = str2;
                                        attributeBoolean = z4;
                                        fromString = uuid2;
                                    }
                                    arrayList.add(uriRelativeFilterGroup);
                                    i4 = -1;
                                }
                                arrayMap3.put(string5, arrayList);
                                i4 = -1;
                                z5 = false;
                                str3 = null;
                            }
                            uuid = fromString;
                            z2 = attributeBoolean;
                            str = string3;
                            z3 = z5;
                            i3 = i4;
                            break;
                        default:
                            z3 = z5 ? 1 : 0;
                            uuid = fromString;
                            z2 = attributeBoolean;
                            str = string3;
                            i3 = i4;
                            break;
                    }
                    i4 = i3;
                    z5 = z3;
                    string3 = str;
                    attributeBoolean = z2;
                    fromString = uuid;
                }
                z = z5 ? 1 : 0;
                i = i4;
                domainVerificationPkgState =
                        new DomainVerificationPkgState(
                                string,
                                fromString,
                                attributeBoolean,
                                arrayMap2,
                                sparseArray,
                                string3,
                                arrayMap3);
            }
            if (domainVerificationPkgState != null) {
                arrayMap.put(domainVerificationPkgState.mPackageName, domainVerificationPkgState);
            }
            i4 = i;
            z5 = z;
        }
    }

    public static void writePackageStates(
            SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl,
            Collection collection,
            int i,
            DomainVerificationService$$ExternalSyntheticLambda1
                    domainVerificationService$$ExternalSyntheticLambda1) {
        if (collection.isEmpty()) {
            return;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            DomainVerificationPkgState domainVerificationPkgState =
                    (DomainVerificationPkgState) it.next();
            String str = domainVerificationPkgState.mPackageName;
            String str2 =
                    domainVerificationService$$ExternalSyntheticLambda1 == null
                            ? null
                            : (String)
                                    domainVerificationService$$ExternalSyntheticLambda1.apply(str);
            if (str2 == null) {
                str2 = domainVerificationPkgState.mBackupSignatureHash;
            }
            settingsXml$ReadSectionImpl.startSection("package-state");
            settingsXml$ReadSectionImpl.attribute("packageName", str);
            settingsXml$ReadSectionImpl.attribute("id", domainVerificationPkgState.mId.toString());
            boolean z = domainVerificationPkgState.mHasAutoVerifyDomains;
            if (z) {
                ((TypedXmlSerializer) settingsXml$ReadSectionImpl.mParser)
                        .attributeBoolean((String) null, "hasAutoVerifyDomains", z);
            }
            settingsXml$ReadSectionImpl.attribute("signature", str2);
            try {
                writeStateMap(settingsXml$ReadSectionImpl, domainVerificationPkgState.mStateMap);
                SparseArray sparseArray = domainVerificationPkgState.mUserStates;
                int size = sparseArray.size();
                if (size != 0) {
                    settingsXml$ReadSectionImpl.startSection("user-states");
                    if (i == -1) {
                        for (int i2 = 0; i2 < size; i2++) {
                            try {
                                writeUserStateToXml(
                                        settingsXml$ReadSectionImpl,
                                        (DomainVerificationInternalUserState)
                                                sparseArray.valueAt(i2));
                            } finally {
                            }
                        }
                    } else {
                        DomainVerificationInternalUserState domainVerificationInternalUserState =
                                (DomainVerificationInternalUserState) sparseArray.get(i);
                        if (domainVerificationInternalUserState != null) {
                            writeUserStateToXml(
                                    settingsXml$ReadSectionImpl,
                                    domainVerificationInternalUserState);
                        }
                    }
                    settingsXml$ReadSectionImpl.close();
                }
                writeUriRelativeFilterGroupMap(
                        settingsXml$ReadSectionImpl,
                        domainVerificationPkgState.mUriRelativeFilterGroupMap);
                settingsXml$ReadSectionImpl.close();
            } catch (Throwable th) {
                try {
                    settingsXml$ReadSectionImpl.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    public static void writeStateMap(
            SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl, ArrayMap arrayMap) {
        if (arrayMap.isEmpty()) {
            return;
        }
        settingsXml$ReadSectionImpl.startSection(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        try {
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                settingsXml$ReadSectionImpl.startSection("domain");
                settingsXml$ReadSectionImpl.attribute("name", (String) arrayMap.keyAt(i));
                settingsXml$ReadSectionImpl.attribute(
                        ((Integer) arrayMap.valueAt(i)).intValue(),
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                settingsXml$ReadSectionImpl.close();
            }
            settingsXml$ReadSectionImpl.close();
        } catch (Throwable th) {
            try {
                settingsXml$ReadSectionImpl.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void writeToXml(
            TypedXmlSerializer typedXmlSerializer,
            DomainVerificationStateMap domainVerificationStateMap,
            ArrayMap arrayMap,
            ArrayMap arrayMap2,
            int i,
            DomainVerificationService$$ExternalSyntheticLambda1
                    domainVerificationService$$ExternalSyntheticLambda1) {
        SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl =
                new SettingsXml$ReadSectionImpl(typedXmlSerializer);
        try {
            settingsXml$ReadSectionImpl.startSection("domain-verifications");
            try {
                ArraySet arraySet = new ArraySet();
                int size = domainVerificationStateMap.mPackageNameMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arraySet.add(
                            (DomainVerificationPkgState)
                                    domainVerificationStateMap.mPackageNameMap.valueAt(i2));
                }
                int size2 = arrayMap.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arraySet.add((DomainVerificationPkgState) arrayMap.valueAt(i3));
                }
                settingsXml$ReadSectionImpl.startSection("active");
                try {
                    writePackageStates(
                            settingsXml$ReadSectionImpl,
                            arraySet,
                            i,
                            domainVerificationService$$ExternalSyntheticLambda1);
                    settingsXml$ReadSectionImpl.close();
                    settingsXml$ReadSectionImpl.startSection("restored");
                    try {
                        writePackageStates(
                                settingsXml$ReadSectionImpl,
                                arrayMap2.values(),
                                i,
                                domainVerificationService$$ExternalSyntheticLambda1);
                        settingsXml$ReadSectionImpl.close();
                        settingsXml$ReadSectionImpl.close();
                        if (settingsXml$ReadSectionImpl.mDepthStack != null) {
                            while (!settingsXml$ReadSectionImpl.mDepthStack.isEmpty()) {
                                settingsXml$ReadSectionImpl.close();
                            }
                        }
                        typedXmlSerializer.flush();
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (Throwable th) {
            try {
                if (settingsXml$ReadSectionImpl.mDepthStack != null) {
                    while (!settingsXml$ReadSectionImpl.mDepthStack.isEmpty()) {
                        settingsXml$ReadSectionImpl.close();
                    }
                }
                typedXmlSerializer.flush();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void writeUriRelativeFilterGroup(
            SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl,
            UriRelativeFilterGroup uriRelativeFilterGroup) {
        settingsXml$ReadSectionImpl.startSection("uri-relative-filter-group");
        settingsXml$ReadSectionImpl.attribute(uriRelativeFilterGroup.getAction(), "action");
        try {
            for (UriRelativeFilter uriRelativeFilter :
                    uriRelativeFilterGroup.getUriRelativeFilters()) {
                settingsXml$ReadSectionImpl.startSection("uri-relative-filter");
                settingsXml$ReadSectionImpl.attribute(uriRelativeFilter.getUriPart(), "uri-part");
                settingsXml$ReadSectionImpl.attribute(
                        uriRelativeFilter.getPatternType(), "pattern-type");
                settingsXml$ReadSectionImpl.attribute("filter", uriRelativeFilter.getFilter());
                settingsXml$ReadSectionImpl.close();
            }
            settingsXml$ReadSectionImpl.close();
        } catch (Throwable th) {
            try {
                settingsXml$ReadSectionImpl.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void writeUriRelativeFilterGroupMap(
            SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl, ArrayMap arrayMap) {
        if (arrayMap.isEmpty()) {
            return;
        }
        settingsXml$ReadSectionImpl.startSection("uri-relative-filter-groups");
        for (int i = 0; i < arrayMap.size(); i++) {
            try {
                String str = (String) arrayMap.keyAt(i);
                List list = (List) arrayMap.valueAt(i);
                if (!list.isEmpty()) {
                    settingsXml$ReadSectionImpl.startSection("domain");
                    settingsXml$ReadSectionImpl.attribute("name", str);
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        try {
                            writeUriRelativeFilterGroup(
                                    settingsXml$ReadSectionImpl,
                                    (UriRelativeFilterGroup) list.get(i2));
                        } finally {
                        }
                    }
                    settingsXml$ReadSectionImpl.close();
                }
            } catch (Throwable th) {
                try {
                    settingsXml$ReadSectionImpl.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        settingsXml$ReadSectionImpl.close();
    }

    public static void writeUserStateToXml(
            SettingsXml$ReadSectionImpl settingsXml$ReadSectionImpl,
            DomainVerificationInternalUserState domainVerificationInternalUserState) {
        settingsXml$ReadSectionImpl.startSection("user-state");
        settingsXml$ReadSectionImpl.attribute(
                domainVerificationInternalUserState.mUserId, "userId");
        boolean z = domainVerificationInternalUserState.mLinkHandlingAllowed;
        if (z) {
            ((TypedXmlSerializer) settingsXml$ReadSectionImpl.mParser)
                    .attributeBoolean((String) null, "allowLinkHandling", z);
        }
        try {
            ArraySet arraySet = domainVerificationInternalUserState.mEnabledHosts;
            if (!arraySet.isEmpty()) {
                settingsXml$ReadSectionImpl.startSection("enabled-hosts");
                try {
                    int size = arraySet.size();
                    for (int i = 0; i < size; i++) {
                        settingsXml$ReadSectionImpl.startSection("host");
                        settingsXml$ReadSectionImpl.attribute("name", (String) arraySet.valueAt(i));
                        settingsXml$ReadSectionImpl.close();
                    }
                    settingsXml$ReadSectionImpl.close();
                } finally {
                }
            }
            settingsXml$ReadSectionImpl.close();
        } catch (Throwable th) {
            try {
                settingsXml$ReadSectionImpl.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
