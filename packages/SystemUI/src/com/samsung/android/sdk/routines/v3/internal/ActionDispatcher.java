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

/* loaded from: classes4.dex */
public class ActionDispatcher extends Dispatcher {

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
        Bundle bundleC$2;
        Bundle bundle3;
        ErrorContents errorContents;
        List list;
        String jsonString;
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
        final ParameterValues parameterValuesFromJsonString = ParameterValues.fromJsonString(bundle.getString(ExtraKey.PARAMETER_VALUES.a, ""));
        int[] iArr = AnonymousClass1.a;
        ActionMethod[] actionMethodArrValues = ActionMethod.values();
        int length = actionMethodArrValues.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i = 0;
                Log.a("ActionMethod", "ActionMethod.fromValue - not supported value: " + str);
                actionMethod = ActionMethod.UNKNOWN;
                break;
            }
            actionMethod = actionMethodArrValues[i2];
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
                new Thread(new Runnable(context, string, parameterValuesFromJsonString, j, bundle2, obj) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda0
                    public final /* synthetic */ ParameterValues f$3;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.f$3 = parameterValuesFromJsonString;
                        this.f$5 = bundle2;
                        this.f$6 = obj;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        EdgelightingRoutineActionHandler edgelightingRoutineActionHandler2 = this.f$0;
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
                    bundleC$2 = Dispatcher.c$2();
                    bundle3 = bundleC$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 2:
                bundle2 = new Bundle();
                final Object obj2 = new Object();
                new Thread(new Runnable() { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda2
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    /* JADX WARN: Removed duplicated region for block: B:9:0x0048  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() throws NumberFormatException {
                        EdgelightingRoutineActionHandler edgelightingRoutineActionHandler2 = edgelightingRoutineActionHandler;
                        Context context2 = context;
                        String str2 = string;
                        ParameterValues parameterValues = parameterValuesFromJsonString;
                        ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6 = new ActionDispatcher$$ExternalSyntheticLambda6(bundle2, j, obj2);
                        edgelightingRoutineActionHandler2.mContext = context2;
                        Slog.d("EdgelightingRoutineActionHandler", "onPerformAction : ".concat(str2));
                        if (!parameterValues.a.isEmpty()) {
                            if (edgelightingRoutineActionHandler2.mEdgeLightingInfo == null) {
                                edgelightingRoutineActionHandler2.mEdgeLightingInfo = new EdgeEffectInfo();
                            }
                            String string2 = parameterValues.getString("effect_key", "");
                            string2.getClass();
                            switch (string2) {
                                case "preload/spotlight":
                                case "preload/noframe":
                                case "preload/reflection":
                                case "preload/basic":
                                case "preload/echo":
                                    edgelightingRoutineActionHandler2.mEdgeLightingEffect = parameterValues.getString("effect_key", "");
                                    int i3 = Integer.parseInt(parameterValues.getString("color_key", ""));
                                    int i4 = Integer.parseInt(parameterValues.getString("transparency_key", ""));
                                    int i5 = Integer.parseInt(parameterValues.getString("thickness_key", ""));
                                    int i6 = Integer.parseInt(parameterValues.getString("duration_key", ""));
                                    int i7 = Integer.parseInt(parameterValues.getString("color_value_key", ""));
                                    StringBuilder sb = new StringBuilder("onPerformAction param=");
                                    sb.append(edgelightingRoutineActionHandler2.mEdgeLightingEffect);
                                    sb.append(";");
                                    sb.append(i3);
                                    sb.append(";");
                                    ViewPager$$ExternalSyntheticOutline0.m(sb, i4, ";", i5, ";");
                                    sb.append(i6);
                                    sb.append(";");
                                    sb.append(i7);
                                    Slog.d("EdgelightingRoutineActionHandler", sb.toString());
                                    int preloadIndex = EdgeLightingStyleManager.getInstance().getPreloadIndex(edgelightingRoutineActionHandler2.mEdgeLightingEffect);
                                    int edgeLightingStylePreDefineColor = (i7 == 0 || i3 != 99) ? EdgeLightingSettingUtils.getEdgeLightingStylePreDefineColor(context2, i3, true) : i7;
                                    float f = 1.0f - (i4 / 100.0f);
                                    int edgeLightingWidth = EdgeLightingSettingUtils.getEdgeLightingWidth(i5, context2.getApplicationContext());
                                    int edgeLightingDuration = EdgeLightingSettingUtils.getEdgeLightingDuration(i6);
                                    StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(preloadIndex, i3, "EdgeLightingInfo : type=", ",color=", ",alpha=");
                                    sbM.append(f);
                                    sbM.append(",width=");
                                    sbM.append(edgeLightingWidth);
                                    sbM.append(",time=");
                                    sbM.append(edgeLightingDuration);
                                    sbM.append(",colorValue=");
                                    sbM.append(i7);
                                    Slog.d("EdgelightingRoutineActionHandler", sbM.toString());
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
                    bundleC$2 = Dispatcher.c$2();
                    bundle3 = bundleC$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 3:
                bundle2 = new Bundle();
                final Object obj3 = new Object();
                new Thread(new Runnable(context, string, parameterValuesFromJsonString, j, bundle2, obj3) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda1
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
                        EdgelightingRoutineActionHandler edgelightingRoutineActionHandler2 = this.f$0;
                        ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6 = new ActionDispatcher$$ExternalSyntheticLambda6(this.f$5, this.f$4, this.f$6);
                        edgelightingRoutineActionHandler2.getClass();
                        android.util.Log.e("RoutineActionHandler", "onPerformReverseAction: this should not be called without overriding!!!");
                        actionDispatcher$$ExternalSyntheticLambda6.actionFinished(new ActionResult.Default(ActionResult.ResultCode.FAIL_NOT_SUPPORTED));
                    }
                }).start();
                if (!a(obj3)) {
                    Log.a("ActionDispatcher", "onPerformReverseAction: timeout");
                    bundleC$2 = Dispatcher.c$2();
                    bundle3 = bundleC$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 4:
                bundle2 = new Bundle();
                final Object obj4 = new Object();
                new Thread(new Runnable(context, string, parameterValuesFromJsonString, j, bundle2, obj4) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda4
                    public final /* synthetic */ Context f$1;
                    public final /* synthetic */ ParameterValues f$3;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.f$3 = parameterValuesFromJsonString;
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
                    bundleC$2 = Dispatcher.c$2();
                    bundle3 = bundleC$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 5:
                bundle2 = new Bundle();
                final Object obj5 = new Object();
                final int i3 = 0;
                new Thread(new Runnable(edgelightingRoutineActionHandler, context, string, parameterValuesFromJsonString, j, bundle2, obj5, i3) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda3
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
                    bundleC$2 = Dispatcher.c$2();
                    bundle3 = bundleC$2;
                    break;
                }
                bundle3 = bundle2;
                break;
            case 6:
                bundle2 = new Bundle();
                final Object obj6 = new Object();
                final int i4 = 1;
                new Thread(new Runnable(edgelightingRoutineActionHandler, context, string, parameterValuesFromJsonString, j, bundle2, obj6, i4) { // from class: com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda3
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
                    bundleC$2 = Dispatcher.c$2();
                    bundle3 = bundleC$2;
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
                String str2 = ExtraKey.CONFIG_TEMPLATE.a;
                android.util.Log.e("RoutineActionHandler", "onRequestTemplateContents: this should not be called without overriding!!!");
                bundle3.putBundle(str2, new UiTemplate(new Bundle()).a);
                break;
            case 9:
                int i5 = bundle.getInt(ExtraKey.RESULT_INT.a, i);
                bundle3 = new Bundle();
                String str3 = ExtraKey.ERROR_DIALOG_CONTENTS.a;
                Slog.i("EdgelightingRoutineActionHandler", "error code : " + i5);
                if (i5 != 100) {
                    ErrorContents.Builder builder = new ErrorContents.Builder("Action not executed due to some reason");
                    builder.a = "Error";
                    errorContents = new ErrorContents("Error", builder.b, null);
                } else {
                    ErrorContents.Builder builder2 = new ErrorContents.Builder("Couldn't perform this action because not supported effect");
                    errorContents = new ErrorContents(builder2.a, builder2.b, null);
                }
                bundle3.putBundle(str3, errorContents.toBundle());
                break;
            case 10:
                Bundle bundle4 = new Bundle();
                String str4 = ExtraKey.MIGRATED_PARAMETER.a;
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
                        String str5 = stringArrayList.get(i6);
                        i6++;
                        arrayList.add((TargetInstanceInfo) gson.fromJson(str5, TargetInstanceInfo.class));
                    }
                    list = arrayList;
                }
                if (list.isEmpty()) {
                    jsonString = null;
                } else {
                    String[] strArrSplit = ((TargetInstanceInfo) list.getFirst()).getIntentParam().split(";");
                    ParameterValues parameterValues = new ParameterValues();
                    parameterValues.put("effect_key", strArrSplit[i]);
                    parameterValues.put("color_key", strArrSplit[1]);
                    parameterValues.put("transparency_key", strArrSplit[2]);
                    parameterValues.put("thickness_key", strArrSplit[3]);
                    parameterValues.put("duration_key", strArrSplit[4]);
                    parameterValues.put("color_value_key", strArrSplit[5]);
                    jsonString = parameterValues.toJsonString();
                }
                bundle4.putString(str4, jsonString);
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
