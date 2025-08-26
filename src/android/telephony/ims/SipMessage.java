package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.telephony.SipMessageParsingUtils;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SipMessage implements Parcelable {
    private static final String CRLF = "\r\n";
    private final String mCallIdParam;
    private final byte[] mContent;
    private final String mHeaderSection;
    private final String mStartLine;
    private final String mViaBranchParam;
    private static final boolean IS_DEBUGGING = Build.IS_ENG;
    public static final Parcelable.Creator<SipMessage> CREATOR = new Parcelable.Creator<SipMessage>() { // from class: android.telephony.ims.SipMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipMessage createFromParcel(Parcel parcel) {
            return new SipMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipMessage[] newArray(int i) {
            return new SipMessage[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SipMessage(String str, String str2, byte[] bArr) {
        Objects.requireNonNull(str, "Required parameter is null: startLine");
        Objects.requireNonNull(str2, "Required parameter is null: headerSection");
        Objects.requireNonNull(bArr, "Required parameter is null: content");
        this.mStartLine = str;
        this.mHeaderSection = str2;
        this.mContent = bArr;
        String transactionId = SipMessageParsingUtils.getTransactionId(str2);
        this.mViaBranchParam = transactionId;
        if (TextUtils.isEmpty(transactionId)) {
            throw new IllegalArgumentException("header section MUST contain a branch parameter inside of the Via header.");
        }
        this.mCallIdParam = SipMessageParsingUtils.getCallId(str2);
    }

    private SipMessage(Parcel parcel) {
        this.mStartLine = parcel.readString();
        this.mHeaderSection = parcel.readString();
        byte[] bArr = new byte[parcel.readInt()];
        this.mContent = bArr;
        parcel.readByteArray(bArr);
        this.mViaBranchParam = parcel.readString();
        this.mCallIdParam = parcel.readString();
    }

    public String getStartLine() {
        return this.mStartLine;
    }

    public String getHeaderSection() {
        return this.mHeaderSection;
    }

    public byte[] getContent() {
        return this.mContent;
    }

    public String getViaBranchParameter() {
        return this.mViaBranchParam;
    }

    public String getCallIdParameter() {
        return this.mCallIdParam;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mStartLine);
        parcel.writeString(this.mHeaderSection);
        parcel.writeInt(this.mContent.length);
        parcel.writeByteArray(this.mContent);
        parcel.writeString(this.mViaBranchParam);
        parcel.writeString(this.mCallIdParam);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StartLine: [");
        boolean z = IS_DEBUGGING;
        if (z) {
            sb.append(this.mStartLine);
        } else {
            sb.append(sanitizeStartLineRequest(this.mStartLine));
        }
        sb.append("], Header: [");
        if (z) {
            sb.append(this.mHeaderSection);
        } else {
            sb.append("***");
        }
        sb.append("], Content: ");
        sb.append(getContent().length == 0 ? "[NONE]" : "[NOT SHOWN]");
        return sb.toString();
    }

    private String sanitizeStartLineRequest(String str) {
        if (!SipMessageParsingUtils.isSipRequest(str)) {
            return str;
        }
        String[] strArrSplit = str.split(" ");
        return strArrSplit[0] + " <Request-URI> " + strArrSplit[2];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SipMessage sipMessage = (SipMessage) obj;
            if (this.mStartLine.equals(sipMessage.mStartLine) && this.mHeaderSection.equals(sipMessage.mHeaderSection) && Arrays.equals(this.mContent, sipMessage.mContent)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(this.mStartLine, this.mHeaderSection) * 31) + Arrays.hashCode(this.mContent);
    }

    public byte[] toEncodedMessage() {
        byte[] bytes = (this.mStartLine + this.mHeaderSection + CRLF).getBytes(StandardCharsets.UTF_8);
        byte[] bArr = new byte[bytes.length + this.mContent.length];
        System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        byte[] bArr2 = this.mContent;
        System.arraycopy(bArr2, 0, bArr, bytes.length, bArr2.length);
        return bArr;
    }
}
