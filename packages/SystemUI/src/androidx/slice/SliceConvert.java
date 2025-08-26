package androidx.slice;

import android.app.RemoteInput;
import android.app.slice.Slice;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class SliceConvert {
    private SliceConvert() {
    }

    public static android.app.slice.Slice unwrap(Slice slice) {
        if (slice == null || Uri.parse(slice.mUri) == null) {
            return null;
        }
        Uri uri = Uri.parse(slice.mUri);
        SliceSpec sliceSpec = slice.mSpec;
        Slice.Builder builder = new Slice.Builder(uri, sliceSpec != null ? new android.app.slice.SliceSpec(sliceSpec.mType, sliceSpec.mRevision) : null);
        builder.addHints(Arrays.asList(slice.mHints));
        for (SliceItem sliceItem : slice.mItems) {
            String str = sliceItem.mFormat;
            str.getClass();
            switch (str) {
                case "action":
                    builder.addAction(sliceItem.getAction(), unwrap(sliceItem.getSlice()), sliceItem.mSubType);
                    break;
                case "bundle":
                    builder.addBundle((Bundle) sliceItem.mObj, sliceItem.mSubType, Arrays.asList(sliceItem.mHints));
                    break;
                case "int":
                    builder.addInt(sliceItem.getInt(), sliceItem.mSubType, Arrays.asList(sliceItem.mHints));
                    break;
                case "long":
                    builder.addLong(sliceItem.getLong(), sliceItem.mSubType, Arrays.asList(sliceItem.mHints));
                    break;
                case "text":
                    builder.addText((CharSequence) sliceItem.mObj, sliceItem.mSubType, Arrays.asList(sliceItem.mHints));
                    break;
                case "image":
                    builder.addIcon(((IconCompat) sliceItem.mObj).toIcon$1(), sliceItem.mSubType, Arrays.asList(sliceItem.mHints));
                    break;
                case "input":
                    builder.addRemoteInput((RemoteInput) sliceItem.mObj, sliceItem.mSubType, Arrays.asList(sliceItem.mHints));
                    break;
                case "slice":
                    builder.addSubSlice(unwrap(sliceItem.getSlice()), sliceItem.mSubType);
                    break;
            }
        }
        return builder.build();
    }

    public static Slice wrap(android.app.slice.Slice slice, Context context) {
        if (slice == null || slice.getUri() == null) {
            return null;
        }
        Slice.Builder builder = new Slice.Builder(slice.getUri());
        List<String> hints = slice.getHints();
        builder.addHints((String[]) hints.toArray(new String[hints.size()]));
        android.app.slice.SliceSpec spec = slice.getSpec();
        builder.mSpec = spec != null ? new SliceSpec(spec.getType(), spec.getRevision()) : null;
        for (android.app.slice.SliceItem sliceItem : slice.getItems()) {
            String format = sliceItem.getFormat();
            format.getClass();
            switch (format) {
                case "action":
                    builder.addAction(sliceItem.getAction(), wrap(sliceItem.getSlice(), context), sliceItem.getSubType());
                    break;
                case "bundle":
                    builder.addItem(new SliceItem(sliceItem.getBundle(), sliceItem.getFormat(), sliceItem.getSubType(), sliceItem.getHints()));
                    break;
                case "int":
                    int i = sliceItem.getInt();
                    String subType = sliceItem.getSubType();
                    List<String> hints2 = sliceItem.getHints();
                    builder.addInt(i, subType, (String[]) hints2.toArray(new String[hints2.size()]));
                    break;
                case "long":
                    long j = sliceItem.getLong();
                    String subType2 = sliceItem.getSubType();
                    List<String> hints3 = sliceItem.getHints();
                    builder.mItems.add(new SliceItem(Long.valueOf(j), "long", subType2, (String[]) hints3.toArray(new String[hints3.size()])));
                    break;
                case "text":
                    CharSequence text = sliceItem.getText();
                    String subType3 = sliceItem.getSubType();
                    List<String> hints4 = sliceItem.getHints();
                    builder.addText(text, subType3, (String[]) hints4.toArray(new String[hints4.size()]));
                    break;
                case "image":
                    try {
                        IconCompat iconCompatCreateFromIcon = IconCompat.createFromIcon(context, sliceItem.getIcon());
                        String subType4 = sliceItem.getSubType();
                        List<String> hints5 = sliceItem.getHints();
                        if (Slice.isValidIcon(iconCompatCreateFromIcon)) {
                            builder.addIcon(iconCompatCreateFromIcon, subType4, (String[]) hints5.toArray(new String[hints5.size()]));
                            break;
                        } else {
                            break;
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.w("SliceConvert", "The icon resource isn't available.", e);
                        break;
                    } catch (IllegalArgumentException e2) {
                        Log.w("SliceConvert", "The icon resource isn't available.", e2);
                        break;
                    }
                case "input":
                    RemoteInput remoteInput = sliceItem.getRemoteInput();
                    String subType5 = sliceItem.getSubType();
                    List<String> hints6 = sliceItem.getHints();
                    remoteInput.getClass();
                    builder.mItems.add(new SliceItem(remoteInput, "input", subType5, (String[]) hints6.toArray(new String[hints6.size()])));
                    break;
                case "slice":
                    builder.addSubSlice(wrap(sliceItem.getSlice(), context), sliceItem.getSubType());
                    break;
            }
        }
        return builder.build();
    }
}
