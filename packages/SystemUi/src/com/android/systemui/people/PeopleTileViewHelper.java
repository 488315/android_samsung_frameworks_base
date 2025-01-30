package com.android.systemui.people;

import android.app.PendingIntent;
import android.app.people.ConversationStatus;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.core.math.MathUtils;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PeopleTileViewHelper {
    public final int mAppWidgetId;
    public final Context mContext;
    public final float mDensity;
    public final int mHeight;
    public NumberFormat mIntegerFormat;
    public final boolean mIsLeftToRight;
    public final PeopleTileKey mKey;
    public final int mLayoutSize;
    public Locale mLocale;
    public int mMediumVerticalPadding;
    public final PeopleSpaceTile mTile;
    public final int mWidth;
    public static final Pattern DOUBLE_EXCLAMATION_PATTERN = Pattern.compile("[!][!]+");
    public static final Pattern DOUBLE_QUESTION_PATTERN = Pattern.compile("[?][?]+");
    public static final Pattern ANY_DOUBLE_MARK_PATTERN = Pattern.compile("[!?][!?]+");
    public static final Pattern MIXED_MARK_PATTERN = Pattern.compile("![?].*|.*[?]!");
    public static final Pattern EMOJI_PATTERN = Pattern.compile("\\p{RI}\\p{RI}|(\\p{Emoji}(\\p{EMod}|\\x{FE0F}\\x{20E3}?|[\\x{E0020}-\\x{E007E}]+\\x{E007F})|[\\p{Emoji}&&\\p{So}])(\\x{200D}\\p{Emoji}(\\p{EMod}|\\x{FE0F}\\x{20E3}?|[\\x{E0020}-\\x{E007E}]+\\x{E007F})?)*");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        return new RemoteViews((Map<SizeF, RemoteViews>) parcelableArrayList.stream().distinct().collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                SizeF sizeF = (SizeF) obj;
                return new PeopleTileViewHelper(context, peopleSpaceTile, i, (int) sizeF.getWidth(), (int) sizeF.getHeight(), peopleTileKey).getViews();
            }
        })));
    }

    public static boolean getHasNewStory(PeopleSpaceTile peopleSpaceTile) {
        return peopleSpaceTile.getStatuses() != null && peopleSpaceTile.getStatuses().stream().anyMatch(new PeopleTileViewHelper$$ExternalSyntheticLambda3(1));
    }

    public static Bitmap getPersonIconBitmap(Context context, int i, boolean z, Icon icon, String str, int i2, boolean z2, boolean z3) {
        Drawable defaultActivityIcon;
        if (icon == null) {
            Drawable mutate = context.getDrawable(R.drawable.ic_avatar_with_badge).mutate();
            mutate.setColorFilter(FastBitmapDrawable.getDisabledColorFilter(1.0f));
            return PeopleSpaceUtils.convertDrawableToBitmap(mutate);
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

    /* JADX WARN: Removed duplicated region for block: B:24:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final RemoteViewsAndSizes createDndRemoteViews() {
        int i;
        StaticLayout staticLayout;
        int height;
        TextView textView;
        Context context = this.mContext;
        String packageName = context.getPackageName();
        int i2 = this.mLayoutSize;
        RemoteViews remoteViews = new RemoteViews(packageName, i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_with_suppression_detail_content_vertical : R.layout.people_tile_with_suppression_detail_content_horizontal);
        int sizeInDp = getSizeInDp(R.dimen.avatar_size_for_medium_empty);
        int sizeInDp2 = getSizeInDp(R.dimen.max_people_avatar_size);
        String string = context.getString(R.string.paused_by_dnd);
        remoteViews.setTextViewText(R.id.text_content, string);
        int i3 = i2 == 2 ? R.dimen.content_text_size_for_large : R.dimen.content_text_size_for_medium;
        remoteViews.setTextViewTextSize(R.id.text_content, 0, context.getResources().getDimension(i3));
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
                textView = new TextView(context);
                i = i5;
            } catch (Exception e) {
                e = e;
                i = i5;
            }
            try {
                textView.setTextSize(0, context.getResources().getDimension(i3));
                textView.setTextAppearance(android.R.style.TextAppearance.DeviceDefault);
                staticLayout = StaticLayout.Builder.obtain(string, 0, string.length(), textView.getPaint(), (int) (i9 * f)).setBreakStrategy(0).build();
            } catch (Exception e2) {
                e = e2;
                EmergencyButton$$ExternalSyntheticOutline0.m58m("Could not create static layout: ", e, "PeopleTileView");
                staticLayout = null;
                if (staticLayout != null) {
                }
                if (height <= i11) {
                }
                if (i2 != 0) {
                }
                sizeInDp = getMaxAvatarSize(remoteViews);
                remoteViews.setViewVisibility(R.id.messages_count, 8);
                remoteViews.setViewVisibility(R.id.name, 8);
                remoteViews.setContentDescription(R.id.predefined_icon, string);
                remoteViews.setViewVisibility(R.id.predefined_icon, 0);
                remoteViews.setImageViewResource(R.id.predefined_icon, R.drawable.ic_qs_dnd_on);
                return new RemoteViewsAndSizes(remoteViews, sizeInDp);
            }
            height = staticLayout != null ? Integer.MAX_VALUE : (int) (staticLayout.getHeight() / f);
            if (height <= i11 || i2 != 2) {
                if (i2 != 0) {
                    remoteViews = new RemoteViews(context.getPackageName(), R.layout.people_tile_small);
                }
                sizeInDp = getMaxAvatarSize(remoteViews);
                remoteViews.setViewVisibility(R.id.messages_count, 8);
                remoteViews.setViewVisibility(R.id.name, 8);
                remoteViews.setContentDescription(R.id.predefined_icon, string);
            } else {
                remoteViews.setViewVisibility(R.id.text_content, 0);
                remoteViews.setInt(R.id.text_content, "setMaxLines", i11 / lineHeightFromResource);
                remoteViews.setContentDescription(R.id.predefined_icon, null);
                sizeInDp = MathUtils.clamp(Math.min(i8 - 32, (i7 - height) - i10), (int) (f * 10.0f), sizeInDp2);
                remoteViews.setViewPadding(android.R.id.background, i, i6, i, i);
                float f2 = sizeInDp3;
                remoteViews.setViewLayoutWidth(R.id.predefined_icon, f2, 1);
                remoteViews.setViewLayoutHeight(R.id.predefined_icon, f2, 1);
            }
            remoteViews.setViewVisibility(R.id.predefined_icon, 0);
            remoteViews.setImageViewResource(R.id.predefined_icon, R.drawable.ic_qs_dnd_on);
        }
        return new RemoteViewsAndSizes(remoteViews, sizeInDp);
    }

    public final RemoteViews createStatusRemoteViews(ConversationStatus conversationStatus) {
        int i;
        Context context = this.mContext;
        String packageName = context.getPackageName();
        int i2 = this.mLayoutSize;
        RemoteViews remoteViews = new RemoteViews(packageName, i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_with_status_content : R.layout.people_tile_medium_with_content);
        setViewForContentLayout(remoteViews);
        CharSequence description = conversationStatus.getDescription();
        CharSequence charSequence = "";
        if (TextUtils.isEmpty(description)) {
            switch (conversationStatus.getActivity()) {
                case 1:
                    description = context.getString(R.string.birthday_status);
                    break;
                case 2:
                    description = context.getString(R.string.anniversary_status);
                    break;
                case 3:
                    description = context.getString(R.string.new_story_status);
                    break;
                case 4:
                    description = context.getString(R.string.audio_status);
                    break;
                case 5:
                    description = context.getString(R.string.video_status);
                    break;
                case 6:
                    description = context.getString(R.string.game_status);
                    break;
                case 7:
                    description = context.getString(R.string.location_status);
                    break;
                case 8:
                    description = context.getString(R.string.upcoming_birthday_status);
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
        PeopleSpaceTile peopleSpaceTile = this.mTile;
        CharSequence userName = peopleSpaceTile.getUserName();
        if (TextUtils.isEmpty(conversationStatus.getDescription())) {
            switch (conversationStatus.getActivity()) {
                case 1:
                    charSequence = context.getString(R.string.birthday_status_content_description, userName);
                    break;
                case 2:
                    charSequence = context.getString(R.string.anniversary_status_content_description, userName);
                    break;
                case 3:
                    charSequence = context.getString(R.string.new_story_status_content_description, userName);
                    break;
                case 4:
                    charSequence = context.getString(R.string.audio_status);
                    break;
                case 5:
                    charSequence = context.getString(R.string.video_status);
                    break;
                case 6:
                    charSequence = context.getString(R.string.game_status);
                    break;
                case 7:
                    charSequence = context.getString(R.string.location_status_content_description, userName);
                    break;
                case 8:
                    charSequence = context.getString(R.string.upcoming_birthday_status_content_description, userName);
                    break;
            }
        } else {
            charSequence = conversationStatus.getDescription();
        }
        String string = context.getString(R.string.new_status_content_description, peopleSpaceTile.getUserName(), charSequence);
        if (i2 == 0) {
            remoteViews.setContentDescription(R.id.predefined_icon, string);
        } else if (i2 == 1) {
            if (icon != null) {
                i3 = R.id.name;
            }
            remoteViews.setContentDescription(i3, string);
        } else if (i2 == 2) {
            remoteViews.setContentDescription(R.id.text_content, string);
        }
        return remoteViews;
    }

    public final RemoteViews decorateBackground(RemoteViews remoteViews, CharSequence charSequence) {
        CharSequence doubleEmoji = getDoubleEmoji(charSequence);
        if (!TextUtils.isEmpty(doubleEmoji)) {
            setEmojiBackground(remoteViews, doubleEmoji);
            setPunctuationBackground(remoteViews, null);
            return remoteViews;
        }
        CharSequence doublePunctuation = getDoublePunctuation(charSequence);
        setEmojiBackground(remoteViews, null);
        setPunctuationBackground(remoteViews, doublePunctuation);
        return remoteViews;
    }

    public CharSequence getDoubleEmoji(CharSequence charSequence) {
        Matcher matcher = EMOJI_PATTERN.matcher(charSequence);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            arrayList.add(new Pair(Integer.valueOf(start), Integer.valueOf(end)));
            arrayList2.add(charSequence.subSequence(start, end));
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
        Context context = this.mContext;
        try {
            TextView textView = new TextView(context);
            textView.setTextSize(0, context.getResources().getDimension(i));
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
            sizeInDp = Math.min(i2 - ((((getLineHeightFromResource(R.dimen.content_text_size_for_large) + (getLineHeightFromResource(R.dimen.name_text_size_for_large) + 28)) + 16) + 10) + 16), i - 28);
        }
        if (isDndBlockingTileData(this.mTile) && this.mLayoutSize != 0) {
            sizeInDp = createDndRemoteViews().mAvatarSize;
        }
        return Math.min(sizeInDp, getSizeInDp(R.dimen.max_people_avatar_size));
    }

    public final int getSizeInDp(int i) {
        return (int) (this.mContext.getResources().getDimension(i) / this.mDensity);
    }

    /* JADX WARN: Removed duplicated region for block: B:149:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x03dc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public RemoteViews getViews() {
        RemoteViews remoteViews;
        RemoteViews remoteViews2;
        int dimensionPixelSize;
        PeopleTileKey peopleTileKey;
        String string;
        int i;
        String format;
        PeopleSpaceTile peopleSpaceTile = this.mTile;
        Context context = this.mContext;
        if (peopleSpaceTile == null || peopleSpaceTile.isPackageSuspended() || peopleSpaceTile.isUserQuieted()) {
            remoteViews = (peopleSpaceTile == null || !peopleSpaceTile.isUserQuieted()) ? new RemoteViews(context.getPackageName(), R.layout.people_tile_suppressed_layout) : new RemoteViews(context.getPackageName(), R.layout.people_tile_work_profile_quiet_layout);
            Drawable mutate = context.getDrawable(R.drawable.ic_conversation_icon).mutate();
            mutate.setColorFilter(FastBitmapDrawable.getDisabledColorFilter(1.0f));
            remoteViews.setImageViewBitmap(R.id.icon, PeopleSpaceUtils.convertDrawableToBitmap(mutate));
        } else if (isDndBlockingTileData(peopleSpaceTile)) {
            remoteViews = createDndRemoteViews().mRemoteViews;
        } else {
            boolean equals = Objects.equals(peopleSpaceTile.getNotificationCategory(), "missed_call");
            int i2 = this.mLayoutSize;
            if (equals) {
                RemoteViews remoteViews3 = new RemoteViews(context.getPackageName(), i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_with_status_content : R.layout.people_tile_medium_with_content);
                setViewForContentLayout(remoteViews3);
                setPredefinedIconVisible(remoteViews3);
                remoteViews3.setViewVisibility(R.id.text_content, 0);
                remoteViews3.setViewVisibility(R.id.messages_count, 8);
                setMaxLines(remoteViews3, false);
                CharSequence notificationContent = peopleSpaceTile.getNotificationContent();
                remoteViews3.setTextViewText(R.id.text_content, notificationContent);
                remoteViews3.setContentDescription(i2 == 0 ? R.id.predefined_icon : R.id.text_content, context.getString(R.string.new_notification_text_content_description, peopleSpaceTile.getUserName(), notificationContent));
                remoteViews3.setColorAttr(R.id.text_content, "setTextColor", android.R.attr.colorError);
                remoteViews3.setColorAttr(R.id.predefined_icon, "setColorFilter", android.R.attr.colorError);
                remoteViews3.setImageViewResource(R.id.predefined_icon, R.drawable.ic_phone_missed);
                if (i2 == 2) {
                    remoteViews3.setInt(R.id.content, "setGravity", 80);
                    remoteViews3.setViewLayoutHeightDimen(R.id.predefined_icon, R.dimen.larger_predefined_icon);
                    remoteViews3.setViewLayoutWidthDimen(R.id.predefined_icon, R.dimen.larger_predefined_icon);
                }
                setAvailabilityDotPadding(remoteViews3, R.dimen.availability_dot_notification_padding);
                remoteViews = remoteViews3;
            } else {
                if (peopleSpaceTile.getNotificationKey() != null) {
                    RemoteViews remoteViews4 = new RemoteViews(context.getPackageName(), i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_with_notification_content : R.layout.people_tile_medium_with_content);
                    setViewForContentLayout(remoteViews4);
                    CharSequence notificationSender = peopleSpaceTile.getNotificationSender();
                    Uri notificationDataUri = peopleSpaceTile.getNotificationDataUri();
                    if (notificationDataUri != null) {
                        String string2 = context.getString(R.string.new_notification_image_content_description, peopleSpaceTile.getUserName());
                        remoteViews4.setContentDescription(R.id.image, string2);
                        remoteViews4.setViewVisibility(R.id.image, 0);
                        remoteViews4.setViewVisibility(R.id.text_content, 8);
                        try {
                            remoteViews4.setImageViewBitmap(R.id.image, PeopleSpaceUtils.convertDrawableToBitmap(ImageDecoder.decodeDrawable(ImageDecoder.createSource(context.getContentResolver(), notificationDataUri), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda4
                                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                    PeopleTileViewHelper peopleTileViewHelper = PeopleTileViewHelper.this;
                                    float f = peopleTileViewHelper.mWidth;
                                    Context context2 = peopleTileViewHelper.mContext;
                                    int max = Math.max((int) TypedValue.applyDimension(1, f, context2.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(1, peopleTileViewHelper.mHeight, context2.getResources().getDisplayMetrics()));
                                    int min = (int) (Math.min(r10, r7) * 1.5d);
                                    if (min < max) {
                                        max = min;
                                    }
                                    Size size = imageInfo.getSize();
                                    imageDecoder.setTargetSampleSize(Math.max(1, Integer.highestOneBit((int) Math.floor(Math.max(size.getHeight(), size.getWidth()) > max ? (r7 * 1.0f) / max : 1.0d))));
                                }
                            })));
                        } catch (IOException | SecurityException e) {
                            Log.e("PeopleTileView", "Could not decode image: " + e);
                            remoteViews4.setTextViewText(R.id.text_content, string2);
                            remoteViews4.setViewVisibility(R.id.text_content, 0);
                            remoteViews4.setViewVisibility(R.id.image, 8);
                        }
                    } else {
                        setMaxLines(remoteViews4, !TextUtils.isEmpty(notificationSender));
                        CharSequence notificationContent2 = peopleSpaceTile.getNotificationContent();
                        remoteViews4.setContentDescription(i2 == 0 ? R.id.predefined_icon : R.id.text_content, context.getString(R.string.new_notification_text_content_description, notificationSender != null ? notificationSender : peopleSpaceTile.getUserName(), notificationContent2));
                        decorateBackground(remoteViews4, notificationContent2);
                        remoteViews4.setColorAttr(R.id.text_content, "setTextColor", android.R.attr.textColorPrimary);
                        remoteViews4.setTextViewText(R.id.text_content, peopleSpaceTile.getNotificationContent());
                        if (i2 == 2) {
                            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.above_notification_text_padding);
                            i = R.id.image;
                            remoteViews4.setViewPadding(R.id.name, 0, 0, 0, dimensionPixelSize2);
                        } else {
                            i = R.id.image;
                        }
                        remoteViews4.setViewVisibility(i, 8);
                        remoteViews4.setImageViewResource(R.id.predefined_icon, R.drawable.ic_message);
                    }
                    if (peopleSpaceTile.getMessagesCount() > 1) {
                        if (i2 == 1) {
                            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.before_messages_count_padding);
                            boolean z = this.mIsLeftToRight;
                            int i3 = z ? 0 : dimensionPixelSize3;
                            if (!z) {
                                dimensionPixelSize3 = 0;
                            }
                            remoteViews4.setViewPadding(R.id.name, i3, 0, dimensionPixelSize3, 0);
                        }
                        remoteViews4.setViewVisibility(R.id.messages_count, 0);
                        int messagesCount = peopleSpaceTile.getMessagesCount();
                        if (messagesCount >= 6) {
                            format = context.getResources().getString(R.string.messages_count_overflow_indicator, 6);
                        } else {
                            Locale locale = context.getResources().getConfiguration().getLocales().get(0);
                            if (!locale.equals(this.mLocale)) {
                                this.mLocale = locale;
                                this.mIntegerFormat = NumberFormat.getIntegerInstance(locale);
                            }
                            format = this.mIntegerFormat.format(messagesCount);
                        }
                        remoteViews4.setTextViewText(R.id.messages_count, format);
                        if (i2 == 0) {
                            remoteViews4.setViewVisibility(R.id.predefined_icon, 8);
                        }
                    }
                    if (TextUtils.isEmpty(notificationSender)) {
                        remoteViews4.setViewVisibility(R.id.subtext, 8);
                    } else {
                        remoteViews4.setViewVisibility(R.id.subtext, 0);
                        remoteViews4.setTextViewText(R.id.subtext, notificationSender);
                    }
                    setAvailabilityDotPadding(remoteViews4, R.dimen.availability_dot_notification_padding);
                    remoteViews2 = remoteViews4;
                    int maxAvatarSize = getMaxAvatarSize(remoteViews2);
                    if (peopleSpaceTile != null) {
                        try {
                            if (peopleSpaceTile.getStatuses() != null && peopleSpaceTile.getStatuses().stream().anyMatch(new PeopleTileViewHelper$$ExternalSyntheticLambda3(0))) {
                                remoteViews2.setViewVisibility(R.id.availability, 0);
                                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.availability_dot_shown_padding);
                                remoteViews2.setContentDescription(R.id.availability, context.getString(R.string.person_available));
                            } else {
                                remoteViews2.setViewVisibility(R.id.availability, 8);
                                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.availability_dot_missing_padding);
                            }
                            boolean z2 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 0;
                            int i4 = z2 ? dimensionPixelSize : 0;
                            if (z2) {
                                dimensionPixelSize = 0;
                            }
                            remoteViews2.setViewPadding(R.id.padding_before_availability, i4, 0, dimensionPixelSize, 0);
                            boolean hasNewStory = getHasNewStory(peopleSpaceTile);
                            Context context2 = this.mContext;
                            Icon userIcon = peopleSpaceTile.getUserIcon();
                            String packageName = peopleSpaceTile.getPackageName();
                            PeopleTileKey peopleTileKey2 = PeopleSpaceUtils.EMPTY_KEY;
                            remoteViews2.setImageViewBitmap(R.id.person_icon, getPersonIconBitmap(context2, maxAvatarSize, hasNewStory, userIcon, packageName, peopleSpaceTile.getUserHandle().getIdentifier(), peopleSpaceTile.isImportantConversation(), isDndBlockingTileData(peopleSpaceTile)));
                            if (hasNewStory) {
                                remoteViews2.setContentDescription(R.id.person_icon, context.getString(R.string.new_story_status_content_description, peopleSpaceTile.getUserName()));
                            } else {
                                remoteViews2.setContentDescription(R.id.person_icon, null);
                            }
                        } catch (Exception e2) {
                            EmergencyButton$$ExternalSyntheticOutline0.m58m("Failed to set common fields: ", e2, "PeopleTileView");
                        }
                    }
                    peopleTileKey = this.mKey;
                    if (PeopleTileKey.isValid(peopleTileKey) && peopleSpaceTile != null) {
                        try {
                            Intent intent = new Intent(context, (Class<?>) LaunchConversationActivity.class);
                            intent.addFlags(1350598656);
                            intent.putExtra("extra_tile_id", peopleTileKey.mShortcutId);
                            intent.putExtra("extra_package_name", peopleTileKey.mPackageName);
                            intent.putExtra("extra_user_handle", new UserHandle(peopleTileKey.mUserId));
                            intent.putExtra("extra_notification_key", peopleSpaceTile.getNotificationKey());
                            remoteViews2.setOnClickPendingIntent(android.R.id.background, PendingIntent.getActivity(context, this.mAppWidgetId, intent, 167772160));
                        } catch (Exception e3) {
                            EmergencyButton$$ExternalSyntheticOutline0.m58m("Failed to add launch intents: ", e3, "PeopleTileView");
                        }
                    }
                    return remoteViews2;
                }
                List asList = peopleSpaceTile.getStatuses() == null ? Arrays.asList(new ConversationStatus[0]) : (List) peopleSpaceTile.getStatuses().stream().filter(new Predicate() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        ConversationStatus conversationStatus = (ConversationStatus) obj;
                        PeopleTileViewHelper.this.getClass();
                        int activity = conversationStatus.getActivity();
                        return activity == 1 || activity == 2 || !TextUtils.isEmpty(conversationStatus.getDescription()) || conversationStatus.getIcon() != null;
                    }
                }).collect(Collectors.toList());
                Optional findFirst = asList.stream().filter(new PeopleTileViewHelper$$ExternalSyntheticLambda3(2)).findFirst();
                ConversationStatus build = findFirst.isPresent() ? (ConversationStatus) findFirst.get() : !TextUtils.isEmpty(peopleSpaceTile.getBirthdayText()) ? new ConversationStatus.Builder(peopleSpaceTile.getId(), 1).build() : null;
                if (build != null) {
                    remoteViews = createStatusRemoteViews(build);
                } else if (asList.isEmpty()) {
                    remoteViews = new RemoteViews(context.getPackageName(), i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_empty : R.layout.people_tile_medium_empty);
                    remoteViews.setInt(R.id.name, "setMaxLines", 1);
                    if (i2 == 0) {
                        remoteViews.setViewVisibility(R.id.name, 0);
                        remoteViews.setViewVisibility(R.id.predefined_icon, 8);
                        remoteViews.setViewVisibility(R.id.messages_count, 8);
                    }
                    if (peopleSpaceTile.getUserName() != null) {
                        remoteViews.setTextViewText(R.id.name, peopleSpaceTile.getUserName());
                    }
                    long lastInteractionTimestamp = peopleSpaceTile.getLastInteractionTimestamp();
                    if (lastInteractionTimestamp == 0) {
                        Log.e("PeopleTileView", "Could not get valid last interaction");
                    } else {
                        Duration ofMillis = Duration.ofMillis(System.currentTimeMillis() - lastInteractionTimestamp);
                        if (ofMillis.toDays() > 1) {
                            string = ofMillis.toDays() < 7 ? context.getString(R.string.days_timestamp, Long.valueOf(ofMillis.toDays())) : ofMillis.toDays() == 7 ? context.getString(R.string.one_week_timestamp) : ofMillis.toDays() < 14 ? context.getString(R.string.over_one_week_timestamp) : ofMillis.toDays() == 14 ? context.getString(R.string.two_weeks_timestamp) : context.getString(R.string.over_two_weeks_timestamp);
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
                    }
                    string = null;
                    if (string == null) {
                    }
                } else {
                    remoteViews = createStatusRemoteViews((ConversationStatus) asList.stream().max(Comparator.comparing(new PeopleTileViewHelper$$ExternalSyntheticLambda1())).get());
                }
            }
        }
        remoteViews2 = remoteViews;
        int maxAvatarSize2 = getMaxAvatarSize(remoteViews2);
        if (peopleSpaceTile != null) {
        }
        peopleTileKey = this.mKey;
        if (PeopleTileKey.isValid(peopleTileKey)) {
            Intent intent2 = new Intent(context, (Class<?>) LaunchConversationActivity.class);
            intent2.addFlags(1350598656);
            intent2.putExtra("extra_tile_id", peopleTileKey.mShortcutId);
            intent2.putExtra("extra_package_name", peopleTileKey.mPackageName);
            intent2.putExtra("extra_user_handle", new UserHandle(peopleTileKey.mUserId));
            intent2.putExtra("extra_notification_key", peopleSpaceTile.getNotificationKey());
            remoteViews2.setOnClickPendingIntent(android.R.id.background, PendingIntent.getActivity(context, this.mAppWidgetId, intent2, 167772160));
        }
        return remoteViews2;
    }

    public final void setAvailabilityDotPadding(RemoteViews remoteViews, int i) {
        Context context = this.mContext;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(i);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.medium_content_padding_above_name);
        boolean z = this.mIsLeftToRight;
        remoteViews.setViewPadding(R.id.medium_content, z ? dimensionPixelSize : 0, 0, z ? 0 : dimensionPixelSize, dimensionPixelSize2);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setMaxLines(RemoteViews remoteViews, boolean z) {
        int lineHeightFromResource;
        int i;
        int i2;
        int i3;
        int i4 = this.mLayoutSize;
        if (i4 == 2) {
            lineHeightFromResource = getLineHeightFromResource(R.dimen.name_text_size_for_large_content);
            i = R.dimen.content_text_size_for_large;
        } else {
            lineHeightFromResource = getLineHeightFromResource(R.dimen.name_text_size_for_medium_content);
            i = R.dimen.content_text_size_for_medium;
        }
        boolean z2 = remoteViews.getLayoutId() == R.layout.people_tile_large_with_status_content;
        if (i4 == 1) {
            i2 = (this.mMediumVerticalPadding * 2) + lineHeightFromResource + 12;
        } else {
            if (i4 != 2) {
                i3 = -1;
                int max = Math.max(2, Math.floorDiv(i3, getLineHeightFromResource(i)));
                if (z) {
                    max--;
                }
                remoteViews.setInt(R.id.text_content, "setMaxLines", max);
            }
            i2 = getSizeInDp(R.dimen.max_people_avatar_size_for_large_content) + lineHeightFromResource + (z2 ? 76 : 62);
        }
        i3 = this.mHeight - i2;
        int max2 = Math.max(2, Math.floorDiv(i3, getLineHeightFromResource(i)));
        if (z) {
        }
        remoteViews.setInt(R.id.text_content, "setMaxLines", max2);
    }

    public final void setPredefinedIconVisible(RemoteViews remoteViews) {
        remoteViews.setViewVisibility(R.id.predefined_icon, 0);
        if (this.mLayoutSize == 1) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.before_predefined_icon_padding);
            boolean z = this.mIsLeftToRight;
            remoteViews.setViewPadding(R.id.name, z ? 0 : dimensionPixelSize, 0, z ? dimensionPixelSize : 0, 0);
        }
    }

    public final RemoteViews setViewForContentLayout(RemoteViews remoteViews) {
        decorateBackground(remoteViews, "");
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
        Context context = this.mContext;
        if (i == 1) {
            float f = this.mDensity;
            int floor = (int) Math.floor(16.0f * f);
            int floor2 = (int) Math.floor(this.mMediumVerticalPadding * f);
            remoteViews.setViewPadding(R.id.content, floor, floor2, floor, floor2);
            remoteViews.setViewPadding(R.id.name, 0, 0, 0, 0);
            if (this.mHeight > ((int) (context.getResources().getDimension(R.dimen.medium_height_for_max_name_text_size) / f))) {
                remoteViews.setTextViewTextSize(R.id.name, 0, (int) context.getResources().getDimension(R.dimen.max_name_text_size_for_medium));
            }
        }
        if (i == 2) {
            remoteViews.setViewPadding(R.id.name, 0, 0, 0, context.getResources().getDimensionPixelSize(R.dimen.below_name_text_padding));
            remoteViews.setInt(R.id.content, "setGravity", 48);
        }
        remoteViews.setViewLayoutHeightDimen(R.id.predefined_icon, R.dimen.regular_predefined_icon);
        remoteViews.setViewLayoutWidthDimen(R.id.predefined_icon, R.dimen.regular_predefined_icon);
        remoteViews.setViewVisibility(R.id.messages_count, 8);
        PeopleSpaceTile peopleSpaceTile = this.mTile;
        if (peopleSpaceTile.getUserName() != null) {
            remoteViews.setTextViewText(R.id.name, peopleSpaceTile.getUserName());
        }
        return remoteViews;
    }
}
