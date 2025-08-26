package android.window;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.InputWindowHandle;
import android.window.WindowInfosListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class WindowInfosListenerForTest {
    private static final String TAG = "WindowInfosListenerForTest";
    private final ArrayMap<BiConsumer<List<WindowInfo>, List<DisplayInfo>>, WindowInfosListener> mListeners = new ArrayMap<>();
    private final ArrayMap<Consumer<List<WindowInfo>>, BiConsumer<List<WindowInfo>, List<DisplayInfo>>> mConsumersToBiConsumers = new ArrayMap<>();

    public static class WindowInfo {
        public final Rect bounds;
        public final int displayId;
        public final boolean isDuplicateTouchToWallpaper;
        public final boolean isFocusable;
        public final boolean isTouchable;
        public final boolean isTrustedOverlay;
        public final boolean isVisible;
        public final boolean isWatchOutsideTouch;
        public final String name;
        public final Matrix transform;
        public final IBinder windowToken;

        WindowInfo(IBinder iBinder, String str, int i, Rect rect, int i2, Matrix matrix) {
            this.windowToken = iBinder;
            this.name = str;
            this.displayId = i;
            this.bounds = rect;
            this.isTrustedOverlay = (i2 & 256) != 0;
            this.isVisible = (i2 & 2) == 0;
            this.transform = matrix;
            this.isTouchable = (i2 & 8) == 0;
            this.isFocusable = (i2 & 4) == 0;
            this.isDuplicateTouchToWallpaper = (i2 & 32) != 0;
            this.isWatchOutsideTouch = (i2 & 512) != 0;
        }

        public String toString() {
            return this.name + ", displayId=" + this.displayId + ", frame=" + this.bounds + ", isVisible=" + this.isVisible + ", isTrustedOverlay=" + this.isTrustedOverlay + ", token=" + this.windowToken + ", transform=" + this.transform;
        }
    }

    public static class DisplayInfo {
        public final int displayId;
        public final Matrix transform;

        DisplayInfo(int i, Matrix matrix) {
            this.displayId = i;
            this.transform = matrix;
        }

        public String toString() {
            return TextUtils.formatSimple("DisplayInfo{displayId=%s, transform=%s}", Integer.valueOf(this.displayId), this.transform);
        }
    }

    @Deprecated
    public void addWindowInfosListener(final Consumer<List<WindowInfo>> consumer) {
        BiConsumer<List<WindowInfo>, List<DisplayInfo>> biConsumer = new BiConsumer() { // from class: android.window.WindowInfosListenerForTest$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept((List) obj);
            }
        };
        this.mConsumersToBiConsumers.put(consumer, biConsumer);
        addWindowInfosListener(biConsumer);
    }

    public void addWindowInfosListener(final BiConsumer<List<WindowInfo>, List<DisplayInfo>> biConsumer) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        WindowInfosListener windowInfosListener = new WindowInfosListener(this) { // from class: android.window.WindowInfosListenerForTest.1
            @Override // android.window.WindowInfosListener
            public void onWindowInfosChanged(InputWindowHandle[] inputWindowHandleArr, WindowInfosListener.DisplayInfo[] displayInfoArr) throws InterruptedException {
                try {
                    countDownLatch.await();
                } catch (InterruptedException unused) {
                    Log.e(WindowInfosListenerForTest.TAG, "Exception thrown while waiting for listener to be called with initial state");
                }
                Pair pairBuildParams = WindowInfosListenerForTest.buildParams(inputWindowHandleArr, displayInfoArr);
                biConsumer.accept((List) pairBuildParams.first, (List) pairBuildParams.second);
            }
        };
        this.mListeners.put(biConsumer, windowInfosListener);
        Pair<InputWindowHandle[], WindowInfosListener.DisplayInfo[]> pairRegister = windowInfosListener.register();
        Pair<List<WindowInfo>, List<DisplayInfo>> pairBuildParams = buildParams(pairRegister.first, pairRegister.second);
        biConsumer.accept(pairBuildParams.first, pairBuildParams.second);
        countDownLatch.countDown();
    }

    @Deprecated
    public void removeWindowInfosListener(Consumer<List<WindowInfo>> consumer) {
        WindowInfosListener windowInfosListenerRemove;
        BiConsumer<List<WindowInfo>, List<DisplayInfo>> biConsumerRemove = this.mConsumersToBiConsumers.remove(consumer);
        if (biConsumerRemove == null || (windowInfosListenerRemove = this.mListeners.remove(biConsumerRemove)) == null) {
            return;
        }
        windowInfosListenerRemove.unregister();
    }

    public void removeWindowInfosListener(BiConsumer<List<WindowInfo>, List<DisplayInfo>> biConsumer) {
        WindowInfosListener windowInfosListenerRemove = this.mListeners.remove(biConsumer);
        if (windowInfosListenerRemove == null) {
            return;
        }
        windowInfosListenerRemove.unregister();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Pair<List<WindowInfo>, List<DisplayInfo>> buildParams(InputWindowHandle[] inputWindowHandleArr, WindowInfosListener.DisplayInfo[] displayInfoArr) {
        ArrayList arrayList = new ArrayList(inputWindowHandleArr.length);
        ArrayList arrayList2 = new ArrayList(displayInfoArr.length);
        SparseArray sparseArray = new SparseArray(displayInfoArr.length);
        for (WindowInfosListener.DisplayInfo displayInfo : displayInfoArr) {
            sparseArray.put(displayInfo.mDisplayId, displayInfo);
        }
        for (WindowInfosListener.DisplayInfo displayInfo2 : displayInfoArr) {
            arrayList2.add(new DisplayInfo(displayInfo2.mDisplayId, displayInfo2.mTransform));
        }
        RectF rectF = new RectF();
        for (InputWindowHandle inputWindowHandle : inputWindowHandleArr) {
            Rect rect = new Rect(inputWindowHandle.frame);
            WindowInfosListener.DisplayInfo displayInfo3 = (WindowInfosListener.DisplayInfo) sparseArray.get(inputWindowHandle.displayId);
            if (displayInfo3 != null) {
                rectF.set(rect);
                displayInfo3.mTransform.mapRect(rectF);
                rectF.round(rect);
            }
            arrayList.add(new WindowInfo(inputWindowHandle.getWindowToken(), inputWindowHandle.name, inputWindowHandle.displayId, rect, inputWindowHandle.inputConfig, inputWindowHandle.transform));
        }
        return new Pair<>(arrayList, arrayList2);
    }
}
