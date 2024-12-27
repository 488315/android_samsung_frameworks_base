package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.samsung.android.sdk.routines.automationservice.AutomationServiceProvider;
import com.samsung.android.sdk.routines.automationservice.data.MetaInfo;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import com.samsung.android.sdk.routines.automationservice.interfaces.ChangeObserver;
import com.samsung.android.sdk.routines.automationservice.interfaces.ContentHandler;
import com.samsung.android.sdk.routines.automationservice.internal.AutomationServiceImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl;
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
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class RoutineManager {
    public final Context context;
    public final ModelProvider modelProvider;
    public final SoundCraftSettings settings;
    public final RoutineManager$changeObserver$1 changeObserver = new ChangeObserver() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$changeObserver$1
    };
    public final Lazy service$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$service$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AutomationServiceProvider.INSTANCE.getClass();
            return new AutomationServiceImpl(new ContentHandlerImpl());
        }
    });

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

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$changeObserver$1] */
    public RoutineManager(Context context, ModelProvider modelProvider, SoundCraftSettings soundCraftSettings) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.settings = soundCraftSettings;
    }

    public final void createRoutine(final EffectModel effectModel, final String str) {
        Function0 function0 = new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$createRoutine$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                HashMap buildBudsActions;
                String str2;
                RoutineManager routineManager = RoutineManager.this;
                String str3 = str;
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
                HashMap buildConditions = RoutineConditionBuilder.buildConditions(context, str3, z);
                RoutineManager routineManager2 = RoutineManager.this;
                EffectModel effectModel2 = effectModel;
                if (routineManager2.modelProvider.effectOutDeviceType == EffectOutDeviceType.PHONE) {
                    RoutineActionBuilder.INSTANCE.getClass();
                    buildBudsActions = RoutineActionBuilder.buildPhoneActions(effectModel2);
                } else {
                    RoutineActionBuilder routineActionBuilder = RoutineActionBuilder.INSTANCE;
                    String str4 = routineManager2.settings.budsPluginPackageName;
                    routineActionBuilder.getClass();
                    buildBudsActions = RoutineActionBuilder.buildBudsActions(effectModel2, str4);
                }
                ArrayList arrayList = new ArrayList(buildBudsActions.size());
                Iterator it = buildBudsActions.entrySet().iterator();
                while (it.hasNext()) {
                    arrayList.add(((MetaInfo) ((Map.Entry) it.next()).getKey()).tag);
                }
                List list = CollectionsKt___CollectionsKt.toList(arrayList);
                List filterSupportedTags = ((AutomationServiceImpl) routineManager.getService()).filterSupportedTags(routineManager.context, list);
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : buildBudsActions.entrySet()) {
                    if (((ArrayList) filterSupportedTags).contains(((MetaInfo) entry.getKey()).tag)) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                int size = list.size();
                int size2 = ((ArrayList) filterSupportedTags).size();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(size, "createRoutine : packageName=", str3, ", tag size=(", " -> ");
                m.append(size2);
                m.append("), tags=");
                m.append(list);
                m.append(", supportedTags=");
                m.append(filterSupportedTags);
                m.append(", filteredActions=");
                m.append(linkedHashMap);
                Log.d("SoundCraft.RoutineManager", m.toString());
                if (linkedHashMap.isEmpty()) {
                    Log.e("SoundCraft.RoutineManager", "createRoutine failed. action is none");
                } else {
                    AutomationService service = routineManager.getService();
                    Context context2 = routineManager.context;
                    AutomationService.SystemRoutineType currentSystemRoutineType = routineManager.getCurrentSystemRoutineType();
                    AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
                    automationServiceImpl.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log log = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                    String str5 = "createRoutine: name:" + str3 + " type:" + currentSystemRoutineType + ", conditions:" + buildConditions.keySet() + ", actions:" + linkedHashMap.keySet();
                    log.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log.i("AutomationServiceImpl@SDK", str5);
                    if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, context2, currentSystemRoutineType)) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("condition_size", buildConditions.size());
                        bundle.putInt("action_size", linkedHashMap.size());
                        Set keySet = buildConditions.keySet();
                        ArrayList<String> arrayList2 = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(keySet, 10));
                        Iterator it2 = keySet.iterator();
                        while (it2.hasNext()) {
                            arrayList2.add(((MetaInfo) it2.next()).toString());
                        }
                        bundle.putStringArrayList("condition_keys", arrayList2);
                        Set keySet2 = linkedHashMap.keySet();
                        ArrayList<String> arrayList3 = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(keySet2, 10));
                        Iterator it3 = keySet2.iterator();
                        while (it3.hasNext()) {
                            arrayList3.add(((MetaInfo) it3.next()).toString());
                        }
                        bundle.putStringArrayList("action_keys", arrayList3);
                        bundle.putString("name", str3);
                        bundle.putString("type", currentSystemRoutineType.getValue());
                        ContentValues contentValues = new ContentValues();
                        for (MetaInfo metaInfo : buildConditions.keySet()) {
                            contentValues.put(metaInfo.toString(), AutomationServiceImpl.createContentValue((ParameterValues) buildConditions.get(metaInfo)));
                        }
                        for (MetaInfo metaInfo2 : linkedHashMap.keySet()) {
                            contentValues.put(metaInfo2.toString(), AutomationServiceImpl.createContentValue((ParameterValues) linkedHashMap.get(metaInfo2)));
                        }
                        String value = currentSystemRoutineType.getValue();
                        ((ContentHandlerImpl) automationServiceImpl.contentHandler).getClass();
                        Uri insert = context2.getContentResolver().insert(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service"), contentValues, bundle);
                        if (insert == null || (str2 = insert.getLastPathSegment()) == null) {
                            str2 = "";
                        }
                        if (str2.length() > 0) {
                            ContentHandlerImpl.notifyChange(context2, value, str2);
                        }
                    }
                    modelProvider.appSettingModel.routineExistOnPlugin = true;
                }
                RoutineManager.this.getRoutineId(str);
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
                Cursor query = context.getContentResolver().query(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/monitor/".concat(str)).buildUpon().appendQueryParameter("type", value).build(), null, null, null, null, null);
                if (query != null) {
                    try {
                        if (query.getCount() > 0 && query.moveToFirst()) {
                            do {
                                int columnIndex = query.getColumnIndex("uuid");
                                if (columnIndex != -1) {
                                    arrayList.add(query.getString(columnIndex));
                                }
                            } while (query.moveToNext());
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(query, null);
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
        Function0 function0 = new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager$updateRoutine$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                HashMap buildBudsActions;
                RoutineManager routineManager = RoutineManager.this;
                if (routineManager.modelProvider.effectOutDeviceType == EffectOutDeviceType.PHONE) {
                    RoutineActionBuilder routineActionBuilder = RoutineActionBuilder.INSTANCE;
                    EffectModel effectModel2 = effectModel;
                    routineActionBuilder.getClass();
                    buildBudsActions = RoutineActionBuilder.buildPhoneActions(effectModel2);
                } else {
                    RoutineActionBuilder routineActionBuilder2 = RoutineActionBuilder.INSTANCE;
                    EffectModel effectModel3 = effectModel;
                    String str3 = routineManager.settings.budsPluginPackageName;
                    routineActionBuilder2.getClass();
                    buildBudsActions = RoutineActionBuilder.buildBudsActions(effectModel3, str3);
                }
                RoutineConditionBuilder routineConditionBuilder = RoutineConditionBuilder.INSTANCE;
                RoutineManager routineManager2 = RoutineManager.this;
                Context context = routineManager2.context;
                String str4 = str;
                ModelProvider modelProvider = routineManager2.modelProvider;
                boolean z = false;
                if (modelProvider.effectOutDeviceType == EffectOutDeviceType.BUDS) {
                    Boolean connectionState = modelProvider.budsModel.getConnectionState();
                    if (connectionState != null ? connectionState.booleanValue() : false) {
                        z = true;
                    }
                }
                routineConditionBuilder.getClass();
                HashMap buildConditions = RoutineConditionBuilder.buildConditions(context, str4, z);
                ArrayList arrayList = new ArrayList(buildBudsActions.size());
                Iterator it = buildBudsActions.entrySet().iterator();
                while (it.hasNext()) {
                    arrayList.add(((MetaInfo) ((Map.Entry) it.next()).getKey()).tag);
                }
                List filterSupportedTags = ((AutomationServiceImpl) RoutineManager.this.getService()).filterSupportedTags(RoutineManager.this.context, CollectionsKt___CollectionsKt.toList(arrayList));
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : buildBudsActions.entrySet()) {
                    if (((ArrayList) filterSupportedTags).contains(((MetaInfo) entry.getKey()).tag)) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                int i = -1;
                if (!linkedHashMap.isEmpty()) {
                    AutomationService service = RoutineManager.this.getService();
                    RoutineManager routineManager3 = RoutineManager.this;
                    Context context2 = routineManager3.context;
                    AutomationService.SystemRoutineType currentSystemRoutineType = routineManager3.getCurrentSystemRoutineType();
                    String str5 = str2;
                    AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) service;
                    automationServiceImpl.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log log = com.samsung.android.sdk.routines.automationservice.internal.Log.INSTANCE;
                    StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("updateRoutineByRoutineId: routineId:", str5, ", conditions:");
                    m.append(buildConditions.keySet());
                    m.append(", actions:");
                    m.append(linkedHashMap.keySet());
                    String sb = m.toString();
                    log.getClass();
                    com.samsung.android.sdk.routines.automationservice.internal.Log.i("AutomationServiceImpl@SDK", sb);
                    if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, context2, currentSystemRoutineType)) {
                        ContentValues contentValues = new ContentValues();
                        for (MetaInfo metaInfo : buildConditions.keySet()) {
                            contentValues.put(metaInfo.toString(), AutomationServiceImpl.createContentValue((ParameterValues) buildConditions.get(metaInfo)));
                        }
                        ContentValues contentValues2 = new ContentValues();
                        for (MetaInfo metaInfo2 : linkedHashMap.keySet()) {
                            contentValues2.put(metaInfo2.toString(), AutomationServiceImpl.createContentValue((ParameterValues) linkedHashMap.get(metaInfo2)));
                        }
                        String value = currentSystemRoutineType.getValue();
                        ((ContentHandlerImpl) automationServiceImpl.contentHandler).getClass();
                        i = context2.getContentResolver().update(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/action_status/".concat(str5)), contentValues2, null, null) + context2.getContentResolver().update(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/condition_status/".concat(str5)), contentValues, null, null);
                        if (i > 0) {
                            ContentHandlerImpl.notifyChange(context2, value, str5);
                        }
                    }
                }
                StringBuilder m2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("updateRoutine : routineId=", str2, ", packageName=", str, ", supportedTags=");
                m2.append(filterSupportedTags);
                m2.append(", actions=");
                m2.append(buildBudsActions);
                m2.append(", updateRoutine result=");
                RecyclerView$$ExternalSyntheticOutline0.m(i, "SoundCraft.RoutineManager", m2);
                RoutineManager.this.modelProvider.appSettingModel.routineExistOnPlugin = true;
                return Unit.INSTANCE;
            }
        };
        RoutineUpdateThread.INSTANCE.getClass();
        ((Handler) RoutineUpdateThread.handler$delegate.getValue()).post(new RoutineUpdateThread$sam$java_lang_Runnable$0(function0));
    }
}
