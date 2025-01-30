package android.window;

import android.graphics.Rect;
import android.graphics.RectF;
import android.p009os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.InputWindowHandle;
import android.window.WindowInfosListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class WindowInfosListenerForTest {
    private static final String TAG = "WindowInfosListenerForTest";
    private ArrayMap<Consumer<List<WindowInfo>>, WindowInfosListener> mListeners = new ArrayMap<>();

    public static class WindowInfo {
        public final Rect bounds;
        public final int displayId;
        public final boolean isTrustedOverlay;
        public final boolean isVisible;
        public final String name;
        public final IBinder windowToken;

        WindowInfo(IBinder windowToken, String name, int displayId, Rect bounds, int inputConfig) {
            this.windowToken = windowToken;
            this.name = name;
            this.displayId = displayId;
            this.bounds = bounds;
            this.isTrustedOverlay = (inputConfig & 256) != 0;
            this.isVisible = (inputConfig & 2) == 0;
        }
    }

    public void addWindowInfosListener(final Consumer<List<WindowInfo>> consumer) {
        final CountDownLatch calledWithInitialState = new CountDownLatch(1);
        WindowInfosListener windowInfosListener = new WindowInfosListener() { // from class: android.window.WindowInfosListenerForTest.1
            @Override // android.window.WindowInfosListener
            public void onWindowInfosChanged(InputWindowHandle[] windowHandles, WindowInfosListener.DisplayInfo[] displayInfos) {
                try {
                    calledWithInitialState.await();
                } catch (InterruptedException e) {
                    Log.m96e(WindowInfosListenerForTest.TAG, "Exception thrown while waiting for listener to be called with initial state");
                }
                consumer.accept(WindowInfosListenerForTest.buildWindowInfos(windowHandles, displayInfos));
            }
        };
        this.mListeners.put(consumer, windowInfosListener);
        Pair<InputWindowHandle[], WindowInfosListener.DisplayInfo[]> initialState = windowInfosListener.register();
        consumer.accept(buildWindowInfos(initialState.first, initialState.second));
        calledWithInitialState.countDown();
    }

    public void removeWindowInfosListener(Consumer<List<WindowInfo>> consumer) {
        WindowInfosListener listener = this.mListeners.remove(consumer);
        if (listener == null) {
            return;
        }
        listener.unregister();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<WindowInfo> buildWindowInfos(InputWindowHandle[] windowHandles, WindowInfosListener.DisplayInfo[] displayInfos) {
        ArrayList<WindowInfo> windowInfos = new ArrayList<>(windowHandles.length);
        SparseArray<WindowInfosListener.DisplayInfo> displayInfoById = new SparseArray<>(displayInfos.length);
        for (WindowInfosListener.DisplayInfo displayInfo : displayInfos) {
            displayInfoById.put(displayInfo.mDisplayId, displayInfo);
        }
        RectF tmp = new RectF();
        for (InputWindowHandle handle : windowHandles) {
            Rect bounds = new Rect(handle.frameLeft, handle.frameTop, handle.frameRight, handle.frameBottom);
            WindowInfosListener.DisplayInfo display = displayInfoById.get(handle.displayId);
            if (display != null) {
                tmp.set(bounds);
                display.mTransform.mapRect(tmp);
                tmp.round(bounds);
            }
            windowInfos.add(new WindowInfo(handle.getWindowToken(), handle.name, handle.displayId, bounds, handle.inputConfig));
        }
        return windowInfos;
    }
}
