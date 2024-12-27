package com.android.systemui.people;

import android.app.people.ConversationStatus;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.Pair;
import android.util.SizeF;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.core.math.MathUtils;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.people.PeopleStoryIconFactory;
import com.android.systemui.people.widget.PeopleTileKey;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public final RemoteViewsAndSizes createDndRemoteViews() {
        StaticLayout staticLayout;
        String packageName = this.mContext.getPackageName();
        int i = this.mLayoutSize;
        RemoteViews remoteViews = new RemoteViews(packageName, i != 1 ? i != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_with_suppression_detail_content_vertical : R.layout.people_tile_with_suppression_detail_content_horizontal);
        int sizeInDp = getSizeInDp(R.dimen.avatar_size_for_medium_empty);
        int sizeInDp2 = getSizeInDp(R.dimen.max_people_avatar_size);
        String string = this.mContext.getString(R.string.paused_by_dnd);
        remoteViews.setTextViewText(R.id.text_content, string);
        int i2 = i == 2 ? R.dimen.content_text_size_for_large : R.dimen.content_text_size_for_medium;
        remoteViews.setTextViewTextSize(R.id.text_content, 0, this.mContext.getResources().getDimension(i2));
        int lineHeightFromResource = getLineHeightFromResource(i2);
        int i3 = this.mHeight;
        if (i == 1) {
            remoteViews.setInt(R.id.text_content, "setMaxLines", (i3 - 16) / lineHeightFromResource);
        } else {
            float f = this.mDensity;
            int i4 = (int) (16 * f);
            int i5 = (int) (14 * f);
            int sizeInDp3 = getSizeInDp(i == 0 ? R.dimen.regular_predefined_icon : R.dimen.largest_predefined_icon);
            int i6 = (i3 - 32) - sizeInDp3;
            int sizeInDp4 = getSizeInDp(R.dimen.padding_between_suppressed_layout_items);
            int i7 = this.mWidth;
            int i8 = i7 - 32;
            int i9 = sizeInDp4 * 2;
            int i10 = (i6 - sizeInDp) - i9;
            try {
                TextView textView = new TextView(this.mContext);
                textView.setTextSize(0, this.mContext.getResources().getDimension(i2));
                textView.setTextAppearance(android.R.style.TextAppearance.DeviceDefault);
                staticLayout = StaticLayout.Builder.obtain(string, 0, string.length(), textView.getPaint(), (int) (i8 * f)).setBreakStrategy(0).build();
            } catch (Exception e) {
                EmergencyButton$$ExternalSyntheticOutline0.m("Could not create static layout: ", e, "PeopleTileView");
                staticLayout = null;
            }
            int height = staticLayout == null ? Integer.MAX_VALUE : (int) (staticLayout.getHeight() / f);
            if (height > i10 || i != 2) {
                if (i != 0) {
                    remoteViews = new RemoteViews(this.mContext.getPackageName(), R.layout.people_tile_small);
                }
                sizeInDp = getMaxAvatarSize(remoteViews);
                remoteViews.setViewVisibility(R.id.messages_count, 8);
                remoteViews.setViewVisibility(R.id.name, 8);
                remoteViews.setContentDescription(R.id.predefined_icon, string);
            } else {
                remoteViews.setViewVisibility(R.id.text_content, 0);
                remoteViews.setInt(R.id.text_content, "setMaxLines", i10 / lineHeightFromResource);
                remoteViews.setContentDescription(R.id.predefined_icon, null);
                sizeInDp = MathUtils.clamp(Math.min(i7 - 32, (i6 - height) - i9), (int) (10.0f * f), sizeInDp2);
                remoteViews.setViewPadding(android.R.id.background, i4, i5, i4, i4);
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
        int i2 = this.mLayoutSize;
        RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), i2 != 1 ? i2 != 2 ? getLayoutSmallByHeight() : R.layout.people_tile_large_with_status_content : R.layout.people_tile_medium_with_content);
        setViewForContentLayout(remoteViews);
        CharSequence description = conversationStatus.getDescription();
        CharSequence charSequence = "";
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
                    charSequence = this.mContext.getString(R.string.birthday_status_content_description, userName);
                    break;
                case 2:
                    charSequence = this.mContext.getString(R.string.anniversary_status_content_description, userName);
                    break;
                case 3:
                    charSequence = this.mContext.getString(R.string.new_story_status_content_description, userName);
                    break;
                case 4:
                    charSequence = this.mContext.getString(R.string.audio_status);
                    break;
                case 5:
                    charSequence = this.mContext.getString(R.string.video_status);
                    break;
                case 6:
                    charSequence = this.mContext.getString(R.string.game_status);
                    break;
                case 7:
                    charSequence = this.mContext.getString(R.string.location_status_content_description, userName);
                    break;
                case 8:
                    charSequence = this.mContext.getString(R.string.upcoming_birthday_status_content_description, userName);
                    break;
            }
        } else {
            charSequence = conversationStatus.getDescription();
        }
        String string = this.mContext.getString(R.string.new_status_content_description, this.mTile.getUserName(), charSequence);
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

    public CharSequence getDoubleEmoji(CharSequence charSequence) {
        Matcher matcher = EmojiHelper.EMOJI_PATTERN.matcher(charSequence);
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

    /* JADX WARN: Code restructure failed: missing block: B:59:0x0473, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x050d, code lost:
    
        com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m("Failed to set common fields: ", r0, "PeopleTileView");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.widget.RemoteViews getViews() {
        /*
            Method dump skipped, instructions count: 1390
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.PeopleTileViewHelper.getViews():android.widget.RemoteViews");
    }

    public final void setAvailabilityDotPadding(RemoteViews remoteViews, int i) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(i);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.medium_content_padding_above_name);
        boolean z = this.mIsLeftToRight;
        remoteViews.setViewPadding(R.id.medium_content, z ? dimensionPixelSize : 0, 0, z ? 0 : dimensionPixelSize, dimensionPixelSize2);
    }

    public final void setMaxLines(RemoteViews remoteViews, boolean z) {
        int lineHeightFromResource;
        int i;
        int i2;
        int i3 = this.mLayoutSize;
        if (i3 == 2) {
            lineHeightFromResource = getLineHeightFromResource(R.dimen.name_text_size_for_large_content);
            i = R.dimen.content_text_size_for_large;
        } else {
            lineHeightFromResource = getLineHeightFromResource(R.dimen.name_text_size_for_medium_content);
            i = R.dimen.content_text_size_for_medium;
        }
        boolean z2 = remoteViews.getLayoutId() == R.layout.people_tile_large_with_status_content;
        int i4 = this.mHeight;
        if (i3 == 1) {
            i2 = i4 - ((this.mMediumVerticalPadding * 2) + (lineHeightFromResource + 12));
        } else if (i3 != 2) {
            i2 = -1;
        } else {
            i2 = i4 - ((getSizeInDp(R.dimen.max_people_avatar_size_for_large_content) + lineHeightFromResource) + (z2 ? 76 : 62));
        }
        int max = Math.max(2, Math.floorDiv(i2, getLineHeightFromResource(i)));
        if (z) {
            max--;
        }
        remoteViews.setInt(R.id.text_content, "setMaxLines", max);
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
            int floor = (int) Math.floor(16.0f * f);
            int floor2 = (int) Math.floor(this.mMediumVerticalPadding * f);
            remoteViews.setViewPadding(R.id.content, floor, floor2, floor, floor2);
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
