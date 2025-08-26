package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ChannelMessage implements Parcelable {
    public static final Parcelable.Creator<ChannelMessage> CREATOR = new Parcelable.Creator<ChannelMessage>() { // from class: android.hardware.power.ChannelMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChannelMessage createFromParcel(Parcel parcel) {
            ChannelMessage channelMessage = new ChannelMessage();
            channelMessage.readFromParcel(parcel);
            return channelMessage;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChannelMessage[] newArray(int i) {
            return new ChannelMessage[i];
        }
    };
    public ChannelMessageContents data;
    public int sessionID = 0;
    public long timeStampNanos = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sessionID);
        parcel.writeLong(this.timeStampNanos);
        parcel.writeTypedObject(this.data, i);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.sessionID = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.timeStampNanos = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.data = (ChannelMessageContents) parcel.readTypedObject(ChannelMessageContents.CREATOR);
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.data);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public static final class ChannelMessageContents implements Parcelable {
        public static final Parcelable.Creator<ChannelMessageContents> CREATOR = new Parcelable.Creator<ChannelMessageContents>() { // from class: android.hardware.power.ChannelMessage.ChannelMessageContents.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ChannelMessageContents createFromParcel(Parcel parcel) {
                return new ChannelMessageContents(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ChannelMessageContents[] newArray(int i) {
                return new ChannelMessageContents[i];
            }
        };
        public static final int hint = 2;
        public static final int mode = 3;
        public static final int reserved = 0;
        public static final int targetDuration = 1;
        public static final int workDuration = 4;
        private int _tag;
        private Object _value;

        public @interface Tag {
            public static final byte hint = 2;
            public static final byte mode = 3;
            public static final byte reserved = 0;
            public static final byte targetDuration = 1;
            public static final byte workDuration = 4;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        public ChannelMessageContents() {
            this._tag = 0;
            this._value = new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        }

        private ChannelMessageContents(Parcel parcel) {
            readFromParcel(parcel);
        }

        private ChannelMessageContents(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public int getTag() {
            return this._tag;
        }

        public static ChannelMessageContents reserved(long[] jArr) {
            return new ChannelMessageContents(0, jArr);
        }

        public long[] getReserved() {
            _assertTag(0);
            return (long[]) this._value;
        }

        public void setReserved(long[] jArr) {
            _set(0, jArr);
        }

        public static ChannelMessageContents targetDuration(long j) {
            return new ChannelMessageContents(1, Long.valueOf(j));
        }

        public long getTargetDuration() {
            _assertTag(1);
            return ((Long) this._value).longValue();
        }

        public void setTargetDuration(long j) {
            _set(1, Long.valueOf(j));
        }

        public static ChannelMessageContents hint(int i) {
            return new ChannelMessageContents(2, Integer.valueOf(i));
        }

        public int getHint() {
            _assertTag(2);
            return ((Integer) this._value).intValue();
        }

        public void setHint(int i) {
            _set(2, Integer.valueOf(i));
        }

        public static ChannelMessageContents mode(SessionModeSetter sessionModeSetter) {
            return new ChannelMessageContents(3, sessionModeSetter);
        }

        public SessionModeSetter getMode() {
            _assertTag(3);
            return (SessionModeSetter) this._value;
        }

        public void setMode(SessionModeSetter sessionModeSetter) {
            _set(3, sessionModeSetter);
        }

        public static ChannelMessageContents workDuration(WorkDurationFixedV1 workDurationFixedV1) {
            return new ChannelMessageContents(4, workDurationFixedV1);
        }

        public WorkDurationFixedV1 getWorkDuration() {
            _assertTag(4);
            return (WorkDurationFixedV1) this._value;
        }

        public void setWorkDuration(WorkDurationFixedV1 workDurationFixedV1) {
            _set(4, workDurationFixedV1);
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this._tag);
            int i2 = this._tag;
            if (i2 == 0) {
                parcel.writeFixedArray(getReserved(), i, 16);
                return;
            }
            if (i2 == 1) {
                parcel.writeLong(getTargetDuration());
                return;
            }
            if (i2 == 2) {
                parcel.writeInt(getHint());
            } else if (i2 == 3) {
                parcel.writeTypedObject(getMode(), i);
            } else {
                if (i2 != 4) {
                    return;
                }
                parcel.writeTypedObject(getWorkDuration(), i);
            }
        }

        public void readFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == 0) {
                _set(i, (long[]) parcel.createFixedArray(long[].class, 16));
                return;
            }
            if (i == 1) {
                _set(i, Long.valueOf(parcel.readLong()));
                return;
            }
            if (i == 2) {
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            }
            if (i == 3) {
                _set(i, (SessionModeSetter) parcel.readTypedObject(SessionModeSetter.CREATOR));
            } else if (i == 4) {
                _set(i, (WorkDurationFixedV1) parcel.readTypedObject(WorkDurationFixedV1.CREATOR));
            } else {
                throw new IllegalArgumentException("union: unknown tag: " + i);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            int tag = getTag();
            if (tag == 3) {
                return describeContents(getMode());
            }
            if (tag != 4) {
                return 0;
            }
            return describeContents(getWorkDuration());
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }

        private void _assertTag(int i) {
            if (getTag() == i) {
                return;
            }
            throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }

        private String _tagString(int i) {
            if (i == 0) {
                return "reserved";
            }
            if (i == 1) {
                return "targetDuration";
            }
            if (i == 2) {
                return "hint";
            }
            if (i == 3) {
                return "mode";
            }
            if (i == 4) {
                return "workDuration";
            }
            throw new IllegalStateException("unknown field: " + i);
        }

        private void _set(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public static class SessionModeSetter implements Parcelable {
            public static final Parcelable.Creator<SessionModeSetter> CREATOR = new Parcelable.Creator<SessionModeSetter>() { // from class: android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SessionModeSetter createFromParcel(Parcel parcel) {
                    SessionModeSetter sessionModeSetter = new SessionModeSetter();
                    sessionModeSetter.readFromParcel(parcel);
                    return sessionModeSetter;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SessionModeSetter[] newArray(int i) {
                    return new SessionModeSetter[i];
                }
            };
            public boolean enabled = false;
            public int modeInt;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public final int getStability() {
                return 1;
            }

            @Override // android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i) {
                int iDataPosition = parcel.dataPosition();
                parcel.writeInt(0);
                parcel.writeInt(this.modeInt);
                parcel.writeBoolean(this.enabled);
                int iDataPosition2 = parcel.dataPosition();
                parcel.setDataPosition(iDataPosition);
                parcel.writeInt(iDataPosition2 - iDataPosition);
                parcel.setDataPosition(iDataPosition2);
            }

            public final void readFromParcel(Parcel parcel) {
                int iDataPosition = parcel.dataPosition();
                int i = parcel.readInt();
                try {
                    if (i < 4) {
                        throw new BadParcelableException("Parcelable too small");
                    }
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.modeInt = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.enabled = parcel.readBoolean();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition + i);
                } catch (Throwable th) {
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition + i);
                    throw th;
                }
            }
        }
    }
}
