package com.android.systemui.p016qs.cinema;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.logging.PanelScreenShotLogger;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSCinemaLogger {
    public final QSCinemaProvider mProvider;
    public ArrayList mTmpLog = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScreenShotLogProvider implements PanelScreenShotLogger.LogProvider {
        public /* synthetic */ ScreenShotLogProvider(QSCinemaLogger qSCinemaLogger, int i) {
            this();
        }

        @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
        public final ArrayList gatherState() {
            QSCinemaLogger qSCinemaLogger = QSCinemaLogger.this;
            qSCinemaLogger.getClass();
            qSCinemaLogger.mTmpLog = new ArrayList(Arrays.asList("QSCinemaLogger ## Quickpanel View State of Screen Capture ########"));
            View view = qSCinemaLogger.mProvider.mQs.getView();
            if (view != null && (view instanceof ViewGroup)) {
                qSCinemaLogger.visitLayoutTreeToAssembleLogLine((ViewGroup) view, 0);
            }
            return qSCinemaLogger.mTmpLog;
        }

        private ScreenShotLogProvider() {
        }
    }

    public QSCinemaLogger(QSCinemaProvider qSCinemaProvider) {
        this.mProvider = qSCinemaProvider;
    }

    public final void visitLayoutTreeToAssembleLogLine(ViewGroup viewGroup, int i) {
        for (int i2 = 0; i2 < viewGroup.getChildCount() && i2 <= 50 && i <= 20; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt != null) {
                ArrayList arrayList = this.mTmpLog;
                StringBuilder sb = new StringBuilder("QSCinemaLogger ");
                for (int i3 = 0; i3 < i; i3++) {
                    sb.append("  | ");
                }
                sb.append(" idx=" + i2);
                sb.append(":::" + childAt.toString());
                sb.append(", w:" + childAt.getWidth());
                sb.append(", mw:" + childAt.getMeasuredWidth());
                sb.append(", x:" + childAt.getX());
                sb.append(", px:" + childAt.getPivotX());
                sb.append(", tx:" + childAt.getTranslationX());
                sb.append(", lr:" + childAt.isLayoutRequested());
                sb.append(", clickable:" + childAt.isClickable());
                sb.append(", focusable:" + childAt.isFocusable());
                sb.append(", a:" + childAt.getAlpha());
                arrayList.add(sb.toString());
                if (childAt instanceof ViewGroup) {
                    visitLayoutTreeToAssembleLogLine((ViewGroup) childAt, i + 1);
                }
            }
        }
    }
}
