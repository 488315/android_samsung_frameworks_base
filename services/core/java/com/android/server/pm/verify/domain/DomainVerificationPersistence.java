package com.android.server.pm.verify.domain;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.SettingsXml;
import com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState;
import com.android.server.pm.verify.domain.models.DomainVerificationPkgState;
import com.android.server.pm.verify.domain.models.DomainVerificationStateMap;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Function;

/* loaded from: classes3.dex */
public abstract class DomainVerificationPersistence {
    public static void writeToXml(TypedXmlSerializer typedXmlSerializer, DomainVerificationStateMap domainVerificationStateMap, ArrayMap arrayMap, ArrayMap arrayMap2, int i, Function function) {
        SettingsXml.Serializer serializer = SettingsXml.serializer(typedXmlSerializer);
        try {
            SettingsXml.WriteSection startSection = serializer.startSection("domain-verifications");
            try {
                ArraySet arraySet = new ArraySet();
                int size = domainVerificationStateMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arraySet.add((DomainVerificationPkgState) domainVerificationStateMap.valueAt(i2));
                }
                int size2 = arrayMap.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arraySet.add((DomainVerificationPkgState) arrayMap.valueAt(i3));
                }
                SettingsXml.WriteSection startSection2 = serializer.startSection("active");
                try {
                    writePackageStates(startSection2, arraySet, i, function);
                    if (startSection2 != null) {
                        startSection2.close();
                    }
                    startSection2 = serializer.startSection("restored");
                    try {
                        writePackageStates(startSection2, arrayMap2.values(), i, function);
                        if (startSection2 != null) {
                            startSection2.close();
                        }
                        if (startSection != null) {
                            startSection.close();
                        }
                        serializer.close();
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (Throwable th) {
            if (serializer != null) {
                try {
                    serializer.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static void writePackageStates(SettingsXml.WriteSection writeSection, Collection collection, int i, Function function) {
        if (collection.isEmpty()) {
            return;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            writePkgStateToXml(writeSection, (DomainVerificationPkgState) it.next(), i, function);
        }
    }

    public static ReadResult readFromXml(TypedXmlPullParser typedXmlPullParser) {
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        SettingsXml.ChildSection children = SettingsXml.parser(typedXmlPullParser).children();
        while (children.moveToNext()) {
            String name = children.getName();
            name.hashCode();
            if (name.equals("active")) {
                readPackageStates(children, arrayMap);
            } else if (name.equals("restored")) {
                readPackageStates(children, arrayMap2);
            }
        }
        return new ReadResult(arrayMap, arrayMap2);
    }

    public static void readPackageStates(SettingsXml.ReadSection readSection, ArrayMap arrayMap) {
        SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext("package-state")) {
            DomainVerificationPkgState createPkgStateFromXml = createPkgStateFromXml(children);
            if (createPkgStateFromXml != null) {
                arrayMap.put(createPkgStateFromXml.getPackageName(), createPkgStateFromXml);
            }
        }
    }

    public static DomainVerificationPkgState createPkgStateFromXml(SettingsXml.ReadSection readSection) {
        String string = readSection.getString("packageName");
        String string2 = readSection.getString("id");
        boolean z = readSection.getBoolean("hasAutoVerifyDomains");
        String string3 = readSection.getString("signature");
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
            return null;
        }
        UUID fromString = UUID.fromString(string2);
        ArrayMap arrayMap = new ArrayMap();
        SparseArray sparseArray = new SparseArray();
        SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext()) {
            String name = children.getName();
            name.hashCode();
            if (name.equals("user-states")) {
                readUserStates(children, sparseArray);
            } else if (name.equals(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)) {
                readDomainStates(children, arrayMap);
            }
        }
        return new DomainVerificationPkgState(string, fromString, z, arrayMap, sparseArray, string3);
    }

    public static void readUserStates(SettingsXml.ReadSection readSection, SparseArray sparseArray) {
        SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext("user-state")) {
            DomainVerificationInternalUserState createUserStateFromXml = createUserStateFromXml(children);
            if (createUserStateFromXml != null) {
                sparseArray.put(createUserStateFromXml.getUserId(), createUserStateFromXml);
            }
        }
    }

    public static void readDomainStates(SettingsXml.ReadSection readSection, ArrayMap arrayMap) {
        SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext("domain")) {
            arrayMap.put(children.getString("name"), Integer.valueOf(children.getInt(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0)));
        }
    }

    public static void writePkgStateToXml(SettingsXml.WriteSection writeSection, DomainVerificationPkgState domainVerificationPkgState, int i, Function function) {
        String packageName = domainVerificationPkgState.getPackageName();
        String str = function == null ? null : (String) function.apply(packageName);
        if (str == null) {
            str = domainVerificationPkgState.getBackupSignatureHash();
        }
        SettingsXml.WriteSection attribute = writeSection.startSection("package-state").attribute("packageName", packageName).attribute("id", domainVerificationPkgState.getId().toString()).attribute("hasAutoVerifyDomains", domainVerificationPkgState.isHasAutoVerifyDomains()).attribute("signature", str);
        try {
            writeStateMap(writeSection, domainVerificationPkgState.getStateMap());
            writeUserStates(writeSection, i, domainVerificationPkgState.getUserStates());
            if (attribute != null) {
                attribute.close();
            }
        } catch (Throwable th) {
            if (attribute != null) {
                try {
                    attribute.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static void writeUserStates(SettingsXml.WriteSection writeSection, int i, SparseArray sparseArray) {
        int size = sparseArray.size();
        if (size == 0) {
            return;
        }
        SettingsXml.WriteSection startSection = writeSection.startSection("user-states");
        try {
            if (i == -1) {
                for (int i2 = 0; i2 < size; i2++) {
                    writeUserStateToXml(startSection, (DomainVerificationInternalUserState) sparseArray.valueAt(i2));
                }
            } else {
                DomainVerificationInternalUserState domainVerificationInternalUserState = (DomainVerificationInternalUserState) sparseArray.get(i);
                if (domainVerificationInternalUserState != null) {
                    writeUserStateToXml(startSection, domainVerificationInternalUserState);
                }
            }
            if (startSection != null) {
                startSection.close();
            }
        } catch (Throwable th) {
            if (startSection != null) {
                try {
                    startSection.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static void writeStateMap(SettingsXml.WriteSection writeSection, ArrayMap arrayMap) {
        if (arrayMap.isEmpty()) {
            return;
        }
        SettingsXml.WriteSection startSection = writeSection.startSection(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        try {
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                startSection.startSection("domain").attribute("name", (String) arrayMap.keyAt(i)).attribute(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, ((Integer) arrayMap.valueAt(i)).intValue()).finish();
            }
            if (startSection != null) {
                startSection.close();
            }
        } catch (Throwable th) {
            if (startSection != null) {
                try {
                    startSection.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static DomainVerificationInternalUserState createUserStateFromXml(SettingsXml.ReadSection readSection) {
        int i = readSection.getInt("userId");
        if (i == -1) {
            return null;
        }
        boolean z = readSection.getBoolean("allowLinkHandling", false);
        ArraySet arraySet = new ArraySet();
        SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext("enabled-hosts")) {
            readEnabledHosts(children, arraySet);
        }
        return new DomainVerificationInternalUserState(i, arraySet, z);
    }

    public static void readEnabledHosts(SettingsXml.ReadSection readSection, ArraySet arraySet) {
        SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext("host")) {
            String string = children.getString("name");
            if (!TextUtils.isEmpty(string)) {
                arraySet.add(string);
            }
        }
    }

    public static void writeUserStateToXml(SettingsXml.WriteSection writeSection, DomainVerificationInternalUserState domainVerificationInternalUserState) {
        SettingsXml.WriteSection attribute = writeSection.startSection("user-state").attribute("userId", domainVerificationInternalUserState.getUserId()).attribute("allowLinkHandling", domainVerificationInternalUserState.isLinkHandlingAllowed());
        try {
            ArraySet enabledHosts = domainVerificationInternalUserState.getEnabledHosts();
            if (!enabledHosts.isEmpty()) {
                SettingsXml.WriteSection startSection = attribute.startSection("enabled-hosts");
                try {
                    int size = enabledHosts.size();
                    for (int i = 0; i < size; i++) {
                        startSection.startSection("host").attribute("name", (String) enabledHosts.valueAt(i)).finish();
                    }
                    if (startSection != null) {
                        startSection.close();
                    }
                } finally {
                }
            }
            if (attribute != null) {
                attribute.close();
            }
        } catch (Throwable th) {
            if (attribute != null) {
                try {
                    attribute.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public class ReadResult {
        public final ArrayMap active;
        public final ArrayMap restored;

        public ReadResult(ArrayMap arrayMap, ArrayMap arrayMap2) {
            this.active = arrayMap;
            this.restored = arrayMap2;
        }
    }
}
