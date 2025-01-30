package com.samsung.android.allshare.extension.impl;

import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.extension.FlatProvider;
import com.samsung.android.allshare.media.Provider;
import com.samsung.android.allshare.media.SearchCriteria;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SearchMediaGetter implements IMediaGetter {
    private static final int DEFAULT_REQUEST_SIZE = 50;
    private Provider mProvider = null;
    private ArrayList<FlatProviderConnectionInfo> mConns = new ArrayList<>();

    private class FlatProviderConnectionInfo implements FlatProvider.IFlatProviderConnection {
        private FlatProvider.IFlatProviderConnection mConn;
        private boolean mIsCancel = false;
        private SearchCriteria mCurrentSearchCriteria = null;
        private int mStartIndex = -1;

        public FlatProviderConnectionInfo(FlatProvider.IFlatProviderConnection conn, Provider provider) {
            this.mConn = null;
            this.mConn = conn;
            SearchMediaGetter.this.mProvider = provider;
        }

        @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
        public void onStart() {
            this.mConn.onStart();
        }

        @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
        public void onProgress(ArrayList<Item> items) {
            this.mConn.onProgress(items);
        }

        @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
        public void onFinish() {
            this.mConn.onFinish();
        }

        @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
        public void onCancel() {
            this.mConn.onCancel();
        }

        @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
        public void onError(ERROR error) {
            this.mConn.onError(error);
        }

        public void setCancel() {
            this.mIsCancel = true;
        }

        public boolean isCanceled() {
            return this.mIsCancel;
        }

        public void setCurrentSearchRequest(SearchCriteria criteria, int startIndex) {
            this.mCurrentSearchCriteria = criteria;
            this.mStartIndex = startIndex;
        }

        public boolean isCurrentSearchRequest(SearchCriteria criteria, int startIndex) {
            return criteria != null && criteria.equals(this.mCurrentSearchCriteria) && this.mStartIndex == startIndex;
        }
    }

    @Override // com.samsung.android.allshare.extension.impl.IMediaGetter
    public void cancel(FlatProvider.IFlatProviderConnection connection) {
        Iterator<FlatProviderConnectionInfo> it = this.mConns.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            FlatProviderConnectionInfo conn = it.next();
            if (conn.mConn.equals(connection)) {
                conn.setCancel();
                break;
            }
        }
        connection.onCancel();
    }

    @Override // com.samsung.android.allshare.extension.impl.IMediaGetter
    public void start(Provider provider, ArrayList<Item.MediaType> types, FlatProvider.IFlatProviderConnection callback) {
        startFlatSearching(provider, types, callback);
    }

    @Override // com.samsung.android.allshare.extension.impl.IMediaGetter
    public void start(Provider provider, Item.MediaType type, FlatProvider.IFlatProviderConnection callback) {
        ArrayList<Item.MediaType> types = new ArrayList<>();
        types.add(type);
        startFlatSearching(provider, types, callback);
    }

    private void startFlatSearching(Provider provider, ArrayList<Item.MediaType> types, FlatProvider.IFlatProviderConnection callback) {
        callback.onStart();
        this.mProvider = provider;
        if (provider == null) {
            ERROR err = ERROR.INVALID_ARGUMENT;
            callback.onError(err);
            return;
        }
        if (types == null || types.size() == 0) {
            ERROR err2 = ERROR.INVALID_ARGUMENT;
            callback.onError(err2);
            return;
        }
        for (int i = 0; i < types.size(); i++) {
            Item.MediaType type = types.get(i);
            if (type != Item.MediaType.ITEM_AUDIO && type != Item.MediaType.ITEM_VIDEO && type != Item.MediaType.ITEM_IMAGE) {
                ERROR err3 = ERROR.INVALID_ARGUMENT;
                callback.onError(err3);
                return;
            }
        }
        provider.setSearchResponseListener(new Provider.IProviderSearchResponseListener() { // from class: com.samsung.android.allshare.extension.impl.SearchMediaGetter.1
            @Override // com.samsung.android.allshare.media.Provider.IProviderSearchResponseListener
            public void onSearchResponseReceived(ArrayList<Item> items, int requestedStartIndex, int requestedCount, SearchCriteria searchCriteria, boolean endOfItems, ERROR err4) {
                int returnedCount = items.size();
                Iterator<Item> itemIt = items.iterator();
                while (itemIt.hasNext()) {
                    Item obj = itemIt.next();
                    if (obj.getType() == Item.MediaType.ITEM_UNKNOWN) {
                        itemIt.remove();
                    }
                }
                if (err4.compareTo(ERROR.FEATURE_NOT_SUPPORTED) == 0) {
                    DLog.w_api(SearchMediaGetter.class.getSimpleName(), "Feature Not Supported");
                }
                Iterator<FlatProviderConnectionInfo> it = SearchMediaGetter.this.mConns.iterator();
                while (it.hasNext()) {
                    FlatProviderConnectionInfo conn = it.next();
                    if (searchCriteria == null) {
                        conn.onError(err4);
                    } else if (conn.isCurrentSearchRequest(searchCriteria, requestedStartIndex)) {
                        if (err4.compareTo(ERROR.SUCCESS) != 0) {
                            conn.onError(err4);
                        } else if (conn.isCanceled()) {
                            it.remove();
                        } else {
                            if (items.size() != 0) {
                                conn.onProgress(items);
                            }
                            if (endOfItems) {
                                conn.onFinish();
                            } else {
                                conn.setCurrentSearchRequest(searchCriteria, requestedStartIndex + returnedCount);
                                SearchMediaGetter.this.mProvider.search(searchCriteria, requestedStartIndex + returnedCount, 50);
                            }
                        }
                    }
                }
            }
        });
        SearchCriteria.Builder builder = new SearchCriteria.Builder();
        Iterator<Item.MediaType> it = types.iterator();
        while (it.hasNext()) {
            builder.addItemType(it.next());
        }
        SearchCriteria criteria = builder.build();
        FlatProviderConnectionInfo conn = new FlatProviderConnectionInfo(callback, provider);
        this.mConns.add(conn);
        conn.setCurrentSearchRequest(criteria, 0);
        provider.search(builder.build(), 0, 50);
    }
}
