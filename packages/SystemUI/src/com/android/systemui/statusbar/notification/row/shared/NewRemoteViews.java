package com.android.systemui.statusbar.notification.row.shared;

import android.widget.RemoteViews;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NewRemoteViews {
    public final RemoteViews contracted;
    public final RemoteViews expanded;
    public final RemoteViews headsUp;
    public final RemoteViews promotedView;

    /* renamed from: public, reason: not valid java name */
    public final RemoteViews f107public;

    public NewRemoteViews() {
        this(null, null, null, null, null, null, null, 127, null);
    }

    public NewRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, RemoteViews remoteViews5, RemoteViews remoteViews6, RemoteViews remoteViews7) {
        this.contracted = remoteViews;
        this.headsUp = remoteViews2;
        this.expanded = remoteViews3;
        this.f107public = remoteViews4;
        this.promotedView = remoteViews7;
    }

    public /* synthetic */ NewRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, RemoteViews remoteViews5, RemoteViews remoteViews6, RemoteViews remoteViews7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : remoteViews, (i & 2) != 0 ? null : remoteViews2, (i & 4) != 0 ? null : remoteViews3, (i & 8) != 0 ? null : remoteViews4, (i & 16) != 0 ? null : remoteViews5, (i & 32) != 0 ? null : remoteViews6, (i & 64) != 0 ? null : remoteViews7);
    }
}
