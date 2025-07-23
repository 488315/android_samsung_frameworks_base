package android.telephony.ims;

import android.content.ContentValues;
import android.content.Context;
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
            Characteristic characteristic = null;
            while (it.hasNext() && (characteristic = it.next().getSubByType(str)) == null) {
            }
            return characteristic;
        }

        private boolean hasSubByType(String str) {
            return getSubByType(str) != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getParmValue(String str) {
            String str2 = this.mParms.get(str);
            if (str2 == null) {
                Iterator<Characteristic> it = this.mSubs.iterator();
                while (it.hasNext() && (str2 = it.next().getParmValue(str)) == null) {
                }
            }
            return str2;
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

    public RcsConfig(byte[] bArr) throws IllegalArgumentException {
        String str;
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("Empty data");
        }
        byte b = 0;
        Characteristic characteristic = new Characteristic(null, 0 == true ? 1 : 0);
        this.mRoot = characteristic;
        this.mCurrent = characteristic;
        this.mData = bArr;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            try {
                XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
                newInstance.setNamespaceAware(true);
                XmlPullParser newPullParser = newInstance.newPullParser();
                newPullParser.setInput(byteArrayInputStream, null);
                for (int eventType = newPullParser.getEventType(); eventType != 1 && characteristic != null; eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        String lowerCase = newPullParser.getName().trim().toLowerCase(Locale.ROOT);
                        int i = 0;
                        if (TAG_CHARACTERISTIC.equals(lowerCase)) {
                            int attributeCount = newPullParser.getAttributeCount();
                            if (attributeCount > 0) {
                                while (i < attributeCount) {
                                    String lowerCase2 = newPullParser.getAttributeName(i).trim().toLowerCase(Locale.ROOT);
                                    if ("type".equals(lowerCase2)) {
                                        str = newPullParser.getAttributeValue(newPullParser.getAttributeNamespace(i), lowerCase2).trim().toLowerCase(Locale.ROOT);
                                        break;
                                    }
                                    i++;
                                }
                            }
                            str = null;
                            Characteristic characteristic2 = new Characteristic(str, characteristic);
                            characteristic.getSubs().add(characteristic2);
                            characteristic = characteristic2;
                        } else if (TAG_PARM.equals(lowerCase)) {
                            int attributeCount2 = newPullParser.getAttributeCount();
                            String str2 = null;
                            String str3 = null;
                            if (attributeCount2 > 1) {
                                while (i < attributeCount2) {
                                    String lowerCase3 = newPullParser.getAttributeName(i).trim().toLowerCase(Locale.ROOT);
                                    if ("name".equals(lowerCase3)) {
                                        str2 = newPullParser.getAttributeValue(newPullParser.getAttributeNamespace(i), lowerCase3).trim().toLowerCase(Locale.ROOT);
                                    } else if ("value".equals(lowerCase3)) {
                                        str3 = newPullParser.getAttributeValue(newPullParser.getAttributeNamespace(i), lowerCase3).trim();
                                    }
                                    i++;
                                }
                            }
                            if (str2 != null && str3 != null) {
                                characteristic.getParms().put(str2, str3);
                            }
                        }
                    } else if (eventType == 3 && TAG_CHARACTERISTIC.equals(newPullParser.getName().trim().toLowerCase(Locale.ROOT))) {
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

    public static byte[] compressGzip(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        byte[] bArr2 = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr2;
        } catch (IOException e) {
            loge("Error to compressGzip due to " + e);
            return bArr2;
        }
    }

    public static byte[] decompressGzip(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        byte[] bArr2 = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] bArr3 = new byte[1024];
            for (int read = gZIPInputStream.read(bArr3); read >= 0; read = gZIPInputStream.read(bArr3)) {
                byteArrayOutputStream.write(bArr3, 0, read);
            }
            gZIPInputStream.close();
            byteArrayInputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr2;
        } catch (IOException e) {
            loge("Error to decompressGzip due to " + e);
            return bArr2;
        }
    }

    public static void updateConfigForSub(Context context, int i, byte[] bArr, boolean z) {
        if (!z) {
            bArr = compressGzip(bArr);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(Telephony.SimInfo.COLUMN_RCS_CONFIG, bArr);
        context.getContentResolver().update(Telephony.SimInfo.CONTENT_URI, contentValues, "_id=" + i, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004f, code lost:
    
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004d, code lost:
    
        if (r8 == null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0059, code lost:
    
        if (r8 != null) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x005c, code lost:
    
        if (r10 == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0063, code lost:
    
        return decompressGzip(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:?, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] loadRcsConfigForSub(android.content.Context r8, int r9, boolean r10) {
        /*
            java.lang.String r1 = "error to load rcs config for sub:"
            android.content.ContentResolver r2 = r8.getContentResolver()
            android.net.Uri r3 = android.provider.Telephony.SimInfo.CONTENT_URI
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r0 = "_id="
            r8.<init>(r0)
            r8.append(r9)
            java.lang.String r5 = r8.toString()
            r6 = 0
            r7 = 0
            r4 = 0
            android.database.Cursor r8 = r2.query(r3, r4, r5, r6, r7)
            r2 = 0
            if (r8 == 0) goto L59
            boolean r0 = r8.moveToFirst()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            if (r0 == 0) goto L59
            java.lang.String r0 = "rcs_config"
            int r0 = r8.getColumnIndexOrThrow(r0)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            byte[] r2 = r8.getBlob(r0)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            goto L59
        L32:
            r0 = move-exception
            r9 = r0
            goto L53
        L35:
            r0 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L32
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L32
            r3.append(r9)     // Catch: java.lang.Throwable -> L32
            java.lang.String r9 = " due to "
            r3.append(r9)     // Catch: java.lang.Throwable -> L32
            r3.append(r0)     // Catch: java.lang.Throwable -> L32
            java.lang.String r9 = r3.toString()     // Catch: java.lang.Throwable -> L32
            loge(r9)     // Catch: java.lang.Throwable -> L32
            if (r8 == 0) goto L5c
        L4f:
            r8.close()
            goto L5c
        L53:
            if (r8 == 0) goto L58
            r8.close()
        L58:
            throw r9
        L59:
            if (r8 == 0) goto L5c
            goto L4f
        L5c:
            if (r10 == 0) goto L5f
            goto L63
        L5f:
            byte[] r2 = decompressGzip(r2)
        L63:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.RcsConfig.loadRcsConfigForSub(android.content.Context, int, boolean):byte[]");
    }

    private static void logd(String str) {
        Rlog.d(LOG_TAG, str);
    }

    private static void loge(String str) {
        Rlog.e(LOG_TAG, str);
    }
}
