package com.android.server.wm;

import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.IWindow;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class EmbeddedWindowController {
    public final ActivityTaskManagerService mAtmService;
    public final Object mGlobalLock;
    public ArrayMap mWindows = new ArrayMap();
    public ArrayMap mWindowsByFocusToken = new ArrayMap();
    public ArrayMap mWindowsByWindowToken = new ArrayMap();

    public EmbeddedWindowController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtmService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.getGlobalLock();
    }

    public void add(final IBinder iBinder, EmbeddedWindow embeddedWindow) {
        try {
            this.mWindows.put(iBinder, embeddedWindow);
            final IBinder focusGrantToken = embeddedWindow.getFocusGrantToken();
            this.mWindowsByFocusToken.put(focusGrantToken, embeddedWindow);
            this.mWindowsByWindowToken.put(embeddedWindow.getWindowToken(), embeddedWindow);
            updateProcessController(embeddedWindow);
            embeddedWindow.mClient.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.wm.EmbeddedWindowController$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    EmbeddedWindowController.this.lambda$add$0(iBinder, focusGrantToken);
                }
            }, 0);
        } catch (RemoteException unused) {
            this.mWindows.remove(iBinder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$add$0(IBinder iBinder, IBinder iBinder2) {
        synchronized (this.mGlobalLock) {
            this.mWindows.remove(iBinder);
            this.mWindowsByFocusToken.remove(iBinder2);
        }
    }

    public final void updateProcessController(EmbeddedWindow embeddedWindow) {
        if (embeddedWindow.mHostActivityRecord == null) {
            return;
        }
        WindowProcessController processController = this.mAtmService.getProcessController(embeddedWindow.mOwnerPid, embeddedWindow.mOwnerUid);
        if (processController == null) {
            Slog.w(StartingSurfaceController.TAG, "Could not find the embedding process.");
        } else {
            processController.addHostActivity(embeddedWindow.mHostActivityRecord);
        }
    }

    public void remove(IWindow iWindow) {
        for (int size = this.mWindows.size() - 1; size >= 0; size--) {
            EmbeddedWindow embeddedWindow = (EmbeddedWindow) this.mWindows.valueAt(size);
            if (embeddedWindow.mClient.asBinder() == iWindow.asBinder()) {
                ((EmbeddedWindow) this.mWindows.removeAt(size)).onRemoved();
                this.mWindowsByFocusToken.remove(embeddedWindow.getFocusGrantToken());
                this.mWindowsByWindowToken.remove(embeddedWindow.getWindowToken());
                return;
            }
        }
    }

    public void onWindowRemoved(WindowState windowState) {
        for (int size = this.mWindows.size() - 1; size >= 0; size--) {
            EmbeddedWindow embeddedWindow = (EmbeddedWindow) this.mWindows.valueAt(size);
            if (embeddedWindow.mHostWindowState == windowState) {
                ((EmbeddedWindow) this.mWindows.removeAt(size)).onRemoved();
                this.mWindowsByFocusToken.remove(embeddedWindow.getFocusGrantToken());
                this.mWindowsByWindowToken.remove(embeddedWindow.getWindowToken());
            }
        }
    }

    public EmbeddedWindow get(IBinder iBinder) {
        return (EmbeddedWindow) this.mWindows.get(iBinder);
    }

    public EmbeddedWindow getByFocusToken(IBinder iBinder) {
        return (EmbeddedWindow) this.mWindowsByFocusToken.get(iBinder);
    }

    public EmbeddedWindow getByWindowToken(IBinder iBinder) {
        return (EmbeddedWindow) this.mWindowsByWindowToken.get(iBinder);
    }

    public void onActivityRemoved(ActivityRecord activityRecord) {
        WindowProcessController processController;
        for (int size = this.mWindows.size() - 1; size >= 0; size--) {
            EmbeddedWindow embeddedWindow = (EmbeddedWindow) this.mWindows.valueAt(size);
            if (embeddedWindow.mHostActivityRecord == activityRecord && (processController = this.mAtmService.getProcessController(embeddedWindow.mOwnerPid, embeddedWindow.mOwnerUid)) != null) {
                processController.removeHostActivity(activityRecord);
            }
        }
    }

    public ArrayList getExcludeLayersByTaskToken(WindowContainerToken windowContainerToken) {
        ArrayList arrayList = new ArrayList();
        for (int size = this.mWindows.size() - 1; size >= 0; size--) {
            EmbeddedWindow embeddedWindow = (EmbeddedWindow) this.mWindows.valueAt(size);
            if (windowContainerToken.equals(embeddedWindow.mTaskToken)) {
                arrayList.add(embeddedWindow.mSurface);
            }
        }
        return arrayList;
    }

    public class EmbeddedWindow implements InputTarget {
        public final IWindow mClient;
        public final int mDisplayId;
        public final IBinder mFocusGrantToken;
        public boolean mFocusGranted;
        public final ActivityRecord mHostActivityRecord;
        public final WindowState mHostWindowState;
        public InputChannel mInputChannel;
        public boolean mIsFocusable;
        public final String mName;
        public final int mOwnerPid;
        public final int mOwnerUid;
        public Session mSession;
        public int mShowUserId;
        public SurfaceControl mSurface;
        public WindowContainerToken mTaskToken;
        public final int mWindowType;
        public final WindowManagerService mWmService;

        @Override // com.android.server.wm.InputTarget
        public boolean canScreenshotIme() {
            return true;
        }

        public EmbeddedWindow(Session session, WindowManagerService windowManagerService, IWindow iWindow, WindowState windowState, int i, int i2, int i3, int i4, IBinder iBinder, String str, boolean z) {
            this(session, windowManagerService, iWindow, windowState, i, i2, i3, i4, iBinder, str, z, null, null);
        }

        public EmbeddedWindow(Session session, WindowManagerService windowManagerService, IWindow iWindow, WindowState windowState, int i, int i2, int i3, int i4, IBinder iBinder, String str, boolean z, SurfaceControl surfaceControl, WindowContainerToken windowContainerToken) {
            String str2;
            if (CoreRune.MW_CAPTION_SHELL) {
                this.mSurface = surfaceControl;
                this.mTaskToken = windowContainerToken;
            }
            this.mSession = session;
            this.mWmService = windowManagerService;
            this.mClient = iWindow;
            this.mHostWindowState = windowState;
            this.mHostActivityRecord = windowState != null ? windowState.mActivityRecord : null;
            this.mOwnerUid = i;
            this.mOwnerPid = i2;
            this.mWindowType = i3;
            this.mDisplayId = i4;
            this.mFocusGrantToken = iBinder;
            if (windowState != null) {
                str2 = PackageManagerShellCommandDataLoader.STDIN_PATH + windowState.getWindowTag().toString();
            } else {
                str2 = "";
            }
            this.mIsFocusable = z;
            this.mName = "Embedded{" + str + str2 + "}";
            this.mShowUserId = UserHandle.getUserId(session.mUid);
        }

        public String toString() {
            return this.mName;
        }

        public InputApplicationHandle getApplicationHandle() {
            WindowState windowState = this.mHostWindowState;
            if (windowState == null || windowState.mInputWindowHandle.getInputApplicationHandle() == null) {
                return null;
            }
            return new InputApplicationHandle(this.mHostWindowState.mInputWindowHandle.getInputApplicationHandle());
        }

        public InputChannel openInputChannel() {
            InputChannel createInputChannel = this.mWmService.mInputManager.createInputChannel(toString());
            this.mInputChannel = createInputChannel;
            return createInputChannel;
        }

        public void onRemoved() {
            if (this.mFocusGranted) {
                this.mWmService.grantEmbeddedWindowFocus(this.mSession, this.mFocusGrantToken, false);
            }
            InputChannel inputChannel = this.mInputChannel;
            if (inputChannel != null) {
                this.mWmService.mInputManager.removeInputChannel(inputChannel.getToken());
                this.mInputChannel.dispose();
                this.mInputChannel = null;
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

        public void onGrantFocusChanged(boolean z) {
            if (this.mFocusGranted != z) {
                this.mFocusGranted = z;
                Slog.d("EmbeddedWindow", "onGrantFocusChanged: " + z + ", this=" + this + ", Callers=" + Debug.getCallers(2));
            }
        }

        @Override // com.android.server.wm.InputTarget
        public WindowState getWindowState() {
            return this.mHostWindowState;
        }

        @Override // com.android.server.wm.InputTarget
        public int getDisplayId() {
            return this.mDisplayId;
        }

        @Override // com.android.server.wm.InputTarget
        public DisplayContent getDisplayContent() {
            return this.mWmService.mRoot.getDisplayContent(getDisplayId());
        }

        @Override // com.android.server.wm.InputTarget
        public IWindow getIWindow() {
            return this.mClient;
        }

        public IBinder getWindowToken() {
            return this.mClient.asBinder();
        }

        @Override // com.android.server.wm.InputTarget
        public int getPid() {
            return this.mOwnerPid;
        }

        public IBinder getFocusGrantToken() {
            return this.mFocusGrantToken;
        }

        public IBinder getInputChannelToken() {
            InputChannel inputChannel = this.mInputChannel;
            if (inputChannel != null) {
                return inputChannel.getToken();
            }
            return null;
        }

        public void setIsFocusable(boolean z) {
            this.mIsFocusable = z;
        }

        @Override // com.android.server.wm.InputTarget
        public boolean receiveFocusFromTapOutside() {
            return this.mIsFocusable;
        }

        public final void handleTap(boolean z) {
            if (this.mInputChannel != null) {
                WindowState windowState = this.mHostWindowState;
                if (windowState != null) {
                    this.mWmService.grantEmbeddedWindowFocus(null, windowState.mClient, this.mFocusGrantToken, z);
                    if (z) {
                        this.mHostWindowState.handleTapOutsideFocusInsideSelf();
                        return;
                    }
                    return;
                }
                this.mWmService.grantEmbeddedWindowFocus(this.mSession, this.mFocusGrantToken, z);
            }
        }

        @Override // com.android.server.wm.InputTarget
        public void handleTapOutsideFocusOutsideSelf() {
            handleTap(false);
        }

        @Override // com.android.server.wm.InputTarget
        public void handleTapOutsideFocusInsideSelf() {
            handleTap(true);
        }

        @Override // com.android.server.wm.InputTarget
        public boolean shouldControlIme() {
            return this.mHostWindowState != null;
        }

        @Override // com.android.server.wm.InputTarget
        public InsetsControlTarget getImeControlTarget() {
            WindowState windowState = this.mHostWindowState;
            if (windowState != null) {
                return windowState.getImeControlTarget();
            }
            return this.mWmService.getDefaultDisplayContentLocked().mRemoteInsetsControlTarget;
        }

        @Override // com.android.server.wm.InputTarget
        public boolean isInputMethodClientFocus(int i, int i2) {
            return i == this.mOwnerUid && i2 == this.mOwnerPid;
        }

        @Override // com.android.server.wm.InputTarget
        public ActivityRecord getActivityRecord() {
            return this.mHostActivityRecord;
        }

        @Override // com.android.server.wm.InputTarget
        public void dumpProto(ProtoOutputStream protoOutputStream, long j, int i) {
            long start = protoOutputStream.start(j);
            long start2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, System.identityHashCode(this));
            protoOutputStream.write(1138166333443L, "EmbeddedWindow");
            protoOutputStream.end(start2);
            protoOutputStream.end(start);
        }
    }
}
