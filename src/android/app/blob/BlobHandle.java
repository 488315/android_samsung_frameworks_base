package android.app.blob;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.IndentingPrintWriter;
import com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public final class BlobHandle implements Parcelable {
    public static final String ALGO_SHA_256 = "SHA-256";
    private static final int LIMIT_BLOB_LABEL_LENGTH = 100;
    private static final int LIMIT_BLOB_TAG_LENGTH = 128;
    public final String algorithm;
    public final byte[] digest;
    public final long expiryTimeMillis;
    public final CharSequence label;
    public final String tag;
    private static final String[] SUPPORTED_ALGOS = {"SHA-256"};
    public static final Parcelable.Creator<BlobHandle> CREATOR = new Parcelable.Creator<BlobHandle>() { // from class: android.app.blob.BlobHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlobHandle createFromParcel(Parcel parcel) {
            return new BlobHandle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BlobHandle[] newArray(int i) {
            return new BlobHandle[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BlobHandle(String str, byte[] bArr, CharSequence charSequence, long j, String str2) {
        this.algorithm = str;
        this.digest = bArr;
        this.label = charSequence;
        this.expiryTimeMillis = j;
        this.tag = str2;
    }

    private BlobHandle(Parcel parcel) {
        this.algorithm = parcel.readString();
        this.digest = parcel.createByteArray();
        this.label = parcel.readCharSequence();
        this.expiryTimeMillis = parcel.readLong();
        this.tag = parcel.readString();
    }

    public static BlobHandle create(String str, byte[] bArr, CharSequence charSequence, long j, String str2) {
        BlobHandle blobHandle = new BlobHandle(str, bArr, charSequence, j, str2);
        blobHandle.assertIsValid();
        return blobHandle;
    }

    public static BlobHandle createWithSha256(byte[] bArr, CharSequence charSequence, long j, String str) {
        return create("SHA-256", bArr, charSequence, j, str);
    }

    public byte[] getSha256Digest() {
        return this.digest;
    }

    public CharSequence getLabel() {
        return this.label;
    }

    public long getExpiryTimeMillis() {
        return this.expiryTimeMillis;
    }

    public String getTag() {
        return this.tag;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.algorithm);
        parcel.writeByteArray(this.digest);
        parcel.writeCharSequence(this.label);
        parcel.writeLong(this.expiryTimeMillis);
        parcel.writeString(this.tag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof BlobHandle)) {
            BlobHandle blobHandle = (BlobHandle) obj;
            if (this.algorithm.equals(blobHandle.algorithm) && Arrays.equals(this.digest, blobHandle.digest) && this.label.toString().equals(blobHandle.label.toString()) && this.expiryTimeMillis == blobHandle.expiryTimeMillis && this.tag.equals(blobHandle.tag)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.algorithm, Integer.valueOf(Arrays.hashCode(this.digest)), this.label, Long.valueOf(this.expiryTimeMillis), this.tag);
    }

    public void dump(IndentingPrintWriter indentingPrintWriter, boolean z) {
        if (z) {
            indentingPrintWriter.println("algo: " + this.algorithm);
            StringBuilder sb = new StringBuilder("digest: ");
            sb.append(z ? encodeDigest(this.digest) : safeDigest(this.digest));
            indentingPrintWriter.println(sb.toString());
            indentingPrintWriter.println("label: " + ((Object) this.label));
            indentingPrintWriter.println("expiryMs: " + this.expiryTimeMillis);
            indentingPrintWriter.println("tag: " + this.tag);
            return;
        }
        indentingPrintWriter.println(toString());
    }

    public void assertIsValid() {
        Preconditions.checkArgumentIsSupported(SUPPORTED_ALGOS, this.algorithm);
        Preconditions.checkByteArrayNotEmpty(this.digest, CMSAttributeTableGenerator.DIGEST);
        Preconditions.checkStringNotEmpty(this.label, "label must not be null");
        Preconditions.checkArgument(this.label.length() <= 100, "label too long");
        Preconditions.checkArgumentNonnegative(this.expiryTimeMillis, "expiryTimeMillis must not be negative");
        Preconditions.checkStringNotEmpty(this.tag, "tag must not be null");
        Preconditions.checkArgument(this.tag.length() <= 128, "tag too long");
    }

    public String toString() {
        return "BlobHandle {algo:" + this.algorithm + ",digest:" + safeDigest(this.digest) + ",label:" + ((Object) this.label) + ",expiryMs:" + this.expiryTimeMillis + ",tag:" + this.tag + "}";
    }

    public static String safeDigest(byte[] bArr) {
        String encodeDigest = encodeDigest(bArr);
        return encodeDigest.substring(0, 2) + ".." + encodeDigest.substring(encodeDigest.length() - 2);
    }

    private static String encodeDigest(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    public boolean isExpired() {
        long j = this.expiryTimeMillis;
        return j != 0 && j < System.currentTimeMillis();
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IOException {
        XmlUtils.writeStringAttribute(xmlSerializer, XmlTags.ATTR_ALGO, this.algorithm);
        XmlUtils.writeByteArrayAttribute(xmlSerializer, XmlTags.ATTR_DIGEST, this.digest);
        XmlUtils.writeStringAttribute(xmlSerializer, XmlTags.ATTR_LABEL, this.label);
        XmlUtils.writeLongAttribute(xmlSerializer, XmlTags.ATTR_EXPIRY_TIME, this.expiryTimeMillis);
        XmlUtils.writeStringAttribute(xmlSerializer, XmlTags.ATTR_TAG, this.tag);
    }

    public static BlobHandle createFromXml(XmlPullParser xmlPullParser) throws IOException {
        return create(XmlUtils.readStringAttribute(xmlPullParser, XmlTags.ATTR_ALGO), XmlUtils.readByteArrayAttribute(xmlPullParser, XmlTags.ATTR_DIGEST), XmlUtils.readStringAttribute(xmlPullParser, XmlTags.ATTR_LABEL), XmlUtils.readLongAttribute(xmlPullParser, XmlTags.ATTR_EXPIRY_TIME), XmlUtils.readStringAttribute(xmlPullParser, XmlTags.ATTR_TAG));
    }
}
