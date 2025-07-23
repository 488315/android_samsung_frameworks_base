package com.android.wm.shell;

import android.app.ResourcesManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.Display;
import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.DisplayAreaInfo;
import android.window.DisplayAreaOrganizer;
import android.window.SystemPerformanceHinter;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellInit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RootTaskDisplayAreaOrganizer extends DisplayAreaOrganizer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final SparseArray mDisplayAreaContexts;
    public final SparseArray mDisplayAreasInfo;
    public final SparseArray mLeashes;
    public final SparseArray mListeners;
    public final SparseArray mPendingDesktopModeEligibleChanged;
    public final SparseArray mPendingDisplayAdded;
    public final SystemPerformanceHinter.DisplayRootProvider mPerfRootProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DisplayAreaContext extends ContextWrapper {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final ResourcesManager mResourcesManager;
        public final IBinder mToken;

        public DisplayAreaContext(Context context, Display display) {
            super(null);
            Binder binder = new Binder();
            this.mToken = binder;
            this.mResourcesManager = ResourcesManager.getInstance();
            attachBaseContext(context.createTokenContext(binder, display));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface RootTaskDisplayAreaListener {
    }

    public RootTaskDisplayAreaOrganizer(Executor executor, Context context, ShellInit shellInit) {
        super(executor);
        this.mDisplayAreasInfo = new SparseArray();
        this.mLeashes = new SparseArray();
        this.mListeners = new SparseArray();
        this.mDisplayAreaContexts = new SparseArray();
        this.mPerfRootProvider = new SystemPerformanceHinter.DisplayRootProvider() { // from class: com.android.wm.shell.RootTaskDisplayAreaOrganizer.1
            public final SurfaceControl getRootForDisplay(int i) {
                return (SurfaceControl) RootTaskDisplayAreaOrganizer.this.mLeashes.get(i);
            }
        };
        this.mPendingDisplayAdded = new SparseArray();
        this.mPendingDesktopModeEligibleChanged = new SparseArray();
        this.mContext = context;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.RootTaskDisplayAreaOrganizer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = RootTaskDisplayAreaOrganizer.this;
                int i = RootTaskDisplayAreaOrganizer.$r8$clinit;
                List registerOrganizer = rootTaskDisplayAreaOrganizer.registerOrganizer(1);
                for (int size = registerOrganizer.size() - 1; size >= 0; size--) {
                    rootTaskDisplayAreaOrganizer.onDisplayAreaAppeared(((DisplayAreaAppearedInfo) registerOrganizer.get(size)).getDisplayAreaInfo(), ((DisplayAreaAppearedInfo) registerOrganizer.get(size)).getLeash());
                }
            }
        }, this);
    }

    public final void applyConfigChangesToContext(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        Display display = ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(i);
        if (display == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -2444964539964575288L, 1, Long.valueOf(i));
                return;
            }
            return;
        }
        DisplayAreaContext displayAreaContext = (DisplayAreaContext) this.mDisplayAreaContexts.get(i);
        if (displayAreaContext == null) {
            displayAreaContext = new DisplayAreaContext(this.mContext, display);
            this.mDisplayAreaContexts.put(i, displayAreaContext);
        }
        Configuration configuration = displayAreaInfo.configuration;
        int i2 = DisplayAreaContext.$r8$clinit;
        if (displayAreaContext.getResources().getConfiguration().diff(configuration) != 0) {
            displayAreaContext.mResourcesManager.updateResourcesForActivity(displayAreaContext.mToken, configuration, displayAreaContext.getDisplayId());
        }
    }

    public final void attachToDisplayArea(int i, SurfaceControl.Builder builder) {
        builder.setParent((SurfaceControl) this.mLeashes.get(i));
    }

    public final DisplayAreaInfo getDisplayAreaInfo(int i) {
        return (DisplayAreaInfo) this.mDisplayAreasInfo.get(i);
    }

    public final int[] getDisplayIds() {
        int[] iArr = new int[this.mDisplayAreasInfo.size()];
        for (int i = 0; i < this.mDisplayAreasInfo.size(); i++) {
            iArr[i] = this.mDisplayAreasInfo.keyAt(i);
        }
        return iArr;
    }

    public final void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) {
        if (displayAreaInfo.featureId != 1) {
            throw new IllegalArgumentException("Unknown feature: " + displayAreaInfo.featureId + "displayAreaInfo:" + displayAreaInfo);
        }
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) != null) {
            throw new IllegalArgumentException("Duplicate DA for displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
        }
        surfaceControl.setUnreleasedWarningCallSite("RootTaskDisplayAreaOrganizer.onDisplayAreaAppeared");
        this.mDisplayAreasInfo.put(i, displayAreaInfo);
        this.mLeashes.put(i, surfaceControl);
        ArrayList arrayList = (ArrayList) this.mListeners.get(i);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((RootTaskDisplayAreaListener) arrayList.get(size)).getClass();
            }
        }
        applyConfigChangesToContext(displayAreaInfo);
        if (i != 0) {
            if (this.mPendingDisplayAdded.get(i) != null) {
                getExecutor().execute((Runnable) this.mPendingDisplayAdded.get(i));
                this.mPendingDisplayAdded.remove(i);
            }
            if (this.mPendingDesktopModeEligibleChanged.get(i) != null) {
                getExecutor().execute((Runnable) this.mPendingDesktopModeEligibleChanged.get(i));
                this.mPendingDesktopModeEligibleChanged.remove(i);
            }
        }
    }

    public final void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) == null) {
            throw new IllegalArgumentException("onDisplayAreaInfoChanged() Unknown DA displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
        }
        this.mDisplayAreasInfo.put(i, displayAreaInfo);
        ArrayList arrayList = (ArrayList) this.mListeners.get(i);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((RootTaskDisplayAreaListener) arrayList.get(size)).getClass();
            }
        }
        applyConfigChangesToContext(displayAreaInfo);
    }

    public final void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) == null) {
            throw new IllegalArgumentException("onDisplayAreaVanished() Unknown DA displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
        }
        this.mDisplayAreasInfo.remove(i);
        ((SurfaceControl) this.mLeashes.get(i)).release();
        this.mLeashes.remove(i);
        ArrayList arrayList = (ArrayList) this.mListeners.get(i);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((RootTaskDisplayAreaListener) arrayList.get(size)).getClass();
            }
        }
        this.mDisplayAreaContexts.remove(i);
    }

    public final void reparentToDisplayArea(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.reparent(surfaceControl, (SurfaceControl) this.mLeashes.get(i));
    }

    public final String toString() {
        return "RootTaskDisplayAreaOrganizer#" + this.mDisplayAreasInfo.size();
    }
}
