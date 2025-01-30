package com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionParamCreator;
import com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ActionType;
import com.android.systemui.qs.bar.soundcraft.interfaces.routine.extension.ConditionParamCreator;
import com.android.systemui.qs.bar.soundcraft.model.BudsInfo;
import com.android.systemui.qs.bar.soundcraft.model.Equalizer;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.routines.automationservice.AutomationServiceProvider;
import com.samsung.android.sdk.routines.automationservice.data.MetaInfo;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import com.samsung.android.sdk.routines.automationservice.interfaces.ContentHandler;
import com.samsung.android.sdk.routines.automationservice.internal.AutomationServiceImpl;
import com.samsung.android.sdk.routines.automationservice.internal.ContentHandlerImpl;
import com.samsung.android.sdk.routines.automationservice.internal.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.io.CloseableKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RoutineManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final Lazy service$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager.RoutineManager$service$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AutomationServiceProvider.INSTANCE.getClass();
            return new AutomationServiceImpl(new ContentHandlerImpl());
        }
    });

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

    public RoutineManager(Context context) {
        this.context = context;
    }

    public static final HashMap access$buildActions(RoutineManager routineManager, BudsInfo budsInfo) {
        routineManager.getClass();
        HashMap hashMap = new HashMap();
        Boolean spatialAudio = budsInfo.getSpatialAudio();
        if (spatialAudio != null) {
            boolean booleanValue = spatialAudio.booleanValue();
            ActionParamCreator actionParamCreator = ActionParamCreator.INSTANCE;
            ActionType actionType = ActionType.SPATIAL_AUDIO;
            String valueOf = String.valueOf(booleanValue);
            actionParamCreator.getClass();
            ActionParamCreator.putActionValue(hashMap, actionType, valueOf);
        }
        Boolean headTracking = budsInfo.getHeadTracking();
        if (headTracking != null) {
            boolean booleanValue2 = headTracking.booleanValue();
            ActionParamCreator actionParamCreator2 = ActionParamCreator.INSTANCE;
            ActionType actionType2 = ActionType.HEAD_TRACKING;
            String valueOf2 = String.valueOf(booleanValue2);
            actionParamCreator2.getClass();
            ActionParamCreator.putActionValue(hashMap, actionType2, valueOf2);
        }
        List<Equalizer> equalizerList = budsInfo.getEqualizerList();
        if (equalizerList != null) {
            for (Equalizer equalizer : equalizerList) {
                if (equalizer.getState()) {
                    ActionParamCreator actionParamCreator3 = ActionParamCreator.INSTANCE;
                    ActionType actionType3 = ActionType.EQUALIZER;
                    List equalizerList2 = budsInfo.getEqualizerList();
                    Intrinsics.checkNotNull(equalizerList2);
                    String valueOf3 = String.valueOf(equalizerList2.indexOf(equalizer));
                    actionParamCreator3.getClass();
                    ActionParamCreator.putActionValue(hashMap, actionType3, valueOf3);
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        Boolean voiceBoost = budsInfo.getVoiceBoost();
        if (voiceBoost != null) {
            boolean booleanValue3 = voiceBoost.booleanValue();
            ActionParamCreator actionParamCreator4 = ActionParamCreator.INSTANCE;
            ActionType actionType4 = ActionType.VOICE_BOOST;
            String valueOf4 = String.valueOf(booleanValue3);
            actionParamCreator4.getClass();
            ActionParamCreator.putActionValue(hashMap, actionType4, valueOf4);
        }
        Boolean volumeNormalization = budsInfo.getVolumeNormalization();
        if (volumeNormalization != null) {
            boolean booleanValue4 = volumeNormalization.booleanValue();
            ActionParamCreator actionParamCreator5 = ActionParamCreator.INSTANCE;
            ActionType actionType5 = ActionType.VOLUME_NORMALIZATION;
            String valueOf5 = String.valueOf(booleanValue4);
            actionParamCreator5.getClass();
            ActionParamCreator.putActionValue(hashMap, actionType5, valueOf5);
        }
        return hashMap;
    }

    public static final HashMap access$buildConditions(RoutineManager routineManager, String str) {
        routineManager.getClass();
        HashMap hashMap = new HashMap();
        ConditionParamCreator conditionParamCreator = ConditionParamCreator.INSTANCE;
        String valueOf = String.valueOf(routineManager.context.getPackageManager().getPackageUid(str, 1));
        conditionParamCreator.getClass();
        MetaInfo.Companion.getClass();
        MetaInfo metaInfo = new MetaInfo(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "playing_audio", null);
        ParameterValues.Companion.getClass();
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.put("playing_audio_app_uid", valueOf);
        parameterValues.put("playing_audio_app_package_name", str);
        Unit unit = Unit.INSTANCE;
        hashMap.put(metaInfo, parameterValues);
        return hashMap;
    }

    public final void createRoutine(final String str, final BudsInfo budsInfo) {
        Function0 function0 = new Function0() { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager.RoutineManager$createRoutine$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RoutineManager routineManager = RoutineManager.this;
                String str2 = str;
                HashMap access$buildConditions = RoutineManager.access$buildConditions(routineManager, str2);
                HashMap access$buildActions = RoutineManager.access$buildActions(RoutineManager.this, budsInfo);
                AbstractC0000x2c234b15.m3m("createRoutine : packageName=", str2, "SoundCraft.RoutineManager");
                AutomationService automationService = (AutomationService) routineManager.service$delegate.getValue();
                AutomationService.SystemRoutineType systemRoutineType = AutomationService.SystemRoutineType.SOUND_CRAFT;
                String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, " Preset");
                AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) automationService;
                automationServiceImpl.getClass();
                if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, systemRoutineType)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("condition_size", access$buildConditions.size());
                    bundle.putInt("action_size", access$buildActions.size());
                    Set keySet = access$buildConditions.keySet();
                    ArrayList<String> arrayList = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(keySet, 10));
                    Iterator it = keySet.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((MetaInfo) it.next()).toString());
                    }
                    bundle.putStringArrayList("condition_keys", arrayList);
                    Set keySet2 = access$buildActions.keySet();
                    ArrayList<String> arrayList2 = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(keySet2, 10));
                    Iterator it2 = keySet2.iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(((MetaInfo) it2.next()).toString());
                    }
                    bundle.putStringArrayList("action_keys", arrayList2);
                    bundle.putString("name", m14m);
                    bundle.putString("type", systemRoutineType.getValue());
                    ContentValues contentValues = new ContentValues();
                    for (MetaInfo metaInfo : access$buildConditions.keySet()) {
                        contentValues.put(metaInfo.toString(), AutomationServiceImpl.createContentValue((ParameterValues) access$buildConditions.get(metaInfo)));
                    }
                    for (MetaInfo metaInfo2 : access$buildActions.keySet()) {
                        contentValues.put(metaInfo2.toString(), AutomationServiceImpl.createContentValue((ParameterValues) access$buildActions.get(metaInfo2)));
                    }
                    ((ContentHandlerImpl) automationServiceImpl.contentHandler).getClass();
                    Uri insert = routineManager.context.getContentResolver().insert(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service"), contentValues, bundle);
                    if (insert != null) {
                        insert.getLastPathSegment();
                    }
                }
                return Unit.INSTANCE;
            }
        };
        RoutineUpdateThread.INSTANCE.getClass();
        ((Handler) RoutineUpdateThread.handler$delegate.getValue()).post(new RoutineUpdateThread$sam$java_lang_Runnable$0(function0));
    }

    public final String getRoutineId(String str) {
        List list;
        AutomationService automationService = (AutomationService) this.service$delegate.getValue();
        Context context = this.context;
        AutomationService.SystemRoutineType systemRoutineType = AutomationService.SystemRoutineType.SOUND_CRAFT;
        AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) automationService;
        automationServiceImpl.getClass();
        Log.INSTANCE.getClass();
        android.util.Log.i("Routine@AutomationService[1.0.1]: ".concat("AutomationServiceImpl@SDK"), "findRoutineIdsByMonitoredPackage: " + str + ", type:" + systemRoutineType);
        if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, systemRoutineType)) {
            ArrayList arrayList = new ArrayList();
            try {
                ContentHandler contentHandler = automationServiceImpl.contentHandler;
                String value = systemRoutineType.getValue();
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
                Log log = Log.INSTANCE;
                String str2 = "getRoutineUuidByMonitoredPackageAsConditionParam: " + e.getMessage();
                log.getClass();
                Log.m265e("AutomationServiceImpl@SDK", str2);
            }
            list = arrayList;
        } else {
            list = EmptyList.INSTANCE;
        }
        String str3 = (String) CollectionsKt___CollectionsKt.firstOrNull(list);
        android.util.Log.d("SoundCraft.RoutineManager", MotionLayout$$ExternalSyntheticOutline0.m22m("getRoutineId : packageName=", str, ", return id=", str3, " (sdk=1.0.1)"));
        return str3;
    }

    public final void updateRoutine(final String str, final String str2, final BudsInfo budsInfo) {
        Function0 function0 = new Function0() { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager.RoutineManager$updateRoutine$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int update;
                android.util.Log.d("SoundCraft.RoutineManager", "updateBudsInfo : routineId=" + str2 + ", budsInfo=" + budsInfo);
                RoutineManager routineManager = this;
                int i = RoutineManager.$r8$clinit;
                AutomationService automationService = (AutomationService) routineManager.service$delegate.getValue();
                RoutineManager routineManager2 = this;
                Context context = routineManager2.context;
                AutomationService.SystemRoutineType systemRoutineType = AutomationService.SystemRoutineType.SOUND_CRAFT;
                String str3 = str2;
                HashMap access$buildConditions = RoutineManager.access$buildConditions(routineManager2, str);
                HashMap access$buildActions = RoutineManager.access$buildActions(this, budsInfo);
                AutomationServiceImpl automationServiceImpl = (AutomationServiceImpl) automationService;
                automationServiceImpl.getClass();
                Log log = Log.INSTANCE;
                StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("updateRoutineByRoutineId: routineId:", str3, ", conditions:");
                m4m.append(access$buildConditions.keySet());
                m4m.append(", actions:");
                m4m.append(access$buildActions.keySet());
                String sb = m4m.toString();
                log.getClass();
                android.util.Log.i("Routine@AutomationService[1.0.1]: ".concat("AutomationServiceImpl@SDK"), sb);
                if (AutomationServiceImpl.Companion.access$isValidRequest(AutomationServiceImpl.Companion, systemRoutineType)) {
                    ContentValues contentValues = new ContentValues();
                    for (MetaInfo metaInfo : access$buildConditions.keySet()) {
                        contentValues.put(metaInfo.toString(), AutomationServiceImpl.createContentValue((ParameterValues) access$buildConditions.get(metaInfo)));
                    }
                    ((ContentHandlerImpl) automationServiceImpl.contentHandler).getClass();
                    int update2 = context.getContentResolver().update(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/condition_status/".concat(str3)), contentValues, null, null);
                    ContentValues contentValues2 = new ContentValues();
                    for (MetaInfo metaInfo2 : access$buildActions.keySet()) {
                        contentValues2.put(metaInfo2.toString(), AutomationServiceImpl.createContentValue((ParameterValues) access$buildActions.get(metaInfo2)));
                    }
                    update = update2 + context.getContentResolver().update(Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/action_status/".concat(str3)), contentValues2, null, null);
                } else {
                    update = -1;
                }
                android.util.Log.d("SoundCraft.RoutineManager", "updateBudsInfo : routineId=" + str2 + " update result=" + update);
                return Unit.INSTANCE;
            }
        };
        RoutineUpdateThread.INSTANCE.getClass();
        ((Handler) RoutineUpdateThread.handler$delegate.getValue()).post(new RoutineUpdateThread$sam$java_lang_Runnable$0(function0));
    }
}
