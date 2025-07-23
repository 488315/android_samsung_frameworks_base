package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SuppressedAttachState {
    public static final Companion Companion = new Companion(null);
    public PipelineEntry parent;
    public NotifSection section;
    public boolean wasPruneSuppressed;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int hashCode = (notifSection == null ? 0 : notifSection.hashCode()) * 31;
        PipelineEntry pipelineEntry = this.parent;
        return Boolean.hashCode(this.wasPruneSuppressed) + ((hashCode + (pipelineEntry != null ? pipelineEntry.hashCode() : 0)) * 31);
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
