package com.android.systemui.people;

import android.app.PendingIntent;
import android.app.people.ConversationStatus;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.util.SizeF;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.core.math.MathUtils;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.people.PeopleStoryIconFactory;
import com.android.systemui.people.widget.LaunchConversationActivity;
import com.android.systemui.people.widget.PeopleTileKey;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class PeopleTileViewHelper {
    public final int mAppWidgetId;
    public final Context mContext;
    public final float mDensity;
    public final int mHeight;
    public NumberFormat mIntegerFormat;
    public final boolean mIsLeftToRight;
    public final PeopleTileKey mKey;
    public final int mLayoutSize;
    public Locale mLocale;
    public final int mMediumVerticalPadding;
    public final PeopleSpaceTile mTile;
    public final int mWidth;
    public static final Pattern DOUBLE_EXCLAMATION_PATTERN = Pattern.compile("[!][!]+");
    public static final Pattern DOUBLE_QUESTION_PATTERN = Pattern.compile("[?][?]+");
    public static final Pattern ANY_DOUBLE_MARK_PATTERN = Pattern.compile("[!?][!?]+");
    public static final Pattern MIXED_MARK_PATTERN = Pattern.compile("![?].*|.*[?]!");

    public final class RemoteViewsAndSizes {
        public final int mAvatarSize;
        public final RemoteViews mRemoteViews;

        public RemoteViewsAndSizes(RemoteViews remoteViews, int i) {
            this.mRemoteViews = remoteViews;
            this.mAvatarSize = i;
        }
    }

    public PeopleTileViewHelper(Context context, PeopleSpaceTile peopleSpaceTile, int i, int i2, int i3, PeopleTileKey peopleTileKey) {
        this.mContext = context;
        this.mTile = peopleSpaceTile;
        this.mKey = peopleTileKey;
        this.mAppWidgetId = i;
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mWidth = i2;
        this.mHeight = i3;
        int i4 = 2;
        if (i3 < getSizeInDp(R.dimen.required_height_for_large) || i2 < getSizeInDp(R.dimen.required_width_for_large)) {
            if (i3 < getSizeInDp(R.dimen.required_height_for_medium) || i2 < getSizeInDp(R.dimen.required_width_for_medium)) {
                i4 = 0;
            } else {
                this.mMediumVerticalPadding = Math.max(4, Math.min(Math.floorDiv(i3 - (getLineHeightFromResource(R.dimen.name_text_size_for_medium_content) + (getSizeInDp(R.dimen.avatar_size_for_medium) + 4)), 2), 16));
                i4 = 1;
            }
        }
        this.mLayoutSize = i4;
        this.mIsLeftToRight = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 0;
    }

    public static RemoteViews createRemoteViews(final Context context, final PeopleSpaceTile peopleSpaceTile, final int i, Bundle bundle, final PeopleTileKey peopleTileKey) {
        float f = context.getResources().getDisplayMetrics().density;
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("appWidgetSizes");
        if (parcelableArrayList == null || parcelableArrayList.isEmpty()) {
            int dimension = (int) (context.getResources().getDimension(R.dimen.default_width) / f);
            int dimension2 = (int) (context.getResources().getDimension(R.dimen.default_height) / f);
            ArrayList arrayList = new ArrayList(2);
            arrayList.add(new SizeF(bundle.getInt("appWidgetMinWidth", dimension), bundle.getInt("appWidgetMaxHeight", dimension2)));
            arrayList.add(new SizeF(bundle.getInt("appWidgetMaxWidth", dimension), bundle.getInt("appWidgetMinHeight", dimension2)));
            parcelableArrayList = arrayList;
        }
        return new RemoteViews((Map<SizeF, RemoteViews>) parcelableArrayList.stream().distinct().collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                SizeF sizeF = (SizeF) obj;
                return new PeopleTileViewHelper(context, peopleSpaceTile, i, (int) sizeF.getWidth(), (int) sizeF.getHeight(), peopleTileKey).getViews();
            }
        })));
    }

    public static Bitmap getPersonIconBitmap(Context context, int i, boolean z, Icon icon, String str, int i2, boolean z2, boolean z3) {
        Drawable defaultActivityIcon;
        if (icon == null) {
            Drawable drawableMutate = context.getDrawable(R.drawable.ic_avatar_with_badge).mutate();
            drawableMutate.setColorFilter(FastBitmapDrawable.getDisabledColorFilter(1.0f));
            return PeopleSpaceUtils.convertDrawableToBitmap(drawableMutate);
        }
        PeopleStoryIconFactory peopleStoryIconFactory = new PeopleStoryIconFactory(context, context.getPackageManager(), IconDrawableFactory.newInstance(context, false), i);
        RoundedBitmapDrawable21 roundedBitmapDrawable21 = new RoundedBitmapDrawable21(context.getResources(), icon.getBitmap());
        try {
            defaultActivityIcon = Utils.getBadgedIcon(peopleStoryIconFactory.mContext, peopleStoryIconFactory.mPackageManager.getApplicationInfoAsUser(str, 128, i2));
        } catch (PackageManager.NameNotFoundException unused) {
            defaultActivityIcon = peopleStoryIconFactory.mPackageManager.getDefaultActivityIcon();
        }
        PeopleStoryIconFactory.PeopleStoryIconDrawable peopleStoryIconDrawable = new PeopleStoryIconFactory.PeopleStoryIconDrawable(roundedBitmapDrawable21, defaultActivityIcon, peopleStoryIconFactory.mIconBitmapSize, peopleStoryIconFactory.mImportantConversationColor, z2, peopleStoryIconFactory.mIconSize, peopleStoryIconFactory.mDensity, peopleStoryIconFactory.mAccentColor, z);
        if (z3) {
            peopleStoryIconDrawable.setColorFilter(FastBitmapDrawable.getDisabledColorFilter(1.0f));
        }
        return PeopleSpaceUtils.convertDrawableToBitmap(peopleStoryIconDrawable);
    }

    public static boolean isDndBlockingTileData(PeopleSpaceTile peopleSpaceTile) {
        if (peopleSpaceTile == null) {
            return false;
        }
        int notificationPolicyState = peopleSpaceTile.getNotificationPolicyState();
        if ((notificationPolicyState & 1) != 0) {
            return false;
        }
        if ((notificationPolicyState & 4) != 0 && peopleSpaceTile.isImportantConversation()) {
            return false;
        }
        if ((notificationPolicyState & 8) != 0 && peopleSpaceTile.getContactAffinity() == 1.0f) {
            return false;
        }
        if ((notificationPolicyState & 16) == 0 || !(peopleSpaceTile.getContactAffinity() == 0.5f || peopleSpaceTile.getContactAffinity() == 1.0f)) {
            return !peopleSpaceTile.canBypassDnd();
        }
        return false;
    }

    public static void setEmojiBackground(RemoteViews remoteViews, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            remoteViews.setViewVisibility(R.id.emojis, 8);
            return;
        }
        remoteViews.setTextViewText(R.id.emoji1, charSequence);
        remoteViews.setTextViewText(R.id.emoji2, charSequence);
        remoteViews.setTextViewText(R.id.emoji3, charSequence);
        remoteViews.setViewVisibility(R.id.emojis, 0);
    }

    public static void setPunctuationBackground(RemoteViews remoteViews, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            remoteViews.setViewVisibility(R.id.punctuations, 8);
            return;
        }
        remoteViews.setTextViewText(R.id.punctuation1, charSequence);
        remoteViews.setTextViewText(R.id.punctuation2, charSequence);
        remoteViews.setTextViewText(R.id.punctuation3, charSequence);
        remoteViews.setTextViewText(R.id.punctuation4, charSequence);
        remoteViews.setTextViewText(R.id.punctuation5, charSequence);
        remoteViews.setTextViewText(R.id.punctuation6, charSequence);
        remoteViews.setViewVisibility(R.id.punctuations, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0131  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final RemoteViewsAndSizes createDndRemoteViews() {
        int i;
        StaticLayout staticLayoutBuild;
        int height;
        String packageName = this.mContext.getPackageName();
        int i2 = this.mLayoutSize;
        RemoteViews remoteViews = new RemoteViews(packageName, i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_with_suppression_detail_content_vertical : R.layout.people_tile_with_suppression_detail_content_horizontal);
        int sizeInDp = getSizeInDp(R.dimen.avatar_size_for_medium_empty);
        int sizeInDp2 = getSizeInDp(R.dimen.max_people_avatar_size);
        String string = this.mContext.getString(R.string.paused_by_dnd);
        remoteViews.setTextViewText(R.id.text_content, string);
        int i3 = i2 == 2 ? R.dimen.content_text_size_for_large : R.dimen.content_text_size_for_medium;
        remoteViews.setTextViewTextSize(R.id.text_content, 0, this.mContext.getResources().getDimension(i3));
        int lineHeightFromResource = getLineHeightFromResource(i3);
        int i4 = this.mHeight;
        if (i2 == 1) {
            remoteViews.setInt(R.id.text_content, "setMaxLines", (i4 - 16) / lineHeightFromResource);
        } else {
            float f = this.mDensity;
            int i5 = (int) (16 * f);
            int i6 = (int) (14 * f);
            int sizeInDp3 = getSizeInDp(i2 == 0 ? R.dimen.regular_predefined_icon : R.dimen.largest_predefined_icon);
            int i7 = (i4 - 32) - sizeInDp3;
            int sizeInDp4 = getSizeInDp(R.dimen.padding_between_suppressed_layout_items);
            int i8 = this.mWidth;
            int i9 = i8 - 32;
            int i10 = sizeInDp4 * 2;
            int i11 = (i7 - sizeInDp) - i10;
            try {
                i = lineHeightFromResource;
            } catch (Exception e) {
                e = e;
                i = lineHeightFromResource;
            }
            try {
                TextView textView = new TextView(this.mContext);
                textView.setTextSize(0, this.mContext.getResources().getDimension(i3));
                textView.setTextAppearance(android.R.style.TextAppearance.DeviceDefault);
                staticLayoutBuild = StaticLayout.Builder.obtain(string, 0, string.length(), textView.getPaint(), (int) (i9 * f)).setBreakStrategy(0).build();
            } catch (Exception e2) {
                e = e2;
                EmergencyButton$$ExternalSyntheticOutline0.m("Could not create static layout: ", e, "PeopleTileView");
                staticLayoutBuild = null;
                if (staticLayoutBuild != null) {
                }
                if (height <= i11) {
                    if (i2 != 0) {
                    }
                    sizeInDp = getMaxAvatarSize(remoteViews);
                    remoteViews.setViewVisibility(R.id.messages_count, 8);
                    remoteViews.setViewVisibility(R.id.name, 8);
                    remoteViews.setContentDescription(R.id.predefined_icon, string);
                    remoteViews.setViewVisibility(R.id.predefined_icon, 0);
                    remoteViews.setImageViewResource(R.id.predefined_icon, R.drawable.ic_qs_dnd_on);
                }
                return new RemoteViewsAndSizes(remoteViews, sizeInDp);
            }
            height = staticLayoutBuild != null ? Integer.MAX_VALUE : (int) (staticLayoutBuild.getHeight() / f);
            if (height <= i11 || i2 != 2) {
                if (i2 != 0) {
                    remoteViews = new RemoteViews(this.mContext.getPackageName(), R.layout.people_tile_small);
                }
                sizeInDp = getMaxAvatarSize(remoteViews);
                remoteViews.setViewVisibility(R.id.messages_count, 8);
                remoteViews.setViewVisibility(R.id.name, 8);
                remoteViews.setContentDescription(R.id.predefined_icon, string);
            } else {
                remoteViews.setViewVisibility(R.id.text_content, 0);
                remoteViews.setInt(R.id.text_content, "setMaxLines", i11 / i);
                remoteViews.setContentDescription(R.id.predefined_icon, null);
                sizeInDp = MathUtils.clamp(Math.min(i8 - 32, (i7 - height) - i10), (int) (10.0f * f), sizeInDp2);
                remoteViews.setViewPadding(android.R.id.background, i5, i6, i5, i5);
                remoteViews = remoteViews;
                float f2 = sizeInDp3;
                remoteViews.setViewLayoutWidth(R.id.predefined_icon, f2, 1);
                remoteViews.setViewLayoutHeight(R.id.predefined_icon, f2, 1);
            }
            remoteViews.setViewVisibility(R.id.predefined_icon, 0);
            remoteViews.setImageViewResource(R.id.predefined_icon, R.drawable.ic_qs_dnd_on);
        }
        return new RemoteViewsAndSizes(remoteViews, sizeInDp);
    }

    public final RemoteViews createStatusRemoteViews(ConversationStatus conversationStatus) throws Resources.NotFoundException {
        int i;
        int i2 = this.mLayoutSize;
        RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_with_status_content : R.layout.people_tile_medium_with_content);
        setViewForContentLayout(remoteViews);
        CharSequence description = conversationStatus.getDescription();
        CharSequence string = "";
        if (TextUtils.isEmpty(description)) {
            switch (conversationStatus.getActivity()) {
                case 1:
                    description = this.mContext.getString(R.string.birthday_status);
                    break;
                case 2:
                    description = this.mContext.getString(R.string.anniversary_status);
                    break;
                case 3:
                    description = this.mContext.getString(R.string.new_story_status);
                    break;
                case 4:
                    description = this.mContext.getString(R.string.audio_status);
                    break;
                case 5:
                    description = this.mContext.getString(R.string.video_status);
                    break;
                case 6:
                    description = this.mContext.getString(R.string.game_status);
                    break;
                case 7:
                    description = this.mContext.getString(R.string.location_status);
                    break;
                case 8:
                    description = this.mContext.getString(R.string.upcoming_birthday_status);
                    break;
                default:
                    description = "";
                    break;
            }
        }
        setPredefinedIconVisible(remoteViews);
        int i3 = R.id.text_content;
        remoteViews.setTextViewText(R.id.text_content, description);
        if (conversationStatus.getActivity() == 1 || conversationStatus.getActivity() == 8) {
            Pattern pattern = EmojiHelper.EMOJI_PATTERN;
            setEmojiBackground(remoteViews, "🎂");
        }
        Icon icon = conversationStatus.getIcon();
        if (icon != null) {
            remoteViews.setViewVisibility(R.id.scrim_layout, 0);
            remoteViews.setImageViewIcon(R.id.status_icon, icon);
            if (i2 == 2) {
                remoteViews.setInt(R.id.content, "setGravity", 80);
                remoteViews.setViewVisibility(R.id.name, 8);
                remoteViews.setColorAttr(R.id.text_content, "setTextColor", android.R.attr.textColorPrimary);
            } else if (i2 == 1) {
                remoteViews.setViewVisibility(R.id.text_content, 8);
                remoteViews.setTextViewText(R.id.name, description);
            }
        } else {
            remoteViews.setColorAttr(R.id.text_content, "setTextColor", android.R.attr.textColorSecondary);
            setMaxLines(remoteViews, false);
        }
        setAvailabilityDotPadding(remoteViews, R.dimen.availability_dot_status_padding);
        switch (conversationStatus.getActivity()) {
            case 1:
                i = R.drawable.ic_cake;
                break;
            case 2:
                i = R.drawable.ic_celebration;
                break;
            case 3:
                i = R.drawable.ic_pages;
                break;
            case 4:
                i = R.drawable.ic_music_note;
                break;
            case 5:
                i = R.drawable.ic_video;
                break;
            case 6:
                i = R.drawable.ic_play_games;
                break;
            case 7:
                i = R.drawable.ic_location;
                break;
            case 8:
                i = R.drawable.ic_gift;
                break;
            default:
                i = R.drawable.ic_person;
                break;
        }
        remoteViews.setImageViewResource(R.id.predefined_icon, i);
        CharSequence userName = this.mTile.getUserName();
        if (TextUtils.isEmpty(conversationStatus.getDescription())) {
            switch (conversationStatus.getActivity()) {
                case 1:
                    string = this.mContext.getString(R.string.birthday_status_content_description, userName);
                    break;
                case 2:
                    string = this.mContext.getString(R.string.anniversary_status_content_description, userName);
                    break;
                case 3:
                    string = this.mContext.getString(R.string.new_story_status_content_description, userName);
                    break;
                case 4:
                    string = this.mContext.getString(R.string.audio_status);
                    break;
                case 5:
                    string = this.mContext.getString(R.string.video_status);
                    break;
                case 6:
                    string = this.mContext.getString(R.string.game_status);
                    break;
                case 7:
                    string = this.mContext.getString(R.string.location_status_content_description, userName);
                    break;
                case 8:
                    string = this.mContext.getString(R.string.upcoming_birthday_status_content_description, userName);
                    break;
            }
        } else {
            string = conversationStatus.getDescription();
        }
        String string2 = this.mContext.getString(R.string.new_status_content_description, this.mTile.getUserName(), string);
        if (i2 == 0) {
            remoteViews.setContentDescription(R.id.predefined_icon, string2);
            return remoteViews;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                return remoteViews;
            }
            remoteViews.setContentDescription(R.id.text_content, string2);
            return remoteViews;
        }
        if (icon != null) {
            i3 = R.id.name;
        }
        remoteViews.setContentDescription(i3, string2);
        return remoteViews;
    }

    public CharSequence getDoubleEmoji(CharSequence charSequence) {
        Matcher matcher = EmojiHelper.EMOJI_PATTERN.matcher(charSequence);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        while (matcher.find()) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            arrayList.add(new Pair(Integer.valueOf(iStart), Integer.valueOf(iEnd)));
            arrayList2.add(charSequence.subSequence(iStart, iEnd));
        }
        if (arrayList.size() < 2) {
            return null;
        }
        for (int i = 1; i < arrayList.size(); i++) {
            int i2 = i - 1;
            if (Objects.equals(((Pair) arrayList.get(i)).first, ((Pair) arrayList.get(i2)).second) && Objects.equals(arrayList2.get(i), arrayList2.get(i2))) {
                return (CharSequence) arrayList2.get(i);
            }
        }
        return null;
    }

    public CharSequence getDoublePunctuation(CharSequence charSequence) {
        if (!ANY_DOUBLE_MARK_PATTERN.matcher(charSequence).find()) {
            return null;
        }
        if (MIXED_MARK_PATTERN.matcher(charSequence).find()) {
            return "!?";
        }
        Matcher matcher = DOUBLE_QUESTION_PATTERN.matcher(charSequence);
        if (!matcher.find()) {
            return "!";
        }
        Matcher matcher2 = DOUBLE_EXCLAMATION_PATTERN.matcher(charSequence);
        return (matcher2.find() && matcher.start() >= matcher2.start()) ? "!" : "?";
    }

    public final int getLayoutSmallByHeight() {
        return this.mHeight >= getSizeInDp(R.dimen.required_height_for_medium) ? R.layout.people_tile_small : R.layout.people_tile_small_horizontal;
    }

    public final int getLineHeightFromResource(int i) {
        try {
            TextView textView = new TextView(this.mContext);
            textView.setTextSize(0, this.mContext.getResources().getDimension(i));
            textView.setTextAppearance(android.R.style.TextAppearance.DeviceDefault);
            return (int) (textView.getLineHeight() / this.mDensity);
        } catch (Exception e) {
            Log.e("PeopleTileView", "Could not create text view: " + e);
            return this.getSizeInDp(R.dimen.content_text_size_for_medium);
        }
    }

    public final int getMaxAvatarSize(RemoteViews remoteViews) {
        int layoutId = remoteViews.getLayoutId();
        int sizeInDp = getSizeInDp(R.dimen.avatar_size_for_medium);
        if (layoutId == R.layout.people_tile_medium_empty) {
            return getSizeInDp(R.dimen.max_people_avatar_size_for_large_content);
        }
        if (layoutId == R.layout.people_tile_medium_with_content) {
            return getSizeInDp(R.dimen.avatar_size_for_medium);
        }
        int i = this.mWidth;
        int i2 = this.mHeight;
        if (layoutId == R.layout.people_tile_small) {
            sizeInDp = Math.min(i2 - (Math.max(18, getLineHeightFromResource(R.dimen.name_text_size_for_small)) + 18), i - 8);
        }
        if (layoutId == R.layout.people_tile_small_horizontal) {
            sizeInDp = Math.min(i2 - 10, i - 16);
        }
        if (layoutId == R.layout.people_tile_large_with_notification_content) {
            return Math.min(i2 - ((getLineHeightFromResource(R.dimen.content_text_size_for_large) * 3) + 62), getSizeInDp(R.dimen.max_people_avatar_size_for_large_content));
        }
        if (layoutId == R.layout.people_tile_large_with_status_content) {
            return Math.min(i2 - ((getLineHeightFromResource(R.dimen.content_text_size_for_large) * 3) + 76), getSizeInDp(R.dimen.max_people_avatar_size_for_large_content));
        }
        if (layoutId == R.layout.people_tile_large_empty) {
            sizeInDp = Math.min(i2 - ((getLineHeightFromResource(R.dimen.content_text_size_for_large) + (getLineHeightFromResource(R.dimen.name_text_size_for_large) + 28)) + 42), i - 28);
        }
        if (isDndBlockingTileData(this.mTile) && this.mLayoutSize != 0) {
            sizeInDp = createDndRemoteViews().mAvatarSize;
        }
        return Math.min(sizeInDp, getSizeInDp(R.dimen.max_people_avatar_size));
    }

    public final int getSizeInDp(int i) {
        Context context = this.mContext;
        return (int) (context.getResources().getDimension(i) / this.mDensity);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|2|(3:145|(1:150)(1:149)|151)(2:9|(1:11)(2:13|(7:15|(2:(1:18)(1:20)|19)(1:21)|22|(1:24)(1:25)|26|(1:28)|29)(2:30|(17:32|(1:(1:35)(1:36))(1:37)|38|(4:40|203|41|45)(9:46|(1:48)(1:49)|50|(1:52)(1:53)|54|(1:56)(1:57)|58|(1:60)(1:61)|62)|63|(6:(4:66|(1:68)(1:69)|(1:71)(1:72)|73)|74|(1:76)(3:78|(1:80)|81)|77|82|(1:84))|85|(1:87)(1:89)|88|90|152|205|153|(9:156|(10:158|(1:160)|165|(1:167)(1:168)|(1:170)(1:171)|(1:173)(1:174)|175|(1:180)(1:179)|181|(1:183)(1:184))(1:163)|164|165|(0)(0)|(0)(0)|(0)(0)|175|(4:177|180|181|(0)(0))(0))(1:155)|187|(4:201|192|(1:194)|197)|200)(5:91|(1:93)(1:94)|95|(1:97)(2:98|(1:100)(1:101))|(1:103)(2:104|(1:106)(11:107|(1:(1:110)(1:111))(1:112)|113|(1:115)|116|(1:118)|119|(1:121)(4:123|(0)(2:126|(1:128)(2:129|(1:131)(2:132|(1:134)(2:135|(1:137)(1:138)))))|139|(1:141)(2:142|(1:144)))|122|139|(0)(0)))))))|12|152|205|153|(0)(0)|187|(5:189|201|192|(0)|197)|200) */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0477, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0512, code lost:
    
        com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m("Failed to set common fields: ", r0, "PeopleTileView");
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:141:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0439  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x043b A[Catch: Exception -> 0x0477, TryCatch #2 {Exception -> 0x0477, blocks: (B:153:0x0435, B:156:0x043b, B:158:0x0444, B:160:0x045a, B:165:0x048b, B:175:0x04a2, B:177:0x04b2, B:181:0x04ca, B:183:0x04f6, B:184:0x050d, B:164:0x047b), top: B:205:0x0435 }] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0495  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0497  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x049a  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x049c  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x049f  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x04a1  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x04c8  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x04f6 A[Catch: Exception -> 0x0477, TryCatch #2 {Exception -> 0x0477, blocks: (B:153:0x0435, B:156:0x043b, B:158:0x0444, B:160:0x045a, B:165:0x048b, B:175:0x04a2, B:177:0x04b2, B:181:0x04ca, B:183:0x04f6, B:184:0x050d, B:164:0x047b), top: B:205:0x0435 }] */
    /* JADX WARN: Removed duplicated region for block: B:184:0x050d A[Catch: Exception -> 0x0477, TRY_LEAVE, TryCatch #2 {Exception -> 0x0477, blocks: (B:153:0x0435, B:156:0x043b, B:158:0x0444, B:160:0x045a, B:165:0x048b, B:175:0x04a2, B:177:0x04b2, B:181:0x04ca, B:183:0x04f6, B:184:0x050d, B:164:0x047b), top: B:205:0x0435 }] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0551 A[Catch: Exception -> 0x055b, TryCatch #0 {Exception -> 0x055b, blocks: (B:192:0x0524, B:194:0x0551, B:197:0x055d), top: B:201:0x0524 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public RemoteViews getViews() throws Resources.NotFoundException {
        RemoteViews remoteViews;
        RemoteViews remoteViews2;
        PeopleTileKey peopleTileKey;
        PeopleSpaceTile peopleSpaceTile;
        PeopleSpaceTile peopleSpaceTile2;
        int i;
        int dimensionPixelSize;
        boolean z;
        String string;
        RemoteViews remoteViews3;
        String string2;
        int layoutSmallByHeight;
        int i2 = this.mLayoutSize;
        PeopleSpaceTile peopleSpaceTile3 = this.mTile;
        if (peopleSpaceTile3 == null || peopleSpaceTile3.isPackageSuspended() || this.mTile.isUserQuieted()) {
            PeopleSpaceTile peopleSpaceTile4 = this.mTile;
            remoteViews = (peopleSpaceTile4 == null || !peopleSpaceTile4.isUserQuieted()) ? new RemoteViews(this.mContext.getPackageName(), R.layout.people_tile_suppressed_layout) : new RemoteViews(this.mContext.getPackageName(), R.layout.people_tile_work_profile_quiet_layout);
            Drawable drawableMutate = this.mContext.getDrawable(R.drawable.ic_conversation_icon).mutate();
            drawableMutate.setColorFilter(FastBitmapDrawable.getDisabledColorFilter(1.0f));
            remoteViews.setImageViewBitmap(R.id.icon, PeopleSpaceUtils.convertDrawableToBitmap(drawableMutate));
        } else if (isDndBlockingTileData(this.mTile)) {
            remoteViews = createDndRemoteViews().mRemoteViews;
        } else if (Objects.equals(this.mTile.getNotificationCategory(), "missed_call")) {
            String packageName = this.mContext.getPackageName();
            if (i2 != 1) {
                layoutSmallByHeight = i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_with_status_content;
            } else {
                layoutSmallByHeight = R.layout.people_tile_medium_with_content;
            }
            remoteViews = new RemoteViews(packageName, layoutSmallByHeight);
            setViewForContentLayout(remoteViews);
            setPredefinedIconVisible(remoteViews);
            remoteViews.setViewVisibility(R.id.text_content, 0);
            remoteViews.setViewVisibility(R.id.messages_count, 8);
            setMaxLines(remoteViews, false);
            CharSequence notificationContent = this.mTile.getNotificationContent();
            remoteViews.setTextViewText(R.id.text_content, notificationContent);
            remoteViews.setContentDescription(i2 == 0 ? R.id.predefined_icon : R.id.text_content, this.mContext.getString(R.string.new_notification_text_content_description, this.mTile.getUserName(), notificationContent));
            remoteViews.setColorAttr(R.id.text_content, "setTextColor", android.R.attr.colorError);
            remoteViews.setColorAttr(R.id.predefined_icon, "setColorFilter", android.R.attr.colorError);
            remoteViews.setImageViewResource(R.id.predefined_icon, R.drawable.ic_phone_missed);
            if (i2 == 2) {
                remoteViews.setInt(R.id.content, "setGravity", 80);
                remoteViews.setViewLayoutHeightDimen(R.id.predefined_icon, R.dimen.larger_predefined_icon);
                remoteViews.setViewLayoutWidthDimen(R.id.predefined_icon, R.dimen.larger_predefined_icon);
            }
            setAvailabilityDotPadding(remoteViews, R.dimen.availability_dot_notification_padding);
        } else {
            if (this.mTile.getNotificationKey() != null) {
                RemoteViews remoteViews4 = new RemoteViews(this.mContext.getPackageName(), i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_with_notification_content : R.layout.people_tile_medium_with_content);
                setViewForContentLayout(remoteViews4);
                CharSequence notificationSender = this.mTile.getNotificationSender();
                Uri notificationDataUri = this.mTile.getNotificationDataUri();
                if (notificationDataUri != null) {
                    String string3 = this.mContext.getString(R.string.new_notification_image_content_description, this.mTile.getUserName());
                    remoteViews4.setContentDescription(R.id.image, string3);
                    remoteViews4.setViewVisibility(R.id.image, 0);
                    remoteViews4.setViewVisibility(R.id.text_content, 8);
                    try {
                        remoteViews4.setImageViewBitmap(R.id.image, PeopleSpaceUtils.convertDrawableToBitmap(ImageDecoder.decodeDrawable(ImageDecoder.createSource(this.mContext.getContentResolver(), notificationDataUri), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda4
                            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                            public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                PeopleTileViewHelper peopleTileViewHelper = this.f$0;
                                int iMax = Math.max((int) ActionRow$$ExternalSyntheticOutline0.m(peopleTileViewHelper.mContext, 1, peopleTileViewHelper.mWidth), (int) ActionRow$$ExternalSyntheticOutline0.m(peopleTileViewHelper.mContext, 1, peopleTileViewHelper.mHeight));
                                int iMin = (int) (Math.min(r9, r6) * 1.5d);
                                if (iMin < iMax) {
                                    iMax = iMin;
                                }
                                Size size = imageInfo.getSize();
                                imageDecoder.setTargetSampleSize(Math.max(1, Integer.highestOneBit((int) Math.floor(Math.max(size.getHeight(), size.getWidth()) > iMax ? (r6 * 1.0f) / iMax : 1.0d))));
                            }
                        })));
                    } catch (IOException | SecurityException e) {
                        EmergencyButton$$ExternalSyntheticOutline0.m("Could not decode image: ", e, "PeopleTileView");
                        remoteViews4.setTextViewText(R.id.text_content, string3);
                        remoteViews4.setViewVisibility(R.id.text_content, 0);
                        remoteViews4.setViewVisibility(R.id.image, 8);
                    }
                    remoteViews3 = remoteViews4;
                } else {
                    setMaxLines(remoteViews4, !TextUtils.isEmpty(notificationSender));
                    CharSequence notificationContent2 = this.mTile.getNotificationContent();
                    remoteViews4.setContentDescription(i2 == 0 ? R.id.predefined_icon : R.id.text_content, this.mContext.getString(R.string.new_notification_text_content_description, notificationSender != null ? notificationSender : this.mTile.getUserName(), notificationContent2));
                    CharSequence doubleEmoji = getDoubleEmoji(notificationContent2);
                    if (TextUtils.isEmpty(doubleEmoji)) {
                        CharSequence doublePunctuation = getDoublePunctuation(notificationContent2);
                        setEmojiBackground(remoteViews4, null);
                        setPunctuationBackground(remoteViews4, doublePunctuation);
                    } else {
                        setEmojiBackground(remoteViews4, doubleEmoji);
                        setPunctuationBackground(remoteViews4, null);
                    }
                    remoteViews4.setColorAttr(R.id.text_content, "setTextColor", android.R.attr.textColorPrimary);
                    remoteViews4.setTextViewText(R.id.text_content, this.mTile.getNotificationContent());
                    if (i2 == 2) {
                        remoteViews4.setViewPadding(R.id.name, 0, 0, 0, this.mContext.getResources().getDimensionPixelSize(R.dimen.above_notification_text_padding));
                        remoteViews3 = remoteViews4;
                    } else {
                        remoteViews3 = remoteViews4;
                    }
                    remoteViews3.setViewVisibility(R.id.image, 8);
                    remoteViews3.setImageViewResource(R.id.predefined_icon, R.drawable.ic_message);
                }
                if (this.mTile.getMessagesCount() > 1) {
                    if (i2 == 1) {
                        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.before_messages_count_padding);
                        boolean z2 = this.mIsLeftToRight;
                        remoteViews3.setViewPadding(R.id.name, z2 ? 0 : dimensionPixelSize2, 0, z2 ? dimensionPixelSize2 : 0, 0);
                    }
                    remoteViews3.setViewVisibility(R.id.messages_count, 0);
                    int messagesCount = this.mTile.getMessagesCount();
                    if (messagesCount >= 6) {
                        string2 = this.mContext.getResources().getString(R.string.messages_count_overflow_indicator, 6);
                    } else {
                        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
                        if (!locale.equals(this.mLocale)) {
                            this.mLocale = locale;
                            this.mIntegerFormat = NumberFormat.getIntegerInstance(locale);
                        }
                        string2 = this.mIntegerFormat.format(messagesCount);
                    }
                    remoteViews3.setTextViewText(R.id.messages_count, string2);
                    if (i2 == 0) {
                        remoteViews3.setViewVisibility(R.id.predefined_icon, 8);
                    }
                }
                if (TextUtils.isEmpty(notificationSender)) {
                    remoteViews3.setViewVisibility(R.id.subtext, 8);
                } else {
                    remoteViews3.setViewVisibility(R.id.subtext, 0);
                    remoteViews3.setTextViewText(R.id.subtext, notificationSender);
                }
                setAvailabilityDotPadding(remoteViews3, R.dimen.availability_dot_notification_padding);
                remoteViews2 = remoteViews3;
                int maxAvatarSize = getMaxAvatarSize(remoteViews2);
                peopleSpaceTile2 = this.mTile;
                if (peopleSpaceTile2 != null) {
                    if (peopleSpaceTile2.getStatuses() != null) {
                        i = 0;
                        if (this.mTile.getStatuses().stream().anyMatch(new PeopleTileViewHelper$$ExternalSyntheticLambda0(0))) {
                            remoteViews2.setViewVisibility(R.id.availability, 0);
                            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.availability_dot_shown_padding);
                            remoteViews2.setContentDescription(R.id.availability, this.mContext.getString(R.string.person_available));
                        }
                        int i3 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 0 ? 1 : i;
                        remoteViews2.setViewPadding(R.id.padding_before_availability, i3 == 0 ? dimensionPixelSize : i, 0, i3 == 0 ? i : dimensionPixelSize, 0);
                        PeopleSpaceTile peopleSpaceTile5 = this.mTile;
                        z = (peopleSpaceTile5.getStatuses() == null && peopleSpaceTile5.getStatuses().stream().anyMatch(new PeopleTileViewHelper$$ExternalSyntheticLambda0(2))) ? 1 : i;
                        Context context = this.mContext;
                        PeopleSpaceTile peopleSpaceTile6 = this.mTile;
                        Icon userIcon = peopleSpaceTile6.getUserIcon();
                        String packageName2 = peopleSpaceTile6.getPackageName();
                        PeopleTileKey peopleTileKey2 = PeopleSpaceUtils.EMPTY_KEY;
                        remoteViews2.setImageViewBitmap(R.id.person_icon, getPersonIconBitmap(context, maxAvatarSize, z, userIcon, packageName2, peopleSpaceTile6.getUserHandle().getIdentifier(), peopleSpaceTile6.isImportantConversation(), isDndBlockingTileData(peopleSpaceTile6)));
                        if (z == 0) {
                            remoteViews2.setContentDescription(R.id.person_icon, this.mContext.getString(R.string.new_story_status_content_description, this.mTile.getUserName()));
                        } else {
                            remoteViews2.setContentDescription(R.id.person_icon, null);
                        }
                    } else {
                        i = 0;
                    }
                    remoteViews2.setViewVisibility(R.id.availability, 8);
                    dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.availability_dot_missing_padding);
                    if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 0) {
                    }
                    remoteViews2.setViewPadding(R.id.padding_before_availability, i3 == 0 ? dimensionPixelSize : i, 0, i3 == 0 ? i : dimensionPixelSize, 0);
                    PeopleSpaceTile peopleSpaceTile52 = this.mTile;
                    if (peopleSpaceTile52.getStatuses() == null) {
                        Context context2 = this.mContext;
                        PeopleSpaceTile peopleSpaceTile62 = this.mTile;
                        Icon userIcon2 = peopleSpaceTile62.getUserIcon();
                        String packageName22 = peopleSpaceTile62.getPackageName();
                        PeopleTileKey peopleTileKey22 = PeopleSpaceUtils.EMPTY_KEY;
                        remoteViews2.setImageViewBitmap(R.id.person_icon, getPersonIconBitmap(context2, maxAvatarSize, z, userIcon2, packageName22, peopleSpaceTile62.getUserHandle().getIdentifier(), peopleSpaceTile62.isImportantConversation(), isDndBlockingTileData(peopleSpaceTile62)));
                        if (z == 0) {
                        }
                    }
                }
                peopleTileKey = this.mKey;
                if (PeopleTileKey.isValid(peopleTileKey) && this.mTile != null) {
                    try {
                        Intent intent = new Intent(this.mContext, (Class<?>) LaunchConversationActivity.class);
                        intent.addFlags(1350598656);
                        intent.putExtra("extra_tile_id", peopleTileKey.mShortcutId);
                        intent.putExtra("extra_package_name", peopleTileKey.mPackageName);
                        intent.putExtra("extra_user_handle", new UserHandle(peopleTileKey.mUserId));
                        peopleSpaceTile = this.mTile;
                        if (peopleSpaceTile != null) {
                            intent.putExtra("extra_notification_key", peopleSpaceTile.getNotificationKey());
                        }
                        remoteViews2.setOnClickPendingIntent(android.R.id.background, PendingIntent.getActivity(this.mContext, this.mAppWidgetId, intent, 167772160));
                    } catch (Exception e2) {
                        EmergencyButton$$ExternalSyntheticOutline0.m("Failed to add launch intents: ", e2, "PeopleTileView");
                    }
                }
                return remoteViews2;
            }
            List listAsList = this.mTile.getStatuses() == null ? Arrays.asList(new ConversationStatus[0]) : (List) this.mTile.getStatuses().stream().filter(new Predicate() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    PeopleTileViewHelper peopleTileViewHelper = this.f$0;
                    ConversationStatus conversationStatus = (ConversationStatus) obj;
                    Pattern pattern = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
                    peopleTileViewHelper.getClass();
                    int activity = conversationStatus.getActivity();
                    return activity == 1 || activity == 2 || !TextUtils.isEmpty(conversationStatus.getDescription()) || conversationStatus.getIcon() != null;
                }
            }).collect(Collectors.toList());
            Optional optionalFindFirst = listAsList.stream().filter(new PeopleTileViewHelper$$ExternalSyntheticLambda0(1)).findFirst();
            ConversationStatus conversationStatusBuild = optionalFindFirst.isPresent() ? (ConversationStatus) optionalFindFirst.get() : !TextUtils.isEmpty(this.mTile.getBirthdayText()) ? new ConversationStatus.Builder(this.mTile.getId(), 1).build() : null;
            if (conversationStatusBuild != null) {
                remoteViews = createStatusRemoteViews(conversationStatusBuild);
            } else if (listAsList.isEmpty()) {
                remoteViews = new RemoteViews(this.mContext.getPackageName(), i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_empty : R.layout.people_tile_medium_empty);
                remoteViews.setInt(R.id.name, "setMaxLines", 1);
                if (i2 == 0) {
                    remoteViews.setViewVisibility(R.id.name, 0);
                    remoteViews.setViewVisibility(R.id.predefined_icon, 8);
                    remoteViews.setViewVisibility(R.id.messages_count, 8);
                }
                if (this.mTile.getUserName() != null) {
                    remoteViews.setTextViewText(R.id.name, this.mTile.getUserName());
                }
                Context context3 = this.mContext;
                long lastInteractionTimestamp = this.mTile.getLastInteractionTimestamp();
                if (lastInteractionTimestamp == 0) {
                    Log.e("PeopleTileView", "Could not get valid last interaction");
                } else {
                    Duration durationOfMillis = Duration.ofMillis(System.currentTimeMillis() - lastInteractionTimestamp);
                    if (durationOfMillis.toDays() > 1) {
                        string = durationOfMillis.toDays() < 7 ? context3.getString(R.string.days_timestamp, Long.valueOf(durationOfMillis.toDays())) : durationOfMillis.toDays() == 7 ? context3.getString(R.string.one_week_timestamp) : durationOfMillis.toDays() < 14 ? context3.getString(R.string.over_one_week_timestamp) : durationOfMillis.toDays() == 14 ? context3.getString(R.string.two_weeks_timestamp) : context3.getString(R.string.over_two_weeks_timestamp);
                    }
                    if (string == null) {
                        remoteViews.setViewVisibility(R.id.last_interaction, 0);
                        remoteViews.setTextViewText(R.id.last_interaction, string);
                    } else {
                        remoteViews.setViewVisibility(R.id.last_interaction, 8);
                        if (i2 == 1) {
                            remoteViews.setInt(R.id.name, "setMaxLines", 3);
                        }
                    }
                }
                string = null;
                if (string == null) {
                }
            } else {
                remoteViews = createStatusRemoteViews((ConversationStatus) listAsList.stream().max(Comparator.comparing(new PeopleTileViewHelper$$ExternalSyntheticLambda3())).get());
            }
        }
        remoteViews2 = remoteViews;
        int maxAvatarSize2 = getMaxAvatarSize(remoteViews2);
        peopleSpaceTile2 = this.mTile;
        if (peopleSpaceTile2 != null) {
        }
        peopleTileKey = this.mKey;
        if (PeopleTileKey.isValid(peopleTileKey)) {
            Intent intent2 = new Intent(this.mContext, (Class<?>) LaunchConversationActivity.class);
            intent2.addFlags(1350598656);
            intent2.putExtra("extra_tile_id", peopleTileKey.mShortcutId);
            intent2.putExtra("extra_package_name", peopleTileKey.mPackageName);
            intent2.putExtra("extra_user_handle", new UserHandle(peopleTileKey.mUserId));
            peopleSpaceTile = this.mTile;
            if (peopleSpaceTile != null) {
            }
            remoteViews2.setOnClickPendingIntent(android.R.id.background, PendingIntent.getActivity(this.mContext, this.mAppWidgetId, intent2, 167772160));
        }
        return remoteViews2;
    }

    public final void setAvailabilityDotPadding(RemoteViews remoteViews, int i) throws Resources.NotFoundException {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(i);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.medium_content_padding_above_name);
        boolean z = this.mIsLeftToRight;
        remoteViews.setViewPadding(R.id.medium_content, z ? dimensionPixelSize : 0, 0, z ? 0 : dimensionPixelSize, dimensionPixelSize2);
    }

    public final void setMaxLines(RemoteViews remoteViews, boolean z) {
        int lineHeightFromResource;
        int i;
        int sizeInDp;
        int i2 = this.mLayoutSize;
        if (i2 == 2) {
            lineHeightFromResource = getLineHeightFromResource(R.dimen.name_text_size_for_large_content);
            i = R.dimen.content_text_size_for_large;
        } else {
            lineHeightFromResource = getLineHeightFromResource(R.dimen.name_text_size_for_medium_content);
            i = R.dimen.content_text_size_for_medium;
        }
        boolean z2 = remoteViews.getLayoutId() == R.layout.people_tile_large_with_status_content;
        int i3 = this.mHeight;
        if (i2 == 1) {
            sizeInDp = i3 - ((this.mMediumVerticalPadding * 2) + (lineHeightFromResource + 12));
        } else if (i2 != 2) {
            sizeInDp = -1;
        } else {
            sizeInDp = i3 - ((getSizeInDp(R.dimen.max_people_avatar_size_for_large_content) + lineHeightFromResource) + (z2 ? 76 : 62));
        }
        int iMax = Math.max(2, Math.floorDiv(sizeInDp, getLineHeightFromResource(i)));
        if (z) {
            iMax--;
        }
        remoteViews.setInt(R.id.text_content, "setMaxLines", iMax);
    }

    public final void setPredefinedIconVisible(RemoteViews remoteViews) throws Resources.NotFoundException {
        remoteViews.setViewVisibility(R.id.predefined_icon, 0);
        if (this.mLayoutSize == 1) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.before_predefined_icon_padding);
            boolean z = this.mIsLeftToRight;
            remoteViews.setViewPadding(R.id.name, z ? 0 : dimensionPixelSize, 0, z ? dimensionPixelSize : 0, 0);
        }
    }

    public final RemoteViews setViewForContentLayout(RemoteViews remoteViews) {
        CharSequence doubleEmoji = getDoubleEmoji("");
        if (TextUtils.isEmpty(doubleEmoji)) {
            CharSequence doublePunctuation = getDoublePunctuation("");
            setEmojiBackground(remoteViews, null);
            setPunctuationBackground(remoteViews, doublePunctuation);
        } else {
            setEmojiBackground(remoteViews, doubleEmoji);
            setPunctuationBackground(remoteViews, null);
        }
        remoteViews.setContentDescription(R.id.predefined_icon, null);
        remoteViews.setContentDescription(R.id.text_content, null);
        remoteViews.setContentDescription(R.id.name, null);
        remoteViews.setContentDescription(R.id.image, null);
        remoteViews.setAccessibilityTraversalAfter(R.id.text_content, R.id.name);
        int i = this.mLayoutSize;
        if (i == 0) {
            remoteViews.setViewVisibility(R.id.predefined_icon, 0);
            remoteViews.setViewVisibility(R.id.name, 8);
        } else {
            remoteViews.setViewVisibility(R.id.predefined_icon, 8);
            remoteViews.setViewVisibility(R.id.name, 0);
            remoteViews.setViewVisibility(R.id.text_content, 0);
            remoteViews.setViewVisibility(R.id.subtext, 8);
            remoteViews.setViewVisibility(R.id.image, 8);
            remoteViews.setViewVisibility(R.id.scrim_layout, 8);
        }
        if (i == 1) {
            float f = this.mDensity;
            int iFloor = (int) Math.floor(16.0f * f);
            int iFloor2 = (int) Math.floor(this.mMediumVerticalPadding * f);
            remoteViews.setViewPadding(R.id.content, iFloor, iFloor2, iFloor, iFloor2);
            remoteViews.setViewPadding(R.id.name, 0, 0, 0, 0);
            if (this.mHeight > ((int) (this.mContext.getResources().getDimension(R.dimen.medium_height_for_max_name_text_size) / f))) {
                remoteViews.setTextViewTextSize(R.id.name, 0, (int) this.mContext.getResources().getDimension(R.dimen.max_name_text_size_for_medium));
            }
        }
        if (i == 2) {
            remoteViews.setViewPadding(R.id.name, 0, 0, 0, this.mContext.getResources().getDimensionPixelSize(R.dimen.below_name_text_padding));
            remoteViews.setInt(R.id.content, "setGravity", 48);
        }
        remoteViews.setViewLayoutHeightDimen(R.id.predefined_icon, R.dimen.regular_predefined_icon);
        remoteViews.setViewLayoutWidthDimen(R.id.predefined_icon, R.dimen.regular_predefined_icon);
        remoteViews.setViewVisibility(R.id.messages_count, 8);
        if (this.mTile.getUserName() != null) {
            remoteViews.setTextViewText(R.id.name, this.mTile.getUserName());
        }
        return remoteViews;
    }
}
