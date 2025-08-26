package android.sec.clipboard.data;

import android.os.Parcel;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.content.clipboard.data.SemHtmlClipData;
import com.samsung.android.content.clipboard.data.SemImageClipData;
import com.samsung.android.content.clipboard.data.SemIntentClipData;
import com.samsung.android.content.clipboard.data.SemTextClipData;
import com.samsung.android.content.clipboard.data.SemUriClipData;
import com.samsung.android.content.clipboard.data.SemUriListClipData;

/* loaded from: classes3.dex */
public class ClipboardDataFactory {
    public static SemClipData createClipBoardData(int i) {
        if (i == 1) {
            return new SemTextClipData();
        }
        if (i == 2) {
            return new SemImageClipData();
        }
        if (i == 4) {
            return new SemHtmlClipData();
        }
        if (i == 8) {
            return new SemIntentClipData();
        }
        if (i == 16) {
            return new SemUriClipData();
        }
        if (i != 32) {
            return null;
        }
        return new SemUriListClipData();
    }

    public static SemClipData createClipBoardData(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 1) {
            return new SemTextClipData(parcel);
        }
        if (i == 2) {
            return new SemImageClipData(parcel);
        }
        if (i == 4) {
            return new SemHtmlClipData(parcel);
        }
        if (i == 8) {
            return new SemIntentClipData(parcel);
        }
        if (i == 16) {
            return new SemUriClipData(parcel);
        }
        if (i != 32) {
            return null;
        }
        return new SemUriListClipData(parcel);
    }
}
