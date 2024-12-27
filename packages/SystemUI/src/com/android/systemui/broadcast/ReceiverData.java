package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.UserHandle;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ReceiverData {
    public final Executor executor;
    public final IntentFilter filter;
    public final String permission;
    public final BroadcastReceiver receiver;
    public final UserHandle user;

    public ReceiverData(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, String str) {
        this.receiver = broadcastReceiver;
        this.filter = intentFilter;
        this.executor = executor;
        this.user = userHandle;
        this.permission = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReceiverData)) {
            return false;
        }
        ReceiverData receiverData = (ReceiverData) obj;
        return Intrinsics.areEqual(this.receiver, receiverData.receiver) && Intrinsics.areEqual(this.filter, receiverData.filter) && Intrinsics.areEqual(this.executor, receiverData.executor) && Intrinsics.areEqual(this.user, receiverData.user) && Intrinsics.areEqual(this.permission, receiverData.permission);
    }

    public final int hashCode() {
        int hashCode = (this.user.hashCode() + ((this.executor.hashCode() + ((this.filter.hashCode() + (this.receiver.hashCode() * 31)) * 31)) * 31)) * 31;
        String str = this.permission;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        BroadcastReceiver broadcastReceiver = this.receiver;
        IntentFilter intentFilter = this.filter;
        Executor executor = this.executor;
        UserHandle userHandle = this.user;
        StringBuilder sb = new StringBuilder("ReceiverData(receiver=");
        sb.append(broadcastReceiver);
        sb.append(", filter=");
        sb.append(intentFilter);
        sb.append(", executor=");
        sb.append(executor);
        sb.append(", user=");
        sb.append(userHandle);
        sb.append(", permission=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.permission, ")");
    }

    public /* synthetic */ ReceiverData(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(broadcastReceiver, intentFilter, executor, userHandle, (i & 16) != 0 ? null : str);
    }
}
