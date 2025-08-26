package android.os;

import android.app.slice.SliceItem;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public final class PersistableBundle extends BaseBundle implements Cloneable, Parcelable, XmlUtils.WriteMapCallback {
    public static final Parcelable.Creator<PersistableBundle> CREATOR;
    public static final PersistableBundle EMPTY;
    private static final String TAG = "PersistableBundle";
    private static final String TAG_PERSISTABLEMAP = "pbundle_as_map";

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        PersistableBundle persistableBundle = new PersistableBundle();
        EMPTY = persistableBundle;
        persistableBundle.mMap = ArrayMap.EMPTY;
        CREATOR = new Parcelable.Creator<PersistableBundle>() { // from class: android.os.PersistableBundle.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PersistableBundle createFromParcel(Parcel parcel) {
                return parcel.readPersistableBundle();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PersistableBundle[] newArray(int i) {
                return new PersistableBundle[i];
            }
        };
    }

    public static boolean isValidType(Object obj) {
        return (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof String) || (obj instanceof int[]) || (obj instanceof long[]) || (obj instanceof double[]) || (obj instanceof String[]) || (obj instanceof PersistableBundle) || obj == null || (obj instanceof Boolean) || (obj instanceof boolean[]);
    }

    public PersistableBundle() {
        this.mFlags = 1;
    }

    public PersistableBundle(int i) {
        super(i);
        this.mFlags = 1;
    }

    public PersistableBundle(PersistableBundle persistableBundle) {
        super(persistableBundle);
        this.mFlags = persistableBundle.mFlags;
    }

    public PersistableBundle(Bundle bundle) {
        this(bundle, true);
    }

    private PersistableBundle(Bundle bundle, boolean z) {
        this(bundle.getItemwiseMap(), z);
    }

    private PersistableBundle(ArrayMap<String, Object> arrayMap, boolean z) {
        this.mFlags = 1;
        putAll(arrayMap);
        for (int size = this.mMap.size() - 1; size >= 0; size--) {
            Object objValueAt = this.mMap.valueAt(size);
            if (objValueAt instanceof ArrayMap) {
                this.mMap.setValueAt(size, new PersistableBundle((ArrayMap<String, Object>) objValueAt, z));
            } else if (objValueAt instanceof Bundle) {
                this.mMap.setValueAt(size, new PersistableBundle((Bundle) objValueAt, z));
            } else if (isValidType(objValueAt)) {
                continue;
            } else {
                String str = "Bad value in PersistableBundle key=" + this.mMap.keyAt(size) + " value=" + objValueAt;
                if (z) {
                    throw new IllegalArgumentException(str);
                }
                Slog.wtfStack(TAG, str);
                this.mMap.removeAt(size);
            }
        }
    }

    PersistableBundle(Parcel parcel, int i) {
        super(parcel, i);
        this.mFlags = 1;
    }

    PersistableBundle(PersistableBundle persistableBundle, boolean z) {
        super(persistableBundle, z);
    }

    public static PersistableBundle forPair(String str, String str2) {
        PersistableBundle persistableBundle = new PersistableBundle(1);
        persistableBundle.putString(str, str2);
        return persistableBundle;
    }

    public Object clone() {
        return new PersistableBundle(this);
    }

    public PersistableBundle deepCopy() {
        return new PersistableBundle(this, true);
    }

    public void putPersistableBundle(String str, PersistableBundle persistableBundle) {
        unparcel();
        this.mMap.put(str, persistableBundle);
    }

    public PersistableBundle getPersistableBundle(String str) {
        unparcel();
        Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (PersistableBundle) obj;
        } catch (ClassCastException e) {
            typeWarning(str, obj, "Bundle", e);
            return null;
        }
    }

    @Override // com.android.internal.util.XmlUtils.WriteMapCallback
    public void writeUnknownObject(Object obj, String str, TypedXmlSerializer typedXmlSerializer) throws XmlPullParserException, IOException {
        if (obj instanceof PersistableBundle) {
            typedXmlSerializer.startTag(null, TAG_PERSISTABLEMAP);
            typedXmlSerializer.attribute(null, "name", str);
            ((PersistableBundle) obj).saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag(null, TAG_PERSISTABLEMAP);
            return;
        }
        throw new XmlPullParserException("Unknown Object o=" + obj);
    }

    public void saveToXml(XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        saveToXml(XmlUtils.makeTyped(xmlSerializer));
    }

    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws XmlPullParserException, IOException {
        unparcel();
        for (int size = this.mMap.size() - 1; size >= 0; size--) {
            Object objValueAt = this.mMap.valueAt(size);
            if (!isValidType(objValueAt)) {
                Slog.e(TAG, "Dropping bad data before persisting: " + this.mMap.keyAt(size) + "=" + objValueAt);
                this.mMap.removeAt(size);
            }
        }
        XmlUtils.writeMapXml(this.mMap, typedXmlSerializer, this);
    }

    public boolean isBundleContentsWithinLengthLimit(int i) {
        unparcel();
        if (this.mMap == null) {
            return true;
        }
        for (int i2 = 0; i2 < this.mMap.size(); i2++) {
            if (this.mMap.keyAt(i2) != null && this.mMap.keyAt(i2).length() > i) {
                return false;
            }
            Object objValueAt = this.mMap.valueAt(i2);
            if ((objValueAt instanceof String) && ((String) objValueAt).length() > i) {
                return false;
            }
            if (objValueAt instanceof String[]) {
                for (String str : (String[]) objValueAt) {
                    if (str != null && str.length() > i) {
                        return false;
                    }
                }
            } else if ((objValueAt instanceof PersistableBundle) && !((PersistableBundle) objValueAt).isBundleContentsWithinLengthLimit(i)) {
                return false;
            }
        }
        return true;
    }

    static class MyReadMapCallback implements XmlUtils.ReadMapCallback {
        MyReadMapCallback() {
        }

        @Override // com.android.internal.util.XmlUtils.ReadMapCallback
        public Object readThisUnknownObjectXml(TypedXmlPullParser typedXmlPullParser, String str) throws XmlPullParserException, IOException {
            if (PersistableBundle.TAG_PERSISTABLEMAP.equals(str)) {
                return PersistableBundle.restoreFromXml(typedXmlPullParser);
            }
            throw new XmlPullParserException("Unknown tag=" + str);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        boolean zPushAllowFds = parcel.pushAllowFds(false);
        try {
            writeToParcelInner(parcel, i);
        } finally {
            parcel.restoreAllowFds(zPushAllowFds);
        }
    }

    public static PersistableBundle restoreFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return restoreFromXml(XmlUtils.makeTyped(xmlPullParser));
    }

    public static PersistableBundle restoreFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        int next;
        int depth = typedXmlPullParser.getDepth();
        String name = typedXmlPullParser.getName();
        String[] strArr = new String[1];
        do {
            next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() >= depth)) {
                return new PersistableBundle();
            }
        } while (next != 2);
        return new PersistableBundle((ArrayMap<String, Object>) XmlUtils.readThisArrayMapXml(typedXmlPullParser, name, strArr, new MyReadMapCallback()), false);
    }

    public synchronized String toString() {
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                return "PersistableBundle[EMPTY_PARCEL]";
            }
            return "PersistableBundle[mParcelledData.dataSize=" + this.mParcelledData.dataSize() + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return "PersistableBundle[" + this.mMap.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public synchronized String toShortString() {
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                return "EMPTY_PARCEL";
            }
            return "mParcelledData.dataSize=" + this.mParcelledData.dataSize();
        }
        return this.mMap.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                protoOutputStream.write(1120986464257L, 0);
            } else {
                protoOutputStream.write(1120986464257L, this.mParcelledData.dataSize());
            }
        } else {
            protoOutputStream.write(1138166333442L, this.mMap.toString());
        }
        protoOutputStream.end(jStart);
    }

    public void writeToStream(OutputStream outputStream) throws IOException {
        TypedXmlSerializer typedXmlSerializerNewFastSerializer = Xml.newFastSerializer();
        typedXmlSerializerNewFastSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        typedXmlSerializerNewFastSerializer.startTag(null, SliceItem.FORMAT_BUNDLE);
        try {
            saveToXml(typedXmlSerializerNewFastSerializer);
            typedXmlSerializerNewFastSerializer.endTag(null, SliceItem.FORMAT_BUNDLE);
            typedXmlSerializerNewFastSerializer.flush();
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
    }

    public static PersistableBundle readFromStream(InputStream inputStream) throws IOException {
        try {
            TypedXmlPullParser typedXmlPullParserNewFastPullParser = Xml.newFastPullParser();
            typedXmlPullParserNewFastPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
            typedXmlPullParserNewFastPullParser.next();
            return restoreFromXml(typedXmlPullParserNewFastPullParser);
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
    }
}
