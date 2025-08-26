package com.android.internal.widget;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Icon;
import android.hardware.scontext.SContextConstants;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.internal.R;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ContrastColorUtil;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class PeopleHelper {
    private static final float COLOR_SHIFT_AMOUNT = 60.0f;
    private static final Pattern IGNORABLE_CHAR_PATTERN = Pattern.compile("[\\p{C}\\p{Z}]");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    private int mAvatarSize;
    private Context mContext;
    private Paint mPaint = new Paint(1);
    private Paint mTextPaint = new Paint();

    public void init(Context context) {
        this.mContext = context;
        this.mAvatarSize = context.getResources().getDimensionPixelSize(R.dimen.messaging_avatar_size);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint.setAntiAlias(true);
    }

    public void animateViewForceHidden(final CachingIconView cachingIconView, final boolean z) {
        if (z == (cachingIconView.willBeForceHidden() || cachingIconView.isForceHidden())) {
            return;
        }
        cachingIconView.animate().cancel();
        cachingIconView.setWillBeForceHidden(z);
        cachingIconView.animate().scaleX(z ? 0.5f : 1.0f).scaleY(z ? 0.5f : 1.0f).alpha(z ? 0.0f : 1.0f).setInterpolator(z ? MessagingPropertyAnimator.ALPHA_OUT : MessagingPropertyAnimator.ALPHA_IN).setDuration(160L);
        if (cachingIconView.getVisibility() != 0) {
            cachingIconView.setForceHidden(z);
        } else {
            cachingIconView.animate().withEndAction(new Runnable() { // from class: com.android.internal.widget.PeopleHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    cachingIconView.setForceHidden(z);
                }
            });
        }
        cachingIconView.animate().start();
    }

    public Icon createAvatarSymbol(CharSequence charSequence, String str, int i) {
        float f;
        float f2;
        if (str == null || str.isEmpty() || TextUtils.isDigitsOnly(str) || SPECIAL_CHAR_PATTERN.matcher(str).find()) {
            Icon iconCreateWithResource = Icon.createWithResource(this.mContext, R.drawable.messaging_user);
            iconCreateWithResource.setTint(findColor(charSequence, i));
            return iconCreateWithResource;
        }
        int i2 = this.mAvatarSize;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        float f3 = this.mAvatarSize / 2.0f;
        int iFindColor = findColor(charSequence, i);
        this.mPaint.setColor(iFindColor);
        canvas.drawCircle(f3, f3, f3, this.mPaint);
        this.mTextPaint.setColor((ColorUtils.calculateLuminance(iFindColor) > 0.5d ? 1 : (ColorUtils.calculateLuminance(iFindColor) == 0.5d ? 0 : -1)) > 0 ? -16777216 : -1);
        Paint paint = this.mTextPaint;
        if (str.length() == 1) {
            f = this.mAvatarSize;
            f2 = 0.5f;
        } else {
            f = this.mAvatarSize;
            f2 = 0.3f;
        }
        paint.setTextSize(f * f2);
        canvas.drawText(str, f3, (int) (f3 - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f)), this.mTextPaint);
        return Icon.createWithBitmap(bitmapCreateBitmap);
    }

    private int findColor(CharSequence charSequence, int i) {
        return ContrastColorUtil.getShiftedColor(i, (int) (((float) (((float) ((((Math.abs(charSequence.hashCode()) % 5) / 4.0f) - 0.5f) + Math.max(0.30000001192092896d - r0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN))) - Math.max(0.30000001192092896d - (1.0d - ContrastColorUtil.calculateLuminance(i)), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN))) * 60.0f));
    }

    private String getPureName(CharSequence charSequence) {
        return IGNORABLE_CHAR_PATTERN.matcher(charSequence).replaceAll("");
    }

    public String findNamePrefix(CharSequence charSequence, String str) {
        String pureName = getPureName(charSequence);
        if (!pureName.isEmpty()) {
            try {
                return new String(Character.toChars(pureName.codePointAt(0)));
            } catch (RuntimeException unused) {
            }
        }
        return str;
    }

    public String findNameSplit(CharSequence charSequence) {
        String[] strArrSplit = (charSequence instanceof String ? (String) charSequence : charSequence.toString()).trim().split("[ ]+");
        if (strArrSplit.length > 1) {
            String strFindNamePrefix = findNamePrefix(strArrSplit[0], null);
            String strFindNamePrefix2 = findNamePrefix(strArrSplit[1], null);
            if (strFindNamePrefix != null && strFindNamePrefix2 != null) {
                return strFindNamePrefix + strFindNamePrefix2;
            }
        }
        return findNamePrefix(charSequence, "");
    }

    public Map<CharSequence, String> mapUniqueNamesToPrefix(List<MessagingGroup> list) {
        String strFindNamePrefix;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        for (int i = 0; i < list.size(); i++) {
            MessagingGroup messagingGroup = list.get(i);
            CharSequence senderName = messagingGroup.getSenderName();
            if (messagingGroup.needsGeneratedAvatar() && !TextUtils.isEmpty(senderName) && !arrayMap.containsKey(senderName) && (strFindNamePrefix = findNamePrefix(senderName, null)) != null) {
                if (arrayMap2.containsKey(strFindNamePrefix)) {
                    CharSequence charSequence = (CharSequence) arrayMap2.get(strFindNamePrefix);
                    if (charSequence != null) {
                        arrayMap.put(charSequence, findNameSplit(charSequence));
                        arrayMap2.put(strFindNamePrefix, null);
                    }
                    arrayMap.put(senderName, findNameSplit(senderName));
                } else {
                    arrayMap.put(senderName, strFindNamePrefix);
                    arrayMap2.put(strFindNamePrefix, senderName);
                }
            }
        }
        return arrayMap;
    }

    public class NameToPrefixMap {
        Map<String, String> mMap;

        NameToPrefixMap(PeopleHelper peopleHelper, Map<String, String> map) {
            this.mMap = map;
        }

        public String getPrefix(CharSequence charSequence) {
            return this.mMap.get(charSequence.toString());
        }
    }

    public NameToPrefixMap mapUniqueNamesToPrefixWithGroupList(List<List<Notification.MessagingStyle.Message>> list) {
        Person senderPerson;
        String strFindNamePrefix;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        for (int i = 0; i < list.size(); i++) {
            List<Notification.MessagingStyle.Message> list2 = list.get(i);
            if (!list2.isEmpty() && (senderPerson = list2.get(0).getSenderPerson()) != null) {
                CharSequence name = senderPerson.getName();
                if (senderPerson.getIcon() == null && !TextUtils.isEmpty(name)) {
                    String string = name.toString();
                    if (!arrayMap.containsKey(string) && (strFindNamePrefix = findNamePrefix(name, null)) != null) {
                        if (arrayMap2.containsKey(strFindNamePrefix)) {
                            CharSequence charSequence = (CharSequence) arrayMap2.get(strFindNamePrefix);
                            if (charSequence != null) {
                                arrayMap.put(charSequence.toString(), findNameSplit(charSequence));
                                arrayMap2.put(strFindNamePrefix, null);
                            }
                            arrayMap.put(string, findNameSplit(name));
                        } else {
                            arrayMap.put(string, strFindNamePrefix);
                            arrayMap2.put(strFindNamePrefix, name);
                        }
                    }
                }
            }
        }
        return new NameToPrefixMap(this, arrayMap);
    }

    public void maybeHideFirstSenderName(List<MessagingGroup> list, boolean z, CharSequence charSequence) {
        for (int size = list.size() - 1; size >= 0; size--) {
            MessagingGroup messagingGroup = list.get(size);
            messagingGroup.setCanHideSenderIfFirst(z && TextUtils.equals(charSequence, messagingGroup.getSenderName()));
        }
    }
}
