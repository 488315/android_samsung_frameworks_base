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
import java.util.StringTokenizer;

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
            String string = PerfettoProtoLogImpl.NULL_STRING;
            String string2 = (uri == null || uri.toString() == null || this.mSubtitle.toString().length() <= 0) ? PerfettoProtoLogImpl.NULL_STRING : this.mSubtitle.toString();
            Uri uri2 = this.mItemUri;
            if (uri2 != null && uri2.toString() != null && this.mItemUri.toString().length() > 0) {
                string = this.mItemUri.toString();
            }
            return this.mItemType + DELIMITER + this.mProviderId + DELIMITER + this.mObjectId + DELIMITER + this.mTitle + DELIMITER + string2 + DELIMITER + this.mDuration + DELIMITER + string + DELIMITER + this.mMimeType + DELIMITER + this.mFileSize;
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

        /* JADX WARN: Removed duplicated region for block: B:20:0x0074  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0075 A[Catch: Exception -> 0x007a, TRY_LEAVE, TryCatch #1 {Exception -> 0x007a, blocks: (B:18:0x006e, B:21:0x0075), top: B:32:0x006e }] */
        @Deprecated
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static Seed parseSeedString(String str) {
            Uri uri;
            long jLongValue;
            String strNextToken;
            DLog.v_api(ItemExtractor.CLASS_TAG, "parseSeedString : " + str);
            StringTokenizer stringTokenizer = new StringTokenizer(str, DELIMITER);
            int iCountTokens = stringTokenizer.countTokens();
            Uri uri2 = null;
            if (iCountTokens != 9) {
                DLog.w_api(ItemExtractor.CLASS_TAG, "count : " + iCountTokens);
                return null;
            }
            String strNextToken2 = stringTokenizer.nextToken();
            String strNextToken3 = stringTokenizer.nextToken();
            String strNextToken4 = stringTokenizer.nextToken();
            String strNextToken5 = stringTokenizer.nextToken();
            String strNextToken6 = stringTokenizer.nextToken();
            if (!strNextToken6.equals(PerfettoProtoLogImpl.NULL_STRING)) {
                uri = Uri.parse(strNextToken6);
                long jLongValue2 = -1;
                jLongValue = Long.valueOf(stringTokenizer.nextToken()).longValue();
                strNextToken = stringTokenizer.nextToken();
                if (strNextToken.equals(PerfettoProtoLogImpl.NULL_STRING)) {
                }
                String strNextToken7 = stringTokenizer.nextToken();
                jLongValue2 = Long.valueOf(stringTokenizer.nextToken()).longValue();
                Seed seed = new Seed();
                seed.mItemType = strNextToken2;
                seed.mProviderId = strNextToken3;
                seed.mObjectId = strNextToken4;
                seed.mTitle = strNextToken5;
                seed.mSubtitle = uri;
                seed.mDuration = jLongValue;
                seed.mItemUri = uri2;
                seed.mMimeType = strNextToken7;
                seed.mFileSize = jLongValue2;
                return seed;
            }
            uri = null;
            long jLongValue22 = -1;
            try {
                jLongValue = Long.valueOf(stringTokenizer.nextToken()).longValue();
            } catch (Exception unused) {
                jLongValue = -1;
            }
            strNextToken = stringTokenizer.nextToken();
            try {
                if (strNextToken.equals(PerfettoProtoLogImpl.NULL_STRING)) {
                    uri2 = Uri.parse(strNextToken);
                }
            } catch (Exception unused2) {
            }
            String strNextToken72 = stringTokenizer.nextToken();
            try {
                jLongValue22 = Long.valueOf(stringTokenizer.nextToken()).longValue();
            } catch (Exception unused3) {
            }
            Seed seed2 = new Seed();
            seed2.mItemType = strNextToken2;
            seed2.mProviderId = strNextToken3;
            seed2.mObjectId = strNextToken4;
            seed2.mTitle = strNextToken5;
            seed2.mSubtitle = uri;
            seed2.mDuration = jLongValue;
            seed2.mItemUri = uri2;
            seed2.mMimeType = strNextToken72;
            seed2.mFileSize = jLongValue22;
            return seed2;
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x0072  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0073 A[Catch: Exception -> 0x0078, TRY_LEAVE, TryCatch #1 {Exception -> 0x0078, blocks: (B:22:0x006c, B:25:0x0073), top: B:36:0x006c }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static Seed parseSeedStringUsingSplit(String str) {
            Uri uri;
            long jLongValue;
            String str2;
            Uri uri2 = null;
            if (str == null) {
                DLog.w_api(ItemExtractor.CLASS_TAG, "seedString == null");
                return null;
            }
            DLog.v_api(ItemExtractor.CLASS_TAG, "parseSeedStringUsingSplit : " + str);
            String[] strArrSplit = str.split(DELIMITER);
            int length = strArrSplit.length;
            if (length != 9) {
                DLog.w_api(ItemExtractor.CLASS_TAG, "count : " + length);
                return null;
            }
            String str3 = strArrSplit[0];
            String str4 = strArrSplit[1];
            String str5 = strArrSplit[2];
            String str6 = strArrSplit[3];
            String str7 = strArrSplit[4];
            if (!str7.equals(PerfettoProtoLogImpl.NULL_STRING)) {
                uri = Uri.parse(str7);
                long jLongValue2 = -1;
                jLongValue = Long.valueOf(strArrSplit[5]).longValue();
                str2 = strArrSplit[6];
                if (str2.equals(PerfettoProtoLogImpl.NULL_STRING)) {
                }
                String str8 = strArrSplit[7];
                jLongValue2 = Long.valueOf(strArrSplit[8]).longValue();
                Seed seed = new Seed();
                seed.mItemType = str3;
                seed.mProviderId = str4;
                seed.mObjectId = str5;
                seed.mTitle = str6;
                seed.mSubtitle = uri;
                seed.mDuration = jLongValue;
                seed.mItemUri = uri2;
                seed.mMimeType = str8;
                seed.mFileSize = jLongValue2;
                return seed;
            }
            uri = null;
            long jLongValue22 = -1;
            try {
                jLongValue = Long.valueOf(strArrSplit[5]).longValue();
            } catch (Exception unused) {
                jLongValue = -1;
            }
            str2 = strArrSplit[6];
            try {
                if (str2.equals(PerfettoProtoLogImpl.NULL_STRING)) {
                    uri2 = Uri.parse(str2);
                }
            } catch (Exception unused2) {
            }
            String str82 = strArrSplit[7];
            try {
                jLongValue22 = Long.valueOf(strArrSplit[8]).longValue();
            } catch (Exception unused3) {
            }
            Seed seed2 = new Seed();
            seed2.mItemType = str3;
            seed2.mProviderId = str4;
            seed2.mObjectId = str5;
            seed2.mTitle = str6;
            seed2.mSubtitle = uri;
            seed2.mDuration = jLongValue;
            seed2.mItemUri = uri2;
            seed2.mMimeType = str82;
            seed2.mFileSize = jLongValue22;
            return seed2;
        }
    }

    public static Item create(String str) {
        Seed seedStringUsingSplit = Seed.parseSeedStringUsingSplit(str);
        if (seedStringUsingSplit == null) {
            DLog.w_api(CLASS_TAG, "create : return seed is null");
            return null;
        }
        Item.MediaType mediaTypeStringToEnum = Item.MediaType.stringToEnum(seedStringUsingSplit.getItemType());
        Bundle bundle = new Bundle();
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TYPE, mediaTypeStringToEnum.enumToString());
        bundle.putString(AllShareKey.BUNDLE_STRING_OBJECT_ID, seedStringUsingSplit.getObjectID());
        bundle.putString("BUNDLE_STRING_ID", seedStringUsingSplit.getProviderID());
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY, "MEDIA_SERVER");
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TITLE, seedStringUsingSplit.getTitle());
        bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI, seedStringUsingSplit.getItemUri());
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE, seedStringUsingSplit.getMimeType());
        bundle.putLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE, seedStringUsingSplit.getFileSize());
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$allshare$Item$MediaType[mediaTypeStringToEnum.ordinal()];
        if (i == 1) {
            bundle.putLong(AllShareKey.BUNDLE_LONG_AUDIO_ITEM_DURATION, seedStringUsingSplit.getDuration());
            return new SimpleAudioItem(bundle);
        }
        if (i == 2) {
            return new SimpleImageItem(bundle);
        }
        if (i == 3) {
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE, seedStringUsingSplit.getSubtitle());
            bundle.putLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION, seedStringUsingSplit.getDuration());
            return new SimpleVideoItem(bundle);
        }
        if (i == 4) {
            return new SimpleFolderItem(bundle);
        }
        DLog.w_api(CLASS_TAG, "create : type is " + mediaTypeStringToEnum);
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
        Parcel parcelObtain = Parcel.obtain();
        item.writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        Bundle bundle = parcelObtain.readBundle();
        parcelObtain.recycle();
        Item.MediaType mediaTypeStringToEnum = Item.MediaType.stringToEnum(bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE));
        String string = bundle.getString(AllShareKey.BUNDLE_STRING_OBJECT_ID);
        String string2 = bundle.getString("BUNDLE_STRING_ID");
        String string3 = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
        String string4 = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE);
        Uri uri2 = (Uri) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI);
        String string5 = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
        long j3 = bundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE);
        Long lValueOf = Long.valueOf(j3);
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$allshare$Item$MediaType[mediaTypeStringToEnum.ordinal()];
        if (i == 1) {
            j = bundle.getLong(AllShareKey.BUNDLE_LONG_AUDIO_ITEM_DURATION);
        } else {
            if (i == 3) {
                uri = (Uri) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE);
                j2 = bundle.getLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION);
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
                seed.mItemType = mediaTypeStringToEnum.toString();
                seed.mObjectId = string;
                seed.mProviderId = string2;
                seed.mTitle = string4;
                seed.mSubtitle = uri;
                seed.mDuration = j2;
                seed.mItemUri = uri2;
                seed.mMimeType = string5;
                lValueOf.getClass();
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
