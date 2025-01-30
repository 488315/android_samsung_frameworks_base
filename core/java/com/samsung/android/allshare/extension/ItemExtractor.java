package com.samsung.android.allshare.extension;

import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Parcel;
import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.Item;
import com.samsung.android.ims.options.SemCapabilities;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.StringTokenizer;

/* loaded from: classes5.dex */
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
            String itemUri = SemCapabilities.FEATURE_TAG_NULL;
            String subtitle = (uri == null || uri.toString() == null || this.mSubtitle.toString().length() <= 0) ? SemCapabilities.FEATURE_TAG_NULL : this.mSubtitle.toString();
            Uri uri2 = this.mItemUri;
            if (uri2 != null && uri2.toString() != null && this.mItemUri.toString().length() > 0) {
                itemUri = this.mItemUri.toString();
            }
            return this.mItemType + DELIMITER + this.mProviderId + DELIMITER + this.mObjectId + DELIMITER + this.mTitle + DELIMITER + subtitle + DELIMITER + this.mDuration + DELIMITER + itemUri + DELIMITER + this.mMimeType + DELIMITER + this.mFileSize;
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

        @Deprecated
        public static Seed parseSeedString(String seedString) {
            Uri subtitle;
            long duration;
            Uri itemUri;
            long filesize;
            Uri itemUri2;
            Uri subtitle2;
            DLog.v_api(ItemExtractor.CLASS_TAG, "parseSeedString : " + seedString);
            StringTokenizer st = new StringTokenizer(seedString, DELIMITER);
            int count = st.countTokens();
            if (count != 9) {
                DLog.w_api(ItemExtractor.CLASS_TAG, "count : " + count);
                return null;
            }
            String type = st.nextToken();
            String providerId = st.nextToken();
            String objectId = st.nextToken();
            String title = st.nextToken();
            String temp = st.nextToken();
            try {
                if (temp.equals(SemCapabilities.FEATURE_TAG_NULL)) {
                    subtitle2 = null;
                } else {
                    subtitle2 = Uri.parse(temp);
                }
                subtitle = subtitle2;
            } catch (Exception e) {
                subtitle = null;
            }
            try {
                duration = Long.valueOf(st.nextToken()).longValue();
            } catch (Exception e2) {
                duration = -1;
            }
            String temp2 = st.nextToken();
            try {
                if (temp2.equals(SemCapabilities.FEATURE_TAG_NULL)) {
                    itemUri2 = null;
                } else {
                    itemUri2 = Uri.parse(temp2);
                }
                itemUri = itemUri2;
            } catch (Exception e3) {
                itemUri = null;
            }
            String mime = st.nextToken();
            try {
                filesize = Long.valueOf(st.nextToken()).longValue();
            } catch (Exception e4) {
                filesize = -1;
            }
            Seed seed = new Seed();
            seed.mItemType = type;
            seed.mProviderId = providerId;
            seed.mObjectId = objectId;
            seed.mTitle = title;
            seed.mSubtitle = subtitle;
            seed.mDuration = duration;
            seed.mItemUri = itemUri;
            seed.mMimeType = mime;
            seed.mFileSize = filesize;
            return seed;
        }

        public static Seed parseSeedStringUsingSplit(String seedString) {
            Uri subtitle;
            long duration;
            Uri itemUri;
            long filesize;
            Uri itemUri2;
            Uri subtitle2;
            if (seedString != null) {
                DLog.v_api(ItemExtractor.CLASS_TAG, "parseSeedStringUsingSplit : " + seedString);
                String[] seedMember = seedString.split(DELIMITER);
                int count = seedMember.length;
                if (count != 9) {
                    DLog.w_api(ItemExtractor.CLASS_TAG, "count : " + count);
                    return null;
                }
                int index = 0 + 1;
                String type = seedMember[0];
                int index2 = index + 1;
                String providerId = seedMember[index];
                int index3 = index2 + 1;
                String objectId = seedMember[index2];
                int index4 = index3 + 1;
                String title = seedMember[index3];
                int index5 = index4 + 1;
                String temp = seedMember[index4];
                try {
                    if (temp.equals(SemCapabilities.FEATURE_TAG_NULL)) {
                        subtitle2 = null;
                    } else {
                        subtitle2 = Uri.parse(temp);
                    }
                    subtitle = subtitle2;
                } catch (Exception e) {
                    subtitle = null;
                }
                int index6 = index5 + 1;
                try {
                    duration = Long.valueOf(seedMember[index5]).longValue();
                } catch (Exception e2) {
                    duration = -1;
                }
                int index7 = index6 + 1;
                String temp2 = seedMember[index6];
                try {
                    if (temp2.equals(SemCapabilities.FEATURE_TAG_NULL)) {
                        itemUri2 = null;
                    } else {
                        itemUri2 = Uri.parse(temp2);
                    }
                    itemUri = itemUri2;
                } catch (Exception e3) {
                    itemUri = null;
                }
                int index8 = index7 + 1;
                String mime = seedMember[index7];
                int i = index8 + 1;
                try {
                    long filesize2 = Long.valueOf(seedMember[index8]).longValue();
                    filesize = filesize2;
                } catch (Exception e4) {
                    filesize = -1;
                }
                Seed seed = new Seed();
                seed.mItemType = type;
                seed.mProviderId = providerId;
                seed.mObjectId = objectId;
                seed.mTitle = title;
                seed.mSubtitle = subtitle;
                seed.mDuration = duration;
                seed.mItemUri = itemUri;
                seed.mMimeType = mime;
                seed.mFileSize = filesize;
                return seed;
            }
            DLog.w_api(ItemExtractor.CLASS_TAG, "seedString == null");
            return null;
        }
    }

    public static Item create(String seedString) {
        Seed seed = Seed.parseSeedStringUsingSplit(seedString);
        if (seed == null) {
            DLog.w_api(CLASS_TAG, "create : return seed is null");
            return null;
        }
        String typeString = seed.getItemType();
        Item.MediaType type = Item.MediaType.stringToEnum(typeString);
        Bundle bundle = new Bundle();
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TYPE, type.enumToString());
        bundle.putString(AllShareKey.BUNDLE_STRING_OBJECT_ID, seed.getObjectID());
        bundle.putString("BUNDLE_STRING_ID", seed.getProviderID());
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY, "MEDIA_SERVER");
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TITLE, seed.getTitle());
        bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI, seed.getItemUri());
        bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE, seed.getMimeType());
        bundle.putLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE, seed.getFileSize());
        switch (C48961.$SwitchMap$com$samsung$android$allshare$Item$MediaType[type.ordinal()]) {
            case 1:
                bundle.putLong(AllShareKey.BUNDLE_LONG_AUDIO_ITEM_DURATION, seed.getDuration());
                break;
            case 2:
                break;
            case 3:
                bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE, seed.getSubtitle());
                bundle.putLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION, seed.getDuration());
                break;
            case 4:
                break;
            default:
                DLog.w_api(CLASS_TAG, "create : type is " + type);
                break;
        }
        return null;
    }

    /* renamed from: com.samsung.android.allshare.extension.ItemExtractor$1 */
    static /* synthetic */ class C48961 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;

        static {
            int[] iArr = new int[Item.MediaType.values().length];
            $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr;
            try {
                iArr[Item.MediaType.ITEM_AUDIO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[Item.MediaType.ITEM_FOLDER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static Seed extract(Item item) {
        long duration;
        Seed seed;
        if (item == null) {
            DLog.w_api(CLASS_TAG, "extract : return item is null");
            return null;
        }
        Parcel p = Parcel.obtain();
        item.writeToParcel(p, 0);
        p.setDataPosition(0);
        Bundle bundle = p.readBundle();
        p.recycle();
        String typeString = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TYPE);
        Item.MediaType type = Item.MediaType.stringToEnum(typeString);
        String objId = bundle.getString(AllShareKey.BUNDLE_STRING_OBJECT_ID);
        String providerId = bundle.getString("BUNDLE_STRING_ID");
        String constructorKey = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
        String title = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_TITLE);
        Uri uri = (Uri) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI);
        Uri subtitle = null;
        String mime = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
        Long filesize = Long.valueOf(bundle.getLong(AllShareKey.BUNDLE_LONG_ITEM_FILE_SIZE));
        switch (C48961.$SwitchMap$com$samsung$android$allshare$Item$MediaType[type.ordinal()]) {
            case 1:
                long duration2 = bundle.getLong(AllShareKey.BUNDLE_LONG_AUDIO_ITEM_DURATION);
                duration = duration2;
                break;
            case 2:
            default:
                duration = -1;
                break;
            case 3:
                subtitle = (Uri) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_VIDEO_ITEM_SUBTITLE);
                long duration3 = bundle.getLong(AllShareKey.BUNDLE_LONG_VIDEO_ITEM_DURATION);
                duration = duration3;
                break;
        }
        if (objId == null || objId.isEmpty() || providerId == null) {
            seed = null;
        } else {
            if (!providerId.isEmpty()) {
                if (title == null) {
                    DLog.w_api(CLASS_TAG, "extract : Title is null");
                    return null;
                }
                if (constructorKey != null && !constructorKey.equals("MEDIA_SERVER")) {
                    DLog.w_api(CLASS_TAG, "ItemExtractor support only MEDIA_SERVER Item");
                    throw new IllegalArgumentException("ItemExtractor support only MEDIA_SERVER Item");
                }
                if (!objId.contains(",@,#,") && !providerId.contains(",@,#,")) {
                    if (!title.contains(",@,#,")) {
                        Seed seed2 = new Seed();
                        seed2.mItemType = item.getType().enumToString();
                        seed2.mObjectId = objId;
                        seed2.mProviderId = providerId;
                        seed2.mTitle = title;
                        seed2.mSubtitle = subtitle;
                        seed2.mDuration = duration;
                        seed2.mItemUri = uri;
                        seed2.mMimeType = mime;
                        long duration4 = filesize.longValue();
                        seed2.mFileSize = duration4;
                        return seed2;
                    }
                }
                DLog.w_api(CLASS_TAG, "ItemExtractor doesn't suppport object id or provider id that contains DELIMITER");
                return null;
            }
            seed = null;
        }
        DLog.w_api(CLASS_TAG, "extract : return something is empty");
        return seed;
    }
}
