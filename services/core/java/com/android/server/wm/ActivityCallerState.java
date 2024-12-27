package com.android.server.wm;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.BadParcelableException;
import android.util.ArraySet;
import android.util.Slog;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.uri.GrantUri;

import com.samsung.android.knoxguard.service.SystemIntentProcessor;

import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActivityCallerState {
    public final ActivityTaskManagerService mAtmService;
    public final WeakHashMap mCallerTokenInfoMap = new WeakHashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallerInfo {
        public final boolean mIsShareIdentityEnabled;
        public final String mPackageName;
        public final int mUid;
        public final ArraySet mReadableContentUris = new ArraySet();
        public final ArraySet mWritableContentUris = new ArraySet();
        public final ArraySet mInaccessibleContentUris = new ArraySet();

        public CallerInfo(int i, String str, boolean z) {
            this.mUid = i;
            this.mPackageName = str;
            this.mIsShareIdentityEnabled = z;
        }

        public static GrantUri restoreGrantUriFromXml(TypedXmlPullParser typedXmlPullParser) {
            return new GrantUri(
                    typedXmlPullParser.getAttributeInt((String) null, "source_user_id", 0),
                    typedXmlPullParser.getAttributeBoolean((String) null, "prefix", false)
                            ? 128
                            : 0,
                    Uri.parse(
                            typedXmlPullParser.getAttributeValue(
                                    (String) null, SystemIntentProcessor.KEY_URI)));
        }

        public static void saveGrantUriToXml(
                TypedXmlSerializer typedXmlSerializer, GrantUri grantUri, String str) {
            typedXmlSerializer.startTag((String) null, str);
            typedXmlSerializer.attributeInt((String) null, "source_user_id", grantUri.sourceUserId);
            typedXmlSerializer.attribute(
                    (String) null, SystemIntentProcessor.KEY_URI, String.valueOf(grantUri.uri));
            typedXmlSerializer.attributeBoolean((String) null, "prefix", grantUri.prefix);
            typedXmlSerializer.endTag((String) null, str);
        }
    }

    public ActivityCallerState(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtmService = activityTaskManagerService;
    }

    public static void addUriIfContentUri(Uri uri, ArraySet arraySet) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        arraySet.add(uri);
    }

    public static ArraySet getContentUrisFromIntent(Intent intent) {
        ArrayList arrayList;
        ArraySet arraySet = new ArraySet();
        if (intent == null) {
            return arraySet;
        }
        addUriIfContentUri(intent.getData(), arraySet);
        if (intent.hasExtra("android.intent.extra.STREAM")) {
            Uri uri = null;
            try {
                arrayList =
                        intent.getParcelableArrayListExtra(
                                "android.intent.extra.STREAM", Uri.class);
            } catch (BadParcelableException e) {
                Slog.w(
                        "ActivityTaskManager",
                        "Failed to unparcel an ArrayList of URIs in EXTRA_STREAM, returning null: "
                                + e);
                arrayList = null;
            }
            if (arrayList == null) {
                try {
                    uri = (Uri) intent.getParcelableExtra("android.intent.extra.STREAM", Uri.class);
                } catch (BadParcelableException e2) {
                    Slog.w(
                            "ActivityTaskManager",
                            "Failed to unparcel an URI in EXTRA_STREAM, returning null: " + e2);
                }
                addUriIfContentUri(uri, arraySet);
            } else {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    addUriIfContentUri((Uri) arrayList.get(size), arraySet);
                }
            }
        }
        ClipData clipData = intent.getClipData();
        if (clipData == null) {
            return arraySet;
        }
        for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item itemAt = clipData.getItemAt(i);
            addUriIfContentUri(itemAt.getUri(), arraySet);
            arraySet.addAll(getContentUrisFromIntent(itemAt.getIntent()));
        }
        return arraySet;
    }
}
