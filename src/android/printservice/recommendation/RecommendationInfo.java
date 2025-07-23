package android.printservice.recommendation;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class RecommendationInfo implements Parcelable {
    public static final Parcelable.Creator<RecommendationInfo> CREATOR = new Parcelable.Creator<RecommendationInfo>() { // from class: android.printservice.recommendation.RecommendationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecommendationInfo createFromParcel(Parcel parcel) {
            return new RecommendationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecommendationInfo[] newArray(int i) {
            return new RecommendationInfo[i];
        }
    };
    private final List<InetAddress> mDiscoveredPrinters;
    private final CharSequence mName;
    private final CharSequence mPackageName;
    private final boolean mRecommendsMultiVendorService;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RecommendationInfo(CharSequence charSequence, CharSequence charSequence2, List<InetAddress> list, boolean z) {
        this.mPackageName = Preconditions.checkStringNotEmpty(charSequence);
        this.mName = Preconditions.checkStringNotEmpty(charSequence2);
        this.mDiscoveredPrinters = (List) Preconditions.checkCollectionElementsNotNull(list, "discoveredPrinters");
        this.mRecommendsMultiVendorService = z;
    }

    @Deprecated
    public RecommendationInfo(CharSequence charSequence, CharSequence charSequence2, int i, boolean z) {
        throw new IllegalArgumentException("This constructor has been deprecated");
    }

    private static ArrayList<InetAddress> readDiscoveredPrinters(Parcel parcel) {
        int readInt = parcel.readInt();
        ArrayList<InetAddress> arrayList = new ArrayList<>(readInt);
        for (int i = 0; i < readInt; i++) {
            try {
                arrayList.add(InetAddress.getByAddress(parcel.readBlob()));
            } catch (UnknownHostException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return arrayList;
    }

    private RecommendationInfo(Parcel parcel) {
        this(parcel.readCharSequence(), parcel.readCharSequence(), readDiscoveredPrinters(parcel), parcel.readByte() != 0);
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public boolean recommendsMultiVendorService() {
        return this.mRecommendsMultiVendorService;
    }

    public List<InetAddress> getDiscoveredPrinters() {
        return this.mDiscoveredPrinters;
    }

    public int getNumDiscoveredPrinters() {
        return this.mDiscoveredPrinters.size();
    }

    public CharSequence getName() {
        return this.mName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mPackageName);
        parcel.writeCharSequence(this.mName);
        parcel.writeInt(this.mDiscoveredPrinters.size());
        Iterator<InetAddress> it = this.mDiscoveredPrinters.iterator();
        while (it.hasNext()) {
            parcel.writeBlob(it.next().getAddress());
        }
        parcel.writeByte(this.mRecommendsMultiVendorService ? (byte) 1 : (byte) 0);
    }
}
