package android.view;

import android.annotation.NonNull;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IScrollCaptureConnection;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ScrollCaptureResponse implements Parcelable {
    public static final Parcelable.Creator<ScrollCaptureResponse> CREATOR = new Parcelable.Creator<ScrollCaptureResponse>() { // from class: android.view.ScrollCaptureResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScrollCaptureResponse[] newArray(int i) {
            return new ScrollCaptureResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScrollCaptureResponse createFromParcel(Parcel parcel) {
            return new ScrollCaptureResponse(parcel);
        }
    };
    private Rect mBoundsInWindow;
    private IScrollCaptureConnection mConnection;
    private String mDescription;
    private ArrayList<String> mMessages;
    private String mPackageName;
    private Rect mWindowBounds;
    private String mWindowTitle;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isConnected() {
        IScrollCaptureConnection iScrollCaptureConnection = this.mConnection;
        return iScrollCaptureConnection != null && iScrollCaptureConnection.asBinder().isBinderAlive();
    }

    public void close() {
        IScrollCaptureConnection iScrollCaptureConnection = this.mConnection;
        if (iScrollCaptureConnection != null) {
            try {
                iScrollCaptureConnection.close();
            } catch (RemoteException unused) {
            }
            this.mConnection = null;
        }
    }

    ScrollCaptureResponse(String str, IScrollCaptureConnection iScrollCaptureConnection, Rect rect, Rect rect2, String str2, String str3, ArrayList<String> arrayList) {
        this.mDescription = "";
        this.mConnection = null;
        this.mWindowBounds = null;
        this.mBoundsInWindow = null;
        this.mWindowTitle = null;
        this.mPackageName = null;
        this.mMessages = new ArrayList<>();
        this.mDescription = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mConnection = iScrollCaptureConnection;
        this.mWindowBounds = rect;
        this.mBoundsInWindow = rect2;
        this.mWindowTitle = str2;
        this.mPackageName = str3;
        this.mMessages = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
    }

    public String getDescription() {
        return this.mDescription;
    }

    public IScrollCaptureConnection getConnection() {
        return this.mConnection;
    }

    public Rect getWindowBounds() {
        return this.mWindowBounds;
    }

    public Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    public String getWindowTitle() {
        return this.mWindowTitle;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public ArrayList<String> getMessages() {
        return this.mMessages;
    }

    public String toString() {
        return "ScrollCaptureResponse { description = " + this.mDescription + ", connection = " + this.mConnection + ", windowBounds = " + this.mWindowBounds + ", boundsInWindow = " + this.mBoundsInWindow + ", windowTitle = " + this.mWindowTitle + ", packageName = " + this.mPackageName + ", messages = " + this.mMessages + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mConnection != null ? (byte) 2 : (byte) 0;
        if (this.mWindowBounds != null) {
            b = (byte) (b | 4);
        }
        if (this.mBoundsInWindow != null) {
            b = (byte) (b | 8);
        }
        if (this.mWindowTitle != null) {
            b = (byte) (b | 16);
        }
        if (this.mPackageName != null) {
            b = (byte) (b | 32);
        }
        parcel.writeByte(b);
        parcel.writeString(this.mDescription);
        IScrollCaptureConnection iScrollCaptureConnection = this.mConnection;
        if (iScrollCaptureConnection != null) {
            parcel.writeStrongInterface(iScrollCaptureConnection);
        }
        Rect rect = this.mWindowBounds;
        if (rect != null) {
            parcel.writeTypedObject(rect, i);
        }
        Rect rect2 = this.mBoundsInWindow;
        if (rect2 != null) {
            parcel.writeTypedObject(rect2, i);
        }
        String str = this.mWindowTitle;
        if (str != null) {
            parcel.writeString(str);
        }
        String str2 = this.mPackageName;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        parcel.writeStringList(this.mMessages);
    }

    protected ScrollCaptureResponse(Parcel parcel) {
        this.mDescription = "";
        this.mConnection = null;
        this.mWindowBounds = null;
        this.mBoundsInWindow = null;
        this.mWindowTitle = null;
        this.mPackageName = null;
        this.mMessages = new ArrayList<>();
        byte b = parcel.readByte();
        String string = parcel.readString();
        IScrollCaptureConnection iScrollCaptureConnectionAsInterface = (b & 2) == 0 ? null : IScrollCaptureConnection.Stub.asInterface(parcel.readStrongBinder());
        Rect rect = (b & 4) == 0 ? null : (Rect) parcel.readTypedObject(Rect.CREATOR);
        Rect rect2 = (b & 8) == 0 ? null : (Rect) parcel.readTypedObject(Rect.CREATOR);
        String string2 = (b & 16) == 0 ? null : parcel.readString();
        String string3 = (b & 32) == 0 ? null : parcel.readString();
        ArrayList<String> arrayList = new ArrayList<>();
        parcel.readStringList(arrayList);
        this.mDescription = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mConnection = iScrollCaptureConnectionAsInterface;
        this.mWindowBounds = rect;
        this.mBoundsInWindow = rect2;
        this.mWindowTitle = string2;
        this.mPackageName = string3;
        this.mMessages = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
    }

    public static class Builder {
        private Rect mBoundsInWindow;
        private long mBuilderFieldsSet = 0;
        private IScrollCaptureConnection mConnection;
        private String mDescription;
        private ArrayList<String> mMessages;
        private String mPackageName;
        private Rect mWindowBounds;
        private String mWindowTitle;

        public Builder setDescription(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mDescription = str;
            return this;
        }

        public Builder setConnection(IScrollCaptureConnection iScrollCaptureConnection) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mConnection = iScrollCaptureConnection;
            return this;
        }

        public Builder setWindowBounds(Rect rect) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mWindowBounds = rect;
            return this;
        }

        public Builder setBoundsInWindow(Rect rect) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mBoundsInWindow = rect;
            return this;
        }

        public Builder setWindowTitle(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mWindowTitle = str;
            return this;
        }

        public Builder setPackageName(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mPackageName = str;
            return this;
        }

        public Builder setMessages(ArrayList<String> arrayList) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mMessages = arrayList;
            return this;
        }

        public Builder addMessage(String str) {
            if (this.mMessages == null) {
                setMessages(new ArrayList<>());
            }
            this.mMessages.add(str);
            return this;
        }

        public ScrollCaptureResponse build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 128;
            this.mBuilderFieldsSet = j;
            if ((1 & j) == 0) {
                this.mDescription = "";
            }
            if ((2 & j) == 0) {
                this.mConnection = null;
            }
            if ((4 & j) == 0) {
                this.mWindowBounds = null;
            }
            if ((8 & j) == 0) {
                this.mBoundsInWindow = null;
            }
            if ((16 & j) == 0) {
                this.mWindowTitle = null;
            }
            if ((32 & j) == 0) {
                this.mPackageName = null;
            }
            if ((j & 64) == 0) {
                this.mMessages = new ArrayList<>();
            }
            return new ScrollCaptureResponse(this.mDescription, this.mConnection, this.mWindowBounds, this.mBoundsInWindow, this.mWindowTitle, this.mPackageName, this.mMessages);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 128) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
