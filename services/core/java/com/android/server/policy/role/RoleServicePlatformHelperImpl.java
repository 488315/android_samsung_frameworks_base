package com.android.server.policy.role;

import android.app.admin.DevicePolicyManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.Signature;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.util.CollectionUtils;
import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.role.RoleServicePlatformHelper;

import libcore.util.HexEncoding;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class RoleServicePlatformHelperImpl implements RoleServicePlatformHelper {
    public final Context mContext;

    public final class MessageDigestOutputStream extends OutputStream {
        public final MessageDigest mMessageDigest;

        public MessageDigestOutputStream() {
            try {
                this.mMessageDigest = MessageDigest.getInstance("SHA256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Failed to create MessageDigest", e);
            }
        }

        @Override // java.io.OutputStream
        public final void write(int i) {
            this.mMessageDigest.update((byte) i);
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr) {
            this.mMessageDigest.update(bArr);
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr, int i, int i2) {
            this.mMessageDigest.update(bArr, i, i2);
        }
    }

    public RoleServicePlatformHelperImpl(Context context) {
        this.mContext = context;
    }

    public static Map parseXml(XmlPullParser xmlPullParser) {
        int depth;
        int depth2;
        int depth3;
        int depth4 = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || ((depth = xmlPullParser.getDepth()) < depth4 && next == 3)) {
                break;
            }
            if (depth <= depth4 && next == 2 && xmlPullParser.getName().equals("roles")) {
                ArrayMap arrayMap = new ArrayMap();
                int depth5 = xmlPullParser.getDepth() + 1;
                while (true) {
                    int next2 = xmlPullParser.next();
                    if (next2 == 1
                            || ((depth2 = xmlPullParser.getDepth()) < depth5 && next2 == 3)) {
                        break;
                    }
                    if (depth2 <= depth5 && next2 == 2 && xmlPullParser.getName().equals("role")) {
                        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                        ArraySet arraySet = new ArraySet();
                        int depth6 = xmlPullParser.getDepth() + 1;
                        while (true) {
                            int next3 = xmlPullParser.next();
                            if (next3 == 1
                                    || ((depth3 = xmlPullParser.getDepth()) < depth6
                                            && next3 == 3)) {
                                break;
                            }
                            if (depth3 <= depth6
                                    && next3 == 2
                                    && xmlPullParser.getName().equals("holder")) {
                                arraySet.add(xmlPullParser.getAttributeValue(null, "name"));
                            }
                        }
                        arrayMap.put(attributeValue, arraySet);
                    }
                }
                return arrayMap;
            }
        }
        throw new IOException("Missing <roles> in roles.xml");
    }

    @Override // com.android.server.role.RoleServicePlatformHelper
    public final String computePackageStateHash(final int i) {
        ComponentName deviceOwnerComponent;
        String packageName;
        ComponentName profileOwnerAsUser;
        final PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        DevicePolicyManagerInternal devicePolicyManagerInternal =
                (DevicePolicyManagerInternal)
                        LocalServices.getService(DevicePolicyManagerInternal.class);
        MessageDigestOutputStream messageDigestOutputStream = new MessageDigestOutputStream();
        final DataOutputStream dataOutputStream =
                new DataOutputStream(new BufferedOutputStream(messageDigestOutputStream));
        packageManagerInternal.forEachInstalledPackage(
                i,
                new Consumer() { // from class:
                                 // com.android.server.policy.role.RoleServicePlatformHelperImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DataOutputStream dataOutputStream2 = dataOutputStream;
                        PackageManagerInternal packageManagerInternal2 = packageManagerInternal;
                        int i2 = i;
                        AndroidPackage androidPackage = (AndroidPackage) obj;
                        try {
                            dataOutputStream2.writeUTF(androidPackage.getPackageName());
                            dataOutputStream2.writeLong(androidPackage.getLongVersionCode());
                            PackageStateInternal packageStateInternal =
                                    ((PackageManagerService.PackageManagerInternalImpl)
                                                    packageManagerInternal2)
                                            .getPackageStateInternal(
                                                    androidPackage.getPackageName());
                            dataOutputStream2.writeInt(
                                    packageStateInternal == null
                                            ? 0
                                            : packageStateInternal
                                                    .getUserStateOrDefault(i2)
                                                    .getEnabledState());
                            Set requestedPermissions = androidPackage.getRequestedPermissions();
                            dataOutputStream2.writeInt(requestedPermissions.size());
                            Iterator it = requestedPermissions.iterator();
                            while (it.hasNext()) {
                                dataOutputStream2.writeUTF((String) it.next());
                            }
                            PackageStateInternal packageStateInternal2 =
                                    ((PackageManagerService.PackageManagerInternalImpl)
                                                    packageManagerInternal2)
                                            .getPackageStateInternal(
                                                    androidPackage.getPackageName());
                            ArraySet arraySet =
                                    packageStateInternal2 == null
                                            ? new ArraySet()
                                            : packageStateInternal2
                                                    .getUserStateOrDefault(i2)
                                                    .m788getEnabledComponents();
                            int size = CollectionUtils.size(arraySet);
                            dataOutputStream2.writeInt(size);
                            for (int i3 = 0; i3 < size; i3++) {
                                dataOutputStream2.writeUTF((String) arraySet.valueAt(i3));
                            }
                            PackageStateInternal packageStateInternal3 =
                                    ((PackageManagerService.PackageManagerInternalImpl)
                                                    packageManagerInternal2)
                                            .getPackageStateInternal(
                                                    androidPackage.getPackageName());
                            ArraySet arraySet2 =
                                    packageStateInternal3 == null
                                            ? new ArraySet()
                                            : packageStateInternal3
                                                    .getUserStateOrDefault(i2)
                                                    .m787getDisabledComponents();
                            int size2 = CollectionUtils.size(arraySet2);
                            for (int i4 = 0; i4 < size2; i4++) {
                                dataOutputStream2.writeUTF((String) arraySet2.valueAt(i4));
                            }
                            for (Signature signature :
                                    androidPackage.getSigningDetails().getSignatures()) {
                                dataOutputStream2.write(signature.toByteArray());
                            }
                        } catch (IOException e) {
                            throw new AssertionError(e);
                        }
                    }
                });
        String str = "";
        if (devicePolicyManagerInternal != null) {
            try {
                if (devicePolicyManagerInternal.getDeviceOwnerUserId() == i
                        && (deviceOwnerComponent =
                                        devicePolicyManagerInternal.getDeviceOwnerComponent(false))
                                != null) {
                    packageName = deviceOwnerComponent.getPackageName();
                    dataOutputStream.writeUTF(packageName);
                    if (devicePolicyManagerInternal != null
                            && (profileOwnerAsUser =
                                            devicePolicyManagerInternal.getProfileOwnerAsUser(i))
                                    != null) {
                        str = profileOwnerAsUser.getPackageName();
                    }
                    dataOutputStream.writeUTF(str);
                    dataOutputStream.writeInt(
                            Settings.Global.getInt(
                                    this.mContext.getContentResolver(), "device_demo_mode", 0));
                    dataOutputStream.writeBoolean(Flags.walletRoleEnabled());
                    dataOutputStream.flush();
                    return HexEncoding.encodeToString(
                            messageDigestOutputStream.mMessageDigest.digest(), true);
                }
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
        packageName = "";
        dataOutputStream.writeUTF(packageName);
        if (devicePolicyManagerInternal != null) {
            str = profileOwnerAsUser.getPackageName();
        }
        dataOutputStream.writeUTF(str);
        dataOutputStream.writeInt(
                Settings.Global.getInt(this.mContext.getContentResolver(), "device_demo_mode", 0));
        dataOutputStream.writeBoolean(Flags.walletRoleEnabled());
        dataOutputStream.flush();
        return HexEncoding.encodeToString(messageDigestOutputStream.mMessageDigest.digest(), true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x016f, code lost:

       if (((r7 == null || (r7 = r7.activityInfo) == null) ? false : r1.equals(r7.packageName)) != false) goto L78;
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0096, code lost:

       if (android.text.TextUtils.isEmpty(r1) == false) goto L33;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0057  */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.util.Map] */
    @Override // com.android.server.role.RoleServicePlatformHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map getLegacyRoleState(int r8) {
        /*
            Method dump skipped, instructions count: 406
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.role.RoleServicePlatformHelperImpl.getLegacyRoleState(int):java.util.Map");
    }
}
