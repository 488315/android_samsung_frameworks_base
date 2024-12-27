package com.android.server.pm;

import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongSparseArray;
import android.util.Slog;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.utils.WatchedArrayMap;

import java.security.PublicKey;
import java.util.Iterator;
import java.util.Objects;

public final class KeySetManagerService {
    public long lastIssuedKeyId;
    public long lastIssuedKeySetId;
    public final LongSparseArray mKeySetMapping;
    public final LongSparseArray mKeySets;
    public final WatchedArrayMap mPackages;
    public final LongSparseArray mPublicKeys;

    public final class PublicKeyHandle {
        public final PublicKey mKey;
        public int mRefCount = 1;

        public PublicKeyHandle(PublicKey publicKey) {
            this.mKey = publicKey;
        }

        public PublicKeyHandle(PublicKey publicKey, int i) {
            this.mKey = publicKey;
        }
    }

    public KeySetManagerService(
            KeySetManagerService keySetManagerService, WatchedArrayMap watchedArrayMap) {
        this.lastIssuedKeySetId = 0L;
        this.lastIssuedKeyId = 0L;
        this.mKeySets = keySetManagerService.mKeySets.clone();
        this.mPublicKeys = keySetManagerService.mPublicKeys.clone();
        this.mKeySetMapping = keySetManagerService.mKeySetMapping.clone();
        this.mPackages = watchedArrayMap;
    }

    public KeySetManagerService(WatchedArrayMap watchedArrayMap) {
        this.lastIssuedKeySetId = 0L;
        this.lastIssuedKeyId = 0L;
        this.mKeySets = new LongSparseArray();
        this.mPublicKeys = new LongSparseArray();
        this.mKeySetMapping = new LongSparseArray();
        this.mPackages = watchedArrayMap;
    }

    public final KeySetHandle addKeySetLPw(ArraySet arraySet) {
        long j;
        long j2;
        if (arraySet == null || arraySet.size() == 0) {
            throw new IllegalArgumentException("Cannot add an empty set of keys!");
        }
        ArraySet arraySet2 = new ArraySet(arraySet.size());
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            PublicKey publicKey = (PublicKey) arraySet.valueAt(i);
            Objects.requireNonNull(publicKey, "Cannot add null public key!");
            String str = new String(publicKey.getEncoded());
            int i2 = 0;
            while (true) {
                if (i2 >= this.mPublicKeys.size()) {
                    j2 = -1;
                    break;
                }
                if (str.equals(
                        new String(
                                ((PublicKeyHandle) this.mPublicKeys.valueAt(i2))
                                        .mKey.getEncoded()))) {
                    j2 = this.mPublicKeys.keyAt(i2);
                    break;
                }
                i2++;
            }
            if (j2 != -1) {
                ((PublicKeyHandle) this.mPublicKeys.get(j2)).mRefCount++;
            } else {
                j2 = this.lastIssuedKeyId + 1;
                this.lastIssuedKeyId = j2;
                this.mPublicKeys.put(j2, new PublicKeyHandle(publicKey));
            }
            arraySet2.add(Long.valueOf(j2));
        }
        int i3 = 0;
        while (true) {
            if (i3 >= this.mKeySetMapping.size()) {
                j = -1;
                break;
            }
            if (((ArraySet) this.mKeySetMapping.valueAt(i3)).equals(arraySet2)) {
                j = this.mKeySetMapping.keyAt(i3);
                break;
            }
            i3++;
        }
        if (j != -1) {
            for (int i4 = 0; i4 < size; i4++) {
                decrementPublicKeyLPw(((Long) arraySet2.valueAt(i4)).longValue());
            }
            KeySetHandle keySetHandle = (KeySetHandle) this.mKeySets.get(j);
            keySetHandle.mRefCount++;
            return keySetHandle;
        }
        long j3 = this.lastIssuedKeySetId + 1;
        this.lastIssuedKeySetId = j3;
        KeySetHandle keySetHandle2 = new KeySetHandle(j3);
        this.mKeySets.put(j3, keySetHandle2);
        this.mKeySetMapping.put(j3, arraySet2);
        return keySetHandle2;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addScannedPackageLPw(com.android.server.pm.pkg.AndroidPackage r9) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.KeySetManagerService.addScannedPackageLPw(com.android.server.pm.pkg.AndroidPackage):void");
    }

    public final boolean checkUpgradeKeySetLocked(
            PackageSetting packageSetting, AndroidPackage androidPackage) {
        for (long j : packageSetting.keySetData.mUpgradeKeySets) {
            ArraySet publicKeysFromKeySetLPr = getPublicKeysFromKeySetLPr(j);
            if (publicKeysFromKeySetLPr != null
                    && androidPackage
                            .getSigningDetails()
                            .getPublicKeys()
                            .containsAll(publicKeysFromKeySetLPr)) {
                return true;
            }
        }
        return false;
    }

    public final void decrementKeySetLPw(long j) {
        KeySetHandle keySetHandle = (KeySetHandle) this.mKeySets.get(j);
        if (keySetHandle == null) {
            return;
        }
        int i = keySetHandle.mRefCount - 1;
        keySetHandle.mRefCount = i;
        if (i <= 0) {
            ArraySet arraySet = (ArraySet) this.mKeySetMapping.get(j);
            int size = arraySet.size();
            for (int i2 = 0; i2 < size; i2++) {
                decrementPublicKeyLPw(((Long) arraySet.valueAt(i2)).longValue());
            }
            this.mKeySets.delete(j);
            this.mKeySetMapping.delete(j);
        }
    }

    public final void decrementPublicKeyLPw(long j) {
        PublicKeyHandle publicKeyHandle = (PublicKeyHandle) this.mPublicKeys.get(j);
        if (publicKeyHandle == null) {
            return;
        }
        int i = publicKeyHandle.mRefCount - 1;
        publicKeyHandle.mRefCount = i;
        if (i <= 0) {
            this.mPublicKeys.delete(j);
        }
    }

    public final long getIdByKeySetLPr(KeySetHandle keySetHandle) {
        for (int i = 0; i < this.mKeySets.size(); i++) {
            if (keySetHandle.equals((KeySetHandle) this.mKeySets.valueAt(i))) {
                return this.mKeySets.keyAt(i);
            }
        }
        return -1L;
    }

    public final ArraySet getPublicKeysFromKeySetLPr(long j) {
        ArraySet arraySet = (ArraySet) this.mKeySetMapping.get(j);
        if (arraySet == null) {
            return null;
        }
        ArraySet arraySet2 = new ArraySet();
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            arraySet2.add(
                    ((PublicKeyHandle)
                                    this.mPublicKeys.get(((Long) arraySet.valueAt(i)).longValue()))
                            .mKey);
        }
        return arraySet2;
    }

    public final void readKeySetsLPw(ArrayMap arrayMap, TypedXmlPullParser typedXmlPullParser) {
        int i;
        int depth = typedXmlPullParser.getDepth();
        if (typedXmlPullParser.getAttributeValue((String) null, "version") == null) {
            while (true) {
                int next = typedXmlPullParser.next();
                if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                    break;
                }
            }
            for (PackageSetting packageSetting : this.mPackages.values()) {
                PackageKeySetData packageKeySetData = packageSetting.keySetData;
                packageKeySetData.mProperSigningKeySet = -1L;
                packageKeySetData.mKeySetAliases.erase();
                packageSetting.keySetData.mUpgradeKeySets = null;
            }
            return;
        }
        while (true) {
            int next2 = typedXmlPullParser.next();
            if (next2 == 1 || (next2 == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next2 != 3 && next2 != 4) {
                String name = typedXmlPullParser.getName();
                if (name.equals("keys")) {
                    int depth2 = typedXmlPullParser.getDepth();
                    while (true) {
                        int next3 = typedXmlPullParser.next();
                        if (next3 != 1 && (next3 != 3 || typedXmlPullParser.getDepth() > depth2)) {
                            if (next3 != 3
                                    && next3 != 4
                                    && typedXmlPullParser.getName().equals("public-key")) {
                                long attributeLong =
                                        typedXmlPullParser.getAttributeLong(
                                                (String) null, "identifier");
                                PublicKey parsePublicKey =
                                        FrameworkParsingPackageUtils.parsePublicKey(
                                                typedXmlPullParser.getAttributeBytesBase64(
                                                        (String) null, "value", (byte[]) null));
                                if (parsePublicKey != null) {
                                    this.mPublicKeys.put(
                                            attributeLong, new PublicKeyHandle(parsePublicKey, 0));
                                }
                            }
                        }
                    }
                } else if (name.equals("keysets")) {
                    int depth3 = typedXmlPullParser.getDepth();
                    long j = 0;
                    while (true) {
                        int next4 = typedXmlPullParser.next();
                        if (next4 != 1 && (next4 != 3 || typedXmlPullParser.getDepth() > depth3)) {
                            if (next4 != 3 && next4 != 4) {
                                String name2 = typedXmlPullParser.getName();
                                if (name2.equals("keyset")) {
                                    j =
                                            typedXmlPullParser.getAttributeLong(
                                                    (String) null, "identifier");
                                    this.mKeySets.put(j, new KeySetHandle(j, 0));
                                    this.mKeySetMapping.put(j, new ArraySet());
                                } else if (name2.equals("key-id")) {
                                    ((ArraySet) this.mKeySetMapping.get(j))
                                            .add(
                                                    Long.valueOf(
                                                            typedXmlPullParser.getAttributeLong(
                                                                    (String) null, "identifier")));
                                }
                            }
                        }
                    }
                } else if (name.equals("lastIssuedKeyId")) {
                    this.lastIssuedKeyId =
                            typedXmlPullParser.getAttributeLong((String) null, "value");
                } else if (name.equals("lastIssuedKeySetId")) {
                    this.lastIssuedKeySetId =
                            typedXmlPullParser.getAttributeLong((String) null, "value");
                }
            }
        }
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            KeySetHandle keySetHandle =
                    (KeySetHandle) this.mKeySets.get(((Long) arrayMap.keyAt(i2)).longValue());
            if (keySetHandle == null) {
                Slog.wtf(
                        "KeySetManagerService",
                        "Encountered non-existent key-set reference when reading settings");
            } else {
                keySetHandle.mRefCount = ((Integer) arrayMap.valueAt(i2)).intValue();
            }
        }
        ArraySet arraySet = new ArraySet();
        int size2 = this.mKeySets.size();
        for (int i3 = 0; i3 < size2; i3++) {
            if (((KeySetHandle) this.mKeySets.valueAt(i3)).mRefCount == 0) {
                Slog.wtf(
                        "KeySetManagerService",
                        "Encountered key-set w/out package references when reading settings");
                arraySet.add(Long.valueOf(this.mKeySets.keyAt(i3)));
            }
            ArraySet arraySet2 = (ArraySet) this.mKeySetMapping.valueAt(i3);
            int size3 = arraySet2.size();
            for (int i4 = 0; i4 < size3; i4++) {
                ((PublicKeyHandle) this.mPublicKeys.get(((Long) arraySet2.valueAt(i4)).longValue()))
                        .mRefCount++;
            }
        }
        int size4 = arraySet.size();
        for (i = 0; i < size4; i++) {
            decrementKeySetLPw(((Long) arraySet.valueAt(i)).longValue());
        }
    }

    public final void removeAppKeySetDataLPw(String str) {
        PackageSetting packageSetting = (PackageSetting) this.mPackages.mStorage.get(str);
        Objects.requireNonNull(
                packageSetting,
                "pkg name: " + str + "does not have a corresponding entry in mPackages.");
        decrementKeySetLPw(packageSetting.keySetData.mProperSigningKeySet);
        ArrayMap arrayMap = packageSetting.keySetData.mKeySetAliases;
        for (int i = 0; i < arrayMap.size(); i++) {
            decrementKeySetLPw(((Long) arrayMap.valueAt(i)).longValue());
        }
        PackageKeySetData packageKeySetData = packageSetting.keySetData;
        packageKeySetData.mProperSigningKeySet = -1L;
        packageKeySetData.mKeySetAliases.erase();
        packageSetting.keySetData.mUpgradeKeySets = null;
    }

    public final boolean shouldCheckUpgradeKeySetLocked(
            PackageSetting packageSetting, SharedUserSetting sharedUserSetting, int i) {
        long[] jArr;
        if (packageSetting == null
                || (i & 512) != 0
                || sharedUserSetting != null
                || (jArr = packageSetting.keySetData.mUpgradeKeySets) == null
                || jArr.length <= 0) {
            return false;
        }
        for (int i2 = 0; i2 < jArr.length; i2++) {
            if (this.mKeySets.get(jArr[i2]) == null) {
                StringBuilder sb = new StringBuilder("Package ");
                String str = packageSetting.mName;
                if (str == null) {
                    str = "<null>";
                }
                sb.append(str);
                sb.append(" contains upgrade-key-set reference to unknown key-set: ");
                sb.append(jArr[i2]);
                sb.append(" reverting to signatures check.");
                Slog.wtf("KeySetManagerService", sb.toString());
                return false;
            }
        }
        return true;
    }

    public final void writeKeySetManagerServiceLPr(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.startTag((String) null, "keyset-settings");
        typedXmlSerializer.attributeInt((String) null, "version", 1);
        typedXmlSerializer.startTag((String) null, "keys");
        for (int i = 0; i < this.mPublicKeys.size(); i++) {
            long keyAt = this.mPublicKeys.keyAt(i);
            PublicKeyHandle publicKeyHandle = (PublicKeyHandle) this.mPublicKeys.valueAt(i);
            typedXmlSerializer.startTag((String) null, "public-key");
            typedXmlSerializer.attributeLong((String) null, "identifier", keyAt);
            typedXmlSerializer.attributeBytesBase64(
                    (String) null, "value", publicKeyHandle.mKey.getEncoded());
            typedXmlSerializer.endTag((String) null, "public-key");
        }
        typedXmlSerializer.endTag((String) null, "keys");
        typedXmlSerializer.startTag((String) null, "keysets");
        for (int i2 = 0; i2 < this.mKeySetMapping.size(); i2++) {
            long keyAt2 = this.mKeySetMapping.keyAt(i2);
            ArraySet arraySet = (ArraySet) this.mKeySetMapping.valueAt(i2);
            typedXmlSerializer.startTag((String) null, "keyset");
            typedXmlSerializer.attributeLong((String) null, "identifier", keyAt2);
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                long longValue = ((Long) it.next()).longValue();
                typedXmlSerializer.startTag((String) null, "key-id");
                typedXmlSerializer.attributeLong((String) null, "identifier", longValue);
                typedXmlSerializer.endTag((String) null, "key-id");
            }
            typedXmlSerializer.endTag((String) null, "keyset");
        }
        typedXmlSerializer.endTag((String) null, "keysets");
        typedXmlSerializer.startTag((String) null, "lastIssuedKeyId");
        typedXmlSerializer.attributeLong((String) null, "value", this.lastIssuedKeyId);
        typedXmlSerializer.endTag((String) null, "lastIssuedKeyId");
        typedXmlSerializer.startTag((String) null, "lastIssuedKeySetId");
        typedXmlSerializer.attributeLong((String) null, "value", this.lastIssuedKeySetId);
        typedXmlSerializer.endTag((String) null, "lastIssuedKeySetId");
        typedXmlSerializer.endTag((String) null, "keyset-settings");
    }
}
