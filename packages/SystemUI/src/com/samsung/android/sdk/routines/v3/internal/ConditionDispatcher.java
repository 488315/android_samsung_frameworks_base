package com.samsung.android.sdk.routines.v3.internal;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettingConstants;
import com.google.gson.Gson;
import com.samsung.android.sdk.routines.v3.data.ConditionValidity;
import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SatisfactionStatus;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.data.TargetInstanceInfo;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineConditionHandler;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* loaded from: classes4.dex */
public class ConditionDispatcher extends Dispatcher {

    /* renamed from: com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ConditionMethod.values().length];
            a = iArr;
            try {
                iArr[ConditionMethod.IS_SATISFIED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ConditionMethod.ON_ENABLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[ConditionMethod.ON_DISABLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[ConditionMethod.GET_LABEL_PARAM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[ConditionMethod.IS_VALID.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[ConditionMethod.IS_SUPPORT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[ConditionMethod.GET_CONFIG_TEMPLATE_CONTENTS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[ConditionMethod.GET_ERROR_DIALOG_CONTENTS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[ConditionMethod.ON_MIGRATE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    @Override // com.samsung.android.sdk.routines.v3.internal.Dispatcher
    public final String a() {
        return "ConditionDispatcher";
    }

    public final Bundle a(final Context context, String str, Bundle bundle) throws NumberFormatException {
        ConditionMethod conditionMethod;
        final Bundle bundle2;
        Bundle bundleC$2;
        Bundle bundle3;
        Bundle bundle4;
        Object next;
        final String string = bundle.getString(ExtraKey.TAG.a);
        if (string == null) {
            Log.a("ConditionDispatcher", "callConditionHandler - tag is null");
            return null;
        }
        final RoutineConditionHandler routineConditionHandler = (RoutineConditionHandler) RoutineSdkImpl.LazyHolder.a.d.getWithTimeout(string);
        if (routineConditionHandler == null) {
            Log.a("ConditionDispatcher", "callConditionHandler - conditionHandler is null. tag=".concat(string));
            return null;
        }
        Log.b("ConditionDispatcher", "callConditionHandler start - tag=" + string + ", method=" + str);
        final ParameterValues parameterValuesFromJsonString = ParameterValues.fromJsonString(bundle.getString(ExtraKey.PARAMETER_VALUES.a, ""));
        final long j = bundle.getLong(ExtraKey.INSTANCE_ID.a, 0L);
        int[] iArr = AnonymousClass1.a;
        ConditionMethod[] conditionMethodArrValues = ConditionMethod.values();
        int length = conditionMethodArrValues.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                Log.a("ConditionMethod", "ConditionMethod.fromValue - not supported value: " + str);
                conditionMethod = ConditionMethod.UNKNOWN;
                break;
            }
            conditionMethod = conditionMethodArrValues[i];
            if (conditionMethod.a.equals(str)) {
                break;
            }
            i++;
        }
        switch (iArr[conditionMethod.ordinal()]) {
            case 1:
                bundle2 = new Bundle();
                final Object obj = new Object();
                new Thread(new Runnable(context, string, parameterValuesFromJsonString, j, bundle2, obj) { // from class: com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$$ExternalSyntheticLambda0
                    public final /* synthetic */ Context f$1;
                    public final /* synthetic */ String f$2;
                    public final /* synthetic */ ParameterValues f$3;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.f$5 = bundle2;
                        this.f$6 = obj;
                    }

                    @Override // java.lang.Runnable
                    public final void run() throws NumberFormatException {
                        RoutineConditionHandler routineConditionHandler2 = this.f$0;
                        Context context2 = this.f$1;
                        String str2 = this.f$2;
                        ParameterValues parameterValues = this.f$3;
                        ConditionDispatcher$$ExternalSyntheticLambda3 conditionDispatcher$$ExternalSyntheticLambda3 = new ConditionDispatcher$$ExternalSyntheticLambda3(this.f$5, this.f$6, 0);
                        PlayingAudioConditionHandler playingAudioConditionHandler = (PlayingAudioConditionHandler) routineConditionHandler2;
                        if (str2.equals("playing_audio")) {
                            int i2 = Integer.parseInt(parameterValues.getString("playing_audio_app_uid", "-1"));
                            boolean zBooleanValue = parameterValues.getBoolean().booleanValue();
                            SoundCraftSettingConstants.INSTANCE.getClass();
                            if (!(Settings.System.getInt(context2.getContentResolver(), "audio_soundcraft_app_setting", 1) == 1)) {
                                MediaSessions$H$$ExternalSyntheticOutline0.m("isSatisfied : (setting off) tag=", str2, ", app=", playingAudioConditionHandler.uidPackageName(i2), "SoundCraft.PlayingAudioConditionHandler");
                                conditionDispatcher$$ExternalSyntheticLambda3.setResponse(SatisfactionStatus.NOT_SATISFIED);
                                return;
                            }
                            if (zBooleanValue && !playingAudioConditionHandler.isBudsPluginCanAction()) {
                                MediaSessions$H$$ExternalSyntheticOutline0.m("isSatisfied : false (isBudsAction, plugin disconnected) tag=", str2, ", app=", playingAudioConditionHandler.uidPackageName(i2), "SoundCraft.PlayingAudioConditionHandler");
                                conditionDispatcher$$ExternalSyntheticLambda3.setResponse(SatisfactionStatus.NOT_SATISFIED);
                                return;
                            }
                            if (!zBooleanValue && playingAudioConditionHandler.isBudsPluginCanAction()) {
                                MediaSessions$H$$ExternalSyntheticOutline0.m("isSatisfied : false (isPhoneAction, buds connected) tag=", str2, ", app=", playingAudioConditionHandler.uidPackageName(i2), "SoundCraft.PlayingAudioConditionHandler");
                                conditionDispatcher$$ExternalSyntheticLambda3.setResponse(SatisfactionStatus.NOT_SATISFIED);
                                return;
                            }
                            boolean z = playingAudioConditionHandler.lastStartedUid == i2;
                            boolean zIsBudsPluginCanAction = playingAudioConditionHandler.isBudsPluginCanAction();
                            String strUidPackageName = playingAudioConditionHandler.uidPackageName(i2);
                            StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("isSatisfied: tag=", str2, ", isBudsAction=", ", isBudsPluginCanAction=", zBooleanValue);
                            sbM.append(zIsBudsPluginCanAction);
                            sbM.append(" app=");
                            sbM.append(strUidPackageName);
                            sbM.append(", isSatisfied=");
                            ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, z, "SoundCraft.PlayingAudioConditionHandler");
                            conditionDispatcher$$ExternalSyntheticLambda3.setResponse(z ? SatisfactionStatus.SATISFIED : SatisfactionStatus.NOT_SATISFIED);
                        }
                    }
                }).start();
                if (!a(obj)) {
                    Log.a("ConditionDispatcher", "isSatisfied: timeout");
                    bundleC$2 = Dispatcher.c$2();
                    bundle4 = bundleC$2;
                    break;
                }
                bundle4 = bundle2;
                break;
            case 2:
                bundle3 = null;
                PlayingAudioConditionHandler playingAudioConditionHandler = (PlayingAudioConditionHandler) routineConditionHandler;
                if (string.equals("playing_audio")) {
                    int i2 = Integer.parseInt(parameterValuesFromJsonString.getString("playing_audio_app_uid", "-1"));
                    playingAudioConditionHandler.enableList.add(Integer.valueOf(i2));
                    RecyclerView$$ExternalSyntheticOutline0.m(playingAudioConditionHandler.enableList.size(), "SoundCraft.PlayingAudioConditionHandler", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onEnabled : tag=", string, ", app=", playingAudioConditionHandler.uidPackageName(i2), ", enableCount="));
                }
                bundle4 = bundle3;
                break;
            case 3:
                bundle3 = null;
                PlayingAudioConditionHandler playingAudioConditionHandler2 = (PlayingAudioConditionHandler) routineConditionHandler;
                if (string.equals("playing_audio")) {
                    int i3 = Integer.parseInt(parameterValuesFromJsonString.getString("playing_audio_app_uid", "-1"));
                    Iterator it = playingAudioConditionHandler2.enableList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            next = it.next();
                            if (((Number) next).intValue() == i3) {
                            }
                        } else {
                            next = null;
                        }
                    }
                    TypeIntrinsics.asMutableCollection(playingAudioConditionHandler2.enableList).remove((Integer) next);
                    RecyclerView$$ExternalSyntheticOutline0.m(playingAudioConditionHandler2.enableList.size(), "SoundCraft.PlayingAudioConditionHandler", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onDisabled : tag=", string, ", app=", playingAudioConditionHandler2.uidPackageName(i3), ", enableCount="));
                }
                bundle4 = bundle3;
                break;
            case 4:
                bundle2 = new Bundle();
                final Object obj2 = new Object();
                new Thread(new Runnable(context, string, parameterValuesFromJsonString, j, bundle2, obj2) { // from class: com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$$ExternalSyntheticLambda2
                    public final /* synthetic */ String f$2;
                    public final /* synthetic */ ParameterValues f$3;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.f$2 = string;
                        this.f$3 = parameterValuesFromJsonString;
                        this.f$5 = bundle2;
                        this.f$6 = obj2;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        RoutineConditionHandler routineConditionHandler2 = this.f$0;
                        String str2 = this.f$2;
                        ParameterValues parameterValues = this.f$3;
                        Bundle bundle5 = this.f$5;
                        Object obj3 = this.f$6;
                        ((PlayingAudioConditionHandler) routineConditionHandler2).getClass();
                        if (Intrinsics.areEqual(str2, "playing_audio")) {
                            String string2 = parameterValues.getString("playing_audio_app_package_name", "");
                            MediaSessions$H$$ExternalSyntheticOutline0.m("getParameterLabel : tag=", str2, ", packageName=", string2, "SoundCraft.PlayingAudioConditionHandler");
                            bundle5.putString(ExtraKey.CONFIG_LABEL_PARAMS.a, string2);
                            synchronized (obj3) {
                                obj3.notify();
                            }
                        }
                    }
                }).start();
                if (!a(obj2)) {
                    Log.a("ConditionDispatcher", "getParameterLabel: timeout");
                    bundleC$2 = Dispatcher.c$2();
                    bundle4 = bundleC$2;
                    break;
                }
                bundle4 = bundle2;
                break;
            case 5:
                bundle2 = new Bundle();
                final Object obj3 = new Object();
                new Thread(new Runnable(context, string, parameterValuesFromJsonString, j, bundle2, obj3) { // from class: com.samsung.android.sdk.routines.v3.internal.ConditionDispatcher$$ExternalSyntheticLambda1
                    public final /* synthetic */ String f$2;
                    public final /* synthetic */ Bundle f$5;
                    public final /* synthetic */ Object f$6;

                    {
                        this.f$2 = string;
                        this.f$5 = bundle2;
                        this.f$6 = obj3;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        String str2 = this.f$2;
                        ConditionDispatcher$$ExternalSyntheticLambda3 conditionDispatcher$$ExternalSyntheticLambda3 = new ConditionDispatcher$$ExternalSyntheticLambda3(this.f$5, this.f$6, 1);
                        if (str2.equals("playing_audio")) {
                            conditionDispatcher$$ExternalSyntheticLambda3.setResponse(new ConditionValidity.Valid());
                        } else {
                            conditionDispatcher$$ExternalSyntheticLambda3.setResponse(new ConditionValidity.Error(1030));
                        }
                    }
                }).start();
                if (!a(obj3)) {
                    Log.a("ConditionDispatcher", "checkValidity: timeout");
                    bundleC$2 = Dispatcher.c$2();
                    bundle4 = bundleC$2;
                    break;
                }
                bundle4 = bundle2;
                break;
            case 6:
                bundle4 = new Bundle();
                bundle4.putInt(ExtraKey.RESULT_INT.a, SupportStatus.SUPPORTED.a);
                break;
            case 7:
                bundleC$2 = new Bundle();
                String str2 = ExtraKey.CONFIG_TEMPLATE.a;
                android.util.Log.d("SoundCraft.PlayingAudioConditionHandler", "onRequestTemplateContents : tag=" + string + ", lastStartedPackageName=" + ((PlayingAudioConditionHandler) routineConditionHandler).lastStartedPackageName);
                bundleC$2.putBundle(str2, new UiTemplate(new Bundle()).a);
                bundle4 = bundleC$2;
                break;
            case 8:
                int i4 = bundle.getInt(ExtraKey.RESULT_INT.a, 0);
                Bundle bundle5 = new Bundle();
                String str3 = ExtraKey.ERROR_DIALOG_CONTENTS.a;
                String str4 = ((PlayingAudioConditionHandler) routineConditionHandler).lastStartedPackageName;
                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i4, "onRequestErrorDialogContents: tag=", string, ", errorCode=", ", lastStartedPackageName=");
                sbM890m.append(str4);
                android.util.Log.d("SoundCraft.PlayingAudioConditionHandler", sbM890m.toString());
                ErrorContents.Builder builder = new ErrorContents.Builder(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "Not handled error code:"));
                builder.a = "Condition Error";
                bundle5.putBundle(str3, new ErrorContents("Condition Error", builder.b, null).toBundle());
                bundle4 = bundle5;
                break;
            case 9:
                Bundle bundle6 = new Bundle();
                String str5 = ExtraKey.MIGRATED_PARAMETER.a;
                ArrayList<String> stringArrayList = bundle.getStringArrayList(ExtraKey.TARGET_INSTANCES.a);
                if (stringArrayList == null) {
                    Log.a("ConditionDispatcher", "getTargetInstances() targetInstances is null");
                    List list = Collections.EMPTY_LIST;
                } else {
                    ArrayList arrayList = new ArrayList();
                    Gson gson = new Gson();
                    int size = stringArrayList.size();
                    int i5 = 0;
                    while (i5 < size) {
                        String str6 = stringArrayList.get(i5);
                        i5++;
                        arrayList.add((TargetInstanceInfo) gson.fromJson(str6, TargetInstanceInfo.class));
                    }
                }
                android.util.Log.e("RoutineConditionHandler", "onMigrate: this should not be called without overriding!!!");
                bundle6.putString(str5, null);
                bundle4 = bundle6;
                Log.a("ConditionDispatcher", "callConditionHandler - not supported method: " + str);
                break;
            default:
                bundle4 = null;
                Log.a("ConditionDispatcher", "callConditionHandler - not supported method: " + str);
                break;
        }
        Log.b("ConditionDispatcher", "callConditionHandler end - tag=" + string + ", method=" + str);
        return bundle4;
    }
}
