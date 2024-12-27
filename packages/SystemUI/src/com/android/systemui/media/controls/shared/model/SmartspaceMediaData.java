package com.android.systemui.media.controls.shared.model;

import android.app.smartspace.SmartspaceAction;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SmartspaceMediaData {
    public final SmartspaceAction cardAction;
    public final Intent dismissIntent;
    public final long expiryTimeMs;
    public final long headphoneConnectionTimeMillis;
    public final InstanceId instanceId;
    public final boolean isActive;
    public final String packageName;
    public final List recommendations;
    public final String targetId;

    public SmartspaceMediaData() {
        this(null, false, null, null, null, null, 0L, null, 0L, 511, null);
    }

    public static SmartspaceMediaData copy$default(SmartspaceMediaData smartspaceMediaData, String str, boolean z, Intent intent, long j, InstanceId instanceId, long j2, int i) {
        boolean z2 = (i & 2) != 0 ? smartspaceMediaData.isActive : z;
        String str2 = smartspaceMediaData.packageName;
        SmartspaceAction smartspaceAction = smartspaceMediaData.cardAction;
        List list = smartspaceMediaData.recommendations;
        Intent intent2 = (i & 32) != 0 ? smartspaceMediaData.dismissIntent : intent;
        long j3 = (i & 64) != 0 ? smartspaceMediaData.headphoneConnectionTimeMillis : j;
        long j4 = (i & 256) != 0 ? smartspaceMediaData.expiryTimeMs : j2;
        smartspaceMediaData.getClass();
        return new SmartspaceMediaData(str, z2, str2, smartspaceAction, list, intent2, j3, instanceId, j4);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SmartspaceMediaData)) {
            return false;
        }
        SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) obj;
        return Intrinsics.areEqual(this.targetId, smartspaceMediaData.targetId) && this.isActive == smartspaceMediaData.isActive && Intrinsics.areEqual(this.packageName, smartspaceMediaData.packageName) && Intrinsics.areEqual(this.cardAction, smartspaceMediaData.cardAction) && Intrinsics.areEqual(this.recommendations, smartspaceMediaData.recommendations) && Intrinsics.areEqual(this.dismissIntent, smartspaceMediaData.dismissIntent) && this.headphoneConnectionTimeMillis == smartspaceMediaData.headphoneConnectionTimeMillis && Intrinsics.areEqual(this.instanceId, smartspaceMediaData.instanceId) && this.expiryTimeMs == smartspaceMediaData.expiryTimeMs;
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.targetId.hashCode() * 31, 31, this.isActive), 31, this.packageName);
        SmartspaceAction smartspaceAction = this.cardAction;
        int m2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.recommendations, (m + (smartspaceAction == null ? 0 : smartspaceAction.hashCode())) * 31, 31);
        Intent intent = this.dismissIntent;
        int m3 = Scale$$ExternalSyntheticOutline0.m((m2 + (intent == null ? 0 : intent.hashCode())) * 31, 31, this.headphoneConnectionTimeMillis);
        InstanceId instanceId = this.instanceId;
        return Long.hashCode(this.expiryTimeMs) + ((m3 + (instanceId != null ? instanceId.hashCode() : 0)) * 31);
    }

    public final boolean isValid() {
        List list = this.recommendations;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((SmartspaceAction) obj).getIcon() != null) {
                arrayList.add(obj);
            }
        }
        return arrayList.size() >= 3;
    }

    public final String toString() {
        SmartspaceAction smartspaceAction = this.cardAction;
        List list = this.recommendations;
        Intent intent = this.dismissIntent;
        InstanceId instanceId = this.instanceId;
        StringBuilder sb = new StringBuilder("SmartspaceMediaData(targetId=");
        sb.append(this.targetId);
        sb.append(", isActive=");
        sb.append(this.isActive);
        sb.append(", packageName=");
        sb.append(this.packageName);
        sb.append(", cardAction=");
        sb.append(smartspaceAction);
        sb.append(", recommendations=");
        sb.append(list);
        sb.append(", dismissIntent=");
        sb.append(intent);
        sb.append(", headphoneConnectionTimeMillis=");
        sb.append(this.headphoneConnectionTimeMillis);
        sb.append(", instanceId=");
        sb.append(instanceId);
        sb.append(", expiryTimeMs=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.expiryTimeMs, ")", sb);
    }

    public SmartspaceMediaData(String str, boolean z, String str2, SmartspaceAction smartspaceAction, List<SmartspaceAction> list, Intent intent, long j, InstanceId instanceId, long j2) {
        this.targetId = str;
        this.isActive = z;
        this.packageName = str2;
        this.cardAction = smartspaceAction;
        this.recommendations = list;
        this.dismissIntent = intent;
        this.headphoneConnectionTimeMillis = j;
        this.instanceId = instanceId;
        this.expiryTimeMs = j2;
    }

    public SmartspaceMediaData(String str, boolean z, String str2, SmartspaceAction smartspaceAction, List list, Intent intent, long j, InstanceId instanceId, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "INVALID" : str, (i & 2) != 0 ? false : z, (i & 4) == 0 ? str2 : "INVALID", (i & 8) != 0 ? null : smartspaceAction, (i & 16) != 0 ? EmptyList.INSTANCE : list, (i & 32) != 0 ? null : intent, (i & 64) != 0 ? 0L : j, (i & 128) == 0 ? instanceId : null, (i & 256) == 0 ? j2 : 0L);
    }
}
