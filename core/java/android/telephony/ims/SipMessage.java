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
    public static final Parcelable.Creator<SipMessage> CREATOR =
            new Parcelable.Creator<SipMessage>() { // from class: android.telephony.ims.SipMessage.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SipMessage createFromParcel(Parcel source) {
                    return new SipMessage(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SipMessage[] newArray(int size) {
                    return new SipMessage[size];
                }
            };

    public SipMessage(String startLine, String headerSection, byte[] content) {
        Objects.requireNonNull(startLine, "Required parameter is null: startLine");
        Objects.requireNonNull(headerSection, "Required parameter is null: headerSection");
        Objects.requireNonNull(content, "Required parameter is null: content");
        this.mStartLine = startLine;
        this.mHeaderSection = headerSection;
        this.mContent = content;
        this.mViaBranchParam = SipMessageParsingUtils.getTransactionId(this.mHeaderSection);
        if (TextUtils.isEmpty(this.mViaBranchParam)) {
            throw new IllegalArgumentException(
                    "header section MUST contain a branch parameter inside of the Via header.");
        }
        this.mCallIdParam = SipMessageParsingUtils.getCallId(this.mHeaderSection);
    }

    private SipMessage(Parcel source) {
        this.mStartLine = source.readString();
        this.mHeaderSection = source.readString();
        this.mContent = new byte[source.readInt()];
        source.readByteArray(this.mContent);
        this.mViaBranchParam = source.readString();
        this.mCallIdParam = source.readString();
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mStartLine);
        dest.writeString(this.mHeaderSection);
        dest.writeInt(this.mContent.length);
        dest.writeByteArray(this.mContent);
        dest.writeString(this.mViaBranchParam);
        dest.writeString(this.mCallIdParam);
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("StartLine: [");
        if (IS_DEBUGGING) {
            b.append(this.mStartLine);
        } else {
            b.append(sanitizeStartLineRequest(this.mStartLine));
        }
        b.append("], Header: [");
        if (IS_DEBUGGING) {
            b.append(this.mHeaderSection);
        } else {
            b.append("***");
        }
        b.append("], Content: ");
        b.append(getContent().length == 0 ? "[NONE]" : "[NOT SHOWN]");
        return b.toString();
    }

    private String sanitizeStartLineRequest(String startLine) {
        if (!SipMessageParsingUtils.isSipRequest(startLine)) {
            return startLine;
        }
        String[] splitLine = startLine.split(" ");
        return splitLine[0] + " <Request-URI> " + splitLine[2];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SipMessage that = (SipMessage) o;
        if (this.mStartLine.equals(that.mStartLine)
                && this.mHeaderSection.equals(that.mHeaderSection)
                && Arrays.equals(this.mContent, that.mContent)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = Objects.hash(this.mStartLine, this.mHeaderSection);
        return (result * 31) + Arrays.hashCode(this.mContent);
    }

    public byte[] toEncodedMessage() {
        byte[] header =
                (this.mStartLine + this.mHeaderSection + CRLF).getBytes(StandardCharsets.UTF_8);
        byte[] sipMessage = new byte[header.length + this.mContent.length];
        System.arraycopy(header, 0, sipMessage, 0, header.length);
        System.arraycopy(this.mContent, 0, sipMessage, header.length, this.mContent.length);
        return sipMessage;
    }
}
