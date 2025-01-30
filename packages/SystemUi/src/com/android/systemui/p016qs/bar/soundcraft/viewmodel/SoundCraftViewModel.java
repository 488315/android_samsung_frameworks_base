package com.android.systemui.p016qs.bar.soundcraft.viewmodel;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.p016qs.bar.soundcraft.feature.SoundCraftDebugFeatures;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.audio.AudioPlaybackManager;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension.ActionType;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension.RoutineDetailActionExtractor;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.BudsPluginInfo;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.requester.GetDummyInfoRequester;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.requester.GetInfoRequester;
import com.android.systemui.p016qs.bar.soundcraft.model.BudsInfo;
import com.android.systemui.p016qs.bar.soundcraft.model.Equalizer;
import com.android.systemui.p016qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.p016qs.bar.soundcraft.model.NoiseControl;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p055io.CloseableKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundCraftViewModel extends BaseViewModel {
    public final AudioPlaybackManager audioPlaybackManager;
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final WearableManager wearableManager;
    public final MutableLiveData showNoiseControlBox = new MutableLiveData();
    public final MutableLiveData showAudioEffectBox = new MutableLiveData();
    public final MutableLiveData showLoadingView = new MutableLiveData();
    public final MutableLiveData showDownloadGuideView = new MutableLiveData();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SoundCraftViewModel(Context context, WearableManager wearableManager, RoutineManager routineManager, BluetoothDeviceManager bluetoothDeviceManager, ModelProvider modelProvider, AudioPlaybackManager audioPlaybackManager) {
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.modelProvider = modelProvider;
        this.audioPlaybackManager = audioPlaybackManager;
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("init : hashCode=", hashCode(), "SoundCraft.SoundCraftViewModel");
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        ModelProvider modelProvider = this.modelProvider;
        Log.d("SoundCraft.SoundCraftViewModel", "notifyChange : budsInfo=" + modelProvider.budsInfo);
        this.showNoiseControlBox.setValue(Boolean.TRUE);
        MutableLiveData mutableLiveData = this.showAudioEffectBox;
        BudsInfo budsInfo = modelProvider.budsInfo;
        mutableLiveData.setValue(Boolean.valueOf((budsInfo.getEqualizerList() == null && budsInfo.getSpatialAudio() == null && budsInfo.getVoiceBoost() == null && budsInfo.getVolumeNormalization() == null) ? false : true));
        this.showLoadingView.setValue(Boolean.valueOf(modelProvider.budsInfo.getConnectionState() == null));
    }

    public final void onCreateView() {
        boolean z;
        ModelProvider modelProvider = this.modelProvider;
        SoundCraftSettings soundCraftSettings = modelProvider.settings;
        Context context = soundCraftSettings.context;
        soundCraftSettings.isAppSettingEnabled = Settings.System.getInt(context.getContentResolver(), "audio_soundcraft_app_setting", 0) == 1;
        soundCraftSettings.budsPluginPackageName = Settings.System.getString(context.getContentResolver(), "buds_plugin_package_name");
        final Function0 function0 = new Function0() { // from class: com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel$onCreateView$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModel.this.notifyChange();
                return Unit.INSTANCE;
            }
        };
        WearableManager wearableManager = this.wearableManager;
        wearableManager.getClass();
        SoundCraftDebugFeatures.INSTANCE.getClass();
        Context context2 = wearableManager.context;
        boolean z2 = Settings.System.getInt(context2.getContentResolver(), "audio_soundcraft_debug_dummy_buds_info", 0) == 1;
        SoundCraftSettings soundCraftSettings2 = wearableManager.soundCraftSettings;
        if (z2) {
            Log.d("SoundCraft.wearable.WearableManager", "isSupport : useDummyBudsInfo is true.");
            z = true;
        } else {
            String str = soundCraftSettings2.budsPluginPackageName;
            if (str.length() == 0) {
                Log.d("SoundCraft.wearable.WearableManager", "isSupport : packageName is empty. The device is not supported.");
                z = false;
            } else {
                PackageManager packageManager = context2.getPackageManager();
                Intent intent = new Intent("com.samsung.accessory.hearablemgr.BUDS_CONTROLLER");
                intent.setPackage(str);
                z = !packageManager.queryIntentServices(intent, 0).isEmpty();
                Log.d("SoundCraft.wearable.WearableManager", "isSupport : packageName=" + str + ", isServiceExist=" + z);
            }
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m59m("onCreateView : isBudsPluginSupport=", z, ", isAppSettingEnabled=", modelProvider.settings.isAppSettingEnabled, "SoundCraft.SoundCraftViewModel");
        if (z) {
            Function1 function1 = new Function1() { // from class: com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel$getBudsInfo$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Code restructure failed: missing block: B:35:0x03fa, code lost:
                
                    if (r3 == null) goto L187;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:59:0x0494, code lost:
                
                    if (r1 == null) goto L211;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:16:0x0344  */
                /* JADX WARN: Removed duplicated region for block: B:174:0x032f  */
                /* JADX WARN: Removed duplicated region for block: B:176:0x0332  */
                /* JADX WARN: Removed duplicated region for block: B:24:0x0393  */
                /* JADX WARN: Removed duplicated region for block: B:68:0x0515  */
                /* JADX WARN: Removed duplicated region for block: B:72:0x04ff  */
                /* JADX WARN: Removed duplicated region for block: B:73:0x036b  */
                /* JADX WARN: Removed duplicated region for block: B:84:0x01a8  */
                /* JADX WARN: Removed duplicated region for block: B:99:0x02fa  */
                /* JADX WARN: Type inference failed for: r10v10 */
                /* JADX WARN: Type inference failed for: r10v11 */
                /* JADX WARN: Type inference failed for: r10v12 */
                /* JADX WARN: Type inference failed for: r10v13 */
                /* JADX WARN: Type inference failed for: r10v14 */
                /* JADX WARN: Type inference failed for: r10v3, types: [java.lang.String] */
                /* JADX WARN: Type inference failed for: r10v4 */
                /* JADX WARN: Type inference failed for: r10v5 */
                /* JADX WARN: Type inference failed for: r10v7 */
                /* JADX WARN: Type inference failed for: r10v8 */
                /* JADX WARN: Type inference failed for: r10v9 */
                /* JADX WARN: Type inference failed for: r15v1, types: [android.content.ContentResolver] */
                /* JADX WARN: Type inference failed for: r16v10 */
                /* JADX WARN: Type inference failed for: r16v11 */
                /* JADX WARN: Type inference failed for: r16v12 */
                /* JADX WARN: Type inference failed for: r16v16 */
                /* JADX WARN: Type inference failed for: r16v2 */
                /* JADX WARN: Type inference failed for: r16v3 */
                /* JADX WARN: Type inference failed for: r16v6, types: [android.net.Uri] */
                /* JADX WARN: Type inference failed for: r16v7 */
                /* JADX WARN: Type inference failed for: r16v8 */
                /* JADX WARN: Type inference failed for: r16v9 */
                /* JADX WARN: Type inference failed for: r8v6 */
                /* JADX WARN: Type inference failed for: r8v7, types: [java.lang.String] */
                /* JADX WARN: Type inference failed for: r8v8 */
                /* JADX WARN: Type inference failed for: r8v9, types: [java.lang.String] */
                @Override // kotlin.jvm.functions.Function1
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj) {
                    SoundCraftViewModel soundCraftViewModel;
                    String str2;
                    BudsInfo budsInfo;
                    String str3;
                    boolean z3;
                    RoutineDetail routineDetail;
                    BudsInfo budsInfo2;
                    Boolean bool;
                    ArrayList arrayList;
                    Boolean bool2;
                    ?? r16;
                    String str4;
                    SoundCraftViewModel soundCraftViewModel2;
                    RoutineInfo routineInfo;
                    RoutineInfo routineInfo2;
                    boolean z4;
                    Cursor query;
                    SoundCraftViewModel soundCraftViewModel3;
                    Cursor query2;
                    SoundCraftViewModel soundCraftViewModel4;
                    SoundCraftViewModel soundCraftViewModel5;
                    AutomationService.SystemRoutineType systemRoutineType;
                    SoundCraftViewModel soundCraftViewModel6;
                    ?? r8;
                    BudsInfo budsInfo3 = (BudsInfo) obj;
                    SoundCraftViewModel.this.modelProvider.budsInfo = budsInfo3 == null ? new BudsInfo(null, null, null, null, null, null, null, null, null, null, null, null, 4095, null) : budsInfo3;
                    if (budsInfo3 != null) {
                        SoundCraftViewModel soundCraftViewModel7 = SoundCraftViewModel.this;
                        ModelProvider modelProvider2 = soundCraftViewModel7.modelProvider;
                        if (modelProvider2.settings.isAppSettingEnabled) {
                            modelProvider2.playingAudioPackageNameForAppSetting = soundCraftViewModel7.audioPlaybackManager.getPlayingAppPackage();
                            SoundCraftViewModel soundCraftViewModel8 = SoundCraftViewModel.this;
                            ModelProvider modelProvider3 = soundCraftViewModel8.modelProvider;
                            String str5 = modelProvider3.playingAudioPackageNameForAppSetting;
                            if (str5 != null) {
                                String str6 = modelProvider3.settings.budsPluginPackageName;
                                BudsInfo budsInfo4 = modelProvider3.budsInfo;
                                RoutineManager routineManager = soundCraftViewModel8.routineManager;
                                routineManager.getClass();
                                Log.d("SoundCraft.RoutineManager", "getBudsInfo : packageName=".concat(str5));
                                String routineId = routineManager.getRoutineId(str5);
                                if (routineId != null) {
                                    AutomationService automationService = (AutomationService) routineManager.service$delegate.getValue();
                                    Context context3 = routineManager.context;
                                    AutomationService.SystemRoutineType systemRoutineType2 = AutomationService.SystemRoutineType.SOUND_CRAFT;
                                    AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) automationService;
                                    automationServiceImpl.getClass();
                                    com.samsung.android.sdk.routines.automationservice.internal.Log log = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                    String concat = "getRoutineDetailByRoutineId: routineId:".concat(routineId);
                                    log.getClass();
                                    Log.i("Routine@AutomationService[1.0.1]: ".concat("AutomationServiceImpl@SDK"), concat);
                                    if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, systemRoutineType2)) {
                                        ?? r10 = "enabled";
                                        ContentHandler contentHandler = automationServiceImpl.contentHandler;
                                        try {
                                            ((ContentHandlerImpl) contentHandler).getClass();
                                            ?? contentResolver = context3.getContentResolver();
                                            r16 = Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/routine_info/routine/".concat(routineId));
                                            str2 = null;
                                            budsInfo = null;
                                            str4 = null;
                                            query2 = contentResolver.query(r16, null, null, null, null, null);
                                        } catch (Exception e) {
                                            e = e;
                                            r16 = soundCraftViewModel8;
                                            str2 = str6;
                                            budsInfo = budsInfo4;
                                            obj = "SoundCraft.RoutineManager";
                                            str4 = "Routine@AutomationService[1.0.1]: ";
                                        }
                                        try {
                                        } catch (Exception e2) {
                                            e = e2;
                                            com.samsung.android.sdk.routines.automationservice.internal.Log log2 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                            String str7 = "queryRoutineInfo: " + e.getMessage();
                                            log2.getClass();
                                            com.samsung.android.sdk.routines.automationservice.internal.Log.m265e("AutomationServiceImpl@SDK", str7);
                                            soundCraftViewModel3 = r16;
                                            routineInfo = null;
                                            soundCraftViewModel2 = soundCraftViewModel3;
                                            routineInfo2 = routineInfo;
                                            if (routineInfo2 == null) {
                                            }
                                            com.samsung.android.sdk.routines.automationservice.internal.Log log3 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                            StringBuilder sb = new StringBuilder("getRoutineDetailByRoutineUuid() routineInfo.type: ");
                                            sb.append(routineInfo2 != null ? routineInfo2.type : null);
                                            String sb2 = sb.toString();
                                            log3.getClass();
                                            com.samsung.android.sdk.routines.automationservice.internal.Log.m265e("AutomationServiceImpl@SDK", sb2);
                                            z3 = true;
                                            routineDetail = null;
                                            soundCraftViewModel6 = soundCraftViewModel2;
                                            if (routineDetail != null) {
                                            }
                                            Log.d(r8, "getRoutineDetailById : routineId=" + routineId + ", return RoutineDetail=" + routineDetail);
                                            str3 = r8;
                                            soundCraftViewModel = soundCraftViewModel6;
                                            if (routineDetail != null) {
                                            }
                                            Log.d("SoundCraft.SoundCraftViewModel", "onCreateView : routineDetail -> budsInfo=" + budsInfo2);
                                            if (budsInfo2 != null) {
                                            }
                                            function0.invoke();
                                            return Unit.INSTANCE;
                                        }
                                        if (query2 != null) {
                                            try {
                                                if (query2.getCount() > 0 && query2.moveToFirst()) {
                                                    RoutineInfo.Companion companion = RoutineInfo.Companion;
                                                    String string = query2.getString(query2.getColumnIndex("name"));
                                                    if (string != null) {
                                                        r16 = soundCraftViewModel8;
                                                        try {
                                                            String string2 = query2.getString(query2.getColumnIndex("uuid"));
                                                            if (string2 == null) {
                                                                soundCraftViewModel4 = r16;
                                                                str2 = str6;
                                                                budsInfo = budsInfo4;
                                                                obj = "SoundCraft.RoutineManager";
                                                                str4 = "Routine@AutomationService[1.0.1]: ";
                                                                soundCraftViewModel5 = soundCraftViewModel4;
                                                                Unit unit = Unit.INSTANCE;
                                                                CloseableKt.closeFinally(query2, null);
                                                                soundCraftViewModel3 = soundCraftViewModel5;
                                                            } else {
                                                                AutomationService.SystemRoutineType.Companion companion2 = AutomationService.SystemRoutineType.Companion;
                                                                budsInfo = budsInfo4;
                                                                try {
                                                                    String string3 = query2.getString(query2.getColumnIndex("type"));
                                                                    companion2.getClass();
                                                                    str2 = str6;
                                                                    try {
                                                                        AutomationService.SystemRoutineType[] values = AutomationService.SystemRoutineType.values();
                                                                        obj = "SoundCraft.RoutineManager";
                                                                        try {
                                                                            int length = values.length;
                                                                            str4 = "Routine@AutomationService[1.0.1]: ";
                                                                            int i = 0;
                                                                            while (true) {
                                                                                if (i >= length) {
                                                                                    systemRoutineType = null;
                                                                                    break;
                                                                                }
                                                                                try {
                                                                                    systemRoutineType = values[i];
                                                                                    AutomationService.SystemRoutineType[] systemRoutineTypeArr = values;
                                                                                    if (Intrinsics.areEqual(systemRoutineType.getValue(), string3)) {
                                                                                        break;
                                                                                    }
                                                                                    i++;
                                                                                    values = systemRoutineTypeArr;
                                                                                } catch (Throwable th) {
                                                                                    th = th;
                                                                                    try {
                                                                                        throw th;
                                                                                    } finally {
                                                                                    }
                                                                                }
                                                                            }
                                                                            AutomationService.SystemRoutineType systemRoutineType3 = systemRoutineType;
                                                                            if (systemRoutineType3 == null) {
                                                                                soundCraftViewModel5 = r16;
                                                                                Unit unit2 = Unit.INSTANCE;
                                                                                CloseableKt.closeFinally(query2, null);
                                                                                soundCraftViewModel3 = soundCraftViewModel5;
                                                                            } else {
                                                                                companion.getClass();
                                                                                routineInfo = new RoutineInfo(string, string2, systemRoutineType3, null);
                                                                                CloseableKt.closeFinally(query2, null);
                                                                                soundCraftViewModel2 = r16;
                                                                                routineInfo2 = routineInfo;
                                                                                if (routineInfo2 == null && routineInfo2.type == systemRoutineType2) {
                                                                                    ArrayList arrayList2 = new ArrayList();
                                                                                    try {
                                                                                        ((ContentHandlerImpl) contentHandler).getClass();
                                                                                        Cursor query3 = context3.getContentResolver().query(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/condition_status/".concat(routineId)), null, null, null, null, null);
                                                                                        if (query3 != null) {
                                                                                            try {
                                                                                                if (query3.getCount() > 0 && query3.moveToFirst()) {
                                                                                                    int columnIndex = query3.getColumnIndex("instance_id");
                                                                                                    int columnIndex2 = query3.getColumnIndex("tag");
                                                                                                    int columnIndex3 = query3.getColumnIndex("enabled");
                                                                                                    do {
                                                                                                        ConditionStatus.Companion companion3 = ConditionStatus.Companion;
                                                                                                        long j = query3.getLong(columnIndex);
                                                                                                        boolean z5 = query3.getInt(columnIndex3) == 1;
                                                                                                        String string4 = query3.getString(columnIndex2);
                                                                                                        ParameterValues parameterValues = AutomationServiceImpl.getParameterValues(query3);
                                                                                                        companion3.getClass();
                                                                                                        arrayList2.add(new ConditionStatus(j, z5, string4, parameterValues, null));
                                                                                                    } while (query3.moveToNext());
                                                                                                }
                                                                                                Unit unit3 = Unit.INSTANCE;
                                                                                                CloseableKt.closeFinally(query3, null);
                                                                                            } finally {
                                                                                            }
                                                                                        }
                                                                                    } catch (Exception e3) {
                                                                                        com.samsung.android.sdk.routines.automationservice.internal.Log log4 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                                                                        String str8 = "queryConditionStatus: " + e3.getMessage();
                                                                                        log4.getClass();
                                                                                        com.samsung.android.sdk.routines.automationservice.internal.Log.m265e("AutomationServiceImpl@SDK", str8);
                                                                                    }
                                                                                    ArrayList arrayList3 = new ArrayList();
                                                                                    try {
                                                                                        ((ContentHandlerImpl) contentHandler).getClass();
                                                                                        query = context3.getContentResolver().query(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/action_status/".concat(routineId)), null, null, null, null, null);
                                                                                    } catch (Exception e4) {
                                                                                        e = e4;
                                                                                        r10 = 1;
                                                                                    }
                                                                                    try {
                                                                                    } catch (Exception e5) {
                                                                                        e = e5;
                                                                                        com.samsung.android.sdk.routines.automationservice.internal.Log log5 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                                                                        String str9 = "queryActionStatus: " + e.getMessage();
                                                                                        log5.getClass();
                                                                                        com.samsung.android.sdk.routines.automationservice.internal.Log.m265e("AutomationServiceImpl@SDK", str9);
                                                                                        z4 = r10;
                                                                                        z3 = z4;
                                                                                        if (!arrayList2.isEmpty()) {
                                                                                        }
                                                                                        routineDetail = null;
                                                                                        soundCraftViewModel6 = soundCraftViewModel2;
                                                                                        if (routineDetail != null) {
                                                                                        }
                                                                                        Log.d(r8, "getRoutineDetailById : routineId=" + routineId + ", return RoutineDetail=" + routineDetail);
                                                                                        str3 = r8;
                                                                                        soundCraftViewModel = soundCraftViewModel6;
                                                                                        if (routineDetail != null) {
                                                                                        }
                                                                                        Log.d("SoundCraft.SoundCraftViewModel", "onCreateView : routineDetail -> budsInfo=" + budsInfo2);
                                                                                        if (budsInfo2 != null) {
                                                                                        }
                                                                                        function0.invoke();
                                                                                        return Unit.INSTANCE;
                                                                                    }
                                                                                    if (query != null) {
                                                                                        try {
                                                                                            if (query.getCount() <= 0 || !query.moveToFirst()) {
                                                                                                r10 = 1;
                                                                                            } else {
                                                                                                int columnIndex4 = query.getColumnIndex("instance_id");
                                                                                                int columnIndex5 = query.getColumnIndex("tag");
                                                                                                int columnIndex6 = query.getColumnIndex("enabled");
                                                                                                do {
                                                                                                    ActionStatus.Companion companion4 = ActionStatus.Companion;
                                                                                                    long j2 = query.getLong(columnIndex4);
                                                                                                    r10 = 1;
                                                                                                    r10 = 1;
                                                                                                    boolean z6 = query.getInt(columnIndex6) == 1;
                                                                                                    try {
                                                                                                        String string5 = query.getString(columnIndex5);
                                                                                                        ParameterValues parameterValues2 = AutomationServiceImpl.getParameterValues(query);
                                                                                                        companion4.getClass();
                                                                                                        arrayList3.add(new ActionStatus(j2, z6, string5, parameterValues2, null));
                                                                                                    } catch (Throwable th2) {
                                                                                                        th = th2;
                                                                                                        try {
                                                                                                            throw th;
                                                                                                        } finally {
                                                                                                        }
                                                                                                    }
                                                                                                } while (query.moveToNext());
                                                                                            }
                                                                                            Unit unit4 = Unit.INSTANCE;
                                                                                            CloseableKt.closeFinally(query, null);
                                                                                            z4 = r10;
                                                                                            z3 = z4;
                                                                                            if (!arrayList2.isEmpty() && !arrayList3.isEmpty()) {
                                                                                                routineDetail = new RoutineDetail(routineInfo2, arrayList2, arrayList3);
                                                                                                com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE.getClass();
                                                                                                Log.i(str4.concat("AutomationServiceImpl@SDK"), "getRoutineDetailByRoutineUuid: " + routineDetail);
                                                                                                soundCraftViewModel6 = soundCraftViewModel2;
                                                                                            }
                                                                                        } catch (Throwable th3) {
                                                                                            th = th3;
                                                                                            r10 = 1;
                                                                                        }
                                                                                    } else {
                                                                                        z3 = true;
                                                                                        if (!arrayList2.isEmpty()) {
                                                                                            routineDetail = new RoutineDetail(routineInfo2, arrayList2, arrayList3);
                                                                                            com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE.getClass();
                                                                                            Log.i(str4.concat("AutomationServiceImpl@SDK"), "getRoutineDetailByRoutineUuid: " + routineDetail);
                                                                                            soundCraftViewModel6 = soundCraftViewModel2;
                                                                                        }
                                                                                    }
                                                                                } else {
                                                                                    com.samsung.android.sdk.routines.automationservice.internal.Log log32 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                                                                    StringBuilder sb3 = new StringBuilder("getRoutineDetailByRoutineUuid() routineInfo.type: ");
                                                                                    sb3.append(routineInfo2 != null ? routineInfo2.type : null);
                                                                                    String sb22 = sb3.toString();
                                                                                    log32.getClass();
                                                                                    com.samsung.android.sdk.routines.automationservice.internal.Log.m265e("AutomationServiceImpl@SDK", sb22);
                                                                                    z3 = true;
                                                                                }
                                                                                routineDetail = null;
                                                                                soundCraftViewModel6 = soundCraftViewModel2;
                                                                            }
                                                                        } catch (Throwable th4) {
                                                                            th = th4;
                                                                            str4 = "Routine@AutomationService[1.0.1]: ";
                                                                            throw th;
                                                                        }
                                                                    } catch (Throwable th5) {
                                                                        th = th5;
                                                                        obj = "SoundCraft.RoutineManager";
                                                                        str4 = "Routine@AutomationService[1.0.1]: ";
                                                                        throw th;
                                                                    }
                                                                } catch (Throwable th6) {
                                                                    th = th6;
                                                                    str2 = str6;
                                                                }
                                                            }
                                                        } catch (Throwable th7) {
                                                            th = th7;
                                                            str2 = str6;
                                                            budsInfo = budsInfo4;
                                                            obj = "SoundCraft.RoutineManager";
                                                            str4 = "Routine@AutomationService[1.0.1]: ";
                                                            throw th;
                                                        }
                                                    }
                                                }
                                                soundCraftViewModel4 = soundCraftViewModel8;
                                                str2 = str6;
                                                budsInfo = budsInfo4;
                                                obj = "SoundCraft.RoutineManager";
                                                str4 = "Routine@AutomationService[1.0.1]: ";
                                                soundCraftViewModel5 = soundCraftViewModel4;
                                                Unit unit22 = Unit.INSTANCE;
                                                CloseableKt.closeFinally(query2, null);
                                                soundCraftViewModel3 = soundCraftViewModel5;
                                            } catch (Throwable th8) {
                                                th = th8;
                                                r16 = soundCraftViewModel8;
                                            }
                                        } else {
                                            soundCraftViewModel3 = soundCraftViewModel8;
                                            str2 = str6;
                                            budsInfo = budsInfo4;
                                            obj = "SoundCraft.RoutineManager";
                                            str4 = "Routine@AutomationService[1.0.1]: ";
                                        }
                                        routineInfo = null;
                                        soundCraftViewModel2 = soundCraftViewModel3;
                                        routineInfo2 = routineInfo;
                                        if (routineInfo2 == null) {
                                        }
                                        com.samsung.android.sdk.routines.automationservice.internal.Log log322 = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                                        StringBuilder sb32 = new StringBuilder("getRoutineDetailByRoutineUuid() routineInfo.type: ");
                                        sb32.append(routineInfo2 != null ? routineInfo2.type : null);
                                        String sb222 = sb32.toString();
                                        log322.getClass();
                                        com.samsung.android.sdk.routines.automationservice.internal.Log.m265e("AutomationServiceImpl@SDK", sb222);
                                        z3 = true;
                                        routineDetail = null;
                                        soundCraftViewModel6 = soundCraftViewModel2;
                                    } else {
                                        routineDetail = null;
                                        z3 = true;
                                        soundCraftViewModel6 = soundCraftViewModel8;
                                        str2 = str6;
                                        budsInfo = budsInfo4;
                                        obj = "SoundCraft.RoutineManager";
                                    }
                                    if (routineDetail != null) {
                                        for (ActionStatus actionStatus : routineDetail.actions) {
                                            CustomizationProvider$$ExternalSyntheticOutline0.m135m("- getRoutineDetailById : action: ", actionStatus.tag, " - ", actionStatus.parameterValues.toJsonString(), obj);
                                        }
                                        r8 = obj;
                                    } else {
                                        r8 = obj;
                                        routineDetail = null;
                                    }
                                    Log.d(r8, "getRoutineDetailById : routineId=" + routineId + ", return RoutineDetail=" + routineDetail);
                                    str3 = r8;
                                    soundCraftViewModel = soundCraftViewModel6;
                                } else {
                                    soundCraftViewModel = soundCraftViewModel8;
                                    str2 = str6;
                                    budsInfo = budsInfo4;
                                    str3 = "SoundCraft.RoutineManager";
                                    z3 = true;
                                    routineDetail = null;
                                }
                                if (routineDetail != null) {
                                    RoutineDetailActionExtractor routineDetailActionExtractor = RoutineDetailActionExtractor.INSTANCE;
                                    ActionType actionType = ActionType.EQUALIZER;
                                    Integer num = 0;
                                    BudsPluginInfo.Companion.getClass();
                                    String findProjectName = BudsPluginInfo.Companion.findProjectName(str2);
                                    if (findProjectName != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor2 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag = actionType.getTag(findProjectName);
                                        String valueOf = String.valueOf(num);
                                        routineDetailActionExtractor2.getClass();
                                        String actionValue = RoutineDetailActionExtractor.getActionValue(routineDetail, tag, valueOf);
                                        Integer valueOf2 = num instanceof Boolean ? (Integer) Boolean.valueOf(Boolean.parseBoolean(actionValue)) : Integer.valueOf(Integer.parseInt(actionValue));
                                        if (valueOf2 != null) {
                                            num = valueOf2;
                                        }
                                    }
                                    int intValue = num.intValue();
                                    ActionType actionType2 = ActionType.SPATIAL_AUDIO;
                                    Boolean bool3 = Boolean.FALSE;
                                    String findProjectName2 = BudsPluginInfo.Companion.findProjectName(str2);
                                    if (findProjectName2 != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor3 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag2 = actionType2.getTag(findProjectName2);
                                        String valueOf3 = String.valueOf(bool3);
                                        routineDetailActionExtractor3.getClass();
                                        bool = Boolean.valueOf(Boolean.parseBoolean(RoutineDetailActionExtractor.getActionValue(routineDetail, tag2, valueOf3)));
                                    }
                                    bool = bool3;
                                    boolean booleanValue = bool.booleanValue();
                                    ActionType actionType3 = ActionType.HEAD_TRACKING;
                                    String findProjectName3 = BudsPluginInfo.Companion.findProjectName(str2);
                                    if (findProjectName3 != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor4 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag3 = actionType3.getTag(findProjectName3);
                                        String valueOf4 = String.valueOf(bool3);
                                        routineDetailActionExtractor4.getClass();
                                        Boolean valueOf5 = Boolean.valueOf(Boolean.parseBoolean(RoutineDetailActionExtractor.getActionValue(routineDetail, tag3, valueOf4)));
                                        if (valueOf5 != null) {
                                            bool3 = valueOf5;
                                        }
                                    }
                                    boolean booleanValue2 = bool3.booleanValue();
                                    List<Equalizer> equalizerList = budsInfo.getEqualizerList();
                                    if (equalizerList != null) {
                                        arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(equalizerList, 10));
                                        for (Equalizer equalizer : equalizerList) {
                                            String name = equalizer.getName();
                                            List equalizerList2 = budsInfo.getEqualizerList();
                                            arrayList.add(new Equalizer(name, (equalizerList2 == null || equalizerList2.indexOf(equalizer) != intValue) ? false : z3));
                                        }
                                    } else {
                                        arrayList = null;
                                    }
                                    ArrayList arrayList4 = arrayList;
                                    RoutineDetailActionExtractor routineDetailActionExtractor5 = RoutineDetailActionExtractor.INSTANCE;
                                    ActionType actionType4 = ActionType.VOICE_BOOST;
                                    Boolean bool4 = Boolean.FALSE;
                                    BudsPluginInfo.Companion.getClass();
                                    String findProjectName4 = BudsPluginInfo.Companion.findProjectName(str2);
                                    if (findProjectName4 != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor6 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag4 = actionType4.getTag(findProjectName4);
                                        String valueOf6 = String.valueOf(bool4);
                                        routineDetailActionExtractor6.getClass();
                                        bool2 = Boolean.valueOf(Boolean.parseBoolean(RoutineDetailActionExtractor.getActionValue(routineDetail, tag4, valueOf6)));
                                    }
                                    bool2 = bool4;
                                    boolean booleanValue3 = bool2.booleanValue();
                                    ActionType actionType5 = ActionType.VOLUME_NORMALIZATION;
                                    String findProjectName5 = BudsPluginInfo.Companion.findProjectName(str2);
                                    if (findProjectName5 != null) {
                                        RoutineDetailActionExtractor routineDetailActionExtractor7 = RoutineDetailActionExtractor.INSTANCE;
                                        String tag5 = actionType5.getTag(findProjectName5);
                                        String valueOf7 = String.valueOf(bool4);
                                        routineDetailActionExtractor7.getClass();
                                        Boolean valueOf8 = Boolean.valueOf(Boolean.parseBoolean(RoutineDetailActionExtractor.getActionValue(routineDetail, tag5, valueOf7)));
                                        if (valueOf8 != null) {
                                            bool4 = valueOf8;
                                        }
                                    }
                                    budsInfo2 = new BudsInfo(null, arrayList4, null, null, null, null, null, null, Boolean.valueOf(booleanValue), Boolean.valueOf(booleanValue2), Boolean.valueOf(booleanValue3), Boolean.valueOf(bool4.booleanValue()), IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList, null);
                                    Log.d(str3, "getBudsInfo : return budsInfo=" + budsInfo2);
                                } else {
                                    budsInfo2 = null;
                                }
                                Log.d("SoundCraft.SoundCraftViewModel", "onCreateView : routineDetail -> budsInfo=" + budsInfo2);
                                if (budsInfo2 != null) {
                                    soundCraftViewModel.modelProvider.budsInfo = budsInfo2;
                                }
                            }
                        } else {
                            modelProvider2.playingAudioPackageNameForAppSetting = null;
                        }
                        function0.invoke();
                    }
                    return Unit.INSTANCE;
                }
            };
            Log.d("SoundCraft.wearable.WearableManager", "getBudsInfo");
            if (Settings.System.getInt(context2.getContentResolver(), "audio_soundcraft_debug_dummy_buds_info", 0) == 1) {
                new GetDummyInfoRequester();
                List listOf = CollectionsKt__CollectionsKt.listOf(new Equalizer("Balanced", true), new Equalizer("Bass boost", false), new Equalizer("Smooth", false), new Equalizer("Dynamic", false), new Equalizer("Clear", false), new Equalizer("Treble boost", false), new Equalizer("Custom", false));
                Boolean bool = Boolean.TRUE;
                Boolean bool2 = Boolean.FALSE;
                function1.invoke(new BudsInfo(null, listOf, null, null, null, null, null, null, bool, bool2, bool, bool2, IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList, null));
            } else {
                new GetInfoRequester(context2, soundCraftSettings2.budsPluginPackageName, function1).bindService();
            }
        } else {
            function0.invoke();
        }
        BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
        bluetoothDeviceManager.getClass();
        Log.d("SoundCraft.BluetoothDeviceManager", "init()");
        BluetoothDevice activeDevice = bluetoothDeviceManager.getActiveDevice();
        Function1 function12 = bluetoothDeviceManager.callback;
        if (function12 != null) {
            BluetoothDevice activeDevice2 = bluetoothDeviceManager.getActiveDevice();
            function12.invoke(activeDevice2 != null ? bluetoothDeviceManager.getNoiseControlList(activeDevice2) : new LinkedHashSet());
        }
        if (activeDevice == null) {
            Log.e("SoundCraft.BluetoothDeviceManager", "connected device is empty");
        }
        bluetoothDeviceManager.context.registerReceiver(bluetoothDeviceManager.bluetoothMetadataBroadcastReceiver, new IntentFilter("com.samsung.bluetooth.device.action.META_DATA_CHANGED"));
        Function1 function13 = new Function1() { // from class: com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel$getNoiseControlInfo$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Set set = (Set) obj;
                final Set noiseControlsList = SoundCraftViewModel.this.modelProvider.budsInfo.getNoiseControlsList();
                if (noiseControlsList != null) {
                    set.stream().forEach(new Consumer() { // from class: com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel$getNoiseControlInfo$1$1$1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj2) {
                            final NoiseControl noiseControl = (NoiseControl) obj2;
                            noiseControlsList.removeIf(new Predicate() { // from class: com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel$getNoiseControlInfo$1$1$1.1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj3) {
                                    return Intrinsics.areEqual(((NoiseControl) obj3).getName(), NoiseControl.this.getName());
                                }
                            });
                        }
                    });
                    noiseControlsList.addAll(set);
                } else {
                    SoundCraftViewModel.this.modelProvider.budsInfo.setNoiseControlsList(set);
                }
                Log.d("SoundCraft.SoundCraftViewModel", "noiseControl state changed " + SoundCraftViewModel.this.modelProvider.budsInfo.getNoiseControlsList());
                SoundCraftViewModel.this.notifyChange();
                return Unit.INSTANCE;
            }
        };
        bluetoothDeviceManager.callback = function13;
        BluetoothDevice activeDevice3 = bluetoothDeviceManager.getActiveDevice();
        function13.invoke(activeDevice3 != null ? bluetoothDeviceManager.getNoiseControlList(activeDevice3) : new LinkedHashSet());
    }
}
