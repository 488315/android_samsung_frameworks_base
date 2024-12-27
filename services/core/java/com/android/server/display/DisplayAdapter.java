package com.android.server.display;

import android.content.Context;
import android.os.Handler;
import com.android.server.display.DisplayDeviceRepository;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.feature.DisplayManagerFlags;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class DisplayAdapter {
    public static final AtomicInteger NEXT_DISPLAY_MODE_ID = new AtomicInteger(1);
    public final Context mContext;
    public final DisplayManagerFlags mFeatureFlags;
    public final Handler mHandler;
    public final Listener mListener;
    public final String mName;
    public final DisplayManagerService.SyncRoot mSyncRoot;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Listener {
    }

    public DisplayAdapter(DisplayManagerService.SyncRoot syncRoot, Context context, Handler handler, Listener listener, String str, DisplayManagerFlags displayManagerFlags) {
        this.mSyncRoot = syncRoot;
        this.mContext = context;
        this.mHandler = handler;
        this.mListener = listener;
        this.mName = str;
        this.mFeatureFlags = displayManagerFlags;
    }

    public void dumpLocked(PrintWriter printWriter) {
    }

    public final void sendDisplayDeviceEventLocked(final DisplayDevice displayDevice, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.display.DisplayAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DisplayAdapter displayAdapter = DisplayAdapter.this;
                ((DisplayDeviceRepository) displayAdapter.mListener).onDisplayDeviceEvent(displayDevice, i);
            }
        });
    }

    public final void sendTraversalRequestLocked() {
        this.mHandler.post(new Runnable() { // from class: com.android.server.display.DisplayAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DisplayDeviceRepository displayDeviceRepository = (DisplayDeviceRepository) DisplayAdapter.this.mListener;
                int size = ((ArrayList) displayDeviceRepository.mListeners).size();
                for (int i = 0; i < size; i++) {
                    DisplayManagerService.AnonymousClass1 anonymousClass1 = ((LogicalDisplayMapper) ((DisplayDeviceRepository.Listener) ((ArrayList) displayDeviceRepository.mListeners).get(i))).mListener;
                    synchronized (DisplayManagerService.this.mSyncRoot) {
                        DisplayManagerService.this.scheduleTraversalLocked(false);
                    }
                }
            }
        });
    }
}
