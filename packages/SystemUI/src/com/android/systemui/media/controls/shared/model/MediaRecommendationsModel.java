package com.android.systemui.media.controls.shared.model;

import android.content.Intent;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaRecommendationsModel {
    public final CharSequence appName;
    public final boolean areRecommendationsValid;
    public final Intent dismissIntent;
    public final InstanceId instanceId;
    public final String key;
    public final List mediaRecs;
    public final String packageName;
    public final int uid;

    public MediaRecommendationsModel(String str, int i, String str2, InstanceId instanceId, CharSequence charSequence, Intent intent, boolean z, List<MediaRecModel> list) {
        this.key = str;
        this.uid = i;
        this.packageName = str2;
        this.instanceId = instanceId;
        this.appName = charSequence;
        this.dismissIntent = intent;
        this.areRecommendationsValid = z;
        this.mediaRecs = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaRecommendationsModel)) {
            return false;
        }
        MediaRecommendationsModel mediaRecommendationsModel = (MediaRecommendationsModel) obj;
        return Intrinsics.areEqual(this.key, mediaRecommendationsModel.key) && this.uid == mediaRecommendationsModel.uid && Intrinsics.areEqual(this.packageName, mediaRecommendationsModel.packageName) && Intrinsics.areEqual(this.instanceId, mediaRecommendationsModel.instanceId) && Intrinsics.areEqual(this.appName, mediaRecommendationsModel.appName) && Intrinsics.areEqual(this.dismissIntent, mediaRecommendationsModel.dismissIntent) && this.areRecommendationsValid == mediaRecommendationsModel.areRecommendationsValid && Intrinsics.areEqual(this.mediaRecs, mediaRecommendationsModel.mediaRecs);
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.uid, this.key.hashCode() * 31, 31), 31, this.packageName);
        InstanceId instanceId = this.instanceId;
        int hashCode = (m + (instanceId == null ? 0 : instanceId.hashCode())) * 31;
        CharSequence charSequence = this.appName;
        int hashCode2 = (hashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        Intent intent = this.dismissIntent;
        return this.mediaRecs.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (intent != null ? intent.hashCode() : 0)) * 31, 31, this.areRecommendationsValid);
    }

    public final String toString() {
        InstanceId instanceId = this.instanceId;
        CharSequence charSequence = this.appName;
        return "MediaRecommendationsModel(key=" + this.key + ", uid=" + this.uid + ", packageName=" + this.packageName + ", instanceId=" + instanceId + ", appName=" + ((Object) charSequence) + ", dismissIntent=" + this.dismissIntent + ", areRecommendationsValid=" + this.areRecommendationsValid + ", mediaRecs=" + this.mediaRecs + ")";
    }

    public /* synthetic */ MediaRecommendationsModel(String str, int i, String str2, InstanceId instanceId, CharSequence charSequence, Intent intent, boolean z, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? -1 : i, str2, (i2 & 8) != 0 ? null : instanceId, (i2 & 16) != 0 ? null : charSequence, (i2 & 32) != 0 ? null : intent, (i2 & 64) != 0 ? false : z, list);
    }
}
