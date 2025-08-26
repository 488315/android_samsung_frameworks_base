package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.samsung.android.sdk.routines.automationservice.data.MetaInfo;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import com.samsung.android.sdk.routines.automationservice.interfaces.ChangeObserver;
import com.samsung.android.sdk.routines.automationservice.interfaces.ContentHandler;
import com.samsung.android.sdk.routines.automationservice.internal.AutomationServiceImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class RoutineManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final ModelProvider modelProvider;
    public final SoundCraftSettings settings;
    public final RoutineManager$changeObserver$1 changeObserver = new ChangeObserver() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$changeObserver$1
    };
    public final Lazy service$delegate = LazyKt__LazyJVMKt.lazy(new RoutineManager$$ExternalSyntheticLambda0());

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

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$changeObserver$1] */
    public RoutineManager(Context context, ModelProvider modelProvider, SoundCraftSettings soundCraftSettings) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.settings = soundCraftSettings;
    }

    public final void createRoutine(final EffectModel effectModel, final String str) {
        Function0 function0 = new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws PackageManager.NameNotFoundException, IOException {
                HashMap mapBuildBudsActions;
                String lastPathSegment;
                int i = RoutineManager.$r8$clinit;
                RoutineConditionBuilder routineConditionBuilder = RoutineConditionBuilder.INSTANCE;
                RoutineManager routineManager = this.f$0;
                Context context = routineManager.context;
                ModelProvider modelProvider = routineManager.modelProvider;
                boolean z = false;
                if (modelProvider.effectOutDeviceType == EffectOutDeviceType.BUDS) {
                    Boolean connectionState = modelProvider.budsModel.getConnectionState();
                    if (connectionState != null ? connectionState.booleanValue() : false) {
                        z = true;
                    }
                }
                routineConditionBuilder.getClass();
                String str2 = str;
                HashMap mapBuildConditions = RoutineConditionBuilder.buildConditions(context, str2, z);
                EffectOutDeviceType effectOutDeviceType = modelProvider.effectOutDeviceType;
                EffectOutDeviceType effectOutDeviceType2 = EffectOutDeviceType.PHONE;
                EffectModel effectModel2 = effectModel;
                if (effectOutDeviceType == effectOutDeviceType2) {
                    RoutineActionBuilder.INSTANCE.getClass();
                    mapBuildBudsActions = RoutineActionBuilder.buildPhoneActions(effectModel2);
                } else {
                    RoutineActionBuilder routineActionBuilder = RoutineActionBuilder.INSTANCE;
                    String str3 = routineManager.settings.budsPluginPackageName;
                    routineActionBuilder.getClass();
                    mapBuildBudsActions = RoutineActionBuilder.buildBudsActions(effectModel2, str3);
                }
                ArrayList arrayList = new ArrayList(mapBuildBudsActions.size());
                Iterator it = mapBuildBudsActions.entrySet().iterator();
                while (it.hasNext()) {
                    arrayList.add(((MetaInfo) ((Map.Entry) it.next()).getKey()).tag);
                }
                List list = CollectionsKt___CollectionsKt.toList(arrayList);
                List listFilterSupportedTags = ((AutomationServiceImpl) routineManager.getService()).filterSupportedTags(routineManager.context, list);
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : mapBuildBudsActions.entrySet()) {
                    if (((ArrayList) listFilterSupportedTags).contains(((MetaInfo) entry.getKey()).tag)) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                int size = list.size();
                int size2 = ((ArrayList) listFilterSupportedTags).size();
                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(size, "createRoutine : packageName=", str2, ", tag size=(", " -> ");
                sbM890m.append(size2);
                sbM890m.append("), tags=");
                sbM890m.append(list);
                sbM890m.append(", supportedTags=");
                sbM890m.append(listFilterSupportedTags);
                sbM890m.append(", filteredActions=");
                sbM890m.append(linkedHashMap);
                Log.d("SoundCraft.RoutineManager", sbM890m.toString());
                if (linkedHashMap.isEmpty()) {
                    Log.e("SoundCraft.RoutineManager", "createRoutine failed. action is none");
                } else {
                    AutomationService service = routineManager.getService();
                    Context context2 = routineManager.context;
                    AutomationService.SystemRoutineType currentSystemRoutineType = routineManager.getCurrentSystemRoutineType();
                    AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
                    automationServiceImpl.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log log = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                    String str4 = "createRoutine: name:" + str2 + " type:" + currentSystemRoutineType + ", conditions:" + mapBuildConditions.keySet() + ", actions:" + linkedHashMap.keySet();
                    log.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log.i("AutomationServiceImpl@SDK", str4);
                    if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, context2, currentSystemRoutineType)) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("condition_size", mapBuildConditions.size());
                        bundle.putInt("action_size", linkedHashMap.size());
                        Set setKeySet = mapBuildConditions.keySet();
                        ArrayList<String> arrayList2 = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(setKeySet, 10));
                        Iterator it2 = setKeySet.iterator();
                        while (it2.hasNext()) {
                            arrayList2.add(((MetaInfo) it2.next()).toString());
                        }
                        bundle.putStringArrayList("condition_keys", arrayList2);
                        Set setKeySet2 = linkedHashMap.keySet();
                        ArrayList<String> arrayList3 = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(setKeySet2, 10));
                        Iterator it3 = setKeySet2.iterator();
                        while (it3.hasNext()) {
                            arrayList3.add(((MetaInfo) it3.next()).toString());
                        }
                        bundle.putStringArrayList("action_keys", arrayList3);
                        bundle.putString("name", str2);
                        bundle.putString("type", currentSystemRoutineType.getValue());
                        ContentValues contentValues = new ContentValues();
                        for (MetaInfo metaInfo : mapBuildConditions.keySet()) {
                            contentValues.put(metaInfo.toString(), AutomationServiceImpl.createContentValue((ParameterValues) mapBuildConditions.get(metaInfo)));
                        }
                        for (MetaInfo metaInfo2 : linkedHashMap.keySet()) {
                            contentValues.put(metaInfo2.toString(), AutomationServiceImpl.createContentValue((ParameterValues) linkedHashMap.get(metaInfo2)));
                        }
                        String value = currentSystemRoutineType.getValue();
                        ((ContentHandlerImpl) automationServiceImpl.contentHandler).getClass();
                        Uri uriInsert = context2.getContentResolver().insert(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service"), contentValues, bundle);
                        if (uriInsert == null || (lastPathSegment = uriInsert.getLastPathSegment()) == null) {
                            lastPathSegment = "";
                        }
                        if (lastPathSegment.length() > 0) {
                            ContentHandlerImpl.notifyChange(context2, value, lastPathSegment);
                        }
                    }
                    modelProvider.appSettingModel.routineExistOnPlugin = true;
                }
                routineManager.getRoutineId(str2);
                return Unit.INSTANCE;
            }
        };
        RoutineUpdateThread.INSTANCE.getClass();
        ((Handler) RoutineUpdateThread.handler$delegate.getValue()).post(new RoutineUpdateThread$sam$java_lang_Runnable$0(function0));
    }

    public final AutomationService.SystemRoutineType getCurrentSystemRoutineType() {
        return this.modelProvider.effectOutDeviceType == EffectOutDeviceType.PHONE ? AutomationService.SystemRoutineType.SOUND_CRAFT_FOR_PHONE : AutomationService.SystemRoutineType.SOUND_CRAFT_FOR_BUDS;
    }

    public final String getRoutineId(String str) {
        List list;
        AutomationService service = getService();
        Context context = this.context;
        AutomationService.SystemRoutineType currentSystemRoutineType = getCurrentSystemRoutineType();
        AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
        automationServiceImpl.getClass();
        com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE.getClass();
        com.samsung.android.sdk.routines.automationservice.internal.Log.i("AutomationServiceImpl@SDK", "findRoutineIdsByMonitoredPackage: " + str + ", type:" + currentSystemRoutineType);
        if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, context, currentSystemRoutineType)) {
            ArrayList arrayList = new ArrayList();
            try {
                ContentHandler contentHandler = automationServiceImpl.contentHandler;
                String value = currentSystemRoutineType.getValue();
                ((ContentHandlerImpl) contentHandler).getClass();
                Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/monitor/" + str).buildUpon().appendQueryParameter("type", value).build(), null, null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.getCount() > 0 && cursorQuery.moveToFirst()) {
                            do {
                                int columnIndex = cursorQuery.getColumnIndex("uuid");
                                if (columnIndex != -1) {
                                    arrayList.add(cursorQuery.getString(columnIndex));
                                }
                            } while (cursorQuery.moveToNext());
                        }
                        Unit unit = Unit.INSTANCE;
                        cursorQuery.close();
                    } finally {
                    }
                }
            } catch (Exception e) {
                com.samsung.android.sdk.routines.automationservice.internal.Log log = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                String str2 = "getRoutineUuidByMonitoredPackageAsConditionParam: " + e.getMessage();
                log.getClass();
                com.samsung.android.sdk.routines.automationservice.internal.Log.e("AutomationServiceImpl@SDK", str2);
            }
            list = arrayList;
        } else {
            list = EmptyList.INSTANCE;
        }
        String str3 = (String) CollectionsKt___CollectionsKt.firstOrNull(list);
        Log.d("SoundCraft.RoutineManager", MotionLayout$$ExternalSyntheticOutline0.m("getRoutineId : packageName=", str, ", return id=", str3, " (sdk=v1.1.4)"));
        this.modelProvider.appSettingModel.routineId = str3;
        return str3;
    }

    public final AutomationService getService() {
        return (AutomationService) this.service$delegate.getValue();
    }

    public final void updateRoutine(final String str, final String str2, final EffectModel effectModel) {
        Function0 function0 = new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws PackageManager.NameNotFoundException, IOException {
                HashMap mapBuildBudsActions;
                RoutineManager routineManager = this.f$0;
                EffectOutDeviceType effectOutDeviceType = routineManager.modelProvider.effectOutDeviceType;
                EffectOutDeviceType effectOutDeviceType2 = EffectOutDeviceType.PHONE;
                EffectModel effectModel2 = effectModel;
                if (effectOutDeviceType == effectOutDeviceType2) {
                    RoutineActionBuilder.INSTANCE.getClass();
                    mapBuildBudsActions = RoutineActionBuilder.buildPhoneActions(effectModel2);
                } else {
                    RoutineActionBuilder routineActionBuilder = RoutineActionBuilder.INSTANCE;
                    String str3 = routineManager.settings.budsPluginPackageName;
                    routineActionBuilder.getClass();
                    mapBuildBudsActions = RoutineActionBuilder.buildBudsActions(effectModel2, str3);
                }
                RoutineConditionBuilder routineConditionBuilder = RoutineConditionBuilder.INSTANCE;
                Context context = routineManager.context;
                ModelProvider modelProvider = routineManager.modelProvider;
                boolean z = false;
                if (modelProvider.effectOutDeviceType == EffectOutDeviceType.BUDS) {
                    Boolean connectionState = modelProvider.budsModel.getConnectionState();
                    if (connectionState != null ? connectionState.booleanValue() : false) {
                        z = true;
                    }
                }
                routineConditionBuilder.getClass();
                String str4 = str;
                HashMap mapBuildConditions = RoutineConditionBuilder.buildConditions(context, str4, z);
                ArrayList arrayList = new ArrayList(mapBuildBudsActions.size());
                Iterator it = mapBuildBudsActions.entrySet().iterator();
                while (it.hasNext()) {
                    arrayList.add(((MetaInfo) ((Map.Entry) it.next()).getKey()).tag);
                }
                List listFilterSupportedTags = ((AutomationServiceImpl) routineManager.getService()).filterSupportedTags(routineManager.context, CollectionsKt___CollectionsKt.toList(arrayList));
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : mapBuildBudsActions.entrySet()) {
                    if (((ArrayList) listFilterSupportedTags).contains(((MetaInfo) entry.getKey()).tag)) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                boolean zIsEmpty = linkedHashMap.isEmpty();
                String str5 = str2;
                int iUpdate = -1;
                if (!zIsEmpty) {
                    AutomationService service = routineManager.getService();
                    Context context2 = routineManager.context;
                    AutomationService.SystemRoutineType currentSystemRoutineType = routineManager.getCurrentSystemRoutineType();
                    AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
                    automationServiceImpl.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log log = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                    StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("updateRoutineByRoutineId: routineId:", str5, ", conditions:");
                    sbM.append(mapBuildConditions.keySet());
                    sbM.append(", actions:");
                    sbM.append(linkedHashMap.keySet());
                    String string = sbM.toString();
                    log.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log.i("AutomationServiceImpl@SDK", string);
                    if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, context2, currentSystemRoutineType)) {
                        ContentValues contentValues = new ContentValues();
                        for (MetaInfo metaInfo : mapBuildConditions.keySet()) {
                            contentValues.put(metaInfo.toString(), AutomationServiceImpl.createContentValue((ParameterValues) mapBuildConditions.get(metaInfo)));
                        }
                        ContentValues contentValues2 = new ContentValues();
                        for (MetaInfo metaInfo2 : linkedHashMap.keySet()) {
                            contentValues2.put(metaInfo2.toString(), AutomationServiceImpl.createContentValue((ParameterValues) linkedHashMap.get(metaInfo2)));
                        }
                        String value = currentSystemRoutineType.getValue();
                        ((ContentHandlerImpl) automationServiceImpl.contentHandler).getClass();
                        iUpdate = context2.getContentResolver().update(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/action_status/".concat(str5)), contentValues2, null, null) + context2.getContentResolver().update(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/condition_status/".concat(str5)), contentValues, null, null);
                        if (iUpdate > 0) {
                            ContentHandlerImpl.notifyChange(context2, value, str5);
                        }
                    }
                }
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("updateRoutine : routineId=", str5, ", packageName=", str4, ", supportedTags=");
                sbM2.append(listFilterSupportedTags);
                sbM2.append(", actions=");
                sbM2.append(mapBuildBudsActions);
                sbM2.append(", updateRoutine result=");
                RecyclerView$$ExternalSyntheticOutline0.m(iUpdate, "SoundCraft.RoutineManager", sbM2);
                modelProvider.appSettingModel.routineExistOnPlugin = true;
                return Unit.INSTANCE;
            }
        };
        RoutineUpdateThread.INSTANCE.getClass();
        ((Handler) RoutineUpdateThread.handler$delegate.getValue()).post(new RoutineUpdateThread$sam$java_lang_Runnable$0(function0));
    }
}
