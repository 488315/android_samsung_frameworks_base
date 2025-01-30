package com.samsung.android.allshare;

import android.net.Uri;
import android.p009os.Bundle;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.media.Provider;
import com.samsung.android.allshare.media.Receiver;
import com.samsung.android.allshare.media.SearchCriteria;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
final class ProviderImpl extends Provider implements IBundleHolder, IHandlerHolder {
    private static final String TAG_CLASS = "ProviderImpl";
    private static Item mRootFolderItem;
    private IAllShareConnector mAllShareConnector;
    private DeviceImpl mDeviceImpl;
    private Receiver mReceiver;
    private Provider.IProviderBrowseResponseListener mBrowseResponseListener = null;
    private Provider.IProviderSearchResponseListener mSearchResponseListener = null;
    private Provider.IProviderEventListener mProviderEventListener = null;
    private boolean mIsSubscribed = false;
    private AllShareEventHandler mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ProviderImpl.1
        @Override // com.samsung.android.allshare.AllShareEventHandler
        void handleEventMessage(CVMessage cvm) {
            if (ProviderImpl.this.mProviderEventListener == null) {
                return;
            }
            String actionID = cvm.getActionID();
            Bundle resBundle = cvm.getBundle();
            if (resBundle == null) {
                return;
            }
            String errStr = resBundle.getString("BUNDLE_ENUM_ERROR");
            if (actionID.equals(AllShareEvent.EVENT_PROVIDER_CONTENTS_UPDATED)) {
                if (errStr == null) {
                    ProviderImpl.this.mProviderEventListener.onContentUpdated(ERROR.FAIL);
                } else {
                    ERROR error = ERROR.stringToEnum(errStr);
                    ProviderImpl.this.mProviderEventListener.onContentUpdated(error);
                }
            }
        }
    };
    private AllShareResponseHandler mAllShareRespHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ProviderImpl.2
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
            FolderItemImpl requestedFolderItem;
            String searchString;
            String str;
            String str2;
            SearchCriteria criteria;
            Provider.IProviderSearchResponseListener iProviderSearchResponseListener;
            if (cvm == null) {
                return;
            }
            String actionID = cvm.getActionID();
            Bundle resBundle = cvm.getBundle();
            if (actionID == null || resBundle == null) {
                return;
            }
            ArrayList<Bundle> itemImpleList = resBundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_CONTENT_BUNDLE);
            ArrayList<Bundle> itemImpleList2 = itemImpleList == null ? new ArrayList<>() : itemImpleList;
            int requestedStartIndex = resBundle.getInt(AllShareKey.BUNDLE_INT_STARTINDEX);
            int reqeustedCount = resBundle.getInt(AllShareKey.BUNDLE_INT_REQUESTCOUNT);
            boolean endOfItem = resBundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_ENDOFITEM);
            ERROR error = ERROR.stringToEnum(resBundle.getString("BUNDLE_ENUM_ERROR"));
            ArrayList<String> itemTypeStrList = resBundle.getStringArrayList(AllShareKey.BUNDLE_STRING_ITEM_TYPE_ARRAYLIST);
            ArrayList<Item> itemList = new ArrayList<>();
            Iterator<Bundle> it = itemImpleList2.iterator();
            while (it.hasNext()) {
                Bundle b = it.next();
                Item.MediaType type = getItemType(b);
                if (type != null) {
                    switch (C48753.$SwitchMap$com$samsung$android$allshare$Item$MediaType[type.ordinal()]) {
                        case 1:
                            itemList.add(new AudioItemImpl(b));
                            break;
                        case 2:
                            itemList.add(new ImageItemImpl(b));
                            break;
                        case 3:
                            itemList.add(new VideoItemImpl(b));
                            break;
                        case 4:
                            itemList.add(new FolderItemImpl(b));
                            break;
                        default:
                            return;
                    }
                }
            }
            if (!actionID.equals(AllShareAction.ACTION_PROVIDER_REQUEST_SEARCH_CRITERIA_ITEMS)) {
                if (actionID.equals(AllShareAction.ACTION_PROVIDER_REQUEST_BROWSE_ITEMS)) {
                    Bundle bundleFolder = (Bundle) resBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_FOLDERITEM);
                    if (bundleFolder != null) {
                        String objID = bundleFolder.getString(AllShareKey.BUNDLE_STRING_OBJECT_ID);
                        if (objID == null || objID.equals("0")) {
                            FolderItemImpl requestedFolderItem2 = new RootFolderItem(bundleFolder);
                            requestedFolderItem = requestedFolderItem2;
                        } else {
                            FolderItemImpl requestedFolderItem3 = new FolderItemImpl(bundleFolder);
                            requestedFolderItem = requestedFolderItem3;
                        }
                    } else {
                        requestedFolderItem = null;
                    }
                    if (ProviderImpl.this.mBrowseResponseListener == null) {
                        return;
                    }
                    try {
                    } catch (Error e) {
                        err = e;
                    } catch (Exception e2) {
                        e = e2;
                    }
                    try {
                        ProviderImpl.this.mBrowseResponseListener.onBrowseResponseReceived(itemList, requestedStartIndex, reqeustedCount, requestedFolderItem, endOfItem, error);
                        return;
                    } catch (Error e3) {
                        err = e3;
                        DLog.w_api(ProviderImpl.TAG_CLASS, "mAllShareRespHandler.handleResponseMessage Error", err);
                        return;
                    } catch (Exception e4) {
                        e = e4;
                        DLog.w_api(ProviderImpl.TAG_CLASS, "mAllShareRespHandler.handleResponseMessage Exception", e);
                        return;
                    }
                }
                return;
            }
            String searchString2 = resBundle.getString(AllShareKey.BUNDLE_STRING_SEARCHSTRING);
            if (ProviderImpl.this.mSearchResponseListener == null) {
                return;
            }
            try {
                SearchCriteria.Builder builder = new SearchCriteria.Builder().setKeyword(searchString2);
                if (itemTypeStrList != null) {
                    try {
                        Iterator<String> it2 = itemTypeStrList.iterator();
                        while (it2.hasNext()) {
                            String iType = it2.next();
                            Item.MediaType t = Item.MediaType.stringToEnum(iType);
                            ArrayList<Bundle> itemImpleList3 = itemImpleList2;
                            try {
                                builder.addItemType(t);
                                itemImpleList2 = itemImpleList3;
                            } catch (Error e5) {
                                err = e5;
                                searchString = ProviderImpl.TAG_CLASS;
                                str2 = "mAllShareRespHandler.handleResponseMessage Error";
                                DLog.w_api(searchString, str2, err);
                            } catch (Exception e6) {
                                e = e6;
                                searchString = ProviderImpl.TAG_CLASS;
                                str = "mAllShareRespHandler.handleResponseMessage Exception";
                                DLog.w_api(searchString, str, e);
                            }
                        }
                    } catch (Error e7) {
                        err = e7;
                        searchString = ProviderImpl.TAG_CLASS;
                        str2 = "mAllShareRespHandler.handleResponseMessage Error";
                    } catch (Exception e8) {
                        e = e8;
                        searchString = ProviderImpl.TAG_CLASS;
                        str = "mAllShareRespHandler.handleResponseMessage Exception";
                    }
                }
                try {
                    criteria = builder.build();
                    iProviderSearchResponseListener = ProviderImpl.this.mSearchResponseListener;
                    searchString = ProviderImpl.TAG_CLASS;
                    str = "mAllShareRespHandler.handleResponseMessage Exception";
                    str2 = "mAllShareRespHandler.handleResponseMessage Error";
                } catch (Error e9) {
                    err = e9;
                    searchString = ProviderImpl.TAG_CLASS;
                    str2 = "mAllShareRespHandler.handleResponseMessage Error";
                } catch (Exception e10) {
                    e = e10;
                    searchString = ProviderImpl.TAG_CLASS;
                    str = "mAllShareRespHandler.handleResponseMessage Exception";
                }
            } catch (Error e11) {
                err = e11;
                searchString = ProviderImpl.TAG_CLASS;
                str2 = "mAllShareRespHandler.handleResponseMessage Error";
            } catch (Exception e12) {
                e = e12;
                searchString = ProviderImpl.TAG_CLASS;
                str = "mAllShareRespHandler.handleResponseMessage Exception";
            }
            try {
                iProviderSearchResponseListener.onSearchResponseReceived(itemList, requestedStartIndex, reqeustedCount, criteria, endOfItem, error);
            } catch (Error e13) {
                err = e13;
                DLog.w_api(searchString, str2, err);
            } catch (Exception e14) {
                e = e14;
                DLog.w_api(searchString, str, e);
            }
        }

        Item.MediaType getItemType(Bundle b) {
            if (b == null) {
                return Item.MediaType.ITEM_UNKNOWN;
            }
            String typeStr = b.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE);
            if (typeStr == null) {
                return Item.MediaType.ITEM_UNKNOWN;
            }
            return Item.MediaType.stringToEnum(typeStr);
        }
    };

    static {
        mRootFolderItem = null;
        Bundle mFolderBunle = new Bundle();
        mFolderBunle.putString(AllShareKey.BUNDLE_STRING_ITEM_TITLE, "");
        mFolderBunle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI, null);
        mFolderBunle.putLong(AllShareKey.BUNDLE_LONG_ITEM_DATE, -1L);
        mFolderBunle.putLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE, -1L);
        mFolderBunle.putString(AllShareKey.BUNDLE_STRING_OBJECT_ID, "0");
        mRootFolderItem = new RootFolderItem(mFolderBunle);
    }

    private static class RootFolderItem extends FolderItemImpl {
        RootFolderItem(Bundle bundle) {
            super(bundle);
        }

        @Override // com.samsung.android.allshare.FolderItemImpl, com.samsung.android.allshare.Item
        public boolean isRootFolder() {
            return true;
        }

        @Override // com.samsung.android.allshare.FolderItemImpl
        public boolean equals(Object o) {
            return super.equals(o);
        }

        @Override // com.samsung.android.allshare.FolderItemImpl
        public int hashCode() {
            return super.hashCode();
        }
    }

    ProviderImpl(IAllShareConnector connector, DeviceImpl deviceImpl) {
        this.mAllShareConnector = null;
        this.mDeviceImpl = null;
        if (connector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
            return;
        }
        this.mDeviceImpl = deviceImpl;
        this.mAllShareConnector = connector;
        Bundle bundle = deviceImpl.getBundle();
        if (bundle == null) {
            this.mReceiver = null;
            DLog.w_api(TAG_CLASS, "bundle == null!");
            return;
        }
        Boolean isReceiverable = Boolean.valueOf(bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_RECEIVERABLE));
        if (isReceiverable.booleanValue()) {
            this.mReceiver = new ReceiverImpl(connector, deviceImpl);
        } else {
            this.mReceiver = null;
        }
    }

    /* renamed from: com.samsung.android.allshare.ProviderImpl$3 */
    static /* synthetic */ class C48753 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;

        static {
            int[] iArr = new int[Item.MediaType.values().length];
            $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr;
            try {
                iArr[Item.MediaType.ITEM_AUDIO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_FOLDER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_UNKNOWN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @Override // com.samsung.android.allshare.media.Provider
    public Item getRootFolder() {
        return mRootFolderItem;
    }

    @Override // com.samsung.android.allshare.media.Provider
    public boolean isSearchable() {
        Bundle result = getBundle();
        if (result == null) {
            return false;
        }
        return result.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SEARCHABLE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.allshare.media.Provider
    public void browse(Item item, int startIndex, int requestCount) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            DLog.i_api(TAG_CLASS, "browse fail : SERVICE_NOT_CONNECTED");
            Provider.IProviderBrowseResponseListener iProviderBrowseResponseListener = this.mBrowseResponseListener;
            if (iProviderBrowseResponseListener != null) {
                iProviderBrowseResponseListener.onBrowseResponseReceived(new ArrayList<>(), -1, -1, null, false, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        if (item == 0 || startIndex == -1 || requestCount <= 0) {
            DLog.i_api(TAG_CLASS, "browse fail : INVALID_ARGUMENT");
            Provider.IProviderBrowseResponseListener iProviderBrowseResponseListener2 = this.mBrowseResponseListener;
            if (iProviderBrowseResponseListener2 != null) {
                iProviderBrowseResponseListener2.onBrowseResponseReceived(new ArrayList<>(), startIndex, requestCount, item, false, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        if (!item.getType().equals(Item.MediaType.ITEM_FOLDER)) {
            DLog.i_api(TAG_CLASS, "browse fail : INVALID_ARGUMENT");
            Provider.IProviderBrowseResponseListener iProviderBrowseResponseListener3 = this.mBrowseResponseListener;
            if (iProviderBrowseResponseListener3 != null) {
                iProviderBrowseResponseListener3.onBrowseResponseReceived(new ArrayList<>(), startIndex, requestCount, item, false, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PROVIDER_REQUEST_BROWSE_ITEMS);
        Bundle req_bundle = new Bundle();
        Bundle folderBundle = null;
        if (item instanceof IBundleHolder) {
            folderBundle = ((IBundleHolder) item).getBundle();
        }
        req_bundle.putString("BUNDLE_STRING_ID", getID());
        req_bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_FOLDERITEM, folderBundle);
        req_bundle.putInt(AllShareKey.BUNDLE_INT_STARTINDEX, startIndex);
        req_bundle.putInt(AllShareKey.BUNDLE_INT_REQUESTCOUNT, requestCount);
        DLog.i_api(TAG_CLASS, "browse " + getName() + ", index: " + startIndex + ", count: " + requestCount);
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.Provider
    public void setBrowseItemsResponseListener(Provider.IProviderBrowseResponseListener l) {
        this.mBrowseResponseListener = l;
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceDomain getDeviceDomain() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return Device.DeviceDomain.UNKNOWN;
        }
        return deviceImpl.getDeviceDomain();
    }

    @Override // com.samsung.android.allshare.Device
    public String getID() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getID();
    }

    @Override // com.samsung.android.allshare.Device
    public Uri getIcon() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return null;
        }
        return deviceImpl.getIcon();
    }

    @Override // com.samsung.android.allshare.Device
    public ArrayList<Icon> getIconList() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return new ArrayList<>();
        }
        return deviceImpl.getIconList();
    }

    @Override // com.samsung.android.allshare.Device
    public String getName() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getName();
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceType getDeviceType() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return Device.DeviceType.UNKNOWN;
        }
        return deviceImpl.getDeviceType();
    }

    @Override // com.samsung.android.allshare.Device
    public String getModelName() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getModelName();
    }

    @Override // com.samsung.android.allshare.Device
    @Deprecated
    public String getIPAdress() {
        return getIPAddress();
    }

    @Override // com.samsung.android.allshare.Device
    public String getIPAddress() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getIPAddress();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return null;
        }
        return deviceImpl.getBundle();
    }

    @Override // com.samsung.android.allshare.media.Provider
    public Receiver getReceiver() {
        return this.mReceiver;
    }

    @Override // com.samsung.android.allshare.media.Provider
    public void setEventListener(Provider.IProviderEventListener l) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return;
        }
        this.mProviderEventListener = l;
        boolean z = this.mIsSubscribed;
        if (!z && l != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = true;
        } else if (z && l == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = false;
        }
    }

    @Override // com.samsung.android.allshare.media.Provider
    public void search(SearchCriteria searchCriteria, int startIndex, int requestCount) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            DLog.i_api(TAG_CLASS, "search fail : SERVICE_NOT_CONNECTED");
            Provider.IProviderSearchResponseListener iProviderSearchResponseListener = this.mSearchResponseListener;
            if (iProviderSearchResponseListener != null) {
                iProviderSearchResponseListener.onSearchResponseReceived(new ArrayList<>(), -1, -1, searchCriteria, false, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        if (searchCriteria == null) {
            DLog.i_api(TAG_CLASS, "search fail : INVALID_ARGUMENT (searchCriteria)");
            Provider.IProviderSearchResponseListener iProviderSearchResponseListener2 = this.mSearchResponseListener;
            if (iProviderSearchResponseListener2 != null) {
                iProviderSearchResponseListener2.onSearchResponseReceived(new ArrayList<>(), startIndex, requestCount, searchCriteria, false, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        if (startIndex == -1 || requestCount <= 0) {
            DLog.i_api(TAG_CLASS, "search fail : INVALID_ARGUMENT (index)");
            Provider.IProviderSearchResponseListener iProviderSearchResponseListener3 = this.mSearchResponseListener;
            if (iProviderSearchResponseListener3 != null) {
                iProviderSearchResponseListener3.onSearchResponseReceived(new ArrayList<>(), startIndex, requestCount, searchCriteria, false, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PROVIDER_REQUEST_SEARCH_CRITERIA_ITEMS);
        Bundle req_bundle = new Bundle();
        ArrayList<String> typeList = new ArrayList<>();
        if (searchCriteria.isMatchedItemType(Item.MediaType.ITEM_UNKNOWN)) {
            DLog.i_api(TAG_CLASS, "search fail : INVALID_ARGUMENT (MediaType)");
            Provider.IProviderSearchResponseListener iProviderSearchResponseListener4 = this.mSearchResponseListener;
            if (iProviderSearchResponseListener4 != null) {
                iProviderSearchResponseListener4.onSearchResponseReceived(new ArrayList<>(), startIndex, requestCount, searchCriteria, false, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        if (searchCriteria.isMatchedItemType(Item.MediaType.ITEM_AUDIO)) {
            typeList.add(Item.MediaType.ITEM_AUDIO.enumToString());
        }
        if (searchCriteria.isMatchedItemType(Item.MediaType.ITEM_VIDEO)) {
            typeList.add(Item.MediaType.ITEM_VIDEO.enumToString());
        }
        if (searchCriteria.isMatchedItemType(Item.MediaType.ITEM_IMAGE)) {
            typeList.add(Item.MediaType.ITEM_IMAGE.enumToString());
        }
        if (searchCriteria.isMatchedItemType(Item.MediaType.ITEM_FOLDER)) {
            typeList.add(Item.MediaType.ITEM_FOLDER.enumToString());
        }
        req_bundle.putString("BUNDLE_STRING_ID", getID());
        req_bundle.putString(AllShareKey.BUNDLE_STRING_SEARCHSTRING, searchCriteria.getKeyword());
        req_bundle.putInt(AllShareKey.BUNDLE_INT_STARTINDEX, startIndex);
        req_bundle.putInt(AllShareKey.BUNDLE_INT_REQUESTCOUNT, requestCount);
        req_bundle.putStringArrayList(AllShareKey.BUNDLE_STRING_ITEM_TYPE_ARRAYLIST, typeList);
        DLog.i_api(TAG_CLASS, "search " + getName() + ", index: " + startIndex + ", count: " + requestCount);
        req_msg.setBundle(req_bundle);
        long retVal = this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
        if (-1 == retVal) {
            DLog.i_api(TAG_CLASS, "Search fail : Error in sending request to Allshare Service");
            Provider.IProviderSearchResponseListener iProviderSearchResponseListener5 = this.mSearchResponseListener;
            if (iProviderSearchResponseListener5 != null) {
                iProviderSearchResponseListener5.onSearchResponseReceived(new ArrayList<>(), startIndex, requestCount, searchCriteria, false, ERROR.FAIL);
            }
        }
    }

    @Override // com.samsung.android.allshare.media.Provider
    public void setSearchResponseListener(Provider.IProviderSearchResponseListener l) {
        this.mSearchResponseListener = l;
    }

    @Override // com.samsung.android.allshare.Device
    public String getNIC() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getNIC();
    }

    @Override // com.sec.android.allshare.iface.IHandlerHolder
    public void removeEventHandler() {
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
        this.mIsSubscribed = false;
        Receiver receiver = this.mReceiver;
        if (receiver != null) {
            ((ReceiverImpl) receiver).removeEventHandler();
        }
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSeekableOnPaused() {
        return false;
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isWholeHomeAudio() {
        return false;
    }

    @Override // com.samsung.android.allshare.Device
    public String getP2pMacAddress() {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo() {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public void requestMobileToTV(String ip, int port) {
    }

    @Override // com.samsung.android.allshare.Device
    public String getSecProductP2pMacAddr() {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingP2pMacAddr() {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public String getProductCapInfo(Device.InformationType infoType) {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo(Device.InformationType infoType) {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSupportedByType(int type) {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return false;
        }
        return deviceImpl.isSupportedByType(type);
    }
}
