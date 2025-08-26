package com.android.systemui.audio.soundcraft.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.audio.AudioPlaybackManager;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.SoundCraftManager;
import com.android.systemui.audio.soundcraft.interfaces.routine.action.phone.PhoneActionType;
import com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType;
import com.android.systemui.audio.soundcraft.interfaces.routine.extension.RoutineDetailActionExtractor;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveManager;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveToggleEnum;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.BudsPluginInfo;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.model.common.Equalizer;
import com.android.systemui.audio.soundcraft.model.phone.Dolby;
import com.android.systemui.audio.soundcraft.model.phone.DolbyEnum;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.samsung.android.sdk.routines.automationservice.data.ActionStatus;
import com.samsung.android.sdk.routines.automationservice.data.ConditionStatus;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.data.RoutineDetail;
import com.samsung.android.sdk.routines.automationservice.data.RoutineInfo;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import com.samsung.android.sdk.routines.automationservice.interfaces.ContentHandler;
import com.samsung.android.sdk.routines.automationservice.internal.AutomationServiceImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class SoundCraftViewModel extends BaseViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioPlaybackManager audioPlaybackManager;
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ColoredBGHelper coloredBGHelper;
    public final Context context;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final SoundCraftSettings settings;
    public final SoundAliveManager soundAliveManager;
    public final SoundCraftManager soundCraftManager;
    public final VolumeManager volumeManager;
    public final WearableManager wearableManager;
    public final MutableLiveData isBatteryInfoBoxVisible = new MutableLiveData();
    public final MutableLiveData isNoiseControlBoxVisible = new MutableLiveData();
    public final MutableLiveData isActionBarVisible = new MutableLiveData();
    public final MutableLiveData isVolumeBarVisible = new MutableLiveData();
    public final MutableLiveData isFromNowBar = new MutableLiveData();
    public final MutableLiveData isFromCover = new MutableLiveData();
    public final MutableLiveData updateBatteryInfoBox = new MutableLiveData();
    public final MutableLiveData updateEffectBox = new MutableLiveData();
    public final MutableLiveData updateNoiseControlBox = new MutableLiveData();
    public final MutableLiveData updateVolumeBar = new MutableLiveData();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SoundCraftViewModel(Context context, WearableManager wearableManager, RoutineManager routineManager, BluetoothDeviceManager bluetoothDeviceManager, ModelProvider modelProvider, SoundCraftSettings soundCraftSettings, AudioPlaybackManager audioPlaybackManager, SoundAliveManager soundAliveManager, VolumeManager volumeManager, SoundCraftManager soundCraftManager, ColoredBGHelper coloredBGHelper) {
        this.context = context;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.modelProvider = modelProvider;
        this.settings = soundCraftSettings;
        this.audioPlaybackManager = audioPlaybackManager;
        this.soundAliveManager = soundAliveManager;
        this.volumeManager = volumeManager;
        this.soundCraftManager = soundCraftManager;
        this.coloredBGHelper = coloredBGHelper;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0363  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0378  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x06df  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01d1  */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v20, types: [com.samsung.android.sdk.routines.automationservice.data.ConditionStatus$Companion, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r14v24 */
    /* JADX WARN: Type inference failed for: r14v33 */
    /* JADX WARN: Type inference failed for: r14v34 */
    /* JADX WARN: Type inference failed for: r14v35 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r19v0 */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v12 */
    /* JADX WARN: Type inference failed for: r19v13 */
    /* JADX WARN: Type inference failed for: r19v22 */
    /* JADX WARN: Type inference failed for: r19v23 */
    /* JADX WARN: Type inference failed for: r19v4, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r19v5 */
    /* JADX WARN: Type inference failed for: r19v6 */
    /* JADX WARN: Type inference failed for: r19v7 */
    /* JADX WARN: Type inference failed for: r19v8 */
    /* JADX WARN: Type inference failed for: r20v10 */
    /* JADX WARN: Type inference failed for: r20v11 */
    /* JADX WARN: Type inference failed for: r20v12 */
    /* JADX WARN: Type inference failed for: r20v13 */
    /* JADX WARN: Type inference failed for: r20v17 */
    /* JADX WARN: Type inference failed for: r20v18 */
    /* JADX WARN: Type inference failed for: r20v19 */
    /* JADX WARN: Type inference failed for: r20v21 */
    /* JADX WARN: Type inference failed for: r20v23 */
    /* JADX WARN: Type inference failed for: r20v24 */
    /* JADX WARN: Type inference failed for: r20v5 */
    /* JADX WARN: Type inference failed for: r20v6 */
    /* JADX WARN: Type inference failed for: r20v9, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r4v28, types: [java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final EffectModel getAppRoutineModel() {
        String str;
        List list;
        String str2;
        boolean z;
        List list2;
        boolean z2;
        RoutineDetail routineDetail;
        EffectModel effectModel;
        Integer numValueOf;
        Integer numValueOf2;
        Boolean boolValueOf;
        String strFindProjectName;
        EffectModel effectModel2;
        String strFindProjectName2;
        Object next;
        DolbyEnum dolbyEnum;
        String routineId;
        ?? r20;
        ?? contentResolver;
        Context context;
        List list3;
        ContentHandler contentHandler;
        RoutineInfo routineInfo;
        boolean z3;
        ?? r14;
        boolean z4;
        ?? r142;
        List list4;
        ContentHandler contentHandler2;
        Cursor cursorQuery;
        List list5;
        ContentHandler contentHandler3;
        List list6;
        ContentHandler contentHandler4;
        String string;
        AbstractList abstractList;
        AutomationService.SystemRoutineType systemRoutineType;
        List list7;
        boolean z5;
        boolean z6;
        List list8;
        AppSettingModel appSettingModel = new AppSettingModel();
        ModelProvider modelProvider = this.modelProvider;
        modelProvider.appSettingModel = appSettingModel;
        SoundCraftSettings soundCraftSettings = this.settings;
        boolean z7 = soundCraftSettings.isAppSettingEnabled;
        Boolean boolValueOf2 = Boolean.valueOf(z7);
        if (!z7) {
            boolValueOf2 = null;
        }
        Log.d("SoundCraft.SoundCraftViewModel", "getAppRoutineModel : isAppSettingEnabled=" + boolValueOf2);
        String playingAppPackage = boolValueOf2 != null ? this.audioPlaybackManager.getPlayingAppPackage() : null;
        modelProvider.appSettingModel.playingAudioPackageName = playingAppPackage;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getAppRoutineModel : playingAudioPackage=", playingAppPackage, "SoundCraft.SoundCraftViewModel");
        if (playingAppPackage == null) {
            modelProvider.appSettingModel.readyToUpdateRoutine = false;
            Log.d("SoundCraft.SoundCraftViewModel", "getAppRoutineModel : readyToUpdateRoutine=false");
            return null;
        }
        modelProvider.appSettingModel.readyToUpdateRoutine = true;
        Log.d("SoundCraft.SoundCraftViewModel", "getAppRoutineModel : readyToUpdateRoutine=true");
        String str3 = soundCraftSettings.budsPluginPackageName;
        List list9 = modelProvider.effectModel.equalizerList;
        RoutineManager routineManager = this.routineManager;
        routineManager.getClass();
        Log.d("SoundCraft.RoutineManager", "getEffectModel : packageName=".concat(playingAppPackage));
        BudsPluginInfo.Companion.getClass();
        String strFindProjectName3 = BudsPluginInfo.Companion.findProjectName(str3);
        ModelProvider modelProvider2 = routineManager.modelProvider;
        if (strFindProjectName3 != null && (routineId = routineManager.getRoutineId(playingAppPackage)) != null) {
            AutomationService service = routineManager.getService();
            Context context2 = routineManager.context;
            AutomationService.SystemRoutineType currentSystemRoutineType = routineManager.getCurrentSystemRoutineType();
            AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
            automationServiceImpl.getClass();
            com.samsung.android.sdk.routines.automationservice.internal.Log log = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
            String strConcat = "getRoutineDetailByRoutineId: routineId:".concat(routineId);
            log.getClass();
            com.samsung.android.sdk.routines.automationservice.internal.Log.i("AutomationServiceImpl@SDK", strConcat);
            if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, context2, currentSystemRoutineType)) {
                ContentHandler contentHandler5 = automationServiceImpl.contentHandler;
                try {
                    ((ContentHandlerImpl) contentHandler5).getClass();
                    contentResolver = context2.getContentResolver();
                    r20 = Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/routine_info/routine/".concat(routineId));
                    context = null;
                    str2 = null;
                    str = str3;
                } catch (Exception e) {
                    e = e;
                    str = str3;
                }
                try {
                    cursorQuery = contentResolver.query(r20, null, null, null, null, null);
                    try {
                    } catch (Exception e2) {
                        e = e2;
                        com.samsung.android.sdk.routines.automationservice.internal.Log log2 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                        String str4 = "queryRoutineInfo: " + e.getMessage();
                        log2.getClass();
                        com.samsung.android.sdk.routines.automationservice.internal.Log.e("AutomationServiceImpl@SDK", str4);
                        contentHandler2 = contentResolver;
                        list4 = r20;
                        routineInfo = null;
                        contentHandler = contentHandler2;
                        list3 = list4;
                        if (routineInfo == null) {
                        }
                        if (routineDetail != null) {
                        }
                        Log.d(str2, "getRoutineEffectModel : budsInfo=" + effectModel);
                        return effectModel;
                    }
                } catch (Exception e3) {
                    e = e3;
                    r20 = list9;
                    str2 = "SoundCraft.SoundCraftViewModel";
                    contentResolver = contentHandler5;
                    context = context2;
                    com.samsung.android.sdk.routines.automationservice.internal.Log log22 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                    String str42 = "queryRoutineInfo: " + e.getMessage();
                    log22.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log.e("AutomationServiceImpl@SDK", str42);
                    contentHandler2 = contentResolver;
                    list4 = r20;
                    routineInfo = null;
                    contentHandler = contentHandler2;
                    list3 = list4;
                    if (routineInfo == null) {
                    }
                    if (routineDetail != null) {
                    }
                    Log.d(str2, "getRoutineEffectModel : budsInfo=" + effectModel);
                    return effectModel;
                }
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.getCount() <= 0 || !cursorQuery.moveToFirst()) {
                            list5 = list9;
                            str2 = "SoundCraft.SoundCraftViewModel";
                            contentHandler3 = contentHandler5;
                            context = context2;
                            contentHandler4 = contentHandler3;
                            list6 = list5;
                            Unit unit = Unit.INSTANCE;
                            cursorQuery.close();
                            contentHandler2 = contentHandler4;
                            list4 = list6;
                        } else {
                            RoutineInfo.Companion companion = RoutineInfo.Companion;
                            try {
                                String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("name"));
                                if (string2 != null) {
                                    contentResolver = contentHandler5;
                                    try {
                                        String string3 = cursorQuery.getString(cursorQuery.getColumnIndex("uuid"));
                                        if (string3 == null) {
                                            list5 = list9;
                                            str2 = "SoundCraft.SoundCraftViewModel";
                                            contentHandler3 = contentResolver;
                                        } else {
                                            try {
                                                AutomationService.SystemRoutineType.Companion companion2 = AutomationService.SystemRoutineType.Companion;
                                                context = context2;
                                                try {
                                                    string = cursorQuery.getString(cursorQuery.getColumnIndex("type"));
                                                    companion2.getClass();
                                                    str2 = "SoundCraft.SoundCraftViewModel";
                                                    try {
                                                        abstractList = (AbstractList) AutomationService.SystemRoutineType.$ENTRIES;
                                                        abstractList.getClass();
                                                        r20 = list9;
                                                    } catch (Throwable th) {
                                                        th = th;
                                                        r20 = list9;
                                                    }
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    r20 = list9;
                                                    str2 = "SoundCraft.SoundCraftViewModel";
                                                }
                                            } catch (Throwable th3) {
                                                th = th3;
                                                r20 = list9;
                                                str2 = "SoundCraft.SoundCraftViewModel";
                                                contentResolver = contentResolver;
                                                context = context2;
                                                try {
                                                    throw th;
                                                } finally {
                                                }
                                            }
                                            try {
                                                AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
                                                while (true) {
                                                    if (!iteratorImpl.hasNext()) {
                                                        systemRoutineType = null;
                                                        break;
                                                    }
                                                    systemRoutineType = (AutomationService.SystemRoutineType) iteratorImpl.next();
                                                    AbstractList.IteratorImpl iteratorImpl2 = iteratorImpl;
                                                    if (Intrinsics.areEqual(systemRoutineType.getValue(), string)) {
                                                        break;
                                                    }
                                                    iteratorImpl = iteratorImpl2;
                                                }
                                                if (systemRoutineType == null) {
                                                    contentHandler4 = contentResolver;
                                                    list6 = r20;
                                                    Unit unit2 = Unit.INSTANCE;
                                                    cursorQuery.close();
                                                    contentHandler2 = contentHandler4;
                                                    list4 = list6;
                                                } else {
                                                    companion.getClass();
                                                    routineInfo = new RoutineInfo(string2, string3, systemRoutineType, null);
                                                    cursorQuery.close();
                                                    contentHandler = contentResolver;
                                                    list3 = r20;
                                                    if (routineInfo == null && routineInfo.type == currentSystemRoutineType) {
                                                        ArrayList arrayList = new ArrayList();
                                                        try {
                                                            ((ContentHandlerImpl) contentHandler).getClass();
                                                            Cursor cursorQuery2 = context.getContentResolver().query(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/condition_status/" + routineId), null, null, null, null, null);
                                                            r14 = currentSystemRoutineType;
                                                            if (cursorQuery2 != null) {
                                                                try {
                                                                    currentSystemRoutineType = currentSystemRoutineType;
                                                                    if (cursorQuery2.getCount() > 0) {
                                                                        currentSystemRoutineType = currentSystemRoutineType;
                                                                        if (cursorQuery2.moveToFirst()) {
                                                                            int columnIndex = cursorQuery2.getColumnIndex("instance_id");
                                                                            int columnIndex2 = cursorQuery2.getColumnIndex("tag");
                                                                            int columnIndex3 = cursorQuery2.getColumnIndex("enabled");
                                                                            while (true) {
                                                                                r142 = ConditionStatus.Companion;
                                                                                long j = cursorQuery2.getLong(columnIndex);
                                                                                int i = columnIndex;
                                                                                int i2 = columnIndex3;
                                                                                boolean z8 = cursorQuery2.getInt(columnIndex3) == 1;
                                                                                String string4 = cursorQuery2.getString(columnIndex2);
                                                                                ParameterValues parameterValues = AutomationServiceImpl.getParameterValues(cursorQuery2);
                                                                                r142.getClass();
                                                                                arrayList.add(new ConditionStatus(j, z8, string4, parameterValues, null));
                                                                                if (!cursorQuery2.moveToNext()) {
                                                                                    break;
                                                                                }
                                                                                columnIndex = i;
                                                                                columnIndex3 = i2;
                                                                            }
                                                                            currentSystemRoutineType = r142;
                                                                        }
                                                                    }
                                                                    Unit unit3 = Unit.INSTANCE;
                                                                    cursorQuery2.close();
                                                                    r14 = currentSystemRoutineType;
                                                                } finally {
                                                                }
                                                            }
                                                        } catch (Exception e4) {
                                                            com.samsung.android.sdk.routines.automationservice.internal.Log log3 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                                            String str5 = "queryConditionStatus: " + e4.getMessage();
                                                            log3.getClass();
                                                            com.samsung.android.sdk.routines.automationservice.internal.Log.e("AutomationServiceImpl@SDK", str5);
                                                            r14 = currentSystemRoutineType;
                                                        }
                                                        ArrayList arrayList2 = new ArrayList();
                                                        try {
                                                            ((ContentHandlerImpl) contentHandler).getClass();
                                                            Cursor cursorQuery3 = context.getContentResolver().query(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/action_status/" + routineId), null, null, null, null, null);
                                                            try {
                                                                if (cursorQuery3 != null) {
                                                                    try {
                                                                        if (cursorQuery3.getCount() <= 0 || !cursorQuery3.moveToFirst()) {
                                                                            r14 = 1;
                                                                        } else {
                                                                            int columnIndex4 = cursorQuery3.getColumnIndex("instance_id");
                                                                            int columnIndex5 = cursorQuery3.getColumnIndex("tag");
                                                                            int columnIndex6 = cursorQuery3.getColumnIndex("enabled");
                                                                            do {
                                                                                ActionStatus.Companion companion3 = ActionStatus.Companion;
                                                                                long j2 = cursorQuery3.getLong(columnIndex4);
                                                                                r14 = 1;
                                                                                r14 = 1;
                                                                                boolean z9 = cursorQuery3.getInt(columnIndex6) == 1;
                                                                                try {
                                                                                    String string5 = cursorQuery3.getString(columnIndex5);
                                                                                    ParameterValues parameterValues2 = AutomationServiceImpl.getParameterValues(cursorQuery3);
                                                                                    companion3.getClass();
                                                                                    arrayList2.add(new ActionStatus(j2, z9, string5, parameterValues2, null));
                                                                                } catch (Throwable th4) {
                                                                                    th = th4;
                                                                                    try {
                                                                                        throw th;
                                                                                    } finally {
                                                                                    }
                                                                                }
                                                                            } while (cursorQuery3.moveToNext());
                                                                        }
                                                                        Unit unit4 = Unit.INSTANCE;
                                                                        cursorQuery3.close();
                                                                        z4 = r14;
                                                                    } catch (Throwable th5) {
                                                                        th = th5;
                                                                        r14 = 1;
                                                                    }
                                                                } else {
                                                                    z4 = true;
                                                                }
                                                            } catch (Exception e5) {
                                                                e = e5;
                                                                com.samsung.android.sdk.routines.automationservice.internal.Log log4 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                                                String str6 = "queryActionStatus: " + e.getMessage();
                                                                log4.getClass();
                                                                com.samsung.android.sdk.routines.automationservice.internal.Log.e("AutomationServiceImpl@SDK", str6);
                                                                z4 = r14;
                                                                z3 = z4;
                                                                list8 = list3;
                                                                if (arrayList.isEmpty()) {
                                                                    routineDetail = null;
                                                                    z5 = z3;
                                                                    list7 = list8;
                                                                    if (routineDetail != null) {
                                                                    }
                                                                    StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("getRoutineDetailById : routineId=", routineId, ", isRoutineExist=", ", return RoutineDetail=", z6);
                                                                    sbM.append(routineDetail);
                                                                    Log.d("SoundCraft.RoutineManager", sbM.toString());
                                                                    modelProvider2.appSettingModel.routineExistOnPlugin = z6;
                                                                    z = z5;
                                                                    list = list7;
                                                                    z2 = z5;
                                                                    list2 = list7;
                                                                    if (!z6) {
                                                                        routineDetail = null;
                                                                        z2 = z;
                                                                        list2 = list;
                                                                    }
                                                                }
                                                                if (routineDetail != null) {
                                                                }
                                                                Log.d(str2, "getRoutineEffectModel : budsInfo=" + effectModel);
                                                                return effectModel;
                                                            }
                                                        } catch (Exception e6) {
                                                            e = e6;
                                                            r14 = 1;
                                                        }
                                                        z3 = z4;
                                                        list8 = list3;
                                                        if (arrayList.isEmpty()) {
                                                            z3 = z4;
                                                            list8 = list3;
                                                            if (!arrayList2.isEmpty()) {
                                                                routineDetail = new RoutineDetail(routineInfo, arrayList, arrayList2);
                                                                com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE.getClass();
                                                                com.samsung.android.sdk.routines.automationservice.internal.Log.i("AutomationServiceImpl@SDK", "getRoutineDetailByRoutineUuid: " + routineDetail);
                                                                z5 = z4;
                                                                list7 = list3;
                                                            }
                                                            if (routineDetail != null) {
                                                                z6 = false;
                                                                for (ActionStatus actionStatus : routineDetail.actions) {
                                                                    if (StringsKt__StringsKt.contains(actionStatus.tag, strFindProjectName3, false)) {
                                                                        z6 = z5 ? 1 : 0;
                                                                    }
                                                                    Log.d("SoundCraft.RoutineManager", "- getRoutineDetailById : action: " + actionStatus.tag + " - " + actionStatus.parameterValues.toJsonString());
                                                                }
                                                            } else {
                                                                z6 = false;
                                                                routineDetail = null;
                                                            }
                                                            StringBuilder sbM2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("getRoutineDetailById : routineId=", routineId, ", isRoutineExist=", ", return RoutineDetail=", z6);
                                                            sbM2.append(routineDetail);
                                                            Log.d("SoundCraft.RoutineManager", sbM2.toString());
                                                            modelProvider2.appSettingModel.routineExistOnPlugin = z6;
                                                            z = z5;
                                                            list = list7;
                                                            z2 = z5;
                                                            list2 = list7;
                                                            if (!z6) {
                                                            }
                                                        }
                                                    } else {
                                                        z3 = true;
                                                        com.samsung.android.sdk.routines.automationservice.internal.Log log5 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                                        StringBuilder sb = new StringBuilder("getRoutineDetailByRoutineUuid() routineInfo.type: ");
                                                        sb.append(routineInfo == null ? routineInfo.type : null);
                                                        String string6 = sb.toString();
                                                        log5.getClass();
                                                        com.samsung.android.sdk.routines.automationservice.internal.Log.e("AutomationServiceImpl@SDK", string6);
                                                        list8 = list3;
                                                    }
                                                }
                                            } catch (Throwable th6) {
                                                th = th6;
                                                throw th;
                                            }
                                        }
                                    } catch (Throwable th7) {
                                        th = th7;
                                        r20 = list9;
                                        str2 = "SoundCraft.SoundCraftViewModel";
                                        contentResolver = contentResolver;
                                    }
                                }
                                context = context2;
                                contentHandler4 = contentHandler3;
                                list6 = list5;
                                Unit unit22 = Unit.INSTANCE;
                                cursorQuery.close();
                                contentHandler2 = contentHandler4;
                                list4 = list6;
                            } catch (Throwable th8) {
                                th = th8;
                                r20 = list9;
                                str2 = "SoundCraft.SoundCraftViewModel";
                                contentResolver = contentHandler5;
                            }
                        }
                        if (routineDetail != null) {
                            if (modelProvider2.effectOutDeviceType == EffectOutDeviceType.PHONE) {
                                RoutineDetailActionExtractor routineDetailActionExtractor = RoutineDetailActionExtractor.INSTANCE;
                                PhoneActionType phoneActionType = PhoneActionType.EQUALIZER;
                                String strValueOf = String.valueOf(SoundAliveEqEnum.Balanced.getRoutineActionValue());
                                routineDetailActionExtractor.getClass();
                                String phoneActionValue = RoutineDetailActionExtractor.getPhoneActionValue(routineDetail, phoneActionType, strValueOf);
                                Iterator it = ((AbstractList) SoundAliveEqEnum.$ENTRIES).iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        next = null;
                                        break;
                                    }
                                    next = it.next();
                                    if (Intrinsics.areEqual(((SoundAliveEqEnum) next).getRoutineActionValue(), phoneActionValue)) {
                                        break;
                                    }
                                }
                                SoundAliveEqEnum soundAliveEqEnum = (SoundAliveEqEnum) next;
                                RoutineDetailActionExtractor routineDetailActionExtractor2 = RoutineDetailActionExtractor.INSTANCE;
                                PhoneActionType phoneActionType2 = PhoneActionType.DOLBY;
                                String strValueOf2 = String.valueOf(DolbyEnum.Off.getRoutineActionValue());
                                routineDetailActionExtractor2.getClass();
                                String phoneActionValue2 = RoutineDetailActionExtractor.getPhoneActionValue(routineDetail, phoneActionType2, strValueOf2);
                                Iterator it2 = ((AbstractList) DolbyEnum.$ENTRIES).iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        dolbyEnum = null;
                                        break;
                                    }
                                    ?? next2 = it2.next();
                                    if (Intrinsics.areEqual(((DolbyEnum) next2).getRoutineActionValue(), phoneActionValue2)) {
                                        dolbyEnum = next2;
                                        break;
                                    }
                                }
                                DolbyEnum dolbyEnum2 = dolbyEnum;
                                List list10 = DolbyEnum.$ENTRIES;
                                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list10, 10));
                                Iterator it3 = ((AbstractList) list10).iterator();
                                while (it3.hasNext()) {
                                    DolbyEnum dolbyEnum3 = (DolbyEnum) it3.next();
                                    arrayList3.add(new Dolby(routineManager.context.getString(dolbyEnum3.getNameResId()), dolbyEnum3 == dolbyEnum2 ? z2 : false));
                                }
                                List list11 = SoundAliveEqEnum.$ENTRIES;
                                ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list11, 10));
                                Iterator it4 = ((AbstractList) list11).iterator();
                                while (it4.hasNext()) {
                                    SoundAliveEqEnum soundAliveEqEnum2 = (SoundAliveEqEnum) it4.next();
                                    arrayList4.add(new Equalizer(routineManager.context.getString(soundAliveEqEnum2.getNameResId()), soundAliveEqEnum2 == soundAliveEqEnum ? z2 : false));
                                }
                                RoutineDetailActionExtractor routineDetailActionExtractor3 = RoutineDetailActionExtractor.INSTANCE;
                                PhoneActionType phoneActionType3 = PhoneActionType.VOICE_BOOST;
                                Boolean bool = Boolean.FALSE;
                                String strValueOf3 = String.valueOf(bool);
                                routineDetailActionExtractor3.getClass();
                                String phoneActionValue3 = RoutineDetailActionExtractor.getPhoneActionValue(routineDetail, phoneActionType3, strValueOf3);
                                SoundAliveToggleEnum.Companion.getClass();
                                effectModel2 = new EffectModel(arrayList3, null, arrayList4, null, null, Boolean.valueOf(SoundAliveToggleEnum.Companion.getBooleanActionValue(phoneActionValue3)), Boolean.valueOf(SoundAliveToggleEnum.Companion.getBooleanActionValue(RoutineDetailActionExtractor.getPhoneActionValue(routineDetail, PhoneActionType.VOLUME_NORMALIZATION, String.valueOf(bool)))), 26, null);
                                Log.d("SoundCraft.RoutineManager", "getPhoneEffectModel : model=" + effectModel2);
                            } else {
                                RoutineDetailActionExtractor routineDetailActionExtractor4 = RoutineDetailActionExtractor.INSTANCE;
                                BudsActionType budsActionType = BudsActionType.EQUALIZER;
                                Integer numValueOf3 = 0;
                                try {
                                    int i3 = Result.$r8$clinit;
                                    BudsPluginInfo.Companion.getClass();
                                    strFindProjectName2 = BudsPluginInfo.Companion.findProjectName(str);
                                } catch (Throwable th9) {
                                    int i4 = Result.$r8$clinit;
                                    Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(new Result.Failure(th9));
                                    if (thM3442exceptionOrNullimpl != null) {
                                        thM3442exceptionOrNullimpl.printStackTrace();
                                    }
                                }
                                if (strFindProjectName2 != null) {
                                    RoutineDetailActionExtractor routineDetailActionExtractor5 = RoutineDetailActionExtractor.INSTANCE;
                                    String tag = budsActionType.getTag(strFindProjectName2);
                                    String strValueOf4 = String.valueOf(numValueOf3);
                                    routineDetailActionExtractor5.getClass();
                                    numValueOf = Integer.valueOf(Integer.parseInt(RoutineDetailActionExtractor.getBudsActionValue(routineDetail, tag, strValueOf4)));
                                } else {
                                    numValueOf = numValueOf3;
                                }
                                int iIntValue = numValueOf.intValue();
                                if (modelProvider2.budsModel.getSpatialAudio() == null) {
                                    boolValueOf = null;
                                } else {
                                    RoutineDetailActionExtractor routineDetailActionExtractor6 = RoutineDetailActionExtractor.INSTANCE;
                                    BudsActionType budsActionType2 = BudsActionType.SPATIAL_AUDIO;
                                    try {
                                        BudsPluginInfo.Companion.getClass();
                                        strFindProjectName = BudsPluginInfo.Companion.findProjectName(str);
                                    } catch (Throwable th10) {
                                        int i5 = Result.$r8$clinit;
                                        Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(new Result.Failure(th10));
                                        if (thM3442exceptionOrNullimpl2 != null) {
                                            thM3442exceptionOrNullimpl2.printStackTrace();
                                        }
                                    }
                                    if (strFindProjectName != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor7 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag2 = budsActionType2.getTag(strFindProjectName);
                                        String strValueOf5 = String.valueOf(numValueOf3);
                                        routineDetailActionExtractor7.getClass();
                                        numValueOf2 = Integer.valueOf(Integer.parseInt(RoutineDetailActionExtractor.getBudsActionValue(routineDetail, tag2, strValueOf5)));
                                    } else {
                                        numValueOf2 = numValueOf3;
                                    }
                                    boolValueOf = Boolean.valueOf(numValueOf2.intValue() != 0 ? z2 : false);
                                }
                                RoutineDetailActionExtractor routineDetailActionExtractor8 = RoutineDetailActionExtractor.INSTANCE;
                                BudsActionType budsActionType3 = BudsActionType.SPATIAL_AUDIO;
                                try {
                                    BudsPluginInfo.Companion.getClass();
                                    String strFindProjectName4 = BudsPluginInfo.Companion.findProjectName(str);
                                    if (strFindProjectName4 != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor9 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag3 = budsActionType3.getTag(strFindProjectName4);
                                        String strValueOf6 = String.valueOf(numValueOf3);
                                        routineDetailActionExtractor9.getClass();
                                        numValueOf3 = Integer.valueOf(Integer.parseInt(RoutineDetailActionExtractor.getBudsActionValue(routineDetail, tag3, strValueOf6)));
                                    }
                                } catch (Throwable th11) {
                                    int i6 = Result.$r8$clinit;
                                    Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(new Result.Failure(th11));
                                    if (thM3442exceptionOrNullimpl3 != null) {
                                        thM3442exceptionOrNullimpl3.printStackTrace();
                                    }
                                }
                                boolean z10 = numValueOf3.intValue() == 2 ? z2 : false;
                                List<Equalizer> list12 = list2;
                                ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list12, 10));
                                List list13 = list2;
                                for (Equalizer equalizer : list12) {
                                    List list14 = list13;
                                    arrayList5.add(new Equalizer(equalizer.getName(), list14.indexOf(equalizer) == iIntValue ? z2 : false));
                                    list13 = list14;
                                }
                                RoutineDetailActionExtractor routineDetailActionExtractor10 = RoutineDetailActionExtractor.INSTANCE;
                                BudsActionType budsActionType4 = BudsActionType.VOICE_BOOST;
                                Boolean boolValueOf3 = Boolean.FALSE;
                                try {
                                    int i7 = Result.$r8$clinit;
                                    BudsPluginInfo.Companion.getClass();
                                    String strFindProjectName5 = BudsPluginInfo.Companion.findProjectName(str);
                                    if (strFindProjectName5 != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor11 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag4 = budsActionType4.getTag(strFindProjectName5);
                                        String strValueOf7 = String.valueOf(boolValueOf3);
                                        routineDetailActionExtractor11.getClass();
                                        boolValueOf3 = Boolean.valueOf(Boolean.parseBoolean(RoutineDetailActionExtractor.getBudsActionValue(routineDetail, tag4, strValueOf7)));
                                    }
                                } catch (Throwable th12) {
                                    int i8 = Result.$r8$clinit;
                                    Throwable thM3442exceptionOrNullimpl4 = Result.m3442exceptionOrNullimpl(new Result.Failure(th12));
                                    if (thM3442exceptionOrNullimpl4 != null) {
                                        thM3442exceptionOrNullimpl4.printStackTrace();
                                    }
                                }
                                Boolean bool2 = boolValueOf3;
                                RoutineDetailActionExtractor routineDetailActionExtractor12 = RoutineDetailActionExtractor.INSTANCE;
                                BudsActionType budsActionType5 = BudsActionType.VOLUME_NORMALIZATION;
                                Boolean boolValueOf4 = Boolean.FALSE;
                                try {
                                    BudsPluginInfo.Companion.getClass();
                                    String strFindProjectName6 = BudsPluginInfo.Companion.findProjectName(str);
                                    if (strFindProjectName6 != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor13 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag5 = budsActionType5.getTag(strFindProjectName6);
                                        String strValueOf8 = String.valueOf(boolValueOf4);
                                        routineDetailActionExtractor13.getClass();
                                        boolValueOf4 = Boolean.valueOf(Boolean.parseBoolean(RoutineDetailActionExtractor.getBudsActionValue(routineDetail, tag5, strValueOf8)));
                                    }
                                } catch (Throwable th13) {
                                    int i9 = Result.$r8$clinit;
                                    Throwable thM3442exceptionOrNullimpl5 = Result.m3442exceptionOrNullimpl(new Result.Failure(th13));
                                    if (thM3442exceptionOrNullimpl5 != null) {
                                        thM3442exceptionOrNullimpl5.printStackTrace();
                                    }
                                }
                                effectModel2 = new EffectModel(null, null, arrayList5, boolValueOf, Boolean.valueOf(z10), bool2, boolValueOf4, 3, null);
                                Log.d("SoundCraft.RoutineManager", "getBudsEffectModel : model=" + effectModel2);
                            }
                            effectModel = effectModel2;
                        } else {
                            effectModel = null;
                        }
                        Log.d(str2, "getRoutineEffectModel : budsInfo=" + effectModel);
                        return effectModel;
                    } catch (Throwable th14) {
                        th = th14;
                        r20 = list9;
                        str2 = "SoundCraft.SoundCraftViewModel";
                        contentResolver = contentHandler5;
                    }
                } else {
                    list4 = list9;
                    str2 = "SoundCraft.SoundCraftViewModel";
                    contentHandler2 = contentHandler5;
                    context = context2;
                }
                routineInfo = null;
                contentHandler = contentHandler2;
                list3 = list4;
                if (routineInfo == null) {
                    z3 = true;
                    com.samsung.android.sdk.routines.automationservice.internal.Log log52 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                    StringBuilder sb2 = new StringBuilder("getRoutineDetailByRoutineUuid() routineInfo.type: ");
                    sb2.append(routineInfo == null ? routineInfo.type : null);
                    String string62 = sb2.toString();
                    log52.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log.e("AutomationServiceImpl@SDK", string62);
                    list8 = list3;
                }
                if (routineDetail != null) {
                }
                Log.d(str2, "getRoutineEffectModel : budsInfo=" + effectModel);
                return effectModel;
            }
            str = str3;
            list8 = list9;
            str2 = "SoundCraft.SoundCraftViewModel";
            z3 = true;
            routineDetail = null;
            z5 = z3;
            list7 = list8;
            if (routineDetail != null) {
            }
            StringBuilder sbM22 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("getRoutineDetailById : routineId=", routineId, ", isRoutineExist=", ", return RoutineDetail=", z6);
            sbM22.append(routineDetail);
            Log.d("SoundCraft.RoutineManager", sbM22.toString());
            modelProvider2.appSettingModel.routineExistOnPlugin = z6;
            z = z5;
            list = list7;
            z2 = z5;
            list2 = list7;
            if (!z6) {
            }
            if (routineDetail != null) {
            }
            Log.d(str2, "getRoutineEffectModel : budsInfo=" + effectModel);
            return effectModel;
        }
        str = str3;
        list = list9;
        str2 = "SoundCraft.SoundCraftViewModel";
        z = true;
        routineDetail = null;
        z2 = z;
        list2 = list;
        if (routineDetail != null) {
        }
        Log.d(str2, "getRoutineEffectModel : budsInfo=" + effectModel);
        return effectModel;
    }

    public final boolean hasNoiseControl() {
        Set noiseControlsList = this.modelProvider.budsModel.getNoiseControlsList();
        boolean z = false;
        if (noiseControlsList != null) {
            Iterator it = noiseControlsList.iterator();
            while (it.hasNext()) {
                String name = ((NoiseControl) it.next()).getName();
                BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
                if (Intrinsics.areEqual(name, bluetoothDeviceManager.getActiveNoiseControlTitle()) || Intrinsics.areEqual(name, bluetoothDeviceManager.getAmbientSoundTitle()) || Intrinsics.areEqual(name, bluetoothDeviceManager.getAdaptiveTitle())) {
                    z = true;
                }
            }
        }
        return z;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        ModelProvider modelProvider = this.modelProvider;
        boolean z = false;
        boolean z2 = modelProvider.effectOutDeviceType == EffectOutDeviceType.BUDS;
        this.isActionBarVisible.setValue(Boolean.valueOf(z2));
        this.isVolumeBarVisible.setValue(Boolean.valueOf(!modelProvider.isFromCover));
        this.isBatteryInfoBoxVisible.setValue(Boolean.valueOf(z2));
        MutableLiveData mutableLiveData = this.isNoiseControlBoxVisible;
        if (z2 && hasNoiseControl()) {
            z = true;
        }
        mutableLiveData.setValue(Boolean.valueOf(z));
        this.isFromNowBar.setValue(Boolean.valueOf(modelProvider.isFromNowBar));
        this.isFromCover.setValue(Boolean.valueOf(modelProvider.isFromCover));
        this.updateEffectBox.setValue(Boolean.TRUE);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("SoundCraft.SoundCraftViewModel", "onCleared");
        RoutineManager routineManager = this.routineManager;
        AutomationService service = routineManager.getService();
        Context context = routineManager.context;
        AutomationService.SystemRoutineType currentSystemRoutineType = routineManager.getCurrentSystemRoutineType();
        AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
        automationServiceImpl.getClass();
        currentSystemRoutineType.getClass();
        ContentHandlerImpl.AnonymousClass1 anonymousClass1 = ((ContentHandlerImpl) automationServiceImpl.contentHandler).contentObserver;
        if (anonymousClass1 != null) {
            context.getContentResolver().unregisterContentObserver(anonymousClass1);
        }
        BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
        if (bluetoothDeviceManager.isRegister) {
            try {
                bluetoothDeviceManager.context.unregisterReceiver(bluetoothDeviceManager.bluetoothMetadataBroadcastReceiver);
            } catch (IllegalArgumentException unused) {
            }
            bluetoothDeviceManager.isRegister = false;
        }
    }
}
