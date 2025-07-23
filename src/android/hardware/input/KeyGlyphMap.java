package android.hardware.input;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseIntArray;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class KeyGlyphMap implements Parcelable {
    public static final Parcelable.Creator<KeyGlyphMap> CREATOR = new Parcelable.Creator<KeyGlyphMap>() { // from class: android.hardware.input.KeyGlyphMap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyGlyphMap createFromParcel(Parcel parcel) {
            return new KeyGlyphMap(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyGlyphMap[] newArray(int i) {
            return new KeyGlyphMap[i];
        }
    };
    private static final String TAG = "KeyGlyphMap";
    private final ComponentName mComponentName;
    private final int[] mFunctionRowKeys;
    private final Map<KeyCombination, Integer> mHardwareShortcuts;
    private final SparseIntArray mKeyGlyphs;
    private final SparseIntArray mModifierGlyphs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeyGlyphMap(ComponentName componentName, SparseIntArray sparseIntArray, SparseIntArray sparseIntArray2, int[] iArr, Map<KeyCombination, Integer> map) {
        this.mComponentName = componentName;
        this.mKeyGlyphs = sparseIntArray;
        this.mModifierGlyphs = sparseIntArray2;
        this.mFunctionRowKeys = iArr;
        this.mHardwareShortcuts = map;
    }

    public KeyGlyphMap(Parcel parcel) {
        this.mComponentName = (ComponentName) parcel.readParcelable(getClass().getClassLoader(), ComponentName.class);
        this.mKeyGlyphs = parcel.readSparseIntArray();
        this.mModifierGlyphs = parcel.readSparseIntArray();
        int[] iArr = new int[parcel.readInt()];
        this.mFunctionRowKeys = iArr;
        parcel.readIntArray(iArr);
        HashMap hashMap = new HashMap(parcel.readInt());
        this.mHardwareShortcuts = hashMap;
        parcel.readMap(hashMap, getClass().getClassLoader(), KeyCombination.class, Integer.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mComponentName, 0);
        parcel.writeSparseIntArray(this.mKeyGlyphs);
        parcel.writeSparseIntArray(this.mModifierGlyphs);
        parcel.writeInt(this.mFunctionRowKeys.length);
        parcel.writeIntArray(this.mFunctionRowKeys);
        parcel.writeInt(this.mHardwareShortcuts.size());
        parcel.writeMap(this.mHardwareShortcuts);
    }

    public static class KeyCombination implements Parcelable {
        public static final Parcelable.Creator<KeyCombination> CREATOR = new Parcelable.Creator<KeyCombination>() { // from class: android.hardware.input.KeyGlyphMap.KeyCombination.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeyCombination createFromParcel(Parcel parcel) {
                return new KeyCombination(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeyCombination[] newArray(int i) {
                return new KeyCombination[i];
            }
        };
        private final int mKeycode;
        private final int mModifierState;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public KeyCombination(int i, int i2) {
            this.mModifierState = i;
            this.mKeycode = i2;
        }

        public KeyCombination(Parcel parcel) {
            this(parcel.readInt(), parcel.readInt());
        }

        public int getModifierState() {
            return this.mModifierState;
        }

        public int getKeycode() {
            return this.mKeycode;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mModifierState);
            parcel.writeInt(this.mKeycode);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof KeyCombination) {
                KeyCombination keyCombination = (KeyCombination) obj;
                if (this.mModifierState == keyCombination.mModifierState && this.mKeycode == keyCombination.mKeycode) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mModifierState), Integer.valueOf(this.mKeycode));
        }
    }

    public int[] getFunctionRowKeys() {
        return this.mFunctionRowKeys;
    }

    public Map<KeyCombination, Integer> getHardwareShortcuts() {
        return this.mHardwareShortcuts;
    }

    public Drawable getDrawableForKeycode(Context context, int i) {
        return getDrawable(context, this.mKeyGlyphs.get(i, 0));
    }

    public Drawable getDrawableForModifier(Context context, int i) {
        int i2;
        if (i == 63) {
            i2 = 4;
        } else if (i != 143) {
            switch (i) {
                case 57:
                case 58:
                    i2 = 2;
                    break;
                case 59:
                case 60:
                    i2 = 1;
                    break;
                default:
                    switch (i) {
                        case 113:
                        case 114:
                            i2 = 4096;
                            break;
                        case 115:
                            i2 = 1048576;
                            break;
                        case 116:
                            i2 = 4194304;
                            break;
                        case 117:
                        case 118:
                            i2 = 65536;
                            break;
                        case 119:
                            i2 = 8;
                            break;
                        default:
                            i2 = 0;
                            break;
                    }
            }
        } else {
            i2 = 2097152;
        }
        return getDrawable(context, this.mModifierGlyphs.get(i2, 0));
    }

    public Drawable getDrawableForModifierState(Context context, int i) {
        return getDrawable(context, this.mModifierGlyphs.get(i, 0));
    }

    private Drawable getDrawable(Context context, int i) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getResourcesForApplication(packageManager.getReceiverInfo(this.mComponentName, 786560).applicationInfo).getDrawable(i, null);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "Package name not found for " + this.mComponentName);
            return null;
        } catch (Resources.NotFoundException unused2) {
            Log.e(TAG, "Resource not found for " + this.mComponentName);
            return null;
        }
    }

    public String toString() {
        return "KeyGlyphMap{mComponentName=" + this.mComponentName + ", mKeyGlyphs=" + this.mKeyGlyphs + ", mModifierGlyphs=" + this.mModifierGlyphs + ", mFunctionRowKeys=" + Arrays.toString(this.mFunctionRowKeys) + ", mHardwareShortcuts=" + this.mHardwareShortcuts + '}';
    }
}
