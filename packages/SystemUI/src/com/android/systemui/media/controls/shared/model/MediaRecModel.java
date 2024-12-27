package com.android.systemui.media.controls.shared.model;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaRecModel {
    public final Bundle extras;
    public final Icon icon;
    public final Intent intent;
    public final CharSequence subtitle;
    public final CharSequence title;

    public MediaRecModel() {
        this(null, null, null, null, null, 31, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaRecModel)) {
            return false;
        }
        MediaRecModel mediaRecModel = (MediaRecModel) obj;
        return Intrinsics.areEqual(this.intent, mediaRecModel.intent) && Intrinsics.areEqual(this.title, mediaRecModel.title) && Intrinsics.areEqual(this.subtitle, mediaRecModel.subtitle) && Intrinsics.areEqual(this.icon, mediaRecModel.icon) && Intrinsics.areEqual(this.extras, mediaRecModel.extras);
    }

    public final int hashCode() {
        Intent intent = this.intent;
        int hashCode = (intent == null ? 0 : intent.hashCode()) * 31;
        CharSequence charSequence = this.title;
        int hashCode2 = (hashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.subtitle;
        int hashCode3 = (hashCode2 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        Icon icon = this.icon;
        int hashCode4 = (hashCode3 + (icon == null ? 0 : icon.hashCode())) * 31;
        Bundle bundle = this.extras;
        return hashCode4 + (bundle != null ? bundle.hashCode() : 0);
    }

    public final String toString() {
        Intent intent = this.intent;
        CharSequence charSequence = this.title;
        CharSequence charSequence2 = this.subtitle;
        return "MediaRecModel(intent=" + intent + ", title=" + ((Object) charSequence) + ", subtitle=" + ((Object) charSequence2) + ", icon=" + this.icon + ", extras=" + this.extras + ")";
    }

    public MediaRecModel(Intent intent, CharSequence charSequence, CharSequence charSequence2, Icon icon, Bundle bundle) {
        this.intent = intent;
        this.title = charSequence;
        this.subtitle = charSequence2;
        this.icon = icon;
        this.extras = bundle;
    }

    public /* synthetic */ MediaRecModel(Intent intent, CharSequence charSequence, CharSequence charSequence2, Icon icon, Bundle bundle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : intent, (i & 2) != 0 ? null : charSequence, (i & 4) != 0 ? null : charSequence2, (i & 8) != 0 ? null : icon, (i & 16) != 0 ? null : bundle);
    }
}
