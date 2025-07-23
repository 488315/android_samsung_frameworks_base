package com.android.systemui.communal.ui.viewmodel;

import android.appwidget.AppWidgetHost;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SetListener implements Request {
    public final int appWidgetId;
    public final AppWidgetHost.AppWidgetHostListener listener;

    public SetListener(int i, AppWidgetHost.AppWidgetHostListener appWidgetHostListener) {
        this.appWidgetId = i;
        this.listener = appWidgetHostListener;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SetListener)) {
            return false;
        }
        SetListener setListener = (SetListener) obj;
        return this.appWidgetId == setListener.appWidgetId && Intrinsics.areEqual(this.listener, setListener.listener);
    }

    public final int hashCode() {
        return this.listener.hashCode() + (Integer.hashCode(this.appWidgetId) * 31);
    }

    public final String toString() {
        return "SetListener(appWidgetId=" + this.appWidgetId + ", listener=" + this.listener + ")";
    }
}
