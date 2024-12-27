package com.android.server.chimera;

import android.util.ArrayMap;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ChimeraAppClassifier {
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
                    add(KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME);
                    add("com.samsung.android.app.telephonyui");
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

    public ChimeraAppClassifier(SystemRepository systemRepository) {
        this.mSystemRepository = systemRepository;
    }
}
