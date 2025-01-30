package com.android.server.pm;

import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongSparseArray;
import android.util.Slog;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.SharedUserApi;
import com.android.server.utils.WatchedArrayMap;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public class KeySetManagerService {
  public long lastIssuedKeyId;
  public long lastIssuedKeySetId;
  public final LongSparseArray mKeySetMapping;
  public final LongSparseArray mKeySets;
  public final WatchedArrayMap mPackages;
  public final LongSparseArray mPublicKeys;

  public class PublicKeyHandle {
    public final long mId;
    public final PublicKey mKey;
    public int mRefCount;

    public PublicKeyHandle(long j, PublicKey publicKey) {
      this.mId = j;
      this.mRefCount = 1;
      this.mKey = publicKey;
    }

    public PublicKeyHandle(long j, int i, PublicKey publicKey) {
      this.mId = j;
      this.mRefCount = i;
      this.mKey = publicKey;
    }

    public PublicKey getKey() {
      return this.mKey;
    }

    public void incrRefCountLPw() {
      this.mRefCount++;
    }

    public long decrRefCountLPw() {
      int i = this.mRefCount - 1;
      this.mRefCount = i;
      return i;
    }
  }

  public KeySetManagerService(WatchedArrayMap watchedArrayMap) {
    this.lastIssuedKeySetId = 0L;
    this.lastIssuedKeyId = 0L;
    this.mKeySets = new LongSparseArray();
    this.mPublicKeys = new LongSparseArray();
    this.mKeySetMapping = new LongSparseArray();
    this.mPackages = watchedArrayMap;
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

  public boolean packageIsSignedByLPr(String str, KeySetHandle keySetHandle) {
    PackageSetting packageSetting = (PackageSetting) this.mPackages.get(str);
    if (packageSetting == null) {
      throw new NullPointerException("Invalid package name");
    }
    if (packageSetting.getKeySetData() == null) {
      throw new NullPointerException("Package has no KeySet data");
    }
    long idByKeySetLPr = getIdByKeySetLPr(keySetHandle);
    if (idByKeySetLPr == -1) {
      return false;
    }
    return ((ArraySet)
            this.mKeySetMapping.get(packageSetting.getKeySetData().getProperSigningKeySet()))
        .containsAll((ArraySet) this.mKeySetMapping.get(idByKeySetLPr));
  }

  public boolean packageIsSignedByExactlyLPr(String str, KeySetHandle keySetHandle) {
    PackageSetting packageSetting = (PackageSetting) this.mPackages.get(str);
    if (packageSetting == null) {
      throw new NullPointerException("Invalid package name");
    }
    if (packageSetting.getKeySetData() == null
        || packageSetting.getKeySetData().getProperSigningKeySet() == -1) {
      throw new NullPointerException("Package has no KeySet data");
    }
    long idByKeySetLPr = getIdByKeySetLPr(keySetHandle);
    if (idByKeySetLPr == -1) {
      return false;
    }
    return ((ArraySet)
            this.mKeySetMapping.get(packageSetting.getKeySetData().getProperSigningKeySet()))
        .equals((ArraySet) this.mKeySetMapping.get(idByKeySetLPr));
  }

  public void assertScannedPackageValid(AndroidPackage androidPackage) {
    if (androidPackage == null || androidPackage.getPackageName() == null) {
      throw new PackageManagerException(-2, "Passed invalid package to keyset validation.");
    }
    ArraySet publicKeys = androidPackage.getSigningDetails().getPublicKeys();
    if (publicKeys == null || publicKeys.size() <= 0 || publicKeys.contains(null)) {
      throw new PackageManagerException(-2, "Package has invalid signing-key-set.");
    }
    Map keySetMapping = androidPackage.getKeySetMapping();
    if (keySetMapping != null) {
      if (keySetMapping.containsKey(null) || keySetMapping.containsValue(null)) {
        throw new PackageManagerException(-2, "Package has null defined key set.");
      }
      for (ArraySet arraySet : keySetMapping.values()) {
        if (arraySet.size() <= 0 || arraySet.contains(null)) {
          throw new PackageManagerException(
              -2, "Package has null/no public keys for defined key-sets.");
        }
      }
    }
    Set upgradeKeySets = androidPackage.getUpgradeKeySets();
    if (upgradeKeySets != null) {
      if (keySetMapping == null || !keySetMapping.keySet().containsAll(upgradeKeySets)) {
        throw new PackageManagerException(
            -2, "Package has upgrade-key-sets without corresponding definitions.");
      }
    }
  }

  public void addScannedPackageLPw(AndroidPackage androidPackage) {
    Objects.requireNonNull(androidPackage, "Attempted to add null pkg to ksms.");
    Objects.requireNonNull(androidPackage.getPackageName(), "Attempted to add null pkg to ksms.");
    PackageSetting packageSetting =
        (PackageSetting) this.mPackages.get(androidPackage.getPackageName());
    Objects.requireNonNull(
        packageSetting,
        "pkg: "
            + androidPackage.getPackageName()
            + "does not have a corresponding entry in mPackages.");
    addSigningKeySetToPackageLPw(
        packageSetting, androidPackage.getSigningDetails().getPublicKeys());
    if (androidPackage.getKeySetMapping() != null) {
      addDefinedKeySetsToPackageLPw(packageSetting, androidPackage.getKeySetMapping());
      if (androidPackage.getUpgradeKeySets() != null) {
        addUpgradeKeySetsToPackageLPw(packageSetting, androidPackage.getUpgradeKeySets());
      }
    }
  }

  public void addSigningKeySetToPackageLPw(PackageSetting packageSetting, ArraySet arraySet) {
    long properSigningKeySet = packageSetting.getKeySetData().getProperSigningKeySet();
    if (properSigningKeySet != -1) {
      ArraySet publicKeysFromKeySetLPr = getPublicKeysFromKeySetLPr(properSigningKeySet);
      if (publicKeysFromKeySetLPr != null && publicKeysFromKeySetLPr.equals(arraySet)) {
        return;
      } else {
        decrementKeySetLPw(properSigningKeySet);
      }
    }
    packageSetting.getKeySetData().setProperSigningKeySet(addKeySetLPw(arraySet).getId());
  }

  public final long getIdByKeySetLPr(KeySetHandle keySetHandle) {
    for (int i = 0; i < this.mKeySets.size(); i++) {
      if (keySetHandle.equals((KeySetHandle) this.mKeySets.valueAt(i))) {
        return this.mKeySets.keyAt(i);
      }
    }
    return -1L;
  }

  public void addDefinedKeySetsToPackageLPw(PackageSetting packageSetting, Map map) {
    ArrayMap aliases = packageSetting.getKeySetData().getAliases();
    ArrayMap arrayMap = new ArrayMap();
    for (Map.Entry entry : map.entrySet()) {
      String str = (String) entry.getKey();
      ArraySet arraySet = (ArraySet) entry.getValue();
      if (str != null && arraySet != null && arraySet.size() > 0) {
        arrayMap.put(str, Long.valueOf(addKeySetLPw(arraySet).getId()));
      }
    }
    int size = aliases.size();
    for (int i = 0; i < size; i++) {
      decrementKeySetLPw(((Long) aliases.valueAt(i)).longValue());
    }
    packageSetting.getKeySetData().removeAllUpgradeKeySets();
    packageSetting.getKeySetData().setAliases(arrayMap);
  }

  public void addUpgradeKeySetsToPackageLPw(PackageSetting packageSetting, Set set) {
    Iterator it = set.iterator();
    while (it.hasNext()) {
      packageSetting.getKeySetData().addUpgradeKeySet((String) it.next());
    }
  }

  public KeySetHandle getKeySetByAliasAndPackageNameLPr(String str, String str2) {
    PackageSetting packageSetting = (PackageSetting) this.mPackages.get(str);
    if (packageSetting == null || packageSetting.getKeySetData() == null) {
      return null;
    }
    ArrayMap aliases = packageSetting.getKeySetData().getAliases();
    Long l = (Long) aliases.get(str2);
    if (l == null) {
      throw new IllegalArgumentException(
          "Unknown KeySet alias: " + str2 + ", aliases = " + aliases);
    }
    return (KeySetHandle) this.mKeySets.get(l.longValue());
  }

  public boolean isIdValidKeySetId(long j) {
    return this.mKeySets.get(j) != null;
  }

  public boolean shouldCheckUpgradeKeySetLocked(
      PackageStateInternal packageStateInternal, SharedUserApi sharedUserApi, int i) {
    if (packageStateInternal == null
        || (i & 512) != 0
        || sharedUserApi != null
        || !packageStateInternal.getKeySetData().isUsingUpgradeKeySets()) {
      return false;
    }
    long[] upgradeKeySets = packageStateInternal.getKeySetData().getUpgradeKeySets();
    for (int i2 = 0; i2 < upgradeKeySets.length; i2++) {
      if (!isIdValidKeySetId(upgradeKeySets[i2])) {
        StringBuilder sb = new StringBuilder();
        sb.append("Package ");
        sb.append(
            packageStateInternal.getPackageName() != null
                ? packageStateInternal.getPackageName()
                : "<null>");
        sb.append(" contains upgrade-key-set reference to unknown key-set: ");
        sb.append(upgradeKeySets[i2]);
        sb.append(" reverting to signatures check.");
        Slog.wtf("KeySetManagerService", sb.toString());
        return false;
      }
    }
    return true;
  }

  public boolean checkUpgradeKeySetLocked(
      PackageStateInternal packageStateInternal, AndroidPackage androidPackage) {
    for (long j : packageStateInternal.getKeySetData().getUpgradeKeySets()) {
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

  public ArraySet getPublicKeysFromKeySetLPr(long j) {
    ArraySet arraySet = (ArraySet) this.mKeySetMapping.get(j);
    if (arraySet == null) {
      return null;
    }
    ArraySet arraySet2 = new ArraySet();
    int size = arraySet.size();
    for (int i = 0; i < size; i++) {
      arraySet2.add(
          ((PublicKeyHandle) this.mPublicKeys.get(((Long) arraySet.valueAt(i)).longValue()))
              .getKey());
    }
    return arraySet2;
  }

  public KeySetHandle getSigningKeySetByPackageNameLPr(String str) {
    PackageSetting packageSetting = (PackageSetting) this.mPackages.get(str);
    if (packageSetting == null
        || packageSetting.getKeySetData() == null
        || packageSetting.getKeySetData().getProperSigningKeySet() == -1) {
      return null;
    }
    return (KeySetHandle)
        this.mKeySets.get(packageSetting.getKeySetData().getProperSigningKeySet());
  }

  public final KeySetHandle addKeySetLPw(ArraySet arraySet) {
    if (arraySet == null || arraySet.size() == 0) {
      throw new IllegalArgumentException("Cannot add an empty set of keys!");
    }
    ArraySet arraySet2 = new ArraySet(arraySet.size());
    int size = arraySet.size();
    for (int i = 0; i < size; i++) {
      arraySet2.add(Long.valueOf(addPublicKeyLPw((PublicKey) arraySet.valueAt(i))));
    }
    long idFromKeyIdsLPr = getIdFromKeyIdsLPr(arraySet2);
    if (idFromKeyIdsLPr != -1) {
      for (int i2 = 0; i2 < size; i2++) {
        decrementPublicKeyLPw(((Long) arraySet2.valueAt(i2)).longValue());
      }
      KeySetHandle keySetHandle = (KeySetHandle) this.mKeySets.get(idFromKeyIdsLPr);
      keySetHandle.incrRefCountLPw();
      return keySetHandle;
    }
    long freeKeySetIDLPw = getFreeKeySetIDLPw();
    KeySetHandle keySetHandle2 = new KeySetHandle(freeKeySetIDLPw);
    this.mKeySets.put(freeKeySetIDLPw, keySetHandle2);
    this.mKeySetMapping.put(freeKeySetIDLPw, arraySet2);
    return keySetHandle2;
  }

  public final void decrementKeySetLPw(long j) {
    KeySetHandle keySetHandle = (KeySetHandle) this.mKeySets.get(j);
    if (keySetHandle != null && keySetHandle.decrRefCountLPw() <= 0) {
      ArraySet arraySet = (ArraySet) this.mKeySetMapping.get(j);
      int size = arraySet.size();
      for (int i = 0; i < size; i++) {
        decrementPublicKeyLPw(((Long) arraySet.valueAt(i)).longValue());
      }
      this.mKeySets.delete(j);
      this.mKeySetMapping.delete(j);
    }
  }

  public final void decrementPublicKeyLPw(long j) {
    PublicKeyHandle publicKeyHandle = (PublicKeyHandle) this.mPublicKeys.get(j);
    if (publicKeyHandle != null && publicKeyHandle.decrRefCountLPw() <= 0) {
      this.mPublicKeys.delete(j);
    }
  }

  public final long addPublicKeyLPw(PublicKey publicKey) {
    Objects.requireNonNull(publicKey, "Cannot add null public key!");
    long idForPublicKeyLPr = getIdForPublicKeyLPr(publicKey);
    if (idForPublicKeyLPr != -1) {
      ((PublicKeyHandle) this.mPublicKeys.get(idForPublicKeyLPr)).incrRefCountLPw();
      return idForPublicKeyLPr;
    }
    long freePublicKeyIdLPw = getFreePublicKeyIdLPw();
    this.mPublicKeys.put(freePublicKeyIdLPw, new PublicKeyHandle(freePublicKeyIdLPw, publicKey));
    return freePublicKeyIdLPw;
  }

  public final long getIdFromKeyIdsLPr(Set set) {
    for (int i = 0; i < this.mKeySetMapping.size(); i++) {
      if (((ArraySet) this.mKeySetMapping.valueAt(i)).equals(set)) {
        return this.mKeySetMapping.keyAt(i);
      }
    }
    return -1L;
  }

  public final long getIdForPublicKeyLPr(PublicKey publicKey) {
    String str = new String(publicKey.getEncoded());
    for (int i = 0; i < this.mPublicKeys.size(); i++) {
      if (str.equals(
          new String(((PublicKeyHandle) this.mPublicKeys.valueAt(i)).getKey().getEncoded()))) {
        return this.mPublicKeys.keyAt(i);
      }
    }
    return -1L;
  }

  public final long getFreeKeySetIDLPw() {
    long j = this.lastIssuedKeySetId + 1;
    this.lastIssuedKeySetId = j;
    return j;
  }

  public final long getFreePublicKeyIdLPw() {
    long j = this.lastIssuedKeyId + 1;
    this.lastIssuedKeyId = j;
    return j;
  }

  public void removeAppKeySetDataLPw(String str) {
    PackageSetting packageSetting = (PackageSetting) this.mPackages.get(str);
    Objects.requireNonNull(
        packageSetting, "pkg name: " + str + "does not have a corresponding entry in mPackages.");
    decrementKeySetLPw(packageSetting.getKeySetData().getProperSigningKeySet());
    ArrayMap aliases = packageSetting.getKeySetData().getAliases();
    for (int i = 0; i < aliases.size(); i++) {
      decrementKeySetLPw(((Long) aliases.valueAt(i)).longValue());
    }
    clearPackageKeySetDataLPw(packageSetting);
  }

  public final void clearPackageKeySetDataLPw(PackageSetting packageSetting) {
    packageSetting.getKeySetData().setProperSigningKeySet(-1L);
    packageSetting.getKeySetData().removeAllDefinedKeySets();
    packageSetting.getKeySetData().removeAllUpgradeKeySets();
  }

  public void dumpLPr(PrintWriter printWriter, String str, DumpState dumpState) {
    boolean z;
    boolean z2;
    boolean z3 = false;
    for (Map.Entry entry : this.mPackages.entrySet()) {
      String str2 = (String) entry.getKey();
      if (str == null || str.equals(str2)) {
        if (!z3) {
          if (dumpState.onTitlePrinted()) {
            printWriter.println();
          }
          printWriter.println("Key Set Manager:");
          z3 = true;
        }
        PackageSetting packageSetting = (PackageSetting) entry.getValue();
        printWriter.print("  [");
        printWriter.print(str2);
        printWriter.println("]");
        if (packageSetting.getKeySetData() != null) {
          boolean z4 = false;
          for (Map.Entry entry2 : packageSetting.getKeySetData().getAliases().entrySet()) {
            if (!z4) {
              printWriter.print("      KeySets Aliases: ");
              z4 = true;
            } else {
              printWriter.print(", ");
            }
            printWriter.print((String) entry2.getKey());
            printWriter.print('=');
            printWriter.print(Long.toString(((Long) entry2.getValue()).longValue()));
          }
          if (z4) {
            printWriter.println("");
          }
          if (packageSetting.getKeySetData().isUsingDefinedKeySets()) {
            ArrayMap aliases = packageSetting.getKeySetData().getAliases();
            int size = aliases.size();
            z = false;
            for (int i = 0; i < size; i++) {
              if (!z) {
                printWriter.print("      Defined KeySets: ");
                z = true;
              } else {
                printWriter.print(", ");
              }
              printWriter.print(Long.toString(((Long) aliases.valueAt(i)).longValue()));
            }
          } else {
            z = false;
          }
          if (z) {
            printWriter.println("");
          }
          long properSigningKeySet = packageSetting.getKeySetData().getProperSigningKeySet();
          printWriter.print("      Signing KeySets: ");
          printWriter.print(Long.toString(properSigningKeySet));
          printWriter.println("");
          if (packageSetting.getKeySetData().isUsingUpgradeKeySets()) {
            z2 = false;
            for (long j : packageSetting.getKeySetData().getUpgradeKeySets()) {
              if (!z2) {
                printWriter.print("      Upgrade KeySets: ");
                z2 = true;
              } else {
                printWriter.print(", ");
              }
              printWriter.print(Long.toString(j));
            }
          } else {
            z2 = false;
          }
          if (z2) {
            printWriter.println("");
          }
        }
      }
    }
  }

  public void writeKeySetManagerServiceLPr(TypedXmlSerializer typedXmlSerializer) {
    typedXmlSerializer.startTag((String) null, "keyset-settings");
    typedXmlSerializer.attributeInt((String) null, "version", 1);
    writePublicKeysLPr(typedXmlSerializer);
    writeKeySetsLPr(typedXmlSerializer);
    typedXmlSerializer.startTag((String) null, "lastIssuedKeyId");
    typedXmlSerializer.attributeLong((String) null, "value", this.lastIssuedKeyId);
    typedXmlSerializer.endTag((String) null, "lastIssuedKeyId");
    typedXmlSerializer.startTag((String) null, "lastIssuedKeySetId");
    typedXmlSerializer.attributeLong((String) null, "value", this.lastIssuedKeySetId);
    typedXmlSerializer.endTag((String) null, "lastIssuedKeySetId");
    typedXmlSerializer.endTag((String) null, "keyset-settings");
  }

  public void writePublicKeysLPr(TypedXmlSerializer typedXmlSerializer) {
    typedXmlSerializer.startTag((String) null, "keys");
    for (int i = 0; i < this.mPublicKeys.size(); i++) {
      long keyAt = this.mPublicKeys.keyAt(i);
      PublicKeyHandle publicKeyHandle = (PublicKeyHandle) this.mPublicKeys.valueAt(i);
      typedXmlSerializer.startTag((String) null, "public-key");
      typedXmlSerializer.attributeLong((String) null, "identifier", keyAt);
      typedXmlSerializer.attributeBytesBase64(
          (String) null, "value", publicKeyHandle.getKey().getEncoded());
      typedXmlSerializer.endTag((String) null, "public-key");
    }
    typedXmlSerializer.endTag((String) null, "keys");
  }

  public void writeKeySetsLPr(TypedXmlSerializer typedXmlSerializer) {
    typedXmlSerializer.startTag((String) null, "keysets");
    for (int i = 0; i < this.mKeySetMapping.size(); i++) {
      long keyAt = this.mKeySetMapping.keyAt(i);
      ArraySet arraySet = (ArraySet) this.mKeySetMapping.valueAt(i);
      typedXmlSerializer.startTag((String) null, "keyset");
      typedXmlSerializer.attributeLong((String) null, "identifier", keyAt);
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
  }

  public void readKeySetsLPw(TypedXmlPullParser typedXmlPullParser, ArrayMap arrayMap) {
    int depth = typedXmlPullParser.getDepth();
    if (typedXmlPullParser.getAttributeValue((String) null, "version") == null) {
      while (true) {
        int next = typedXmlPullParser.next();
        if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
          break;
        }
      }
      Iterator it = this.mPackages.values().iterator();
      while (it.hasNext()) {
        clearPackageKeySetDataLPw((PackageSetting) it.next());
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
          readKeysLPw(typedXmlPullParser);
        } else if (name.equals("keysets")) {
          readKeySetListLPw(typedXmlPullParser);
        } else if (name.equals("lastIssuedKeyId")) {
          this.lastIssuedKeyId = typedXmlPullParser.getAttributeLong((String) null, "value");
        } else if (name.equals("lastIssuedKeySetId")) {
          this.lastIssuedKeySetId = typedXmlPullParser.getAttributeLong((String) null, "value");
        }
      }
    }
    addRefCountsFromSavedPackagesLPw(arrayMap);
  }

  public void readKeysLPw(TypedXmlPullParser typedXmlPullParser) {
    int depth = typedXmlPullParser.getDepth();
    while (true) {
      int next = typedXmlPullParser.next();
      if (next == 1) {
        return;
      }
      if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
        return;
      }
      if (next != 3 && next != 4 && typedXmlPullParser.getName().equals("public-key")) {
        readPublicKeyLPw(typedXmlPullParser);
      }
    }
  }

  public void readKeySetListLPw(TypedXmlPullParser typedXmlPullParser) {
    int depth = typedXmlPullParser.getDepth();
    long j = 0;
    while (true) {
      int next = typedXmlPullParser.next();
      if (next == 1) {
        return;
      }
      if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
        return;
      }
      if (next != 3 && next != 4) {
        String name = typedXmlPullParser.getName();
        if (name.equals("keyset")) {
          j = typedXmlPullParser.getAttributeLong((String) null, "identifier");
          this.mKeySets.put(j, new KeySetHandle(j, 0));
          this.mKeySetMapping.put(j, new ArraySet());
        } else if (name.equals("key-id")) {
          ((ArraySet) this.mKeySetMapping.get(j))
              .add(Long.valueOf(typedXmlPullParser.getAttributeLong((String) null, "identifier")));
        }
      }
    }
  }

  public void readPublicKeyLPw(TypedXmlPullParser typedXmlPullParser) {
    long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "identifier");
    int i = 0;
    PublicKey parsePublicKey =
        FrameworkParsingPackageUtils.parsePublicKey(
            typedXmlPullParser.getAttributeBytesBase64((String) null, "value", (byte[]) null));
    if (parsePublicKey != null) {
      this.mPublicKeys.put(attributeLong, new PublicKeyHandle(attributeLong, i, parsePublicKey));
    }
  }

  public final void addRefCountsFromSavedPackagesLPw(ArrayMap arrayMap) {
    int size = arrayMap.size();
    for (int i = 0; i < size; i++) {
      KeySetHandle keySetHandle =
          (KeySetHandle) this.mKeySets.get(((Long) arrayMap.keyAt(i)).longValue());
      if (keySetHandle == null) {
        Slog.wtf(
            "KeySetManagerService",
            "Encountered non-existent key-set reference when reading settings");
      } else {
        keySetHandle.setRefCountLPw(((Integer) arrayMap.valueAt(i)).intValue());
      }
    }
    ArraySet arraySet = new ArraySet();
    int size2 = this.mKeySets.size();
    for (int i2 = 0; i2 < size2; i2++) {
      if (((KeySetHandle) this.mKeySets.valueAt(i2)).getRefCountLPr() == 0) {
        Slog.wtf(
            "KeySetManagerService",
            "Encountered key-set w/out package references when reading settings");
        arraySet.add(Long.valueOf(this.mKeySets.keyAt(i2)));
      }
      ArraySet arraySet2 = (ArraySet) this.mKeySetMapping.valueAt(i2);
      int size3 = arraySet2.size();
      for (int i3 = 0; i3 < size3; i3++) {
        ((PublicKeyHandle) this.mPublicKeys.get(((Long) arraySet2.valueAt(i3)).longValue()))
            .incrRefCountLPw();
      }
    }
    int size4 = arraySet.size();
    for (int i4 = 0; i4 < size4; i4++) {
      decrementKeySetLPw(((Long) arraySet.valueAt(i4)).longValue());
    }
  }
}
