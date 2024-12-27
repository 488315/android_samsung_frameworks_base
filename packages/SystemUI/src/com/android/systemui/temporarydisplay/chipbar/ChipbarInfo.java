package com.android.systemui.temporarydisplay.chipbar;

import android.R;
import android.os.VibrationEffect;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.temporarydisplay.TemporaryViewInfo;
import com.android.systemui.temporarydisplay.ViewPriority;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ChipbarInfo extends TemporaryViewInfo {
    public static final Companion Companion = new Companion(null);
    public static final int DEFAULT_ICON_TINT = R.^attr-private.materialColorOnSurfaceInverse;
    public final boolean allowSwipeToDismiss;
    public final ChipbarEndItem endItem;
    public final String id;
    public final InstanceId instanceId;
    public final ViewPriority priority;
    public final TintedIcon startIcon;
    public final Text text;
    public final int timeoutMs;
    public final VibrationEffect vibrationEffect;
    public final String wakeReason;
    public final String windowTitle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ ChipbarInfo(TintedIcon tintedIcon, Text text, ChipbarEndItem chipbarEndItem, VibrationEffect vibrationEffect, boolean z, String str, String str2, int i, String str3, ViewPriority viewPriority, InstanceId instanceId, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(tintedIcon, text, chipbarEndItem, (i2 & 8) != 0 ? null : vibrationEffect, (i2 & 16) != 0 ? false : z, str, str2, i, str3, viewPriority, instanceId);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChipbarInfo)) {
            return false;
        }
        ChipbarInfo chipbarInfo = (ChipbarInfo) obj;
        return Intrinsics.areEqual(this.startIcon, chipbarInfo.startIcon) && Intrinsics.areEqual(this.text, chipbarInfo.text) && Intrinsics.areEqual(this.endItem, chipbarInfo.endItem) && Intrinsics.areEqual(this.vibrationEffect, chipbarInfo.vibrationEffect) && this.allowSwipeToDismiss == chipbarInfo.allowSwipeToDismiss && Intrinsics.areEqual(this.windowTitle, chipbarInfo.windowTitle) && Intrinsics.areEqual(this.wakeReason, chipbarInfo.wakeReason) && this.timeoutMs == chipbarInfo.timeoutMs && Intrinsics.areEqual(this.id, chipbarInfo.id) && this.priority == chipbarInfo.priority && Intrinsics.areEqual(this.instanceId, chipbarInfo.instanceId);
    }

    @Override // com.android.systemui.temporarydisplay.TemporaryViewInfo
    public final String getId() {
        return this.id;
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
    public final int getTimeoutMs() {
        return this.timeoutMs;
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
        int hashCode = (this.text.hashCode() + (this.startIcon.hashCode() * 31)) * 31;
        ChipbarEndItem chipbarEndItem = this.endItem;
        int hashCode2 = (hashCode + (chipbarEndItem == null ? 0 : chipbarEndItem.hashCode())) * 31;
        VibrationEffect vibrationEffect = this.vibrationEffect;
        int hashCode3 = (this.priority.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.timeoutMs, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (vibrationEffect == null ? 0 : vibrationEffect.hashCode())) * 31, 31, this.allowSwipeToDismiss), 31, this.windowTitle), 31, this.wakeReason), 31), 31, this.id)) * 31;
        InstanceId instanceId = this.instanceId;
        return hashCode3 + (instanceId != null ? instanceId.hashCode() : 0);
    }

    public final String toString() {
        return "ChipbarInfo(startIcon=" + this.startIcon + ", text=" + this.text + ", endItem=" + this.endItem + ", vibrationEffect=" + this.vibrationEffect + ", allowSwipeToDismiss=" + this.allowSwipeToDismiss + ", windowTitle=" + this.windowTitle + ", wakeReason=" + this.wakeReason + ", timeoutMs=" + this.timeoutMs + ", id=" + this.id + ", priority=" + this.priority + ", instanceId=" + this.instanceId + ")";
    }

    public ChipbarInfo(TintedIcon tintedIcon, Text text, ChipbarEndItem chipbarEndItem, VibrationEffect vibrationEffect, boolean z, String str, String str2, int i, String str3, ViewPriority viewPriority, InstanceId instanceId) {
        this.startIcon = tintedIcon;
        this.text = text;
        this.endItem = chipbarEndItem;
        this.vibrationEffect = vibrationEffect;
        this.allowSwipeToDismiss = z;
        this.windowTitle = str;
        this.wakeReason = str2;
        this.timeoutMs = i;
        this.id = str3;
        this.priority = viewPriority;
        this.instanceId = instanceId;
    }
}
