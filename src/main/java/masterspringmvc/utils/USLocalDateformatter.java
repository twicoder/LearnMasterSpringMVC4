package masterspringmvc.utils;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by I302636 on 3/13/2017.
 */
public class USLocalDateformatter implements Formatter<LocalDate> {
    private static final String US_PATTERN = "MM/dd/yyyy";
    private static final String NORMAL_PATTERN = "dd/MM/yyyy";

    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        return LocalDate.parse(s, DateTimeFormatter.ofPattern(getPattern(locale)));
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return DateTimeFormatter.ofPattern(getPattern(locale)).format(localDate);
    }

    public static String getPattern(Locale locale){
        return isUnitedStates(locale) ? US_PATTERN: NORMAL_PATTERN;
    }

    private static boolean isUnitedStates(Locale locale){
        return Locale.US.getCountry().equals(locale.getCountry());
    }
}
