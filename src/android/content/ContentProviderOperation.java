package android.content;

import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class ContentProviderOperation implements Parcelable {
    public static final Parcelable.Creator<ContentProviderOperation> CREATOR = new Parcelable.Creator<ContentProviderOperation>() { // from class: android.content.ContentProviderOperation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentProviderOperation createFromParcel(Parcel parcel) {
            return new ContentProviderOperation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentProviderOperation[] newArray(int i) {
            return new ContentProviderOperation[i];
        }
    };
    private static final String TAG = "ContentProviderOperation";
    public static final int TYPE_ASSERT = 4;
    public static final int TYPE_CALL = 5;
    public static final int TYPE_DELETE = 3;
    public static final int TYPE_INSERT = 1;
    public static final int TYPE_UPDATE = 2;
    private final String mArg;
    private final boolean mExceptionAllowed;
    private final Integer mExpectedCount;
    private final ArrayMap<String, Object> mExtras;
    private final String mMethod;
    private final String mSelection;
    private final SparseArray<Object> mSelectionArgs;
    private final int mType;
    private final Uri mUri;
    private final ArrayMap<String, Object> mValues;
    private final boolean mYieldAllowed;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ContentProviderOperation(Builder builder) {
        this.mType = builder.mType;
        this.mUri = builder.mUri;
        this.mMethod = builder.mMethod;
        this.mArg = builder.mArg;
        this.mValues = builder.mValues;
        this.mExtras = builder.mExtras;
        this.mSelection = builder.mSelection;
        this.mSelectionArgs = builder.mSelectionArgs;
        this.mExpectedCount = builder.mExpectedCount;
        this.mYieldAllowed = builder.mYieldAllowed;
        this.mExceptionAllowed = builder.mExceptionAllowed;
    }

    private ContentProviderOperation(Parcel parcel) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException, IllegalArgumentException, NegativeArraySizeException {
        this.mType = parcel.readInt();
        this.mUri = Uri.CREATOR.createFromParcel(parcel);
        this.mMethod = parcel.readInt() != 0 ? parcel.readString8() : null;
        this.mArg = parcel.readInt() != 0 ? parcel.readString8() : null;
        int i = parcel.readInt();
        if (i != -1) {
            ArrayMap<String, Object> arrayMap = new ArrayMap<>(i);
            this.mValues = arrayMap;
            parcel.readArrayMap(arrayMap, null);
        } else {
            this.mValues = null;
        }
        int i2 = parcel.readInt();
        if (i2 != -1) {
            ArrayMap<String, Object> arrayMap2 = new ArrayMap<>(i2);
            this.mExtras = arrayMap2;
            parcel.readArrayMap(arrayMap2, null);
        } else {
            this.mExtras = null;
        }
        this.mSelection = parcel.readInt() != 0 ? parcel.readString8() : null;
        this.mSelectionArgs = parcel.readSparseArray(null, Object.class);
        this.mExpectedCount = parcel.readInt() != 0 ? Integer.valueOf(parcel.readInt()) : null;
        this.mYieldAllowed = parcel.readInt() != 0;
        this.mExceptionAllowed = parcel.readInt() != 0;
    }

    public ContentProviderOperation(ContentProviderOperation contentProviderOperation, Uri uri) {
        this.mType = contentProviderOperation.mType;
        this.mUri = uri;
        this.mMethod = contentProviderOperation.mMethod;
        this.mArg = contentProviderOperation.mArg;
        this.mValues = contentProviderOperation.mValues;
        this.mExtras = contentProviderOperation.mExtras;
        this.mSelection = contentProviderOperation.mSelection;
        this.mSelectionArgs = contentProviderOperation.mSelectionArgs;
        this.mExpectedCount = contentProviderOperation.mExpectedCount;
        this.mYieldAllowed = contentProviderOperation.mYieldAllowed;
        this.mExceptionAllowed = contentProviderOperation.mExceptionAllowed;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeInt(this.mType);
        Uri.writeToParcel(parcel, this.mUri);
        if (this.mMethod != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mMethod);
        } else {
            parcel.writeInt(0);
        }
        if (this.mArg != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mArg);
        } else {
            parcel.writeInt(0);
        }
        ArrayMap<String, Object> arrayMap = this.mValues;
        if (arrayMap != null) {
            parcel.writeInt(arrayMap.size());
            parcel.writeArrayMap(this.mValues);
        } else {
            parcel.writeInt(-1);
        }
        ArrayMap<String, Object> arrayMap2 = this.mExtras;
        if (arrayMap2 != null) {
            parcel.writeInt(arrayMap2.size());
            parcel.writeArrayMap(this.mExtras);
        } else {
            parcel.writeInt(-1);
        }
        if (this.mSelection != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mSelection);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeSparseArray(this.mSelectionArgs);
        if (this.mExpectedCount != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.mExpectedCount.intValue());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mYieldAllowed ? 1 : 0);
        parcel.writeInt(this.mExceptionAllowed ? 1 : 0);
    }

    public static Builder newInsert(Uri uri) {
        return new Builder(1, uri);
    }

    public static Builder newUpdate(Uri uri) {
        return new Builder(2, uri);
    }

    public static Builder newDelete(Uri uri) {
        return new Builder(3, uri);
    }

    public static Builder newAssertQuery(Uri uri) {
        return new Builder(4, uri);
    }

    public static Builder newCall(Uri uri, String str, String str2) {
        return new Builder(5, uri, str, str2);
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isYieldAllowed() {
        return this.mYieldAllowed;
    }

    public boolean isExceptionAllowed() {
        return this.mExceptionAllowed;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isInsert() {
        return this.mType == 1;
    }

    public boolean isDelete() {
        return this.mType == 3;
    }

    public boolean isUpdate() {
        return this.mType == 2;
    }

    public boolean isAssertQuery() {
        return this.mType == 4;
    }

    public boolean isCall() {
        return this.mType == 5;
    }

    public boolean isWriteOperation() {
        int i = this.mType;
        return i == 3 || i == 1 || i == 2;
    }

    public boolean isReadOperation() {
        return this.mType == 4;
    }

    public ContentProviderResult apply(ContentProvider contentProvider, ContentProviderResult[] contentProviderResultArr, int i) throws OperationApplicationException {
        if (this.mExceptionAllowed) {
            try {
                return applyInternal(contentProvider, contentProviderResultArr, i);
            } catch (Exception e) {
                return new ContentProviderResult(e);
            }
        }
        return applyInternal(contentProvider, contentProviderResultArr, i);
    }

    private ContentProviderResult applyInternal(ContentProvider contentProvider, ContentProviderResult[] contentProviderResultArr, int i) throws OperationApplicationException {
        String[] strArr;
        int iUpdate;
        ContentValues contentValuesResolveValueBackReferences = resolveValueBackReferences(contentProviderResultArr, i);
        Bundle bundleResolveExtrasBackReferences = resolveExtrasBackReferences(contentProviderResultArr, i);
        if (this.mSelection != null) {
            if (bundleResolveExtrasBackReferences == null) {
                bundleResolveExtrasBackReferences = new Bundle();
            }
            bundleResolveExtrasBackReferences.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, this.mSelection);
        }
        if (this.mSelectionArgs != null) {
            if (bundleResolveExtrasBackReferences == null) {
                bundleResolveExtrasBackReferences = new Bundle();
            }
            bundleResolveExtrasBackReferences.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, resolveSelectionArgsBackReferences(contentProviderResultArr, i));
        }
        int i2 = this.mType;
        if (i2 == 1) {
            Uri uriInsert = contentProvider.insert(this.mUri, contentValuesResolveValueBackReferences, bundleResolveExtrasBackReferences);
            if (uriInsert != null) {
                return new ContentProviderResult(uriInsert);
            }
            throw new OperationApplicationException("Insert into " + this.mUri + " returned no result");
        }
        if (i2 == 5) {
            return new ContentProviderResult(contentProvider.call(this.mUri.getAuthority(), this.mMethod, this.mArg, bundleResolveExtrasBackReferences));
        }
        if (i2 == 3) {
            iUpdate = contentProvider.delete(this.mUri, bundleResolveExtrasBackReferences);
        } else if (i2 == 2) {
            iUpdate = contentProvider.update(this.mUri, contentValuesResolveValueBackReferences, bundleResolveExtrasBackReferences);
        } else if (i2 == 4) {
            if (contentValuesResolveValueBackReferences != null) {
                ArrayList arrayList = new ArrayList();
                Iterator<Map.Entry<String, Object>> it = contentValuesResolveValueBackReferences.valueSet().iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getKey());
                }
                strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            } else {
                strArr = null;
            }
            Cursor cursorQuery = contentProvider.query(this.mUri, strArr, bundleResolveExtrasBackReferences, null);
            try {
                int count = cursorQuery.getCount();
                if (strArr != null) {
                    while (cursorQuery.moveToNext()) {
                        for (int i3 = 0; i3 < strArr.length; i3++) {
                            String string = cursorQuery.getString(i3);
                            String asString = contentValuesResolveValueBackReferences.getAsString(strArr[i3]);
                            if (!TextUtils.equals(string, asString)) {
                                throw new OperationApplicationException("Found value " + string + " when expected " + asString + " for column " + strArr[i3]);
                            }
                        }
                    }
                }
                cursorQuery.close();
                iUpdate = count;
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        } else {
            throw new IllegalStateException("bad type, " + this.mType);
        }
        Integer num = this.mExpectedCount;
        if (num != null && num.intValue() != iUpdate) {
            throw new OperationApplicationException("Expected " + this.mExpectedCount + " rows but actual " + iUpdate);
        }
        return new ContentProviderResult(iUpdate);
    }

    public ContentValues resolveValueBackReferences(ContentProviderResult[] contentProviderResultArr, int i) {
        if (this.mValues == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        for (int i2 = 0; i2 < this.mValues.size(); i2++) {
            Object objValueAt = this.mValues.valueAt(i2);
            if (objValueAt instanceof BackReference) {
                objValueAt = ((BackReference) objValueAt).resolve(contentProviderResultArr, i);
            }
            contentValues.putObject(this.mValues.keyAt(i2), objValueAt);
        }
        return contentValues;
    }

    public Bundle resolveExtrasBackReferences(ContentProviderResult[] contentProviderResultArr, int i) {
        if (this.mExtras == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        for (int i2 = 0; i2 < this.mExtras.size(); i2++) {
            Object objValueAt = this.mExtras.valueAt(i2);
            if (objValueAt instanceof BackReference) {
                objValueAt = ((BackReference) objValueAt).resolve(contentProviderResultArr, i);
            }
            bundle.putObject(this.mExtras.keyAt(i2), objValueAt);
        }
        return bundle;
    }

    public String[] resolveSelectionArgsBackReferences(ContentProviderResult[] contentProviderResultArr, int i) {
        if (this.mSelectionArgs == null) {
            return null;
        }
        int iMax = -1;
        for (int i2 = 0; i2 < this.mSelectionArgs.size(); i2++) {
            iMax = Math.max(iMax, this.mSelectionArgs.keyAt(i2));
        }
        String[] strArr = new String[iMax + 1];
        for (int i3 = 0; i3 < this.mSelectionArgs.size(); i3++) {
            Object objValueAt = this.mSelectionArgs.valueAt(i3);
            if (objValueAt instanceof BackReference) {
                objValueAt = ((BackReference) objValueAt).resolve(contentProviderResultArr, i);
            }
            strArr[this.mSelectionArgs.keyAt(i3)] = String.valueOf(objValueAt);
        }
        return strArr;
    }

    public static String typeToString(int i) {
        if (i == 1) {
            return "insert";
        }
        if (i == 2) {
            return "update";
        }
        if (i == 3) {
            return "delete";
        }
        if (i == 4) {
            return "assert";
        }
        if (i == 5) {
            return "call";
        }
        return Integer.toString(i);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ContentProviderOperation(");
        sb.append("type=");
        sb.append(typeToString(this.mType));
        sb.append(' ');
        if (this.mUri != null) {
            sb.append("uri=");
            sb.append(this.mUri);
            sb.append(' ');
        }
        if (this.mValues != null) {
            sb.append("values=");
            sb.append(this.mValues);
            sb.append(' ');
        }
        if (this.mSelection != null) {
            sb.append("selection=");
            sb.append(this.mSelection);
            sb.append(' ');
        }
        if (this.mSelectionArgs != null) {
            sb.append("selectionArgs=");
            sb.append(this.mSelectionArgs);
            sb.append(' ');
        }
        if (this.mExpectedCount != null) {
            sb.append("expectedCount=");
            sb.append(this.mExpectedCount);
            sb.append(' ');
        }
        if (this.mYieldAllowed) {
            sb.append("yieldAllowed ");
        }
        if (this.mExceptionAllowed) {
            sb.append("exceptionAllowed ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public static class BackReference implements Parcelable {
        public static final Parcelable.Creator<BackReference> CREATOR = new Parcelable.Creator<BackReference>() { // from class: android.content.ContentProviderOperation.BackReference.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BackReference createFromParcel(Parcel parcel) {
                return new BackReference(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BackReference[] newArray(int i) {
                return new BackReference[i];
            }
        };
        private final int fromIndex;
        private final String fromKey;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private BackReference(int i, String str) {
            this.fromIndex = i;
            this.fromKey = str;
        }

        public BackReference(Parcel parcel) {
            this.fromIndex = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.fromKey = parcel.readString8();
            } else {
                this.fromKey = null;
            }
        }

        public Object resolve(ContentProviderResult[] contentProviderResultArr, int i) {
            int i2 = this.fromIndex;
            if (i2 >= i) {
                Log.e(ContentProviderOperation.TAG, toString());
                throw new ArrayIndexOutOfBoundsException("asked for back ref " + this.fromIndex + " but there are only " + i + " back refs");
            }
            ContentProviderResult contentProviderResult = contentProviderResultArr[i2];
            if (contentProviderResult.extras != null) {
                return contentProviderResult.extras.get(this.fromKey);
            }
            if (contentProviderResult.uri != null) {
                return Long.valueOf(ContentUris.parseId(contentProviderResult.uri));
            }
            return Long.valueOf(contentProviderResult.count.intValue());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.fromIndex);
            if (this.fromKey != null) {
                parcel.writeInt(1);
                parcel.writeString8(this.fromKey);
            } else {
                parcel.writeInt(0);
            }
        }
    }

    public static class Builder {
        private final String mArg;
        private boolean mExceptionAllowed;
        private Integer mExpectedCount;
        private ArrayMap<String, Object> mExtras;
        private final String mMethod;
        private String mSelection;
        private SparseArray<Object> mSelectionArgs;
        private final int mType;
        private final Uri mUri;
        private ArrayMap<String, Object> mValues;
        private boolean mYieldAllowed;

        private Builder(int i, Uri uri) {
            this(i, uri, null, null);
        }

        private Builder(int i, Uri uri, String str, String str2) {
            this.mType = i;
            this.mUri = (Uri) Objects.requireNonNull(uri);
            this.mMethod = str;
            this.mArg = str2;
        }

        public ContentProviderOperation build() {
            ArrayMap<String, Object> arrayMap;
            ArrayMap<String, Object> arrayMap2;
            if (this.mType == 2 && ((arrayMap2 = this.mValues) == null || arrayMap2.isEmpty())) {
                throw new IllegalArgumentException("Empty values");
            }
            if (this.mType == 4 && (((arrayMap = this.mValues) == null || arrayMap.isEmpty()) && this.mExpectedCount == null)) {
                throw new IllegalArgumentException("Empty values");
            }
            return new ContentProviderOperation(this);
        }

        private void ensureValues() {
            if (this.mValues == null) {
                this.mValues = new ArrayMap<>();
            }
        }

        private void ensureExtras() {
            if (this.mExtras == null) {
                this.mExtras = new ArrayMap<>();
            }
        }

        private void ensureSelectionArgs() {
            if (this.mSelectionArgs == null) {
                this.mSelectionArgs = new SparseArray<>();
            }
        }

        private void setValue(String str, Object obj) {
            ensureValues();
            boolean z = obj instanceof BackReference;
            if (!(this.mValues.get(str) instanceof BackReference) || z) {
                this.mValues.put(str, obj);
            }
        }

        private void setExtra(String str, Object obj) {
            ensureExtras();
            boolean z = obj instanceof BackReference;
            if (!(this.mExtras.get(str) instanceof BackReference) || z) {
                this.mExtras.put(str, obj);
            }
        }

        private void setSelectionArg(int i, Object obj) {
            ensureSelectionArgs();
            boolean z = obj instanceof BackReference;
            if (!(this.mSelectionArgs.get(i) instanceof BackReference) || z) {
                this.mSelectionArgs.put(i, obj);
            }
        }

        public Builder withValues(ContentValues contentValues) {
            assertValuesAllowed();
            ensureValues();
            ArrayMap<String, Object> values = contentValues.getValues();
            for (int i = 0; i < values.size(); i++) {
                setValue(values.keyAt(i), values.valueAt(i));
            }
            return this;
        }

        public Builder withValue(String str, Object obj) {
            assertValuesAllowed();
            if (!ContentValues.isSupportedValue(obj)) {
                throw new IllegalArgumentException("bad value type: " + obj.getClass().getName());
            }
            setValue(str, obj);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Builder withValueBackReferences(ContentValues contentValues) {
            assertValuesAllowed();
            ArrayMap<String, Object> values = contentValues.getValues();
            for (int i = 0; i < values.size(); i++) {
                setValue(values.keyAt(i), new BackReference(((Integer) values.valueAt(i)).intValue(), null));
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Builder withValueBackReference(String str, int i) {
            assertValuesAllowed();
            setValue(str, new BackReference(i, null));
            return this;
        }

        public Builder withValueBackReference(String str, int i, String str2) {
            assertValuesAllowed();
            setValue(str, new BackReference(i, str2));
            return this;
        }

        public Builder withExtras(Bundle bundle) {
            assertExtrasAllowed();
            ensureExtras();
            for (String str : bundle.keySet()) {
                setExtra(str, bundle.get(str));
            }
            return this;
        }

        public Builder withExtra(String str, Object obj) {
            assertExtrasAllowed();
            setExtra(str, obj);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Builder withExtraBackReference(String str, int i) {
            assertExtrasAllowed();
            setExtra(str, new BackReference(i, null));
            return this;
        }

        public Builder withExtraBackReference(String str, int i, String str2) {
            assertExtrasAllowed();
            setExtra(str, new BackReference(i, str2));
            return this;
        }

        public Builder withSelection(String str, String[] strArr) {
            assertSelectionAllowed();
            this.mSelection = str;
            if (strArr != null) {
                ensureSelectionArgs();
                for (int i = 0; i < strArr.length; i++) {
                    setSelectionArg(i, strArr[i]);
                }
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Builder withSelectionBackReference(int i, int i2) {
            assertSelectionAllowed();
            setSelectionArg(i, new BackReference(i2, null));
            return this;
        }

        public Builder withSelectionBackReference(int i, int i2, String str) {
            assertSelectionAllowed();
            setSelectionArg(i, new BackReference(i2, str));
            return this;
        }

        public Builder withExpectedCount(int i) {
            int i2 = this.mType;
            if (i2 != 2 && i2 != 3 && i2 != 4) {
                throw new IllegalArgumentException("only updates, deletes, and asserts can have expected counts");
            }
            this.mExpectedCount = Integer.valueOf(i);
            return this;
        }

        public Builder withYieldAllowed(boolean z) {
            this.mYieldAllowed = z;
            return this;
        }

        public Builder withExceptionAllowed(boolean z) {
            this.mExceptionAllowed = z;
            return this;
        }

        public Builder withFailureAllowed(boolean z) {
            return withExceptionAllowed(z);
        }

        private void assertValuesAllowed() {
            int i = this.mType;
            if (i == 1 || i == 2 || i == 4) {
                return;
            }
            throw new IllegalArgumentException("Values not supported for " + ContentProviderOperation.typeToString(this.mType));
        }

        private void assertSelectionAllowed() {
            int i = this.mType;
            if (i == 2 || i == 3 || i == 4) {
                return;
            }
            throw new IllegalArgumentException("Selection not supported for " + ContentProviderOperation.typeToString(this.mType));
        }

        private void assertExtrasAllowed() {
            int i = this.mType;
            if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
                return;
            }
            throw new IllegalArgumentException("Extras not supported for " + ContentProviderOperation.typeToString(this.mType));
        }
    }
}
