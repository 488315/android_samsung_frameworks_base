package com.android.systemui.qs.tiles.impl.irecording.data.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IssueRecordingModel {
    public final boolean isRecording;

    private /* synthetic */ IssueRecordingModel(boolean z) {
        this.isRecording = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ IssueRecordingModel m2914boximpl(boolean z) {
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
