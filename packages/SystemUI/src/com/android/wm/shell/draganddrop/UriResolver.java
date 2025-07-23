package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class UriResolver extends BaseResolver {
    public boolean mIsClipDataFromSBrowser;
    public final ExecutableAppHolder.MimeTypeBlockList mMimeTypeBlockList;

    public UriResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
        this.mMimeTypeBlockList = new ExecutableAppHolder.MimeTypeBlockList(context);
    }

    @Override // com.android.wm.shell.draganddrop.BaseResolver
    public final Optional makeFrom(ClipData clipData, int i, AppResultFactory.ResultExtra resultExtra) {
        CharSequence label;
        CharSequence text;
        boolean z = false;
        Uri uri = null;
        if (clipData.getItemCount() > 0) {
            ClipData.Item itemAt = clipData.getItemAt(0);
            ArrayList arrayList = new ArrayList();
            if (itemAt.getUri() != null) {
                arrayList.add(itemAt.getUri());
            }
            Intent intent = itemAt.getIntent();
            if (intent != null) {
                if (intent.getData() != null) {
                    arrayList.add(intent.getData());
                }
                if (intent.getClipData() != null) {
                    intent.getClipData().collectUris(arrayList);
                }
            }
            Uri uri2 = arrayList.isEmpty() ? null : (Uri) arrayList.get(0);
            if (uri2 == null && (text = itemAt.getText()) != null) {
                try {
                    uri2 = Uri.parse(URI.create(text.toString()).toString());
                    if (uri2.getScheme() == null) {
                        uri2 = null;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            if (uri2 != null) {
                uri = uri2;
            }
        }
        if (uri == null) {
            return Optional.empty();
        }
        ClipDescription description = clipData.getDescription();
        if (description != null && (label = description.getLabel()) != null && "terrace-image-or-link-drag-label".equals(label.toString())) {
            z = true;
        }
        this.mIsClipDataFromSBrowser = z;
        Intent intent2 = new Intent();
        intent2.setAction("android.intent.action.VIEW");
        if ("content".equals(uri.getScheme())) {
            intent2.setDataAndType(uri, this.mContext.getContentResolver().getType(uri));
            intent2.addFlags(i);
            intent2.addFlags(8388608);
        } else {
            intent2.setData(uri);
        }
        if (intent2.hasWebURI()) {
            intent2.addCategory("android.intent.category.BROWSABLE");
        }
        if (this.mIsClipDataFromSBrowser) {
            intent2.putExtra("terrace-image-or-link-drag-label", true);
        }
        String calculateContentType = BaseResolver.calculateContentType(intent2);
        if (this.mMimeTypeBlockList.mBlockList.contains(calculateContentType)) {
            return Optional.empty();
        }
        int callingUserId = clipData.getCallingUserId();
        if (this.mIsClipDataFromSBrowser) {
            resolveActivitiesForSBrowser(intent2, callingUserId, this.mTempList, resultExtra);
        } else {
            resolveActivities(intent2, callingUserId, this.mTempList, resultExtra);
        }
        return this.mTempList.isEmpty() ? Optional.empty() : Optional.of(new SingleIntentAppResult(intent2, this.mTempList, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, calculateContentType, true));
    }
}
