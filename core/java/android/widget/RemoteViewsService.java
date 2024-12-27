package android.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.widget.RemoteViews;
import com.android.internal.widget.IRemoteViewsFactory;
import java.util.HashMap;

/* loaded from: classes4.dex */
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

        default RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int capSize) {
            new RemoteViews.RemoteCollectionItems.Builder().build();
            Parcel capSizeTestParcel = Parcel.obtain();
            boolean prevAllowSquashing = capSizeTestParcel.allowSquashing();
            try {
                RemoteViews.RemoteCollectionItems.Builder itemsBuilder = new RemoteViews.RemoteCollectionItems.Builder();
                onDataSetChanged();
                itemsBuilder.setHasStableIds(hasStableIds());
                int numOfEntries = getCount();
                for (int i = 0; i < numOfEntries; i++) {
                    long currentItemId = getItemId(i);
                    RemoteViews currentView = getViewAt(i);
                    currentView.writeToParcel(capSizeTestParcel, 0);
                    if (capSizeTestParcel.dataSize() > capSize) {
                        break;
                    }
                    itemsBuilder.addItem(currentItemId, currentView);
                }
                RemoteViews.RemoteCollectionItems items = itemsBuilder.build();
                return items;
            } finally {
                capSizeTestParcel.restoreAllowSquashing(prevAllowSquashing);
                capSizeTestParcel.recycle();
            }
        }
    }

    private static class RemoteViewsFactoryAdapter extends IRemoteViewsFactory.Stub {
        private RemoteViewsFactory mFactory;
        private boolean mIsCreated;

        public RemoteViewsFactoryAdapter(RemoteViewsFactory factory, boolean isCreated) {
            this.mFactory = factory;
            this.mIsCreated = isCreated;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized boolean isCreated() {
            return this.mIsCreated;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized void onDataSetChanged() {
            try {
                this.mFactory.onDataSetChanged();
            } catch (Exception ex) {
                Thread t = Thread.currentThread();
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
            }
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized void onDataSetChangedAsync() {
            onDataSetChanged();
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized int getCount() {
            int count;
            count = 0;
            try {
                count = this.mFactory.getCount();
            } catch (Exception ex) {
                Thread t = Thread.currentThread();
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
            }
            return count;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized RemoteViews getViewAt(int position) {
            RemoteViews rv;
            rv = null;
            try {
                rv = this.mFactory.getViewAt(position);
                if (rv != null) {
                    rv.addFlags(2);
                }
            } catch (Exception ex) {
                Thread t = Thread.currentThread();
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
            }
            return rv;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized RemoteViews getLoadingView() {
            RemoteViews rv;
            rv = null;
            try {
                rv = this.mFactory.getLoadingView();
            } catch (Exception ex) {
                Thread t = Thread.currentThread();
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
            }
            return rv;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized int getViewTypeCount() {
            int count;
            count = 0;
            try {
                count = this.mFactory.getViewTypeCount();
            } catch (Exception ex) {
                Thread t = Thread.currentThread();
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
            }
            return count;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized long getItemId(int position) {
            long id;
            id = 0;
            try {
                id = this.mFactory.getItemId(position);
            } catch (Exception ex) {
                Thread t = Thread.currentThread();
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
            }
            return id;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized boolean hasStableIds() {
            boolean hasStableIds;
            hasStableIds = false;
            try {
                hasStableIds = this.mFactory.hasStableIds();
            } catch (Exception ex) {
                Thread t = Thread.currentThread();
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
            }
            return hasStableIds;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDestroy(Intent intent) {
            synchronized (RemoteViewsService.sLock) {
                Intent.FilterComparison fc = new Intent.FilterComparison(intent);
                if (RemoteViewsService.sRemoteViewFactories.containsKey(fc)) {
                    RemoteViewsFactory factory = (RemoteViewsFactory) RemoteViewsService.sRemoteViewFactories.get(fc);
                    try {
                        factory.onDestroy();
                    } catch (Exception ex) {
                        Thread t = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
                    }
                    RemoteViewsService.sRemoteViewFactories.remove(fc);
                }
            }
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public RemoteViews.RemoteCollectionItems getRemoteCollectionItems(int capSize) {
            RemoteViews.RemoteCollectionItems items = new RemoteViews.RemoteCollectionItems.Builder().build();
            try {
                RemoteViews.RemoteCollectionItems items2 = this.mFactory.getRemoteCollectionItems(capSize);
                return items2;
            } catch (Exception ex) {
                Thread t = Thread.currentThread();
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, ex);
                return items;
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        RemoteViewsFactory factory;
        boolean isCreated;
        RemoteViewsFactoryAdapter remoteViewsFactoryAdapter;
        synchronized (sLock) {
            Intent.FilterComparison fc = new Intent.FilterComparison(intent);
            if (!sRemoteViewFactories.containsKey(fc)) {
                factory = onGetViewFactory(intent);
                sRemoteViewFactories.put(fc, factory);
                factory.onCreate();
                isCreated = false;
            } else {
                factory = sRemoteViewFactories.get(fc);
                isCreated = true;
            }
            remoteViewsFactoryAdapter = new RemoteViewsFactoryAdapter(factory, isCreated);
        }
        return remoteViewsFactoryAdapter;
    }
}
