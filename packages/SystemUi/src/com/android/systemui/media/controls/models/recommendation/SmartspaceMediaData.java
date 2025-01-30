package com.android.systemui.media.controls.models.recommendation;

import android.app.smartspace.SmartspaceAction;
import android.content.Intent;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.android.app.motiontool.TraceMetadata$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    public static SmartspaceMediaData copy$default(SmartspaceMediaData smartspaceMediaData, String str, boolean z, Intent intent, long j, InstanceId instanceId, long j2, int i) {
        String str2 = (i & 1) != 0 ? smartspaceMediaData.targetId : str;
        boolean z2 = (i & 2) != 0 ? smartspaceMediaData.isActive : z;
        String str3 = (i & 4) != 0 ? smartspaceMediaData.packageName : null;
        SmartspaceAction smartspaceAction = (i & 8) != 0 ? smartspaceMediaData.cardAction : null;
        List list = (i & 16) != 0 ? smartspaceMediaData.recommendations : null;
        Intent intent2 = (i & 32) != 0 ? smartspaceMediaData.dismissIntent : intent;
        long j3 = (i & 64) != 0 ? smartspaceMediaData.headphoneConnectionTimeMillis : j;
        InstanceId instanceId2 = (i & 128) != 0 ? smartspaceMediaData.instanceId : instanceId;
        long j4 = (i & 256) != 0 ? smartspaceMediaData.expiryTimeMs : j2;
        smartspaceMediaData.getClass();
        return new SmartspaceMediaData(str2, z2, str3, smartspaceAction, list, intent2, j3, instanceId2, j4);
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.targetId.hashCode() * 31;
        boolean z = this.isActive;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.packageName, (hashCode + i) * 31, 31);
        SmartspaceAction smartspaceAction = this.cardAction;
        int hashCode2 = (this.recommendations.hashCode() + ((m41m + (smartspaceAction == null ? 0 : smartspaceAction.hashCode())) * 31)) * 31;
        Intent intent = this.dismissIntent;
        return Long.hashCode(this.expiryTimeMs) + ((this.instanceId.hashCode() + TraceMetadata$$ExternalSyntheticOutline0.m51m(this.headphoneConnectionTimeMillis, (hashCode2 + (intent != null ? intent.hashCode() : 0)) * 31, 31)) * 31);
    }

    public final boolean isValid() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.recommendations.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((SmartspaceAction) next).getIcon() != null) {
                arrayList.add(next);
            }
        }
        return arrayList.size() >= 3;
    }

    public final String toString() {
        return "SmartspaceMediaData(targetId=" + this.targetId + ", isActive=" + this.isActive + ", packageName=" + this.packageName + ", cardAction=" + this.cardAction + ", recommendations=" + this.recommendations + ", dismissIntent=" + this.dismissIntent + ", headphoneConnectionTimeMillis=" + this.headphoneConnectionTimeMillis + ", instanceId=" + this.instanceId + ", expiryTimeMs=" + this.expiryTimeMs + ")";
    }
}
