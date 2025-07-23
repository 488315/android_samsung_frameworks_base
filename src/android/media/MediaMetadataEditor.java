package android.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseIntArray;

@Deprecated
/* loaded from: classes2.dex */
public abstract class MediaMetadataEditor {
    public static final int BITMAP_KEY_ARTWORK = 100;
    public static final int KEY_EDITABLE_MASK = 536870911;
    protected static final SparseIntArray METADATA_KEYS_TYPE;
    protected static final int METADATA_TYPE_BITMAP = 2;
    protected static final int METADATA_TYPE_INVALID = -1;
    protected static final int METADATA_TYPE_LONG = 0;
    protected static final int METADATA_TYPE_RATING = 3;
    protected static final int METADATA_TYPE_STRING = 1;
    public static final int RATING_KEY_BY_OTHERS = 101;
    public static final int RATING_KEY_BY_USER = 268435457;
    private static final String TAG = "MediaMetadataEditor";
    protected long mEditableKeys;
    protected Bitmap mEditorArtwork;
    protected Bundle mEditorMetadata;
    protected MediaMetadata.Builder mMetadataBuilder;
    protected boolean mMetadataChanged = false;
    protected boolean mApplied = false;
    protected boolean mArtworkChanged = false;

    public abstract void apply();

    protected MediaMetadataEditor() {
    }

    public synchronized void clear() {
        if (this.mApplied) {
            Log.e(TAG, "Can't clear a previously applied MediaMetadataEditor");
            return;
        }
        this.mEditorMetadata.clear();
        this.mEditorArtwork = null;
        this.mMetadataBuilder = new MediaMetadata.Builder();
    }

    public synchronized void addEditableKey(int i) {
        if (this.mApplied) {
            Log.e(TAG, "Can't change editable keys of a previously applied MetadataEditor");
            return;
        }
        if (i == 268435457) {
            this.mEditableKeys |= i & KEY_EDITABLE_MASK;
            this.mMetadataChanged = true;
        } else {
            Log.e(TAG, "Metadata key " + i + " cannot be edited");
        }
    }

    public synchronized void removeEditableKeys() {
        if (this.mApplied) {
            Log.e(TAG, "Can't remove all editable keys of a previously applied MetadataEditor");
            return;
        }
        if (this.mEditableKeys != 0) {
            this.mEditableKeys = 0L;
            this.mMetadataChanged = true;
        }
    }

    public synchronized int[] getEditableKeys() {
        if (this.mEditableKeys != 268435457) {
            return null;
        }
        return new int[]{268435457};
    }

    public synchronized MediaMetadataEditor putString(int i, String str) throws IllegalArgumentException {
        if (this.mApplied) {
            Log.e(TAG, "Can't edit a previously applied MediaMetadataEditor");
            return this;
        }
        if (METADATA_KEYS_TYPE.get(i, -1) != 1) {
            throw new IllegalArgumentException("Invalid type 'String' for key " + i);
        }
        this.mEditorMetadata.putString(String.valueOf(i), str);
        this.mMetadataChanged = true;
        return this;
    }

    public synchronized MediaMetadataEditor putLong(int i, long j) throws IllegalArgumentException {
        if (this.mApplied) {
            Log.e(TAG, "Can't edit a previously applied MediaMetadataEditor");
            return this;
        }
        if (METADATA_KEYS_TYPE.get(i, -1) != 0) {
            throw new IllegalArgumentException("Invalid type 'long' for key " + i);
        }
        this.mEditorMetadata.putLong(String.valueOf(i), j);
        this.mMetadataChanged = true;
        return this;
    }

    public synchronized MediaMetadataEditor putBitmap(int i, Bitmap bitmap) throws IllegalArgumentException {
        if (this.mApplied) {
            Log.e(TAG, "Can't edit a previously applied MediaMetadataEditor");
            return this;
        }
        if (i != 100) {
            throw new IllegalArgumentException("Invalid type 'Bitmap' for key " + i);
        }
        this.mEditorArtwork = bitmap;
        this.mArtworkChanged = true;
        return this;
    }

    public synchronized MediaMetadataEditor putObject(int i, Object obj) throws IllegalArgumentException {
        if (this.mApplied) {
            Log.e(TAG, "Can't edit a previously applied MediaMetadataEditor");
            return this;
        }
        int i2 = METADATA_KEYS_TYPE.get(i, -1);
        if (i2 == 0) {
            if (obj instanceof Long) {
                return putLong(i, ((Long) obj).longValue());
            }
            throw new IllegalArgumentException("Not a non-null Long for key " + i);
        }
        if (i2 == 1) {
            if (obj != null && !(obj instanceof String)) {
                throw new IllegalArgumentException("Not a String for key " + i);
            }
            return putString(i, (String) obj);
        }
        if (i2 != 2) {
            if (i2 == 3) {
                this.mEditorMetadata.putParcelable(String.valueOf(i), (Parcelable) obj);
                this.mMetadataChanged = true;
                return this;
            }
            throw new IllegalArgumentException("Invalid key " + i);
        }
        if (obj != null && !(obj instanceof Bitmap)) {
            throw new IllegalArgumentException("Not a Bitmap for key " + i);
        }
        return putBitmap(i, (Bitmap) obj);
    }

    public synchronized long getLong(int i, long j) throws IllegalArgumentException {
        if (METADATA_KEYS_TYPE.get(i, -1) != 0) {
            throw new IllegalArgumentException("Invalid type 'long' for key " + i);
        }
        return this.mEditorMetadata.getLong(String.valueOf(i), j);
    }

    public synchronized String getString(int i, String str) throws IllegalArgumentException {
        if (METADATA_KEYS_TYPE.get(i, -1) != 1) {
            throw new IllegalArgumentException("Invalid type 'String' for key " + i);
        }
        return this.mEditorMetadata.getString(String.valueOf(i), str);
    }

    public synchronized Bitmap getBitmap(int i, Bitmap bitmap) throws IllegalArgumentException {
        if (i != 100) {
            throw new IllegalArgumentException("Invalid type 'Bitmap' for key " + i);
        }
        Bitmap bitmap2 = this.mEditorArtwork;
        if (bitmap2 != null) {
            bitmap = bitmap2;
        }
        return bitmap;
    }

    public synchronized Object getObject(int i, Object obj) throws IllegalArgumentException {
        int i2 = METADATA_KEYS_TYPE.get(i, -1);
        if (i2 == 0) {
            if (!this.mEditorMetadata.containsKey(String.valueOf(i))) {
                return obj;
            }
            return Long.valueOf(this.mEditorMetadata.getLong(String.valueOf(i)));
        }
        if (i2 == 1) {
            if (!this.mEditorMetadata.containsKey(String.valueOf(i))) {
                return obj;
            }
            return this.mEditorMetadata.getString(String.valueOf(i));
        }
        if (i2 != 2) {
            if (i2 == 3) {
                if (!this.mEditorMetadata.containsKey(String.valueOf(i))) {
                    return obj;
                }
                return this.mEditorMetadata.getParcelable(String.valueOf(i));
            }
        } else if (i == 100) {
            Bitmap bitmap = this.mEditorArtwork;
            if (bitmap != null) {
                obj = bitmap;
            }
            return obj;
        }
        throw new IllegalArgumentException("Invalid key " + i);
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(17);
        METADATA_KEYS_TYPE = sparseIntArray;
        sparseIntArray.put(0, 0);
        sparseIntArray.put(14, 0);
        sparseIntArray.put(9, 0);
        sparseIntArray.put(8, 0);
        sparseIntArray.put(1, 1);
        sparseIntArray.put(13, 1);
        sparseIntArray.put(7, 1);
        sparseIntArray.put(2, 1);
        sparseIntArray.put(3, 1);
        sparseIntArray.put(15, 1);
        sparseIntArray.put(4, 1);
        sparseIntArray.put(5, 1);
        sparseIntArray.put(6, 1);
        sparseIntArray.put(11, 1);
        sparseIntArray.put(100, 2);
        sparseIntArray.put(101, 3);
        sparseIntArray.put(268435457, 3);
    }
}
