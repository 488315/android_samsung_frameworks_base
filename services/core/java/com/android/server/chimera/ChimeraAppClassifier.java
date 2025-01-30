package com.android.server.chimera;

import android.bluetooth.BluetoothAdapter;
import android.os.IInstalld;
import android.util.ArrayMap;
import android.util.Log;
import com.android.server.am.MARsPolicyManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class ChimeraAppClassifier {
  public final SystemRepository mSystemRepository;
  public static final List PROTECTED_PACKAGES_FOR_ALL_TRIGGERS =
      new ArrayList() { // from class: com.android.server.chimera.ChimeraAppClassifier.1
        {
          add("com.samsung.cmh");
          add("android.process.acore");
          add("com.android.providers.blockednumber");
          add("com.android.providers.userdictionary");
          add("com.android.providers.contacts");
          add("com.samsung.android.providers.contacts");
          add("com.android.calllogbackup");
          add("android.process.media");
          add("com.samsung.android.providers.media");
          add("com.android.providers.media");
          add("com.google.android.providers.media.module");
          add("com.android.providers.media.module");
          add("com.sec.android.app.fm");
          add("com.google.android.projection.gearhead");
          add("com.samsung.android.singletake.service");
          add("com.sec.android.app.shealth");
          add("com.baidu.map.location");
          add("com.amap.android.locations");
          add("com.tencent.android.location");
          add("com.sec.clocationservice");
          add("com.sec.location.nsflp2");
          add("com.samsung.android.game.gos");
          add("com.samsung.android.game.gametools");
          add("com.teslamotors.tesla");
          add("com.sec.android.easyMover.Agent");
          add("com.dsi.ant.service.socket");
          add("com.dsi.ant.plugins.antplus");
          add("com.samsung.android.mcfserver");
          add("com.samsung.android.mcfds");
          add("com.samsung.android.dynamiclock");
          add("com.samsung.android.game.gamelab");
          add("com.skt.skaf.A000Z00040");
          add("com.kt.olleh.storefront");
          add("com.lguplus.appstore");
          add("com.skt.skaf.OA00018282");
          add("com.verizon.mips.services");
          add("com.samsung.android.carlink");
          add("com.google.android.cellbroadcastreceiver");
          add("com.android.cellbroadcastreceiver");
          add("com.samsung.android.sidegesturepad");
          add("com.unionpay.tsmservice");
          add("com.samsung.android.scs");
          add("com.samsung.remoteviewer");
          add("com.bitstrips.imoji");
          add("com.samsung.android.knox.kpecore");
          add("com.sec.android.mimage.photoretouching");
          add("com.sec.android.app.myfiles");
          add("com.sec.modem.settings");
          add("com.sec.facatfunction");
          add("com.google.android.apps.scone");
        }
      };
  public static final List PROTECTED_PACKAGES_EXCEPT_FOR_PMM_TRIGGER =
      new ArrayList() { // from class: com.android.server.chimera.ChimeraAppClassifier.2
        {
          add("com.samsung.android.spay");
          add("com.google.android.as");
        }
      };
  public static final List PROTECTED_PACKAGES_WHILE_DOCKING =
      new ArrayList() { // from class: com.android.server.chimera.ChimeraAppClassifier.3
        {
          add("com.samsung.android.oneconnect");
        }
      };
  public static final List mProtectOnBubDisabledList = new ArrayList();
  public final Map mPackageTypeMap = new ArrayMap();
  public final List mBluetoothUsingUidList = new ArrayList();
  public ChimeraCommonUtil.TriggerSource mTriggerSource =
      ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_HOME_IDLE;
  public final Map mLongLiveApps = new HashMap();

  public final int mars2ChimeraType(int i) {
    int i2 = (i & 1024) > 0 ? 16 : 0;
    if ((i & 512) > 0) {
      i2 |= 16384;
    }
    if ((i & 16) > 0) {
      i2 |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
    }
    if ((196896 & i) > 0) {
      i2 |= 262144;
    }
    if ((i & IInstalld.FLAG_USE_QUOTA) > 0) {
      i2 |= 8388608;
    }
    if ((i & 64) > 0) {
      i2 |= 128;
    }
    if ((i & 128) > 0) {
      i2 |= 32;
    }
    if ((i & 16384) > 0) {
      i2 |= 64;
    }
    if ((i & 8) > 0) {
      i2 |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
    }
    if ((i & 4) > 0) {
      i2 |= 8;
    }
    if ((i & IInstalld.FLAG_FORCE) > 0) {
      i2 |= 2;
    }
    return (i == 0 || i2 == 0) ? i2 | 1 : i2;
  }

  public ChimeraAppClassifier(SystemRepository systemRepository) {
    this.mSystemRepository = systemRepository;
  }

  public void updatePackagesType(List list) {
    this.mPackageTypeMap.clear();
    List pkgsTypeForChimera = this.mSystemRepository.getPkgsTypeForChimera(list);
    if (pkgsTypeForChimera == null || pkgsTypeForChimera.size() <= 0) {
      return;
    }
    Iterator it = pkgsTypeForChimera.iterator();
    while (it.hasNext()) {
      String[] split = ((String) it.next()).split("/");
      if (split != null) {
        try {
          String str = split[0];
          String str2 = split[1];
          String str3 = split[2];
          this.mPackageTypeMap.put(str + "/" + str2, str3);
        } catch (NumberFormatException unused) {
          Log.d("ChimeraAppClassifier", "NumberFormatException!");
        }
      }
    }
  }

  public final boolean isLongLiveApp(String str, int i) {
    Map map = this.mLongLiveApps;
    Integer valueOf = Integer.valueOf(i);
    final SystemRepository systemRepository = this.mSystemRepository;
    Objects.requireNonNull(systemRepository);
    List list =
        (List)
            map.computeIfAbsent(
                valueOf,
                new Function() { // from class:
                                 // com.android.server.chimera.ChimeraAppClassifier$$ExternalSyntheticLambda0
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return SystemRepository.this.getLongLiveProcessesForUser(
                        ((Integer) obj).intValue());
                  }
                });
    if (list == null || !list.contains(str)) {
      return false;
    }
    Log.d("ChimeraAppClassifier", "isLongLiveApp: " + str);
    return true;
  }

  public final boolean isNeverKilledApp(String str, int i) {
    if (isLongLiveApp(str, i)) {
      return true;
    }
    if (this.mSystemRepository.isHomeHubDocked()
        && PROTECTED_PACKAGES_WHILE_DOCKING.contains(str)) {
      return true;
    }
    if (str != null && str.startsWith("com.android.cts.")) {
      return true;
    }
    if (this.mTriggerSource == ChimeraCommonUtil.TriggerSource.TRIGGER_SOURCE_PMM_CRITICAL) {
      return PROTECTED_PACKAGES_FOR_ALL_TRIGGERS.contains(str);
    }
    return PROTECTED_PACKAGES_FOR_ALL_TRIGGERS.contains(str)
        || PROTECTED_PACKAGES_EXCEPT_FOR_PMM_TRIGGER.contains(str);
  }

  public int getPackageType(String str, int i, Set set, int i2) {
    if (str != null && this.mPackageTypeMap.size() > 0) {
      String str2 = (String) this.mPackageTypeMap.get(str + "/" + i);
      r6 = mars2ChimeraType(str2 != null ? Integer.parseInt(str2) : 0);
    }
    if (MARsPolicyManager.getInstance().getMARsEnabled()
        && MARsPolicyManager.getInstance().isChinaPolicyEnabled()
        && MARsPolicyManager.getInstance().isAutoRunOn(str, i)) {
      r6 |= IInstalld.FLAG_USE_QUOTA;
    }
    if (isNeverKilledApp(str, i)) {
      r6 |= 1048576;
    }
    if (isBluetoothUsingUid(i2)) {
      r6 |= 2097152;
    }
    return isProtectOnBubDisabled(str) ? r6 | 4194304 : r6;
  }

  public void prepare(ChimeraCommonUtil.TriggerSource triggerSource) {
    this.mTriggerSource = triggerSource;
    clearLongLiveApps();
    updateBluetoothUsingUidList();
  }

  public final void clearLongLiveApps() {
    this.mLongLiveApps.clear();
  }

  public final void updateBluetoothUsingUidList() {
    this.mBluetoothUsingUidList.clear();
    Optional.ofNullable(BluetoothAdapter.getDefaultAdapter())
        .map(
            new Function() { // from class:
                             // com.android.server.chimera.ChimeraAppClassifier$$ExternalSyntheticLambda1
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                return ((BluetoothAdapter) obj).getHWUsingApps();
              }
            })
        .ifPresent(
            new Consumer() { // from class:
                             // com.android.server.chimera.ChimeraAppClassifier$$ExternalSyntheticLambda2
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ChimeraAppClassifier.this.lambda$updateBluetoothUsingUidList$0((Map) obj);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateBluetoothUsingUidList$0(Map map) {
    for (Map.Entry entry : map.entrySet()) {
      if (((Integer) entry.getKey()).intValue() == 1) {
        this.mBluetoothUsingUidList.addAll((Collection) entry.getValue());
      }
    }
  }

  public final boolean isBluetoothUsingUid(int i) {
    return this.mBluetoothUsingUidList.contains(Integer.valueOf(i));
  }

  public final boolean isProtectOnBubDisabled(String str) {
    return mProtectOnBubDisabledList.contains(str);
  }
}
