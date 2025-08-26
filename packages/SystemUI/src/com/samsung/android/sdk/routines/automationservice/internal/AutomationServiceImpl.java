package com.samsung.android.sdk.routines.automationservice.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.google.gson.Gson;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import com.samsung.android.sdk.routines.automationservice.interfaces.ContentHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt___StringsKt;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class AutomationServiceImpl implements AutomationService {
    public static final Companion Companion = new Companion(null);
    public static final Map allowedPackageNamesByType = MapsKt__MapsKt.mapOf(new Pair(AutomationService.SystemRoutineType.SOUND_CRAFT_FOR_PHONE, ArraysKt___ArraysKt.toSet(new String[]{"com.sec.android.app.soundalive", "com.android.systemui", "com.samsung.android.app.routines"})), new Pair(AutomationService.SystemRoutineType.SOUND_CRAFT_FOR_BUDS, ArraysKt___ArraysKt.toSet(new String[]{"com.sec.android.app.soundalive", "com.android.systemui", "com.samsung.android.app.routines"})), new Pair(AutomationService.SystemRoutineType.GAME_CRAFT, ArraysKt___ArraysKt.toSet(new String[]{"com.samsung.android.game.gos", "com.samsung.android.app.routines"})));
    public final ContentHandler contentHandler;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final boolean access$isValidRequest(Companion companion, Context context, AutomationService.SystemRoutineType systemRoutineType) {
            companion.getClass();
            if (systemRoutineType.getValue().startsWith("system")) {
                String packageName = context.getApplicationContext().getPackageName();
                Set set = (Set) AutomationServiceImpl.allowedPackageNamesByType.get(systemRoutineType);
                boolean z = set != null && set.contains(packageName);
                if (systemRoutineType == AutomationService.SystemRoutineType.SOUND_CRAFT_FOR_BUDS) {
                    z = z || (packageName.startsWith("com.samsung.accessory") && Intrinsics.areEqual(StringsKt___StringsKt.takeLast(3, packageName), "mgr"));
                }
                if (!z) {
                    Log.INSTANCE.getClass();
                    Log.e("AutomationServiceImpl@SDK", "isMatchingPackageName: invalid Request packageName:" + packageName + ", type:" + systemRoutineType);
                }
                if (z) {
                    return true;
                }
            }
            return false;
        }

        private Companion() {
        }
    }

    public AutomationServiceImpl(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    public static String createContentValue(ParameterValues parameterValues) {
        String string;
        String jsonString;
        if (parameterValues == null || (string = parameterValues.getString("v2IntentParam", "")) == null) {
            string = "";
        }
        return string.length() > 0 ? string : (parameterValues == null || (jsonString = parameterValues.toJsonString()) == null) ? "" : jsonString;
    }

    public static ParameterValues getParameterValues(Cursor cursor) throws JSONException {
        ParameterValues parameterValues;
        String string = cursor.getString(cursor.getColumnIndex("intent_param"));
        if (string == null) {
            Log.INSTANCE.getClass();
            Log.e("AutomationServiceImpl@SDK", "getParameterValues(parameter is null) - ");
            ParameterValues.Companion.getClass();
            return new ParameterValues();
        }
        ParameterValues.Companion.getClass();
        HashMap map = new HashMap();
        if (string.length() == 0) {
            parameterValues = new ParameterValues(map, null);
        } else {
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    next.getClass();
                    ParameterValues.ParameterValue.Companion companion = ParameterValues.ParameterValue.Companion;
                    String string2 = jSONObject.getString(next);
                    companion.getClass();
                    ParameterValues.ParameterValue parameterValue = new ParameterValues.ParameterValue((DefaultConstructorMarker) null);
                    try {
                        try {
                            JSONObject jSONObject2 = new JSONObject(string2);
                            parameterValue.mValueType = ParameterValues.ParameterValue.Companion.parseType(jSONObject2);
                            ParameterValues.ParameterValue.ValueType valueType = parameterValue.mValueType;
                            if (valueType == null) {
                                valueType = null;
                            }
                            parameterValue.value = ParameterValues.ParameterValue.Companion.parseValue(jSONObject2, valueType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    }
                    map.put(next, parameterValue);
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            parameterValues = new ParameterValues(map, null);
        }
        if (((HashMap) parameterValues.parameterValueMap).isEmpty()) {
            ((HashMap) parameterValues.parameterValueMap).put("v2IsNegative", new ParameterValues.ParameterValue(cursor.getInt(cursor.getColumnIndex("is_negative"))));
            parameterValues.put("v2IntentParam", string);
        }
        return parameterValues;
    }

    public final List filterSupportedTags(Context context, List list) throws IOException {
        ArrayList arrayList = new ArrayList();
        try {
            ((ContentHandlerImpl) this.contentHandler).getClass();
            ContentResolver contentResolver = context.getContentResolver();
            Uri.Builder builderBuildUpon = Uri.parse("content://com.samsung.android.app.routines.routineinfoprovider/core_service/tag_supported").buildUpon();
            builderBuildUpon.appendQueryParameter("tag", new Gson().toJson(list));
            Cursor cursorQuery = contentResolver.query(builderBuildUpon.build(), null, null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() > 0 && cursorQuery.moveToFirst()) {
                        int columnIndex = cursorQuery.getColumnIndex("tag");
                        do {
                            arrayList.add(cursorQuery.getString(columnIndex));
                        } while (cursorQuery.moveToNext());
                    }
                    Unit unit = Unit.INSTANCE;
                    cursorQuery.close();
                    return arrayList;
                } finally {
                }
            }
        } catch (Exception e) {
            Log log = Log.INSTANCE;
            String str = "filterSupportedTags: " + e.getMessage();
            log.getClass();
            Log.e("AutomationServiceImpl@SDK", str);
        }
        return arrayList;
    }
}
