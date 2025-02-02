package android.net;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.android.modules.utils.build.SdkLevel;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class NetworkFactory {
  public static final int CMD_CANCEL_REQUEST = 2;
  public static final int CMD_REQUEST_NETWORK = 1;
  static final boolean DBG = true;
  static final boolean VDBG = false;
  private final String LOG_TAG;
  final NetworkFactoryShim mImpl;
  private int mRefCount = 0;

  public boolean acceptRequest(NetworkRequest networkRequest) {
    return true;
  }

  public void startNetwork() {}

  public void stopNetwork() {}

  public NetworkFactory(
      Looper looper, Context context, String str, NetworkCapabilities networkCapabilities) {
    this.LOG_TAG = str;
    if (SdkLevel.isAtLeastS()) {
      this.mImpl = new NetworkFactoryImpl(this, looper, context, networkCapabilities);
    } else {
      this.mImpl = new NetworkFactoryLegacyImpl(this, looper, context, networkCapabilities);
    }
  }

  public Message obtainMessage(int i, int i2, int i3, Object obj) {
    return this.mImpl.obtainMessage(i, i2, i3, obj);
  }

  public final Looper getLooper() {
    return this.mImpl.getLooper();
  }

  public void register() {
    this.mImpl.register(this.LOG_TAG);
  }

  public void registerIgnoringScore() {
    this.mImpl.registerIgnoringScore(this.LOG_TAG);
  }

  public void terminate() {
    this.mImpl.terminate();
  }

  public final void reevaluateAllRequests() {
    this.mImpl.reevaluateAllRequests();
  }

  public void releaseRequestAsUnfulfillableByAnyFactory(NetworkRequest networkRequest) {
    this.mImpl.releaseRequestAsUnfulfillableByAnyFactory(networkRequest);
  }

  public void needNetworkFor(NetworkRequest networkRequest) {
    int i = this.mRefCount + 1;
    this.mRefCount = i;
    if (i == 1) {
      startNetwork();
    }
  }

  public void releaseNetworkFor(NetworkRequest networkRequest) {
    int i = this.mRefCount - 1;
    this.mRefCount = i;
    if (i == 0) {
      stopNetwork();
    }
  }

  @Deprecated
  public void setScoreFilter(int i) {
    this.mImpl.setScoreFilter(i);
  }

  public void setScoreFilter(NetworkScore networkScore) {
    this.mImpl.setScoreFilter(networkScore);
  }

  public void setCapabilityFilter(NetworkCapabilities networkCapabilities) {
    this.mImpl.setCapabilityFilter(networkCapabilities);
  }

  public int getRequestCount() {
    return this.mImpl.getRequestCount();
  }

  public int getSerialNumber() {
    return this.mImpl.getSerialNumber();
  }

  public NetworkProvider getProvider() {
    return this.mImpl.getProvider();
  }

  public void log(String str) {
    Log.d(this.LOG_TAG, str);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    this.mImpl.dump(fileDescriptor, printWriter, strArr);
  }

  public String toString() {
    return "{" + this.LOG_TAG + " " + this.mImpl.toString() + "}";
  }
}
