package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class SuppressedAttachState {
    public static final Companion Companion = new Companion(null);
    public PipelineEntry parent;
    public NotifSection section;
    public boolean wasPruneSuppressed;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ SuppressedAttachState(NotifSection notifSection, PipelineEntry pipelineEntry, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(notifSection, pipelineEntry, z);
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

    public final int hashCode() {
        NotifSection notifSection = this.section;
        int iHashCode = (notifSection == null ? 0 : notifSection.hashCode()) * 31;
        PipelineEntry pipelineEntry = this.parent;
        return Boolean.hashCode(this.wasPruneSuppressed) + ((iHashCode + (pipelineEntry != null ? pipelineEntry.hashCode() : 0)) * 31);
    }

    public final String toString() {
        NotifSection notifSection = this.section;
        PipelineEntry pipelineEntry = this.parent;
        boolean z = this.wasPruneSuppressed;
        StringBuilder sb = new StringBuilder("SuppressedAttachState(section=");
        sb.append(notifSection);
        sb.append(", parent=");
        sb.append(pipelineEntry);
        sb.append(", wasPruneSuppressed=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, z, ")");
    }

    private SuppressedAttachState(NotifSection notifSection, PipelineEntry pipelineEntry, boolean z) {
        this.section = notifSection;
        this.parent = pipelineEntry;
        this.wasPruneSuppressed = z;
    }
}
