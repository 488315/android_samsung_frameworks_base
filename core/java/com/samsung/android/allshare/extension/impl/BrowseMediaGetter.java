package com.samsung.android.allshare.extension.impl;

import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.extension.FlatProvider;
import com.samsung.android.allshare.media.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

/* loaded from: classes5.dex */
public class BrowseMediaGetter implements IMediaGetter, Provider.IProviderBrowseResponseListener {
  private static final int DEFAULT_BROWSE_REQUEST_SIZE = 2;
  private static final int DEFAULT_REQUEST_SIZE = 50;
  private static final String TAG_CLASS = "BrowseMediaGetter";
  private ArrayList<FlatProviderConnectionInfo> mConns = new ArrayList<>();

  private static class FlatProviderConnectionInfo implements FlatProvider.IFlatProviderConnection {
    private FlatProvider.IFlatProviderConnection mConn;
    private Provider mProvider;
    private ArrayList<Item.MediaType> mTypes;
    private HashMap<String, String> mItems = new HashMap<>();
    private HashMap<Item, Integer> mCurrentBrowseMap = new HashMap<>();
    private HashMap<Item, Boolean> mBrowseRetryMap = new HashMap<>();
    private Stack<Item> mFutureBrowseFolderStack = new Stack<>();
    private Stack<Item> mFailedBrowseFolderStack = new Stack<>();
    private HashMap<Item, Integer> mFailedBrowseFolderStartIndexMap = new HashMap<>();
    private boolean mIsCanceled = false;

    public FlatProviderConnectionInfo(
        ArrayList<Item.MediaType> types,
        FlatProvider.IFlatProviderConnection conn,
        Provider provider) {
      this.mConn = null;
      this.mTypes = new ArrayList<>();
      this.mProvider = null;
      this.mConn = conn;
      this.mTypes = types;
      this.mProvider = provider;
    }

    @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
    public void onStart() {
      this.mItems.clear();
      this.mConn.onStart();
    }

    @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
    public void onProgress(ArrayList<Item> items) {
      this.mConn.onProgress(items);
    }

    @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
    public void onFinish() {
      this.mItems.clear();
      this.mConn.onFinish();
    }

    @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
    public void onCancel() {
      this.mItems.clear();
      this.mConn.onCancel();
    }

    @Override // com.samsung.android.allshare.extension.FlatProvider.IFlatProviderConnection
    public void onError(ERROR error) {
      this.mConn.onError(error);
    }

    public void setCurrentBrowseRequest(Item folder, int startIndex) {
      this.mCurrentBrowseMap.put(folder, Integer.valueOf(startIndex));
    }

    public boolean isCurrentBrowseRequest(Item folder, int startIndex) {
      Integer currentStartIdx;
      return (folder == null
              || (currentStartIdx = this.mCurrentBrowseMap.get(folder)) == null
              || startIndex != currentStartIdx.intValue())
          ? false
          : true;
    }

    public boolean isMathchingItem(Item i) {
      Iterator<Item.MediaType> it = this.mTypes.iterator();
      while (it.hasNext()) {
        Item.MediaType type = it.next();
        if (i.getType().equals(type)) {
          return true;
        }
      }
      return false;
    }

    public void setCancel() {
      this.mIsCanceled = true;
    }

    public boolean isCanceled() {
      return this.mIsCanceled;
    }

    public void setRetry(Item folderItem) {
      this.mBrowseRetryMap.put(folderItem, true);
    }

    public void removeRetry(Item folderItem) {
      this.mBrowseRetryMap.remove(folderItem);
    }

    public boolean isRetry(Item folderItem) {
      Boolean result = this.mBrowseRetryMap.get(folderItem);
      if (result == null) {
        return false;
      }
      return result.booleanValue();
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
  public void start(
      Provider provider,
      ArrayList<Item.MediaType> types,
      FlatProvider.IFlatProviderConnection connection) {
    if (provider == null
        || types.isEmpty()
        || types.contains(Item.MediaType.ITEM_UNKNOWN)
        || types.contains(Item.MediaType.ITEM_FOLDER)) {
      connection.onError(ERROR.INVALID_ARGUMENT);
      return;
    }
    connection.onStart();
    provider.setBrowseItemsResponseListener(this);
    FlatProviderConnectionInfo conn = new FlatProviderConnectionInfo(types, connection, provider);
    this.mConns.add(conn);
    conn.setCurrentBrowseRequest(provider.getRootFolder(), 0);
    conn.mProvider.browse(provider.getRootFolder(), 0, 50);
  }

  @Override // com.samsung.android.allshare.extension.impl.IMediaGetter
  public void start(
      Provider provider, Item.MediaType type, FlatProvider.IFlatProviderConnection callback) {
    ArrayList<Item.MediaType> types = new ArrayList<>();
    types.add(type);
    start(provider, types, callback);
  }

  @Override // com.samsung.android.allshare.media.Provider.IProviderBrowseResponseListener
  public void onBrowseResponseReceived(
      ArrayList<Item> items,
      int requestedStartIndex,
      int requestedCount,
      Item requestedFolderItem,
      boolean endOfItems,
      ERROR err) {
    Iterator<Item> itemIt;
    Iterator<Item> itemIt2;
    int returnedCount = items.size();
    Iterator<Item> itemIt3 = items.iterator();
    while (itemIt3.hasNext()) {
      Item obj = itemIt3.next();
      if (obj.getType() == Item.MediaType.ITEM_UNKNOWN) {
        itemIt3.remove();
      }
    }
    Iterator<FlatProviderConnectionInfo> it = this.mConns.iterator();
    if (requestedFolderItem == null) {
      DLog.d_api(
          BrowseMediaGetter.class.getSimpleName(),
          "onBrowseResponseReceived for requestedFolderItem == null");
    } else {
      DLog.d_api(
          BrowseMediaGetter.class.getSimpleName(),
          "onBrowseResponseReceived for "
              + requestedFolderItem.getTitle()
              + ", start idx = "
              + requestedStartIndex
              + ", req count "
              + requestedCount
              + ", endOfItems"
              + endOfItems);
    }
    while (it.hasNext()) {
      ArrayList<Item> result = new ArrayList<>();
      FlatProviderConnectionInfo conn = it.next();
      if (!conn.isCurrentBrowseRequest(requestedFolderItem, requestedStartIndex)) {
        itemIt = itemIt3;
      } else {
        if (err.compareTo(ERROR.SUCCESS) != 0) {
          DLog.d_api(
              BrowseMediaGetter.class.getSimpleName(),
              "browse failed for " + requestedFolderItem.getTitle());
          if (err.equals(ERROR.FAIL)) {
            if (!conn.isRetry(requestedFolderItem)) {
              conn.setRetry(requestedFolderItem);
              conn.mFailedBrowseFolderStack.push(requestedFolderItem);
              conn.mFailedBrowseFolderStartIndexMap.put(
                  requestedFolderItem, Integer.valueOf(requestedStartIndex));
              DLog.d_api(
                  BrowseMediaGetter.class.getSimpleName(),
                  "failed browse added "
                      + requestedFolderItem.getTitle()
                      + ", start "
                      + requestedStartIndex);
              itemIt = itemIt3;
            } else {
              DLog.d_api(BrowseMediaGetter.class.getSimpleName(), "failed again, no more try");
              conn.removeRetry(requestedFolderItem);
              itemIt = itemIt3;
            }
          } else {
            conn.onError(err);
          }
        } else {
          Iterator<Item> it2 = items.iterator();
          while (it2.hasNext()) {
            Item i = it2.next();
            if (conn.isMathchingItem(i)) {
              result.add(i);
            }
            if (i.getType() != Item.MediaType.ITEM_FOLDER) {
              itemIt2 = itemIt3;
            } else {
              itemIt2 = itemIt3;
              DLog.d_api(
                  BrowseMediaGetter.class.getSimpleName(), "container stacking + " + i.getTitle());
              conn.mFutureBrowseFolderStack.push(i);
            }
            itemIt3 = itemIt2;
          }
          itemIt = itemIt3;
        }
        if (conn.isCanceled()) {
          DLog.d_api(BrowseMediaGetter.class.getSimpleName(), "conn is canceled..");
          it.remove();
          itemIt3 = itemIt;
        } else {
          if (result.size() > 0) {
            conn.onProgress(result);
            result.clear();
          }
          conn.mCurrentBrowseMap.remove(requestedFolderItem);
          DLog.d_api(
              BrowseMediaGetter.class.getSimpleName(),
              "endOfItems=="
                  + endOfItems
                  + ", err="
                  + err.enumToString()
                  + ", conn.mFutureBrowseFolderStack.size() ="
                  + conn.mFutureBrowseFolderStack.size()
                  + ", conn.mFailedBrowseFolderStack.size() ="
                  + conn.mFailedBrowseFolderStack.size()
                  + ", conn.mCurrentBrowseMap.size() ="
                  + conn.mCurrentBrowseMap.size());
          if (!endOfItems && err.compareTo(ERROR.SUCCESS) == 0) {
            DLog.d_api(
                BrowseMediaGetter.class.getSimpleName(),
                "browse reamin contents "
                    + requestedFolderItem.getTitle()
                    + " idx = "
                    + requestedStartIndex
                    + returnedCount);
            conn.setCurrentBrowseRequest(requestedFolderItem, requestedStartIndex + returnedCount);
            conn.mProvider.browse(requestedFolderItem, requestedStartIndex + returnedCount, 50);
          } else if (conn.mFutureBrowseFolderStack.size() > 0) {
            while (conn.mFutureBrowseFolderStack.size() > 0 && conn.mCurrentBrowseMap.size() < 2) {
              Item currentBrowseFolder = (Item) conn.mFutureBrowseFolderStack.pop();
              conn.setCurrentBrowseRequest(currentBrowseFolder, 0);
              conn.mProvider.browse(currentBrowseFolder, 0, 50);
              DLog.d_api(
                  BrowseMediaGetter.class.getSimpleName(),
                  "browse stacked container ["
                      + currentBrowseFolder.getTitle()
                      + "], remained size = "
                      + conn.mFutureBrowseFolderStack.size());
            }
          } else if (conn.mFailedBrowseFolderStack.size() > 0) {
            Item currentBrowseFolder2 = (Item) conn.mFailedBrowseFolderStack.pop();
            Integer startIndex =
                (Integer) conn.mFailedBrowseFolderStartIndexMap.get(currentBrowseFolder2);
            conn.setCurrentBrowseRequest(currentBrowseFolder2, startIndex.intValue());
            DLog.d_api(
                BrowseMediaGetter.class.getSimpleName(),
                "brwose failed container ["
                    + currentBrowseFolder2.getTitle()
                    + "], start "
                    + startIndex
                    + ", remained size = "
                    + conn.mFailedBrowseFolderStack.size());
            conn.mProvider.browse(currentBrowseFolder2, startIndex.intValue(), 50);
          } else if (conn.mCurrentBrowseMap.size() <= 0) {
            DLog.d_api(BrowseMediaGetter.class.getSimpleName(), "FINISHED ");
            conn.onFinish();
          }
        }
      }
      itemIt3 = itemIt;
    }
  }
}
