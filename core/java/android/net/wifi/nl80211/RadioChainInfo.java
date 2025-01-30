package android.net.wifi.nl80211;

import android.annotation.SystemApi;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class RadioChainInfo implements Parcelable {
  public static final Parcelable.Creator<RadioChainInfo> CREATOR =
      new Parcelable.Creator<
          RadioChainInfo>() { // from class: android.net.wifi.nl80211.RadioChainInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioChainInfo createFromParcel(Parcel in) {
          return new RadioChainInfo(in.readInt(), in.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioChainInfo[] newArray(int size) {
          return new RadioChainInfo[size];
        }
      };
  private static final String TAG = "RadioChainInfo";
  public int chainId;
  public int level;

  public int getChainId() {
    return this.chainId;
  }

  public int getLevelDbm() {
    return this.level;
  }

  public RadioChainInfo(int chainId, int level) {
    this.chainId = chainId;
    this.level = level;
  }

  public boolean equals(Object rhs) {
    RadioChainInfo chainInfo;
    if (this == rhs) {
      return true;
    }
    if ((rhs instanceof RadioChainInfo) && (chainInfo = (RadioChainInfo) rhs) != null) {
      return this.chainId == chainInfo.chainId && this.level == chainInfo.level;
    }
    return false;
  }

  public int hashCode() {
    return Objects.hash(Integer.valueOf(this.chainId), Integer.valueOf(this.level));
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(this.chainId);
    out.writeInt(this.level);
  }
}
