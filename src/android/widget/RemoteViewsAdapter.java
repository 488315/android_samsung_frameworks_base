package android.widget;

import android.app.IServiceConnection;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import com.android.internal.R;
import com.android.internal.widget.IRemoteViewsFactory;
import com.samsung.android.cocktailbar.CocktailBarManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class RemoteViewsAdapter extends BaseAdapter implements Handler.Callback {
    private static final int CACHE_RESET_CONFIG_FLAGS = -1073737216;
    private static final int DEFAULT_CACHE_SIZE = 40;
    private static final int DEFAULT_LOADING_VIEW_HEIGHT = 50;
    static final int MSG_LOAD_NEXT_ITEM = 3;
    private static final int MSG_MAIN_HANDLER_COMMIT_METADATA = 1;
    private static final int MSG_MAIN_HANDLER_REMOTE_ADAPTER_CONNECTED = 3;
    private static final int MSG_MAIN_HANDLER_REMOTE_ADAPTER_DISCONNECTED = 4;
    private static final int MSG_MAIN_HANDLER_REMOTE_VIEWS_LOADED = 5;
    private static final int MSG_MAIN_HANDLER_SUPER_NOTIFY_DATA_SET_CHANGED = 2;
    static final int MSG_NOTIFY_DATA_SET_CHANGED = 2;
    static final int MSG_REQUEST_BIND = 1;
    static final int MSG_UNBIND_SERVICE = 4;
    private static final int REMOTE_VIEWS_CACHE_DURATION = 5000;
    private static final String TAG = "RemoteViewsAdapter";
    private static final int UNBIND_SERVICE_DELAY = 5000;
    private static Handler sCacheRemovalQueue;
    private static HandlerThread sCacheRemovalThread;
    private static final HashMap<RemoteViewsCacheKey, FixedSizeRemoteViewsCache> sCachedRemoteViewsCaches = new HashMap<>();
    private static final HashMap<RemoteViewsCacheKey, Runnable> sRemoteViewsCacheRemoveRunnables = new HashMap<>();
    private final int mAppWidgetId;
    private final Executor mAsyncViewLoadExecutor;
    private final FixedSizeRemoteViewsCache mCache;
    private final RemoteAdapterConnectionCallback mCallback;
    private final Context mContext;
    private boolean mDataReady;
    private final Intent mIntent;
    private ApplicationInfo mLastRemoteViewAppInfo;
    private final Handler mMainHandler;
    private final boolean mOnLightBackground;
    private RemoteViews.InteractionHandler mRemoteViewsInteractionHandler;
    private RemoteViewsFrameLayoutRefSet mRequestedViews;
    private final RemoteServiceHandler mServiceHandler;
    private boolean mUsePreloadPositionIndices = true;
    private int mVisibleWindowLowerBound;
    private int mVisibleWindowUpperBound;
    private final HandlerThread mWorkerThread;

    public interface RemoteAdapterConnectionCallback {
        void deferNotifyDataSetChanged();

        boolean onRemoteAdapterConnected();

        void onRemoteAdapterDisconnected();

        void setRemoteViewsAdapter(Intent intent, boolean z);
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    public static class AsyncRemoteAdapterAction implements Runnable {
        private final RemoteAdapterConnectionCallback mCallback;
        private final Intent mIntent;

        public AsyncRemoteAdapterAction(RemoteAdapterConnectionCallback remoteAdapterConnectionCallback, Intent intent) {
            this.mCallback = remoteAdapterConnectionCallback;
            this.mIntent = intent;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mCallback.setRemoteViewsAdapter(this.mIntent, true);
        }
    }

    private static class RemoteServiceHandler extends Handler implements ServiceConnection {
        private static String DISPLAY_CATEGORY_BUILTIN = "com.samsung.android.hardware.display.category.BUILTIN";
        private static int EXTRA_BUILT_IN_DISPLAY = 1;
        private final WeakReference<RemoteViewsAdapter> mAdapter;
        private boolean mBindRequested;
        private final Context mContext;
        private final DisplayManager mDisplayManager;
        private boolean mNotifyDataSetChangedPending;
        private IRemoteViewsFactory mRemoteViewsFactory;

        RemoteServiceHandler(Looper looper, RemoteViewsAdapter remoteViewsAdapter, Context context) {
            super(looper);
            this.mNotifyDataSetChangedPending = false;
            this.mBindRequested = false;
            this.mAdapter = new WeakReference<>(remoteViewsAdapter);
            this.mContext = context;
            this.mDisplayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.mRemoteViewsFactory = IRemoteViewsFactory.Stub.asInterface(iBinder);
            enqueueDeferredUnbindServiceMessage();
            RemoteViewsAdapter remoteViewsAdapter = this.mAdapter.get();
            if (remoteViewsAdapter == null) {
                return;
            }
            if (this.mNotifyDataSetChangedPending) {
                this.mNotifyDataSetChangedPending = false;
                Message obtain = Message.obtain(this, 2);
                handleMessage(obtain);
                obtain.recycle();
                return;
            }
            if (sendNotifyDataSetChange(false)) {
                remoteViewsAdapter.updateTemporaryMetaData(this.mRemoteViewsFactory);
                remoteViewsAdapter.mMainHandler.sendEmptyMessage(1);
                remoteViewsAdapter.mMainHandler.sendEmptyMessage(3);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.mRemoteViewsFactory = null;
            RemoteViewsAdapter remoteViewsAdapter = this.mAdapter.get();
            if (remoteViewsAdapter != null) {
                remoteViewsAdapter.mMainHandler.sendEmptyMessage(4);
            }
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            unbindNow();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i;
            int[] visibleWindow;
            RemoteViewsAdapter remoteViewsAdapter = this.mAdapter.get();
            int i2 = message.what;
            if (i2 == 1) {
                if (remoteViewsAdapter == null || this.mRemoteViewsFactory != null) {
                    enqueueDeferredUnbindServiceMessage();
                }
                if (this.mBindRequested) {
                    Log.e(RemoteViewsAdapter.TAG, "Already requested Bind Message");
                    return;
                }
                IServiceConnection serviceDispatcher = this.mContext.getServiceDispatcher(this, this, InputDevice.SOURCE_HDMI);
                Intent intent = (Intent) message.obj;
                int i3 = message.arg1;
                try {
                    if (intent.getIntExtra("remoteAdapterCocktail", -1) == 1) {
                        CocktailBarManager cocktailBarManager = CocktailBarManager.getInstance(this.mContext);
                        if (remoteViewsAdapter != null) {
                            this.mBindRequested = cocktailBarManager.bindRemoteViewsService(this.mContext, i3, intent, serviceDispatcher, InputDevice.SOURCE_HDMI);
                            return;
                        } else {
                            Log.e(RemoteViewsAdapter.TAG, "bind: adapter was null");
                            return;
                        }
                    }
                    this.mBindRequested = AppWidgetManager.getInstance(this.mContext).bindRemoteViewsService(this.mContext, i3, intent, serviceDispatcher, InputDevice.SOURCE_HDMI);
                    return;
                } catch (Exception e) {
                    Log.e(RemoteViewsAdapter.TAG, "Failed to bind remoteViewsService: " + e.getMessage());
                    return;
                }
            }
            if (i2 != 2) {
                if (i2 == 3) {
                    if (remoteViewsAdapter == null || this.mRemoteViewsFactory == null) {
                        return;
                    }
                    removeMessages(4);
                    int nextIndexToLoad = remoteViewsAdapter.mCache.getNextIndexToLoad();
                    if (nextIndexToLoad > -1) {
                        remoteViewsAdapter.updateRemoteViews(this.mRemoteViewsFactory, nextIndexToLoad, true);
                        sendEmptyMessage(3);
                        return;
                    } else {
                        enqueueDeferredUnbindServiceMessage();
                        return;
                    }
                }
                if (i2 != 4) {
                    return;
                }
                unbindNow();
                return;
            }
            enqueueDeferredUnbindServiceMessage();
            if (remoteViewsAdapter == null) {
                return;
            }
            boolean isCoverDisplay = isCoverDisplay();
            if (!isCoverDisplay) {
                synchronized (remoteViewsAdapter.mCache) {
                    remoteViewsAdapter.mCache.reset();
                }
            }
            if (this.mRemoteViewsFactory == null) {
                this.mNotifyDataSetChangedPending = true;
                remoteViewsAdapter.requestBindService();
                return;
            }
            if (sendNotifyDataSetChange(true)) {
                if (isCoverDisplay) {
                    synchronized (remoteViewsAdapter.mCache) {
                        remoteViewsAdapter.mCache.reset();
                    }
                }
                remoteViewsAdapter.updateTemporaryMetaData(this.mRemoteViewsFactory);
                synchronized (remoteViewsAdapter.mCache.getTemporaryMetaData()) {
                    i = remoteViewsAdapter.mCache.getTemporaryMetaData().count;
                    visibleWindow = remoteViewsAdapter.getVisibleWindow(i);
                }
                for (int i4 : visibleWindow) {
                    if (i4 < i) {
                        remoteViewsAdapter.updateRemoteViews(this.mRemoteViewsFactory, i4, false);
                    }
                }
                remoteViewsAdapter.mMainHandler.sendEmptyMessage(1);
                remoteViewsAdapter.mMainHandler.sendEmptyMessage(2);
            }
        }

        private boolean isCoverDisplay() {
            Display display;
            Display[] displays = this.mDisplayManager.getDisplays(DISPLAY_CATEGORY_BUILTIN);
            int length = displays.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    display = null;
                    break;
                }
                display = displays[i];
                if (display.getDisplayId() == EXTRA_BUILT_IN_DISPLAY && display.getState() == 2) {
                    break;
                }
                i++;
            }
            return display != null;
        }

        protected void unbindNow() {
            if (this.mBindRequested) {
                this.mBindRequested = false;
                this.mContext.unbindService(this);
            }
            this.mRemoteViewsFactory = null;
        }

        private boolean sendNotifyDataSetChange(boolean z) {
            if (!z) {
                try {
                    if (this.mRemoteViewsFactory.isCreated()) {
                        return true;
                    }
                } catch (RemoteException | RuntimeException e) {
                    Log.e(RemoteViewsAdapter.TAG, "Error in updateNotifyDataSetChanged(): " + e.getMessage());
                    return false;
                }
            }
            this.mRemoteViewsFactory.onDataSetChanged();
            return true;
        }

        private void enqueueDeferredUnbindServiceMessage() {
            removeMessages(4);
            sendEmptyMessageDelayed(4, 5000L);
        }
    }

    static class RemoteViewsFrameLayout extends AppWidgetHostView.AdapterChildHostView {
        public int cacheIndex;
        private final FixedSizeRemoteViewsCache mCache;

        public RemoteViewsFrameLayout(Context context, FixedSizeRemoteViewsCache fixedSizeRemoteViewsCache) {
            super(context);
            this.cacheIndex = -1;
            this.mCache = fixedSizeRemoteViewsCache;
        }

        public void onRemoteViewsLoaded(RemoteViews remoteViews, RemoteViews.InteractionHandler interactionHandler, boolean z) {
            setInteractionHandler(interactionHandler);
            applyRemoteViews(remoteViews, z || (remoteViews != null && remoteViews.prefersAsyncApply()));
        }

        @Override // android.appwidget.AppWidgetHostView
        protected View getDefaultView() {
            int i = this.mCache.getMetaData().getLoadingTemplate(getContext()).defaultHeight;
            TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.remote_views_adapter_default_loading_view, (ViewGroup) this, false);
            textView.setHeight(i);
            return textView;
        }

        @Override // android.appwidget.AppWidgetHostView
        protected View getErrorView() {
            return getDefaultView();
        }
    }

    private class RemoteViewsFrameLayoutRefSet extends SparseArray<ArrayList<RemoteViewsFrameLayout>> {
        private RemoteViewsFrameLayoutRefSet() {
        }

        public void add(int i, RemoteViewsFrameLayout remoteViewsFrameLayout) {
            ArrayList<RemoteViewsFrameLayout> arrayList = get(i);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                put(i, arrayList);
            }
            remoteViewsFrameLayout.cacheIndex = i;
            arrayList.add(remoteViewsFrameLayout);
        }

        public void notifyOnRemoteViewsLoaded(int i, RemoteViews remoteViews) {
            ArrayList<RemoteViewsFrameLayout> removeReturnOld;
            if (remoteViews == null || (removeReturnOld = removeReturnOld(i)) == null) {
                return;
            }
            Iterator<RemoteViewsFrameLayout> it = removeReturnOld.iterator();
            while (it.hasNext()) {
                it.next().onRemoteViewsLoaded(remoteViews, RemoteViewsAdapter.this.mRemoteViewsInteractionHandler, true);
            }
        }

        public void removeView(RemoteViewsFrameLayout remoteViewsFrameLayout) {
            if (remoteViewsFrameLayout.cacheIndex < 0) {
                return;
            }
            ArrayList<RemoteViewsFrameLayout> arrayList = get(remoteViewsFrameLayout.cacheIndex);
            if (arrayList != null) {
                arrayList.remove(remoteViewsFrameLayout);
            }
            remoteViewsFrameLayout.cacheIndex = -1;
        }
    }

    private static class RemoteViewsMetaData {
        int count;
        boolean hasStableIds;
        LoadingViewTemplate loadingTemplate;
        private final SparseIntArray mTypeIdIndexMap = new SparseIntArray();
        int viewTypeCount;

        public RemoteViewsMetaData() {
            reset();
        }

        public void set(RemoteViewsMetaData remoteViewsMetaData) {
            synchronized (remoteViewsMetaData) {
                this.count = remoteViewsMetaData.count;
                this.viewTypeCount = remoteViewsMetaData.viewTypeCount;
                this.hasStableIds = remoteViewsMetaData.hasStableIds;
                this.loadingTemplate = remoteViewsMetaData.loadingTemplate;
            }
        }

        public void reset() {
            this.count = 0;
            this.viewTypeCount = 1;
            this.hasStableIds = true;
            this.loadingTemplate = null;
            this.mTypeIdIndexMap.clear();
        }

        public int getMappedViewType(int i) {
            int i2 = this.mTypeIdIndexMap.get(i, -1);
            if (i2 != -1) {
                return i2;
            }
            int size = this.mTypeIdIndexMap.size() + 1;
            this.mTypeIdIndexMap.put(i, size);
            return size;
        }

        public boolean isViewTypeInRange(int i) {
            return getMappedViewType(i) < this.viewTypeCount;
        }

        public synchronized LoadingViewTemplate getLoadingTemplate(Context context) {
            if (this.loadingTemplate == null) {
                this.loadingTemplate = new LoadingViewTemplate(null, context);
            }
            return this.loadingTemplate;
        }
    }

    private static class RemoteViewsIndexMetaData {
        long itemId;
        int typeId;

        public RemoteViewsIndexMetaData(RemoteViews remoteViews, long j) {
            set(remoteViews, j);
        }

        public void set(RemoteViews remoteViews, long j) {
            this.itemId = j;
            if (remoteViews != null) {
                this.typeId = remoteViews.getLayoutId();
            } else {
                this.typeId = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FixedSizeRemoteViewsCache {
        private static final float sMaxCountSlackPercent = 0.75f;
        private static final int sMaxMemoryLimitInBytes = 2097152;
        private final Configuration mConfiguration;
        private final int mMaxCount;
        private final int mMaxCountSlack;
        private final RemoteViewsMetaData mMetaData = new RemoteViewsMetaData();
        private final RemoteViewsMetaData mTemporaryMetaData = new RemoteViewsMetaData();
        private final SparseArray<RemoteViewsIndexMetaData> mIndexMetaData = new SparseArray<>();
        private final SparseArray<RemoteViews> mIndexRemoteViews = new SparseArray<>();
        private final SparseBooleanArray mIndicesToLoad = new SparseBooleanArray();
        private int mPreloadLowerBound = 0;
        private int mPreloadUpperBound = -1;
        private int mLastRequestedIndex = -1;

        FixedSizeRemoteViewsCache(int i, Configuration configuration) {
            this.mMaxCount = i;
            this.mMaxCountSlack = Math.round((i / 2) * 0.75f);
            this.mConfiguration = new Configuration(configuration);
        }

        public void insert(int i, RemoteViews remoteViews, long j, int[] iArr) {
            int farthestPositionFrom;
            if (this.mIndexRemoteViews.size() >= this.mMaxCount) {
                this.mIndexRemoteViews.remove(getFarthestPositionFrom(i, iArr));
            }
            int i2 = this.mLastRequestedIndex;
            if (i2 <= -1) {
                i2 = i;
            }
            while (getRemoteViewsBitmapMemoryUsage() >= 2097152 && (farthestPositionFrom = getFarthestPositionFrom(i2, iArr)) >= 0) {
                this.mIndexRemoteViews.remove(farthestPositionFrom);
            }
            RemoteViewsIndexMetaData remoteViewsIndexMetaData = this.mIndexMetaData.get(i);
            if (remoteViewsIndexMetaData != null) {
                remoteViewsIndexMetaData.set(remoteViews, j);
            } else {
                this.mIndexMetaData.put(i, new RemoteViewsIndexMetaData(remoteViews, j));
            }
            this.mIndexRemoteViews.put(i, remoteViews);
        }

        public RemoteViewsMetaData getMetaData() {
            return this.mMetaData;
        }

        public RemoteViewsMetaData getTemporaryMetaData() {
            return this.mTemporaryMetaData;
        }

        public RemoteViews getRemoteViewsAt(int i) {
            return this.mIndexRemoteViews.get(i);
        }

        public RemoteViewsIndexMetaData getMetaDataAt(int i) {
            return this.mIndexMetaData.get(i);
        }

        public void commitTemporaryMetaData() {
            synchronized (this.mTemporaryMetaData) {
                synchronized (this.mMetaData) {
                    this.mMetaData.set(this.mTemporaryMetaData);
                }
            }
        }

        private int getRemoteViewsBitmapMemoryUsage() {
            int i = 0;
            for (int size = this.mIndexRemoteViews.size() - 1; size >= 0; size--) {
                RemoteViews valueAt = this.mIndexRemoteViews.valueAt(size);
                if (valueAt != null) {
                    i = (int) (i + valueAt.estimateMemoryUsage());
                }
            }
            return i;
        }

        private int getFarthestPositionFrom(int i, int[] iArr) {
            int i2 = 0;
            int i3 = 0;
            int i4 = -1;
            int i5 = -1;
            for (int size = this.mIndexRemoteViews.size() - 1; size >= 0; size--) {
                int keyAt = this.mIndexRemoteViews.keyAt(size);
                int abs = Math.abs(keyAt - i);
                if (abs > i2 && Arrays.binarySearch(iArr, keyAt) < 0) {
                    i4 = keyAt;
                    i2 = abs;
                }
                if (abs >= i3) {
                    i5 = keyAt;
                    i3 = abs;
                }
            }
            return i4 > -1 ? i4 : i5;
        }

        public void queueRequestedPositionToLoad(int i) {
            this.mLastRequestedIndex = i;
            synchronized (this.mIndicesToLoad) {
                this.mIndicesToLoad.put(i, true);
            }
        }

        public boolean queuePositionsToBePreloadedFromRequestedPosition(int i) {
            int i2;
            int i3;
            int i4 = this.mPreloadLowerBound;
            if (i4 <= i && i <= (i3 = this.mPreloadUpperBound) && Math.abs(i - ((i3 + i4) / 2)) < this.mMaxCountSlack) {
                return false;
            }
            synchronized (this.mMetaData) {
                i2 = this.mMetaData.count;
            }
            synchronized (this.mIndicesToLoad) {
                for (int size = this.mIndicesToLoad.size() - 1; size >= 0; size--) {
                    if (!this.mIndicesToLoad.valueAt(size)) {
                        this.mIndicesToLoad.removeAt(size);
                    }
                }
                int i5 = this.mMaxCount / 2;
                int i6 = i - i5;
                this.mPreloadLowerBound = i6;
                this.mPreloadUpperBound = i + i5;
                int min = Math.min(this.mPreloadUpperBound, i2 - 1);
                for (int max = Math.max(0, i6); max <= min; max++) {
                    if (this.mIndexRemoteViews.indexOfKey(max) < 0 && !this.mIndicesToLoad.get(max)) {
                        this.mIndicesToLoad.put(max, false);
                    }
                }
            }
            return true;
        }

        public int getNextIndexToLoad() {
            synchronized (this.mIndicesToLoad) {
                int indexOfValue = this.mIndicesToLoad.indexOfValue(true);
                if (indexOfValue < 0) {
                    indexOfValue = this.mIndicesToLoad.indexOfValue(false);
                }
                if (indexOfValue < 0) {
                    return -1;
                }
                int keyAt = this.mIndicesToLoad.keyAt(indexOfValue);
                this.mIndicesToLoad.removeAt(indexOfValue);
                return keyAt;
            }
        }

        public boolean containsRemoteViewAt(int i) {
            return this.mIndexRemoteViews.indexOfKey(i) >= 0;
        }

        public boolean containsMetaDataAt(int i) {
            return this.mIndexMetaData.indexOfKey(i) >= 0;
        }

        public void reset() {
            this.mPreloadLowerBound = 0;
            this.mPreloadUpperBound = -1;
            this.mLastRequestedIndex = -1;
            this.mIndexRemoteViews.clear();
            this.mIndexMetaData.clear();
            synchronized (this.mIndicesToLoad) {
                this.mIndicesToLoad.clear();
            }
        }
    }

    static class RemoteViewsCacheKey {
        final Intent.FilterComparison filter;
        final int widgetId;

        RemoteViewsCacheKey(Intent.FilterComparison filterComparison, int i) {
            this.filter = filterComparison;
            this.widgetId = i;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof RemoteViewsCacheKey)) {
                return false;
            }
            RemoteViewsCacheKey remoteViewsCacheKey = (RemoteViewsCacheKey) obj;
            return remoteViewsCacheKey.filter.equals(this.filter) && remoteViewsCacheKey.widgetId == this.widgetId;
        }

        public int hashCode() {
            Intent.FilterComparison filterComparison = this.filter;
            return (this.widgetId << 2) ^ (filterComparison == null ? 0 : filterComparison.hashCode());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00d9 A[Catch: all -> 0x00de, TryCatch #1 {, blocks: (B:12:0x0091, B:14:0x00a1, B:17:0x00b0, B:18:0x00bc, B:25:0x00d5, B:27:0x00d9, B:28:0x00dc, B:34:0x00cb, B:35:0x00cc, B:20:0x00bd, B:22:0x00c5, B:23:0x00c7), top: B:11:0x0091, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RemoteViewsAdapter(android.content.Context r8, android.content.Intent r9, android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.RemoteViewsAdapter.<init>(android.content.Context, android.content.Intent, android.widget.RemoteViewsAdapter$RemoteAdapterConnectionCallback, boolean):void");
    }

    protected void finalize() throws Throwable {
        try {
            this.mServiceHandler.unbindNow();
            this.mWorkerThread.quit();
        } finally {
            super.finalize();
        }
    }

    public boolean isDataReady() {
        return this.mDataReady;
    }

    public void setRemoteViewsInteractionHandler(RemoteViews.InteractionHandler interactionHandler) {
        this.mRemoteViewsInteractionHandler = interactionHandler;
    }

    public void saveRemoteViewsCache() {
        int i;
        int size;
        final RemoteViewsCacheKey remoteViewsCacheKey = new RemoteViewsCacheKey(new Intent.FilterComparison(this.mIntent), this.mAppWidgetId);
        HashMap<RemoteViewsCacheKey, FixedSizeRemoteViewsCache> hashMap = sCachedRemoteViewsCaches;
        synchronized (hashMap) {
            HashMap<RemoteViewsCacheKey, Runnable> hashMap2 = sRemoteViewsCacheRemoveRunnables;
            if (hashMap2.containsKey(remoteViewsCacheKey)) {
                sCacheRemovalQueue.removeCallbacks(hashMap2.get(remoteViewsCacheKey));
                hashMap2.remove(remoteViewsCacheKey);
            }
            synchronized (this.mCache.mMetaData) {
                i = this.mCache.mMetaData.count;
            }
            synchronized (this.mCache) {
                size = this.mCache.mIndexRemoteViews.size();
            }
            if (i > 0 && size > 0) {
                hashMap.put(remoteViewsCacheKey, this.mCache);
            }
            Runnable runnable = new Runnable() { // from class: android.widget.RemoteViewsAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteViewsAdapter.lambda$saveRemoteViewsCache$0(RemoteViewsAdapter.RemoteViewsCacheKey.this);
                }
            };
            hashMap2.put(remoteViewsCacheKey, runnable);
            sCacheRemovalQueue.postDelayed(runnable, 5000L);
        }
    }

    static /* synthetic */ void lambda$saveRemoteViewsCache$0(RemoteViewsCacheKey remoteViewsCacheKey) {
        HashMap<RemoteViewsCacheKey, FixedSizeRemoteViewsCache> hashMap = sCachedRemoteViewsCaches;
        synchronized (hashMap) {
            hashMap.remove(remoteViewsCacheKey);
            sRemoteViewsCacheRemoveRunnables.remove(remoteViewsCacheKey);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTemporaryMetaData(IRemoteViewsFactory iRemoteViewsFactory) {
        RemoteViews viewAt;
        try {
            boolean hasStableIds = iRemoteViewsFactory.hasStableIds();
            int viewTypeCount = iRemoteViewsFactory.getViewTypeCount();
            int count = iRemoteViewsFactory.getCount();
            LoadingViewTemplate loadingViewTemplate = new LoadingViewTemplate(iRemoteViewsFactory.getLoadingView(), this.mContext);
            if (count > 0 && loadingViewTemplate.remoteViews == null && (viewAt = iRemoteViewsFactory.getViewAt(0)) != null) {
                loadingViewTemplate.loadFirstViewHeight(viewAt, this.mContext, new HandlerThreadExecutor(this.mWorkerThread));
            }
            RemoteViewsMetaData temporaryMetaData = this.mCache.getTemporaryMetaData();
            synchronized (temporaryMetaData) {
                temporaryMetaData.hasStableIds = hasStableIds;
                temporaryMetaData.viewTypeCount = viewTypeCount + 1;
                temporaryMetaData.count = count;
                temporaryMetaData.loadingTemplate = loadingViewTemplate;
            }
        } catch (RemoteException | RuntimeException e) {
            Log.e(TAG, "Error in updateMetaData: " + e.getMessage());
            synchronized (this.mCache.getMetaData()) {
                this.mCache.getMetaData().reset();
                synchronized (this.mCache) {
                    this.mCache.reset();
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRemoteViews(IRemoteViewsFactory iRemoteViewsFactory, int i, boolean z) {
        int i2;
        boolean isViewTypeInRange;
        int i3;
        try {
            RemoteViews viewAt = iRemoteViewsFactory.getViewAt(i);
            long itemId = iRemoteViewsFactory.getItemId(i);
            if (viewAt == null) {
                i2 = i;
                try {
                    throw new RuntimeException("Null remoteViews");
                } catch (RemoteException | RuntimeException e) {
                    e = e;
                    Log.e(TAG, "Error in updateRemoteViews(" + i2 + "): " + e.getMessage());
                    return;
                }
            }
            if (viewAt.mApplication != null) {
                ApplicationInfo applicationInfo = this.mLastRemoteViewAppInfo;
                if (applicationInfo != null && viewAt.hasSameAppInfo(applicationInfo)) {
                    viewAt.mApplication = this.mLastRemoteViewAppInfo;
                } else {
                    this.mLastRemoteViewAppInfo = viewAt.mApplication;
                }
            }
            int layoutId = viewAt.getLayoutId();
            RemoteViewsMetaData metaData = this.mCache.getMetaData();
            synchronized (metaData) {
                isViewTypeInRange = metaData.isViewTypeInRange(layoutId);
                i3 = this.mCache.mMetaData.count;
            }
            synchronized (this.mCache) {
                if (isViewTypeInRange) {
                    this.mCache.insert(i, viewAt, itemId, getVisibleWindow(i3));
                    if (z) {
                        Message.obtain(this.mMainHandler, 5, i, 0, viewAt).sendToTarget();
                    }
                } else {
                    Log.e(TAG, "Error: widget's RemoteViewsFactory returns more view types than  indicated by getViewTypeCount() ");
                }
            }
        } catch (RemoteException | RuntimeException e2) {
            e = e2;
            i2 = i;
        }
    }

    public Intent getRemoteViewsServiceIntent() {
        return this.mIntent;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        int i;
        RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            i = metaData.count;
        }
        return i;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        synchronized (this.mCache) {
            if (!this.mCache.containsMetaDataAt(i)) {
                return 0L;
            }
            return this.mCache.getMetaDataAt(i).itemId;
        }
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        int mappedViewType;
        synchronized (this.mCache) {
            if (!this.mCache.containsMetaDataAt(i)) {
                return 0;
            }
            int i2 = this.mCache.getMetaDataAt(i).typeId;
            RemoteViewsMetaData metaData = this.mCache.getMetaData();
            synchronized (metaData) {
                mappedViewType = metaData.getMappedViewType(i2);
            }
            return mappedViewType;
        }
    }

    public void setVisibleRangeHint(int i, int i2) {
        this.mVisibleWindowLowerBound = i;
        this.mVisibleWindowUpperBound = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003b A[Catch: all -> 0x0087, TryCatch #0 {, blocks: (B:4:0x0003, B:8:0x0011, B:10:0x0015, B:12:0x001f, B:14:0x0037, B:16:0x003b, B:19:0x0056, B:21:0x005d, B:22:0x0085, B:26:0x0063, B:27:0x003e, B:28:0x0024, B:30:0x0028, B:31:0x002f), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0056 A[Catch: all -> 0x0087, TryCatch #0 {, blocks: (B:4:0x0003, B:8:0x0011, B:10:0x0015, B:12:0x001f, B:14:0x0037, B:16:0x003b, B:19:0x0056, B:21:0x005d, B:22:0x0085, B:26:0x0063, B:27:0x003e, B:28:0x0024, B:30:0x0028, B:31:0x002f), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0063 A[Catch: all -> 0x0087, TryCatch #0 {, blocks: (B:4:0x0003, B:8:0x0011, B:10:0x0015, B:12:0x001f, B:14:0x0037, B:16:0x003b, B:19:0x0056, B:21:0x005d, B:22:0x0085, B:26:0x0063, B:27:0x003e, B:28:0x0024, B:30:0x0028, B:31:0x002f), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x003e A[Catch: all -> 0x0087, TryCatch #0 {, blocks: (B:4:0x0003, B:8:0x0011, B:10:0x0015, B:12:0x001f, B:14:0x0037, B:16:0x003b, B:19:0x0056, B:21:0x005d, B:22:0x0085, B:26:0x0063, B:27:0x003e, B:28:0x0024, B:30:0x0028, B:31:0x002f), top: B:3:0x0003 }] */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View getView(int r7, android.view.View r8, android.view.ViewGroup r9) {
        /*
            r6 = this;
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r0 = r6.mCache
            monitor-enter(r0)
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r1 = r6.mCache     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViews r1 = r1.getRemoteViewsAt(r7)     // Catch: java.lang.Throwable -> L87
            r2 = 0
            if (r1 == 0) goto Le
            r3 = 1
            goto Lf
        Le:
            r3 = r2
        Lf:
            if (r8 == 0) goto L1d
            boolean r4 = r8 instanceof android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout     // Catch: java.lang.Throwable -> L87
            if (r4 == 0) goto L1d
            android.widget.RemoteViewsAdapter$RemoteViewsFrameLayoutRefSet r4 = r6.mRequestedViews     // Catch: java.lang.Throwable -> L87
            r5 = r8
            android.widget.RemoteViewsAdapter$RemoteViewsFrameLayout r5 = (android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout) r5     // Catch: java.lang.Throwable -> L87
            r4.removeView(r5)     // Catch: java.lang.Throwable -> L87
        L1d:
            if (r3 != 0) goto L24
            r6.requestBindService()     // Catch: java.lang.Throwable -> L87
        L22:
            r4 = r2
            goto L37
        L24:
            boolean r4 = r6.mUsePreloadPositionIndices     // Catch: java.lang.Throwable -> L87
            if (r4 == 0) goto L2f
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r4 = r6.mCache     // Catch: java.lang.Throwable -> L87
            boolean r4 = r4.queuePositionsToBePreloadedFromRequestedPosition(r7)     // Catch: java.lang.Throwable -> L87
            goto L37
        L2f:
            java.lang.String r4 = "RemoteViewsAdapter"
            java.lang.String r5 = "disable queue position preload"
            android.util.Log.i(r4, r5)     // Catch: java.lang.Throwable -> L87
            goto L22
        L37:
            boolean r5 = r8 instanceof android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout     // Catch: java.lang.Throwable -> L87
            if (r5 == 0) goto L3e
            android.widget.RemoteViewsAdapter$RemoteViewsFrameLayout r8 = (android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout) r8     // Catch: java.lang.Throwable -> L87
            goto L53
        L3e:
            android.widget.RemoteViewsAdapter$RemoteViewsFrameLayout r8 = new android.widget.RemoteViewsAdapter$RemoteViewsFrameLayout     // Catch: java.lang.Throwable -> L87
            android.content.Context r9 = r9.getContext()     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r5 = r6.mCache     // Catch: java.lang.Throwable -> L87
            r8.<init>(r9, r5)     // Catch: java.lang.Throwable -> L87
            java.util.concurrent.Executor r9 = r6.mAsyncViewLoadExecutor     // Catch: java.lang.Throwable -> L87
            r8.setExecutor(r9)     // Catch: java.lang.Throwable -> L87
            boolean r9 = r6.mOnLightBackground     // Catch: java.lang.Throwable -> L87
            r8.setOnLightBackground(r9)     // Catch: java.lang.Throwable -> L87
        L53:
            r9 = 3
            if (r3 == 0) goto L63
            android.widget.RemoteViews$InteractionHandler r7 = r6.mRemoteViewsInteractionHandler     // Catch: java.lang.Throwable -> L87
            r8.onRemoteViewsLoaded(r1, r7, r2)     // Catch: java.lang.Throwable -> L87
            if (r4 == 0) goto L85
            android.widget.RemoteViewsAdapter$RemoteServiceHandler r6 = r6.mServiceHandler     // Catch: java.lang.Throwable -> L87
            r6.sendEmptyMessage(r9)     // Catch: java.lang.Throwable -> L87
            goto L85
        L63:
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r1 = r6.mCache     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViewsAdapter$RemoteViewsMetaData r1 = r1.getMetaData()     // Catch: java.lang.Throwable -> L87
            android.content.Context r3 = r6.mContext     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViewsAdapter$LoadingViewTemplate r1 = r1.getLoadingTemplate(r3)     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViews r1 = r1.remoteViews     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViews$InteractionHandler r3 = r6.mRemoteViewsInteractionHandler     // Catch: java.lang.Throwable -> L87
            r8.onRemoteViewsLoaded(r1, r3, r2)     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViewsAdapter$RemoteViewsFrameLayoutRefSet r1 = r6.mRequestedViews     // Catch: java.lang.Throwable -> L87
            r1.add(r7, r8)     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViewsAdapter$FixedSizeRemoteViewsCache r1 = r6.mCache     // Catch: java.lang.Throwable -> L87
            r1.queueRequestedPositionToLoad(r7)     // Catch: java.lang.Throwable -> L87
            android.widget.RemoteViewsAdapter$RemoteServiceHandler r6 = r6.mServiceHandler     // Catch: java.lang.Throwable -> L87
            r6.sendEmptyMessage(r9)     // Catch: java.lang.Throwable -> L87
        L85:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L87
            return r8
        L87:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L87
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.RemoteViewsAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        int i;
        RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            i = metaData.viewTypeCount;
        }
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        boolean z;
        RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            z = metaData.hasStableIds;
        }
        return z;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean isEmpty() {
        return getCount() <= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getVisibleWindow(int i) {
        int i2 = this.mVisibleWindowLowerBound;
        int i3 = this.mVisibleWindowUpperBound;
        int i4 = 0;
        if (i2 < 0 || i3 < 0) {
            return new int[0];
        }
        if (i2 <= i3) {
            int[] iArr = new int[(i3 + 1) - i2];
            while (i2 <= i3) {
                iArr[i4] = i2;
                i2++;
                i4++;
            }
            return iArr;
        }
        int max = Math.max(i, i2);
        int[] iArr2 = new int[(max - i2) + i3 + 1];
        int i5 = 0;
        while (i4 <= i3) {
            iArr2[i5] = i4;
            i4++;
            i5++;
        }
        while (i2 < max) {
            iArr2[i5] = i2;
            i2++;
            i5++;
        }
        return iArr2;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        this.mServiceHandler.removeMessages(4);
        this.mServiceHandler.sendEmptyMessage(2);
    }

    void superNotifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            this.mCache.commitTemporaryMetaData();
            return true;
        }
        if (i == 2) {
            superNotifyDataSetChanged();
            return true;
        }
        if (i == 3) {
            RemoteAdapterConnectionCallback remoteAdapterConnectionCallback = this.mCallback;
            if (remoteAdapterConnectionCallback != null) {
                remoteAdapterConnectionCallback.onRemoteAdapterConnected();
            }
            return true;
        }
        if (i != 4) {
            if (i != 5) {
                return false;
            }
            this.mRequestedViews.notifyOnRemoteViewsLoaded(message.arg1, (RemoteViews) message.obj);
            return true;
        }
        RemoteAdapterConnectionCallback remoteAdapterConnectionCallback2 = this.mCallback;
        if (remoteAdapterConnectionCallback2 != null) {
            remoteAdapterConnectionCallback2.onRemoteAdapterDisconnected();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestBindService() {
        this.mServiceHandler.removeMessages(4);
        Message.obtain(this.mServiceHandler, 1, this.mAppWidgetId, 0, this.mIntent).sendToTarget();
    }

    private static class HandlerThreadExecutor implements Executor {
        private final HandlerThread mThread;

        HandlerThreadExecutor(HandlerThread handlerThread) {
            this.mThread = handlerThread;
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            if (Thread.currentThread().getId() == this.mThread.getId()) {
                runnable.run();
            } else {
                new Handler(this.mThread.getLooper()).post(runnable);
            }
        }
    }

    private static class LoadingViewTemplate {
        public int defaultHeight;
        public final RemoteViews remoteViews;

        LoadingViewTemplate(RemoteViews remoteViews, Context context) {
            this.remoteViews = remoteViews;
            this.defaultHeight = Math.round(context.getResources().getDisplayMetrics().density * 50.0f);
        }

        public void loadFirstViewHeight(RemoteViews remoteViews, Context context, Executor executor) {
            remoteViews.applyAsync(context, new RemoteViewsFrameLayout(context, null), executor, new RemoteViews.OnViewAppliedListener() { // from class: android.widget.RemoteViewsAdapter.LoadingViewTemplate.1
                @Override // android.widget.RemoteViews.OnViewAppliedListener
                public void onViewApplied(View view) {
                    try {
                        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
                        LoadingViewTemplate.this.defaultHeight = view.getMeasuredHeight();
                    } catch (Exception e) {
                        onError(e);
                    }
                }

                @Override // android.widget.RemoteViews.OnViewAppliedListener
                public void onError(Exception exc) {
                    Log.w(RemoteViewsAdapter.TAG, "Error inflating first RemoteViews", exc);
                }
            });
        }
    }

    public void semUsePreloadPositionIndices(boolean z) {
        this.mUsePreloadPositionIndices = z;
    }
}
