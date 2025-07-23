package com.android.systemui.qs.tiles.base.shared.logging;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSTileLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBufferFactory factory;
    public final Map logBufferCache;
    public final StatusBarStateController mStatusBarStateController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, qSTileState.expandedAccessibilityClassName, "]");
    }

    public final LogBuffer getLogBuffer(TileSpec tileSpec) {
        LogBuffer logBuffer;
        synchronized (this.logBufferCache) {
            try {
                LinkedHashMap linkedHashMap = (LinkedHashMap) this.logBufferCache;
                Object obj = linkedHashMap.get(tileSpec);
                if (obj == null) {
                    obj = LogBufferFactory.create$default(this.factory, getLogTag(tileSpec), 25, false, null, 24);
                    linkedHashMap.put(tileSpec, obj);
                }
                logBuffer = (LogBuffer) obj;
            } catch (Throwable th) {
                throw th;
            }
        }
        return logBuffer;
    }

    public final void logError(TileSpec tileSpec, String str, Throwable th) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        logBuffer.commit(logBuffer.obtain(getLogTag(tileSpec), LogLevel.ERROR, new QSTileLogger$$ExternalSyntheticLambda1(str, 0), th));
    }

    public final void logInfo(String str, TileSpec tileSpec) {
        LogBuffer logBuffer = getLogBuffer(tileSpec);
        LogMessage obtain = logBuffer.obtain(getLogTag(tileSpec), LogLevel.INFO, new QSTileLogger$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public static String toLogString(QSTileUserAction qSTileUserAction) {
        if (qSTileUserAction instanceof QSTileUserAction.Click) {
            return "click";
        }
        if (qSTileUserAction instanceof QSTileUserAction.ToggleClick) {
            return "toggle click";
        }
        if (qSTileUserAction instanceof QSTileUserAction.LongClick) {
            return "long click";
        }
        throw new NoWhenBranchMatchedException();
    }
}
