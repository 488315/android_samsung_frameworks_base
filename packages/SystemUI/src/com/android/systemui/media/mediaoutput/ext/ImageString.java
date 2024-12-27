package com.android.systemui.media.mediaoutput.ext;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ImageString implements CharSequence {
    public final ImageVector image;
    public final int resId;
    public final String selection;

    public ImageString(int i, String str, ImageVector imageVector) {
        this.resId = i;
        this.selection = str;
        this.image = imageVector;
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ char charAt(int i) {
        return '!';
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImageString)) {
            return false;
        }
        ImageString imageString = (ImageString) obj;
        return this.resId == imageString.resId && Intrinsics.areEqual(this.selection, imageString.selection) && Intrinsics.areEqual(this.image, imageString.image);
    }

    public final int hashCode() {
        return this.image.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.resId) * 31, 31, this.selection);
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ int length() {
        return 1;
    }

    @Override // java.lang.CharSequence
    public final CharSequence subSequence(int i, int i2) {
        return "";
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        int i = this.resId;
        String str = this.selection;
        ImageVector imageVector = this.image;
        StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i, "ImageString(resId=", ", selection=", str, ", image=");
        m.append(imageVector);
        m.append(")");
        return m.toString();
    }
}
