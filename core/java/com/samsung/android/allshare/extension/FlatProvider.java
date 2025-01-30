package com.samsung.android.allshare.extension;

import android.net.Uri;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Icon;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.extension.impl.IMediaGetter;
import com.samsung.android.allshare.extension.impl.MediaGetterManager;
import com.samsung.android.allshare.media.Provider;
import com.samsung.android.allshare.media.Receiver;
import com.samsung.android.allshare.media.SearchCriteria;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class FlatProvider extends Provider {
  private IMediaGetter mFlatBorwseWorker;
  private Provider mProvider;

  public interface IFlatProviderConnection {
    void onCancel();

    void onError(ERROR error);

    void onFinish();

    void onProgress(ArrayList<Item> arrayList);

    void onStart();
  }

  public FlatProvider(Provider decoratedProvider) {
    this.mProvider = null;
    this.mFlatBorwseWorker = null;
    this.mProvider = decoratedProvider;
    this.mFlatBorwseWorker = MediaGetterManager.createMediaGetter(this);
  }

  public void startFlatBrowse(Item.MediaType type, IFlatProviderConnection conn) {
    this.mFlatBorwseWorker.start(this, type, conn);
  }

  void startBrowseMedeaGetterFlatBrowse(
      ArrayList<Item.MediaType> types, IFlatProviderConnection conn) {
    this.mFlatBorwseWorker.start(this, types, conn);
  }

  public void startFlatBrowse(ArrayList<Item.MediaType> types, IFlatProviderConnection conn) {
    this.mFlatBorwseWorker.start(this, types, conn);
  }

  public void cancelFlatBrowse(IFlatProviderConnection conn) {
    this.mFlatBorwseWorker.cancel(conn);
  }

  @Override // com.samsung.android.allshare.media.Provider
  public void browse(Item parentFolderItem, int startIndex, int requestCount) {
    this.mProvider.browse(parentFolderItem, startIndex, requestCount);
  }

  @Override // com.samsung.android.allshare.Device
  public Device.DeviceDomain getDeviceDomain() {
    return this.mProvider.getDeviceDomain();
  }

  @Override // com.samsung.android.allshare.Device
  public Device.DeviceType getDeviceType() {
    return this.mProvider.getDeviceType();
  }

  @Override // com.samsung.android.allshare.Device
  public String getID() {
    return this.mProvider.getID();
  }

  @Override // com.samsung.android.allshare.Device
  @Deprecated
  public String getIPAdress() {
    return getIPAddress();
  }

  @Override // com.samsung.android.allshare.Device
  public String getIPAddress() {
    return this.mProvider.getIPAddress();
  }

  @Override // com.samsung.android.allshare.Device
  public Uri getIcon() {
    return this.mProvider.getIcon();
  }

  @Override // com.samsung.android.allshare.Device
  public ArrayList<Icon> getIconList() {
    Provider provider = this.mProvider;
    if (provider == null) {
      return new ArrayList<>();
    }
    return provider.getIconList();
  }

  @Override // com.samsung.android.allshare.Device
  public String getModelName() {
    return this.mProvider.getModelName();
  }

  @Override // com.samsung.android.allshare.Device
  public String getName() {
    return this.mProvider.getName();
  }

  @Override // com.samsung.android.allshare.media.Provider
  public Receiver getReceiver() {
    return this.mProvider.getReceiver();
  }

  @Override // com.samsung.android.allshare.media.Provider
  public Item getRootFolder() {
    return this.mProvider.getRootFolder();
  }

  @Override // com.samsung.android.allshare.media.Provider
  public boolean isSearchable() {
    return this.mProvider.isSearchable();
  }

  @Override // com.samsung.android.allshare.media.Provider
  public void setEventListener(Provider.IProviderEventListener l) {
    this.mProvider.setEventListener(l);
  }

  @Override // com.samsung.android.allshare.media.Provider
  public void search(SearchCriteria searchCriteria, int startIndex, int requestCount) {
    this.mProvider.search(searchCriteria, startIndex, requestCount);
  }

  @Override // com.samsung.android.allshare.media.Provider
  public void setBrowseItemsResponseListener(Provider.IProviderBrowseResponseListener l) {
    this.mProvider.setBrowseItemsResponseListener(l);
  }

  @Override // com.samsung.android.allshare.media.Provider
  public void setSearchResponseListener(Provider.IProviderSearchResponseListener l) {
    this.mProvider.setSearchResponseListener(l);
  }

  @Override // com.samsung.android.allshare.Device
  public String getNIC() {
    return this.mProvider.getNIC();
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
  public void requestMobileToTV(String ip, int port) {}

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
    return false;
  }
}
