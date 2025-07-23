package android.hardware.input;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public class AidlKeyGestureEvent implements Parcelable {
    public static final Parcelable.Creator<AidlKeyGestureEvent> CREATOR = new Parcelable.Creator<AidlKeyGestureEvent>() { // from class: android.hardware.input.AidlKeyGestureEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AidlKeyGestureEvent createFromParcel(Parcel parcel) {
            AidlKeyGestureEvent aidlKeyGestureEvent = new AidlKeyGestureEvent();
            aidlKeyGestureEvent.readFromParcel(parcel);
            return aidlKeyGestureEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AidlKeyGestureEvent[] newArray(int i) {
            return new AidlKeyGestureEvent[i];
        }
    };
    public String appLaunchCategory;
    public String appLaunchClassName;
    public String appLaunchPackageName;
    public String appLaunchRole;
    public long[] eventTimes;
    public int[] keycodes;
    public int deviceId = 0;
    public int modifierState = 0;
    public int gestureType = 0;
    public int action = 0;
    public int displayId = 0;
    public int flags = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.deviceId);
        parcel.writeIntArray(this.keycodes);
        parcel.writeLongArray(this.eventTimes);
        parcel.writeInt(this.modifierState);
        parcel.writeInt(this.gestureType);
        parcel.writeInt(this.action);
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.flags);
        parcel.writeString(this.appLaunchCategory);
        parcel.writeString(this.appLaunchRole);
        parcel.writeString(this.appLaunchPackageName);
        parcel.writeString(this.appLaunchClassName);
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
                this.deviceId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.keycodes = parcel.createIntArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.eventTimes = parcel.createLongArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.modifierState = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.gestureType = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.action = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.displayId = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.flags = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.appLaunchCategory = parcel.readString();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.appLaunchRole = parcel.readString();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.appLaunchPackageName = parcel.readString();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.appLaunchClassName = parcel.readString();
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AidlKeyGestureEvent)) {
            return false;
        }
        AidlKeyGestureEvent aidlKeyGestureEvent = (AidlKeyGestureEvent) obj;
        return Objects.deepEquals(Integer.valueOf(this.deviceId), Integer.valueOf(aidlKeyGestureEvent.deviceId)) && Objects.deepEquals(this.keycodes, aidlKeyGestureEvent.keycodes) && Objects.deepEquals(this.eventTimes, aidlKeyGestureEvent.eventTimes) && Objects.deepEquals(Integer.valueOf(this.modifierState), Integer.valueOf(aidlKeyGestureEvent.modifierState)) && Objects.deepEquals(Integer.valueOf(this.gestureType), Integer.valueOf(aidlKeyGestureEvent.gestureType)) && Objects.deepEquals(Integer.valueOf(this.action), Integer.valueOf(aidlKeyGestureEvent.action)) && Objects.deepEquals(Integer.valueOf(this.displayId), Integer.valueOf(aidlKeyGestureEvent.displayId)) && Objects.deepEquals(Integer.valueOf(this.flags), Integer.valueOf(aidlKeyGestureEvent.flags)) && Objects.deepEquals(this.appLaunchCategory, aidlKeyGestureEvent.appLaunchCategory) && Objects.deepEquals(this.appLaunchRole, aidlKeyGestureEvent.appLaunchRole) && Objects.deepEquals(this.appLaunchPackageName, aidlKeyGestureEvent.appLaunchPackageName) && Objects.deepEquals(this.appLaunchClassName, aidlKeyGestureEvent.appLaunchClassName);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.deviceId), this.keycodes, this.eventTimes, Integer.valueOf(this.modifierState), Integer.valueOf(this.gestureType), Integer.valueOf(this.action), Integer.valueOf(this.displayId), Integer.valueOf(this.flags), this.appLaunchCategory, this.appLaunchRole, this.appLaunchPackageName, this.appLaunchClassName).toArray());
    }
}
