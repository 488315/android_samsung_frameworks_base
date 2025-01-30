package com.android.systemui.media.controls.models.player;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaDeviceData {
    public final SecMediaDeviceDataImpl customMediaDeviceData;
    public final boolean enabled;
    public final Drawable icon;

    /* renamed from: id */
    public final String f306id;
    public final PendingIntent intent;
    public final CharSequence name;
    public final boolean showBroadcastButton;

    public MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, PendingIntent pendingIntent, String str, boolean z2) {
        this(z, drawable, charSequence, pendingIntent, str, z2, null, 64, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaDeviceData)) {
            return false;
        }
        MediaDeviceData mediaDeviceData = (MediaDeviceData) obj;
        return this.enabled == mediaDeviceData.enabled && Intrinsics.areEqual(this.icon, mediaDeviceData.icon) && Intrinsics.areEqual(this.name, mediaDeviceData.name) && Intrinsics.areEqual(this.intent, mediaDeviceData.intent) && Intrinsics.areEqual(this.f306id, mediaDeviceData.f306id) && this.showBroadcastButton == mediaDeviceData.showBroadcastButton && Intrinsics.areEqual(this.customMediaDeviceData, mediaDeviceData.customMediaDeviceData);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.enabled;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        Drawable drawable = this.icon;
        int hashCode = (i2 + (drawable == null ? 0 : drawable.hashCode())) * 31;
        CharSequence charSequence = this.name;
        int hashCode2 = (hashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        PendingIntent pendingIntent = this.intent;
        int hashCode3 = (hashCode2 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        String str = this.f306id;
        int hashCode4 = (hashCode3 + (str != null ? str.hashCode() : 0)) * 31;
        boolean z2 = this.showBroadcastButton;
        return this.customMediaDeviceData.hashCode() + ((hashCode4 + (z2 ? 1 : z2 ? 1 : 0)) * 31);
    }

    public final String toString() {
        return "MediaDeviceData(enabled=" + this.enabled + ", icon=" + this.icon + ", name=" + ((Object) this.name) + ", intent=" + this.intent + ", id=" + this.f306id + ", showBroadcastButton=" + this.showBroadcastButton + ", customMediaDeviceData=" + this.customMediaDeviceData + ")";
    }

    public MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, PendingIntent pendingIntent, boolean z2) {
        this(z, drawable, charSequence, pendingIntent, null, z2, null, 80, null);
    }

    public MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, boolean z2) {
        this(z, drawable, charSequence, null, null, z2, null, 88, null);
    }

    public MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, PendingIntent pendingIntent, String str, boolean z2, SecMediaDeviceDataImpl secMediaDeviceDataImpl) {
        this.enabled = z;
        this.icon = drawable;
        this.name = charSequence;
        this.intent = pendingIntent;
        this.f306id = str;
        this.showBroadcastButton = z2;
        this.customMediaDeviceData = secMediaDeviceDataImpl;
    }

    public /* synthetic */ MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, PendingIntent pendingIntent, String str, boolean z2, SecMediaDeviceDataImpl secMediaDeviceDataImpl, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, drawable, charSequence, (i & 8) != 0 ? null : pendingIntent, (i & 16) != 0 ? null : str, z2, (i & 64) != 0 ? new SecMediaDeviceDataImpl(0) : secMediaDeviceDataImpl);
    }
}
