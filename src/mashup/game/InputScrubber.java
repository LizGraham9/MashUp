
package mashup.game;

import java.util.regex.Pattern;

/**
 *
 * @author Elizabeth
 */
class InputScrubber {
//    takes in text and returns alphanumeric, lowercase string

//    consider dropping articles ("the", "a", etc)
//    consider converting common symbols to english counterpart ("&" -> "and") before passing into regex
//    consider converting all numbers to their english counterpart ("1" -> "one") before passing into regex
    private static final Pattern PATTERN = Pattern.compile("[^A-Z0-9]", Pattern.CASE_INSENSITIVE);

    String scrubText(String text) {
        text = PATTERN.matcher(text).replaceAll("");
        text = text.toLowerCase();
        return text;
    }
}
