package com.android.server.wm;

import android.view.ContentRecordingSession;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContentRecordingController {
    public DisplayContent mDisplayContent;
    public ContentRecordingSession mSession;

    public ContentRecordingSession getContentRecordingSessionLocked() {
        return this.mSession;
    }

    public final void setContentRecordingSessionLocked(
            ContentRecordingSession contentRecordingSession,
            WindowManagerService windowManagerService) {
        DisplayContent displayContent;
        if (contentRecordingSession == null
                || ContentRecordingSession.isValid(contentRecordingSession)) {
            ContentRecordingSession contentRecordingSession2 = this.mSession;
            boolean z =
                    (contentRecordingSession2 == null
                                    || contentRecordingSession == null
                                    || !contentRecordingSession2.isWaitingForConsent()
                                    || contentRecordingSession.isWaitingForConsent())
                            ? false
                            : true;
            boolean isProjectionOnSameDisplay =
                    ContentRecordingSession.isProjectionOnSameDisplay(
                            this.mSession, contentRecordingSession);
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled;
            if (isProjectionOnSameDisplay) {
                if (!z) {
                    if (zArr[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                                -5981322449150461244L,
                                1,
                                "Content Recording: Ignoring session on same display %d, with an"
                                    + " existing session %s",
                                Long.valueOf(contentRecordingSession.getVirtualDisplayId()),
                                String.valueOf(this.mSession.getVirtualDisplayId()));
                        return;
                    }
                    return;
                }
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            -225319884529912382L,
                            1,
                            "Content Recording: Accept session updating same display %d with"
                                + " granted consent, with an existing session %s",
                            Long.valueOf(contentRecordingSession.getVirtualDisplayId()),
                            String.valueOf(this.mSession.getVirtualDisplayId()));
                }
            }
            if (contentRecordingSession != null) {
                if (zArr[1]) {
                    long virtualDisplayId = contentRecordingSession.getVirtualDisplayId();
                    ContentRecordingSession contentRecordingSession3 = this.mSession;
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            4226710957373144819L,
                            1,
                            "Content Recording: Handle incoming session on display %d, with a"
                                + " pre-existing session %s",
                            Long.valueOf(virtualDisplayId),
                            String.valueOf(
                                    contentRecordingSession3 == null
                                            ? null
                                            : Integer.valueOf(
                                                    contentRecordingSession3
                                                            .getVirtualDisplayId())));
                }
                displayContent =
                        windowManagerService.mRoot.getDisplayContentOrCreate(
                                contentRecordingSession.getVirtualDisplayId());
                if (displayContent == null) {
                    if (zArr[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                                -1415855962859555663L,
                                1,
                                "Content Recording: Incoming session on display %d can't be set"
                                    + " since it is already null; the corresponding VirtualDisplay"
                                    + " must have already been removed.",
                                Long.valueOf(contentRecordingSession.getVirtualDisplayId()));
                        return;
                    }
                    return;
                }
                displayContent.setContentRecordingSession(contentRecordingSession);
                displayContent.updateRecording();
            } else {
                displayContent = null;
            }
            if (this.mSession != null && !z) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            -5750232782380780139L,
                            0,
                            "Content Recording: Pause the recording session on display %s",
                            String.valueOf(this.mDisplayContent.mDisplayId));
                }
                this.mDisplayContent.pauseRecording();
                this.mDisplayContent.setContentRecordingSession(null);
            }
            this.mDisplayContent = displayContent;
            this.mSession = contentRecordingSession;
        }
    }
}
