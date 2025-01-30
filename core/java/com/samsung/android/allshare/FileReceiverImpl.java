package com.samsung.android.allshare;

import android.content.ContentResolver;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.allshare.file.FileReceiver;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
final class FileReceiverImpl extends FileReceiver implements IBundleHolder, IHandlerHolder {
  private static final String TAG_CLASS = "FileReceiverImpl";
  private IAllShareConnector mAllShareConnector;
  private DeviceImpl mDeviceImpl;
  private FileReceiver mReceiver;
  private HashMap<String, SessionInfo> mTimeKeyInfoMap = new HashMap<>();
  private HashMap<String, SessionInfo> mSessionKeyInfoMap = new HashMap<>();
  private boolean mCancelReq = false;
  private boolean mIsSubscribed = false;
  private AllShareResponseHandler mRespHandler =
      new AllShareResponseHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.FileReceiverImpl.2
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
          String action = cvm.getActionID();
          if (action.equals(AllShareAction.FileReceiverAction.ACTION_FILE_RECEIVER_RECEIVE)) {
            notifyResponse(action, cvm.getBundle());
          } else if (action.equals(AllShareAction.FileReceiverAction.ACTION_FILE_RECEIVER_CANCEL)) {
            notifyResponse(action, cvm.getBundle());
          }
          if (action.equals(
              AllShareAction.FileReceiverAction.ACTION_FILE_ARRAYLIST_RECEIVER_RECEIVE)) {
            DLog.d_api(FileReceiverImpl.TAG_CLASS, "mRespHandler.handleResponseMessage() called..");
            notifyListResponse(action, cvm.getBundle());
          } else if (action.equals(
              AllShareAction.FileReceiverAction.ACTION_FILE_ARRAYLIST_RECEIVER_CANCEL)) {
            notifyListResponse(action, cvm.getBundle());
          }
        }

        private void notifyListResponse(String action, Bundle bundle) {
          String errStr = bundle.getString("BUNDLE_ENUM_ERROR");
          String name = bundle.getString("BUNDLE_STRING_NAME");
          String sessionId = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_SESSIONID);
          String uniqueKey = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_UNIQUEKEY);
          DLog.d_api(
              FileReceiverImpl.TAG_CLASS, "action : " + action + " sessionID : " + sessionId);
          ERROR err = ERROR.stringToEnum(errStr);
          ArrayList<Uri> uriList = new ArrayList<>();
          if (action.equals(
              AllShareAction.FileReceiverAction.ACTION_FILE_ARRAYLIST_RECEIVER_RECEIVE)) {
            DLog.d_api(
                FileReceiverImpl.TAG_CLASS,
                "notifyListResponse() ACTION_FILE_ARRAYLIST_RECEIVER_RECEIVE");
            SessionInfo tempSessionInfo = FileReceiverImpl.this.getTimeKeyInfoMap(uniqueKey);
            if (tempSessionInfo == null) {
              DLog.w_api(FileReceiverImpl.TAG_CLASS, "Null pointer Error!, uniqueKey=" + uniqueKey);
              return;
            }
            FileReceiverImpl.this.putSessionKeyInfoMap(sessionId, tempSessionInfo);
            FileReceiverImpl.this.removeTimeKeyInfoMap(uniqueKey);
            SessionInfo tempSessionInfo2 = FileReceiverImpl.this.getSessionKeyInfoMap(sessionId);
            if (tempSessionInfo2 == null) {
              DLog.w_api(FileReceiverImpl.TAG_CLASS, "Null pointer Error!, sessionId=" + sessionId);
              return;
            }
            FileReceiver.IFileReceiverReceiveResponseListener listener =
                tempSessionInfo2.getResponseListener();
            ArrayList<String> contentUriList =
                bundle.getStringArrayList(
                    AllShareKey.FileReceiverKey.BUNDLE_STRING_ARRAYLIST_CONTENT_URI);
            if (contentUriList != null) {
              Iterator<String> it = contentUriList.iterator();
              while (it.hasNext()) {
                String uriPath = it.next();
                uriList.add(Uri.parse(uriPath));
              }
            }
            if (listener != null) {
              listener.onReceiveResponseReceived(
                  FileReceiverImpl.this.mReceiver, sessionId, uriList, name, err);
              return;
            } else {
              DLog.w_api(FileReceiverImpl.TAG_CLASS, "onReceiveResponseReceived listener is null!");
              return;
            }
          }
          if (action.equals(
              AllShareAction.FileReceiverAction.ACTION_FILE_ARRAYLIST_RECEIVER_CANCEL)) {
            DLog.d_api(
                FileReceiverImpl.TAG_CLASS,
                "notifyListResponse() ACTION_FILE_ARRAYLIST_RECEIVER_CANCEL  sessionID : "
                    + sessionId);
            SessionInfo tempSessionInfo3 = FileReceiverImpl.this.getSessionKeyInfoMap(sessionId);
            if (tempSessionInfo3 == null) {
              DLog.w_api(
                  FileReceiverImpl.TAG_CLASS, "Null pointer Error!, sessionId= " + sessionId);
              return;
            }
            FileReceiver.IFileReceiverReceiveResponseListener listener2 =
                tempSessionInfo3.getResponseListener();
            if (listener2 != null) {
              DLog.d_api(
                  FileReceiverImpl.TAG_CLASS,
                  "listener.onCancelResponseReceived( mReceiver, sessionId, err )");
              listener2.onCancelResponseReceived(FileReceiverImpl.this.mReceiver, sessionId, err);
              if (FileReceiverImpl.this.isCancelRequest() && tempSessionInfo3.removed()) {
                FileReceiverImpl.this.removeSessionKeyInfoMap(sessionId);
                FileReceiverImpl.this.releaseCancelRequest();
                return;
              }
              return;
            }
            DLog.w_api(FileReceiverImpl.TAG_CLASS, "onCancelResponseReceived listener is null!");
          }
        }

        private void notifyResponse(String action, Bundle bundle) {
          String errStr = bundle.getString("BUNDLE_ENUM_ERROR");
          String name = bundle.getString("BUNDLE_STRING_NAME");
          String sessionId = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_SESSIONID);
          String uniqueKey = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_UNIQUEKEY);
          ERROR err = ERROR.stringToEnum(errStr);
          if (action.equals(AllShareAction.FileReceiverAction.ACTION_FILE_RECEIVER_RECEIVE)) {
            DLog.d_api(
                FileReceiverImpl.TAG_CLASS, "notifyListResponse()- ACTION_FILE_RECEIVER_RECEIVE");
            SessionInfo tempSessionInfo = FileReceiverImpl.this.getTimeKeyInfoMap(uniqueKey);
            if (tempSessionInfo == null) {
              DLog.w_api(FileReceiverImpl.TAG_CLASS, "Null pointer Error!, uniqueKey=" + uniqueKey);
              return;
            }
            FileReceiverImpl.this.putSessionKeyInfoMap(sessionId, tempSessionInfo);
            FileReceiverImpl.this.removeTimeKeyInfoMap(uniqueKey);
            SessionInfo tempSessionInfo2 = FileReceiverImpl.this.getSessionKeyInfoMap(sessionId);
            if (tempSessionInfo2 == null) {
              DLog.w_api(FileReceiverImpl.TAG_CLASS, "Null pointer Error!, sessionId=" + sessionId);
              return;
            }
            FileReceiver.IFileReceiverReceiveResponseListener listener =
                tempSessionInfo2.getResponseListener();
            String filePath = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_FILE_PATH);
            ArrayList<File> fileList = new ArrayList<>();
            fileList.add(new File(filePath));
            String uriPath = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_URI_PATH);
            ArrayList<Uri> uriList = new ArrayList<>();
            uriList.add(Uri.parse(uriPath));
            if (listener != null) {
              listener.onReceiveResponseReceived(
                  FileReceiverImpl.this.mReceiver, sessionId, uriList, name, err);
              return;
            } else {
              DLog.w_api(FileReceiverImpl.TAG_CLASS, "onReceiveResponseReceived listener is null!");
              return;
            }
          }
          if (action.equals(AllShareAction.FileReceiverAction.ACTION_FILE_RECEIVER_CANCEL)) {
            DLog.d_api(
                FileReceiverImpl.TAG_CLASS, "notifyListResponse()- ACTION_FILE_RECEIVER_CANCEL");
            SessionInfo tempSessionInfo3 = FileReceiverImpl.this.getSessionKeyInfoMap(sessionId);
            if (tempSessionInfo3 == null) {
              DLog.w_api(FileReceiverImpl.TAG_CLASS, "Null pointer Error!, sessionId=" + sessionId);
              return;
            }
            FileReceiver.IFileReceiverReceiveResponseListener listener2 =
                tempSessionInfo3.getResponseListener();
            if (listener2 != null) {
              listener2.onCancelResponseReceived(FileReceiverImpl.this.mReceiver, sessionId, err);
              if (FileReceiverImpl.this.isCancelRequest() && tempSessionInfo3.removed()) {
                FileReceiverImpl.this.removeSessionKeyInfoMap(sessionId);
                FileReceiverImpl.this.releaseCancelRequest();
                return;
              }
              return;
            }
            DLog.w_api(FileReceiverImpl.TAG_CLASS, "onCancelResponseReceived listener is null!");
          }
        }
      };
  private AllShareEventHandler mEventHandler =
      new AllShareEventHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.FileReceiverImpl.3
        private HashMap<String, INotifyProgressEvent> mEventNotiMap;

        {
          HashMap<String, INotifyProgressEvent> hashMap = new HashMap<>();
          this.mEventNotiMap = hashMap;
          hashMap.put(
              AllShareEvent.FileReceiverEvent.EVENT_FILE_RECEIVER_PROGRESS, new NotifyProgress());
          this.mEventNotiMap.put(
              AllShareEvent.FileReceiverEvent.EVENT_FILE_RECEIVER_COMPLETED, new NotifyCompleted());
          this.mEventNotiMap.put(
              AllShareEvent.FileReceiverEvent.EVENT_FILE_RECEIVER_FAILED, new NotifyFailed());
        }

        @Override // com.samsung.android.allshare.AllShareEventHandler
        void handleEventMessage(CVMessage cvm) {
          FileReceiver.IFileReceiverProgressUpdateEventListener listener =
              FileReceiverImpl.this.mProgressEventListener;
          if (listener == null) {
            return;
          }
          String event = cvm.getEventID();
          INotifyProgressEvent notifier = this.mEventNotiMap.get(event);
          if (notifier != null) {
            notifier.onNotifyEvent(listener, cvm.getBundle());
          }
        }

        /* renamed from: com.samsung.android.allshare.FileReceiverImpl$3$NotifyProgress */
        class NotifyProgress implements INotifyProgressEvent {
          NotifyProgress() {}

          @Override // com.samsung.android.allshare.FileReceiverImpl.INotifyProgressEvent
          public void onNotifyEvent(
              FileReceiver.IFileReceiverProgressUpdateEventListener listener, Bundle bundle) {
            ERROR error;
            ERROR error2 = ERROR.FAIL;
            long progress = bundle.getLong(AllShareKey.FileReceiverKey.BUNDLE_LONG_FILE_PROGRESS);
            long size = bundle.getLong(AllShareKey.FileReceiverKey.BUNDLE_LONG_FILE_SIZE);
            String path = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_FILE_PATH);
            String uriPath = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_URI_PATH);
            String sessionId =
                bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_SESSIONID);
            String errorStr = bundle.getString("BUNDLE_ENUM_ERROR");
            if (errorStr == null) {
              error = ERROR.FAIL;
            } else {
              error = ERROR.stringToEnum(errorStr);
            }
            File file = new File(path);
            Uri uri = Uri.parse(uriPath);
            listener.onProgressUpdated(
                FileReceiverImpl.this.mReceiver, sessionId, progress, size, file, uri, error);
          }
        }

        /* renamed from: com.samsung.android.allshare.FileReceiverImpl$3$NotifyCompleted */
        class NotifyCompleted implements INotifyProgressEvent {
          NotifyCompleted() {}

          @Override // com.samsung.android.allshare.FileReceiverImpl.INotifyProgressEvent
          public void onNotifyEvent(
              FileReceiver.IFileReceiverProgressUpdateEventListener listener, Bundle bundle) {
            ERROR error;
            ERROR error2 = ERROR.FAIL;
            String path = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_FILE_PATH);
            String uriPath = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_URI_PATH);
            String errorStr = bundle.getString("BUNDLE_ENUM_ERROR");
            String sessionId =
                bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_SESSIONID);
            if (errorStr == null) {
              error = ERROR.FAIL;
            } else {
              error = ERROR.stringToEnum(errorStr);
            }
            File file = new File(path);
            Uri uri = Uri.parse(uriPath);
            listener.onCompleted(FileReceiverImpl.this.mReceiver, sessionId, file, uri, error);
          }
        }

        /* renamed from: com.samsung.android.allshare.FileReceiverImpl$3$NotifyFailed */
        class NotifyFailed implements INotifyProgressEvent {
          NotifyFailed() {}

          @Override // com.samsung.android.allshare.FileReceiverImpl.INotifyProgressEvent
          public void onNotifyEvent(
              FileReceiver.IFileReceiverProgressUpdateEventListener listener, Bundle bundle) {
            String path = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_FILE_PATH);
            String uriPath = bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_URI_PATH);
            String errStr = bundle.getString("BUNDLE_ENUM_ERROR");
            String sessionId =
                bundle.getString(AllShareKey.FileReceiverKey.BUNDLE_STRING_SESSIONID);
            File file = new File(path);
            Uri uri = Uri.parse(uriPath);
            ERROR err = ERROR.stringToEnum(errStr);
            listener.onFailed(FileReceiverImpl.this.mReceiver, sessionId, file, uri, err);
          }
        }
      };
  private FileReceiver.IFileReceiverProgressUpdateEventListener mProgressEventListener =
      new FileReceiver
          .IFileReceiverProgressUpdateEventListener() { // from class:
                                                        // com.samsung.android.allshare.FileReceiverImpl.4
        @Override // com.samsung.android.allshare.file.FileReceiver.IFileReceiverProgressUpdateEventListener
        public void onProgressUpdated(
            FileReceiver receiver,
            String sessionID,
            long receivedSize,
            long totalSize,
            File file,
            Uri uri,
            ERROR err) {
          DLog.d_api(FileReceiverImpl.TAG_CLASS, "onProgressUpdated()");
          SessionInfo tempSessionInfo = FileReceiverImpl.this.getSessionKeyInfoMap(sessionID);
          if (tempSessionInfo == null) {
            DLog.w_api(FileReceiverImpl.TAG_CLASS, "Null pointer Error!, sessionId=" + sessionID);
            return;
          }
          FileReceiver.IFileReceiverProgressUpdateEventListener listener =
              tempSessionInfo.getEventListener();
          if (listener != null) {
            listener.onProgressUpdated(
                receiver, sessionID, receivedSize, totalSize, file, uri, err);
          } else {
            DLog.w_api(FileReceiverImpl.TAG_CLASS, "onProgressUpdated listener is null!");
          }
        }

        @Override // com.samsung.android.allshare.file.FileReceiver.IFileReceiverProgressUpdateEventListener
        public void onFailed(
            FileReceiver receiver, String sessionID, File file, Uri uri, ERROR err) {
          DLog.d_api(FileReceiverImpl.TAG_CLASS, "onFailed()");
          SessionInfo tempSessionInfo = FileReceiverImpl.this.getSessionKeyInfoMap(sessionID);
          if (tempSessionInfo == null) {
            DLog.w_api(FileReceiverImpl.TAG_CLASS, "Null pointer Error!, sessionId=" + sessionID);
            return;
          }
          FileReceiver.IFileReceiverProgressUpdateEventListener listener =
              tempSessionInfo.getEventListener();
          if (listener != null) {
            listener.onFailed(receiver, sessionID, file, uri, err);
          } else {
            DLog.w_api(FileReceiverImpl.TAG_CLASS, "onFailed listener is null!");
          }
          if (FileReceiverImpl.this.isCancelRequest() && tempSessionInfo.removed()) {
            FileReceiverImpl.this.removeSessionKeyInfoMap(sessionID);
            FileReceiverImpl.this.releaseCancelRequest();
          } else if (!FileReceiverImpl.this.isCancelRequest()) {
            FileReceiverImpl.this.removeSessionKeyInfoMap(sessionID);
          }
        }

        @Override // com.samsung.android.allshare.file.FileReceiver.IFileReceiverProgressUpdateEventListener
        public void onCompleted(
            FileReceiver receiver, String sessionID, File file, Uri uri, ERROR err) {
          DLog.d_api(FileReceiverImpl.TAG_CLASS, "onCompleted()");
          SessionInfo tempSessionInfo = FileReceiverImpl.this.getSessionKeyInfoMap(sessionID);
          if (tempSessionInfo == null) {
            DLog.w_api(FileReceiverImpl.TAG_CLASS, "Null pointer Error!, sessionId=" + sessionID);
            return;
          }
          FileReceiver.IFileReceiverProgressUpdateEventListener listener =
              tempSessionInfo.getEventListener();
          if (listener != null) {
            listener.onCompleted(receiver, sessionID, file, uri, err);
          } else {
            DLog.w_api(FileReceiverImpl.TAG_CLASS, "onCompleted listener is null!");
          }
          if (tempSessionInfo.completed()) {
            DLog.i_api(
                FileReceiverImpl.TAG_CLASS,
                "All of FileTransfer was completed ["
                    + sessionID
                    + ",["
                    + tempSessionInfo.getCount()
                    + NavigationBarInflaterView.SIZE_MOD_END);
            FileReceiverImpl.this.removeSessionKeyInfoMap(sessionID);
          }
        }
      };

  private interface INotifyProgressEvent {
    void onNotifyEvent(
        FileReceiver.IFileReceiverProgressUpdateEventListener
            iFileReceiverProgressUpdateEventListener,
        Bundle bundle);
  }

  private static class SessionInfo {
    private int mCount;
    private FileReceiver.IFileReceiverProgressUpdateEventListener mEventListener;
    private FileReceiver.IFileReceiverReceiveResponseListener mResponseListener;
    private int mState = 0;

    public SessionInfo(
        int count,
        FileReceiver.IFileReceiverReceiveResponseListener responseListener,
        FileReceiver.IFileReceiverProgressUpdateEventListener eventListener) {
      this.mCount = 0;
      this.mResponseListener = null;
      this.mEventListener = null;
      this.mCount = count;
      this.mResponseListener = responseListener;
      this.mEventListener = eventListener;
    }

    public FileReceiver.IFileReceiverReceiveResponseListener getResponseListener() {
      return this.mResponseListener;
    }

    public FileReceiver.IFileReceiverProgressUpdateEventListener getEventListener() {
      return this.mEventListener;
    }

    public int getCount() {
      return this.mCount;
    }

    public boolean completed() {
      int i = this.mCount - 1;
      this.mCount = i;
      if (i > 0) {
        return false;
      }
      this.mCount = 0;
      return true;
    }

    public boolean removed() {
      int i = this.mState - 1;
      this.mState = i;
      if (i <= -2) {
        this.mState = -2;
        return true;
      }
      return false;
    }
  }

  FileReceiverImpl(IAllShareConnector connector, DeviceImpl deviceImpl) {
    this.mDeviceImpl = null;
    this.mAllShareConnector = null;
    this.mReceiver = null;
    if (connector == null) {
      DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
      return;
    }
    ContentResolver cr = connector.getContentResolver();
    if (cr != null) {
      DLog.d_api(TAG_CLASS, "ContentResolver : " + cr.toString());
    }
    this.mDeviceImpl = deviceImpl;
    this.mAllShareConnector = connector;
    setProgressUpdateEventListener(this.mProgressEventListener);
    this.mReceiver = this;
  }

  @Override // com.samsung.android.allshare.Device
  public String getNIC() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getNIC();
  }

  @Override // com.samsung.android.allshare.file.FileReceiver
  public void cancel(final String sessionId) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      this.mRespHandler.postDelayed(
          new Runnable() { // from class: com.samsung.android.allshare.FileReceiverImpl.1
            @Override // java.lang.Runnable
            public void run() {
              String str = sessionId;
              if (str != null) {
                SessionInfo tempSessionInfo = FileReceiverImpl.this.getSessionKeyInfoMap(str);
                if (tempSessionInfo == null) {
                  DLog.w_api(
                      FileReceiverImpl.TAG_CLASS, "Null pointer Error!, sessionId=" + sessionId);
                  return;
                }
                FileReceiver.IFileReceiverReceiveResponseListener listener =
                    tempSessionInfo.getResponseListener();
                if (listener != null) {
                  listener.onCancelResponseReceived(
                      FileReceiverImpl.this.mReceiver, sessionId, ERROR.FAIL);
                  FileReceiverImpl.this.removeSessionKeyInfoMap(sessionId);
                }
              }
            }
          },
          1L);
      return;
    }
    if (sessionId != null) {
      CVMessage req_msg = new CVMessage();
      req_msg.setActionID(AllShareAction.FileReceiverAction.ACTION_FILE_ARRAYLIST_RECEIVER_CANCEL);
      Bundle bundle = req_msg.getBundle();
      bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
      bundle.putString(AllShareKey.FileReceiverKey.BUNDLE_STRING_SESSIONID, sessionId);
      DLog.d_api(TAG_CLASS, "sessionID : " + sessionId);
      this.mAllShareConnector.requestCVMAsync(req_msg, this.mRespHandler);
      setCancelRequest();
    }
  }

  private void setCancelRequest() {
    this.mCancelReq = true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void releaseCancelRequest() {
    this.mCancelReq = false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean isCancelRequest() {
    return this.mCancelReq;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void putSessionKeyInfoMap(String key, SessionInfo sessionInfo) {
    DLog.d_api(
        TAG_CLASS,
        "putSessionKeyInfoMap() called. key : " + key + ", count : " + sessionInfo.getCount());
    SessionInfo sessionKeyInfo =
        new SessionInfo(
            sessionInfo.getCount(),
            sessionInfo.getResponseListener(),
            sessionInfo.getEventListener());
    this.mSessionKeyInfoMap.put(key, sessionKeyInfo);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public SessionInfo getSessionKeyInfoMap(String key) {
    DLog.d_api(
        TAG_CLASS,
        "getSessionKeyInfoMap() called. key : " + key + "size : " + this.mSessionKeyInfoMap.size());
    return this.mSessionKeyInfoMap.get(key);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void removeSessionKeyInfoMap(String key) {
    this.mSessionKeyInfoMap.remove(key);
    DLog.d_api(
        TAG_CLASS,
        "removeSessionKeyInfoMap() called. key : " + key + "size : " + this.mTimeKeyInfoMap.size());
  }

  private void putTimeKeyInfoMap(String key, SessionInfo sessionInfo) {
    DLog.d_api(
        TAG_CLASS,
        "putTimeKeyInfoMap() called. key : " + key + ", count : " + sessionInfo.getCount());
    SessionInfo timeKeyInfo =
        new SessionInfo(
            sessionInfo.getCount(),
            sessionInfo.getResponseListener(),
            sessionInfo.getEventListener());
    this.mTimeKeyInfoMap.put(key, timeKeyInfo);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public SessionInfo getTimeKeyInfoMap(String key) {
    DLog.d_api(TAG_CLASS, "getTimeKeyInfoMap() called. key : " + key);
    return this.mTimeKeyInfoMap.get(key);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void removeTimeKeyInfoMap(String key) {
    this.mTimeKeyInfoMap.remove(key);
    DLog.d_api(
        TAG_CLASS,
        "removeTimeKeyInfoMap() called. key : " + key + "size : " + this.mTimeKeyInfoMap.size());
  }

  private void setProgressUpdateEventListener(
      FileReceiver.IFileReceiverProgressUpdateEventListener listener) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return;
    }
    this.mProgressEventListener = listener;
    boolean z = this.mIsSubscribed;
    if (!z && listener != null) {
      this.mAllShareConnector.subscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
      this.mIsSubscribed = true;
    } else if (z && listener == null) {
      this.mAllShareConnector.unsubscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
      this.mIsSubscribed = false;
    }
  }

  @Override // com.samsung.android.allshare.Device
  public Device.DeviceDomain getDeviceDomain() {
    return this.mDeviceImpl.getDeviceDomain();
  }

  @Override // com.samsung.android.allshare.Device
  public Device.DeviceType getDeviceType() {
    return this.mDeviceImpl.getDeviceType();
  }

  @Override // com.samsung.android.allshare.Device
  public String getID() {
    return this.mDeviceImpl.getID();
  }

  @Override // com.samsung.android.allshare.Device
  @Deprecated
  public String getIPAdress() {
    return getIPAddress();
  }

  @Override // com.samsung.android.allshare.Device
  public String getIPAddress() {
    return this.mDeviceImpl.getIPAddress();
  }

  @Override // com.samsung.android.allshare.Device
  public Uri getIcon() {
    return this.mDeviceImpl.getIcon();
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
  public String getModelName() {
    return this.mDeviceImpl.getModelName();
  }

  @Override // com.samsung.android.allshare.Device
  public String getName() {
    return this.mDeviceImpl.getName();
  }

  @Override // com.samsung.android.allshare.file.FileReceiver
  public void receive(
      ArrayList<File> filelist,
      final ArrayList<Uri> urilist,
      final String senderName,
      Boolean isFolder,
      String parentFolder,
      final FileReceiver.IFileReceiverReceiveResponseListener responseListener,
      FileReceiver.IFileReceiverProgressUpdateEventListener eventListener) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector != null && iAllShareConnector.isAllShareServiceConnected()) {
      if (urilist != null && !urilist.isEmpty()) {
        DLog.w_api(
            TAG_CLASS, "urilist size = " + urilist.size() + "filelist size = " + filelist.size());
        ArrayList<String> filePathList = new ArrayList<>();
        ArrayList<String> uriList = new ArrayList<>();
        Iterator<File> it = filelist.iterator();
        while (it.hasNext()) {
          File file = it.next();
          if (file != null) {
            filePathList.add(file.getAbsolutePath());
          } else {
            filePathList.add(null);
          }
        }
        Iterator<Uri> it2 = urilist.iterator();
        while (it2.hasNext()) {
          Uri uri = it2.next();
          uriList.add(uri.toString());
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(
            AllShareAction.FileReceiverAction.ACTION_FILE_ARRAYLIST_RECEIVER_RECEIVE);
        Bundle bundle = req_msg.getBundle();
        bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        bundle.putString("BUNDLE_STRING_NAME", senderName);
        bundle.putStringArrayList(
            AllShareKey.FileReceiverKey.BUNDLE_STRING_ARRAYLIST_FILE_PATH, filePathList);
        bundle.putStringArrayList(
            AllShareKey.FileReceiverKey.BUNDLE_STRING_ARRAYLIST_CONTENT_URI, uriList);
        bundle.putBoolean(
            AllShareKey.FileReceiverKey.BUNDLE_BOOLEAN_FOLDER, isFolder.booleanValue());
        bundle.putString(AllShareKey.FileReceiverKey.BUNDLE_STRING_PARENTFOLDER, parentFolder);
        String timeKey = Long.toString(System.nanoTime());
        bundle.putString(AllShareKey.FileReceiverKey.BUNDLE_STRING_UNIQUEKEY, timeKey);
        SessionInfo tempSessionInfo =
            new SessionInfo(uriList.size(), responseListener, eventListener);
        putTimeKeyInfoMap(timeKey, tempSessionInfo);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mRespHandler);
        return;
      }
      this.mRespHandler.postDelayed(
          new Runnable() { // from class: com.samsung.android.allshare.FileReceiverImpl.5
            @Override // java.lang.Runnable
            public void run() {
              DLog.d_api(
                  FileReceiverImpl.TAG_CLASS,
                  "mRespHandler.postDelayed.mReceiveResponseListener.onReceiveResponseReceived()"
                      + " called..");
              responseListener.onReceiveResponseReceived(
                  FileReceiverImpl.this.mReceiver, "", urilist, senderName, ERROR.FAIL);
            }
          },
          1L);
      return;
    }
    responseListener.onReceiveResponseReceived(this.mReceiver, "", urilist, senderName, ERROR.FAIL);
  }

  @Override // com.sec.android.allshare.iface.IBundleHolder
  public Bundle getBundle() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return null;
    }
    return deviceImpl.getBundle();
  }

  @Override // com.sec.android.allshare.iface.IHandlerHolder
  public void removeEventHandler() {
    this.mAllShareConnector.unsubscribeAllShareEvent(
        AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
    this.mIsSubscribed = false;
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
    if (this.mDeviceImpl == null) {
      DLog.w_api(TAG_CLASS, "getP2pMacAddress : mDeviceImpl == null");
      return "";
    }
    DLog.w_api(TAG_CLASS, "getP2pMacAddress");
    return this.mDeviceImpl.getP2pMacAddress();
  }

  @Override // com.samsung.android.allshare.Device
  public String getScreenSharingInfo() {
    return "";
  }

  @Override // com.samsung.android.allshare.Device
  public void requestMobileToTV(String ip, int port) {
    DLog.w_api(TAG_CLASS, "requestMobileToTV : call requestMobileToTV");
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return;
    }
    deviceImpl.requestMobileToTV(ip, port);
  }

  @Override // com.samsung.android.allshare.Device
  public String getSecProductP2pMacAddr() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getSecProductP2pMacAddr();
  }

  @Override // com.samsung.android.allshare.Device
  public String getScreenSharingP2pMacAddr() {
    return "";
  }

  @Override // com.samsung.android.allshare.Device
  public String getProductCapInfo(Device.InformationType infoType) {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getProductCapInfo(infoType);
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
