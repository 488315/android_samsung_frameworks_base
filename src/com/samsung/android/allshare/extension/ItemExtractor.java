package com.samsung.android.allshare.extension;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.extension.impl.SimpleAudioItem;
import com.samsung.android.allshare.extension.impl.SimpleFolderItem;
import com.samsung.android.allshare.extension.impl.SimpleImageItem;
import com.samsung.android.allshare.extension.impl.SimpleVideoItem;
import com.sec.android.allshare.iface.message.AllShareKey;

/* loaded from: classes6.dex */
public class ItemExtractor {
    private static final String CLASS_TAG = "ItemExtractor";

    public static class Seed {
        private static final String DELIMITER = ",@,#,";
        private static final int FIELD_NUMBER = 9;
        private long mDuration;
        private long mFileSize;
        private String mItemType;
        private Uri mItemUri;
        private String mMimeType;
        private String mObjectId;
        private String mProviderId;
        private Uri mSubtitle;
        private String mTitle;

        private Seed() {
            this.mObjectId = "";
            this.mProviderId = "";
            this.mItemType = "";
            this.mTitle = "";
            this.mSubtitle = null;
            this.mDuration = -1L;
            this.mItemUri = null;
            this.mMimeType = "";
            this.mFileSize = 0L;
        }

        public String getSeedString() {
            Uri uri = this.mSubtitle;
            String str = PerfettoProtoLogImpl.NULL_STRING;
            String uri2 = (uri == null || uri.toString() == null || this.mSubtitle.toString().length() <= 0) ? PerfettoProtoLogImpl.NULL_STRING : this.mSubtitle.toString();
            Uri uri3 = this.mItemUri;
            if (uri3 != null && uri3.toString() != null && this.mItemUri.toString().length() > 0) {
                str = this.mItemUri.toString();
            }
            return this.mItemType + DELIMITER + this.mProviderId + DELIMITER + this.mObjectId + DELIMITER + this.mTitle + DELIMITER + uri2 + DELIMITER + this.mDuration + DELIMITER + str + DELIMITER + this.mMimeType + DELIMITER + this.mFileSize;
        }

        public String getObjectID() {
            return this.mObjectId;
        }

        public String getProviderID() {
            return this.mProviderId;
        }

        public String getItemType() {
            return this.mItemType;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public Uri getSubtitle() {
            return this.mSubtitle;
        }

        public long getDuration() {
            return this.mDuration;
        }

        public Uri getItemUri() {
            return this.mItemUri;
        }

        public String getMimeType() {
            return this.mMimeType;
        }

        public long getFileSize() {
            return this.mFileSize;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0074  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0075 A[Catch: Exception -> 0x007a, TRY_LEAVE, TryCatch #1 {Exception -> 0x007a, blocks: (B:17:0x006e, B:27:0x0075), top: B:16:0x006e }] */
        @java.lang.Deprecated
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.samsung.android.allshare.extension.ItemExtractor.Seed parseSeedString(java.lang.String r12) {
            /*
                java.lang.String r0 = "null"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "parseSeedString : "
                r1.<init>(r2)
                r1.append(r12)
                java.lang.String r1 = r1.toString()
                java.lang.String r2 = "ItemExtractor"
                com.samsung.android.allshare.DLog.v_api(r2, r1)
                java.util.StringTokenizer r1 = new java.util.StringTokenizer
                java.lang.String r3 = ",@,#,"
                r1.<init>(r12, r3)
                int r12 = r1.countTokens()
                r3 = 9
                r4 = 0
                if (r12 == r3) goto L39
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "count : "
                r0.<init>(r1)
                r0.append(r12)
                java.lang.String r12 = r0.toString()
                com.samsung.android.allshare.DLog.w_api(r2, r12)
                return r4
            L39:
                java.lang.String r12 = r1.nextToken()
                java.lang.String r2 = r1.nextToken()
                java.lang.String r3 = r1.nextToken()
                java.lang.String r5 = r1.nextToken()
                java.lang.String r6 = r1.nextToken()
                boolean r7 = r6.equals(r0)     // Catch: java.lang.Exception -> L59
                if (r7 == 0) goto L54
                goto L59
            L54:
                android.net.Uri r6 = android.net.Uri.parse(r6)     // Catch: java.lang.Exception -> L59
                goto L5a
            L59:
                r6 = r4
            L5a:
                java.lang.String r7 = r1.nextToken()
                r8 = -1
                java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Exception -> L69
                long r10 = r7.longValue()     // Catch: java.lang.Exception -> L69
                goto L6a
            L69:
                r10 = r8
            L6a:
                java.lang.String r7 = r1.nextToken()
                boolean r0 = r7.equals(r0)     // Catch: java.lang.Exception -> L7a
                if (r0 == 0) goto L75
                goto L7a
            L75:
                android.net.Uri r0 = android.net.Uri.parse(r7)     // Catch: java.lang.Exception -> L7a
                r4 = r0
            L7a:
                java.lang.String r0 = r1.nextToken()
                java.lang.String r1 = r1.nextToken()
                java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch: java.lang.Exception -> L8a
                long r8 = r1.longValue()     // Catch: java.lang.Exception -> L8a
            L8a:
                com.samsung.android.allshare.extension.ItemExtractor$Seed r1 = new com.samsung.android.allshare.extension.ItemExtractor$Seed
                r1.<init>()
                r1.mItemType = r12
                r1.mProviderId = r2
                r1.mObjectId = r3
                r1.mTitle = r5
                r1.mSubtitle = r6
                r1.mDuration = r10
                r1.mItemUri = r4
                r1.mMimeType = r0
                r1.mFileSize = r8
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.allshare.extension.ItemExtractor.Seed.parseSeedString(java.lang.String):com.samsung.android.allshare.extension.ItemExtractor$Seed");
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0072  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0073 A[Catch: Exception -> 0x0078, TRY_LEAVE, TryCatch #1 {Exception -> 0x0078, blocks: (B:21:0x006c, B:31:0x0073), top: B:20:0x006c }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.samsung.android.allshare.extension.ItemExtractor.Seed parseSeedStringUsingSplit(java.lang.String r12) {
            /*
                java.lang.String r0 = "null"
                java.lang.String r1 = "ItemExtractor"
                r2 = 0
                if (r12 != 0) goto Lf
                java.lang.String r12 = "seedString == null"
                com.samsung.android.allshare.DLog.w_api(r1, r12)
                return r2
            Lf:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r4 = "parseSeedStringUsingSplit : "
                r3.<init>(r4)
                r3.append(r12)
                java.lang.String r3 = r3.toString()
                com.samsung.android.allshare.DLog.v_api(r1, r3)
                java.lang.String r3 = ",@,#,"
                java.lang.String[] r12 = r12.split(r3)
                int r3 = r12.length
                r4 = 9
                if (r3 == r4) goto L3e
                java.lang.StringBuilder r12 = new java.lang.StringBuilder
                java.lang.String r0 = "count : "
                r12.<init>(r0)
                r12.append(r3)
                java.lang.String r12 = r12.toString()
                com.samsung.android.allshare.DLog.w_api(r1, r12)
                return r2
            L3e:
                r1 = 0
                r1 = r12[r1]
                r3 = 1
                r3 = r12[r3]
                r4 = 2
                r4 = r12[r4]
                r5 = 3
                r5 = r12[r5]
                r6 = 4
                r6 = r12[r6]
                boolean r7 = r6.equals(r0)     // Catch: java.lang.Exception -> L59
                if (r7 == 0) goto L54
                goto L59
            L54:
                android.net.Uri r6 = android.net.Uri.parse(r6)     // Catch: java.lang.Exception -> L59
                goto L5a
            L59:
                r6 = r2
            L5a:
                r7 = 5
                r7 = r12[r7]
                r8 = -1
                java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Exception -> L68
                long r10 = r7.longValue()     // Catch: java.lang.Exception -> L68
                goto L69
            L68:
                r10 = r8
            L69:
                r7 = 6
                r7 = r12[r7]
                boolean r0 = r7.equals(r0)     // Catch: java.lang.Exception -> L78
                if (r0 == 0) goto L73
                goto L78
            L73:
                android.net.Uri r0 = android.net.Uri.parse(r7)     // Catch: java.lang.Exception -> L78
                r2 = r0
            L78:
                r0 = 7
                r0 = r12[r0]
                r7 = 8
                r12 = r12[r7]
                java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch: java.lang.Exception -> L87
                long r8 = r12.longValue()     // Catch: java.lang.Exception -> L87
            L87:
                com.samsung.android.allshare.extension.ItemExtractor$Seed r12 = new com.samsung.android.allshare.extension.ItemExtractor$Seed
                r12.<init>()
                r12.mItemType = r1
                r12.mProviderId = r3
                r12.mObjectId = r4
                r12.mTitle = r5
                r12.mSubtitle = r6
                r12.mDuration = r10
                r12.mItemUri = r2
                r12.mMimeType = r0
                r12.mFileSize = r8
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.allshare.extension.ItemExtractor.Seed.parseSeedStringUsingSplit(java.lang.String):com.samsung.android.allshare.extension.ItemExtractor$Seed");
        }
    }

    public static Item create(String str) {
        Seed parseSeedStringUsingSplit = Seed.parseSeedStringUsingSplit(str);
        if (parseSeedStringUsingSplit == null) {
            DLog.w_api(CLASS_TAG, "create : return seed is null");
            return null;
        }
        Item.MediaType stringToEnum = Item.MediaType.stringToEnum(parseSeedStringUsingSplit.getItemType());
        Bundle bundle = new Bundle();
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TYPE, stringToEnum.enumToString());
        bundle.putString(AllShareKey.BUNDLE_STRING_OBJECT_ID, parseSeedStringUsingSplit.getObjectID());
        bundle.putString("BUNDLE_STRING_ID", parseSeedStringUsingSplit.getProviderID());
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY, "MEDIA_SERVER");
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TITLE, parseSeedStringUsingSplit.getTitle());
        bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI, parseSeedStringUsingSplit.getItemUri());
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE, parseSeedStringUsingSplit.getMimeType());
        bundle.putLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE, parseSeedStringUsingSplit.getFileSize());
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$allshare$Item$MediaType[stringToEnum.ordinal()];
        if (i == 1) {
            bundle.putLong(AllShareKey.BUNDLE_LONG_AUDIO_ITEM_DURATION, parseSeedStringUsingSplit.getDuration());
            return new SimpleAudioItem(bundle);
        }
        if (i == 2) {
            return new SimpleImageItem(bundle);
        }
        if (i == 3) {
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE, parseSeedStringUsingSplit.getSubtitle());
            bundle.putLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION, parseSeedStringUsingSplit.getDuration());
            return new SimpleVideoItem(bundle);
        }
        if (i == 4) {
            return new SimpleFolderItem(bundle);
        }
        DLog.w_api(CLASS_TAG, "create : type is " + stringToEnum);
        return null;
    }

    /* renamed from: com.samsung.android.allshare.extension.ItemExtractor$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;

        static {
            int[] iArr = new int[Item.MediaType.values().length];
            $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr;
            try {
                iArr[Item.MediaType.ITEM_AUDIO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_FOLDER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static Seed extract(Item item) {
        long j;
        long j2;
        Uri uri;
        if (item == null) {
            DLog.w_api(CLASS_TAG, "extract : return item is null");
            return null;
        }
        Parcel obtain = Parcel.obtain();
        item.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        Bundle readBundle = obtain.readBundle();
        obtain.recycle();
        Item.MediaType stringToEnum = Item.MediaType.stringToEnum(readBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE));
        String string = readBundle.getString(AllShareKey.BUNDLE_STRING_OBJECT_ID);
        String string2 = readBundle.getString("BUNDLE_STRING_ID");
        String string3 = readBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
        String string4 = readBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE);
        Uri uri2 = (Uri) readBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI);
        String string5 = readBundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
        long j3 = readBundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE);
        Long valueOf = Long.valueOf(j3);
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$allshare$Item$MediaType[stringToEnum.ordinal()];
        if (i == 1) {
            j = readBundle.getLong(AllShareKey.BUNDLE_LONG_AUDIO_ITEM_DURATION);
        } else {
            if (i == 3) {
                uri = (Uri) readBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE);
                j2 = readBundle.getLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION);
                if (string != null || string.isEmpty() || string2 == null || string2.isEmpty()) {
                    DLog.w_api(CLASS_TAG, "extract : return something is empty");
                    return null;
                }
                if (string4 == null) {
                    DLog.w_api(CLASS_TAG, "extract : Title is null");
                    return null;
                }
                if (string3 != null && !string3.equals("MEDIA_SERVER")) {
                    DLog.w_api(CLASS_TAG, "ItemExtractor support only MEDIA_SERVER Item");
                    throw new IllegalArgumentException("ItemExtractor support only MEDIA_SERVER Item");
                }
                if (string.contains(",@,#,") || string2.contains(",@,#,") || string4.contains(",@,#,")) {
                    DLog.w_api(CLASS_TAG, "ItemExtractor doesn't suppport object id or provider id that contains DELIMITER");
                    return null;
                }
                Seed seed = new Seed();
                seed.mItemType = stringToEnum.toString();
                seed.mObjectId = string;
                seed.mProviderId = string2;
                seed.mTitle = string4;
                seed.mSubtitle = uri;
                seed.mDuration = j2;
                seed.mItemUri = uri2;
                seed.mMimeType = string5;
                valueOf.getClass();
                seed.mFileSize = j3;
                return seed;
            }
            j = -1;
        }
        j2 = j;
        uri = null;
        if (string != null) {
        }
        DLog.w_api(CLASS_TAG, "extract : return something is empty");
        return null;
    }
}
