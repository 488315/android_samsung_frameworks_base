package android.telephony.ims;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.telephony.Rlog;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes4.dex */
public final class RcsConfig {
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_VALUE = "value";
    private static final boolean DBG = Build.IS_ENG;
    private static final String LOG_TAG = "RcsConfig";
    private static final String PARM_SINGLE_REGISTRATION = "rcsVolteSingleRegistration";
    private static final String TAG_CHARACTERISTIC = "characteristic";
    private static final String TAG_PARM = "parm";
    private Characteristic mCurrent;
    private final byte[] mData;
    private final Characteristic mRoot;

    public static class Characteristic {
        private final Characteristic mParent;
        private final Map<String, String> mParms;
        private final Set<Characteristic> mSubs;
        private String mType;

        private Characteristic(String str, Characteristic characteristic) {
            this.mParms = new ArrayMap();
            this.mSubs = new ArraySet();
            this.mType = str;
            this.mParent = characteristic;
        }

        private String getType() {
            return this.mType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, String> getParms() {
            return this.mParms;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Set<Characteristic> getSubs() {
            return this.mSubs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Characteristic getParent() {
            return this.mParent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Characteristic getSubByType(String str) {
            if (TextUtils.equals(this.mType, str)) {
                return this;
            }
            Iterator<Characteristic> it = this.mSubs.iterator();
            Characteristic subByType = null;
            while (it.hasNext() && (subByType = it.next().getSubByType(str)) == null) {
            }
            return subByType;
        }

        private boolean hasSubByType(String str) {
            return getSubByType(str) != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getParmValue(String str) {
            String parmValue = this.mParms.get(str);
            if (parmValue == null) {
                Iterator<Characteristic> it = this.mSubs.iterator();
                while (it.hasNext() && (parmValue = it.next().getParmValue(str)) == null) {
                }
            }
            return parmValue;
        }

        boolean hasParm(String str) {
            if (this.mParms.containsKey(str)) {
                return true;
            }
            Iterator<Characteristic> it = this.mSubs.iterator();
            while (it.hasNext()) {
                if (it.next().hasParm(str)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(NavigationBarInflaterView.SIZE_MOD_START + this.mType + "]: ");
            if (RcsConfig.DBG) {
                sb.append(this.mParms);
            }
            for (Characteristic characteristic : this.mSubs) {
                sb.append(ShaderAssembler.NEWLINE);
                sb.append(characteristic.toString().replace(ShaderAssembler.NEWLINE, "\n\t"));
            }
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Characteristic)) {
                return false;
            }
            Characteristic characteristic = (Characteristic) obj;
            return TextUtils.equals(this.mType, characteristic.mType) && this.mParms.equals(characteristic.mParms) && this.mSubs.equals(characteristic.mSubs);
        }

        public int hashCode() {
            return Objects.hash(this.mType, this.mParms, this.mSubs);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RcsConfig(byte[] bArr) throws IOException, IllegalArgumentException {
        String lowerCase;
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("Empty data");
        }
        Object[] objArr = 0;
        Characteristic characteristic = new Characteristic(null, 0 == true ? 1 : 0);
        this.mRoot = characteristic;
        this.mCurrent = characteristic;
        this.mData = bArr;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            try {
                XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
                xmlPullParserFactoryNewInstance.setNamespaceAware(true);
                XmlPullParser xmlPullParserNewPullParser = xmlPullParserFactoryNewInstance.newPullParser();
                xmlPullParserNewPullParser.setInput(byteArrayInputStream, null);
                for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1 && characteristic != null; eventType = xmlPullParserNewPullParser.next()) {
                    if (eventType == 2) {
                        String lowerCase2 = xmlPullParserNewPullParser.getName().trim().toLowerCase(Locale.ROOT);
                        int i = 0;
                        if (TAG_CHARACTERISTIC.equals(lowerCase2)) {
                            int attributeCount = xmlPullParserNewPullParser.getAttributeCount();
                            if (attributeCount > 0) {
                                while (i < attributeCount) {
                                    String lowerCase3 = xmlPullParserNewPullParser.getAttributeName(i).trim().toLowerCase(Locale.ROOT);
                                    if ("type".equals(lowerCase3)) {
                                        lowerCase = xmlPullParserNewPullParser.getAttributeValue(xmlPullParserNewPullParser.getAttributeNamespace(i), lowerCase3).trim().toLowerCase(Locale.ROOT);
                                        break;
                                    }
                                    i++;
                                }
                                lowerCase = null;
                                Characteristic characteristic2 = new Characteristic(lowerCase, characteristic);
                                characteristic.getSubs().add(characteristic2);
                                characteristic = characteristic2;
                            } else {
                                lowerCase = null;
                                Characteristic characteristic22 = new Characteristic(lowerCase, characteristic);
                                characteristic.getSubs().add(characteristic22);
                                characteristic = characteristic22;
                            }
                        } else if (TAG_PARM.equals(lowerCase2)) {
                            int attributeCount2 = xmlPullParserNewPullParser.getAttributeCount();
                            String lowerCase4 = null;
                            String strTrim = null;
                            if (attributeCount2 > 1) {
                                while (i < attributeCount2) {
                                    String lowerCase5 = xmlPullParserNewPullParser.getAttributeName(i).trim().toLowerCase(Locale.ROOT);
                                    if ("name".equals(lowerCase5)) {
                                        lowerCase4 = xmlPullParserNewPullParser.getAttributeValue(xmlPullParserNewPullParser.getAttributeNamespace(i), lowerCase5).trim().toLowerCase(Locale.ROOT);
                                    } else if ("value".equals(lowerCase5)) {
                                        strTrim = xmlPullParserNewPullParser.getAttributeValue(xmlPullParserNewPullParser.getAttributeNamespace(i), lowerCase5).trim();
                                    }
                                    i++;
                                }
                            }
                            if (lowerCase4 != null && strTrim != null) {
                                characteristic.getParms().put(lowerCase4, strTrim);
                            }
                        }
                    } else if (eventType == 3 && TAG_CHARACTERISTIC.equals(xmlPullParserNewPullParser.getName().trim().toLowerCase(Locale.ROOT))) {
                        characteristic = characteristic.getParent();
                    }
                }
            } catch (IOException | XmlPullParserException e) {
                throw new IllegalArgumentException(e);
            }
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException unused) {
                loge("error to close input stream, skip.");
            }
        }
    }

    public String getString(String str, String str2) {
        String parmValue = this.mCurrent.getParmValue(str.trim().toLowerCase(Locale.ROOT));
        return parmValue != null ? parmValue : str2;
    }

    public int getInteger(String str, int i) {
        try {
            return Integer.parseInt(getString(str, null));
        } catch (NumberFormatException e) {
            logd("error to getInteger for " + str + " due to " + e);
            return i;
        }
    }

    public boolean getBoolean(String str, boolean z) {
        String string = getString(str, null);
        return string != null ? Boolean.parseBoolean(string) : z;
    }

    public boolean hasConfig(String str) {
        return this.mCurrent.hasParm(str.trim().toLowerCase(Locale.ROOT));
    }

    public Characteristic getCharacteristic(String str) {
        return this.mCurrent.getSubByType(str.trim().toLowerCase(Locale.ROOT));
    }

    public boolean hasCharacteristic(String str) {
        return this.mCurrent.getSubByType(str.trim().toLowerCase(Locale.ROOT)) != null;
    }

    public void setCurrentCharacteristic(Characteristic characteristic) {
        if (characteristic != null) {
            this.mCurrent = characteristic;
        }
    }

    public boolean moveToParent() {
        if (this.mCurrent.getParent() == null) {
            return false;
        }
        this.mCurrent = this.mCurrent.getParent();
        return true;
    }

    public void moveToRoot() {
        this.mCurrent = this.mRoot;
    }

    public Characteristic getRoot() {
        return this.mRoot;
    }

    public Characteristic getCurrentCharacteristic() {
        return this.mCurrent;
    }

    public boolean isRcsVolteSingleRegistrationSupported(boolean z) {
        int integer = getInteger(PARM_SINGLE_REGISTRATION, 1);
        return z ? integer == 1 : integer > 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[RCS Config]");
        if (DBG) {
            sb.append("=== Root ===\n");
            sb.append(this.mRoot);
            sb.append("=== Current ===\n");
            sb.append(this.mCurrent);
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RcsConfig)) {
            return false;
        }
        RcsConfig rcsConfig = (RcsConfig) obj;
        return this.mRoot.equals(rcsConfig.mRoot) && this.mCurrent.equals(rcsConfig.mCurrent);
    }

    public int hashCode() {
        return Objects.hash(this.mRoot, this.mCurrent);
    }

    public static byte[] compressGzip(byte[] bArr) throws IOException {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        byte[] byteArray = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (IOException e) {
            loge("Error to compressGzip due to " + e);
            return byteArray;
        }
    }

    public static byte[] decompressGzip(byte[] bArr) throws IOException {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        byte[] byteArray = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] bArr2 = new byte[1024];
            for (int i = gZIPInputStream.read(bArr2); i >= 0; i = gZIPInputStream.read(bArr2)) {
                byteArrayOutputStream.write(bArr2, 0, i);
            }
            gZIPInputStream.close();
            byteArrayInputStream.close();
            byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (IOException e) {
            loge("Error to decompressGzip due to " + e);
            return byteArray;
        }
    }

    public static void updateConfigForSub(Context context, int i, byte[] bArr, boolean z) throws IOException {
        if (!z) {
            bArr = compressGzip(bArr);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(Telephony.SimInfo.COLUMN_RCS_CONFIG, bArr);
        context.getContentResolver().update(Telephony.SimInfo.CONTENT_URI, contentValues, "_id=" + i, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004f A[PHI: r2
      0x004f: PHI (r2v4 byte[]) = (r2v1 byte[]), (r2v7 byte[]) binds: [B:12:0x004d, B:17:0x0059] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] loadRcsConfigForSub(Context context, int i, boolean z) {
        Cursor cursorQuery = context.getContentResolver().query(Telephony.SimInfo.CONTENT_URI, null, "_id=" + i, null, null);
        byte[] blob = null;
        try {
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        blob = cursorQuery.getBlob(cursorQuery.getColumnIndexOrThrow(Telephony.SimInfo.COLUMN_RCS_CONFIG));
                    }
                } catch (Exception e) {
                    loge("error to load rcs config for sub:" + i + " due to " + e);
                    if (cursorQuery != null) {
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } else if (cursorQuery != null) {
            }
            return z ? blob : decompressGzip(blob);
        } finally {
        }
    }

    private static void logd(String str) {
        Rlog.d(LOG_TAG, str);
    }

    private static void loge(String str) {
        Rlog.e(LOG_TAG, str);
    }
}
