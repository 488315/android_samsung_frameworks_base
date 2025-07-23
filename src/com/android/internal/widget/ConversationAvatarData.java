package com.android.internal.widget;

import android.graphics.drawable.Drawable;

/* loaded from: classes6.dex */
public interface ConversationAvatarData {

    public static final class OneToOneConversationAvatarData implements ConversationAvatarData {
        public final Drawable mDrawable;

        OneToOneConversationAvatarData(Drawable drawable) {
            this.mDrawable = drawable;
        }
    }

    public static final class GroupConversationAvatarData implements ConversationAvatarData {
        final Drawable mLastIcon;
        final Drawable mSecondLastIcon;

        GroupConversationAvatarData(Drawable drawable, Drawable drawable2) {
            this.mLastIcon = drawable;
            this.mSecondLastIcon = drawable2;
        }
    }
}
