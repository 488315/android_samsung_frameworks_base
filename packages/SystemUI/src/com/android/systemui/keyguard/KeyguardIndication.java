package com.android.systemui.keyguard;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardIndication {
    public final Drawable mBackground;
    public final boolean mForceAccessibilityLiveRegionAssertive;
    public final Drawable mIcon;
    public final CharSequence mMessage;
    public final Long mMinVisibilityMillis;
    public final View.OnClickListener mOnClickListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Builder {
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
        String str;
        if (TextUtils.isEmpty(this.mMessage)) {
            str = "KeyguardIndication{";
        } else {
            str = "KeyguardIndication{mMessage=" + ((Object) this.mMessage);
        }
        if (this.mIcon != null) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, " mIcon=");
            m.append(this.mIcon);
            str = m.toString();
        }
        if (this.mOnClickListener != null) {
            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, " mOnClickListener=");
            m2.append(this.mOnClickListener);
            str = m2.toString();
        }
        if (this.mBackground != null) {
            StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, " mBackground=");
            m3.append(this.mBackground);
            str = m3.toString();
        }
        Long l = this.mMinVisibilityMillis;
        if (l != null) {
            str = str + " mMinVisibilityMillis=" + l;
        }
        if (this.mForceAccessibilityLiveRegionAssertive) {
            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "mForceAccessibilityLiveRegionAssertive");
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "}");
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
