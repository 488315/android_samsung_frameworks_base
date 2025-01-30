package com.android.wm.shell;

import android.app.ResourcesManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.DisplayAreaInfo;
import android.window.DisplayAreaOrganizer;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RootTaskDisplayAreaOrganizer extends DisplayAreaOrganizer {
    public final Context mContext;
    public final SparseArray mDisplayAreaContexts;
    public final SparseArray mDisplayAreasInfo;
    public final SparseArray mLeashes;
    public final SparseArray mListeners;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class DisplayAreaContext extends ContextWrapper {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface RootTaskDisplayAreaListener {
    }

    public RootTaskDisplayAreaOrganizer(Executor executor, Context context) {
        super(executor);
        this.mDisplayAreasInfo = new SparseArray();
        this.mLeashes = new SparseArray();
        this.mListeners = new SparseArray();
        this.mDisplayAreaContexts = new SparseArray();
        this.mContext = context;
        List registerOrganizer = registerOrganizer(1);
        for (int size = registerOrganizer.size() - 1; size >= 0; size--) {
            onDisplayAreaAppeared(((DisplayAreaAppearedInfo) registerOrganizer.get(size)).getDisplayAreaInfo(), ((DisplayAreaAppearedInfo) registerOrganizer.get(size)).getLeash());
        }
    }

    public final void applyConfigChangesToContext(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        Display display = ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(i);
        if (display == null) {
            if (ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled) {
                ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -581313329, 1, null, Long.valueOf(i));
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
    }

    public final void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) == null) {
            Slog.w("RootTaskDisplayAreaOrganizer", "onDisplayAreaInfoChanged() Unknown DA displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
            return;
        }
        this.mDisplayAreasInfo.put(i, displayAreaInfo);
        ArrayList arrayList = (ArrayList) this.mListeners.get(i);
        if (arrayList != null) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else {
                    ((RootTaskDisplayAreaListener) arrayList.get(size)).getClass();
                }
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
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else {
                    ((RootTaskDisplayAreaListener) arrayList.get(size)).getClass();
                }
            }
        }
        this.mDisplayAreaContexts.remove(i);
    }

    public final String toString() {
        return "RootTaskDisplayAreaOrganizer#" + this.mDisplayAreasInfo.size();
    }
}
