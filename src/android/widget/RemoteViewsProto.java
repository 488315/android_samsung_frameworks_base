package android.widget;

/* loaded from: classes5.dex */
public final class RemoteViewsProto {
    public static final long ACTIONS = 2246267895824L;
    public static final long APPLY_FLAGS = 1120986464263L;
    public static final long BITMAP_CACHE = 2250562863118L;
    public static final long HAS_DRAW_INSTRUCTIONS = 1133871366157L;
    public static final long IDEAL_SIZE = 1146756268038L;
    public static final long IS_ROOT = 1133871366156L;
    public static final long LANDSCAPE_REMOTEVIEWS = 1146756268043L;
    public static final long LAYOUT_ID = 1138166333443L;
    public static final long LIGHT_BACKGROUND_LAYOUT_ID = 1138166333444L;
    public static final long MODE = 1120986464257L;
    public static final long PACKAGE_NAME = 1138166333442L;
    public static final long PORTRAIT_REMOTEVIEWS = 1146756268042L;
    public static final long PROVIDER_INSTANCE_ID = 1112396529672L;
    public static final long REMOTE_COLLECTION_CACHE = 1146756268047L;
    public static final long SIZED_REMOTEVIEWS = 2246267895817L;
    public static final long UID = 1120986464273L;
    public static final long VIEW_ID = 1138166333445L;

    public final class RemoteCollectionCache {
        public static final long ENTRIES = 2246267895809L;

        public RemoteCollectionCache(RemoteViewsProto remoteViewsProto) {
        }

        public final class Entry {
            public static final long ID = 1112396529665L;
            public static final long ITEMS = 1146756268035L;
            public static final long URI = 1138166333442L;

            public Entry(RemoteCollectionCache remoteCollectionCache) {
            }
        }
    }

    public final class RemoteCollectionItems {
        public static final long ATTACHED = 1133871366149L;
        public static final long HAS_STABLE_IDS = 1133871366147L;
        public static final long IDS = 5510443040769L;
        public static final long VIEWS = 2246267895810L;
        public static final long VIEW_TYPE_COUNT = 1120986464260L;

        public RemoteCollectionItems(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class Icon {
        public static final long ADAPTIVE_BITMAP = 1151051235336L;
        public static final long BITMAP = 1151051235331L;
        public static final long BLEND_MODE = 1120986464257L;
        public static final long DATA = 1151051235333L;
        public static final long RESOURCE = 1138166333444L;
        public static final long TINT_LIST = 1146756268034L;
        public static final long URI = 1138166333446L;
        public static final long URI_ADAPTIVE_BITMAP = 1138166333447L;

        public Icon(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class CharSequence {
        public static final long SPANS = 2246267895810L;
        public static final long TEXT = 1138166333441L;

        public CharSequence(RemoteViewsProto remoteViewsProto) {
        }

        public final class Span {
            public static final long ABSOLUTE_SIZE = 2246267895812L;
            public static final long ACCESSIBILITY_CLICKABLE = 2246267895813L;
            public static final long ACCESSIBILITY_REPLACEMENT = 2246267895814L;
            public static final long ACCESSIBILITY_URL = 2246267895815L;
            public static final long ALIGNMENT = 2246267895816L;
            public static final long ANNOTATION = 2246267895817L;
            public static final long BACKGROUND_COLOR = 2246267895818L;
            public static final long BULLET = 2246267895819L;
            public static final long EASY_EDIT = 2246267895820L;
            public static final long END = 1120986464258L;
            public static final long FLAGS = 1120986464259L;
            public static final long FOREGROUND_COLOR = 2246267895821L;
            public static final long LEADING_MARGIN = 2246267895822L;
            public static final long LINE_BACKGROUND = 2246267895823L;
            public static final long LINE_BREAK = 2246267895824L;
            public static final long LINE_HEIGHT = 2246267895825L;
            public static final long LOCALE = 2246267895826L;
            public static final long QUOTE = 2246267895827L;
            public static final long RELATIVE_SIZE = 2246267895828L;
            public static final long SCALE_X = 2246267895829L;
            public static final long SPELL_CHECK = 2246267895830L;
            public static final long START = 1120986464257L;
            public static final long STRIKETHROUGH = 2246267895831L;
            public static final long STYLE = 2246267895832L;
            public static final long SUBSCRIPT = 2246267895833L;
            public static final long SUGGESTION = 2246267895834L;
            public static final long SUGGESTION_RANGE = 2246267895835L;
            public static final long SUPERSCRIPT = 2246267895836L;
            public static final long TEXT_APPEARANCE = 2246267895837L;
            public static final long TTS = 2246267895838L;
            public static final long TYPEFACE = 2246267895839L;
            public static final long UNDERLINE = 2246267895840L;
            public static final long URL = 2246267895841L;

            public Span(CharSequence charSequence) {
            }

            public final class AbsoluteSize {
                public static final long DIP = 1133871366146L;
                public static final long SIZE = 1120986464257L;

                public AbsoluteSize(Span span) {
                }
            }

            public final class AccessibilityClickable {
                public static final long ORIGINAL_CLICKABLE_SPAN_ID = 1120986464257L;

                public AccessibilityClickable(Span span) {
                }
            }

            public final class AccessibilityReplacement {
                public static final long CONTENT_DESCRIPTION = 1146756268033L;

                public AccessibilityReplacement(Span span) {
                }
            }

            public final class AccessibilityUrl {
                public static final long URL = 1138166333441L;

                public AccessibilityUrl(Span span) {
                }
            }

            public final class Alignment {
                public static final long ALIGNMENT = 1138166333441L;

                public Alignment(Span span) {
                }
            }

            public final class Annotation {
                public static final long KEY = 1138166333441L;
                public static final long VALUE = 1138166333442L;

                public Annotation(Span span) {
                }
            }

            public final class BackgroundColor {
                public static final long COLOR = 1120986464257L;

                public BackgroundColor(Span span) {
                }
            }

            public final class Bullet {
                public static final long BULLET_RADIUS = 1120986464259L;
                public static final long COLOR = 1120986464258L;
                public static final long GAP_WIDTH = 1120986464257L;
                public static final long WANT_COLOR = 1133871366148L;

                public Bullet(Span span) {
                }
            }

            public final class EasyEdit {
                public EasyEdit(Span span) {
                }
            }

            public final class ForegroundColor {
                public static final long COLOR = 1120986464257L;

                public ForegroundColor(Span span) {
                }
            }

            public final class LeadingMargin {
                public static final long FIRST = 1120986464257L;
                public static final long REST = 1120986464258L;

                public LeadingMargin(Span span) {
                }
            }

            public final class LineBackground {
                public static final long COLOR = 1120986464257L;

                public LineBackground(Span span) {
                }
            }

            public final class LineBreak {
                public static final long HYPHENATION = 1120986464259L;
                public static final long LINE_BREAK_STYLE = 1120986464257L;
                public static final long LINE_BREAK_WORD_STYLE = 1120986464258L;

                public LineBreak(Span span) {
                }
            }

            public final class LineHeight {
                public static final long HEIGHT = 1120986464257L;

                public LineHeight(Span span) {
                }
            }

            public final class Locale {
                public static final long LANGUAGE_TAGS = 1138166333441L;

                public Locale(Span span) {
                }
            }

            public final class Quote {
                public static final long COLOR = 1120986464257L;
                public static final long GAP_WIDTH = 1120986464259L;
                public static final long STRIPE_WIDTH = 1120986464258L;

                public Quote(Span span) {
                }
            }

            public final class RelativeSize {
                public static final long PROPORTION = 1108101562369L;

                public RelativeSize(Span span) {
                }
            }

            public final class ScaleX {
                public static final long PROPORTION = 1108101562369L;

                public ScaleX(Span span) {
                }
            }

            public final class SpellCheck {
                public static final long IN_PROGRESS = 1133871366145L;

                public SpellCheck(Span span) {
                }
            }

            public final class Strikethrough {
                public Strikethrough(Span span) {
                }
            }

            public final class Style {
                public static final long FONT_WEIGHT_ADJUSTMENT = 1120986464258L;
                public static final long STYLE = 1120986464257L;

                public Style(Span span) {
                }
            }

            public final class Subscript {
                public Subscript(Span span) {
                }
            }

            public final class Suggestion {
                public static final long AUTO_CORRECTION_UNDERLINE_COLOR = 1120986464266L;
                public static final long AUTO_CORRECTION_UNDERLINE_THICKNESS = 1108101562379L;
                public static final long EASY_CORRECT_UNDERLINE_COLOR = 1120986464262L;
                public static final long EASY_CORRECT_UNDERLINE_THICKNESS = 1108101562375L;
                public static final long FLAGS = 1120986464258L;
                public static final long GRAMMAR_ERROR_UNDERLINE_COLOR = 1120986464268L;
                public static final long GRAMMAR_ERROR_UNDERLINE_THICKNESS = 1108101562381L;
                public static final long HASH_CODE = 1120986464261L;
                public static final long LANGUAGE_TAG = 1138166333444L;
                public static final long LOCALE_STRING_FOR_COMPATIBILITY = 1138166333443L;
                public static final long MISSPELLED_UNDERLINE_COLOR = 1120986464264L;
                public static final long MISSPELLED_UNDERLINE_THICKNESS = 1108101562377L;
                public static final long SUGGESTIONS = 2237677961217L;

                public Suggestion(Span span) {
                }
            }

            public final class SuggestionRange {
                public static final long BACKGROUND_COLOR = 1120986464257L;

                public SuggestionRange(Span span) {
                }
            }

            public final class Superscript {
                public Superscript(Span span) {
                }
            }

            public final class TextAppearance {
                public static final long ELEGANT_TEXT_HEIGHT = 1133871366158L;
                public static final long FAMILY_NAME = 1138166333441L;
                public static final long FONT_FEATURE_SETTINGS = 1138166333457L;
                public static final long FONT_VARIATION_SETTINGS = 1138166333458L;
                public static final long HAS_ELEGANT_TEXT_HEIGHT_FIELD = 1133871366157L;
                public static final long HAS_LETTER_SPACING_FIELD = 1133871366159L;
                public static final long LETTER_SPACING = 1108101562384L;
                public static final long SHADOW_COLOR = 1120986464268L;
                public static final long SHADOW_DX = 1108101562378L;
                public static final long SHADOW_DY = 1108101562379L;
                public static final long SHADOW_RADIUS = 1108101562377L;
                public static final long STYLE = 1120986464258L;
                public static final long TEXT_COLOR = 1146756268036L;
                public static final long TEXT_COLOR_LINK = 1146756268037L;
                public static final long TEXT_FONT_WEIGHT = 1120986464263L;
                public static final long TEXT_LOCALE = 1138166333448L;
                public static final long TEXT_SIZE = 1120986464259L;

                public TextAppearance(Span span) {
                }
            }

            public final class Tts {
                public static final long ARGS = 1138166333442L;
                public static final long TYPE = 1138166333441L;

                public Tts(Span span) {
                }
            }

            public final class Typeface {
                public static final long FAMILY = 1138166333441L;

                public Typeface(Span span) {
                }
            }

            public final class Underline {
                public Underline(Span span) {
                }
            }

            public final class Url {
                public static final long URL = 1138166333441L;

                public Url(Span span) {
                }
            }
        }
    }

    public final class Action {
        public static final long ATTRIBUTE_REFLECTION_ACTION = 1146756268033L;
        public static final long BITMAP_REFLECTION_ACTION = 1146756268034L;
        public static final long COMPLEX_UNIT_DIMENSION_REFLECTION_ACTION = 1146756268035L;
        public static final long LAYOUT_PARAM_ACTION = 1146756268036L;
        public static final long NIGHT_MODE_REFLECTION_ACTION = 1146756268037L;
        public static final long REFLECTION_ACTION = 1146756268038L;
        public static final long REMOVE_FROM_PARENT_ACTION = 1146756268039L;
        public static final long RESOURCE_REFLECTION_ACTION = 1146756268040L;
        public static final long SET_COMPOUND_BUTTON_CHECKED_ACTION = 1146756268041L;
        public static final long SET_DRAWABLE_TINT_ACTION = 1146756268042L;
        public static final long SET_DRAW_INSTRUCTION_ACTION = 1146756268054L;
        public static final long SET_EMPTY_VIEW_ACTION = 1146756268043L;
        public static final long SET_INT_TAG_ACTION = 1146756268044L;
        public static final long SET_RADIO_GROUP_CHECKED_ACTION = 1146756268045L;
        public static final long SET_REMOTE_COLLECTION_ITEM_LIST_ADAPTER_ACTION = 1146756268046L;
        public static final long SET_RIPPLE_DRAWABLE_COLOR_ACTION = 1146756268047L;
        public static final long SET_VIEW_OUTLINE_PREFERRED_RADIUS_ACTION = 1146756268048L;
        public static final long TEXT_VIEW_DRAWABLE_ACTION = 1146756268049L;
        public static final long TEXT_VIEW_SIZE_ACTION = 1146756268050L;
        public static final long VIEW_GROUP_ADD_ACTION = 1146756268051L;
        public static final long VIEW_GROUP_REMOVE_ACTION = 1146756268052L;
        public static final long VIEW_PADDING_ACTION = 1146756268053L;

        public Action(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class AttributeReflectionAction {
        public static final long ATTRIBUTE_ID = 1138166333445L;
        public static final long METHOD_NAME = 1138166333442L;
        public static final long PARAMETER_TYPE = 1120986464259L;
        public static final long RESOURCE_TYPE = 1120986464260L;
        public static final long VIEW_ID = 1138166333441L;

        public AttributeReflectionAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class BitmapReflectionAction {
        public static final long BITMAP_ID = 1120986464259L;
        public static final long METHOD_NAME = 1138166333442L;
        public static final long VIEW_ID = 1138166333441L;

        public BitmapReflectionAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class ComplexUnitDimensionReflectionAction {
        public static final long DIMENSION_VALUE = 1108101562372L;
        public static final long METHOD_NAME = 1138166333442L;
        public static final long PARAMETER_TYPE = 1120986464259L;
        public static final long UNIT = 1120986464261L;
        public static final long VIEW_ID = 1138166333441L;

        public ComplexUnitDimensionReflectionAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class LayoutParamAction {
        public static final long LAYOUT_VALUE = 1120986464259L;
        public static final long PROPERTY = 1120986464258L;
        public static final long VALUE_TYPE = 1120986464260L;
        public static final long VIEW_ID = 1138166333441L;

        public LayoutParamAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class NightModeReflectionAction {
        public static final long DARK_COLOR_STATE_LIST = 1146756268040L;
        public static final long DARK_ICON = 1146756268039L;
        public static final long DARK_INT = 1120986464265L;
        public static final long LIGHT_COLOR_STATE_LIST = 1146756268037L;
        public static final long LIGHT_ICON = 1146756268036L;
        public static final long LIGHT_INT = 1120986464262L;
        public static final long METHOD_NAME = 1138166333442L;
        public static final long PARAMETER_TYPE = 1120986464259L;
        public static final long VIEW_ID = 1138166333441L;

        public NightModeReflectionAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class ReflectionAction {
        public static final long BITMAP_VALUE = 1151051235343L;
        public static final long BLEND_MODE_VALUE = 1120986464274L;
        public static final long BOOLEAN_VALUE = 1133871366148L;
        public static final long BYTE_VALUE = 1151051235333L;
        public static final long CHAR_SEQUENCE_VALUE = 1146756268045L;
        public static final long CHAR_VALUE = 1120986464267L;
        public static final long COLOR_STATE_LIST_VALUE = 1146756268048L;
        public static final long DOUBLE_VALUE = 1103806595082L;
        public static final long FLOAT_VALUE = 1108101562377L;
        public static final long ICON_VALUE = 1146756268049L;
        public static final long INT_VALUE = 1120986464263L;
        public static final long LONG_VALUE = 1112396529672L;
        public static final long METHOD_NAME = 1138166333442L;
        public static final long PARAMETER_TYPE = 1120986464259L;
        public static final long SHORT_VALUE = 1120986464262L;
        public static final long STRING_VALUE = 1138166333452L;
        public static final long URI_VALUE = 1138166333454L;
        public static final long VIEW_ID = 1138166333441L;

        public ReflectionAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class RemoveFromParentAction {
        public static final long VIEW_ID = 1138166333441L;

        public RemoveFromParentAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class ResourceReflectionAction {
        public static final long METHOD_NAME = 1138166333442L;
        public static final long PARAMETER_TYPE = 1120986464261L;
        public static final long RESOURCE_TYPE = 1120986464259L;
        public static final long RES_ID = 1138166333444L;
        public static final long VIEW_ID = 1138166333441L;

        public ResourceReflectionAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetCompoundButtonCheckedAction {
        public static final long CHECKED = 1133871366146L;
        public static final long VIEW_ID = 1138166333441L;

        public SetCompoundButtonCheckedAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetDrawableTintAction {
        public static final long COLOR_FILTER = 1120986464259L;
        public static final long FILTER_MODE = 1120986464260L;
        public static final long TARGET_BACKGROUND = 1133871366146L;
        public static final long VIEW_ID = 1138166333441L;

        public SetDrawableTintAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetEmptyViewAction {
        public static final long EMPTY_VIEW_ID = 1138166333442L;
        public static final long VIEW_ID = 1138166333441L;

        public SetEmptyViewAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetIntTagAction {
        public static final long KEY = 1138166333442L;
        public static final long TAG = 1120986464259L;
        public static final long VIEW_ID = 1138166333441L;

        public SetIntTagAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetRadioGroupCheckedAction {
        public static final long CHECKED_ID = 1138166333442L;
        public static final long VIEW_ID = 1138166333441L;

        public SetRadioGroupCheckedAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetRemoteCollectionItemListAdapterAction {
        public static final long ITEMS = 1146756268034L;
        public static final long VIEW_ID = 1138166333441L;

        public SetRemoteCollectionItemListAdapterAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetRippleDrawableColorAction {
        public static final long COLOR_STATE_LIST = 1146756268034L;
        public static final long VIEW_ID = 1138166333441L;

        public SetRippleDrawableColorAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetViewOutlinePreferredRadiusAction {
        public static final long VALUE = 1120986464259L;
        public static final long VALUE_TYPE = 1120986464258L;
        public static final long VIEW_ID = 1138166333441L;

        public SetViewOutlinePreferredRadiusAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class TextViewDrawableAction {
        public static final long ICONS = 1146756268036L;
        public static final long IS_RELATIVE = 1133871366146L;
        public static final long RESOURCES = 1146756268035L;
        public static final long VIEW_ID = 1138166333441L;

        public TextViewDrawableAction(RemoteViewsProto remoteViewsProto) {
        }

        public final class Resources {
            public static final long FOUR = 1138166333444L;
            public static final long ONE = 1138166333441L;
            public static final long THREE = 1138166333443L;
            public static final long TWO = 1138166333442L;

            public Resources(TextViewDrawableAction textViewDrawableAction) {
            }
        }

        public final class Icons {
            public static final long FOUR = 1146756268036L;
            public static final long ONE = 1146756268033L;
            public static final long THREE = 1146756268035L;
            public static final long TWO = 1146756268034L;

            public Icons(TextViewDrawableAction textViewDrawableAction) {
            }
        }
    }

    public final class TextViewSizeAction {
        public static final long SIZE = 1108101562371L;
        public static final long UNITS = 1120986464258L;
        public static final long VIEW_ID = 1138166333441L;

        public TextViewSizeAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class ViewGroupAddAction {
        public static final long INDEX = 1120986464259L;
        public static final long NESTED_VIEWS = 1146756268034L;
        public static final long STABLE_ID = 1120986464260L;
        public static final long VIEW_ID = 1138166333441L;

        public ViewGroupAddAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class ViewGroupRemoveAction {
        public static final long VIEW_ID = 1138166333441L;
        public static final long VIEW_ID_TO_KEEP = 1138166333442L;

        public ViewGroupRemoveAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class ViewPaddingAction {
        public static final long BOTTOM = 1120986464261L;
        public static final long LEFT = 1120986464258L;
        public static final long RIGHT = 1120986464259L;
        public static final long TOP = 1120986464260L;
        public static final long VIEW_ID = 1138166333441L;

        public ViewPaddingAction(RemoteViewsProto remoteViewsProto) {
        }
    }

    public final class SetDrawInstructionAction {
        public static final long INSTRUCTIONS = 2250562863105L;

        public SetDrawInstructionAction(RemoteViewsProto remoteViewsProto) {
        }
    }
}
