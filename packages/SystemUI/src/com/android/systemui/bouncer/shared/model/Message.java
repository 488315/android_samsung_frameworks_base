package com.android.systemui.bouncer.shared.model;

import android.content.res.ColorStateList;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class Message {
    public boolean animate;
    public final ColorStateList colorState;
    public Map formatterArgs;
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

    public final int hashCode() {
        String str = this.message;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.messageResId;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        ColorStateList colorStateList = this.colorState;
        int hashCode3 = (hashCode2 + (colorStateList == null ? 0 : colorStateList.hashCode())) * 31;
        Map map = this.formatterArgs;
        return Boolean.hashCode(this.animate) + ((hashCode3 + (map != null ? map.hashCode() : 0)) * 31);
    }

    public final String toString() {
        return "Message(message=" + this.message + ", messageResId=" + this.messageResId + ", colorState=" + this.colorState + ", formatterArgs=" + this.formatterArgs + ", animate=" + this.animate + ")";
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
