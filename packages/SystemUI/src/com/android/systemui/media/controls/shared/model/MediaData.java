package com.android.systemui.media.controls.shared.model;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener$getResumeAction$1;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
    public final long createdTimestampMillis;
    public final MediaDeviceData device;
    public boolean hasCheckedForResume;
    public final boolean initialized;
    public final InstanceId instanceId;
    public final boolean isClearable;
    public final boolean isExplicit;
    public final Boolean isPlaying;
    public long lastActive;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public MediaData() {
        this(0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, null, false, 0L, 0L, null, 0, false, null, 268435455, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v20, types: [java.lang.Runnable] */
    public static MediaData copy$default(MediaData mediaData, List list, List list2, MediaButton mediaButton, String str, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z, MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1, boolean z2, boolean z3, Boolean bool, boolean z4, long j, long j2, InstanceId instanceId, int i, int i2) {
        int i3;
        boolean z5;
        String str2;
        boolean z6;
        int i4 = mediaData.userId;
        boolean z7 = mediaData.initialized;
        String str3 = mediaData.app;
        Icon icon = mediaData.appIcon;
        CharSequence charSequence = mediaData.artist;
        CharSequence charSequence2 = mediaData.song;
        Icon icon2 = mediaData.artwork;
        List list3 = (i2 & 128) != 0 ? mediaData.actions : list;
        List list4 = (i2 & 256) != 0 ? mediaData.actionsToShowInCompact : list2;
        MediaButton mediaButton2 = (i2 & 512) != 0 ? mediaData.semanticActions : mediaButton;
        String str4 = (i2 & 1024) != 0 ? mediaData.packageName : str;
        MediaSession.Token token = (i2 & 2048) != 0 ? mediaData.token : null;
        PendingIntent pendingIntent2 = (i2 & 4096) != 0 ? mediaData.clickIntent : pendingIntent;
        MediaDeviceData mediaDeviceData2 = (i2 & 8192) != 0 ? mediaData.device : mediaDeviceData;
        boolean z8 = (i2 & 16384) != 0 ? mediaData.active : z;
        MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$12 = (i2 & 32768) != 0 ? mediaData.resumeAction : mediaResumeListener$getResumeAction$1;
        int i5 = mediaData.playbackLocation;
        if ((i2 & 131072) != 0) {
            i3 = i5;
            z5 = mediaData.resumption;
        } else {
            i3 = i5;
            z5 = z2;
        }
        String str5 = mediaData.notificationKey;
        if ((i2 & 524288) != 0) {
            str2 = str5;
            z6 = mediaData.hasCheckedForResume;
        } else {
            str2 = str5;
            z6 = z3;
        }
        Boolean bool2 = (1048576 & i2) != 0 ? mediaData.isPlaying : bool;
        boolean z9 = (2097152 & i2) != 0 ? mediaData.isClearable : z4;
        PendingIntent pendingIntent3 = pendingIntent2;
        long j3 = (4194304 & i2) != 0 ? mediaData.lastActive : j;
        long j4 = (8388608 & i2) != 0 ? mediaData.createdTimestampMillis : j2;
        InstanceId instanceId2 = (16777216 & i2) != 0 ? mediaData.instanceId : instanceId;
        int i6 = (i2 & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) != 0 ? mediaData.appUid : i;
        boolean z10 = mediaData.isExplicit;
        Double d = mediaData.resumeProgress;
        mediaData.getClass();
        return new MediaData(i4, z7, str3, icon, charSequence, charSequence2, icon2, list3, list4, mediaButton2, str4, token, pendingIntent3, mediaDeviceData2, z8, mediaResumeListener$getResumeAction$12, i3, z5, str2, z6, bool2, z9, j3, j4, instanceId2, i6, z10, d);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaData)) {
            return false;
        }
        MediaData mediaData = (MediaData) obj;
        return this.userId == mediaData.userId && this.initialized == mediaData.initialized && Intrinsics.areEqual(this.app, mediaData.app) && Intrinsics.areEqual(this.appIcon, mediaData.appIcon) && Intrinsics.areEqual(this.artist, mediaData.artist) && Intrinsics.areEqual(this.song, mediaData.song) && Intrinsics.areEqual(this.artwork, mediaData.artwork) && Intrinsics.areEqual(this.actions, mediaData.actions) && Intrinsics.areEqual(this.actionsToShowInCompact, mediaData.actionsToShowInCompact) && Intrinsics.areEqual(this.semanticActions, mediaData.semanticActions) && Intrinsics.areEqual(this.packageName, mediaData.packageName) && Intrinsics.areEqual(this.token, mediaData.token) && Intrinsics.areEqual(this.clickIntent, mediaData.clickIntent) && Intrinsics.areEqual(this.device, mediaData.device) && this.active == mediaData.active && Intrinsics.areEqual(this.resumeAction, mediaData.resumeAction) && this.playbackLocation == mediaData.playbackLocation && this.resumption == mediaData.resumption && Intrinsics.areEqual(this.notificationKey, mediaData.notificationKey) && this.hasCheckedForResume == mediaData.hasCheckedForResume && Intrinsics.areEqual(this.isPlaying, mediaData.isPlaying) && this.isClearable == mediaData.isClearable && this.lastActive == mediaData.lastActive && this.createdTimestampMillis == mediaData.createdTimestampMillis && Intrinsics.areEqual(this.instanceId, mediaData.instanceId) && this.appUid == mediaData.appUid && this.isExplicit == mediaData.isExplicit && Intrinsics.areEqual(this.resumeProgress, mediaData.resumeProgress);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.userId) * 31, 31, this.initialized);
        String str = this.app;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        Icon icon = this.appIcon;
        int hashCode2 = (hashCode + (icon == null ? 0 : icon.hashCode())) * 31;
        CharSequence charSequence = this.artist;
        int hashCode3 = (hashCode2 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.song;
        int hashCode4 = (hashCode3 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        Icon icon2 = this.artwork;
        int m2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.actionsToShowInCompact, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.actions, (hashCode4 + (icon2 == null ? 0 : icon2.hashCode())) * 31, 31), 31);
        MediaButton mediaButton = this.semanticActions;
        int m3 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((m2 + (mediaButton == null ? 0 : mediaButton.hashCode())) * 31, 31, this.packageName);
        MediaSession.Token token = this.token;
        int hashCode5 = (m3 + (token == null ? 0 : token.hashCode())) * 31;
        PendingIntent pendingIntent = this.clickIntent;
        int hashCode6 = (hashCode5 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        MediaDeviceData mediaDeviceData = this.device;
        int m4 = TransitionData$$ExternalSyntheticOutline0.m((hashCode6 + (mediaDeviceData == null ? 0 : mediaDeviceData.hashCode())) * 31, 31, this.active);
        Runnable runnable = this.resumeAction;
        int m5 = TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.playbackLocation, (m4 + (runnable == null ? 0 : runnable.hashCode())) * 31, 31), 31, this.resumption);
        String str2 = this.notificationKey;
        int m6 = TransitionData$$ExternalSyntheticOutline0.m((m5 + (str2 == null ? 0 : str2.hashCode())) * 31, 31, this.hasCheckedForResume);
        Boolean bool = this.isPlaying;
        int m7 = TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.appUid, (this.instanceId.hashCode() + Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((m6 + (bool == null ? 0 : bool.hashCode())) * 31, 31, this.isClearable), 31, this.lastActive), 31, this.createdTimestampMillis)) * 31, 31), 31, this.isExplicit);
        Double d = this.resumeProgress;
        return m7 + (d != null ? d.hashCode() : 0);
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
        return sb.toString();
    }

    public MediaData(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List<MediaAction> list, List<Integer> list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, long j2, InstanceId instanceId, int i3, boolean z6, Double d) {
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
        this.createdTimestampMillis = j2;
        this.instanceId = instanceId;
        this.appUid = i3;
        this.isExplicit = z6;
        this.resumeProgress = d;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MediaData(int r32, boolean r33, java.lang.String r34, android.graphics.drawable.Icon r35, java.lang.CharSequence r36, java.lang.CharSequence r37, android.graphics.drawable.Icon r38, java.util.List r39, java.util.List r40, com.android.systemui.media.controls.shared.model.MediaButton r41, java.lang.String r42, android.media.session.MediaSession.Token r43, android.app.PendingIntent r44, com.android.systemui.media.controls.shared.model.MediaDeviceData r45, boolean r46, java.lang.Runnable r47, int r48, boolean r49, java.lang.String r50, boolean r51, java.lang.Boolean r52, boolean r53, long r54, long r56, com.android.internal.logging.InstanceId r58, int r59, boolean r60, java.lang.Double r61, int r62, kotlin.jvm.internal.DefaultConstructorMarker r63) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.shared.model.MediaData.<init>(int, boolean, java.lang.String, android.graphics.drawable.Icon, java.lang.CharSequence, java.lang.CharSequence, android.graphics.drawable.Icon, java.util.List, java.util.List, com.android.systemui.media.controls.shared.model.MediaButton, java.lang.String, android.media.session.MediaSession$Token, android.app.PendingIntent, com.android.systemui.media.controls.shared.model.MediaDeviceData, boolean, java.lang.Runnable, int, boolean, java.lang.String, boolean, java.lang.Boolean, boolean, long, long, com.android.internal.logging.InstanceId, int, boolean, java.lang.Double, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
