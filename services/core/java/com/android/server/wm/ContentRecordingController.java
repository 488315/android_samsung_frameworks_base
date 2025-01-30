package com.android.server.wm;

import android.view.ContentRecordingSession;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;

/* loaded from: classes3.dex */
public final class ContentRecordingController {
  public ContentRecordingSession mSession = null;
  public DisplayContent mDisplayContent = null;

  public ContentRecordingSession getContentRecordingSessionLocked() {
    return this.mSession;
  }

  public void setContentRecordingSessionLocked(
      ContentRecordingSession contentRecordingSession, WindowManagerService windowManagerService) {
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
      if (ContentRecordingSession.isProjectionOnSameDisplay(
          this.mSession, contentRecordingSession)) {
        if (z) {
          if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                -584061725,
                1,
                "Content Recording: Accept session updating same display %d with granted consent,"
                    + " with an existing session %s",
                new Object[] {
                  Long.valueOf(contentRecordingSession.getVirtualDisplayId()),
                  String.valueOf(this.mSession.getVirtualDisplayId())
                });
          }
        } else {
          if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                -1136734598,
                1,
                "Content Recording: Ignoring session on same display %d, with an existing session"
                    + " %s",
                new Object[] {
                  Long.valueOf(contentRecordingSession.getVirtualDisplayId()),
                  String.valueOf(this.mSession.getVirtualDisplayId())
                });
            return;
          }
          return;
        }
      }
      if (contentRecordingSession != null) {
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
          long virtualDisplayId = contentRecordingSession.getVirtualDisplayId();
          ContentRecordingSession contentRecordingSession3 = this.mSession;
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
              -767091913,
              1,
              "Content Recording: Handle incoming session on display %d, with a pre-existing"
                  + " session %s",
              new Object[] {
                Long.valueOf(virtualDisplayId),
                String.valueOf(
                    contentRecordingSession3 == null
                        ? null
                        : Integer.valueOf(contentRecordingSession3.getVirtualDisplayId()))
              });
        }
        displayContent =
            windowManagerService.mRoot.getDisplayContentOrCreate(
                contentRecordingSession.getVirtualDisplayId());
        if (displayContent == null) {
          if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                -233530384,
                1,
                "Content Recording: Incoming session on display %d can't be set since it is already"
                    + " null; the corresponding VirtualDisplay must have already been removed.",
                new Object[] {Long.valueOf(contentRecordingSession.getVirtualDisplayId())});
            return;
          }
          return;
        } else {
          displayContent.setContentRecordingSession(contentRecordingSession);
          if (z) {
            displayContent.updateRecording();
          }
        }
      } else {
        displayContent = null;
      }
      if (this.mSession != null && !z) {
        if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
              1546187372,
              0,
              "Content Recording: Pause the recording session on display %s",
              new Object[] {String.valueOf(this.mDisplayContent.getDisplayId())});
        }
        this.mDisplayContent.pauseRecording();
        this.mDisplayContent.setContentRecordingSession(null);
      }
      this.mDisplayContent = displayContent;
      this.mSession = contentRecordingSession;
    }
  }

  public boolean isValid(DisplayContent displayContent) {
    return displayContent == this.mDisplayContent;
  }
}
