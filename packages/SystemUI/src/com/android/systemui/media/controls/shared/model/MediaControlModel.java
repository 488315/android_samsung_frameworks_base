package com.android.systemui.media.controls.shared.model;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaControlModel {
    public final List actionsToShowInCollapsed;
    public final Icon appIcon;
    public final String appName;
    public final CharSequence artistName;
    public final Icon artwork;
    public final PendingIntent clickIntent;
    public final MediaDeviceData deviceData;
    public final InstanceId instanceId;
    public final boolean isDismissible;
    public final boolean isResume;
    public final List notificationActionButtons;
    public final String packageName;
    public final Double resumeProgress;
    public final MediaButton semanticActionButtons;
    public final boolean showExplicit;
    public final CharSequence songName;
    public final MediaSession.Token token;
    public final int uid;

    public MediaControlModel(int i, String str, InstanceId instanceId, MediaSession.Token token, Icon icon, PendingIntent pendingIntent, String str2, CharSequence charSequence, CharSequence charSequence2, boolean z, Icon icon2, MediaDeviceData mediaDeviceData, MediaButton mediaButton, List<MediaAction> list, List<Integer> list2, boolean z2, boolean z3, Double d) {
        this.uid = i;
        this.packageName = str;
        this.instanceId = instanceId;
        this.token = token;
        this.appIcon = icon;
        this.clickIntent = pendingIntent;
        this.appName = str2;
        this.songName = charSequence;
        this.artistName = charSequence2;
        this.showExplicit = z;
        this.artwork = icon2;
        this.deviceData = mediaDeviceData;
        this.semanticActionButtons = mediaButton;
        this.notificationActionButtons = list;
        this.actionsToShowInCollapsed = list2;
        this.isDismissible = z2;
        this.isResume = z3;
        this.resumeProgress = d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaControlModel)) {
            return false;
        }
        MediaControlModel mediaControlModel = (MediaControlModel) obj;
        return this.uid == mediaControlModel.uid && Intrinsics.areEqual(this.packageName, mediaControlModel.packageName) && Intrinsics.areEqual(this.instanceId, mediaControlModel.instanceId) && Intrinsics.areEqual(this.token, mediaControlModel.token) && Intrinsics.areEqual(this.appIcon, mediaControlModel.appIcon) && Intrinsics.areEqual(this.clickIntent, mediaControlModel.clickIntent) && Intrinsics.areEqual(this.appName, mediaControlModel.appName) && Intrinsics.areEqual(this.songName, mediaControlModel.songName) && Intrinsics.areEqual(this.artistName, mediaControlModel.artistName) && this.showExplicit == mediaControlModel.showExplicit && Intrinsics.areEqual(this.artwork, mediaControlModel.artwork) && Intrinsics.areEqual(this.deviceData, mediaControlModel.deviceData) && Intrinsics.areEqual(this.semanticActionButtons, mediaControlModel.semanticActionButtons) && Intrinsics.areEqual(this.notificationActionButtons, mediaControlModel.notificationActionButtons) && Intrinsics.areEqual(this.actionsToShowInCollapsed, mediaControlModel.actionsToShowInCollapsed) && this.isDismissible == mediaControlModel.isDismissible && this.isResume == mediaControlModel.isResume && Intrinsics.areEqual(this.resumeProgress, mediaControlModel.resumeProgress);
    }

    public final int hashCode() {
        int hashCode = (this.instanceId.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.uid) * 31, 31, this.packageName)) * 31;
        MediaSession.Token token = this.token;
        int hashCode2 = (hashCode + (token == null ? 0 : token.hashCode())) * 31;
        Icon icon = this.appIcon;
        int hashCode3 = (hashCode2 + (icon == null ? 0 : icon.hashCode())) * 31;
        PendingIntent pendingIntent = this.clickIntent;
        int hashCode4 = (hashCode3 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        String str = this.appName;
        int hashCode5 = (hashCode4 + (str == null ? 0 : str.hashCode())) * 31;
        CharSequence charSequence = this.songName;
        int hashCode6 = (hashCode5 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.artistName;
        int m = TransitionData$$ExternalSyntheticOutline0.m((hashCode6 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31, 31, this.showExplicit);
        Icon icon2 = this.artwork;
        int hashCode7 = (m + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        MediaDeviceData mediaDeviceData = this.deviceData;
        int hashCode8 = (hashCode7 + (mediaDeviceData == null ? 0 : mediaDeviceData.hashCode())) * 31;
        MediaButton mediaButton = this.semanticActionButtons;
        int m2 = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.actionsToShowInCollapsed, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.notificationActionButtons, (hashCode8 + (mediaButton == null ? 0 : mediaButton.hashCode())) * 31, 31), 31), 31, this.isDismissible), 31, this.isResume);
        Double d = this.resumeProgress;
        return m2 + (d != null ? d.hashCode() : 0);
    }

    public final String toString() {
        InstanceId instanceId = this.instanceId;
        MediaSession.Token token = this.token;
        Icon icon = this.appIcon;
        PendingIntent pendingIntent = this.clickIntent;
        CharSequence charSequence = this.songName;
        CharSequence charSequence2 = this.artistName;
        return "MediaControlModel(uid=" + this.uid + ", packageName=" + this.packageName + ", instanceId=" + instanceId + ", token=" + token + ", appIcon=" + icon + ", clickIntent=" + pendingIntent + ", appName=" + this.appName + ", songName=" + ((Object) charSequence) + ", artistName=" + ((Object) charSequence2) + ", showExplicit=" + this.showExplicit + ", artwork=" + this.artwork + ", deviceData=" + this.deviceData + ", semanticActionButtons=" + this.semanticActionButtons + ", notificationActionButtons=" + this.notificationActionButtons + ", actionsToShowInCollapsed=" + this.actionsToShowInCollapsed + ", isDismissible=" + this.isDismissible + ", isResume=" + this.isResume + ", resumeProgress=" + this.resumeProgress + ")";
    }

    public /* synthetic */ MediaControlModel(int i, String str, InstanceId instanceId, MediaSession.Token token, Icon icon, PendingIntent pendingIntent, String str2, CharSequence charSequence, CharSequence charSequence2, boolean z, Icon icon2, MediaDeviceData mediaDeviceData, MediaButton mediaButton, List list, List list2, boolean z2, boolean z3, Double d, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? -1 : i, str, instanceId, token, icon, pendingIntent, str2, charSequence, charSequence2, z, icon2, mediaDeviceData, mediaButton, list, list2, z2, z3, d);
    }
}
