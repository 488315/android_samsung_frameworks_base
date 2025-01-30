package com.samsung.android.knox.mpos;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes5.dex */
public class TACommandRequest implements Parcelable {
  public static final Parcelable.Creator<TACommandRequest> CREATOR =
      new Parcelable.Creator<
          TACommandRequest>() { // from class: com.samsung.android.knox.mpos.TACommandRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandRequest createFromParcel(Parcel in) {
          return new TACommandRequest(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandRequest[] newArray(int size) {
          return new TACommandRequest[size];
        }
      };
  public static final int HEADER_SIZE = 100;
  public static final int MAX_BUFFER_SIZE = 5242880;
  public static final int MAX_DATA_TRANSACTION_SIZE = 3072;
  public static final int PAYLOAD_SIZE = 2972;
  private static final String TAG = "TACommandRequest";
  public int mCommandId;
  public int mLength;
  public byte[] mMagicNum;
  public int mOffset;
  public byte[] mRequest;
  public int mVersion;

  public TACommandRequest() {
    this.mVersion = -1;
    this.mMagicNum = null;
    this.mLength = 0;
    this.mOffset = 0;
    this.mCommandId = -1;
    this.mRequest = null;
  }

  public void init(int version, byte[] magic, int cmdId, byte[] request) {
    this.mVersion = version;
    this.mMagicNum = magic;
    this.mCommandId = cmdId;
    this.mRequest = request;
    if (request != null) {
      this.mLength = request.length;
    } else {
      this.mLength = 0;
    }
    this.mOffset = 0;
  }

  private TACommandRequest(Parcel in) {
    this.mVersion = -1;
    this.mMagicNum = null;
    this.mLength = 0;
    this.mOffset = 0;
    this.mCommandId = -1;
    this.mRequest = null;
    readFromParcel(in);
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flag) {
    out.writeInt(this.mVersion);
    out.writeInt(this.mMagicNum.length);
    out.writeByteArray(this.mMagicNum);
    out.writeInt(this.mCommandId);
    out.writeInt(this.mLength);
    out.writeInt(this.mOffset);
    byte[] bArr = this.mRequest;
    if (bArr == null || bArr.length == 0) {
      out.writeInt(0);
    } else {
      out.writeInt(bArr.length);
      out.writeByteArray(this.mRequest);
    }
  }

  public void readFromParcel(Parcel in) {
    this.mVersion = in.readInt();
    int len = in.readInt();
    if (len > 0) {
      byte[] bArr = new byte[len];
      this.mMagicNum = bArr;
      in.readByteArray(bArr);
    }
    this.mCommandId = in.readInt();
    this.mLength = in.readInt();
    this.mOffset = in.readInt();
    int len2 = in.readInt();
    if (len2 > 0) {
      byte[] bArr2 = new byte[len2];
      this.mRequest = bArr2;
      in.readByteArray(bArr2);
    }
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  private TACommandRequest(
      int _version,
      byte[] _magicNum,
      int _commandId,
      int _length,
      int _offset,
      byte[] _requestPayload) {
    this.mVersion = -1;
    this.mMagicNum = null;
    this.mLength = 0;
    this.mOffset = 0;
    this.mCommandId = -1;
    this.mRequest = null;
    this.mVersion = _version;
    this.mMagicNum = _magicNum;
    this.mCommandId = _commandId;
    this.mLength = _length;
    this.mOffset = _offset;
    this.mRequest = _requestPayload;
  }

  public int getTotalLength() {
    return this.mLength;
  }

  public int getChunkOffset() {
    return this.mOffset;
  }

  public byte[] getPayload() {
    return this.mRequest;
  }

  public List<TACommandRequest> disassemble() {
    List<TACommandRequest> arr = new ArrayList<>();
    byte[] bArr = this.mRequest;
    if (bArr == null) {
      return null;
    }
    if (bArr.length <= 2972) {
      arr.add(this);
      return arr;
    }
    int offset = 0;
    while (true) {
      int i = offset + 2972;
      int i2 = this.mLength;
      if (i < i2) {
        arr.add(
            new TACommandRequest(
                this.mVersion,
                this.mMagicNum,
                this.mCommandId,
                i2,
                offset,
                Arrays.copyOfRange(this.mRequest, offset, offset + 2972)));
        offset += 2972;
      } else {
        arr.add(
            new TACommandRequest(
                this.mVersion,
                this.mMagicNum,
                this.mCommandId,
                i2,
                offset,
                Arrays.copyOfRange(this.mRequest, offset, i2)));
        return arr;
      }
    }
  }

  public void dump() {}
}
