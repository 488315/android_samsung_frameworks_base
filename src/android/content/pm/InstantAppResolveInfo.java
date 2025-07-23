package android.content.pm;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@SystemApi
/* loaded from: classes.dex */
public final class InstantAppResolveInfo implements Parcelable {
    private static final String SHA_ALGORITHM = "SHA-256";
    private final InstantAppDigest mDigest;
    private final Bundle mExtras;
    private final List<InstantAppIntentFilter> mFilters;
    private final String mPackageName;
    private final boolean mShouldLetInstallerDecide;
    private final long mVersionCode;
    private static final byte[] EMPTY_DIGEST = new byte[0];
    public static final Parcelable.Creator<InstantAppResolveInfo> CREATOR = new Parcelable.Creator<InstantAppResolveInfo>() { // from class: android.content.pm.InstantAppResolveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstantAppResolveInfo createFromParcel(Parcel parcel) {
            return new InstantAppResolveInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstantAppResolveInfo[] newArray(int i) {
            return new InstantAppResolveInfo[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InstantAppResolveInfo(InstantAppDigest instantAppDigest, String str, List<InstantAppIntentFilter> list, int i) {
        this(instantAppDigest, str, list, i, null);
    }

    public InstantAppResolveInfo(InstantAppDigest instantAppDigest, String str, List<InstantAppIntentFilter> list, long j, Bundle bundle) {
        this(instantAppDigest, str, list, j, bundle, false);
    }

    public InstantAppResolveInfo(String str, String str2, List<InstantAppIntentFilter> list) {
        this(new InstantAppDigest(str), str2, list, -1L, null);
    }

    public InstantAppResolveInfo(Bundle bundle) {
        this(InstantAppDigest.UNDEFINED, null, null, -1L, bundle, true);
    }

    private InstantAppResolveInfo(InstantAppDigest instantAppDigest, String str, List<InstantAppIntentFilter> list, long j, Bundle bundle, boolean z) {
        if ((str == null && list != null && list.size() != 0) || (str != null && (list == null || list.size() == 0))) {
            throw new IllegalArgumentException();
        }
        this.mDigest = instantAppDigest;
        if (list != null) {
            ArrayList arrayList = new ArrayList(list.size());
            this.mFilters = arrayList;
            arrayList.addAll(list);
        } else {
            this.mFilters = null;
        }
        this.mPackageName = str;
        this.mVersionCode = j;
        this.mExtras = bundle;
        this.mShouldLetInstallerDecide = z;
    }

    InstantAppResolveInfo(Parcel parcel) {
        boolean readBoolean = parcel.readBoolean();
        this.mShouldLetInstallerDecide = readBoolean;
        this.mExtras = parcel.readBundle();
        if (readBoolean) {
            this.mDigest = InstantAppDigest.UNDEFINED;
            this.mPackageName = null;
            this.mFilters = Collections.EMPTY_LIST;
            this.mVersionCode = -1L;
            return;
        }
        this.mDigest = (InstantAppDigest) parcel.readParcelable(null, InstantAppDigest.class);
        this.mPackageName = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.mFilters = arrayList;
        parcel.readTypedList(arrayList, InstantAppIntentFilter.CREATOR);
        this.mVersionCode = parcel.readLong();
    }

    public boolean shouldLetInstallerDecide() {
        return this.mShouldLetInstallerDecide;
    }

    public byte[] getDigestBytes() {
        return this.mDigest.mDigestBytes.length > 0 ? this.mDigest.getDigestBytes()[0] : EMPTY_DIGEST;
    }

    public int getDigestPrefix() {
        return this.mDigest.getDigestPrefix()[0];
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public List<InstantAppIntentFilter> getIntentFilters() {
        return this.mFilters;
    }

    @Deprecated
    public int getVersionCode() {
        return (int) this.mVersionCode;
    }

    public long getLongVersionCode() {
        return this.mVersionCode;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mShouldLetInstallerDecide);
        parcel.writeBundle(this.mExtras);
        if (this.mShouldLetInstallerDecide) {
            return;
        }
        parcel.writeParcelable(this.mDigest, i);
        parcel.writeString(this.mPackageName);
        parcel.writeTypedList(this.mFilters);
        parcel.writeLong(this.mVersionCode);
    }

    @SystemApi
    public static final class InstantAppDigest implements Parcelable {
        public static final Parcelable.Creator<InstantAppDigest> CREATOR;
        static final int DIGEST_MASK = -4096;
        public static final InstantAppDigest UNDEFINED = new InstantAppDigest(new byte[0][], new int[0]);
        private static Random sRandom;
        private final byte[][] mDigestBytes;
        private final int[] mDigestPrefix;
        private int[] mDigestPrefixSecure;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        static {
            sRandom = null;
            try {
                sRandom = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException unused) {
                sRandom = new Random();
            }
            CREATOR = new Parcelable.Creator<InstantAppDigest>() { // from class: android.content.pm.InstantAppResolveInfo.InstantAppDigest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public InstantAppDigest createFromParcel(Parcel parcel) {
                    if (parcel.readBoolean()) {
                        return InstantAppDigest.UNDEFINED;
                    }
                    return new InstantAppDigest(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public InstantAppDigest[] newArray(int i) {
                    return new InstantAppDigest[i];
                }
            };
        }

        public InstantAppDigest(String str) {
            this(str, -1);
        }

        public InstantAppDigest(String str, int i) {
            if (str == null) {
                throw new IllegalArgumentException();
            }
            byte[][] generateDigest = generateDigest(str.toLowerCase(Locale.ENGLISH), i);
            this.mDigestBytes = generateDigest;
            this.mDigestPrefix = new int[generateDigest.length];
            int i2 = 0;
            while (true) {
                byte[][] bArr = this.mDigestBytes;
                if (i2 >= bArr.length) {
                    return;
                }
                int[] iArr = this.mDigestPrefix;
                byte[] bArr2 = bArr[i2];
                iArr[i2] = ((bArr2[3] & 255) | ((bArr2[0] & 255) << 24) | ((bArr2[1] & 255) << 16) | ((bArr2[2] & 255) << 8)) & DIGEST_MASK;
                i2++;
            }
        }

        private InstantAppDigest(byte[][] bArr, int[] iArr) {
            this.mDigestPrefix = iArr;
            this.mDigestBytes = bArr;
        }

        private static byte[][] generateDigest(String str, int i) {
            ArrayList arrayList = new ArrayList();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                if (i <= 0) {
                    arrayList.add(messageDigest.digest(str.getBytes()));
                } else {
                    int lastIndexOf = str.lastIndexOf(46, str.lastIndexOf(46) - 1);
                    if (lastIndexOf < 0) {
                        arrayList.add(messageDigest.digest(str.getBytes()));
                    } else {
                        arrayList.add(messageDigest.digest(str.substring(lastIndexOf + 1, str.length()).getBytes()));
                        for (int i2 = 1; lastIndexOf >= 0 && i2 < i; i2++) {
                            lastIndexOf = str.lastIndexOf(46, lastIndexOf - 1);
                            arrayList.add(messageDigest.digest(str.substring(lastIndexOf + 1, str.length()).getBytes()));
                        }
                    }
                }
                return (byte[][]) arrayList.toArray(new byte[arrayList.size()][]);
            } catch (NoSuchAlgorithmException unused) {
                throw new IllegalStateException("could not find digest algorithm");
            }
        }

        InstantAppDigest(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                this.mDigestBytes = null;
            } else {
                this.mDigestBytes = new byte[readInt][];
                for (int i = 0; i < readInt; i++) {
                    this.mDigestBytes[i] = parcel.createByteArray();
                }
            }
            this.mDigestPrefix = parcel.createIntArray();
            this.mDigestPrefixSecure = parcel.createIntArray();
        }

        public byte[][] getDigestBytes() {
            return this.mDigestBytes;
        }

        public int[] getDigestPrefix() {
            return this.mDigestPrefix;
        }

        public int[] getDigestPrefixSecure() {
            if (this == UNDEFINED) {
                return getDigestPrefix();
            }
            if (this.mDigestPrefixSecure == null) {
                int length = getDigestPrefix().length;
                int nextInt = length + 10 + sRandom.nextInt(10);
                this.mDigestPrefixSecure = Arrays.copyOf(getDigestPrefix(), nextInt);
                while (length < nextInt) {
                    this.mDigestPrefixSecure[length] = sRandom.nextInt() & DIGEST_MASK;
                    length++;
                }
                Arrays.sort(this.mDigestPrefixSecure);
            }
            return this.mDigestPrefixSecure;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            int i2 = 0;
            boolean z = this == UNDEFINED;
            parcel.writeBoolean(z);
            if (z) {
                return;
            }
            byte[][] bArr = this.mDigestBytes;
            if (bArr == null) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(bArr.length);
                while (true) {
                    byte[][] bArr2 = this.mDigestBytes;
                    if (i2 >= bArr2.length) {
                        break;
                    }
                    parcel.writeByteArray(bArr2[i2]);
                    i2++;
                }
            }
            parcel.writeIntArray(this.mDigestPrefix);
            parcel.writeIntArray(this.mDigestPrefixSecure);
        }
    }
}
