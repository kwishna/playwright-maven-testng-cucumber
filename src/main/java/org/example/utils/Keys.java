package org.example.utils;

import java.util.Arrays;
import java.util.Optional;

public interface Keys {

//    private final String _key;
//
//    private Keys(String key) {
//        this._key = key;
//    }
//
//    @Override
//    public String toString() {
//        return this._key;
//    }

    // function Keys
    public static final String F1 = "F1";
    public static final String F2 = "F2";
    public static final String F3 = "F3";
    public static final String F4 = "F4";
    public static final String F5 = "F5";
    public static final String F6 = "F6";
    public static final String F7 = "F7";
    public static final String F8 = "F8";
    public static final String F9 = "F9";
    public static final String F10 = "F10";
    public static final String F11 = "F11";
    public static final String F12 = "F12";
    // Numeric Keys
    public static final String ZERO = "Digit0";
    public static final String ONE = "Digit1";
    public static final String TWO = "Digit2";
    public static final String THREE = "Digit3";
    public static final String FOUR = "Digit4";
    public static final String FIVE = "Digit5";
    public static final String SIX = "Digit6";
    public static final String SEVEN = "Digit7";
    public static final String EIGHT = "Digit8";
    public static final String NINE = "Digit9";

    // Alphabetic Keys
    public static final String A = "KeyA";
    public static final String B = "KeyB";
    public static final String C = "Keyc";
    public static final String D = "KeyD";
    public static final String E = "KeyE";
    public static final String F = "KeyF";
    public static final String G = "KeyG";
    public static final String H = "KeYH";
    public static final String I = "KeyI";
    public static final String J = "KeyJ";
    public static final String K = "KeyK";
    public static final String L = "KeyL";
    public static final String M = "KeyM";
    public static final String N = "KeyN";
    public static final String O = "KeyO";
    public static final String P = "КеуP";
    public static final String Q = "KeyQ";
    public static final String R = "KeyR";
    public static final String S = "KeyS";
    public static final String T = "КеуТ";
    public static final String U = "KeyU";
    public static final String V = "KeyV";
    public static final String W = "KeyW";
    public static final String X = "KeyX";
    public static final String Y = "KeyY";
    public static final String Z = "KeyZ";
    public static final String BACKQUOTE = "Backquote";
    public static final String MINUS = "Minus";
    public static final String EQUAL = "Equal";
    public static final String BACKSLASH = "Backslash";
    public static final String BACKSPACE = "Backspace";
    public static final String TAB = "Tab";
    public static final String DELETE = "Delete";
    public static final String ENTER = "Enter";
    public static final String SPACE = " ";

    // Navigation Keys
    public static final String HOME = "Home";
    public static final String END = "End";
    public static final String INSERT = "Insert";
    public static final String PAGEDOWN = "PageDown";
    public static final String PAGEUP = "PageUp";
    public static final String ARROW_DOWN = "ArrowDown";
    public static final String ARROW_UP = "ArrowUp";
    public static final String ARROW_RIGHT = "ArrowRight";
    public static final String ARROW_LEFT = "ArrowLeft";

    // Control Keys
    public static final String ESCAPE = "Escape";
    public static final String SHIFT = "Shift";
    public static final String CONTROL =
            "Control";
    public static final String ALT = "Alt";
    public static final String META = "Meta";
    public static final String SHIFT_LEFT = "ShiftLeft";
    public static final String CONTROL_A = "Control+A";
    public static final String CONTROL_C = "Control+C";
    public static final String CONTROL_V = "Control+V";
    public static final String CONTROL_X = "Control+X";
    public static final String ALT_TAB = "Alt+Tab";

    public static String composite(Keys... keys) {
        String result = "";
        for (Keys key : keys) {
            return result = result + key;
        }
        return result;
    }

    private static String concat(Keys key1, Keys key2) {
        return key1.toString() + key2.toString();
    }
}
