package com.android.systemui.statusbar.notification.collection;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SuppressedAttachState {
    public static final Companion Companion = new Companion(null);
    public GroupEntry parent;
    public NotifSection section;
    public boolean wasPruneSuppressed;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ SuppressedAttachState(NotifSection notifSection, GroupEntry groupEntry, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(notifSection, groupEntry, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SuppressedAttachState)) {
            return false;
        }
        SuppressedAttachState suppressedAttachState = (SuppressedAttachState) obj;
        return Intrinsics.areEqual(this.section, suppressedAttachState.section) && Intrinsics.areEqual(this.parent, suppressedAttachState.parent) && this.wasPruneSuppressed == suppressedAttachState.wasPruneSuppressed;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        NotifSection notifSection = this.section;
        int hashCode = (notifSection == null ? 0 : notifSection.hashCode()) * 31;
        GroupEntry groupEntry = this.parent;
        int hashCode2 = (hashCode + (groupEntry != null ? groupEntry.hashCode() : 0)) * 31;
        boolean z = this.wasPruneSuppressed;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode2 + i;
    }

    public final String toString() {
        NotifSection notifSection = this.section;
        GroupEntry groupEntry = this.parent;
        boolean z = this.wasPruneSuppressed;
        StringBuilder sb = new StringBuilder("SuppressedAttachState(section=");
        sb.append(notifSection);
        sb.append(", parent=");
        sb.append(groupEntry);
        sb.append(", wasPruneSuppressed=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, z, ")");
    }

    private SuppressedAttachState(NotifSection notifSection, GroupEntry groupEntry, boolean z) {
        this.section = notifSection;
        this.parent = groupEntry;
        this.wasPruneSuppressed = z;
    }
}
