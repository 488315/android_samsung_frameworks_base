package android.net;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class InterfaceConfiguration implements Parcelable {
    private static final String FLAG_DOWN = "down";
    private static final String FLAG_UP = "up";
    private LinkAddress mAddr;
    private HashSet<String> mFlags = new HashSet<>();
    private String mHwAddr;
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Parcelable.Creator<InterfaceConfiguration> CREATOR = new Parcelable.Creator<InterfaceConfiguration>() { // from class: android.net.InterfaceConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InterfaceConfiguration createFromParcel(Parcel parcel) {
            InterfaceConfiguration interfaceConfiguration = new InterfaceConfiguration();
            interfaceConfiguration.mHwAddr = parcel.readString();
            if (parcel.readByte() == 1) {
                interfaceConfiguration.mAddr = (LinkAddress) parcel.readParcelable(null);
            }
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                interfaceConfiguration.mFlags.add(parcel.readString());
            }
            return interfaceConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InterfaceConfiguration[] newArray(int i) {
            return new InterfaceConfiguration[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "mHwAddr=" + this.mHwAddr + " mAddr=" + String.valueOf(this.mAddr) + " mFlags=" + getFlags();
    }

    public Iterable<String> getFlags() {
        return this.mFlags;
    }

    public boolean hasFlag(String str) {
        validateFlag(str);
        return this.mFlags.contains(str);
    }

    public void clearFlag(String str) {
        validateFlag(str);
        this.mFlags.remove(str);
    }

    public void setFlag(String str) {
        validateFlag(str);
        this.mFlags.add(str);
    }

    public void setInterfaceUp() {
        this.mFlags.remove("down");
        this.mFlags.add("up");
    }

    public void setInterfaceDown() {
        this.mFlags.remove("up");
        this.mFlags.add("down");
    }

    public void ignoreInterfaceUpDownStatus() {
        this.mFlags.remove("up");
        this.mFlags.remove("down");
    }

    public LinkAddress getLinkAddress() {
        return this.mAddr;
    }

    public void setLinkAddress(LinkAddress linkAddress) {
        this.mAddr = linkAddress;
    }

    public String getHardwareAddress() {
        return this.mHwAddr;
    }

    public void setHardwareAddress(String str) {
        this.mHwAddr = str;
    }

    public boolean isActive() {
        try {
            if (isUp()) {
                for (byte b : this.mAddr.getAddress().getAddress()) {
                    if (b != 0) {
                        return true;
                    }
                }
            }
        } catch (NullPointerException unused) {
        }
        return false;
    }

    public boolean isUp() {
        return hasFlag("up");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mHwAddr);
        if (this.mAddr != null) {
            parcel.writeByte((byte) 1);
            parcel.writeParcelable(this.mAddr, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mFlags.size());
        Iterator<String> it = this.mFlags.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }

    private static void validateFlag(String str) {
        if (str.indexOf(32) < 0) {
            return;
        }
        throw new IllegalArgumentException("flag contains space: " + str);
    }
}
