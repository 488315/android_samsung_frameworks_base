package com.android.systemui.qs.tiles.impl.irecording.data.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public final class IssueRecordingModel {
    public final boolean isRecording;

    private /* synthetic */ IssueRecordingModel(boolean z) {
        this.isRecording = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ IssueRecordingModel m2929boximpl(boolean z) {
        return new IssueRecordingModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof IssueRecordingModel) {
            return this.isRecording == ((IssueRecordingModel) obj).isRecording;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isRecording);
    }

    public final String toString() {
        return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("IssueRecordingModel(isRecording="), this.isRecording, ")");
    }
}
