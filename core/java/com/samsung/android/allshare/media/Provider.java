package com.samsung.android.allshare.media;

import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Item;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public abstract class Provider extends Device {

    public interface IProviderBrowseResponseListener {
        void onBrowseResponseReceived(ArrayList<Item> arrayList, int i, int i2, Item item, boolean z, ERROR error);
    }

    public interface IProviderEventListener {
        void onContentUpdated(ERROR error);
    }

    public interface IProviderSearchResponseListener {
        void onSearchResponseReceived(ArrayList<Item> arrayList, int i, int i2, SearchCriteria searchCriteria, boolean z, ERROR error);
    }

    public abstract void browse(Item item, int i, int i2);

    public abstract Receiver getReceiver();

    public abstract Item getRootFolder();

    public abstract boolean isSearchable();

    public abstract void search(SearchCriteria searchCriteria, int i, int i2);

    public abstract void setBrowseItemsResponseListener(IProviderBrowseResponseListener iProviderBrowseResponseListener);

    public abstract void setEventListener(IProviderEventListener iProviderEventListener);

    public abstract void setSearchResponseListener(IProviderSearchResponseListener iProviderSearchResponseListener);

    protected Provider() {
    }
}
