package com.android.systemui.statusbar.notification.row.shared;

import android.widget.RemoteViews;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NewRemoteViews {
    public final RemoteViews contracted;
    public final RemoteViews expanded;
    public final RemoteViews headsUp;
    public final RemoteViews minimizedGroupHeader;
    public final RemoteViews normalGroupHeader;

    /* renamed from: public, reason: not valid java name */
    public final RemoteViews f77public;

    public NewRemoteViews() {
        this(null, null, null, null, null, null, 63, null);
    }

    public NewRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, RemoteViews remoteViews5, RemoteViews remoteViews6) {
        this.contracted = remoteViews;
        this.headsUp = remoteViews2;
        this.expanded = remoteViews3;
        this.f77public = remoteViews4;
    }

    public /* synthetic */ NewRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, RemoteViews remoteViews5, RemoteViews remoteViews6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : remoteViews, (i & 2) != 0 ? null : remoteViews2, (i & 4) != 0 ? null : remoteViews3, (i & 8) != 0 ? null : remoteViews4, (i & 16) != 0 ? null : remoteViews5, (i & 32) != 0 ? null : remoteViews6);
    }
}
