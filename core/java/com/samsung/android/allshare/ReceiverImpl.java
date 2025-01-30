package com.samsung.android.allshare;

import android.p009os.Bundle;
import com.samsung.android.allshare.media.Receiver;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;

/* loaded from: classes5.dex */
class ReceiverImpl extends Receiver {
  private static final String TAG_CLASS = "ReceiverImpl";
  private IAllShareConnector mAllShareConnector;
  private DeviceImpl mDeviceImpl;
  private Receiver.IProgressUpdateEventListener mProgressUpdateListener = null;
  private Receiver.IReceiverResponseListener mReceiverResponseListener = null;
  private boolean mIsSubscribed = false;
  AllShareEventHandler mEventHandler =
      new AllShareEventHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.ReceiverImpl.1
        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
          ERROR error;
          String actionID = cvm.getActionID();
          Bundle bundle = cvm.getBundle();
          if (bundle == null) {
            DLog.w_api(ReceiverImpl.TAG_CLASS, "mEventHandler bundle is NULL!");
            return;
          }
          if (ReceiverImpl.this.mProgressUpdateListener == null) {
            return;
          }
          String errStr = bundle.getString("BUNDLE_ENUM_ERROR");
          ERROR error2 = ERROR.FAIL;
          if (errStr == null) {
            ERROR error3 = ERROR.SUCCESS;
            error = error3;
          } else {
            ERROR error4 = ERROR.stringToEnum(errStr);
            error = error4;
          }
          if (!actionID.equals(AllShareEvent.EVENT_RECEIVER_PROGRESS_UPDATE_BY_ITEM)) {
            if (actionID.equals(AllShareEvent.EVENT_RECEIVER_COMPLETED_BY_ITEM)) {
              Bundle itemBundle = bundle.getBundle(AllShareKey.BUNDLE_PARCELABLE_ITEM);
              Item item = ReceiverImpl.this.getItem(itemBundle);
              try {
                ReceiverImpl.this.mProgressUpdateListener.onCompleted(item, error);
                return;
              } catch (Error err) {
                DLog.w_api(
                    ReceiverImpl.TAG_CLASS,
                    "mEventHandler(EVENT_RECEIVER_COMPLETED_BY_ITEM) Error ",
                    err);
                return;
              } catch (Exception e) {
                DLog.w_api(
                    ReceiverImpl.TAG_CLASS,
                    "mEventHandler(EVENT_RECEIVER_COMPLETED_BY_ITEM) Exception ",
                    e);
                return;
              }
            }
            return;
          }
          long receivedSize = bundle.getLong("BUNDLE_LONG_PROGRESS");
          long totalSize = bundle.getLong(AllShareKey.BUNDLE_LONG_TOTAL_SIZE);
          Bundle itemBundle2 = bundle.getBundle(AllShareKey.BUNDLE_PARCELABLE_ITEM);
          Item item2 = ReceiverImpl.this.getItem(itemBundle2);
          try {
            try {
              ReceiverImpl.this.mProgressUpdateListener.onProgressUpdated(
                  receivedSize, totalSize, item2, error);
            } catch (Error e2) {
              err = e2;
              DLog.w_api(
                  ReceiverImpl.TAG_CLASS,
                  "mEventHandler(EVENT_RECEIVER_PROGRESS_UPDATE_BY_ITEM) Error ",
                  err);
            } catch (Exception e3) {
              e = e3;
              DLog.w_api(
                  ReceiverImpl.TAG_CLASS,
                  "mEventHandler(EVENT_RECEIVER_PROGRESS_UPDATE_BY_ITEM) Exception ",
                  e);
            }
          } catch (Error e4) {
            err = e4;
          } catch (Exception e5) {
            e = e5;
          }
        }
      };
  AllShareResponseHandler mResponseHandler =
      new AllShareResponseHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.ReceiverImpl.2
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
          Bundle bundle = cvm.getBundle();
          if (bundle == null) {
            DLog.w_api(ReceiverImpl.TAG_CLASS, "mResponseHandler bundle is NULL!");
            return;
          }
          String actionID = cvm.getActionID();
          ERROR err = ERROR.stringToEnum(bundle.getString("BUNDLE_ENUM_ERROR"));
          if (actionID.equals(AllShareAction.ACTION_RECEIVER_RECEIVE_BY_ITEM)) {
            Bundle itemBundle = bundle.getBundle(AllShareKey.BUNDLE_PARCELABLE_ITEM);
            Item item = ReceiverImpl.this.getItem(itemBundle);
            try {
              ReceiverImpl.this.mReceiverResponseListener.onReceiveResponseReceived(item, err);
              return;
            } catch (Error e) {
              DLog.w_api(
                  ReceiverImpl.TAG_CLASS,
                  "mResponseHandler ACTION_RECEIVER_RECEIVE_BY_ITEM Error",
                  e);
              return;
            } catch (Exception e2) {
              DLog.w_api(
                  ReceiverImpl.TAG_CLASS,
                  "mResponseHandler ACTION_RECEIVER_RECEIVE_BY_ITEM Exception",
                  e2);
              return;
            }
          }
          if (actionID.equals(AllShareAction.ACTION_RECEIVER_CANCEL_BY_ITEM)) {
            Bundle itemBundle2 = bundle.getBundle(AllShareKey.BUNDLE_PARCELABLE_ITEM);
            Item item2 = ReceiverImpl.this.getItem(itemBundle2);
            try {
              ReceiverImpl.this.mReceiverResponseListener.onCancelResponseReceived(item2, err);
            } catch (Error e3) {
              DLog.w_api(
                  ReceiverImpl.TAG_CLASS,
                  "mResponseHandler ACTION_RECEIVER_CANCEL_BY_ITEM Error",
                  e3);
            } catch (Exception e4) {
              DLog.w_api(
                  ReceiverImpl.TAG_CLASS,
                  "mResponseHandler ACTION_RECEIVER_CANCEL_BY_ITEM Exception",
                  e4);
            }
          }
        }
      };

  ReceiverImpl(IAllShareConnector connector, DeviceImpl deviceImpl) {
    this.mAllShareConnector = null;
    this.mDeviceImpl = null;
    if (connector == null) {
      DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
    } else {
      this.mDeviceImpl = deviceImpl;
      this.mAllShareConnector = connector;
    }
  }

  Item getItem(Bundle b) {
    String typeStr;
    if (b == null || (typeStr = b.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE)) == null) {
      return null;
    }
    Item.MediaType type = Item.MediaType.stringToEnum(typeStr);
    switch (C48783.$SwitchMap$com$samsung$android$allshare$Item$MediaType[type.ordinal()]) {
    }
    return null;
  }

  /* renamed from: com.samsung.android.allshare.ReceiverImpl$3 */
  static /* synthetic */ class C48783 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;

    static {
      int[] iArr = new int[Item.MediaType.values().length];
      $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr;
      try {
        iArr[Item.MediaType.ITEM_AUDIO.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Item$MediaType[
                Item.MediaType.ITEM_IMAGE.ordinal()] =
            2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Item$MediaType[
                Item.MediaType.ITEM_VIDEO.ordinal()] =
            3;
      } catch (NoSuchFieldError e3) {
      }
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

  @Override // com.samsung.android.allshare.media.Receiver
  public void receive(Item item) {
    itemHandling(item, AllShareAction.ACTION_RECEIVER_RECEIVE_BY_ITEM);
  }

  @Override // com.samsung.android.allshare.media.Receiver
  public void cancel(Item item) {
    itemHandling(item, AllShareAction.ACTION_RECEIVER_CANCEL_BY_ITEM);
  }

  @Override // com.samsung.android.allshare.media.Receiver
  public void setProgressUpdateEventListener(Receiver.IProgressUpdateEventListener l) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
      return;
    }
    this.mProgressUpdateListener = l;
    boolean z = this.mIsSubscribed;
    if (!z && l != null) {
      this.mAllShareConnector.subscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
      this.mIsSubscribed = true;
    } else if (z && l == null) {
      this.mAllShareConnector.unsubscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
      this.mIsSubscribed = false;
    }
  }

  @Override // com.samsung.android.allshare.media.Receiver
  public void setReceiverResponseListener(Receiver.IReceiverResponseListener l) {
    this.mReceiverResponseListener = l;
  }

  public String getID() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getID();
  }

  /* JADX WARN: Multi-variable type inference failed */
  private void itemHandling(Item item, String actionID) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      if (actionID.equals(AllShareAction.ACTION_RECEIVER_RECEIVE_BY_ITEM)) {
        this.mReceiverResponseListener.onReceiveResponseReceived(item, ERROR.SERVICE_NOT_CONNECTED);
        return;
      } else {
        this.mReceiverResponseListener.onCancelResponseReceived(item, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
    }
    CVMessage cvm = new CVMessage();
    cvm.setActionID(actionID);
    Bundle bundle = new Bundle();
    if (item instanceof AudioItemImpl) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((AudioItemImpl) item).getBundle());
    } else if (item instanceof VideoItemImpl) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((VideoItemImpl) item).getBundle());
    } else if (item instanceof ImageItemImpl) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((ImageItemImpl) item).getBundle());
    } else if (item.getContentBuildType().equals(Item.ContentBuildType.LOCAL)) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI, item.getURI());
      bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE, item.getMimetype());
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
    } else if (actionID.equals(AllShareAction.ACTION_RECEIVER_RECEIVE_BY_ITEM)) {
      this.mReceiverResponseListener.onReceiveResponseReceived(item, ERROR.INVALID_ARGUMENT);
    } else {
      this.mReceiverResponseListener.onCancelResponseReceived(item, ERROR.INVALID_ARGUMENT);
    }
    bundle.putString("BUNDLE_STRING_ID", getID());
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
  }

  Bundle getBundle() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return null;
    }
    return deviceImpl.getBundle();
  }

  public void removeEventHandler() {
    this.mAllShareConnector.unsubscribeAllShareEvent(
        AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
    this.mIsSubscribed = false;
  }
}
