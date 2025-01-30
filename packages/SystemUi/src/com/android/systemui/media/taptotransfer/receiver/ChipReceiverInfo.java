package com.android.systemui.media.taptotransfer.receiver;

import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.temporarydisplay.TemporaryViewInfo;
import com.android.systemui.temporarydisplay.ViewPriority;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ChipReceiverInfo extends TemporaryViewInfo {
    public final Drawable appIconDrawableOverride;
    public final CharSequence appNameOverride;

    /* renamed from: id */
    public final String f312id;
    public final InstanceId instanceId;
    public final ViewPriority priority;
    public final MediaRoute2Info routeInfo;
    public final String wakeReason;
    public final String windowTitle;

    public /* synthetic */ ChipReceiverInfo(MediaRoute2Info mediaRoute2Info, Drawable drawable, CharSequence charSequence, String str, String str2, String str3, ViewPriority viewPriority, InstanceId instanceId, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mediaRoute2Info, drawable, charSequence, (i & 8) != 0 ? "Media Transfer Chip View (Receiver)" : str, (i & 16) != 0 ? "MEDIA_TRANSFER_ACTIVATED_RECEIVER" : str2, str3, (i & 64) != 0 ? ViewPriority.NORMAL : viewPriority, instanceId);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChipReceiverInfo)) {
            return false;
        }
        ChipReceiverInfo chipReceiverInfo = (ChipReceiverInfo) obj;
        return Intrinsics.areEqual(this.routeInfo, chipReceiverInfo.routeInfo) && Intrinsics.areEqual(this.appIconDrawableOverride, chipReceiverInfo.appIconDrawableOverride) && Intrinsics.areEqual(this.appNameOverride, chipReceiverInfo.appNameOverride) && Intrinsics.areEqual(this.windowTitle, chipReceiverInfo.windowTitle) && Intrinsics.areEqual(this.wakeReason, chipReceiverInfo.wakeReason) && Intrinsics.areEqual(this.f312id, chipReceiverInfo.f312id) && this.priority == chipReceiverInfo.priority && Intrinsics.areEqual(this.instanceId, chipReceiverInfo.instanceId);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewInfo
    public final String getId() {
        return this.f312id;
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewInfo
    public final InstanceId getInstanceId() {
        return this.instanceId;
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewInfo
    public final ViewPriority getPriority() {
        return this.priority;
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewInfo
    public final String getWakeReason() {
        return this.wakeReason;
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewInfo
    public final String getWindowTitle() {
        return this.windowTitle;
    }

    public final int hashCode() {
        int hashCode = this.routeInfo.hashCode() * 31;
        Drawable drawable = this.appIconDrawableOverride;
        int hashCode2 = (hashCode + (drawable == null ? 0 : drawable.hashCode())) * 31;
        CharSequence charSequence = this.appNameOverride;
        return this.instanceId.hashCode() + ((this.priority.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.f312id, AppInfo$$ExternalSyntheticOutline0.m41m(this.wakeReason, AppInfo$$ExternalSyntheticOutline0.m41m(this.windowTitle, (hashCode2 + (charSequence != null ? charSequence.hashCode() : 0)) * 31, 31), 31), 31)) * 31);
    }

    public final String toString() {
        return "ChipReceiverInfo(routeInfo=" + this.routeInfo + ", appIconDrawableOverride=" + this.appIconDrawableOverride + ", appNameOverride=" + ((Object) this.appNameOverride) + ", windowTitle=" + this.windowTitle + ", wakeReason=" + this.wakeReason + ", id=" + this.f312id + ", priority=" + this.priority + ", instanceId=" + this.instanceId + ")";
    }

    public ChipReceiverInfo(MediaRoute2Info mediaRoute2Info, Drawable drawable, CharSequence charSequence, String str, String str2, String str3, ViewPriority viewPriority, InstanceId instanceId) {
        this.routeInfo = mediaRoute2Info;
        this.appIconDrawableOverride = drawable;
        this.appNameOverride = charSequence;
        this.windowTitle = str;
        this.wakeReason = str2;
        this.f312id = str3;
        this.priority = viewPriority;
        this.instanceId = instanceId;
    }
}
