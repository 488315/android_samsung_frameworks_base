package com.android.server.devicepolicy;

import android.app.admin.SystemUpdateInfo;
import android.app.admin.SystemUpdatePolicy;
import android.app.admin.flags.Flags;
import android.content.ComponentName;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public final class OwnersData {
    public OwnerInfo mDeviceOwner;
    public ArrayMap mDeviceOwnerProtectedPackages;
    public final PolicyPathProvider mPathProvider;
    public LocalDate mSystemUpdateFreezeEnd;
    public LocalDate mSystemUpdateFreezeStart;
    public SystemUpdateInfo mSystemUpdateInfo;
    public SystemUpdatePolicy mSystemUpdatePolicy;
    public int mDeviceOwnerUserId = -10000;
    public final ArrayMap mDeviceOwnerTypes = new ArrayMap();
    public final ArrayMap mProfileOwners = new ArrayMap();
    public boolean mMigratedToPolicyEngine = false;
    public boolean mSecurityLoggingMigrated = false;
    public boolean mRequiredPasswordComplexityMigrated = false;
    public boolean mSuspendedPackagesMigrated = false;
    public boolean mPoliciesMigratedPostUpdate = false;

    public final class DeviceOwnerReadWriter extends FileReadWriter {
        public DeviceOwnerReadWriter() {
            super(OwnersData.this.getDeviceOwnerFile());
        }

        /* JADX WARN: Code restructure failed: missing block: B:65:0x005f, code lost:

           if (r10.equals("device-owner-context") == false) goto L7;
        */
        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean readInner(
                com.android.modules.utils.TypedXmlPullParser r8, int r9, java.lang.String r10) {
            /*
                Method dump skipped, instructions count: 432
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.devicepolicy.OwnersData.DeviceOwnerReadWriter.readInner(com.android.modules.utils.TypedXmlPullParser,"
                        + " int, java.lang.String):boolean");
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public final boolean shouldWrite() {
            if (!Flags.alwaysPersistDo()) {
                OwnersData ownersData = OwnersData.this;
                if (ownersData.mDeviceOwner == null
                        && ownersData.mSystemUpdatePolicy == null
                        && ownersData.mSystemUpdateInfo == null) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public final void writeInner(TypedXmlSerializer typedXmlSerializer) {
            OwnersData ownersData = OwnersData.this;
            OwnerInfo ownerInfo = ownersData.mDeviceOwner;
            if (ownerInfo != null) {
                ownerInfo.writeToXml(typedXmlSerializer, "device-owner");
                typedXmlSerializer.startTag((String) null, "device-owner-context");
                typedXmlSerializer.attributeInt(
                        (String) null, "userId", ownersData.mDeviceOwnerUserId);
                typedXmlSerializer.endTag((String) null, "device-owner-context");
            }
            if (!ownersData.mDeviceOwnerTypes.isEmpty()) {
                for (Map.Entry entry : ownersData.mDeviceOwnerTypes.entrySet()) {
                    typedXmlSerializer.startTag((String) null, "device-owner-type");
                    typedXmlSerializer.attribute((String) null, "package", (String) entry.getKey());
                    typedXmlSerializer.attributeInt(
                            (String) null, "value", ((Integer) entry.getValue()).intValue());
                    typedXmlSerializer.endTag((String) null, "device-owner-type");
                }
            }
            if (ownersData.mSystemUpdatePolicy != null) {
                typedXmlSerializer.startTag((String) null, "system-update-policy");
                ownersData.mSystemUpdatePolicy.saveToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "system-update-policy");
            }
            SystemUpdateInfo systemUpdateInfo = ownersData.mSystemUpdateInfo;
            if (systemUpdateInfo != null) {
                systemUpdateInfo.writeToXml(typedXmlSerializer, "pending-ota-info");
            }
            if (ownersData.mSystemUpdateFreezeStart != null
                    || ownersData.mSystemUpdateFreezeEnd != null) {
                typedXmlSerializer.startTag((String) null, "freeze-record");
                LocalDate localDate = ownersData.mSystemUpdateFreezeStart;
                if (localDate != null) {
                    typedXmlSerializer.attribute((String) null, "start", localDate.toString());
                }
                LocalDate localDate2 = ownersData.mSystemUpdateFreezeEnd;
                if (localDate2 != null) {
                    typedXmlSerializer.attribute((String) null, "end", localDate2.toString());
                }
                typedXmlSerializer.endTag((String) null, "freeze-record");
            }
            typedXmlSerializer.startTag((String) null, "policy-engine-migration");
            typedXmlSerializer.attributeBoolean(
                    (String) null, "migratedToPolicyEngine", ownersData.mMigratedToPolicyEngine);
            typedXmlSerializer.attributeBoolean(
                    (String) null, "migratedPostUpgrade", ownersData.mPoliciesMigratedPostUpdate);
            if (Flags.securityLogV2Enabled()) {
                typedXmlSerializer.attributeBoolean(
                        (String) null, "securityLogMigrated", ownersData.mSecurityLoggingMigrated);
            }
            if (Flags.unmanagedModeMigration()) {
                typedXmlSerializer.attributeBoolean(
                        (String) null,
                        "passwordComplexityMigrated",
                        ownersData.mRequiredPasswordComplexityMigrated);
                typedXmlSerializer.attributeBoolean(
                        (String) null,
                        "suspendedPackagesMigrated",
                        ownersData.mSuspendedPackagesMigrated);
            }
            typedXmlSerializer.endTag((String) null, "policy-engine-migration");
        }
    }

    public abstract class FileReadWriter {
        public final File mFile;
        public final ReserveAtomicHelper resAtom;

        public FileReadWriter(File file) {
            this.mFile = file;
            this.resAtom = new ReserveAtomicHelper(file);
        }

        /* JADX WARN: Code restructure failed: missing block: B:48:0x00cd, code lost:

           return;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void readFromFileLocked() {
            /*
                r9 = this;
                java.lang.String r0 = "DevicePolicyManagerService"
                java.io.File r1 = r9.mFile
                boolean r1 = r1.exists()
                if (r1 != 0) goto Lb
                return
            Lb:
                com.android.server.devicepolicy.ReserveAtomicHelper r1 = r9.resAtom
                java.io.File r2 = r1.orignalFile
                boolean r2 = r2.exists()
                if (r2 == 0) goto L21
                boolean r2 = r1.failFlag
                if (r2 != 0) goto L21
                android.util.AtomicFile r2 = new android.util.AtomicFile
                java.io.File r3 = r1.orignalFile
                r2.<init>(r3)
                goto L3e
            L21:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "orignal file failed moving to reserve, failflag status : "
                r2.<init>(r3)
                boolean r3 = r1.failFlag
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                java.lang.String r3 = r1.LOG_TAG
                android.util.Slog.d(r3, r2)
                android.util.AtomicFile r2 = new android.util.AtomicFile
                java.io.File r3 = r1.mReserveFile
                r2.<init>(r3)
            L3e:
                r3 = 0
                r4 = 1
                r5 = 0
                java.io.FileInputStream r5 = r2.openRead()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                com.android.modules.utils.TypedXmlPullParser r2 = android.util.Xml.resolvePullParser(r5)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r6 = r3
            L4a:
                int r7 = r2.next()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                if (r7 == r4) goto L91
                r8 = 2
                if (r7 == r8) goto L5a
                r8 = 3
                if (r7 == r8) goto L57
                goto L4a
            L57:
                int r6 = r6 + (-1)
                goto L4a
            L5a:
                int r6 = r6 + 1
                java.lang.String r7 = r2.getName()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                if (r6 != r4) goto L87
                java.lang.String r8 = "root"
                boolean r8 = r8.equals(r7)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                if (r8 != 0) goto L4a
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r2.<init>()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                java.lang.String r6 = "Invalid root tag: "
                r2.append(r6)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                r2.append(r7)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                android.util.Slog.e(r0, r2)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                libcore.io.IoUtils.closeQuietly(r5)
                return
            L83:
                r9 = move-exception
                goto Lce
            L85:
                r2 = move-exception
                goto L95
            L87:
                boolean r7 = r9.readInner(r2, r6, r7)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L85
                if (r7 != 0) goto L4a
                libcore.io.IoUtils.closeQuietly(r5)
                return
            L91:
                libcore.io.IoUtils.closeQuietly(r5)
                goto Lcd
            L95:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L83
                java.lang.String r7 = "setting failFlag previous status : "
                r6.<init>(r7)     // Catch: java.lang.Throwable -> L83
                boolean r7 = r1.failFlag     // Catch: java.lang.Throwable -> L83
                r6.append(r7)     // Catch: java.lang.Throwable -> L83
                java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L83
                java.lang.String r7 = r1.LOG_TAG     // Catch: java.lang.Throwable -> L83
                android.util.Slog.d(r7, r6)     // Catch: java.lang.Throwable -> L83
                boolean r6 = r1.failFlag     // Catch: java.lang.Throwable -> L83
                if (r6 == 0) goto Lb0
                goto Lb3
            Lb0:
                r1.failFlag = r4     // Catch: java.lang.Throwable -> L83
                r3 = r4
            Lb3:
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L83
                r1.<init>()     // Catch: java.lang.Throwable -> L83
                java.lang.String r4 = "Error parsing owners information file, failread Status : "
                r1.append(r4)     // Catch: java.lang.Throwable -> L83
                r1.append(r3)     // Catch: java.lang.Throwable -> L83
                java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L83
                android.util.Slog.e(r0, r1, r2)     // Catch: java.lang.Throwable -> L83
                if (r3 == 0) goto L91
                r9.readFromFileLocked()     // Catch: java.lang.Throwable -> L83
                goto L91
            Lcd:
                return
            Lce:
                libcore.io.IoUtils.closeQuietly(r5)
                throw r9
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.devicepolicy.OwnersData.FileReadWriter.readFromFileLocked():void");
        }

        public abstract boolean readInner(TypedXmlPullParser typedXmlPullParser, int i, String str);

        public abstract boolean shouldWrite();

        public abstract void writeInner(TypedXmlSerializer typedXmlSerializer);

        public final boolean writeToFileLocked() {
            FileOutputStream startWrite;
            if (!shouldWrite()) {
                if (this.mFile.exists() && !this.mFile.delete()) {
                    Slog.e(
                            "DevicePolicyManagerService",
                            "Failed to remove " + this.mFile.getPath());
                }
                return true;
            }
            AtomicFile atomicFile = new AtomicFile(this.mFile);
            FileOutputStream fileOutputStream = null;
            try {
                startWrite = atomicFile.startWrite();
            } catch (IOException e) {
                e = e;
            }
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.startTag((String) null, "root");
                writeInner(resolveSerializer);
                resolveSerializer.endTag((String) null, "root");
                resolveSerializer.endDocument();
                resolveSerializer.flush();
                atomicFile.finishWrite(startWrite);
                this.resAtom.writeReserve();
                return true;
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = startWrite;
                Slog.e("DevicePolicyManagerService", "Exception when writing", e);
                if (fileOutputStream == null) {
                    return false;
                }
                atomicFile.failWrite(fileOutputStream);
                return false;
            }
        }
    }

    public final class OwnerInfo {
        public final ComponentName admin;
        public boolean isOrganizationOwnedDevice;
        public final String packageName;
        public String remoteBugreportHash;
        public String remoteBugreportUri;

        public OwnerInfo(ComponentName componentName, String str, String str2, boolean z) {
            this.admin = componentName;
            this.packageName = componentName.getPackageName();
            this.remoteBugreportUri = str;
            this.remoteBugreportHash = str2;
            this.isOrganizationOwnedDevice = z;
        }

        public static OwnerInfo readFromXml(TypedXmlPullParser typedXmlPullParser) {
            String attributeValue =
                    typedXmlPullParser.getAttributeValue((String) null, "component");
            String attributeValue2 =
                    typedXmlPullParser.getAttributeValue((String) null, "remoteBugreportUri");
            String attributeValue3 =
                    typedXmlPullParser.getAttributeValue((String) null, "remoteBugreportHash");
            boolean equals =
                    "true"
                                    .equals(
                                            typedXmlPullParser.getAttributeValue(
                                                    (String) null, "isPoOrganizationOwnedDevice"))
                            | "true"
                                    .equals(
                                            typedXmlPullParser.getAttributeValue(
                                                    (String) null, "canAccessDeviceIds"));
            if (attributeValue == null) {
                Slog.e("DevicePolicyManagerService", "Owner component not found");
                return null;
            }
            ComponentName unflattenFromString = ComponentName.unflattenFromString(attributeValue);
            if (unflattenFromString != null) {
                return new OwnerInfo(unflattenFromString, attributeValue2, attributeValue3, equals);
            }
            Slog.e(
                    "DevicePolicyManagerService",
                    "Owner component not parsable: ".concat(attributeValue));
            return null;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("admin=" + this.admin);
            indentingPrintWriter.println("package=" + this.packageName);
            indentingPrintWriter.println(
                    "isOrganizationOwnedDevice=" + this.isOrganizationOwnedDevice);
        }

        public final void writeToXml(TypedXmlSerializer typedXmlSerializer, String str) {
            typedXmlSerializer.startTag((String) null, str);
            ComponentName componentName = this.admin;
            if (componentName != null) {
                typedXmlSerializer.attribute(
                        (String) null, "component", componentName.flattenToString());
            }
            String str2 = this.remoteBugreportUri;
            if (str2 != null) {
                typedXmlSerializer.attribute((String) null, "remoteBugreportUri", str2);
            }
            String str3 = this.remoteBugreportHash;
            if (str3 != null) {
                typedXmlSerializer.attribute((String) null, "remoteBugreportHash", str3);
            }
            boolean z = this.isOrganizationOwnedDevice;
            if (z) {
                typedXmlSerializer.attributeBoolean(
                        (String) null, "isPoOrganizationOwnedDevice", z);
            }
            typedXmlSerializer.endTag((String) null, str);
        }
    }

    public final class ProfileOwnerReadWriter extends FileReadWriter {
        public final int mUserId;

        public ProfileOwnerReadWriter(int i) {
            super(OwnersData.this.getProfileOwnerFile(i));
            this.mUserId = i;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public final boolean readInner(TypedXmlPullParser typedXmlPullParser, int i, String str) {
            if (i > 2) {
                return true;
            }
            str.getClass();
            if (str.equals("profile-owner")) {
                OwnersData.this.mProfileOwners.put(
                        Integer.valueOf(this.mUserId), OwnerInfo.readFromXml(typedXmlPullParser));
                return true;
            }
            Slog.e("DevicePolicyManagerService", "Unexpected tag: ".concat(str));
            return false;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public final boolean shouldWrite() {
            return OwnersData.this.mProfileOwners.get(Integer.valueOf(this.mUserId)) != null;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public final void writeInner(TypedXmlSerializer typedXmlSerializer) {
            OwnerInfo ownerInfo =
                    (OwnerInfo) OwnersData.this.mProfileOwners.get(Integer.valueOf(this.mUserId));
            if (ownerInfo != null) {
                ownerInfo.writeToXml(typedXmlSerializer, "profile-owner");
            }
        }
    }

    public OwnersData(PolicyPathProvider policyPathProvider) {
        this.mPathProvider = policyPathProvider;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        boolean z;
        boolean z2 = true;
        if (this.mDeviceOwner != null) {
            indentingPrintWriter.println("Device Owner: ");
            indentingPrintWriter.increaseIndent();
            this.mDeviceOwner.dump(indentingPrintWriter);
            indentingPrintWriter.println("User ID: " + this.mDeviceOwnerUserId);
            indentingPrintWriter.decreaseIndent();
            z = true;
        } else {
            z = false;
        }
        if (this.mSystemUpdatePolicy != null) {
            if (z) {
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("System Update Policy: " + this.mSystemUpdatePolicy);
            z = true;
        }
        ArrayMap arrayMap = this.mProfileOwners;
        if (arrayMap != null) {
            for (Map.Entry entry : arrayMap.entrySet()) {
                if (z) {
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println("Profile Owner (User " + entry.getKey() + "): ");
                indentingPrintWriter.increaseIndent();
                ((OwnerInfo) entry.getValue()).dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                z = true;
            }
        }
        if (this.mSystemUpdateInfo != null) {
            if (z) {
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("Pending System Update: " + this.mSystemUpdateInfo);
        } else {
            z2 = z;
        }
        if (this.mSystemUpdateFreezeStart == null && this.mSystemUpdateFreezeEnd == null) {
            return;
        }
        if (z2) {
            indentingPrintWriter.println();
        }
        indentingPrintWriter.println(
                "System update freeze record: " + getSystemUpdateFreezePeriodRecordAsString());
    }

    public File getDeviceOwnerFile() {
        this.mPathProvider.getClass();
        return new File(Environment.getDataSystemDirectory(), "device_owner_2.xml");
    }

    public File getProfileOwnerFile(int i) {
        this.mPathProvider.getClass();
        return new File(Environment.getUserSystemDirectory(i), "profile_owner.xml");
    }

    public final String getSystemUpdateFreezePeriodRecordAsString() {
        StringBuilder sb = new StringBuilder("start: ");
        LocalDate localDate = this.mSystemUpdateFreezeStart;
        if (localDate != null) {
            sb.append(localDate.toString());
        } else {
            sb.append("null");
        }
        sb.append("; end: ");
        LocalDate localDate2 = this.mSystemUpdateFreezeEnd;
        if (localDate2 != null) {
            sb.append(localDate2.toString());
        } else {
            sb.append("null");
        }
        return sb.toString();
    }

    public final void load(int[] iArr) {
        new DeviceOwnerReadWriter().readFromFileLocked();
        for (int i : iArr) {
            new ProfileOwnerReadWriter(i).readFromFileLocked();
        }
        OwnerInfo ownerInfo =
                (OwnerInfo) this.mProfileOwners.get(Integer.valueOf(this.mDeviceOwnerUserId));
        ComponentName componentName = ownerInfo != null ? ownerInfo.admin : null;
        if (this.mDeviceOwner == null || componentName == null) {
            return;
        }
        Slog.w(
                "DevicePolicyManagerService",
                String.format(
                        "User %d has both DO and PO, which is not supported",
                        Integer.valueOf(this.mDeviceOwnerUserId)));
    }

    public final boolean writeDeviceOwner() {
        return new DeviceOwnerReadWriter().writeToFileLocked();
    }
}
