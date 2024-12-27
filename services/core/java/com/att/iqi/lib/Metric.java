package com.att.iqi.lib;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class Metric implements Parcelable {
    private final String mClassName;
    private final int mMetricId;
    private static final Map sLoadedClasses = new HashMap();
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() { // from class: com.att.iqi.lib.Metric.1
                @Override // android.os.Parcelable.Creator
                public Metric createFromParcel(Parcel parcel) {
                    int dataPosition = parcel.dataPosition();
                    if (parcel.readInt() == 1) {
                        throw new IllegalArgumentException("API 1 not supported");
                    }
                    parcel.readInt();
                    String readString = parcel.readString();
                    String packageName = Metric.class.getPackageName();
                    if (readString == null || !readString.startsWith(packageName)) {
                        throw new IllegalArgumentException("Invalid or null package name found");
                    }
                    parcel.setDataPosition(dataPosition);
                    return Metric.maybeLoadAndInstantiate(readString, parcel);
                }

                @Override // android.os.Parcelable.Creator
                public Metric[] newArray(int i) {
                    return new Metric[i];
                }
            };

    public final class ID implements Parcelable {
        private final int mID;
        private final String mStringID;
        private static final String sIDPattern = "[A-Z0-9_]{4}";
        private static final Pattern sPattern = Pattern.compile(sIDPattern);
        public static final Parcelable.Creator CREATOR =
                new Parcelable.Creator() { // from class: com.att.iqi.lib.Metric.ID.1
                    @Override // android.os.Parcelable.Creator
                    public ID createFromParcel(Parcel parcel) {
                        return new ID(parcel, 0);
                    }

                    @Override // android.os.Parcelable.Creator
                    public ID[] newArray(int i) {
                        return new ID[i];
                    }
                };

        public ID(int i) {
            this.mID = i;
            String idFromInt = idFromInt(i);
            this.mStringID = idFromInt;
            if (isInvalidId(idFromInt)) {
                throw new IllegalArgumentException("Invalid Metric ID");
            }
        }

        private ID(Parcel parcel) {
            parcel.readInt();
            this.mID = parcel.readInt();
            this.mStringID = parcel.readString();
        }

        public /* synthetic */ ID(Parcel parcel, int i) {
            this(parcel);
        }

        public ID(String str) {
            if (isInvalidId(str)) {
                throw new IllegalArgumentException("Invalid Metric ID");
            }
            this.mStringID = str;
            this.mID = idFromString(str);
        }

        private static String idFromInt(int i) {
            return new String(
                    new char[] {
                        (char)
                                ((i >> 24)
                                        & IDnsResolverUnsolicitedEventListener
                                                .DNS_HEALTH_RESULT_TIMEOUT),
                        (char)
                                ((i >> 16)
                                        & IDnsResolverUnsolicitedEventListener
                                                .DNS_HEALTH_RESULT_TIMEOUT),
                        (char)
                                ((i >> 8)
                                        & IDnsResolverUnsolicitedEventListener
                                                .DNS_HEALTH_RESULT_TIMEOUT),
                        (char) (i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                    });
        }

        private static int idFromString(String str) {
            if (str.length() != 4) {
                return 0;
            }
            return (str.charAt(3) & 255)
                    | ((str.charAt(0) & 255) << 24)
                    | ((str.charAt(1) & 255) << 16)
                    | ((str.charAt(2) & 255) << 8);
        }

        private boolean isInvalidId(String str) {
            return !sPattern.matcher(str).matches();
        }

        public int asInt() {
            return this.mID;
        }

        public String asString() {
            return this.mStringID;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object obj) {
            if (obj == null || ID.class != obj.getClass()) {
                return false;
            }
            ID id = (ID) obj;
            return id.mID == this.mID && TextUtils.equals(id.mStringID, this.mStringID);
        }

        public int hashCode() {
            String str = this.mStringID;
            return 3349 + (str != null ? str.hashCode() : 0);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(14);
            parcel.writeInt(this.mID);
            parcel.writeString(this.mStringID);
        }
    }

    public Metric() {
        this.mClassName = getClass().getCanonicalName();
        this.mMetricId = enforceDeclarationAndGetMetricID();
    }

    public Metric(Parcel parcel) {
        if (parcel.readInt() == 1) {
            throw new IllegalArgumentException("API 1 not supported");
        }
        int readInt = parcel.readInt();
        this.mClassName = parcel.readString();
        parcel.readLong();
        parcel.setDataPosition(readInt);
        this.mMetricId = enforceDeclarationAndGetMetricID();
    }

    private int enforceDeclarationAndGetMetricID() {
        try {
            Field field = getClass().getField("ID");
            Object obj = field.get(field);
            if (obj instanceof ID) {
                return ((ID) obj).asInt();
            }
            throw new IllegalArgumentException("ID was null");
        } catch (IllegalAccessException unused) {
            throw new IllegalArgumentException("Can't read ID field from sub class");
        } catch (NoSuchFieldException unused2) {
            throw new IllegalArgumentException("Sub class must define an ID field");
        }
    }

    public static Metric maybeLoadAndInstantiate(String str, Parcel parcel) {
        try {
            Map map = sLoadedClasses;
            Constructor<?> constructor = (Constructor) map.get(str);
            if (constructor == null) {
                constructor = Class.forName(str).getDeclaredConstructor(Parcel.class);
                constructor.setAccessible(true);
                map.put(str, constructor);
            }
            return (Metric) constructor.newInstance(parcel);
        } catch (Exception unused) {
            return new Metric() { // from class: com.att.iqi.lib.Metric.2
                @Override // com.att.iqi.lib.Metric
                public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
                    return 0;
                }
            };
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getId() {
        return this.mMetricId;
    }

    public abstract int serialize(ByteBuffer byteBuffer) throws BufferOverflowException;

    public void stringOut(ByteBuffer byteBuffer, String str) throws BufferOverflowException {
        if (str != null) {
            byteBuffer.put(str.replace((char) 0, '_').getBytes(Charset.defaultCharset()));
        }
        byteBuffer.put((byte) 0);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(14);
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mClassName);
        parcel.writeLong(0L);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2);
        parcel.setDataPosition(dataPosition2);
        parcel.writeInt(14);
    }
}
