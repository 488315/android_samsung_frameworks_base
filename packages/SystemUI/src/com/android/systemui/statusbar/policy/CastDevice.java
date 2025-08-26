package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionInfo;
import android.text.TextUtils;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.Utils;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class CastDevice {
    public static final Companion Companion = new Companion(null);
    public final String description;
    public final String id;
    public final boolean isCasting;
    public final String name;
    public final CastOrigin origin;
    public final String shortLogString;
    public final CastState state;
    public final Object tag;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class CastOrigin {
        public static final /* synthetic */ CastOrigin[] $VALUES;
        public static final CastOrigin MediaProjection;
        public static final CastOrigin MediaRouter;

        static {
            CastOrigin castOrigin = new CastOrigin("MediaRouter", 0);
            MediaRouter = castOrigin;
            CastOrigin castOrigin2 = new CastOrigin("MediaProjection", 1);
            MediaProjection = castOrigin2;
            CastOrigin[] castOriginArr = {castOrigin, castOrigin2};
            $VALUES = castOriginArr;
            EnumEntriesKt.enumEntries(castOriginArr);
        }

        private CastOrigin(String str, int i) {
        }

        public static CastOrigin valueOf(String str) {
            return (CastOrigin) Enum.valueOf(CastOrigin.class, str);
        }

        public static CastOrigin[] values() {
            return (CastOrigin[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class CastState {
        public static final /* synthetic */ CastState[] $VALUES;
        public static final CastState Connected;
        public static final CastState Connecting;
        public static final CastState Disconnected;

        static {
            CastState castState = new CastState("Disconnected", 0);
            Disconnected = castState;
            CastState castState2 = new CastState("Connecting", 1);
            Connecting = castState2;
            CastState castState3 = new CastState("Connected", 2);
            Connected = castState3;
            CastState[] castStateArr = {castState, castState2, castState3};
            $VALUES = castStateArr;
            EnumEntriesKt.enumEntries(castStateArr);
        }

        private CastState(String str, int i) {
        }

        public static CastState valueOf(String str) {
            return (CastState) Enum.valueOf(CastState.class, str);
        }

        public static CastState[] values() {
            return (CastState[]) $VALUES.clone();
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static CastDevice toCastDevice(MediaRouter.RouteInfo routeInfo, Context context) {
            CastState castState = routeInfo.getStatusCode() == 2 ? CastState.Connecting : (routeInfo.isSelected() || routeInfo.getStatusCode() == 6) ? CastState.Connected : CastState.Disconnected;
            String string = routeInfo.getTag().toString();
            CharSequence name = routeInfo.getName(context);
            String string2 = name != null ? name.toString() : null;
            CharSequence description = routeInfo.getDescription();
            return new CastDevice(string, string2, description != null ? description.toString() : null, castState, CastOrigin.MediaRouter, routeInfo);
        }

        private Companion() {
        }

        public static CastDevice toCastDevice(MediaProjectionInfo mediaProjectionInfo, Context context, PackageManager packageManager, CastControllerLogger castControllerLogger) {
            String packageName = mediaProjectionInfo.getPackageName();
            String packageName2 = mediaProjectionInfo.getPackageName();
            if (Utils.isHeadlessRemoteDisplayProvider(packageManager, packageName2)) {
                packageName2 = "";
            } else {
                try {
                    CharSequence charSequenceLoadLabel = packageManager.getApplicationInfo(packageName2, 0).loadLabel(packageManager);
                    if (!TextUtils.isEmpty(charSequenceLoadLabel)) {
                        packageName2 = charSequenceLoadLabel.toString();
                    } else {
                        LogLevel logLevel = LogLevel.WARNING;
                        final int i = 0;
                        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.policy.CastDevice$Companion$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                switch (i) {
                                    case 0:
                                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No label found for package: ", logMessage.getStr1());
                                    default:
                                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error getting appName for package=", logMessage.getStr1());
                                }
                            }
                        };
                        LogBuffer logBuffer = castControllerLogger.logger;
                        LogMessage logMessageObtain = logBuffer.obtain("#getAppName", logLevel, function1, null);
                        ((LogMessageImpl) logMessageObtain).str1 = packageName2;
                        logBuffer.commit(logMessageObtain);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    LogLevel logLevel2 = LogLevel.WARNING;
                    final int i2 = 1;
                    Function1 function12 = new Function1() { // from class: com.android.systemui.statusbar.policy.CastDevice$Companion$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            switch (i2) {
                                case 0:
                                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No label found for package: ", logMessage.getStr1());
                                default:
                                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error getting appName for package=", logMessage.getStr1());
                            }
                        }
                    };
                    LogBuffer logBuffer2 = castControllerLogger.logger;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("#getAppName", logLevel2, function12, e);
                    ((LogMessageImpl) logMessageObtain2).str1 = packageName2;
                    logBuffer2.commit(logMessageObtain2);
                }
            }
            return new CastDevice(packageName, packageName2, context.getString(R.string.quick_settings_casting), CastState.Connected, CastOrigin.MediaProjection, mediaProjectionInfo);
        }
    }

    public CastDevice(String str, String str2, String str3, CastState castState, CastOrigin castOrigin, Object obj) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.state = castState;
        this.origin = castOrigin;
        this.tag = obj;
        this.isCasting = castState == CastState.Connecting || castState == CastState.Connected;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("CastDevice(id=", str, " name=", str2, " description=");
        sbM.append(str3);
        sbM.append(" state=");
        sbM.append(castState);
        sbM.append(" origin=");
        sbM.append(castOrigin);
        sbM.append(")");
        this.shortLogString = sbM.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CastDevice)) {
            return false;
        }
        CastDevice castDevice = (CastDevice) obj;
        return Intrinsics.areEqual(this.id, castDevice.id) && Intrinsics.areEqual(this.name, castDevice.name) && Intrinsics.areEqual(this.description, castDevice.description) && this.state == castDevice.state && this.origin == castDevice.origin && Intrinsics.areEqual(this.tag, castDevice.tag);
    }

    public final int hashCode() {
        int iHashCode = this.id.hashCode() * 31;
        String str = this.name;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.description;
        int iHashCode3 = (this.origin.hashCode() + ((this.state.hashCode() + ((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31)) * 31)) * 31;
        Object obj = this.tag;
        return iHashCode3 + (obj != null ? obj.hashCode() : 0);
    }

    public final String toString() {
        return "CastDevice(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", state=" + this.state + ", origin=" + this.origin + ", tag=" + this.tag + ")";
    }

    public /* synthetic */ CastDevice(String str, String str2, String str3, CastState castState, CastOrigin castOrigin, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : str3, castState, castOrigin, (i & 32) != 0 ? null : obj);
    }
}
