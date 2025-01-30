package com.android.systemui.dump;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RegisteredDumpable {
    public final Object dumpable;
    public final String name;
    public final DumpPriority priority;

    public RegisteredDumpable(String str, Object obj, DumpPriority dumpPriority) {
        this.name = str;
        this.dumpable = obj;
        this.priority = dumpPriority;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RegisteredDumpable)) {
            return false;
        }
        RegisteredDumpable registeredDumpable = (RegisteredDumpable) obj;
        return Intrinsics.areEqual(this.name, registeredDumpable.name) && Intrinsics.areEqual(this.dumpable, registeredDumpable.dumpable) && this.priority == registeredDumpable.priority;
    }

    public final int hashCode() {
        int hashCode = this.name.hashCode() * 31;
        Object obj = this.dumpable;
        return this.priority.hashCode() + ((hashCode + (obj == null ? 0 : obj.hashCode())) * 31);
    }

    public final String toString() {
        return "RegisteredDumpable(name=" + this.name + ", dumpable=" + this.dumpable + ", priority=" + this.priority + ")";
    }
}
