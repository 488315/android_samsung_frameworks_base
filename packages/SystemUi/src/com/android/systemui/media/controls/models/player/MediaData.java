package com.android.systemui.media.controls.models.player;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.app.motiontool.TraceMetadata$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.resume.MediaResumeListener$getResumeAction$1;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaData {
    public final List actions;
    public final List actionsToShowInCompact;
    public boolean active;
    public final String app;
    public final Icon appIcon;
    public final int appUid;
    public final CharSequence artist;
    public final Icon artwork;
    public final PendingIntent clickIntent;
    public final SecMediaDataImpl customMediaData;
    public final MediaDeviceData device;
    public boolean hasCheckedForResume;
    public final boolean initialized;
    public final InstanceId instanceId;
    public final boolean isClearable;
    public final boolean isExplicit;
    public final Boolean isPlaying;
    public final long lastActive;
    public final String notificationKey;
    public final String packageName;
    public final int playbackLocation;
    public Runnable resumeAction;
    public final Double resumeProgress;
    public final boolean resumption;
    public final MediaButton semanticActions;
    public final CharSequence song;
    public final MediaSession.Token token;
    public final int userId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public MediaData(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List<MediaAction> list, List<Integer> list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, InstanceId instanceId, int i3, boolean z6, Double d, SecMediaDataImpl secMediaDataImpl) {
        this.userId = i;
        this.initialized = z;
        this.app = str;
        this.appIcon = icon;
        this.artist = charSequence;
        this.song = charSequence2;
        this.artwork = icon2;
        this.actions = list;
        this.actionsToShowInCompact = list2;
        this.semanticActions = mediaButton;
        this.packageName = str2;
        this.token = token;
        this.clickIntent = pendingIntent;
        this.device = mediaDeviceData;
        this.active = z2;
        this.resumeAction = runnable;
        this.playbackLocation = i2;
        this.resumption = z3;
        this.notificationKey = str3;
        this.hasCheckedForResume = z4;
        this.isPlaying = bool;
        this.isClearable = z5;
        this.lastActive = j;
        this.instanceId = instanceId;
        this.appUid = i3;
        this.isExplicit = z6;
        this.resumeProgress = d;
        this.customMediaData = secMediaDataImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v42, types: [java.lang.Runnable] */
    public static MediaData copy$default(MediaData mediaData, List list, List list2, MediaButton mediaButton, String str, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z, MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1, boolean z2, boolean z3, Boolean bool, boolean z4, InstanceId instanceId, int i, int i2) {
        int i3 = (i2 & 1) != 0 ? mediaData.userId : 0;
        boolean z5 = (i2 & 2) != 0 ? mediaData.initialized : false;
        String str2 = (i2 & 4) != 0 ? mediaData.app : null;
        Icon icon = (i2 & 8) != 0 ? mediaData.appIcon : null;
        CharSequence charSequence = (i2 & 16) != 0 ? mediaData.artist : null;
        CharSequence charSequence2 = (i2 & 32) != 0 ? mediaData.song : null;
        Icon icon2 = (i2 & 64) != 0 ? mediaData.artwork : null;
        List list3 = (i2 & 128) != 0 ? mediaData.actions : list;
        List list4 = (i2 & 256) != 0 ? mediaData.actionsToShowInCompact : list2;
        MediaButton mediaButton2 = (i2 & 512) != 0 ? mediaData.semanticActions : mediaButton;
        String str3 = (i2 & 1024) != 0 ? mediaData.packageName : str;
        MediaSession.Token token = (i2 & 2048) != 0 ? mediaData.token : null;
        PendingIntent pendingIntent2 = (i2 & 4096) != 0 ? mediaData.clickIntent : pendingIntent;
        MediaDeviceData mediaDeviceData2 = (i2 & 8192) != 0 ? mediaData.device : mediaDeviceData;
        boolean z6 = (i2 & 16384) != 0 ? mediaData.active : z;
        MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$12 = (32768 & i2) != 0 ? mediaData.resumeAction : mediaResumeListener$getResumeAction$1;
        int i4 = (65536 & i2) != 0 ? mediaData.playbackLocation : 0;
        boolean z7 = (131072 & i2) != 0 ? mediaData.resumption : z2;
        String str4 = (262144 & i2) != 0 ? mediaData.notificationKey : null;
        boolean z8 = (524288 & i2) != 0 ? mediaData.hasCheckedForResume : z3;
        Boolean bool2 = (1048576 & i2) != 0 ? mediaData.isPlaying : bool;
        boolean z9 = (2097152 & i2) != 0 ? mediaData.isClearable : z4;
        long j = (4194304 & i2) != 0 ? mediaData.lastActive : 0L;
        InstanceId instanceId2 = (8388608 & i2) != 0 ? mediaData.instanceId : instanceId;
        int i5 = (16777216 & i2) != 0 ? mediaData.appUid : i;
        boolean z10 = (33554432 & i2) != 0 ? mediaData.isExplicit : false;
        Double d = (67108864 & i2) != 0 ? mediaData.resumeProgress : null;
        SecMediaDataImpl secMediaDataImpl = (i2 & 134217728) != 0 ? mediaData.customMediaData : null;
        mediaData.getClass();
        return new MediaData(i3, z5, str2, icon, charSequence, charSequence2, icon2, list3, list4, mediaButton2, str3, token, pendingIntent2, mediaDeviceData2, z6, mediaResumeListener$getResumeAction$12, i4, z7, str4, z8, bool2, z9, j, instanceId2, i5, z10, d, secMediaDataImpl);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaData)) {
            return false;
        }
        MediaData mediaData = (MediaData) obj;
        return this.userId == mediaData.userId && this.initialized == mediaData.initialized && Intrinsics.areEqual(this.app, mediaData.app) && Intrinsics.areEqual(this.appIcon, mediaData.appIcon) && Intrinsics.areEqual(this.artist, mediaData.artist) && Intrinsics.areEqual(this.song, mediaData.song) && Intrinsics.areEqual(this.artwork, mediaData.artwork) && Intrinsics.areEqual(this.actions, mediaData.actions) && Intrinsics.areEqual(this.actionsToShowInCompact, mediaData.actionsToShowInCompact) && Intrinsics.areEqual(this.semanticActions, mediaData.semanticActions) && Intrinsics.areEqual(this.packageName, mediaData.packageName) && Intrinsics.areEqual(this.token, mediaData.token) && Intrinsics.areEqual(this.clickIntent, mediaData.clickIntent) && Intrinsics.areEqual(this.device, mediaData.device) && this.active == mediaData.active && Intrinsics.areEqual(this.resumeAction, mediaData.resumeAction) && this.playbackLocation == mediaData.playbackLocation && this.resumption == mediaData.resumption && Intrinsics.areEqual(this.notificationKey, mediaData.notificationKey) && this.hasCheckedForResume == mediaData.hasCheckedForResume && Intrinsics.areEqual(this.isPlaying, mediaData.isPlaying) && this.isClearable == mediaData.isClearable && this.lastActive == mediaData.lastActive && Intrinsics.areEqual(this.instanceId, mediaData.instanceId) && this.appUid == mediaData.appUid && this.isExplicit == mediaData.isExplicit && Intrinsics.areEqual(this.resumeProgress, mediaData.resumeProgress) && Intrinsics.areEqual(this.customMediaData, mediaData.customMediaData);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = Integer.hashCode(this.userId) * 31;
        boolean z = this.initialized;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        String str = this.app;
        int hashCode2 = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        Icon icon = this.appIcon;
        int hashCode3 = (hashCode2 + (icon == null ? 0 : icon.hashCode())) * 31;
        CharSequence charSequence = this.artist;
        int hashCode4 = (hashCode3 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.song;
        int hashCode5 = (hashCode4 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        Icon icon2 = this.artwork;
        int hashCode6 = (this.actionsToShowInCompact.hashCode() + ((this.actions.hashCode() + ((hashCode5 + (icon2 == null ? 0 : icon2.hashCode())) * 31)) * 31)) * 31;
        MediaButton mediaButton = this.semanticActions;
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.packageName, (hashCode6 + (mediaButton == null ? 0 : mediaButton.hashCode())) * 31, 31);
        MediaSession.Token token = this.token;
        int hashCode7 = (m41m + (token == null ? 0 : token.hashCode())) * 31;
        PendingIntent pendingIntent = this.clickIntent;
        int hashCode8 = (hashCode7 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        MediaDeviceData mediaDeviceData = this.device;
        int hashCode9 = (hashCode8 + (mediaDeviceData == null ? 0 : mediaDeviceData.hashCode())) * 31;
        boolean z2 = this.active;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (hashCode9 + i3) * 31;
        Runnable runnable = this.resumeAction;
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.playbackLocation, (i4 + (runnable == null ? 0 : runnable.hashCode())) * 31, 31);
        boolean z3 = this.resumption;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (m42m + i5) * 31;
        String str2 = this.notificationKey;
        int hashCode10 = (i6 + (str2 == null ? 0 : str2.hashCode())) * 31;
        boolean z4 = this.hasCheckedForResume;
        int i7 = z4;
        if (z4 != 0) {
            i7 = 1;
        }
        int i8 = (hashCode10 + i7) * 31;
        Boolean bool = this.isPlaying;
        int hashCode11 = (i8 + (bool == null ? 0 : bool.hashCode())) * 31;
        boolean z5 = this.isClearable;
        int i9 = z5;
        if (z5 != 0) {
            i9 = 1;
        }
        int m42m2 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.appUid, (this.instanceId.hashCode() + TraceMetadata$$ExternalSyntheticOutline0.m51m(this.lastActive, (hashCode11 + i9) * 31, 31)) * 31, 31);
        boolean z6 = this.isExplicit;
        int i10 = (m42m2 + (z6 ? 1 : z6 ? 1 : 0)) * 31;
        Double d = this.resumeProgress;
        return this.customMediaData.hashCode() + ((i10 + (d != null ? d.hashCode() : 0)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [ USERID : " + this.userId + " ]");
        sb.append(" [ INITIALIZED : " + this.initialized + " ]");
        String str = this.app;
        if (str != null) {
            sb.append(" [ APP : " + str + " ]");
        }
        CharSequence charSequence = this.artist;
        if (charSequence != null) {
            sb.append(" [ ARTIST : " + ((Object) charSequence) + " ]");
        }
        CharSequence charSequence2 = this.song;
        if (charSequence2 != null) {
            sb.append(" [ SONG : " + ((Object) charSequence2) + " ]");
        }
        sb.append(" [ ACTIONS : ");
        Iterator it = this.actions.iterator();
        while (it.hasNext()) {
            sb.append(((Object) ((MediaAction) it.next()).contentDescription) + ", ");
        }
        sb.append(" ]");
        sb.append(" [ ACTIONSTOSHOWINCOMPACT : " + this.actionsToShowInCompact + " ]");
        sb.append(" [ PACKAGENAME : " + this.packageName + " ]");
        MediaDeviceData mediaDeviceData = this.device;
        if (mediaDeviceData != null) {
            sb.append(" [ DEVICE : " + ((Object) mediaDeviceData.name) + " ]");
            sb.append(" [ SECMEDIADEVICEDATA : " + mediaDeviceData.customMediaDeviceData.deviceType + " ]");
        }
        sb.append(" [ ACTIVE : " + this.active + " ]");
        sb.append(" [ PLAYBACKLOCATION : " + this.playbackLocation + " ]");
        sb.append(" [ RESUMPTION : " + this.resumption + " ]");
        String str2 = this.notificationKey;
        if (str2 != null) {
            sb.append(" [ NOTIFICATIONKEY : " + str2 + " ]");
        }
        sb.append(" [ HASCHECKFORRESUME : " + this.hasCheckedForResume + " ]");
        sb.append(" [ ISPLAYING : " + this.isPlaying + " ]");
        sb.append(" [ ISCLEARABLE : " + this.isClearable + " ]");
        sb.append(" [ LASTACTIVE : " + new Timestamp(this.lastActive) + " ]");
        sb.append(" [ INSTANCEID : " + this.instanceId.getId() + " ]");
        sb.append(" [ APPUID : " + this.appUid + " ]");
        sb.append(" [ CUSTOMMEDIADATA : " + this.customMediaData.uid + " ]");
        return sb.toString();
    }

    public /* synthetic */ MediaData(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List list, List list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, InstanceId instanceId, int i3, boolean z6, Double d, SecMediaDataImpl secMediaDataImpl, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i4 & 2) != 0 ? false : z, str, icon, charSequence, charSequence2, icon2, list, list2, (i4 & 512) != 0 ? null : mediaButton, str2, token, pendingIntent, mediaDeviceData, z2, runnable, (65536 & i4) != 0 ? 0 : i2, (131072 & i4) != 0 ? false : z3, (262144 & i4) != 0 ? null : str3, (524288 & i4) != 0 ? false : z4, (1048576 & i4) != 0 ? null : bool, (2097152 & i4) != 0 ? true : z5, (4194304 & i4) != 0 ? 0L : j, instanceId, i3, (33554432 & i4) != 0 ? false : z6, (67108864 & i4) != 0 ? null : d, (i4 & 134217728) != 0 ? new SecMediaDataImpl(-1) : secMediaDataImpl);
    }
}
