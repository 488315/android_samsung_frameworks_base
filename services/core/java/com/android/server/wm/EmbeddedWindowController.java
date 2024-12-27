package com.android.server.wm;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.SurfaceControl;
import android.window.InputTransferToken;
import android.window.WindowContainerToken;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import com.samsung.android.rune.CoreRune;

public final class EmbeddedWindowController {
    public final ActivityTaskManagerService mAtmService;
    public final WindowManagerGlobalLock mGlobalLock;
    public final InputManagerService mInputManagerService;
    public final ArrayMap mWindows = new ArrayMap();
    public final ArrayMap mWindowsByInputTransferToken = new ArrayMap();
    public final ArrayMap mWindowsByWindowToken = new ArrayMap();

    public final class EmbeddedWindow implements InputTarget {
        public final IBinder mClient;
        public final int mDisplayId;
        public boolean mFocusGranted;
        public final ActivityRecord mHostActivityRecord;
        public final WindowState mHostWindowState;
        public InputChannel mInputChannel;
        public final InputTransferToken mInputTransferToken;
        public boolean mIsFocusable;
        public final String mName;
        public final int mOwnerPid;
        public final int mOwnerUid;
        public final Session mSession;
        public final int mShowUserId;
        public SurfaceControl mSurface;
        public WindowContainerToken mTaskToken;
        public final int mWindowType;
        public final WindowManagerService mWmService;

        public EmbeddedWindow(
                Session session,
                WindowManagerService windowManagerService,
                IBinder iBinder,
                WindowState windowState,
                int i,
                int i2,
                int i3,
                int i4,
                InputTransferToken inputTransferToken,
                String str,
                boolean z,
                SurfaceControl surfaceControl,
                WindowContainerToken windowContainerToken) {
            String str2;
            if (CoreRune.MW_CAPTION_SHELL) {
                this.mSurface = surfaceControl;
                this.mTaskToken = windowContainerToken;
            }
            this.mSession = session;
            this.mWmService = windowManagerService;
            this.mClient = iBinder;
            this.mHostWindowState = windowState;
            this.mHostActivityRecord = windowState != null ? windowState.mActivityRecord : null;
            this.mOwnerUid = i;
            this.mOwnerPid = i2;
            this.mWindowType = i3;
            this.mDisplayId = i4;
            this.mInputTransferToken = inputTransferToken;
            if (windowState != null) {
                str2 =
                        PackageManagerShellCommandDataLoader.STDIN_PATH
                                + windowState.getWindowTag().toString();
            } else {
                str2 = "";
            }
            this.mIsFocusable = z;
            this.mName = BootReceiver$$ExternalSyntheticOutline0.m("Embedded{", str, str2, "}");
            this.mShowUserId = UserHandle.getUserId(session.mUid);
        }

        @Override // com.android.server.wm.InputTarget
        public final boolean canScreenshotIme() {
            return true;
        }

        @Override // com.android.server.wm.InputTarget
        public final void dumpProto(int i, ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268060L);
            long start2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, System.identityHashCode(this));
            protoOutputStream.write(1138166333443L, "EmbeddedWindow");
            protoOutputStream.end(start2);
            protoOutputStream.end(start);
        }

        @Override // com.android.server.wm.InputTarget
        public final ActivityRecord getActivityRecord() {
            return this.mHostActivityRecord;
        }

        public final InputApplicationHandle getApplicationHandle() {
            WindowState windowState = this.mHostWindowState;
            if (windowState == null
                    || windowState.mInputWindowHandle.mHandle.inputApplicationHandle == null) {
                return null;
            }
            return new InputApplicationHandle(
                    windowState.mInputWindowHandle.mHandle.inputApplicationHandle);
        }

        @Override // com.android.server.wm.InputTarget
        public final DisplayContent getDisplayContent() {
            return this.mWmService.mRoot.getDisplayContent(this.mDisplayId);
        }

        @Override // com.android.server.wm.InputTarget
        public final int getDisplayId() {
            return this.mDisplayId;
        }

        @Override // com.android.server.wm.InputTarget
        public final InsetsControlTarget getImeControlTarget() {
            WindowState windowState = this.mHostWindowState;
            return windowState != null
                    ? windowState.getImeControlTarget()
                    : this.mWmService.getDefaultDisplayContentLocked().mRemoteInsetsControlTarget;
        }

        @Override // com.android.server.wm.InputTarget
        public final int getPid() {
            return this.mOwnerPid;
        }

        @Override // com.android.server.wm.InputTarget
        public final WindowState getWindowState() {
            return this.mHostWindowState;
        }

        @Override // com.android.server.wm.InputTarget
        public final IBinder getWindowToken() {
            return this.mClient;
        }

        public final void handleTap(boolean z) {
            if (this.mInputChannel != null) {
                WindowManagerService windowManagerService = this.mWmService;
                WindowState windowState = this.mHostWindowState;
                if (windowState == null) {
                    windowManagerService.grantEmbeddedWindowFocus(
                            this.mSession, this.mInputTransferToken, z);
                    return;
                }
                windowManagerService.grantEmbeddedWindowFocus(
                        null, windowState.mClient, this.mInputTransferToken, z);
                if (z) {
                    windowState.handleTapOutsideFocusInsideSelf();
                }
            }
        }

        @Override // com.android.server.wm.InputTarget
        public final void handleTapOutsideFocusInsideSelf() {
            handleTap(true);
        }

        @Override // com.android.server.wm.InputTarget
        public final void handleTapOutsideFocusOutsideSelf() {
            handleTap(false);
        }

        @Override // com.android.server.wm.InputTarget
        public final boolean isInputMethodClientFocus(int i, int i2) {
            return i == this.mOwnerUid && i2 == this.mOwnerPid;
        }

        public final void onRemoved() {
            WindowProcessController processController;
            boolean z = this.mFocusGranted;
            WindowManagerService windowManagerService = this.mWmService;
            if (z) {
                windowManagerService.grantEmbeddedWindowFocus(
                        this.mSession, this.mInputTransferToken, false);
            }
            InputChannel inputChannel = this.mInputChannel;
            if (inputChannel != null) {
                windowManagerService.mInputManager.removeInputChannel(inputChannel.getToken());
                this.mInputChannel.dispose();
                this.mInputChannel = null;
            }
            ActivityRecord activityRecord = this.mHostActivityRecord;
            if (activityRecord != null
                    && (processController =
                                    windowManagerService.mAtmService.getProcessController(
                                            this.mOwnerPid, this.mOwnerUid))
                            != null) {
                processController.removeRemoteActivityFlags(1, activityRecord);
            }
            if (CoreRune.MW_CAPTION_SHELL) {
                if (this.mTaskToken != null) {
                    this.mTaskToken = null;
                }
                SurfaceControl surfaceControl = this.mSurface;
                if (surfaceControl != null) {
                    surfaceControl.release();
                    this.mSurface = null;
                }
            }
        }

        @Override // com.android.server.wm.InputTarget
        public final boolean receiveFocusFromTapOutside() {
            return this.mIsFocusable;
        }

        @Override // com.android.server.wm.InputTarget
        public final boolean shouldControlIme() {
            return this.mHostWindowState != null;
        }

        public final String toString() {
            return this.mName;
        }
    }

    public EmbeddedWindowController(
            ActivityTaskManagerService activityTaskManagerService,
            InputManagerService inputManagerService) {
        this.mAtmService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mInputManagerService = inputManagerService;
    }

    public static boolean isValidTouchGestureParams(
            WindowState windowState, EmbeddedWindow embeddedWindow) {
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_EMBEDDED_WINDOWS_enabled;
        if (embeddedWindow == null) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS,
                        -1797662102094201628L,
                        0,
                        null,
                        null);
            }
            return false;
        }
        WindowState windowState2 = embeddedWindow.mHostWindowState;
        if (windowState2 == null) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS,
                        929964979835124721L,
                        0,
                        null,
                        null);
            }
            return false;
        }
        if (windowState2.mClient.asBinder() != windowState.mClient.asBinder()) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS,
                        676191989331669410L,
                        0,
                        null,
                        null);
            }
            return false;
        }
        InputChannel inputChannel = embeddedWindow.mInputChannel;
        if ((inputChannel != null ? inputChannel.getToken() : null) == null) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS,
                        553249487221306249L,
                        0,
                        null,
                        null);
            }
            return false;
        }
        if (windowState.mInputChannelToken != null) {
            return true;
        }
        if (zArr[3]) {
            ProtoLogImpl_54989576.w(
                    ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS, -8678904073078032058L, 0, null, null);
        }
        return false;
    }

    public final void add(final IBinder iBinder, EmbeddedWindow embeddedWindow) {
        try {
            this.mWindows.put(iBinder, embeddedWindow);
            final InputTransferToken inputTransferToken = embeddedWindow.mInputTransferToken;
            this.mWindowsByInputTransferToken.put(inputTransferToken, embeddedWindow);
            final IBinder iBinder2 = embeddedWindow.mClient;
            this.mWindowsByWindowToken.put(iBinder2, embeddedWindow);
            ActivityRecord activityRecord = embeddedWindow.mHostActivityRecord;
            if (activityRecord != null) {
                WindowProcessController processController =
                        this.mAtmService.getProcessController(
                                embeddedWindow.mOwnerPid, embeddedWindow.mOwnerUid);
                if (processController == null) {
                    Slog.w("WindowManager", "Could not find the embedding process.");
                } else {
                    int[] remoteActivityFlags =
                            processController.getRemoteActivityFlags(activityRecord);
                    remoteActivityFlags[0] = remoteActivityFlags[0] | 1;
                }
            }
            embeddedWindow.mClient.linkToDeath(
                    new IBinder
                            .DeathRecipient() { // from class:
                                                // com.android.server.wm.EmbeddedWindowController$$ExternalSyntheticLambda0
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            EmbeddedWindowController embeddedWindowController =
                                    EmbeddedWindowController.this;
                            IBinder iBinder3 = iBinder;
                            InputTransferToken inputTransferToken2 = inputTransferToken;
                            IBinder iBinder4 = iBinder2;
                            synchronized (embeddedWindowController.mGlobalLock) {
                                embeddedWindowController.mWindows.remove(iBinder3);
                                embeddedWindowController.mWindowsByInputTransferToken.remove(
                                        inputTransferToken2);
                                embeddedWindowController.mWindowsByWindowToken.remove(iBinder4);
                            }
                        }
                    },
                    0);
        } catch (RemoteException unused) {
            this.mWindows.remove(iBinder);
        }
    }

    public final void remove(IBinder iBinder) {
        for (int size = this.mWindows.size() - 1; size >= 0; size--) {
            EmbeddedWindow embeddedWindow = (EmbeddedWindow) this.mWindows.valueAt(size);
            if (embeddedWindow.mClient == iBinder) {
                ((EmbeddedWindow) this.mWindows.removeAt(size)).onRemoved();
                this.mWindowsByInputTransferToken.remove(embeddedWindow.mInputTransferToken);
                this.mWindowsByWindowToken.remove(embeddedWindow.mClient);
                return;
            }
        }
    }
}
