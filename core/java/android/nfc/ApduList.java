package android.nfc;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class ApduList implements Parcelable {
  public static final Parcelable.Creator<ApduList> CREATOR =
      new Parcelable.Creator<ApduList>() { // from class: android.nfc.ApduList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduList createFromParcel(Parcel in) {
          return new ApduList(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduList[] newArray(int size) {
          return new ApduList[size];
        }
      };
  private ArrayList<byte[]> commands;

  public ApduList() {
    this.commands = new ArrayList<>();
  }

  public void add(byte[] command) {
    this.commands.add(command);
  }

  public List<byte[]> get() {
    return this.commands;
  }

  private ApduList(Parcel in) {
    this.commands = new ArrayList<>();
    int count = in.readInt();
    for (int i = 0; i < count; i++) {
      int length = in.readInt();
      byte[] cmd = new byte[length];
      in.readByteArray(cmd);
      this.commands.add(cmd);
    }
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.commands.size());
    Iterator<byte[]> it = this.commands.iterator();
    while (it.hasNext()) {
      byte[] cmd = it.next();
      dest.writeInt(cmd.length);
      dest.writeByteArray(cmd);
    }
  }
}
