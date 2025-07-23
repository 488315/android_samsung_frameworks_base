package com.samsung.android.sdk.routines.v3.internal;

import android.content.Context;
import android.os.Bundle;
import android.util.Slog;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyle;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.routine.EdgelightingRoutineActionHandler;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.google.gson.Gson;
import com.samsung.android.sdk.routines.v3.data.ActionResult;
import com.samsung.android.sdk.routines.v3.data.ActionValidity;
import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.data.TargetInstanceInfo;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ActionDispatcher extends Dispatcher {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ActionMethod.values().length];
            a = iArr;
            try {
                iArr[ActionMethod.GET_CURRENT_PARAM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ActionMethod.PERFORM_ACTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[ActionMethod.RECOVER_ACTION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[ActionMethod.GET_LABEL_PARAM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[ActionMethod.GET_PREVIEW_IMAGE_FILE_DESCRIPTOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[ActionMethod.IS_VALID.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[ActionMethod.IS_SUPPORT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[ActionMethod.GET_CONFIG_TEMPLATE_CONTENTS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[ActionMethod.GET_ERROR_DIALOG_CONTENTS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[ActionMethod.ON_MIGRATE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    @Override // com.samsung.android.sdk.routines.v3.internal.Dispatcher
    public final String a() {
        return "ActionDispatcher";
    }

    public final Bundle a(final Context context, String str, Bundle bundle) {
        int i;
        ActionMethod actionMethod;
        final Bundle bundle2;
        Bundle c$2;
        Bundle bundle3;
        ErrorContents errorContents;
        List list;
        String str2;
        final String string = bundle.getString(ExtraKey.TAG.a);
        if (string == null) {
            Log.a("ActionDispatcher", "callActionHandler - tag is null");
            return null;
        }
        final EdgelightingRoutineActionHandler edgelightingRoutineActionHandler = (EdgelightingRoutineActionHandler) RoutineSdkImpl.LazyHolder.a.e.getWithTimeout(string);
        if (edgelightingRoutineActionHandler == null) {
            Log.a("ActionDispatcher", "callActionHandler - actionHandler is null. tag=".concat(string));
            return null;
        }
        Log.b("ActionDispatcher", "callActionHandler start - tag=" + string + ", method=" + str);
        final long j = bundle.getLong(ExtraKey.INSTANCE_ID.a, 0L);
        final ParameterValues fromJsonString = ParameterValues.fromJsonString(bundle.getString(ExtraKey.PARAMETER_VALUES.a, ""));
        int[] iArr = AnonymousClass1.a;
        ActionMethod[] values = ActionMethod.values();
        int length = values.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i = 0;
                Log.a("ActionMethod", "ActionMethod.fromValue - not supported value: " + str);
                actionMethod = ActionMethod.UNKNOWN;
                break;
            }
            actionMethod = values[i2];
            i = 0;
            if (actionMethod.a.equals(str)) {
                break;
            }
            i2++;
        }
        switch (iArr[actionMethod.ordinal()]) {
            case 1:
                bundle2 = new Bundle();
                final Object obj = new Object();
                new Thread(new Runnable(context, string, fromJsonString, j, bundle2, obj) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda0
                    public final /* synthetic */ ParameterValues f$3;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.f$3 = fromJsonString;
                        this.f$5 = bundle2;
                        this.f$6 = obj;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        EdgelightingRoutineActionHandler edgelightingRoutineActionHandler2 = EdgelightingRoutineActionHandler.this;
                        ParameterValues parameterValues = this.f$3;
                        Bundle bundle4 = this.f$5;
                        Object obj2 = this.f$6;
                        edgelightingRoutineActionHandler2.getClass();
                        android.util.Log.e("RoutineActionHandler", "getCurrentParameterValues: this should not be called without overriding!!!");
                        bundle4.putString(ExtraKey.PARAMETER_VALUES.a, parameterValues.toJsonString());
                        synchronized (obj2) {
                            obj2.notify();
                        }
                    }
                }).start();
                if (!a(obj)) {
                    Log.a("ActionDispatcher", "getCurrentParameterValues: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 2:
                bundle2 = new Bundle();
                final Object obj2 = new Object();
                new Thread(new Runnable() { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda2
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    @Override // java.lang.Runnable
                    public final void run() {
                        char c;
                        EdgelightingRoutineActionHandler edgelightingRoutineActionHandler2 = EdgelightingRoutineActionHandler.this;
                        Context context2 = context;
                        String str3 = string;
                        ParameterValues parameterValues = fromJsonString;
                        ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6 = new ActionDispatcher$$ExternalSyntheticLambda6(bundle2, j, obj2);
                        edgelightingRoutineActionHandler2.mContext = context2;
                        Slog.d("EdgelightingRoutineActionHandler", "onPerformAction : ".concat(str3));
                        if (!parameterValues.a.isEmpty()) {
                            if (edgelightingRoutineActionHandler2.mEdgeLightingInfo == null) {
                                edgelightingRoutineActionHandler2.mEdgeLightingInfo = new EdgeEffectInfo();
                            }
                            String string2 = parameterValues.getString("effect_key", "");
                            string2.getClass();
                            switch (string2.hashCode()) {
                                case -1685878578:
                                    if (string2.equals("preload/spotlight")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -677405114:
                                    if (string2.equals("preload/noframe")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 659972081:
                                    if (string2.equals("preload/reflection")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 962108584:
                                    if (string2.equals("preload/basic")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1555147371:
                                    if (string2.equals("preload/echo")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    edgelightingRoutineActionHandler2.mEdgeLightingEffect = parameterValues.getString("effect_key", "");
                                    int parseInt = Integer.parseInt(parameterValues.getString("color_key", ""));
                                    int parseInt2 = Integer.parseInt(parameterValues.getString("transparency_key", ""));
                                    int parseInt3 = Integer.parseInt(parameterValues.getString("thickness_key", ""));
                                    int parseInt4 = Integer.parseInt(parameterValues.getString("duration_key", ""));
                                    int parseInt5 = Integer.parseInt(parameterValues.getString("color_value_key", ""));
                                    StringBuilder sb = new StringBuilder("onPerformAction param=");
                                    sb.append(edgelightingRoutineActionHandler2.mEdgeLightingEffect);
                                    sb.append(";");
                                    sb.append(parseInt);
                                    sb.append(";");
                                    ViewPager$$ExternalSyntheticOutline0.m(sb, parseInt2, ";", parseInt3, ";");
                                    sb.append(parseInt4);
                                    sb.append(";");
                                    sb.append(parseInt5);
                                    Slog.d("EdgelightingRoutineActionHandler", sb.toString());
                                    int preloadIndex = EdgeLightingStyleManager.getInstance().getPreloadIndex(edgelightingRoutineActionHandler2.mEdgeLightingEffect);
                                    int edgeLightingStylePreDefineColor = (parseInt5 == 0 || parseInt != 99) ? EdgeLightingSettingUtils.getEdgeLightingStylePreDefineColor(context2, parseInt, true) : parseInt5;
                                    float f = 1.0f - (parseInt2 / 100.0f);
                                    int edgeLightingWidth = EdgeLightingSettingUtils.getEdgeLightingWidth(parseInt3, context2.getApplicationContext());
                                    int edgeLightingDuration = EdgeLightingSettingUtils.getEdgeLightingDuration(parseInt4);
                                    StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(preloadIndex, parseInt, "EdgeLightingInfo : type=", ",color=", ",alpha=");
                                    m.append(f);
                                    m.append(",width=");
                                    m.append(edgeLightingWidth);
                                    m.append(",time=");
                                    m.append(edgeLightingDuration);
                                    m.append(",colorValue=");
                                    m.append(parseInt5);
                                    Slog.d("EdgelightingRoutineActionHandler", m.toString());
                                    EdgeEffectInfo edgeEffectInfo = edgelightingRoutineActionHandler2.mEdgeLightingInfo;
                                    edgeEffectInfo.mEffectType = preloadIndex;
                                    edgeEffectInfo.mEffectColors = new int[]{edgeLightingStylePreDefineColor};
                                    edgeEffectInfo.mStrokeAlpha = f;
                                    edgeEffectInfo.mStrokeWidth = edgeLightingWidth;
                                    edgeEffectInfo.mWidthDepth = -1;
                                    edgeEffectInfo.mLightingDuration = edgeLightingDuration;
                                    edgeEffectInfo.mIsMultiResolutionSupoorted = true;
                                    EdgelightingRoutineActionHandler.AnonymousClass1 anonymousClass1 = edgelightingRoutineActionHandler2.mHandler;
                                    if (anonymousClass1.hasMessages(0)) {
                                        anonymousClass1.removeMessages(0);
                                    }
                                    anonymousClass1.sendEmptyMessage(0);
                                    break;
                                default:
                                    actionDispatcher$$ExternalSyntheticLambda6.actionFinished(new ActionResult.Error(100));
                                    return;
                            }
                        }
                        actionDispatcher$$ExternalSyntheticLambda6.actionFinished(new ActionResult.Default(ActionResult.ResultCode.SUCCESS));
                    }
                }).start();
                if (!a(obj2)) {
                    Log.a("ActionDispatcher", "onPerformAction: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 3:
                bundle2 = new Bundle();
                final Object obj3 = new Object();
                new Thread(new Runnable(context, string, fromJsonString, j, bundle2, obj3) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda1
                    public final /* synthetic */ long f$4;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.f$4 = j;
                        this.f$5 = bundle2;
                        this.f$6 = obj3;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        EdgelightingRoutineActionHandler edgelightingRoutineActionHandler2 = EdgelightingRoutineActionHandler.this;
                        ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6 = new ActionDispatcher$$ExternalSyntheticLambda6(this.f$5, this.f$4, this.f$6);
                        edgelightingRoutineActionHandler2.getClass();
                        android.util.Log.e("RoutineActionHandler", "onPerformReverseAction: this should not be called without overriding!!!");
                        actionDispatcher$$ExternalSyntheticLambda6.actionFinished(new ActionResult.Default(ActionResult.ResultCode.FAIL_NOT_SUPPORTED));
                    }
                }).start();
                if (!a(obj3)) {
                    Log.a("ActionDispatcher", "onPerformReverseAction: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 4:
                bundle2 = new Bundle();
                final Object obj4 = new Object();
                new Thread(new Runnable(context, string, fromJsonString, j, bundle2, obj4) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda4
                    public final /* synthetic */ Context f$1;
                    public final /* synthetic */ ParameterValues f$3;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.f$3 = fromJsonString;
                        this.f$5 = bundle2;
                        this.f$6 = obj4;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        Context context2 = this.f$1;
                        ParameterValues parameterValues = this.f$3;
                        Bundle bundle4 = this.f$5;
                        Object obj5 = this.f$6;
                        if (!parameterValues.a.isEmpty()) {
                            String string2 = parameterValues.getString("effect_key", "");
                            Slog.d("EdgelightingRoutineActionHandler", "getParameterLabel param=" + string2);
                            EdgeLightingStyle edgeLightingStyle = (EdgeLightingStyle) EdgeLightingStyleManager.getInstance().mStyleHashMap.get(string2);
                            if (edgeLightingStyle != null) {
                                int i3 = edgeLightingStyle.mTitleStrID;
                                if (context2.getText(i3) != null) {
                                    bundle4.putString(ExtraKey.CONFIG_LABEL_PARAMS.a, context2.getText(i3).toString());
                                    synchronized (obj5) {
                                        obj5.notify();
                                    }
                                    return;
                                }
                            }
                        }
                        Slog.d("EdgelightingRoutineActionHandler", "getLabelParam text is empty");
                        bundle4.putString(ExtraKey.CONFIG_LABEL_PARAMS.a, context2.getString(R.string.edge_lighting_style_noframe));
                        synchronized (obj5) {
                            obj5.notify();
                        }
                    }
                }).start();
                if (!a(obj4)) {
                    Log.a("ActionDispatcher", "getParameterLabel: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 5:
                bundle2 = new Bundle();
                final Object obj5 = new Object();
                final int i3 = 0;
                new Thread(new Runnable(edgelightingRoutineActionHandler, context, string, fromJsonString, j, bundle2, obj5, i3) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda3
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ EdgelightingRoutineActionHandler f$0;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.$r8$classId = i3;
                        this.f$5 = bundle2;
                        this.f$6 = obj5;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (this.$r8$classId) {
                            case 0:
                                EdgelightingRoutineActionHandler edgelightingRoutineActionHandler2 = this.f$0;
                                Bundle bundle4 = this.f$5;
                                Object obj6 = this.f$6;
                                edgelightingRoutineActionHandler2.getClass();
                                android.util.Log.e("RoutineActionHandler", "getPreviewImageFileDescriptor: this should not be called without overriding!!!");
                                bundle4.putParcelable(ExtraKey.PREVIEW_IMAGE_FILE_DESCRIPTOR.a, null);
                                synchronized (obj6) {
                                    obj6.notify();
                                }
                                return;
                            default:
                                EdgelightingRoutineActionHandler edgelightingRoutineActionHandler3 = this.f$0;
                                Bundle bundle5 = this.f$5;
                                Object obj7 = this.f$6;
                                edgelightingRoutineActionHandler3.getClass();
                                ActionValidity.Default r4 = new ActionValidity.Default(ActionValidity.Validity.VALID);
                                if (r4.type == ActionValidity.ValidityType.CUSTOM_ERROR) {
                                    bundle5.putInt(ExtraKey.RESULT_TYPE.a, 16);
                                    bundle5.putInt(ExtraKey.RESULT_INT.a, r4.customReasonCode);
                                } else {
                                    bundle5.putInt(ExtraKey.RESULT_INT.a, r4.validity.value);
                                }
                                synchronized (obj7) {
                                    obj7.notify();
                                }
                                return;
                        }
                    }
                }).start();
                if (!a(obj5)) {
                    Log.a("ActionDispatcher", "getPreviewImageFileDescriptor: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 6:
                bundle2 = new Bundle();
                final Object obj6 = new Object();
                final int i4 = 1;
                new Thread(new Runnable(edgelightingRoutineActionHandler, context, string, fromJsonString, j, bundle2, obj6, i4) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda3
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ EdgelightingRoutineActionHandler f$0;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.$r8$classId = i4;
                        this.f$5 = bundle2;
                        this.f$6 = obj6;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (this.$r8$classId) {
                            case 0:
                                EdgelightingRoutineActionHandler edgelightingRoutineActionHandler2 = this.f$0;
                                Bundle bundle4 = this.f$5;
                                Object obj62 = this.f$6;
                                edgelightingRoutineActionHandler2.getClass();
                                android.util.Log.e("RoutineActionHandler", "getPreviewImageFileDescriptor: this should not be called without overriding!!!");
                                bundle4.putParcelable(ExtraKey.PREVIEW_IMAGE_FILE_DESCRIPTOR.a, null);
                                synchronized (obj62) {
                                    obj62.notify();
                                }
                                return;
                            default:
                                EdgelightingRoutineActionHandler edgelightingRoutineActionHandler3 = this.f$0;
                                Bundle bundle5 = this.f$5;
                                Object obj7 = this.f$6;
                                edgelightingRoutineActionHandler3.getClass();
                                ActionValidity.Default r4 = new ActionValidity.Default(ActionValidity.Validity.VALID);
                                if (r4.type == ActionValidity.ValidityType.CUSTOM_ERROR) {
                                    bundle5.putInt(ExtraKey.RESULT_TYPE.a, 16);
                                    bundle5.putInt(ExtraKey.RESULT_INT.a, r4.customReasonCode);
                                } else {
                                    bundle5.putInt(ExtraKey.RESULT_INT.a, r4.validity.value);
                                }
                                synchronized (obj7) {
                                    obj7.notify();
                                }
                                return;
                        }
                    }
                }).start();
                if (!a(obj6)) {
                    Log.a("ActionDispatcher", "checkValidity: timeout");
                    c$2 = Dispatcher.c$2();
                    bundle3 = c$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 7:
                bundle3 = new Bundle();
                bundle3.putInt(ExtraKey.RESULT_INT.a, SupportStatus.SUPPORTED.a);
                break;
            case 8:
                bundle3 = new Bundle();
                String str3 = ExtraKey.CONFIG_TEMPLATE.a;
                android.util.Log.e("RoutineActionHandler", "onRequestTemplateContents: this should not be called without overriding!!!");
                bundle3.putBundle(str3, new UiTemplate(new Bundle()).a);
                break;
            case 9:
                int i5 = bundle.getInt(ExtraKey.RESULT_INT.a, i);
                bundle3 = new Bundle();
                String str4 = ExtraKey.ERROR_DIALOG_CONTENTS.a;
                Slog.i("EdgelightingRoutineActionHandler", "error code : " + i5);
                if (i5 != 100) {
                    ErrorContents.Builder builder = new ErrorContents.Builder("Action not executed due to some reason");
                    builder.a = "Error";
                    errorContents = new ErrorContents("Error", builder.b, null);
                } else {
                    ErrorContents.Builder builder2 = new ErrorContents.Builder("Couldn't perform this action because not supported effect");
                    errorContents = new ErrorContents(builder2.a, builder2.b, null);
                }
                bundle3.putBundle(str4, errorContents.toBundle());
                break;
            case 10:
                Bundle bundle4 = new Bundle();
                String str5 = ExtraKey.MIGRATED_PARAMETER.a;
                ArrayList<String> stringArrayList = bundle.getStringArrayList(ExtraKey.TARGET_INSTANCES.a);
                if (stringArrayList == null) {
                    Log.a("ActionDispatcher", "getTargetInstances() targetInstances is null");
                    list = Collections.EMPTY_LIST;
                } else {
                    ArrayList arrayList = new ArrayList();
                    Gson gson = new Gson();
                    int size = stringArrayList.size();
                    int i6 = i;
                    while (i6 < size) {
                        String str6 = stringArrayList.get(i6);
                        i6++;
                        arrayList.add((TargetInstanceInfo) gson.fromJson(str6, TargetInstanceInfo.class));
                    }
                    list = arrayList;
                }
                if (list.isEmpty()) {
                    str2 = null;
                } else {
                    String[] split = ((TargetInstanceInfo) list.getFirst()).getIntentParam().split(";");
                    ParameterValues parameterValues = new ParameterValues();
                    parameterValues.put("effect_key", split[i]);
                    parameterValues.put("color_key", split[1]);
                    parameterValues.put("transparency_key", split[2]);
                    parameterValues.put("thickness_key", split[3]);
                    parameterValues.put("duration_key", split[4]);
                    parameterValues.put("color_value_key", split[5]);
                    str2 = parameterValues.toJsonString();
                }
                bundle4.putString(str5, str2);
                bundle3 = bundle4;
                Log.a("ActionDispatcher", "callActionHandler - not supported method: " + str);
                break;
            default:
                bundle3 = null;
                Log.a("ActionDispatcher", "callActionHandler - not supported method: " + str);
                break;
        }
        Log.b("ActionDispatcher", "callActionHandler end - tag=" + string + ", method=" + str);
        return bundle3;
    }
}
