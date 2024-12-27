package com.android.server.media;

import android.app.ActivityThread;
import android.media.MediaMetadata;
import android.media.session.ISessionManager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MediaShellCommand extends ShellCommand {
    public static MediaSessionManager sMediaSessionManager;
    public static ActivityThread sThread;
    public PrintWriter mErrorWriter;
    public InputStream mInput;
    public final String mPackageName;
    public ISessionManager mSessionService;
    public PrintWriter mWriter;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ControllerCallback extends MediaController.Callback {
        public ControllerCallback() {}

        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            MediaShellCommand.this.mWriter.println("onAudioInfoChanged " + playbackInfo);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onExtrasChanged(Bundle bundle) {
            MediaShellCommand.this.mWriter.println("onExtrasChanged " + bundle);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            String str;
            if (mediaMetadata == null) {
                str = null;
            } else {
                str = "title=" + mediaMetadata.getDescription();
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                    MediaShellCommand.this.mWriter, "onMetadataChanged ", str);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            MediaShellCommand.this.mWriter.println("onPlaybackStateChanged " + playbackState);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onQueueChanged(List list) {
            String str;
            PrintWriter printWriter = MediaShellCommand.this.mWriter;
            StringBuilder sb = new StringBuilder("onQueueChanged, ");
            if (list == null) {
                str = "null queue";
            } else {
                str = " size=" + list.size();
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, str, printWriter);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onQueueTitleChanged(CharSequence charSequence) {
            MediaShellCommand.this.mWriter.println("onQueueTitleChange " + ((Object) charSequence));
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            MediaShellCommand.this.mWriter.println("onSessionDestroyed. Enter q to quit.");
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionEvent(String str, Bundle bundle) {
            MediaShellCommand.this.mWriter.println(
                    "onSessionEvent event=" + str + ", extras=" + bundle);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ControllerMonitor {
        public final MediaController mController;
        public final ControllerCallback mControllerCallback;

        public ControllerMonitor(MediaController mediaController) {
            this.mController = mediaController;
            this.mControllerCallback = MediaShellCommand.this.new ControllerCallback();
        }

        public final void dispatchKeyCode(int i) {
            long uptimeMillis = SystemClock.uptimeMillis();
            KeyEvent keyEvent =
                    new KeyEvent(
                            uptimeMillis,
                            uptimeMillis,
                            0,
                            i,
                            0,
                            0,
                            -1,
                            0,
                            0,
                            FrameworkStatsLog
                                    .HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP);
            KeyEvent keyEvent2 =
                    new KeyEvent(
                            uptimeMillis,
                            uptimeMillis,
                            1,
                            i,
                            0,
                            0,
                            -1,
                            0,
                            0,
                            FrameworkStatsLog
                                    .HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP);
            try {
                this.mController.dispatchMediaButtonEvent(keyEvent);
                this.mController.dispatchMediaButtonEvent(keyEvent2);
            } catch (RuntimeException unused) {
                AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(
                        MediaShellCommand.this.mErrorWriter, "Failed to dispatch ", i);
            }
        }

        public final void printUsageMessage() {
            MediaShellCommand mediaShellCommand = MediaShellCommand.this;
            try {
                mediaShellCommand.mWriter.println(
                        "V2Monitoring session "
                                + this.mController.getTag()
                                + "...  available commands: play, pause, next, previous");
            } catch (RuntimeException unused) {
                mediaShellCommand.mWriter.println("Error trying to monitor session!");
            }
            mediaShellCommand.mWriter.println("(q)uit: finish monitoring");
        }

        public final void run() {
            MediaController mediaController;
            boolean z;
            printUsageMessage();
            HandlerThread handlerThread =
                    new HandlerThread() { // from class:
                                          // com.android.server.media.MediaShellCommand.ControllerMonitor.1
                        @Override // android.os.HandlerThread
                        public final void onLooperPrepared() {
                            try {
                                ControllerMonitor controllerMonitor = ControllerMonitor.this;
                                controllerMonitor.mController.registerCallback(
                                        controllerMonitor.mControllerCallback);
                            } catch (RuntimeException unused) {
                                MediaShellCommand.this.mErrorWriter.println(
                                        "Error registering monitor callback");
                            }
                        }
                    };
            handlerThread.start();
            try {
                try {
                    try {
                        BufferedReader bufferedReader =
                                new BufferedReader(
                                        new InputStreamReader(MediaShellCommand.this.mInput));
                        while (true) {
                            MediaShellCommand.this.mWriter.flush();
                            MediaShellCommand.this.mErrorWriter.flush();
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                if (readLine.length() > 0) {
                                    if ("q".equals(readLine) || "quit".equals(readLine)) {
                                        break;
                                    }
                                    if ("play".equals(readLine)) {
                                        dispatchKeyCode(126);
                                    } else if ("pause".equals(readLine)) {
                                        dispatchKeyCode(127);
                                    } else if ("next".equals(readLine)) {
                                        dispatchKeyCode(87);
                                    } else if ("previous".equals(readLine)) {
                                        dispatchKeyCode(88);
                                    } else {
                                        MediaShellCommand.this.mErrorWriter.println(
                                                "Invalid command: " + readLine);
                                    }
                                    z = true;
                                } else {
                                    z = false;
                                }
                                synchronized (this) {
                                    if (z) {
                                        try {
                                            MediaShellCommand.this.mWriter.println("");
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                    printUsageMessage();
                                }
                            }
                        }
                        mediaController = this.mController;
                    } catch (IOException e) {
                        e.printStackTrace();
                        handlerThread.getLooper().quit();
                        mediaController = this.mController;
                    }
                    mediaController.unregisterCallback(this.mControllerCallback);
                } catch (Exception unused) {
                }
            } finally {
                handlerThread.getLooper().quit();
                try {
                    this.mController.unregisterCallback(this.mControllerCallback);
                } catch (Exception unused2) {
                }
            }
        }
    }

    public MediaShellCommand(String str) {
        this.mPackageName = str;
    }

    public final void log(String str, String str2) {
        this.mWriter.println(str + " " + str2);
    }

    public final int onCommand(String str) {
        this.mWriter = getOutPrintWriter();
        this.mErrorWriter = getErrPrintWriter();
        this.mInput = getRawInputStream();
        if (TextUtils.isEmpty(str)) {
            return handleDefaultCommands(str);
        }
        if (sThread == null) {
            Looper.prepare();
            ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
            sThread = currentActivityThread;
            sMediaSessionManager =
                    (MediaSessionManager)
                            currentActivityThread
                                    .getSystemContext()
                                    .getSystemService("media_session");
        }
        ISessionManager asInterface =
                ISessionManager.Stub.asInterface(ServiceManager.checkService("media_session"));
        this.mSessionService = asInterface;
        if (asInterface == null) {
            throw new IllegalStateException(
                    "Can't connect to media session service; is the system running?");
        }
        try {
            if (str.equals("dispatch")) {
                runDispatch();
                return 0;
            }
            if (str.equals("list-sessions")) {
                runListSessions();
                return 0;
            }
            if (str.equals("monitor")) {
                runMonitor();
                return 0;
            }
            if (str.equals("volume")) {
                runVolume();
                return 0;
            }
            if (str.equals("expire-temp-engaged-sessions")) {
                this.mSessionService.expireTempEngagedSessions();
                return 0;
            }
            showError("Error: unknown command '" + str + "'");
            return -1;
        } catch (Exception e) {
            showError(e.toString());
            return -1;
        }
    }

    public final void onHelp() {
        this.mWriter.println("usage: media_session [subcommand] [options]");
        this.mWriter.println("       media_session dispatch KEY");
        this.mWriter.println("       media_session list-sessions");
        this.mWriter.println("       media_session monitor <tag>");
        this.mWriter.println("       media_session volume [options]");
        this.mWriter.println("       media_session expire-temp-engaged-sessions");
        this.mWriter.println();
        this.mWriter.println("media_session dispatch: dispatch a media key to the system.");
        this.mWriter.println(
                "                KEY may be: play, pause, play-pause, mute, headsethook,");
        this.mWriter.println("                stop, next, previous, rewind, record, fast-forward.");
        this.mWriter.println("media_session list-sessions: print a list of the current sessions.");
        this.mWriter.println("media_session monitor: monitor updates to the specified session.");
        this.mWriter.println("                       Use the tag from list-sessions.");
        this.mWriter.println("media_session volume:  ".concat(VolumeCtrl.USAGE));
        this.mWriter.println("media_session expire-temp-engaged-sessions: Expires any ongoing");
        this.mWriter.println(
                "                timers for media sessions in a temporary user-engaged");
        this.mWriter.println("                state.");
        this.mWriter.println();
    }

    public final void runDispatch() {
        int i;
        String nextArgRequired = getNextArgRequired();
        if ("play".equals(nextArgRequired)) {
            i = 126;
        } else if ("pause".equals(nextArgRequired)) {
            i = 127;
        } else if ("play-pause".equals(nextArgRequired)) {
            i = 85;
        } else if ("mute".equals(nextArgRequired)) {
            i = 91;
        } else if ("headsethook".equals(nextArgRequired)) {
            i = 79;
        } else if ("stop".equals(nextArgRequired)) {
            i = 86;
        } else if ("next".equals(nextArgRequired)) {
            i = 87;
        } else if ("previous".equals(nextArgRequired)) {
            i = 88;
        } else if ("rewind".equals(nextArgRequired)) {
            i = 89;
        } else if ("record".equals(nextArgRequired)) {
            i = 130;
        } else {
            if (!"fast-forward".equals(nextArgRequired)) {
                showError("Error: unknown dispatch code '" + nextArgRequired + "'");
                return;
            }
            i = 90;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        int i2 = i;
        try {
            this.mSessionService.dispatchMediaKeyEvent(
                    this.mPackageName,
                    false,
                    new KeyEvent(
                            uptimeMillis,
                            uptimeMillis,
                            0,
                            i,
                            0,
                            0,
                            -1,
                            0,
                            0,
                            FrameworkStatsLog
                                    .HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP),
                    false);
        } catch (RemoteException unused) {
        }
        try {
            this.mSessionService.dispatchMediaKeyEvent(
                    this.mPackageName,
                    false,
                    new KeyEvent(
                            uptimeMillis,
                            uptimeMillis,
                            1,
                            i2,
                            0,
                            0,
                            -1,
                            0,
                            0,
                            FrameworkStatsLog
                                    .HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP),
                    false);
        } catch (RemoteException unused2) {
        }
    }

    public final void runListSessions() {
        this.mWriter.println("Sessions:");
        try {
            for (MediaController mediaController : sMediaSessionManager.getActiveSessions(null)) {
                if (mediaController != null) {
                    try {
                        this.mWriter.println(
                                "  tag="
                                        + mediaController.getTag()
                                        + ", package="
                                        + mediaController.getPackageName());
                    } catch (RuntimeException unused) {
                    }
                }
            }
        } catch (Exception unused2) {
            this.mErrorWriter.println("***Error listing sessions***");
        }
    }

    public final void runMonitor() {
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            showError("Error: must include a session id");
            return;
        }
        try {
            for (MediaController mediaController : sMediaSessionManager.getActiveSessions(null)) {
                if (mediaController != null) {
                    try {
                        if (nextArgRequired.equals(mediaController.getTag())) {
                            new ControllerMonitor(mediaController).run();
                            return;
                        }
                        continue;
                    } catch (RemoteException unused) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            this.mErrorWriter.println("***Error monitoring session*** " + e.getMessage());
        }
        this.mErrorWriter.println("No session found with id ".concat(nextArgRequired));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0190  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runVolume() {
        /*
            Method dump skipped, instructions count: 780
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.media.MediaShellCommand.runVolume():void");
    }

    public final void showError(String str) {
        onHelp();
        this.mErrorWriter.println(str);
    }
}
