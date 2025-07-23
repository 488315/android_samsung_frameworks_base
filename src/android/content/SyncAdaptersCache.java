package android.content;

import android.content.pm.RegisteredServicesCache;
import android.content.pm.XmlSerializerAndParser;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.SparseArray;
import com.android.internal.R;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SyncAdaptersCache extends RegisteredServicesCache<SyncAdapterType> {
    private static final String ATTRIBUTES_NAME = "sync-adapter";
    private static final String SERVICE_INTERFACE = "android.content.SyncAdapter";
    private static final String SERVICE_META_DATA = "android.content.SyncAdapter";
    private static final String TAG = "Account";
    private static final MySerializer sSerializer = new MySerializer();
    private SparseArray<ArrayMap<String, String[]>> mAuthorityToSyncAdapters;

    public SyncAdaptersCache(Context context) {
        super(context, "android.content.SyncAdapter", "android.content.SyncAdapter", ATTRIBUTES_NAME, sSerializer);
        this.mAuthorityToSyncAdapters = new SparseArray<>();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.content.pm.RegisteredServicesCache
    public SyncAdapterType parseServiceAttributes(Resources resources, String str, AttributeSet attributeSet) {
        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.SyncAdapter);
        try {
            String string = obtainAttributes.getString(2);
            String string2 = obtainAttributes.getString(1);
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                return new SyncAdapterType(string, string2, obtainAttributes.getBoolean(3, true), obtainAttributes.getBoolean(4, true), obtainAttributes.getBoolean(6, false), obtainAttributes.getBoolean(5, false), obtainAttributes.getString(0), str);
            }
            obtainAttributes.recycle();
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    @Override // android.content.pm.RegisteredServicesCache
    protected void onServicesChangedLocked(int i) {
        synchronized (this.mServicesLock) {
            ArrayMap<String, String[]> arrayMap = this.mAuthorityToSyncAdapters.get(i);
            if (arrayMap != null) {
                arrayMap.clear();
            }
        }
        super.onServicesChangedLocked(i);
    }

    public String[] getSyncAdapterPackagesForAuthority(String str, int i) {
        synchronized (this.mServicesLock) {
            ArrayMap<String, String[]> arrayMap = this.mAuthorityToSyncAdapters.get(i);
            if (arrayMap == null) {
                arrayMap = new ArrayMap<>();
                this.mAuthorityToSyncAdapters.put(i, arrayMap);
            }
            if (arrayMap.containsKey(str)) {
                return arrayMap.get(str);
            }
            Collection<RegisteredServicesCache.ServiceInfo<SyncAdapterType>> allServices = getAllServices(i);
            ArrayList arrayList = new ArrayList();
            for (RegisteredServicesCache.ServiceInfo<SyncAdapterType> serviceInfo : allServices) {
                if (str.equals(serviceInfo.type.authority) && serviceInfo.componentName != null) {
                    arrayList.add(serviceInfo.componentName.getPackageName());
                }
            }
            String[] strArr = new String[arrayList.size()];
            arrayList.toArray(strArr);
            arrayMap.put(str, strArr);
            return strArr;
        }
    }

    @Override // android.content.pm.RegisteredServicesCache
    protected void onUserRemoved(int i) {
        synchronized (this.mServicesLock) {
            this.mAuthorityToSyncAdapters.remove(i);
        }
        super.onUserRemoved(i);
    }

    static class MySerializer implements XmlSerializerAndParser<SyncAdapterType> {
        MySerializer() {
        }

        @Override // android.content.pm.XmlSerializerAndParser
        public void writeAsXml(SyncAdapterType syncAdapterType, TypedXmlSerializer typedXmlSerializer) throws IOException {
            typedXmlSerializer.attribute(null, ContactsContract.Directory.DIRECTORY_AUTHORITY, syncAdapterType.authority);
            typedXmlSerializer.attribute(null, "accountType", syncAdapterType.accountType);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.pm.XmlSerializerAndParser
        public SyncAdapterType createFromXml(TypedXmlPullParser typedXmlPullParser) throws IOException, XmlPullParserException {
            return SyncAdapterType.newKey(typedXmlPullParser.getAttributeValue(null, ContactsContract.Directory.DIRECTORY_AUTHORITY), typedXmlPullParser.getAttributeValue(null, "accountType"));
        }
    }
}
