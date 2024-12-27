package com.android.systemui.qs.tiles.base.logging;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor;
import com.android.systemui.qs.tiles.impl.night.ui.NightDisplayTileMapper;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUserAction;
import com.android.systemui.statusbar.StatusBarState;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt___StringsKt;

public final class QSTileLogger {
    public final LogBufferFactory factory;
    public final Map logBufferCache;
    public final StatusBarStateController mStatusBarStateController;

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

    public QSTileLogger(Map<TileSpec, LogBuffer> map, LogBufferFactory logBufferFactory, StatusBarStateController statusBarStateController) {
        this.factory = logBufferFactory;
        this.mStatusBarStateController = statusBarStateController;
        this.logBufferCache = new LinkedHashMap(map);
    }

    public static String getLogTag(TileSpec tileSpec) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("QSLog_tile__", tileSpec.getSpec());
    }

    public static String toLogString(QSTileState qSTileState) {
        CharSequence charSequence = qSTileState.label;
        CharSequence charSequence2 = qSTileState.secondaryLabel;
        CharSequence charSequence3 = qSTileState.contentDescription;
        CharSequence charSequence4 = qSTileState.stateDescription;
        StringBuilder sb = new StringBuilder("[label=");
        sb.append((Object) charSequence);
        sb.append(", state=");
        sb.append(qSTileState.activationState);
        sb.append(", s_label=");
        sb.append((Object) charSequence2);
        sb.append(", cd=");
        sb.append((Object) charSequence3);
        sb.append(", sd=");
        sb.append((Object) charSequence4);
        sb.append(", svi=");
        sb.append(qSTileState.sideViewIcon);
        sb.append(", enabled=");
        sb.append(qSTileState.enabledState);
        sb.append(", a11y=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, qSTileState.expandedAccessibilityClassName, "]");
    }

    public final LogBuffer getLogBuffer(TileSpec tileSpec) {
        LogBuffer logBuffer;
        synchronized (this.logBufferCache) {
            try {
                LinkedHashMap linkedHashMap = (LinkedHashMap) this.logBufferCache;
                Object obj = linkedHashMap.get(tileSpec);
                if (obj == null) {
                    obj = this.factory.create(25, getLogTag(tileSpec), false);
                    linkedHashMap.put(tileSpec, obj);
                }
                logBuffer = (LogBuffer) obj;
            } catch (Throwable th) {
                throw th;
            }
        }
        return logBuffer;
    }

    public final void logCustomTileUserActionDelivered(TileSpec tileSpec) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        logBuffer.commit(logBuffer.obtain(getLogTag(tileSpec), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logCustomTileUserActionDelivered$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "user action delivered to the service";
            }
        }, null));
    }

    public final void logError(TileSpec tileSpec, final String str, Throwable th) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        logBuffer.commit(logBuffer.obtain(getLogTag(tileSpec), LogLevel.ERROR, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logError$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return str;
            }
        }, th));
    }

    public final void logForceUpdate(TileSpec tileSpec) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        logBuffer.commit(logBuffer.obtain(getLogTag(tileSpec), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logForceUpdate$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "tile data force update";
            }
        }, null));
    }

    public final void logInfo() {
        TileSpec tileSpec = NightDisplayTileUserActionInteractor.spec;
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(getLogTag(tileSpec), LogLevel.INFO, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logInfo$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String str1 = ((LogMessage) obj).getStr1();
                Intrinsics.checkNotNull(str1);
                return str1;
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = "Enrolled in forced night display auto mode";
        logBuffer.commit(obtain);
    }

    public final void logInitialRequest(TileSpec tileSpec) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        logBuffer.commit(logBuffer.obtain(getLogTag(tileSpec), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logInitialRequest$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "tile data initial update";
            }
        }, null));
    }

    public final void logStateUpdate(TileSpec tileSpec, QSTileState qSTileState, Object obj) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(getLogTag(tileSpec), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logStateUpdate$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                return FontProvider$$ExternalSyntheticOutline0.m("tile state update: state=", logMessage.getStr1(), ", data=", logMessage.getStr2());
            }
        }, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = toLogString(qSTileState);
        logMessageImpl.str2 = StringsKt___StringsKt.take(50, String.valueOf(obj));
        logBuffer.commit(obtain);
    }

    public final void logUserAction(QSTileUserAction qSTileUserAction, TileSpec tileSpec, boolean z, boolean z2) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(getLogTag(tileSpec), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logUserAction$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String statusBarState = StatusBarState.toString(logMessage.getInt1());
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("tile ", str1, ": statusBarState=", statusBarState, ", hasState="), logMessage.getBool1(), ", hasData=", logMessage.getBool2());
            }
        }, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = toLogString(qSTileUserAction);
        logMessageImpl.int1 = this.mStatusBarStateController.getState();
        logMessageImpl.bool1 = z2;
        logMessageImpl.bool2 = z;
        logBuffer.commit(obtain);
    }

    public final void logUserActionPipeline(TileSpec tileSpec, QSTileUserAction qSTileUserAction, QSTileState qSTileState, Object obj) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(getLogTag(tileSpec), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logUserActionPipeline$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                String str1 = logMessage.getStr1();
                String statusBarState = StatusBarState.toString(logMessage.getInt1());
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("tile ", str1, " pipeline: statusBarState=", statusBarState, ", state="), logMessage.getStr2(), ", data=", logMessage.getStr3());
            }
        }, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = toLogString(qSTileUserAction);
        logMessageImpl.str2 = toLogString(qSTileState);
        logMessageImpl.str3 = StringsKt___StringsKt.take(50, String.valueOf(obj));
        logBuffer.commit(obtain);
    }

    public final void logUserActionRejectedByFalsing(QSTileUserAction qSTileUserAction, TileSpec tileSpec) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(getLogTag(tileSpec), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logUserActionRejectedByFalsing$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("tile ", ((LogMessage) obj).getStr1(), ": rejected by falsing");
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = toLogString(qSTileUserAction);
        logBuffer.commit(obtain);
    }

    public final void logUserActionRejectedByPolicy(QSTileUserAction qSTileUserAction, TileSpec tileSpec, final String str) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(getLogTag(tileSpec), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logUserActionRejectedByPolicy$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return FontProvider$$ExternalSyntheticOutline0.m("tile ", ((LogMessage) obj).getStr1(), ": rejected by policy, restriction: ", str);
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = toLogString(qSTileUserAction);
        logBuffer.commit(obtain);
    }

    public final void logWarning(String str) {
        TileSpec tileSpec = NightDisplayTileMapper.spec;
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(getLogTag(tileSpec), LogLevel.WARNING, new Function1() { // from class: com.android.systemui.qs.tiles.base.logging.QSTileLogger$logWarning$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String str1 = ((LogMessage) obj).getStr1();
                Intrinsics.checkNotNull(str1);
                return str1;
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public static String toLogString(QSTileUserAction qSTileUserAction) {
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            return "click";
        }
        if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            return "long click";
        }
        throw new NoWhenBranchMatchedException();
    }
}
