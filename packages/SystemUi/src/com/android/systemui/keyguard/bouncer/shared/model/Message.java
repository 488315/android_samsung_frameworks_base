package com.android.systemui.keyguard.bouncer.shared.model;

import android.content.res.ColorStateList;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Message {
    public final boolean animate;
    public final ColorStateList colorState;
    public final Map formatterArgs;
    public final String message;
    public final Integer messageResId;

    public Message() {
        this(null, null, null, null, false, 31, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Message)) {
            return false;
        }
        Message message = (Message) obj;
        return Intrinsics.areEqual(this.message, message.message) && Intrinsics.areEqual(this.messageResId, message.messageResId) && Intrinsics.areEqual(this.colorState, message.colorState) && Intrinsics.areEqual(this.formatterArgs, message.formatterArgs) && this.animate == message.animate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        String str = this.message;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.messageResId;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        ColorStateList colorStateList = this.colorState;
        int hashCode3 = (hashCode2 + (colorStateList == null ? 0 : colorStateList.hashCode())) * 31;
        Map map = this.formatterArgs;
        int hashCode4 = (hashCode3 + (map != null ? map.hashCode() : 0)) * 31;
        boolean z = this.animate;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode4 + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Message(message=");
        sb.append(this.message);
        sb.append(", messageResId=");
        sb.append(this.messageResId);
        sb.append(", colorState=");
        sb.append(this.colorState);
        sb.append(", formatterArgs=");
        sb.append(this.formatterArgs);
        sb.append(", animate=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.animate, ")");
    }

    public Message(String str, Integer num, ColorStateList colorStateList, Map<String, ? extends Object> map, boolean z) {
        this.message = str;
        this.messageResId = num;
        this.colorState = colorStateList;
        this.formatterArgs = map;
        this.animate = z;
    }

    public /* synthetic */ Message(String str, Integer num, ColorStateList colorStateList, Map map, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : num, (i & 4) != 0 ? null : colorStateList, (i & 8) != 0 ? null : map, (i & 16) != 0 ? true : z);
    }
}
