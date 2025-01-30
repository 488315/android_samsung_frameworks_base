package com.android.server.om;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.om.CriticalOverlayInfo;
import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.content.om.SamsungThemeConstants;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.os.IInstalld;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.TextServicesManager;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes2.dex */
public class OverlayManagerServiceExt implements IOverlayManagerExt {
  public final Context mContext;
  public final IdmapManagerWrapper mIdmapManager;
  public boolean mIsInitOnBoot;
  public final PMSHelperImpl mPackageManager;
  public final OverlayManagerService.PackageUpdateHelper mPackageUpdateHelper;
  public final OverlayManagerSettings mSettings;
  public final UserManagerService mUserManager = UserManagerService.getInstance();

  public OverlayManagerServiceExt(
      Context context,
      PackageManagerHelper packageManagerHelper,
      OverlayManagerSettings overlayManagerSettings,
      IdmapManager idmapManager,
      OverlayManagerService.PackageUpdateHelper packageUpdateHelper) {
    this.mContext = context;
    this.mPackageManager = new PMSHelperImpl(packageManagerHelper);
    this.mIdmapManager = new IdmapManagerWrapper(idmapManager, IdmapDaemon.getInstance());
    this.mSettings = overlayManagerSettings;
    this.mPackageUpdateHelper = packageUpdateHelper;
    verifyOverlayPackages();
    updateIdmapforMandatoryTargets();
    OverlayPolicyManager.registerPolicy(new OverlayInfoExtPolicy());
    checkAndEnableThemeCenter();
    checkAndEnableThemeStore();
  }

  public final void updateIdmapforMandatoryTargets() {
    for (int i : this.mSettings.getUsers()) {
      for (OverlayInfo overlayInfo :
          (List)
              getSafeStream(this.mSettings.getOverlaysForUser(i).values())
                  .flatMap(new OverlayManagerService$2$$ExternalSyntheticLambda1())
                  .collect(Collectors.toList())) {
        if (OverlayInfoExt.isOverlayInfoExt(overlayInfo)
            && SamsungThemeConstants.mandatoryTargets.contains(overlayInfo.getTargetPackageName())
            && overlayInfo.state == 3) {
          updateIdmap(
              this.mPackageManager.getPackageForUser(overlayInfo.getTargetPackageName(), i),
              OverlayInfoExt.initFromInfo(overlayInfo),
              i,
              overlayInfo.state);
        }
      }
    }
  }

  public final void verifyOverlayPackages() {
    for (int i : this.mSettings.getUsers()) {
      for (OverlayInfo overlayInfo :
          (List)
              getSafeStream(this.mSettings.getOverlaysForUser(i).values())
                  .flatMap(new OverlayManagerService$2$$ExternalSyntheticLambda1())
                  .collect(Collectors.toList())) {
        if (OverlayInfoExt.isOverlayInfoExt(overlayInfo)
            && !new File(overlayInfo.baseCodePath).exists()) {
          this.mSettings.remove(overlayInfo.getOverlayIdentifier(), i);
          removeIdmap(OverlayInfoExt.initFromInfo(overlayInfo));
        }
      }
    }
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public void handleUserSwitch(int i) {
    List list =
        (List)
            getSafeStream(
                    new ArrayList(
                        (Collection)
                            this.mSettings.getOverlaysForUser(i).values().stream()
                                .flatMap(new OverlayManagerService$2$$ExternalSyntheticLambda1())
                                .collect(Collectors.toSet())))
                .map(
                    new Function() { // from class:
                                     // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda3
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        return OverlayInfoExt.initFromInfo((OverlayInfo) obj);
                      }
                    })
                .filter(
                    new Predicate() { // from class:
                                      // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda4
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return Objects.nonNull((OverlayInfoExt) obj);
                      }
                    })
                .collect(Collectors.toList());
    HashSet hashSet = new HashSet();
    hashSet.addAll(list);
    if (i != 0) {
      ArrayList arrayList =
          new ArrayList(
              (Collection)
                  this.mSettings.getOverlaysForUser(0).values().stream()
                      .flatMap(new OverlayManagerService$2$$ExternalSyntheticLambda1())
                      .collect(Collectors.toSet()));
      final int i2 = (SemPersonaManager.isKnoxId(i) || SemPersonaManager.isDualAppId(i)) ? 48 : 16;
      hashSet.addAll(
          (List)
              getSafeStream(arrayList)
                  .map(
                      new Function() { // from class:
                                       // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          return OverlayInfoExt.initFromInfo((OverlayInfo) obj);
                        }
                      })
                  .filter(
                      new Predicate() { // from class:
                                        // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          return Objects.nonNull((OverlayInfoExt) obj);
                        }
                      })
                  .filter(
                      new Predicate() { // from class:
                                        // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda5
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          boolean lambda$handleUserSwitch$0;
                          lambda$handleUserSwitch$0 =
                              OverlayManagerServiceExt.lambda$handleUserSwitch$0(
                                  i2, (OverlayInfoExt) obj);
                          return lambda$handleUserSwitch$0;
                        }
                      })
                  .collect(Collectors.toList()));
    }
    ArrayList arrayList2 = new ArrayList(hashSet);
    if (arrayList2.isEmpty()) {
      Slog.e("OverlayManagerExt", "Working without any overlayInfoExts ? + for user " + i);
      return;
    }
    addOverlays(arrayList2, null, i);
    this.mIsInitOnBoot = false;
  }

  public static /* synthetic */ boolean lambda$handleUserSwitch$0(
      int i, OverlayInfoExt overlayInfoExt) {
    return (i & overlayInfoExt.configFlags) != 0;
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public void replaceOverlays(
      List list, List list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
    if (list == null) {
      list = Collections.emptyList();
    }
    if (list2 == null) {
      list2 = Collections.emptyList();
    }
    List removeOverlaysInternal = removeOverlaysInternal(list, iSamsungOverlayCallback, i);
    ArrayList arrayList = new ArrayList(addOverlaysInternal(list2, iSamsungOverlayCallback, i));
    arrayList.addAll(removeOverlaysInternal);
    ArrayList arrayList2 = new ArrayList(new HashSet(arrayList));
    ArrayList arrayList3 = new ArrayList();
    arrayList3.addAll(list);
    arrayList3.addAll(list2);
    notifySystemServices(arrayList2, arrayList3, i);
  }

  public final int[] getUserIds(int i, int i2) {
    int[] userIds = this.mUserManager.getUserIds();
    if (-1 == i2) {
      return userIds;
    }
    if (i2 != 0) {
      return new int[] {i2};
    }
    if ((i & 16) != 0) {
      return userIds;
    }
    if ((i & 32) != 0) {
      ArrayList arrayList = new ArrayList();
      arrayList.add(Integer.valueOf(i2));
      for (int i3 : userIds) {
        if (SemPersonaManager.isKnoxId(i3) || SemPersonaManager.isDualAppId(i3)) {
          arrayList.add(Integer.valueOf(i3));
        }
      }
      return getSafeStream(arrayList)
          .mapToInt(new AudioService$$ExternalSyntheticLambda0())
          .toArray();
    }
    return new int[] {i2};
  }

  public final List addOverlaysInternal(
      List list, final ISamsungOverlayCallback iSamsungOverlayCallback, final int i) {
    return (List)
        ((Stream) getSafeStream(list).parallel())
            .map(
                new Function() { // from class:
                                 // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda2
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    String lambda$addOverlaysInternal$1;
                    lambda$addOverlaysInternal$1 =
                        OverlayManagerServiceExt.this.lambda$addOverlaysInternal$1(
                            i, iSamsungOverlayCallback, (OverlayInfoExt) obj);
                    return lambda$addOverlaysInternal$1;
                  }
                })
            .collect(Collectors.toList());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ String lambda$addOverlaysInternal$1(
      int i, ISamsungOverlayCallback iSamsungOverlayCallback, OverlayInfoExt overlayInfoExt) {
    if (overlayInfoExt == null || overlayInfoExt.info == null) {
      return null;
    }
    int[] userIds = getUserIds(overlayInfoExt.configFlags, i);
    for (int length = userIds.length - 1; length >= 0; length--) {
      int i2 = userIds[length];
      setEnabledState(
          overlayInfoExt,
          i2,
          i,
          this.mPackageManager.getPackageForUser(overlayInfoExt.info.targetPackageName, i2));
    }
    notifyListener(overlayInfoExt, iSamsungOverlayCallback, i);
    return overlayInfoExt.info.targetPackageName;
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public void addOverlays(List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
    notifySystemServices(addOverlaysInternal(list, iSamsungOverlayCallback, i), list, i);
  }

  public final void setEnabledState(
      OverlayInfoExt overlayInfoExt, int i, int i2, AndroidPackage androidPackage) {
    if (i != i2 && "com.android.systemui".equals(overlayInfoExt.info.targetPackageName)) {
      Slog.i(
          "OverlayManagerExt",
          "skip to update overlay : " + overlayInfoExt.info.packageName + ", for " + i);
      return;
    }
    updateOverlayState(
        androidPackage,
        overlayInfoExt,
        i,
        androidPackage == null ? 0 : initInSettings(overlayInfoExt, i) ? 3 : 2);
  }

  public final boolean initInSettings(OverlayInfoExt overlayInfoExt, int i) {
    boolean z;
    OverlayIdentifier overlayIdentifier = overlayInfoExt.getOverlayIdentifier();
    if ((overlayInfoExt.configFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) {
      try {
        z = this.mSettings.getEnabled(overlayIdentifier, i);
      } catch (OverlayManagerSettings.BadKeyException unused) {
        z = false;
      }
      this.mSettings.remove(overlayIdentifier, i);
    } else {
      z = true;
    }
    boolean z2 = z;
    OverlayManagerSettings overlayManagerSettings = this.mSettings;
    OverlayInfo overlayInfo = overlayInfoExt.info;
    overlayManagerSettings.init(
        overlayIdentifier,
        i,
        overlayInfo.targetPackageName,
        overlayInfo.targetOverlayableName,
        overlayInfo.baseCodePath,
        true,
        z2,
        overlayInfo.priority,
        overlayInfo.category,
        false);
    return z2;
  }

  /* JADX WARN: Code restructure failed: missing block: B:22:0x001a, code lost:

     if ((r3 & 1) != 0) goto L13;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void updateOverlayState(
      AndroidPackage androidPackage, OverlayInfoExt overlayInfoExt, int i, int i2) {
    if (i2 != 0) {
      if (this.mIsInitOnBoot) {
        i2 = overlayInfoExt.info.state;
      } else if (i2 == 3) {
        int updateIdmap = updateIdmap(androidPackage, overlayInfoExt, i, i2);
        if ((updateIdmap & 2) == 0) {
          i2 = 1;
        }
        i2 = 3;
      }
    } else {
      OverlayInfo overlayInfo = overlayInfoExt.info;
      if (i == 0 && androidPackage == null) {
        Slog.i(
            "OverlayManagerExt",
            "updateOverlayState(): removing idmap for Zipped Overlays: " + overlayInfo);
        removeIdmap(overlayInfoExt);
        if (overlayInfo != null
            && overlayInfo.getCategory() != null
            && overlayInfo.getCategory().startsWith("zipped-overlay")) {
          SemSamsungThemeUtils.deleteFile(overlayInfo.baseCodePath);
          this.mSettings.remove(overlayInfoExt.getOverlayIdentifier(), i);
          return;
        }
      }
      i2 = -1;
    }
    if (overlayInfoExt.info != null) {
      try {
        this.mSettings.setState(overlayInfoExt.getOverlayIdentifier(), i, i2);
      } catch (OverlayManagerSettings.BadKeyException e) {
        e.printStackTrace();
        Slog.e(
            "OverlayManagerExt",
            "Couldnt update overlay state "
                + overlayInfoExt.info.packageName
                + " for "
                + overlayInfoExt.info.targetPackageName);
      }
    }
  }

  public final int updateIdmap(
      AndroidPackage androidPackage, OverlayInfoExt overlayInfoExt, int i, int i2) {
    if ((overlayInfoExt.configFlags & 512) != 0) {
      SemSamsungThemeUtils.deleteResourceMapFile(androidPackage);
    }
    if (i2 == 3) {
      if (SemDualAppManager.isDualAppId(i) || SemPersonaManager.isKnoxId(i)) {
        i = 0;
      }
      return createIdmap(androidPackage, overlayInfoExt, i);
    }
    if (i2 != 2) {
      return 0;
    }
    removeIdmap(overlayInfoExt);
    return 0;
  }

  public final int createIdmap(
      AndroidPackage androidPackage, OverlayInfoExt overlayInfoExt, int i) {
    int createIdmap = this.mIdmapManager.createIdmap(androidPackage, overlayInfoExt, i);
    if ((createIdmap & 2) == 0 && (overlayInfoExt.configFlags & 8) != 0) {
      createIdmap = this.mIdmapManager.createIdmap(androidPackage, overlayInfoExt, i);
    }
    Slog.e(
        "OverlayManagerExt",
        "createidmap for "
            + androidPackage
            + " "
            + overlayInfoExt.info.packageName
            + " for user= "
            + i
            + " created ? "
            + createIdmap);
    return createIdmap;
  }

  public final void removeIdmap(OverlayInfoExt overlayInfoExt) {
    OverlayInfo overlayInfo;
    if (overlayInfoExt == null
        || (overlayInfo = overlayInfoExt.info) == null
        || !this.mIdmapManager.idmapExists(overlayInfo)) {
      return;
    }
    for (int i : this.mSettings.getUsers()) {
      try {
        OverlayInfo overlayInfo2 =
            this.mSettings.getOverlayInfo(overlayInfoExt.getOverlayIdentifier(), i);
        if (overlayInfo2 != null && overlayInfo2.isEnabled()) {
          return;
        }
      } catch (OverlayManagerSettings.BadKeyException unused) {
      }
    }
    Slog.e(
        "OverlayManagerExt",
        "removing idmap for "
            + overlayInfoExt.getTargetPackageName()
            + " - "
            + overlayInfoExt.info.packageName);
    IdmapManagerWrapper idmapManagerWrapper = this.mIdmapManager;
    OverlayInfo overlayInfo3 = overlayInfoExt.info;
    idmapManagerWrapper.removeIdmap(overlayInfo3, overlayInfo3.userId);
  }

  public final List removeOverlaysInternal(
      List list, final ISamsungOverlayCallback iSamsungOverlayCallback, final int i) {
    return (List)
        ((Stream) getSafeStream(list).parallel())
            .map(
                new Function() { // from class:
                                 // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda0
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    String lambda$removeOverlaysInternal$2;
                    lambda$removeOverlaysInternal$2 =
                        OverlayManagerServiceExt.this.lambda$removeOverlaysInternal$2(
                            i, iSamsungOverlayCallback, (OverlayInfoExt) obj);
                    return lambda$removeOverlaysInternal$2;
                  }
                })
            .filter(new OverlayManagerServiceExt$$ExternalSyntheticLambda1())
            .collect(Collectors.toList());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ String lambda$removeOverlaysInternal$2(
      int i, ISamsungOverlayCallback iSamsungOverlayCallback, OverlayInfoExt overlayInfoExt) {
    if (overlayInfoExt == null || overlayInfoExt.info == null) {
      return null;
    }
    int[] userIds = getUserIds(overlayInfoExt.configFlags, i);
    for (int length = userIds.length - 1; length >= 0; length--) {
      int i2 = userIds[length];
      this.mSettings.remove(overlayInfoExt.info.getOverlayIdentifier(), i2);
      boolean z = (overlayInfoExt.configFlags & 256) != 0;
      if (SemDualAppManager.isDualAppId(i2) && z) {
        Slog.d("OverlayManagerExt", "Skip deleting idmap for dual app");
      } else {
        removeIdmap(overlayInfoExt);
      }
    }
    notifyListener(overlayInfoExt, iSamsungOverlayCallback, i);
    return overlayInfoExt.info.targetPackageName;
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public void removeOverlays(List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
    notifySystemServices(removeOverlaysInternal(list, iSamsungOverlayCallback, i), list, i);
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public boolean changeOverlayState(String str, int i, boolean z) {
    if (str == null) {
      return false;
    }
    try {
      OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(new OverlayIdentifier(str), i);
      OverlayInfoExt initFromInfo = OverlayInfoExt.initFromInfo(overlayInfo);
      if (initFromInfo != null && initFromInfo.info != null) {
        try {
          this.mSettings.setEnabled(initFromInfo.getOverlayIdentifier(), i, z);
          int handleStateUpdate =
              handleStateUpdate(
                  this.mPackageManager.getPackageForUser(overlayInfo.getTargetPackageName(), i),
                  overlayInfo,
                  0,
                  i);
          return handleStateUpdate == 1 || handleStateUpdate == 2;
        } catch (OverlayManagerSettings.BadKeyException e) {
          e.printStackTrace();
        }
      }
      return false;
    } catch (OverlayManagerSettings.BadKeyException e2) {
      e2.printStackTrace();
      return false;
    }
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2) {
    ArrayMap overlaysForUser = this.mSettings.getOverlaysForUser(i2);
    ArrayList arrayList = new ArrayList();
    Iterator it = overlaysForUser.values().iterator();
    while (it.hasNext()) {
      for (OverlayInfo overlayInfo : (List) it.next()) {
        if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, i)) {
          arrayList.add(OverlayInfoExt.initFromInfo(overlayInfo));
        }
      }
    }
    return (OverlayInfoExt[]) arrayList.toArray(new OverlayInfoExt[0]);
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public OverlayInfoExt getOverlayForPath(String str, int i) {
    Iterator it = this.mSettings.getOverlaysForUser(i).values().iterator();
    while (it.hasNext()) {
      for (OverlayInfo overlayInfo : (List) it.next()) {
        if (overlayInfo.baseCodePath.equals(str)) {
          return OverlayInfoExt.initFromInfo(overlayInfo);
        }
      }
    }
    return null;
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2) {
    List<OverlayInfo> overlaysForTarget = this.mSettings.getOverlaysForTarget(str, i2);
    ArrayList arrayList = new ArrayList();
    for (OverlayInfo overlayInfo : overlaysForTarget) {
      if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, i)) {
        arrayList.add(OverlayInfoExt.initFromInfo(overlayInfo));
      }
    }
    return (OverlayInfoExt[]) arrayList.toArray(new OverlayInfoExt[0]);
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public void setIsInitonBoot(boolean z) {
    this.mIsInitOnBoot = z;
  }

  public void notifySystemServices(List list, List list2, final int i) {
    List list3 =
        (List)
            ((Stream) getSafeStream(list2).parallel())
                .map(
                    new Function() { // from class:
                                     // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda6
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        List lambda$notifySystemServices$3;
                        lambda$notifySystemServices$3 =
                            OverlayManagerServiceExt.this.lambda$notifySystemServices$3(
                                i, (OverlayInfoExt) obj);
                        return lambda$notifySystemServices$3;
                      }
                    })
                .collect(Collectors.toList());
    HashSet hashSet = new HashSet();
    Iterator it = list3.iterator();
    while (it.hasNext()) {
      hashSet.addAll((List) it.next());
    }
    Iterator it2 = hashSet.iterator();
    while (it2.hasNext()) {
      notifySystemServicesInternal(list, list2, ((Integer) it2.next()).intValue());
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ List lambda$notifySystemServices$3(int i, OverlayInfoExt overlayInfoExt) {
    if (overlayInfoExt == null || overlayInfoExt.info == null) {
      return null;
    }
    return (List)
        Arrays.stream(getUserIds(overlayInfoExt.configFlags, i))
            .boxed()
            .collect(Collectors.toList());
  }

  public final void notifySystemServicesInternal(List list, List list2, int i) {
    boolean z;
    Iterator it = list2.iterator();
    while (true) {
      if (!it.hasNext()) {
        z = true;
        break;
      }
      OverlayInfoExt overlayInfoExt = (OverlayInfoExt) it.next();
      if ("android".equals(overlayInfoExt.info.targetPackageName)
          && (overlayInfoExt.configFlags & 1024) == 0) {
        Slog.e(
            "OverlayManagerExt",
            "Not pruning targets because of overlayInfoExt of category - "
                + overlayInfoExt.category
                + " "
                + overlayInfoExt.info);
        z = false;
        break;
      }
    }
    if (list.contains("android") && z) {
      list.addAll(SamsungThemeConstants.changeableApps);
      Iterator it2 = this.mSettings.getOverlaysForUser(i).values().iterator();
      while (it2.hasNext()) {
        list.addAll(
            (List)
                getSafeStream((List) it2.next())
                    .map(
                        new Function() { // from class:
                                         // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda9
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            String lambda$notifySystemServicesInternal$4;
                            lambda$notifySystemServicesInternal$4 =
                                OverlayManagerServiceExt.lambda$notifySystemServicesInternal$4(
                                    (OverlayInfo) obj);
                            return lambda$notifySystemServicesInternal$4;
                          }
                        })
                    .collect(Collectors.toList()));
      }
    }
    List updatePackageManager =
        this.mPackageUpdateHelper.updatePackageManager(new ArrayList(new HashSet(list)), i, z);
    this.mPackageUpdateHelper.persistSettings();
    notifyActivityManager(updatePackageManager, i, z);
  }

  public static /* synthetic */ String lambda$notifySystemServicesInternal$4(
      OverlayInfo overlayInfo) {
    return OverlayInfoExt.isOverlayInfoExt(overlayInfo) ? overlayInfo.targetPackageName : "android";
  }

  public final void notifyActivityManager(final List list, final int i, boolean z) {
    Slog.d(
        "OverlayManagerExt",
        "notifyActivityManager() called with: targetPackageNames = "
            + list
            + ", userId = ["
            + i
            + "], targetsPruned = "
            + z);
    updateServiceInfos(list, i);
    if (z && list.contains("android")) {
      list.remove("android");
      list.add("framework-res");
    }
    FgThread.getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda11
              @Override // java.lang.Runnable
              public final void run() {
                OverlayManagerServiceExt.lambda$notifyActivityManager$5(list, i);
              }
            });
  }

  public static /* synthetic */ void lambda$notifyActivityManager$5(List list, int i) {
    IActivityManager service = ActivityManager.getService();
    if (service != null) {
      try {
        service.scheduleApplicationInfoChanged(list, i);
      } catch (RemoteException e) {
        Slog.e(
            "OverlayManagerExt",
            "updateActivityManagerforSamsungOverlay scheduleApplicationInfoChanged",
            e);
      }
    }
  }

  public final void updateServiceInfos(List list, int i) {
    ServiceInfo serviceInfo;
    String str;
    ApplicationInfo applicationInfo;
    try {
      SpellCheckerInfo currentSpellCheckerInfo =
          ((TextServicesManager) this.mContext.getSystemService("textservices"))
              .getCurrentSpellCheckerInfo();
      if (currentSpellCheckerInfo != null) {
        serviceInfo = currentSpellCheckerInfo.getServiceInfo();
        str = currentSpellCheckerInfo.getPackageName();
      } else {
        serviceInfo = null;
        str = null;
      }
      if (!list.contains(str)
          || serviceInfo == null
          || (applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 0))
              == null) {
        return;
      }
      ApplicationInfo applicationInfo2 = serviceInfo.applicationInfo;
      applicationInfo2.resourceDirs = applicationInfo.resourceDirs;
      applicationInfo2.overlayPaths = applicationInfo.overlayPaths;
    } catch (Exception e) {
      Slog.d(
          "OverlayManagerExt", "Exception during getting spell checker service " + e.getMessage());
    }
  }

  public final void notifyListener(
      final OverlayInfoExt overlayInfoExt,
      final ISamsungOverlayCallback iSamsungOverlayCallback,
      final int i) {
    if (iSamsungOverlayCallback == null) {
      return;
    }
    FgThread.getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda10
              @Override // java.lang.Runnable
              public final void run() {
                OverlayManagerServiceExt.this.lambda$notifyListener$6(
                    overlayInfoExt, i, iSamsungOverlayCallback);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyListener$6(
      OverlayInfoExt overlayInfoExt, int i, ISamsungOverlayCallback iSamsungOverlayCallback) {
    int i2;
    try {
      i2 = this.mSettings.getState(overlayInfoExt.getOverlayIdentifier(), i);
    } catch (OverlayManagerSettings.BadKeyException unused) {
      i2 = -1;
    }
    try {
      OverlayInfo overlayInfo = overlayInfoExt.info;
      iSamsungOverlayCallback.onOverlayStateChanged(
          overlayInfo.baseCodePath, overlayInfo.packageName, i2);
    } catch (RemoteException unused2) {
    }
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public int handleStateUpdate(
      AndroidPackage androidPackage, CriticalOverlayInfo criticalOverlayInfo, int i, int i2) {
    int i3;
    boolean z = false;
    try {
      OverlayInfo overlayInfo =
          this.mSettings.getOverlayInfo(
              new OverlayIdentifier(criticalOverlayInfo.getPackageName()), i);
      Slog.e(
          "OverlayManagerExt",
          "handleStateUpdate info exists as " + overlayInfo + " for user " + i);
      OverlayInfoExt initFromInfo = OverlayInfoExt.initFromInfo(overlayInfo);
      if (initFromInfo == null) {
        return 0;
      }
      if (((i2 & 24) != 0)
          && overlayInfo.getCategory() != null
          && overlayInfo.getCategory().startsWith("zipped-overlay")) {
        return 1;
      }
      if (androidPackage != null) {
        int createIdmap = this.mIdmapManager.createIdmap(androidPackage, initFromInfo, i);
        z = false | ((createIdmap & 2) != 0);
        i3 = createIdmap;
      } else {
        i3 = 0;
      }
      int state = this.mSettings.getState(initFromInfo.info.getOverlayIdentifier(), i);
      int inferNewState = inferNewState(androidPackage, initFromInfo.info, i2, i, i3);
      if (state != inferNewState) {
        updateOverlayState(androidPackage, initFromInfo, i, inferNewState);
        z = true;
      }
      return z ? 2 : 1;
    } catch (OverlayManagerSettings.BadKeyException e) {
      e.printStackTrace();
      return 0;
    }
  }

  @Override // com.android.server.om.IOverlayManagerExt
  public String getTargetPath(String str) {
    return this.mIdmapManager.getTargetPath(str);
  }

  public final int inferNewState(
      AndroidPackage androidPackage, OverlayInfo overlayInfo, int i, int i2, int i3) {
    if ((i & 1) != 0) {
      return 4;
    }
    if ((i & 2) != 0) {
      return 5;
    }
    if ((i & 4) != 0) {
      return 7;
    }
    if (androidPackage == null) {
      return 0;
    }
    if ((i3 & 1) != 0 || this.mIdmapManager.idmapExists(overlayInfo)) {
      return this.mSettings.getEnabled(overlayInfo.getOverlayIdentifier(), i2) ? 3 : 2;
    }
    return 1;
  }

  public final Stream getSafeStream(Collection collection) {
    return ((Collection)
            Optional.ofNullable(collection)
                .orElseGet(new OverlayManagerServiceExt$$ExternalSyntheticLambda7()))
        .stream()
            .filter(
                new Predicate() { // from class:
                                  // com.android.server.om.OverlayManagerServiceExt$$ExternalSyntheticLambda8
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return Objects.nonNull(obj);
                  }
                });
  }

  public class PMSHelperImpl {
    public final PackageManagerHelper packageManagerHelper;

    public PMSHelperImpl(PackageManagerHelper packageManagerHelper) {
      this.packageManagerHelper = packageManagerHelper;
    }

    public AndroidPackage getPackageForUser(String str, int i) {
      PackageState packageStateForUser = this.packageManagerHelper.getPackageStateForUser(str, i);
      if (packageStateForUser == null) {
        return null;
      }
      return packageStateForUser.getAndroidPackage();
    }
  }

  public final void checkAndEnableThemeCenter() {
    try {
      int applicationEnabledSetting =
          this.mContext
              .getPackageManager()
              .getApplicationEnabledSetting("com.samsung.android.themecenter");
      if (applicationEnabledSetting > 1) {
        Slog.i(
            "OverlayManagerExt",
            "themecenter state is " + applicationEnabledSetting + ", but it'll be enabled.");
        this.mContext
            .getPackageManager()
            .setApplicationEnabledSetting("com.samsung.android.themecenter", 1, 0);
      }
    } catch (Exception e) {
      Slog.e("OverlayManagerExt", "Failed to enable themecenter " + e.toString());
    }
  }

  public final void checkAndEnableThemeStore() {
    try {
      PackageUserStateInternal userStateOrDefault =
          ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
              .getPackageStateInternal("com.samsung.android.themestore")
              .getUserStateOrDefault(0);
      if (userStateOrDefault.getEnabledState() <= 1
          || !"shell:1000".equals(userStateOrDefault.getLastDisableAppCaller())) {
        return;
      }
      Slog.i(
          "OverlayManagerExt",
          "themestore state is "
              + userStateOrDefault.getEnabledState()
              + " and the last disabler is "
              + userStateOrDefault.getLastDisableAppCaller()
              + ", but it'll be enabled.");
      this.mContext
          .getPackageManager()
          .setApplicationEnabledSetting("com.samsung.android.themestore", 1, 0);
    } catch (Exception e) {
      Slog.e("OverlayManagerExt", "Failed to enable themestore " + e.toString());
    }
  }
}
