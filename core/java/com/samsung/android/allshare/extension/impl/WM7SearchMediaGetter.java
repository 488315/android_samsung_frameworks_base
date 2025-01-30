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
public class WM7SearchMediaGetter implements IMediaGetter {
    private static final int DEFAULT_REQUEST_SIZE = 50;
    private Provider mProvider = null;
    private ArrayList<FlatProviderConnectionInfo> mConns = new ArrayList<>();

    private static class SearchRequest {
        private SearchCriteria mSearchCriteria;
        private int mStartIndex;

        public SearchRequest(SearchCriteria criteria, int startIndex) {
            this.mSearchCriteria = null;
            this.mStartIndex = -1;
            this.mSearchCriteria = criteria;
            this.mStartIndex = startIndex;
        }

        public boolean isSame(SearchCriteria criteria, int startIndex) {
            if (criteria == null || !criteria.equals(this.mSearchCriteria) || this.mStartIndex != startIndex) {
                return false;
            }
            return true;
        }

        public void updateSearchRequest(SearchCriteria criteria, int startIndex) {
            this.mSearchCriteria = criteria;
            this.mStartIndex = startIndex;
        }
    }

    private class FlatProviderConnectionInfo implements FlatProvider.IFlatProviderConnection {
        private FlatProvider.IFlatProviderConnection mConn;
        private boolean mIsCancel = false;
        private ArrayList<SearchRequest> mSearchRequestList = new ArrayList<>();

        public FlatProviderConnectionInfo(FlatProvider.IFlatProviderConnection conn, Provider provider) {
            this.mConn = null;
            this.mConn = conn;
            WM7SearchMediaGetter.this.mProvider = provider;
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

        public void addCurrentSearchRequest(ArrayList<Item.MediaType> types, int startIndex) {
            Iterator<Item.MediaType> it = types.iterator();
            while (it.hasNext()) {
                Item.MediaType type = it.next();
                SearchCriteria.Builder builder = new SearchCriteria.Builder();
                builder.addItemType(type);
                SearchRequest request = new SearchRequest(builder.build(), startIndex);
                this.mSearchRequestList.add(request);
            }
        }

        public SearchRequest getCurrentSearchRequest(SearchCriteria criteria, int startIndex) {
            if (criteria == null) {
                return null;
            }
            Iterator<SearchRequest> it = this.mSearchRequestList.iterator();
            while (it.hasNext()) {
                SearchRequest request = it.next();
                if (request.isSame(criteria, startIndex)) {
                    return request;
                }
            }
            return null;
        }

        public void removeSearchRequest(SearchRequest request) {
            this.mSearchRequestList.remove(request);
        }

        public boolean isrequestFinished() {
            return this.mSearchRequestList.isEmpty();
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
        FlatProviderConnectionInfo conn = new FlatProviderConnectionInfo(callback, provider);
        this.mConns.add(conn);
        conn.addCurrentSearchRequest(types, 0);
        provider.setSearchResponseListener(new Provider.IProviderSearchResponseListener() { // from class: com.samsung.android.allshare.extension.impl.WM7SearchMediaGetter.1
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
                    DLog.w_api(WM7SearchMediaGetter.class.getSimpleName(), "Feature Not Supported");
                }
                Iterator<FlatProviderConnectionInfo> it = WM7SearchMediaGetter.this.mConns.iterator();
                while (it.hasNext()) {
                    FlatProviderConnectionInfo conn2 = it.next();
                    if (searchCriteria == null) {
                        conn2.onError(err4);
                    } else {
                        SearchRequest request = conn2.getCurrentSearchRequest(searchCriteria, requestedStartIndex);
                        if (request == null) {
                            continue;
                        } else if (err4.compareTo(ERROR.SUCCESS) != 0) {
                            conn2.onError(err4);
                        } else if (conn2.isCanceled()) {
                            it.remove();
                        } else {
                            if (err4.ordinal() != 0) {
                                conn2.onError(err4);
                                return;
                            }
                            if (items.size() != 0) {
                                conn2.onProgress(items);
                            }
                            if (endOfItems) {
                                conn2.removeSearchRequest(request);
                                if (conn2.isrequestFinished()) {
                                    conn2.onFinish();
                                }
                            } else {
                                request.updateSearchRequest(searchCriteria, requestedStartIndex + returnedCount);
                                WM7SearchMediaGetter.this.mProvider.search(searchCriteria, requestedStartIndex + returnedCount, 50);
                            }
                        }
                    }
                }
            }
        });
        Iterator<Item.MediaType> it = types.iterator();
        while (it.hasNext()) {
            Item.MediaType type2 = it.next();
            SearchCriteria.Builder builder = new SearchCriteria.Builder();
            builder.addItemType(type2);
            provider.search(builder.build(), 0, 50);
        }
    }
}
