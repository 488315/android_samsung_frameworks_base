package android.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.internal.widget.IRemoteViewsFactory;
import java.util.HashMap;

/* loaded from: classes5.dex */
public abstract class RemoteViewsService extends Service {
    private static final String LOG_TAG = "RemoteViewsService";
    private static final HashMap<Intent.FilterComparison, RemoteViewsFactory> sRemoteViewFactories = new HashMap<>();
    private static final Object sLock = new Object();

    public abstract RemoteViewsFactory onGetViewFactory(Intent intent);

    public interface RemoteViewsFactory {
        int getCount();

        long getItemId(int i);

        RemoteViews getLoadingView();

        RemoteViews getViewAt(int i);

        int getViewTypeCount();

        boolean hasStableIds();

        void onCreate();

        void onDataSetChanged();

        void onDestroy();

        default RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int i, int i2) {
            RemoteViews.RemoteCollectionItems remoteCollectionItemsBuild = new RemoteViews.RemoteCollectionItems.Builder().build();
            Parcel parcelObtain = Parcel.obtain();
            boolean zAllowSquashing = parcelObtain.allowSquashing();
            try {
                RemoteViews.RemoteCollectionItems.Builder builder = new RemoteViews.RemoteCollectionItems.Builder();
                onDataSetChanged();
                builder.setHasStableIds(hasStableIds());
                int count = getCount();
                int i3 = 0;
                RemoteViews.BitmapCache bitmapCache = null;
                int i4 = 0;
                while (i4 < count) {
                    long itemId = getItemId(i4);
                    RemoteViews viewAt = getViewAt(i4);
                    if (viewAt == null) {
                        Log.w(RemoteViewsService.LOG_TAG, "getViewAt is null it replaced with LoadingView. Pos" + i4 + "/" + count);
                        viewAt = getLoadingView();
                    }
                    viewAt.writeToParcel(parcelObtain, i3);
                    if (parcelObtain.dataSize() > i) {
                        break;
                    }
                    if (bitmapCache == null) {
                        bitmapCache = new RemoteViews.BitmapCache(viewAt.getBitmapCache());
                    } else {
                        bitmapCache.mergeWithCache(viewAt.getBitmapCache());
                    }
                    RemoteViews.BitmapCache bitmapCache2 = bitmapCache;
                    if (bitmapCache.getBitmapMemory() >= i2) {
                        break;
                    }
                    builder.addItem(itemId, viewAt);
                    i4++;
                    bitmapCache = bitmapCache2;
                    i3 = 0;
                }
                return builder.build();
            } catch (Exception e) {
                Log.e(RemoteViewsService.LOG_TAG, "Error getting RemoteCollectionItems", e);
                return remoteCollectionItemsBuild;
            } finally {
                parcelObtain.restoreAllowSquashing(zAllowSquashing);
                parcelObtain.recycle();
            }
        }
    }

    private static class RemoteViewsFactoryAdapter extends IRemoteViewsFactory.Stub {
        private static final String INNER_LOG_TAG = "RemoteViewsFactoryAdapter";
        private RemoteViewsFactory mFactory;
        private boolean mIsCreated;

        public RemoteViewsFactoryAdapter(RemoteViewsFactory remoteViewsFactory, boolean z) {
            this.mFactory = remoteViewsFactory;
            this.mIsCreated = z;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized boolean isCreated() {
            return this.mIsCreated;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized void onDataSetChanged() {
            try {
                this.mFactory.onDataSetChanged();
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized void onDataSetChangedAsync() {
            onDataSetChanged();
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized int getCount() {
            int count;
            try {
                count = this.mFactory.getCount();
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                count = 0;
            }
            return count;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized RemoteViews getViewAt(int i) {
            RemoteViews viewAt;
            viewAt = null;
            try {
                viewAt = this.mFactory.getViewAt(i);
                if (viewAt != null) {
                    viewAt.addFlags(2);
                }
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
            return viewAt;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized RemoteViews getLoadingView() {
            RemoteViews loadingView;
            try {
                loadingView = this.mFactory.getLoadingView();
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                loadingView = null;
            }
            return loadingView;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized int getViewTypeCount() {
            int viewTypeCount;
            try {
                viewTypeCount = this.mFactory.getViewTypeCount();
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                viewTypeCount = 0;
            }
            return viewTypeCount;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized long getItemId(int i) {
            long itemId;
            try {
                itemId = this.mFactory.getItemId(i);
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                itemId = 0;
            }
            return itemId;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized boolean hasStableIds() {
            boolean zHasStableIds;
            try {
                zHasStableIds = this.mFactory.hasStableIds();
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                zHasStableIds = false;
            }
            return zHasStableIds;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDestroy(Intent intent) {
            synchronized (RemoteViewsService.sLock) {
                Intent.FilterComparison filterComparison = new Intent.FilterComparison(intent);
                if (RemoteViewsService.sRemoteViewFactories.containsKey(filterComparison)) {
                    try {
                        ((RemoteViewsFactory) RemoteViewsService.sRemoteViewFactories.get(filterComparison)).onDestroy();
                    } catch (Exception e) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                    }
                    RemoteViewsService.sRemoteViewFactories.remove(filterComparison);
                }
            }
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int i, int i2) {
            RemoteViews.RemoteCollectionItems remoteCollectionItemsBuild = new RemoteViews.RemoteCollectionItems.Builder().build();
            try {
                return this.mFactory.getRemoteCollectionItems(i, i2);
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                return remoteCollectionItemsBuild;
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        RemoteViewsFactory remoteViewsFactoryOnGetViewFactory;
        boolean z;
        RemoteViewsFactoryAdapter remoteViewsFactoryAdapter;
        synchronized (sLock) {
            Intent.FilterComparison filterComparison = new Intent.FilterComparison(intent);
            HashMap<Intent.FilterComparison, RemoteViewsFactory> map = sRemoteViewFactories;
            if (!map.containsKey(filterComparison)) {
                remoteViewsFactoryOnGetViewFactory = onGetViewFactory(intent);
                map.put(filterComparison, remoteViewsFactoryOnGetViewFactory);
                remoteViewsFactoryOnGetViewFactory.onCreate();
                z = false;
            } else {
                remoteViewsFactoryOnGetViewFactory = map.get(filterComparison);
                z = true;
            }
            remoteViewsFactoryAdapter = new RemoteViewsFactoryAdapter(remoteViewsFactoryOnGetViewFactory, z);
        }
        return remoteViewsFactoryAdapter;
    }
}
