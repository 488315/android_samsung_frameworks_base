package android.security.keymaster;

import android.os.Parcel;
import android.os.Parcelable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class KeymasterArguments implements Parcelable {
    public static final Parcelable.Creator<KeymasterArguments> CREATOR;
    public static final long UINT32_MAX_VALUE = 4294967295L;
    private static final long UINT32_RANGE = 4294967296L;
    public static final BigInteger UINT64_MAX_VALUE;
    private static final BigInteger UINT64_RANGE;
    private List<KeymasterArgument> mArguments;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        BigInteger bigIntegerShiftLeft = BigInteger.ONE.shiftLeft(64);
        UINT64_RANGE = bigIntegerShiftLeft;
        UINT64_MAX_VALUE = bigIntegerShiftLeft.subtract(BigInteger.ONE);
        CREATOR = new Parcelable.Creator<KeymasterArguments>() { // from class: android.security.keymaster.KeymasterArguments.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeymasterArguments createFromParcel(Parcel parcel) {
                return new KeymasterArguments(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeymasterArguments[] newArray(int i) {
                return new KeymasterArguments[i];
            }
        };
    }

    public KeymasterArguments() {
        this.mArguments = new ArrayList();
    }

    private KeymasterArguments(Parcel parcel) {
        this.mArguments = parcel.createTypedArrayList(KeymasterArgument.CREATOR);
    }

    public void addEnum(int i, int i2) {
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != 268435456 && tagType != 536870912) {
            throw new IllegalArgumentException("Not an enum or repeating enum tag: " + i);
        }
        addEnumTag(i, i2);
    }

    public void addEnums(int i, int... iArr) {
        if (KeymasterDefs.getTagType(i) != 536870912) {
            throw new IllegalArgumentException("Not a repeating enum tag: " + i);
        }
        for (int i2 : iArr) {
            addEnumTag(i, i2);
        }
    }

    public int getEnum(int i, int i2) {
        if (KeymasterDefs.getTagType(i) != 268435456) {
            throw new IllegalArgumentException("Not an enum tag: " + i);
        }
        KeymasterArgument argumentByTag = getArgumentByTag(i);
        return argumentByTag == null ? i2 : getEnumTagValue(argumentByTag);
    }

    public List<Integer> getEnums(int i) {
        if (KeymasterDefs.getTagType(i) != 536870912) {
            throw new IllegalArgumentException("Not a repeating enum tag: " + i);
        }
        ArrayList arrayList = new ArrayList();
        for (KeymasterArgument keymasterArgument : this.mArguments) {
            if (keymasterArgument.tag == i) {
                arrayList.add(Integer.valueOf(getEnumTagValue(keymasterArgument)));
            }
        }
        return arrayList;
    }

    private void addEnumTag(int i, int i2) {
        this.mArguments.add(new KeymasterIntArgument(i, i2));
    }

    private int getEnumTagValue(KeymasterArgument keymasterArgument) {
        return ((KeymasterIntArgument) keymasterArgument).value;
    }

    public void addUnsignedInt(int i, long j) {
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != 805306368 && tagType != 1073741824) {
            throw new IllegalArgumentException("Not an int or repeating int tag: " + i);
        }
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException("Int tag value out of range: " + j);
        }
        this.mArguments.add(new KeymasterIntArgument(i, (int) j));
    }

    public long getUnsignedInt(int i, long j) {
        if (KeymasterDefs.getTagType(i) != 805306368) {
            throw new IllegalArgumentException("Not an int tag: " + i);
        }
        return getArgumentByTag(i) == null ? j : ((KeymasterIntArgument) r2).value & 4294967295L;
    }

    public void addUnsignedLong(int i, BigInteger bigInteger) {
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != 1342177280 && tagType != -1610612736) {
            throw new IllegalArgumentException("Not a long or repeating long tag: " + i);
        }
        addLongTag(i, bigInteger);
    }

    public List<BigInteger> getUnsignedLongs(int i) {
        if (KeymasterDefs.getTagType(i) != -1610612736) {
            throw new IllegalArgumentException("Tag is not a repeating long: " + i);
        }
        ArrayList arrayList = new ArrayList();
        for (KeymasterArgument keymasterArgument : this.mArguments) {
            if (keymasterArgument.tag == i) {
                arrayList.add(getLongTagValue(keymasterArgument));
            }
        }
        return arrayList;
    }

    private void addLongTag(int i, BigInteger bigInteger) {
        if (bigInteger.signum() == -1 || bigInteger.compareTo(UINT64_MAX_VALUE) > 0) {
            throw new IllegalArgumentException("Long tag value out of range: " + bigInteger);
        }
        this.mArguments.add(new KeymasterLongArgument(i, bigInteger.longValue()));
    }

    private BigInteger getLongTagValue(KeymasterArgument keymasterArgument) {
        return toUint64(((KeymasterLongArgument) keymasterArgument).value);
    }

    public void addBoolean(int i) {
        if (KeymasterDefs.getTagType(i) != 1879048192) {
            throw new IllegalArgumentException("Not a boolean tag: " + i);
        }
        this.mArguments.add(new KeymasterBooleanArgument(i));
    }

    public boolean getBoolean(int i) {
        if (KeymasterDefs.getTagType(i) == 1879048192) {
            return getArgumentByTag(i) != null;
        }
        throw new IllegalArgumentException("Not a boolean tag: " + i);
    }

    public void addBytes(int i, byte[] bArr) {
        if (KeymasterDefs.getTagType(i) != -1879048192) {
            throw new IllegalArgumentException("Not a bytes tag: " + i);
        }
        if (bArr == null) {
            throw new NullPointerException("value == nulll");
        }
        this.mArguments.add(new KeymasterBlobArgument(i, bArr));
    }

    public byte[] getBytes(int i, byte[] bArr) {
        if (KeymasterDefs.getTagType(i) != -1879048192) {
            throw new IllegalArgumentException("Not a bytes tag: " + i);
        }
        KeymasterArgument argumentByTag = getArgumentByTag(i);
        return argumentByTag == null ? bArr : ((KeymasterBlobArgument) argumentByTag).blob;
    }

    public void addDate(int i, Date date) {
        if (KeymasterDefs.getTagType(i) != 1610612736) {
            throw new IllegalArgumentException("Not a date tag: " + i);
        }
        if (date == null) {
            throw new NullPointerException("value == nulll");
        }
        if (date.getTime() < 0) {
            throw new IllegalArgumentException("Date tag value out of range: " + date);
        }
        this.mArguments.add(new KeymasterDateArgument(i, date));
    }

    public void addDateIfNotNull(int i, Date date) {
        if (KeymasterDefs.getTagType(i) != 1610612736) {
            throw new IllegalArgumentException("Not a date tag: " + i);
        }
        if (date != null) {
            addDate(i, date);
        }
    }

    public Date getDate(int i, Date date) {
        if (KeymasterDefs.getTagType(i) != 1610612736) {
            throw new IllegalArgumentException("Tag is not a date type: " + i);
        }
        KeymasterArgument argumentByTag = getArgumentByTag(i);
        if (argumentByTag == null) {
            return date;
        }
        Date date2 = ((KeymasterDateArgument) argumentByTag).date;
        if (date2.getTime() >= 0) {
            return date2;
        }
        throw new IllegalArgumentException("Tag value too large. Tag: " + i);
    }

    private KeymasterArgument getArgumentByTag(int i) {
        for (KeymasterArgument keymasterArgument : this.mArguments) {
            if (keymasterArgument.tag == i) {
                return keymasterArgument;
            }
        }
        return null;
    }

    public boolean containsTag(int i) {
        return getArgumentByTag(i) != null;
    }

    public int size() {
        return this.mArguments.size();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mArguments);
    }

    public void readFromParcel(Parcel parcel) {
        parcel.readTypedList(this.mArguments, KeymasterArgument.CREATOR);
    }

    public static BigInteger toUint64(long j) {
        if (j >= 0) {
            return BigInteger.valueOf(j);
        }
        return BigInteger.valueOf(j).add(UINT64_RANGE);
    }
}
