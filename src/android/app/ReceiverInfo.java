package android.app;

import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.CompatibilityInfo;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ReceiverInfo implements Parcelable {
    public static final Parcelable.Creator<ReceiverInfo> CREATOR = new Parcelable.Creator<ReceiverInfo>() { // from class: android.app.ReceiverInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReceiverInfo createFromParcel(Parcel parcel) {
            ReceiverInfo receiverInfo = new ReceiverInfo();
            receiverInfo.readFromParcel(parcel);
            return receiverInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReceiverInfo[] newArray(int i) {
            return new ReceiverInfo[i];
        }
    };
    public ActivityInfo activityInfo;
    public CompatibilityInfo compatInfo;
    public String data;
    public Bundle extras;
    public Intent intent;
    public IIntentReceiver receiver;
    public String sendingPackage;
    public boolean assumeDelivered = false;
    public int sendingUser = 0;
    public int processState = 0;
    public int resultCode = 0;
    public int sendingUid = -1;
    public boolean registered = false;
    public boolean ordered = false;
    public boolean sticky = false;
    public boolean sync = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.intent, i);
        parcel.writeString(this.data);
        parcel.writeTypedObject(this.extras, i);
        parcel.writeBoolean(this.assumeDelivered);
        parcel.writeInt(this.sendingUser);
        parcel.writeInt(this.processState);
        parcel.writeInt(this.resultCode);
        parcel.writeInt(this.sendingUid);
        parcel.writeString(this.sendingPackage);
        parcel.writeBoolean(this.registered);
        parcel.writeStrongInterface(this.receiver);
        parcel.writeBoolean(this.ordered);
        parcel.writeBoolean(this.sticky);
        parcel.writeTypedObject(this.activityInfo, i);
        parcel.writeTypedObject(this.compatInfo, i);
        parcel.writeBoolean(this.sync);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.data = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.extras = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.assumeDelivered = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.sendingUser = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.processState = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.resultCode = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.sendingUid = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.sendingPackage = parcel.readString();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.registered = parcel.readBoolean();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.receiver = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.ordered = parcel.readBoolean();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.sticky = parcel.readBoolean();
                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                    this.activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                        this.compatInfo = (CompatibilityInfo) parcel.readTypedObject(CompatibilityInfo.CREATOR);
                                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                                            this.sync = parcel.readBoolean();
                                                                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                                                            }
                                                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                                                        }
                                                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                                                    }
                                                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                                }
                                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                                            }
                                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                                        }
                                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                                    }
                                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.compatInfo) | describeContents(this.intent) | describeContents(this.extras) | describeContents(this.activityInfo);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
