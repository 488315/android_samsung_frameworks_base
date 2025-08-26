package com.android.systemui.keyguard;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public class KeyguardIndication {
    public final Drawable mBackground;
    public final boolean mForceAccessibilityLiveRegionAssertive;
    public final Drawable mIcon;
    public final CharSequence mMessage;
    public final Long mMinVisibilityMillis;
    public final View.OnClickListener mOnClickListener;

    public class Builder {
        public boolean mForceAccessibilityLiveRegionAssertive;
        public CharSequence mMessage;
        public Long mMinVisibilityMillis;
        public ColorStateList mTextColor;

        public final KeyguardIndication build() {
            if (TextUtils.isEmpty(this.mMessage)) {
                throw new IllegalStateException("message or icon must be set");
            }
            ColorStateList colorStateList = this.mTextColor;
            if (colorStateList != null) {
                return new KeyguardIndication(this.mMessage, colorStateList, this.mMinVisibilityMillis, Boolean.valueOf(this.mForceAccessibilityLiveRegionAssertive));
            }
            throw new IllegalStateException("text color must be set");
        }
    }

    public /* synthetic */ KeyguardIndication(CharSequence charSequence, ColorStateList colorStateList, Long l, Boolean bool) {
        this(charSequence, colorStateList, null, null, null, l, bool);
    }

    public final String toString() {
        String strM;
        if (TextUtils.isEmpty(this.mMessage)) {
            strM = "KeyguardIndication{";
        } else {
            strM = "KeyguardIndication{mMessage=" + ((Object) this.mMessage);
        }
        if (this.mIcon != null) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strM, " mIcon=");
            sbM.append(this.mIcon);
            strM = sbM.toString();
        }
        if (this.mOnClickListener != null) {
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strM, " mOnClickListener=");
            sbM2.append(this.mOnClickListener);
            strM = sbM2.toString();
        }
        if (this.mBackground != null) {
            StringBuilder sbM3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strM, " mBackground=");
            sbM3.append(this.mBackground);
            strM = sbM3.toString();
        }
        Long l = this.mMinVisibilityMillis;
        if (l != null) {
            strM = strM + " mMinVisibilityMillis=" + l;
        }
        if (this.mForceAccessibilityLiveRegionAssertive) {
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, "mForceAccessibilityLiveRegionAssertive");
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, "}");
    }

    private KeyguardIndication(CharSequence charSequence, ColorStateList colorStateList, Drawable drawable, View.OnClickListener onClickListener, Drawable drawable2, Long l, Boolean bool) {
        this.mMessage = charSequence;
        this.mIcon = drawable;
        this.mOnClickListener = onClickListener;
        this.mBackground = drawable2;
        this.mMinVisibilityMillis = l;
        this.mForceAccessibilityLiveRegionAssertive = bool.booleanValue();
    }
}
