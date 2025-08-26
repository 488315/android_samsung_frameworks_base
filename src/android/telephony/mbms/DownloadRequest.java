package android.telephony.mbms;

import android.annotation.SystemApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class DownloadRequest implements Parcelable {
    public static final Parcelable.Creator<DownloadRequest> CREATOR = new Parcelable.Creator<DownloadRequest>() { // from class: android.telephony.mbms.DownloadRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadRequest createFromParcel(Parcel parcel) {
            return new DownloadRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadRequest[] newArray(int i) {
            return new DownloadRequest[i];
        }
    };
    private static final int CURRENT_VERSION = 1;
    private static final String LOG_TAG = "MbmsDownloadRequest";
    public static final int MAX_APP_INTENT_SIZE = 50000;
    public static final int MAX_DESTINATION_URI_SIZE = 50000;
    private final Uri destinationUri;
    private final String fileServiceId;
    private final String serializedResultIntentForApp;
    private final Uri sourceUri;
    private final int subscriptionId;
    private final int version;

    public static int getMaxAppIntentSize() {
        return 50000;
    }

    public static int getMaxDestinationUriSize() {
        return 50000;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static class SerializationDataContainer implements Externalizable {
        private String appIntent;
        private Uri destination;
        private String fileServiceId;
        private Uri source;
        private int subscriptionId;
        private int version;

        public SerializationDataContainer() {
        }

        SerializationDataContainer(DownloadRequest downloadRequest) {
            this.fileServiceId = downloadRequest.fileServiceId;
            this.source = downloadRequest.sourceUri;
            this.destination = downloadRequest.destinationUri;
            this.subscriptionId = downloadRequest.subscriptionId;
            this.appIntent = downloadRequest.serializedResultIntentForApp;
            this.version = downloadRequest.version;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.write(this.version);
            objectOutput.writeUTF(this.fileServiceId);
            objectOutput.writeUTF(this.source.toString());
            objectOutput.writeUTF(this.destination.toString());
            objectOutput.write(this.subscriptionId);
            objectOutput.writeUTF(this.appIntent);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            this.version = objectInput.read();
            this.fileServiceId = objectInput.readUTF();
            this.source = Uri.parse(objectInput.readUTF());
            this.destination = Uri.parse(objectInput.readUTF());
            this.subscriptionId = objectInput.read();
            this.appIntent = objectInput.readUTF();
        }
    }

    public static class Builder {
        private String appIntent;
        private Uri destination;
        private String fileServiceId;
        private Uri source;
        private int subscriptionId;
        private int version = 1;

        public static Builder fromDownloadRequest(DownloadRequest downloadRequest) {
            Builder subscriptionId = new Builder(downloadRequest.sourceUri, downloadRequest.destinationUri).setServiceId(downloadRequest.fileServiceId).setSubscriptionId(downloadRequest.subscriptionId);
            subscriptionId.appIntent = downloadRequest.serializedResultIntentForApp;
            return subscriptionId;
        }

        public static Builder fromSerializedRequest(byte[] bArr) {
            try {
                SerializationDataContainer serializationDataContainer = (SerializationDataContainer) new ObjectInputStream(new ByteArrayInputStream(bArr)).readObject();
                Builder builder = new Builder(serializationDataContainer.source, serializationDataContainer.destination);
                builder.version = serializationDataContainer.version;
                builder.appIntent = serializationDataContainer.appIntent;
                builder.fileServiceId = serializationDataContainer.fileServiceId;
                builder.subscriptionId = serializationDataContainer.subscriptionId;
                return builder;
            } catch (IOException e) {
                Log.e(DownloadRequest.LOG_TAG, "Got IOException trying to parse opaque data");
                throw new IllegalArgumentException(e);
            } catch (ClassNotFoundException e2) {
                Log.e(DownloadRequest.LOG_TAG, "Got ClassNotFoundException trying to parse opaque data");
                throw new IllegalArgumentException(e2);
            }
        }

        public Builder(Uri uri, Uri uri2) {
            if (uri == null || uri2 == null) {
                throw new IllegalArgumentException("Source and destination URIs must be non-null.");
            }
            this.source = uri;
            this.destination = uri2;
        }

        public Builder setServiceInfo(FileServiceInfo fileServiceInfo) {
            this.fileServiceId = fileServiceInfo.getServiceId();
            return this;
        }

        @SystemApi
        public Builder setServiceId(String str) {
            this.fileServiceId = str;
            return this;
        }

        public Builder setSubscriptionId(int i) {
            this.subscriptionId = i;
            return this;
        }

        public Builder setAppIntent(Intent intent) {
            String uri = intent.toUri(0);
            this.appIntent = uri;
            if (uri.length() <= 50000) {
                return this;
            }
            throw new IllegalArgumentException("App intent must not exceed length 50000");
        }

        public DownloadRequest build() {
            return new DownloadRequest(this.fileServiceId, this.source, this.destination, this.subscriptionId, this.appIntent, this.version);
        }
    }

    private DownloadRequest(String str, Uri uri, Uri uri2, int i, String str2, int i2) {
        this.fileServiceId = str;
        this.sourceUri = uri;
        this.subscriptionId = i;
        this.destinationUri = uri2;
        this.serializedResultIntentForApp = str2;
        this.version = i2;
    }

    private DownloadRequest(Parcel parcel) {
        this.fileServiceId = parcel.readString();
        this.sourceUri = (Uri) parcel.readParcelable(getClass().getClassLoader(), Uri.class);
        this.destinationUri = (Uri) parcel.readParcelable(getClass().getClassLoader(), Uri.class);
        this.subscriptionId = parcel.readInt();
        this.serializedResultIntentForApp = parcel.readString();
        this.version = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fileServiceId);
        parcel.writeParcelable(this.sourceUri, i);
        parcel.writeParcelable(this.destinationUri, i);
        parcel.writeInt(this.subscriptionId);
        parcel.writeString(this.serializedResultIntentForApp);
        parcel.writeInt(this.version);
    }

    public String getFileServiceId() {
        return this.fileServiceId;
    }

    public Uri getSourceUri() {
        return this.sourceUri;
    }

    public Uri getDestinationUri() {
        return this.destinationUri;
    }

    public int getSubscriptionId() {
        return this.subscriptionId;
    }

    public Intent getIntentForApp() {
        try {
            return Intent.parseUri(this.serializedResultIntentForApp, 0);
        } catch (URISyntaxException unused) {
            return null;
        }
    }

    public byte[] toByteArray() throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(new SerializationDataContainer(this));
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            Log.e(LOG_TAG, "Got IOException trying to serialize opaque data");
            return null;
        }
    }

    public int getVersion() {
        return this.version;
    }

    public String getHash() throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            if (this.version >= 1) {
                messageDigest.update(this.sourceUri.toString().getBytes(StandardCharsets.UTF_8));
                messageDigest.update(this.destinationUri.toString().getBytes(StandardCharsets.UTF_8));
                String str = this.serializedResultIntentForApp;
                if (str != null) {
                    messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
                }
            }
            return Base64.encodeToString(messageDigest.digest(), 10);
        } catch (NoSuchAlgorithmException unused) {
            throw new RuntimeException("Could not get sha256 hash object");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DownloadRequest)) {
            return false;
        }
        DownloadRequest downloadRequest = (DownloadRequest) obj;
        return this.subscriptionId == downloadRequest.subscriptionId && this.version == downloadRequest.version && Objects.equals(this.fileServiceId, downloadRequest.fileServiceId) && Objects.equals(this.sourceUri, downloadRequest.sourceUri) && Objects.equals(this.destinationUri, downloadRequest.destinationUri) && Objects.equals(this.serializedResultIntentForApp, downloadRequest.serializedResultIntentForApp);
    }

    public int hashCode() {
        return Objects.hash(this.fileServiceId, this.sourceUri, this.destinationUri, Integer.valueOf(this.subscriptionId), this.serializedResultIntentForApp, Integer.valueOf(this.version));
    }
}
